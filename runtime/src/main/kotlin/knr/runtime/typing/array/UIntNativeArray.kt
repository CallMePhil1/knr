@file:OptIn(ExperimentalUnsignedTypes::class)

package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class UIntNativeArray(
    memory: Memory,
    size: Int
) : NativeArray<UInt, UIntArray>(memory, size, UInt.SIZE_BYTES) {

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

fun uintNativeArray(memory: Memory, size: Int) = UIntNativeArray(memory, size)
fun uintNativeArray(memory: Memory) = uintNativeArray(memory, memory.byteSize.toInt() / UInt.SIZE_BYTES)
fun uintNativeArray(size: Long) = uintNativeArray(ArenaMemory.allocate(size * UInt.SIZE_BYTES))
fun uintNativeArray(array: UIntArray): UIntNativeArray {
    val memory = ArenaMemory.allocate((array.size * UInt.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asIntArray()))
    return uintNativeArray(memory)
}