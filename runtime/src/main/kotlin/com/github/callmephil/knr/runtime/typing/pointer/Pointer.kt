package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC

abstract class ByRef<T> internal constructor(
    arc: ARC?,
    private val onArcUpdated: (() -> Unit)?
) : AutoCloseable {

    var arc: ARC? = arc
        internal set(value) {
            field = value
            onArcUpdated?.invoke()
        }

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

    abstract fun pointTo(arc: ARC)
}

abstract class Pointer<T> internal constructor(
    arc: ARC?,
    onArcUpdated: (() -> Unit)?
) : ByRef<T>(arc, onArcUpdated) {

    fun giveTo(other: Pointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc = null
    }

    fun giveTo(other: NullablePointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc = null
    }

    override fun pointTo(arc: ARC) {
        if (arc.isNull)
            throw NullPointerException("Tried to point to a null ARC in a non-nullable Pointer")
        this.arc?.decrementCount()
        this.arc = arc
    }

    fun shareWith(other: Pointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }

    fun shareWith(other: NullablePointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }

    abstract fun get(): T
    abstract fun set(value: T & Any)
}

abstract class NullablePointer<T> internal constructor(
    arc: ARC?,
    onArcUpdated: (() -> Unit)?
) : ByRef<T>(arc, onArcUpdated) {

    fun giveTo(other: Pointer<T>) {
        validOrThrow(this)

        if (arc!!.isNull)
            throw NullPointerException("Tried to give a nullable pointer to a non-nullable pointer when the pointer is null")

        other.arc?.decrementCount()
        other.arc = arc
        arc = null
    }

    fun giveTo(other: NullablePointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc = null
    }

    override fun pointTo(arc: ARC) {
        this.arc?.decrementCount()
        this.arc = arc
    }

    fun setToNull() = pointTo(ARC.ofNull())

    fun shareWith(other: Pointer<T>) {
        validOrThrow(this)

        if (arc!!.isNull)
            throw NullPointerException("Tried to share a nullable pointer to a non-nullable pointer when the pointer is null")

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }

    fun shareWith(other: NullablePointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }

    abstract fun get(): T
    abstract fun set(value: T & Any)
}

infix fun <T> Pointer<T>.giveTo(other: Pointer<T>): Unit = this.giveTo(other)

infix fun <T> Pointer<T>.giveTo(other: NullablePointer<T>): Unit = this.giveTo(other)

infix fun <T> NullablePointer<T>.giveTo(other: Pointer<T>): Unit = this.giveTo(other)

infix fun <T> NullablePointer<T>.giveTo(other: NullablePointer<T>): Unit = this.giveTo(other)

infix fun <T> Pointer<T>.takeFrom(other: Pointer<T>): Unit = other.giveTo(this)

infix fun <T> Pointer<T>.takeFrom(other: NullablePointer<T>): Unit = other.giveTo(this)

infix fun <T> NullablePointer<T>.takeFrom(other: Pointer<T>): Unit = other.giveTo(this)

infix fun <T> NullablePointer<T>.takeFrom(other: NullablePointer<T>): Unit = other.giveTo(this)

infix fun <T> Pointer<T>.shareWith(other: Pointer<T>): Unit = this.shareWith(other)

infix fun <T> Pointer<T>.shareWith(other: NullablePointer<T>): Unit = this.shareWith(other)

infix fun <T> NullablePointer<T>.shareWith(other: Pointer<T>): Unit = this.shareWith(other)

infix fun <T> NullablePointer<T>.shareWith(other: NullablePointer<T>): Unit = this.shareWith(other)

infix fun <T> Pointer<T>.shareFrom(other: Pointer<T>): Unit = other.shareWith(this)

infix fun <T> Pointer<T>.shareFrom(other: NullablePointer<T>): Unit = other.shareWith(this)

infix fun <T> NullablePointer<T>.shareFrom(other: Pointer<T>): Unit = other.shareWith(this)

infix fun <T> NullablePointer<T>.shareFrom(other: NullablePointer<T>): Unit = other.shareWith(this)

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