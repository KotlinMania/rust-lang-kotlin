@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M128i
import io.github.kotlinmania.rustlang.x86.M256i
import io.github.kotlinmania.rustlang.x86.M512i

public actual fun vpmadd52luq128(z: M128i, x: M128i, y: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpmadd52huq128(z: M128i, x: M128i, y: M128i): M128i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpmadd52luq256(z: M256i, x: M256i, y: M256i): M256i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpmadd52huq256(z: M256i, x: M256i, y: M256i): M256i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpmadd52luq512(z: M512i, x: M512i, y: M512i): M512i =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpmadd52huq512(z: M512i, x: M512i, y: M512i): M512i =
    throw UnsupportedOperationException("Native FFI addon is not available")
