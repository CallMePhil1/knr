package pointer

import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import kotlin.test.Test
import kotlin.test.assertFails

class StructPointerTests {
    @Test
    fun `GIVEN a struct with a pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        allPointersDirect.i.set(1000)
        assert(allPointersDirect.i.get() == 1000)

        val allPointersMethod = AllPointers.allocateShared()

        assert(allPointersMethod.i.get() == 0)
        assert(PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod) == 0)

        allPointersMethod.i.set(3000)
        assert(PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod) == 3000)

        PointerTestLibrary.setIntViaPointerFromStruct(allPointersMethod, 10000)
        assert(PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod) == 10000)
    }

    @Test
    fun `GIVEN a struct with a nullable pointer field WHEN setting and getting the pointer THEN it should work`() {
        val allPointersDirect = AllPointers.allocateShared()

        assertFails { allPointersDirect.ni.get() }
        assertFails { allPointersDirect.ni.set(1000) }

        allPointersDirect.ni takeFrom nullableIntPointerOf(0)

        assert(allPointersDirect.ni.get() == 0)
        assert(PointerTestLibrary.getNullableIntViaPointerFromStruct(allPointersDirect) == 0)

        allPointersDirect.ni.set(1000)
        assert(allPointersDirect.ni.get() == 1000)
        assert(PointerTestLibrary.getNullableIntViaPointerFromStruct(allPointersDirect) == 1000)

        PointerTestLibrary.setNullableIntViaPointerFromStruct(allPointersDirect, 2000)
        assert(allPointersDirect.ni.get() == 2000)
        assert(PointerTestLibrary.getNullableIntViaPointerFromStruct(allPointersDirect) == 2000)

    }
}