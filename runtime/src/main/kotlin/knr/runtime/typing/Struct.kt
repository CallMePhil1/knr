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
import knr.runtime.layout.StructDefinition
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.array.CachedCCharNativeArray
import knr.runtime.typing.array.NativeArray
import knr.runtime.typing.array.byteNativeArray
import knr.runtime.typing.array.cachedCCharNativeArray
import knr.runtime.typing.array.ccharNativeArray
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
import java.lang.foreign.MemoryLayout
import java.lang.foreign.PaddingLayout
import java.lang.foreign.ValueLayout
import java.nio.charset.Charset

abstract class Struct<T : Struct<T>>(
    memory: Memory,
    private val definition: StructDefinition
) : Native<T>(memory) {

    private var offsetIndex = 0
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

    protected fun getOffset() = definition.offset(offsetIndex++)

    protected fun booleanField(offset: Long = getOffset()) = BooleanDelegate(this, offset)

    protected fun byteField(offset: Long = getOffset()) = ByteDelegate(this, offset)
    protected fun ubyteField(offset: Long = getOffset()) = UByteDelegate(this, offset)

    protected fun shortField(offset: Long = getOffset()) = ShortDelegate(this, offset)
    protected fun ushortField(offset: Long = getOffset()) = UShortDelegate(this, offset)

    protected fun intField(offset: Long = getOffset()) = IntDelegate(this, offset)
    protected fun uintField(offset: Long = getOffset()) = UIntDelegate(this, offset)

    protected fun longField(offset: Long = getOffset()) = LongDelegate(this, offset)
    protected fun ulongField(offset: Long = getOffset()) = ULongDelegate(this, offset)

    protected fun floatField(offset: Long = getOffset()) = FloatDelegate(this, offset)
    protected fun doubleField(offset: Long = getOffset()) = DoubleDelegate(this, offset)

    protected fun <S: Struct<S>> structField(offset: Long = getOffset(), structCompanion: StructCompanion<S>) = StructFieldDelegate(this, offset, structCompanion)
    protected fun <U: Union> unionField(offset: Long = getOffset(), unionCompanion: UnionCompanion<U>) = UnionFieldDelegate(this, offset, unionCompanion)

    protected fun <T, C, A: NativeArray<T, C>> nativeArrayField(offset: Long = getOffset(), initialValue: A): ArrayFieldDelegate<T, C, A> {
        val delegate = ArrayFieldDelegate(this, offset, initialValue)
        return delegate
    }

    protected fun byteArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, byteNativeArray(memory.asSlice(offset, size)))
    protected fun ubyteArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, ubyteNativeArray(memory.asSlice(offset, size)))
    protected fun shortArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, shortNativeArray(memory.asSlice(offset, size * Short.SIZE_BYTES)))
    protected fun ushortArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, ushortNativeArray(memory.asSlice(offset, size * UShort.SIZE_BYTES)))
    protected fun intArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, intNativeArray(memory.asSlice(offset, size * Int.SIZE_BYTES)))
    protected fun uintArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, uintNativeArray(memory.asSlice(offset, size * UInt.SIZE_BYTES)))
    protected fun longArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, longNativeArray(memory.asSlice(offset, size * Long.SIZE_BYTES)))
    protected fun ulongArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, ulongNativeArray(memory.asSlice(offset, size * ULong.SIZE_BYTES)))
    protected fun floatArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, floatNativeArray(memory.asSlice(offset, size * Float.SIZE_BYTES)))
    protected fun doubleArrayField(offset: Long = getOffset(), size: Long) = nativeArrayField(offset, doubleNativeArray(memory.asSlice(offset, size * Double.SIZE_BYTES)))
    protected fun ccharArrayField(offset: Long = getOffset(), size: Long, charset: Charset) = nativeArrayField(offset, ccharNativeArray(memory.asSlice(offset, size), charset))
    protected fun cachedCCharArrayField(offset: Long = getOffset(), size: Long, charset: Charset, initialValue: String?): ArrayFieldDelegate<Byte, ByteArray, CachedCCharNativeArray> {
        val charArray = when (initialValue) {
            null -> cachedCCharNativeArray(memory.asSlice(offset, size), charset, "").apply {
                updateCache()
            }
            else -> cachedCCharNativeArray(memory.asSlice(offset, size), charset, initialValue)
        }
        return nativeArrayField(offset, charArray)
    }
    protected inline fun <reified P: Pointer<*>?> pointerArrayField(offset: Long = getOffset(), size: Long, ctor: (Int, Memory) -> P) =
        nativeArrayField(offset, pointerNativeArray(memory.asSlice(offset, size * ValueLayout.ADDRESS.byteSize()), ctor))

    protected inline fun <reified S: Struct<S>> structArrayField(offset: Long = getOffset(), size: Int, structCompanion: StructCompanion<S>) =
        nativeArrayField(offset, structNativeArray(memory.asSlice(offset, structCompanion.definition.byteSize * size), size, structCompanion))

    protected fun <T, P : Pointer<T>?> pointerField(offset: Long = getOffset(), initialValue: P): PointerFieldDelegate<T, P> {
        val delegate = PointerFieldDelegate(this, offset, initialValue)
        addPointerDelegate(delegate)
        return delegate
    }

    protected fun opaquePointerField(offset: Long = getOffset(), initialValue: OpaquePointer) = pointerField(offset, initialValue)
    protected fun nullableOpaquePointerField(offset: Long = getOffset(), initialValue: OpaquePointer?) = pointerField(offset, initialValue)

    protected fun bytePointerField(offset: Long = getOffset(), initialValue: BytePointer) = pointerField(offset, initialValue)
    protected fun nullableBytePointerField(offset: Long = getOffset(), initialValue: BytePointer?) = pointerField(offset, initialValue)
    protected fun ubytePointerField(offset: Long = getOffset(), initialValue: UBytePointer) = pointerField(offset, initialValue)
    protected fun nullableUBytePointerField(offset: Long = getOffset(), initialValue: UBytePointer?) = pointerField(offset, initialValue)

    protected fun shortPointerField(offset: Long = getOffset(), initialValue: ShortPointer) = pointerField(offset, initialValue)
    protected fun nullableShortPointerField(offset: Long = getOffset(), initialValue: ShortPointer?) = pointerField(offset, initialValue)
    protected fun ushortPointerField(offset: Long = getOffset(), initialValue: UShortPointer) = pointerField(offset, initialValue)
    protected fun nullableUShortPointerField(offset: Long = getOffset(), initialValue: UShortPointer?) = pointerField(offset, initialValue)

    protected fun intPointerField(offset: Long = getOffset(), initialValue: IntPointer) = pointerField(offset, initialValue)
    protected fun nullableIntPointerField(offset: Long = getOffset(), initialValue: IntPointer?) = pointerField(offset, initialValue)
    protected fun uintPointerField(offset: Long = getOffset(), initialValue: UIntPointer) = pointerField(offset, initialValue)
    protected fun nullableUIntPointerField(offset: Long = getOffset(), initialValue: UIntPointer?) = pointerField(offset, initialValue)

    protected fun longPointerField(offset: Long = getOffset(), initialValue: LongPointer) = pointerField(offset, initialValue)
    protected fun nullableLongPointerField(offset: Long = getOffset(), initialValue: LongPointer?) = pointerField(offset, initialValue)
    protected fun ulongPointerField(offset: Long = getOffset(), initialValue: ULongPointer) = pointerField(offset, initialValue)
    protected fun nullableULongPointerField(offset: Long = getOffset(), initialValue: ULongPointer?) = pointerField(offset, initialValue)

    protected fun cstringField(offset: Long = getOffset(), initialValue: CString) = pointerField(offset, initialValue)
    protected fun nullableCStringField(offset: Long = getOffset(), initialValue: CString?) = pointerField(offset, initialValue)
    protected fun cachedCStringField(offset: Long = getOffset(), initialValue: CachedCString) = pointerField(offset, initialValue)
    protected fun nullableCachedCStringField(offset: Long = getOffset(), initialValue: CachedCString?) = pointerField(offset, initialValue)
}

