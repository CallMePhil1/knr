package typing.array

import knr.runtime.typing.array.intNativeArray
import knr.runtime.typing.array.toTypedArray
import kotlin.test.Test
import kotlin.test.assertEquals

class IntNativeArrayTests {
    @Test
    fun `GIVEN a IntNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val intArray = intNativeArray(10)

        intArray[1] = 1

        assertEquals(1, intArray[1])

        ArrayTestLibrary.setInt(intArray, 2, 2)

        assertEquals(2, ArrayTestLibrary.getInt(intArray, 2))
        assertEquals(2, intArray[2])
    }

    @Test
    fun `GIVEN a IntNativeArray WHEN getting its size THEN it should return correct size`() {
        val intArray = intNativeArray(10)
        assertEquals(10, intArray.size)
        assertEquals(40, intArray.memory.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a IntArray WHEN constructing a IntNativeArray from the array THEN it should succeed`() {
        val intArray = intArrayOf(0, 10, 20, 30, 40)
        val nativeArray = intNativeArray(intArray)

        assertEquals(10, nativeArray[1])
        assertEquals(20, nativeArray[2])
    }

    @Test
    fun `GIVEN a IntArray when calling toTypedArray THEN it should return a typed array`() {
        val array = intArrayOf(0, 10, 20, 30, 40)
        val nativeArray = intNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}