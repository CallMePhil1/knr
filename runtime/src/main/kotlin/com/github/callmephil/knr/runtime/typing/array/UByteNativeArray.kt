@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory

open class UByteNativeArray internal constructor(
    memory: Memory,
) : NativeArray<UByte, UByteArray>(memory, UByte.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = memory.getUByte(index.toLong())
    override operator fun get(index: Int): UByte {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = toByteArray().asUByteArray()

    override fun setUnchecked(index: Int, value: UByte) = memory.setUByte(index.toLong(), value)
    override operator fun set(index: Int, value: UByte) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: UByteArray) = setByteArray(value.asByteArray())
    override fun set(value: Array<UByte>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun ubyteNativeArray(memory: Memory) = UByteNativeArray(memory)
fun ubyteNativeArray(size: Long) = ubyteNativeArray(ArenaMemory.allocate(size))
fun ubyteNativeArray(array: UByteArray): UByteNativeArray {
    val memory = ArenaMemory.allocate(array.size.toLong())
    memory.setBytes(array.asByteArray())
    return ubyteNativeArray(memory)
}