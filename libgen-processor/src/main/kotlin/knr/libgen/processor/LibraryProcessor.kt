package knr.libgen.processor

import com.github.callmephil.knr.runtime.typing.CString
import com.github.callmephil.knr.runtime.typing.flags.BitFlagSet
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
import knr.libgen.annotations.Library
import knr.libgen.annotations.Method
import knr.libgen.annotations.NoVerify
import knr.libgen.annotations.StringParam
import knr.libgen.processor.ext.addClsImport
import knr.libgen.processor.ext.assignableTo
import knr.libgen.processor.ext.get
import knr.libgen.processor.ext.has
import knr.libgen.processor.ext.inheritsNative
import knr.libgen.processor.ext.isPrimitive
import knr.libgen.processor.ext.isString
import knr.libgen.processor.ext.pascalToSnakecase
import knr.libgen.processor.ext.qualifiedName
import knr.libgen.processor.ext.simpleName
import knr.libgen.processor.ext.toValueLayoutString
import knr.libgen.processor.util.standardCharsets
import org.tinylog.Level
import org.tinylog.configuration.Configuration
import java.lang.foreign.Arena
import java.lang.foreign.Linker
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

    private fun addFunctionBody(
        func: KSFunctionDeclaration,
        builder: FunSpec.Builder,
        methodHandle: PropertySpec,
        caches: Map<String, PropertySpec>,
        resolver: Resolver
    ) {
        val funcBody = CodeBlock.builder()
        val invokeParamsList = mutableListOf<String>()
        val noVerifyMethod = func.annotations.has<NoVerify>(resolver)

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
                    val stringParamAnno = param.annotations.get<StringParam>(resolver)
                    val annoCharsetName = stringParamAnno.get<String>(StringParam::charset.name)
                    val charset = standardCharsets[annoCharsetName]

                    val charsetString = if (charset != null) "StandardCharsets.$charset" else "charset(\"$annoCharsetName\")"

                    val cacheName = caches[paramName]!!.name

                    funcBody.addStatement("%L?.dispose()", cacheName)
                    funcBody.addStatement("%L = cstringOf(%L, %L)", cacheName, paramName, charsetString)
                    invokeParamsList.add("${cacheName}!!.memory.memorySegment")
                }
                type.inheritsNative(resolver) -> {
                    if (!noVerifyMethod && !param.annotations.has<NoVerify>(resolver))
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

        when {
            returnType.toClassName() == Unit::class.java.asTypeName() -> {
                funcBody.addStatement("%L.invokeExact(%L)", methodHandle.name, invokeParams)
            }
            returnType.isPrimitive -> {
                val returnTypeString = returnType.toClassName().simpleName
                funcBody.addStatement("return %L.invokeExact(%L) as %L", methodHandle.name, invokeParams, returnTypeString)
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

        fileSpec.addImport("com.github.callmephil.knr.runtime.ext", "downcallHandle")
        fileSpec.addImport("com.github.callmephil.knr.runtime.typing", "cstringOf")
        fileSpec.addClsImport(ValueLayout::class.java)
        fileSpec.addClsImport(StandardCharsets::class.java)

        logger.debug { "Created FileSpec[Path: ${fileSpec.packageName}.${fileSpec.name} | ClassName: $clsName]" }
        return fileSpec
    }

    private fun createCaches(func: KSFunctionDeclaration, resolver: Resolver): Map<String, PropertySpec> =
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

        val propertySpec = PropertySpec
            .builder("${func.simpleName.asString()}Handle", MethodHandle::class, KModifier.PRIVATE)
            .mutable(false)
            .initializer("""linker.downcallHandle(
                |    segment = lookup.find(%S).orElseThrow(),
                |    retType = %L,
                |    %L
                |)
            """.trimMargin(), nativeName, returnType, paramsString)
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

                    val caches = createCaches(func, resolver)
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
            fileSpec.build().writeTo(codeGenerator, aggregating = false)
        }

        return emptyList()
    }

    private fun validateFunc(func: KSFunctionDeclaration, resolver: Resolver) {
        val funcName = func.simpleName.asString()

        func.parameters.forEach {
            val type = it.type.resolve()
            val paramName = it.name!!.asString()

            if (type.isString) {
                if (!it.annotations.has<StringParam>(resolver))
                    throw IllegalStateException("Parameter '$paramName: String' for function '$funcName' does not have a 'StringParam' annotation")
            }
            else if (!type.isPrimitive && !type.inheritsNative(resolver) && !type.assignableTo<BitFlagSet<*, *>>(resolver))
                throw IllegalStateException("Parameter '$paramName: ${type.qualifiedName!!.asString()}' for function '$funcName' is not a supported type")
        }

        val returnType = func.returnType!!.resolve()
        if (!returnType.isPrimitive) {
            val returnTypeName = returnType.declaration.qualifiedName!!.asString()
            throw IllegalStateException("Return type '${returnTypeName}' for function '$funcName' is not a primitive")
        }
    }
}

class LibraryProcessorProvider : SymbolProcessorProvider {
    private lateinit var logger: KLogger

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logLevelProp = environment.options["libgen.logLevel"] ?: "info"
        val level = Level.valueOf(logLevelProp.uppercase())
        Configuration.set("level", level.name)

        logger = KotlinLogging.logger {  }
        logger.info{ "Log Level: ${level.name}" }

        return LibraryProcessor(environment.codeGenerator)
    }
}
