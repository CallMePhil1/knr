package memory.rc

import com.github.callmephil.knr.runtime.memory.rc.Arc
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ArcTests {
    @Test
    fun `GIVEN an Arc WHEN disposing of it THEN it should dispose Native object`() {
        val pointer = bytePointerOf(10)
        val arc = Arc.of(pointer)

        assertEquals(1, arc.shared!!.refCount.get())

        arc.dispose()

        assertNull(arc.shared)
        assertTrue(pointer.isNotValid)
        assertFailsWith<IllegalStateException> { pointer.get() }
    }

    @Test
    fun `GIVEN an Arc WHEN getting a clone THEN both should be tied to the Shared`() {
        val pointer = bytePointerOf(10)
        val arc1 = Arc.of(pointer)
        val arc2 = arc1.clone()

        assertEquals(2, arc1.shared!!.refCount.get())
        assertEquals(2, arc2.shared!!.refCount.get())

        arc2.dispose()

        assertNull(arc2.shared)
        assertEquals(1, arc1.shared!!.refCount.get())
        assertEquals(pointer, arc1.get())

        arc1.dispose()

        assertNull(arc1.shared)
        assertTrue(pointer.isNotValid)
        assertFailsWith<IllegalStateException> { pointer.get() }
    }

    @Test
    fun `GIVEN an Arc WHEN getting getting a WeakArc THEN weak should not prevent cleanup`() {
        val pointer = bytePointerOf(10)
        val arc = Arc.of(pointer)
        val warc = arc.weakArc()
        val warc2 = arc.weakArc()

        assertEquals(1, arc.shared!!.refCount.get())
        assertEquals(warc.get(), pointer)

        warc.dispose()

        assertFalse(warc.isValid)

        arc.dispose()

        assertNull(arc.shared)
        assertFalse(warc2.isValid)
        assertTrue(pointer.isNotValid)
        assertFailsWith<IllegalStateException> { warc2.get() }
        assertFailsWith<IllegalStateException> { pointer.get() }
    }

    @Test
    fun `GIVEN an Arc WHEN creating and disposing of clones concurrently THEN there should be no collisions`() {
        val pointer = bytePointerOf(10)
        val arc = Arc.of(pointer)
        val scope = CoroutineScope(Dispatchers.Unconfined) + Job()
        val jobs = mutableListOf<Job>()

        repeat(1000) {
            jobs.add(scope.launch {
                val tempArc = arc.clone()
                delay(Random.nextLong(100, 500))
                tempArc.dispose()
            })
        }

        assertNotEquals(1, arc.shared!!.refCount.get())

        runBlocking {
            jobs.joinAll()
        }

        assertEquals(1, arc.shared!!.refCount.get())
    }
}