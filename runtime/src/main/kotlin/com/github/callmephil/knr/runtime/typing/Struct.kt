package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.delegates.*
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

    protected fun intPointerField(offset: Long, initialValue: Int) = IntPointerDelegate(arc, offset, initialValue)
    protected fun nullableIntPointerField(offset: Long, initialValue: Int?) = NullableIntPointerDelegate(arc, offset, initialValue)

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
