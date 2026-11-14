@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vcvtss2si64(a: F32x4, rounding: Int): Long =
    0L

public actual fun vcvtss2usi64(a: F32x4, rounding: Int): ULong =
    0uL

public actual fun vcvtsd2si64(a: F64x2, rounding: Int): Long =
    0L

public actual fun vcvtsd2usi64(a: F64x2, rounding: Int): ULong =
    0uL

public actual fun vcvtsi2ss64(a: F32x4, b: Long, rounding: Int): F32x4 =
    null as F32x4

public actual fun vcvtsi2sd64(a: F64x2, b: Long, rounding: Int): F64x2 =
    null as F64x2

public actual fun vcvtusi2ss64(a: F32x4, b: ULong, rounding: Int): F32x4 =
    null as F32x4

public actual fun vcvtusi2sd64(a: F64x2, b: ULong, rounding: Int): F64x2 =
    null as F64x2

public actual fun vcvttss2si64(a: F32x4, rounding: Int): Long =
    0L

public actual fun vcvttss2usi64(a: F32x4, rounding: Int): ULong =
    0uL

public actual fun vcvttsd2si64(a: F64x2, rounding: Int): Long =
    0L

public actual fun vcvttsd2usi64(a: F64x2, rounding: Int): ULong =
    0uL
