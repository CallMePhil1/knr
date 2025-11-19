package knr.runtime.delegates.struct

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Native
import knr.runtime.typing.array.NativeArray

class ArrayFieldDelegate<T, C, A: NativeArray<T, C>> internal constructor(
    parent: Native<*>,
    offset: Long,
    initialValue: A
) : FieldDelegate<A>(parent, offset) {

    private var array: A = initialValue

    override fun get() = array

    override fun set(value: A) {
        value.copyTo(array)
    }
}