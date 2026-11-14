@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

public actual fun pclmulqdq(a: M128i, roundKey: M128i, imm8: UByte): M128i =
    M128i()
