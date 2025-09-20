package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC

class BooleanDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Boolean>(arc, offset) {
    override fun get(): Boolean = ownerArc.getBoolean(offset)
    override fun set(value: Boolean) {
        ownerArc.setBoolean(offset, value)
    }
}

class ByteDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Byte>(arc, offset) {
    override fun get(): Byte = ownerArc.getByte(offset)
    override fun set(value: Byte) = ownerArc.setByte(offset, value)
}

class UByteDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<UByte>(arc, offset) {
    override fun get(): UByte = ownerArc.getByte(offset).toUByte()
    override fun set(value: UByte) = ownerArc.setByte(offset, value.toByte())
}

class ShortDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Short>(arc, offset) {
    override fun get(): Short = ownerArc.getShort(offset)
    override fun set(value: Short) = ownerArc.setShort(offset, value)
}

class UShortDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<UShort>(arc, offset) {
    override fun get(): UShort = ownerArc.getShort(offset).toUShort()
    override fun set(value: UShort) = ownerArc.setShort(offset, value.toShort())
}

class IntDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Int>(arc, offset) {
    override fun get(): Int = ownerArc.getInt(offset)
    override fun set(value: Int) = ownerArc.setInt(offset, value)
}

class UIntDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<UInt>(arc, offset) {
    override fun get(): UInt = ownerArc.getInt(offset).toUInt()
    override fun set(value: UInt) = ownerArc.setInt(offset, value.toInt())
}

class LongDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Long>(arc, offset) {
    override fun get(): Long = ownerArc.getLong(offset)
    override fun set(value: Long) = ownerArc.setLong(offset, value)
}

class ULongDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<ULong>(arc, offset) {
    override fun get(): ULong = ownerArc.getLong(offset).toULong()
    override fun set(value: ULong) = ownerArc.setLong(offset, value.toLong())
}

class FloatDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Float>(arc, offset) {
    override fun get(): Float = ownerArc.getFloat(offset)
    override fun set(value: Float) = ownerArc.setFloat(offset, value)
}

class DoubleDelegate(
    arc: ARC,
    offset: Long
) : FieldDelegate<Double>(arc, offset) {
    override fun get(): Double = ownerArc.getDouble(offset)
    override fun set(value: Double) = ownerArc.setDouble(offset, value)
}
