package pointer

import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.use

class PointerStructTests {
    @Test
    fun `GIVEN a pointer WHEN setting and getting the pointer THEN it should work`() {
        Arena.ofConfined().use {
            val longPointerDirect = intPointerOf(it.allocate(ValueLayout.JAVA_INT))

            longPointerDirect.set(1000)
            assert(longPointerDirect.get() == 1000)

            val longPointerMethod = intPointerOf(it.allocate(ValueLayout.JAVA_INT))

            PointerTestLibrary.setLongForPointer(longPointerMethod, 2000)
            assert(PointerTestLibrary.getLongFromPointer(longPointerMethod) == 2000)
            assert(longPointerMethod.get() == 2000)
        }
    }

    @Test
    fun `GIVEN a nullable pointer WHEN setting and getting the pointer THEN it should work`() {
        Arena.ofConfined().use {
            val longPointerDirect = nullableIntPointerOf(it.allocate(ValueLayout.JAVA_INT))

            longPointerDirect.set(1000)
            assert(longPointerDirect.get() == 1000)

            longPointerDirect.reference(MemorySegment.NULL)
            assert(longPointerDirect.get() == null)
        }
    }

    @Test
    fun `GIVEN a struct with a pointer field WHEN setting and getting the pointer THEN it should work`() {
        Arena.ofConfined().use {
            val allPointersDirect = AllPointers.allocate(it)

            allPointersDirect.i.set(1000)
            assert(allPointersDirect.i.get() == 1000)

            val allPointersMethod = AllPointers.allocate(it)

            allPointersMethod.i.set(3000)
            assert(PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod) == 3000)

            PointerTestLibrary.setIntViaPointerFromStruct(allPointersMethod, 10000)
            assert(PointerTestLibrary.getIntViaPointerFromStruct(allPointersMethod) == 10000)
        }
    }

    @Test
    fun `GIVEN a struct with a nullable pointer field WHEN setting and getting the pointer THEN it should work`() {
        Arena.ofConfined().use {
            val allPointersDirect = AllPointers.allocate(it)

            assert(allPointersDirect.ni.get() == null)

            allPointersDirect.ni.set(1000)
            assert(allPointersDirect.ni.get() == 1000)

            allPointersDirect.ni = nullableIntPointerOf()
            assert(allPointersDirect.ni.get() == null)
        }
    }
}