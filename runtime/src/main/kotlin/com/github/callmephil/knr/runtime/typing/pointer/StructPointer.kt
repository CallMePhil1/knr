//package com.github.callmephil.knr.runtime.typing.pointer
//
//import com.github.callmephil.knr.runtime.typing.Struct
//import java.lang.foreign.MemorySegment
//
//class NullableStructPointer<S : Struct>(
//    private var struct: S?
//) : Pointer<S?, S?>(struct?.memorySegment ?: MemorySegment.NULL) {
//
//    override fun get(): S? = struct
//
//    override fun reference(ref: S?) {
//        struct = ref
//        memorySegment = ref?.memorySegment ?: MemorySegment.NULL
//    }
//
//    override fun set(value: S) {
//        memorySegment.copyFrom(value.memorySegment)
//    }
//}
//
//class StructPointer<S : Struct>(
//    private var struct: S
//) : Pointer<S, S>(struct.memorySegment) {
//
//    override fun get(): S = struct
//
//    override fun reference(ref: S) {
//        if (ref.memorySegment == MemorySegment.NULL) {
//            val structType = ref::class.java.simpleName
//            throw NullPointerException("Tried to reference a '$structType' with a NULL memory segment to a non-nullable 'StructPointer<$structType>'.")
//        }
//        struct = ref
//        memorySegment = ref.memorySegment
//    }
//
//    override fun set(value: S) {
//        memorySegment.copyFrom(value.memorySegment)
//    }
//}