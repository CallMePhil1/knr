package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import java.nio.ByteBuffer
import java.nio.charset.Charset

open class CCharNativeArray internal constructor(
    memory: Memory,
    val charset: Charset
) : ByteNativeArray(memory) {
    open fun getString() = memory.getString(0, charset)
    open fun set(value: String) = set(charset.encode(value).array())
}

fun ccharNativeArray(memory: Memory, charset: Charset) = CCharNativeArray(memory, charset)
fun ccharNativeArray(size: Long, charset: Charset) = ccharNativeArray(ArenaMemory.allocate(size), charset)
fun ccharNativeArray(value: String, charset: Charset): CCharNativeArray {
    val memory = ArenaMemory.string(value, charset)
    return ccharNativeArray(memory, charset)
}
fun ccharNativeArray(array: ByteArray, charset: Charset): CCharNativeArray {
    if (array[array.lastIndex] != 0.toByte())
        throw IllegalStateException("Tried to construct a CCharNativeArray with a ByteArray not ending in a null char")
    val memory = ArenaMemory.allocate(array.size.toLong())
    memory.setBytes(array)
    return ccharNativeArray(memory, charset)
}

class CachedCCharNativeArray internal constructor(
    memory: Memory,
    charset: Charset,
    initialValue: String
) : CCharNativeArray(memory, charset) {

    var value: String = initialValue
        private set

    override fun getString() = value
    override fun set(value: String) {
        memory.setString(0, value, charset)
        this.value = value
    }
    fun updateCache() {
        value = memory.getString(0, charset)
    }
}

fun cachedCCharNativeArray(memory: Memory, charset: Charset, initialValue: String) = CachedCCharNativeArray(memory, charset, initialValue)
fun cachedCCharNativeArray(size: Long, charset: Charset, initialValue: String) = cachedCCharNativeArray(ArenaMemory.allocate(size), charset, initialValue)
fun cachedCCharNativeArray(initialValue: String, charset: Charset): CachedCCharNativeArray {
    val memory = ArenaMemory.string(initialValue, charset)
    return cachedCCharNativeArray(memory, charset, initialValue)
}
fun cachedCCharNativeArray(array: ByteArray, charset: Charset, initialValue: String = charset.decode(ByteBuffer.wrap(array)).toString()): CachedCCharNativeArray {
    if (array[array.lastIndex] != 0.toByte())
        throw IllegalStateException("Tried to construct a CachedCCharNativeArray with a ByteArray not ending in a null char")
    val memory = ArenaMemory.allocate(array.size.toLong())
    memory.setBytes(array)
    return cachedCCharNativeArray(memory, charset, initialValue)
}
