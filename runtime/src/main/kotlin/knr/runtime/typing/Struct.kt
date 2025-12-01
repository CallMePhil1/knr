package knr.runtime.typing

import knr.runtime.delegates.*
import knr.runtime.delegates.struct.ArrayFieldDelegate
import knr.runtime.delegates.struct.PointerFieldDelegate
import knr.runtime.delegates.struct.StructFieldDelegate
import knr.runtime.delegates.struct.UnionFieldDelegate
import knr.runtime.layout.StructDefinition
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.array.*
import knr.runtime.typing.flags.BitFlag
import knr.runtime.typing.pointer.*
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

    fun asPointer(): NativePointer<T> = nativePointerOf(this as T)

    protected fun currentOffset(): Long = definition.offset(offsetIndex)
    protected fun nextOffset(): Long = definition.offset(offsetIndex++)

    protected fun booleanField(offset: Long = nextOffset()) = BooleanDelegate(this, offset)

    protected fun byteField(offset: Long = nextOffset()) = ByteDelegate(this, offset)
    protected fun ubyteField(offset: Long = nextOffset()) = UByteDelegate(this, offset)

    protected fun shortField(offset: Long = nextOffset()) = ShortDelegate(this, offset)
    protected fun ushortField(offset: Long = nextOffset()) = UShortDelegate(this, offset)

    protected fun intField(offset: Long = nextOffset()) = IntDelegate(this, offset)
    protected fun uintField(offset: Long = nextOffset()) = UIntDelegate(this, offset)

    protected fun longField(offset: Long = nextOffset()) = LongDelegate(this, offset)
    protected fun ulongField(offset: Long = nextOffset()) = ULongDelegate(this, offset)

    protected fun floatField(offset: Long = nextOffset()) = FloatDelegate(this, offset)
    protected fun doubleField(offset: Long = nextOffset()) = DoubleDelegate(this, offset)

    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<Byte, E>) where E : Enum<E>, E : NativeEnum<Byte> = ByteNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<UByte, E>) where E : Enum<E>, E : NativeEnum<UByte> = UByteNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<Short, E>) where E : Enum<E>, E : NativeEnum<Short> = ShortNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<UShort, E>) where E : Enum<E>, E : NativeEnum<UShort> = UShortNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<Int, E>) where E : Enum<E>, E : NativeEnum<Int> = IntNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<UInt, E>) where E : Enum<E>, E : NativeEnum<UInt> = UIntNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<Long, E>) where E : Enum<E>, E : NativeEnum<Long> = LongNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<ULong, E>) where E : Enum<E>, E : NativeEnum<ULong> = ULongNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<Float, E>) where E : Enum<E>, E : NativeEnum<Float> = FloatNativeEnumDelegate(this, offset, enumCompanion.entriesMap)
    protected fun <E> enumField(offset: Long = nextOffset(), enumCompanion: NativeEnum.Companion<Double, E>) where E : Enum<E>, E : NativeEnum<Double> = DoubleNativeEnumDelegate(this, offset, enumCompanion.entriesMap)

    protected fun <F> byteFlagField(offset: Long) where F : Enum<F>, F : BitFlag<Byte> = ByteBitFlagSetDelegate<F>(this, offset)
    protected fun <F> ubyteFlagField(offset: Long) where F : Enum<F>, F : BitFlag<UByte> = UByteBitFlagSetDelegate<F>(this, offset)
    protected fun <F> shortFlagField(offset: Long) where F : Enum<F>, F : BitFlag<Short> = ShortBitFlagSetDelegate<F>(this, offset)
    protected fun <F> ushortFlagField(offset: Long) where F : Enum<F>, F : BitFlag<UShort> = UShortBitFlagSetDelegate<F>(this, offset)
    protected fun <F> intFlagField(offset: Long) where F : Enum<F>, F : BitFlag<Int> = IntBitFlagSetDelegate<F>(this, offset)
    protected fun <F> uintFlagField(offset: Long) where F : Enum<F>, F : BitFlag<UInt> = UIntBitFlagSetDelegate<F>(this, offset)
    protected fun <F> longFlagField(offset: Long) where F : Enum<F>, F : BitFlag<Long> = LongBitFlagSetDelegate<F>(this, offset)
    protected fun <F> ulongFlagField(offset: Long) where F : Enum<F>, F : BitFlag<ULong> = ULongBitFlagSetDelegate<F>(this, offset)

    protected fun <S: Struct<S>> structField(offset: Long = nextOffset(), structCompanion: Companion<S>) = StructFieldDelegate(this, offset, structCompanion)
    protected fun <U: Union> unionField(offset: Long = nextOffset(), unionCompanion: Union.Companion<U>) = UnionFieldDelegate(this, offset, unionCompanion)

    protected fun <T, C, A: NativeArray<T, C>> nativeArrayField(offset: Long = nextOffset(), initialValue: A) = ArrayFieldDelegate(this, offset, initialValue)

    protected fun byteArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, byteNativeArray(memory.asSlice(offset, size)))
    protected fun ubyteArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, ubyteNativeArray(memory.asSlice(offset, size)))
    protected fun shortArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, shortNativeArray(memory.asSlice(offset, size * Short.SIZE_BYTES)))
    protected fun ushortArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, ushortNativeArray(memory.asSlice(offset, size * UShort.SIZE_BYTES)))
    protected fun intArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, intNativeArray(memory.asSlice(offset, size * Int.SIZE_BYTES)))
    protected fun uintArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, uintNativeArray(memory.asSlice(offset, size * UInt.SIZE_BYTES)))
    protected fun longArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, longNativeArray(memory.asSlice(offset, size * Long.SIZE_BYTES)))
    protected fun ulongArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, ulongNativeArray(memory.asSlice(offset, size * ULong.SIZE_BYTES)))
    protected fun floatArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, floatNativeArray(memory.asSlice(offset, size * Float.SIZE_BYTES)))
    protected fun doubleArrayField(offset: Long = nextOffset(), size: Long) = nativeArrayField(offset, doubleNativeArray(memory.asSlice(offset, size * Double.SIZE_BYTES)))
    protected fun ccharArrayField(offset: Long = nextOffset(), size: Long, charset: Charset) = nativeArrayField(offset, ccharNativeArray(memory.asSlice(offset, size), charset))
    protected fun cachedCCharArrayField(offset: Long = nextOffset(), size: Long, charset: Charset, initialValue: String?): ArrayFieldDelegate<Byte, ByteArray, CachedCCharNativeArray> {
        val charArray = when (initialValue) {
            null -> cachedCCharNativeArray(memory.asSlice(offset, size), charset, "").apply {
                updateCache()
            }
            else -> cachedCCharNativeArray(memory.asSlice(offset, size), charset, initialValue)
        }
        return nativeArrayField(offset, charArray)
    }
    protected inline fun <reified P: Pointer<*>?> pointerArrayField(offset: Long = nextOffset(), size: Long, ctor: (Int, Memory) -> P) =
        nativeArrayField(offset, pointerNativeArray(memory.asSlice(offset, size * ValueLayout.ADDRESS.byteSize()), ctor))

    protected inline fun <reified S: Struct<S>> structArrayField(offset: Long = nextOffset(), size: Int, structCompanion: Companion<S>) =
        nativeArrayField(offset, structNativeArray(memory.asSlice(offset, structCompanion.definition.byteSize * size), size, structCompanion))

    protected fun <P : Pointer<*>?> pointerField(offset: Long = nextOffset(), initialValue: P) = PointerFieldDelegate(this, offset, initialValue)

    protected fun opaquePointerField(offset: Long = nextOffset(), initialValue: OpaquePointer) = pointerField(offset, initialValue)
    protected fun nullableOpaquePointerField(offset: Long = nextOffset(), initialValue: OpaquePointer?) = pointerField(offset, initialValue)

    protected fun bytePointerField(offset: Long = nextOffset(), initialValue: BytePointer) = pointerField(offset, initialValue)
    protected fun nullableBytePointerField(offset: Long = nextOffset(), initialValue: BytePointer?) = pointerField(offset, initialValue)
    protected fun ubytePointerField(offset: Long = nextOffset(), initialValue: UBytePointer) = pointerField(offset, initialValue)
    protected fun nullableUBytePointerField(offset: Long = nextOffset(), initialValue: UBytePointer?) = pointerField(offset, initialValue)

    protected fun shortPointerField(offset: Long = nextOffset(), initialValue: ShortPointer) = pointerField(offset, initialValue)
    protected fun nullableShortPointerField(offset: Long = nextOffset(), initialValue: ShortPointer?) = pointerField(offset, initialValue)
    protected fun ushortPointerField(offset: Long = nextOffset(), initialValue: UShortPointer) = pointerField(offset, initialValue)
    protected fun nullableUShortPointerField(offset: Long = nextOffset(), initialValue: UShortPointer?) = pointerField(offset, initialValue)

    protected fun intPointerField(offset: Long = nextOffset(), initialValue: IntPointer) = pointerField(offset, initialValue)
    protected fun nullableIntPointerField(offset: Long = nextOffset(), initialValue: IntPointer?) = pointerField(offset, initialValue)
    protected fun uintPointerField(offset: Long = nextOffset(), initialValue: UIntPointer) = pointerField(offset, initialValue)
    protected fun nullableUIntPointerField(offset: Long = nextOffset(), initialValue: UIntPointer?) = pointerField(offset, initialValue)

    protected fun longPointerField(offset: Long = nextOffset(), initialValue: LongPointer) = pointerField(offset, initialValue)
    protected fun nullableLongPointerField(offset: Long = nextOffset(), initialValue: LongPointer?) = pointerField(offset, initialValue)
    protected fun ulongPointerField(offset: Long = nextOffset(), initialValue: ULongPointer) = pointerField(offset, initialValue)
    protected fun nullableULongPointerField(offset: Long = nextOffset(), initialValue: ULongPointer?) = pointerField(offset, initialValue)

    protected fun cstringField(offset: Long = nextOffset(), initialValue: CString) = pointerField(offset, initialValue)
    protected fun nullableCStringField(offset: Long = nextOffset(), initialValue: CString?) = pointerField(offset, initialValue)
    protected fun cachedCStringField(offset: Long = nextOffset(), initialValue: CachedCString) = pointerField(offset, initialValue)
    protected fun nullableCachedCStringField(offset: Long = nextOffset(), initialValue: CachedCString?) = pointerField(offset, initialValue)

    interface Companion<T : Struct<T>> {
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
}
