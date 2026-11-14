@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

public actual fun pclmulqdq256(a: M256i, roundKey: M256i, imm8: UByte): M256i =
    M256i()

public actual fun pclmulqdq512(a: M512i, roundKey: M512i, imm8: UByte): M512i =
    M512i()
