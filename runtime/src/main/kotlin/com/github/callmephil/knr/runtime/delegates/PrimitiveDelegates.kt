package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.typing.Struct

class BooleanDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Boolean>(parent, offset) {
    override fun get(): Boolean = parent.memory.getBoolean(offset)
    override fun set(value: Boolean) {
        parent.memory.setBoolean(offset, value)
    }
}

class ByteDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Byte>(parent, offset) {
    override fun get(): Byte = parent.memory.getByte(offset)
    override fun set(value: Byte) = parent.memory.setByte(offset, value)
}

class UByteDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<UByte>(parent, offset) {
    override fun get(): UByte = parent.memory.getByte(offset).toUByte()
    override fun set(value: UByte) = parent.memory.setByte(offset, value.toByte())
}

class ShortDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Short>(parent, offset) {
    override fun get(): Short = parent.memory.getShort(offset)
    override fun set(value: Short) = parent.memory.setShort(offset, value)
}

class UShortDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<UShort>(parent, offset) {
    override fun get(): UShort = parent.memory.getShort(offset).toUShort()
    override fun set(value: UShort) = parent.memory.setShort(offset, value.toShort())
}

class IntDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Int>(parent, offset) {
    override fun get(): Int = parent.memory.getInt(offset)
    override fun set(value: Int) = parent.memory.setInt(offset, value)
}

class UIntDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<UInt>(parent, offset) {
    override fun get(): UInt = parent.memory.getInt(offset).toUInt()
    override fun set(value: UInt) = parent.memory.setInt(offset, value.toInt())
}

class LongDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Long>(parent, offset) {
    override fun get(): Long = parent.memory.getLong(offset)
    override fun set(value: Long) = parent.memory.setLong(offset, value)
}

class ULongDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<ULong>(parent, offset) {
    override fun get(): ULong = parent.memory.getLong(offset).toULong()
    override fun set(value: ULong) = parent.memory.setLong(offset, value.toLong())
}

class FloatDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Float>(parent, offset) {
    override fun get(): Float = parent.memory.getFloat(offset)
    override fun set(value: Float) = parent.memory.setFloat(offset, value)
}

class DoubleDelegate internal constructor(
    parent: Struct,
    offset: Long
) : FieldDelegate<Double>(parent, offset) {
    override fun get(): Double = parent.memory.getDouble(offset)
    override fun set(value: Double) = parent.memory.setDouble(offset, value)
}
