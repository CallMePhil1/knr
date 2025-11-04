package knr.libgen.processor

import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.array.IntNativeArray
import com.github.callmephil.knr.runtime.typing.flags.BitFlag
import com.github.callmephil.knr.runtime.typing.flags.IntBitFlagSet
import knr.libgen.annotations.Library
import knr.libgen.annotations.Method
import knr.libgen.annotations.NoVerify

class TestClass(
    memory: Memory
) : Struct<TestClass>(memory)

enum class TestBitFlags(override val mask: Int): BitFlag<Int> {
    FIRST(1)
}

@Library("test/path")
interface TestLibrary {
    fun primitiveFunc(byte: Byte, short: UShort, int: Int, long: Long): Int
    fun nativeFunc(cls: TestClass, array: IntNativeArray, bitMask: IntBitFlagSet<TestBitFlags>): Int

    @NoVerify
    fun noVerifyFunc(cls: TestClass, array: IntNativeArray)

    fun noVerifyParam(@NoVerify cls: TestClass, array: IntNativeArray)

    @Method("this_is_the_name")
    fun namedMethod()
}