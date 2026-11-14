@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun pclmulqdq256(a: M256i, roundKey: M256i, imm8: UByte): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VpclmulqdqPclmulqdq256")

public actual fun pclmulqdq512(a: M512i, roundKey: M512i, imm8: UByte): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VpclmulqdqPclmulqdq512")
