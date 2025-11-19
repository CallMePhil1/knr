package typing.struct

import knr.runtime.ext.structLayout
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import knr.runtime.typing.Union
import knr.runtime.typing.UnionCompanion
import knr.runtime.typing.pointer.IntPointer
import knr.runtime.typing.pointer.intPointerOf
import java.lang.foreign.MemoryLayout.sequenceLayout
import java.lang.foreign.MemoryLayout.unionLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.UnionLayout
import java.lang.foreign.ValueLayout

class TestUnion(
    memory: Memory
) : Union(memory) {
    var i by intField()
    var l by longField()

    companion object : UnionCompanion<TestUnion> {
        override val layout: UnionLayout = unionLayout(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG
        )

        override fun wrap(memory: Memory) = TestUnion(memory)
    }
}

class InnerStruct(
    memory: Memory
) : Struct<InnerStruct>(memory) {
    var i by intField(0)
    var l by longField(8)

    companion object : StructCompanion<InnerStruct> {
        override val layout: StructLayout = structLayout(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG
        )

        override fun wrap(memory: Memory) = InnerStruct(memory)
    }
}

class TestStruct(
    memory: Memory
) : Struct<TestStruct>(memory) {

    var c by byteField(0)
    var uc by ubyteField(1)

    var s by shortField(2)
    var us by ushortField(4)

    var i by intField(8)
    var ui by uintField(12)

    var l by intField(16)
    var ul by uintField(20)

    var ll by longField(24)
    var ull by ulongField(32)

    var f by floatField(40)
    var d by doubleField(48)

    var b by booleanField(56)

    var u by unionField(64, TestUnion)

    var innerStruct by structField(72, InnerStruct)
    var structArray by structArrayField(88, 4, InnerStruct)
    var pointerArray by pointerArrayField(152, 4) { _, slice -> intPointerOf(0, slice) }
    var nullablePointerArray by pointerArrayField(184, 4) { idx, slice -> if (idx % 2 == 0) intPointerOf(0, slice) else null }

    companion object : StructCompanion<TestStruct> {
        override val layout: StructLayout = structLayout(
            ValueLayout.JAVA_BYTE.withName("c"),
            ValueLayout.JAVA_BYTE.withName("uc"),
            ValueLayout.JAVA_SHORT.withName("s"),
            ValueLayout.JAVA_SHORT.withName("us"),
            ValueLayout.JAVA_INT.withName("i"),
            ValueLayout.JAVA_INT.withName("ui"),
            ValueLayout.JAVA_INT.withName("l"),
            ValueLayout.JAVA_INT.withName("ul"),
            ValueLayout.JAVA_LONG.withName("ll"),
            ValueLayout.JAVA_LONG.withName("ull"),
            ValueLayout.JAVA_FLOAT.withName("f"),
            ValueLayout.JAVA_DOUBLE.withName("d"),
            ValueLayout.JAVA_BOOLEAN.withName("b"),
            TestUnion.layout,
            InnerStruct.layout,
            sequenceLayout(4, InnerStruct.layout),
            sequenceLayout(4, ValueLayout.ADDRESS),
            sequenceLayout(4, ValueLayout.ADDRESS)
        )

        override fun wrap(memory: Memory) = TestStruct(memory)
    }
}