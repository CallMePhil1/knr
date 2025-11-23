package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.memory.ViewMemory
import knr.runtime.typing.pointer.Pointer
import java.lang.foreign.MemorySegment
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

inline fun <reified T : Pointer<*>?> pointerNativeArray(memory: Memory, ctor: (Int, Memory) -> T): PointerNativeArray<T> {
    val addressByteSize = ValueLayout.ADDRESS.byteSize()
    val size = (memory.byteSize / addressByteSize).toInt()
    val array = Array(size) {
        val offset = addressByteSize * it
        val segment = memory.getAddress(offset).reinterpret(addressByteSize)
        val slice = ViewMemory.wrap(segment)
        val pointer = ctor(it, slice)

        if (segment == MemorySegment.NULL && pointer != null) {
            memory.setAddress(offset, pointer.memory.memorySegment!!)
        }

        pointer
    }

    return PointerNativeArray(memory, size, array) as PointerNativeArray<T>
}

inline fun <reified T : Pointer<*>?> pointerNativeArray(size: Int, init: (Int, Memory) -> T): PointerNativeArray<T> {
    val memory = ArenaMemory.allocate(size * ValueLayout.ADDRESS.byteSize())
    return pointerNativeArray(memory, init)
}

inline fun <reified P : Pointer<*>?> pointerNativeArray(size: Int, noinline init: (Int) -> P): PointerNativeArray<P> {
    val array = Array(size, init)
    return pointerNativeArray(*array)
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
