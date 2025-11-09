package knr.processors.util

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