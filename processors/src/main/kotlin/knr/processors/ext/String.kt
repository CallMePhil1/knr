package knr.processors.ext

internal fun String.camelToPascalcase(): String {
    return this[0].uppercase() + this.substring(1)
}

internal fun String.camelToSnakecase(): String {
    val underlineIndices = mutableListOf<Int>()

    for(i in 0 ..< this.length) {
        if(this[i].isUpperCase()) {
            underlineIndices.add(i + underlineIndices.size)
        }
    }

    val mutableString = CharArray(this.length + underlineIndices.size)

    underlineIndices.forEach { mutableString[it] = '_' }

    var cursor = 0
    for(i in 0 ..< mutableString.size) {
        if (mutableString[i] == '_')
            continue

        mutableString[i] = this[cursor].lowercaseChar()
        cursor += 1
    }

    return mutableString.concatToString()
}
