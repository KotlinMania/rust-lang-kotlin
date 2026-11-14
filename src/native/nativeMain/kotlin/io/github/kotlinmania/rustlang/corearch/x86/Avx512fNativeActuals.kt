@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

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
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Avx512fVfmaddsdround
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Avx512fVfmaddssround
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vsqrtps(a: F32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vsqrtpd(a: F64x8, rounding: Int): F64x8 =
    null as F64x8

public actual fun vfmadd132psround(a: M512, b: M512, c: M512, rounding: Int): M512 =
    M512()

public actual fun vfmadd132pdround(a: M512d, b: M512d, c: M512d, rounding: Int): M512d =
    M512d()

public actual fun vfmaddsubpsround(a: M512, b: M512, c: M512, rounding: Int): M512 =
    M512()

public actual fun vfmaddsubpdround(a: M512d, b: M512d, c: M512d, rounding: Int): M512d =
    M512d()

public actual fun vaddps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vaddpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    null as F64x8

public actual fun vsubps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vsubpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    null as F64x8

public actual fun vmulps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vmulpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    null as F64x8

public actual fun vdivps(a: F32x16, b: F32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vdivpd(a: F64x8, b: F64x8, rounding: Int): F64x8 =
    null as F64x8

public actual fun vmaxps(a: F32x16, b: F32x16, sae: Int): F32x16 =
    null as F32x16

public actual fun vmaxpd(a: F64x8, b: F64x8, sae: Int): F64x8 =
    null as F64x8

public actual fun vminps(a: F32x16, b: F32x16, sae: Int): F32x16 =
    null as F32x16

public actual fun vminpd(a: F64x8, b: F64x8, sae: Int): F64x8 =
    null as F64x8

public actual fun vgetexpps(a: F32x16, src: F32x16, m: UShort, sae: Int): F32x16 =
    null as F32x16

public actual fun vgetexpps256(a: F32x8, src: F32x8, m: UByte): F32x8 =
    null as F32x8

public actual fun vgetexpps128(a: F32x4, src: F32x4, m: UByte): F32x4 =
    null as F32x4

public actual fun vgetexppd(a: F64x8, src: F64x8, m: UByte, sae: Int): F64x8 =
    null as F64x8

public actual fun vgetexppd256(a: F64x4, src: F64x4, m: UByte): F64x4 =
    null as F64x4

public actual fun vgetexppd128(a: F64x2, src: F64x2, m: UByte): F64x2 =
    null as F64x2

public actual fun vrndscaleps(a: F32x16, imm8: Int, src: F32x16, mask: UShort, sae: Int): F32x16 =
    null as F32x16

public actual fun vrndscaleps256(a: F32x8, imm8: Int, src: F32x8, mask: UByte): F32x8 =
    null as F32x8

public actual fun vrndscaleps128(a: F32x4, imm8: Int, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vrndscalepd(a: F64x8, imm8: Int, src: F64x8, mask: UByte, sae: Int): F64x8 =
    null as F64x8

public actual fun vrndscalepd256(a: F64x4, imm8: Int, src: F64x4, mask: UByte): F64x4 =
    null as F64x4

public actual fun vrndscalepd128(a: F64x2, imm8: Int, src: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun vscalefps(a: F32x16, b: F32x16, src: F32x16, mask: UShort, rounding: Int): F32x16 =
    null as F32x16

public actual fun vscalefps256(a: F32x8, b: F32x8, src: F32x8, mask: UByte): F32x8 =
    null as F32x8

public actual fun vscalefps128(a: F32x4, b: F32x4, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vscalefpd(a: F64x8, b: F64x8, src: F64x8, mask: UByte, rounding: Int): F64x8 =
    null as F64x8

public actual fun vscalefpd256(a: F64x4, b: F64x4, src: F64x4, mask: UByte): F64x4 =
    null as F64x4

public actual fun vscalefpd128(a: F64x2, b: F64x2, src: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun vfixupimmps(a: F32x16, b: F32x16, c: I32x16, imm8: Int, mask: UShort, sae: Int): F32x16 =
    null as F32x16

public actual fun vfixupimmps256(a: F32x8, b: F32x8, c: I32x8, imm8: Int, mask: UByte): F32x8 =
    null as F32x8

public actual fun vfixupimmps128(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte): F32x4 =
    null as F32x4

public actual fun vfixupimmpd(a: F64x8, b: F64x8, c: I64x8, imm8: Int, mask: UByte, sae: Int): F64x8 =
    null as F64x8

public actual fun vfixupimmpd256(a: F64x4, b: F64x4, c: I64x4, imm8: Int, mask: UByte): F64x4 =
    null as F64x4

public actual fun vfixupimmpd128(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte): F64x2 =
    null as F64x2

public actual fun vfixupimmpsz(a: F32x16, b: F32x16, c: I32x16, imm8: Int, mask: UShort, sae: Int): F32x16 =
    null as F32x16

public actual fun vfixupimmpsz256(a: F32x8, b: F32x8, c: I32x8, imm8: Int, mask: UByte): F32x8 =
    null as F32x8

public actual fun vfixupimmpsz128(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte): F32x4 =
    null as F32x4

public actual fun vfixupimmpdz(a: F64x8, b: F64x8, c: I64x8, imm8: Int, mask: UByte, sae: Int): F64x8 =
    null as F64x8

public actual fun vfixupimmpdz256(a: F64x4, b: F64x4, c: I64x4, imm8: Int, mask: UByte): F64x4 =
    null as F64x4

public actual fun vfixupimmpdz128(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte): F64x2 =
    null as F64x2

public actual fun vpternlogd(a: I32x16, b: I32x16, c: I32x16, imm8: Int): I32x16 =
    null as I32x16

public actual fun vpternlogd256(a: I32x8, b: I32x8, c: I32x8, imm8: Int): I32x8 =
    null as I32x8

public actual fun vpternlogd128(a: I32x4, b: I32x4, c: I32x4, imm8: Int): I32x4 =
    null as I32x4

public actual fun vpternlogq(a: I64x8, b: I64x8, c: I64x8, imm8: Int): I64x8 =
    null as I64x8

public actual fun vpternlogq256(a: I64x4, b: I64x4, c: I64x4, imm8: Int): I64x4 =
    null as I64x4

public actual fun vpternlogq128(a: I64x2, b: I64x2, c: I64x2, imm8: Int): I64x2 =
    null as I64x2

public actual fun vgetmantps(a: F32x16, mantissas: Int, src: F32x16, m: UShort, sae: Int): F32x16 =
    null as F32x16

public actual fun vgetmantps256(a: F32x8, mantissas: Int, src: F32x8, m: UByte): F32x8 =
    null as F32x8

public actual fun vgetmantps128(a: F32x4, mantissas: Int, src: F32x4, m: UByte): F32x4 =
    null as F32x4

public actual fun vgetmantpd(a: F64x8, mantissas: Int, src: F64x8, m: UByte, sae: Int): F64x8 =
    null as F64x8

public actual fun vgetmantpd256(a: F64x4, mantissas: Int, src: F64x4, m: UByte): F64x4 =
    null as F64x4

public actual fun vgetmantpd128(a: F64x2, mantissas: Int, src: F64x2, m: UByte): F64x2 =
    null as F64x2

public actual fun vrcp14ps(a: F32x16, src: F32x16, m: UShort): F32x16 =
    null as F32x16

public actual fun vrcp14ps256(a: F32x8, src: F32x8, m: UByte): F32x8 =
    null as F32x8

public actual fun vrcp14ps128(a: F32x4, src: F32x4, m: UByte): F32x4 =
    null as F32x4

public actual fun vrcp14pd(a: F64x8, src: F64x8, m: UByte): F64x8 =
    null as F64x8

public actual fun vrcp14pd256(a: F64x4, src: F64x4, m: UByte): F64x4 =
    null as F64x4

public actual fun vrcp14pd128(a: F64x2, src: F64x2, m: UByte): F64x2 =
    null as F64x2

public actual fun vrsqrt14ps(a: F32x16, src: F32x16, m: UShort): F32x16 =
    null as F32x16

public actual fun vrsqrt14ps256(a: F32x8, src: F32x8, m: UByte): F32x8 =
    null as F32x8

public actual fun vrsqrt14ps128(a: F32x4, src: F32x4, m: UByte): F32x4 =
    null as F32x4

public actual fun vrsqrt14pd(a: F64x8, src: F64x8, m: UByte): F64x8 =
    null as F64x8

public actual fun vrsqrt14pd256(a: F64x4, src: F64x4, m: UByte): F64x4 =
    null as F64x4

public actual fun vrsqrt14pd128(a: F64x2, src: F64x2, m: UByte): F64x2 =
    null as F64x2

public actual fun vcvtps2dq(a: F32x16, src: I32x16, mask: UShort, rounding: Int): I32x16 =
    null as I32x16

public actual fun vcvtps2udq(a: F32x16, src: U32x16, mask: UShort, rounding: Int): U32x16 =
    null as U32x16

public actual fun vcvtps2udq256(a: F32x8, src: U32x8, mask: UByte): U32x8 =
    null as U32x8

public actual fun vcvtps2udq128(a: F32x4, src: U32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vcvtps2pd(a: F32x8, src: F64x8, mask: UByte, sae: Int): F64x8 =
    null as F64x8

public actual fun vcvtpd2ps128(a: F64x2, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vcvtpd2ps(a: F64x8, src: F32x8, mask: UByte, rounding: Int): F32x8 =
    null as F32x8

public actual fun vcvtpd2dq128(a: F64x2, src: I32x4, k: UByte): I32x4 =
    null as I32x4

public actual fun vcvtpd2dq(a: F64x8, src: I32x8, mask: UByte, rounding: Int): I32x8 =
    null as I32x8

public actual fun vcvtpd2udq(a: F64x8, src: U32x8, mask: UByte, rounding: Int): U32x8 =
    null as U32x8

public actual fun vcvtpd2udq256(a: F64x4, src: U32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vcvtpd2udq128(a: F64x2, src: U32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vcvtdq2ps(a: I32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vcvtudq2ps(a: U32x16, rounding: Int): F32x16 =
    null as F32x16

public actual fun vcvtps2ph(a: F32x16, rounding: Int, src: I16x16, mask: UShort): I16x16 =
    null as I16x16

public actual fun vcvtps2ph256(a: F32x8, imm8: Int, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vcvtps2ph128(a: F32x4, imm8: Int, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vcvtph2ps(a: I16x16, src: F32x16, mask: UShort, sae: Int): F32x16 =
    null as F32x16

public actual fun vcvttps2dq(a: F32x16, src: I32x16, mask: UShort, rounding: Int): I32x16 =
    null as I32x16

public actual fun vcvttps2udq(a: F32x16, src: U32x16, mask: UShort, rounding: Int): U32x16 =
    null as U32x16

public actual fun vcvttps2udq256(a: F32x8, src: U32x8, mask: UByte): U32x8 =
    null as U32x8

public actual fun vcvttps2udq128(a: F32x4, src: U32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vcvttpd2dq(a: F64x8, src: I32x8, mask: UByte, rounding: Int): I32x8 =
    null as I32x8

public actual fun vcvttpd2dq128(a: F64x2, src: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun vcvttpd2udq(a: F64x8, src: I32x8, mask: UByte, rounding: Int): U32x8 =
    null as U32x8

public actual fun vcvttpd2udq256(a: F64x4, src: I32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vcvttpd2udq128(a: F64x2, src: I32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vpmovdw128(a: I32x4, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovdb256(a: I32x8, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovdb128(a: I32x4, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovqw256(a: I64x4, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovqw128(a: I64x2, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovqb256(a: I64x4, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovqb128(a: I64x2, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovqd128(a: I64x2, src: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun vpmovdwmem(memAddr: MutPtr, a: I32x16, mask: UShort) {
}

public actual fun vpmovdwmem256(memAddr: MutPtr, a: I32x8, mask: UByte) {
}

public actual fun vpmovdwmem128(memAddr: MutPtr, a: I32x4, mask: UByte) {
}

public actual fun vpmovsdwmem(memAddr: MutPtr, a: I32x16, mask: UShort) {
}

public actual fun vpmovsdwmem256(memAddr: MutPtr, a: I32x8, mask: UByte) {
}

public actual fun vpmovsdwmem128(memAddr: MutPtr, a: I32x4, mask: UByte) {
}

public actual fun vpmovusdwmem(memAddr: MutPtr, a: I32x16, mask: UShort) {
}

public actual fun vpmovusdwmem256(memAddr: MutPtr, a: I32x8, mask: UByte) {
}

public actual fun vpmovusdwmem128(memAddr: MutPtr, a: I32x4, mask: UByte) {
}

public actual fun vpmovdbmem(memAddr: MutPtr, a: I32x16, mask: UShort) {
}

public actual fun vpmovdbmem256(memAddr: MutPtr, a: I32x8, mask: UByte) {
}

public actual fun vpmovdbmem128(memAddr: MutPtr, a: I32x4, mask: UByte) {
}

public actual fun vpmovsdbmem(memAddr: MutPtr, a: I32x16, mask: UShort) {
}

public actual fun vpmovsdbmem256(memAddr: MutPtr, a: I32x8, mask: UByte) {
}

public actual fun vpmovsdbmem128(memAddr: MutPtr, a: I32x4, mask: UByte) {
}

public actual fun vpmovusdbmem(memAddr: MutPtr, a: I32x16, mask: UShort) {
}

public actual fun vpmovusdbmem256(memAddr: MutPtr, a: I32x8, mask: UByte) {
}

public actual fun vpmovusdbmem128(memAddr: MutPtr, a: I32x4, mask: UByte) {
}

public actual fun vpmovqwmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovqwmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovqwmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovsqwmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovsqwmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovsqwmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovusqwmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovusqwmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovusqwmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovqbmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovqbmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovqbmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovsqbmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovsqbmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovsqbmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovusqbmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovusqbmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovusqbmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovqdmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovqdmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovqdmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovsqdmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovsqdmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovsqdmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovusqdmem(memAddr: MutPtr, a: I64x8, mask: UByte) {
}

public actual fun vpmovusqdmem256(memAddr: MutPtr, a: I64x4, mask: UByte) {
}

public actual fun vpmovusqdmem128(memAddr: MutPtr, a: I64x2, mask: UByte) {
}

public actual fun vpmovqb(a: I64x8, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovsdw(a: I32x16, src: I16x16, mask: UShort): I16x16 =
    null as I16x16

public actual fun vpmovsdw256(a: I32x8, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovsdw128(a: I32x4, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovsdb(a: I32x16, src: I8x16, mask: UShort): I8x16 =
    null as I8x16

public actual fun vpmovsdb256(a: I32x8, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovsdb128(a: I32x4, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovsqd(a: I64x8, src: I32x8, mask: UByte): I32x8 =
    null as I32x8

public actual fun vpmovsqd256(a: I64x4, src: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun vpmovsqd128(a: I64x2, src: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun vpmovsqw(a: I64x8, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovsqw256(a: I64x4, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovsqw128(a: I64x2, src: I16x8, mask: UByte): I16x8 =
    null as I16x8

public actual fun vpmovsqb(a: I64x8, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovsqb256(a: I64x4, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovsqb128(a: I64x2, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovusdw(a: U32x16, src: U16x16, mask: UShort): U16x16 =
    null as U16x16

public actual fun vpmovusdw256(a: U32x8, src: U16x8, mask: UByte): U16x8 =
    null as U16x8

public actual fun vpmovusdw128(a: U32x4, src: U16x8, mask: UByte): U16x8 =
    null as U16x8

public actual fun vpmovusdb(a: U32x16, src: U8x16, mask: UShort): U8x16 =
    null as U8x16

public actual fun vpmovusdb256(a: U32x8, src: U8x16, mask: UByte): U8x16 =
    null as U8x16

public actual fun vpmovusdb128(a: U32x4, src: U8x16, mask: UByte): U8x16 =
    null as U8x16

public actual fun vpmovusqd(a: U64x8, src: U32x8, mask: UByte): U32x8 =
    null as U32x8

public actual fun vpmovusqd256(a: U64x4, src: U32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vpmovusqd128(a: U64x2, src: U32x4, mask: UByte): U32x4 =
    null as U32x4

public actual fun vpmovusqw(a: U64x8, src: U16x8, mask: UByte): U16x8 =
    null as U16x8

public actual fun vpmovusqw256(a: U64x4, src: U16x8, mask: UByte): U16x8 =
    null as U16x8

public actual fun vpmovusqw128(a: U64x2, src: U16x8, mask: UByte): U16x8 =
    null as U16x8

public actual fun vpmovusqb(a: U64x8, src: U8x16, mask: UByte): U8x16 =
    null as U8x16

public actual fun vpmovusqb256(a: U64x4, src: U8x16, mask: UByte): U8x16 =
    null as U8x16

public actual fun vpmovusqb128(a: U64x2, src: U8x16, mask: UByte): U8x16 =
    null as U8x16

public actual fun vgatherdpd(src: F64x8, slice: ConstPtr, offsets: I32x8, mask: Byte, scale: Int): F64x8 =
    null as F64x8

public actual fun vgatherdps(src: F32x16, slice: ConstPtr, offsets: I32x16, mask: Short, scale: Int): F32x16 =
    null as F32x16

public actual fun vgatherqpd(src: F64x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): F64x8 =
    null as F64x8

public actual fun vgatherqps(src: F32x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): F32x8 =
    null as F32x8

public actual fun vpgatherdq(src: I64x8, slice: ConstPtr, offsets: I32x8, mask: Byte, scale: Int): I64x8 =
    null as I64x8

public actual fun vpgatherdd(src: I32x16, slice: ConstPtr, offsets: I32x16, mask: Short, scale: Int): I32x16 =
    null as I32x16

public actual fun vpgatherqq(src: I64x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): I64x8 =
    null as I64x8

public actual fun vpgatherqd(src: I32x8, slice: ConstPtr, offsets: I64x8, mask: Byte, scale: Int): I32x8 =
    null as I32x8

public actual fun vscatterdpd(slice: MutPtr, mask: Byte, offsets: I32x8, src: F64x8, scale: Int) {
}

public actual fun vscatterdps(slice: MutPtr, mask: Short, offsets: I32x16, src: F32x16, scale: Int) {
}

public actual fun vscatterqpd(slice: MutPtr, mask: Byte, offsets: I64x8, src: F64x8, scale: Int) {
}

public actual fun vscatterqps(slice: MutPtr, mask: Byte, offsets: I64x8, src: F32x8, scale: Int) {
}

public actual fun vpscatterdq(slice: MutPtr, mask: Byte, offsets: I32x8, src: I64x8, scale: Int) {
}

public actual fun vpscatterdd(slice: MutPtr, mask: Short, offsets: I32x16, src: I32x16, scale: Int) {
}

public actual fun vpscatterqq(slice: MutPtr, mask: Byte, offsets: I64x8, src: I64x8, scale: Int) {
}

public actual fun vpscatterqd(slice: MutPtr, mask: Byte, offsets: I64x8, src: I32x8, scale: Int) {
}

public actual fun vpscatterdd128(slice: MutPtr, k: UByte, offsets: I32x4, src: I32x4, scale: Int) {
}

public actual fun vpscatterdq128(slice: MutPtr, k: UByte, offsets: I32x4, src: I64x2, scale: Int) {
}

public actual fun vscatterdpd128(slice: MutPtr, k: UByte, offsets: I32x4, src: F64x2, scale: Int) {
}

public actual fun vscatterdps128(slice: MutPtr, k: UByte, offsets: I32x4, src: F32x4, scale: Int) {
}

public actual fun vpscatterqd128(slice: MutPtr, k: UByte, offsets: I64x2, src: I32x4, scale: Int) {
}

public actual fun vpscatterqq128(slice: MutPtr, k: UByte, offsets: I64x2, src: I64x2, scale: Int) {
}

public actual fun vscatterqpd128(slice: MutPtr, k: UByte, offsets: I64x2, src: F64x2, scale: Int) {
}

public actual fun vscatterqps128(slice: MutPtr, k: UByte, offsets: I64x2, src: F32x4, scale: Int) {
}

public actual fun vpscatterdd256(slice: MutPtr, k: UByte, offsets: I32x8, src: I32x8, scale: Int) {
}

public actual fun vpscatterdq256(slice: MutPtr, k: UByte, offsets: I32x4, src: I64x4, scale: Int) {
}

public actual fun vscatterdpd256(slice: MutPtr, k: UByte, offsets: I32x4, src: F64x4, scale: Int) {
}

public actual fun vscatterdps256(slice: MutPtr, k: UByte, offsets: I32x8, src: F32x8, scale: Int) {
}

public actual fun vpscatterqd256(slice: MutPtr, k: UByte, offsets: I64x4, src: I32x4, scale: Int) {
}

public actual fun vpscatterqq256(slice: MutPtr, k: UByte, offsets: I64x4, src: I64x4, scale: Int) {
}

public actual fun vscatterqpd256(slice: MutPtr, k: UByte, offsets: I64x4, src: F64x4, scale: Int) {
}

public actual fun vscatterqps256(slice: MutPtr, k: UByte, offsets: I64x4, src: F32x4, scale: Int) {
}

public actual fun vpgatherdd128(src: I32x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): I32x4 =
    null as I32x4

public actual fun vpgatherdq128(src: I64x2, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): I64x2 =
    null as I64x2

public actual fun vgatherdpd128(src: F64x2, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): F64x2 =
    null as F64x2

public actual fun vgatherdps128(src: F32x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): F32x4 =
    null as F32x4

public actual fun vpgatherqd128(src: I32x4, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): I32x4 =
    null as I32x4

public actual fun vpgatherqq128(src: I64x2, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): I64x2 =
    null as I64x2

public actual fun vgatherqpd128(src: F64x2, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): F64x2 =
    null as F64x2

public actual fun vgatherqps128(src: F32x4, slice: ConstPtr, offsets: I64x2, k: UByte, scale: Int): F32x4 =
    null as F32x4

public actual fun vpgatherdd256(src: I32x8, slice: ConstPtr, offsets: I32x8, k: UByte, scale: Int): I32x8 =
    null as I32x8

public actual fun vpgatherdq256(src: I64x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): I64x4 =
    null as I64x4

public actual fun vgatherdpd256(src: F64x4, slice: ConstPtr, offsets: I32x4, k: UByte, scale: Int): F64x4 =
    null as F64x4

public actual fun vgatherdps256(src: F32x8, slice: ConstPtr, offsets: I32x8, k: UByte, scale: Int): F32x8 =
    null as F32x8

public actual fun vpgatherqd256(src: I32x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): I32x4 =
    null as I32x4

public actual fun vpgatherqq256(src: I64x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): I64x4 =
    null as I64x4

public actual fun vgatherqpd256(src: F64x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): F64x4 =
    null as F64x4

public actual fun vgatherqps256(src: F32x4, slice: ConstPtr, offsets: I64x4, k: UByte, scale: Int): F32x4 =
    null as F32x4

public actual fun vcmpss(a: M128, b: M128, op: Int, m: Byte, sae: Int): Byte =
    0

public actual fun vcmpsd(a: M128d, b: M128d, op: Int, m: Byte, sae: Int): Byte =
    0

public actual fun vcmpps(a: F32x16, b: F32x16, op: Int, m: Short, sae: Int): Short =
    0

public actual fun vcmpps256(a: F32x8, b: F32x8, op: Int, m: Byte): Byte =
    0

public actual fun vcmpps128(a: F32x4, b: F32x4, op: Int, m: Byte): Byte =
    0

public actual fun vcmppd(a: F64x8, b: F64x8, op: Int, m: Byte, sae: Int): Byte =
    0

public actual fun vcmppd256(a: F64x4, b: F64x4, op: Int, m: Byte): Byte =
    0

public actual fun vcmppd128(a: F64x2, b: F64x2, op: Int, m: Byte): Byte =
    0

public actual fun vpsllvd(a: I32x16, b: I32x16): I32x16 =
    null as I32x16

public actual fun vpsrlvd(a: I32x16, b: I32x16): I32x16 =
    null as I32x16

public actual fun vpsllvq(a: I64x8, b: I64x8): I64x8 =
    null as I64x8

public actual fun vpsrlvq(a: I64x8, b: I64x8): I64x8 =
    null as I64x8

public actual fun vpslld(a: I32x16, count: I32x4): I32x16 =
    null as I32x16

public actual fun vpsrld(a: I32x16, count: I32x4): I32x16 =
    null as I32x16

public actual fun vpsllq(a: I64x8, count: I64x2): I64x8 =
    null as I64x8

public actual fun vpsrlq(a: I64x8, count: I64x2): I64x8 =
    null as I64x8

public actual fun vpsrad(a: I32x16, count: I32x4): I32x16 =
    null as I32x16

public actual fun vpsraq(a: I64x8, count: I64x2): I64x8 =
    null as I64x8

public actual fun vpsraq256(a: I64x4, count: I64x2): I64x4 =
    null as I64x4

public actual fun vpsraq128(a: I64x2, count: I64x2): I64x2 =
    null as I64x2

public actual fun vpsravd(a: I32x16, count: I32x16): I32x16 =
    null as I32x16

public actual fun vpsravq(a: I64x8, count: I64x8): I64x8 =
    null as I64x8

public actual fun vpsravq256(a: I64x4, count: I64x4): I64x4 =
    null as I64x4

public actual fun vpsravq128(a: I64x2, count: I64x2): I64x2 =
    null as I64x2

public actual fun vpermilps(a: F32x16, b: I32x16): F32x16 =
    null as F32x16

public actual fun vpermilpd(a: F64x8, b: I64x8): F64x8 =
    null as F64x8

public actual fun vpermd(a: I32x16, idx: I32x16): I32x16 =
    null as I32x16

public actual fun vpermq(a: I64x8, idx: I64x8): I64x8 =
    null as I64x8

public actual fun vpermq256(a: I64x4, idx: I64x4): I64x4 =
    null as I64x4

public actual fun vpermps(a: F32x16, idx: I32x16): F32x16 =
    null as F32x16

public actual fun vpermpd(a: F64x8, idx: I64x8): F64x8 =
    null as F64x8

public actual fun vpermpd256(a: F64x4, idx: I64x4): F64x4 =
    null as F64x4

public actual fun vpermi2d(a: I32x16, idx: I32x16, b: I32x16): I32x16 =
    null as I32x16

public actual fun vpermi2d256(a: I32x8, idx: I32x8, b: I32x8): I32x8 =
    null as I32x8

public actual fun vpermi2d128(a: I32x4, idx: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vpermi2q(a: I64x8, idx: I64x8, b: I64x8): I64x8 =
    null as I64x8

public actual fun vpermi2q256(a: I64x4, idx: I64x4, b: I64x4): I64x4 =
    null as I64x4

public actual fun vpermi2q128(a: I64x2, idx: I64x2, b: I64x2): I64x2 =
    null as I64x2

public actual fun vpermi2ps(a: F32x16, idx: I32x16, b: F32x16): F32x16 =
    null as F32x16

public actual fun vpermi2ps256(a: F32x8, idx: I32x8, b: F32x8): F32x8 =
    null as F32x8

public actual fun vpermi2ps128(a: F32x4, idx: I32x4, b: F32x4): F32x4 =
    null as F32x4

public actual fun vpermi2pd(a: F64x8, idx: I64x8, b: F64x8): F64x8 =
    null as F64x8

public actual fun vpermi2pd256(a: F64x4, idx: I64x4, b: F64x4): F64x4 =
    null as F64x4

public actual fun vpermi2pd128(a: F64x2, idx: I64x2, b: F64x2): F64x2 =
    null as F64x2

public actual fun vpcompressd(a: I32x16, src: I32x16, mask: UShort): I32x16 =
    null as I32x16

public actual fun vpcompressd256(a: I32x8, src: I32x8, mask: UByte): I32x8 =
    null as I32x8

public actual fun vpcompressd128(a: I32x4, src: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun vpcompressq(a: I64x8, src: I64x8, mask: UByte): I64x8 =
    null as I64x8

public actual fun vpcompressq256(a: I64x4, src: I64x4, mask: UByte): I64x4 =
    null as I64x4

public actual fun vpcompressq128(a: I64x2, src: I64x2, mask: UByte): I64x2 =
    null as I64x2

public actual fun vcompressps(a: F32x16, src: F32x16, mask: UShort): F32x16 =
    null as F32x16

public actual fun vcompressps256(a: F32x8, src: F32x8, mask: UByte): F32x8 =
    null as F32x8

public actual fun vcompressps128(a: F32x4, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vcompresspd(a: F64x8, src: F64x8, mask: UByte): F64x8 =
    null as F64x8

public actual fun vcompresspd256(a: F64x4, src: F64x4, mask: UByte): F64x4 =
    null as F64x4

public actual fun vcompresspd128(a: F64x2, src: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun vcompressstored(mem: MutPtr, data: I32x16, mask: UShort) {
}

public actual fun vcompressstored256(mem: MutPtr, data: I32x8, mask: UByte) {
}

public actual fun vcompressstored128(mem: MutPtr, data: I32x4, mask: UByte) {
}

public actual fun vcompressstoreq(mem: MutPtr, data: I64x8, mask: UByte) {
}

public actual fun vcompressstoreq256(mem: MutPtr, data: I64x4, mask: UByte) {
}

public actual fun vcompressstoreq128(mem: MutPtr, data: I64x2, mask: UByte) {
}

public actual fun vcompressstoreps(mem: MutPtr, data: F32x16, mask: UShort) {
}

public actual fun vcompressstoreps256(mem: MutPtr, data: F32x8, mask: UByte) {
}

public actual fun vcompressstoreps128(mem: MutPtr, data: F32x4, mask: UByte) {
}

public actual fun vcompressstorepd(mem: MutPtr, data: F64x8, mask: UByte) {
}

public actual fun vcompressstorepd256(mem: MutPtr, data: F64x4, mask: UByte) {
}

public actual fun vcompressstorepd128(mem: MutPtr, data: F64x2, mask: UByte) {
}

public actual fun vpexpandd(a: I32x16, src: I32x16, mask: UShort): I32x16 =
    null as I32x16

public actual fun vpexpandd256(a: I32x8, src: I32x8, mask: UByte): I32x8 =
    null as I32x8

public actual fun vpexpandd128(a: I32x4, src: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun vpexpandq(a: I64x8, src: I64x8, mask: UByte): I64x8 =
    null as I64x8

public actual fun vpexpandq256(a: I64x4, src: I64x4, mask: UByte): I64x4 =
    null as I64x4

public actual fun vpexpandq128(a: I64x2, src: I64x2, mask: UByte): I64x2 =
    null as I64x2

public actual fun vexpandps(a: F32x16, src: F32x16, mask: UShort): F32x16 =
    null as F32x16

public actual fun vexpandps256(a: F32x8, src: F32x8, mask: UByte): F32x8 =
    null as F32x8

public actual fun vexpandps128(a: F32x4, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vexpandpd(a: F64x8, src: F64x8, mask: UByte): F64x8 =
    null as F64x8

public actual fun vexpandpd256(a: F64x4, src: F64x4, mask: UByte): F64x4 =
    null as F64x4

public actual fun vexpandpd128(a: F64x2, src: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun vaddss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    null as F32x4

public actual fun vaddsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    null as F64x2

public actual fun vsubss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    null as F32x4

public actual fun vsubsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    null as F64x2

public actual fun vmulss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    null as F32x4

public actual fun vmulsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    null as F64x2

public actual fun vdivss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    null as F32x4

public actual fun vdivsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    null as F64x2

public actual fun vmaxss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, sae: Int): F32x4 =
    null as F32x4

public actual fun vmaxsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vminss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, sae: Int): F32x4 =
    null as F32x4

public actual fun vminsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vsqrtss(a: M128, b: M128, src: M128, mask: UByte, rounding: Int): M128 =
    M128()

public actual fun vsqrtsd(a: M128d, b: M128d, src: M128d, mask: UByte, rounding: Int): M128d =
    M128d()

public actual fun vgetexpss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, sae: Int): F32x4 =
    null as F32x4

public actual fun vgetexpsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vgetmantss(a: F32x4, b: F32x4, mantissas: Int, src: F32x4, m: UByte, sae: Int): F32x4 =
    null as F32x4

public actual fun vgetmantsd(a: F64x2, b: F64x2, mantissas: Int, src: F64x2, m: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vrsqrt14ss(a: F32x4, b: F32x4, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vrsqrt14sd(a: F64x2, b: F64x2, src: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun vrcp14ss(a: F32x4, b: F32x4, src: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun vrcp14sd(a: F64x2, b: F64x2, src: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun vrndscaless(a: F32x4, b: F32x4, src: F32x4, mask: UByte, imm8: Int, sae: Int): F32x4 =
    null as F32x4

public actual fun vrndscalesd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, imm8: Int, sae: Int): F64x2 =
    null as F64x2

public actual fun vscalefss(a: F32x4, b: F32x4, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    null as F32x4

public actual fun vscalefsd(a: F64x2, b: F64x2, src: F64x2, mask: UByte, rounding: Int): F64x2 =
    null as F64x2

public actual fun vfmaddssround(a: Float, b: Float, c: Float, rounding: Int): Float =
    corearchX86Avx512fVfmaddssround(a, b, c, rounding)

public actual fun vfmaddsdround(a: Double, b: Double, c: Double, rounding: Int): Double =
    corearchX86Avx512fVfmaddsdround(a, b, c, rounding)

public actual fun vfixupimmss(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte, sae: Int): F32x4 =
    null as F32x4

public actual fun vfixupimmsd(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vfixupimmssz(a: F32x4, b: F32x4, c: I32x4, imm8: Int, mask: UByte, sae: Int): F32x4 =
    null as F32x4

public actual fun vfixupimmsdz(a: F64x2, b: F64x2, c: I64x2, imm8: Int, mask: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vcvtss2sd(a: F64x2, b: F32x4, src: F64x2, mask: UByte, sae: Int): F64x2 =
    null as F64x2

public actual fun vcvtsd2ss(a: F32x4, b: F64x2, src: F32x4, mask: UByte, rounding: Int): F32x4 =
    null as F32x4

public actual fun vcvtss2si(a: F32x4, rounding: Int): Int =
    0

public actual fun vcvtss2usi(a: F32x4, rounding: Int): UInt =
    0u

public actual fun vcvtsd2si(a: F64x2, rounding: Int): Int =
    0

public actual fun vcvtsd2usi(a: F64x2, rounding: Int): UInt =
    0u

public actual fun vcvtsi2ss(a: F32x4, b: Int, rounding: Int): F32x4 =
    null as F32x4

public actual fun vcvtusi2ss(a: F32x4, b: UInt, rounding: Int): F32x4 =
    null as F32x4

public actual fun vcvttss2si(a: F32x4, rounding: Int): Int =
    0

public actual fun vcvttss2usi(a: F32x4, rounding: Int): UInt =
    0u

public actual fun vcvttsd2si(a: F64x2, rounding: Int): Int =
    0

public actual fun vcvttsd2usi(a: F64x2, rounding: Int): UInt =
    0u

public actual fun vcomiss(a: F32x4, b: F32x4, imm8: Int, sae: Int): Int =
    0

public actual fun vcomisd(a: F64x2, b: F64x2, imm8: Int, sae: Int): Int =
    0

public actual fun expandloadd128(memAddr: ConstPtr, a: I32x4, mask: UByte): I32x4 =
    null as I32x4

public actual fun expandloadq128(memAddr: ConstPtr, a: I64x2, mask: UByte): I64x2 =
    null as I64x2

public actual fun expandloadps128(memAddr: ConstPtr, a: F32x4, mask: UByte): F32x4 =
    null as F32x4

public actual fun expandloadpd128(memAddr: ConstPtr, a: F64x2, mask: UByte): F64x2 =
    null as F64x2

public actual fun expandloadd256(memAddr: ConstPtr, a: I32x8, mask: UByte): I32x8 =
    null as I32x8

public actual fun expandloadq256(memAddr: ConstPtr, a: I64x4, mask: UByte): I64x4 =
    null as I64x4

public actual fun expandloadps256(memAddr: ConstPtr, a: F32x8, mask: UByte): F32x8 =
    null as F32x8

public actual fun expandloadpd256(memAddr: ConstPtr, a: F64x4, mask: UByte): F64x4 =
    null as F64x4

public actual fun expandloadd512(memAddr: ConstPtr, a: I32x16, mask: UShort): I32x16 =
    null as I32x16

public actual fun expandloadq512(memAddr: ConstPtr, a: I64x8, mask: UByte): I64x8 =
    null as I64x8

public actual fun expandloadps512(memAddr: ConstPtr, a: F32x16, mask: UShort): F32x16 =
    null as F32x16

public actual fun expandloadpd512(memAddr: ConstPtr, a: F64x8, mask: UByte): F64x8 =
    null as F64x8
