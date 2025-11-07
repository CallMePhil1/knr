package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import java.lang.foreign.StructLayout

class StructNativeArray<T : Struct<T>>(
    memory: Memory,
    byteSize: Int,
    private val structs: Array<T>
) : NativeArray<T, Array<T>>(memory, byteSize) {
    override fun getUnchecked(index: Int) = structs[index]
    override fun get(index: Int) = structs[index]
    override fun get(): Array<T> = structs.copyOf()

    override fun setUnchecked(index: Int, value: T) = set(index, value)
    override fun set(index: Int, value: T) {
        val struct = structs[index]
        value.copyTo(struct)
    }
    override fun set(value: Array<T>) {
        if (value.size > structs.size) {
            val clsName = this::class.java.simpleName
            throw IndexOutOfBoundsException("Tried setting an Array for '$clsName' but was larger than the backing array")
        }
        value.forEachIndexed { idx, it ->
            this[idx] = it
        }
    }
}

inline fun <reified T: Struct<T>> structNativeArray(size: Int, layout: StructLayout, init: (Int, Memory) -> T) = structNativeArray(size, layout.byteSize(), init)
inline fun <reified T: Struct<T>> structNativeArray(size: Int, structByteSize: Long, init: (Int, Memory) -> T): StructNativeArray<T> {
    val memory = ArenaMemory.allocate(structByteSize * size)
    val array = Array(size) {
        val slice = memory.asSlice(it * structByteSize, structByteSize)
        init(it, slice)
    }
    return StructNativeArray(memory, structByteSize.toInt(), array)
}
inline fun <reified T: Struct<T>> structNativeArray(vararg values: T, init: (Memory) -> T): StructNativeArray<T> {
    return if (values.isEmpty())
        StructNativeArray(ArenaMemory.allocate(0), 0, arrayOf())
    else {
        val byteSize = values[0].memory.byteSize
        val memory = ArenaMemory.allocate(byteSize * values.size)
        val array = Array(values.size) {
            val slice = memory.asSlice(it * byteSize, byteSize)
            val struct = init(slice)
            values[it].copyTo(struct)
            struct
        }
        StructNativeArray(memory, byteSize.toInt(), array)
    }
}
