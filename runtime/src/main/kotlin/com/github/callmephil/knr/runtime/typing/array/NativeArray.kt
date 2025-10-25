package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.memory.Native
import java.lang.foreign.ValueLayout

abstract class NativeArray<T, C> internal constructor(
    memory: Memory,
    val typeByteSize: Int
) : Native(memory), Iterable<T> {

    val size: Int = (memory.byteSize / typeByteSize).toInt()

    protected fun checkBounds(target: Int) {
        if (size <= target)
            throw IndexOutOfBoundsException()
    }

    abstract fun getUnchecked(index: Int): T
    abstract operator fun get(index: Int): T
    abstract fun get(): C

    abstract fun setUnchecked(index: Int, value: T)
    abstract operator fun set(index: Int, value: T)
    abstract fun set(value: C)
    abstract fun set(value: Array<T>)
    fun setByteArray(value: ByteArray) {
        checkBounds(value.size - 1)
        memory.setBytes(value)
    }

    fun toByteArray(): ByteArray = memory.memorySegment!!.toArray(ValueLayout.JAVA_BYTE)

    override operator fun iterator(): Iterator<T> = NativeArrayIterator(this)

    class NativeArrayIterator<T> internal constructor(
        private val nativeArray: NativeArray<T, *>
    ) : Iterator<T> {
        private var _index = -1

        override fun next(): T {
            _index += 1
            return nativeArray[_index]
        }

        override fun hasNext() = _index >= nativeArray.size - 1
    }
}

inline fun <reified T> NativeArray<T, *>.toTypedArray(): Array<T> = Array(this.size) { this[it] }
