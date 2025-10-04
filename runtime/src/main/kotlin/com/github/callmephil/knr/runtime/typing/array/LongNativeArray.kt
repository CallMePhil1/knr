package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class LongNativeArray(
    arc: ARC
) : NativeArray<Long, LongArray>(arc, Long.SIZE_BYTES) {

    override operator fun get(index: Int): Long {
        checkBounds(index)
        return arc.getLong(index.toLong() * typeByteSize)
    }
    override fun get() = LongArray(size) { this[it] }
    override fun getArray() = Array(size) { this[it] }

    override operator fun set(index: Int, value: Long) {
        checkBounds(index)
        arc.setLong(index.toLong() * typeByteSize, value)
    }
    override fun set(value: LongArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Long>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this[i] = value[i]
        }
    }
}

fun longNativeArray(arc: ARC) = LongNativeArray(arc)
fun longNativeArray(size: Long) = longNativeArray(ARC.shared(size * Long.SIZE_BYTES))
fun longNativeArray(array: LongArray): LongNativeArray {
    val arc = ARC.shared((array.size * Long.SIZE_BYTES).toLong())
    arc.memorySegment!!.copyFrom(MemorySegment.ofArray(array))
    return longNativeArray(arc)
}