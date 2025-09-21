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
import com.github.callmephil.knr.runtime.typing.pointer.uintPointerOf

class IntPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: Int
) : PointerFieldDelegate<Int, IntPointer>(ownerArc, offset) {

    override var pointer: IntPointer = intPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableIntPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: Int?
) : NullablePointerFieldDelegate<Int, NullableIntPointer>(ownerArc, offset) {

    override var pointer: NullableIntPointer = nullableIntPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class UIntPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: UInt
) : PointerFieldDelegate<UInt, UIntPointer>(ownerArc, offset) {

    override var pointer: UIntPointer = uintPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableUIntPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: UInt?
) : NullablePointerFieldDelegate<UInt, NullableUIntPointer>(ownerArc, offset) {

    override var pointer: NullableUIntPointer = nullableUIntPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}