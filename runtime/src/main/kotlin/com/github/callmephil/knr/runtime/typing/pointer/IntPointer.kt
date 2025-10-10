package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class NullableIntPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePointer<Int>(arc, onArcUpdated) {

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
) : Pointer<Int>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0)

    override fun set(value: Int) = arc!!.setInt(0, value)

    override fun shareOf(): IntPointer {
        validOrThrow(this)
        return IntPointer(arc!!, null)
    }
}

class NullableUIntPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePointer<UInt>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0).toUInt()

    override fun set(value: UInt) = arc!!.setInt(0, value.toInt())

    override fun shareOf(): NullableUIntPointer {
        validOrThrow(this)
        return NullableUIntPointer(arc!!, null)
    }
}

class UIntPointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : Pointer<UInt>(arc, onArcUpdated) {

    override fun get() = arc!!.getInt(0).toUInt()

    override fun set(value: UInt) = arc!!.setInt(0, value.toInt())

    override fun shareOf(): UIntPointer {
        validOrThrow(this)
        return UIntPointer(arc!!, null)
    }
}

// region Int Pointer

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
internal fun nullableIntPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): NullableIntPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_INT.byteSize())
    val arc = ARC.ofSegment(segment)
    return NullableIntPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeNullableIntPointer(
    pointer: NullableIntPointer,
    onArcUpdated: () -> Unit
): NullableIntPointer {
    val newPointer = NullableIntPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
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
internal fun intPointerOf(
    value: Int,
    onArcUpdate: () -> Unit
) = IntPointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate).apply {
    set(value)
}
internal fun intPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): IntPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_INT.byteSize())
    val arc = ARC.ofSegment(segment)
    return IntPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeIntPointer(
    pointer: IntPointer,
    onArcUpdated: () -> Unit
): IntPointer {
    val newPointer = IntPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

// endregion

// region UInt Pointer

fun nullableUIntPointerOf(arc: ARC = ARC.ofNull()) = NullableUIntPointer(arc)
fun nullableUIntPointerOf(value: UInt, arc: ARC = ARC.shared(ValueLayout.JAVA_INT)): NullableUIntPointer {
    val pointer = NullableUIntPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun nullableUIntPointerOf(
    value: UInt?,
    onArcUpdate: () -> Unit
): NullableUIntPointer {
    return if (value == null) {
        NullableUIntPointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableUIntPointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate).apply {
            set(value)
        }
    }
}
internal fun nullableUIntPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): NullableUIntPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_INT.byteSize())
    val arc = ARC.ofSegment(segment)
    return NullableUIntPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeNullableUIntPointer(
    pointer: NullableUIntPointer,
    onArcUpdated: () -> Unit
): NullableUIntPointer {
    val newPointer = NullableUIntPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

fun uintPointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_INT)) = UIntPointer(arc)
fun uintPointerOf(arc: ARC, offset: Long) = UIntPointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_INT.byteSize())
    )
)
fun uintPointerOf(value: UInt, arc: ARC = ARC.shared(ValueLayout.JAVA_INT)): UIntPointer {
    val pointer = UIntPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun uintPointerOf(
    value: UInt,
    onArcUpdate: () -> Unit
) = UIntPointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate).apply {
    set(value)
}
internal fun uintPointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): UIntPointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_INT.byteSize())
    val arc = ARC.ofSegment(segment)
    return UIntPointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}
internal fun takeUIntPointer(
    pointer: UIntPointer,
    onArcUpdated: () -> Unit
): UIntPointer {
    val newPointer = UIntPointer(pointer.arc!!, onArcUpdated)
    pointer.arc = null
    return newPointer
}

// endregion