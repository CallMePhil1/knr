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
 * @param libName The name of the library without its prefix i.e mylib for mylib.dll
 * ```
 * @Library("mylib")
 * interface MyLibrary {
 *     fun myLibFunction(...): ...
 * }
 * ```
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Library(val libName: String, val naming: NamingConvention)
