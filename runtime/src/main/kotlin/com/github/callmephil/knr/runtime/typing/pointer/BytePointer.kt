package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class NullableBytePointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<Byte>(arc, onArcUpdated) {

    override fun get(): Byte = arc!!.getByte(0)

    override fun set(value: Byte) {
        arc!!.setByte(0, value)
    }

    override fun shareOf(): NullableBytePointer {
        validOrThrow(this)
        return NullableBytePointer(arc!!, null)
    }
}

class BytePointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<Byte>(arc, onArcUpdated) {
    init {
        if (arc.isNull) {
            throw NullPointerException("Tried to construct a non-nullable BytePointer with null arc")
        }
        arc.incrementCount()
    }

    override fun get() = arc!!.getByte(0)

    override fun set(value: Byte) = arc!!.setByte(0, value)

    override fun shareOf(): BytePointer {
        validOrThrow(this)
        return BytePointer(arc!!, null)
    }
}

class NullableUBytePointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<UByte>(arc, onArcUpdated) {

    override fun get() = arc!!.getByte(0).toUByte()

    override fun set(value: UByte) {
        arc!!.setByte(0, value.toByte())
    }

    override fun shareOf(): NullableUBytePointer {
        validOrThrow(this)
        return NullableUBytePointer(arc!!, null)
    }
}

class UBytePointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<UByte>(arc, onArcUpdated) {
    init {
        if (arc == MemorySegment.NULL) {
            throw NullPointerException("Tried to construct a non-nullable UBytePointer with NULL")
        }
    }

    override fun get() = arc!!.getByte(0).toUByte()

    override fun set(value: UByte) = arc!!.setByte(0, value.toByte())

    override fun shareOf(): UBytePointer {
        validOrThrow(this)
        return UBytePointer(arc!!, null)
    }
}
