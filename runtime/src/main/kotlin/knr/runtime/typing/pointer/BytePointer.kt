package knr.runtime.typing.pointer

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class BytePointer internal constructor(
    memory: Memory
) : Pointer<Byte>(memory) {

    override fun get() = memory.getByte(0)

    override fun set(value: Byte) = memory.setByte(0, value)

    override fun clone() = bytePointerOf(get())
}

class UBytePointer internal constructor(
    memory: Memory
) : Pointer<UByte>(memory) {

    override fun get() = memory.getByte(0).toUByte()

    override fun set(value: UByte) = memory.setByte(0, value.toByte())

    override fun clone() = ubytePointerOf(get())
}

// region Byte Pointer

fun bytePointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)) = when(memory.isNull) {
    true -> BytePointer(ArenaMemory.allocate(ValueLayout.JAVA_BYTE))
    false -> BytePointer(memory)
}
fun bytePointerOf(value: Byte, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)): BytePointer {
    val pointer = bytePointerOf(memory)
    pointer.set(value)
    return pointer
}

// endregion

// region UByte Pointer

fun ubytePointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)) = when(memory.isNull) {
    true -> UBytePointer(ArenaMemory.allocate(ValueLayout.JAVA_BYTE))
    false -> UBytePointer(memory)
}
fun ubytePointerOf(value: UByte, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)): UBytePointer {
    val pointer = ubytePointerOf(memory)
    pointer.set(value)
    return pointer
}

// endregion
