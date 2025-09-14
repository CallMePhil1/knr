package com.github.callmephil.knr.runtime.exceptions

class ARCIsDisposedException() : Exception("Tried to use a ARC that has been disposed of.")

class PointerIsNotValidException : Exception("Tried to use a pointer that does not have a valid ARC.")

class StructSizeMismatchException(
    expectedSize: Long,
    actualSize: Long
) : Exception("Tried to write a struct of size $actualSize but was expecting a size of $expectedSize")