package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.validOrThrow
import java.nio.charset.Charset

open class CString internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    val charset: Charset
) : Pointer<String>(arc, onArcUpdated) {

    override fun get(): String = arc!!.getString(0, charset)

    override fun set(value: String) {
        throw NotImplementedError("CString is immutable")
    }

    override fun shareOf(): CString {
        validOrThrow(this)
        return CString(arc!!, null, charset)
    }
}

class CachedCString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    private var value: String = "",
    charset: Charset
) : CString(arc, onArcUpdated, charset) {

    override fun get(): String = value

    override fun shareOf(): CachedCString {
        validOrThrow(this)
        return CachedCString(arc!!, null, arc!!.getString(0, charset), charset)
    }

    fun updateCache() {
        if (arc!!.isNull) {
            throw NullPointerException("Tried to update cache for CachedCString but it's pointing to NULL")
        }
        value = arc!!.getString(0, charset)
    }
}

infix fun Pointer<String>.equal(other: Pointer<String>) = StringLib.equal(this, other)

val Pointer<String>.length get() = StringLib.length(this)

internal fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8, onArcUpdated: (() -> Unit)?) =
    CString(ARC.string(value, charset), onArcUpdated, charset)
fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8) =
    cstringOf(value, charset, null)

internal fun cachedCStringOf(value: String = "", charset: Charset = Charsets.UTF_8, onArcUpdated: (() -> Unit)?) =
    CachedCString(ARC.string(value, charset), onArcUpdated, value, charset)
fun cachedCStringOf(value: String = "", charset: Charset = Charsets.UTF_8) = cachedCStringOf(value, charset, null)
