package com.github.callmephil.knr.runtime.typing.flags

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

@JvmInline
value class ByteBitFlagSet<T: BitFlag<Byte>>(override val mask: Byte = 0): BitFlagSet<Byte, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = ByteBitFlagSet<T>(0)
    override fun and(vararg other: T): ByteBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return ByteBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): ByteBitFlagSet<T> {
        var invMask: Byte = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ByteBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): ByteBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return ByteBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): ByteBitFlagSet<T> {
        var invMask: Byte = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ByteBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): ByteBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return ByteBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): ByteBitFlagSet<T> {
        var invMask: Byte = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ByteBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<Byte>> allOf(): ByteBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'ByteBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<Byte>> of(vararg flags: T): ByteBitFlagSet<T> {
            var mask: Byte = 0
            flags.forEach { mask = mask or it.mask }
            return ByteBitFlagSet(mask)
        }
    }
}

@JvmInline
value class UByteBitFlagSet<T: BitFlag<UByte>>(override val mask: UByte = 0u): BitFlagSet<UByte, T> {
    override fun has(vararg others: T) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: T) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: T) = nand(*others)
    override fun on(vararg others: T) = or(*others)
    override fun toggle(vararg others: T) = xor(*others)

    override fun clear() = UByteBitFlagSet<T>(0u)
    override fun and(vararg other: T): UByteBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return UByteBitFlagSet(newMask)
    }
    override fun nand(vararg other: T): UByteBitFlagSet<T> {
        var invMask: UByte = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UByteBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: T): UByteBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return UByteBitFlagSet(newMask)
    }
    override fun nor(vararg other: T): UByteBitFlagSet<T> {
        var invMask: UByte = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UByteBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: T): UByteBitFlagSet<T> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return UByteBitFlagSet(newMask)
    }
    override fun xnor(vararg other: T): UByteBitFlagSet<T> {
        var invMask: UByte = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UByteBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified T: BitFlag<UByte>> allOf(): UByteBitFlagSet<T> {
            val cls = T::class.java
            return when {
                cls.isEnum -> of(*cls.enumConstants)
                else -> throw NotImplementedError("Instantiating 'ByteBitFlagSet' via 'allOf' only works with Enums.")
            }
        }

        fun <T: BitFlag<UByte>> of(vararg flags: T): UByteBitFlagSet<T> {
            var mask: UByte = 0u
            flags.forEach { mask = mask or it.mask }
            return UByteBitFlagSet(mask)
        }
    }
}
