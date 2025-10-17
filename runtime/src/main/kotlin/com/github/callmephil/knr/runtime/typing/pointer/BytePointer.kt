package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.ValueLayout

class BytePointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : Pointer<Byte>(arc, onArcUpdated) {

    override fun get() = arc!!.getByte(0)

    override fun set(value: Byte) = arc!!.setByte(0, value)

    override fun shareOf(): BytePointer {
        validOrThrow(this)
        return BytePointer(arc!!, null)
    }
}

class UBytePointer internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null
) : Pointer<UByte>(arc, onArcUpdated) {

    override fun get() = arc!!.getByte(0).toUByte()

    override fun set(value: UByte) = arc!!.setByte(0, value.toByte())

    override fun shareOf(): UBytePointer {
        validOrThrow(this)
        return UBytePointer(arc!!, null)
    }
}

// region Byte Pointer

fun bytePointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) = BytePointer(arc)
fun bytePointerOf(arc: ARC, offset: Long) = BytePointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )
)
fun bytePointerOf(value: Byte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) = BytePointer(arc).apply {
    set(value)
}
internal fun bytePointerOf(
    value: Byte,
    onArcUpdate: () -> Unit
) = BytePointer(ARC.shared(ValueLayout.JAVA_BYTE), onArcUpdate).apply {
    set(value)
}
internal fun bytePointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): BytePointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    val arc = ARC.ofSegment(segment)
    return BytePointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}

// endregion

// region UByte Pointer

fun ubytePointerOf(arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) = UBytePointer(arc)
fun ubytePointerOf(arc: ARC, offset: Long) = UBytePointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )
)
fun ubytePointerOf(value: UByte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) = UBytePointer(arc).apply {
    set(value)
}
internal fun ubytePointerOf(
    value: UByte,
    onArcUpdate: () -> Unit
) = UBytePointer(ARC.shared(ValueLayout.JAVA_BYTE), onArcUpdate).apply {
    set(value)
}
internal fun ubytePointerOf(
    arc: ARC,
    offset: Long,
    onArcUpdated: () -> Unit
): UBytePointer {
    val segment = arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    val arc = ARC.ofSegment(segment)
    return UBytePointer(arc).apply {
        this.onArcUpdated = onArcUpdated
    }
}

// endregion
