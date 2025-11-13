package knr.runtime.delegates.union

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Union

class BooleanDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Boolean>(parent, offset) {
    override fun get(): Boolean = parent.memory.getBoolean(offset)
    override fun set(value: Boolean) {
        onSet()
        parent.memory.setBoolean(offset, value)
    }
}

class ByteDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Byte>(parent, offset) {
    override fun get(): Byte = parent.memory.getByte(offset)
    override fun set(value: Byte) {
        onSet()
        parent.memory.setByte(offset, value)
    }
}

class UByteDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<UByte>(parent, offset) {
    override fun get(): UByte = parent.memory.getByte(offset).toUByte()
    override fun set(value: UByte) {
        onSet()
        parent.memory.setByte(offset, value.toByte())
    }
}

class ShortDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Short>(parent, offset) {
    override fun get(): Short = parent.memory.getShort(offset)
    override fun set(value: Short) {
        onSet()
        parent.memory.setShort(offset, value)
    }
}

class UShortDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<UShort>(parent, offset) {
    override fun get(): UShort = parent.memory.getShort(offset).toUShort()
    override fun set(value: UShort) {
        onSet()
        parent.memory.setShort(offset, value.toShort())
    }
}

class IntDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Int>(parent, offset) {
    override fun get(): Int = parent.memory.getInt(offset)
    override fun set(value: Int) {
        onSet()
        parent.memory.setInt(offset, value)
    }
}

class UIntDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<UInt>(parent, offset) {
    override fun get(): UInt = parent.memory.getInt(offset).toUInt()
    override fun set(value: UInt) {
        onSet()
        parent.memory.setInt(offset, value.toInt())
    }
}

class LongDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Long>(parent, offset) {
    override fun get(): Long = parent.memory.getLong(offset)
    override fun set(value: Long) {
        onSet()
        parent.memory.setLong(offset, value)
    }
}

class ULongDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<ULong>(parent, offset) {
    override fun get(): ULong = parent.memory.getLong(offset).toULong()
    override fun set(value: ULong) {
        onSet()
        parent.memory.setLong(offset, value.toLong())
    }
}

class FloatDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Float>(parent, offset) {
    override fun get(): Float = parent.memory.getFloat(offset)
    override fun set(value: Float) {
        onSet()
        parent.memory.setFloat(offset, value) }
}

class DoubleDelegate internal constructor(
    parent: Union,
    offset: Long,
    private val onSet: () -> Unit
) : FieldDelegate<Double>(parent, offset) {
    override fun get(): Double = parent.memory.getDouble(offset)
    override fun set(value: Double) {
        onSet()
        parent.memory.setDouble(offset, value)
    }
}
