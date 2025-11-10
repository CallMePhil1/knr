package knr.processors.ext

import knr.runtime.typing.Native
import knr.runtime.typing.flags.BitFlagSet
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import knr.processors.util.valueLayoutMap

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

internal fun KSType.inheritsNative(resolver: Resolver) = getNativeType(resolver).isAssignableFrom(this)

internal inline fun <reified T> KSType.assignableTo(resolver: Resolver) =
    getKSTypeFromClass(resolver, T::class.java).isAssignableFrom(this)

internal fun KSType.toValueLayoutString(resolver: Resolver): String {
    return when {
        this.isPrimitive -> valueLayoutMap[this.declaration.qualifiedName!!.asString()]!!
        this.assignableTo<BitFlagSet<*, *>>(resolver) -> {
            val property = (this.declaration as KSClassDeclaration).getAllProperties().first { it.simpleName.asString() == "mask" }
            return valueLayoutMap[property.type.resolve().qualifiedName!!.asString()]!!
        }
        this.isString ||
        this.inheritsNative(resolver) -> "ValueLayout.ADDRESS"
        else -> error("Couldn't convert type '${this.qualifiedName!!.asString()}' to ValueLayout")
    }
}
