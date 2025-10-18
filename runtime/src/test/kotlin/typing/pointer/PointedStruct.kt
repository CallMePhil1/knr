package typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.StructCompanion
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

class PointedStruct(
    arc: ARC
) : Struct(arc) {

    var l by intField(0)

    companion object : StructCompanion<PointedStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("l")
        )

        override fun wrap(arc: ARC) = PointedStruct(arc)

    }
}

class AllPointers(
    arc: ARC
) : Struct(arc) {

    var b by bytePointerField(0)
    var nb by nullableBytePointerField(8)
    var ub by ubytePointerField(16)
    var nub by nullableUBytePointerField(24)

    var s by shortPointerField(32)
    var ns by nullableShortPointerField(40)
    var us by ushortPointerField(48)
    var nus by nullableUShortPointerField(56)

    var i by intPointerField(64)
    var ni by nullableIntPointerField(72)
    var ui by uintPointerField(80)
    var nui by nullableUIntPointerField(88)

    var l by longPointerField(96)
    var nl by nullableLongPointerField(104)
    var ul by ulongPointerField(112)
    var nul by nullableULongPointerField(120)

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

        override fun wrap(arc: ARC) = AllPointers(arc)
    }
}