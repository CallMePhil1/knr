package knr.runtime.typing

import knr.runtime.delegates.*
import knr.runtime.delegates.union.ArrayFieldDelegate
import knr.runtime.delegates.union.PointerFieldDelegate
import knr.runtime.delegates.union.StructFieldDelegate
import knr.runtime.delegates.union.UnionFieldDelegate
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.array.*
import knr.runtime.typing.flags.BitFlag
import knr.runtime.typing.pointer.*
import java.lang.foreign.Arena
import java.lang.foreign.UnionLayout
import java.lang.foreign.ValueLayout
import java.nio.charset.Charset

abstract class Union(
    memory: Memory
) : Native<Union>(memory) {

    override fun copyTo(native: Union) {
        this.memory.copyTo(native.memory)
    }

    protected fun booleanField() = BooleanDelegate(this, 0)

    protected fun byteField() = ByteDelegate(this, 0)
    protected fun ubyteField() = UByteDelegate(this, 0)

    protected fun shortField() = ShortDelegate(this, 0)
    protected fun ushortField() = UShortDelegate(this, 0)

    protected fun intField() = IntDelegate(this, 0)
    protected fun uintField() = UIntDelegate(this, 0)

    protected fun longField() = LongDelegate(this, 0)
    protected fun ulongField() = ULongDelegate(this, 0)

    protected fun floatField() = FloatDelegate(this, 0)
    protected fun doubleField() = DoubleDelegate(this, 0)

    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<Byte, E>) where E : Enum<E>, E : NativeEnum<Byte> = ByteNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<UByte, E>) where E : Enum<E>, E : NativeEnum<UByte> = UByteNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<Short, E>) where E : Enum<E>, E : NativeEnum<Short> = ShortNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<UShort, E>) where E : Enum<E>, E : NativeEnum<UShort> = UShortNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<Int, E>) where E : Enum<E>, E : NativeEnum<Int> = IntNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<UInt, E>) where E : Enum<E>, E : NativeEnum<UInt> = UIntNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<Long, E>) where E : Enum<E>, E : NativeEnum<Long> = LongNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<ULong, E>) where E : Enum<E>, E : NativeEnum<ULong> = ULongNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<Float, E>) where E : Enum<E>, E : NativeEnum<Float> = FloatNativeEnumDelegate(this, 0, enumCompanion.entriesMap)
    protected fun <E> enumField(enumCompanion: NativeEnum.Companion<Double, E>) where E : Enum<E>, E : NativeEnum<Double> = DoubleNativeEnumDelegate(this, 0, enumCompanion.entriesMap)

    protected fun <F> byteFlagField() where F : Enum<F>, F : BitFlag<Byte> = ByteBitFlagSetDelegate<F>(this, 0)
    protected fun <F> ubyteFlagField() where F : Enum<F>, F : BitFlag<UByte> = UByteBitFlagSetDelegate<F>(this, 0)
    protected fun <F> shortFlagField() where F : Enum<F>, F : BitFlag<Short> = ShortBitFlagSetDelegate<F>(this, 0)
    protected fun <F> ushortFlagField() where F : Enum<F>, F : BitFlag<UShort> = UShortBitFlagSetDelegate<F>(this, 0)
    protected fun <F> intFlagField() where F : Enum<F>, F : BitFlag<Int> = IntBitFlagSetDelegate<F>(this, 0)
    protected fun <F> uintFlagField() where F : Enum<F>, F : BitFlag<UInt> = UIntBitFlagSetDelegate<F>(this, 0)
    protected fun <F> longFlagField() where F : Enum<F>, F : BitFlag<Long> = LongBitFlagSetDelegate<F>(this, 0)
    protected fun <F> ulongFlagField() where F : Enum<F>, F : BitFlag<ULong> = ULongBitFlagSetDelegate<F>(this, 0)

    protected fun <T: Struct<T>> structField(structCompanion: StructCompanion<T>) = StructFieldDelegate(this, structCompanion)
    protected fun <T: Union> unionField(unionCompanion: UnionCompanion<T>) = UnionFieldDelegate(this, unionCompanion)

    protected fun <T, C, A: NativeArray<T, C>> nativeArrayField(initialValue: A) = ArrayFieldDelegate(this, initialValue)

    protected fun byteArrayField(size: Long) = nativeArrayField(byteNativeArray(memory.asSlice(0, size)))
    protected fun ubyteArrayField(size: Long) = nativeArrayField(ubyteNativeArray(memory.asSlice(0, size)))
    protected fun shortArrayField(size: Long) = nativeArrayField(shortNativeArray(memory.asSlice(0, size * Short.SIZE_BYTES)))
    protected fun ushortArrayField(size: Long) = nativeArrayField(ushortNativeArray(memory.asSlice(0, size * UShort.SIZE_BYTES)))
    protected fun intArrayField(size: Long) = nativeArrayField(intNativeArray(memory.asSlice(0, size * Int.SIZE_BYTES)))
    protected fun uintArrayField(size: Long) = nativeArrayField(uintNativeArray(memory.asSlice(0, size * UInt.SIZE_BYTES)))
    protected fun longArrayField(size: Long) = nativeArrayField(longNativeArray(memory.asSlice(0, size * Long.SIZE_BYTES)))
    protected fun ulongArrayField(size: Long) = nativeArrayField(ulongNativeArray(memory.asSlice(0, size * ULong.SIZE_BYTES)))
    protected fun floatArrayField(size: Long) = nativeArrayField(floatNativeArray(memory.asSlice(0, size * Float.SIZE_BYTES)))
    protected fun doubleArrayField(size: Long) = nativeArrayField(doubleNativeArray(memory.asSlice(0, size * Double.SIZE_BYTES)))
    protected fun ccharArrayField(size: Long, charset: Charset) = nativeArrayField(ccharNativeArray(memory.asSlice(0, size), charset))
    protected fun cachedCCharArrayField(size: Long, charset: Charset, initialValue: String?): ArrayFieldDelegate<Byte, ByteArray, CachedCCharNativeArray> {
        val charArray = when (initialValue) {
            null -> cachedCCharNativeArray(memory.asSlice(0, size), charset, "").apply {
                updateCache()
            }
            else -> cachedCCharNativeArray(memory.asSlice(0, size), charset, initialValue)
        }
        return nativeArrayField(charArray)
    }
    protected inline fun <reified S: Struct<S>> structArrayField(size: Int, structCompanion: StructCompanion<S>) =
        structNativeArray(memory.asSlice(0, size * structCompanion.definition.byteSize), size, structCompanion)

    protected fun <T, P : Pointer<T>?> pointerField(byteSize: Long, ctor: (Memory) -> P): PointerFieldDelegate<T, P?> =
        PointerFieldDelegate(this, byteSize, ctor)

    protected fun nullableOpaquePointer() = pointerField(0, ::OpaquePointer)

    protected fun nullableBytePointerField() = pointerField(ValueLayout.JAVA_BYTE.byteSize(), ::BytePointer)
    protected fun nullableUBytePointerField() = pointerField(ValueLayout.JAVA_BYTE.byteSize(), ::UBytePointer)

    protected fun nullableShortPointerField() = pointerField(ValueLayout.JAVA_SHORT.byteSize(), ::ShortPointer)
    protected fun nullableUShortPointerField() = pointerField(ValueLayout.JAVA_SHORT.byteSize(), ::UShortPointer)

    protected fun nullableIntPointerField() = pointerField(ValueLayout.JAVA_INT.byteSize(), ::IntPointer)
    protected fun nullableUIntPointerField() = pointerField(ValueLayout.JAVA_INT.byteSize(), ::UIntPointer)

    protected fun nullableLongPointerField() = pointerField(ValueLayout.JAVA_LONG.byteSize(), ::LongPointer)
    protected fun nullableULongPointerField() = pointerField(ValueLayout.JAVA_LONG.byteSize(), ::ULongPointer)
}

interface UnionCompanion<T : Union> {
    val layout: UnionLayout

    fun allocate(): T {
        val arena = Arena.ofShared()
        val segment = arena.allocate(layout)
        val memory = ArenaMemory(arena, segment)
        return wrap(memory)
    }

    fun wrap(memory: Memory): T
}
