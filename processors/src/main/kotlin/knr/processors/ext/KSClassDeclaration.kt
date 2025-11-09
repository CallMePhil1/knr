package knr.processors.ext

import com.google.devtools.ksp.symbol.KSDeclaration
import knr.processors.util.primitiveTypes

internal val KSDeclaration.isPrimitive
    get() = this.qualifiedName!!.asString() in primitiveTypes

internal val KSDeclaration.isString
    get() = this.qualifiedName!!.asString() == "kotlin.String"
