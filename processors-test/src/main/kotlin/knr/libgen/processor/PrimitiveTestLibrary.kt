package knr.libgen.processor

import knr.annotations.Library
import knr.annotations.Method
import knr.annotations.NamingConvention
import knr.runtime.library.LibraryLoader

@Library("processors-test/src/main/testlib/build/Debug/primitivetest", naming = NamingConvention.PASCALCASE)
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