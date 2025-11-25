package knr.libgen.processor

import knr.annotations.*
import knr.runtime.layout.StructDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.array.IntNativeArray
import knr.runtime.typing.flags.BitFlag
import knr.runtime.typing.flags.IntBitFlagSet

class TestClass(
    memory: Memory
) : Struct<TestClass>(memory, definition) {
    companion object : Struct.Companion<TestClass> {
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