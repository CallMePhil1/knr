package knr.libgen.processor

import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.array.IntNativeArray
import knr.libgen.annotations.Library
import knr.libgen.annotations.Method

class TestClass(
    memory: Memory
) : Struct<TestClass>(memory)

@Library("test/path")
interface TestLibrary {
    fun primitiveFunc(byte: Byte, short: UShort, int: Int, long: Long): Int
    fun nativeFunc(cls: TestClass, array: IntNativeArray)

    @Method("this_is_the_name")
    fun namedMethod()
}