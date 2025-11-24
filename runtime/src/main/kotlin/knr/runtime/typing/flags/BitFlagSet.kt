package knr.runtime.typing.flags

interface BitFlagSet<N, F> where F : BitFlag<N>, F : Enum<F> {
    val mask: N

    fun has(vararg others: F): Boolean
    fun lacks(vararg others: F): Boolean

    fun off(vararg others: F): BitFlagSet<N, F>
    fun on(vararg others: F): BitFlagSet<N, F>
    fun toggle(vararg others: F): BitFlagSet<N, F>

    fun clear(): BitFlagSet<N, F>
    fun and(vararg other: F): BitFlagSet<N, F>
    fun nand(vararg other: F): BitFlagSet<N, F>
    fun or(vararg other: F): BitFlagSet<N, F>
    fun nor(vararg other: F): BitFlagSet<N, F>
    fun xor(vararg other: F): BitFlagSet<N, F>
    fun xnor(vararg other: F): BitFlagSet<N, F>
}

inline fun <N, reified F> BitFlagSet<N, F>.entries(): List<F> where F : BitFlag<N>, F : Enum<F> {
    val cls = F::class.java
    return cls.enumConstants.filter { this.has(it) }
}