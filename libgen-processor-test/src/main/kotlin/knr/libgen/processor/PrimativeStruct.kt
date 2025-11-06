package knr.libgen.processor

import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.StructCompanion
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

class PrimitiveStruct(
    memory: Memory
) : Struct<PrimitiveStruct>(memory) {

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

    companion object : StructCompanion<PrimitiveStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_BYTE.withName("c"),
            ValueLayout.JAVA_BYTE.withName("uc"),
            ValueLayout.JAVA_SHORT.withName("s"),
            ValueLayout.JAVA_SHORT.withName("us"),
            MemoryLayout.paddingLayout(2),
            ValueLayout.JAVA_INT.withName("i"),
            ValueLayout.JAVA_INT.withName("ui"),
            ValueLayout.JAVA_INT.withName("l"),
            ValueLayout.JAVA_INT.withName("ul"),
            ValueLayout.JAVA_LONG.withName("ll"),
            ValueLayout.JAVA_LONG.withName("ull"),
            ValueLayout.JAVA_FLOAT.withName("f"),
            MemoryLayout.paddingLayout(4),
            ValueLayout.JAVA_DOUBLE.withName("d"),
            ValueLayout.JAVA_BOOLEAN.withName("b"),
            MemoryLayout.paddingLayout(7)
        )

        override fun wrap(memory: Memory) = PrimitiveStruct(memory)
    }
}