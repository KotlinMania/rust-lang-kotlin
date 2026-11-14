@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.M128h

public actual fun vcvtsi642sh(a: M128h, b: Long, rounding: Int): M128h =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtusi642sh(a: M128h, b: ULong, rounding: Int): M128h =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtsh2si64(a: M128h, rounding: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtsh2usi64(a: M128h, rounding: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttsh2si64(a: M128h, sae: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttsh2usi64(a: M128h, sae: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")
