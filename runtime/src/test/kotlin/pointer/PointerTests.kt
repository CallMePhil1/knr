package pointer

import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import kotlin.test.Test

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
}