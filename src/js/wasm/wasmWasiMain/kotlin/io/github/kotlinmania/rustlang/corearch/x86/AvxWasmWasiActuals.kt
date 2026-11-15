@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I32x8
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.I64x4
import io.github.kotlinmania.rustlang.simd.I8x32
import io.github.kotlinmania.rustlang.x86.M128
import io.github.kotlinmania.rustlang.x86.M128d
import io.github.kotlinmania.rustlang.x86.M256
import io.github.kotlinmania.rustlang.x86.M256d

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun roundpd256(a: M256d, b: Int): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxRoundpd256")

public actual fun roundps256(a: M256, b: Int): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxRoundps256")

public actual fun vdpps(a: M256, b: M256, imm8: Byte): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVdpps")

public actual fun vcmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcmppd")

public actual fun vcmppd256(a: M256d, b: M256d, imm8: UByte): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcmppd256")

public actual fun vcmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcmpps")

public actual fun vcmpps256(a: M256, b: M256, imm8: UByte): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcmpps256")

public actual fun vcmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcmpsd")

public actual fun vcmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcmpss")

public actual fun vcvtps2dq(a: M256): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcvtps2dq")

public actual fun vcvttpd2dq(a: M256d): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcvttpd2dq")

public actual fun vcvtpd2dq(a: M256d): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcvtpd2dq")

public actual fun vcvttps2dq(a: M256): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVcvttps2dq")

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86AvxVzeroall")
private external fun wasiRustlangCorearchX86AvxVzeroall(): Unit

public actual fun vzeroall(): Unit =
    wasiRustlangCorearchX86AvxVzeroall()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86AvxVzeroupper")
private external fun wasiRustlangCorearchX86AvxVzeroupper(): Unit

public actual fun vzeroupper(): Unit =
    wasiRustlangCorearchX86AvxVzeroupper()

public actual fun vpermilps256(a: M256, b: I32x8): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVpermilps256")

public actual fun vpermilps(a: M128, b: I32x4): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVpermilps")

public actual fun vpermilpd256(a: M256d, b: I64x4): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVpermilpd256")

public actual fun vpermilpd(a: M128d, b: I64x2): M128d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVpermilpd")

public actual fun vlddqu(memAddr: ConstPtr): I8x32 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVlddqu")

public actual fun vrcpps(a: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVrcpps")

public actual fun vrsqrtps(a: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVrsqrtps")

public actual fun ptestnzc256(a: I64x4, b: I64x4): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxPtestnzc256")

public actual fun vtestzpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestzpd256")

public actual fun vtestcpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestcpd256")

public actual fun vtestnzcpd256(a: M256d, b: M256d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestnzcpd256")

public actual fun vtestnzcpd(a: M128d, b: M128d): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestnzcpd")

public actual fun vtestzps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestzps256")

public actual fun vtestcps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestcps256")

public actual fun vtestnzcps256(a: M256, b: M256): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestnzcps256")

public actual fun vtestnzcps(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVtestnzcps")

public actual fun vminps(a: M256, b: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVminps")

public actual fun vmaxps(a: M256, b: M256): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVmaxps")

public actual fun vminpd(a: M256d, b: M256d): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVminpd")

public actual fun vmaxpd(a: M256d, b: M256d): M256d =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86AvxVmaxpd")
