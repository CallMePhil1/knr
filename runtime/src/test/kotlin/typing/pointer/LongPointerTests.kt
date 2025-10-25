package typing.pointer

import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
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
}