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
 * @ReturnsNative(MyLibrary::class, "disposeFunc")
 * interface MyLibrary {
 *     fun disposeFunc(struct: StructA)
 *
 *     @ReturnsNative(MyLibrary::class, "disposeFunc")
 *     fun myFunction(): StructA
 *
 *     @ReturnsNative(AnotherClass::class, "anotherDisposeFunc")
 *     fun myFunction2(): StructA
 *
 *     // Uses MyLibrary.disposeFunc for disposing of StructA due to annotation at class level
 *     fun myFunction3(): StructA
 * }
 * ```
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ReturnsNative(
    val cls: KClass<*>,
    val disposeFunName: String
)

/**
 * Tells the library processor to use stdlib function 'free' to dispose of the native object.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class IgnoreReturnsNative
