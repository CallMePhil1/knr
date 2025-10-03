package string

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.cstringOf
import com.github.callmephil.knr.runtime.typing.nullableCStringOf
import java.lang.foreign.Arena
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CStringTests {
    @Test
    fun `GIVEN different strings with the same value WHEN comparing they are equal THEN it should return true`() {
        val str1 = cstringOf("Hello")
        val cstring = cstringOf("Hello")

        assertTrue(StringLib.equal(str1, cstring))
    }

    @Test
    fun `GIVEN a CString WHEN getting its length THEN it should succeed`() {
        val str = cstringOf("123456")

        assertEquals(StringLib.length(str), 6L)
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
    fun `GIVEN different NullableCStrings with the same value WHEN comparing they are equal THEN it should return true`() {
        val str1 = nullableCStringOf("Hello")
        val cstring = nullableCStringOf("Hello")

        assertTrue(StringLib.equal(str1, cstring))
    }

    @Test
    fun `GIVEN a NullableCStrings WHEN getting its length null or not THEN it should succeed`() {
        val str = nullableCStringOf("123456")
        val nullstr = nullableCStringOf()

        assertEquals(StringLib.length(str), 6L)
        assertFailsWith<NullPointerException> { StringLib.length(nullstr) }
    }

    @Test
    fun `GIVEN a NullableCStrings WHEN pointing to another ARC THEN it should succeed`() {
        val str = nullableCStringOf("")

        assertEquals("", str.get())

        val arc = ARC.string("testing", Charsets.UTF_8)
        str.pointTo(arc)
        assertEquals("testing", str.get())

        str.pointTo(ARC.ofNull())
        assertFailsWith<NullPointerException> { str.get() }
    }

    @Test
    fun `GIVEN a NullableCStrings WHEN trying to update its value THEN it should fail`() {
        val str = nullableCStringOf("testing")

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

    @Test
    fun `GIVEN a StringStruct WHEN setting a new CString THEN it should update the pointer`() {
        Arena.ofConfined().use {
            val struct = StringStruct.allocate(it)
            val pointer1 = cstringOf("testing")
            val pointer2 = cstringOf("testing2")

            struct.strPointer = pointer1

            assertEquals("testing", struct.strPointer.get())
            assertEquals(struct.arc.getAddress(0).address(), pointer1.arc!!.memorySegment!!.address())

            struct.strPointer = pointer2

            assertEquals("testing2", struct.strPointer.get())
            assertEquals(struct.arc.getAddress(0).address(), pointer2.arc!!.memorySegment!!.address())
        }
    }
}