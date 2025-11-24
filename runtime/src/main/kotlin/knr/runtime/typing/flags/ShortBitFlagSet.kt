package knr.runtime.typing.flags

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

@JvmInline
value class ShortBitFlagSet<F>(override val mask: Short = 0): BitFlagSet<Short, F> where F : Enum<F>, F : BitFlag<Short> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = ShortBitFlagSet<F>(0)
    override fun and(vararg other: F): ShortBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return ShortBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): ShortBitFlagSet<F> {
        var invMask: Short = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ShortBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): ShortBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return ShortBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): ShortBitFlagSet<F> {
        var invMask: Short = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ShortBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): ShortBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return ShortBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): ShortBitFlagSet<F> {
        var invMask: Short = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ShortBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): ShortBitFlagSet<F> where F : Enum<F>, F : BitFlag<Short> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): ShortBitFlagSet<F> where F : Enum<F>, F : BitFlag<Short> {
            var mask: Short = 0
            flags.forEach { mask = mask or it.mask }
            return ShortBitFlagSet(mask)
        }
    }
}

@JvmInline
value class UShortBitFlagSet<F>(override val mask: UShort = 0u): BitFlagSet<UShort, F> where F : Enum<F>, F : BitFlag<UShort> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = UShortBitFlagSet<F>(0u)
    override fun and(vararg other: F): UShortBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return UShortBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): UShortBitFlagSet<F> {
        var invMask: UShort = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UShortBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): UShortBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return UShortBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): UShortBitFlagSet<F> {
        var invMask: UShort = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UShortBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): UShortBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return UShortBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): UShortBitFlagSet<F> {
        var invMask: UShort = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UShortBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): UShortBitFlagSet<F> where F : Enum<F>, F : BitFlag<UShort> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): UShortBitFlagSet<F> where F : Enum<F>, F : BitFlag<UShort> {
            var mask: UShort = 0u
            flags.forEach { mask = mask or it.mask }
            return UShortBitFlagSet(mask)
        }
    }
}
