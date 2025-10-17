package com.github.callmephil.knr.runtime.memory

abstract class Native internal constructor(
    arc: ARC
) : AutoCloseable {
    private var _arc: ARC? = arc

    val arc: ARC
        get() = checkNotNull(_arc) { "Tried to access a Native object that was disposed of" }

    val isValid get() = _arc != null
    val isNotValid get() = _arc == null
    val refCount get() = _arc?.counter ?: 0

    override fun close() = dispose()

    open fun dispose() {
        _arc?.decrementCount()
        _arc = null
    }
}