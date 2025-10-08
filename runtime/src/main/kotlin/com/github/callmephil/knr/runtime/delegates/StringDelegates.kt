package com.github.callmephil.knr.runtime.delegates

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.CString
import com.github.callmephil.knr.runtime.typing.CachedCString
import com.github.callmephil.knr.runtime.typing.NullableCString
import com.github.callmephil.knr.runtime.typing.NullableCachedCString
import com.github.callmephil.knr.runtime.typing.cachedCStringOf
import com.github.callmephil.knr.runtime.typing.cstringOf
import com.github.callmephil.knr.runtime.typing.nullableCStringOf
import com.github.callmephil.knr.runtime.typing.nullableCachedCStringOf
import java.nio.charset.Charset

class StringDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset,
    initialValue: String = ""
) : PointerFieldDelegate<String, CString>(ownerArc, offset) {

    override var pointer: CString = cstringOf(initialValue, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableStringDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset,
    initialValue: String?
) : NullablePointerFieldDelegate<String, NullableCString>(ownerArc, offset) {

    override var pointer: NullableCString = nullableCStringOf(initialValue, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class CachedStringDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset,
    initialValue: String
) : PointerFieldDelegate<String, CachedCString>(ownerArc, offset) {

    override var pointer: CachedCString = cachedCStringOf(initialValue, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableCachedStringDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    private val charset: Charset,
    initialValue: String?
) : NullablePointerFieldDelegate<String, NullableCachedCString>(ownerArc, offset) {

    override var pointer: NullableCachedCString = nullableCachedCStringOf(initialValue, charset, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class CCharArrayDelegate internal constructor(
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

class CachedCCharArrayDelegate internal constructor(
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