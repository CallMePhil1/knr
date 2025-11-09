package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class ShortNativeArray(
    memory: Memory,
    size: Int
) : NativeArray<Short, ShortArray>(memory, size, Short.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getShort(index.toLong() * typeByteSize)
    override operator fun get(index: Int): Short {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = ShortArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: Short) = memory.setShort(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: Short) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: ShortArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Short>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun shortNativeArray(memory: Memory, size: Int) = ShortNativeArray(memory, size)
fun shortNativeArray(memory: Memory) = shortNativeArray(memory, memory.byteSize.toInt() / Short.SIZE_BYTES)
fun shortNativeArray(size: Long) = shortNativeArray(ArenaMemory.allocate(size * Short.SIZE_BYTES))
fun shortNativeArray(array: ShortArray): ShortNativeArray {
    val memory = ArenaMemory.allocate((array.size * Short.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return shortNativeArray(memory)
}