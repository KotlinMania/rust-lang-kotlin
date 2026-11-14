@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8

public actual fun vp2intersectd128(a: I32x4, b: I32x4): Pair<UByte, UByte> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vp2intersectq128(a: I64x2, b: I64x2): Pair<UByte, UByte> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vp2intersectd256(a: I32x8, b: I32x8): Pair<UByte, UByte> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vp2intersectq256(a: I64x4, b: I64x4): Pair<UByte, UByte> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vp2intersectd512(a: I32x16, b: I32x16): Pair<UShort, UShort> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vp2intersectq512(a: I64x8, b: I64x8): Pair<UByte, UByte> =
    throw UnsupportedOperationException("Native FFI addon is not available")
