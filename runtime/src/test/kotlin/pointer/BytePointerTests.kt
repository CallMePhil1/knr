package pointer

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.giveTo
import com.github.callmephil.knr.runtime.typing.pointer.nullableBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareFrom
import com.github.callmephil.knr.runtime.typing.pointer.shareWith
import com.github.callmephil.knr.runtime.typing.pointer.takeFrom
import java.lang.foreign.ValueLayout
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class BytePointerTests {
    @Test
    fun `GIVEN a byte pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = bytePointerOf()

        pointerDirect.set(100)
        assert(pointerDirect.get() == 100.toByte())

        val pointerMethod = bytePointerOf()

        PointerTestLibrary.setByteForPointer(pointerMethod, 120)
        assert(PointerTestLibrary.getByteFromPointer(pointerMethod) == 120.toByte())
        assert(pointerMethod.get() == 120.toByte())
    }

    @Test
    fun `GIVEN a byte pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = bytePointerOf()
        val pointer2 = bytePointerOf()

        pointer1.set(100)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 100.toByte())

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a byte pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = bytePointerOf()

        pointer1.set(100)
        assert(pointer1.get() == 100.toByte())

        val pointer2 = pointer1.shareOf()

        assert(pointer1.refCount == 2)
        assert(pointer2.refCount == 2)

        assert(pointer2.get() == 100.toByte())

        assert(PointerTestLibrary.getByteFromPointer(pointer1) == 100.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer2) == 100.toByte())

        pointer2.set(120)

        assert(pointer2.get() == 120.toByte())

        assert(PointerTestLibrary.getByteFromPointer(pointer1) == 120.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer2) == 120.toByte())
    }

    @Test
    fun `GIVEN a nullable byte pointer WHEN setting and getting the pointer THEN it should work`() {
        val pointerDirect = nullableBytePointerOf(ARC.shared(ValueLayout.JAVA_BYTE))

        assert(pointerDirect.get() == 0.toByte())

        pointerDirect.set(100)
        assert(pointerDirect.get() == 100.toByte())

        pointerDirect.setToNull()
        assertFails { pointerDirect.get() }
    }

    @Test
    fun `GIVEN a nullable byte pointer WHEN taking ownership THEN pointer should point to data and old pointer should be disposed`() {
        val pointer1 = nullableBytePointerOf(ARC.shared(ValueLayout.JAVA_BYTE))
        val pointer2 = nullableBytePointerOf(ARC.shared(ValueLayout.JAVA_BYTE))

        pointer1.set(100)

        pointer2 takeFrom pointer1

        assert(pointer1.isNotValid)
        assert(pointer2.get() == 100.toByte())

        assert(pointer2.refCount == 1)
    }

    @Test
    fun `GIVEN a nullable byte pointer WHEN sharing a pointer THEN they should point to the same`() {
        val pointer1 = nullableBytePointerOf()

        assertFails { pointer1.get() }
        assertFails { pointer1.set(10) }
        assert(pointer1.isNull)

        val pointer2 = pointer1.shareOf()

        assertFails { pointer2.get() }
        assertFails { pointer2.set(10) }
        assert(pointer2.isNull)

        pointer1.pointTo(ARC.shared(ValueLayout.JAVA_BYTE.byteSize()))

        assert(pointer1.get() == 0.toByte())
        assert(pointer2.isNull)

        pointer1.set(100)

        pointer2 shareFrom pointer1

        assert(pointer1.get() == 100.toByte())
        assert(pointer2.get() == 100.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer1) == 100.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer2) == 100.toByte())

        pointer2.set(120)

        assert(pointer1.get() == 120.toByte())
        assert(pointer2.get() == 120.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer1) == 120.toByte())
        assert(PointerTestLibrary.getByteFromPointer(pointer2) == 120.toByte())
    }

    @Test
    fun `GIVEN a non null nullable byte pointer WHEN giving to a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableBytePointerOf(100)
        val nonNullablePointer = bytePointerOf(0)

        nullablePointer giveTo nonNullablePointer

        assert(nullablePointer.refCount == 0)
        assert(nonNullablePointer.refCount == 1)
        assert(nonNullablePointer.get() == 100.toByte())
    }

    @Test
    fun `GIVEN a null nullable byte pointer WHEN giving to a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableBytePointerOf()
        val nonNullablePointer = bytePointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer giveTo nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable byte pointer WHEN giving to a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableBytePointerOf(0)
        val nonNullablePointer = bytePointerOf(100)

        nonNullablePointer giveTo nullablePointer

        assert(nonNullablePointer.refCount == 0)
        assert(nullablePointer.refCount == 1)
        assert(nullablePointer.get() == 100.toByte())
    }

    @Test
    fun `GIVEN a non null nullable byte pointer WHEN sharing with a non nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableBytePointerOf(100)
        val nonNullablePointer = bytePointerOf(0)

        nullablePointer shareWith nonNullablePointer

        assert(nullablePointer.refCount == 2)
        assert(nonNullablePointer.refCount == 2)
        assert(nonNullablePointer.get() == 100.toByte())
    }

    @Test
    fun `GIVEN a null nullable byte pointer WHEN sharing with a non nullable pointer THEN it should fail`() {
        val nullablePointer = nullableBytePointerOf()
        val nonNullablePointer = bytePointerOf(0)

        assertFailsWith(NullPointerException::class) { nullablePointer shareWith nonNullablePointer }
    }

    @Test
    fun `GIVEN a non nullable byte pointer WHEN sharing with a nullable pointer THEN it should succeed`() {
        val nullablePointer = nullableBytePointerOf(0)
        val nonNullablePointer = bytePointerOf(100)

        nonNullablePointer shareWith nullablePointer

        assert(nonNullablePointer.refCount == 2)
        assert(nullablePointer.refCount == 2)
        assert(nullablePointer.get() == 100.toByte())
    }
}