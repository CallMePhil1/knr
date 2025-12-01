package structs

import knr.runtime.library.LibraryLoader
import org.junit.Test
import java.lang.foreign.ValueLayout
import kotlin.test.assertEquals

class StructTests {

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling passByPointer Then it should work`() {
        val struct = TestStruct.allocate()
        val ptr = struct.asPointer()

        lib.passByPointer(ptr, 5000)

        assertEquals(5000, ptr.get().i)
        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling passByRef Then it should work`() {
        val struct = TestStruct.allocate()

        lib.passByRef(struct, 5000)

        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling returnByPointer THEN it should work`() {
        val ptr = lib.returnByPointer(5000)

        assertEquals(5000, ptr.get().i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling returnByRef THEN it should work`() {
        val struct = lib.returnByRef(5000)

        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling returnByValue THEN it should work`() {
        val struct = lib.returnByValue(5000)

        assertEquals(5000, struct.i)
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling dispose functions Then it should work`() {
        val struct = lib.returnStructWithDispose(1000)
        val ptr = lib.returnPtrWithDispose(1000)

        val structMem = struct.memory.memorySegment!!
        val ptrMem = ptr.memory.memorySegment!!

        struct.dispose()
        ptr.dispose()

        assertEquals(-1, structMem.get(ValueLayout.JAVA_INT, 0))
        assertEquals(-1, ptrMem.get(ValueLayout.JAVA_INT, 0))
    }

    @Test
    fun `GIVEN a StructTestLibrary WHEN calling array functions THEN it should work`() {
        val structPtr = lib.getStructInArray(0)
        val struct = structPtr.get()

        struct.i = 1000

        val struct2Ptr = lib.getStructInArray(0)
        val struct2 = struct2Ptr.get()

        assertEquals(1000, struct.i)
        assertEquals(1000, struct2.i)

        lib.setValueInArray(0, 2000)

        assertEquals(2000, struct.i)
        assertEquals(2000, struct2.i)

        val newStruct = TestStruct.allocate { i = 3000 }

        lib.setStructInArray(0, newStruct)

        assertEquals(3000, struct.i)
    }

    companion object {
        private val lib = LibraryLoader.loadLibrary<StructTestLibrary>()
    }
}