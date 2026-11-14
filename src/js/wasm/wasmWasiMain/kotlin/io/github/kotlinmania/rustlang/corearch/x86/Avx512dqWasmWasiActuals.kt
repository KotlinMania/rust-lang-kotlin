@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.F64x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun vcvtqq2pd128(a: I64x2, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtqq2pd128")

public actual fun vcvtqq2pd256(a: I64x4, rounding: Int): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtqq2pd256")

public actual fun vcvtqq2pd512(a: I64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtqq2pd512")

public actual fun vcvtqq2ps128(a: I64x2, src: F32x4, k: Mmask8): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtqq2ps128")

public actual fun vcvtqq2ps256(a: I64x4, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtqq2ps256")

public actual fun vcvtqq2ps512(a: I64x8, rounding: Int): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtqq2ps512")

public actual fun vcvtuqq2pd128(a: U64x2, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtuqq2pd128")

public actual fun vcvtuqq2pd256(a: U64x4, rounding: Int): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtuqq2pd256")

public actual fun vcvtuqq2pd512(a: U64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtuqq2pd512")

public actual fun vcvtuqq2ps128(a: U64x2, src: F32x4, k: Mmask8): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtuqq2ps128")

public actual fun vcvtuqq2ps256(a: U64x4, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtuqq2ps256")

public actual fun vcvtuqq2ps512(a: U64x8, rounding: Int): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtuqq2ps512")

public actual fun vcvtpd2qq128(a: F64x2, src: I64x2, k: Mmask8): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtpd2qq128")

public actual fun vcvtpd2qq256(a: F64x4, src: I64x4, k: Mmask8): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtpd2qq256")

public actual fun vcvtpd2qq512(a: F64x8, src: I64x8, k: Mmask8, rounding: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtpd2qq512")

public actual fun vcvtps2qq128(a: F32x4, src: I64x2, k: Mmask8): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtps2qq128")

public actual fun vcvtps2qq256(a: F32x4, src: I64x4, k: Mmask8): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtps2qq256")

public actual fun vcvtps2qq512(a: F32x8, src: I64x8, k: Mmask8, rounding: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtps2qq512")

public actual fun vcvtpd2uqq128(a: F64x2, src: U64x2, k: Mmask8): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtpd2uqq128")

public actual fun vcvtpd2uqq256(a: F64x4, src: U64x4, k: Mmask8): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtpd2uqq256")

public actual fun vcvtpd2uqq512(a: F64x8, src: U64x8, k: Mmask8, rounding: Int): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtpd2uqq512")

public actual fun vcvtps2uqq128(a: F32x4, src: U64x2, k: Mmask8): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtps2uqq128")

public actual fun vcvtps2uqq256(a: F32x4, src: U64x4, k: Mmask8): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtps2uqq256")

public actual fun vcvtps2uqq512(a: F32x8, src: U64x8, k: Mmask8, rounding: Int): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvtps2uqq512")

public actual fun vcvttpd2qq128(a: F64x2, src: I64x2, k: Mmask8): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttpd2qq128")

public actual fun vcvttpd2qq256(a: F64x4, src: I64x4, k: Mmask8): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttpd2qq256")

public actual fun vcvttpd2qq512(a: F64x8, src: I64x8, k: Mmask8, sae: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttpd2qq512")

public actual fun vcvttps2qq128(a: F32x4, src: I64x2, k: Mmask8): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttps2qq128")

public actual fun vcvttps2qq256(a: F32x4, src: I64x4, k: Mmask8): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttps2qq256")

public actual fun vcvttps2qq512(a: F32x8, src: I64x8, k: Mmask8, sae: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttps2qq512")

public actual fun vcvttpd2uqq128(a: F64x2, src: U64x2, k: Mmask8): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttpd2uqq128")

public actual fun vcvttpd2uqq256(a: F64x4, src: U64x4, k: Mmask8): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttpd2uqq256")

public actual fun vcvttpd2uqq512(a: F64x8, src: U64x8, k: Mmask8, sae: Int): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttpd2uqq512")

public actual fun vcvttps2uqq128(a: F32x4, src: U64x2, k: Mmask8): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttps2uqq128")

public actual fun vcvttps2uqq256(a: F32x4, src: U64x4, k: Mmask8): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttps2uqq256")

public actual fun vcvttps2uqq512(a: F32x8, src: U64x8, k: Mmask8, sae: Int): U64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVcvttps2uqq512")

public actual fun vrangepd128(a: F64x2, b: F64x2, imm8: Int, src: F64x2, k: Mmask8): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangepd128")

public actual fun vrangepd256(a: F64x4, b: F64x4, imm8: Int, src: F64x4, k: Mmask8): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangepd256")

public actual fun vrangepd512(a: F64x8, b: F64x8, imm8: Int, src: F64x8, k: Mmask8, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangepd512")

public actual fun vrangeps128(a: F32x4, b: F32x4, imm8: Int, src: F32x4, k: Mmask8): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangeps128")

public actual fun vrangeps256(a: F32x8, b: F32x8, imm8: Int, src: F32x8, k: Mmask8): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangeps256")

public actual fun vrangeps512(a: F32x16, b: F32x16, imm8: Int, src: F32x16, k: Mmask16, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangeps512")

public actual fun vrangesd(a: F64x2, b: F64x2, src: F64x2, k: Mmask8, imm8: Int, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangesd")

public actual fun vrangess(a: F32x4, b: F32x4, src: F32x4, k: Mmask8, imm8: Int, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVrangess")

public actual fun vreducepd128(a: F64x2, imm8: Int, src: F64x2, k: Mmask8): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreducepd128")

public actual fun vreducepd256(a: F64x4, imm8: Int, src: F64x4, k: Mmask8): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreducepd256")

public actual fun vreducepd512(a: F64x8, imm8: Int, src: F64x8, k: Mmask8, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreducepd512")

public actual fun vreduceps128(a: F32x4, imm8: Int, src: F32x4, k: Mmask8): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreduceps128")

public actual fun vreduceps256(a: F32x8, imm8: Int, src: F32x8, k: Mmask8): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreduceps256")

public actual fun vreduceps512(a: F32x16, imm8: Int, src: F32x16, k: Mmask16, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreduceps512")

public actual fun vreducesd(a: F64x2, b: F64x2, src: F64x2, k: Mmask8, imm8: Int, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreducesd")

public actual fun vreducess(a: F32x4, b: F32x4, src: F32x4, k: Mmask8, imm8: Int, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVreducess")

public actual fun vfpclasspd128(a: F64x2, imm8: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclasspd128")

public actual fun vfpclasspd256(a: F64x4, imm8: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclasspd256")

public actual fun vfpclasspd512(a: F64x8, imm8: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclasspd512")

public actual fun vfpclassps128(a: F32x4, imm8: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclassps128")

public actual fun vfpclassps256(a: F32x8, imm8: Int): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclassps256")

public actual fun vfpclassps512(a: F32x16, imm8: Int): Mmask16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclassps512")

public actual fun vfpclasssd(a: F64x2, imm8: Int, k: Mmask8): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclasssd")

public actual fun vfpclassss(a: F32x4, imm8: Int, k: Mmask8): Mmask8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512dqVfpclassss")
