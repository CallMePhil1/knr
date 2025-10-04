package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class IntNativeArray(
    arc: ARC
) : NativeArray<Int, IntArray>(arc, Int.SIZE_BYTES) {

    override operator fun get(index: Int): Int {
        checkBounds(index)
        return arc.getInt(index.toLong() * typeByteSize)
    }
    override fun get() = IntArray(size) { this[it] }
    override fun getArray() = Array(size) { this[it] }

    override operator fun set(index: Int, value: Int) {
        checkBounds(index)
        arc.setInt(index.toLong() * typeByteSize, value)
    }
    override fun set(value: IntArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Int>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this[i] = value[i]
        }
    }
}

fun intNativeArray(arc: ARC) = IntNativeArray(arc)
fun intNativeArray(size: Long) = intNativeArray(ARC.shared(size * Int.SIZE_BYTES))
fun intNativeArray(array: IntArray): IntNativeArray {
    val arc = ARC.shared((array.size * Int.SIZE_BYTES).toLong())
    arc.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return intNativeArray(arc)
}