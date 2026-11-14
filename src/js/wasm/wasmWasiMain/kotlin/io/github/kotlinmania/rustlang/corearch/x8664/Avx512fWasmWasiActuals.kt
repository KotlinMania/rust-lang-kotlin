@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vcvtss2si64(a: F32x4, rounding: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtss2si64")

public actual fun vcvtss2usi64(a: F32x4, rounding: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtss2usi64")

public actual fun vcvtsd2si64(a: F64x2, rounding: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtsd2si64")

public actual fun vcvtsd2usi64(a: F64x2, rounding: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtsd2usi64")

public actual fun vcvtsi2ss64(a: F32x4, b: Long, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtsi2ss64")

public actual fun vcvtsi2sd64(a: F64x2, b: Long, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtsi2sd64")

public actual fun vcvtusi2ss64(a: F32x4, b: ULong, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtusi2ss64")

public actual fun vcvtusi2sd64(a: F64x2, b: ULong, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvtusi2sd64")

public actual fun vcvttss2si64(a: F32x4, rounding: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvttss2si64")

public actual fun vcvttss2usi64(a: F32x4, rounding: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvttss2usi64")

public actual fun vcvttsd2si64(a: F64x2, rounding: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvttsd2si64")

public actual fun vcvttsd2usi64(a: F64x2, rounding: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fVcvttsd2usi64")
