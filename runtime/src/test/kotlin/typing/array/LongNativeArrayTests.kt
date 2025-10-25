package typing.array

import com.github.callmephil.knr.runtime.typing.array.longNativeArray
import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import kotlin.test.Test
import kotlin.test.assertEquals

class LongNativeArrayTests {
    @Test
    fun `GIVEN a LongNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val longArray = longNativeArray(10)

        longArray[1] = 1

        assertEquals(1, longArray[1])

        ArrayTestLibrary.setLong(longArray, 2, 2)

        assertEquals(2, ArrayTestLibrary.getLong(longArray, 2))
        assertEquals(2, longArray[2])
    }

    @Test
    fun `GIVEN a LongNativeArray WHEN getting its size THEN it should return correct size`() {
        val longArray = longNativeArray(10)
        assertEquals(10, longArray.size)
        assertEquals(80, longArray.memory.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a LongArray WHEN constructing a LongNativeArray from the array THEN it should succeed`() {
        val longArray = longArrayOf(0, 10, 20, 30, 40)
        val nativeArray = longNativeArray(longArray)

        assertEquals(10, nativeArray[1])
        assertEquals(20, nativeArray[2])
    }

    @Test
    fun `GIVEN a LongArray when calling toTypedArray THEN it should return a typed array`() {
        val array = longArrayOf(0, 10, 20, 30, 40)
        val nativeArray = longNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}