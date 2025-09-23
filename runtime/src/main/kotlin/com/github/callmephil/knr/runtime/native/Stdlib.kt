package com.github.callmephil.knr.runtime.native

import java.lang.foreign.Linker

object Stdlib {
    internal val stdlibLinker = Linker.nativeLinker()
    internal val stdlib = Linker.nativeLinker().defaultLookup()
}