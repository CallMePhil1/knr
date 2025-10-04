@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class UIntNativeArray(
    arc: ARC
) : NativeArray<UInt, UIntArray>(arc, UInt.SIZE_BYTES) {

    override operator fun get(index: Int): UInt {
        checkBounds(index)
        return arc.getUInt(index.toLong() * typeByteSize)
    }
    override fun get() = UIntArray(size) { this[it] }
    override fun getArray() = Array(size) { this[it] }

    override operator fun set(index: Int, value: UInt) {
        checkBounds(index)
        arc.setUInt(index.toLong() * typeByteSize, value)
    }
    override fun set(value: UIntArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value.asIntArray()))
    }
    override fun set(value: Array<UInt>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this[i] = value[i]
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