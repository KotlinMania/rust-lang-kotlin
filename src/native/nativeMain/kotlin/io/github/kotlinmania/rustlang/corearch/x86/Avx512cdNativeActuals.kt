@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I32x8
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I64x4
import io.github.kotlinmania.rustlang.simd.I64x8
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vpconflictd(a: I32x16): I32x16 =
    null as I32x16

public actual fun vpconflictd256(a: I32x8): I32x8 =
    null as I32x8

public actual fun vpconflictd128(a: I32x4): I32x4 =
    null as I32x4

public actual fun vpconflictq(a: I64x8): I64x8 =
    null as I64x8

public actual fun vpconflictq256(a: I64x4): I64x4 =
    null as I64x4

public actual fun vpconflictq128(a: I64x2): I64x2 =
    null as I64x2
