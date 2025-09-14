package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class NullableIntPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<Int>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0)

    override fun set(value: Int) = arc!!.setInt(0, value)

    override fun shareOf(): NullableIntPointer {
        validOrThrow(this)
        return NullableIntPointer(arc!!, null)
    }
}

class IntPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<Int>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0)

    override fun set(value: Int) = arc!!.setInt(0, value)

    override fun shareOf(): IntPointer {
        validOrThrow(this)
        return IntPointer(arc!!, null)
    }
}

class NullableUIntPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<UInt>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0).toUInt()

    override fun set(value: UInt) = arc!!.setInt(0, value.toInt())

    override fun shareOf(): NullableUIntPointer {
        validOrThrow(this)
        return NullableUIntPointer(arc!!, null)
    }
}

class UIntPointer(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<UInt>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0).toUInt()

    override fun set(value: UInt) = arc!!.setInt(0, value.toInt())

    override fun shareOf(): UIntPointer {
        validOrThrow(this)
        return UIntPointer(arc!!, null)
    }
}

fun nullableIntPointerOf(arc: ARC = ARC.ofNull()) = NullableIntPointer(arc)
fun nullableIntPointerOf(value: Int, arc: ARC = ARC.shared(ValueLayout.JAVA_INT)): NullableIntPointer {
    val pointer = NullableIntPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun nullableIntPointerOf(
    value: Int?,
    onArcUpdate: () -> Unit
): NullableIntPointer {
    return if (value == null) {
        NullableIntPointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableIntPointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate).apply {
            set(value)
        }
    }
}

fun intPointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_INT)) = IntPointer(arc)
fun intPointerOf(arc: ARC, offset: Long) = IntPointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_INT.byteSize())
    )
)
fun intPointerOf(value: Int, arc: ARC = ARC.shared(ValueLayout.JAVA_INT)): IntPointer {
    val pointer = IntPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun intPointerOf(value: Int, onArcUpdate: () -> Unit): IntPointer {
    val pointer = IntPointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate)
    pointer.set(value)
    return pointer
}