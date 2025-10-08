package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC

abstract class ByRef<T> internal constructor(
    arc: ARC?,
    private val onArcUpdated: (() -> Unit)?,
    val allowNull: Boolean
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

    open fun giveTo(other: ByRef<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc = null
    }

    open fun pointTo(arc: ARC) {
        if (!allowNull && arc.isNull)
            throw NullPointerException("Tried to point a non-nullable Pointer to a null")
        this.arc?.decrementCount()
        this.arc = arc
    }

    open fun shareWith(other: ByRef<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }
}

abstract class Pointer<T> internal constructor(
    arc: ARC?,
    onArcUpdated: (() -> Unit)?
) : ByRef<T>(arc, onArcUpdated, false) {

    abstract fun shareOf(): Pointer<T>
    abstract fun get(): T
    abstract fun set(value: T & Any)
}

abstract class NullablePointer<T> internal constructor(
    arc: ARC?,
    onArcUpdated: (() -> Unit)?
) : ByRef<T>(arc, onArcUpdated, true) {

    val isNull: Boolean get() {
        validOrThrow(this)
        return arc!!.isNull
    }

    override fun giveTo(other: ByRef<T>) {
        validOrThrow(this)

        if (!other.allowNull && arc!!.isNull)
            throw NullPointerException("Tried to give a nullable pointer to a non-nullable pointer when the pointer is null")

        super.giveTo(other)
    }

    inline fun ifNull(block: NullablePointer<T>.() -> Unit): NullablePointer<T> {
        if (isNull)
            block()
        return this
    }

    inline fun ifNotNull(block: NullablePointer<T>.(T) -> Unit): NullablePointer<T> {
        if (!isNull)
            block(get())
        return this
    }

    fun setToNull() = pointTo(ARC.ofNull())

    abstract fun shareOf(): NullablePointer<T>

    override fun shareWith(other: ByRef<T>) {
        validOrThrow(this)

        if (!other.allowNull && arc!!.isNull)
            throw NullPointerException("Tried to share a nullable pointer to a non-nullable pointer when the pointer is null")

        super.shareWith(other)
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
