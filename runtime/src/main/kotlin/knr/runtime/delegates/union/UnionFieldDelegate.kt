package knr.runtime.delegates.union

import knr.runtime.delegates.FieldDelegate
import knr.runtime.typing.Union

class UnionFieldDelegate <T : Union> internal constructor(
    parent: Union,
    unionCompanion: Union.Companion<T>
) : FieldDelegate<T>(parent, 0) {

    private val union = unionCompanion.wrap(parent.memory)

    override fun get(): T = union

    override fun set(value: T) {
        value.copyTo(union)
    }
}