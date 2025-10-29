package com.github.callmephil.knr.runtime.memory

abstract class Native<T> internal constructor(
    memory: Memory
) : AutoCloseable {

    internal open var innerMemory: Memory? = memory

    val memory: Memory
        get() = checkNotNull(innerMemory) { "Tried to access a Native object that was disposed of" }

    val isValid get() = innerMemory != null
    val isNotValid get() = !isValid

    open fun clone(): T {
        val name = this::class.java.simpleName
        throw NotImplementedError("Tried to clone '$name' that doesn't override 'clone'")
    }

    override fun close() = dispose()

    open fun copyTo(native: T) {
        val name = this::class.java.simpleName
        throw NotImplementedError("Tried to copy '$name' that doesn't override 'copyTo'")
    }

    open fun dispose() {
        innerMemory?.dispose()
        innerMemory = null
    }
}