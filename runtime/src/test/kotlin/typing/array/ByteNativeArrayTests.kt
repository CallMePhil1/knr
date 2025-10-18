package typing.array

import com.github.callmephil.knr.runtime.typing.array.byteNativeArray
import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteNativeArrayTests {
    @Test
    fun `GIVEN a ByteNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val byteArray = byteNativeArray(10)

        byteArray[1] = 1

        assertEquals(1, byteArray[1])

        ArrayTestLibrary.setByte(byteArray, 2, 2)

        assertEquals(2, ArrayTestLibrary.getByte(byteArray, 2))
        assertEquals(2, byteArray[2])
    }

    @Test
    fun `GIVEN a ByteNativeArray WHEN getting its size THEN it should return correct size`() {
        val byteArray = byteNativeArray(10)
        assertEquals(10, byteArray.size)
        assertEquals(10, byteArray.arc.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a ByteArray WHEN constructing a ByteNativeArray from the array THEN it should succeed`() {
        val byteArray = byteArrayOf(0, 10, 20, 30, 40)
        val nativeArray = byteNativeArray(byteArray)

        assertEquals(10, nativeArray[1])
        assertEquals(20, nativeArray[2])
    }

    @Test
    fun `GIVEN a ByteArray when calling toTypedArray THEN it should return a typed array`() {
        val array = byteArrayOf(0, 10, 20, 30, 40)
        val nativeArray = byteNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}