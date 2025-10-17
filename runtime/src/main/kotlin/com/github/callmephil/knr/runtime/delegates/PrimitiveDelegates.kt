package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.typing.Struct

class BooleanDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Boolean>(parent, offset) {
    override fun get(): Boolean = parent.arc.getBoolean(offset)
    override fun set(value: Boolean) {
        parent.arc.setBoolean(offset, value)
    }
}

class ByteDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Byte>(parent, offset) {
    override fun get(): Byte = parent.arc.getByte(offset)
    override fun set(value: Byte) = parent.arc.setByte(offset, value)
}

class UByteDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<UByte>(parent, offset) {
    override fun get(): UByte = parent.arc.getByte(offset).toUByte()
    override fun set(value: UByte) = parent.arc.setByte(offset, value.toByte())
}

class ShortDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Short>(parent, offset) {
    override fun get(): Short = parent.arc.getShort(offset)
    override fun set(value: Short) = parent.arc.setShort(offset, value)
}

class UShortDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<UShort>(parent, offset) {
    override fun get(): UShort = parent.arc.getShort(offset).toUShort()
    override fun set(value: UShort) = parent.arc.setShort(offset, value.toShort())
}

class IntDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Int>(parent, offset) {
    override fun get(): Int = parent.arc.getInt(offset)
    override fun set(value: Int) = parent.arc.setInt(offset, value)
}

class UIntDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<UInt>(parent, offset) {
    override fun get(): UInt = parent.arc.getInt(offset).toUInt()
    override fun set(value: UInt) = parent.arc.setInt(offset, value.toInt())
}

class LongDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Long>(parent, offset) {
    override fun get(): Long = parent.arc.getLong(offset)
    override fun set(value: Long) = parent.arc.setLong(offset, value)
}

class ULongDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<ULong>(parent, offset) {
    override fun get(): ULong = parent.arc.getLong(offset).toULong()
    override fun set(value: ULong) = parent.arc.setLong(offset, value.toLong())
}

class FloatDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Float>(parent, offset) {
    override fun get(): Float = parent.arc.getFloat(offset)
    override fun set(value: Float) = parent.arc.setFloat(offset, value)
}

class DoubleDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Double>(parent, offset) {
    override fun get(): Double = parent.arc.getDouble(offset)
    override fun set(value: Double) = parent.arc.setDouble(offset, value)
}
