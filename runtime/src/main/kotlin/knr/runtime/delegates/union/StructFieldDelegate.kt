package knr.runtime.delegates.union

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Struct
import knr.runtime.typing.Union

class StructFieldDelegate <T: Struct<T>> internal constructor(
    parent: Union,
    structCompanion: Struct.Companion<T>
) : FieldDelegate<T>(parent, 0) {

    private val struct = structCompanion.wrap(parent.memory)

    override fun get() = struct

    override fun set(value: T) {
        value.copyTo(struct)
    }
}