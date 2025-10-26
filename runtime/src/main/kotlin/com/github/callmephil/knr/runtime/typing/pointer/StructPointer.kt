package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.NativeCloneable

class StructPointer<S : Struct> internal constructor(
    private var struct: S,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<S>(struct.memory, onPointerUpdated) {

    override fun clone(): Pointer<S> {
        val newStruct = when(struct) {
            is NativeCloneable<*> -> (struct as NativeCloneable<*>).clone() as S
            else -> error("Tried to clone a Struct that doesn't implement NativeCloneable")
        }
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

fun <T : Struct> structPointerOf(struct: T) = StructPointer(struct)
internal fun <T : Struct> structPointerOf(struct: T, onPointerUpdated: () -> Unit) = StructPointer(struct, onPointerUpdated)
