package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import java.lang.foreign.MemorySegment

abstract class PointerFieldDelegate<T, P : Pointer<T>> internal constructor(
    parent: Struct,
    offset: Long,
) : FieldDelegate<P>(
    parent,
    offset
) {
    protected abstract var pointer: P

    val isValid get() = pointer.isValid
    val isNotValid get() = !isValid

    override fun get() = pointer

    override fun set(value: P) {
        pointer takeFrom value
    }

    protected fun updateOwnersArc() {
        when {
            parent.isNotValid -> return
            pointer.isNotValid -> return
            else -> parent.arc.setAddress(offset, pointer.arc!!.memorySegment!!)
        }
    }
}

abstract class NullablePointerFieldDelegate<T, P : Pointer<T>> internal constructor(
    parent: Struct,
    offset: Long,
) : FieldDelegate<P?>(
    parent,
    offset
) {
    protected abstract var pointer: P?

    val isValid get() = pointer?.isValid ?: true
    val isNotValid get() = !isValid

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
        when {
            parent.isNotValid -> return
            pointer == null -> parent.arc.setAddress(offset, MemorySegment.NULL)
            pointer!!.isNotValid -> return
            else -> parent.arc.setAddress(offset, pointer!!.arc!!.memorySegment!!)
        }
    }
}
