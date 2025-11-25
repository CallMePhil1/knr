package knr.runtime.delegates.struct

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Struct
import knr.runtime.typing.Union

class UnionFieldDelegate<U: Union> internal constructor(
    parent: Struct<*>,
    offset: Long,
    unionCompanion: Union.Companion<U>
) : FieldDelegate<U>(parent, offset) {

    private val union = unionCompanion.wrap(parent.memory.asSlice(offset, unionCompanion.layout.byteSize()))

    override fun get() = union

    override fun set(value: U) {
        value.copyTo(union)
    }
}