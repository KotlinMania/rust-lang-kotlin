@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86AvxVzeroall
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86AvxVzeroupper

public actual fun roundpd256(a: M256d, b: Int): M256d =
    M256d()

public actual fun roundps256(a: M256, b: Int): M256 =
    M256()

public actual fun vdpps(a: M256, b: M256, imm8: Byte): M256 =
    M256()

public actual fun vcmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    M128d()

public actual fun vcmppd256(a: M256d, b: M256d, imm8: UByte): M256d =
    M256d()

public actual fun vcmpps(a: M128, b: M128, imm8: Byte): M128 =
    M128()

public actual fun vcmpps256(a: M256, b: M256, imm8: UByte): M256 =
    M256()

public actual fun vcmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    M128d()

public actual fun vcmpss(a: M128, b: M128, imm8: Byte): M128 =
    M128()

public actual fun vcvtps2dq(a: M256): I32x8 =
    null as I32x8

public actual fun vcvttpd2dq(a: M256d): I32x4 =
    null as I32x4

public actual fun vcvtpd2dq(a: M256d): I32x4 =
    null as I32x4

public actual fun vcvttps2dq(a: M256): I32x8 =
    null as I32x8

public actual fun vzeroall() {
    corearchX86AvxVzeroall()
}

public actual fun vzeroupper() {
    corearchX86AvxVzeroupper()
}

public actual fun vpermilps256(a: M256, b: I32x8): M256 =
    M256()

public actual fun vpermilps(a: M128, b: I32x4): M128 =
    M128()

public actual fun vpermilpd256(a: M256d, b: I64x4): M256d =
    M256d()

public actual fun vpermilpd(a: M128d, b: I64x2): M128d =
    M128d()

public actual fun vlddqu(memAddr: ConstPtr): I8x32 =
    null as I8x32

public actual fun vrcpps(a: M256): M256 =
    M256()

public actual fun vrsqrtps(a: M256): M256 =
    M256()

public actual fun ptestnzc256(a: I64x4, b: I64x4): Int =
    0

public actual fun vtestzpd256(a: M256d, b: M256d): Int =
    0

public actual fun vtestcpd256(a: M256d, b: M256d): Int =
    0

public actual fun vtestnzcpd256(a: M256d, b: M256d): Int =
    0

public actual fun vtestnzcpd(a: M128d, b: M128d): Int =
    0

public actual fun vtestzps256(a: M256, b: M256): Int =
    0

public actual fun vtestcps256(a: M256, b: M256): Int =
    0

public actual fun vtestnzcps256(a: M256, b: M256): Int =
    0

public actual fun vtestnzcps(a: M128, b: M128): Int =
    0

public actual fun vminps(a: M256, b: M256): M256 =
    M256()

public actual fun vmaxps(a: M256, b: M256): M256 =
    M256()

public actual fun vminpd(a: M256d, b: M256d): M256d =
    M256d()

public actual fun vmaxpd(a: M256d, b: M256d): M256d =
    M256d()
