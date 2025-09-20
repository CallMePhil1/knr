package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.NullablePointer
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.shareFrom
import java.lang.foreign.MemorySegment

abstract class PointerFieldDelegate<T, P : Pointer<T>>(
    ownerArc: ARC,
    offset: Long,
) : FieldDelegate<P>(
    ownerArc,
    offset
) {
    protected abstract var pointer: P

    override fun get() = pointer

    override fun set(value: P) {
        pointer shareFrom value
    }

    protected fun updateOwnersArc() {
        ownerArc.setAddress(offset, pointer.arc!!.memorySegment!!)
    }
}

abstract class NullablePointerFieldDelegate<T, P : NullablePointer<T>>(
    ownerArc: ARC,
    offset: Long,
) : FieldDelegate<P>(
    ownerArc,
    offset
) {
    protected abstract var pointer: P

    override fun get() = pointer

    override fun set(value: P) {
        pointer shareFrom value
    }

    protected fun updateOwnersArc() {
        ownerArc.setAddress(offset, pointer.arc?.memorySegment ?: MemorySegment.NULL)
    }
}
