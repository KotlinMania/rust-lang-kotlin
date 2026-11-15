@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.simd.I16x16
import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I32x8
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I64x4
import io.github.kotlinmania.rustlang.simd.I8x32
import io.github.kotlinmania.rustlang.simd.U16x16
import io.github.kotlinmania.rustlang.simd.U32x8
import io.github.kotlinmania.rustlang.simd.U64x4
import io.github.kotlinmania.rustlang.simd.U8x32
import io.github.kotlinmania.rustlang.x86.M128
import io.github.kotlinmania.rustlang.x86.M128d
import io.github.kotlinmania.rustlang.x86.M256
import io.github.kotlinmania.rustlang.x86.M256d

public actual fun pavgb(a: U8x32, b: U8x32): U8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pavgw(a: U16x16, b: U16x16): U16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmaddwd(a: I16x16, b: I16x16): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmaddubsw(a: U8x32, b: I8x32): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mpsadbw(a: U8x32, b: U8x32, imm8: Byte): U16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmulhuw(a: U16x16, b: U16x16): U16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmulhw(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pmulhrsw(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun packsswb(a: I16x16, b: I16x16): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun packssdw(a: I32x8, b: I32x8): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun packuswb(a: I16x16, b: I16x16): U8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun packusdw(a: I32x8, b: I32x8): U16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psadbw(a: U8x32, b: U8x32): U64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psignb(a: I8x32, b: I8x32): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psignw(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psignd(a: I32x8, b: I32x8): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllw(a: I16x16, count: I16x8): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pslld(a: I32x8, count: I32x4): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllq(a: I64x4, count: I64x2): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllvd(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllvd256(a: I32x8, count: I32x8): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllvq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psllvq256(a: I64x4, count: I64x4): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psraw(a: I16x16, count: I16x8): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrad(a: I32x8, count: I32x4): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psravd(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psravd256(a: I32x8, count: I32x8): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlw(a: I16x16, count: I16x8): I16x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrld(a: I32x8, count: I32x4): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlq(a: I64x4, count: I64x2): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlvd(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlvd256(a: I32x8, count: I32x8): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlvq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun psrlvq256(a: I64x4, count: I64x4): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pshufb(a: U8x32, b: U8x32): U8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun permd(a: U32x8, b: U32x8): U32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun permps(a: M256, b: I32x8): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherdd(src: I32x4, slice: ConstPtr, offsets: I32x4, mask: I32x4, scale: Byte): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherdd(src: I32x8, slice: ConstPtr, offsets: I32x8, mask: I32x8, scale: Byte): I32x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherdq(src: I64x2, slice: ConstPtr, offsets: I32x4, mask: I64x2, scale: Byte): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherdq(src: I64x4, slice: ConstPtr, offsets: I32x4, mask: I64x4, scale: Byte): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherqd(src: I32x4, slice: ConstPtr, offsets: I64x2, mask: I32x4, scale: Byte): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherqd(src: I32x4, slice: ConstPtr, offsets: I64x4, mask: I32x4, scale: Byte): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherqq(src: I64x2, slice: ConstPtr, offsets: I64x2, mask: I64x2, scale: Byte): I64x2 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherqq(src: I64x4, slice: ConstPtr, offsets: I64x4, mask: I64x4, scale: Byte): I64x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherdpd(src: M128d, slice: ConstPtr, offsets: I32x4, mask: M128d, scale: Byte): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherdpd(src: M256d, slice: ConstPtr, offsets: I32x4, mask: M256d, scale: Byte): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherqpd(src: M128d, slice: ConstPtr, offsets: I64x2, mask: M128d, scale: Byte): M128d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherqpd(src: M256d, slice: ConstPtr, offsets: I64x4, mask: M256d, scale: Byte): M256d =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherdps(src: M128, slice: ConstPtr, offsets: I32x4, mask: M128, scale: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherdps(src: M256, slice: ConstPtr, offsets: I32x8, mask: M256, scale: Byte): M256 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pgatherqps(src: M128, slice: ConstPtr, offsets: I64x2, mask: M128, scale: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpgatherqps(src: M128, slice: ConstPtr, offsets: I64x4, mask: M128, scale: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")
