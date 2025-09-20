package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.memory.ARC

abstract class NullablePrimitivePointer<T> internal constructor(
    arc: ARC?,
    onArcUpdated: (() -> Unit)?
) : NullablePointer<T>(arc, onArcUpdated) {

    val isNull: Boolean get() {
        validOrThrow(this)
        return arc!!.isNull
    }

    abstract fun shareOf(): NullablePrimitivePointer<T>

    inline fun ifIsNull(block: NullablePrimitivePointer<T>.() -> Unit): NullablePrimitivePointer<T> {
        if (isNull)
            block()
        return this
    }

    inline fun ifNotNull(block: NullablePrimitivePointer<T>.(T) -> Unit): NullablePrimitivePointer<T> {
        if (!isNull)
            block(get())
        return this
    }
}

abstract class PrimitivePointer<T> internal constructor(
    arc: ARC,
    onArcUpdated: (() -> Unit)?
) : Pointer<T>(arc, onArcUpdated) {
    abstract fun shareOf(): PrimitivePointer<T>
}