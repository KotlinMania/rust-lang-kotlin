@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8

public actual fun cvtne2ps2bf16(a: F32x4, b: F32x4): I16x8 =
    null as I16x8

public actual fun cvtne2ps2bf16256(a: F32x8, b: F32x8): I16x16 =
    null as I16x16

public actual fun cvtne2ps2bf16512(a: F32x16, b: F32x16): I16x32 =
    null as I16x32

public actual fun cvtneps2bf16128(a: F32x4, src: I16x8, k: Mmask8): I16x8 =
    null as I16x8

public actual fun cvtneps2bf16256(a: F32x8): I16x8 =
    null as I16x8

public actual fun cvtneps2bf16512(a: F32x16): I16x16 =
    null as I16x16

public actual fun dpbf16ps(a: F32x4, b: I16x8, c: I16x8): F32x4 =
    null as F32x4

public actual fun dpbf16ps256(a: F32x8, b: I16x16, c: I16x16): F32x8 =
    null as F32x8

public actual fun dpbf16ps512(a: F32x16, b: I16x32, c: I16x32): F32x16 =
    null as F32x16
