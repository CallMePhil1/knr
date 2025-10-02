package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.pointer.ByRef
import com.github.callmephil.knr.runtime.typing.pointer.NullablePointer
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

open class NullableCString internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    val charset: Charset
) : NullablePointer<String>(arc, onArcUpdated) {

    override fun get(): String {
        if (arc!!.isNull) {
            throw NullPointerException("Tried to get string from a NullableCString pointing to NULL")
        }
        return arc!!.getString(0, charset)
    }

    override fun set(value: String) {
        throw NotImplementedError("NullableCString is immutable")
    }

    override fun shareOf(): NullableCString {
        validOrThrow(this)
        return NullableCString(arc!!, null, charset)
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

class NullableCachedCString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    initialValue: String? = null,
    charset: Charset
) : NullableCString(arc, onArcUpdated, charset) {

    private var value: String? = initialValue

    override fun get(): String = value!!

    override fun pointTo(arc: ARC) {
        super.pointTo(arc)
        value = if (arc.isNull) {
            null
        } else {
            arc.getString(0, charset)
        }
    }

    override fun shareOf(): NullableCachedCString {
        validOrThrow(this)
        return NullableCachedCString(arc!!, null, arc!!.getString(0, charset), charset)
    }

    fun updateCache() {
        value = when {
            arc!!.isNull -> null
            else -> arc!!.getString(0, charset)
        }
    }
}

infix fun ByRef<String>.equal(other: ByRef<String>) = StringLib.equal(this, other)

val ByRef<String>.length get() = StringLib.length(this)

internal fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8, onArcUpdated: (() -> Unit)?) =
    CString(ARC.string(value, charset), onArcUpdated, charset)
fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8) =
    cstringOf(value, charset, null)

internal fun nullableCStringOf(value: String? = null, charset: Charset = Charsets.UTF_8, onArcUpdated: (() -> Unit)?) =
    when (value) {
        null -> NullableCString(ARC.ofNull(), onArcUpdated, charset)
        else -> NullableCString(ARC.string(value, charset), onArcUpdated, charset)
    }
fun nullableCStringOf(value: String? = null, charset: Charset = Charsets.UTF_8) =
    nullableCStringOf(value, charset, null)
