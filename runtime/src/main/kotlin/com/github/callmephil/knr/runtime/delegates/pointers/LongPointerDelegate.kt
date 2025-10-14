package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.LongPointer
import com.github.callmephil.knr.runtime.typing.pointer.ULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.takeLongPointer
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
    initialValue: () -> LongPointer?
) : NullablePointerFieldDelegate<Long, LongPointer>(ownerArc, offset) {

    override var pointer: LongPointer? = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takeLongPointer(value, ::updateOwnersArc)
        }
    } else {
        longPointerOf(ownerArc, offset, ::updateOwnersArc)
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
    initialValue: () -> ULongPointer?
) : NullablePointerFieldDelegate<ULong, ULongPointer>(ownerArc, offset) {

    override var pointer: ULongPointer? = if (ownerArc.getAddress(offset) == MemorySegment.NULL) {
        when (val value = initialValue()) {
            null -> null
            else -> takeULongPointer(value, ::updateOwnersArc)
        }
    } else {
        ulongPointerOf(ownerArc, offset, ::updateOwnersArc)
    }

    init {
        updateOwnersArc()
    }
}