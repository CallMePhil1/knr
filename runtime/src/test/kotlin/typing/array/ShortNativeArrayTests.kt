package typing.array

import com.github.callmephil.knr.runtime.typing.array.shortNativeArray
import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ShortNativeArrayTests {
    @Test
    fun `GIVEN a ShortNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val shortArray = shortNativeArray(10)

        shortArray[1] = 1

        assertEquals(1, shortArray[1])

        ArrayTestLibrary.setShort(shortArray, 2, 2)

        assertEquals(2, ArrayTestLibrary.getShort(shortArray, 2))
        assertEquals(2, shortArray[2])
    }

    @Test
    fun `GIVEN a ShortNativeArray WHEN getting its size THEN it should return correct size`() {
        val shortArray = shortNativeArray(10)
        assertEquals(10, shortArray.size)
        assertEquals(20, shortArray.arc.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a ShortArray WHEN constructing a ShortNativeArray from the array THEN it should succeed`() {
        val shortArray = shortArrayOf(0, 10, 20, 30, 40)
        val nativeArray = shortNativeArray(shortArray)

        assertEquals(10, nativeArray[1])
        assertEquals(20, nativeArray[2])
    }

    @Test
    fun `GIVEN a ShortArray when calling toTypedArray THEN it should return a typed array`() {
        val array = shortArrayOf(0, 10, 20, 30, 40)
        val nativeArray = shortNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}