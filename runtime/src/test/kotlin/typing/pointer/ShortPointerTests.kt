package typing.pointer

import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
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
}