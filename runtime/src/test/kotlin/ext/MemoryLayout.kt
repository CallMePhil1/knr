package ext

import knr.runtime.ext.structLayout
import knr.runtime.layout.StructDefinition
import knr.runtime.layout.structDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertEquals

class TestStructB(
    memory: Memory
) : Struct<TestStructB>(memory, definition) {
    var i by intField()
    var l by longField()

    companion object : StructCompanion<TestStructB> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG,
        )

        override fun wrap(memory: Memory): TestStructB {
            TODO("Not yet implemented")
        }
    }
}

class TestStruct(
    memory: Memory
) : Struct<TestStruct>(memory, definition) {
    var i by intField()
    var l by longField()
    var s by structField(structCompanion = TestStructB)

    companion object : StructCompanion<TestStruct> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG,
            TestStructB.definition.layout
        )

        override fun wrap(memory: Memory): TestStruct {
            TODO("Not yet implemented")
        }
    }
}

class MemoryLayoutTests {
    @Test
    fun `GIVEN WHEN calling struct layout THEN it should return same as calling MemoryLayout`() {
        val originalLayout = structLayout(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG,
            TestStructB.definition.layout
        )

        assertEquals(TestStruct.definition.byteSize, originalLayout.byteSize())
    }
}