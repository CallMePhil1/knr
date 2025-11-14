package typing.union

import knr.runtime.ext.downcallHandle
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

object UnionTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/uniontest",
        arena
    )

    private val getByteHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_byte").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getShortHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_short").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getIntHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_int").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getIntFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_int_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getIntFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_int_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getLongFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getLongFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val setByteHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_byte").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_BYTE
    )

    private val setShortHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_short").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_SHORT
    )

    private val setIntHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_int").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_INT
    )

    private val setLongHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_long").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS,
        ValueLayout.JAVA_LONG
    )

    fun getByte(union: UnionObj) = getByteHandle.invokeExact(union.memory.memorySegment) as Byte
    fun getShort(union: UnionObj) = getShortHandle.invokeExact(union.memory.memorySegment) as Short
    fun getInt(union: UnionObj) = getIntHandle.invokeExact(union.memory.memorySegment) as Int
    fun getIntFromPointer(union: UnionObj) = getIntFromPointerHandle.invokeExact(union.memory.memorySegment) as Int
    fun getIntFromStruct(union: UnionObj) = getIntFromStructHandle.invokeExact(union.memory.memorySegment) as Int
    fun getLong(union: UnionObj) = getLongHandle.invokeExact(union.memory.memorySegment) as Long
    fun getLongFromPointer(union: UnionObj) = getLongFromPointerHandle.invokeExact(union.memory.memorySegment) as Long
    fun getLongFromStruct(union: UnionObj) = getLongFromStructHandle.invokeExact(union.memory.memorySegment) as Long


    fun setByte(union: UnionObj, value: Byte) {
        setByteHandle.invokeExact(union.memory.memorySegment, value)
    }
    fun setShort(union: UnionObj, value: Short) {
        setShortHandle.invokeExact(union.memory.memorySegment, value)
    }
    fun setInt(union: UnionObj, value: Int) {
        setIntHandle.invokeExact(union.memory.memorySegment, value)
    }
    fun setLong(union: UnionObj, value: Long) {
        setLongHandle.invokeExact(union.memory.memorySegment, value)
    }
}