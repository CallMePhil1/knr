package com.github.callmephil.knr.runtime.typing.pointer

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class NullableIntPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Int?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_INT, 0)
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Int) {
        memorySegment.set(ValueLayout.JAVA_INT, 0, value)
    }
}

class IntPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Int>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable IntPointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_INT, 0)

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable IntPointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Int) = memorySegment.set(ValueLayout.JAVA_INT, 0, value)
}

class NullableUIntPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<UInt?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_INT, 0).toUInt()
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: UInt) {
        memorySegment.set(ValueLayout.JAVA_INT, 0, value.toInt())
    }
}

class UIntPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<UInt>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable UIntPointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_INT, 0).toUInt()

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable UIntPointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: UInt) = memorySegment.set(ValueLayout.JAVA_INT, 0, value.toInt())
}