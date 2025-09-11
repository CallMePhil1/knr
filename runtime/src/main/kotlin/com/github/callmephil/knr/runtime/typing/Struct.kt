package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.delegates.*
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import java.nio.charset.Charset

abstract class Struct(
    private val arena: Arena,
    val memorySegment: MemorySegment
) {
    protected fun booleanField(offset: Long) = BooleanDelegate(memorySegment, offset)

    protected fun byteField(offset: Long) = ByteDelegate(memorySegment, offset)
    protected fun uByteField(offset: Long) = UByteDelegate(memorySegment, offset)

    protected fun shortField(offset: Long) = ShortDelegate(memorySegment, offset)
    protected fun uShortField(offset: Long) = UShortDelegate(memorySegment, offset)

    protected fun intField(offset: Long) = IntDelegate(memorySegment, offset)
    protected fun uIntField(offset: Long) = UIntDelegate(memorySegment, offset)

    protected fun longField(offset: Long) = LongDelegate(memorySegment, offset)
    protected fun uLongField(offset: Long) = ULongDelegate(memorySegment, offset)

    protected fun floatField(offset: Long) = FloatDelegate(memorySegment, offset)
    protected fun doubleField(offset: Long) = DoubleDelegate(memorySegment, offset)

    protected fun intPointerField(offset: Long, initialValue: Int) = IntPointerDelegate(arena, memorySegment, offset, initialValue)
    protected fun nullableIntPointerField(offset: Long, initialValue: Int?) = NullableIntPointerDelegate(arena, memorySegment, offset, initialValue)

    protected fun stringField(offset: Long, arena: Arena, charset: Charset = Charsets.UTF_8, initialValue: String = "") =
        StringDelegate(memorySegment, offset, arena, charset, initialValue)
    protected fun cachedStringField(offset: Long, arena: Arena, charset: Charset = Charsets.UTF_8, initialValue: String = "") =
        CachedStringDelegate(memorySegment, offset, arena, charset, initialValue)
    protected fun fixedLengthStringField(offset: Long, charset: Charset = Charsets.UTF_8, initialValue: String, length: Int) =
        FixedLengthStringDelegate(memorySegment, offset, charset, initialValue, length)
    protected fun cachedFixedLengthStringField(offset: Long, charset: Charset = Charsets.UTF_8, initialValue: String, length: Int) =
        CachedFixedLengthStringDelegate(memorySegment, offset, charset, initialValue, length)

    fun createPointer() = memorySegment.get(ValueLayout.ADDRESS, 0)
}

interface StructCompanion<T : Struct> {
    val layout: StructLayout

    fun allocate(arena: Arena): T {
        val segment = arena.allocate(layout)
        return wrap(arena, segment)
    }
    fun wrap(arena: Arena, memorySegment: MemorySegment): T
}
