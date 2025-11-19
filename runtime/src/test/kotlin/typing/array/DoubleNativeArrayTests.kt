package typing.array

import knr.runtime.typing.array.doubleNativeArray
import knr.runtime.typing.array.toTypedArray
import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleNativeArrayTests {
    @Test
    fun `GIVEN a DoubleNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val doubleArray = doubleNativeArray(10)

        doubleArray[1] = 1.0

        assertEquals(1.0, doubleArray[1])

        ArrayTestLibrary.setDouble(doubleArray, 2, 2.0)

        assertEquals(2.0, ArrayTestLibrary.getDouble(doubleArray, 2))
        assertEquals(2.0, doubleArray[2])
    }

    @Test
    fun `GIVEN a DoubleNativeArray WHEN getting its size THEN it should return correct size`() {
        val doubleArray = doubleNativeArray(10)
        assertEquals(10, doubleArray.size)
        assertEquals(80, doubleArray.memory.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a DoubleArray WHEN constructing a IntNativeArray from the array THEN it should succeed`() {
        val doubleArray = doubleArrayOf(0.0, 10.0, 20.0, 30.0, 40.0)
        val nativeArray = doubleNativeArray(doubleArray)

        assertEquals(10.0, nativeArray[1])
        assertEquals(20.0, nativeArray[2])
    }

    @Test
    fun `GIVEN a DoubleArray when calling toTypedArray THEN it should return a typed array`() {
        val array = doubleArrayOf(0.0, 10.0, 20.0, 30.0, 40.0)
        val nativeArray = doubleNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}