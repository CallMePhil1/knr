package typing.pointer

import knr.runtime.layout.StructDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.pointer.*
import java.lang.foreign.ValueLayout

class PointedStruct(
    memory: Memory
) : Struct<PointedStruct>(memory, definition) {

    var l by intField()

    companion object : Struct.Companion<PointedStruct> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.JAVA_INT.withName("l")
        )

        override fun wrap(memory: Memory) = PointedStruct(memory)
    }
}

class AllPointers(
    memory: Memory
) : Struct<AllPointers>(memory, definition) {

    var b by bytePointerField(initialValue = bytePointerOf(memory.asAddress(currentOffset(), 1)))
    var nb by nullableBytePointerField(initialValue = null)
    var ub by ubytePointerField(initialValue = ubytePointerOf())
    var nub by nullableUBytePointerField(initialValue = null)

    var s by shortPointerField(initialValue = shortPointerOf())
    var ns by nullableShortPointerField(initialValue = null)
    var us by ushortPointerField(initialValue = ushortPointerOf())
    var nus by nullableUShortPointerField(initialValue = null)

    var i by intPointerField(initialValue = intPointerOf())
    var ni by nullableIntPointerField(initialValue = null)
    var ui by uintPointerField(initialValue = uintPointerOf())
    var nui by nullableUIntPointerField(initialValue = null)

    var l by longPointerField(initialValue = longPointerOf())
    var nl by nullableLongPointerField(initialValue = null)
    var ul by ulongPointerField(initialValue = ulongPointerOf())
    var nul by nullableULongPointerField(initialValue = null)

    companion object : Struct.Companion<AllPointers> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS
        )

        override fun wrap(memory: Memory) = AllPointers(memory)
    }
}