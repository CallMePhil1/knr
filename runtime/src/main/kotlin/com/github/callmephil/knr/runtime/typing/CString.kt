package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.pointer.ByRef
import com.github.callmephil.knr.runtime.typing.pointer.NullablePointer
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import java.nio.charset.Charset

class CString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    private val charset: Charset
) : Pointer<String>(arc, onArcUpdated) {

    override fun get(): String = arc!!.getString(0, charset)

    override fun set(value: String) {
        throw NotImplementedError("CString is immutable")
    }
}

class NullableCString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    private val charset: Charset
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
}

class CachedCString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    private var value: String = "",
    private val charset: Charset
) : Pointer<String>(arc, onArcUpdated) {

    override fun get(): String = value

    override fun set(value: String) {
        throw NotImplementedError("CachedCString is immutable")
    }

    fun updateCache() {
        value = arc!!.getString(0, charset)
    }
}

class NullableCachedCString(
    arc: ARC,
    onArcUpdated: (() -> Unit)? = null,
    initialValue: String? = null,
    private val charset: Charset
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