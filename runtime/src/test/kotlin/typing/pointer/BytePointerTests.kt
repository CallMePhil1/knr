package typing.pointer

import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import kotlin.test.Test

class BytePointerTests {
    @Test
    fun `GIVEN a byte pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = bytePointerOf()

        pointerDirect.set(100)
        assert(pointerDirect.get() == 100.toByte())

        val pointerMethod = bytePointerOf()

        PointerTestLibrary.setByteForPointer(pointerMethod, 120)
        assert(PointerTestLibrary.getByteFromPointer(pointerMethod) == 120.toByte())
        assert(pointerMethod.get() == 120.toByte())
    }

    @Test
    fun `GIVEN a byte pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = bytePointerOf()
        val pointer2 = bytePointerOf()

        pointer1.set(100)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 100.toByte())

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a byte pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = bytePointerOf()

        pointer1.set(100)
        assert(pointer1.get() == 100.toByte())

        val pointer2 = pointer1.shareOf()

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)

        assert(pointer2.get() == 100.toByte())

        assert(PointerTestLibrary.getByteFromPointer(pointer1) == 100.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer2) == 100.toByte())

        pointer2.set(120)

        assert(pointer2.get() == 120.toByte())

        assert(PointerTestLibrary.getByteFromPointer(pointer1) == 120.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer2) == 120.toByte())
    }
}