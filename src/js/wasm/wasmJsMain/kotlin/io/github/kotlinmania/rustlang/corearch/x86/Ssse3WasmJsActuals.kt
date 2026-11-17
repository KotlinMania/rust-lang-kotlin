@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

public actual fun pshufb128(a: U8x16, b: U8x16): U8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmaddubsw128(a: U8x16, b: I8x16): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmulhrsw128(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psignb128(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psignw128(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psignd128(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")
