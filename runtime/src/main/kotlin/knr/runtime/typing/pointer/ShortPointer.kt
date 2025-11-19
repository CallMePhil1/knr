package knr.runtime.typing.pointer

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class ShortPointer internal constructor(
    arc: Memory
) : Pointer<Short>(arc) {

    override fun get() = memory.getShort(0)

    override fun set(value: Short) = memory.setShort(0, value)

    override fun clone() = shortPointerOf(get())
}

class UShortPointer internal constructor(
    arc: Memory
) : Pointer<UShort>(arc) {

    override fun get() = memory.getShort(0).toUShort()

    override fun set(value: UShort) = memory.setShort(0, value.toShort())

    override fun clone() = ushortPointerOf(get())
}

// region Short Pointer

fun shortPointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)) = when(memory.isNull) {
    true -> ShortPointer(ArenaMemory.allocate(ValueLayout.JAVA_SHORT))
    false -> ShortPointer(memory)
}
fun shortPointerOf(value: Short, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)): ShortPointer {
    val pointer = shortPointerOf(memory)
    pointer.set(value)
    return pointer
}

// endregion

// region UShort Pointer

fun ushortPointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)) = when(memory.isNull) {
    true -> UShortPointer(ArenaMemory.allocate(ValueLayout.JAVA_SHORT))
    false -> UShortPointer(memory)
}
fun ushortPointerOf(value: UShort, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)): UShortPointer {
    val pointer = ushortPointerOf(memory)
    pointer.set(value)
    return pointer
}

// endregion
