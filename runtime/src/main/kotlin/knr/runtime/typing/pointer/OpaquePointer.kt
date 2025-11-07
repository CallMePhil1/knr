//package knr.runtime.typing.pointer
//
//import java.lang.foreign.MemorySegment
//
//class NullableOpaquePointer(
//    memorySegment: MemorySegment
//) : PrimitivePointer<MemorySegment>(memorySegment) {
//
//    override fun get() = memorySegment
//
//    override fun reference(ref: MemorySegment) {
//        memorySegment = ref
//    }
//
//    override fun set(value: MemorySegment) {
//        if (memorySegment == MemorySegment.NULL) {
//            throw NullPointerException("Tried to set a NullableOpaquePointer with NULL memory segment. Use reference to set it to NULL.")
//        }
//        memorySegment.copyFrom(value)
//    }
//}
//
//class OpaquePointer(
//    memorySegment: MemorySegment
//) : PrimitivePointer<MemorySegment>(memorySegment) {
//    init {
//        if (memorySegment == MemorySegment.NULL) {
//            throw NullPointerException("Tried to construct a non-nullable OpaquePointer with NULL memory segment.")
//        }
//    }
//
//    override fun get() = memorySegment
//
//    override fun reference(ref: MemorySegment) {
//        if (ref == MemorySegment.NULL)
//            throw NullPointerException("Tried to reference a NULL memory segment to a non-nullable OpaquePointer.")
//        memorySegment = ref
//    }
//
//    override fun set(value: MemorySegment) {
//        if (value == MemorySegment.NULL)
//            throw NullPointerException("Tried to set a NULL memory segment to a non-nullable OpaquePointer.")
//        memorySegment.copyFrom(value)
//    }
//}