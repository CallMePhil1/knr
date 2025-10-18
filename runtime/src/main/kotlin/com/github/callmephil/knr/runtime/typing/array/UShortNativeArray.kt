@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class UShortNativeArray(
    arc: ARC
) : NativeArray<UShort, UShortArray>(arc, UShort.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = arc.getUShort(index.toLong() * typeByteSize)
    override operator fun get(index: Int): UShort {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = UShortArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: UShort) = arc.setUShort(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: UShort) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: UShortArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asShortArray()))
    }
    override fun set(value: Array<UShort>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun ushortNativeArray(arc: ARC) = UShortNativeArray(arc)
fun ushortNativeArray(size: Long) = ushortNativeArray(ARC.shared(size * UShort.SIZE_BYTES))
fun ushortNativeArray(array: UShortArray): UShortNativeArray {
    val arc = ARC.shared((array.size * Short.SIZE_BYTES).toLong())
    arc.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asShortArray()))
    return ushortNativeArray(arc)
}