package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC

abstract class Pointer<T> internal constructor(
    arc: ARC,
    internal var onArcUpdated: (() -> Unit)?
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
        arc.incrementCount()
    }

    override fun close() = dispose()

    /**
     * Decrements the [ARC] associated with this pointer and then sets it to null.
     * This will trigger an [ARC] update
     */
    fun dispose() {
        arc?.decrementCount()
        arc = null
    }

    open fun giveTo(other: Pointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc = null
    }

    open fun pointTo(arc: ARC) {
        this.arc?.decrementCount()
        this.arc = arc
    }

    open fun shareWith(other: Pointer<T>) {
        validOrThrow(this)

        other.arc?.decrementCount()
        other.arc = arc
        arc?.incrementCount()
    }

    abstract fun shareOf(): Pointer<T>
    abstract fun get(): T
    abstract fun set(value: T & Any)
}

infix fun <T> Pointer<T>.giveTo(other: Pointer<T>) = this.giveTo(other)

infix fun <T> Pointer<T>.takeFrom(other: Pointer<T>) = other.giveTo(this)

infix fun <T> Pointer<T>.shareWith(other: Pointer<T>) = this.shareWith(other)

infix fun <T> Pointer<T>.shareFrom(other: Pointer<T>) = other.shareWith(this)
