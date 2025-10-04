package typing.array

import com.github.callmephil.knr.runtime.typing.array.byteNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NativeArrayTests {
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
    fun `GIVEN a ByteNativeArray WHEN getting or setting a value out of bounds THEN it should throw`() {
        val byteArray = byteNativeArray(1)

        assertFailsWith<IndexOutOfBoundsException> { byteArray[2] }
        assertFailsWith<IndexOutOfBoundsException> { byteArray[2] = 2 }
    }

    @Test
    fun `GIVEN a ByteNativeArray WHEN getting its size THEN it should return correct size`() {
        val byteArray = byteNativeArray(10)
        assertEquals(10, byteArray.size)
    }
}