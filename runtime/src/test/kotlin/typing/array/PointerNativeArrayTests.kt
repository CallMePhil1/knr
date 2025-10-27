package typing.array

import com.github.callmephil.knr.runtime.typing.array.pointerNativeArray
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertEquals

class PointerNativeArrayTests {
    @Test
    fun `GIVEN WHEN instantiating a new pointer native array THEN it should work`() {
        val pointer1 = bytePointerOf(1)
        val pointer2 = bytePointerOf(2)
        val pointer3 = bytePointerOf(3)
        val pointer4 = bytePointerOf(4)

        val array = pointerNativeArray(pointer1, pointer2, pointer3, pointer4)

        array.forEachIndexed { idx, it ->
            val itemAddress = array.memory.getAddress(idx * ValueLayout.ADDRESS.byteSize())
            assertEquals(itemAddress, it.memory.memorySegment!!)
        }
    }
}