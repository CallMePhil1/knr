@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class ULongNativeArray(
    arc: ARC
) : NativeArray<ULong, ULongArray>(arc, ULong.SIZE_BYTES) {

    override operator fun get(index: Int): ULong {
        checkBounds(index)
        return arc.getULong(index.toLong() * typeByteSize)
    }
    override fun get() = ULongArray(size) { this[it] }
    override fun getArray() = Array(size) { this[it] }

    override operator fun set(index: Int, value: ULong) {
        checkBounds(index)
        arc.setULong(index.toLong() * typeByteSize, value)
    }
    override fun set(value: ULongArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asLongArray()))
    }
    override fun set(value: Array<ULong>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this[i] = value[i]
        }
    }
}

fun ulongNativeArray(arc: ARC) = ULongNativeArray(arc)
fun ulongNativeArray(size: Long) = ulongNativeArray(ARC.shared(size * ULong.SIZE_BYTES))
fun ulongNativeArray(array: ULongArray): ULongNativeArray {
    val arc = ARC.shared((array.size * ULong.SIZE_BYTES).toLong())
    arc.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asLongArray()))
    return ulongNativeArray(arc)
}