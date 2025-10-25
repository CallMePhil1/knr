package string

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout

object StringTestLibrary {
    private val arena: Arena = Arena.global()
    private val linker: Linker = Linker.nativeLinker()
    private val lookup: SymbolLookup = SymbolLookup.libraryLookup(
        "src/test/testlib/build/Debug/stringtest",
        arena
    )

    private val areStrsEqualHandle = linker.downcallHandle(
        segment = lookup.find("are_strs_equal").orElseThrow(),
        retType = ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    )

    private val structStringEqualHandle = linker.downcallHandle(
        segment = lookup.find("struct_str_equal").orElseThrow(),
        retType = ValueLayout.JAVA_BOOLEAN,
        ValueLayout.ADDRESS,
        ValueLayout.ADDRESS
    )

    fun areStringsEqual(str1: Pointer<String>, str2: Pointer<String>) =
        areStrsEqualHandle.invokeExact(str1.memory.memorySegment, str2.memory.memorySegment) as Boolean

    fun structStringEqual(stringStruct: StringStruct, str: Pointer<String>) =
        structStringEqualHandle.invokeExact(stringStruct.memory.memorySegment, str.memory.memorySegment) as Boolean
}