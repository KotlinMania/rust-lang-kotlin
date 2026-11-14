@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun aesdec(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AesAesdec")

public actual fun aesdeclast(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AesAesdeclast")

public actual fun aesenc(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AesAesenc")

public actual fun aesenclast(a: M128i, roundKey: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AesAesenclast")

public actual fun aesimc(a: M128i): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AesAesimc")

public actual fun aeskeygenassist(a: M128i, imm8: UByte): M128i =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AesAeskeygenassist")
