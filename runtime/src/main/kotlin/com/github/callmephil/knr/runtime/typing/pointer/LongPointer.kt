package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import java.lang.foreign.ValueLayout

class LongPointer internal constructor(
    arc: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<Long>(arc, onPointerUpdated) {

    override fun get() = memory.getLong(0)

    override fun set(value: Long) = memory.setLong(0, value)

    override fun clone() = longPointerOf(get())
}

class ULongPointer internal constructor(
    arc: Memory,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<ULong>(arc, onPointerUpdated) {

    override fun get() = memory.getLong(0).toULong()

    override fun set(value: ULong) = memory.setLong(0, value.toLong())

    override fun clone() = ulongPointerOf(get())
}

// region Long Pointer

fun longPointerOf(arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)) = LongPointer(arc)
fun longPointerOf(value: Long, arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)): LongPointer {
    val pointer = LongPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun longPointerOf(
    value: Long,
    onPointerUpdated: () -> Unit
) = LongPointer(ArenaMemory.allocate(ValueLayout.JAVA_LONG), onPointerUpdated).apply {
    set(value)
}

// endregion

// region ULong Pointer

fun ulongPointerOf(arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)) = ULongPointer(arc)
fun ulongPointerOf(value: ULong, arc: Memory = ArenaMemory.allocate(ValueLayout.JAVA_LONG)): ULongPointer {
    val pointer = ULongPointer(arc)
    pointer.set(value)
    return pointer
}
internal fun ulongPointerOf(
    value: ULong,
    onPointerUpdated: () -> Unit
) = ULongPointer(ArenaMemory.allocate(ValueLayout.JAVA_LONG), onPointerUpdated).apply {
    set(value)
}

// endregion
