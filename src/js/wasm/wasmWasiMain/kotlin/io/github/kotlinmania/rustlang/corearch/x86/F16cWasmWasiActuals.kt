@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x8

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun llvmVcvtps2ph128(a: F32x4, rounding: Int): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86F16cLlvmVcvtps2ph128")

public actual fun llvmVcvtps2ph256(a: F32x8, rounding: Int): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86F16cLlvmVcvtps2ph256")
