package com.github.callmephil.knr.runtime.memory

import com.github.callmephil.knr.runtime.exceptions.ARCIsDisposedException
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.ByteBuffer
import java.nio.charset.Charset

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
    val isNull get() = memorySegment == MemorySegment.NULL
    val byteSize get() = memorySegment?.byteSize() ?: 0

    open fun decrementCount() {
        counter -= 1

        if (counter <= 0)
            dispose()
    }

    private fun dispose() {
        try {
            arena?.close()
        } catch (_: UnsupportedOperationException) {
            /* no-op */
        } finally {
            arena = null
            memorySegment = null
            isDisposed = true
        }
    }

    open fun incrementCount() {
        if (isDisposed) {
            throw ARCIsDisposedException()
        }
        counter += 1
    }

    fun asByteBuffer(): ByteBuffer = memorySegment!!.asByteBuffer()

    fun getAddress(offset: Long): MemorySegment = memorySegment!!.get(ValueLayout.ADDRESS, offset)
    fun getBoolean(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_BOOLEAN, offset)
    fun getByte(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_BYTE, offset)
    fun getUByte(offset: Long) = getByte(offset).toUByte()
    fun getShort(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_SHORT, offset)
    fun getUShort(offset: Long) = getShort(offset).toUShort()
    fun getInt(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_INT, offset)
    fun getUInt(offset: Long) = getInt(offset).toUInt()
    fun getLong(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_LONG, offset)
    fun getULong(offset: Long) = getLong(offset).toULong()
    fun getFloat(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_FLOAT, offset)
    fun getDouble(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_DOUBLE, offset)
    fun getChar(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_CHAR, offset)
    fun getCharUnaligned(offset: Long) = memorySegment!!.get(ValueLayout.JAVA_CHAR_UNALIGNED, offset)
    fun getString(offset: Long, charset: Charset): String = memorySegment!!.getString(offset, charset)

    fun setAddress(offset: Long, value: MemorySegment) = memorySegment!!.set(ValueLayout.ADDRESS, offset, value)
    fun setBoolean(offset: Long, value: Boolean) = memorySegment!!.set(ValueLayout.JAVA_BOOLEAN, offset, value)
    fun setByte(offset: Long, value: Byte) = memorySegment!!.set(ValueLayout.JAVA_BYTE, offset, value)
    fun setUByte(offset: Long, value: UByte) = setByte(offset, value.toByte())
    fun setShort(offset: Long, value: Short) = memorySegment!!.set(ValueLayout.JAVA_SHORT, offset, value)
    fun setUShort(offset: Long, value: UShort) = setShort(offset, value.toShort())
    fun setInt(offset: Long, value: Int) = memorySegment!!.set(ValueLayout.JAVA_INT, offset, value)
    fun setUInt(offset: Long, value: UInt) = setInt(offset, value.toInt())
    fun setLong(offset: Long, value: Long) = memorySegment!!.set(ValueLayout.JAVA_LONG, offset, value)
    fun setULong(offset: Long, value: ULong) = setLong(offset, value.toLong())
    fun setFloat(offset: Long, value: Float) = memorySegment!!.set(ValueLayout.JAVA_FLOAT, offset, value)
    fun setDouble(offset: Long, value: Double) = memorySegment!!.set(ValueLayout.JAVA_DOUBLE, offset, value)
    fun setString(offset: Long, value: String, charset: Charset) = memorySegment!!.setString(offset, value, charset)
    fun setBytes(offset: Long, value: ByteArray, start: Long, end: Long) {
        val srcAmount = end - start
        val dstAmount = memorySegment!!.byteSize() - offset

        if (srcAmount > dstAmount)
            throw IndexOutOfBoundsException()

        val srcSegment = MemorySegment.ofArray(value).asSlice(start, srcAmount)
        val dstSegment = memorySegment!!.asSlice(offset)

        dstSegment.copyFrom(srcSegment)
    }
    fun setBytes(value: ByteArray) = setBytes(0, value, 0, value.size.toLong())

    companion object {
        fun auto(layout: MemoryLayout): ARC = auto(layout.byteSize())
        fun auto(byteSize: Long): ARC {
            val arena = Arena.ofAuto()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun confined(layout: MemoryLayout): ARC = confined(layout.byteSize())
        fun confined(byteSize: Long): ARC {
            val arena = Arena.ofConfined()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun global(layout: MemoryLayout): ARC = global(layout.byteSize())
        fun global(byteSize: Long): ARC {
            val arena = Arena.global()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun ofSegment(memorySegment: MemorySegment): ARC = ARC(null, memorySegment)

        fun shared(layout: MemoryLayout): ARC = shared(layout.byteSize())
        fun shared(byteSize: Long): ARC {
            val arena = Arena.ofShared()
            val memorySegment = arena.allocate(byteSize)
            return ARC(arena, memorySegment)
        }

        fun string(value: String, charset: Charset): ARC {
            val arena = Arena.ofShared()
            val memorySegment = arena.allocateFrom(value, charset)
            return ARC(arena, memorySegment)
        }
    }
}
