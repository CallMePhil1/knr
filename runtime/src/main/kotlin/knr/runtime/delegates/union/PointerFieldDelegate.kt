package knr.runtime.delegates.union

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Union
import knr.runtime.typing.pointer.Pointer

open class PointerFieldDelegate<T, P : Pointer<T>?> internal constructor(
    parent: Union,
    offset: Long,
    initialValue: P,
    private val onSet: PointerFieldDelegate<T, P>.() -> Unit
) : FieldDelegate<P>(
    parent,
    offset
) {
    private var pointer: P = initialValue

    override fun get() = pointer

    fun uncheckedSet(value: P) {
        pointer = value
    }

    override fun set(value: P) {
        pointer = value
        onSet()
    }
}