package knr.runtime.typing.pointer

import knr.runtime.memory.Memory

class OpaquePointer internal constructor(
    memory: Memory
) : Pointer<Unit>(memory) {

    override fun get(): Unit = Unit

    override fun set(value: Unit) {}
}

fun opaquePointerOf(memory: Memory) = OpaquePointer(memory)
