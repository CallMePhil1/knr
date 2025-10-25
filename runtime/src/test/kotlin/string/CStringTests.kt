package string

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.cstringOf
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
    fun `GIVEN a CString WHEN pointing to another ArenaMemory THEN it should succeed`() {
        val str = cstringOf("")

        val memory = ArenaMemory.string("testing", Charsets.UTF_8)

        assertEquals("", str.get())

        str.pointTo(memory)
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

    @Test
    fun `GIVEN a StringStruct WHEN setting a new CString THEN it should update the pointer`() {
        Arena.ofConfined().use {
            val struct = StringStruct.allocate(it)
            val pointer1 = cstringOf("testing")
            val pointer1Address = pointer1.memory!!.memorySegment!!.address()
            val pointer2 = cstringOf("testing2")
            val pointer2Address = pointer2.memory!!.memorySegment!!.address()

            struct.strPointer = pointer1

            assertEquals("testing", struct.strPointer.get())
            assertEquals(struct.memory.getAddress(0).address(), pointer1Address)

            struct.strPointer = pointer2

            assertEquals("testing2", struct.strPointer.get())
            assertEquals(struct.memory.getAddress(0).address(), pointer2Address)
        }
    }
}