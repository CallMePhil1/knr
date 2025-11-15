package knr.runtime.ext

import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout

private fun getLayout(vararg layouts: MemoryLayout): Array<out MemoryLayout> {
    val structLayouts = mutableListOf<MemoryLayout>()
    var totalSize = 0L

    for (layout in layouts) {
        val byteSize = layout.byteSize()
        val padding = totalSize % layout.byteAlignment()

        if (padding > 0) {
            structLayouts.add(MemoryLayout.paddingLayout(padding))
            totalSize += padding
        }

        structLayouts.add(layout)
        totalSize += byteSize
    }

    return structLayouts.toTypedArray()
}

fun structLayout(vararg layouts: MemoryLayout): StructLayout {
    val layoutList = getLayout(*layouts)
    return MemoryLayout.structLayout(*layoutList)
}
