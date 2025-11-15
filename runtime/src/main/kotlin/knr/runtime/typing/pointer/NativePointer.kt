package knr.runtime.typing.pointer

import knr.runtime.typing.Native

class NativePointer<T : Native<T>> internal constructor(
    private var value: T
) : Pointer<T>(value.memory) {
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
