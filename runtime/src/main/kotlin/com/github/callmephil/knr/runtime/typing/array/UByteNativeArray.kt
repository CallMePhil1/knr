@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC

open class UByteNativeArray internal constructor(
    arc: ARC,
) : NativeArray<UByte, UByteArray>(arc, UByte.SIZE_BYTES) {

    override operator fun get(index: Int): UByte {
        checkBounds(index)
        return arc.getUByte(index.toLong())
    }
    override fun get() = getByteArray().asUByteArray()
    override fun getArray(): Array<UByte> {
        val buffer = arc.asByteBuffer()
        val arraySize = (arc.byteSize / typeByteSize).toInt()

        val array = Array<UByte>(arraySize) { 0u }

        for(i in 0 .. arraySize) {
            array[i] = buffer.get().toUByte()
        }

        return array
    }

    override operator fun set(index: Int, value: UByte) {
        checkBounds(index)
        arc.setUByte(index.toLong(), value)
    }
    override fun set(value: UByteArray) = setByteArray(value.asByteArray())
    override fun set(value: Array<UByte>) {
        checkBounds(value.size)
        value.forEachIndexed { idx, byte -> arc.setUByte(idx.toLong(), byte) }
    }
}

fun ubyteNativeArray(arc: ARC) = UByteNativeArray(arc)
fun ubyteNativeArray(size: Long) = ubyteNativeArray(ARC.shared(size))
fun ubyteNativeArray(array: UByteArray): UByteNativeArray {
    val arc = ARC.shared(array.size.toLong())
    arc.setBytes(array.asByteArray())
    return ubyteNativeArray(arc)
}