package knr.libgen.processor.ext

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation

internal fun <R> KSAnnotation.get(name: String): R =
    this.arguments.firstOrNull {
        it.name!!.asString() == name
    }?.value as R ?: throw NoSuchElementException("Annotation '${this.shortName.asString()}' does not have parameter '$name'")

internal inline fun <reified T> Sequence<KSAnnotation>.get(resolver: Resolver): KSAnnotation {
    val anno = this.firstOrNull { it.annotationType.resolve().assignableTo<T>(resolver) }

    if (anno == null) {
        val listValues = this.joinToString(prefix = "{ ", postfix = " }") { it.shortName.asString() }
        throw NoSuchElementException("Could not find annotation '${T::class.java.canonicalName}' in list $listValues")
    }

    return anno
}


internal inline fun <reified T> Sequence<KSAnnotation>.has(resolver: Resolver) =
    this.any { it.annotationType.resolve().assignableTo<T>(resolver) }
