@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vpermi2b(a: I8x64, idx: I8x64, b: I8x64): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpermi2b")

public actual fun vpermi2b256(a: I8x32, idx: I8x32, b: I8x32): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpermi2b256")

public actual fun vpermi2b128(a: I8x16, idx: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpermi2b128")

public actual fun vpermb(a: I8x64, idx: I8x64): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpermb")

public actual fun vpermb256(a: I8x32, idx: I8x32): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpermb256")

public actual fun vpermb128(a: I8x16, idx: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpermb128")

public actual fun vpmultishiftqb(a: I8x64, b: I8x64): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpmultishiftqb")

public actual fun vpmultishiftqb256(a: I8x32, b: I8x32): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpmultishiftqb256")

public actual fun vpmultishiftqb128(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512vbmiVpmultishiftqb128")
