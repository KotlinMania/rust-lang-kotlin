@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun insertps(a: M128, b: M128, imm8: UByte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Insertps")

public actual fun packusdw(a: I32x4, b: I32x4): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Packusdw")

public actual fun dppd(a: M128d, b: M128d, imm8: UByte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Dppd")

public actual fun dpps(a: M128, b: M128, imm8: UByte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Dpps")

public actual fun roundpd(a: M128d, rounding: Int): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Roundpd")

public actual fun roundps(a: M128, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Roundps")

public actual fun roundsd(a: M128d, b: M128d, rounding: Int): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Roundsd")

public actual fun roundss(a: M128, b: M128, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Roundss")

public actual fun phminposuw(a: U16x8): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Phminposuw")

public actual fun mpsadbw(a: U8x16, b: U8x16, imm8: UByte): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Mpsadbw")

public actual fun ptestnzc(a: I64x2, mask: I64x2): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse41Ptestnzc")
