package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.pointer.LongPointer
import com.github.callmephil.knr.runtime.typing.pointer.ULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takePointer
import com.github.callmephil.knr.runtime.typing.pointer.ulongPointerOf
import java.lang.foreign.MemorySegment

class LongPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> LongPointer
) : PointerFieldDelegate<Long, LongPointer>(parent, offset) {

    override var pointer: LongPointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        longPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableLongPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> LongPointer?
) : NullablePointerFieldDelegate<Long, LongPointer>(parent, offset) {

    override var pointer: LongPointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        longPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class ULongPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> ULongPointer
) : PointerFieldDelegate<ULong, ULongPointer>(parent, offset) {

    override var pointer: ULongPointer = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        takePointer(initialValue(), ::updateOwnersArc)
    } else {
        ulongPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}

class NullableULongPointerDelegate internal constructor(
    parent: Struct,
    offset: Long,
    initialValue: () -> ULongPointer?
) : NullablePointerFieldDelegate<ULong, ULongPointer>(parent, offset) {

    override var pointer: ULongPointer? = if (parent.arc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takePointer(value, ::updateOwnersArc)
        }
    } else {
        ulongPointerOf(parent.arc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}