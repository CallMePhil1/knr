package knr.libgen.processor

import knr.libgen.annotations.Library
import knr.libgen.annotations.Method
import knr.runtime.library.LibraryLoader

@Library("libgen-processor-test/src/main/testlib/build/Debug/primitivetest")
interface PrimitiveTestLibrary {

    fun getBool(struct: PrimitiveStruct): Boolean

    @Method("get_char")
    fun getByte(struct: PrimitiveStruct): Byte

    @Method("get_uchar")
    fun getUByte(struct: PrimitiveStruct): UByte

    fun getFloat(struct: PrimitiveStruct): Float

    fun getDouble(struct: PrimitiveStruct): Double

    fun setBool(struct: PrimitiveStruct, value: Boolean)

    companion object {
        val instance by lazy { LibraryLoader.loadLibrary(PrimitiveTestLibrary::class.java) }
    }
}