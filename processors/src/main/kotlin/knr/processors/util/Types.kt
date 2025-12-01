package knr.processors.util

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import knr.annotations.ByRef
import knr.processors.ext.*
import knr.runtime.typing.NativeEnum
import knr.runtime.typing.flags.BitFlagSet

internal val primitiveTypes = setOf(
    "kotlin.Unit",
    "kotlin.Boolean",
    "kotlin.Byte",
    "kotlin.UByte",
    "kotlin.Short",
    "kotlin.UShort",
    "kotlin.Int",
    "kotlin.UInt",
    "kotlin.Long",
    "kotlin.ULong",
    "kotlin.Float",
    "kotlin.Double"
)

internal val valueLayoutMap = mapOf(
    "kotlin.Unit" to "null",
    "kotlin.Boolean" to "ValueLayout.JAVA_BOOLEAN",
    "kotlin.Byte" to "ValueLayout.JAVA_BYTE",
    "kotlin.UByte" to "ValueLayout.JAVA_BYTE",
    "kotlin.Short" to "ValueLayout.JAVA_SHORT",
    "kotlin.UShort" to "ValueLayout.JAVA_SHORT",
    "kotlin.Int" to "ValueLayout.JAVA_INT",
    "kotlin.UInt" to "ValueLayout.JAVA_INT",
    "kotlin.Long" to "ValueLayout.JAVA_LONG",
    "kotlin.ULong" to "ValueLayout.JAVA_LONG",
    "kotlin.Float" to "ValueLayout.JAVA_FLOAT",
    "kotlin.Double" to "ValueLayout.JAVA_DOUBLE"
)

internal fun toMemoryLayout(type: KSType, annotations: Sequence<KSAnnotation>, resolver: Resolver): String {
    return when {
        type.isPrimitive -> valueLayoutMap[type.declaration.qualifiedName!!.asString()]!!
        type.assignableTo<BitFlagSet<*, *>>(resolver) -> {
            val property = (type.declaration as KSClassDeclaration).getAllProperties().first { it.simpleName.asString() == "mask" }
            valueLayoutMap[property.type.resolve().qualifiedName!!.asString()]!!
        }
        type.assignableTo<NativeEnum<*>>(resolver) -> {
            val valueType = type.getNativeEnumValueType(resolver)
            valueLayoutMap[valueType.qualifiedName!!.asString()]!!
        }
        type.isStruct(resolver) -> if (annotations.has<ByRef>()) "ValueLayout.ADDRESS" else "${type.qualifiedName!!.asString()}.definition.layout"
        type.isString ||
                type.inheritsNative(resolver) -> "ValueLayout.ADDRESS"
        else -> error("Couldn't convert type '${type.qualifiedName!!.asString()}' to ValueLayout")
    }
}