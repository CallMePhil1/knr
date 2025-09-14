package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

abstract class Pointer<T, R> internal constructor(
    arc: ARC?,
    protected val onArcUpdated: (() -> Unit)?
) : AutoCloseable {
    var arc: ARC? = arc
        protected set

    val isValid get() = arc != null
    val isNotValid get() = arc == null
    val refCount get() = arc?.counter ?: 0

    init {
        arc?.incrementCount()
    }

    override fun close() = dispose()

    fun dispose() {
        arc?.decrementCount()
        arc = null
    }

    fun giveTo(other: Pointer<T, R>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        other.onArcUpdated?.invoke()
        arc = null
    }

    fun pointTo(arc: ARC) {
        this.arc?.decrementCount()
        this.arc = arc
        onArcUpdated?.invoke()
    }

    fun setToNull() = pointTo(ARC.ofNull())

    fun shareWith(other: Pointer<T, R>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
        other.onArcUpdated?.invoke()
    }

    abstract fun get(): T
    abstract fun set(value: T & Any)
}

infix fun <T, R> Pointer<T, R>.giveTo(other: Pointer<T, R>) {
    this.giveTo(other)
}

infix fun <T, R> Pointer<T, R>.takeFrom(other: Pointer<T, R>) {
    other.giveTo(this)
}

infix fun <T, R> Pointer<T, R>.shareWith(other: Pointer<T, R>) {
    this.shareWith(other)
}

infix fun <T, R> Pointer<T, R>.shareFrom(other: Pointer<T, R>) {
    other.shareWith(this)
}

abstract class NullablePrimitivePointer<T> internal constructor(
    arc: ARC?,
    onArcUpdated: (() -> Unit)?
) : Pointer<T, ARC?>(arc, onArcUpdated) {

    val isNull: Boolean get() {
        validOrThrow(this)
        return arc!!.isNull
    }

    abstract fun shareOf(): NullablePrimitivePointer<T>

    inline fun ifIsNull(block: NullablePrimitivePointer<T>.() -> Unit): NullablePrimitivePointer<T> {
        if (isNull)
            block()
        return this
    }

    inline fun ifNotNull(block: NullablePrimitivePointer<T>.(T) -> Unit): NullablePrimitivePointer<T> {
        if (!isNull)
            block(get())
        return this
    }
}

abstract class PrimitivePointer<T> internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)?
) : Pointer<T, ARC>(arc, onArcUpdated) {
    abstract fun shareOf(): PrimitivePointer<T>
}

// region Byte Pointers

fun nullableBytePointerOf(arc: ARC = ARC.ofNull()) = NullableBytePointer(arc)
fun nullableBytePointerOf(value: Byte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) {
    val pointer = BytePointer(arc)
    pointer.set(value)
}
fun bytePointerOf(arc: ARC) = BytePointer(arc)
fun bytePointerOf(memorySegment: MemorySegment) = BytePointer(ARC(null, memorySegment))
fun bytePointerOf(arc: ARC, offset: Long) = BytePointer(
    ARC(
        null,
        arc.getAddress(offset).reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )
)
fun bytePointerOf(value: Byte, arc: ARC = ARC.shared(ValueLayout.JAVA_BYTE)) {
    val pointer = BytePointer(arc)
    pointer.set(value)
}

// endregion

// region UByte Pointers

//fun nullableUBytePointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableUBytePointer(memorySegment)
//fun nullableUBytePointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableUBytePointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_BYTE)
//)
//fun nullableUBytePointerOf(value: UByte, arena: Arena = Arena.ofConfined()) {
//    val pointer = NullableUBytePointer(arena.allocate(ValueLayout.JAVA_BYTE))
//    pointer.set(value)
//}
//fun ubytePointerOf(memorySegment: MemorySegment) = UBytePointer(memorySegment)
//fun ubytePointerOf(memorySegment: MemorySegment, offset: Long) = UBytePointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_BYTE)
//)
//fun ubytePointerOf(value: UByte, arena: Arena = Arena.ofConfined()) {
//    val pointer = UBytePointer(arena.allocate(ValueLayout.JAVA_BYTE))
//    pointer.set(value)
//}

// endregion

// region Short Pointers

