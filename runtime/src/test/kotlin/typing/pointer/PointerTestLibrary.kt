package typing.pointer

import knr.runtime.ext.downcallHandle
import knr.runtime.typing.pointer.Pointer
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

object PointerTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/pointertest",
        arena
    )

    private val getByteFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_byte_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getUByteFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_ubyte_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getShortFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_short_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getUShortFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_ushort_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getIntFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_int_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getUIntFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_uint_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long_long_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getULongFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_ulong_long_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getLongFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getByteViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_byte_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getNullableByteViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_nullable_byte_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_BYTE,
        ValueLayout.ADDRESS
    )

    private val getShortViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_short_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getNullableShortViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_nullable_short_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_SHORT,
        ValueLayout.ADDRESS
    )

    private val getIntViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_int_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getNullableIntViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_nullable_int_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS
    )

    private val getLongViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val getNullableLongViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_nullable_long_via_pointer_from_struct").orElseThrow(),
        retType = ValueLayout.JAVA_LONG,
        ValueLayout.ADDRESS
    )

    private val setByteForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_byte_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE
    )

    private val setUByteForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_ubyte_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE
    )

    private val setShortForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_short_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_SHORT
    )

    private val setUShortForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_ushort_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_SHORT
    )

    private val setIntForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_int_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setUIntForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_uint_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setLongLongForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_long_long_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    private val setULongLongForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_ulong_long_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    private val setLongForStructHandle = linker.downcallHandle(
        segment = lookup.find("set_long_for_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setByteViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_byte_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE
    )

    private val setNullableByteViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_nullable_byte_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE
    )

    private val setShortViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_short_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_SHORT
    )

    private val setNullableShortViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_nullable_short_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_SHORT
    )

    private val setIntViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_int_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setNullableIntViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_nullable_int_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
    )

    private val setLongViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_long_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    private val setNullableLongViaPointerFromStructHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_nullable_long_via_pointer_from_struct").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_LONG
    )

    fun getByteFromPointer(pointer: Pointer<Byte>) =
        getByteFromPointerHandle.invokeExact(pointer.memory.memorySegment) as Byte

    fun getUByteFromPointer(pointer: Pointer<UByte>) =
        getUByteFromPointerHandle.invokeExact(pointer.memory.memorySegment) as UByte

    fun getShortFromPointer(pointer: Pointer<Short>) =
        getShortFromPointerHandle.invokeExact(pointer.memory.memorySegment) as Short

    fun getUShortFromPointer(pointer: Pointer<UShort>) =
        getUShortFromPointerHandle.invokeExact(pointer.memory.memorySegment) as UShort

    fun getIntFromPointer(pointer: Pointer<Int>) =
        getIntFromPointerHandle.invokeExact(pointer.memory.memorySegment) as Int

    fun getUIntFromPointer(pointer: Pointer<UInt>) =
        getUIntFromPointerHandle.invokeExact(pointer.memory.memorySegment) as UInt

    fun getLongFromPointer(pointer: Pointer<Long>) =
        getLongFromPointerHandle.invokeExact(pointer.memory.memorySegment) as Long

    fun getULongFromPointer(pointer: Pointer<ULong>) =
        getULongFromPointerHandle.invokeExact(pointer.memory.memorySegment) as ULong

    fun getLongFromStruct(struct: PointedStruct) =
        getLongFromStructHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getByteViaPointerFromStruct(struct: AllPointers) =
        getByteViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Byte

    fun getNullableByteViaPointerFromStruct(struct: AllPointers) =
        getNullableByteViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Byte

    fun getShortViaPointerFromStruct(struct: AllPointers) =
        getShortViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Short

    fun getNullableShortViaPointerFromStruct(struct: AllPointers) =
        getNullableShortViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Short

    fun getIntViaPointerFromStruct(struct: AllPointers) =
        getIntViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getNullableIntViaPointerFromStruct(struct: AllPointers) =
        getNullableIntViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Int

    fun getLongViaPointerFromStruct(struct: AllPointers) =
        getLongViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Long

    fun getNullableLongViaPointerFromStruct(struct: AllPointers) =
        getNullableLongViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment) as Long

    fun setByteForPointer(pointer: Pointer<Byte>, value: Byte) {
        setByteForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setUByteForPointer(pointer: Pointer<UByte>, value: UByte) {
        setUByteForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setShortForPointer(pointer: Pointer<Short>, value: Short) {
        setShortForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setShortForPointer(pointer: Pointer<UShort>, value: UShort) {
        setUShortForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setIntForPointer(pointer: Pointer<Int>, value: Int) {
        setIntForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setUIntForPointer(pointer: Pointer<UInt>, value: UInt) {
        setUIntForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setLongForPointer(pointer: Pointer<Long>, value: Long) {
        setLongLongForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setULongForPointer(pointer: Pointer<ULong>, value: ULong) {
        setULongLongForPointerHandle.invokeExact(pointer.memory.memorySegment, value)
    }

    fun setLongForStruct(struct: PointedStruct, value: Int) {
        setLongForStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setByteViaPointerFromStruct(struct: AllPointers, value: Byte) {
        setByteViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setNullableByteViaPointerFromStruct(struct: AllPointers, value: Byte) {
        setNullableByteViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setShortViaPointerFromStruct(struct: AllPointers, value: Short) {
        setShortViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setNullableShortViaPointerFromStruct(struct: AllPointers, value: Short) {
        setNullableShortViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setIntViaPointerFromStruct(struct: AllPointers, value: Int) {
        setIntViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setNullableIntViaPointerFromStruct(struct: AllPointers, value: Int) {
        setNullableIntViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setLongViaPointerFromStruct(struct: AllPointers, value: Long) {
        setLongViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }

    fun setNullableLongViaPointerFromStruct(struct: AllPointers, value: Long) {
        setNullableLongViaPointerFromStructHandle.invokeExact(struct.memory.memorySegment, value)
    }
}