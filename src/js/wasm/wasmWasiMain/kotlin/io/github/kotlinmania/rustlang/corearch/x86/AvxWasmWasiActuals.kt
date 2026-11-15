@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x32

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun roundpd256(a: M256d, b: Int): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxRoundpd256")

public actual fun roundps256(a: M256, b: Int): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxRoundps256")

public actual fun vdpps(a: M256, b: M256, imm8: Byte): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVdpps")

public actual fun vcmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcmppd")

public actual fun vcmppd256(a: M256d, b: M256d, imm8: UByte): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcmppd256")

public actual fun vcmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcmpps")

public actual fun vcmpps256(a: M256, b: M256, imm8: UByte): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcmpps256")

public actual fun vcmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcmpsd")

public actual fun vcmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcmpss")

public actual fun vcvtps2dq(a: M256): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcvtps2dq")

public actual fun vcvttpd2dq(a: M256d): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcvttpd2dq")

public actual fun vcvtpd2dq(a: M256d): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcvtpd2dq")

public actual fun vcvttps2dq(a: M256): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVcvttps2dq")

@kotlin.wasm.WasmImport("kmcore", "corearchX86AvxVzeroall")
private external fun wasiCorearchX86AvxVzeroall(): Unit

public actual fun vzeroall(): Unit =
    wasiCorearchX86AvxVzeroall()

@kotlin.wasm.WasmImport("kmcore", "corearchX86AvxVzeroupper")
private external fun wasiCorearchX86AvxVzeroupper(): Unit

public actual fun vzeroupper(): Unit =
    wasiCorearchX86AvxVzeroupper()

public actual fun vpermilps256(a: M256, b: I32x8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVpermilps256")

public actual fun vpermilps(a: M128, b: I32x4): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVpermilps")

public actual fun vpermilpd256(a: M256d, b: I64x4): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVpermilpd256")

public actual fun vpermilpd(a: M128d, b: I64x2): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVpermilpd")

public actual fun vlddqu(memAddr: ConstPtr): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVlddqu")

public actual fun vrcpps(a: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVrcpps")

public actual fun vrsqrtps(a: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVrsqrtps")

public actual fun ptestnzc256(a: I64x4, b: I64x4): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxPtestnzc256")

public actual fun vtestzpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestzpd256")

public actual fun vtestcpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestcpd256")

public actual fun vtestnzcpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestnzcpd256")

public actual fun vtestnzcpd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestnzcpd")

public actual fun vtestzps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestzps256")

public actual fun vtestcps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestcps256")

public actual fun vtestnzcps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestnzcps256")

public actual fun vtestnzcps(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVtestnzcps")

public actual fun vminps(a: M256, b: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVminps")

public actual fun vmaxps(a: M256, b: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVmaxps")

public actual fun vminpd(a: M256d, b: M256d): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVminpd")

public actual fun vmaxpd(a: M256d, b: M256d): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxVmaxpd")
