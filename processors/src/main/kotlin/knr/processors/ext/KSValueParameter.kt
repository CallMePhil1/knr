package knr.processors.ext

import com.google.devtools.ksp.symbol.KSValueParameter

internal val KSValueParameter.isPrimitive get() = this.type.resolve().isPrimitive

internal val KSValueParameter.isString get() = this.type.resolve().isString
