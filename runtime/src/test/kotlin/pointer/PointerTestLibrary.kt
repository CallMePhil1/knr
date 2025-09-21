package pointer

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.pointer.ByRef
import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.LongPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.ShortPointer
import com.github.callmephil.knr.runtime.typing.pointer.UBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.UIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.ULongPointer
import com.github.callmephil.knr.runtime.typing.pointer.UShortPointer
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

    fun getByteFromPointer(pointer: ByRef<Byte>) =
        getByteFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as Byte

    fun getUByteFromPointer(pointer: ByRef<UByte>) =
        getUByteFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as UByte

    fun getShortFromPointer(pointer: ByRef<Short>) =
        getShortFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as Short

    fun getUShortFromPointer(pointer: ByRef<UShort>) =
        getUShortFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as UShort

    fun getIntFromPointer(pointer: ByRef<Int>) =
        getIntFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as Int

    fun getUIntFromPointer(pointer: ByRef<UInt>) =
        getUIntFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as UInt

    fun getLongFromPointer(pointer: ByRef<Long>) =
        getLongFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as Long

    fun getULongFromPointer(pointer: ByRef<ULong>) =
        getULongFromPointerHandle.invokeExact(pointer.arc!!.memorySegment) as ULong

    fun getLongFromStruct(struct: PointedStruct) =
        getLongFromStructHandle.invokeExact(struct.arc.memorySegment) as Int

    fun getIntViaPointerFromStruct(struct: AllPointers) =
        getIntViaPointerFromStructHandle.invokeExact(struct.arc.memorySegment) as Int

    fun getNullableIntViaPointerFromStruct(struct: AllPointers): Int {
        return getNullableIntViaPointerFromStructHandle.invokeExact(struct.arc.memorySegment) as Int
    }

    fun setByteForPointer(pointer: ByRef<Byte>, value: Byte) {
        setByteForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setUByteForPointer(pointer: ByRef<UByte>, value: UByte) {
        setUByteForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setShortForPointer(pointer: ByRef<Short>, value: Short) {
        setShortForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setShortForPointer(pointer: ByRef<UShort>, value: UShort) {
        setUShortForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setIntForPointer(pointer: ByRef<Int>, value: Int) {
        setIntForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setUIntForPointer(pointer: ByRef<UInt>, value: UInt) {
        setUIntForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setLongForPointer(pointer: ByRef<Long>, value: Long) {
        setLongLongForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setULongForPointer(pointer: ByRef<ULong>, value: ULong) {
        setULongLongForPointerHandle.invokeExact(pointer.arc!!.memorySegment, value)
    }

    fun setLongForStruct(struct: PointedStruct, value: Int) {
        setLongForStructHandle.invokeExact(struct.arc.memorySegment, value)
    }

    fun setIntViaPointerFromStruct(struct: AllPointers, value: Int) {
        setIntViaPointerFromStructHandle.invokeExact(struct.arc.memorySegment, value)
    }

    fun setNullableIntViaPointerFromStruct(struct: AllPointers, value: Int) {
        if (struct.ni.isNull)
            throw NullPointerException("Field 'ni' of 'AllPointers' was null")
        setNullableIntViaPointerFromStructHandle.invokeExact(struct.arc.memorySegment, value)
    }
}