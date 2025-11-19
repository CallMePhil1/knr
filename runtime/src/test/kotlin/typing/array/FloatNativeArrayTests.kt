package typing.array

import knr.runtime.typing.array.floatNativeArray
import knr.runtime.typing.array.toTypedArray
import kotlin.test.Test
import kotlin.test.assertEquals

class FloatNativeArrayTests {
    @Test
    fun `GIVEN a FloatNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val floatArray = floatNativeArray(10)

        floatArray[1] = 1f

        assertEquals(1f, floatArray[1])

        ArrayTestLibrary.setFloat(floatArray, 2, 2f)

        assertEquals(2f, ArrayTestLibrary.getFloat(floatArray, 2))
        assertEquals(2f, floatArray[2])
    }

    @Test
    fun `GIVEN a FloatNativeArray WHEN getting its size THEN it should return correct size`() {
        val floatArray = floatNativeArray(10)
        assertEquals(10, floatArray.size)
        assertEquals(40, floatArray.memory.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a FloatArray WHEN constructing a IntNativeArray from the array THEN it should succeed`() {
        val floatArray = floatArrayOf(0f, 10f, 20f, 30f, 40f)
        val nativeArray = floatNativeArray(floatArray)

        assertEquals(10f, nativeArray[1])
        assertEquals(20f, nativeArray[2])
    }

    @Test
    fun `GIVEN a FloatArray when calling toTypedArray THEN it should return a typed array`() {
        val array = floatArrayOf(0f, 10f, 20f, 30f, 40f)
        val nativeArray = floatNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}