package string

import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.cstringOf
import kotlin.test.Test
import kotlin.test.assertEquals
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
}