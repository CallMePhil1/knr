package knr.processors.ext

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSType
import knr.runtime.typing.Native
import knr.runtime.typing.Struct

private var nativeType: KSType? = null

internal fun getKSTypeFromClass(resolver: Resolver, cls: Class<*>) =
    resolver.getClassDeclarationByName(cls.name)!!.asStarProjectedType()

private fun getNativeType(resolver: Resolver): KSType {
    if (nativeType == null)
        nativeType = getKSTypeFromClass(resolver, Native::class.java)
    return nativeType!!
}

internal val KSType.isPrimitive: Boolean
    get() = this.declaration.isPrimitive

internal val KSType.isString: Boolean
    get() = this.declaration.isString

internal val KSType.qualifiedName
    get() = this.declaration.qualifiedName

internal val KSType.simpleName
    get() = this.declaration.simpleName

internal fun KSType.isStruct(resolver: Resolver) = assignableTo<Struct<*>>(resolver)

internal fun KSType.inheritsNative(resolver: Resolver) = getNativeType(resolver).isAssignableFrom(this)

internal inline fun <reified T> KSType.assignableTo(resolver: Resolver) =
    getKSTypeFromClass(resolver, T::class.java).isAssignableFrom(this)
