package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.MemorySegment

open class FloatNativeArray internal constructor(
    memory: Memory,
    size: Int
) : NativeArray<Float, FloatArray>(memory, size,Float.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getFloat(index.toLong() * typeByteSize)
    override operator fun get(index: Int): Float {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = FloatArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: Float) = memory.setFloat(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: Float) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: FloatArray) {
        checkBounds(value.size)
        memory.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Float>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun floatNativeArray(memory: Memory, size: Int) = FloatNativeArray(memory, size)
fun floatNativeArray(memory: Memory) = floatNativeArray(memory, memory.byteSize.toInt() / Float.SIZE_BYTES)
fun floatNativeArray(size: Long) = floatNativeArray(ArenaMemory.allocate(size * Float.SIZE_BYTES))
fun floatNativeArray(array: FloatArray): FloatNativeArray {
    val memory = ArenaMemory.allocate((array.size * Float.SIZE_BYTES).toLong())
    memory.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return floatNativeArray(memory)
}