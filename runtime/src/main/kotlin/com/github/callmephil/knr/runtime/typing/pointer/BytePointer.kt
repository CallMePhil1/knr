package com.github.callmephil.knr.runtime.typing.pointer

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class NullableBytePointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Byte?>(memorySegment) {

    override fun get(): Byte? = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_BYTE, 0)
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Byte) {
        memorySegment.set(ValueLayout.JAVA_BYTE, 0, value)
    }
}

class BytePointer(
    memorySegment: MemorySegment
) : PrimitivePointer<Byte>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable BytePointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_BYTE, 0)

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable BytePointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: Byte) = memorySegment.set(ValueLayout.JAVA_BYTE, 0, value)
}

class NullableUBytePointer(
    memorySegment: MemorySegment
) : PrimitivePointer<UByte?>(memorySegment) {

    override fun get() = if (memorySegment == MemorySegment.NULL) {
        null
    } else {
        memorySegment.get(ValueLayout.JAVA_BYTE, 0).toUByte()
    }

    override fun reference(ref: MemorySegment) {
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: UByte) {
        memorySegment.set(ValueLayout.JAVA_BYTE, 0, value.toByte())
    }
}

class UBytePointer(
    memorySegment: MemorySegment
) : PrimitivePointer<UByte>(memorySegment) {
    init {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable UBytePointer with NULL")
        }
    }

    override fun get() = memorySegment.get(ValueLayout.JAVA_BYTE, 0).toUByte()

    override fun reference(ref: MemorySegment) {
        if (memorySegment == MemorySegment.NULL) {
            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable UBytePointer.")
        }
        memorySegment = MemorySegment.ofAddress(ref.address())
    }

    override fun set(value: UByte) = memorySegment.set(ValueLayout.JAVA_BYTE, 0, value.toByte())
}
