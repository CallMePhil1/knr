package knr.runtime.delegates.union

import knr.runtime.delegates.FieldDelegate
import knr.runtime.memory.Memory
import knr.runtime.memory.MemorySlice
import knr.runtime.typing.Union
import knr.runtime.typing.pointer.Pointer
import java.lang.foreign.MemorySegment

open class PointerFieldDelegate<T, P : Pointer<T>?> internal constructor(
    parent: Union,
    private val byteSize: Long,
    private val ctor: (Memory) -> P
) : FieldDelegate<P?>(
    parent,
    0
) {

    override fun get() = if (parent.memory.getAddress(0) == MemorySegment.NULL)
        null
    else {
        val segment = MemorySegment
            .ofAddress(parent.memory.getLong(0))
            .reinterpret(byteSize)
        ctor(MemorySlice(segment))
    }

    override fun set(value: P?) {
        if (value == null)
            parent.memory.setAddress(0, MemorySegment.NULL)
        else
            parent.memory.setAddress(0, value.memory.memorySegment!!)
    }
}