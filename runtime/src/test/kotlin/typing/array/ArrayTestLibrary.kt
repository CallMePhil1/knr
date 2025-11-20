package typing.array

import knr.runtime.ext.downcallHandle
import knr.runtime.typing.array.ByteNativeArray
import knr.runtime.typing.array.DoubleNativeArray
import knr.runtime.typing.array.FloatNativeArray
import knr.runtime.typing.array.IntNativeArray
import knr.runtime.typing.array.LongNativeArray
import knr.runtime.typing.array.ShortNativeArray
import knr.runtime.typing.array.StructNativeArray
import knr.runtime.typing.array.UByteNativeArray
import knr.runtime.typing.array.UIntNativeArray
import knr.runtime.typing.array.ULongNativeArray
import knr.runtime.typing.array.UShortNativeArray
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

object ArrayTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/arraytest",
        arena
    )

    private val getIntFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_int_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setIntForStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_int_for_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT
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

    private val getFloatHandle = linker.downcallHandle(
        segment = lookup.find("get_float").orElseThrow(),
        retType = ValueLayout.JAVA_FLOAT,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setFloatHandle = linker.downcallHandle(
        segment = lookup.find("set_float").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_FLOAT
    )

    private val getDoubleHandle = linker.downcallHandle(
        segment = lookup.find("get_double").orElseThrow(),
        retType = ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setDoubleHandle = linker.downcallHandle(
        segment = lookup.find("set_double").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_DOUBLE
    )

    fun getIntFromStruct(array: StructNativeArray<*>, index: Int) =
        getIntFromStructHandle.invokeExact(array.memory.memorySegment, index) as Int

    fun setIntForStruct(array: StructNativeArray<*>, index: Int, value: Int) {
        setIntForStructHandle.invokeExact(array.memory.memorySegment, index, value)
    }

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

    fun getFloat(array: FloatNativeArray, index: Int) =
        getFloatHandle.invokeExact(array.memory.memorySegment, index) as Float

    fun setFloat(array: FloatNativeArray, index: Int, value: Float) {
        setFloatHandle.invokeExact(array.memory.memorySegment, index, value)
    }

    fun getDouble(array: DoubleNativeArray, index: Int) =
        getDoubleHandle.invokeExact(array.memory.memorySegment, index) as Double

    fun setDouble(array: DoubleNativeArray, index: Int, value: Double) {
        setDoubleHandle.invokeExact(array.memory.memorySegment, index, value)
    }
}