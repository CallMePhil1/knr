package typing.array

import com.github.callmephil.knr.runtime.typing.array.ccharNativeArray
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CCharNativeArrayTests {
    @Test
    fun `GIVEN WHEN constructing a CCharNativeArray THEN it should succeed`() {
        val array = ccharNativeArray("testing", Charsets.UTF_8)
        assertEquals("testing", array.getString())
    }

    @Test
    fun `GIVEN a CCharNativeArray WHEN updating the string THEN it should succeed`() {
        val array = ccharNativeArray(11, Charsets.UTF_8)
        array.set("anotherone")

        assertEquals("anotherone", array.getString())
        assertFails { array.set("01234567890") }
    }

    @Test
    fun `GIVEN a byte array of utf 8 WHEN constructing a CCharNativeArray THEN it should succeed`() {
        val bytes = Charsets.UTF_8.encode("testing\u0000").array()
        val array = ccharNativeArray(bytes, Charsets.UTF_8)

        assertEquals("testing", array.getString())
    }
}