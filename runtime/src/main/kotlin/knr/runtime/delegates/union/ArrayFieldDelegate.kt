package knr.runtime.delegates.union

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Native
import knr.runtime.typing.array.NativeArray

class ArrayFieldDelegate<T, C, A: NativeArray<T, C>> internal constructor(
    parent: Native<*>,
    initialValue: A
) : FieldDelegate<A>(parent, 0) {

    private var array: A = initialValue

    override fun get() = array

    override fun set(value: A) {
        value.copyTo(array)
    }
}