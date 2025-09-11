package pointer

import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.StructCompanion
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

class PointedStruct(
    arena: Arena,
    memorySegment: MemorySegment
) : Struct(arena, memorySegment) {

    var l by intField(0)

    companion object : StructCompanion<PointedStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("l")
        )

        override fun wrap(
            arena: Arena,
            memorySegment: MemorySegment
        ) = PointedStruct(arena, memorySegment)

    }
}

class AllPointers(
    arena: Arena,
    memorySegment: MemorySegment
) : Struct(arena, memorySegment) {

    var i by intPointerField(0, 0)
    var ni by nullableIntPointerField(0, null)

    companion object : StructCompanion<AllPointers> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS
        )

        override fun wrap(
            arena: Arena,
            memorySegment: MemorySegment
        ) = AllPointers(arena, memorySegment)
    }
}