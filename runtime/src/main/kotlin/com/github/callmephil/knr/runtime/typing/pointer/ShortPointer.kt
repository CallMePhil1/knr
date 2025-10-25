package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class ShortPointer internal constructor(
    arc: Memory,
    onArcUpdated: (()-> Unit)? = null
) : Pointer<Short>(arc, onArcUpdated) {

    override fun get() = memory.getShort(0)

    override fun set(value: Short) = memory.setShort(0, value)

    override fun copyOf() = shortPointerOf(get())
}

class UShortPointer internal constructor(
    arc: Memory,
    onArcUpdated: (()-> Unit)? = null
) : Pointer<UShort>(arc, onArcUpdated) {

    override fun get() = memory.getShort(0).toUShort()

    override fun set(value: UShort) = memory.setShort(0, value.toShort())

    override fun copyOf() = ushortPointerOf(get())
}

// region Short Pointer

fun shortPointerOf(arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)) = ShortPointer(arc)
fun shortPointerOf(value: Short, arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)): ShortPointer {
    val pointer = ShortPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun shortPointerOf(
    value: Short,
    onPointerUpdated: () -> Unit
) = ShortPointer(ArenaMemory.allocate(ValueLayout.JAVA_SHORT), onPointerUpdated).apply {
    set(value)
}

// endregion

// region UShort Pointer

fun ushortPointerOf(arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)) = UShortPointer(arc)
fun ushortPointerOf(value: UShort, arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_SHORT)): UShortPointer {
    val pointer = UShortPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun ushortPointerOf(
    value: UShort,
    onPointerUpdated: () -> Unit
) = UShortPointer(ArenaMemory.allocate(ValueLayout.JAVA_SHORT), onPointerUpdated).apply {
    set(value)
}

// endregion
