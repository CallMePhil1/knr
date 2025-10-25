@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class UIntNativeArray(
    memory: Memory
) : NativeArray<UInt, UIntArray>(memory, UInt.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getUInt(index.toLong() * typeByteSize)
    override operator fun get(index: Int): UInt {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = UIntArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: UInt) = memory.setUInt(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: UInt) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: UIntArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asIntArray()))
    }
    override fun set(value: Array<UInt>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun uintNativeArray(memory: Memory) = UIntNativeArray(memory)
fun uintNativeArray(size: Long) = uintNativeArray(ArenaMemory.allocate(size * UInt.SIZE_BYTES))
fun uintNativeArray(array: UIntArray): UIntNativeArray {
    val memory = ArenaMemory.allocate((array.size * UInt.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asIntArray()))
    return uintNativeArray(memory)
}