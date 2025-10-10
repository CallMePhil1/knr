package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableUIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.UIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableUIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeNullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeNullableUIntPointer
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
    initialValue: () -> NullableIntPointer
) : NullablePointerFieldDelegate<Int, NullableIntPointer>(ownerArc, offset) {

    override var pointer: NullableIntPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeNullableIntPointer(initialValue(), ::updateOwnersArc)
    } else {
        nullableIntPointerOf(ownerArc, offset, ::updateOwnersArc)
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
    initialValue: () -> NullableUIntPointer
) : NullablePointerFieldDelegate<UInt, NullableUIntPointer>(ownerArc, offset) {

    override var pointer: NullableUIntPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeNullableUIntPointer(initialValue(), ::updateOwnersArc)
    } else {
        nullableUIntPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}