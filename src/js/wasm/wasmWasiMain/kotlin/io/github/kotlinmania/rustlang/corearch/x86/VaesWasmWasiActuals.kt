@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun aesenc256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesenc256")

public actual fun aesenclast256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesenclast256")

public actual fun aesdec256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesdec256")

public actual fun aesdeclast256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesdeclast256")

public actual fun aesenc512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesenc512")

public actual fun aesenclast512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesenclast512")

public actual fun aesdec512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesdec512")

public actual fun aesdeclast512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86VaesAesdeclast512")
