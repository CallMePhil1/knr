package knr.annotations

import kotlin.reflect.KClass

/**
 * If a function returns a type that inherits [knr.runtime.typing.Native] then you must declare how to dispose of the object.
 * If targeting a function then it only declares the dispose function for that function.
 * If targeting a class then it declares the dispose function for all functions.
 *
 * @param cls The parent class that contains the dispose function
 * @param disposeFunName The name of the function inside [cls] that will handle disposing.
 *
 * ```
 * @Library("path/to/lib")
 * @Disposer(MyLibrary::class, "disposeFunc")
 * interface MyLibrary {
 *     fun disposeFunc(struct: StructA)
 *
 *     @Disposer(MyLibrary::class, "disposeFunc")
 *     fun myFunction(): StructA
 *
 *     @Disposer(AnotherClass::class, "anotherDisposeFunc")
 *     fun myFunction2(): StructA
 *
 *     // Uses MyLibrary.disposeFunc for disposing of StructA due to annotation at class level
 *     fun myFunction3(): StructA
 * }
 * ```
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Disposer(
    val cls: KClass<*>,
    val disposeFunName: String
)

/**
 * Notifies the library processor that no dispose function should be used with this function
 */
@Target(AnnotationTarget.FUNCTION)
@Retention
annotation class NoDisposer
