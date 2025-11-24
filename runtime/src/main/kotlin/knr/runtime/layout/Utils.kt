package knr.runtime.layout

import knr.runtime.typing.NativeEnum
import java.lang.foreign.MemoryLayout
import java.lang.foreign.ValueLayout

private fun getValueLayout(cls: Class<*>): ValueLayout {
    return when(cls) {
        java.lang.Byte::class.java,
        Byte::class.java -> ValueLayout.JAVA_BYTE
        java.lang.Short::class.java,
        Short::class.java -> ValueLayout.JAVA_SHORT
        Integer::class.java,
        Int::class.java -> ValueLayout.JAVA_INT
        java.lang.Long::class.java,
        Long::class.java -> ValueLayout.JAVA_LONG
        java.lang.Float::class.java,
        Float::class.java -> ValueLayout.JAVA_FLOAT
        java.lang.Double::class.java,
        Double::class.java -> ValueLayout.JAVA_DOUBLE
        else -> error("NativeEnum only supports primitive numeric types")
    }
}

fun <E> enumLayout(cls: Class<E>): MemoryLayout where E : Enum<E>, E : NativeEnum<*> {
    val type = cls.declaredFields.first { it.name == "value" }.type
    return getValueLayout(type)
}

fun <T, E> enumLayout(enumCompanion: NativeEnum.Companion<T, E>): MemoryLayout where E : Enum<E>, E : NativeEnum<T> {
    val type = enumCompanion.entriesMap.keys.first()!!::class.java
    return getValueLayout(type)
}
