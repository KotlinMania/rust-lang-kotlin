@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.F32x4
import io.github.kotlinmania.rustlang.simd.F32x8
import io.github.kotlinmania.rustlang.simd.I16x8

public actual fun llvmVcvtps2ph128(a: F32x4, rounding: Int): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun llvmVcvtps2ph256(a: F32x8, rounding: Int): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")
