package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.LongPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableLongPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.ULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableLongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableULongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ulongPointerOf

class LongPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: Long
) : PointerFieldDelegate<Long, LongPointer>(ownerArc, offset) {

    override var pointer: LongPointer = longPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableLongPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: Long?
) : NullablePointerFieldDelegate<Long, NullableLongPointer>(ownerArc, offset) {

    override var pointer: NullableLongPointer = nullableLongPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class ULongPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: ULong
) : PointerFieldDelegate<ULong, ULongPointer>(ownerArc, offset) {

    override var pointer: ULongPointer = ulongPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableULongPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: ULong?
) : NullablePointerFieldDelegate<ULong, NullableULongPointer>(ownerArc, offset) {

    override var pointer: NullableULongPointer = nullableULongPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}