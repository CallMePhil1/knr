package knr.annotations

/**
 * Targets functions declared inside a library interface.
 * The library processor converts function names from camel case to snake case.
 * However if that doesn't match the name in the library you can override the name with [Method]
 *
 * @param name Name of the function in the library
 *
 * ```
 * @Library("path/to/lib")
 * interface MyLibrary {
 *     @Method("overridden_name")
 *     fun myLibFunction(...): ...
 * }
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Method(val name: String = "")
