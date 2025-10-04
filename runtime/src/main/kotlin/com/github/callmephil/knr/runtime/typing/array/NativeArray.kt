package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC

abstract class NativeArray<T, C> internal constructor(
    arc: ARC?,
    private val onArcUpdated: (() -> Unit)?,
    val typeByteSize: Int
) {
    var arc: ARC? = arc
        internal set(value) {
            field = value
            calculateSize()
            onArcUpdated?.invoke()
        }

    var size: Int = 0
        private set

    init {
        calculateSize()
    }

    private fun calculateSize() {
        size = ((arc?.byteSize ?: 0) / typeByteSize).toInt()
    }

    protected fun checkBounds(target: Int) {
        if (size < target)
            throw IndexOutOfBoundsException()
    }

    abstract operator fun get(index: Int): T
    abstract fun get(): C
    abstract fun getArray(): Array<T>
    fun getByteArray(): ByteArray = arc!!.memorySegment!!.asByteBuffer().array()

    abstract operator fun set(index: Int, value: T)
    abstract fun set(value: C)
    abstract fun set(value: Array<T>)
    fun setByteArray(value: ByteArray) {
        checkBounds(value.size)
        arc!!.setBytes(value)
    }
}
