package typing.array

import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import knr.runtime.typing.array.structNativeArray
import knr.runtime.typing.pointer.intPointerOf
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayStruct(
    memory: Memory
) : Struct<ArrayStruct>(memory) {
    var i by intField(0)
    var p by intPointerField(8, intPointerOf())

    companion object: StructCompanion<ArrayStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT,
            MemoryLayout.paddingLayout(4),
            ValueLayout.ADDRESS
        )

        override fun wrap(memory: Memory) = ArrayStruct(memory)
    }
}

class StructNativeArrayTests {
    @Test
    fun `GIVEN a StructNativeArray WHEN calling c getter and setter THEN it should work`() {
        val array = structNativeArray(10, ArrayStruct) { idx, struct ->
            struct.i = idx
        }

        for (i in 0 ..< 10) {
            assertEquals(i, ArrayTestLibrary.getIntFromStruct(array, i))
        }

        ArrayTestLibrary.setIntForStruct(array, 2, 20)

        assertEquals(20, ArrayTestLibrary.getIntFromStruct(array, 2))
        assertEquals(20, array[2].i)
    }
}