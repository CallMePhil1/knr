package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.pointer.Pointer
import java.lang.foreign.ValueLayout

class PointerNativeArray<T : Pointer<*>?>(
    memory: Memory,
    size: Int,
    private val pointers: Array<T>
) : NativeArray<T, Array<T>>(memory, size, ValueLayout.ADDRESS.byteSize().toInt()) {
    override fun getUnchecked(index: Int) = pointers[index]
    override fun get(index: Int) = pointers[index]
    override fun get() = pointers.copyOf()

    override fun setUnchecked(index: Int, value: T) = set(index, value)
    override fun set(index: Int, value: T) {
        pointers[index] = value
        if (value != null)
            memory.setAddress((index * typeByteSize).toLong(), value.memory.memorySegment!!)
    }
    override fun set(value: Array<T>) {
        if (value.size > pointers.size) {
            val clsName = this::class.java.simpleName
            throw IndexOutOfBoundsException("Tried setting an Array for '$clsName' but was larger than the backing array")
        }
        value.forEachIndexed { idx, it ->
            this[idx] = it
        }
    }
}

inline fun <reified T : Pointer<*>?> pointerNativeArray(size: Int, noinline init: (Int) -> T?): PointerNativeArray<T?> {
    val addressByteSize = ValueLayout.ADDRESS.byteSize()
    val memory = ArenaMemory.allocate(size * addressByteSize)
    val array = Array(size, init)
    return PointerNativeArray(memory, size, array)
}

inline fun <reified T : Pointer<*>?> pointerNativeArray(vararg values: T): PointerNativeArray<T> {
    val addressByteSize = ValueLayout.ADDRESS.byteSize()
    val memory = ArenaMemory.allocate(values.size * addressByteSize)
    val array = Array(values.size) {
        val value = values[it]
        if (value != null)
            memory.setAddress(it * addressByteSize, value.memory.memorySegment!!)
        value
    }
    return PointerNativeArray(memory, values.size,array)
}
