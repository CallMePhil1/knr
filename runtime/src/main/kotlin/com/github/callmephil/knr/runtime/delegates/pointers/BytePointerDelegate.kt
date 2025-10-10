package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableUBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.UBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableUBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.takeNullableBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.takeNullableUBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.takeUBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.ubytePointerOf
import java.lang.foreign.MemorySegment

class BytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> BytePointer
) : PointerFieldDelegate<Byte, BytePointer>(ownerArc, offset) {

    override var pointer: BytePointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeBytePointer(initialValue(), ::updateOwnersArc)
    } else {
        bytePointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableBytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> NullableBytePointer
) : NullablePointerFieldDelegate<Byte, NullableBytePointer>(ownerArc, offset) {

    override var pointer: NullableBytePointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeNullableBytePointer(initialValue(), ::updateOwnersArc)
    } else {
        nullableBytePointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class UBytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> UBytePointer
) : PointerFieldDelegate<UByte, UBytePointer>(ownerArc, offset) {

    override var pointer: UBytePointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeUBytePointer(initialValue(), ::updateOwnersArc)
    } else {
        ubytePointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableUBytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> NullableUBytePointer
) : NullablePointerFieldDelegate<UByte, NullableUBytePointer>(ownerArc, offset) {

    override var pointer: NullableUBytePointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeNullableUBytePointer(initialValue(), ::updateOwnersArc)
    } else {
        nullableUBytePointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}