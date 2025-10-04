package memory

import com.github.callmephil.knr.runtime.memory.ARC
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ArcTests {
    @Test
    fun `GIVEN an ARC WHEN setting bytes THEN it should succeed`() {
        val bytes = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        val arc = ARC.shared(8)

        arc.setBytes(bytes)

        for(i in 0L ..< bytes.size) {
            assertEquals(i.toByte(), arc.getByte(i))
        }
    }

    @Test
    fun `GIVEN an ARC WHEN setting offset bytes THEN it should succeed`() {
        val bytes = byteArrayOf(2, 3, 4, 5, 6, 7)
        val arc = ARC.shared(8)

        arc.setBytes(2, bytes, 0, bytes.size.toLong())

        assertEquals(0, arc.getByte(0))
        assertEquals(0, arc.getByte(1))

        for(i in 2L ..< bytes.size) {
            assertEquals(i.toByte(), arc.getByte(i))
        }
    }

    @Test
    fun `GIVEN an ARC WHEN setting too many bytes THEN it should throw`() {
        val bytes = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        val arc = ARC.shared(7)

        assertFailsWith<IndexOutOfBoundsException> { arc.setBytes(bytes) }
    }
}