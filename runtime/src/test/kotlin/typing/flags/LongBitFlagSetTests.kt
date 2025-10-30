package typing.flags

import com.github.callmephil.knr.runtime.typing.flags.BitFlag
import com.github.callmephil.knr.runtime.typing.flags.LongBitFlagSet
import com.github.callmephil.knr.runtime.typing.flags.entries
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

enum class LongTestBitFlags(override val mask: Long) : BitFlag<Long> {
    FIRST(1),
    SECOND((1 shl 1).toLong()),
    THIRD((1 shl 2).toLong()),
    LAST((1 shl 3).toLong())
}

class LongBitFlagSetTests {
    @Test
    fun `GIVEN WHEN an LongBitFlagSet from calling of THEN it should work`() {
        val bitSet = LongBitFlagSet.of(LongTestBitFlags.FIRST, LongTestBitFlags.THIRD)

        assertTrue(bitSet.has(LongTestBitFlags.FIRST, LongTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(LongTestBitFlags.SECOND, LongTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN an LongBitFlagSet with entries WHEN calling entries THEN it should return list with entries`() {
        val bitSet = LongBitFlagSet.of(LongTestBitFlags.FIRST, LongTestBitFlags.THIRD)
        val entriesResult = bitSet.entries()

        assertTrue(entriesResult.isSuccess)

        val entries = entriesResult.getOrThrow()

        assertContentEquals(listOf(LongTestBitFlags.FIRST, LongTestBitFlags.THIRD), entries)
    }

    @Test
    fun `GIVEN an LongBitFlagSet WHEN calling on and off and toggle THEN it should set bit to on`() {
        var bitSet = LongBitFlagSet.of(LongTestBitFlags.FIRST, LongTestBitFlags.SECOND)
        bitSet = bitSet.on(LongTestBitFlags.THIRD)

        assertTrue(bitSet.has(LongTestBitFlags.FIRST, LongTestBitFlags.SECOND, LongTestBitFlags.THIRD))

        bitSet = bitSet.off(LongTestBitFlags.FIRST)

        assertTrue(bitSet.has(LongTestBitFlags.SECOND, LongTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(LongTestBitFlags.FIRST))

        bitSet = bitSet.toggle(LongTestBitFlags.FIRST, LongTestBitFlags.SECOND)

        assertTrue(bitSet.has(LongTestBitFlags.FIRST, LongTestBitFlags.THIRD))
        assertTrue(bitSet.lacks(LongTestBitFlags.SECOND, LongTestBitFlags.LAST))
    }

    @Test
    fun `GIVEN WHEN an LongBitFlagSet from calling allOf THEN it should contain all entries`() {
        val bitSet = LongBitFlagSet.allOf<LongTestBitFlags>()

        assertTrue(bitSet.has(LongTestBitFlags.FIRST, LongTestBitFlags.SECOND, LongTestBitFlags.THIRD, LongTestBitFlags.LAST))
    }
}