package knr.libgen.processor

import knr.runtime.library.LibraryLoader
import java.lang.invoke.MethodHandles

fun main() {
    val lib = LibraryLoader.loadLibrary(PrimitiveTestLibrary::class.java)
    assert(PrimitiveTestLibrary.instance == lib)
}