package knr.runtime.layout

import knr.runtime.typing.Struct
import knr.runtime.typing.StructCompanion
import java.lang.foreign.MemoryLayout
import java.lang.foreign.PaddingLayout
import java.lang.foreign.StructLayout

class StructDefinition(
    val layout: StructLayout,
    val offsets: List<Long>
) {
    val byteSize = layout.byteSize()
    fun offset(index: Int) = offsets[index]
}

inline fun <reified T: Struct<T>> StructCompanion<T>.structDefinition(vararg elements: MemoryLayout): StructDefinition {
    val offsets = mutableListOf<Long>()

    val structLayouts = mutableListOf<MemoryLayout>()
    var totalSize = 0L

    for (element in elements) {
        if (element is PaddingLayout)
            error("Found Memory.paddingLayout in call to structDefinition for '${T::class.java.canonicalName}'")
        val byteSize = element.byteSize()
        val alignmentOffset = totalSize % element.byteAlignment()
        val padding = if (alignmentOffset != 0L) byteSize - alignmentOffset else 0

        if (padding > 0) {
            structLayouts.add(MemoryLayout.paddingLayout(padding))
            totalSize += padding
        }

        offsets.add(totalSize)
        structLayouts.add(element)
        totalSize += byteSize
    }

    val layout = MemoryLayout.structLayout(*structLayouts.toTypedArray())

    return StructDefinition(layout, offsets)
}