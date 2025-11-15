package knr.runtime.typing.pointer

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class LongPointer internal constructor(
    memory: Memory
) : Pointer<Long>(memory) {

    override fun get() = memory.getLong(0)

    override fun set(value: Long) = memory.setLong(0, value)

    override fun clone() = longPointerOf(get())
}

class ULongPointer internal constructor(
    memory: Memory
) : Pointer<ULong>(memory) {

    override fun get() = memory.getLong(0).toULong()

    override fun set(value: ULong) = memory.setLong(0, value.toLong())

    override fun clone() = ulongPointerOf(get())
}

// region Long Pointer

fun longPointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)) = LongPointer(memory)
fun longPointerOf(value: Long, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)): LongPointer {
    val pointer = LongPointer(memory)
    pointer.set(value)
    return pointer
}

// endregion

// region ULong Pointer

fun ulongPointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)) = ULongPointer(memory)
fun ulongPointerOf(value: ULong, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)): ULongPointer {
    val pointer = ULongPointer(memory)
    pointer.set(value)
    return pointer
}

// endregion
