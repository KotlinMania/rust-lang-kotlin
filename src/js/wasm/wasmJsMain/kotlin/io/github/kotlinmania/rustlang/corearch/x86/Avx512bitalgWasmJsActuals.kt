@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

public actual fun bitshuffle512(data: I8x64, indices: I8x64): Mmask64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bitshuffle256(data: I8x32, indices: I8x32): Mmask32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bitshuffle128(data: I8x16, indices: I8x16): Mmask16 =
    throw UnsupportedOperationException("Native FFI addon is not available")
