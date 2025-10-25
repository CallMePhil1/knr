package com.github.callmephil.knr.runtime.typing

import com.github.callmephil.knr.runtime.memory.ArenaMemory
import com.github.callmephil.knr.runtime.memory.Memory
import com.github.callmephil.knr.runtime.native.StringLib
import com.github.callmephil.knr.runtime.typing.pointer.Pointer
import java.nio.charset.Charset

open class CString internal constructor(
    memory: Memory,
    onArcUpdated: (() -> Unit)? = null,
    val charset: Charset
) : Pointer<String>(memory, onArcUpdated) {

    override fun get(): String = memory.getString(0, charset)

    override fun set(value: String) {
        throw NotImplementedError("CString is immutable")
    }

    override fun copyOf() = cstringOf(get(), charset)
}

class CachedCString(
    memory: Memory,
    onArcUpdated: (() -> Unit)? = null,
    private var value: String = "",
    charset: Charset
) : CString(memory, onArcUpdated, charset) {

    override fun get(): String = value

    override fun copyOf() = cachedCStringOf(get(), charset)

    fun updateCache() {
        checkNotNull(memory.memorySegment) { "Tried to update cache for CachedCString but it's pointing to NULL" }
        value = memory.getString(0, charset)
    }
}

infix fun Pointer<String>.equal(other: Pointer<String>) = StringLib.equal(this, other)

val Pointer<String>.length get() = StringLib.length(this)

internal fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8, onPointerUpdated: (() -> Unit)?) =
    CString(ArenaMemory.string(value, charset), onPointerUpdated, charset)
fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8) =
    cstringOf(value, charset, null)

internal fun cachedCStringOf(value: String = "", charset: Charset = Charsets.UTF_8, onPointerUpdated: (() -> Unit)?) =
    CachedCString(ArenaMemory.string(value, charset), onPointerUpdated, value, charset)
fun cachedCStringOf(value: String = "", charset: Charset = Charsets.UTF_8) = cachedCStringOf(value, charset, null)
