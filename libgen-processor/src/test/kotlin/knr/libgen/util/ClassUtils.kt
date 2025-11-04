package knr.libgen.util

import java.io.File
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.pathString

fun getFile(cls: Class<*>): File {
    val basePath = "src\\test\\kotlin"
    val classPath = cls.name.replace('.', '\\') + ".kt"
    val path = Path(basePath, classPath)
    val file = path.toFile()

    if (!file.exists())
        throw FileNotFoundException(path.pathString)
    if (!file.isFile)
        throw FileNotFoundException("${path.pathString} is not a file")

    return file
}