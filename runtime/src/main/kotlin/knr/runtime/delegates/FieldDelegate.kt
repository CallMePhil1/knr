package knr.runtime.delegates

import knr.runtime.typing.Native
import kotlin.reflect.KProperty

abstract class FieldDelegate<T> internal constructor(
    protected val parent: Native<*>,
    protected val offset: Long
) {
    abstract fun get(): T
    abstract fun set(value: T)

    operator fun getValue(thisRef: Any, prop: KProperty<*>): T = get()
    operator fun setValue(thisRef: Any, prop: KProperty<*>, value: T) = set(value)
}
