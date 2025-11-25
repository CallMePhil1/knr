package typing.struct

import knr.runtime.layout.StructDefinition
import knr.runtime.layout.enumLayout
import knr.runtime.memory.Memory
import knr.runtime.typing.NativeEnum
import knr.runtime.typing.Struct
import knr.runtime.typing.Union
import knr.runtime.typing.pointer.intPointerOf
import java.lang.foreign.MemoryLayout.sequenceLayout
import java.lang.foreign.MemoryLayout.unionLayout
import java.lang.foreign.UnionLayout
import java.lang.foreign.ValueLayout

enum class StructEnum(override val value: Int) : NativeEnum<Int> {
    LOG_ALL(0),
    LOG_TRACE(1),
    LOG_DEBUG(2),
    LOG_INFO(3),
    LOG_WARNING(4),
    LOG_ERROR(5),
    LOG_FATAL(6),
    LOG_NONE(7);

    companion object : NativeEnum.Companion<Int, StructEnum>(StructEnum.entries)
}

class TestUnion(
    memory: Memory
) : Union(memory) {
    var i by intField()
    var l by longField()

    companion object : Union.Companion<TestUnion> {
        override val layout: UnionLayout = unionLayout(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG
        )

        override fun wrap(memory: Memory) = TestUnion(memory)
    }
}

class InnerStruct(
    memory: Memory
) : Struct<InnerStruct>(memory, definition) {
    var i by intField()
    var l by longField()

    companion object : Struct.Companion<InnerStruct> {
        override val definition: StructDefinition = structDefinition(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG
        )

        override fun wrap(memory: Memory) = InnerStruct(memory)
    }
}

class TestStruct(
    memory: Memory
) : Struct<TestStruct>(memory, definition) {

    var c by byteField()
    var uc by ubyteField()

    var s by shortField()
    var us by ushortField()

    var i by intField()
    var ui by uintField()

    var l by intField()
    var ul by uintField()

    var ll by longField()
    var ull by ulongField()

    var f by floatField()
    var d by doubleField()

    var b by booleanField()

    var structEnum by enumField(enumCompanion = StructEnum)

    var u by unionField(unionCompanion = TestUnion)

    var innerStruct by structField(structCompanion = InnerStruct)
    var structArray by structArrayField(size = 4, structCompanion = InnerStruct)
    var pointerArray by pointerArrayField(size = 4) { _, slice -> intPointerOf(0, slice) }
    var nullablePointerArray by pointerArrayField(size = 4) { idx, slice -> if (idx % 2 == 0) intPointerOf(0, slice) else null }

    companion object : Struct.Companion<TestStruct> {
        override val definition: StructDefinition = structDefinition(
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
            enumLayout(StructEnum),
            TestUnion.layout,
            InnerStruct.definition.layout,
            sequenceLayout(4, InnerStruct.definition.layout),
            sequenceLayout(4, ValueLayout.ADDRESS),
            sequenceLayout(4, ValueLayout.ADDRESS)
        )

        override fun wrap(memory: Memory) = TestStruct(memory)
    }
}