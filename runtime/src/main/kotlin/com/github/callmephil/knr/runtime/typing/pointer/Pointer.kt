package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.StructCompanion
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

abstract class Pointer<T, R> internal constructor(
    memorySegment: MemorySegment
) {
    var memorySegment: MemorySegment = memorySegment
        protected set

    abstract fun get(): T?
    abstract fun reference(ref: R)
    abstract fun set(value: T & Any)
}

abstract class PrimitivePointer<T> internal constructor(
    memorySegment: MemorySegment
) : Pointer<T, MemorySegment>(memorySegment)

// region Byte Pointers

fun nullableBytePointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableBytePointer(memorySegment)
fun nullableBytePointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableBytePointer(
    memorySegment.asSlice(offset,ValueLayout.JAVA_BYTE)
)
fun nullableBytePointerOf(value: Byte, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableBytePointer(arena.allocate(ValueLayout.JAVA_BYTE))
    pointer.set(value)
}
fun bytePointerOf(memorySegment: MemorySegment) = BytePointer(memorySegment)
fun bytePointerOf(memorySegment: MemorySegment, offset: Long) = BytePointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_BYTE)
)
fun bytePointerOf(value: Byte, arena: Arena = Arena.ofConfined()) {
    val pointer = BytePointer(arena.allocate(ValueLayout.JAVA_BYTE))
    pointer.set(value)
}

// endregion

// region UByte Pointers

fun nullableUBytePointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableUBytePointer(memorySegment)
fun nullableUBytePointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableUBytePointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_BYTE)
)
fun nullableUBytePointerOf(value: UByte, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableUBytePointer(arena.allocate(ValueLayout.JAVA_BYTE))
    pointer.set(value)
}
fun ubytePointerOf(memorySegment: MemorySegment) = UBytePointer(memorySegment)
fun ubytePointerOf(memorySegment: MemorySegment, offset: Long) = UBytePointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_BYTE)
)
fun ubytePointerOf(value: UByte, arena: Arena = Arena.ofConfined()) {
    val pointer = UBytePointer(arena.allocate(ValueLayout.JAVA_BYTE))
    pointer.set(value)
}

// endregion

// region Short Pointers

fun nullableShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableShortPointer(memorySegment)
fun nullableShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableShortPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
)
fun nullableShortPointerOf(value: Short, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
    pointer.set(value)
}
fun shortPointerOf(memorySegment: MemorySegment) = ShortPointer(memorySegment)
fun shortPointerOf(memorySegment: MemorySegment, offset: Long) = ShortPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
)
fun shortPointerOf(value: Short, arena: Arena = Arena.ofConfined()) {
    val pointer = ShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
    pointer.set(value)
}

// endregion

// region UShort Pointers

fun nullableUShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableUShortPointer(memorySegment)
fun nullableUShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableUShortPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
)
fun nullableUShortPointerOf(value: UShort, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableUShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
    pointer.set(value)
}
fun ushortPointerOf(memorySegment: MemorySegment) = UShortPointer(memorySegment)
fun ushortPointerOf(memorySegment: MemorySegment, offset: Long) = UShortPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
)
fun ushortPointerOf(value: UShort, arena: Arena = Arena.ofConfined()) {
    val pointer = UShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
    pointer.set(value)
}

// endregion

// region Int Pointers

fun nullableIntPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableIntPointer(memorySegment)
fun nullableIntPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableIntPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_INT)
)
fun nullableIntPointerOf(value: Int, arena: Arena = Arena.ofConfined()): NullableIntPointer {
    val pointer = NullableIntPointer(arena.allocate(ValueLayout.JAVA_INT))
    pointer.set(value)
    return pointer
}
fun intPointerOf(memorySegment: MemorySegment) = IntPointer(memorySegment)
fun intPointerOf(memorySegment: MemorySegment, offset: Long) = IntPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_INT)
)
fun intPointerOf(value: Int, arena: Arena = Arena.ofConfined()): IntPointer {
    val pointer = IntPointer(arena.allocate(ValueLayout.JAVA_INT))
    pointer.set(value)
    return pointer
}

// endregion

// region UInt Pointers

fun nullableUIntPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableUIntPointer(memorySegment)
fun nullableUIntPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableUIntPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_INT)
)
fun nullableUIntPointerOf(value: UInt, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableUIntPointer(arena.allocate(ValueLayout.JAVA_INT))
    pointer.set(value)
}
fun uintPointerOf(memorySegment: MemorySegment) = UIntPointer(memorySegment)
fun uintPointerOf(memorySegment: MemorySegment, offset: Long) = UIntPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_INT)
)
fun uintPointerOf(value: UInt, arena: Arena = Arena.ofConfined()) {
    val pointer = UIntPointer(arena.allocate(ValueLayout.JAVA_INT))
    pointer.set(value)
}

// endregion

// region Long Pointers

fun nullableLongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableLongPointer(memorySegment)
fun nullableLongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableLongPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
)
fun nullableLongPointerOf(value: Long, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableLongPointer(arena.allocate(ValueLayout.JAVA_LONG))
    pointer.set(value)
}
fun longPointerOf(memorySegment: MemorySegment) = LongPointer(memorySegment)
fun longPointerOf(memorySegment: MemorySegment, offset: Long) = LongPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
)
fun longPointerOf(value: Long, arena: Arena = Arena.ofConfined()) {
    val pointer = LongPointer(arena.allocate(ValueLayout.JAVA_LONG))
    pointer.set(value)
}

// endregion

// region ULong Pointers

fun nullableULongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableULongPointer(memorySegment)
fun nullableULongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableULongPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
)
fun nullableULongPointerOf(value: ULong, arena: Arena = Arena.ofConfined()) {
    val pointer = NullableULongPointer(arena.allocate(ValueLayout.JAVA_LONG))
    pointer.set(value)
}
fun ulongPointerOf(memorySegment: MemorySegment) = ULongPointer(memorySegment)
fun ulongPointerOf(memorySegment: MemorySegment, offset: Long) = ULongPointer(
    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
)
fun ulongPointerOf(value: ULong, arena: Arena = Arena.ofConfined()) {
    val pointer = ULongPointer(arena.allocate(ValueLayout.JAVA_LONG))
    pointer.set(value)
}

// endregion

// region Struct Pointers

fun <S : Struct> nullableStructPointerOf(struct: S?) = NullableStructPointer(struct)
fun <S : Struct> nullableStructPointerOf(companion: StructCompanion<S>, arena: Arena = Arena.ofConfined()): NullableStructPointer<S> {
    val struct = companion.allocate(arena)
    return NullableStructPointer(struct)
}
fun <S : Struct> structPointerOf(struct: S) = StructPointer(struct)
fun <S : Struct> structPointerOf(companion: StructCompanion<S>, arena: Arena = Arena.ofConfined()): StructPointer<S> {
    val struct = companion.allocate(arena)
    return StructPointer(struct)
}

// endregion