package typing.flags

import com.github.callmephil.knr.runtime.typing.flags.BitFlag
import com.github.callmephil.knr.runtime.typing.flags.ShortBitFlagSet
import com.github.callmephil.knr.runtime.typing.flags.entries
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

enum class ShortTestBitFlags(override val mask: Short) : BitFlag<Short> {
    FIRST(1),
    SECOND((1 shl 1).toShort()),
    THIRD((1 shl 2).toShort()),
    LAST((1 shl 3).toShort())
}

class ShortBitFlagSetTests {
    @Test
    fun `GIVEN WHEN an ShortBitFlagSet from calling of THEN it should work`() {
        val bitSet = ShortBitFlagSet.of(ShortTestBitFlags.FIRST, ShortTestBitFlags.THIRD)

        assertTrue(bitSet.has(ShortTestBitFlags.FIRST, ShortTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(ShortTestBitFlags.SECOND, ShortTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN an ShortBitFlagSet with entries WHEN calling entries THEN it should return list with entries`() {
        val bitSet = ShortBitFlagSet.of(ShortTestBitFlags.FIRST, ShortTestBitFlags.THIRD)
        val entriesResult = bitSet.entries()

        assertTrue(entriesResult.isSuccess)

        val entries = entriesResult.getOrThrow()

        assertContentEquals(listOf(ShortTestBitFlags.FIRST, ShortTestBitFlags.THIRD), entries)
    }

    @Test
    fun `GIVEN an ShortBitFlagSet WHEN calling on and off and toggle THEN it should set bit to on`() {
        var bitSet = ShortBitFlagSet.of(ShortTestBitFlags.FIRST, ShortTestBitFlags.SECOND)
        bitSet = bitSet.on(ShortTestBitFlags.THIRD)

        assertTrue(bitSet.has(ShortTestBitFlags.FIRST, ShortTestBitFlags.SECOND, ShortTestBitFlags.THIRD))

        bitSet = bitSet.off(ShortTestBitFlags.FIRST)

        assertTrue(bitSet.has(ShortTestBitFlags.SECOND, ShortTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(ShortTestBitFlags.FIRST))

        bitSet = bitSet.toggle(ShortTestBitFlags.FIRST, ShortTestBitFlags.SECOND)

        assertTrue(bitSet.has(ShortTestBitFlags.FIRST, ShortTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(ShortTestBitFlags.SECOND, ShortTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN WHEN an ShortBitFlagSet from calling allOf THEN it should contain all entries`() {
        val bitSet = ShortBitFlagSet.allOf<ShortTestBitFlags>()

        assertTrue(bitSet.has(ShortTestBitFlags.FIRST, ShortTestBitFlags.SECOND, ShortTestBitFlags.THIRD, ShortTestBitFlags.LAST))
    }
}