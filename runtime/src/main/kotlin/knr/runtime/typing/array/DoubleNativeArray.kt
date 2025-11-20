package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

open class DoubleNativeArray internal constructor(
    memory: Memory,
    size: Int
) : NativeArray<Double, DoubleArray>(memory, size,Double.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getDouble(index.toLong() * typeByteSize)
    override operator fun get(index: Int): Double {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = DoubleArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: Double) = memory.setDouble(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: Double) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: DoubleArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Double>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun doubleNativeArray(memory: Memory, size: Int) = DoubleNativeArray(memory, size)
fun doubleNativeArray(memory: Memory) = doubleNativeArray(memory, memory.byteSize.toInt() / Double.SIZE_BYTES)
fun doubleNativeArray(size: Long) = doubleNativeArray(ArenaMemory.allocate(size * Double.SIZE_BYTES))
fun doubleNativeArray(array: DoubleArray): DoubleNativeArray {
    val memory = ArenaMemory.allocate((array.size * Double.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return doubleNativeArray(memory)
}