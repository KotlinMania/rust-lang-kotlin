@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun x86Rdrand16Step(): Pair<UShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86RdrandX86Rdrand16Step")

public actual fun x86Rdrand32Step(): Pair<UInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86RdrandX86Rdrand32Step")

public actual fun x86Rdseed16Step(): Pair<UShort, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86RdrandX86Rdseed16Step")

public actual fun x86Rdseed32Step(): Pair<UInt, Int> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86RdrandX86Rdseed32Step")
