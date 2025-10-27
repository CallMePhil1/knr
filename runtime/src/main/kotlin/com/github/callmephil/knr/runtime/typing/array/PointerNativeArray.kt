package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import java.lang.foreign.ValueLayout

class PointerNativeArray<T : Pointer<*>> internal constructor(
    memory: Memory,
    private val pointers: Array<T>
) : NativeArray<T, Array<T>>(memory, ValueLayout.ADDRESS.byteSize().toInt()) {
    override fun getUnchecked(index: Int) = pointers[index]
    override fun get(index: Int) = pointers[index]
    override fun get() = pointers

    override fun setUnchecked(index: Int, value: T) = set(index, value)
    override fun set(index: Int, value: T) {
        pointers[index] = value
        memory.setAddress((index * typeByteSize).toLong(), value.memory.memorySegment!!)
    }
    override fun set(value: Array<T>) {
        value.forEachIndexed { idx, it ->
            this[idx] = it
        }
    }
}

fun <T : Pointer<*>> pointerNativeArray(vararg values: T): PointerNativeArray<T> {
    val memory = ArenaMemory.allocate(values.size * ValueLayout.ADDRESS.byteSize())
    values.forEachIndexed { idx, it ->
        memory.setAddress(idx * ValueLayout.ADDRESS.byteSize(), it.memory.memorySegment!!)
    }
    return PointerNativeArray(memory, values) as PointerNativeArray<T>
}

