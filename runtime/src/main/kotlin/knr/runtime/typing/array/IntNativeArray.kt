package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

class IntNativeArray(
    memory: Memory,
    size: Int
) : NativeArray<Int, IntArray>(memory, size, Int.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getInt(index.toLong() * typeByteSize)
    override operator fun get(index: Int): Int {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = IntArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: Int) = memory.setInt(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: Int) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: IntArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Int>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun intNativeArray(memory: Memory, size: Int) = IntNativeArray(memory, size)
fun intNativeArray(memory: Memory) = intNativeArray(memory, memory.byteSize.toInt() / Int.SIZE_BYTES)
fun intNativeArray(size: Long) = intNativeArray(ArenaMemory.allocate(size * Int.SIZE_BYTES))
fun intNativeArray(array: IntArray): IntNativeArray {
    val memory = ArenaMemory.allocate((array.size * Int.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return intNativeArray(memory)
}