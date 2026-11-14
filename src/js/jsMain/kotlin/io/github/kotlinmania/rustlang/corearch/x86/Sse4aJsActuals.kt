@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16

public actual fun extrq(x: I64x2, y: I8x16): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun extrqi(x: I64x2, len: UByte, idx: UByte): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun insertq(x: I64x2, y: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun insertqi(x: I64x2, y: I64x2, len: UByte, idx: UByte): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")
