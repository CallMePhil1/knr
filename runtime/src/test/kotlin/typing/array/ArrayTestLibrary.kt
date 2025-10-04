package typing.array

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.array.ByteNativeArray
import com.github.callmephil.knr.runtime.typing.array.IntNativeArray
import com.github.callmephil.knr.runtime.typing.array.LongNativeArray
import com.github.callmephil.knr.runtime.typing.array.ShortNativeArray
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout

object ArrayTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/arraytest",
        arena
    )

    private val getByteHandle = linker.downcallHandle(
        segment = lookup.find("get_byte").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setByteHandle = linker.downcallHandle(
        segment = lookup.find("set_byte").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_BYTE
    )

    private val getShortHandle = linker.downcallHandle(
        segment = lookup.find("get_short").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setShortHandle = linker.downcallHandle(
        segment = lookup.find("set_short").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_SHORT
    )

    private val getIntHandle = linker.downcallHandle(
        segment = lookup.find("get_int").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setIntHandle = linker.downcallHandle(
        segment = lookup.find("set_int").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT
    )

    private val getLongHandle = linker.downcallHandle(
        segment = lookup.find("get_long").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setLongHandle = linker.downcallHandle(
        segment = lookup.find("set_long").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    )

    fun getByte(array: ByteNativeArray, index: Int) =
        getByteHandle.invokeExact(array.arc.memorySegment, index) as Byte

    fun setByte(array: ByteNativeArray, index: Int, value: Byte) {
        setByteHandle.invokeExact(array.arc.memorySegment, index, value)
    }

    fun getShort(array: ShortNativeArray, index: Int) =
        getShortHandle.invokeExact(array.arc.memorySegment, index) as Short

    fun setShort(array: ShortNativeArray, index: Int, value: Short) {
        setShortHandle.invokeExact(array.arc.memorySegment, index, value)
    }

    fun getInt(array: IntNativeArray, index: Int) =
        getIntHandle.invokeExact(array.arc.memorySegment, index) as Int

    fun setInt(array: IntNativeArray, index: Int, value: Int) {
        setIntHandle.invokeExact(array.arc.memorySegment, index, value)
    }

    fun getLong(array: LongNativeArray, index: Int) =
        getLongHandle.invokeExact(array.arc.memorySegment, index) as Long

    fun setLong(array: LongNativeArray, index: Int, value: Long) {
        setLongHandle.invokeExact(array.arc.memorySegment, index, value)
    }
}