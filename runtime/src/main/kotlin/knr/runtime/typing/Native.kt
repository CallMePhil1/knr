package knr.runtime.typing

import knr.runtime.memory.Memory

abstract class Native<T> internal constructor(
    memory: Memory
) : AutoCloseable {

    internal open var innerMemory: Memory? = memory

    val memory: Memory
        get() = checkNotNull(innerMemory) { "Tried to use a Native object '${this::class.java.simpleName}' that was disposed of" }

    val isValid get() = innerMemory != null && innerMemory?.isNull == false
    val isNotValid get() = !isValid

    open fun clone(): T {
        val name = this::class.java.simpleName
        throw NotImplementedError("Tried to clone '$name' but it that doesn't override 'clone'")
    }

    override fun close() = dispose()

    open fun copyTo(native: T) {
        val name = this::class.java.simpleName
        throw NotImplementedError("Tried to copy '$name' but it that doesn't override 'copyTo'")
    }

    open fun dispose() {
        innerMemory?.dispose()
        innerMemory = null
    }
}