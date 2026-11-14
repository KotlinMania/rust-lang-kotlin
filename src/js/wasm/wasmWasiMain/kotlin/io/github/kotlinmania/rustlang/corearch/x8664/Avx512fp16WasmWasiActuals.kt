@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.M128h

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vcvtsi642sh(a: M128h, b: Long, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fp16Vcvtsi642sh")

public actual fun vcvtusi642sh(a: M128h, b: ULong, rounding: Int): M128h =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fp16Vcvtusi642sh")

public actual fun vcvtsh2si64(a: M128h, rounding: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fp16Vcvtsh2si64")

public actual fun vcvtsh2usi64(a: M128h, rounding: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fp16Vcvtsh2usi64")

public actual fun vcvttsh2si64(a: M128h, sae: Int): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fp16Vcvttsh2si64")

public actual fun vcvttsh2usi64(a: M128h, sae: Int): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Avx512fp16Vcvttsh2usi64")
