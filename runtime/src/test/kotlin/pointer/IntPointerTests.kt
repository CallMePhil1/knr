package pointer

import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import kotlin.test.Test

class IntPointerTests {
    @Test
    fun `GIVEN a int pointer WHEN setting and getting the pointer THEN it should work`() {
        val intPointerDirect = intPointerOf()

        intPointerDirect.set(1000)
        assert(intPointerDirect.get() == 1000)

        val intPointerMethod = intPointerOf()

        PointerTestLibrary.setIntForPointer(intPointerMethod, 2000)
        assert(PointerTestLibrary.getIntFromPointer(intPointerMethod) == 2000)
        assert(intPointerMethod.get() == 2000)
    }

    @Test
    fun `GIVEN a int pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val intPointer1 = intPointerOf()
        val intPointer2 = intPointerOf()

        intPointer1.set(1000)

        intPointer2 takeFrom intPointer1

        assert(intPointer1.isNotValid)
        assert(intPointer2.get() == 1000)

        assert(intPointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a int pointer WHEN sharing a pointer THEN they should point to the same`() {
        val longPointer1 = intPointerOf()

        longPointer1.set(1000)
        assert(longPointer1.get() == 1000)

        val longPointer2 = longPointer1.shareOf()

        assert(longPointer1.refCount == 2)
        assert(longPointer2.refCount == 2)

        assert(longPointer2.get() == 1000)

        assert(PointerTestLibrary.getIntFromPointer(longPointer1) == 1000)
        assert(PointerTestLibrary.getIntFromPointer(longPointer2) == 1000)

        longPointer2.set(2000)

        assert(longPointer2.get() == 2000)

        assert(PointerTestLibrary.getIntFromPointer(longPointer1) == 2000)
        assert(PointerTestLibrary.getIntFromPointer(longPointer2) == 2000)
    }
}