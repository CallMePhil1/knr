package knr.libgen.processor

import knr.runtime.library.LibraryLoader

fun main() {
    val lib = LibraryLoader.loadLibrary(PrimitiveTestLibrary::class.java)
    assert(PrimitiveTestLibrary.instance == lib)
}