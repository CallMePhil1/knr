package primitives

import knr.runtime.library.LibraryLoader
import org.junit.Test
import kotlin.test.assertEquals

class PrimitiveTests {
    @Test
    fun `GIVEN a library WHEN using primitive functions THEN it should work`() {
        val library = LibraryLoader.loadLibrary<PrimitiveTestLibrary>()

        val struct = PrimitiveStruct.allocate()
        val structPtr = struct.asPointer()

        struct.b = true
        assertEquals(true, library.getBool(structPtr))
        library.setBool(structPtr, false)
        assertEquals(false, library.getBool(structPtr))

        struct.c = 50
        assertEquals(50, library.getByte(structPtr))
        library.setByte(structPtr, 100)
        assertEquals(100, library.getByte(structPtr))

        struct.s = 500
        assertEquals(500, library.getShort(structPtr))
        library.setShort(structPtr, 1000)
        assertEquals(1000, library.getShort(structPtr))

        struct.i = 500_000
        assertEquals(500_000, library.getInt(structPtr))
        library.setInt(structPtr, 1_000_000)
        assertEquals(1_000_000, library.getInt(structPtr))

        struct.l = 5_000_000_000L
        assertEquals(5_000_000_000L, library.getLong(structPtr))
        library.setLong(structPtr, 10_000_000_000L)
        assertEquals(10_000_000_000L, library.getLong(structPtr))
    }
}