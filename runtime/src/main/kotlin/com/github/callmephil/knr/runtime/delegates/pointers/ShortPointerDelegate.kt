package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.pointer.ShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.UShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takePointer
import com.github.callmephil.knr.runtime.typing.pointer.ushortPointerOf
import java.lang.foreign.MemorySegment

class ShortPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> ShortPointer
) : PointerFieldDelegate<Short, ShortPointer>(parent, offset) {

    override var pointer: ShortPointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        shortPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableShortPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> ShortPointer?
) : NullablePointerFieldDelegate<Short, ShortPointer>(parent, offset) {

    override var pointer: ShortPointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        shortPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class UShortPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> UShortPointer
) : PointerFieldDelegate<UShort, UShortPointer>(parent, offset) {

    override var pointer: UShortPointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        ushortPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableUShortPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> UShortPointer?
) : NullablePointerFieldDelegate<UShort, UShortPointer>(parent, offset) {

    override var pointer: UShortPointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        ushortPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}