package structs

import knr.runtime.library.LibraryLoader
import org.junit.Test
import java.lang.foreign.ValueLayout
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class StructTests {
    @BeforeTest
    fun setup() {
        LibraryLoader.addPaths("src/test/libraries/build/Debug/")
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling passByPointer Then it should work`() {
        val struct = TestStruct.allocate()
        val ptr = struct.asPointer()

        StructTestLibrary.passByPointer(ptr, 5000)

        assertEquals(5000, ptr.get().i)
        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling passByRef Then it should work`() {
        val struct = TestStruct.allocate()

        StructTestLibrary.passByRef(struct, 5000)

        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling returnByPointer THEN it should work`() {
        val ptr = StructTestLibrary.returnByPointer(5000)

        assertEquals(5000, ptr.get().i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling returnByRef THEN it should work`() {
        val struct = StructTestLibrary.returnByRef(5000)

        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling returnByValue THEN it should work`() {
        val struct = StructTestLibrary.returnByValue(5000)

        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling dispose functions Then it should work`() {
        val struct = StructTestLibrary.returnStructWithDispose(1000)
        val ptr = StructTestLibrary.returnPtrWithDispose(1000)

        val structMem = struct.memory.memorySegment!!
        val ptrMem = ptr.memory.memorySegment!!

        struct.dispose()
        ptr.dispose()

        assertEquals(-1, structMem.get(ValueLayout.JAVA_INT, 0))
        assertEquals(-1, ptrMem.get(ValueLayout.JAVA_INT, 0))
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling array functions THEN it should work`() {
        val structPtr = StructTestLibrary.getStructInArray(0)
        val struct = structPtr.get()

        struct.i = 1000

        val struct2Ptr = StructTestLibrary.getStructInArray(0)
        val struct2 = struct2Ptr.get()

        assertEquals(1000, struct.i)
        assertEquals(1000, struct2.i)

        StructTestLibrary.setValueInArray(0, 2000)

        assertEquals(2000, struct.i)
        assertEquals(2000, struct2.i)

        val newStruct = TestStruct.allocate { i = 3000 }

        StructTestLibrary.setStructInArray(0, newStruct)

        assertEquals(3000, struct.i)
    }
}