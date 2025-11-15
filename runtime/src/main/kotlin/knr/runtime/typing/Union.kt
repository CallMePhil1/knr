package knr.runtime.typing

import knr.runtime.delegates.BooleanDelegate
import knr.runtime.delegates.ByteDelegate
import knr.runtime.delegates.DoubleDelegate
import knr.runtime.delegates.FloatDelegate
import knr.runtime.delegates.IntDelegate
import knr.runtime.delegates.LongDelegate
import knr.runtime.delegates.ShortDelegate
import knr.runtime.delegates.UByteDelegate
import knr.runtime.delegates.UIntDelegate
import knr.runtime.delegates.ULongDelegate
import knr.runtime.delegates.UShortDelegate
import knr.runtime.delegates.union.PointerFieldDelegate
import knr.runtime.delegates.union.StructFieldDelegate
import knr.runtime.delegates.union.UnionFieldDelegate
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.pointer.*
import java.lang.foreign.Arena
import java.lang.foreign.UnionLayout
import java.lang.foreign.ValueLayout

abstract class Union(
    memory: Memory
) : Native<Union>(memory) {

    override fun copyTo(native: Union) {
        this.memory.copyTo(native.memory)
    }

    protected fun booleanField() = BooleanDelegate(this, 0)

    protected fun byteField() = ByteDelegate(this, 0)
    protected fun ubyteField() = UByteDelegate(this, 0)

    protected fun shortField() = ShortDelegate(this, 0)
    protected fun ushortField() = UShortDelegate(this, 0)

    protected fun intField() = IntDelegate(this, 0)
    protected fun uintField() = UIntDelegate(this, 0)

    protected fun longField() = LongDelegate(this, 0)
    protected fun ulongField() = ULongDelegate(this, 0)

    protected fun floatField() = FloatDelegate(this, 0)
    protected fun doubleField() = DoubleDelegate(this, 0)

    protected fun <T: Struct<T>> structField(structCompanion: StructCompanion<T>) = StructFieldDelegate(this, structCompanion)
    protected fun <T: Union> unionField(unionCompanion: UnionCompanion<T>) = UnionFieldDelegate(this, unionCompanion)

    protected fun <T, P : Pointer<T>?> pointerField(byteSize: Long, ctor: (Memory) -> P): PointerFieldDelegate<T, P?> =
        PointerFieldDelegate(this, byteSize, ctor)

    protected fun nullableOpaquePointer() = pointerField(0, ::OpaquePointer)

    protected fun nullableBytePointerField() = pointerField(ValueLayout.JAVA_BYTE.byteSize(), ::BytePointer)
    protected fun nullableUBytePointerField() = pointerField(ValueLayout.JAVA_BYTE.byteSize(), ::UBytePointer)

    protected fun nullableShortPointerField() = pointerField(ValueLayout.JAVA_SHORT.byteSize(), ::ShortPointer)
    protected fun nullableUShortPointerField() = pointerField(ValueLayout.JAVA_SHORT.byteSize(), ::UShortPointer)

    protected fun nullableIntPointerField() = pointerField(ValueLayout.JAVA_INT.byteSize(), ::IntPointer)
    protected fun nullableUIntPointerField() = pointerField(ValueLayout.JAVA_INT.byteSize(), ::UIntPointer)

    protected fun nullableLongPointerField() = pointerField(ValueLayout.JAVA_LONG.byteSize(), ::LongPointer)
    protected fun nullableULongPointerField() = pointerField(ValueLayout.JAVA_LONG.byteSize(), ::ULongPointer)
}

interface UnionCompanion<T : Union> {
    val layout: UnionLayout

    fun allocate(): T {
        val arena = Arena.ofShared()
        val segment = arena.allocate(layout)
        val memory = ArenaMemory(arena, segment)
        return wrap(memory)
    }

    fun wrap(memory: Memory): T
}
