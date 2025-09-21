package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.exceptions.PointerIsNotValidException

fun validOrThrow(pointer: ByRef<*>) {
    if (pointer.isNotValid)
        throw PointerIsNotValidException()
}