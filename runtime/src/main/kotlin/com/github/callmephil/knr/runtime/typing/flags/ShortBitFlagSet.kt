package com.github.callmephil.knr.runtime.typing.flags

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

class ShortBitFlagSet<T: BitFlag<Short>>(override val mask: Short = 0): BitFlagSet<Short, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = ShortBitFlagSet<T>(0)
    override fun and(vararg other: T): ShortBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return ShortBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): ShortBitFlagSet<T> {
        var invMask: Short = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ShortBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): ShortBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return ShortBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): ShortBitFlagSet<T> {
        var invMask: Short = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ShortBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): ShortBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return ShortBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): ShortBitFlagSet<T> {
        var invMask: Short = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ShortBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<Short>> allOf(): ShortBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'ShortBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<Short>> of(vararg flags: T): ShortBitFlagSet<T> {
            var mask: Short = 0
            flags.forEach { mask = mask or it.mask }
            return ShortBitFlagSet(mask)
        }
    }
}

class UShortBitFlagSet<T: BitFlag<UShort>>(override val mask: UShort = 0u): BitFlagSet<UShort, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = UShortBitFlagSet<T>(0u)
    override fun and(vararg other: T): UShortBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return UShortBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): UShortBitFlagSet<T> {
        var invMask: UShort = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UShortBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): UShortBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return UShortBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): UShortBitFlagSet<T> {
        var invMask: UShort = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UShortBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): UShortBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return UShortBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): UShortBitFlagSet<T> {
        var invMask: UShort = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UShortBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<UShort>> allOf(): UShortBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'ShortBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<UShort>> of(vararg flags: T): UShortBitFlagSet<T> {
            var mask: UShort = 0u
            flags.forEach { mask = mask or it.mask }
            return UShortBitFlagSet(mask)
        }
    }
}
