@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x32

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun pavgb(a: U8x32, b: U8x32): U8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pavgb")

public actual fun pavgw(a: U16x16, b: U16x16): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pavgw")

public actual fun pmaddwd(a: I16x16, b: I16x16): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pmaddwd")

public actual fun pmaddubsw(a: U8x32, b: I8x32): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pmaddubsw")

public actual fun mpsadbw(a: U8x32, b: U8x32, imm8: Byte): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Mpsadbw")

public actual fun pmulhuw(a: U16x16, b: U16x16): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pmulhuw")

public actual fun pmulhw(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pmulhw")

public actual fun pmulhrsw(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pmulhrsw")

public actual fun packsswb(a: I16x16, b: I16x16): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Packsswb")

public actual fun packssdw(a: I32x8, b: I32x8): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Packssdw")

public actual fun packuswb(a: I16x16, b: I16x16): U8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Packuswb")

public actual fun packusdw(a: I32x8, b: I32x8): U16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Packusdw")

public actual fun psadbw(a: U8x32, b: U8x32): U64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psadbw")

public actual fun psignb(a: I8x32, b: I8x32): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psignb")

public actual fun psignw(a: I16x16, b: I16x16): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psignw")

public actual fun psignd(a: I32x8, b: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psignd")

public actual fun psllw(a: I16x16, count: I16x8): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psllw")

public actual fun pslld(a: I32x8, count: I32x4): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pslld")

public actual fun psllq(a: I64x4, count: I64x2): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psllq")

public actual fun psllvd(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psllvd")

public actual fun psllvd256(a: I32x8, count: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psllvd256")

public actual fun psllvq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psllvq")

public actual fun psllvq256(a: I64x4, count: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psllvq256")

public actual fun psraw(a: I16x16, count: I16x8): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psraw")

public actual fun psrad(a: I32x8, count: I32x4): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrad")

public actual fun psravd(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psravd")

public actual fun psravd256(a: I32x8, count: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psravd256")

public actual fun psrlw(a: I16x16, count: I16x8): I16x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrlw")

public actual fun psrld(a: I32x8, count: I32x4): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrld")

public actual fun psrlq(a: I64x4, count: I64x2): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrlq")

public actual fun psrlvd(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrlvd")

public actual fun psrlvd256(a: I32x8, count: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrlvd256")

public actual fun psrlvq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrlvq")

public actual fun psrlvq256(a: I64x4, count: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Psrlvq256")

public actual fun pshufb(a: U8x32, b: U8x32): U8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pshufb")

public actual fun permd(a: U32x8, b: U32x8): U32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Permd")

public actual fun permps(a: M256, b: I32x8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Permps")

public actual fun pgatherdd(src: I32x4, slice: ConstPtr, offsets: I32x4, mask: I32x4, scale: Byte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherdd")

public actual fun vpgatherdd(src: I32x8, slice: ConstPtr, offsets: I32x8, mask: I32x8, scale: Byte): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherdd")

public actual fun pgatherdq(src: I64x2, slice: ConstPtr, offsets: I32x4, mask: I64x2, scale: Byte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherdq")

public actual fun vpgatherdq(src: I64x4, slice: ConstPtr, offsets: I32x4, mask: I64x4, scale: Byte): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherdq")

public actual fun pgatherqd(src: I32x4, slice: ConstPtr, offsets: I64x2, mask: I32x4, scale: Byte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherqd")

public actual fun vpgatherqd(src: I32x4, slice: ConstPtr, offsets: I64x4, mask: I32x4, scale: Byte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherqd")

public actual fun pgatherqq(src: I64x2, slice: ConstPtr, offsets: I64x2, mask: I64x2, scale: Byte): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherqq")

public actual fun vpgatherqq(src: I64x4, slice: ConstPtr, offsets: I64x4, mask: I64x4, scale: Byte): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherqq")

public actual fun pgatherdpd(src: M128d, slice: ConstPtr, offsets: I32x4, mask: M128d, scale: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherdpd")

public actual fun vpgatherdpd(src: M256d, slice: ConstPtr, offsets: I32x4, mask: M256d, scale: Byte): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherdpd")

public actual fun pgatherqpd(src: M128d, slice: ConstPtr, offsets: I64x2, mask: M128d, scale: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherqpd")

public actual fun vpgatherqpd(src: M256d, slice: ConstPtr, offsets: I64x4, mask: M256d, scale: Byte): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherqpd")

public actual fun pgatherdps(src: M128, slice: ConstPtr, offsets: I32x4, mask: M128, scale: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherdps")

public actual fun vpgatherdps(src: M256, slice: ConstPtr, offsets: I32x8, mask: M256, scale: Byte): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherdps")

public actual fun pgatherqps(src: M128, slice: ConstPtr, offsets: I64x2, mask: M128, scale: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Pgatherqps")

public actual fun vpgatherqps(src: M128, slice: ConstPtr, offsets: I64x4, mask: M128, scale: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Avx2Vpgatherqps")
