package primitives

import knr.runtime.library.LibraryLoader
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class PrimitiveTests {
    @BeforeTest
    fun setup() {
        LibraryLoader.addPaths("src/test/libraries/build/Debug/")
    }

    @Test
    fun `GIVEN a library WHEN using primitive functions THEN it should work`() {
        val struct = PrimitiveStruct.allocate()
        val structPtr = struct.asPointer()

        struct.b = true
        assertEquals(true, PrimitiveTestLibrary.getBool(structPtr))
        PrimitiveTestLibrary.setBool(structPtr, false)
        assertEquals(false, PrimitiveTestLibrary.getBool(structPtr))

        struct.c = 50
        assertEquals(50, PrimitiveTestLibrary.getByte(structPtr))
        PrimitiveTestLibrary.setByte(structPtr, 100)
        assertEquals(100, PrimitiveTestLibrary.getByte(structPtr))

        struct.s = 500
        assertEquals(500, PrimitiveTestLibrary.getShort(structPtr))
        PrimitiveTestLibrary.setShort(structPtr, 1000)
        assertEquals(1000, PrimitiveTestLibrary.getShort(structPtr))

        struct.i = 500_000
        assertEquals(500_000, PrimitiveTestLibrary.getInt(structPtr))
        PrimitiveTestLibrary.setInt(structPtr, 1_000_000)
        assertEquals(1_000_000, PrimitiveTestLibrary.getInt(structPtr))

        struct.l = 5_000_000_000L
        assertEquals(5_000_000_000L, PrimitiveTestLibrary.getLong(structPtr))
        PrimitiveTestLibrary.setLong(structPtr, 10_000_000_000L)
        assertEquals(10_000_000_000L, PrimitiveTestLibrary.getLong(structPtr))
    }
}