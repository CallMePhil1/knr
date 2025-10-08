package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.ShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableUShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.UShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.nullableShortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableUShortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ushortPointerOf

class ShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: Short
) : PointerFieldDelegate<Short, ShortPointer>(ownerArc, offset) {

    override var pointer: ShortPointer = shortPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: Short?
) : NullablePointerFieldDelegate<Short, NullableShortPointer>(ownerArc, offset) {

    override var pointer: NullableShortPointer = nullableShortPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class UShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: UShort
) : PointerFieldDelegate<UShort, UShortPointer>(ownerArc, offset) {

    override var pointer: UShortPointer = ushortPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableUShortPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: UShort?
) : NullablePointerFieldDelegate<UShort, NullableUShortPointer>(ownerArc, offset) {

    override var pointer: NullableUShortPointer = nullableUShortPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}