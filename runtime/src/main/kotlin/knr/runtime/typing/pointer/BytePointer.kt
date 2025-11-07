package knr.runtime.typing.pointer

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class BytePointer internal constructor(
    memory: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<Byte>(memory, onPointerUpdated) {

    override fun get() = memory.getByte(0)

    override fun set(value: Byte) = memory.setByte(0, value)

    override fun clone() = bytePointerOf(get())
}

class UBytePointer internal constructor(
    memory: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<UByte>(memory, onPointerUpdated) {

    override fun get() = memory.getByte(0).toUByte()

    override fun set(value: UByte) = memory.setByte(0, value.toByte())

    override fun clone() = ubytePointerOf(get())
}

// region Byte Pointer

fun bytePointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)) = BytePointer(memory)
fun bytePointerOf(value: Byte, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)) = BytePointer(memory).apply {
    set(value)
}
internal fun bytePointerOf(
    value: Byte,
    onPointerUpdated: () -> Unit
) = BytePointer(ArenaMemory.allocate(ValueLayout.JAVA_BYTE), onPointerUpdated).apply {
    set(value)
}

// endregion

// region UByte Pointer

fun ubytePointerOf(memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)) = UBytePointer(memory)
fun ubytePointerOf(value: UByte, memory: Memory = ArenaMemory.allocate(ValueLayout.JAVA_BYTE)) = UBytePointer(memory).apply {
    set(value)
}
internal fun ubytePointerOf(
    value: UByte,
    onPointerUpdated: () -> Unit
) = UBytePointer(ArenaMemory.allocate(ValueLayout.JAVA_BYTE), onPointerUpdated).apply {
    set(value)
}

// endregion
