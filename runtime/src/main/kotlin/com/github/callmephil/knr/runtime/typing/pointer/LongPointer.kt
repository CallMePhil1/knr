package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC

class NullableLongPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<Long>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0)

    override fun set(value: Long) = arc!!.setLong(0, value)

    override fun shareOf(): NullableLongPointer {
        validOrThrow(this)
        return NullableLongPointer(arc!!, null)
    }
}

class LongPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<Long>(arc, onArcUpdated) {
    override fun get() = arc!!.getLong(0)

    override fun set(value: Long) = arc!!.setLong(0, value)

    override fun shareOf(): LongPointer {
        validOrThrow(this)
        return LongPointer(arc!!, null)
    }
}

class NullableULongPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<ULong>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0).toULong()

    override fun set(value: ULong) = arc!!.setLong(0, value.toLong())

    override fun shareOf(): NullableULongPointer {
        validOrThrow(this)
        return NullableULongPointer(arc!!, null)
    }
}

class ULongPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<ULong>(arc, onArcUpdated) {
    override fun get() = arc!!.getLong(0).toULong()

    override fun set(value: ULong) = arc!!.setLong(0, value.toLong())

    override fun shareOf(): ULongPointer {
        validOrThrow(this)
        return ULongPointer(arc!!, null)
    }
}