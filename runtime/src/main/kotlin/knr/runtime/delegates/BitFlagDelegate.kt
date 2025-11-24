package knr.runtime.delegates

import knr.runtime.typing.Native
import knr.runtime.typing.flags.*

class ByteBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<ByteBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<Byte> {
    override fun get(): ByteBitFlagSet<F> {
        val value = parent.memory.getByte(offset)
        return ByteBitFlagSet(value)
    }

    override fun set(value: ByteBitFlagSet<F>) {
        parent.memory.setByte(offset, value.mask)
    }
}

class UByteBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<UByteBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<UByte> {
    override fun get(): UByteBitFlagSet<F> {
        val value = parent.memory.getUByte(offset)
        return UByteBitFlagSet(value)
    }

    override fun set(value: UByteBitFlagSet<F>) {
        parent.memory.setUByte(offset, value.mask)
    }
}

class ShortBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<ShortBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<Short> {
    override fun get(): ShortBitFlagSet<F> {
        val value = parent.memory.getShort(offset)
        return ShortBitFlagSet(value)
    }

    override fun set(value: ShortBitFlagSet<F>) {
        parent.memory.setShort(offset, value.mask)
    }
}

class UShortBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<UShortBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<UShort> {
    override fun get(): UShortBitFlagSet<F> {
        val value = parent.memory.getUShort(offset)
        return UShortBitFlagSet(value)
    }

    override fun set(value: UShortBitFlagSet<F>) {
        parent.memory.setUShort(offset, value.mask)
    }
}

class IntBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<IntBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<Int> {
    override fun get(): IntBitFlagSet<F> {
        val value = parent.memory.getInt(offset)
        return IntBitFlagSet(value)
    }

    override fun set(value: IntBitFlagSet<F>) {
        parent.memory.setInt(offset, value.mask)
    }
}

class UIntBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<UIntBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<UInt> {
    override fun get(): UIntBitFlagSet<F> {
        val value = parent.memory.getUInt(offset)
        return UIntBitFlagSet(value)
    }

    override fun set(value: UIntBitFlagSet<F>) {
        parent.memory.setUInt(offset, value.mask)
    }
}

class LongBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<LongBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<Long> {
    override fun get(): LongBitFlagSet<F> {
        val value = parent.memory.getLong(offset)
        return LongBitFlagSet(value)
    }

    override fun set(value: LongBitFlagSet<F>) {
        parent.memory.setLong(offset, value.mask)
    }
}

class ULongBitFlagSetDelegate<F>(
    parent: Native<*>,
    offset: Long
): FieldDelegate<ULongBitFlagSet<F>>(parent, offset) where F : Enum<F>, F : BitFlag<ULong> {
    override fun get(): ULongBitFlagSet<F> {
        val value = parent.memory.getULong(offset)
        return ULongBitFlagSet(value)
    }

    override fun set(value: ULongBitFlagSet<F>) {
        parent.memory.setULong(offset, value.mask)
    }
}
