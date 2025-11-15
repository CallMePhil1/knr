package knr.annotations

/**
 * If a library function takes in a string then you must declare the charset that it uses.
 * The library processor will handle converting the string to a native object via the charset.
 * The processor also caches the string obj so it is not collected by the GC.
 * If you call this function again the cache is disposed of and then replaced with the new string.
 *
 * @param charset The charset to use to encode the string into native.
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
annotation class StringParam(val charset: String = "UTF-8")
