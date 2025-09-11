package primitive

import java.lang.foreign.Arena
import kotlin.test.Test

class PrimitiveStructTests {
    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a bool THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.b = true

            assert(structProperty.b)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setBool(structMethod, true)
            assert(PrimitiveTestLibrary.getBool(structMethod))
            assert(structMethod.b)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a byte THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.c = 100

            assert(structProperty.c == 100.toByte())

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setByte(structMethod, 120.toByte())
            assert(PrimitiveTestLibrary.getByte(structMethod) == 120.toByte())
            assert(structMethod.c == 120.toByte())
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a ubyte THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.uc = 100.toUByte()

            assert(structProperty.uc == 100.toUByte())

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setUByte(structMethod, 200.toUByte())
            assert(PrimitiveTestLibrary.getUByte(structMethod) == 200.toUByte())
            assert(structMethod.uc == 200.toUByte())
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a short THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.s = -20000

            assert(structProperty.s == (-20000).toShort())

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setShort(structMethod, -20000)
            assert(PrimitiveTestLibrary.getShort(structMethod) == (-20000).toShort())
            assert(structMethod.s == (-20000).toShort())
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a ushort THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.us = 60000.toUShort()

            assert(structProperty.us == 60000.toUShort())

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setUShort(structMethod, 60000.toUShort())
            assert(PrimitiveTestLibrary.getUShort(structMethod) == 60000.toUShort())
            assert(structMethod.us == 60000.toUShort())
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a int THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.i = -2000000

            assert(structProperty.i == -2000000)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setInt(structMethod, -2000000)
            assert(PrimitiveTestLibrary.getInt(structMethod) == -2000000)
            assert(structMethod.i == -2000000)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a uint THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.ui = 3_000_000_000u

            assert(structProperty.ui == 3_000_000_000u)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setUInt(structMethod, 3_000_000_000u)
            assert(PrimitiveTestLibrary.getUInt(structMethod) == 3_000_000_000u)
            assert(structMethod.ui == 3_000_000_000u)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a long THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.l = -2000000

            assert(structProperty.l == -2000000)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setLong(structMethod, -2000000)
            assert(PrimitiveTestLibrary.getLong(structMethod) == -2000000)
            assert(structMethod.l == -2000000)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a ulong THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.ul = 3_000_000_000u

            assert(structProperty.ul == 3_000_000_000u)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setULong(structMethod, 3_000_000_000u)
            assert(PrimitiveTestLibrary.getULong(structMethod) == 3_000_000_000u)
            assert(structMethod.ul == 3_000_000_000u)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a longlong THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.ll = -2_000_000_000_000

            assert(structProperty.ll == -2_000_000_000_000)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setLongLong(structMethod, -2_000_000_000_000)
            assert(PrimitiveTestLibrary.getLongLong(structMethod) == -2_000_000_000_000)
            assert(structMethod.ll == -2_000_000_000_000)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a ulonglong THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.ull = 3_000_000_000_000u

            assert(structProperty.ull == 3_000_000_000_000u)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setULongLong(structMethod, 3_000_000_000_000u)
            assert(PrimitiveTestLibrary.getULongLong(structMethod) == 3_000_000_000_000u)
            assert(structMethod.ull == 3_000_000_000_000u)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a float THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.f = -2000.234f

            assert(structProperty.f == -2000.234f)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setFloat(structMethod, -2000.234f)
            assert(PrimitiveTestLibrary.getFloat(structMethod) == -2000.234f)
            assert(structMethod.f == -2000.234f)
        }
    }

    @Test
    fun `GIVEN a PrimitiveStruct WHEN setting and getting a double THEN it should work`() {
        Arena.ofConfined().use {
            val structProperty = PrimitiveStruct.allocate(it)
            structProperty.d = -3000000.765

            assert(structProperty.d == -3000000.765)

            val structMethod = PrimitiveStruct.allocate(it)

            PrimitiveTestLibrary.setDouble(structMethod, -3000000.765)
            assert(PrimitiveTestLibrary.getDouble(structMethod) == -3000000.765)
            assert(structMethod.d == -3000000.765)
        }
    }
}