package com.github.callmephil.knr.runtime.typing.pointer

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class NullableLongPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Long?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_LONG, 0)
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Long) {
            memorySegment.set(ValueLayout.JAVA_LONG, 0, value)
    }
}

class LongPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Long>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable LongPointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_LONG, 0)

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable LongPointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Long) = memorySegment.set(ValueLayout.JAVA_LONG, 0, value)
}

class NullableULongPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<ULong?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_LONG, 0).toULong()
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: ULong) {
        memorySegment.set(ValueLayout.JAVA_LONG, 0, value.toLong())
    }
}

class ULongPointer(
    memorySegment: MemorySegment
) : PrimitivePointer<ULong>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable ULongPointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_LONG, 0).toULong()

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable ULongPointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: ULong) = memorySegment.set(ValueLayout.JAVA_LONG, 0, value.toLong())
}