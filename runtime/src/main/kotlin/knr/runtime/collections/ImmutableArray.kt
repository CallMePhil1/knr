package knr.runtime.collections

class ImmutableByteArray(private val array: ByteArray): Iterable<Byte> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableByteArray(vararg values: Byte) = ImmutableByteArray(values)
fun ByteArray.toImmutableByteArray() = ImmutableByteArray(this.copyOf())

class ImmutableUByteArray(private val array: UByteArray): Iterable<UByte> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableUByteArray(vararg values: UByte) = ImmutableUByteArray(values)
fun UByteArray.toImmutableUByteArray() = ImmutableUByteArray(this.copyOf())

class ImmutableShortArray(private val array: ShortArray): Iterable<Short> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableShortArray(vararg values: Short) = ImmutableShortArray(values)
fun ShortArray.toImmutableShortArray() = ImmutableShortArray(this.copyOf())

class ImmutableUShortArray(private val array: UShortArray): Iterable<UShort> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableUShortArray(vararg values: UShort) = ImmutableUShortArray(values)
fun UShortArray.toImmutableUShortArray() = ImmutableUShortArray(this.copyOf())

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

fun immutableUIntArray(vararg values: UInt) = ImmutableUIntArray(values)
fun UIntArray.toImmutableUShortArray() = ImmutableUIntArray(this.copyOf())

class ImmutableLongArray(private val array: LongArray): Iterable<Long> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableLongArray(vararg values: Long) = ImmutableLongArray(values)
fun LongArray.toImmutableLongArray() = ImmutableLongArray(this.copyOf())

class ImmutableULongArray(private val array: ULongArray): Iterable<ULong> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun immutableULongArray(vararg values: ULong) = ImmutableULongArray(values)
fun ULongArray.toImmutableULongArray() = ImmutableULongArray(this.copyOf())

class ImmutableArray<T>(private val array: Array<T>): Iterable<T> {
    val size = array.size
    operator fun get(index: Int) = array[index]
    override fun iterator() = array.iterator()
}

fun <T> immutableArray(vararg values: T) = ImmutableArray(values)
fun <T> Array<T>.toImmutableArray() = ImmutableArray(this.copyOf())
