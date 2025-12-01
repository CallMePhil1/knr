@file:Suppress("UNCHECKED_CAST")

package knr.runtime.library

import io.github.oshai.kotlinlogging.KotlinLogging
import knr.annotations.Library
import knr.runtime.util.SymbolLookupUtils
import java.nio.file.Path

object LibraryLoader {
    private val logger = KotlinLogging.logger {  }

    private val initializedLibraries = hashMapOf<Class<*>, Any>()

    fun addPaths(vararg paths: String) {
        SymbolLookupUtils.locations.addAll(paths.map { Path.of(it) })
    }

    fun <R : Any> loadLibrary(cls: Class<R>): R {
        val libName = cls.canonicalName

        logger.debug { "Loading library '$libName'" }

        if (initializedLibraries.contains(cls)) {
            logger.debug { "Returning cached library" }
            return initializedLibraries[cls] as R
        }

        if (!cls.isInterface) {
            throw IllegalStateException("Class '$libName' is not an interface")
        }

        val libAnno = cls.annotations.firstOrNull { it is Library } as Library?

        if (libAnno == null) {
            val annoName = Library::class.java.canonicalName
            throw IllegalStateException("Interface '$libName' does not have the '$annoName' annotation")
        }

        val name = "${cls.packageName}.generated.${cls.simpleName}Impl"
        val libCls = Class.forName(name)

        logger.debug { "Found implementation '$name'" }

        val objectInstance = libCls.getDeclaredField("INSTANCE").get(null) as R

        initializedLibraries[cls] = objectInstance

        return objectInstance
    }

    inline fun <reified R : Any> loadLibrary() = loadLibrary(R::class.java)
}

