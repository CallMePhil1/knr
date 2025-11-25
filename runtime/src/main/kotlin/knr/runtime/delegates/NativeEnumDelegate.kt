package knr.runtime.delegates

import knr.runtime.typing.Native
import knr.runtime.typing.NativeEnum

abstract class NativeEnumDelegate<T, E> internal constructor(
    parent: Native<*>,
    offset: Long,
    private val entriesMap: Map<T, E>
): FieldDelegate<E>(parent, offset) where E : Enum<E>, E : NativeEnum<T> {

    protected fun getEntry(value: T) = entriesMap[value]!!
}

class ByteNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<Byte, E>
): NativeEnumDelegate<Byte, E>(parent, offset, entries) where E : NativeEnum<Byte>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getByte(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setByte(offset, value.value)
    }
}

class UByteNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<UByte, E>
): NativeEnumDelegate<UByte, E>(parent, offset, entries) where E : NativeEnum<UByte>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getUByte(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setUByte(offset, value.value)
    }
}

class ShortNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<Short, E>
): NativeEnumDelegate<Short, E>(parent, offset, entries) where E : NativeEnum<Short>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getShort(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setShort(offset, value.value)
    }
}

class UShortNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<UShort, E>
): NativeEnumDelegate<UShort, E>(parent, offset, entries) where E : NativeEnum<UShort>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getUShort(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setUShort(offset, value.value)
    }
}

class IntNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<Int, E>
): NativeEnumDelegate<Int, E>(parent, offset, entries) where E : NativeEnum<Int>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getInt(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setInt(offset, value.value)
    }
}

class UIntNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<UInt, E>
): NativeEnumDelegate<UInt, E>(parent, offset, entries) where E : NativeEnum<UInt>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getUInt(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setUInt(offset, value.value)
    }
}

class LongNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<Long, E>
): NativeEnumDelegate<Long, E>(parent, offset, entries) where E : NativeEnum<Long>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getLong(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setLong(offset, value.value)
    }
}

class ULongNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<ULong, E>
): NativeEnumDelegate<ULong, E>(parent, offset, entries) where E : NativeEnum<ULong>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getULong(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setULong(offset, value.value)
    }
}

class FloatNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<Float, E>
): NativeEnumDelegate<Float, E>(parent, offset, entries) where E : NativeEnum<Float>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getFloat(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setFloat(offset, value.value)
    }
}

class DoubleNativeEnumDelegate<E>(
    parent: Native<*>,
    offset: Long,
    entries: Map<Double, E>
): NativeEnumDelegate<Double, E>(parent, offset, entries) where E : NativeEnum<Double>, E : Enum<E> {
    override fun get(): E {
        val value = parent.memory.getDouble(offset)
        return getEntry(value)
    }

    override fun set(value: E) {
        parent.memory.setDouble(offset, value.value)
    }
}
