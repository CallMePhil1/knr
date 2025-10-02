package string

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.StructCompanion
import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

class StringStruct(
    arc: ARC
) : Struct(arc) {
    var strPointer by cstringField(0, Charsets.UTF_8, "")

    companion object : StructCompanion<StringStruct> {
        override val layout: StructLayout = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withName("str_ptr")
        )

        override fun wrap(arc: ARC) = StringStruct(arc)
    }
}