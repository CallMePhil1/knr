@file:OptIn(ExperimentalUnsignedTypes::class)

package com.github.callmephil.knr.runtime.typing.array

import com.github.callmephil.knr.runtime.memory.ARC

open class UByteNativeArray internal constructor(
    arc: ARC,
) : NativeArray<UByte, UByteArray>(arc, UByte.SIZE_BYTES) {

    override fun getUnchecked(index: Int) = arc.getUByte(index.toLong())
    override operator fun get(index: Int): UByte {
        checkBounds(index)
        return getUnchecked(index)
    }
    override fun get() = toByteArray().asUByteArray()

    override fun setUnchecked(index: Int, value: UByte) = arc.setUByte(index.toLong(), value)
    override operator fun set(index: Int, value: UByte) {
        checkBounds(index)
        setUnchecked(index, value)
    }
    override fun set(value: UByteArray) = setByteArray(value.asByteArray())
    override fun set(value: Array<UByte>) {
        checkBounds(value.size)
        for(i in 0 .. size) {
            this.setUnchecked(i, value[i])
        }
    }
}

fun ubyteNativeArray(arc: ARC) = UByteNativeArray(arc)
fun ubyteNativeArray(size: Long) = ubyteNativeArray(ARC.shared(size))
fun ubyteNativeArray(array: UByteArray): UByteNativeArray {
    val arc = ARC.shared(array.size.toLong())
    arc.setBytes(array.asByteArray())
    return ubyteNativeArray(arc)
}