package knr.runtime.typing.pointer

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class IntPointer internal constructor(
    memory: Memory
) : Pointer<Int>(memory) {

    override fun get() = memory.getInt(0)

    override fun set(value: Int) = memory.setInt(0, value)

    override fun clone() = intPointerOf(get())
}

class UIntPointer internal constructor(
    memory: Memory
) : Pointer<UInt>(memory) {

    override fun get() = memory.getInt(0).toUInt()

    override fun set(value: UInt) = memory.setInt(0, value.toInt())

    override fun clone() = uintPointerOf(get())
}

// region Int Pointer

fun intPointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)) = IntPointer(memory)
fun intPointerOf(value: Int, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)): IntPointer {
    val pointer = IntPointer(memory)
    pointer.set(value)
    return pointer
}

// endregion

// region UInt Pointer

fun uintPointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)) = UIntPointer(memory)
fun uintPointerOf(value: UInt, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)): UIntPointer {
    val pointer = UIntPointer(memory)
    pointer.set(value)
    return pointer
}

// endregion
