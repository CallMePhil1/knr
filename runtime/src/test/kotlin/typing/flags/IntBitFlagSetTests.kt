package typing.flags

import knr.runtime.typing.flags.BitFlag
import knr.runtime.typing.flags.IntBitFlagSet
import knr.runtime.typing.flags.entries
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

enum class IntTestBitFlags(override val mask: Int) : BitFlag<Int> {
    FIRST(1),
    SECOND(1 shl 1),
    THIRD(1 shl 2),
    LAST(1 shl 3)
}

class IntBitFlagSetTests {
    @Test
    fun `GIVEN WHEN an IntBitFlagSet from calling of THEN it should work`() {
        val bitSet = IntBitFlagSet.of(IntTestBitFlags.FIRST, IntTestBitFlags.THIRD)

        assertTrue(bitSet.has(IntTestBitFlags.FIRST, IntTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(IntTestBitFlags.SECOND, IntTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN an IntBitFlagSet with entries WHEN calling entries THEN it should return list with entries`() {
        val bitSet = IntBitFlagSet.of(IntTestBitFlags.FIRST, IntTestBitFlags.THIRD)
        val entries = bitSet.entries()

        assertContentEquals(listOf(IntTestBitFlags.FIRST, IntTestBitFlags.THIRD), entries)
    }

    @Test
    fun `GIVEN an IntBitFlagSet WHEN calling on and off and toggle THEN it should set bit to on`() {
        var bitSet = IntBitFlagSet.of(IntTestBitFlags.FIRST, IntTestBitFlags.SECOND)
        bitSet = bitSet.on(IntTestBitFlags.THIRD)

        assertTrue(bitSet.has(IntTestBitFlags.FIRST, IntTestBitFlags.SECOND, IntTestBitFlags.THIRD))

        bitSet = bitSet.off(IntTestBitFlags.FIRST)

        assertTrue(bitSet.has(IntTestBitFlags.SECOND, IntTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(IntTestBitFlags.FIRST))

        bitSet = bitSet.toggle(IntTestBitFlags.FIRST, IntTestBitFlags.SECOND)

        assertTrue(bitSet.has(IntTestBitFlags.FIRST, IntTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(IntTestBitFlags.SECOND, IntTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN WHEN an IntBitFlagSet from calling allOf THEN it should contain all entries`() {
        val bitSet = IntBitFlagSet.allOf<IntTestBitFlags>()

        assertTrue(bitSet.has(IntTestBitFlags.FIRST, IntTestBitFlags.SECOND, IntTestBitFlags.THIRD, IntTestBitFlags.LAST))
    }
}