package typing.array

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.array.ByteNativeArray
import com.github.callmephil.knr.runtime.typing.array.IntNativeArray
import com.github.callmephil.knr.runtime.typing.array.LongNativeArray
import com.github.callmephil.knr.runtime.typing.array.ShortNativeArray
import com.github.callmephil.knr.runtime.typing.array.UByteNativeArray
import com.github.callmephil.knr.runtime.typing.array.UIntNativeArray
import com.github.callmephil.knr.runtime.typing.array.ULongNativeArray
import com.github.callmephil.knr.runtime.typing.array.UShortNativeArray
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

    private val getUByteHandle = linker.downcallHandle(
        segment = lookup.find("get_ubyte").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setUByteHandle = linker.downcallHandle(
        segment = lookup.find("set_ubyte").orElseThrow(),
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

    private val getUShortHandle = linker.downcallHandle(
        segment = lookup.find("get_ushort").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setUShortHandle = linker.downcallHandle(
        segment = lookup.find("set_ushort").orElseThrow(),
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

    private val getUIntHandle = linker.downcallHandle(
        segment = lookup.find("get_uint").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setUIntHandle = linker.downcallHandle(
        segment = lookup.find("set_uint").orElseThrow(),
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

    private val getULongHandle = linker.downcallHandle(
        segment = lookup.find("get_ulong").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setULongHandle = linker.downcallHandle(
        segment = lookup.find("set_ulong").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_LONG
    )

    fun getByte(array: ByteNativeArray, index: Int) =
        getByteHandle.invokeExact(array.memory.memorySegment, index) as Byte

    fun setByte(array: ByteNativeArray, index: Int, value: Byte) {
        setByteHandle.invokeExact(array.memory.memorySegment, index, value)
    }

    fun getUByte(array: UByteNativeArray, index: Int) =
        (getUByteHandle.invokeExact(array.memory.memorySegment, index) as Byte).toUByte()

    fun setUByte(array: UByteNativeArray, index: Int, value: UByte) {
        setUByteHandle.invokeExact(array.memory.memorySegment, index, value.toByte())
    }

    fun getShort(array: ShortNativeArray, index: Int) =
        getShortHandle.invokeExact(array.memory.memorySegment, index) as Short

    fun setShort(array: ShortNativeArray, index: Int, value: Short) {
        setShortHandle.invokeExact(array.memory.memorySegment, index, value)
    }

    fun getUShort(array: UShortNativeArray, index: Int) =
        (getUShortHandle.invokeExact(array.memory.memorySegment, index) as Short).toUShort()

    fun setUShort(array: UShortNativeArray, index: Int, value: UShort) {
        setUShortHandle.invokeExact(array.memory.memorySegment, index, value.toShort())
    }

    fun getInt(array: IntNativeArray, index: Int) =
        getIntHandle.invokeExact(array.memory.memorySegment, index) as Int

    fun setInt(array: IntNativeArray, index: Int, value: Int) {
        setIntHandle.invokeExact(array.memory.memorySegment, index, value)
    }

    fun getUInt(array: UIntNativeArray, index: Int) =
        (getUIntHandle.invokeExact(array.memory.memorySegment, index) as Int).toUInt()

    fun setUInt(array: UIntNativeArray, index: Int, value: UInt) {
        setUIntHandle.invokeExact(array.memory.memorySegment, index, value.toInt())
    }

    fun getLong(array: LongNativeArray, index: Int) =
        getLongHandle.invokeExact(array.memory.memorySegment, index) as Long

    fun setLong(array: LongNativeArray, index: Int, value: Long) {
        setLongHandle.invokeExact(array.memory.memorySegment, index, value)
    }

    fun getULong(array: ULongNativeArray, index: Int) =
        (getULongHandle.invokeExact(array.memory.memorySegment, index) as Long).toULong()

    fun setULong(array: ULongNativeArray, index: Int, value: ULong) {
        setULongHandle.invokeExact(array.memory.memorySegment, index, value.toLong())
    }
}