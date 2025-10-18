package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment

class LongNativeArray(
    arc: ARC
) : NativeArray<Long, LongArray>(arc, Long.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = arc.getLong(index.toLong() * typeByteSize)
    override operator fun get(index: Int): Long {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = LongArray(size) { getUnchecked(it) }

    override fun setUnchecked(index: Int, value: Long) = arc.setLong(index.toLong() * typeByteSize, value)
    override operator fun set(index: Int, value: Long) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: LongArray) {
        checkBounds(value.size)
        arc.memorySegment!!.copyFrom(MemorySegment.ofArray(value))
    }
    override fun set(value: Array<Long>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
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