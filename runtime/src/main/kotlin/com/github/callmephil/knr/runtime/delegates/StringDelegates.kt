package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.typing.CString
import com.github.callmephil.knr.runtime.typing.CachedCString
import com.github.callmephil.knr.runtime.typing.Struct
import com.github.callmephil.knr.runtime.typing.cachedCStringOf
import com.github.callmephil.knr.runtime.typing.cstringOf
import java.nio.charset.Charset

class StringDelegate internal constructor(
    parent: Struct,
    offset: Long,
    private val charset: Charset,
    initialValue: String = ""
) : PointerFieldDelegate<String, CString>(parent, offset) {

    override var pointer: CString = cstringOf(initialValue, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableStringDelegate internal constructor(
    parent: Struct,
    offset: Long,
    private val charset: Charset,
    initialValue: String?
) : NullablePointerFieldDelegate<String, CString>(parent, offset) {

    override var pointer: CString? = cstringOf(initialValue!!, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class CachedStringDelegate internal constructor(
    parent: Struct,
    offset: Long,
    private val charset: Charset,
    initialValue: String
) : PointerFieldDelegate<String, CachedCString>(parent, offset) {

    override var pointer: CachedCString = cachedCStringOf(initialValue, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableCachedStringDelegate internal constructor(
    parent: Struct,
    offset: Long,
    private val charset: Charset,
    initialValue: String?
) : NullablePointerFieldDelegate<String, CachedCString>(parent, offset) {

    override var pointer: CachedCString? = cachedCStringOf(initialValue!!, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

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

    override fun get(): String = parent.arc.getString(offset, charset)
    override fun set(value: String) {
        if (value.length > length)
            throw StringIndexOutOfBoundsException("Tried to write a string of length ${value.length} to a fixed length string of $length")
        parent.arc.setString(offset, value, charset)
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
        parent.arc.setString(offset, value, charset)
        this.value = value
    }
}