package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class BytePointerDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<BytePointer>(memorySegment, offset) {

    private var pointer = bytePointerOf(
        memorySegment
            .get(ValueLayout.ADDRESS, offset)
            .reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )

    override fun get() = pointer

    override fun set(value: BytePointer) {
        pointer = value
        memorySegment.set(ValueLayout.ADDRESS, offset, value.memorySegment)
    }
}

class NullableBytePointerDelegate(
    memorySegment: MemorySegment,
    offset: Long
) : FieldDelegate<NullableBytePointer>(memorySegment, offset) {

    private var pointer = NullableBytePointer(
        memorySegment
            .get(ValueLayout.ADDRESS, offset)
            .reinterpret(ValueLayout.JAVA_BYTE.byteSize())
    )

    override fun get() = pointer

    override fun set(value: NullableBytePointer) {
        pointer = value
        memorySegment.set(ValueLayout.ADDRESS, offset, value.memorySegment)
    }
}

class IntPointerDelegate(
    arena: Arena,
    ownerSegment: MemorySegment,
    offset: Long,
    initialValue: Int
) : FieldDelegate<IntPointer>(ownerSegment, offset) {

    private lateinit var pointer: IntPointer

    init {
        set(intPointerOf(initialValue, arena))
    }

    override fun get() = pointer

    override fun set(value: IntPointer) {
        pointer = value
        memorySegment.set(ValueLayout.ADDRESS, offset, value.memorySegment)
    }
}

class NullableIntPointerDelegate(
    arena: Arena,
    ownerSegment: MemorySegment,
    offset: Long,
    initialValue: Int?
) : FieldDelegate<NullableIntPointer>(ownerSegment, offset) {

    private lateinit var pointer: NullableIntPointer

    init {
        if (initialValue == null)
            set(nullableIntPointerOf())
        else
            set(nullableIntPointerOf(initialValue, arena))
    }

    override fun get() = pointer

    override fun set(value: NullableIntPointer) {
        pointer = value
        memorySegment.set(ValueLayout.ADDRESS, offset, value.memorySegment)
    }
}