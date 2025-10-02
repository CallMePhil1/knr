package string

import com.github.callmephil.knr.runtime.ext.downcallHandle
import com.github.callmephil.knr.runtime.typing.pointer.ByRef
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

    fun areStringsEqual(str1: ByRef<String>, str2: ByRef<String>) =
        areStrsEqualHandle.invokeExact(str1.arc!!.memorySegment, str2.arc!!.memorySegment) as Boolean

    fun structStringEqual(stringStruct: StringStruct, str: ByRef<String>) =
        structStringEqualHandle.invokeExact(stringStruct.arc.memorySegment, str.arc!!.memorySegment) as Boolean
}