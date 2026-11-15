@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M128i

public actual fun pclmulqdq(a: M128i, roundKey: M128i, imm8: UByte): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")
