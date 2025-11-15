package ext

import knr.runtime.ext.structLayout
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertEquals

class TestStructB(
    memory: Memory
) : Struct<TestStructB>(memory) {
    var i by intField(0)
    var l by longField(8)

    companion object : StructCompanion<TestStructB> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT,
            MemoryLayout.paddingLayout(4),
            ValueLayout.JAVA_LONG,
        ).withName("TestStructB")

        override fun wrap(memory: Memory): TestStructB {
            TODO("Not yet implemented")
        }
    }
}

class TestStruct(
    memory: Memory
) : Struct<TestStruct>(memory) {
    var i by intField(0)
    var l by longField(8)
    var s by structField(16, TestStructB)

    companion object : StructCompanion<TestStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT,
            MemoryLayout.paddingLayout(4),
            ValueLayout.JAVA_LONG,
            TestStructB.layout
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
            TestStructB.layout
        )

        assertEquals(TestStruct.layout.byteSize(), originalLayout.byteSize())
    }
}