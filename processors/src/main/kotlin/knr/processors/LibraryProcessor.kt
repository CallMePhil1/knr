package knr.processors

import knr.runtime.memory.NativeMemory
import knr.runtime.typing.CString
import knr.runtime.typing.flags.BitFlagSet
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import knr.annotations.IgnoreReturnsNative
import knr.annotations.Library
import knr.annotations.Method
import knr.annotations.NoVerify
import knr.annotations.ReturnsNative
import knr.annotations.StringParam
import knr.processors.ext.addClsImport
import knr.processors.ext.assignableTo
import knr.processors.ext.get
import knr.processors.ext.has
import knr.processors.ext.inheritsNative
import knr.processors.ext.isPrimitive
import knr.processors.ext.isString
import knr.processors.ext.pascalToSnakecase
import knr.processors.ext.qualifiedName
import knr.processors.ext.simpleName
import knr.processors.ext.toValueLayoutString
import knr.processors.util.standardCharsets
import org.tinylog.Level
import org.tinylog.configuration.Configuration
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle
import java.nio.charset.StandardCharsets
import kotlin.collections.listOf

private val functionIgnoreList = setOf(
    "kotlin.Any.equals",
    "kotlin.Any.hashCode",
    "kotlin.Any.toString"
)

internal class LibraryProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    private val logger = KotlinLogging.logger {  }

    @OptIn(KspExperimental::class)
    private fun addFunctionBody(
        func: KSFunctionDeclaration,
        builder: FunSpec.Builder,
        methodHandle: PropertySpec,
        caches: Map<String, PropertySpec>,
        resolver: Resolver
    ) {
        val funcBody = CodeBlock.builder()
        val invokeParamsList = mutableListOf<String>()
        val noVerifyMethod = func.annotations.has<NoVerify>()

        func.parameters.forEach { param ->
            val type = param.type.resolve()
            val paramName = param.name!!.asString()

            when {
                param.isPrimitive -> {
                    val typeName = type.simpleName.asString()
                    val conversionCall = when {
                        typeName.startsWith("U") -> ".to" + typeName.slice(1..typeName.lastIndex) + "()"
                        else -> ""
                    }
                    invokeParamsList.add("$paramName$conversionCall")
                }
                type.isString -> {
                    val stringParamAnno = param.annotations.get<StringParam>()
                    val annoCharsetName = stringParamAnno.get<String>(StringParam::charset.name)
                    val charset = standardCharsets[annoCharsetName]

                    val charsetString = if (charset != null) "StandardCharsets.$charset" else "charset(\"$annoCharsetName\")"

                    val cacheName = caches[paramName]!!.name

                    funcBody.addStatement("%L?.dispose()", cacheName)
                    funcBody.addStatement("%L = cstringOf(%L, %L)", cacheName, paramName, charsetString)
                    invokeParamsList.add("${cacheName}!!.memory.memorySegment")
                }
                type.inheritsNative(resolver) -> {
                    if (!noVerifyMethod && !param.annotations.has<NoVerify>())
                        funcBody.addStatement("%L.verifyIsValid()", paramName)
                    invokeParamsList.add("${paramName}.memory.memorySegment")
                }
                type.assignableTo<BitFlagSet<*, *>>(resolver) -> {
                    invokeParamsList.add("$paramName.mask")
                }
            }
        }

        val invokeParams = invokeParamsList.joinToString()
        val returnType = func.returnType!!.resolve()
        val returnTypeName = returnType.simpleName.asString()

        when {
            returnType.toClassName() == Unit::class.java.asTypeName() -> {
                funcBody.add("%L.invokeExact(%L)", methodHandle.name, invokeParams)
            }
            returnType.isPrimitive -> {
                val returnTypeString = returnType.toClassName().simpleName
                funcBody.add("return %L.invokeExact(%L) as %L", methodHandle.name, invokeParams, returnTypeString)
            }
            returnType.inheritsNative(resolver) -> {
                val annotation = when {
                    func.annotations.has<IgnoreReturnsNative>() -> null
                    func.annotations.has<ReturnsNative>() -> func.annotations.get<ReturnsNative>()
                    func.parentDeclaration?.annotations?.has<ReturnsNative>() ?: false -> func.parentDeclaration!!.annotations.get<ReturnsNative>()
                    else -> null
                }

                if (annotation != null) {
                    val cls = annotation.get<KSType>("cls")
                    val funcName = annotation.get<String>("disposeFunName")
                    val clsImplPackage = "${cls.declaration.packageName.asString()}.generated"
                    val clsImplName = "${cls.simpleName.asString()}Impl"
                    val invokeCall = "$clsImplPackage.$clsImplName.$funcName"

                    funcBody.add("""val byteSize = ${returnTypeName}.definition.byteSize
                        |val result = (${methodHandle.name}.invokeExact($invokeParams) as MemorySegment).reinterpret(byteSize)
                        |var struct: ${returnTypeName}? = null
                        |val disposeFun = { $invokeCall(struct!!) }
                        |val memory = NativeMemory.wrap(result, disposeFun)
                        |struct = ${returnTypeName}.wrap(memory)
                        |return struct
                    """.trimMargin())

                } else {
                    funcBody.add("""val byteSize = ${returnTypeName}.definition.byteSize
                        |val result = (${methodHandle.name}.invokeExact($invokeParams) as MemorySegment).reinterpret(byteSize)
                        |val memory = NativeMemory.wrap(result)
                        |return ${returnTypeName}.wrap(memory)
                    """.trimMargin())
                }
            }
            else -> {}
        }

        builder.addCode(funcBody.build())
    }

    private fun addFunctionHeader(func: KSFunctionDeclaration, builder: FunSpec.Builder, resolver: Resolver) {
        logger.debug { "Creating function header for ${func.qualifiedName}" }

        builder.addModifiers(KModifier.OVERRIDE)

        val parameters = func.parameters.map {
            val paramTypeRef = it.type
            val paramType = paramTypeRef.resolve()
            val isNative = paramType.inheritsNative(resolver)

            logger.debug { "Adding Parameter[Name: ${it.name!!.asString()}, Type: ${paramType.declaration.simpleName.asString()}, IsPrimitive: ${paramType.isPrimitive}, Native: $isNative]" }
            ParameterSpec.builder(it.name!!.asString(), it.type.toTypeName()).build()
        }

        builder.addParameters(parameters)

        val returnType = func.returnType!!.resolve().declaration.qualifiedName!!.asString()

        logger.debug { "Adding Return[Type: $returnType]" }

        builder.returns(func.returnType!!.toTypeName())
    }

    private fun createFileSpec(cls: KSClassDeclaration): FileSpec.Builder {
        val packageName = cls.qualifiedName!!.getQualifier() + ".generated"
        val clsName = cls.simpleName.asString() + "Impl"
        val fileSpec = FileSpec.builder(packageName, clsName)

        fileSpec.addImport("knr.runtime.ext", "downcallHandle")
        fileSpec.addImport("knr.runtime.typing", "cstringOf")
        fileSpec.addClsImport(MemorySegment::class.java)
        fileSpec.addClsImport(NativeMemory::class.java)
        fileSpec.addClsImport(StandardCharsets::class.java)
        fileSpec.addClsImport(ValueLayout::class.java)

        logger.debug { "Created FileSpec[Path: ${fileSpec.packageName}.${fileSpec.name} | ClassName: $clsName]" }
        return fileSpec
    }

    private fun createCaches(func: KSFunctionDeclaration): Map<String, PropertySpec> =
        func.parameters
            .filter { it.isString }
            .associate {
                val name = func.simpleName.asString() + it.name!!.asString()
                val property = PropertySpec.builder(name, CString::class.asClassName().copy(nullable = true), KModifier.PRIVATE)
                    .mutable(true)
                    .initializer("null")
                    .build()
                it.name!!.asString() to property
            }

    private fun createMethodHandleProperty(func: KSFunctionDeclaration, nativeName: String, resolver: Resolver): PropertySpec {
        val params = func.parameters.map { it.type.resolve().toValueLayoutString(resolver) }
        val paramsString = params.joinToString(",\n    ")
        val returnType = func.returnType!!.resolve().toValueLayoutString(resolver)

        val initializer = CodeBlock.builder()
        initializer.add("""linker.downcallHandle(
            |segment = lookup.find(%S).orElseThrow(),
            |retType = %L,
            |%L
            |)""".trimMargin(), nativeName, returnType, paramsString)

        val propertySpec = PropertySpec
            .builder("${func.simpleName.asString()}Handle", MethodHandle::class, KModifier.PRIVATE)
            .mutable(false)
            .initializer(initializer.build())
            .build()

        logger.debug { "Create MethodHandle PropertySpec[Name: ${propertySpec.name}, Params: {$paramsString}, Return: $returnType]" }

        return propertySpec
    }

    @OptIn(KspExperimental::class)
    private fun createObjectSpec(libCls: KSClassDeclaration): TypeSpec.Builder {
        val libPath = libCls.getAnnotationsByType(Library::class).first().libPath
        val arenaProperty = PropertySpec.builder("arena", Arena::class, KModifier.PRIVATE)
            .initializer("Arena.global()")
            .build()
        val linkerProperty = PropertySpec.builder("linker", Linker::class, KModifier.PRIVATE)
            .initializer("Linker.nativeLinker()")
            .build()
        val lookupProperty = PropertySpec.builder("lookup", SymbolLookup::class, KModifier.PRIVATE)
            .initializer("SymbolLookup.libraryLookup(%S, %L)", libPath, "arena")
            .build()

        val objectName = "${libCls.simpleName.asString()}Impl"

        logger.debug { "Getting TypeSpec[LibPath: $libPath]" }

        return TypeSpec.objectBuilder(objectName)
            .addSuperinterface(libCls.asStarProjectedType().toClassName())
            .addProperties(listOf(arenaProperty, linkerProperty, lookupProperty))
    }

    private fun getNativeFunctionName(func: KSFunctionDeclaration, resolver: Resolver): String {
        val methodAnnotation = func.annotations.firstOrNull { it.annotationType.resolve().assignableTo<Method>(resolver) }

        return when {
            methodAnnotation == null -> func.simpleName.asString().pascalToSnakecase()
            else -> methodAnnotation.arguments.first {
                it.name!!.asString() == "name"
            }.value!! as String
        }
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Library::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()

        symbols.forEach { lib ->
            val fileSpec = createFileSpec(lib).indent("    ")
            val objectSpec = createObjectSpec(lib)

            lib.getAllFunctions()
                .filter { it.qualifiedName!!.asString() !in functionIgnoreList }
                .forEach { func ->
                    validateFunc(func, resolver)
                    val funcNativeName = getNativeFunctionName(func, resolver)

                    val caches = createCaches(func)
                    val methodHandle = createMethodHandleProperty(func, funcNativeName,resolver)

                    val funcSpecBuilder = FunSpec.builder(func.simpleName.asString())
                    addFunctionHeader(func, funcSpecBuilder, resolver)
                    addFunctionBody(func, funcSpecBuilder, methodHandle, caches, resolver)

                    val overrideFunc = funcSpecBuilder.build()

                    objectSpec.addProperty(methodHandle)
                    objectSpec.addProperties(caches.values)
                    objectSpec.addFunction(overrideFunc)
                }

            fileSpec.addType(objectSpec.build())
            fileSpec.build().writeTo(codeGenerator, aggregating = false, originatingKSFiles = listOf(lib.containingFile!!))
        }

        return emptyList()
    }

    private fun validateFunc(func: KSFunctionDeclaration, resolver: Resolver) {
        val funcName = func.simpleName.asString()

        func.parameters.forEach {
            val type = it.type.resolve()
            val paramName = it.name!!.asString()

            if (type.isString) {
                if (!it.annotations.has<StringParam>())
                    throw IllegalStateException("Parameter '$paramName: String' for function '$funcName' does not have a 'StringParam' annotation")
            }
            else if (!type.isPrimitive && !type.inheritsNative(resolver) && !type.assignableTo<BitFlagSet<*, *>>(resolver))
                throw IllegalStateException("Parameter '$paramName: ${type.qualifiedName!!.asString()}' for function '$funcName' is not a supported type")
        }

        val returnType = func.returnType!!.resolve()
        if (!returnType.isPrimitive && !returnType.inheritsNative(resolver)) {
            val returnTypeName = returnType.declaration.qualifiedName!!.asString()
            throw IllegalStateException("Return type '${returnTypeName}' for function '$funcName' is not a primitive or inherits 'Native'")
        }
    }
}

internal class LibraryProcessorProvider : SymbolProcessorProvider {
    private lateinit var logger: KLogger

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logLevelProp = environment.options["knr.processors.logLevel"] ?: "info"
        val level = Level.valueOf(logLevelProp.uppercase())
        Configuration.set("level", level.name)

        logger = KotlinLogging.logger {  }
        logger.info{ "Log Level: ${level.name}" }

        return LibraryProcessor(environment.codeGenerator)
    }
}
