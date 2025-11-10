package knr.runtime.typing.pointer

import knr.runtime.memory.Memory
import knr.runtime.typing.Native

abstract class Pointer<T> internal constructor(
    memory: Memory,
    internal var onPointerUpdated: (() -> Unit)?
) : Native<Pointer<T>>(memory) {

    override var innerMemory: Memory? = memory
        set(value) {
            field = value
            onPointerUpdated?.invoke()
        }

    override fun close() = dispose()

    open fun pointTo(memory: Memory) {
        innerMemory?.dispose()
        innerMemory = memory
    }

    abstract fun get(): T
    abstract fun set(value: T & Any)
}
