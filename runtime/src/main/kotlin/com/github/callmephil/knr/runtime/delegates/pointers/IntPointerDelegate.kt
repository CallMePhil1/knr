package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf

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