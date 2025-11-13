package knr.runtime.typing

import knr.runtime.delegates.union.*
import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.typing.pointer.*
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.UnionLayout

abstract class Union(
    memory: Memory
) : Native<Union>(memory) {
    private var inUseDelegate: PointerFieldDelegate<*, Pointer<*>?>? = null

    private fun clearInUseDelegate() {
        if (inUseDelegate != null) {
            inUseDelegate!!.uncheckedSet(null)
            inUseDelegate = null
        }
    }

    private fun updateMemoryForPointer(pointer: Pointer<*>?) {
        when {
            this.isNotValid -> return
            pointer == null -> memory.setAddress(0, MemorySegment.NULL)
            pointer.isNotValid -> return
            else -> memory.setAddress(0, pointer.memory.memorySegment!!)
        }
    }

    protected fun booleanField() = BooleanDelegate(this, 0, ::clearInUseDelegate)

    protected fun byteField() = ByteDelegate(this, 0, ::clearInUseDelegate)
    protected fun ubyteField() = UByteDelegate(this, 0, ::clearInUseDelegate)

    protected fun shortField() = ShortDelegate(this, 0, ::clearInUseDelegate)
    protected fun ushortField() = UShortDelegate(this, 0, ::clearInUseDelegate)

    protected fun intField() = IntDelegate(this, 0, ::clearInUseDelegate)
    protected fun uintField() = UIntDelegate(this, 0, ::clearInUseDelegate)

    protected fun longField() = LongDelegate(this, 0, ::clearInUseDelegate)
    protected fun ulongField() = ULongDelegate(this, 0, ::clearInUseDelegate)

    protected fun floatField() = FloatDelegate(this, 0, ::clearInUseDelegate)
    protected fun doubleField() = DoubleDelegate(this, 0, ::clearInUseDelegate)

    protected fun <T, P : Pointer<T>?> pointerField(): PointerFieldDelegate<T, P?> =
        PointerFieldDelegate(this, 0, null) {
            if (inUseDelegate != this) {
                inUseDelegate?.uncheckedSet(null)
                updateMemoryForPointer(this.get())
                inUseDelegate = this as PointerFieldDelegate<*, Pointer<*>?>
            } else {
                updateMemoryForPointer(this.get())
            }
        }

    protected fun nullableBytePointerField() = pointerField<Byte, BytePointer?>()
    protected fun nullableUBytePointerField() = pointerField<UByte, UBytePointer?>()

    protected fun nullableShortPointerField() = pointerField<Short, ShortPointer?>()
    protected fun nullableUShortPointerField() = pointerField<UShort, UShortPointer?>()

    protected fun nullableIntPointerField() = pointerField<Int, IntPointer?>()
    protected fun nullableUIntPointerField() = pointerField<UInt, UIntPointer?>()

    protected fun nullableLongPointerField() = pointerField<Long, LongPointer?>()
    protected fun nullableULongPointerField() = pointerField<ULong, ULongPointer?>()
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
