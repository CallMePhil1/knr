package string

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.cachedCStringOf
import java.lang.foreign.Arena
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.Test
import kotlin.use

class CachedCStringTests {
    @Test
    fun `GIVEN different CachedCStrings with the same value WHEN comparing they are equal THEN it should return true`() {
        val str1 = cachedCStringOf("Hello")
        val str2 = cachedCStringOf("Hello")

        assertTrue(StringLib.equal(str1, str2))
        assertEquals(str1.get(), str2.get())
    }

    @Test
    fun `GIVEN a CachedCString WHEN getting its length THEN it should succeed`() {
        val str = cachedCStringOf("123456")

        assertEquals(6L, StringLib.length(str))
    }

    @Test
    fun `GIVEN a CachedCString WHEN pointing to another ARC THEN it should succeed`() {
        val str = cachedCStringOf("")

        assertEquals("", str.get())

        val arc = ARC.string("testing", Charsets.UTF_8)

        str.pointTo(arc)
        str.updateCache()
        assertEquals("testing", str.get())
    }

    @Test
    fun `GIVEN a CachedCString WHEN trying to update its value THEN it should fail`() {
        val str = cachedCStringOf("testing")

        assertEquals(str.get(), "testing")
        assertFailsWith<NotImplementedError> { str.set("newValue") }
    }

    @Test
    fun `GIVEN a StringStruct WHEN comparing to a equal string THEN it should succeed`() {
        Arena.ofConfined().use {
            val struct = StringStruct.allocate(it)

            struct.cachedStrPointer = cachedCStringOf("testing")
            struct.cachedStrPointer.updateCache()

            assertEquals("testing", struct.cachedStrPointer.get())
        }
    }

    @Test
    fun `GIVEN a StringStruct WHEN setting a new CString THEN it should update the pointer`() {
        Arena.ofConfined().use {
            val struct = StringStruct.allocate(it)
            val pointer1 = cachedCStringOf("testing")
            val pointer1Address = pointer1.arc!!.memorySegment!!.address()
            val pointer2 = cachedCStringOf("testing2")
            val pointer2Address = pointer2.arc!!.memorySegment!!.address()

            struct.cachedStrPointer = pointer1
            struct.cachedStrPointer.updateCache()

            assertEquals("testing", struct.cachedStrPointer.get())
            assertEquals(struct.arc.getAddress(8).address(), pointer1Address)

            struct.cachedStrPointer = pointer2
            struct.cachedStrPointer.updateCache()

            assertEquals("testing2", struct.cachedStrPointer.get())
            assertEquals(struct.arc.getAddress(8).address(), pointer2Address)
        }
    }
}