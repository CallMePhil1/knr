package knr.processors.ext

import com.google.devtools.ksp.symbol.KSAnnotation
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName

internal fun <R> KSAnnotation.get(name: String): R =
    this.arguments.firstOrNull {
        it.name!!.asString() == name
    }?.value as R ?: throw NoSuchElementException("Annotation '${this.shortName.asString()}' does not have parameter '$name'")

internal inline fun <reified T> Sequence<KSAnnotation>.get(): KSAnnotation {
    val anno = getOrNull<T>()

    if (anno == null) {
        val listValues = this.joinToString(prefix = "{ ", postfix = " }") { it.shortName.asString() }
        throw NoSuchElementException("Could not find annotation '${T::class.java.canonicalName}' in list $listValues")
    }

    return anno
}

internal inline fun <reified T> Sequence<KSAnnotation>.getOrNull(): KSAnnotation? {
    return this.firstOrNull { it.annotationType.toTypeName() == T::class.java.asTypeName() }
}

internal inline fun <reified T> Sequence<KSAnnotation>.has() =
    this.any { it.annotationType.toTypeName() == T::class.java.asTypeName() }
