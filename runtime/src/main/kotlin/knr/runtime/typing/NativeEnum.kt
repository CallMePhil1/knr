package knr.runtime.typing

import kotlin.enums.EnumEntries

interface NativeEnum<T> {
    val value: T

    abstract class Companion<T, E>(entries: EnumEntries<E>) where E : Enum<E>, E : NativeEnum<T> {
        val entriesMap: Map<T, E> = HashMap<T, E>().apply {
            entries.forEach { this[it.value] = it }
        }

        fun of(value: T) = entriesMap[value]!!
    }
}