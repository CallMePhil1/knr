package string

import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import knr.runtime.typing.cachedCStringOf
import knr.runtime.typing.cstringOf
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

class StringStruct(
    memory: Memory
) : Struct<StringStruct>(memory) {
    var strPointer by cstringField(0, cstringOf(""))
    var cachedStrPointer by cachedCStringField(8, cachedCStringOf(""))

    companion object : StructCompanion<StringStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withName("str_ptr"),
            ValueLayout.ADDRESS.withName("cached_str_ptr")
        )

        override fun wrap(memory: Memory) = StringStruct(memory)
    }
}