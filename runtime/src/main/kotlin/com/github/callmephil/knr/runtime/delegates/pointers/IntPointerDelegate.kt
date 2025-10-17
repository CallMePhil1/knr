package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.UIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takePointer
import com.github.callmephil.knr.runtime.typing.pointer.uintPointerOf
import java.lang.foreign.MemorySegment

class IntPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> IntPointer
) : PointerFieldDelegate<Int, IntPointer>(parent, offset) {

    override var pointer: IntPointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        intPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableIntPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> IntPointer?
) : NullablePointerFieldDelegate<Int, IntPointer>(parent, offset) {

    override var pointer: IntPointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        intPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class UIntPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> UIntPointer
) : PointerFieldDelegate<UInt, UIntPointer>(parent, offset) {

    override var pointer: UIntPointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        uintPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableUIntPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> UIntPointer?
) : NullablePointerFieldDelegate<UInt, UIntPointer>(parent, offset) {

    override var pointer: UIntPointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        uintPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}