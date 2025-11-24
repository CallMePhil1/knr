package knr.runtime.typing.flags

@JvmInline
value class LongBitFlagSet<F>(override val mask: Long = 0): BitFlagSet<Long, F> where F : Enum<F>, F : BitFlag<Long> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = LongBitFlagSet<F>(0)
    override fun and(vararg other: F): LongBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return LongBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): LongBitFlagSet<F> {
        var invMask = 0L
        other.forEach {
            invMask = invMask or it.mask
        }
        return LongBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): LongBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return LongBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): LongBitFlagSet<F> {
        var invMask = 0L
        other.forEach {
            invMask = invMask or it.mask
        }
        return LongBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): LongBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return LongBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): LongBitFlagSet<F> {
        var invMask = 0L
        other.forEach {
            invMask = invMask or it.mask
        }
        return LongBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): LongBitFlagSet<F> where F : Enum<F>, F : BitFlag<Long> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): LongBitFlagSet<F> where F : Enum<F>, F : BitFlag<Long> {
            var mask = 0L
            flags.forEach { mask = mask or it.mask }
            return LongBitFlagSet(mask)
        }
    }
}

@JvmInline
value class ULongBitFlagSet<F>(override val mask: ULong = 0u): BitFlagSet<ULong, F> where F : Enum<F>, F : BitFlag<ULong> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = ULongBitFlagSet<F>(0u)
    override fun and(vararg other: F): ULongBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return ULongBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): ULongBitFlagSet<F> {
        var invMask: ULong = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return ULongBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): ULongBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return ULongBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): ULongBitFlagSet<F> {
        var invMask: ULong = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return ULongBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): ULongBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return ULongBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): ULongBitFlagSet<F> {
        var invMask: ULong = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return ULongBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): ULongBitFlagSet<F> where F : Enum<F>, F : BitFlag<ULong> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): ULongBitFlagSet<F> where F : Enum<F>, F : BitFlag<ULong> {
            var mask: ULong = 0u
            flags.forEach { mask = mask or it.mask }
            return ULongBitFlagSet(mask)
        }
    }
}
