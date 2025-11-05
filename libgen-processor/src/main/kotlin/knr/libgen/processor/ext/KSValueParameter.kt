package knr.libgen.processor.ext

import com.google.devtools.ksp.symbol.KSValueParameter

val KSValueParameter.isPrimitive get() = this.type.resolve().isPrimitive

val KSValueParameter.isString get() = this.type.resolve().isString
