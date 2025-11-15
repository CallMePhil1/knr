package knr.runtime.typing.pointer

import knr.runtime.memory.Memory
import knr.runtime.typing.Native

abstract class Pointer<T> internal constructor(
    memory: Memory
) : Native<Pointer<T>>(memory) {

    override var innerMemory: Memory? = memory

    override fun close() = dispose()

    abstract fun get(): T
    abstract fun set(value: T & Any)
}
