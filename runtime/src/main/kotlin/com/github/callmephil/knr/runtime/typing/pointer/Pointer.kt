package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC

abstract class ByRef<T> internal constructor(
    arc: ARC,
    internal var onArcUpdated: (() -> Unit)?,
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
        if (!allowNull && arc.isNull)
            throw NullPointerException("Tried to construct a non nullable ByRef with a null ARC")
        arc.incrementCount()
    }

    override fun close() = dispose()

    fun dispose() {
        arc?.decrementCount()
        arc = null
    }

    open fun giveTo(other: ByRef<T>) {
        validOrThrow(this)

        if (!other.allowNull && this.arc!!.isNull)
            throw NullPointerException("Tried to give a null pointer to a non nullable pointer")

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

        if (!other.allowNull && this.arc!!.isNull)
            throw NullPointerException("Tried to share a null pointer with a non nullable pointer")

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }
}

abstract class Pointer<T> internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)?
) : ByRef<T>(arc, onArcUpdated, false) {

    abstract fun shareOf(): Pointer<T>
    abstract fun get(): T
    abstract fun set(value: T & Any)
}

abstract class NullablePointer<T> internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)?
) : ByRef<T>(arc, onArcUpdated, true) {

    val isNull: Boolean get() {
        validOrThrow(this)
        return arc!!.isNull
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

    abstract fun get(): T
    abstract fun set(value: T & Any)
}

infix fun <T> ByRef<T>.giveTo(other: ByRef<T>) = this.giveTo(other)

infix fun <T> ByRef<T>.takeFrom(other: ByRef<T>) = other.giveTo(this)

infix fun <T> ByRef<T>.shareWith(other: ByRef<T>) = this.shareWith(other)

infix fun <T> ByRef<T>.shareFrom(other: ByRef<T>) = other.shareWith(this)
