package com.github.callmephil.knr.runtime.memory

import com.github.callmephil.knr.runtime.exceptions.ARCIsDisposedException
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

open class ARC(
    arena: Arena?,
    memorySegment: MemorySegment
) {
    var arena: Arena? = arena
        private set
    var memorySegment: MemorySegment? = memorySegment
        private set

    var counter: Int = 0
        protected set

    var isDisposed: Boolean = false

    open fun decrementCount() {
        counter -= 1

        if (counter <= 0)
            dispose()
    }

    open fun incrementCount() {
        if (isDisposed) {
            throw ARCIsDisposedException()
        }
        counter += 1
    }

    val isNull get() = memorySegment == MemorySegment.NULL

    fun dispose() {
        setToNull()
        isDisposed = true
    }

    fun setToNull() {
        try {
            arena?.close()
        } catch (_: UnsupportedOperationException) {
            /* no-op */
        } finally {
            arena = null
            memorySegment = null
        }
    }

    fun getAddress(offset: Long): MemorySegment = memorySegment!!.get(ValueLayout.ADDRESS, offset)
    fun getBoolean(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_BOOLEAN, offset)
    fun getByte(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_BYTE, offset)
    fun getShort(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_SHORT, offset)
    fun getInt(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_INT, offset)
    fun getLong(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_LONG, offset)
    fun getFloat(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_FLOAT, offset)
    fun getDouble(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_DOUBLE, offset)

    fun setAddress(offset: Long, value: MemorySegment) = memorySegment!!.set(ValueLayout.ADDRESS, offset, value)
    fun setBoolean(offset: Long, value: Boolean) = memorySegment!!.set(ValueLayout.JAVA_BOOLEAN, offset, value)
    fun setByte(offset: Long, value: Byte) = memorySegment!!.set(ValueLayout.JAVA_BYTE, offset, value)
    fun setShort(offset: Long, value: Short) = memorySegment!!.set(ValueLayout.JAVA_SHORT, offset, value)
    fun setInt(offset: Long, value: Int) = memorySegment!!.set(ValueLayout.JAVA_INT, offset, value)
    fun setLong(offset: Long, value: Long) = memorySegment!!.set(ValueLayout.JAVA_LONG, offset, value)
    fun setFloat(offset: Long, value: Float) = memorySegment!!.set(ValueLayout.JAVA_FLOAT, offset, value)
    fun setDouble(offset: Long, value: Double) = memorySegment!!.set(ValueLayout.JAVA_DOUBLE, offset, value)

    companion object {
        fun auto(layout: MemoryLayout): ARC {
            val arena = Arena.ofAuto()
            val memorySegment = arena.allocate(layout)
            return ARC(arena, memorySegment)
        }
        fun auto(byteSize: Long): ARC {
            val arena = Arena.ofAuto()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun confined(layout: MemoryLayout): ARC {
            val arena = Arena.ofConfined()
            val memorySegment = arena.allocate(layout)
            return ARC(arena, memorySegment)
        }
        fun confined(byteSize: Long): ARC {
            val arena = Arena.ofConfined()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun global(layout: MemoryLayout): ARC {
            val arena = Arena.global()
            val memorySegment = arena.allocate(layout)
            return ARC(arena, memorySegment)
        }
        fun global(byteSize: Long): ARC {
            val arena = Arena.global()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun ofNull(): ARC = ARC(null, MemorySegment.NULL)

        fun shared(layout: MemoryLayout): ARC {
            val arena = Arena.ofShared()
            val memorySegment = arena.allocate(layout)
            return ARC(arena, memorySegment)
        }
        fun shared(byteSize: Long): ARC {
            val arena = Arena.ofShared()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }
    }
}
