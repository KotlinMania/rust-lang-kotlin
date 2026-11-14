@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun vgf2p8affineinvqb512(x: I8x64, a: I8x64, imm8: UByte): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8affineinvqb512")

public actual fun vgf2p8affineinvqb256(x: I8x32, a: I8x32, imm8: UByte): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8affineinvqb256")

public actual fun vgf2p8affineinvqb128(x: I8x16, a: I8x16, imm8: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8affineinvqb128")

public actual fun vgf2p8affineqb512(x: I8x64, a: I8x64, imm8: UByte): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8affineqb512")

public actual fun vgf2p8affineqb256(x: I8x32, a: I8x32, imm8: UByte): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8affineqb256")

public actual fun vgf2p8affineqb128(x: I8x16, a: I8x16, imm8: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8affineqb128")

public actual fun vgf2p8mulb512(a: I8x64, b: I8x64): I8x64 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8mulb512")

public actual fun vgf2p8mulb256(a: I8x32, b: I8x32): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8mulb256")

public actual fun vgf2p8mulb128(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86GfniVgf2p8mulb128")
