package string

import knr.runtime.layout.StructDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import knr.runtime.typing.cachedCStringOf
import knr.runtime.typing.cstringOf
import java.lang.foreign.ValueLayout

class StringStruct(
    memory: Memory
) : Struct<StringStruct>(memory, definition) {
    var strPointer by cstringField(initialValue = cstringOf(""))
    var cachedStrPointer by cachedCStringField(initialValue = cachedCStringOf(""))

    companion object : StructCompanion<StringStruct> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.ADDRESS.withName("str_ptr"),
            ValueLayout.ADDRESS.withName("cached_str_ptr")
        )

        override fun wrap(memory: Memory) = StringStruct(memory)
    }
}