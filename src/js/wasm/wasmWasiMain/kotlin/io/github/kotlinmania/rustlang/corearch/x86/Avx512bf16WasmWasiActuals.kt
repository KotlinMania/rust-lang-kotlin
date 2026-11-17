@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun cvtne2ps2bf16(a: F32x4, b: F32x4): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Cvtne2ps2bf16")

public actual fun cvtne2ps2bf16256(a: F32x8, b: F32x8): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Cvtne2ps2bf16256")

public actual fun cvtne2ps2bf16512(a: F32x16, b: F32x16): I16x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Cvtne2ps2bf16512")

public actual fun cvtneps2bf16128(a: F32x4, src: I16x8, k: Mmask8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Cvtneps2bf16128")

public actual fun cvtneps2bf16256(a: F32x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Cvtneps2bf16256")

public actual fun cvtneps2bf16512(a: F32x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Cvtneps2bf16512")

public actual fun dpbf16ps(a: F32x4, b: I16x8, c: I16x8): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Dpbf16ps")

public actual fun dpbf16ps256(a: F32x8, b: I16x16, c: I16x16): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Dpbf16ps256")

public actual fun dpbf16ps512(a: F32x16, b: I16x32, c: I16x32): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512bf16Dpbf16ps512")
