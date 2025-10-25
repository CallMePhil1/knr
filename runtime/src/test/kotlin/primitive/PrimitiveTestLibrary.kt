package primitive

import com.github.callmephil.knr.runtime.ext.downcallHandle
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

object PrimitiveTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/primitivetest",
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

    // endregion

    fun getBool(struct: PrimitiveStruct) =
        getBoolHandle.invokeExact(struct.memory.memorySegment) as Boolean

    fun getByte(struct: PrimitiveStruct) =
        getByteHandle.invokeExact(struct.memory.memorySegment) as Byte

    fun getUByte(struct: PrimitiveStruct) =
        (getUByteHandle.invokeExact(struct.memory.memorySegment) as Byte).toUByte()

    fun getShort(struct: PrimitiveStruct) =
        getShortHandle.invokeExact(struct.memory.memorySegment) as Short

    fun getUShort(struct: PrimitiveStruct) =
        (getUShortHandle.invokeExact(struct.memory.memorySegment) as Short).toUShort()

    fun getInt(struct: PrimitiveStruct) =
        getIntHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getUInt(struct: PrimitiveStruct) =
        (getUIntHandle.invokeExact(struct.memory.memorySegment) as Int).toUInt()

    fun getLong(struct: PrimitiveStruct) =
        getLongHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getULong(struct: PrimitiveStruct) =
        (getULongHandle.invokeExact(struct.memory.memorySegment) as Int).toUInt()

    fun getLongLong(struct: PrimitiveStruct) =
        getLongLongHandle.invokeExact(struct.memory.memorySegment) as Long

    fun getULongLong(struct: PrimitiveStruct) =
        (getULongLongHandle.invokeExact(struct.memory.memorySegment) as Long).toULong()

    fun getFloat(struct: PrimitiveStruct) =
        getFloatHandle.invokeExact(struct.memory.memorySegment) as Float

    fun getDouble(struct: PrimitiveStruct) =
        getDoubleHandle.invokeExact(struct.memory.memorySegment) as Double

    fun setBool(struct: PrimitiveStruct, value: Boolean) {
        setBoolHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setByte(struct: PrimitiveStruct, value: Byte) {
        setByteHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setUByte(struct: PrimitiveStruct, value: UByte) {
        setUByteHandle.invokeExact(struct.memory.memorySegment, value.toByte())
    }

    fun setShort(struct: PrimitiveStruct, value: Short) {
        setShortHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setUShort(struct: PrimitiveStruct, value: UShort) {
        setUShortHandle.invokeExact(struct.memory.memorySegment, value.toShort())
    }

    fun setInt(struct: PrimitiveStruct, value: Int) {
        setIntHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setUInt(struct: PrimitiveStruct, value: UInt) {
        setUIntHandle.invokeExact(struct.memory.memorySegment, value.toInt())
    }

    fun setLong(struct: PrimitiveStruct, value: Int) {
        setLongHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setULong(struct: PrimitiveStruct, value: UInt) {
        setULongHandle.invokeExact(struct.memory.memorySegment, value.toInt())
    }

    fun setLongLong(struct: PrimitiveStruct, value: Long) {
        setLongLongHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setULongLong(struct: PrimitiveStruct, value: ULong) {
        setULongLongHandle.invokeExact(struct.memory.memorySegment, value.toLong())
    }

    fun setFloat(struct: PrimitiveStruct, value: Float) {
        setFloatHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setDouble(struct: PrimitiveStruct, value: Double) {
        setDoubleHandle.invokeExact(struct.memory.memorySegment, value)
    }
}