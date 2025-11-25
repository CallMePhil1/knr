package knr.processors.util

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import knr.processors.ext.assignableTo
import knr.processors.ext.qualifiedName
import knr.processors.ext.simpleName
import knr.runtime.typing.NativeEnum

fun KSType.getNativeEnumValueType(resolver: Resolver): KSType {
    if (!this.assignableTo<NativeEnum<*>>(resolver))
        error("KSType '${this.qualifiedName!!.asString()}' is not a 'NativeEnum'")

    val interfaceType = (this.declaration as KSClassDeclaration)
        .getAllSuperTypes()
        .first { it.simpleName.asString() == "NativeEnum" }

    return interfaceType.arguments[0].type!!.resolve()
}
