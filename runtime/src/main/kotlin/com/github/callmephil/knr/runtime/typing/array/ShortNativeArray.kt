package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class ShortNativeArray(
    arc: ARC
) : NativeArray<Short, ShortArray>(arc, Short.SIZE_BYTES) {

    override operator fun get(index: Int): Short {
        checkBounds(index)
        return arc.getShort(index.toLong() * typeByteSize)
    }
    override fun get() = ShortArray(size) { this[it] }
    override fun getArray() = Array(size) { this[it] }

    override operator fun set(index: Int, value: Short) {
        checkBounds(index)
        arc.setShort(index.toLong() * typeByteSize, value)
    }
    override fun set(value: ShortArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Short>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this[i] = value[i]
        }
    }
}

fun shortNativeArray(arc: ARC) = ShortNativeArray(arc)
fun shortNativeArray(size: Long) = shortNativeArray(ARC.shared(size * Short.SIZE_BYTES))
fun shortNativeArray(array: ShortArray): ShortNativeArray {
    val arc = ARC.shared((array.size * Short.SIZE_BYTES).toLong())
    arc.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return shortNativeArray(arc)
}