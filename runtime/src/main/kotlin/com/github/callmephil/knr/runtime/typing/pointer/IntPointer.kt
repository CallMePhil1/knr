package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class IntPointer internal constructor(
    arc: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<Int>(arc, onPointerUpdated) {

    override fun get() = memory.getInt(0)

    override fun set(value: Int) = memory.setInt(0, value)

    override fun clone() = intPointerOf(get())
}

class UIntPointer internal constructor(
    arc: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<UInt>(arc, onPointerUpdated) {

    override fun get() = memory.getInt(0).toUInt()

    override fun set(value: UInt) = memory.setInt(0, value.toInt())

    override fun clone() = uintPointerOf(get())
}

// region Int Pointer

fun intPointerOf(arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)) = IntPointer(arc)
fun intPointerOf(value: Int, arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)): IntPointer {
    val pointer = IntPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun intPointerOf(
    value: Int,
    onPointerUpdated: () -> Unit
) = IntPointer(ArenaMemory.allocate(ValueLayout.JAVA_INT), onPointerUpdated).apply {
    set(value)
}

// endregion

// region UInt Pointer

fun uintPointerOf(arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)) = UIntPointer(arc)
fun uintPointerOf(value: UInt, arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_INT)): UIntPointer {
    val pointer = UIntPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun uintPointerOf(
    value: UInt,
    onPointerUpdated: () -> Unit
) = UIntPointer(ArenaMemory.allocate(ValueLayout.JAVA_INT), onPointerUpdated).apply {
    set(value)
}

// endregion
