package pointer

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.giveTo
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareFrom
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

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

    @Test
    fun `GIVEN a nullable int pointer WHEN setting and getting the pointer THEN it should work`() {
        val intPointerDirect = nullableIntPointerOf(ARC.shared(ValueLayout.JAVA_INT))

        assert(intPointerDirect.get() == 0)

        intPointerDirect.set(1000)
        assert(intPointerDirect.get() == 1000)

        intPointerDirect.setToNull()
        assertFails { intPointerDirect.get() }
    }

    @Test
    fun `GIVEN a nullable int pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val intPointer1 = nullableIntPointerOf(ARC.shared(ValueLayout.JAVA_INT))
        val intPointer2 = nullableIntPointerOf(ARC.shared(ValueLayout.JAVA_INT))

        intPointer1.set(1000)

        intPointer2 takeFrom intPointer1

        assert(intPointer1.isNotValid)
        assert(intPointer2.get() == 1000)

        assert(intPointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a nullable int pointer WHEN sharing a pointer THEN they should point to the same`() {
        val intPointer1 = nullableIntPointerOf()

        assertFails { intPointer1.get() }
        assertFails { intPointer1.set(10) }
        assert(intPointer1.isNull)

        val intPointer2 = intPointer1.shareOf()

        assertFails { intPointer2.get() }
        assertFails { intPointer2.set(10) }
        assert(intPointer2.isNull)

        intPointer1.pointTo(ARC.shared(ValueLayout.JAVA_INT.byteSize()))

        assert(intPointer1.get() == 0)
        assert(intPointer2.isNull)

        intPointer1.set(1000)

        intPointer2 shareFrom intPointer1

        assert(intPointer1.get() == 1000)
        assert(intPointer2.get() == 1000)
        assert(PointerTestLibrary.getIntFromPointer(intPointer1) == 1000)
        assert(PointerTestLibrary.getIntFromPointer(intPointer2) == 1000)

        intPointer2.set(2000)

        assert(intPointer1.get() == 2000)
        assert(intPointer2.get() == 2000)
        assert(PointerTestLibrary.getIntFromPointer(intPointer1) == 2000)
        assert(PointerTestLibrary.getIntFromPointer(intPointer2) == 2000)
    }

    @Test
    fun `GIVEN a non null nullable int pointer WHEN giving to a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableIntPointerOf(1000)
        val nonNullablePointer = intPointerOf(0)

        nullablePointer giveTo nonNullablePointer

        assert(nullablePointer.refCount == 0)
        assert(nonNullablePointer.refCount == 1)
        assert(nonNullablePointer.get() == 1000)
    }

    @Test
    fun `GIVEN a null nullable int pointer WHEN giving to a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableIntPointerOf()
        val nonNullablePointer = intPointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer giveTo nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable int pointer WHEN giving to a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableIntPointerOf(0)
        val nonNullablePointer = intPointerOf(1000)

        nonNullablePointer giveTo nullablePointer

        assert(nonNullablePointer.refCount == 0)
        assert(nullablePointer.refCount == 1)
        assert(nullablePointer.get() == 1000)
    }

    @Test
    fun `GIVEN a non null nullable int pointer WHEN sharing with a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableIntPointerOf(1000)
        val nonNullablePointer = intPointerOf(0)

        nullablePointer shareWith nonNullablePointer

        assert(nullablePointer.refCount == 2)
        assert(nonNullablePointer.refCount == 2)
        assert(nonNullablePointer.get() == 1000)
    }

    @Test
    fun `GIVEN a null nullable int pointer WHEN sharing with a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableIntPointerOf()
        val nonNullablePointer = intPointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer shareWith nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable int pointer WHEN sharing with a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableIntPointerOf(0)
        val nonNullablePointer = intPointerOf(1000)

        nonNullablePointer shareWith nullablePointer

        assert(nonNullablePointer.refCount == 2)
        assert(nullablePointer.refCount == 2)
        assert(nullablePointer.get() == 1000)
    }
}