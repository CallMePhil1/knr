package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.UIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeUIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.uintPointerOf
import java.lang.foreign.MemorySegment

class IntPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> IntPointer
) : PointerFieldDelegate<Int, IntPointer>(ownerArc, offset) {

    override var pointer: IntPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeIntPointer(initialValue(), ::updateOwnersArc)
    } else {
        intPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableIntPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> IntPointer?
) : NullablePointerFieldDelegate<Int, IntPointer>(ownerArc, offset) {

    override var pointer: IntPointer? = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takeIntPointer(value, ::updateOwnersArc)
        }
    } else {
        intPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class UIntPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> UIntPointer
) : PointerFieldDelegate<UInt, UIntPointer>(ownerArc, offset) {

    override var pointer: UIntPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeUIntPointer(initialValue(), ::updateOwnersArc)
    } else {
        uintPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableUIntPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> UIntPointer?
) : NullablePointerFieldDelegate<UInt, UIntPointer>(ownerArc, offset) {

    override var pointer: UIntPointer? = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takeUIntPointer(value, ::updateOwnersArc)
        }
    } else {
        uintPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}