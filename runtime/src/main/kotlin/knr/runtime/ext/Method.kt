package knr.runtime.ext

import java.lang.foreign.*
import java.lang.invoke.MethodHandle

private val typeMapping = mapOf<MemoryLayout, Class<*>>(
    ValueLayout.JAVA_BOOLEAN to Boolean::class.javaPrimitiveType!!,
    ValueLayout.JAVA_BYTE to Byte::class.javaPrimitiveType!!,
    ValueLayout.JAVA_SHORT to Short::class.javaPrimitiveType!!,
    ValueLayout.JAVA_INT to Int::class.javaPrimitiveType!!,
    ValueLayout.JAVA_LONG to Long::class.javaPrimitiveType!!,
    ValueLayout.JAVA_FLOAT to Float::class.javaPrimitiveType!!,
    ValueLayout.JAVA_DOUBLE to Double::class.javaPrimitiveType!!,
    ValueLayout.ADDRESS to MemorySegment::class.java
)

private val newTypeMapping = mapOf(
    ValueLayout.OfBoolean::class.java to Boolean::class.javaPrimitiveType!!,
    ValueLayout.OfByte::class.java to Byte::class.javaPrimitiveType!!,
    ValueLayout.OfShort::class.java to Short::class.javaPrimitiveType!!,
    ValueLayout.OfInt::class.java to Int::class.javaPrimitiveType!!,
    ValueLayout.OfLong::class.java to Long::class.javaPrimitiveType!!,
    ValueLayout.OfFloat::class.java to Float::class.javaPrimitiveType!!,
    ValueLayout.OfDouble::class.java to Double::class.javaPrimitiveType!!,
    AddressLayout::class.java to MemorySegment::class.java,
    StructLayout::class.java to MemorySegment::class.java
)

fun Linker.downcallHandle(
    segment: MemorySegment,
    retType: MemoryLayout? = null,
    vararg params: MemoryLayout
): MethodHandle {
    val funcDescriptor = if (retType == null) {
        FunctionDescriptor.ofVoid(*params)
    } else {
        FunctionDescriptor.of(retType, *params)
    }

    return this.downcallHandle(segment, funcDescriptor)
}