package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import java.lang.foreign.MemorySegment

abstract class PointerFieldDelegate<T, P : Pointer<T>> internal constructor(
    ownerArc: ARC,
    offset: Long,
) : FieldDelegate<P>(
    ownerArc,
    offset
) {
    protected abstract var pointer: P

    override fun get() = pointer

    override fun set(value: P) {
        pointer takeFrom value
    }

    protected fun updateOwnersArc() {
        ownerArc.setAddress(offset, pointer.arc!!.memorySegment!!)
    }
}

abstract class NullablePointerFieldDelegate<T, P : Pointer<T>> internal constructor(
    ownerArc: ARC,
    offset: Long,
) : FieldDelegate<P?>(
    ownerArc,
    offset
) {
    protected abstract var pointer: P?

    override fun get(): P? = pointer

    @Suppress("UNCHECKED_CAST")
    override fun set(value: P?) {
        when {
            value == null -> {
                val prevPointer = pointer
                pointer = null
                prevPointer?.dispose()
            }
            pointer == null -> {
                pointer = value.shareOf() as P
                pointer!!.onArcUpdated = ::updateOwnersArc
                updateOwnersArc()
            }
            else -> { pointer!! takeFrom value }
        }
    }

    protected fun updateOwnersArc() {
        if (pointer == null)
            ownerArc.setAddress(offset, MemorySegment.NULL)
        else
            ownerArc.setAddress(offset, pointer!!.arc!!.memorySegment!!)
    }
}
