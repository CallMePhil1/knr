package knr.runtime.util

import io.github.oshai.kotlinlogging.KotlinLogging
import java.lang.foreign.Arena
import java.lang.foreign.SymbolLookup
import java.nio.file.Path

object SymbolLookupUtils {
    private val logger = KotlinLogging.logger {  }
    private val loadedLibs = hashMapOf<String, SymbolLookup>()

    internal val locations = mutableSetOf<Path>()

    fun lookup(arena: Arena, libName: String): SymbolLookup {
        logger.debug { "Searching for lib '$libName'" }

        val nativeLibName = System.mapLibraryName(libName)

        if (loadedLibs.contains(nativeLibName)) {
            logger.debug { "Lib already loaded" }
            return loadedLibs[nativeLibName]!!
        }

        val systemLookupResult = runCatching { SymbolLookup.libraryLookup(nativeLibName, arena) }
        when {
            systemLookupResult.isSuccess -> {
                logger.debug { "OS library search did find the library" }
                val symbolLookup = systemLookupResult.getOrThrow()
                loadedLibs[nativeLibName] = symbolLookup
                return symbolLookup
            }
            else -> { logger.debug { "OS library search did NOT find the library due to '${systemLookupResult.exceptionOrNull()}'" }}
        }

        for (path in locations) {
            val absPath = if (path.isAbsolute) path else path.resolve(nativeLibName).toAbsolutePath()

            val pathLookupResult = runCatching { SymbolLookup.libraryLookup(absPath, arena) }
            when {
                pathLookupResult.isSuccess -> {
                    logger.debug { "Found library at '${absPath}'" }
                    val symbolLookup = pathLookupResult.getOrThrow()
                    loadedLibs[nativeLibName] = symbolLookup
                    return symbolLookup
                }
                else -> { logger.debug { "Did not find library at '$absPath' due to '${pathLookupResult.exceptionOrNull()}'" } }
            }
        }

        throw IllegalArgumentException("Couldn't find library '$nativeLibName'")
    }
}