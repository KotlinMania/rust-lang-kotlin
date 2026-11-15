@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun llvmAddcarryU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664AdxLlvmAddcarryU64")

public actual fun llvmSubborrowU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX8664AdxLlvmSubborrowU64")
