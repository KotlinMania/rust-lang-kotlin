package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.transmute
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

public fun fromSimdLong(simd: M128i): Simd<Long> = transmute(simd)

public fun fromSimdFloat(simd: M128): Simd<Float> = transmute(simd)

public fun fromSimdDouble(simd: M128d): Simd<Double> = transmute(simd)

public fun fromSimdLong(simd: M256i): Simd<Long> = transmute(simd)

public fun fromSimdFloat(simd: M256): Simd<Float> = transmute(simd)

public fun fromSimdDouble(simd: M256d): Simd<Double> = transmute(simd)

public fun fromSimdLong(simd: M512i): Simd<Long> = transmute(simd)

public fun fromSimdFloat(simd: M512): Simd<Float> = transmute(simd)

public fun fromSimdDouble(simd: M512d): Simd<Double> = transmute(simd)

public fun fromSimdUShort(simd: M128bh): Simd<UShort> = transmute(simd)

public fun fromSimdUShort(simd: M256bh): Simd<UShort> = transmute(simd)

public fun fromSimdUShort(simd: M512bh): Simd<UShort> = transmute(simd)

public fun fromSimdF16(simd: M128h): Simd<F16> = transmute(simd)

public fun fromSimdF16(simd: M256h): Simd<F16> = transmute(simd)

public fun fromSimdF16(simd: M512h): Simd<F16> = transmute(simd)

public fun U8x16.asM128i(): M128i = transmute(this)

public fun U16x8.asM128i(): M128i = transmute(this)

public fun U32x4.asM128i(): M128i = transmute(this)

public fun U64x2.asM128i(): M128i = transmute(this)

public fun I8x16.asM128i(): M128i = transmute(this)

public fun I16x8.asM128i(): M128i = transmute(this)

public fun I32x4.asM128i(): M128i = transmute(this)

public fun I64x2.asM128i(): M128i = transmute(this)

public fun U8x32.asM256i(): M256i = transmute(this)

public fun U16x16.asM256i(): M256i = transmute(this)

public fun U32x8.asM256i(): M256i = transmute(this)

public fun U64x4.asM256i(): M256i = transmute(this)

public fun I8x32.asM256i(): M256i = transmute(this)

public fun I16x16.asM256i(): M256i = transmute(this)

public fun I32x8.asM256i(): M256i = transmute(this)

public fun I64x4.asM256i(): M256i = transmute(this)

public fun U8x64.asM512i(): M512i = transmute(this)

public fun U16x32.asM512i(): M512i = transmute(this)

public fun U32x16.asM512i(): M512i = transmute(this)

public fun U64x8.asM512i(): M512i = transmute(this)

public fun I8x64.asM512i(): M512i = transmute(this)

public fun I16x32.asM512i(): M512i = transmute(this)

public fun I32x16.asM512i(): M512i = transmute(this)

public fun I64x8.asM512i(): M512i = transmute(this)

public fun F32x4.asM128(): M128 = transmute(this)

public fun F64x2.asM128d(): M128d = transmute(this)

public fun F32x8.asM256(): M256 = transmute(this)

public fun F64x4.asM256d(): M256d = transmute(this)

public fun F32x16.asM512(): M512 = transmute(this)

public fun F64x8.asM512d(): M512d = transmute(this)

public fun U16x8.asM128bh(): M128bh = transmute(this)

public fun U32x4.asM128bh(): M128bh = transmute(this)

public fun I16x8.asM128bh(): M128bh = transmute(this)

public fun I32x4.asM128bh(): M128bh = transmute(this)

public fun U16x16.asM256bh(): M256bh = transmute(this)

public fun U32x8.asM256bh(): M256bh = transmute(this)

public fun I16x16.asM256bh(): M256bh = transmute(this)

public fun I32x8.asM256bh(): M256bh = transmute(this)

public fun U16x32.asM512bh(): M512bh = transmute(this)

public fun U32x16.asM512bh(): M512bh = transmute(this)

public fun I16x32.asM512bh(): M512bh = transmute(this)

public fun I32x16.asM512bh(): M512bh = transmute(this)

public fun F16x8.asM128h(): M128h = transmute(this)

public fun F16x16.asM256h(): M256h = transmute(this)

public fun F16x32.asM512h(): M512h = transmute(this)
