package knr.libgen.processor

import knr.runtime.layout.StructDefinition
import knr.runtime.memory.Memory
import knr.runtime.typing.Struct
import java.lang.foreign.ValueLayout

class PrimitiveStruct(
    memory: Memory
) : Struct<PrimitiveStruct>(memory, definition) {

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

    companion object : Struct.Companion<PrimitiveStruct> {
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
        )

        override fun wrap(memory: Memory) = PrimitiveStruct(memory)
    }
}