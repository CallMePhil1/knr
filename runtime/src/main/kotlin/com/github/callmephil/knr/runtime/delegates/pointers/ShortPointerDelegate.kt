package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.ShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.UShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeUShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.ushortPointerOf
import java.lang.foreign.MemorySegment

class ShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> ShortPointer
) : PointerFieldDelegate<Short, ShortPointer>(ownerArc, offset) {

    override var pointer: ShortPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeShortPointer(initialValue(), ::updateOwnersArc)
    } else {
        shortPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> ShortPointer?
) : NullablePointerFieldDelegate<Short, ShortPointer>(ownerArc, offset) {

    override var pointer: ShortPointer? = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takeShortPointer(value, ::updateOwnersArc)
        }
    } else {
        shortPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class UShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> UShortPointer
) : PointerFieldDelegate<UShort, UShortPointer>(ownerArc, offset) {

    override var pointer: UShortPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeUShortPointer(initialValue(), ::updateOwnersArc)
    } else {
        ushortPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableUShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> UShortPointer?
) : NullablePointerFieldDelegate<UShort, UShortPointer>(ownerArc, offset) {

    override var pointer: UShortPointer? = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takeUShortPointer(value, ::updateOwnersArc)
        }
    } else {
        ushortPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}