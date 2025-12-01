package knr.annotations

enum class NamingConvention {
    CAMELCASE,
    PASCALCASE,
    SNAKECASE
}
/**
 * This annotation targets interfaces that describe a C libraries API.
 * The library processor looks for interfaces with this annotation and generates objects with implementations of the functions declared
 * Libraries that use this annotation should then use [knr.runtime.library.LibraryLoader] to load the implemented objects
 *
 * @param libPath The path to the library. i.e dll/so/lib
 * ```
 * @Library("path/to/lib")
 * interface MyLibrary {
 *     fun myLibFunction(...): ...
 * }
 * ```
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Library(val libPath: String, val naming: NamingConvention)
