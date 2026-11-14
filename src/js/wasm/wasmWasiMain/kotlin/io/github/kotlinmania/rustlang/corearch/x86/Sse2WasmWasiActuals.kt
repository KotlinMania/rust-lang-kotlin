@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchX86Sse2Pause")
private external fun wasiCorearchX86Sse2Pause(): Unit

public actual fun pause(): Unit =
    wasiCorearchX86Sse2Pause()

public actual fun clflush(p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Clflush")
}

@kotlin.wasm.WasmImport("corearch", "corearchX86Sse2Lfence")
private external fun wasiCorearchX86Sse2Lfence(): Unit

public actual fun lfence(): Unit =
    wasiCorearchX86Sse2Lfence()

@kotlin.wasm.WasmImport("corearch", "corearchX86Sse2Mfence")
private external fun wasiCorearchX86Sse2Mfence(): Unit

public actual fun mfence(): Unit =
    wasiCorearchX86Sse2Mfence()

public actual fun pavgb(a: U8x16, b: U8x16): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Pavgb")

public actual fun pavgw(a: U16x8, b: U16x8): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Pavgw")

public actual fun pmaddwd(a: I16x8, b: I16x8): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Pmaddwd")

public actual fun pmulhw(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Pmulhw")

public actual fun pmulhuw(a: U16x8, b: U16x8): U16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Pmulhuw")

public actual fun psadbw(a: U8x16, b: U8x16): U64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psadbw")

public actual fun psllw(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psllw")

public actual fun pslld(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Pslld")

public actual fun psllq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psllq")

public actual fun psraw(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psraw")

public actual fun psrad(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psrad")

public actual fun psrlw(a: I16x8, count: I16x8): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psrlw")

public actual fun psrld(a: I32x4, count: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psrld")

public actual fun psrlq(a: I64x2, count: I64x2): I64x2 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Psrlq")

public actual fun cvtps2dq(a: M128): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvtps2dq")

public actual fun maskmovdqu(a: I8x16, mask: I8x16, memAddr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Maskmovdqu")
}

public actual fun packsswb(a: I16x8, b: I16x8): I8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Packsswb")

public actual fun packssdw(a: I32x4, b: I32x4): I16x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Packssdw")

public actual fun packuswb(a: I16x8, b: I16x8): U8x16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Packuswb")

public actual fun maxsd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Maxsd")

public actual fun maxpd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Maxpd")

public actual fun minsd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Minsd")

public actual fun minpd(a: M128d, b: M128d): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Minpd")

public actual fun cmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cmpsd")

public actual fun cmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cmppd")

public actual fun comieqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Comieqsd")

public actual fun comiltsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Comiltsd")

public actual fun comilesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Comilesd")

public actual fun comigtsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Comigtsd")

public actual fun comigesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Comigesd")

public actual fun comineqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Comineqsd")

public actual fun ucomieqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Ucomieqsd")

public actual fun ucomiltsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Ucomiltsd")

public actual fun ucomilesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Ucomilesd")

public actual fun ucomigtsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Ucomigtsd")

public actual fun ucomigesd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Ucomigesd")

public actual fun ucomineqsd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Ucomineqsd")

public actual fun cvtpd2dq(a: M128d): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvtpd2dq")

public actual fun cvtsd2si(a: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvtsd2si")

public actual fun cvtsd2ss(a: M128, b: M128d): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvtsd2ss")

public actual fun cvttpd2dq(a: M128d): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvttpd2dq")

public actual fun cvttsd2si(a: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvttsd2si")

public actual fun cvttps2dq(a: M128): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86Sse2Cvttps2dq")
