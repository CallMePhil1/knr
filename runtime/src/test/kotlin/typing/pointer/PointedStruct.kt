package typing.pointer

import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.StructCompanion
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.longPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shortPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ubytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.uintPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ulongPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ushortPointerOf
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

class PointedStruct(
    memory: Memory
) : Struct<PointedStruct>(memory) {

    var l by intField(0)

    companion object : StructCompanion<PointedStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("l")
        )

        override fun wrap(memory: Memory) = PointedStruct(memory)
    }
}

class AllPointers(
    memory: Memory
) : Struct<AllPointers>(memory) {

    var b by bytePointerField(0, bytePointerOf())
    var nb by nullableBytePointerField(8, null)
    var ub by ubytePointerField(16, ubytePointerOf())
    var nub by nullableUBytePointerField(24, null)

    var s by shortPointerField(32, shortPointerOf())
    var ns by nullableShortPointerField(40, null)
    var us by ushortPointerField(48, ushortPointerOf())
    var nus by nullableUShortPointerField(56, null)

    var i by intPointerField(64, intPointerOf())
    var ni by nullableIntPointerField(72, null)
    var ui by uintPointerField(80, uintPointerOf())
    var nui by nullableUIntPointerField(88, null)

    var l by longPointerField(96, longPointerOf())
    var nl by nullableLongPointerField(104, null)
    var ul by ulongPointerField(112, ulongPointerOf())
    var nul by nullableULongPointerField(120, null)

    companion object : StructCompanion<AllPointers> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS
        )

        override fun wrap(memory: Memory) = AllPointers(memory)
    }
}