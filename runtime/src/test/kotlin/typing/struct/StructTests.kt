package typing.struct

import kotlin.test.Test
import kotlin.test.assertEquals

class StructTests {
    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a bool THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.b = true

        assert(structProperty.b)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setBool(structMethod, true)
        assert(StructTestLibrary.getBool(structMethod))
        assert(structMethod.b)
        
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a byte THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.c = 100

        assert(structProperty.c == 100.toByte())

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setByte(structMethod, 120.toByte())
        assert(StructTestLibrary.getByte(structMethod) == 120.toByte())
        assert(structMethod.c == 120.toByte())
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a ubyte THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.uc = 100.toUByte()

        assert(structProperty.uc == 100.toUByte())

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setUByte(structMethod, 200.toUByte())
        assert(StructTestLibrary.getUByte(structMethod) == 200.toUByte())
        assert(structMethod.uc == 200.toUByte())
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a short THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.s = -20000

        assert(structProperty.s == (-20000).toShort())

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setShort(structMethod, -20000)
        assert(StructTestLibrary.getShort(structMethod) == (-20000).toShort())
        assert(structMethod.s == (-20000).toShort())
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a ushort THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.us = 60000.toUShort()

        assert(structProperty.us == 60000.toUShort())

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setUShort(structMethod, 60000.toUShort())
        assert(StructTestLibrary.getUShort(structMethod) == 60000.toUShort())
        assert(structMethod.us == 60000.toUShort())
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a int THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.i = -2000000

        assert(structProperty.i == -2000000)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setInt(structMethod, -2000000)
        assert(StructTestLibrary.getInt(structMethod) == -2000000)
        assert(structMethod.i == -2000000)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a uint THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.ui = 3_000_000_000u

        assert(structProperty.ui == 3_000_000_000u)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setUInt(structMethod, 3_000_000_000u)
        assert(StructTestLibrary.getUInt(structMethod) == 3_000_000_000u)
        assert(structMethod.ui == 3_000_000_000u)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a long THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.l = -2000000

        assert(structProperty.l == -2000000)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setLong(structMethod, -2000000)
        assert(StructTestLibrary.getLong(structMethod) == -2000000)
        assert(structMethod.l == -2000000)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a ulong THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.ul = 3_000_000_000u

        assert(structProperty.ul == 3_000_000_000u)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setULong(structMethod, 3_000_000_000u)
        assert(StructTestLibrary.getULong(structMethod) == 3_000_000_000u)
        assert(structMethod.ul == 3_000_000_000u)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a longlong THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.ll = -2_000_000_000_000

        assert(structProperty.ll == -2_000_000_000_000)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setLongLong(structMethod, -2_000_000_000_000)
        assert(StructTestLibrary.getLongLong(structMethod) == -2_000_000_000_000)
        assert(structMethod.ll == -2_000_000_000_000)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a ulonglong THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.ull = 3_000_000_000_000u

        assert(structProperty.ull == 3_000_000_000_000u)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setULongLong(structMethod, 3_000_000_000_000u)
        assert(StructTestLibrary.getULongLong(structMethod) == 3_000_000_000_000u)
        assert(structMethod.ull == 3_000_000_000_000u)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a float THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.f = -2000.234f

        assert(structProperty.f == -2000.234f)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setFloat(structMethod, -2000.234f)
        assert(StructTestLibrary.getFloat(structMethod) == -2000.234f)
        assert(structMethod.f == -2000.234f)
    }

    @Test
    fun `GIVEN a TestStruct WHEN setting and getting a double THEN it should work`() {
        val structProperty = TestStruct.allocate()
        structProperty.d = -3000000.765

        assertEquals(-3000000.765, structProperty.d)

        val structMethod = TestStruct.allocate()

        StructTestLibrary.setDouble(structMethod, -3000000.765)
        assertEquals(-3000000.765, StructTestLibrary.getDouble(structMethod))
        assertEquals(-3000000.765, structMethod.d)
    }
    
    @Test
    fun `GIVEN a TestStruct WHEN accessing or modifying a union THEN it should work`() {
        val struct = TestStruct.allocate()

        struct.u.l = 1_000_000

        assertEquals(1_000_000, struct.u.l)
        assertEquals(1_000_000, struct.u.i)
        assertEquals(1_000_000, StructTestLibrary.getLongFromUnion(struct))
    }
}