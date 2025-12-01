package primitives

import knr.annotations.Library
import knr.annotations.NamingConvention
import knr.runtime.library.LibraryLoader
import knr.runtime.typing.pointer.NativePointer

@Library("primitivelib", naming = NamingConvention.SNAKECASE)
interface PrimitiveTestLibrary {
    fun getBool(struct: NativePointer<PrimitiveStruct>): Boolean
    fun setBool(struct: NativePointer<PrimitiveStruct>, value: Boolean)

    fun getByte(struct: NativePointer<PrimitiveStruct>): Byte
    fun setByte(struct: NativePointer<PrimitiveStruct>, value: Byte)

    fun getShort(struct: NativePointer<PrimitiveStruct>): Short
    fun setShort(struct: NativePointer<PrimitiveStruct>, value: Short)

    fun getInt(struct: NativePointer<PrimitiveStruct>): Int
    fun setInt(struct: NativePointer<PrimitiveStruct>, value: Int)

    fun getLong(struct: NativePointer<PrimitiveStruct>): Long
    fun setLong(struct: NativePointer<PrimitiveStruct>, value: Long)

    fun getFloat(struct: NativePointer<PrimitiveStruct>): Float
    fun setFloat(struct: NativePointer<PrimitiveStruct>, value: Float)

    fun getDouble(struct: NativePointer<PrimitiveStruct>): Double
    fun setDouble(struct: NativePointer<PrimitiveStruct>, value: Double)

    companion object : PrimitiveTestLibrary by LibraryLoader.loadLibrary()
}