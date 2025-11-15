package knr.runtime.typing.pointer

import knr.runtime.memory.Memory

class OpaquePointer internal constructor(
    memory: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<Unit>(memory, onPointerUpdated) {

    override fun get(): Unit = Unit

    override fun set(value: Unit) {}
}