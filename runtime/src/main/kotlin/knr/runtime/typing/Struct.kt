package knr.runtime.typing

import knr.runtime.delegates.BooleanDelegate
import knr.runtime.delegates.ByteDelegate
import knr.runtime.delegates.DoubleDelegate
import knr.runtime.delegates.FloatDelegate
import knr.runtime.delegates.IntDelegate
import knr.runtime.delegates.LongDelegate
import knr.runtime.delegates.struct.PointerFieldDelegate
import knr.runtime.delegates.ShortDelegate
import knr.runtime.delegates.UByteDelegate
import knr.runtime.delegates.UIntDelegate
import knr.runtime.delegates.ULongDelegate
import knr.runtime.delegates.UShortDelegate
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.pointer.BytePointer
import knr.runtime.typing.pointer.IntPointer
import knr.runtime.typing.pointer.LongPointer
import knr.runtime.typing.pointer.Pointer
import knr.runtime.typing.pointer.ShortPointer
import knr.runtime.typing.pointer.UBytePointer
import knr.runtime.typing.pointer.UIntPointer
import knr.runtime.typing.pointer.ULongPointer
import knr.runtime.typing.pointer.UShortPointer
import java.lang.foreign.Arena
import java.lang.foreign.StructLayout

abstract class Struct<T : Struct<T>>(
    memory: Memory
) : Native<T>(memory) {

    private val _verifyFuncs = mutableListOf<() -> Unit>()

    private fun addPointerDelegate(delegate: PointerFieldDelegate<*, *>) {
        _verifyFuncs.add {
            if (delegate.get()?.isNotValid == true) {
                val structName = this::class.java.simpleName
                val delegateType = delegate::class.java.simpleName
                throw IllegalStateException("Tried to use struct '$structName' but it contained an invalid '$delegateType'.")
            }
        }
    }

    override fun verifyIsValid() {
        super.verifyIsValid()
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
    protected fun ubyteField(offset: Long, initialValue: UByte = 0u) = UByteDelegate(this, offset).apply {
        if (get() != 0u.toUByte())
            set(initialValue)
    }

    protected fun shortField(offset: Long, initialValue: Short = 0) = ShortDelegate(this, offset).apply {
        if (get() != 0.toShort())
            set(initialValue)
    }
    protected fun ushortField(offset: Long, initialValue: UShort = 0u) = UShortDelegate(this, offset).apply {
        if (get() != 0u.toUShort())
            set(initialValue)
    }

    protected fun intField(offset: Long, initialValue: Int = 0) = IntDelegate(this, offset).apply {
        if (get() != 0)
            set(initialValue)
    }
    protected fun uintField(offset: Long, initialValue: UInt = 0u) = UIntDelegate(this, offset).apply {
        if (get() != 0u)
            set(initialValue)
    }

    protected fun longField(offset: Long, initialValue: Long = 0L) = LongDelegate(this, offset).apply {
        if (get() != 0L)
            set(initialValue)
    }
    protected fun ulongField(offset: Long, initialValue: ULong = 0u) = ULongDelegate(this, offset).apply {
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

    protected fun <T, P : Pointer<T>?> pointerField(offset: Long, initialValue: P): PointerFieldDelegate<T, P> {
        val delegate = PointerFieldDelegate(this, offset, initialValue)
        addPointerDelegate(delegate)
        return delegate
    }

    protected fun bytePointerField(offset: Long, initialValue: BytePointer) = pointerField(offset, initialValue)
    protected fun nullableBytePointerField(offset: Long, initialValue: BytePointer?) = pointerField(offset, initialValue)
    protected fun ubytePointerField(offset: Long, initialValue: UBytePointer) = pointerField(offset, initialValue)
    protected fun nullableUBytePointerField(offset: Long, initialValue: UBytePointer?) = pointerField(offset, initialValue)

    protected fun shortPointerField(offset: Long, initialValue: ShortPointer) = pointerField(offset, initialValue)
    protected fun nullableShortPointerField(offset: Long, initialValue: ShortPointer?) = pointerField(offset, initialValue)
    protected fun ushortPointerField(offset: Long, initialValue: UShortPointer) = pointerField(offset, initialValue)
    protected fun nullableUShortPointerField(offset: Long, initialValue: UShortPointer?) = pointerField(offset, initialValue)

    protected fun intPointerField(offset: Long, initialValue: IntPointer) = pointerField(offset, initialValue)
    protected fun nullableIntPointerField(offset: Long, initialValue: IntPointer?) = pointerField(offset, initialValue)
    protected fun uintPointerField(offset: Long, initialValue: UIntPointer) = pointerField(offset, initialValue)
    protected fun nullableUIntPointerField(offset: Long, initialValue: UIntPointer?) = pointerField(offset, initialValue)

    protected fun longPointerField(offset: Long, initialValue: LongPointer) = pointerField(offset, initialValue)
    protected fun nullableLongPointerField(offset: Long, initialValue: LongPointer?) = pointerField(offset, initialValue)
    protected fun ulongPointerField(offset: Long, initialValue: ULongPointer) = pointerField(offset, initialValue)
    protected fun nullableULongPointerField(offset: Long, initialValue: ULongPointer?) = pointerField(offset, initialValue)

    protected fun cstringField(offset: Long, initialValue: CString) = pointerField(offset, initialValue)
    protected fun nullableCStringField(offset: Long, initialValue: CString?) = pointerField(offset, initialValue)
    protected fun cachedCStringField(offset: Long, initialValue: CachedCString) = pointerField(offset, initialValue)
    protected fun nullableCachedCStringField(offset: Long, initialValue: CachedCString?) = pointerField(offset, initialValue)
}

interface StructCompanion<T : Struct<T>> {
    val layout: StructLayout

    fun allocate(): T {
        val arena = Arena.ofShared()
        val segment = arena.allocate(layout)
        val memory = ArenaMemory(arena, segment)
        return wrap(memory)
    }

    fun allocate(init: T.() -> Unit): T {
        val newStruct = allocate()
        newStruct.init()
        return newStruct
    }

    fun wrap(memory: Memory): T
}
