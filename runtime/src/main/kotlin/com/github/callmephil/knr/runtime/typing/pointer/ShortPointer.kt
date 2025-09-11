package com.github.callmephil.knr.runtime.typing.pointer

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class NullableShortPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Short?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_SHORT, 0)
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Short) {
        memorySegment.set(ValueLayout.JAVA_SHORT, 0, value)
    }
}

class ShortPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Short>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable ShortPointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_SHORT, 0)

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable ShortPointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Short) = memorySegment.set(ValueLayout.JAVA_SHORT, 0, value)
}

class NullableUShortPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<UShort?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_SHORT, 0).toUShort()
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: UShort) {
        memorySegment.set(ValueLayout.JAVA_SHORT, 0, value.toShort())
    }
}

class UShortPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<UShort>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable UShortPointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_SHORT, 0).toUShort()

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable UShortPointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: UShort) = memorySegment.set(ValueLayout.JAVA_SHORT, 0, value.toShort())
}