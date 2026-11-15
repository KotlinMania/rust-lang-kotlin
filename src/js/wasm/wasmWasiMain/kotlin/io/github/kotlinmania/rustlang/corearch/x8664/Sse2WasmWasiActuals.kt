@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128d

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun cvtsd2si64(a: M128d): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Sse2Cvtsd2si64")

public actual fun cvttsd2si64(a: M128d): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664Sse2Cvttsd2si64")
