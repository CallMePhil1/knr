package com.github.callmephil.knr.runtime.memory

abstract class Native internal constructor(
    memory: Memory
) : AutoCloseable {

    internal open var innerMemory: Memory? = memory

    val memory: Memory
        get() = checkNotNull(innerMemory) { "Tried to access a Native object that was disposed of" }

    val isValid get() = innerMemory != null
    val isNotValid get() = !isValid

    override fun close() = dispose()

    open fun dispose() {
        innerMemory?.dispose()
        innerMemory = null
    }
}