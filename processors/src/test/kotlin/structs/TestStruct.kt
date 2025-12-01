package structs

import knr.runtime.layout.StructDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import java.lang.foreign.ValueLayout

class TestStruct(
    memory: Memory
) : Struct<TestStruct>(memory, definition) {
    var i by intField()

    companion object : Struct.Companion<TestStruct> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.JAVA_INT
        )

        override fun wrap(memory: Memory) = TestStruct(memory)
    }
}