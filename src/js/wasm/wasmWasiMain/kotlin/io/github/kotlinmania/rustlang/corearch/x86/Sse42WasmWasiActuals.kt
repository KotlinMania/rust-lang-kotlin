@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun pcmpestrm128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestrm128")

public actual fun pcmpestri128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestri128")

public actual fun pcmpestriz128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestriz128")

public actual fun pcmpestric128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestric128")

public actual fun pcmpestris128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestris128")

public actual fun pcmpestrio128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestrio128")

public actual fun pcmpestria128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpestria128")

public actual fun pcmpistrm128(a: I8x16, b: I8x16, imm8: Byte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistrm128")

public actual fun pcmpistri128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistri128")

public actual fun pcmpistriz128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistriz128")

public actual fun pcmpistric128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistric128")

public actual fun pcmpistris128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistris128")

public actual fun pcmpistrio128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistrio128")

public actual fun pcmpistria128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse42Pcmpistria128")

@kotlin.wasm.WasmImport("corearch", "corearchX86Sse42Crc32328")
private external fun wasiCorearchX86Sse42Crc32328(crc: Int, v: Int): Int

public actual fun crc32328(crc: UInt, v: UByte): UInt =
    wasiCorearchX86Sse42Crc32328(crc.toInt(), v.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchX86Sse42Crc323216")
private external fun wasiCorearchX86Sse42Crc323216(crc: Int, v: Int): Int

public actual fun crc323216(crc: UInt, v: UShort): UInt =
    wasiCorearchX86Sse42Crc323216(crc.toInt(), v.toInt()).toUInt()

@kotlin.wasm.WasmImport("corearch", "corearchX86Sse42Crc323232")
private external fun wasiCorearchX86Sse42Crc323232(crc: Int, v: Int): Int

public actual fun crc323232(crc: UInt, v: UInt): UInt =
    wasiCorearchX86Sse42Crc323232(crc.toInt(), v.toInt()).toUInt()
