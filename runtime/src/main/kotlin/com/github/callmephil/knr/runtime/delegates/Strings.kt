package com.github.callmephil.knr.runtime.delegates

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.charset.Charset

//class StringDelegate(
//    memorySegment: MemorySegment,
//    offset: Long,
//    private val arena: Arena,
//    private val charset: Charset = Charsets.UTF_8,
//    value: String = ""
//) : FieldDelegate<String>(memorySegment, offset) {
//
//    private lateinit var stringMemorySegment: MemorySegment
//
//    init {
//        set(value)
//    }
//
//    override fun get(): String = stringMemorySegment.getString(0, charset)
//    override fun set(value: String) {
//        stringMemorySegment = arena.allocateFrom(value, charset)
//        memorySegment.set(ValueLayout.ADDRESS, offset, stringMemorySegment)
//    }
//}
//
//class CachedStringDelegate(
//    memorySegment: MemorySegment,
//    offset: Long,
//    private val arena: Arena,
//    private val charset: Charset = Charsets.UTF_8,
//    private var value: String = ""
//) : FieldDelegate<String>(memorySegment, offset) {
//
//    private lateinit var stringMemorySegment: MemorySegment
//
//    init {
//        set(value)
//    }
//
//    override fun get(): String = value
//    override fun set(value: String) {
//        stringMemorySegment = arena.allocateFrom(value, charset)
//        memorySegment.set(ValueLayout.ADDRESS, offset, stringMemorySegment)
//        this.value = value
//    }
//}
//
//class FixedLengthStringDelegate(
//    memorySegment: MemorySegment,
//    offset: Long,
//    private val charset: Charset = Charsets.UTF_8,
//    value: String = "",
//    private val length: Int
//) : FieldDelegate<String>(memorySegment, offset) {
//
//    init {
//        set(value)
//    }
//
//    override fun get(): String = memorySegment.getString(offset, charset)
//    override fun set(value: String) {
//        if (value.length > length)
//            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
//        memorySegment.setString(offset, value, charset)
//    }
//}
//
//class CachedFixedLengthStringDelegate(
//    memorySegment: MemorySegment,
//    offset: Long,
//    private val charset: Charset = Charsets.UTF_8,
//    private var value: String = "",
//    private val length: Int
//) : FieldDelegate<String>(memorySegment, offset) {
//
//    init {
//        set(value)
//    }
//
//    override fun get(): String = value
//    override fun set(value: String) {
//        if (value.length > length)
//            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
//        memorySegment.setString(offset, value, charset)
//        this.value = value
//    }
//}