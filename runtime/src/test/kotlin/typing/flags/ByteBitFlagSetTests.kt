package typing.flags

import knr.runtime.typing.flags.BitFlag
import knr.runtime.typing.flags.ByteBitFlagSet
import knr.runtime.typing.flags.entries
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

enum class ByteTestBitFlags(override val mask: Byte) : BitFlag<Byte> {
    FIRST(1),
    SECOND((1 shl 1).toByte()),
    THIRD((1 shl 2).toByte()),
    LAST((1 shl 3).toByte())
}

class ByteBitFlagSetTests {
    @Test
    fun `GIVEN WHEN an ByteBitFlagSet from calling of THEN it should work`() {
        val bitSet = ByteBitFlagSet.of(ByteTestBitFlags.FIRST, ByteTestBitFlags.THIRD)

        assertTrue(bitSet.has(ByteTestBitFlags.FIRST, ByteTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(ByteTestBitFlags.SECOND, ByteTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN an ByteBitFlagSet with entries WHEN calling entries THEN it should return list with entries`() {
        val bitSet = ByteBitFlagSet.of(ByteTestBitFlags.FIRST, ByteTestBitFlags.THIRD)
        val entries = bitSet.entries()

        assertContentEquals(listOf(ByteTestBitFlags.FIRST, ByteTestBitFlags.THIRD), entries)
    }

    @Test
    fun `GIVEN an ByteBitFlagSet WHEN calling on and off and toggle THEN it should set bit to on`() {
        var bitSet = ByteBitFlagSet.of(ByteTestBitFlags.FIRST, ByteTestBitFlags.SECOND)
        bitSet = bitSet.on(ByteTestBitFlags.THIRD)

        assertTrue(bitSet.has(ByteTestBitFlags.FIRST, ByteTestBitFlags.SECOND, ByteTestBitFlags.THIRD))

        bitSet = bitSet.off(ByteTestBitFlags.FIRST)

        assertTrue(bitSet.has(ByteTestBitFlags.SECOND, ByteTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(ByteTestBitFlags.FIRST))

        bitSet = bitSet.toggle(ByteTestBitFlags.FIRST, ByteTestBitFlags.SECOND)

        assertTrue(bitSet.has(ByteTestBitFlags.FIRST, ByteTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(ByteTestBitFlags.SECOND, ByteTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN WHEN an ByteBitFlagSet from calling allOf THEN it should contain all entries`() {
        val bitSet = ByteBitFlagSet.allOf<ByteTestBitFlags>()

        assertTrue(bitSet.has(ByteTestBitFlags.FIRST, ByteTestBitFlags.SECOND, ByteTestBitFlags.THIRD, ByteTestBitFlags.LAST))
    }
}