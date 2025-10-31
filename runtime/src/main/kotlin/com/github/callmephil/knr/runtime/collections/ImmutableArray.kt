package com.github.callmephil.knr.runtime.collections

class ImmutableByteArray(private val array: ByteArray): Iterable<Byte> {
    val lastIndex = array.lastIndex
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableUByteArray(private val array: UByteArray): Iterable<UByte> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableShortArray(private val array: ShortArray): Iterable<Short> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableUShortArray(private val array: UShortArray): Iterable<UShort> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableIntArray(private val array: IntArray): Iterable<Int> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableIntArray(vararg values: Int) = ImmutableIntArray(values)
fun IntArray.toImmutableIntArray() = ImmutableIntArray(this.copyOf())

class ImmutableUIntArray(private val array: UIntArray): Iterable<UInt> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableLongArray(private val array: LongArray): Iterable<Long> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableULongArray(private val array: ULongArray): Iterable<ULong> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

class ImmutableArray<T>(private val array: Array<T>): Iterable<T> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}