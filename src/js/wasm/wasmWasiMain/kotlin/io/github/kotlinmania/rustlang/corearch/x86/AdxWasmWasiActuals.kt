@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun llvmAddcarryU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AdxLlvmAddcarryU32")

public actual fun llvmSubborrowU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AdxLlvmSubborrowU32")
