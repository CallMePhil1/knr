package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.IntPointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableIntPointer
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.intPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableIntPointerOf
import com.github.callmephil.knr.runtime.typing.pointer.shareFrom
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

abstract class PointerFieldDelegate<T, P : Pointer<T, ARC>>(
    ownerArc: ARC,
    offset: Long,
) : FieldDelegate<P>(
    ownerArc,
    offset
) {
    protected abstract var pointer: P

    override fun get() = pointer

    override fun set(value: P) {
        pointer shareFrom value
        updateOwnersArc()
    }

    protected fun updateOwnersArc() {
        ownerArc.setAddress(offset, pointer.arc!!.memorySegment!!)
    }
}

abstract class NullablePointerFieldDelegate<T, P : Pointer<T, ARC?>>(
    ownerArc: ARC,
    offset: Long,
) : FieldDelegate<P>(
    ownerArc,
    offset
) {
    protected abstract var pointer: P

    override fun get() = pointer

    override fun set(value: P) {
        pointer shareFrom value
        updateOwnersArc()
    }

    protected fun updateOwnersArc() {
        ownerArc.setAddress(offset, pointer.arc?.memorySegment ?: MemorySegment.NULL)
    }
}

//class BytePointerDelegate(
//    ownerArc: ARC,
//    offset: Long
//) : FieldDelegate<BytePointer>(ownerArc, offset) {
//
//    private var pointer = bytePointerOf(ownerArc, offset)
//
//    override fun get() = pointer
//
//    override fun set(value: BytePointer) {
//        pointer.arc.decrementCount()
//        pointer = value
//        value.arc.incrementCount()
//        ownerArc.setAddress(offset, value.arc.memorySegment!!)
//    }
//}
//
//class NullableBytePointerDelegate(
//    ownerArc: ARC,
//    offset: Long
//) : FieldDelegate<NullableBytePointer>(ownerArc, offset) {
//
//    private var pointer = NullableBytePointer(
//        ownerArc
//            .getAddress(offset)
//            .reinterpret(ValueLayout.JAVA_BYTE.byteSize())
//    )
//
//    override fun get() = pointer
//
//    override fun set(value: NullableBytePointer) {
//        pointer = value
//        ownerArc.setAddress(offset, value.ownerArc)
//    }
//}

class IntPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: Int
) : PointerFieldDelegate<Int, IntPointer>(ownerArc, offset) {

    override var pointer: IntPointer = intPointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableIntPointerDelegate(
    ownerArc: ARC,
    offset: Long,
    initialValue: Int?
) : NullablePointerFieldDelegate<Int, NullableIntPointer>(ownerArc, offset) {

    override var pointer: NullableIntPointer = nullableIntPointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}