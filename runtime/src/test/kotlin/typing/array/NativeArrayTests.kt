@file:OptIn(ExperimentalUnsignedTypes::class)

package typing.array

import knr.runtime.typing.array.NativeArray
import knr.runtime.typing.array.byteNativeArray
import knr.runtime.typing.array.shortNativeArray
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
    fun `GIVEN a NativeArray WHEN setting bytes THEN it should succeed`() {
        val byteArray = byteNativeArray(10) as NativeArray<Byte, ByteArray>

        byteArray.set(ByteArray(10) { 0 })
    }

    @Test
    fun `GIVEN a NativeArray WHEN iterating over it THEN it should succeed`() {
        val shortArray = shortArrayOf(0, 5000, 10000, 15000, 20000, 25000, 30000)
        val nativeArray = shortNativeArray(shortArray)

        nativeArray.forEachIndexed { idx, byte ->
            assertEquals((idx * 5000).toShort(), byte)
        }
    }
}