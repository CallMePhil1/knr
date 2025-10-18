package typing.array

import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import com.github.callmephil.knr.runtime.typing.array.ulongNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ULongNativeArrayTests {
    @Test
    fun `GIVEN a ULongNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val ulongArray = ulongNativeArray(10)

        ulongArray[1] = 1u

        assertEquals(1u, ulongArray[1])

        ArrayTestLibrary.setULong(ulongArray, 2, 2u)

        assertEquals(2u, ArrayTestLibrary.getULong(ulongArray, 2))
        assertEquals(2u, ulongArray[2])
    }

    @Test
    fun `GIVEN a ULongNativeArray WHEN getting its size THEN it should return correct size`() {
        val ulongArray = ulongNativeArray(10)
        assertEquals(10, ulongArray.size)
        assertEquals(80, ulongArray.arc.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a ULongArray WHEN constructing a ULongNativeArray from the array THEN it should succeed`() {
        val longArray = ulongArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = ulongNativeArray(longArray)

        assertEquals(10u, nativeArray[1])
        assertEquals(20u, nativeArray[2])
    }

    @Test
    fun `GIVEN a ULongArray when calling toTypedArray THEN it should return a typed array`() {
        val array = ulongArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = ulongNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}