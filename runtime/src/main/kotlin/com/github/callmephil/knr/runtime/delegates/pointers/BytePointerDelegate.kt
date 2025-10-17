package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.UBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takePointer
import com.github.callmephil.knr.runtime.typing.pointer.ubytePointerOf
import java.lang.foreign.MemorySegment

class BytePointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> BytePointer
) : PointerFieldDelegate<Byte, BytePointer>(parent, offset) {

    override var pointer: BytePointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        bytePointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableBytePointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> BytePointer?
) : NullablePointerFieldDelegate<Byte, BytePointer>(parent, offset) {

    override var pointer: BytePointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        bytePointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class UBytePointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> UBytePointer
) : PointerFieldDelegate<UByte, UBytePointer>(parent, offset) {

    override var pointer: UBytePointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        ubytePointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableUBytePointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> UBytePointer?
) : NullablePointerFieldDelegate<UByte, UBytePointer>(parent, offset) {

    override var pointer: UBytePointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        ubytePointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}