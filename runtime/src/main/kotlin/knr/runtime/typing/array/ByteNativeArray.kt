package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory

open class ByteNativeArray internal constructor(
    memory: Memory,
) : NativeArray<Byte, ByteArray>(memory, Byte.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getByte(index.toLong())
    override operator fun get(index: Int): Byte {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = toByteArray()

    override fun setUnchecked(index: Int, value: Byte) = memory.setByte(index.toLong(), value)
    override operator fun set(index: Int, value: Byte) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: ByteArray) = setByteArray(value)
    override fun set(value: Array<Byte>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun byteNativeArray(memory: Memory) = ByteNativeArray(memory)
fun byteNativeArray(size: Long) = byteNativeArray(ArenaMemory.allocate(size))
fun byteNativeArray(array: ByteArray): ByteNativeArray {
    val memory = ArenaMemory.allocate(array.size.toLong())
    memory.setBytes(array)
    return byteNativeArray(memory)
}