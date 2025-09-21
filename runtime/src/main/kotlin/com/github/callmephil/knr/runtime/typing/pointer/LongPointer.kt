package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class NullableLongPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<Long>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0)

    override fun set(value: Long) = arc!!.setLong(0, value)

    override fun shareOf(): NullableLongPointer {
        validOrThrow(this)
        return NullableLongPointer(arc!!, null)
    }
}

class LongPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<Long>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0)

    override fun set(value: Long) = arc!!.setLong(0, value)

    override fun shareOf(): LongPointer {
        validOrThrow(this)
        return LongPointer(arc!!, null)
    }
}

class NullableULongPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<ULong>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0).toULong()

    override fun set(value: ULong) = arc!!.setLong(0, value.toLong())

    override fun shareOf(): NullableULongPointer {
        validOrThrow(this)
        return NullableULongPointer(arc!!, null)
    }
}

class ULongPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<ULong>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0).toULong()

    override fun set(value: ULong) = arc!!.setLong(0, value.toLong())

    override fun shareOf(): ULongPointer {
        validOrThrow(this)
        return ULongPointer(arc!!, null)
    }
}

// region Long Pointer

fun nullableLongPointerOf(arc: ARC = ARC.ofNull()) = NullableLongPointer(arc)
fun nullableLongPointerOf(value: Long, arc: ARC = ARC.shared(ValueLayout.JAVA_LONG)): NullableLongPointer {
    val pointer = NullableLongPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun nullableLongPointerOf(
    value: Long?,
    onArcUpdate: () -> Unit
): NullableLongPointer {
    return if (value == null) {
        NullableLongPointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableLongPointer(ARC.shared(ValueLayout.JAVA_LONG), onArcUpdate).apply {
            set(value)
        }
    }
}

fun longPointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_LONG)) = LongPointer(arc)
fun longPointerOf(arc: ARC, offset: Long) = LongPointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    )
)
fun longPointerOf(value: Long, arc: ARC = ARC.shared(ValueLayout.JAVA_LONG)): LongPointer {
    val pointer = LongPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun longPointerOf(
    value: Long,
    onArcUpdate: () -> Unit
) = LongPointer(ARC.shared(ValueLayout.JAVA_LONG), onArcUpdate).apply {
    set(value)
}

// endregion

// region ULong Pointer

fun nullableULongPointerOf(arc: ARC = ARC.ofNull()) = NullableULongPointer(arc)
fun nullableULongPointerOf(value: ULong, arc: ARC = ARC.shared(ValueLayout.JAVA_LONG)): NullableULongPointer {
    val pointer = NullableULongPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun nullableULongPointerOf(
    value: ULong?,
    onArcUpdate: () -> Unit
): NullableULongPointer {
    return if (value == null) {
        NullableULongPointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableULongPointer(ARC.shared(ValueLayout.JAVA_LONG), onArcUpdate).apply {
            set(value)
        }
    }
}

fun ulongPointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_LONG)) = ULongPointer(arc)
fun ulongPointerOf(arc: ARC, offset: Long) = ULongPointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    )
)
fun ulongPointerOf(value: ULong, arc: ARC = ARC.shared(ValueLayout.JAVA_LONG)): ULongPointer {
    val pointer = ULongPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun ulongPointerOf(
    value: ULong,
    onArcUpdate: () -> Unit
) = ULongPointer(ARC.shared(ValueLayout.JAVA_LONG), onArcUpdate).apply {
    set(value)
}

// endregion