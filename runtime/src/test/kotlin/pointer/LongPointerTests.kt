package pointer

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.giveTo
import com.github.callmephil.knr.runtime.typing.pointer.nullableLongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareFrom
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

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

    @Test
    fun `GIVEN a nullable long pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = nullableLongPointerOf(ARC.shared(ValueLayout.JAVA_LONG))

        assert(pointerDirect.get() == 0L)

        pointerDirect.set(10000)
        assert(pointerDirect.get() == 10000L)

        pointerDirect.setToNull()
        assertFails { pointerDirect.get() }
    }

    @Test
    fun `GIVEN a nullable long pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = nullableLongPointerOf(ARC.shared(ValueLayout.JAVA_LONG))
        val pointer2 = nullableLongPointerOf(ARC.shared(ValueLayout.JAVA_LONG))

        pointer1.set(10000)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 10000L)

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a nullable long pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = nullableLongPointerOf()

        assertFails { pointer1.get() }
        assertFails { pointer1.set(10000) }
        assert(pointer1.isNull)

        val pointer2 = pointer1.shareOf()

        assertFails { pointer2.get() }
        assertFails { pointer2.set(10000) }
        assert(pointer2.isNull)

        pointer1.pointTo(ARC.shared(ValueLayout.JAVA_LONG))

        assert(pointer1.get() == 0L)
        assert(pointer2.isNull)

        pointer1.set(10000)

        pointer2 shareFrom pointer1

        assert(pointer1.get() == 10000L)
        assert(pointer2.get() == 10000L)
        assert(PointerTestLibrary.getLongFromPointer(pointer1) == 10000L)
        assert(PointerTestLibrary.getLongFromPointer(pointer2) == 10000L)

        pointer2.set(-12000)

        assert(pointer1.get() == -12000L)
        assert(pointer2.get() == -12000L)
        assert(PointerTestLibrary.getLongFromPointer(pointer1) == -12000L)
        assert(PointerTestLibrary.getLongFromPointer(pointer2) == -12000L)
    }

    @Test
    fun `GIVEN a non null nullable long pointer WHEN giving to a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableLongPointerOf(10000)
        val nonNullablePointer = longPointerOf(0)

        nullablePointer giveTo nonNullablePointer

        assert(nullablePointer.refCount == 0)
        assert(nonNullablePointer.refCount == 1)
        assert(nonNullablePointer.get() == 10000L)
    }

    @Test
    fun `GIVEN a null nullable long pointer WHEN giving to a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableLongPointerOf()
        val nonNullablePointer = longPointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer giveTo nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable long pointer WHEN giving to a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableLongPointerOf(0)
        val nonNullablePointer = longPointerOf(10000)

        nonNullablePointer giveTo nullablePointer

        assert(nonNullablePointer.refCount == 0)
        assert(nullablePointer.refCount == 1)
        assert(nullablePointer.get() == 10000L)
    }

    @Test
    fun `GIVEN a non null nullable long pointer WHEN sharing with a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableLongPointerOf(10000)
        val nonNullablePointer = longPointerOf(0)

        nullablePointer shareWith nonNullablePointer

        assert(nullablePointer.refCount == 2)
        assert(nonNullablePointer.refCount == 2)
        assert(nonNullablePointer.get() == 10000L)
    }

    @Test
    fun `GIVEN a null nullable long pointer WHEN sharing with a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableLongPointerOf()
        val nonNullablePointer = longPointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer shareWith nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable long pointer WHEN sharing with a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableLongPointerOf(0)
        val nonNullablePointer = longPointerOf(10000)

        nonNullablePointer shareWith nullablePointer

        assert(nonNullablePointer.refCount == 2)
        assert(nullablePointer.refCount == 2)
        assert(nullablePointer.get() == 10000L)
    }
}