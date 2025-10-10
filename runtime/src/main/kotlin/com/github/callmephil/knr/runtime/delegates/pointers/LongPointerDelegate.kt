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
import com.github.callmephil.knr.runtime.typing.pointer.takeLongPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeNullableLongPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeNullableULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.takeULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.ulongPointerOf
import java.lang.foreign.MemorySegment

class LongPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> LongPointer
) : PointerFieldDelegate<Long, LongPointer>(ownerArc, offset) {

    override var pointer: LongPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeLongPointer(initialValue(), ::updateOwnersArc)
    } else {
        longPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableLongPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> NullableLongPointer
) : NullablePointerFieldDelegate<Long, NullableLongPointer>(ownerArc, offset) {

    override var pointer: NullableLongPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeNullableLongPointer(initialValue(), ::updateOwnersArc)
    } else {
        nullableLongPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class ULongPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> ULongPointer
) : PointerFieldDelegate<ULong, ULongPointer>(ownerArc, offset) {

    override var pointer: ULongPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeULongPointer(initialValue(), ::updateOwnersArc)
    } else {
        ulongPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableULongPointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: () -> NullableULongPointer
) : NullablePointerFieldDelegate<ULong, NullableULongPointer>(ownerArc, offset) {

    override var pointer: NullableULongPointer = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        takeNullableULongPointer(initialValue(), ::updateOwnersArc)
    } else {
        nullableULongPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}