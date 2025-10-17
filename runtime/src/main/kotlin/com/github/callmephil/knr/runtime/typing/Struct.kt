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
import com.github.callmephil.knr.runtime.typing.pointer.ShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.UBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.UIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.ULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.UShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ubytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.uintPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ulongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ushortPointerOf
import java.lang.foreign.Arena
import java.lang.foreign.StructLayout
import java.nio.ByteBuffer
import java.nio.charset.Charset

abstract class Struct(
    arc: ARC
) : Native(arc) {

    private val _verifyFuncs = mutableListOf<() -> Unit>()
    private val _disposeFuncs = mutableListOf<() -> Unit>()
    
    init {
        arc.incrementCount()
    }

    private fun addPointerDelegate(delegate: PointerFieldDelegate<*, *>) {
        _verifyFuncs.add {
            if (delegate.isNotValid) {
                val structName = this::class.java.simpleName
                val delegateType = delegate::class.java.simpleName
                throw IllegalStateException("Tried to use struct '$structName' but it contained an invalid '$delegateType'.")
            }
        }
        _disposeFuncs.add { delegate.get().dispose() }
    }

    private fun addPointerDelegate(delegate: NullablePointerFieldDelegate<*, *>) {
        _verifyFuncs.add {
            if (delegate.isNotValid) {
                val structName = this::class.java.simpleName
                val delegateType = delegate::class.java.simpleName
                throw IllegalStateException("Tried to use struct '$structName' but it contained an invalid '$delegateType'.")
            }
        }
        _disposeFuncs.add { delegate.get()?.dispose() }
    }

    override fun dispose() {
        super.dispose()
        _disposeFuncs.forEach { it() }
    }

    fun verifyIsValid() {
        if (isNotValid) {
            val structName = this::class.java.simpleName
            throw IllegalStateException("Tried to use struct '$structName' but it's been disposed")
        }
        _verifyFuncs.forEach {
            it()
        }
    }

    protected fun booleanField(offset: Long, initialValue: Boolean = false) = BooleanDelegate(this, offset).apply {
        if (!get())
            set(initialValue)
    }

    protected fun byteField(offset: Long, initialValue: Byte = 0) = ByteDelegate(this, offset).apply {
        if (get() != 0.toByte())
            set(initialValue)
    }
    protected fun uByteField(offset: Long, initialValue: UByte = 0u) = UByteDelegate(this, offset).apply {
        if (get() != 0u.toUByte())
            set(initialValue)
    }

    protected fun shortField(offset: Long, initialValue: Short = 0) = ShortDelegate(this, offset).apply {
        if (get() != 0.toShort())
            set(initialValue)
    }
    protected fun uShortField(offset: Long, initialValue: UShort = 0u) = UShortDelegate(this, offset).apply {
        if (get() != 0u.toUShort())
            set(initialValue)
    }

    protected fun intField(offset: Long, initialValue: Int = 0) = IntDelegate(this, offset).apply {
        if (get() != 0)
            set(initialValue)
    }
    protected fun uIntField(offset: Long, initialValue: UInt = 0u) = UIntDelegate(this, offset).apply {
        if (get() != 0u)
            set(initialValue)
    }

    protected fun longField(offset: Long, initialValue: Long = 0L) = LongDelegate(this, offset).apply {
        if (get() != 0L)
            set(initialValue)
    }
    protected fun uLongField(offset: Long, initialValue: ULong = 0u) = ULongDelegate(this, offset).apply {
        if (get() != 0u.toULong())
            set(initialValue)
    }

    protected fun floatField(offset: Long, initialValue: Float = 0.0f) = FloatDelegate(this, offset).apply {
        if (get() != 0f)
            set(initialValue)
    }
    protected fun doubleField(offset: Long, initialValue: Double = 0.0) = DoubleDelegate(this, offset).apply {
        if (get() != 0.0)
            set(initialValue)
    }

    protected fun bytePointerField(offset: Long, initialValue: () -> BytePointer = { bytePointerOf() }) = BytePointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableBytePointerField(offset: Long, initialValue: () -> BytePointer? = { null }) = NullableBytePointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun ubytePointerField(offset: Long, initialValue: () -> UBytePointer = { ubytePointerOf() }) = UBytePointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableUBytePointerField(offset: Long, initialValue: () -> UBytePointer? = { null }) = NullableUBytePointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }

    protected fun shortPointerField(offset: Long, initialValue: () -> ShortPointer = { shortPointerOf() }) = ShortPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableShortPointerField(offset: Long, initialValue: () -> ShortPointer? = { null }) = NullableShortPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun ushortPointerField(offset: Long, initialValue: () -> UShortPointer = { ushortPointerOf() }) = UShortPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableUShortPointerField(offset: Long, initialValue: () -> UShortPointer? = { null }) = NullableUShortPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }

    protected fun intPointerField(offset: Long, initialValue: () -> IntPointer = { intPointerOf() }) = IntPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableIntPointerField(offset: Long, initialValue: () -> IntPointer? = { null }) = NullableIntPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun uintPointerField(offset: Long, initialValue: () -> UIntPointer = { uintPointerOf() }) = UIntPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableUIntPointerField(offset: Long, initialValue: () -> UIntPointer? = { null }) = NullableUIntPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }

    protected fun longPointerField(offset: Long, initialValue: () -> LongPointer = { longPointerOf() }) = LongPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableLongPointerField(offset: Long, initialValue: () -> LongPointer? = { null }) = NullableLongPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun ulongPointerField(offset: Long, initialValue: () -> ULongPointer = { ulongPointerOf() }) = ULongPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableULongPointerField(offset: Long, initialValue: () -> ULongPointer? = { null }) = NullableULongPointerDelegate(this, offset, initialValue).apply {
        addPointerDelegate(this)
    }

    protected fun cstringField(offset: Long, charset: Charset, initialValue: String = "") = StringDelegate(this, offset, charset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableCStringField(offset: Long, charset: Charset, initialValue: String? = null) = NullableStringDelegate(this, offset, charset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun cachedCStringField(offset: Long, charset: Charset, initialValue: String = "") = CachedStringDelegate(this, offset, charset, initialValue).apply {
        addPointerDelegate(this)
    }
    protected fun nullableCachedCStringField(offset: Long, charset: Charset, initialValue: String? = null) = NullableCachedStringDelegate(this, offset, charset, initialValue).apply {
        addPointerDelegate(this)
    }

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
