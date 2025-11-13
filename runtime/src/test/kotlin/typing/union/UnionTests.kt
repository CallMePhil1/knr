package typing.union

import knr.runtime.typing.pointer.intPointerOf
import knr.runtime.typing.pointer.longPointerOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnionTests {
    @Test
    fun `GIVEN a UnionStruct WHEN accessing or modifying its fields THEN it should succeed`() {
        val union = UnionStruct.allocate()

        union.c = 120

        assertEquals(120.toByte(), union.c)
        assertEquals(120.toByte(), UnionTestLibrary.getByte(union))

        assertEquals(120, union.i)
        assertEquals(120, UnionTestLibrary.getInt(union))
    }

    @Test
    fun `GIVEN a UnionStruct WHEN accessing or modifying a pointer field THEN it should succeed`() {
        val union = UnionStruct.allocate()

        union.p = longPointerOf(1000L)

        assertEquals(union.p!!.memory.memorySegment!!.address(), union.l)
        assertEquals(1000L, UnionTestLibrary.getLongFromPointer(union))

        union.p = longPointerOf(2000L)

        assertEquals(union.p!!.memory.memorySegment!!.address(), union.l)
        assertEquals(2000L, UnionTestLibrary.getLongFromPointer(union))

        union.p1 = intPointerOf(1000)

        assertEquals(union.p1!!.memory.memorySegment!!.address(), union.l)
        assertEquals(1000, UnionTestLibrary.getIntFromPointer(union))
        assertNull(union.p)
    }
}