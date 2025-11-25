package knr.runtime.typing.flags

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

@JvmInline
value class ByteBitFlagSet<F>(override val mask: Byte = 0): BitFlagSet<Byte, F> where F : Enum<F>, F : BitFlag<Byte> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = ByteBitFlagSet<F>(0)
    override fun and(vararg other: F): ByteBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return ByteBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): ByteBitFlagSet<F> {
        var invMask: Byte = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ByteBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): ByteBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return ByteBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): ByteBitFlagSet<F> {
        var invMask: Byte = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ByteBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): ByteBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return ByteBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): ByteBitFlagSet<F> {
        var invMask: Byte = 0
        other.forEach {
            invMask = invMask or it.mask
        }
        return ByteBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): ByteBitFlagSet<F> where F : Enum<F>, F : BitFlag<Byte> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): ByteBitFlagSet<F> where F : Enum<F>, F : BitFlag<Byte> {
            var mask: Byte = 0
            flags.forEach { mask = mask or it.mask }
            return ByteBitFlagSet(mask)
        }
    }
}

@JvmInline
value class UByteBitFlagSet<F>(override val mask: UByte = 0u): BitFlagSet<UByte, F> where F : Enum<F>, F : BitFlag<UByte> {
    override fun has(vararg others: F) = others.all {
        (mask and it.mask) == it.mask
    }
    override fun lacks(vararg others: F) = others.all {
        (mask and it.mask) != it.mask
    }

    override fun off(vararg others: F) = nand(*others)
    override fun on(vararg others: F) = or(*others)
    override fun toggle(vararg others: F) = xor(*others)

    override fun clear() = UByteBitFlagSet<F>(0u)
    override fun and(vararg other: F): UByteBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask and it.mask
        }
        return UByteBitFlagSet(newMask)
    }
    override fun nand(vararg other: F): UByteBitFlagSet<F> {
        var invMask: UByte = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UByteBitFlagSet(mask and invMask.inv())
    }
    override fun or(vararg other: F): UByteBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask or it.mask
        }
        return UByteBitFlagSet(newMask)
    }
    override fun nor(vararg other: F): UByteBitFlagSet<F> {
        var invMask: UByte = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UByteBitFlagSet(mask or invMask.inv())
    }
    override fun xor(vararg other: F): UByteBitFlagSet<F> {
        var newMask = mask
        other.forEach {
            newMask = newMask xor it.mask
        }
        return UByteBitFlagSet(newMask)
    }
    override fun xnor(vararg other: F): UByteBitFlagSet<F> {
        var invMask: UByte = 0u
        other.forEach {
            invMask = invMask or it.mask
        }
        return UByteBitFlagSet(mask xor invMask.inv())
    }

    companion object {
        inline fun <reified F> allOf(): UByteBitFlagSet<F> where F : Enum<F>, F : BitFlag<UByte> {
            val cls = F::class.java
            return of(*cls.enumConstants)
        }

        fun <F> of(vararg flags: F): UByteBitFlagSet<F> where F : Enum<F>, F : BitFlag<UByte> {
            var mask: UByte = 0u
            flags.forEach { mask = mask or it.mask }
            return UByteBitFlagSet(mask)
        }
    }
}
