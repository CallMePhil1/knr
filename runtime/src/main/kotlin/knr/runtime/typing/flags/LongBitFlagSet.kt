package knr.runtime.typing.flags

@JvmInline
value class LongBitFlagSet<T: BitFlag<Long>>(override val mask: Long = 0): BitFlagSet<Long, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = LongBitFlagSet<T>(0)
    override fun and(vararg other: T): LongBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return LongBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): LongBitFlagSet<T> {
        var invMask: Long = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return LongBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): LongBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return LongBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): LongBitFlagSet<T> {
        var invMask: Long = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return LongBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): LongBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return LongBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): LongBitFlagSet<T> {
        var invMask: Long = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return LongBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<Long>> allOf(): LongBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'LongBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<Long>> of(vararg flags: T): LongBitFlagSet<T> {
            var mask: Long = 0
            flags.forEach { mask = mask or it.mask }
            return LongBitFlagSet(mask)
        }
    }
}

@JvmInline
value class ULongBitFlagSet<T: BitFlag<ULong>>(override val mask: ULong = 0u): BitFlagSet<ULong, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = ULongBitFlagSet<T>(0u)
    override fun and(vararg other: T): ULongBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return ULongBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): ULongBitFlagSet<T> {
        var invMask: ULong = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return ULongBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): ULongBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return ULongBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): ULongBitFlagSet<T> {
        var invMask: ULong = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return ULongBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): ULongBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return ULongBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): ULongBitFlagSet<T> {
        var invMask: ULong = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return ULongBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<ULong>> allOf(): ULongBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'LongBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<ULong>> of(vararg flags: T): ULongBitFlagSet<T> {
            var mask: ULong = 0u
            flags.forEach { mask = mask or it.mask }
            return ULongBitFlagSet(mask)
        }
    }
}
