package knr.libgen.processor.util

import java.nio.charset.StandardCharsets

internal val standardCharsets = hashMapOf(
    StandardCharsets.ISO_8859_1.name() to "ISO_8859_1",
    StandardCharsets.US_ASCII.name() to "US_ASCII",
    StandardCharsets.UTF_8.name() to "UTF_8",
    StandardCharsets.UTF_16.name() to "UTF_16",
    StandardCharsets.UTF_16BE.name() to "UTF_16BE",
    StandardCharsets.UTF_16LE.name() to "UTF_16LE",
    StandardCharsets.UTF_32.name() to "UTF_32",
    StandardCharsets.UTF_32BE.name() to "UTF_32BE",
    StandardCharsets.UTF_32LE.name() to "UTF_32LE"
)