package typing.pointer

import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
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
}