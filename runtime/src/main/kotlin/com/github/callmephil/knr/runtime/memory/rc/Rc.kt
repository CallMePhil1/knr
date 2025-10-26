package com.github.callmephil.knr.runtime.memory.rc

import com.github.callmephil.knr.runtime.memory.Native

open class Rc<T : Native> internal constructor(
    shared: Shared<T>
) : AutoCloseable {

    internal var shared: Shared<T>? = shared
    val isValid: Boolean get() = shared?.isValid ?: false

    open fun clone(): Rc<T> {
        val nonNullShared = requireShared()
        nonNullShared.increment()
        return Rc(nonNullShared)
    }

    override fun close() = dispose()

    open fun dispose() {
        shared?.decrement()
        shared = null
    }

    open fun get(): T = checkNotNull(shared?.value) { "Tried to access a 'Rc' that was disposed of" }
    fun getOrNull() = shared?.value

    internal fun requireShared(): Shared<T> = checkNotNull(shared) { "Tried to access a 'Rc' that was already disposed"}

    fun weakRc(): WeakRc<T> = WeakRc(requireShared())

    companion object {
        fun <R: Native> of(native: R): Rc<R> {
            val shared = Shared(native)
            return Rc(shared)
        }
    }

    internal class Shared<T : Native>(value: T) {
        internal var refCount = 1
        internal var value: T? = value
        internal val isValid: Boolean get() = value != null

        fun decrement() {
            refCount -= 1
            when {
                refCount == 0 -> {
                    value?.dispose()
                    value = null
                }
                refCount < 0 -> error("Ref count underflow")
            }
        }

        fun increment() {
            if (refCount == 0) error("Value already dropped")
            refCount += 1
        }
    }
}

class WeakRc<T : Native> internal constructor(shared: Rc.Shared<T>) : Rc<T>(shared) {
    override fun clone(): WeakRc<T> {
        val nonNullShared = requireShared()
        nonNullShared.increment()
        return WeakRc(nonNullShared)
    }

    override fun dispose() {
        shared = null
    }

    override fun get() = checkNotNull(shared?.value) { "Tried to access a 'WeakRc' that was disposed of" }
}
