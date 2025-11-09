package knr.processors.ext

import com.squareup.kotlinpoet.FileSpec

internal fun FileSpec.Builder.addClsImport(cls: Class<*>) {
    addImport(cls.packageName, cls.simpleName)
}
