package com.github.callmephil.knr.runtime.memory.rc

import com.github.callmephil.knr.runtime.memory.Native
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

open class Arc<T : Native<T>> internal constructor(
    shared: Shared<T>
): AutoCloseable {

    internal var shared: Shared<T>? = shared
    val isValid: Boolean get() = shared?.isValid ?: false

    open fun clone(): Arc<T> {
        val nonNullShare = requireShared()
        nonNullShare.increment()
        return Arc(nonNullShare)
    }

    override fun close() {
        dispose()
    }

    open fun dispose() {
        shared?.decrement()
        shared = null
    }

    open fun get(): T = checkNotNull(shared?.value?.get()) { "Tried to access a 'Arc' that was disposed of" }
    fun getOrNull() = shared?.value?.get()

    internal fun requireShared(): Shared<T> = checkNotNull(shared) { "Tried to access a 'Rc' that was already disposed"}

    fun weakArc(): WeakArc<T> = WeakArc(requireShared())

    companion object {
        fun <T: Native<T>> of(native: T): Arc<T> {
            val shared = Shared(native)
            return Arc(shared)
        }
    }

    internal class Shared<T : Native<T>>(value: T) {
        internal var refCount = AtomicInteger(1)
        internal var value = AtomicReference<T?>(value)
        internal val isValid: Boolean get() = value.get() != null

        fun decrement() {
            val count = refCount.decrementAndGet()
            when {
                count == 0 -> {
                    value.updateAndGet {
                        it?.dispose()
                        null
                    }
                }
                count < 0 -> error("Ref count underflow")
            }
        }

        fun increment() {
            val count = refCount.getAndUpdate {
                if (it == 0) it
                else it + 1
            }
            if (count <= 0) error("Value already dropped")
        }
    }
}

class WeakArc<T : Native<T>> internal constructor(
    shared: Shared<T>
) : Arc<T>(shared) {
    override fun clone(): WeakArc<T> {
        val nonNullShare = requireShared()
        nonNullShare.increment()
        return WeakArc(nonNullShare)
    }

    override fun dispose() {
        shared = null
    }

    override fun get() = checkNotNull(shared?.value?.get()) { "Tried to access a 'WeakArc' that was disposed of" }
}
