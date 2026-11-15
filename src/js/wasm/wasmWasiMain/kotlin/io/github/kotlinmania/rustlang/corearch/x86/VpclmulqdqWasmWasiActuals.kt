@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M256i
import io.github.kotlinmania.rustlang.x86.M512i

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun pclmulqdq256(a: M256i, roundKey: M256i, imm8: UByte): M256i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VpclmulqdqPclmulqdq256")

public actual fun pclmulqdq512(a: M512i, roundKey: M512i, imm8: UByte): M512i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86VpclmulqdqPclmulqdq512")
