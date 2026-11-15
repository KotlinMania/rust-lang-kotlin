package io.github.kotlinmania.rustlang.core.unicode

import io.github.kotlinmania.rustlang.unicode.UNICODE_VERSION

/**
 * The version of [Unicode](https://www.unicode.org/) that the Unicode parts of
 * `char` and `String` methods are based on.
 *
 * New versions of Unicode are released regularly and subsequently all methods
 * in the standard library depending on Unicode are updated. Therefore the
 * behavior of some `char` and `String` methods and the value of this constant
 * changes over time, within the boundaries of Unicode's [stability policies].
 * This is *not* considered to be a breaking change.
 *
 * [stability policies]: https://www.unicode.org/policies/stabilityPolicy.html
 *
 * The version numbering scheme is explained in
 * [Section 3.1 (Version Numbering)] of the Unicode Standard.
 *
 * [Section 3.1 (Version Numbering)]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G49512
 */
public val UNICODE_VERSION: Triple<UByte, UByte, UByte> = UNICODE_VERSION
