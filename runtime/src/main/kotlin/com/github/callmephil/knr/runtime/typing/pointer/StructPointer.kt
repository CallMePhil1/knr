package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.typing.Struct

class StructPointer<S : Struct<S>> internal constructor(
    private var struct: S,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<S>(struct.memory, onPointerUpdated) {

    override fun clone(): Pointer<S> {
        val newStruct = struct.clone()
        return StructPointer(newStruct)
    }

    fun disposeAndSet(value: S) {
        struct.dispose()
        set(value)
    }

    override fun get(): S = struct
    override fun set(value: S) {
        struct = value
        innerMemory = value.memory
    }
}

fun <T : Struct<T>> structPointerOf(struct: T) = StructPointer(struct)
internal fun <T : Struct<T>> structPointerOf(struct: T, onPointerUpdated: () -> Unit) = StructPointer(struct, onPointerUpdated)
