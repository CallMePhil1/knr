@file:OptIn(ExperimentalUnsignedTypes::class)

package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class UShortNativeArray(
    memory: Memory,
    size: Int
) : NativeArray<UShort, UShortArray>(memory, size, UShort.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getUShort(index.toLong() * typeByteSize)
    override operator fun get(index: Int): UShort {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = UShortArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: UShort) = memory.setUShort(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: UShort) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: UShortArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asShortArray()))
    }
    override fun set(value: Array<UShort>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun ushortNativeArray(memory: Memory, size: Int) = UShortNativeArray(memory, size)
fun ushortNativeArray(memory: Memory) = ushortNativeArray(memory, memory.byteSize.toInt() / UShort.SIZE_BYTES)
fun ushortNativeArray(size: Long) = ushortNativeArray(ArenaMemory.allocate(size * UShort.SIZE_BYTES))
fun ushortNativeArray(array: UShortArray): UShortNativeArray {
    val memory = ArenaMemory.allocate((array.size * Short.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asShortArray()))
    return ushortNativeArray(memory)
}