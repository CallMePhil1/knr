package knr.annotations.string

/**
 * If a library function returns a string then you must declare what charset to use to decode the string.
 * The library processor will handle converting the C string to a JVM String.
 *
 * @param charset The charset to use to decode the C string into a JVM [String].
 *
 * ```
 * @Library("path/to/lib")
 * interface MyLibrary {
 *     @ReturnsString("UTF-8")
 *     fun getString(): String
 * }
 * ```
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ReturnsString(val charset: String = "UTF-8")
