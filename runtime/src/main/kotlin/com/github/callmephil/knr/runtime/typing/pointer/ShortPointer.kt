package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class NullableShortPointer(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : NullablePrimitivePointer<Short>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0)

    override fun set(value: Short) {
        arc!!.setShort(0, value)
    }

    override fun shareOf(): NullableShortPointer {
        validOrThrow(this)
        return NullableShortPointer(arc!!, null)
    }
}

class ShortPointer(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : PrimitivePointer<Short>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0)

    override fun set(value: Short) = arc!!.setShort(0, value)

    override fun shareOf(): ShortPointer {
        validOrThrow(this)
        return ShortPointer(arc!!, null)
    }
}

class NullableUShortPointer(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : NullablePrimitivePointer<UShort>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0).toUShort()

    override fun set(value: UShort) {
        arc!!.setShort(0, value.toShort())
    }

    override fun shareOf(): NullableUShortPointer {
        validOrThrow(this)
        return NullableUShortPointer(arc!!, null)
    }
}

class UShortPointer(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : PrimitivePointer<UShort>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0).toUShort()

    override fun set(value: UShort) = arc!!.setShort(0, value.toShort())

    override fun shareOf(): UShortPointer {
        validOrThrow(this)
        return UShortPointer(arc!!, null)
    }
}