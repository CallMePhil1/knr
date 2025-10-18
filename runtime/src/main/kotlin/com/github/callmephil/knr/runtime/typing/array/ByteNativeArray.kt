package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC

open class ByteNativeArray internal constructor(
    arc: ARC,
) : NativeArray<Byte, ByteArray>(arc, Byte.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = arc.getByte(index.toLong())
    override operator fun get(index: Int): Byte {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = toByteArray()

    override fun setUnchecked(index: Int, value: Byte) = arc.setByte(index.toLong(), value)
    override operator fun set(index: Int, value: Byte) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: ByteArray) = setByteArray(value)
    override fun set(value: Array<Byte>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun byteNativeArray(arc: ARC) = ByteNativeArray(arc)
fun byteNativeArray(size: Long) = byteNativeArray(ARC.shared(size))
fun byteNativeArray(array: ByteArray): ByteNativeArray {
    val arc = ARC.shared(array.size.toLong())
    arc.setBytes(array)
    return byteNativeArray(arc)
}