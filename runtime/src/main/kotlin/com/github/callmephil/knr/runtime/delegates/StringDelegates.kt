package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.typing.Struct
import java.nio.charset.Charset

class CCharArrayDelegate internal constructor(
    parent: Struct,
    offset: Long,
    private val charset: Charset = Charsets.UTF_8,
    value: String = "",
    private val length: Int
) : FieldDelegate<String>(parent, offset) {

    init {
        set(value)
    }

    override fun get(): String = parent.memory.getString(offset, charset)
    override fun set(value: String) {
        if (value.length > length)
            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
        parent.memory.setString(offset, value, charset)
    }
}

class CachedCCharArrayDelegate internal constructor(
    parent: Struct,
    offset: Long,
    private val charset: Charset = Charsets.UTF_8,
    private var value: String = "",
    private val length: Int
) : FieldDelegate<String>(parent, offset) {

    init {
        set(value)
    }

    override fun get(): String = value
    override fun set(value: String) {
        if (value.length > length)
            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
        parent.memory.setString(offset, value, charset)
        this.value = value
    }
}