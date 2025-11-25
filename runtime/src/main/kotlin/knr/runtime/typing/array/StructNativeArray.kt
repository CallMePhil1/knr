package knr.runtime.typing.array

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct

class StructNativeArray<T : Struct<T>>(
    memory: Memory,
    size: Int,
    byteSize: Int,
    private val structs: Array<T>
) : NativeArray<T, Array<T>>(memory, size, byteSize) {
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

inline fun <reified T: Struct<T>> structNativeArray(memory: Memory, size: Int, companion: Struct.Companion<T>, noinline init: ((Int, T) -> Unit)? = null): StructNativeArray<T> {
    val structByteSize = companion.definition.byteSize
    val nativeArray = Array(size) {
        val slice = memory.asSlice(it * structByteSize, structByteSize)
        companion.wrap(slice)
    }
    if (init != null && size > 0) {
        nativeArray.forEachIndexed { idx, struct -> init(idx, struct) }
    }
    return StructNativeArray(memory, size, structByteSize.toInt(),nativeArray)
}
inline fun <reified T: Struct<T>> structNativeArray(size: Int, companion: Struct.Companion<T>, noinline init: ((Int, T) -> Unit)? = null): StructNativeArray<T> =
    structNativeArray(ArenaMemory.allocate(size * companion.definition.byteSize), size, companion, init)

inline fun <reified T: Struct<T>> structNativeArray(vararg values: T, structCompanion: Struct.Companion<T>) =
    structNativeArray(values.size, structCompanion) { idx, struct ->
        values[idx].copyTo(struct)
    }
