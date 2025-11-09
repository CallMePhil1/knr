package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class LongNativeArray(
    memory: Memory,
    size: Int
) : NativeArray<Long, LongArray>(memory, size, Long.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getLong(index.toLong() * typeByteSize)
    override operator fun get(index: Int): Long {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = LongArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: Long) = memory.setLong(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: Long) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: LongArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Long>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun longNativeArray(memory: Memory, size: Int) = LongNativeArray(memory, size)
fun longNativeArray(memory: Memory) = longNativeArray(memory, memory.byteSize.toInt() / Long.SIZE_BYTES)
fun longNativeArray(size: Long) = longNativeArray(ArenaMemory.allocate(size * Long.SIZE_BYTES))
fun longNativeArray(array: LongArray): LongNativeArray {
    val memory = ArenaMemory.allocate((array.size * Long.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return longNativeArray(memory)
}