@file:OptIn(KspExperimental::class)

package knr.processors.ext

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSValueParameter
import knr.annotations.ByRef
import knr.processors.util.toMemoryLayout
import knr.runtime.typing.Struct

internal val KSValueParameter.isByRef get() = this.isAnnotationPresent(ByRef::class)

internal val KSValueParameter.isPrimitive get() = this.type.resolve().isPrimitive

internal val KSValueParameter.isString get() = this.type.resolve().isString

internal fun KSValueParameter.isStruct(resolver: Resolver) = this.type.resolve().assignableTo<Struct<*>>(resolver)

internal fun KSValueParameter.toMemoryLayout(resolver: Resolver) = toMemoryLayout(this.type.resolve(), annotations, resolver)
