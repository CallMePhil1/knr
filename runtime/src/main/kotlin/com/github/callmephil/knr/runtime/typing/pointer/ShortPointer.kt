package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class NullableShortPointer internal constructor(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : NullablePointer<Short>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0)

    override fun set(value: Short) {
        arc!!.setShort(0, value)
    }

    override fun shareOf(): NullableShortPointer {
        validOrThrow(this)
        return NullableShortPointer(arc!!, null)
    }
}

class ShortPointer internal constructor(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : Pointer<Short>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0)

    override fun set(value: Short) = arc!!.setShort(0, value)

    override fun shareOf(): ShortPointer {
        validOrThrow(this)
        return ShortPointer(arc!!, null)
    }
}

class NullableUShortPointer internal constructor(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : NullablePointer<UShort>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0).toUShort()

    override fun set(value: UShort) {
        arc!!.setShort(0, value.toShort())
    }

    override fun shareOf(): NullableUShortPointer {
        validOrThrow(this)
        return NullableUShortPointer(arc!!, null)
    }
}

class UShortPointer internal constructor(
    arc: ARC,
    onArcUpdated: (()-> Unit)? = null
) : Pointer<UShort>(arc, onArcUpdated) {

    override fun get() = arc!!.getShort(0).toUShort()

    override fun set(value: UShort) = arc!!.setShort(0, value.toShort())

    override fun shareOf(): UShortPointer {
        validOrThrow(this)
        return UShortPointer(arc!!, null)
    }
}

// region Short Pointer

fun nullableShortPointerOf(arc: ARC = ARC.ofNull()) = NullableShortPointer(arc)
fun nullableShortPointerOf(value: Short, arc: ARC = ARC.shared(ValueLayout.JAVA_SHORT)): NullableShortPointer {
    val pointer = NullableShortPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun nullableShortPointerOf(
    value: Short?,
    onArcUpdate: () -> Unit
): NullableShortPointer {
    return if (value == null) {
        NullableShortPointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableShortPointer(ARC.shared(ValueLayout.JAVA_SHORT), onArcUpdate).apply {
            set(value)
        }
    }
}
internal fun nullableShortPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): NullableShortPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    val arc = ARC.ofSegment(segment)
    return NullableShortPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeNullableShortPointer(
    pointer: NullableShortPointer,
    onArcUpdated: () -> Unit
): NullableShortPointer {
    val newPointer = NullableShortPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

fun shortPointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_SHORT)) = ShortPointer(arc)
fun shortPointerOf(arc: ARC, offset: Long) = ShortPointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_SHORT.byteSize())
    )
)
fun shortPointerOf(value: Short, arc: ARC = ARC.shared(ValueLayout.JAVA_SHORT)): ShortPointer {
    val pointer = ShortPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun shortPointerOf(
    value: Short,
    onArcUpdate: () -> Unit
) = ShortPointer(ARC.shared(ValueLayout.JAVA_SHORT), onArcUpdate).apply {
    set(value)
}
internal fun shortPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): ShortPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    val arc = ARC.ofSegment(segment)
    return ShortPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeShortPointer(
    pointer: ShortPointer,
    onArcUpdated: () -> Unit
): ShortPointer {
    val newPointer = ShortPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

// endregion

// region UShort Pointer

fun nullableUShortPointerOf(arc: ARC = ARC.ofNull()) = NullableUShortPointer(arc)
fun nullableUShortPointerOf(value: UShort, arc: ARC = ARC.shared(ValueLayout.JAVA_SHORT)): NullableUShortPointer {
    val pointer = NullableUShortPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun nullableUShortPointerOf(
    value: UShort?,
    onArcUpdate: () -> Unit
): NullableUShortPointer {
    return if (value == null) {
        NullableUShortPointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableUShortPointer(ARC.shared(ValueLayout.JAVA_SHORT), onArcUpdate).apply {
            set(value)
        }
    }
}
internal fun nullableUShortPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): NullableUShortPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    val arc = ARC.ofSegment(segment)
    return NullableUShortPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeNullableUShortPointer(
    pointer: NullableUShortPointer,
    onArcUpdated: () -> Unit
): NullableUShortPointer {
    val newPointer = NullableUShortPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

fun ushortPointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_SHORT)) = UShortPointer(arc)
fun ushortPointerOf(arc: ARC, offset: Long) = UShortPointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_SHORT.byteSize())
    )
)
fun ushortPointerOf(value: UShort, arc: ARC = ARC.shared(ValueLayout.JAVA_SHORT)): UShortPointer {
    val pointer = UShortPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun ushortPointerOf(
    value: UShort,
    onArcUpdate: () -> Unit
) = UShortPointer(ARC.shared(ValueLayout.JAVA_SHORT), onArcUpdate).apply {
    set(value)
}
internal fun ushortPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): UShortPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_LONG.byteSize())
    val arc = ARC.ofSegment(segment)
    return UShortPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeUShortPointer(
    pointer: UShortPointer,
    onArcUpdated: () -> Unit
): UShortPointer {
    val newPointer = UShortPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

// endregion