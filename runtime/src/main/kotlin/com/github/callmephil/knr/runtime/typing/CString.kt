package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.pointer.ByRef
import com.github.callmephil.knr.runtime.typing.pointer.NullablePointer
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import com.github.callmephil.knr.runtime.typing.pointer.validOrThrow
import java.nio.charset.Charset

class CString(
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

class NullableCString(
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
    val charset: Charset
) : Pointer<String>(arc, onArcUpdated) {

    override fun get(): String = value

    override fun set(value: String) {
        throw NotImplementedError("CachedCString is immutable")
    }

    override fun shareOf(): CachedCString {
        validOrThrow(this)
        return CachedCString(arc!!, null, arc!!.getString(0, charset), charset)
    }

    fun updateCache() {
        value = arc!!.getString(0, charset)
    }
}

class NullableCachedCString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    initialValue: String? = null,
    val charset: Charset
) : NullablePointer<String>(arc, onArcUpdated) {

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

    override fun set(value: String) {
        throw NotImplementedError("NullableCachedCString is immutable")
    }

    override fun shareOf(): NullableCachedCString {
        validOrThrow(this)
        return NullableCachedCString(arc!!, null, arc!!.getString(0, charset), charset)
    }

    fun updateCache() {
        if (arc!!.isNull) {
            throw NullPointerException("Tried to update cache for NullableCachedCString but it's pointing to NULL")
        }
        value = arc!!.getString(0, charset)
    }
}

infix fun ByRef<String>.cmp(other: ByRef<String>) = StringLib.stringCompare(this, other)

val ByRef<String>.length get() = StringLib.stringLength(this)

fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8): CString {
    val arc = ARC.string(value, charset)
    return CString(arc, null, charset)
}