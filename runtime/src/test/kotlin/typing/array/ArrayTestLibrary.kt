package typing.array

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.array.ByteNativeArray
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
}