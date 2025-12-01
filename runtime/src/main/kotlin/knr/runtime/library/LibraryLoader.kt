package knr.runtime.library

import io.github.oshai.kotlinlogging.KotlinLogging
import knr.annotations.Library

object LibraryLoader {
    private val logger = KotlinLogging.logger {  }

    fun <R> loadLibrary(cls: Class<R>): R {
        val libName = cls.canonicalName

        logger.debug { "Loading library '$libName'" }

        if (!cls.isInterface) {
            throw IllegalStateException("Class '$libName' is not an interface")
        }
        if (!cls.annotations.any { it is Library }) {
            val annoName = Library::class.java.canonicalName
            throw IllegalStateException("Interface '$libName' does not have the '$annoName' annotation")
        }

        val name = "${cls.packageName}.generated.${cls.simpleName}Impl"
        val libCls = Class.forName(name)

        logger.debug { "Found implementation '$name'" }

        val objectInstance = libCls.getDeclaredField("INSTANCE").get(null) as R

        return objectInstance
    }

    inline fun <reified R> loadLibrary() = loadLibrary(R::class.java)
}

