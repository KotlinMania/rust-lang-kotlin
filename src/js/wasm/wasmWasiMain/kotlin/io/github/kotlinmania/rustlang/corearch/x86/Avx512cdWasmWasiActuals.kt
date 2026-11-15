@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I32x8
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I64x4
import io.github.kotlinmania.rustlang.simd.I64x8

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vpconflictd(a: I32x16): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512cdVpconflictd")

public actual fun vpconflictd256(a: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512cdVpconflictd256")

public actual fun vpconflictd128(a: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512cdVpconflictd128")

public actual fun vpconflictq(a: I64x8): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512cdVpconflictq")

public actual fun vpconflictq256(a: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512cdVpconflictq256")

public actual fun vpconflictq128(a: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86Avx512cdVpconflictq128")
