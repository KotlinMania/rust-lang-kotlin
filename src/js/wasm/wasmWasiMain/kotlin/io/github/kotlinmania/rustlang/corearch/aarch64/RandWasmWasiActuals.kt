@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun rndr(): Tuple =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64RandRndr")

public actual fun rndrrs(): Tuple =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchAarch64RandRndrrs")
