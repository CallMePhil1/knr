package pointer

import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import kotlin.test.Test

class ShortPointerTests {

    @Test
    fun `GIVEN a short pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = shortPointerOf()

        pointerDirect.set(10000)
        assert(pointerDirect.get() == 10000.toShort())

        val pointerMethod = shortPointerOf()

        PointerTestLibrary.setShortForPointer(pointerMethod, -10000)
        assert(PointerTestLibrary.getShortFromPointer(pointerMethod) == (-10000).toShort())
        assert(pointerMethod.get() == (-10000).toShort())
    }

    @Test
    fun `GIVEN a short pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = shortPointerOf()
        val pointer2 = shortPointerOf()

        pointer1.set(10000)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 10000.toShort())

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a short pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = shortPointerOf()

        pointer1.set(10000)
        assert(pointer1.get() == 10000.toShort())

        val pointer2 = pointer1.shareOf()

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)

        assert(pointer2.get() == 10000.toShort())

        assert(PointerTestLibrary.getShortFromPointer(pointer1) == 10000.toShort())
        assert(PointerTestLibrary.getShortFromPointer(pointer2) == 10000.toShort())

        pointer2.set(-10000)

        assert(pointer2.get() == (-10000).toShort())

        assert(PointerTestLibrary.getShortFromPointer(pointer1) == (-10000).toShort())
        assert(PointerTestLibrary.getShortFromPointer(pointer2) == (-10000).toShort())
    }
}