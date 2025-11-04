package knr.libgen.processor.ext

import com.squareup.kotlinpoet.FileSpec

fun FileSpec.Builder.addClsImport(cls: Class<*>) {
    addImport(cls.packageName, cls.simpleName)
}
