@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun x86Rdrand64Step(): Pair<ULong, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664RdrandX86Rdrand64Step")

public actual fun x86Rdseed64Step(): Pair<ULong, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664RdrandX86Rdseed64Step")
