@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.simd.F32x4
import io.github.kotlinmania.rustlang.simd.F64x2

public actual fun vcvtss2si64(a: F32x4, rounding: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtss2usi64(a: F32x4, rounding: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtsd2si64(a: F64x2, rounding: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtsd2usi64(a: F64x2, rounding: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtsi2ss64(a: F32x4, b: Long, rounding: Int): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtsi2sd64(a: F64x2, b: Long, rounding: Int): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtusi2ss64(a: F32x4, b: ULong, rounding: Int): F32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvtusi2sd64(a: F64x2, b: ULong, rounding: Int): F64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttss2si64(a: F32x4, rounding: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttss2usi64(a: F32x4, rounding: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttsd2si64(a: F64x2, rounding: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcvttsd2usi64(a: F64x2, rounding: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")
