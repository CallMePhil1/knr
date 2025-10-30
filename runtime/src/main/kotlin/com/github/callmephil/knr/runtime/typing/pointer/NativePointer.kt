package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.Native

class NativePointer<T : Native<T>> internal constructor(
    private var value: T,
    onPointerUpdated: (() -> Unit)? = null
) : Pointer<T>(value.memory, onPointerUpdated) {
    override fun clone(): Pointer<T> {
        val newNative = value.clone()
        return NativePointer(newNative)
    }
    fun disposeAndSet(value: T) {
        this.value.dispose()
        set(value)
    }
    override fun get() = value
    override fun set(value: T) {
        this.value = value
        innerMemory = value.memory
    }
}

fun <T : Native<T>> nativePointerOf(native: T) = NativePointer(native)
internal fun <T : Native<T>> nativePointerOf(native: T, onPointerUpdated: (() -> Unit)) = NativePointer(native, onPointerUpdated)
