@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M128i

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun aesdec(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AesAesdec")

public actual fun aesdeclast(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AesAesdeclast")

public actual fun aesenc(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AesAesenc")

public actual fun aesenclast(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AesAesenclast")

public actual fun aesimc(a: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AesAesimc")

public actual fun aeskeygenassist(a: M128i, imm8: UByte): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AesAeskeygenassist")
