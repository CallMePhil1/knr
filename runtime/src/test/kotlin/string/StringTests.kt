package string

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.cstringOf
import java.lang.foreign.Arena
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringTests {
    @Test
    fun `GIVEN different strings with the same value WHEN comparing they are equal THEN it should return true`() {
        val str1 = cstringOf("Hello")
        val cstring = cstringOf("Hello")

        assertTrue(StringLib.stringCompare(str1, cstring))
    }

    @Test
    fun `GIVEN a CString WHEN getting its length THEN it should succeed`() {
        val str = cstringOf("123456")

        assertEquals(StringLib.stringLength(str), 6L)
    }

    @Test
    fun `GIVEN a CString WHEN pointing to another ARC THEN it should succeed`() {
        val str = cstringOf("")

        val arc = ARC.string("testing", Charsets.UTF_8)

        assertEquals("", str.get())

        str.pointTo(arc)
        assertEquals("testing", str.get())
    }

    @Test
    fun `GIVEN a CString WHEN trying to update its value THEN it should fail`() {
        val str = cstringOf("testing")

        assertEquals(str.get(), "testing")

        assertFailsWith<NotImplementedError> { str.set("newValue") }
    }

    @Test
    fun `GIVEN a StringStruct WHEN comparing to a equal string THEN it should succeed`() {
        Arena.ofConfined().use {
            val struct = StringStruct.allocate(it)

            struct.strPointer = cstringOf("testing")

            assertEquals("testing", struct.strPointer.get())

            val str = cstringOf("testing")

            assertTrue(StringTestLibrary.structStringEqual(struct, str))

            val str2 = cstringOf("Testing2")

            assertFalse(StringTestLibrary.structStringEqual(struct , str2))
        }
    }
}