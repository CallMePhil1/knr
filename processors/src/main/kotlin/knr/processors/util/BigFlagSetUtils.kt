package knr.processors.util

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import knr.processors.ext.assignableTo
import knr.processors.ext.qualifiedName
import knr.processors.ext.simpleName
import knr.runtime.typing.flags.BitFlagSet

fun KSType.getBitFlagValueType(resolver: Resolver): KSType {
    if (!this.assignableTo<BitFlagSet<*, *>>(resolver))
        error("KSType '${this.qualifiedName!!.asString()}' is not a 'BitFlagSet'")

    val interfaceType = (this.declaration as KSClassDeclaration)
        .getAllSuperTypes()
        .first { it.simpleName.asString().startsWith(BitFlagSet::class.java.simpleName) }

    val flagValueType = interfaceType.arguments[0].type!!.resolve()

    return flagValueType
}