package knr.processors.ext

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import knr.processors.util.toMemoryLayout

fun KSFunctionDeclaration.returnToMemoryLayout(resolver: Resolver) = toMemoryLayout(this.returnType!!.resolve(), annotations, resolver)
