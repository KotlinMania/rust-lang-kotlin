@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.F64x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun vsqrtps(a: F32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsqrtps")

public actual fun vsqrtpd(a: F64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsqrtpd")

public actual fun vfmadd132psround(a: M512, b: M512, c: M512, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfmadd132psround")

public actual fun vfmadd132pdround(a: M512d, b: M512d, c: M512d, rounding: Int): M512d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfmadd132pdround")

public actual fun vfmaddsubpsround(a: M512, b: M512, c: M512, rounding: Int): M512 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfmaddsubpsround")

public actual fun vfmaddsubpdround(a: M512d, b: M512d, c: M512d, rounding: Int): M512d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfmaddsubpdround")

public actual fun vaddps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVaddps")

public actual fun vaddpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVaddpd")

public actual fun vsubps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsubps")

public actual fun vsubpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsubpd")

public actual fun vmulps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmulps")

public actual fun vmulpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmulpd")

public actual fun vdivps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVdivps")

public actual fun vdivpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVdivpd")

public actual fun vmaxps(a: F32x16, b: F32x16, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmaxps")

public actual fun vmaxpd(a: F64x8, b: F64x8, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmaxpd")

public actual fun vminps(a: F32x16, b: F32x16, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVminps")

public actual fun vminpd(a: F64x8, b: F64x8, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVminpd")

public actual fun vgetexpps(a: F32x16, src: F32x16, m: UShort, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexpps")

public actual fun vgetexpps256(a: F32x8, src: F32x8, m: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexpps256")

public actual fun vgetexpps128(a: F32x4, src: F32x4, m: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexpps128")

public actual fun vgetexppd(a: F64x8, src: F64x8, m: UByte, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexppd")

public actual fun vgetexppd256(a: F64x4, src: F64x4, m: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexppd256")

public actual fun vgetexppd128(a: F64x2, src: F64x2, m: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexppd128")

public actual fun vrndscaleps(a: F32x16, imm8: Int, src: F32x16, mask: UShort, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscaleps")

public actual fun vrndscaleps256(a: F32x8, imm8: Int, src: F32x8, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscaleps256")

public actual fun vrndscaleps128(a: F32x4, imm8: Int, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscaleps128")

public actual fun vrndscalepd(a: F64x8, imm8: Int, src: F64x8, mask: UByte, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscalepd")

public actual fun vrndscalepd256(a: F64x4, imm8: Int, src: F64x4, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscalepd256")

public actual fun vrndscalepd128(a: F64x2, imm8: Int, src: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscalepd128")

public actual fun vscalefps(a: F32x16, b: F32x16, src: F32x16, mask: UShort, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefps")

public actual fun vscalefps256(a: F32x8, b: F32x8, src: F32x8, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefps256")

public actual fun vscalefps128(a: F32x4, b: F32x4, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefps128")

public actual fun vscalefpd(a: F64x8, b: F64x8, src: F64x8, mask: UByte, rounding: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefpd")

public actual fun vscalefpd256(a: F64x4, b: F64x4, src: F64x4, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefpd256")

public actual fun vscalefpd128(a: F64x2, b: F64x2, src: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefpd128")

public actual fun vfixupimmps(a: F32x16, b: F32x16, c: I32x16, imm8: Int, mask: UShort, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmps")

public actual fun vfixupimmps256(a: F32x8, b: F32x8, c: I32x8, imm8: Int, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmps256")

public actual fun vfixupimmps128(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmps128")

public actual fun vfixupimmpd(a: F64x8, b: F64x8, c: I64x8, imm8: Int, mask: UByte, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpd")

public actual fun vfixupimmpd256(a: F64x4, b: F64x4, c: I64x4, imm8: Int, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpd256")

public actual fun vfixupimmpd128(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpd128")

public actual fun vfixupimmpsz(a: F32x16, b: F32x16, c: I32x16, imm8: Int, mask: UShort, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpsz")

public actual fun vfixupimmpsz256(a: F32x8, b: F32x8, c: I32x8, imm8: Int, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpsz256")

public actual fun vfixupimmpsz128(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpsz128")

public actual fun vfixupimmpdz(a: F64x8, b: F64x8, c: I64x8, imm8: Int, mask: UByte, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpdz")

public actual fun vfixupimmpdz256(a: F64x4, b: F64x4, c: I64x4, imm8: Int, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpdz256")

public actual fun vfixupimmpdz128(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmpdz128")

public actual fun vpternlogd(a: I32x16, b: I32x16, c: I32x16, imm8: Int): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpternlogd")

public actual fun vpternlogd256(a: I32x8, b: I32x8, c: I32x8, imm8: Int): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpternlogd256")

public actual fun vpternlogd128(a: I32x4, b: I32x4, c: I32x4, imm8: Int): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpternlogd128")

public actual fun vpternlogq(a: I64x8, b: I64x8, c: I64x8, imm8: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpternlogq")

public actual fun vpternlogq256(a: I64x4, b: I64x4, c: I64x4, imm8: Int): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpternlogq256")

public actual fun vpternlogq128(a: I64x2, b: I64x2, c: I64x2, imm8: Int): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpternlogq128")

public actual fun vgetmantps(a: F32x16, mantissas: Int, src: F32x16, m: UShort, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantps")

public actual fun vgetmantps256(a: F32x8, mantissas: Int, src: F32x8, m: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantps256")

public actual fun vgetmantps128(a: F32x4, mantissas: Int, src: F32x4, m: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantps128")

public actual fun vgetmantpd(a: F64x8, mantissas: Int, src: F64x8, m: UByte, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantpd")

public actual fun vgetmantpd256(a: F64x4, mantissas: Int, src: F64x4, m: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantpd256")

public actual fun vgetmantpd128(a: F64x2, mantissas: Int, src: F64x2, m: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantpd128")

public actual fun vrcp14ps(a: F32x16, src: F32x16, m: UShort): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14ps")

public actual fun vrcp14ps256(a: F32x8, src: F32x8, m: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14ps256")

public actual fun vrcp14ps128(a: F32x4, src: F32x4, m: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14ps128")

public actual fun vrcp14pd(a: F64x8, src: F64x8, m: UByte): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14pd")

public actual fun vrcp14pd256(a: F64x4, src: F64x4, m: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14pd256")

public actual fun vrcp14pd128(a: F64x2, src: F64x2, m: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14pd128")

public actual fun vrsqrt14ps(a: F32x16, src: F32x16, m: UShort): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14ps")

public actual fun vrsqrt14ps256(a: F32x8, src: F32x8, m: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14ps256")

public actual fun vrsqrt14ps128(a: F32x4, src: F32x4, m: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14ps128")

public actual fun vrsqrt14pd(a: F64x8, src: F64x8, m: UByte): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14pd")

public actual fun vrsqrt14pd256(a: F64x4, src: F64x4, m: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14pd256")

public actual fun vrsqrt14pd128(a: F64x2, src: F64x2, m: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14pd128")

public actual fun vcvtps2dq(a: F32x16, src: I32x16, mask: UShort, rounding: Int): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2dq")

public actual fun vcvtps2udq(a: F32x16, src: U32x16, mask: UShort, rounding: Int): U32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2udq")

public actual fun vcvtps2udq256(a: F32x8, src: U32x8, mask: UByte): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2udq256")

public actual fun vcvtps2udq128(a: F32x4, src: U32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2udq128")

public actual fun vcvtps2pd(a: F32x8, src: F64x8, mask: UByte, sae: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2pd")

public actual fun vcvtpd2ps128(a: F64x2, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2ps128")

public actual fun vcvtpd2ps(a: F64x8, src: F32x8, mask: UByte, rounding: Int): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2ps")

public actual fun vcvtpd2dq128(a: F64x2, src: I32x4, k: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2dq128")

public actual fun vcvtpd2dq(a: F64x8, src: I32x8, mask: UByte, rounding: Int): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2dq")

public actual fun vcvtpd2udq(a: F64x8, src: U32x8, mask: UByte, rounding: Int): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2udq")

public actual fun vcvtpd2udq256(a: F64x4, src: U32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2udq256")

public actual fun vcvtpd2udq128(a: F64x2, src: U32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtpd2udq128")

public actual fun vcvtdq2ps(a: I32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtdq2ps")

public actual fun vcvtudq2ps(a: U32x16, rounding: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtudq2ps")

public actual fun vcvtps2ph(a: F32x16, rounding: Int, src: I16x16, mask: UShort): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2ph")

public actual fun vcvtps2ph256(a: F32x8, imm8: Int, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2ph256")

public actual fun vcvtps2ph128(a: F32x4, imm8: Int, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtps2ph128")

public actual fun vcvtph2ps(a: I16x16, src: F32x16, mask: UShort, sae: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtph2ps")

public actual fun vcvttps2dq(a: F32x16, src: I32x16, mask: UShort, rounding: Int): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttps2dq")

public actual fun vcvttps2udq(a: F32x16, src: U32x16, mask: UShort, rounding: Int): U32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttps2udq")

public actual fun vcvttps2udq256(a: F32x8, src: U32x8, mask: UByte): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttps2udq256")

public actual fun vcvttps2udq128(a: F32x4, src: U32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttps2udq128")

public actual fun vcvttpd2dq(a: F64x8, src: I32x8, mask: UByte, rounding: Int): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttpd2dq")

public actual fun vcvttpd2dq128(a: F64x2, src: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttpd2dq128")

public actual fun vcvttpd2udq(a: F64x8, src: I32x8, mask: UByte, rounding: Int): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttpd2udq")

public actual fun vcvttpd2udq256(a: F64x4, src: I32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttpd2udq256")

public actual fun vcvttpd2udq128(a: F64x2, src: I32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttpd2udq128")

public actual fun vpmovdw128(a: I32x4, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdw128")

public actual fun vpmovdb256(a: I32x8, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdb256")

public actual fun vpmovdb128(a: I32x4, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdb128")

public actual fun vpmovqw256(a: I64x4, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqw256")

public actual fun vpmovqw128(a: I64x2, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqw128")

public actual fun vpmovqb256(a: I64x4, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqb256")

public actual fun vpmovqb128(a: I64x2, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqb128")

public actual fun vpmovqd128(a: I64x2, src: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqd128")

public actual fun vpmovdwmem(memAddr: MutPtr, a: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdwmem")

public actual fun vpmovdwmem256(memAddr: MutPtr, a: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdwmem256")

public actual fun vpmovdwmem128(memAddr: MutPtr, a: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdwmem128")

public actual fun vpmovsdwmem(memAddr: MutPtr, a: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdwmem")

public actual fun vpmovsdwmem256(memAddr: MutPtr, a: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdwmem256")

public actual fun vpmovsdwmem128(memAddr: MutPtr, a: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdwmem128")

public actual fun vpmovusdwmem(memAddr: MutPtr, a: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdwmem")

public actual fun vpmovusdwmem256(memAddr: MutPtr, a: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdwmem256")

public actual fun vpmovusdwmem128(memAddr: MutPtr, a: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdwmem128")

public actual fun vpmovdbmem(memAddr: MutPtr, a: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdbmem")

public actual fun vpmovdbmem256(memAddr: MutPtr, a: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdbmem256")

public actual fun vpmovdbmem128(memAddr: MutPtr, a: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovdbmem128")

public actual fun vpmovsdbmem(memAddr: MutPtr, a: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdbmem")

public actual fun vpmovsdbmem256(memAddr: MutPtr, a: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdbmem256")

public actual fun vpmovsdbmem128(memAddr: MutPtr, a: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdbmem128")

public actual fun vpmovusdbmem(memAddr: MutPtr, a: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdbmem")

public actual fun vpmovusdbmem256(memAddr: MutPtr, a: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdbmem256")

public actual fun vpmovusdbmem128(memAddr: MutPtr, a: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdbmem128")

public actual fun vpmovqwmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqwmem")

public actual fun vpmovqwmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqwmem256")

public actual fun vpmovqwmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqwmem128")

public actual fun vpmovsqwmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqwmem")

public actual fun vpmovsqwmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqwmem256")

public actual fun vpmovsqwmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqwmem128")

public actual fun vpmovusqwmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqwmem")

public actual fun vpmovusqwmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqwmem256")

public actual fun vpmovusqwmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqwmem128")

public actual fun vpmovqbmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqbmem")

public actual fun vpmovqbmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqbmem256")

public actual fun vpmovqbmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqbmem128")

public actual fun vpmovsqbmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqbmem")

public actual fun vpmovsqbmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqbmem256")

public actual fun vpmovsqbmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqbmem128")

public actual fun vpmovusqbmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqbmem")

public actual fun vpmovusqbmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqbmem256")

public actual fun vpmovusqbmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqbmem128")

public actual fun vpmovqdmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqdmem")

public actual fun vpmovqdmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqdmem256")

public actual fun vpmovqdmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqdmem128")

public actual fun vpmovsqdmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqdmem")

public actual fun vpmovsqdmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqdmem256")

public actual fun vpmovsqdmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqdmem128")

public actual fun vpmovusqdmem(memAddr: MutPtr, a: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqdmem")

public actual fun vpmovusqdmem256(memAddr: MutPtr, a: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqdmem256")

public actual fun vpmovusqdmem128(memAddr: MutPtr, a: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqdmem128")

public actual fun vpmovqb(a: I64x8, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovqb")

public actual fun vpmovsdw(a: I32x16, src: I16x16, mask: UShort): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdw")

public actual fun vpmovsdw256(a: I32x8, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdw256")

public actual fun vpmovsdw128(a: I32x4, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdw128")

public actual fun vpmovsdb(a: I32x16, src: I8x16, mask: UShort): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdb")

public actual fun vpmovsdb256(a: I32x8, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdb256")

public actual fun vpmovsdb128(a: I32x4, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsdb128")

public actual fun vpmovsqd(a: I64x8, src: I32x8, mask: UByte): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqd")

public actual fun vpmovsqd256(a: I64x4, src: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqd256")

public actual fun vpmovsqd128(a: I64x2, src: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqd128")

public actual fun vpmovsqw(a: I64x8, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqw")

public actual fun vpmovsqw256(a: I64x4, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqw256")

public actual fun vpmovsqw128(a: I64x2, src: I16x8, mask: UByte): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqw128")

public actual fun vpmovsqb(a: I64x8, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqb")

public actual fun vpmovsqb256(a: I64x4, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqb256")

public actual fun vpmovsqb128(a: I64x2, src: I8x16, mask: UByte): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovsqb128")

public actual fun vpmovusdw(a: U32x16, src: U16x16, mask: UShort): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdw")

public actual fun vpmovusdw256(a: U32x8, src: U16x8, mask: UByte): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdw256")

public actual fun vpmovusdw128(a: U32x4, src: U16x8, mask: UByte): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdw128")

public actual fun vpmovusdb(a: U32x16, src: U8x16, mask: UShort): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdb")

public actual fun vpmovusdb256(a: U32x8, src: U8x16, mask: UByte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdb256")

public actual fun vpmovusdb128(a: U32x4, src: U8x16, mask: UByte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusdb128")

public actual fun vpmovusqd(a: U64x8, src: U32x8, mask: UByte): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqd")

public actual fun vpmovusqd256(a: U64x4, src: U32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqd256")

public actual fun vpmovusqd128(a: U64x2, src: U32x4, mask: UByte): U32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqd128")

public actual fun vpmovusqw(a: U64x8, src: U16x8, mask: UByte): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqw")

public actual fun vpmovusqw256(a: U64x4, src: U16x8, mask: UByte): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqw256")

public actual fun vpmovusqw128(a: U64x2, src: U16x8, mask: UByte): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqw128")

public actual fun vpmovusqb(a: U64x8, src: U8x16, mask: UByte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqb")

public actual fun vpmovusqb256(a: U64x4, src: U8x16, mask: UByte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqb256")

public actual fun vpmovusqb128(a: U64x2, src: U8x16, mask: UByte): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpmovusqb128")

public actual fun vgatherdpd(src: F64x8, slice: ConstPtr, offsets: I32x8, mask: Byte, scale: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherdpd")

public actual fun vgatherdps(src: F32x16, slice: ConstPtr, offsets: I32x16, mask: Short, scale: Int): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherdps")

public actual fun vgatherqpd(src: F64x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherqpd")

public actual fun vgatherqps(src: F32x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherqps")

public actual fun vpgatherdq(src: I64x8, slice: ConstPtr, offsets: I32x8, mask: Byte, scale: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherdq")

public actual fun vpgatherdd(src: I32x16, slice: ConstPtr, offsets: I32x16, mask: Short, scale: Int): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherdd")

public actual fun vpgatherqq(src: I64x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherqq")

public actual fun vpgatherqd(src: I32x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherqd")

public actual fun vscatterdpd(slice: MutPtr, mask: Byte, offsets: I32x8, src: F64x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterdpd")

public actual fun vscatterdps(slice: MutPtr, mask: Short, offsets: I32x16, src: F32x16, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterdps")

public actual fun vscatterqpd(slice: MutPtr, mask: Byte, offsets: I64x8, src: F64x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterqpd")

public actual fun vscatterqps(slice: MutPtr, mask: Byte, offsets: I64x8, src: F32x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterqps")

public actual fun vpscatterdq(slice: MutPtr, mask: Byte, offsets: I32x8, src: I64x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterdq")

public actual fun vpscatterdd(slice: MutPtr, mask: Short, offsets: I32x16, src: I32x16, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterdd")

public actual fun vpscatterqq(slice: MutPtr, mask: Byte, offsets: I64x8, src: I64x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterqq")

public actual fun vpscatterqd(slice: MutPtr, mask: Byte, offsets: I64x8, src: I32x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterqd")

public actual fun vpscatterdd128(slice: MutPtr, k: UByte, offsets: I32x4, src: I32x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterdd128")

public actual fun vpscatterdq128(slice: MutPtr, k: UByte, offsets: I32x4, src: I64x2, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterdq128")

public actual fun vscatterdpd128(slice: MutPtr, k: UByte, offsets: I32x4, src: F64x2, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterdpd128")

public actual fun vscatterdps128(slice: MutPtr, k: UByte, offsets: I32x4, src: F32x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterdps128")

public actual fun vpscatterqd128(slice: MutPtr, k: UByte, offsets: I64x2, src: I32x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterqd128")

public actual fun vpscatterqq128(slice: MutPtr, k: UByte, offsets: I64x2, src: I64x2, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterqq128")

public actual fun vscatterqpd128(slice: MutPtr, k: UByte, offsets: I64x2, src: F64x2, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterqpd128")

public actual fun vscatterqps128(slice: MutPtr, k: UByte, offsets: I64x2, src: F32x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterqps128")

public actual fun vpscatterdd256(slice: MutPtr, k: UByte, offsets: I32x8, src: I32x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterdd256")

public actual fun vpscatterdq256(slice: MutPtr, k: UByte, offsets: I32x4, src: I64x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterdq256")

public actual fun vscatterdpd256(slice: MutPtr, k: UByte, offsets: I32x4, src: F64x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterdpd256")

public actual fun vscatterdps256(slice: MutPtr, k: UByte, offsets: I32x8, src: F32x8, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterdps256")

public actual fun vpscatterqd256(slice: MutPtr, k: UByte, offsets: I64x4, src: I32x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterqd256")

public actual fun vpscatterqq256(slice: MutPtr, k: UByte, offsets: I64x4, src: I64x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpscatterqq256")

public actual fun vscatterqpd256(slice: MutPtr, k: UByte, offsets: I64x4, src: F64x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterqpd256")

public actual fun vscatterqps256(slice: MutPtr, k: UByte, offsets: I64x4, src: F32x4, scale: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscatterqps256")

public actual fun vpgatherdd128(src: I32x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherdd128")

public actual fun vpgatherdq128(src: I64x2, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherdq128")

public actual fun vgatherdpd128(src: F64x2, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherdpd128")

public actual fun vgatherdps128(src: F32x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherdps128")

public actual fun vpgatherqd128(src: I32x4, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherqd128")

public actual fun vpgatherqq128(src: I64x2, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherqq128")

public actual fun vgatherqpd128(src: F64x2, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherqpd128")

public actual fun vgatherqps128(src: F32x4, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherqps128")

public actual fun vpgatherdd256(src: I32x8, slice: ConstPtr, offsets: I32x8, k: UByte, scale: Int): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherdd256")

public actual fun vpgatherdq256(src: I64x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherdq256")

public actual fun vgatherdpd256(src: F64x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherdpd256")

public actual fun vgatherdps256(src: F32x8, slice: ConstPtr, offsets: I32x8, k: UByte, scale: Int): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherdps256")

public actual fun vpgatherqd256(src: I32x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherqd256")

public actual fun vpgatherqq256(src: I64x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpgatherqq256")

public actual fun vgatherqpd256(src: F64x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherqpd256")

public actual fun vgatherqps256(src: F32x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgatherqps256")

public actual fun vcmpss(a: M128, b: M128, op: Int, m: Byte, sae: Int): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmpss")

public actual fun vcmpsd(a: M128d, b: M128d, op: Int, m: Byte, sae: Int): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmpsd")

public actual fun vcmpps(a: F32x16, b: F32x16, op: Int, m: Short, sae: Int): Short =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmpps")

public actual fun vcmpps256(a: F32x8, b: F32x8, op: Int, m: Byte): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmpps256")

public actual fun vcmpps128(a: F32x4, b: F32x4, op: Int, m: Byte): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmpps128")

public actual fun vcmppd(a: F64x8, b: F64x8, op: Int, m: Byte, sae: Int): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmppd")

public actual fun vcmppd256(a: F64x4, b: F64x4, op: Int, m: Byte): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmppd256")

public actual fun vcmppd128(a: F64x2, b: F64x2, op: Int, m: Byte): Byte =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcmppd128")

public actual fun vpsllvd(a: I32x16, b: I32x16): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsllvd")

public actual fun vpsrlvd(a: I32x16, b: I32x16): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsrlvd")

public actual fun vpsllvq(a: I64x8, b: I64x8): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsllvq")

public actual fun vpsrlvq(a: I64x8, b: I64x8): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsrlvq")

public actual fun vpslld(a: I32x16, count: I32x4): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpslld")

public actual fun vpsrld(a: I32x16, count: I32x4): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsrld")

public actual fun vpsllq(a: I64x8, count: I64x2): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsllq")

public actual fun vpsrlq(a: I64x8, count: I64x2): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsrlq")

public actual fun vpsrad(a: I32x16, count: I32x4): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsrad")

public actual fun vpsraq(a: I64x8, count: I64x2): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsraq")

public actual fun vpsraq256(a: I64x4, count: I64x2): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsraq256")

public actual fun vpsraq128(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsraq128")

public actual fun vpsravd(a: I32x16, count: I32x16): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsravd")

public actual fun vpsravq(a: I64x8, count: I64x8): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsravq")

public actual fun vpsravq256(a: I64x4, count: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsravq256")

public actual fun vpsravq128(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpsravq128")

public actual fun vpermilps(a: F32x16, b: I32x16): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermilps")

public actual fun vpermilpd(a: F64x8, b: I64x8): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermilpd")

public actual fun vpermd(a: I32x16, idx: I32x16): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermd")

public actual fun vpermq(a: I64x8, idx: I64x8): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermq")

public actual fun vpermq256(a: I64x4, idx: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermq256")

public actual fun vpermps(a: F32x16, idx: I32x16): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermps")

public actual fun vpermpd(a: F64x8, idx: I64x8): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermpd")

public actual fun vpermpd256(a: F64x4, idx: I64x4): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermpd256")

public actual fun vpermi2d(a: I32x16, idx: I32x16, b: I32x16): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2d")

public actual fun vpermi2d256(a: I32x8, idx: I32x8, b: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2d256")

public actual fun vpermi2d128(a: I32x4, idx: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2d128")

public actual fun vpermi2q(a: I64x8, idx: I64x8, b: I64x8): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2q")

public actual fun vpermi2q256(a: I64x4, idx: I64x4, b: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2q256")

public actual fun vpermi2q128(a: I64x2, idx: I64x2, b: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2q128")

public actual fun vpermi2ps(a: F32x16, idx: I32x16, b: F32x16): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2ps")

public actual fun vpermi2ps256(a: F32x8, idx: I32x8, b: F32x8): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2ps256")

public actual fun vpermi2ps128(a: F32x4, idx: I32x4, b: F32x4): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2ps128")

public actual fun vpermi2pd(a: F64x8, idx: I64x8, b: F64x8): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2pd")

public actual fun vpermi2pd256(a: F64x4, idx: I64x4, b: F64x4): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2pd256")

public actual fun vpermi2pd128(a: F64x2, idx: I64x2, b: F64x2): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpermi2pd128")

public actual fun vpcompressd(a: I32x16, src: I32x16, mask: UShort): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpcompressd")

public actual fun vpcompressd256(a: I32x8, src: I32x8, mask: UByte): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpcompressd256")

public actual fun vpcompressd128(a: I32x4, src: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpcompressd128")

public actual fun vpcompressq(a: I64x8, src: I64x8, mask: UByte): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpcompressq")

public actual fun vpcompressq256(a: I64x4, src: I64x4, mask: UByte): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpcompressq256")

public actual fun vpcompressq128(a: I64x2, src: I64x2, mask: UByte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpcompressq128")

public actual fun vcompressps(a: F32x16, src: F32x16, mask: UShort): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressps")

public actual fun vcompressps256(a: F32x8, src: F32x8, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressps256")

public actual fun vcompressps128(a: F32x4, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressps128")

public actual fun vcompresspd(a: F64x8, src: F64x8, mask: UByte): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompresspd")

public actual fun vcompresspd256(a: F64x4, src: F64x4, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompresspd256")

public actual fun vcompresspd128(a: F64x2, src: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompresspd128")

public actual fun vcompressstored(mem: MutPtr, data: I32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstored")

public actual fun vcompressstored256(mem: MutPtr, data: I32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstored256")

public actual fun vcompressstored128(mem: MutPtr, data: I32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstored128")

public actual fun vcompressstoreq(mem: MutPtr, data: I64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstoreq")

public actual fun vcompressstoreq256(mem: MutPtr, data: I64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstoreq256")

public actual fun vcompressstoreq128(mem: MutPtr, data: I64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstoreq128")

public actual fun vcompressstoreps(mem: MutPtr, data: F32x16, mask: UShort): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstoreps")

public actual fun vcompressstoreps256(mem: MutPtr, data: F32x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstoreps256")

public actual fun vcompressstoreps128(mem: MutPtr, data: F32x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstoreps128")

public actual fun vcompressstorepd(mem: MutPtr, data: F64x8, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstorepd")

public actual fun vcompressstorepd256(mem: MutPtr, data: F64x4, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstorepd256")

public actual fun vcompressstorepd128(mem: MutPtr, data: F64x2, mask: UByte): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcompressstorepd128")

public actual fun vpexpandd(a: I32x16, src: I32x16, mask: UShort): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpexpandd")

public actual fun vpexpandd256(a: I32x8, src: I32x8, mask: UByte): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpexpandd256")

public actual fun vpexpandd128(a: I32x4, src: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpexpandd128")

public actual fun vpexpandq(a: I64x8, src: I64x8, mask: UByte): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpexpandq")

public actual fun vpexpandq256(a: I64x4, src: I64x4, mask: UByte): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpexpandq256")

public actual fun vpexpandq128(a: I64x2, src: I64x2, mask: UByte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVpexpandq128")

public actual fun vexpandps(a: F32x16, src: F32x16, mask: UShort): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVexpandps")

public actual fun vexpandps256(a: F32x8, src: F32x8, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVexpandps256")

public actual fun vexpandps128(a: F32x4, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVexpandps128")

public actual fun vexpandpd(a: F64x8, src: F64x8, mask: UByte): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVexpandpd")

public actual fun vexpandpd256(a: F64x4, src: F64x4, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVexpandpd256")

public actual fun vexpandpd128(a: F64x2, src: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVexpandpd128")

public actual fun vaddss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVaddss")

public actual fun vaddsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVaddsd")

public actual fun vsubss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsubss")

public actual fun vsubsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsubsd")

public actual fun vmulss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmulss")

public actual fun vmulsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmulsd")

public actual fun vdivss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVdivss")

public actual fun vdivsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVdivsd")

public actual fun vmaxss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmaxss")

public actual fun vmaxsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVmaxsd")

public actual fun vminss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVminss")

public actual fun vminsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVminsd")

public actual fun vsqrtss(a: M128, b: M128, src: M128, mask: UByte, rounding: Int): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsqrtss")

public actual fun vsqrtsd(a: M128d, b: M128d, src: M128d, mask: UByte, rounding: Int): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVsqrtsd")

public actual fun vgetexpss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexpss")

public actual fun vgetexpsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetexpsd")

public actual fun vgetmantss(a: F32x4, b: F32x4, mantissas: Int, src: F32x4, m: UByte, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantss")

public actual fun vgetmantsd(a: F64x2, b: F64x2, mantissas: Int, src: F64x2, m: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVgetmantsd")

public actual fun vrsqrt14ss(a: F32x4, b: F32x4, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14ss")

public actual fun vrsqrt14sd(a: F64x2, b: F64x2, src: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrsqrt14sd")

public actual fun vrcp14ss(a: F32x4, b: F32x4, src: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14ss")

public actual fun vrcp14sd(a: F64x2, b: F64x2, src: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrcp14sd")

public actual fun vrndscaless(a: F32x4, b: F32x4, src: F32x4, mask: UByte, imm8: Int, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscaless")

public actual fun vrndscalesd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, imm8: Int, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVrndscalesd")

public actual fun vscalefss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefss")

public actual fun vscalefsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVscalefsd")

@kotlin.wasm.WasmImport("kmcore", "corearchX86Avx512fVfmaddssround")
private external fun wasiCorearchX86Avx512fVfmaddssround(a: Float, b: Float, c: Float, rounding: Int): Float

public actual fun vfmaddssround(a: Float, b: Float, c: Float, rounding: Int): Float =
    wasiCorearchX86Avx512fVfmaddssround(a, b, c, rounding).toFloat()

@kotlin.wasm.WasmImport("kmcore", "corearchX86Avx512fVfmaddsdround")
private external fun wasiCorearchX86Avx512fVfmaddsdround(a: Double, b: Double, c: Double, rounding: Int): Double

public actual fun vfmaddsdround(a: Double, b: Double, c: Double, rounding: Int): Double =
    wasiCorearchX86Avx512fVfmaddsdround(a, b, c, rounding).toDouble()

public actual fun vfixupimmss(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmss")

public actual fun vfixupimmsd(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmsd")

public actual fun vfixupimmssz(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte, sae: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmssz")

public actual fun vfixupimmsdz(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVfixupimmsdz")

public actual fun vcvtss2sd(a: F64x2, b: F32x4, src: F64x2, mask: UByte, sae: Int): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtss2sd")

public actual fun vcvtsd2ss(a: F32x4, b: F64x2, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtsd2ss")

public actual fun vcvtss2si(a: F32x4, rounding: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtss2si")

public actual fun vcvtss2usi(a: F32x4, rounding: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtss2usi")

public actual fun vcvtsd2si(a: F64x2, rounding: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtsd2si")

public actual fun vcvtsd2usi(a: F64x2, rounding: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtsd2usi")

public actual fun vcvtsi2ss(a: F32x4, b: Int, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtsi2ss")

public actual fun vcvtusi2ss(a: F32x4, b: UInt, rounding: Int): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvtusi2ss")

public actual fun vcvttss2si(a: F32x4, rounding: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttss2si")

public actual fun vcvttss2usi(a: F32x4, rounding: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttss2usi")

public actual fun vcvttsd2si(a: F64x2, rounding: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttsd2si")

public actual fun vcvttsd2usi(a: F64x2, rounding: Int): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcvttsd2usi")

public actual fun vcomiss(a: F32x4, b: F32x4, imm8: Int, sae: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcomiss")

public actual fun vcomisd(a: F64x2, b: F64x2, imm8: Int, sae: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fVcomisd")

public actual fun expandloadd128(memAddr: ConstPtr, a: I32x4, mask: UByte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadd128")

public actual fun expandloadq128(memAddr: ConstPtr, a: I64x2, mask: UByte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadq128")

public actual fun expandloadps128(memAddr: ConstPtr, a: F32x4, mask: UByte): F32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadps128")

public actual fun expandloadpd128(memAddr: ConstPtr, a: F64x2, mask: UByte): F64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadpd128")

public actual fun expandloadd256(memAddr: ConstPtr, a: I32x8, mask: UByte): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadd256")

public actual fun expandloadq256(memAddr: ConstPtr, a: I64x4, mask: UByte): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadq256")

public actual fun expandloadps256(memAddr: ConstPtr, a: F32x8, mask: UByte): F32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadps256")

public actual fun expandloadpd256(memAddr: ConstPtr, a: F64x4, mask: UByte): F64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadpd256")

public actual fun expandloadd512(memAddr: ConstPtr, a: I32x16, mask: UShort): I32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadd512")

public actual fun expandloadq512(memAddr: ConstPtr, a: I64x8, mask: UByte): I64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadq512")

public actual fun expandloadps512(memAddr: ConstPtr, a: F32x16, mask: UShort): F32x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadps512")

public actual fun expandloadpd512(memAddr: ConstPtr, a: F64x8, mask: UByte): F64x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx512fExpandloadpd512")
