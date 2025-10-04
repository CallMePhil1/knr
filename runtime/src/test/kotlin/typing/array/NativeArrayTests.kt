package typing.array

import com.github.callmephil.knr.runtime.typing.array.NativeArray
import com.github.callmephil.knr.runtime.typing.array.byteNativeArray
import com.github.callmephil.knr.runtime.typing.array.intNativeArray
import com.github.callmephil.knr.runtime.typing.array.shortNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NativeArrayTests {
    @Test
    fun `GIVEN a NativeArray WHEN getting or setting a value out of bounds THEN it should throw`() {
        val shortArray = shortNativeArray(1) as NativeArray<Short, ShortArray>

        assertEquals(0, shortArray[0])
        assertFailsWith<IndexOutOfBoundsException> { shortArray[1] }
        assertFailsWith<IndexOutOfBoundsException> { shortArray[1] = 2 }
    }

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
        val shortArray = intNativeArray(10)
        assertEquals(10, shortArray.size)
        assertEquals(40, shortArray.arc.memorySegment!!.byteSize())
    }

    @Test
    fun `GIVEN a IntArray WHEN constructing a IntNativeArray from the array THEN it should succeed`() {
        val shortArray = intArrayOf(0, 10, 20, 30, 40)
        val nativeArray = intNativeArray(shortArray)

        assertEquals(10, nativeArray[1])
        assertEquals(20, nativeArray[2])
    }
}