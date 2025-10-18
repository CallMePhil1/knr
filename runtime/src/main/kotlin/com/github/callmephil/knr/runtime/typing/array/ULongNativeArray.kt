@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class ULongNativeArray(
    arc: ARC
) : NativeArray<ULong, ULongArray>(arc, ULong.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = arc.getULong(index.toLong() * typeByteSize)
    override operator fun get(index: Int): ULong {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = ULongArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: ULong) = arc.setULong(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: ULong) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: ULongArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asLongArray()))
    }
    override fun set(value: Array<ULong>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
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