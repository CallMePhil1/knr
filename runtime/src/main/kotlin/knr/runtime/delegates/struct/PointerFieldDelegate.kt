package knr.runtime.delegates.struct

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Native
import knr.runtime.typing.pointer.Pointer
import java.lang.foreign.MemorySegment

open class PointerFieldDelegate<T, P : Pointer<T>?> internal constructor(
    parent: Native<*>,
    offset: Long,
    initialValue: P
) : FieldDelegate<P>(
    parent,
    offset
) {
    private var pointer: P = initialValue

    init {
        updateOwner()
    }

    fun disposeAndSet(value: P) {
        pointer?.dispose()
        set(value)
    }

    override fun get() = pointer

    override fun set(value: P) {
        pointer = value
        updateOwner()
    }

    private fun updateOwner() {
        when {
            parent.isNotValid -> return
            pointer == null -> parent.memory.setAddress(offset, MemorySegment.NULL)
            pointer!!.isNotValid -> return
            else -> parent.memory.setAddress(offset, pointer!!.innerMemory!!.memorySegment!!)
        }
    }
}