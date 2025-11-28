package knr.annotations.string

/**
 * Decides how to dispose of the native string object
 *
 * [DisposeMethod.AFTER_USE] Converts the string to a cstring, passes in the cstring to the native function then disposes of the cstring
 *
 * [DisposeMethod.CACHE] A variable is added to the library to contain the cstring. When calling the native function it will dispose of the previous value
 *
 * [DisposeMethod.NONE] Will convert the string to cstring and calls the native function. The native function should handle disposing of the string.
 */
enum class DisposeMethod {
    AFTER_USE,
    CACHE,
    NONE
}

/**
 * If a library function takes in a string then you must declare the charset that it should use.
 * The library processor will handle converting the string to a native object via the charset.
 *
 *
 * @param charset The charset to use to encode the string into native.
 * @param disposeMethod Notifies the processor how to handle disposing of the native string
 *
 * ```
 * @Library("path/to/lib")
 * interface MyLibrary {
 *     fun myFunction(@StringParam title: String)
 *
 *     fun myFunction2(@StringParam("US_ASCII") title: String)
 * }
 * ```
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class StringParam(val charset: String = "UTF-8", val disposeMethod: DisposeMethod = DisposeMethod.AFTER_USE)
