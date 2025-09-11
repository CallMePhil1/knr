package com.github.callmephil.knr.runtime.delegates

import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class BooleanDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Boolean>(memorySegment, offset) {
    override fun get(): Boolean = memorySegment.get(ValueLayout.JAVA_BOOLEAN, offset)
    override fun set(value: Boolean) {
        memorySegment.set(ValueLayout.JAVA_BOOLEAN, offset, value)
    }
}

class ByteDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Byte>(memorySegment, offset) {
    override fun get(): Byte = memorySegment.get(ValueLayout.JAVA_BYTE, offset)
    override fun set(value: Byte) = memorySegment.set(ValueLayout.JAVA_BYTE, offset, value)
}

class UByteDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<UByte>(memorySegment, offset) {
    override fun get(): UByte = memorySegment.get(ValueLayout.JAVA_BYTE, offset).toUByte()
    override fun set(value: UByte) = memorySegment.set(ValueLayout.JAVA_BYTE, offset, value.toByte())
}

class ShortDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Short>(memorySegment, offset) {
    override fun get(): Short = memorySegment.get(ValueLayout.JAVA_SHORT, offset)
    override fun set(value: Short) = memorySegment.set(ValueLayout.JAVA_SHORT, offset, value)
}

class UShortDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<UShort>(memorySegment, offset) {
    override fun get(): UShort = memorySegment.get(ValueLayout.JAVA_SHORT, offset).toUShort()
    override fun set(value: UShort) = memorySegment.set(ValueLayout.JAVA_SHORT, offset, value.toShort())
}

class IntDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Int>(memorySegment, offset) {
    override fun get(): Int = memorySegment.get(ValueLayout.JAVA_INT, offset)
    override fun set(value: Int) = memorySegment.set(ValueLayout.JAVA_INT, offset, value)
}

class UIntDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<UInt>(memorySegment, offset) {
    override fun get(): UInt = memorySegment.get(ValueLayout.JAVA_INT, offset).toUInt()
    override fun set(value: UInt) = memorySegment.set(ValueLayout.JAVA_INT, offset, value.toInt())
}

class LongDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Long>(memorySegment, offset) {
    override fun get(): Long = memorySegment.get(ValueLayout.JAVA_LONG, offset)
    override fun set(value: Long) = memorySegment.set(ValueLayout.JAVA_LONG, offset, value)
}

class ULongDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<ULong>(memorySegment, offset) {
    override fun get(): ULong = memorySegment.get(ValueLayout.JAVA_LONG, offset).toULong()
    override fun set(value: ULong) = memorySegment.set(ValueLayout.JAVA_LONG, offset, value.toLong())
}

class FloatDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Float>(memorySegment, offset) {
    override fun get(): Float = memorySegment.get(ValueLayout.JAVA_FLOAT, offset)
    override fun set(value: Float) = memorySegment.set(ValueLayout.JAVA_FLOAT, offset, value)
}

class DoubleDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<Double>(memorySegment, offset) {
    override fun get(): Double = memorySegment.get(ValueLayout.JAVA_DOUBLE, offset)
    override fun set(value: Double) = memorySegment.set(ValueLayout.JAVA_DOUBLE, offset, value)
}
