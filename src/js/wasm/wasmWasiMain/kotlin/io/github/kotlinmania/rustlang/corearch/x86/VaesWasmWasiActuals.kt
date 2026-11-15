@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M256i
import io.github.kotlinmania.rustlang.x86.M512i

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun aesenc256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesenc256")

public actual fun aesenclast256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesenclast256")

public actual fun aesdec256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesdec256")

public actual fun aesdeclast256(a: M256i, roundKey: M256i): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesdeclast256")

public actual fun aesenc512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesenc512")

public actual fun aesenclast512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesenclast512")

public actual fun aesdec512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesdec512")

public actual fun aesdeclast512(a: M512i, roundKey: M512i): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VaesAesdeclast512")
