@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun cvtss2si64(a: M128): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664SseCvtss2si64")

public actual fun cvttss2si64(a: M128): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX8664SseCvttss2si64")
