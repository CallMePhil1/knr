package knr.runtime.layout

import java.lang.foreign.StructLayout

class StructDefinition internal constructor(
    val layout: StructLayout,
    val offsets: List<Long>
) {
    val byteSize = layout.byteSize()
    fun offset(index: Int) = offsets[index]
}