interface StructCompanion<T : Struct<T>> {
    val definition: StructDefinition

    fun allocate(): T {
        val arena = Arena.ofShared()
        val segment = arena.allocate(definition.layout)
        val memory = ArenaMemory(arena, segment)
        return wrap(memory)
    }

    fun allocate(init: T.() -> Unit): T {
        val newStruct = allocate()
        newStruct.init()
        return newStruct
    }

    fun structDefinition(vararg elements: MemoryLayout): StructDefinition {
        val offsets = mutableListOf<Long>()

        val structLayouts = mutableListOf<MemoryLayout>()
        var totalSize = 0L

        for (element in elements) {
            if (element is PaddingLayout)
                error("Found Memory.paddingLayout in call to structDefinition for '${this::class.java.canonicalName}'")
            val byteSize = element.byteSize()
            val alignmentOffset = totalSize % element.byteAlignment()
            val padding = if (alignmentOffset != 0L) byteSize - alignmentOffset else 0

            if (padding > 0) {
                structLayouts.add(MemoryLayout.paddingLayout(padding))
                totalSize += padding
            }

            offsets.add(totalSize)
            structLayouts.add(element)
            totalSize += byteSize
        }

        val layout = MemoryLayout.structLayout(*structLayouts.toTypedArray())

        return StructDefinition(layout, offsets)
    }

    fun wrap(memory: Memory): T
}
