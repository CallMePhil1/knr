@file:OptIn(ExperimentalUnsignedTypes::class)

package typing.array

import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import com.github.callmephil.knr.runtime.typing.array.uintNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals

class UIntNativeArrayTests {
    @Test
    fun `GIVEN a UIntNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val uintArray = uintNativeArray(10)

        uintArray[1] = 1u

        assertEquals(1u, uintArray[1])

        ArrayTestLibrary.setUInt(uintArray, 2, 2u)

        assertEquals(2u, ArrayTestLibrary.getUInt(uintArray, 2))
        assertEquals(2u, uintArray[2])
    }

    @Test
    fun `GIVEN a UIntNativeArray WHEN getting its size THEN it should return correct size`() {
        val uintArray = uintNativeArray(10)
        assertEquals(10, uintArray.size)
        assertEquals(40, uintArray.memory.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a UIntArray WHEN constructing a UIntNativeArray from the array THEN it should succeed`() {
        val uintArray = uintArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = uintNativeArray(uintArray)

        assertEquals(10u, nativeArray[1])
        assertEquals(20u, nativeArray[2])
    }

    @Test
    fun `GIVEN a UIntArray when calling toTypedArray THEN it should return a typed array`() {
        val array = uintArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = uintNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}