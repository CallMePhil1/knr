package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.memory.Native

abstract class Pointer<T> internal constructor(
    memory: Memory,
    internal var onPointerUpdated: (() -> Unit)?
) : Native(memory) {

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

    abstract fun copyOf(): Pointer<T>
    abstract fun get(): T
    abstract fun set(value: T & Any)
}
