@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import kotlinx.cinterop.ExperimentalForeignApi

public actual fun aesdec(a: M128i, roundKey: M128i): M128i =
    M128i()

public actual fun aesdeclast(a: M128i, roundKey: M128i): M128i =
    M128i()

public actual fun aesenc(a: M128i, roundKey: M128i): M128i =
    M128i()

public actual fun aesenclast(a: M128i, roundKey: M128i): M128i =
    M128i()

public actual fun aesimc(a: M128i): M128i =
    M128i()

public actual fun aeskeygenassist(a: M128i, imm8: UByte): M128i =
    M128i()
