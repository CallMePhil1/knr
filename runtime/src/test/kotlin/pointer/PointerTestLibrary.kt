package pointer

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
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

    private val getLongFromPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("get_long_from_pointer").orElseThrow(),
        retType = ValueLayout.JAVA_INT,
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

    private val setLongForPointerHandle: MethodHandle = linker.downcallHandle(
        segment = lookup.find("set_long_for_pointer").orElseThrow(),
        retType = null,
        ValueLayout.ADDRESS, ValueLayout.JAVA_INT
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

    fun getLongFromPointer(pointer: IntPointer) =
        getLongFromPointerHandle.invokeExact(pointer.memorySegment) as Int

    fun getLongFromStruct(struct: PointedStruct) =
        getLongFromStructHandle.invokeExact(struct.memorySegment) as Int

    fun getIntViaPointerFromStruct(struct: AllPointers) =
        getIntViaPointerFromStructHandle.invokeExact(struct.memorySegment) as Int

    fun setLongForPointer(pointer: IntPointer, value: Int) {
        setLongForPointerHandle.invokeExact(pointer.memorySegment, value)
    }

    fun setLongForStruct(struct: PointedStruct, value: Int) {
        setLongForStructHandle.invokeExact(struct.memorySegment, value)
    }

    fun setIntViaPointerFromStruct(struct: AllPointers, value: Int) {
        setIntViaPointerFromStructHandle.invokeExact(struct.memorySegment, value)
    }
}