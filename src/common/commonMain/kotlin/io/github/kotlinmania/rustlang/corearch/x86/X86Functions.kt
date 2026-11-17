package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.simd.F16x16
import io.github.kotlinmania.rustlang.corearch.simd.F16x32
import io.github.kotlinmania.rustlang.corearch.simd.F16x8
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.F64x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x32
import io.github.kotlinmania.rustlang.corearch.simd.U8x64

public fun fromSimdLong(simd: M128i): Simd<Long> = transmuteWithLayouts<M128i, Simd<Long>>(simd, M128i.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

public fun fromSimdFloat(simd: M128): Simd<Float> = transmuteWithLayouts<M128, Simd<Float>>(simd, M128.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

public fun fromSimdDouble(simd: M128d): Simd<Double> = transmuteWithLayouts<M128d, Simd<Double>>(simd, M128d.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public fun fromSimdLong(simd: M256i): Simd<Long> = transmuteWithLayouts<M256i, Simd<Long>>(simd, M256i.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

public fun fromSimdFloat(simd: M256): Simd<Float> = transmuteWithLayouts<M256, Simd<Float>>(simd, M256.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

public fun fromSimdDouble(simd: M256d): Simd<Double> = transmuteWithLayouts<M256d, Simd<Double>>(simd, M256d.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public fun fromSimdLong(simd: M512i): Simd<Long> = transmuteWithLayouts<M512i, Simd<Long>>(simd, M512i.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

public fun fromSimdFloat(simd: M512): Simd<Float> = transmuteWithLayouts<M512, Simd<Float>>(simd, M512.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

public fun fromSimdDouble(simd: M512d): Simd<Double> = transmuteWithLayouts<M512d, Simd<Double>>(simd, M512d.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public fun fromSimdUShort(simd: M128bh): Simd<UShort> = transmuteWithLayouts<M128bh, Simd<UShort>>(simd, M128bh.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

public fun fromSimdUShort(simd: M256bh): Simd<UShort> = transmuteWithLayouts<M256bh, Simd<UShort>>(simd, M256bh.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

public fun fromSimdUShort(simd: M512bh): Simd<UShort> = transmuteWithLayouts<M512bh, Simd<UShort>>(simd, M512bh.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

public fun fromSimdF16(simd: M128h): Simd<F16> = transmuteWithLayouts<M128h, Simd<F16>>(simd, M128h.typeLayout(), UnresolvedTypeLayout<Simd<F16>>())

public fun fromSimdF16(simd: M256h): Simd<F16> = transmuteWithLayouts<M256h, Simd<F16>>(simd, M256h.typeLayout(), UnresolvedTypeLayout<Simd<F16>>())

public fun fromSimdF16(simd: M512h): Simd<F16> = transmuteWithLayouts<M512h, Simd<F16>>(simd, M512h.typeLayout(), UnresolvedTypeLayout<Simd<F16>>())

public fun U8x16.asM128i(): M128i = transmuteWithLayouts<U8x16, M128i>(this, UnresolvedTypeLayout<U8x16>(), M128i.typeLayout())

public fun U16x8.asM128i(): M128i = transmuteWithLayouts<U16x8, M128i>(this, UnresolvedTypeLayout<U16x8>(), M128i.typeLayout())

public fun U32x4.asM128i(): M128i = transmuteWithLayouts<U32x4, M128i>(this, UnresolvedTypeLayout<U32x4>(), M128i.typeLayout())

public fun U64x2.asM128i(): M128i = transmuteWithLayouts<U64x2, M128i>(this, UnresolvedTypeLayout<U64x2>(), M128i.typeLayout())

public fun I8x16.asM128i(): M128i = transmuteWithLayouts<I8x16, M128i>(this, UnresolvedTypeLayout<I8x16>(), M128i.typeLayout())

public fun I16x8.asM128i(): M128i = transmuteWithLayouts<I16x8, M128i>(this, UnresolvedTypeLayout<I16x8>(), M128i.typeLayout())

public fun I32x4.asM128i(): M128i = transmuteWithLayouts<I32x4, M128i>(this, UnresolvedTypeLayout<I32x4>(), M128i.typeLayout())

public fun I64x2.asM128i(): M128i = transmuteWithLayouts<I64x2, M128i>(this, UnresolvedTypeLayout<I64x2>(), M128i.typeLayout())

public fun U8x32.asM256i(): M256i = transmuteWithLayouts<U8x32, M256i>(this, UnresolvedTypeLayout<U8x32>(), M256i.typeLayout())

public fun U16x16.asM256i(): M256i = transmuteWithLayouts<U16x16, M256i>(this, UnresolvedTypeLayout<U16x16>(), M256i.typeLayout())

public fun U32x8.asM256i(): M256i = transmuteWithLayouts<U32x8, M256i>(this, UnresolvedTypeLayout<U32x8>(), M256i.typeLayout())

public fun U64x4.asM256i(): M256i = transmuteWithLayouts<U64x4, M256i>(this, UnresolvedTypeLayout<U64x4>(), M256i.typeLayout())

public fun I8x32.asM256i(): M256i = transmuteWithLayouts<I8x32, M256i>(this, UnresolvedTypeLayout<I8x32>(), M256i.typeLayout())

public fun I16x16.asM256i(): M256i = transmuteWithLayouts<I16x16, M256i>(this, UnresolvedTypeLayout<I16x16>(), M256i.typeLayout())

public fun I32x8.asM256i(): M256i = transmuteWithLayouts<I32x8, M256i>(this, UnresolvedTypeLayout<I32x8>(), M256i.typeLayout())

public fun I64x4.asM256i(): M256i = transmuteWithLayouts<I64x4, M256i>(this, UnresolvedTypeLayout<I64x4>(), M256i.typeLayout())

public fun U8x64.asM512i(): M512i = transmuteWithLayouts<U8x64, M512i>(this, UnresolvedTypeLayout<U8x64>(), M512i.typeLayout())

public fun U16x32.asM512i(): M512i = transmuteWithLayouts<U16x32, M512i>(this, UnresolvedTypeLayout<U16x32>(), M512i.typeLayout())

public fun U32x16.asM512i(): M512i = transmuteWithLayouts<U32x16, M512i>(this, UnresolvedTypeLayout<U32x16>(), M512i.typeLayout())

public fun U64x8.asM512i(): M512i = transmuteWithLayouts<U64x8, M512i>(this, UnresolvedTypeLayout<U64x8>(), M512i.typeLayout())

public fun I8x64.asM512i(): M512i = transmuteWithLayouts<I8x64, M512i>(this, UnresolvedTypeLayout<I8x64>(), M512i.typeLayout())

public fun I16x32.asM512i(): M512i = transmuteWithLayouts<I16x32, M512i>(this, UnresolvedTypeLayout<I16x32>(), M512i.typeLayout())

public fun I32x16.asM512i(): M512i = transmuteWithLayouts<I32x16, M512i>(this, UnresolvedTypeLayout<I32x16>(), M512i.typeLayout())

public fun I64x8.asM512i(): M512i = transmuteWithLayouts<I64x8, M512i>(this, UnresolvedTypeLayout<I64x8>(), M512i.typeLayout())

public fun F32x4.asM128(): M128 = transmuteWithLayouts<F32x4, M128>(this, UnresolvedTypeLayout<F32x4>(), M128.typeLayout())

public fun F64x2.asM128d(): M128d = transmuteWithLayouts<F64x2, M128d>(this, UnresolvedTypeLayout<F64x2>(), M128d.typeLayout())

public fun F32x8.asM256(): M256 = transmuteWithLayouts<F32x8, M256>(this, UnresolvedTypeLayout<F32x8>(), M256.typeLayout())

public fun F64x4.asM256d(): M256d = transmuteWithLayouts<F64x4, M256d>(this, UnresolvedTypeLayout<F64x4>(), M256d.typeLayout())

public fun F32x16.asM512(): M512 = transmuteWithLayouts<F32x16, M512>(this, UnresolvedTypeLayout<F32x16>(), M512.typeLayout())

public fun F64x8.asM512d(): M512d = transmuteWithLayouts<F64x8, M512d>(this, UnresolvedTypeLayout<F64x8>(), M512d.typeLayout())

public fun U16x8.asM128bh(): M128bh = transmuteWithLayouts<U16x8, M128bh>(this, UnresolvedTypeLayout<U16x8>(), M128bh.typeLayout())

public fun U32x4.asM128bh(): M128bh = transmuteWithLayouts<U32x4, M128bh>(this, UnresolvedTypeLayout<U32x4>(), M128bh.typeLayout())

public fun I16x8.asM128bh(): M128bh = transmuteWithLayouts<I16x8, M128bh>(this, UnresolvedTypeLayout<I16x8>(), M128bh.typeLayout())

public fun I32x4.asM128bh(): M128bh = transmuteWithLayouts<I32x4, M128bh>(this, UnresolvedTypeLayout<I32x4>(), M128bh.typeLayout())

public fun U16x16.asM256bh(): M256bh = transmuteWithLayouts<U16x16, M256bh>(this, UnresolvedTypeLayout<U16x16>(), M256bh.typeLayout())

public fun U32x8.asM256bh(): M256bh = transmuteWithLayouts<U32x8, M256bh>(this, UnresolvedTypeLayout<U32x8>(), M256bh.typeLayout())

public fun I16x16.asM256bh(): M256bh = transmuteWithLayouts<I16x16, M256bh>(this, UnresolvedTypeLayout<I16x16>(), M256bh.typeLayout())

public fun I32x8.asM256bh(): M256bh = transmuteWithLayouts<I32x8, M256bh>(this, UnresolvedTypeLayout<I32x8>(), M256bh.typeLayout())

public fun U16x32.asM512bh(): M512bh = transmuteWithLayouts<U16x32, M512bh>(this, UnresolvedTypeLayout<U16x32>(), M512bh.typeLayout())

public fun U32x16.asM512bh(): M512bh = transmuteWithLayouts<U32x16, M512bh>(this, UnresolvedTypeLayout<U32x16>(), M512bh.typeLayout())

public fun I16x32.asM512bh(): M512bh = transmuteWithLayouts<I16x32, M512bh>(this, UnresolvedTypeLayout<I16x32>(), M512bh.typeLayout())

public fun I32x16.asM512bh(): M512bh = transmuteWithLayouts<I32x16, M512bh>(this, UnresolvedTypeLayout<I32x16>(), M512bh.typeLayout())

public fun F16x8.asM128h(): M128h = transmuteWithLayouts<F16x8, M128h>(this, UnresolvedTypeLayout<F16x8>(), M128h.typeLayout())

public fun F16x16.asM256h(): M256h = transmuteWithLayouts<F16x16, M256h>(this, UnresolvedTypeLayout<F16x16>(), M256h.typeLayout())

public fun F16x32.asM512h(): M512h = transmuteWithLayouts<F16x32, M512h>(this, UnresolvedTypeLayout<F16x32>(), M512h.typeLayout())
