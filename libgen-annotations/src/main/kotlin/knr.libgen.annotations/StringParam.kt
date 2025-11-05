package knr.libgen.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class StringParam(val charset: String = "UTF-8")
