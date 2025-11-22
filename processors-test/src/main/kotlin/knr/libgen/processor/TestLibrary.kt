package knr.libgen.processor

import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import knr.runtime.typing.array.IntNativeArray
import knr.runtime.typing.flags.BitFlag
import knr.runtime.typing.flags.IntBitFlagSet
import knr.annotations.IgnoreReturnsNative
import knr.annotations.Library
import knr.annotations.Method
import knr.annotations.ReturnsNative
import knr.annotations.StringParam
import knr.runtime.layout.StructDefinition

class TestClass(
    memory: Memory
) : Struct<TestClass>(memory, definition) {
    companion object : StructCompanion<TestClass> {
        override val definition: StructDefinition = structDefinition()

        override fun wrap(memory: Memory) = TestClass(memory)
    }
}

enum class TestBitFlags(override val mask: Int): BitFlag<Int> {
    FIRST(1)
}

@Library("test/path")
@ReturnsNative(TestLibrary::class, "disposeTestClass")
interface TestLibrary {
    fun primitiveFunc(byte: Byte, short: UShort, int: Int, long: Long): Int
    fun nativeFunc(cls: TestClass, array: IntNativeArray, bitMask: IntBitFlagSet<TestBitFlags>): Int

    fun noVerifyFunc(cls: TestClass, array: IntNativeArray)

    fun noVerifyParam(cls: TestClass, array: IntNativeArray)

    @Method("this_is_the_name")
    fun namedMethod()

    fun stringMethod(@StringParam string: String, @StringParam("nonstandard") string2: String)

    @ReturnsNative(TestLibrary::class, "disposeTestClass2")
    fun createTestClass(): TestClass

    fun createTestClass2(): TestClass

    @IgnoreReturnsNative
    fun createTestClass3(): TestClass

    fun disposeTestClass(cls: TestClass)

    fun disposeTestClass2(cls: TestClass)
}