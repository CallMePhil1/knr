@file:OptIn(KspExperimental::class)

package knr.processors

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import knr.annotations.*
import knr.annotations.string.DisposeMethod
import knr.annotations.string.ReturnsString
import knr.annotations.string.StringParam
import knr.processors.ext.*
import knr.processors.util.getBitFlagValueType
import knr.processors.util.getCharset
import knr.processors.util.getNativeEnumValueType
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.NativeMemory
import knr.runtime.typing.CString
import knr.runtime.typing.NativeEnum
import knr.runtime.typing.Struct
import knr.runtime.typing.flags.BitFlagSet
import knr.runtime.typing.pointer.NativePointer
import knr.runtime.typing.pointer.Pointer
import org.tinylog.Level
import org.tinylog.configuration.Configuration
import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.nio.charset.StandardCharsets

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
        logger.debug { "Adding function body for '${func.qualifiedName!!.asString()}'" }

        val funcBody = CodeBlock.builder()
        val invokeParamsList = mutableListOf<String>()

        val preInvoke = CodeBlock.builder()
        val invokeBlock = CodeBlock.builder()
        val postInvoke = CodeBlock.builder()
        val returnBlock = CodeBlock.builder()

        val isStruct = func.returnType!!.resolve().assignableTo<Struct<*>>(resolver)
        val returnsByValue = isStruct && !func.annotations.has<ByRef>()

        if (returnsByValue) {
            invokeParamsList.add("(memory.memorySegment as SegmentAllocator)")
        }

        func.parameters.forEach { param ->
            logger.debug { "Processing Param[Name: '${param.name!!.asString()}', Type: '${param.type.toTypeName()}']" }

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
                    val stringParamAnno = param.getAnnotationsByType(StringParam::class).first()
                    val annoCharsetName = stringParamAnno.charset
                    val disposeMethod = stringParamAnno.disposeMethod

                    val charsetString = getCharset(annoCharsetName)

                    when (disposeMethod) {
                        DisposeMethod.AFTER_USE -> {
                            val cstringName = "${paramName}String"
                            preInvoke.addStatement(
                                "val %L = cstringOf(%L, %L)",
                                cstringName,
                                paramName,
                                charsetString
                            )
                            postInvoke.addStatement("%L.dispose()", cstringName)
                            invokeParamsList.add("${cstringName}.memory.memorySegment")
                        }

                        DisposeMethod.CACHE -> {
                            val cacheName = caches[paramName]!!.name

                            preInvoke.addStatement("%L?.dispose()", cacheName)
                            preInvoke.addStatement("%L = cstringOf(%L, %L)", cacheName, paramName, charsetString)
                            invokeParamsList.add("${cacheName}!!.memory.memorySegment")
                        }

                        DisposeMethod.NONE -> {
                            val cstringName = "${paramName}String"
                            preInvoke.addStatement(
                                "val %L = cstringOf(%L, %L)",
                                cstringName,
                                paramName,
                                charsetString
                            )
                            invokeParamsList.add("${cstringName}.memory.memorySegment")
                        }
                    }
                }
                type.inheritsNative(resolver) -> {
                    invokeParamsList.add("${paramName}.memory.memorySegment")
                }
                type.assignableTo<BitFlagSet<*, *>>(resolver) -> {
                    invokeParamsList.add("$paramName.mask")
                }
                type.assignableTo<NativeEnum<*>>(resolver) -> {
                    invokeParamsList.add("$paramName.value")
                }
            }
        }

        val invokeParams = invokeParamsList.joinToString()
        val returnType = func.returnType!!.resolve()
        val returnTypeName = returnType.simpleName.asString()

        logger.debug { "Processing Return[Type: '${returnType.qualifiedName!!.asString()}']" }

        when {
            returnType.toTypeName() == Unit::class.java.asTypeName() -> {
                invokeBlock.addStatement("%L.invokeExact(%L)", methodHandle.name, invokeParams)
            }
            returnType.isPrimitive -> {
                val returnTypeString = returnType.toClassName().simpleName
                invokeBlock.addStatement("val result = %L.invokeExact(%L) as %L", methodHandle.name, invokeParams, returnTypeString)
                returnBlock.addStatement("return result")
            }
            returnType.isString -> {
                if (!func.annotations.has<ReturnsString>())
                    error("Function ${func.qualifiedName!!.asString()} returns 'String' but is missing 'ReturnsString' annotation")

                val returnStringAnno = func.getAnnotationsByType(ReturnsString::class).first()
                val charset = getCharset(returnStringAnno.charset)

                invokeBlock.addStatement("val result = ${methodHandle.name}.invokeExact($invokeParams) as MemorySegment")

                postInvoke.addStatement("var cstring: CString? = null")

                when (val returnNativeAnno = getDisposer(func)) {
                    null -> {
                        postInvoke.addStatement("val memory = NativeMemory.wrap(result, null)")
                    }
                    else -> {
                        val disposeFuncString = getDisposeFuncString(returnNativeAnno)

                        postInvoke.add("""val disposeFun = { $disposeFuncString(cstring!!) }
                            |val memory = NativeMemory.wrap(result, disposeFun)
                            |""".trimMargin())
                    }
                }

                postInvoke.add("""cstring = cstringOf(memory, $charset)
                    |val string = cstring.get()
                    |cstring.dispose()
                    |""".trimMargin())

                returnBlock.addStatement("return string")
            }
            returnType.assignableTo<CString>(resolver) -> {
                if (!func.annotations.has<ReturnsString>())
                    error("Function ${func.qualifiedName!!.asString()} returns 'CString' but is missing 'ReturnsString' annotation")

                val returnStringAnno = func.getAnnotationsByType(ReturnsString::class).first()
                val charset = getCharset(returnStringAnno.charset)

                invokeBlock.addStatement("val result = ${methodHandle.name}.invokeExact($invokeParams) as MemorySegment")

                postInvoke.addStatement("var cstring: CString? = null")

                when (val returnNativeAnno = getDisposer(func)) {
                    null -> {
                        postInvoke.addStatement("val memory = NativeMemory.wrap(result, null)")
                    }
                    else -> {
                        val disposeFuncString = getDisposeFuncString(returnNativeAnno)

                        postInvoke.add("""val disposeFun = { $disposeFuncString(cstring!!) }
                            |val memory = NativeMemory.wrap(result, disposeFun)
                            |""".trimMargin())
                    }
                }

                postInvoke.addStatement("cstring = cstringOf(memory, $charset)")

                returnBlock.addStatement("return cstring")
            }
            returnType.assignableTo<NativePointer<*>>(resolver) -> {
                invokeBlock.addStatement("val result = (${methodHandle.name}.invokeExact($invokeParams) as MemorySegment).reinterpret(byteSize)")

                val structType = returnType.arguments[0].type!!.resolve()
                val structTypeName = structType.qualifiedName!!.asString()

                when(val disposeAnno = getDisposer(func)) {
                    null -> {
                        preInvoke.addStatement("val byteSize = ${structTypeName}.definition.byteSize")
                        postInvoke.add("""val memory = NativeMemory.wrap(result, null)
                            |val struct = ${structTypeName}.wrap(memory)
                            |""".trimMargin())
                        returnBlock.addStatement("return nativePointerOf(struct)")
                    }
                    else -> {
                        val disposeCall = getDisposeFuncString(disposeAnno)

                        preInvoke.addStatement("val byteSize = ${structTypeName}.definition.byteSize")

                        postInvoke.add("""var ptr: ${returnTypeName}<$structTypeName>? = null
                                |val disposeFun = { $disposeCall(ptr!!) }
                                |val memory = NativeMemory.wrap(result, disposeFun)
                                |val struct = ${structTypeName}.wrap(memory)
                                |ptr = struct.asPointer()
                                |""".trimMargin())

                        returnBlock.addStatement("return nativePointerOf(struct)")
                    }
                }
            }
            returnType.assignableTo<Pointer<*>>(resolver) -> {
                val returnTypeString = returnTypeName[0].lowercase() + returnTypeName.substring(1)

                when (val disposeAnno = getDisposer(func)) {
                    null -> {
                        invokeBlock.addStatement("val result = ${methodHandle.name}.invokeExact($invokeParams) as MemorySegment")
                        postInvoke.addStatement("val memory = NativeMemory.wrap(result, null)")
                        returnBlock.addStatement("return ${returnTypeString}Of(memory)")
                    }
                    else -> {
                        val invokeCall = getDisposeFuncString(disposeAnno)

                        invokeBlock.addStatement("val result = ${methodHandle.name}.invokeExact($invokeParams) as MemorySegment")

                        postInvoke.add("""var pointer: ${returnTypeName}? = null
                        |val disposeFun = { $invokeCall(pointer!!) }
                        |val memory = NativeMemory.wrap(result, disposeFun)
                        |pointer = ${returnTypeString}Of(memory)
                        |""".trimMargin())

                        returnBlock.addStatement("return pointer")
                    }
                }
            }
            returnType.inheritsNative(resolver) -> {
                when(returnsByValue) {
                    true -> {
                        preInvoke.addStatement("val memory = ArenaMemory.allocate(${returnTypeName}.definition.byteSize)")

                        invokeBlock.addStatement("${methodHandle.name}.invokeExact($invokeParams) as MemorySegment")

                        returnBlock.addStatement("return ${returnTypeName}.wrap(memory)")
                    }
                    false -> {
                        val disposeAnno = getDisposer(func)

                        if (disposeAnno != null) {
                            val disposeCall = getDisposeFuncString(disposeAnno)

                            preInvoke.addStatement("val byteSize = ${returnTypeName}.definition.byteSize")

                            invokeBlock.addStatement("val result = (${methodHandle.name}.invokeExact($invokeParams) as MemorySegment).reinterpret(byteSize)")

                            postInvoke.add("""var struct: ${returnTypeName}? = null
                                |val disposeFun = { $disposeCall(struct!!) }
                                |val memory = NativeMemory.wrap(result, disposeFun)
                                |struct = ${returnTypeName}.wrap(memory)
                                |""".trimMargin())

                            returnBlock.addStatement("return struct")

                        } else {
                            preInvoke.addStatement("val byteSize = ${returnTypeName}.definition.byteSize")
                            invokeBlock.addStatement("val result = (${methodHandle.name}.invokeExact($invokeParams) as MemorySegment).reinterpret(byteSize)")
                            postInvoke.addStatement("val memory = NativeMemory.wrap(result, null)")
                            returnBlock.addStatement("return ${returnTypeName}.wrap(memory)")
                        }
                    }
                }
            }
            returnType.assignableTo<BitFlagSet<*, *>>(resolver) -> {
                val valueType = returnType.getBitFlagValueType(resolver)
                invokeBlock.addStatement("val result = ${methodHandle.name}.invokeExact($invokeParams) as ${valueType.simpleName.asString()}")
                returnBlock.addStatement("return ${returnType.qualifiedName!!.asString()}(result)")
            }
            returnType.assignableTo<NativeEnum<*>>(resolver) -> {
                val valueType = returnType.getNativeEnumValueType(resolver)
                invokeBlock.addStatement("val result = ${methodHandle.name}.invokeExact($invokeParams) as ${valueType.simpleName.asString()}")
                returnBlock.addStatement("return ${returnType.qualifiedName!!.asString()}.of(result)")
            }
            else -> {}
        }

        funcBody.add(preInvoke.build())
        funcBody.add(invokeBlock.build())
        funcBody.add(postInvoke.build())
        funcBody.add(returnBlock.build())

        builder.addCode(funcBody.build())
    }

    private fun addFunctionHeader(func: KSFunctionDeclaration, builder: FunSpec.Builder, resolver: Resolver) {
        logger.debug { "Creating function header for '${func.qualifiedName!!.asString()}'" }

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
        fileSpec.addPointerFuncs()
        fileSpec.addClsImport(MemorySegment::class.java, SegmentAllocator::class.java)
        fileSpec.addClsImport(ArenaMemory::class.java, NativeMemory::class.java)
        fileSpec.addClsImport(StandardCharsets::class.java)
        fileSpec.addClsImport(ValueLayout::class.java)

        logger.debug { "Created FileSpec[Path: ${fileSpec.packageName}.${fileSpec.name} | ClassName: $clsName]" }
        return fileSpec
    }

    private fun createCaches(func: KSFunctionDeclaration): Map<String, PropertySpec> =
        func.parameters
            .filter { it.isString }
            .filter {
                val annotation = it.getAnnotationsByType(StringParam::class).first()
                val disposeMethod = annotation.disposeMethod
                return@filter disposeMethod == DisposeMethod.CACHE
            }
            .associate {
                val name = func.simpleName.asString() + it.name!!.asString()
                val property = PropertySpec.builder(name, CString::class.asClassName().copy(nullable = true), KModifier.PRIVATE)
                    .mutable(true)
                    .initializer("null")
                    .build()
                it.name!!.asString() to property
            }

    private fun createMethodHandleProperty(func: KSFunctionDeclaration, nativeName: String, resolver: Resolver): PropertySpec {
        val params = func.parameters.map { it.toMemoryLayout(resolver) }

        val paramsString = params.joinToString(",\n    ")
        val returnType = func.returnType!!.resolve()
        val returnTypeString = func.returnToMemoryLayout(resolver)

        val initializer = CodeBlock.builder()
        initializer.add("""linker.downcallHandle(
            |segment = lookup.find(%S).orElseThrow(),
            |retType = %L,
            |%L
            |)""".trimMargin(), nativeName, returnTypeString, paramsString)

        val propertySpec = PropertySpec
            .builder("${func.simpleName.asString()}Handle", MethodHandle::class, KModifier.PRIVATE)
            .mutable(false)
            .initializer(initializer.build())
            .build()

        logger.debug { "Create MethodHandle PropertySpec[Name: ${propertySpec.name}, Params: {$paramsString}, Return: $returnType]" }

        return propertySpec
    }

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

    private fun getDisposeFuncString(annotation: KSAnnotation): String {
        val cls = annotation.get<KSType>("cls")
        val funcName = annotation.get<String>("disposeFunName")
        val clsImplPackage = "${cls.declaration.packageName.asString()}.generated"
        val clsImplName = "${cls.simpleName.asString()}Impl"
        return "$clsImplPackage.$clsImplName.$funcName"
    }

    private fun getNativeFunctionName(naming: NamingConvention, func: KSFunctionDeclaration, resolver: Resolver): String {
        val methodAnnotation = func.annotations.firstOrNull { it.annotationType.resolve().assignableTo<Method>(resolver) }

        return when {
            methodAnnotation == null -> when (naming) {
                NamingConvention.CAMELCASE -> func.simpleName.asString()
                NamingConvention.SNAKECASE -> func.simpleName.asString().camelToSnakecase()
                NamingConvention.PASCALCASE -> func.simpleName.asString().camelToPascalcase()
            }
            else -> methodAnnotation.arguments.first {
                it.name!!.asString() == "name"
            }.value!! as String
        }
    }

    private fun getDisposer(func: KSFunctionDeclaration) = when {
        func.annotations.has<Disposer>() -> func.annotations.get<Disposer>()
        func.annotations.has<NoDisposer>() -> null
        func.parentDeclaration?.annotations?.has<Disposer>() ?: false -> func.parentDeclaration!!.annotations.get<Disposer>()
        else -> null
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
                    val naming = lib.getAnnotationsByType(Library::class).first().naming
                    val funcNativeName = getNativeFunctionName(naming, func, resolver)

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

            if (type.isString || type.assignableTo<CString>(resolver)) {
                if (!it.annotations.has<StringParam>())
                    throw IllegalStateException("Parameter '$paramName: String' for function '$funcName' does not have a 'StringParam' annotation")
            }
            else if (
                !type.isPrimitive &&
                !type.inheritsNative(resolver) &&
                !type.assignableTo<BitFlagSet<*, *>>(resolver) &&
                !type.assignableTo<NativeEnum<*>>(resolver)
            ) {
                throw IllegalStateException("Parameter '$paramName: ${type.qualifiedName!!.asString()}' for function '$funcName' is not a supported type")
            }
        }

        val returnType = func.returnType!!.resolve()
        if (
            !returnType.isPrimitive &&
            !returnType.isString &&
            !returnType.inheritsNative(resolver) &&
            !returnType.assignableTo<BitFlagSet<*, *>>(resolver) &&
            !returnType.assignableTo<NativeEnum<*>>(resolver)
        ) {
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
