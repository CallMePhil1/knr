package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.CString
import com.github.callmephil.knr.runtime.typing.cstringOf
import java.nio.charset.Charset

class StringDelegate(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset,
    initialValue: String = ""
) : PointerFieldDelegate<String, CString>(ownerArc, offset) {

    override var pointer: CString = cstringOf(initialValue, charset)

    init {
        updateOwnersArc()
    }
}

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

class CCharArrayDelegate(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset = Charsets.UTF_8,
    value: String = "",
    private val length: Int
) : FieldDelegate<String>(ownerArc, offset) {

    init {
        set(value)
    }

    override fun get(): String = ownerArc.getString(offset, charset)
    override fun set(value: String) {
        if (value.length > length)
            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
        ownerArc.setString(offset, value, charset)
    }
}

class CachedCCharArrayDelegate(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset = Charsets.UTF_8,
    private var value: String = "",
    private val length: Int
) : FieldDelegate<String>(ownerArc, offset) {

    init {
        set(value)
    }

    override fun get(): String = value
    override fun set(value: String) {
        if (value.length > length)
            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
        ownerArc.setString(offset, value, charset)
        this.value = value
    }
}