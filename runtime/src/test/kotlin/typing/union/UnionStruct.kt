package typing.union

import knr.runtime.memory.Memory
import knr.runtime.typing.Union
import knr.runtime.typing.UnionCompanion
import java.lang.foreign.MemoryLayout
import java.lang.foreign.UnionLayout
import java.lang.foreign.ValueLayout

class UnionStruct(
    memory: Memory
) : Union(memory) {
    var c by byteField()
    var s by shortField()
    var i by intField()
    var l by longField()

    var p by nullableLongPointerField()
    var p1 by nullableIntPointerField()

    companion object : UnionCompanion<UnionStruct> {
        override val layout: UnionLayout = MemoryLayout.unionLayout(
            ValueLayout.JAVA_BYTE,
            ValueLayout.JAVA_SHORT,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG
        )

        override fun wrap(memory: Memory) = UnionStruct(memory)
    }
}