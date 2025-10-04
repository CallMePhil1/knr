package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC

open class ByteNativeArray internal constructor(
    arc: ARC,
) : NativeArray<Byte, ByteArray>(arc, Byte.SIZE_BYTES) {

    override operator fun get(index: Int): Byte {
        checkBounds(index)
        return arc.getByte(index.toLong())
    }
    override fun get() = getByteArray()
    override fun getArray(): Array<Byte> {
        val buffer = arc.asByteBuffer()
        val arraySize = (arc.byteSize / typeByteSize).toInt()

        val array = Array<Byte>(arraySize) { 0 }

        for(i in 0 .. arraySize) {
            array[i] = buffer.get()
        }

        return array
    }

    override operator fun set(index: Int, value: Byte) {
        checkBounds(index)
        arc.setByte(index.toLong(), value)
    }
    override fun set(value: ByteArray) = setByteArray(value)
    override fun set(value: Array<Byte>) {
        checkBounds(value.size)
        value.forEachIndexed { idx, byte -> arc.setByte(idx.toLong(), byte) }
    }
}

fun byteNativeArray(arc: ARC) = ByteNativeArray(arc)
fun byteNativeArray(size: Long) = byteNativeArray(ARC.shared(size))
fun byteNativeArray(array: ByteArray): ByteNativeArray {
    val arc = ARC.shared(array.size.toLong())
    arc.setBytes(array)
    return byteNativeArray(arc)
}