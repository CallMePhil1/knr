package pointer

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

    var i by intPointerField(0, 0)
    var ni by nullableIntPointerField(8, null)

    companion object : StructCompanion<AllPointers> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS
        )

        override fun wrap(arc: ARC) = AllPointers(arc)
    }
}