package knr.runtime.collections

open class ImmutablePair<T1, T2> internal constructor(
    open val first: T1,
    open val second: T2
) {
    override fun equals(other: Any?) = when {
        other == this -> true
        other !is ImmutablePair<T1, T2> -> false
        else -> first == other.first && second == other.second
    }

    override fun hashCode(): Int {
        var result = first?.hashCode() ?: 0
        result = 31 * result + (second?.hashCode() ?: 0)
        return result
    }
}

class MutablePair<T1, T2> internal constructor(
    override var first: T1,
    override var second: T2
) : ImmutablePair<T1, T2>(first, second) {
    fun asImmutable(): ImmutablePair<T1, T2> = this
    fun toImmutable(): ImmutablePair<T1, T2> = ImmutablePair(first, second)
}

fun <T1, T2> immutablePair(first: T1, second: T2) = ImmutablePair(first, second)
fun <T1, T2> mutablePair(first: T1, second: T2) = MutablePair(first, second)