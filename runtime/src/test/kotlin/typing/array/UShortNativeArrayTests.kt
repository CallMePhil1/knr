package typing.array

import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import com.github.callmephil.knr.runtime.typing.array.ushortNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals

class UShortNativeArrayTests {
    @Test
    fun `GIVEN a UShortNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val ushortArray = ushortNativeArray(10)

        ushortArray[1] = 1u

        assertEquals(1u, ushortArray[1])

        ArrayTestLibrary.setUShort(ushortArray, 2, 2u)

        assertEquals(2u, ArrayTestLibrary.getUShort(ushortArray, 2))
        assertEquals(2u, ushortArray[2])
    }

    @Test
    fun `GIVEN a UShortNativeArray WHEN getting its size THEN it should return correct size`() {
        val shortArray = ushortNativeArray(10)
        assertEquals(10, shortArray.size)
        assertEquals(20, shortArray.arc.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a UShortArray WHEN constructing a UShortNativeArray from the array THEN it should succeed`() {
        val shortArray = ushortArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = ushortNativeArray(shortArray)

        assertEquals(10u, nativeArray[1])
        assertEquals(20u, nativeArray[2])
    }

    @Test
    fun `GIVEN a UByteArray when calling toTypedArray THEN it should return a typed array`() {
        val array = ushortArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = ushortNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}