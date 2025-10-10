package pointer

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.ByRef
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ByRefTests {
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
    fun `GIVEN two ByRefs WHEN sharing and disposing THEN ref count should update`() {
        val pointer1 = bytePointerOf() as ByRef<Byte>
        val pointer2 = bytePointerOf() as ByRef<Byte>

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

    @Test
    fun `GIVEN one non null nullable ByRef and one non nullable WHEN sharing between them multiple times THEN it should work`() {
        val pointer1 = bytePointerOf()
        val pointer2 = nullableBytePointerOf(0)

        assert(pointer1.refCount == 1)
        assert(pointer2.refCount == 1)

        pointer1 shareWith pointer2

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)
    }

    @Test
    fun `GIVEN one null nullable ByRef and one non nullable WHEN sharing between them multiple times THEN it should work`() {
        val pointer1 = bytePointerOf()
        val pointer2 = nullableBytePointerOf()

        assertFailsWith<NullPointerException> { pointer2 shareWith pointer1 }
    }

    @Test
    fun `GIVEN a non nullable ByRef WHEN constructing it with a null arc THEN it should fail`() {
        assertFailsWith<NullPointerException> { intPointerOf(ARC.ofNull()) }
    }
}