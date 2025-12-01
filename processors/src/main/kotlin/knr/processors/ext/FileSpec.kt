package knr.processors.ext

import com.squareup.kotlinpoet.FileSpec
import knr.runtime.typing.pointer.IntPointer

internal fun FileSpec.Builder.addClsImport(vararg cls: Class<*>) {
    cls.forEach {
        addImport(it.packageName, it.simpleName)
    }
}

internal fun FileSpec.Builder.addPointerFuncs() {
    addImport(
        IntPointer::class.java.packageName,
        "opaquePointerOf", "nativePointerOf",
        "bytePointerOf", "ubytePointerOf",
        "shortPointerOf", "ushortPointerOf",
        "intPointerOf", "uintPointerOf",
        "longPointerOf", "ulongPointerOf",
    )
}