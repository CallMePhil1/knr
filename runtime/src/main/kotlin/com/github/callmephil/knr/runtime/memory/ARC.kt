package com.github.callmephil.knr.runtime.memory

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

class ARC(
    private val arena: Arena,
    private val memorySegment: MemorySegment
) {
    fun dispose() {
        try {
            arena.close()
        } catch (e: UnsupportedOperationException) {
            /* no-op */
        }
    }
}
