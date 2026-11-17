@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8

public actual fun vpconflictd(a: I32x16): I32x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpconflictd256(a: I32x8): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpconflictd128(a: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpconflictq(a: I64x8): I64x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpconflictq256(a: I64x4): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpconflictq128(a: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")
