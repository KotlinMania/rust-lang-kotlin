@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x8

public actual fun llvmVcvtps2ph128(a: F32x4, rounding: Int): I16x8 =
    null as I16x8

public actual fun llvmVcvtps2ph256(a: F32x8, rounding: Int): I16x8 =
    null as I16x8
