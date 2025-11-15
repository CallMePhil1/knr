package knr.runtime.delegates.struct

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion

class StructFieldDelegate <T: Struct<T>> internal constructor(
    parent: Struct<*>,
    offset: Long,
    structCompanion: StructCompanion<T>
) : FieldDelegate<T>(parent, 0) {

    private val struct = structCompanion.wrap(parent.memory.asSlice(offset, structCompanion.layout))

    override fun get() = struct

    override fun set(value: T) {
        value.copyTo(struct)
    }
}