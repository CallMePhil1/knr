package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC
import java.nio.ByteBuffer
import java.nio.charset.Charset

open class CCharNativeArray internal constructor(
    arc: ARC,
    val charset: Charset
) : ByteNativeArray(arc) {
    open fun getString() = arc.getString(0, charset)
    open fun set(value: String) = set(charset.encode(value).array())
}

fun ccharNativeArray(arc: ARC, charset: Charset) = CCharNativeArray(arc, charset)
fun ccharNativeArray(size: Long, charset: Charset) = ccharNativeArray(ARC.shared(size), charset)
fun ccharNativeArray(value: String, charset: Charset): CCharNativeArray {
    val arc = ARC.string(value, charset)
    return ccharNativeArray(arc, charset)
}
fun ccharNativeArray(array: ByteArray, charset: Charset): CCharNativeArray {
    if (array[array.lastIndex] != 0.toByte())
        throw IllegalStateException("Tried to construct a CCharNativeArray with a ByteArray not ending in a null char")
    val arc = ARC.shared(array.size.toLong())
    arc.setBytes(array)
    return ccharNativeArray(arc, charset)
}

class CachedCCharNativeArray internal constructor(
    arc: ARC,
    charset: Charset,
    initialValue: String
) : CCharNativeArray(arc, charset) {

    var value: String = initialValue
        private set

    override fun getString() = value
    override fun set(value: String) {
        arc.setString(0, value, charset)
        this.value = value
    }
    fun updateCache() {
        value = arc.getString(0, charset)
    }
}

fun cachedCCharNativeArray(arc: ARC, charset: Charset, initialValue: String) = CachedCCharNativeArray(arc, charset, initialValue)
fun cachedCCharNativeArray(size: Long, charset: Charset, initialValue: String) = cachedCCharNativeArray(ARC.shared(size), charset, initialValue)
fun cachedCCharNativeArray(initialValue: String, charset: Charset): CachedCCharNativeArray {
    val arc = ARC.string(initialValue, charset)
    return cachedCCharNativeArray(arc, charset, initialValue)
}
fun cachedCCharNativeArray(array: ByteArray, charset: Charset, initialValue: String = charset.decode(ByteBuffer.wrap(array)).toString()): CachedCCharNativeArray {
    val arc = ARC.shared(array.size.toLong())
    arc.setBytes(array)
    return cachedCCharNativeArray(arc, charset, initialValue)
}
