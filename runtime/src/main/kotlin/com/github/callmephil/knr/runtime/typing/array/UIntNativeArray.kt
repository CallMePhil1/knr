@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class UIntNativeArray(
    arc: ARC
) : NativeArray<UInt, UIntArray>(arc, UInt.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = arc.getUInt(index.toLong() * typeByteSize)
    override operator fun get(index: Int): UInt {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = UIntArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: UInt) = arc.setUInt(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: UInt) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: UIntArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asIntArray()))
    }
    override fun set(value: Array<UInt>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun uintNativeArray(arc: ARC) = UIntNativeArray(arc)
fun uintNativeArray(size: Long) = uintNativeArray(ARC.shared(size * UInt.SIZE_BYTES))
fun uintNativeArray(array: UIntArray): UIntNativeArray {
    val arc = ARC.shared((array.size * UInt.SIZE_BYTES).toLong())
    arc.memorySegment!!.copyFrom(MemorySegment.ofArray(array.asIntArray()))
    return uintNativeArray(arc)
}