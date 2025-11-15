package typing.struct

import knr.runtime.ext.downcallHandle
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

object StructTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/structtest",
        arena
    )

    // region Getters

    private val getBoolHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_bool").orElseThrow(),
        retType = ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS
    )

    private val getByteHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_char").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getUByteHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_uchar").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getShortHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_short").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getUShortHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_ushort").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getIntHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_int").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getUIntHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_uint").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_long").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getULongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_ulong").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongLongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_longlong").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getULongLongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_ulonglong").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getFloatHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_float").orElseThrow(),
        retType = ValueLayout.JAVA_FLOAT,
        ValueLayout.ADDRESS
    )

    private val getDoubleHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_double").orElseThrow(),
        retType = ValueLayout.JAVA_DOUBLE,
        ValueLayout.ADDRESS
    )

    private val getIntFromUnionHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_int_from_union").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongFromUnionHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_long_from_union").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getIntFromInnerStructHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_int_from_inner_struct").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongFromInnerStructHandle: MethodHandle = linker.downcallHandle(
        lookup.find("get_long_from_inner_struct").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    // endregion

    // region Setters

    private val setBoolHandle = linker.downcallHandle(
        segment = lookup.find("set_bool").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BOOLEAN
    )

    private val setByteHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_char").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE
    )

    private val setUByteHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_uchar").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE
    )

    private val setShortHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_short").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_SHORT
    )

    private val setUShortHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_ushort").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_SHORT
    )

    private val setIntHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_int").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setUIntHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_uint").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setLongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_long").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setULongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_ulong").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setLongLongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_longlong").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    private val setULongLongHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_ulonglong").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    private val setFloatHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_float").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT
    )

    private val setDoubleHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_double").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_DOUBLE
    )

    private val setIntForInnerStructHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_int_for_inner_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setLongForInnerStructHandle: MethodHandle = linker.downcallHandle(
        lookup.find("set_long_for_inner_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    // endregion

    fun getBool(struct: TestStruct) =
        getBoolHandle.invokeExact(struct.memory.memorySegment) as Boolean

    fun getByte(struct: TestStruct) =
        getByteHandle.invokeExact(struct.memory.memorySegment) as Byte

    fun getUByte(struct: TestStruct) =
        (getUByteHandle.invokeExact(struct.memory.memorySegment) as Byte).toUByte()

    fun getShort(struct: TestStruct) =
        getShortHandle.invokeExact(struct.memory.memorySegment) as Short

    fun getUShort(struct: TestStruct) =
        (getUShortHandle.invokeExact(struct.memory.memorySegment) as Short).toUShort()

    fun getInt(struct: TestStruct) =
        getIntHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getUInt(struct: TestStruct) =
        (getUIntHandle.invokeExact(struct.memory.memorySegment) as Int).toUInt()

    fun getLong(struct: TestStruct) =
        getLongHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getULong(struct: TestStruct) =
        (getULongHandle.invokeExact(struct.memory.memorySegment) as Int).toUInt()

    fun getLongLong(struct: TestStruct) =
        getLongLongHandle.invokeExact(struct.memory.memorySegment) as Long

    fun getULongLong(struct: TestStruct) =
        (getULongLongHandle.invokeExact(struct.memory.memorySegment) as Long).toULong()

    fun getFloat(struct: TestStruct) =
        getFloatHandle.invokeExact(struct.memory.memorySegment) as Float

    fun getDouble(struct: TestStruct) =
        getDoubleHandle.invokeExact(struct.memory.memorySegment) as Double

    fun getIntFromUnion(struct: TestStruct) =
        getIntFromUnionHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getLongFromUnion(struct: TestStruct) =
        getLongFromUnionHandle.invokeExact(struct.memory.memorySegment) as Long

    fun getIntFromInnerStruct(struct: TestStruct) =
        getIntFromInnerStructHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getLongFromInnerStruct(struct: TestStruct) =
        getLongFromInnerStructHandle.invokeExact(struct.memory.memorySegment) as Long

    fun setBool(struct: TestStruct, value: Boolean) {
        setBoolHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setByte(struct: TestStruct, value: Byte) {
        setByteHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setUByte(struct: TestStruct, value: UByte) {
        setUByteHandle.invokeExact(struct.memory.memorySegment, value.toByte())
    }

    fun setShort(struct: TestStruct, value: Short) {
        setShortHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setUShort(struct: TestStruct, value: UShort) {
        setUShortHandle.invokeExact(struct.memory.memorySegment, value.toShort())
    }

    fun setInt(struct: TestStruct, value: Int) {
        setIntHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setUInt(struct: TestStruct, value: UInt) {
        setUIntHandle.invokeExact(struct.memory.memorySegment, value.toInt())
    }

    fun setLong(struct: TestStruct, value: Int) {
        setLongHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setULong(struct: TestStruct, value: UInt) {
        setULongHandle.invokeExact(struct.memory.memorySegment, value.toInt())
    }

    fun setLongLong(struct: TestStruct, value: Long) {
        setLongLongHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setULongLong(struct: TestStruct, value: ULong) {
        setULongLongHandle.invokeExact(struct.memory.memorySegment, value.toLong())
    }

    fun setFloat(struct: TestStruct, value: Float) {
        setFloatHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setDouble(struct: TestStruct, value: Double) {
        setDoubleHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setIntForInnerStruct(struct: TestStruct, value: Int) {
        setIntForInnerStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setLongForInnerStruct(struct: TestStruct, value: Long) {
        setLongForInnerStructHandle.invokeExact(struct.memory.memorySegment, value)
    }
}