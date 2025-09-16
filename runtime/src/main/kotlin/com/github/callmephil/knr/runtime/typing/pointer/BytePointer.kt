package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class NullableBytePointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<Byte>(arc, onArcUpdated) {

    override fun get(): Byte = arc!!.getByte(0)

    override fun set(value: Byte) {
        arc!!.setByte(0, value)
    }

    override fun shareOf(): NullableBytePointer {
        validOrThrow(this)
        return NullableBytePointer(arc!!, null)
    }
}

class BytePointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<Byte>(arc, onArcUpdated) {

    override fun get() = arc!!.getByte(0)

    override fun set(value: Byte) = arc!!.setByte(0, value)

    override fun shareOf(): BytePointer {
        validOrThrow(this)
        return BytePointer(arc!!, null)
    }
}

class NullableUBytePointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : NullablePrimitivePointer<UByte>(arc, onArcUpdated) {

    override fun get() = arc!!.getByte(0).toUByte()

    override fun set(value: UByte) {
        arc!!.setByte(0, value.toByte())
    }

    override fun shareOf(): NullableUBytePointer {
        validOrThrow(this)
        return NullableUBytePointer(arc!!, null)
    }
}

class UBytePointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : PrimitivePointer<UByte>(arc, onArcUpdated) {

    override fun get() = arc!!.getByte(0).toUByte()

    override fun set(value: UByte) = arc!!.setByte(0, value.toByte())

    override fun shareOf(): UBytePointer {
        validOrThrow(this)
        return UBytePointer(arc!!, null)
    }
}

// region Byte Pointer
fun nullableBytePointerOf(arc: ARC = ARC.ofNull()) = NullableBytePointer(arc)
fun nullableBytePointerOf(value: Byte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) {
    val pointer = BytePointer(arc)
    pointer.set(value)
}
internal fun nullableBytePointerOf(
    value: Byte?,
    onArcUpdate: () -> Unit
): NullableBytePointer {
    return if (value == null) {
        NullableBytePointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableBytePointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate).apply {
            set(value)
        }
    }
}

fun bytePointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) = BytePointer(arc)
fun bytePointerOf(arc: ARC, offset: Long) = BytePointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )
)
fun bytePointerOf(value: Byte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) {
    val pointer = BytePointer(arc)
    pointer.set(value)
}
internal fun bytePointerOf(value: Byte, onArcUpdate: () -> Unit): BytePointer {
    val pointer = BytePointer(ARC.shared(ValueLayout.JAVA_BYTE), onArcUpdate)
    pointer.set(value)
    return pointer
}

// endregion

// region UByte Pointer

fun nullableUBytePointerOf(arc: ARC = ARC.ofNull()) = NullableUBytePointer(arc)
fun nullableUBytePointerOf(value: UByte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) {
    val pointer = UBytePointer(arc)
    pointer.set(value)
}
internal fun nullableUBytePointerOf(
    value: UByte?,
    onArcUpdate: () -> Unit
): NullableUBytePointer {
    return if (value == null) {
        NullableUBytePointer(ARC.ofNull(), onArcUpdate)
    } else {
        NullableUBytePointer(ARC.shared(ValueLayout.JAVA_INT), onArcUpdate).apply {
            set(value)
        }
    }
}

fun ubytePointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) = UBytePointer(arc)
fun ubytePointerOf(arc: ARC, offset: Long) = UBytePointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )
)
fun bytePointerOf(value: UByte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) {
    val pointer = UBytePointer(arc)
    pointer.set(value)
}
internal fun bytePointerOf(value: UByte, onArcUpdate: () -> Unit): UBytePointer {
    val pointer = UBytePointer(ARC.shared(ValueLayout.JAVA_BYTE), onArcUpdate)
    pointer.set(value)
    return pointer
}

// endregion
