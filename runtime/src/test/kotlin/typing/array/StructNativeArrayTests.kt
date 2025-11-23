package typing.array

import knr.runtime.layout.StructDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import knr.runtime.typing.array.structNativeArray
import knr.runtime.typing.pointer.intPointerOf
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayStruct(
    memory: Memory
) : Struct<ArrayStruct>(memory, definition) {
    var i by intField()
    var p by intPointerField(initialValue = intPointerOf())

    companion object: StructCompanion<ArrayStruct> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.JAVA_INT,
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