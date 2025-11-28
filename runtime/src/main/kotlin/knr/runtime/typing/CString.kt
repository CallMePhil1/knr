package knr.runtime.typing

import knr.runtime.memory.ArenaMemory
import knr.runtime.memory.Memory
import knr.runtime.native.StringLib
import knr.runtime.typing.pointer.Pointer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

open class CString internal constructor(
    memory: Memory,
    val charset: Charset
) : Pointer<String>(memory) {

    override fun get(): String = memory.getString(0, charset)

    override fun set(value: String) {
        throw NotImplementedError("CString is immutable")
    }

    override fun clone() = cstringOf(get(), charset)
}

class CachedCString(
    memory: Memory,
    private var value: String = "",
    charset: Charset
) : CString(memory, charset) {

    override fun get(): String = value

    override fun clone() = cachedCStringOf(get(), charset)

    fun updateCache() {
        checkNotNull(memory.memorySegment) { "Tried to update cache for CachedCString but it's pointing to NULL" }
        value = memory.getString(0, charset)
    }
}

infix fun Pointer<String>.equal(other: Pointer<String>) = StringLib.equal(this, other)

val Pointer<String>.length get() = StringLib.length(this)

fun cstringOf(memory: Memory, charset: Charset = StandardCharsets.UTF_8) = CString(memory, charset)
fun cstringOf(value: String = "", charset: Charset = Charsets.UTF_8) =
    CString(ArenaMemory.string(value, charset), charset)

fun cachedCStringOf(value: String = "", charset: Charset = Charsets.UTF_8) =
    CachedCString(ArenaMemory.string(value, charset), value, charset)
