package com.github.callmephil.knr.runtime.native

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import java.lang.foreign.ValueLayout

object StringLib {
    private val strcmpHandle = Stdlib.stdlibLinker.downcallHandle(
        segment = Stdlib.stdlib.find("strcmp").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    )

    private val strlenHandle = Stdlib.stdlibLinker.downcallHandle(
        segment = Stdlib.stdlib.find("strlen").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    fun compare(cString1: Pointer<String>, cString2: Pointer<String>): Int {
        when {
            cString1.memory.isNull -> throw NullPointerException("cString1 is NULL")
            cString2.memory.isNull -> throw NullPointerException("cString2 is NULL")
        }
        return strcmpHandle.invokeExact(cString1.memory.memorySegment, cString2.memory.memorySegment) as Int
    }

    fun equal(cString1: Pointer<String>, cString2: Pointer<String>): Boolean {
        when {
            cString1.memory.isNull -> throw NullPointerException("cString1 is NULL")
            cString2.memory.isNull -> throw NullPointerException("cString2 is NULL")
        }
        val result = strcmpHandle.invokeExact(cString1.memory.memorySegment, cString2.memory.memorySegment) as Int
        return result == 0
    }

    fun length(cString: Pointer<String>): Long {
        if (cString.memory.isNull)
            throw NullPointerException("Tried to get a null string's length via strlen")
        return strlenHandle.invokeExact(cString.memory.memorySegment) as Long
    }
}