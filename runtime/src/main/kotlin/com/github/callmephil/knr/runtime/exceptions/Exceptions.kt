package com.github.callmephil.knr.runtime.exceptions

class StructSizeMismatchException(
    expectedSize: Long,
    actualSize: Long
) : Exception("Tried to write a struct of size $actualSize but was expecting a size of $expectedSize")