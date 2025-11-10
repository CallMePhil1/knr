package typing.pointer

import knr.runtime.typing.pointer.bytePointerOf
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
}