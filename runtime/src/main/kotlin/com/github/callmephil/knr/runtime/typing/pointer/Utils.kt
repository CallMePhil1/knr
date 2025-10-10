package com.github.callmephil.knr.runtime.typing.pointer

import com.github.callmephil.knr.runtime.exceptions.PointerIsNotValidException

inline fun <T: ByRef<*>, R> use(
    ref: T,
    block: (ref: T) -> R
) {
    block(ref)
    ref.dispose()
}

inline fun <T1: ByRef<*>, T2: ByRef<*>, R> use(
    ref1: T1,
    ref2: T2,
    block: (ref1: T1, ref2: T2) -> R
) {
    block(ref1, ref2)
    ref1.dispose()
    ref2.dispose()
}

inline fun <T1: ByRef<*>, T2: ByRef<*>, T3: ByRef<*>, R> use(
    ref1: T1,
    ref2: T2,
    ref3: T3,
    block: (ref1: T1, ref2: T2, ref3: T3) -> R
) {
    block(ref1, ref2, ref3)
    ref1.dispose()
    ref2.dispose()
    ref3.dispose()
}

inline fun <T1: ByRef<*>, T2: ByRef<*>, T3: ByRef<*>, T4: ByRef<*>, R> use(
    ref1: T1,
    ref2: T2,
    ref3: T3,
    ref4: T4,
    block: (ref1: T1, ref2: T2, ref3: T3, ref4: T4) -> R
) {
    block(ref1, ref2, ref3, ref4)
    ref1.dispose()
    ref2.dispose()
    ref3.dispose()
    ref4.dispose()
}

inline fun <T1: ByRef<*>, T2: ByRef<*>, T3: ByRef<*>, T4: ByRef<*>, T5: ByRef<*>, R> use(
    ref1: T1,
    ref2: T2,
    ref3: T3,
    ref4: T4,
    ref5: T5,
    block: (ref1: T1, ref2: T2, ref3: T3, ref4: T4, ref5: T5) -> R
) {
    block(ref1, ref2, ref3, ref4, ref5)
    ref1.dispose()
    ref2.dispose()
    ref3.dispose()
    ref4.dispose()
    ref5.dispose()
}

fun validOrThrow(pointer: ByRef<*>) {
    if (pointer.isNotValid)
        throw PointerIsNotValidException()
}
