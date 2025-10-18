package typing.pointer

import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import kotlin.test.Test

class LongPointerTests {

    @Test
    fun `GIVEN a long pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = longPointerOf()

        pointerDirect.set(10000)
        assert(pointerDirect.get() == 10000L)

        val pointerMethod = longPointerOf()

        PointerTestLibrary.setLongForPointer(pointerMethod, -10000)
        assert(PointerTestLibrary.getLongFromPointer(pointerMethod) == -10000L)
        assert(pointerMethod.get() == -10000L)
    }

    @Test
    fun `GIVEN a long pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = longPointerOf()
        val pointer2 = longPointerOf()

        pointer1.set(10000)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 10000L)

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a long pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = longPointerOf()

        pointer1.set(10000)
        assert(pointer1.get() == 10000L)

        val pointer2 = pointer1.shareOf()

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)

        assert(pointer2.get() == 10000L)

        assert(PointerTestLibrary.getLongFromPointer(pointer1) == 10000L)
        assert(PointerTestLibrary.getLongFromPointer(pointer2) == 10000L)

        pointer2.set(-10000)

        assert(pointer2.get() == -10000L)

        assert(PointerTestLibrary.getLongFromPointer(pointer1) == -10000L)
        assert(PointerTestLibrary.getLongFromPointer(pointer2) == -10000L)
    }
}