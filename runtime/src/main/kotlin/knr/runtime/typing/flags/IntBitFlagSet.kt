package knr.runtime.typing.flags

@JvmInline
value class IntBitFlagSet<T: BitFlag<Int>>(override val mask: Int = 0): BitFlagSet<Int, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = IntBitFlagSet<T>(0)
    override fun and(vararg other: T): IntBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return IntBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): IntBitFlagSet<T> {
        var invMask = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return IntBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): IntBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return IntBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): IntBitFlagSet<T> {
        var invMask = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return IntBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): IntBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return IntBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): IntBitFlagSet<T> {
        var invMask = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return IntBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<Int>> allOf(): IntBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'IntBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<Int>> of(vararg flags: T): IntBitFlagSet<T> {
            var mask = 0
            flags.forEach { mask = mask or it.mask }
            return IntBitFlagSet(mask)
        }
    }
}

@JvmInline
value class UIntBitFlagSet<T: BitFlag<UInt>>(override val mask: UInt = 0u): BitFlagSet<UInt, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = UIntBitFlagSet<T>(0u)
    override fun and(vararg other: T): UIntBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return UIntBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): UIntBitFlagSet<T> {
        var invMask = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UIntBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): UIntBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return UIntBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): UIntBitFlagSet<T> {
        var invMask = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UIntBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): UIntBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return UIntBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): UIntBitFlagSet<T> {
        var invMask = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UIntBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<UInt>> allOf(): UIntBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'IntBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<UInt>> of(vararg flags: T): UIntBitFlagSet<T> {
            var mask = 0u
            flags.forEach { mask = mask or it.mask }
            return UIntBitFlagSet(mask)
        }
    }
}