//fun nullableShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableShortPointer(memorySegment)
//fun nullableShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableShortPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
//)
//fun nullableShortPointerOf(value: Short, arena: Arena = Arena.ofConfined()) {
//    val pointer = NullableShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
//    pointer.set(value)
//}
//fun shortPointerOf(memorySegment: MemorySegment) = ShortPointer(memorySegment)
//fun shortPointerOf(memorySegment: MemorySegment, offset: Long) = ShortPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
//)
//fun shortPointerOf(value: Short, arena: Arena = Arena.ofConfined()) {
//    val pointer = ShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
//    pointer.set(value)
//}

// endregion

// region UShort Pointers

//fun nullableUShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableUShortPointer(memorySegment)
//fun nullableUShortPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableUShortPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
//)
//fun nullableUShortPointerOf(value: UShort, arena: Arena = Arena.ofConfined()) {
//    val pointer = NullableUShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
//    pointer.set(value)
//}
//fun ushortPointerOf(memorySegment: MemorySegment) = UShortPointer(memorySegment)
//fun ushortPointerOf(memorySegment: MemorySegment, offset: Long) = UShortPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_SHORT)
//)
//fun ushortPointerOf(value: UShort, arena: Arena = Arena.ofConfined()) {
//    val pointer = UShortPointer(arena.allocate(ValueLayout.JAVA_SHORT))
//    pointer.set(value)
//}

// endregion

// region UInt Pointers

//fun nullableUIntPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableUIntPointer(memorySegment)
//fun nullableUIntPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableUIntPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_INT)
//)
//fun nullableUIntPointerOf(value: UInt, arena: Arena = Arena.ofConfined()) {
//    val pointer = NullableUIntPointer(arena.allocate(ValueLayout.JAVA_INT))
//    pointer.set(value)
//}
//fun uintPointerOf(memorySegment: MemorySegment) = UIntPointer(memorySegment)
//fun uintPointerOf(memorySegment: MemorySegment, offset: Long) = UIntPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_INT)
//)
//fun uintPointerOf(value: UInt, arena: Arena = Arena.ofConfined()) {
//    val pointer = UIntPointer(arena.allocate(ValueLayout.JAVA_INT))
//    pointer.set(value)
//}

// endregion

// region Long Pointers

//fun nullableLongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableLongPointer(memorySegment)
//fun nullableLongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableLongPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
//)
//fun nullableLongPointerOf(value: Long, arena: Arena = Arena.ofConfined()) {
//    val pointer = NullableLongPointer(arena.allocate(ValueLayout.JAVA_LONG))
//    pointer.set(value)
//}
//fun longPointerOf(memorySegment: MemorySegment) = LongPointer(memorySegment)
//fun longPointerOf(memorySegment: MemorySegment, offset: Long) = LongPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
//)
//fun longPointerOf(value: Long, arena: Arena = Arena.ofConfined()) {
//    val pointer = LongPointer(arena.allocate(ValueLayout.JAVA_LONG))
//    pointer.set(value)
//}

// endregion

// region ULong Pointers

//fun nullableULongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL) = NullableULongPointer(memorySegment)
//fun nullableULongPointerOf(memorySegment: MemorySegment = MemorySegment.NULL, offset: Long) = NullableULongPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
//)
//fun nullableULongPointerOf(value: ULong, arena: Arena = Arena.ofConfined()) {
//    val pointer = NullableULongPointer(arena.allocate(ValueLayout.JAVA_LONG))
//    pointer.set(value)
//}
//fun ulongPointerOf(memorySegment: MemorySegment) = ULongPointer(memorySegment)
//fun ulongPointerOf(memorySegment: MemorySegment, offset: Long) = ULongPointer(
//    memorySegment.asSlice(offset, ValueLayout.JAVA_LONG)
//)
//fun ulongPointerOf(value: ULong, arena: Arena = Arena.ofConfined()) {
//    val pointer = ULongPointer(arena.allocate(ValueLayout.JAVA_LONG))
//    pointer.set(value)
//}

// endregion

// region Struct Pointers

//fun <S : Struct> nullableStructPointerOf(struct: S?) = NullableStructPointer(struct)
//fun <S : Struct> nullableStructPointerOf(companion: StructCompanion<S>, arena: Arena = Arena.ofConfined()): NullableStructPointer<S> {
//    val struct = companion.allocate(arena)
//    return NullableStructPointer(struct)
//}
//fun <S : Struct> structPointerOf(struct: S) = StructPointer(struct)
//fun <S : Struct> structPointerOf(companion: StructCompanion<S>, arena: Arena = Arena.ofConfined()): StructPointer<S> {
//    val struct = companion.allocate(arena)
//    return StructPointer(struct)
//}

// endregion