@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I8x16
import io.github.kotlinmania.rustlang.simd.U8x16
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun pshufb128(a: U8x16, b: U8x16): U8x16 =
    null as U8x16

public actual fun pmaddubsw128(a: U8x16, b: I8x16): I16x8 =
    null as I16x8

public actual fun pmulhrsw128(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun psignb128(a: I8x16, b: I8x16): I8x16 =
    null as I8x16

public actual fun psignw128(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun psignd128(a: I32x4, b: I32x4): I32x4 =
    null as I32x4
