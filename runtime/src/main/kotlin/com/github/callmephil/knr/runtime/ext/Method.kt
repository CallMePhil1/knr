package com.github.callmephil.knr.runtime.ext

import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodType

private val typeMapping = mapOf<ValueLayout, Class<*>>(
    ValueLayout.JAVA_BOOLEAN to Boolean::class.javaPrimitiveType!!,
    ValueLayout.JAVA_BYTE to Byte::class.javaPrimitiveType!!,
    ValueLayout.JAVA_SHORT to Short::class.javaPrimitiveType!!,
    ValueLayout.JAVA_INT to Int::class.javaPrimitiveType!!,
    ValueLayout.JAVA_LONG to Long::class.javaPrimitiveType!!,
    ValueLayout.JAVA_FLOAT to Float::class.javaPrimitiveType!!,
    ValueLayout.JAVA_DOUBLE to Double::class.javaPrimitiveType!!,
    ValueLayout.ADDRESS to MemorySegment::class.java
)

fun Linker.downcallHandle(
    segment: MemorySegment,
    retType: ValueLayout? = null,
    vararg params: ValueLayout
): MethodHandle {
    val funcDescriptor = if (retType == null) {
        FunctionDescriptor.ofVoid(*params)
    } else {
        FunctionDescriptor.of(retType, *params)
    }
    return this.downcallHandle(segment, funcDescriptor)
        .asType(
        MethodType.methodType(
            if (retType == null) Void::class.javaPrimitiveType else typeMapping[retType],
            params.map { typeMapping[it] }
        )
    )
}