@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

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
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse2Clflush
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse2Lfence
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse2Mfence
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse2Pause
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun pause() {
    corearchX86Sse2Pause()
}

public actual fun clflush(p: ConstPtr) {
    corearchX86Sse2Clflush(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun lfence() {
    corearchX86Sse2Lfence()
}

public actual fun mfence() {
    corearchX86Sse2Mfence()
}

public actual fun pavgb(a: U8x16, b: U8x16): U8x16 =
    null as U8x16

public actual fun pavgw(a: U16x8, b: U16x8): U16x8 =
    null as U16x8

public actual fun pmaddwd(a: I16x8, b: I16x8): I32x4 =
    null as I32x4

public actual fun pmulhw(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun pmulhuw(a: U16x8, b: U16x8): U16x8 =
    null as U16x8

public actual fun psadbw(a: U8x16, b: U8x16): U64x2 =
    null as U64x2

public actual fun psllw(a: I16x8, count: I16x8): I16x8 =
    null as I16x8

public actual fun pslld(a: I32x4, count: I32x4): I32x4 =
    null as I32x4

public actual fun psllq(a: I64x2, count: I64x2): I64x2 =
    null as I64x2

public actual fun psraw(a: I16x8, count: I16x8): I16x8 =
    null as I16x8

public actual fun psrad(a: I32x4, count: I32x4): I32x4 =
    null as I32x4

public actual fun psrlw(a: I16x8, count: I16x8): I16x8 =
    null as I16x8

public actual fun psrld(a: I32x4, count: I32x4): I32x4 =
    null as I32x4

public actual fun psrlq(a: I64x2, count: I64x2): I64x2 =
    null as I64x2

public actual fun cvtps2dq(a: M128): I32x4 =
    null as I32x4

public actual fun maskmovdqu(a: I8x16, mask: I8x16, memAddr: MutPtr) {
}

public actual fun packsswb(a: I16x8, b: I16x8): I8x16 =
    null as I8x16

public actual fun packssdw(a: I32x4, b: I32x4): I16x8 =
    null as I16x8

public actual fun packuswb(a: I16x8, b: I16x8): U8x16 =
    null as U8x16

public actual fun maxsd(a: M128d, b: M128d): M128d =
    M128d()

public actual fun maxpd(a: M128d, b: M128d): M128d =
    M128d()

public actual fun minsd(a: M128d, b: M128d): M128d =
    M128d()

public actual fun minpd(a: M128d, b: M128d): M128d =
    M128d()

public actual fun cmpsd(a: M128d, b: M128d, imm8: Byte): M128d =
    M128d()

public actual fun cmppd(a: M128d, b: M128d, imm8: Byte): M128d =
    M128d()

public actual fun comieqsd(a: M128d, b: M128d): Int =
    0

public actual fun comiltsd(a: M128d, b: M128d): Int =
    0

public actual fun comilesd(a: M128d, b: M128d): Int =
    0

public actual fun comigtsd(a: M128d, b: M128d): Int =
    0

public actual fun comigesd(a: M128d, b: M128d): Int =
    0

public actual fun comineqsd(a: M128d, b: M128d): Int =
    0

public actual fun ucomieqsd(a: M128d, b: M128d): Int =
    0

public actual fun ucomiltsd(a: M128d, b: M128d): Int =
    0

public actual fun ucomilesd(a: M128d, b: M128d): Int =
    0

public actual fun ucomigtsd(a: M128d, b: M128d): Int =
    0

public actual fun ucomigesd(a: M128d, b: M128d): Int =
    0

public actual fun ucomineqsd(a: M128d, b: M128d): Int =
    0

public actual fun cvtpd2dq(a: M128d): I32x4 =
    null as I32x4

public actual fun cvtsd2si(a: M128d): Int =
    0

public actual fun cvtsd2ss(a: M128, b: M128d): M128 =
    M128()

public actual fun cvttpd2dq(a: M128d): I32x4 =
    null as I32x4

public actual fun cvttsd2si(a: M128d): Int =
    0

public actual fun cvttps2dq(a: M128): I32x4 =
    null as I32x4
