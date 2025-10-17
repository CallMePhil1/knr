package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class LongPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : Pointer<Long>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0)

    override fun set(value: Long) = arc!!.setLong(0, value)

    override fun shareOf(): LongPointer {
        validOrThrow(this)
        return LongPointer(arc!!, null)
    }
}

class ULongPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : Pointer<ULong>(arc, onArcUpdated) {

    override fun get() = arc!!.getLong(0).toULong()

    override fun set(value: ULong) = arc!!.setLong(0, value.toLong())

    override fun shareOf(): ULongPointer {
        validOrThrow(this)
        return ULongPointer(arc!!, null)
    }
}

// region Long Pointer

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
internal fun longPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): LongPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    val arc = ARC.ofSegment(segment)
    return LongPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}

// endregion

// region ULong Pointer

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
internal fun ulongPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): ULongPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    val arc = ARC.ofSegment(segment)
    return ULongPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}

// endregion