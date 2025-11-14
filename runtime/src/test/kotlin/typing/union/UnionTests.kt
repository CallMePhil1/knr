package typing.union

import knr.runtime.typing.pointer.intPointerOf
import knr.runtime.typing.pointer.longPointerOf
import kotlin.test.Test
import kotlin.test.assertEquals

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
        assertEquals(1000L, union.p!!.get())
        assertEquals(1000, union.p1!!.get())

        union.p = null

        assertEquals(null, union.p)

        union.p = longPointerOf(2000L)

        assertEquals(union.p!!.memory.memorySegment!!.address(), union.l)
        assertEquals(2000L, UnionTestLibrary.getLongFromPointer(union))
        assertEquals(2000L, union.p!!.get())
        assertEquals(2000, union.p1!!.get())

        union.p1 = intPointerOf(1000)

        assertEquals(union.p1!!.memory.memorySegment!!.address(), union.l)
        assertEquals(1000, UnionTestLibrary.getIntFromPointer(union))
        assertEquals(1000, union.p1!!.get())
    }

    @Test
    fun `GIVEN a UnionStruct WHEN accessing or modifying a inner union THEN it should succeed`() {
        val union = UnionStruct.allocate()

        union.u.s = 1000

        assertEquals(1000, union.s)
        assertEquals(union.s, union.u.s)

        union.u.l = 100000

        assertEquals(100000, union.u.l)
        assertEquals(union.u.l, union.i.toLong())
    }
}