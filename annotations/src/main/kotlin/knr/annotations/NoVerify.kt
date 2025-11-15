package knr.annotations

/**
 * If a library function takes in any parameters that inherit [knr.runtime.typing.Native] then it invokes [verifyIsValid].
 * If verification is not wanted then label the function with [NoVerify] and it is skipped.
 * If targeting a function then all [knr.runtime.typing.Native] parameters are ignored. If targeting a parameter then only that one is ignored.
 *
 * ```
 * @Library("path/to/lib")
 * interface MyLibrary {
 *     @NoVerify
 *     fun myFunction(param1: StructA, param2: StructB) {
 *         // No verification at all
 *     }
 *
 *     fun myFunction2(param1: StructA, @NoVerify param2: StructB) {
 *         // Only param1 invokes verify, param2 is skipped
 *     }
 * }
 * ```
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class NoVerify
