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
import com.github.callmephil.knr.runtime.memory.Native
import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.LongPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableLongPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableUBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableUIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.UBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.UIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.ULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableLongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableUBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableUIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableULongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ubytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.uintPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ulongPointerOf
import java.lang.foreign.Arena
import java.lang.foreign.StructLayout
import java.nio.ByteBuffer
import java.nio.charset.Charset

abstract class Struct(
    arc: ARC
) : Native(arc) {
    
    init {
        arc.incrementCount()
    }

    protected fun booleanField(offset: Long, initialValue: Boolean = false) = BooleanDelegate(arc, offset).apply {
        if (!get())
            set(initialValue)
    }

    protected fun byteField(offset: Long, initialValue: Byte = 0) = ByteDelegate(arc, offset).apply {
        if (get() != 0.toByte())
            set(initialValue)
    }
    protected fun uByteField(offset: Long, initialValue: UByte = 0u) = UByteDelegate(arc, offset).apply {
        if (get() != 0u.toUByte())
            set(initialValue)
    }

    protected fun shortField(offset: Long, initialValue: Short = 0) = ShortDelegate(arc, offset).apply {
        if (get() != 0.toShort())
            set(initialValue)
    }
    protected fun uShortField(offset: Long, initialValue: UShort = 0u) = UShortDelegate(arc, offset).apply {
        if (get() != 0u.toUShort())
            set(initialValue)
    }

    protected fun intField(offset: Long, initialValue: Int = 0) = IntDelegate(arc, offset).apply {
        if (get() != 0)
            set(initialValue)
    }
    protected fun uIntField(offset: Long, initialValue: UInt = 0u) = UIntDelegate(arc, offset).apply {
        if (get() != 0u)
            set(initialValue)
    }

    protected fun longField(offset: Long, initialValue: Long = 0L) = LongDelegate(arc, offset).apply {
        if (get() != 0L)
            set(initialValue)
    }
    protected fun uLongField(offset: Long, initialValue: ULong = 0u) = ULongDelegate(arc, offset).apply {
        if (get() != 0u.toULong())
            set(initialValue)
    }

    protected fun floatField(offset: Long, initialValue: Float = 0.0f) = FloatDelegate(arc, offset).apply {
        if (get() != 0f)
            set(initialValue)
    }
    protected fun doubleField(offset: Long, initialValue: Double = 0.0) = DoubleDelegate(arc, offset).apply {
        if (get() != 0.0)
            set(initialValue)
    }

    protected fun bytePointerField(offset: Long, initialValue: () -> BytePointer = { bytePointerOf() }) = BytePointerDelegate(arc, offset, initialValue)
    protected fun nullableBytePointerField(offset: Long, initialValue: () -> NullableBytePointer = { nullableBytePointerOf() }) = NullableBytePointerDelegate(arc, offset, initialValue)
    protected fun ubytePointerField(offset: Long, initialValue: () -> UBytePointer = { ubytePointerOf() }) = UBytePointerDelegate(arc, offset, initialValue)
    protected fun nullableUBytePointerField(offset: Long, initialValue: () -> NullableUBytePointer = { nullableUBytePointerOf() }) = NullableUBytePointerDelegate(arc, offset, initialValue)

    protected fun shortPointerField(offset: Long, initialValue: Short = 0) = ShortPointerDelegate(arc, offset, initialValue)
    protected fun nullableShortPointerField(offset: Long, initialValue: Short? = null) = NullableShortPointerDelegate(arc, offset, initialValue)
    protected fun ushortPointerField(offset: Long, initialValue: UShort = 0u) = UShortPointerDelegate(arc, offset, initialValue)
    protected fun nullableUShortPointerField(offset: Long, initialValue: UShort? = null) = NullableUShortPointerDelegate(arc, offset, initialValue)

    protected fun intPointerField(offset: Long, initialValue: () -> IntPointer = { intPointerOf() }) = IntPointerDelegate(arc, offset, initialValue)
    protected fun nullableIntPointerField(offset: Long, initialValue: () -> NullableIntPointer = { nullableIntPointerOf() }) = NullableIntPointerDelegate(arc, offset, initialValue)
    protected fun uintPointerField(offset: Long, initialValue: () -> UIntPointer = { uintPointerOf() }) = UIntPointerDelegate(arc, offset, initialValue)
    protected fun nullableUIntPointerField(offset: Long, initialValue: () -> NullableUIntPointer = { nullableUIntPointerOf() }) = NullableUIntPointerDelegate(arc, offset, initialValue)

    protected fun longPointerField(offset: Long, initialValue: () -> LongPointer = { longPointerOf() }) = LongPointerDelegate(arc, offset, initialValue)
    protected fun nullableLongPointerField(offset: Long, initialValue: () -> NullableLongPointer = { nullableLongPointerOf() }) = NullableLongPointerDelegate(arc, offset, initialValue)
    protected fun ulongPointerField(offset: Long, initialValue: () -> ULongPointer = { ulongPointerOf() }) = ULongPointerDelegate(arc, offset, initialValue)
    protected fun nullableULongPointerField(offset: Long, initialValue: () -> NullableULongPointer = { nullableULongPointerOf() }) = NullableULongPointerDelegate(arc, offset, initialValue)

    protected fun cstringField(offset: Long, charset: Charset, initialValue: String = "") = StringDelegate(arc, offset, charset, initialValue)
    protected fun nullableCStringField(offset: Long, charset: Charset, initialValue: String? = null) = NullableStringDelegate(arc, offset, charset, initialValue)
    protected fun cachedCStringField(offset: Long, charset: Charset, initialValue: String = "") = CachedStringDelegate(arc, offset, charset, initialValue)
    protected fun nullableCachedCStringField(offset: Long, charset: Charset, initialValue: String? = null) = NullableCachedStringDelegate(arc, offset, charset, initialValue)

    fun asByteBuffer(): ByteBuffer = arc.memorySegment!!.asByteBuffer()
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
