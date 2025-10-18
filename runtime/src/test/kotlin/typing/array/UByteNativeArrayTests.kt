package typing.array

import com.github.callmephil.knr.runtime.typing.array.toTypedArray
import com.github.callmephil.knr.runtime.typing.array.ubyteNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals

class UByteNativeArrayTests {
    @Test
    fun `GIVEN a UByteNativeArray WHEN getting and setting a value THEN it should succeed`() {
        val ubyteArray = ubyteNativeArray(10)

        ubyteArray[1] = 1u

        assertEquals(1u, ubyteArray[1])

        ArrayTestLibrary.setUByte(ubyteArray, 2, 2u)

        assertEquals(2u, ArrayTestLibrary.getUByte(ubyteArray, 2))
        assertEquals(2u, ubyteArray[2])
    }

    @Test
    fun `GIVEN a UByteNativeArray WHEN getting its size THEN it should return correct size`() {
        val ubyteArray = ubyteNativeArray(10)
        assertEquals(10, ubyteArray.size)
        assertEquals(10, ubyteArray.arc.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a UByteArray WHEN constructing a UByteNativeArray from the array THEN it should succeed`() {
        val ubyteArray = ubyteArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = ubyteNativeArray(ubyteArray)

        assertEquals(10u, nativeArray[1])
        assertEquals(20u, nativeArray[2])
    }

    @Test
    fun `GIVEN a UByteArray when calling toTypedArray THEN it should return a typed array`() {
        val array = ubyteArrayOf(0u, 10u, 20u, 30u, 40u)
        val nativeArray = ubyteNativeArray(array)

        val typedArray = nativeArray.toTypedArray()

        for (i in 0 ..< array.size) {
            assertEquals(array[i], typedArray[i])
        }
    }
}