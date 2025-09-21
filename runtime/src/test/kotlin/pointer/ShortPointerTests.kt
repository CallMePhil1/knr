package pointer

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.giveTo
import com.github.callmephil.knr.runtime.typing.pointer.nullableShortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareFrom
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class ShortPointerTests {
    @Test
    fun `GIVEN two short pointer WHEN sharing between them multiple times THEN ref count shouldn't change`() {
        val pointer1 = shortPointerOf()
        val pointer2 = shortPointerOf()

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
    fun `GIVEN two short pointers WHEN sharing and disposing THEN ref count should update`() {
        val pointer1 = shortPointerOf()
        val pointer2 = shortPointerOf()

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

    @Test
    fun `GIVEN two nullable short pointer WHEN sharing between them multiple times THEN ref count shouldn't change`() {
        val pointer1 = nullableShortPointerOf()
        val pointer2 = nullableShortPointerOf()

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
    fun `GIVEN two nullable short pointers WHEN sharing and disposing THEN ref count should update`() {
        val pointer1 = nullableShortPointerOf()
        val pointer2 = nullableShortPointerOf()

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
    fun `GIVEN a nullable short pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = nullableShortPointerOf(ARC.shared(ValueLayout.JAVA_SHORT))

        assert(pointerDirect.get() == 0.toShort())

        pointerDirect.set(10000)
        assert(pointerDirect.get() == 10000.toShort())

        pointerDirect.setToNull()
        assertFails { pointerDirect.get() }
    }

    @Test
    fun `GIVEN a nullable short pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = nullableShortPointerOf(ARC.shared(ValueLayout.JAVA_SHORT))
        val pointer2 = nullableShortPointerOf(ARC.shared(ValueLayout.JAVA_SHORT))

        pointer1.set(10000)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 10000.toShort())

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a nullable short pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = nullableShortPointerOf()

        assertFails { pointer1.get() }
        assertFails { pointer1.set(10000) }
        assert(pointer1.isNull)

        val pointer2 = pointer1.shareOf()

        assertFails { pointer2.get() }
        assertFails { pointer2.set(10000) }
        assert(pointer2.isNull)

        pointer1.pointTo(ARC.shared(ValueLayout.JAVA_SHORT.byteSize()))

        assert(pointer1.get() == 0.toShort())
        assert(pointer2.isNull)

        pointer1.set(10000)

        pointer2 shareFrom pointer1

        assert(pointer1.get() == 10000.toShort())
        assert(pointer2.get() == 10000.toShort())
        assert(PointerTestLibrary.getShortFromPointer(pointer1) == 10000.toShort())
        assert(PointerTestLibrary.getShortFromPointer(pointer2) == 10000.toShort())

        pointer2.set(-12000)

        assert(pointer1.get() == (-12000).toShort())
        assert(pointer2.get() == (-12000).toShort())
        assert(PointerTestLibrary.getShortFromPointer(pointer1) == (-12000).toShort())
        assert(PointerTestLibrary.getShortFromPointer(pointer2) == (-12000).toShort())
    }

    @Test
    fun `GIVEN a non null nullable short pointer WHEN giving to a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableShortPointerOf(10000)
        val nonNullablePointer = shortPointerOf(0)

        nullablePointer giveTo nonNullablePointer

        assert(nullablePointer.refCount == 0)
        assert(nonNullablePointer.refCount == 1)
        assert(nonNullablePointer.get() == 10000.toShort())
    }

    @Test
    fun `GIVEN a null nullable short pointer WHEN giving to a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableShortPointerOf()
        val nonNullablePointer = shortPointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer giveTo nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable short pointer WHEN giving to a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableShortPointerOf(0)
        val nonNullablePointer = shortPointerOf(10000)

        nonNullablePointer giveTo nullablePointer

        assert(nonNullablePointer.refCount == 0)
        assert(nullablePointer.refCount == 1)
        assert(nullablePointer.get() == 10000.toShort())
    }

    @Test
    fun `GIVEN a non null nullable short pointer WHEN sharing with a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableShortPointerOf(10000)
        val nonNullablePointer = shortPointerOf(0)

        nullablePointer shareWith nonNullablePointer

        assert(nullablePointer.refCount == 2)
        assert(nonNullablePointer.refCount == 2)
        assert(nonNullablePointer.get() == 10000.toShort())
    }

    @Test
    fun `GIVEN a null nullable short pointer WHEN sharing with a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableShortPointerOf()
        val nonNullablePointer = shortPointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer shareWith nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable short pointer WHEN sharing with a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableShortPointerOf(0)
        val nonNullablePointer = shortPointerOf(10000)

        nonNullablePointer shareWith nullablePointer

        assert(nonNullablePointer.refCount == 2)
        assert(nullablePointer.refCount == 2)
        assert(nullablePointer.get() == 10000.toShort())
    }
}