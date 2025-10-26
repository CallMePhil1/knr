package memory.rc

import com.github.callmephil.knr.runtime.memory.rc.Rc
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RcTests {
    @Test
    fun `GIVEN a Rc WHEN disposing of it THEN it should dispose Native object`() {
        val pointer = bytePointerOf(10)
        val rc = Rc.of(pointer)

        assertEquals(1, rc.shared!!.refCount)

        rc.dispose()

        assertNull(rc.shared)
        assertTrue(pointer.isNotValid)
        assertFailsWith<IllegalStateException> { pointer.get() }
    }

    @Test
    fun `GIVEN a Rc WHEN getting a clone THEN both should be tied to the Shared`() {
        val pointer = bytePointerOf(10)
        val rc1 = Rc.of(pointer)
        val rc2 = rc1.clone()

        assertEquals(2, rc1.shared!!.refCount)
        assertEquals(2, rc2.shared!!.refCount)

        rc2.dispose()

        assertNull(rc2.shared)
        assertEquals(1, rc1.shared!!.refCount)
        assertEquals(pointer, rc1.get())

        rc1.dispose()

        assertNull(rc1.shared)
        assertTrue(pointer.isNotValid)
        assertFailsWith<IllegalStateException> { pointer.get() }
    }

    @Test
    fun `GIVEN a Rc WHEN getting getting a WeakRc THEN weak should not prevent cleanup`() {
        val pointer = bytePointerOf(10)
        val rc = Rc.of(pointer)
        val wrc = rc.weakRc()
        val wrc2 = rc.weakRc()

        assertEquals(1, rc.shared!!.refCount)
        assertEquals(wrc.get(), pointer)

        wrc.dispose()

        assertFalse(wrc.isValid)

        rc.dispose()

        assertNull(rc.shared)
        assertFalse(wrc2.isValid)
        assertTrue(pointer.isNotValid)
        assertFailsWith<IllegalStateException> { wrc2.get() }
        assertFailsWith<IllegalStateException> { pointer.get() }
    }
}