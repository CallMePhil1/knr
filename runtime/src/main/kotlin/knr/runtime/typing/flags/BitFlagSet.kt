package knr.runtime.typing.flags

interface BitFlagSet<N, T: BitFlag<N>> {
    val mask: N

    fun has(vararg others: T): Boolean
    fun lacks(vararg others: T): Boolean

    fun off(vararg others: T): BitFlagSet<N, T>
    fun on(vararg others: T): BitFlagSet<N, T>
    fun toggle(vararg others: T): BitFlagSet<N, T>

    fun clear(): BitFlagSet<N, T>
    fun and(vararg other: T): BitFlagSet<N, T>
    fun nand(vararg other: T): BitFlagSet<N, T>
    fun or(vararg other: T): BitFlagSet<N, T>
    fun nor(vararg other: T): BitFlagSet<N, T>
    fun xor(vararg other: T): BitFlagSet<N, T>
    fun xnor(vararg other: T): BitFlagSet<N, T>
}

inline fun <N, reified T: BitFlag<N>> BitFlagSet<N, T>.entries(): Result<List<T>> {
    val cls = T::class.java

    return when {
        cls.isEnum -> Result.success(cls.enumConstants.filter { this.has(it) })
        else -> Result.failure(NotImplementedError("Method 'entries' only works when the BitFlag is an Enum."))
    }
}