package pointer

import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class StructPointerTests {
    @Test
    fun `GIVEN a struct with a pointer WHEN setting and getting to null and a value THEN it should work`() {
        val allPointersDirect = AllPointers.allocateConfined()

        allPointersDirect.ni = intPointerOf(100)

        assertEquals(100, allPointersDirect.ni!!.get())

        allPointersDirect.ni = null

        assertFailsWith<NullPointerException> {
            allPointersDirect.ni!!.get()
        }
    }

    @Test
    fun `GIVEN a struct with a byte pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        allPointersDirect.b.set(100)
        assertEquals(100, allPointersDirect.b.get())

        val allPointersMethod = AllPointers.allocateShared()

        assertEquals(0, allPointersMethod.b.get())
        assertEquals(0, PointerTestLibrary.getByteViaPointerFromStruct(allPointersMethod))

        allPointersMethod.b.set(30)
        assertEquals(30, PointerTestLibrary.getByteViaPointerFromStruct(allPointersMethod))

        PointerTestLibrary.setByteViaPointerFromStruct(allPointersMethod, 100.toByte())
        assertEquals(100, PointerTestLibrary.getByteViaPointerFromStruct(allPointersMethod))
    }

    @Test
    fun `GIVEN a struct with a byte nullable pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        assertFails { allPointersDirect.nb!!.get() }
        assertFails { allPointersDirect.nb!!.set(100) }

        allPointersDirect.nb = bytePointerOf(0)

        assertEquals(0, allPointersDirect.nb!!.get())
        assertEquals(0, PointerTestLibrary.getNullableByteViaPointerFromStruct(allPointersDirect))

        allPointersDirect.nb!!.set(100.toByte())
        assertEquals(100,allPointersDirect.nb!!.get())
        assertEquals(100, PointerTestLibrary.getNullableByteViaPointerFromStruct(allPointersDirect))

        PointerTestLibrary.setNullableByteViaPointerFromStruct(allPointersDirect, 20)
        assertEquals(20, allPointersDirect.nb!!.get())
        assertEquals(20, PointerTestLibrary.getNullableByteViaPointerFromStruct(allPointersDirect))

    }

    @Test
    fun `GIVEN a struct with a short pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        allPointersDirect.s.set(100)
        assertEquals(100, allPointersDirect.s.get())

        val allPointersMethod = AllPointers.allocateShared()

        assertEquals(0, allPointersMethod.s.get())
        assertEquals(0, PointerTestLibrary.getShortViaPointerFromStruct(allPointersMethod))

        allPointersMethod.s.set(30)
        assertEquals(30, PointerTestLibrary.getShortViaPointerFromStruct(allPointersMethod))

        PointerTestLibrary.setShortViaPointerFromStruct(allPointersMethod, 100.toShort())
        assertEquals(100, PointerTestLibrary.getShortViaPointerFromStruct(allPointersMethod))
    }

    @Test
    fun `GIVEN a struct with a short nullable pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        assertFails { allPointersDirect.ns!!.get() }
        assertFails { allPointersDirect.ns!!.set(100) }

        allPointersDirect.ns = shortPointerOf(0)

        assertEquals(0, allPointersDirect.ns!!.get())
        assertEquals(0, PointerTestLibrary.getNullableShortViaPointerFromStruct(allPointersDirect))

        allPointersDirect.ns!!.set(100.toShort())
        assertEquals(100,allPointersDirect.ns!!.get())
        assertEquals(100, PointerTestLibrary.getNullableShortViaPointerFromStruct(allPointersDirect))

        PointerTestLibrary.setNullableShortViaPointerFromStruct(allPointersDirect, 20)
        assertEquals(20, allPointersDirect.ns!!.get())
        assertEquals(20, PointerTestLibrary.getNullableShortViaPointerFromStruct(allPointersDirect))

    }

    @Test
    fun `GIVEN a struct with a int pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        allPointersDirect.i.set(1000)
        assertEquals(1000, allPointersDirect.i.get())

        val allPointersMethod = AllPointers.allocateShared()

        assertEquals(0, allPointersMethod.i.get())
        assertEquals(0, PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod))

        allPointersMethod.i.set(3000)
        assertEquals(3000, PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod))

        PointerTestLibrary.setIntViaPointerFromStruct(allPointersMethod, 10000)
        assertEquals(10000, PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod))
    }

    @Test
    fun `GIVEN a struct with a int nullable pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        assertFails { allPointersDirect.ni!!.get() }
        assertFails { allPointersDirect.ni!!.set(1000) }

        allPointersDirect.ni = intPointerOf(0)

        assertEquals(0, allPointersDirect.ni!!.get())
        assertEquals(0, PointerTestLibrary.getNullableIntViaPointerFromStruct(allPointersDirect))

        allPointersDirect.ni!!.set(1000)
        assertEquals(1000, allPointersDirect.ni!!.get())
        assertEquals(1000, PointerTestLibrary.getNullableIntViaPointerFromStruct(allPointersDirect))

        PointerTestLibrary.setNullableIntViaPointerFromStruct(allPointersDirect, 2000)
        assertEquals(2000, allPointersDirect.ni!!.get())
        assertEquals(2000, PointerTestLibrary.getNullableIntViaPointerFromStruct(allPointersDirect))

    }

    @Test
    fun `GIVEN a struct with a long pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        allPointersDirect.l.set(1000)
        assertEquals(1000, allPointersDirect.l.get())

        val allPointersMethod = AllPointers.allocateShared()

        assertEquals(0, allPointersMethod.l.get())
        assertEquals(0, PointerTestLibrary.getLongViaPointerFromStruct(allPointersMethod))

        allPointersMethod.l.set(3000)
        assertEquals(3000, PointerTestLibrary.getLongViaPointerFromStruct(allPointersMethod))

        PointerTestLibrary.setLongViaPointerFromStruct(allPointersMethod, 10000)
        assertEquals(10000, PointerTestLibrary.getLongViaPointerFromStruct(allPointersMethod))
    }

    @Test
    fun `GIVEN a struct with a long nullable pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        assertFails { allPointersDirect.nl!!.get() }
        assertFails { allPointersDirect.nl!!.set(1000) }

        allPointersDirect.nl = longPointerOf(0)

        assertEquals(0, allPointersDirect.nl!!.get())
        assertEquals(0, PointerTestLibrary.getNullableLongViaPointerFromStruct(allPointersDirect))

        allPointersDirect.nl!!.set(1000)
        assertEquals(1000, allPointersDirect.nl!!.get())
        assertEquals(1000, PointerTestLibrary.getNullableLongViaPointerFromStruct(allPointersDirect))

        PointerTestLibrary.setNullableLongViaPointerFromStruct(allPointersDirect, 2000)
        assertEquals(2000, allPointersDirect.nl!!.get())
        assertEquals(2000, PointerTestLibrary.getNullableLongViaPointerFromStruct(allPointersDirect))

    }
}