package com.github.callmephil.knr.runtime.native

import com.github.callmephil.knr.runtime.ext.downcallHandle
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

object Stdlib {
    internal val stdlibLinker = Linker.nativeLinker()
    internal val stdlib = Linker.nativeLinker().defaultLookup()

    internal val freeHandle = stdlibLinker.downcallHandle(
        segment = stdlib.find("free").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS
    )

    fun free(memorySegment: MemorySegment) {
        freeHandle.invokeExact(memorySegment)
    }
}