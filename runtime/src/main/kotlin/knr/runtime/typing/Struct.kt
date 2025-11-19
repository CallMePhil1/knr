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
import knr.runtime.delegates.struct.ArrayFieldDelegate
import knr.runtime.delegates.struct.StructFieldDelegate
import knr.runtime.delegates.struct.UnionFieldDelegate
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.array.NativeArray
import knr.runtime.typing.array.byteNativeArray
import knr.runtime.typing.array.doubleNativeArray
import knr.runtime.typing.array.floatNativeArray
import knr.runtime.typing.array.intNativeArray
import knr.runtime.typing.array.longNativeArray
import knr.runtime.typing.array.pointerNativeArray
import knr.runtime.typing.array.shortNativeArray
import knr.runtime.typing.array.structNativeArray
import knr.runtime.typing.array.ubyteNativeArray
import knr.runtime.typing.array.uintNativeArray
import knr.runtime.typing.array.ulongNativeArray
import knr.runtime.typing.array.ushortNativeArray
import knr.runtime.typing.pointer.BytePointer
import knr.runtime.typing.pointer.IntPointer
import knr.runtime.typing.pointer.LongPointer
import knr.runtime.typing.pointer.OpaquePointer
import knr.runtime.typing.pointer.Pointer
import knr.runtime.typing.pointer.ShortPointer
import knr.runtime.typing.pointer.UBytePointer
import knr.runtime.typing.pointer.UIntPointer
import knr.runtime.typing.pointer.ULongPointer
import knr.runtime.typing.pointer.UShortPointer
import java.lang.foreign.Arena
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

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

    protected fun booleanField(offset: Long) = BooleanDelegate(this, offset)

    protected fun byteField(offset: Long) = ByteDelegate(this, offset)
    protected fun ubyteField(offset: Long) = UByteDelegate(this, offset)

    protected fun shortField(offset: Long) = ShortDelegate(this, offset)
    protected fun ushortField(offset: Long) = UShortDelegate(this, offset)

    protected fun intField(offset: Long) = IntDelegate(this, offset)
    protected fun uintField(offset: Long) = UIntDelegate(this, offset)

    protected fun longField(offset: Long) = LongDelegate(this, offset)
    protected fun ulongField(offset: Long) = ULongDelegate(this, offset)

    protected fun floatField(offset: Long) = FloatDelegate(this, offset)
    protected fun doubleField(offset: Long) = DoubleDelegate(this, offset)

    protected fun <S: Struct<S>> structField(offset: Long, structCompanion: StructCompanion<S>) = StructFieldDelegate(this, offset, structCompanion)
    protected fun <U: Union> unionField(offset: Long, unionCompanion: UnionCompanion<U>) = UnionFieldDelegate(this, offset, unionCompanion)

    protected fun <T, C, A: NativeArray<T, C>> nativeArrayField(offset: Long, initialValue: A): ArrayFieldDelegate<T, C, A> {
        val delegate = ArrayFieldDelegate(this, offset, initialValue)
        return delegate
    }

    protected fun byteArrayField(offset: Long, size: Long) = nativeArrayField(offset, byteNativeArray(memory.asSlice(offset, size)))
    protected fun ubyteArrayField(offset: Long, size: Long) = nativeArrayField(offset, ubyteNativeArray(memory.asSlice(offset, size)))
    protected fun shortArrayField(offset: Long, size: Long) = nativeArrayField(offset, shortNativeArray(memory.asSlice(offset, size * Short.SIZE_BYTES)))
    protected fun ushortArrayField(offset: Long, size: Long) = nativeArrayField(offset, ushortNativeArray(memory.asSlice(offset, size * UShort.SIZE_BYTES)))
    protected fun intArrayField(offset: Long, size: Long) = nativeArrayField(offset, intNativeArray(memory.asSlice(offset, size * Int.SIZE_BYTES)))
    protected fun uintArrayField(offset: Long, size: Long) = nativeArrayField(offset, uintNativeArray(memory.asSlice(offset, size * UInt.SIZE_BYTES)))
    protected fun longArrayField(offset: Long, size: Long) = nativeArrayField(offset, longNativeArray(memory.asSlice(offset, size * Long.SIZE_BYTES)))
    protected fun ulongArrayField(offset: Long, size: Long) = nativeArrayField(offset, ulongNativeArray(memory.asSlice(offset, size * ULong.SIZE_BYTES)))
    protected fun floatArrayField(offset: Long, size: Long) = nativeArrayField(offset, floatNativeArray(memory.asSlice(offset, size * Float.SIZE_BYTES)))
    protected fun doubleArrayField(offset: Long, size: Long) = nativeArrayField(offset, doubleNativeArray(memory.asSlice(offset, size * Double.SIZE_BYTES)))
    protected inline fun <reified P: Pointer<*>?> pointerArrayField(offset: Long, size: Long, ctor: (Int, Memory) -> P) =
        nativeArrayField(offset, pointerNativeArray(memory.asSlice(offset, size * ValueLayout.ADDRESS.byteSize()), ctor))

    protected inline fun <reified S: Struct<S>> structArrayField(offset: Long, size: Int, structCompanion: StructCompanion<S>) =
        nativeArrayField(offset, structNativeArray(memory.asSlice(offset, structCompanion.layout.byteSize() * size), size, structCompanion))

    protected fun <T, P : Pointer<T>?> pointerField(offset: Long, initialValue: P): PointerFieldDelegate<T, P> {
        val delegate = PointerFieldDelegate(this, offset, initialValue)
        addPointerDelegate(delegate)
        return delegate
    }

    protected fun opaquePointerField(offset: Long, initialValue: OpaquePointer) = pointerField(offset, initialValue)
    protected fun nullableOpaquePointerField(offset: Long, initialValue: OpaquePointer?) = pointerField(offset, initialValue)

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
