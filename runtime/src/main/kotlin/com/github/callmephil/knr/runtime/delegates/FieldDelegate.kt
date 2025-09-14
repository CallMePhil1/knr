package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC
import kotlin.reflect.KProperty

abstract class FieldDelegate<T> internal constructor(
    protected val ownerArc: ARC,
    protected val offset: Long
) {
    abstract fun get(): T
    abstract fun set(value: T)

    operator fun getValue(thisRef: Any, prop: KProperty<*>): T = get()
    operator fun setValue(thisRef: Any, prop: KProperty<*>, value: T) = set(value)
}

//class OpaqueStructDelegate(
//    memorySegment: MemorySegment,
//    offset: Long,
//    private val size: Long
//) : FieldDelegate<MemorySegment>(memorySegment, offset) {
//
//    override fun get(): MemorySegment = memorySegment.reinterpret(size)
//    override fun set(value: MemorySegment) {
//        if (value.byteSize() != size)
//            throw StructSizeMismatchException(size, value.byteSize())
//        if (value.address() == memorySegment.address())
//            return
//        memorySegment.copyFrom(value)
//    }
//}
//
//class StructDelegate<T : Struct>(
//    memorySegment: MemorySegment,
//    offset: Long,
//    private val arena: Arena,
//    private val companion: StructCompanion<T>
//) : FieldDelegate<T>(memorySegment, offset) {
//
//    private val instance by lazy { companion.wrap(arena, memorySegment) }
//
//    override fun get(): T = instance
//    override fun set(value: T) {
//        val size = companion.layout.byteSize()
//
//        if (value.memorySegment.byteSize() != size)
//            throw StructSizeMismatchException(size, value.memorySegment.byteSize())
//        if (value.memorySegment.address() == memorySegment.address())
//            return
//        memorySegment.copyFrom(value.memorySegment)
//    }
//}
//
//class OpaquePointerDelegate(
//    memorySegment: MemorySegment,
//    offset: Long
//) : FieldDelegate<MemorySegment>(memorySegment, offset) {
//    override fun get(): MemorySegment = memorySegment.get(ValueLayout.ADDRESS, offset)
//    override fun set(value: MemorySegment) = memorySegment.set(ValueLayout.ADDRESS, offset, value)
//}
//
//class StructPointerDelegate()
