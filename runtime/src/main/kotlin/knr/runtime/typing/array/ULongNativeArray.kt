@file:OptIn(ExperimentalUnsignedTypes::class)

package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class ULongNativeArray(
    memory: Memory,
    size: Int
) : NativeArray<ULong, ULongArray>(memory, size, ULong.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getULong(index.toLong() * typeByteSize)
    override operator fun get(index: Int): ULong {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = ULongArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: ULong) = memory.setULong(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: ULong) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: ULongArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asLongArray()))
    }
    override fun set(value: Array<ULong>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun ulongNativeArray(memory: Memory, size: Int) = ULongNativeArray(memory, size)
fun ulongNativeArray(memory: Memory) = ulongNativeArray(memory, memory.byteSize.toInt() / ULong.SIZE_BYTES)
fun ulongNativeArray(size: Long) = ulongNativeArray(ArenaMemory.allocate(size * ULong.SIZE_BYTES))
fun ulongNativeArray(array: ULongArray): ULongNativeArray {
    val memory = ArenaMemory.allocate((array.size * ULong.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asLongArray()))
    return ulongNativeArray(memory)
}