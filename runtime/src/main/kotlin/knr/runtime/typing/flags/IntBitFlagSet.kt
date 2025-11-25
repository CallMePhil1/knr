package knr.runtime.typing.flags

@JvmInline
value class IntBitFlagSet<F>(override val mask: Int = 0): BitFlagSet<Int, F> where F : Enum<F>, F : BitFlag<Int> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = IntBitFlagSet<F>(0)
    override fun and(vararg other: F): IntBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return IntBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): IntBitFlagSet<F> {
        var invMask = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return IntBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): IntBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return IntBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): IntBitFlagSet<F> {
        var invMask = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return IntBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): IntBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return IntBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): IntBitFlagSet<F> {
        var invMask = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return IntBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): IntBitFlagSet<F> where F : Enum<F>, F : BitFlag<Int> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): IntBitFlagSet<F> where F : Enum<F>, F : BitFlag<Int> {
            var mask = 0
            flags.forEach { mask = mask or it.mask }
            return IntBitFlagSet(mask)
        }
    }
}

@JvmInline
value class UIntBitFlagSet<F>(override val mask: UInt = 0u): BitFlagSet<UInt, F> where F : Enum<F>, F : BitFlag<UInt> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = UIntBitFlagSet<F>(0u)
    override fun and(vararg other: F): UIntBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return UIntBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): UIntBitFlagSet<F> {
        var invMask = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UIntBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): UIntBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return UIntBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): UIntBitFlagSet<F> {
        var invMask = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UIntBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): UIntBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return UIntBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): UIntBitFlagSet<F> {
        var invMask = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UIntBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): UIntBitFlagSet<F> where F : Enum<F>, F : BitFlag<UInt> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): UIntBitFlagSet<F> where F : Enum<F>, F : BitFlag<UInt> {
            var mask = 0u
            flags.forEach { mask = mask or it.mask }
            return UIntBitFlagSet(mask)
        }
    }
}
