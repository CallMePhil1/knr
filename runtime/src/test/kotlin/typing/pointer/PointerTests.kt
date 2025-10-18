package typing.pointer

import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import com.github.callmephil.knr.runtime.typing.pointer.takePointer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PointerTests {
    @Test
    fun `GIVEN two byte pointer WHEN sharing between them multiple times THEN ref count shouldn't change`() {
        val pointer1 = bytePointerOf()
        val pointer2 = bytePointerOf()

        assert(pointer1.refCount == 1)
        assert(pointer2.refCount == 1)

        pointer1 shareWith pointer2

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)

        pointer1 shareWith pointer2

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)
    }

    @Test
    fun `GIVEN two Pointer WHEN sharing and disposing THEN ref count should update`() {
        val pointer1 = bytePointerOf() as Pointer<Byte>
        val pointer2 = bytePointerOf() as Pointer<Byte>

        assert(pointer1.refCount == 1)
        assert(pointer2.refCount == 1)

        pointer1 shareWith pointer2

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)

        pointer1.dispose()

        assert(pointer1.refCount == 0)
        assert(pointer2.refCount == 1)

        pointer2.dispose()

        assert(pointer2.refCount == 0)
    }

    @Test
    fun `GIVEN a pointer WHEN taking the pointer THEN the old pointer should be disposed and new pointer points to arc`() {
        val bytePointer = bytePointerOf(100)

        assertEquals(1, bytePointer.refCount)

        val newBytePointer = takePointer(bytePointer)

        assertEquals(0, bytePointer.refCount)
        assertTrue(bytePointer.isNotValid)

        assertEquals(1, newBytePointer.refCount)
    }
}