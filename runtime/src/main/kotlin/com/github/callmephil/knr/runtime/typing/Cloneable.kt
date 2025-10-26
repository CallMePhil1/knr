package com.github.callmephil.knr.runtime.typing

interface NativeCloneable<T> {
    fun clone(): T
}