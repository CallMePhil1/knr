package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.delegates.*
import com.github.callmephil.knr.runtime.delegates.pointers.BytePointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.IntPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.LongPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableBytePointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableIntPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableLongPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableShortPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableUBytePointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableUIntPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableULongPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.NullableUShortPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.ShortPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.UBytePointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.UIntPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.ULongPointerDelegate
import com.github.callmephil.knr.runtime.delegates.pointers.UShortPointerDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.Arena
import java.lang.foreign.StructLayout
import java.nio.ByteBuffer

abstract class Struct(
    val arc: ARC
) {
    init {
        arc.incrementCount()
    }

    protected fun booleanField(offset: Long) = BooleanDelegate(arc, offset)

    protected fun byteField(offset: Long) = ByteDelegate(arc, offset)
    protected fun uByteField(offset: Long) = UByteDelegate(arc, offset)

    protected fun shortField(offset: Long) = ShortDelegate(arc, offset)
    protected fun uShortField(offset: Long) = UShortDelegate(arc, offset)

    protected fun intField(offset: Long) = IntDelegate(arc, offset)
    protected fun uIntField(offset: Long) = UIntDelegate(arc, offset)

    protected fun longField(offset: Long) = LongDelegate(arc, offset)
    protected fun uLongField(offset: Long) = ULongDelegate(arc, offset)

    protected fun floatField(offset: Long) = FloatDelegate(arc, offset)
    protected fun doubleField(offset: Long) = DoubleDelegate(arc, offset)

    protected fun bytePointerField(offset: Long, initialValue: Byte = 0) = BytePointerDelegate(arc, offset, initialValue)
    protected fun nullableBytePointerField(offset: Long, initialValue: Byte? = null) = NullableBytePointerDelegate(arc, offset, initialValue)
    protected fun ubytePointerField(offset: Long, initialValue: UByte = 0u) = UBytePointerDelegate(arc, offset, initialValue)
    protected fun nullableUBytePointerField(offset: Long, initialValue: UByte? = null) = NullableUBytePointerDelegate(arc, offset, initialValue)

    protected fun shortPointerField(offset: Long, initialValue: Short = 0) = ShortPointerDelegate(arc, offset, initialValue)
    protected fun nullableShortPointerField(offset: Long, initialValue: Short? = null) = NullableShortPointerDelegate(arc, offset, initialValue)
    protected fun ushortPointerField(offset: Long, initialValue: UShort = 0u) = UShortPointerDelegate(arc, offset, initialValue)
    protected fun nullableUShortPointerField(offset: Long, initialValue: UShort? = null) = NullableUShortPointerDelegate(arc, offset, initialValue)

    protected fun intPointerField(offset: Long, initialValue: Int = 0) = IntPointerDelegate(arc, offset, initialValue)
    protected fun nullableIntPointerField(offset: Long, initialValue: Int? = null) = NullableIntPointerDelegate(arc, offset, initialValue)
    protected fun uintPointerField(offset: Long, initialValue: UInt = 0u) = UIntPointerDelegate(arc, offset, initialValue)
    protected fun nullableUIntPointerField(offset: Long, initialValue: UInt? = null) = NullableUIntPointerDelegate(arc, offset, initialValue)

    protected fun longPointerField(offset: Long, initialValue: Long = 0) = LongPointerDelegate(arc, offset, initialValue)
    protected fun nullableLongPointerField(offset: Long, initialValue: Long? = null) = NullableLongPointerDelegate(arc, offset, initialValue)
    protected fun ulongPointerField(offset: Long, initialValue: ULong = 0u) = ULongPointerDelegate(arc, offset, initialValue)
    protected fun nullableULongPointerField(offset: Long, initialValue: ULong? = null) = NullableULongPointerDelegate(arc, offset, initialValue)

    fun asByteBuffer(): ByteBuffer = arc.memorySegment!!.asByteBuffer()

//    protected fun stringField(offset: Long, arena: Arena, charset: Charset = Charsets.UTF_8, initialValue: String = "") =
//        StringDelegate(memorySegment, offset, arena, charset, initialValue)
//    protected fun cachedStringField(offset: Long, arena: Arena, charset: Charset = Charsets.UTF_8, initialValue: String = "") =
//        CachedStringDelegate(memorySegment, offset, arena, charset, initialValue)
//    protected fun fixedLengthStringField(offset: Long, charset: Charset = Charsets.UTF_8, initialValue: String, length: Int) =
//        FixedLengthStringDelegate(memorySegment, offset, charset, initialValue, length)
//    protected fun cachedFixedLengthStringField(offset: Long, charset: Charset = Charsets.UTF_8, initialValue: String, length: Int) =
//        CachedFixedLengthStringDelegate(memorySegment, offset, charset, initialValue, length)
}

interface StructCompanion<T : Struct> {
    val layout: StructLayout

    fun allocate(arena: Arena): T {
        val segment = arena.allocate(layout)
        return wrap(ARC(arena, segment))
    }

    fun allocateAuto() = allocate(Arena.ofAuto())
    fun allocateConfined() = allocate(Arena.ofConfined())
    fun allocateGlobal() = allocate(Arena.global())
    fun allocateShared() = allocate(Arena.ofShared())

    fun wrap(arc: ARC): T
}
