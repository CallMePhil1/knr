package knr.libgen.processor.ext

import com.google.devtools.ksp.symbol.KSDeclaration
import knr.libgen.processor.util.primitiveTypes

val KSDeclaration.isPrimitive
    get() = this.qualifiedName!!.asString() in primitiveTypes

val KSDeclaration.isString
    get() = this.qualifiedName!!.asString() == "kotlin.String"
