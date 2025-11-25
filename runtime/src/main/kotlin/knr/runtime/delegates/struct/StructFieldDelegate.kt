package knr.runtime.delegates.struct

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Struct

class StructFieldDelegate <T: Struct<T>> internal constructor(
    parent: Struct<*>,
    offset: Long,
    structCompanion: Struct.Companion<T>
) : FieldDelegate<T>(parent, 0) {

    private val struct = structCompanion.wrap(parent.memory.asSlice(offset, structCompanion.definition))

    override fun get() = struct

    override fun set(value: T) {
        value.copyTo(struct)
    }
}