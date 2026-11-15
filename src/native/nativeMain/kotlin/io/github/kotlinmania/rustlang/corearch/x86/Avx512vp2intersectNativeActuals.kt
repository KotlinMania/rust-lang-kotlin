@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I32x8
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I64x4
import io.github.kotlinmania.rustlang.simd.I64x8
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vp2intersectd128(a: I32x4, b: I32x4): Pair<UByte, UByte> =
    Pair<UByte, UByte>(0u, 0u)

public actual fun vp2intersectq128(a: I64x2, b: I64x2): Pair<UByte, UByte> =
    Pair<UByte, UByte>(0u, 0u)

public actual fun vp2intersectd256(a: I32x8, b: I32x8): Pair<UByte, UByte> =
    Pair<UByte, UByte>(0u, 0u)

public actual fun vp2intersectq256(a: I64x4, b: I64x4): Pair<UByte, UByte> =
    Pair<UByte, UByte>(0u, 0u)

public actual fun vp2intersectd512(a: I32x16, b: I32x16): Pair<UShort, UShort> =
    Pair<UShort, UShort>(0u, 0u)

public actual fun vp2intersectq512(a: I64x8, b: I64x8): Pair<UByte, UByte> =
    Pair<UByte, UByte>(0u, 0u)
