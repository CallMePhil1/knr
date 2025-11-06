package knr.libgen.processor

import com.github.callmephil.knr.runtime.library.LibraryLoader

fun main() {
    val lib = LibraryLoader.loadLibrary(PrimitiveTestLibrary::class.java)
    assert(PrimitiveTestLibrary.instance == lib)
}