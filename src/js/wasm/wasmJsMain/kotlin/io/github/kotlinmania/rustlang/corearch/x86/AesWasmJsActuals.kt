@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

public actual fun aesdec(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesdeclast(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesenc(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesenclast(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aesimc(a: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aeskeygenassist(a: M128i, imm8: UByte): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")
