package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.memory.Native
import java.lang.foreign.ValueLayout

abstract class NativeArray<T, C> internal constructor(
    arc: ARC,
    val typeByteSize: Int
) : Native(arc) {

    val size: Int = (arc.byteSize / typeByteSize).toInt()

    protected fun checkBounds(target: Int) {
        if (size <= target)
            throw IndexOutOfBoundsException()
    }

    abstract operator fun get(index: Int): T
    abstract fun get(): C
    abstract fun getArray(): Array<T>
    fun getByteArray(): ByteArray = arc.memorySegment!!.toArray(ValueLayout.JAVA_BYTE)

    abstract operator fun set(index: Int, value: T)
    abstract fun set(value: C)
    abstract fun set(value: Array<T>)
    fun setByteArray(value: ByteArray) {
        checkBounds(value.size)
        arc.setBytes(value)
    }
}
