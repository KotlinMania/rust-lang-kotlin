// port-lint: tests x86/sse2.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf64
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import kotlin.experimental.inv

// Streaming SIMD Extensions 2 (SSE2)

public val NAN: Double = Double.NaN

public fun testMmClflush() {
    val x = 0u.toUByte()
    run {
        mmClflush(addrOf(x))
    }
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
public fun testMmLfence() {
    mmLfence()
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
public fun testMmMfence() {
    mmMfence()
}

public fun testMmAddEpi8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mmAddEpi8(a, b)
    val e = mmSetrEpi8(16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46)
    assertEqM128i(r, e)
}

public fun testMmAddEpi8Overflow() {
    val a = mmSet1Epi8(0x7F)
    val b = mmSet1Epi8(1)
    val r = mmAddEpi8(a, b)
    assertEqM128i(r, mmSet1Epi8((-128).toByte()))
}

public fun testMmAddEpi16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetrEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmAddEpi16(a, b)
    val e = mmSetrEpi16(8, 10, 12, 14, 16, 18, 20, 22)
    assertEqM128i(r, e)
}

public fun testMmAddEpi32() {
    val a = mmSetrEpi32(0, 1, 2, 3)
    val b = mmSetrEpi32(4, 5, 6, 7)
    val r = mmAddEpi32(a, b)
    val e = mmSetrEpi32(4, 6, 8, 10)
    assertEqM128i(r, e)
}

public fun testMmAddEpi64() {
    val a = mmSetrEpi64x(0, 1)
    val b = mmSetrEpi64x(2, 3)
    val r = mmAddEpi64(a, b)
    val e = mmSetrEpi64x(2, 4)
    assertEqM128i(r, e)
}

public fun testMmAddsEpi8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mmAddsEpi8(a, b)
    val e = mmSetrEpi8(16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46)
    assertEqM128i(r, e)
}

public fun testMmAddsEpi8SaturatePositive() {
    val a = mmSet1Epi8(0x7F)
    val b = mmSet1Epi8(1)
    val r = mmAddsEpi8(a, b)
    assertEqM128i(r, a)
}

public fun testMmAddsEpi8SaturateNegative() {
    val a = mmSet1Epi8((-0x80).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val r = mmAddsEpi8(a, b)
    assertEqM128i(r, a)
}

public fun testMmAddsEpi16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetrEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmAddsEpi16(a, b)
    val e = mmSetrEpi16(8, 10, 12, 14, 16, 18, 20, 22)
    assertEqM128i(r, e)
}

public fun testMmAddsEpi16SaturatePositive() {
    val a = mmSet1Epi16(0x7FFF)
    val b = mmSet1Epi16(1)
    val r = mmAddsEpi16(a, b)
    assertEqM128i(r, a)
}

public fun testMmAddsEpi16SaturateNegative() {
    val a = mmSet1Epi16((-0x8000).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val r = mmAddsEpi16(a, b)
    assertEqM128i(r, a)
}

public fun testMmAddsEpu8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mmAddsEpu8(a, b)
    val e = mmSetrEpi8(16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46)
    assertEqM128i(r, e)
}

public fun testMmAddsEpu8Saturate() {
    val a = mmSet1Epi8((0.inv()).toByte())
    val b = mmSet1Epi8(1)
    val r = mmAddsEpu8(a, b)
    assertEqM128i(r, a)
}

public fun testMmAddsEpu16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetrEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmAddsEpu16(a, b)
    val e = mmSetrEpi16(8, 10, 12, 14, 16, 18, 20, 22)
    assertEqM128i(r, e)
}

public fun testMmAddsEpu16Saturate() {
    val a = mmSet1Epi16((0.inv()).toShort())
    val b = mmSet1Epi16(1)
    val r = mmAddsEpu16(a, b)
    assertEqM128i(r, a)
}

public fun testMmAvgEpu8() {
    val (a, b) = Pair(mmSet1Epi8(3), mmSet1Epi8(9))
    val r = mmAvgEpu8(a, b)
    assertEqM128i(r, mmSet1Epi8(6))
}

public fun testMmAvgEpu16() {
    val (a, b) = Pair(mmSet1Epi16(3), mmSet1Epi16(9))
    val r = mmAvgEpu16(a, b)
    assertEqM128i(r, mmSet1Epi16(6))
}

public fun testMmMaddEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetrEpi16(9, 10, 11, 12, 13, 14, 15, 16)
    val r = mmMaddEpi16(a, b)
    val e = mmSetrEpi32(29, 81, 149, 233)
    assertEqM128i(r, e)
    // Test large values.
    // MIN*MIN+MIN*MIN will overflow into i32.Min.
    val a1 = mmSetrEpi16(Short.MAX_VALUE, Short.MAX_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MAX_VALUE, 0, 0)
    val b1 = mmSetrEpi16(Short.MAX_VALUE, Short.MAX_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MAX_VALUE, Short.MIN_VALUE, 0, 0)
    val r1 = mmMaddEpi16(a1, b1)
    val e1 = mmSetrEpi32(0x7FFE0002, Int.MIN_VALUE, -0x7FFF0000, 0)
    assertEqM128i(r1, e1)
}

public fun testMmMaxEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((-1).toShort())
    val r = mmMaxEpi16(a, b)
    assertEqM128i(r, a)
}

public fun testMmMaxEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8((0.inv()).toByte())
    val r = mmMaxEpu8(a, b)
    assertEqM128i(r, b)
}

public fun testMmMinEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((-1).toShort())
    val r = mmMinEpi16(a, b)
    assertEqM128i(r, b)
}

public fun testMmMinEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8((0.inv()).toByte())
    val r = mmMinEpu8(a, b)
    assertEqM128i(r, a)
}

public fun testMmMulhiEpi16() {
    val (a, b) = Pair(mmSet1Epi16(1000), mmSet1Epi16((-1001).toShort()))
    val r = mmMulhiEpi16(a, b)
    assertEqM128i(r, mmSet1Epi16((-16).toShort()))
}

public fun testMmMulhiEpu16() {
    val (a, b) = Pair(mmSet1Epi16(1000), mmSet1Epi16(1001))
    val r = mmMulhiEpu16(a, b)
    assertEqM128i(r, mmSet1Epi16(15))
}

public fun testMmMulloEpi16() {
    val (a, b) = Pair(mmSet1Epi16(1000), mmSet1Epi16((-1001).toShort()))
    val r = mmMulloEpi16(a, b)
    assertEqM128i(r, mmSet1Epi16((-17960).toShort()))
}

public fun testMmMulEpu32() {
    val a = mmSetrEpi64x(1000000000, 1 shl 34)
    val b = mmSetrEpi64x(1000000000, 1 shl 35)
    val r = mmMulEpu32(a, b)
    val e = mmSetrEpi64x(1000000000 * 1000000000, 0)
    assertEqM128i(r, e)
}

public fun testMmSadEpu8() {
    val a = mmSetrEpi8((255u.toUByte()).toByte(), (254u.toUByte()).toByte(), (253u.toUByte()).toByte(), (252u.toUByte()).toByte(), 1, 2, 3, 4, (155u.toUByte()).toByte(), (154u.toUByte()).toByte(), (153u.toUByte()).toByte(), (152u.toUByte()).toByte(), 1, 2, 3, 4)
    val b = mmSetrEpi8(0, 0, 0, 0, 2, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2)
    val r = mmSadEpu8(a, b)
    val e = mmSetrEpi64x(1020, 614)
    assertEqM128i(r, e)
}

public fun testMmSubEpi8() {
    val (a, b) = Pair(mmSet1Epi8(5), mmSet1Epi8(6))
    val r = mmSubEpi8(a, b)
    assertEqM128i(r, mmSet1Epi8((-1).toByte()))
}

public fun testMmSubEpi16() {
    val (a, b) = Pair(mmSet1Epi16(5), mmSet1Epi16(6))
    val r = mmSubEpi16(a, b)
    assertEqM128i(r, mmSet1Epi16((-1).toShort()))
}

public fun testMmSubEpi32() {
    val (a, b) = Pair(mmSet1Epi32(5), mmSet1Epi32(6))
    val r = mmSubEpi32(a, b)
    assertEqM128i(r, mmSet1Epi32(-1))
}

public fun testMmSubEpi64() {
    val (a, b) = Pair(mmSet1Epi64x(5L), mmSet1Epi64x(6L))
    val r = mmSubEpi64(a, b)
    assertEqM128i(r, mmSet1Epi64x(-1L))
}

public fun testMmSubsEpi8() {
    val (a, b) = Pair(mmSet1Epi8(5), mmSet1Epi8(2))
    val r = mmSubsEpi8(a, b)
    assertEqM128i(r, mmSet1Epi8(3))
}

public fun testMmSubsEpi8SaturatePositive() {
    val a = mmSet1Epi8(0x7F)
    val b = mmSet1Epi8((-1).toByte())
    val r = mmSubsEpi8(a, b)
    assertEqM128i(r, a)
}

public fun testMmSubsEpi8SaturateNegative() {
    val a = mmSet1Epi8((-0x80).toByte())
    val b = mmSet1Epi8(1)
    val r = mmSubsEpi8(a, b)
    assertEqM128i(r, a)
}

public fun testMmSubsEpi16() {
    val (a, b) = Pair(mmSet1Epi16(5), mmSet1Epi16(2))
    val r = mmSubsEpi16(a, b)
    assertEqM128i(r, mmSet1Epi16(3))
}

public fun testMmSubsEpi16SaturatePositive() {
    val a = mmSet1Epi16(0x7FFF)
    val b = mmSet1Epi16((-1).toShort())
    val r = mmSubsEpi16(a, b)
    assertEqM128i(r, a)
}

public fun testMmSubsEpi16SaturateNegative() {
    val a = mmSet1Epi16((-0x8000).toShort())
    val b = mmSet1Epi16(1)
    val r = mmSubsEpi16(a, b)
    assertEqM128i(r, a)
}

public fun testMmSubsEpu8() {
    val (a, b) = Pair(mmSet1Epi8(5), mmSet1Epi8(2))
    val r = mmSubsEpu8(a, b)
    assertEqM128i(r, mmSet1Epi8(3))
}

public fun testMmSubsEpu8Saturate() {
    val a = mmSet1Epi8(0)
    val b = mmSet1Epi8(1)
    val r = mmSubsEpu8(a, b)
    assertEqM128i(r, a)
}

public fun testMmSubsEpu16() {
    val (a, b) = Pair(mmSet1Epi16(5), mmSet1Epi16(2))
    val r = mmSubsEpu16(a, b)
    assertEqM128i(r, mmSet1Epi16(3))
}

public fun testMmSubsEpu16Saturate() {
    val a = mmSet1Epi16(0)
    val b = mmSet1Epi16(1)
    val r = mmSubsEpu16(a, b)
    assertEqM128i(r, a)
}

public fun testMmSlliSi128() {
    val a = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mmSlliSi128(a, 1)
    val e = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r, e)
    val a1 = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r1 = mmSlliSi128(a1, 15)
    val e1 = mmSetrEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    assertEqM128i(r1, e1)
    val a2 = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r2 = mmSlliSi128(a2, 16)
    assertEqM128i(r2, mmSet1Epi8(0))
}

public fun testMmSlliEpi16() {
    val a = mmSetrEpi16(0xCC, (-0xCC).toShort(), 0xDD, (-0xDD).toShort(), 0xEE, (-0xEE).toShort(), 0xFF, (-0xFF).toShort())
    val r = mmSlliEpi16(a, 4)
    assertEqM128i(r, mmSetrEpi16(0xCC0, (-0xCC0).toShort(), 0xDD0, (-0xDD0).toShort(), 0xEE0, (-0xEE0).toShort(), 0xFF0, (-0xFF0).toShort()))
    val r1 = mmSlliEpi16(a, 16)
    assertEqM128i(r1, mmSet1Epi16(0))
}

public fun testMmSllEpi16() {
    val a = mmSetrEpi16(0xCC, (-0xCC).toShort(), 0xDD, (-0xDD).toShort(), 0xEE, (-0xEE).toShort(), 0xFF, (-0xFF).toShort())
    val r = mmSllEpi16(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetrEpi16(0xCC0, (-0xCC0).toShort(), 0xDD0, (-0xDD0).toShort(), 0xEE0, (-0xEE0).toShort(), 0xFF0, (-0xFF0).toShort()))
    val r1 = mmSllEpi16(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSllEpi16(a, mmSetEpi64x(0L, 16L))
    assertEqM128i(r2, mmSet1Epi16(0))
    val r3 = mmSllEpi16(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSet1Epi16(0))
}

public fun testMmSlliEpi32() {
    val a = mmSetrEpi32(0xEEEE, -0xEEEE, 0xFFFF, -0xFFFF)
    val r = mmSlliEpi32(a, 4)
    assertEqM128i(r, mmSetrEpi32(0xEEEE0, -0xEEEE0, 0xFFFF0, -0xFFFF0))
    val r1 = mmSlliEpi32(a, 32)
    assertEqM128i(r1, mmSet1Epi32(0))
}

public fun testMmSllEpi32() {
    val a = mmSetrEpi32(0xEEEE, -0xEEEE, 0xFFFF, -0xFFFF)
    val r = mmSllEpi32(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetrEpi32(0xEEEE0, -0xEEEE0, 0xFFFF0, -0xFFFF0))
    val r1 = mmSllEpi32(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSllEpi32(a, mmSetEpi64x(0L, 32L))
    assertEqM128i(r2, mmSet1Epi32(0))
    val r3 = mmSllEpi32(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSet1Epi32(0))
}

public fun testMmSlliEpi64() {
    val a = mmSetEpi64x(0xFFFFFFFFL, -0xFFFFFFFFL)
    val r = mmSlliEpi64(a, 4)
    assertEqM128i(r, mmSetEpi64x(0xFFFFFFFF0L, -0xFFFFFFFF0L))
    val r1 = mmSlliEpi64(a, 64)
    assertEqM128i(r1, mmSet1Epi64x(0L))
}

public fun testMmSllEpi64() {
    val a = mmSetEpi64x(0xFFFFFFFFL, -0xFFFFFFFFL)
    val r = mmSllEpi64(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetEpi64x(0xFFFFFFFF0L, -0xFFFFFFFF0L))
    val r1 = mmSllEpi64(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSllEpi64(a, mmSetEpi64x(0L, 64L))
    assertEqM128i(r2, mmSet1Epi64x(0L))
    val r3 = mmSllEpi64(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSet1Epi64x(0L))
}

public fun testMmSraiEpi16() {
    val a = mmSetrEpi16(0xCC, (-0xCC).toShort(), 0xDD, (-0xDD).toShort(), 0xEE, (-0xEE).toShort(), 0xFF, (-0xFF).toShort())
    val r = mmSraiEpi16(a, 4)
    assertEqM128i(r, mmSetrEpi16(0xC, (-0xD).toShort(), 0xD, (-0xE).toShort(), 0xE, (-0xF).toShort(), 0xF, (-0x10).toShort()))
    val r1 = mmSraiEpi16(a, 16)
    assertEqM128i(r1, mmSetrEpi16(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort()))
}

public fun testMmSraEpi16() {
    val a = mmSetrEpi16(0xCC, (-0xCC).toShort(), 0xDD, (-0xDD).toShort(), 0xEE, (-0xEE).toShort(), 0xFF, (-0xFF).toShort())
    val r = mmSraEpi16(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetrEpi16(0xC, (-0xD).toShort(), 0xD, (-0xE).toShort(), 0xE, (-0xF).toShort(), 0xF, (-0x10).toShort()))
    val r1 = mmSraEpi16(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSraEpi16(a, mmSetEpi64x(0L, 16L))
    assertEqM128i(r2, mmSetrEpi16(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort()))
    val r3 = mmSraEpi16(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSetrEpi16(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort()))
}

public fun testMmSraiEpi32() {
    val a = mmSetrEpi32(0xEEEE, -0xEEEE, 0xFFFF, -0xFFFF)
    val r = mmSraiEpi32(a, 4)
    assertEqM128i(r, mmSetrEpi32(0xEEE, -0xEEF, 0xFFF, -0x1000))
    val r1 = mmSraiEpi32(a, 32)
    assertEqM128i(r1, mmSetrEpi32(0, -1, 0, -1))
}

public fun testMmSraEpi32() {
    val a = mmSetrEpi32(0xEEEE, -0xEEEE, 0xFFFF, -0xFFFF)
    val r = mmSraEpi32(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetrEpi32(0xEEE, -0xEEF, 0xFFF, -0x1000))
    val r1 = mmSraEpi32(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSraEpi32(a, mmSetEpi64x(0L, 32L))
    assertEqM128i(r2, mmSetrEpi32(0, -1, 0, -1))
    val r3 = mmSraEpi32(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSetrEpi32(0, -1, 0, -1))
}

public fun testMmSrliSi128() {
    val a = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mmSrliSi128(a, 1)
    val e = mmSetrEpi8(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0)
    assertEqM128i(r, e)
    val a1 = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r1 = mmSrliSi128(a1, 15)
    val e1 = mmSetrEpi8(16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e1)
    val a2 = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r2 = mmSrliSi128(a2, 16)
    assertEqM128i(r2, mmSet1Epi8(0))
}

public fun testMmSrliEpi16() {
    val a = mmSetrEpi16(0xCC, (-0xCC).toShort(), 0xDD, (-0xDD).toShort(), 0xEE, (-0xEE).toShort(), 0xFF, (-0xFF).toShort())
    val r = mmSrliEpi16(a, 4)
    assertEqM128i(r, mmSetrEpi16(0xC, 0xFF3, 0xD, 0xFF2, 0xE, 0xFF1, 0xF, 0xFF0))
    val r1 = mmSrliEpi16(a, 16)
    assertEqM128i(r1, mmSet1Epi16(0))
}

public fun testMmSrlEpi16() {
    val a = mmSetrEpi16(0xCC, (-0xCC).toShort(), 0xDD, (-0xDD).toShort(), 0xEE, (-0xEE).toShort(), 0xFF, (-0xFF).toShort())
    val r = mmSrlEpi16(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetrEpi16(0xC, 0xFF3, 0xD, 0xFF2, 0xE, 0xFF1, 0xF, 0xFF0))
    val r1 = mmSrlEpi16(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSrlEpi16(a, mmSetEpi64x(0L, 16L))
    assertEqM128i(r2, mmSet1Epi16(0))
    val r3 = mmSrlEpi16(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSet1Epi16(0))
}

public fun testMmSrliEpi32() {
    val a = mmSetrEpi32(0xEEEE, -0xEEEE, 0xFFFF, -0xFFFF)
    val r = mmSrliEpi32(a, 4)
    assertEqM128i(r, mmSetrEpi32(0xEEE, 0xFFFF111, 0xFFF, 0xFFFF000))
    val r1 = mmSrliEpi32(a, 32)
    assertEqM128i(r1, mmSet1Epi32(0))
}

public fun testMmSrlEpi32() {
    val a = mmSetrEpi32(0xEEEE, -0xEEEE, 0xFFFF, -0xFFFF)
    val r = mmSrlEpi32(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetrEpi32(0xEEE, 0xFFFF111, 0xFFF, 0xFFFF000))
    val r1 = mmSrlEpi32(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSrlEpi32(a, mmSetEpi64x(0L, 32L))
    assertEqM128i(r2, mmSet1Epi32(0))
    val r3 = mmSrlEpi32(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSet1Epi32(0))
}

public fun testMmSrliEpi64() {
    val a = mmSetEpi64x(0xFFFFFFFFL, -0xFFFFFFFFL)
    val r = mmSrliEpi64(a, 4)
    assertEqM128i(r, mmSetEpi64x(0xFFFFFFFL, 0xFFFFFFFF0000000L))
    val r1 = mmSrliEpi64(a, 64)
    assertEqM128i(r1, mmSet1Epi64x(0L))
}

public fun testMmSrlEpi64() {
    val a = mmSetEpi64x(0xFFFFFFFFL, -0xFFFFFFFFL)
    val r = mmSrlEpi64(a, mmSetEpi64x(0L, 4L))
    assertEqM128i(r, mmSetEpi64x(0xFFFFFFFL, 0xFFFFFFFF0000000L))
    val r1 = mmSrlEpi64(a, mmSetEpi64x(4L, 0L))
    assertEqM128i(r1, a)
    val r2 = mmSrlEpi64(a, mmSetEpi64x(0L, 64L))
    assertEqM128i(r2, mmSet1Epi64x(0L))
    val r3 = mmSrlEpi64(a, mmSetEpi64x(0L, Long.MAX_VALUE))
    assertEqM128i(r3, mmSet1Epi64x(0L))
}

public fun testMmAndSi128() {
    val a = mmSet1Epi8(5)
    val b = mmSet1Epi8(3)
    val r = mmAndSi128(a, b)
    assertEqM128i(r, mmSet1Epi8(1))
}

public fun testMmAndnotSi128() {
    val a = mmSet1Epi8(5)
    val b = mmSet1Epi8(3)
    val r = mmAndnotSi128(a, b)
    assertEqM128i(r, mmSet1Epi8(2))
}

public fun testMmOrSi128() {
    val a = mmSet1Epi8(5)
    val b = mmSet1Epi8(3)
    val r = mmOrSi128(a, b)
    assertEqM128i(r, mmSet1Epi8(7))
}

public fun testMmXorSi128() {
    val a = mmSet1Epi8(5)
    val b = mmSet1Epi8(3)
    val r = mmXorSi128(a, b)
    assertEqM128i(r, mmSet1Epi8(6))
}

public fun testMmCmpeqEpi8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(15, 14, 2, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmCmpeqEpi8(a, b)
    assertEqM128i(r, mmSetrEpi8(0, 0, (0xFFu.toUByte()).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
}

public fun testMmCmpeqEpi16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetrEpi16(7, 6, 2, 4, 3, 2, 1, 0)
    val r = mmCmpeqEpi16(a, b)
    assertEqM128i(r, mmSetrEpi16(0, 0, (0.inv()).toShort(), 0, 0, 0, 0, 0))
}

public fun testMmCmpeqEpi32() {
    val a = mmSetrEpi32(0, 1, 2, 3)
    val b = mmSetrEpi32(3, 2, 2, 0)
    val r = mmCmpeqEpi32(a, b)
    assertEqM128i(r, mmSetrEpi32(0, 0, 0.inv(), 0))
}

public fun testMmCmpgtEpi8() {
    val a = mmSetEpi8(5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val b = mmSet1Epi8(0)
    val r = mmCmpgtEpi8(a, b)
    val e = mmSetEpi8((0.inv()).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmCmpgtEpi16() {
    val a = mmSetEpi16(5, 0, 0, 0, 0, 0, 0, 0)
    val b = mmSet1Epi16(0)
    val r = mmCmpgtEpi16(a, b)
    val e = mmSetEpi16((0.inv()).toShort(), 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmCmpgtEpi32() {
    val a = mmSetEpi32(5, 0, 0, 0)
    val b = mmSet1Epi32(0)
    val r = mmCmpgtEpi32(a, b)
    assertEqM128i(r, mmSetEpi32(0.inv(), 0, 0, 0))
}

public fun testMmCmpltEpi8() {
    val a = mmSet1Epi8(0)
    val b = mmSetEpi8(5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = mmCmpltEpi8(a, b)
    val e = mmSetEpi8((0.inv()).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmCmpltEpi16() {
    val a = mmSet1Epi16(0)
    val b = mmSetEpi16(5, 0, 0, 0, 0, 0, 0, 0)
    val r = mmCmpltEpi16(a, b)
    val e = mmSetEpi16((0.inv()).toShort(), 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmCmpltEpi32() {
    val a = mmSet1Epi32(0)
    val b = mmSetEpi32(5, 0, 0, 0)
    val r = mmCmpltEpi32(a, b)
    assertEqM128i(r, mmSetEpi32(0.inv(), 0, 0, 0))
}

public fun testMmCvtepi32Pd() {
    val a = mmSetEpi32(35, 25, 15, 5)
    val r = mmCvtepi32Pd(a)
    assertEqM128d(r, mmSetrPd(5.0, 15.0))
}

public fun testMmCvtsi32Sd() {
    val a = mmSet1Pd(3.5)
    val r = mmCvtsi32Sd(a, 5)
    assertEqM128d(r, mmSetrPd(5.0, 3.5))
}

public fun testMmCvtepi32Ps() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val r = mmCvtepi32Ps(a)
    assertEqM128(r, mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMmCvtpsEpi32() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtpsEpi32(a)
    assertEqM128i(r, mmSetrEpi32(1, 2, 3, 4))
}

public fun testMmCvtsi32Si128() {
    val r = mmCvtsi32Si128(5)
    assertEqM128i(r, mmSetrEpi32(5, 0, 0, 0))
}

public fun testMmCvtsi128Si32() {
    val r = mmCvtsi128Si32(mmSetrEpi32(5, 0, 0, 0))
    check(r == 5)
}

public fun testMmSetEpi64x() {
    val r = mmSetEpi64x(0L, 1L)
    assertEqM128i(r, mmSetrEpi64x(1, 0))
}

public fun testMmSetEpi32() {
    val r = mmSetEpi32(0, 1, 2, 3)
    assertEqM128i(r, mmSetrEpi32(3, 2, 1, 0))
}

public fun testMmSetEpi16() {
    val r = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r, mmSetrEpi16(7, 6, 5, 4, 3, 2, 1, 0))
}

public fun testMmSetEpi8() {
    val r = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e = mmSetrEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM128i(r, e)
}

public fun testMmSet1Epi64x() {
    val r = mmSet1Epi64x(1L)
    assertEqM128i(r, mmSet1Epi64x(1L))
}

public fun testMmSet1Epi32() {
    val r = mmSet1Epi32(1)
    assertEqM128i(r, mmSet1Epi32(1))
}

public fun testMmSet1Epi16() {
    val r = mmSet1Epi16(1)
    assertEqM128i(r, mmSet1Epi16(1))
}

public fun testMmSet1Epi8() {
    val r = mmSet1Epi8(1)
    assertEqM128i(r, mmSet1Epi8(1))
}

public fun testMmSetrEpi32() {
    val r = mmSetrEpi32(0, 1, 2, 3)
    assertEqM128i(r, mmSetrEpi32(0, 1, 2, 3))
}

public fun testMmSetrEpi16() {
    val r = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r, mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7))
}

public fun testMmSetrEpi8() {
    val r = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMmSetzeroSi128() {
    val r = mmSetzeroSi128()
    assertEqM128i(r, mmSet1Epi64x(0L))
}

public fun testMmLoadlEpi64() {
    val a = mmSetrEpi64x(6, 5)
    val r = mmLoadlEpi64(addrOf(a))
    assertEqM128i(r, mmSetrEpi64x(6, 0))
}

public fun testMmLoadSi128() {
    val a = mmSetEpi64x(5L, 6L)
    val r = mmLoadSi128(constPtrFromRustCast(addrOf(a)))
    assertEqM128i(a, r)
}

public fun testMmLoaduSi128() {
    val a = mmSetEpi64x(5L, 6L)
    val r = mmLoaduSi128(constPtrFromRustCast(addrOf(a)))
    assertEqM128i(a, r)
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmMaskmoveuSi128() {
    val a = mmSet1Epi8(9)
    val mask = mmSetEpi8(0, 0, (0x80u.toUByte()).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = mmSet1Epi8(0)
    run {
        mmMaskmoveuSi128(a, mask, mutPtrFromRustCast(addrOfMut(r)))
    }
    mmSfence()
    val e = mmSetEpi8(0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmStoreSi128() {
    val a = mmSet1Epi8(9)
    val localStorageL4217C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL4217C8
    run {
        mmStoreSi128(RustMutableReference(localStorageL4217C8.asMutPtr(), localStorageL4217C8.layout), a)
    }
    assertEqM128i(r, a)
}

public fun testMmStoreuSi128() {
    val a = mmSet1Epi8(9)
    val localStorageL4227C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL4227C8
    run {
        mmStoreuSi128(RustMutableReference(localStorageL4227C8.asMutPtr(), localStorageL4227C8.layout), a)
    }
    assertEqM128i(r, a)
}

public fun testMmStorelEpi64() {
    val a = mmSetrEpi64x(2, 9)
    val localStorageL4237C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL4237C8
    run {
        mmStorelEpi64(RustMutableReference(localStorageL4237C8.asMutPtr(), localStorageL4237C8.layout), a)
    }
    assertEqM128i(r, mmSetrEpi64x(2, 0))
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamSi128() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val r = mmUndefinedSi128()
    run {
        mmStreamSi128(addrOfMut(r), a)
    }
    mmSfence()
    assertEqM128i(r, a)
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamSi32() {
    val a: Int = 7
    val mem = -1
    run {
        mmStreamSi32(addrOfMut(mem), a)
    }
    mmSfence()
    check(a == mem)
}

public fun testMmMoveEpi64() {
    val a = mmSetrEpi64x(5, 6)
    val r = mmMoveEpi64(a)
    assertEqM128i(r, mmSetrEpi64x(5, 0))
}

public fun testMmPacksEpi16() {
    val a = mmSetrEpi16(0x80, (-0x81).toShort(), 0, 0, 0, 0, 0, 0)
    val b = mmSetrEpi16(0, 0, 0, 0, 0, 0, (-0x81).toShort(), 0x80)
    val r = mmPacksEpi16(a, b)
    assertEqM128i(r, mmSetrEpi8(0x7F, (-0x80).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (-0x80).toByte(), 0x7F))
}

public fun testMmPacksEpi32() {
    val a = mmSetrEpi32(0x8000, -0x8001, 0, 0)
    val b = mmSetrEpi32(0, 0, -0x8001, 0x8000)
    val r = mmPacksEpi32(a, b)
    assertEqM128i(r, mmSetrEpi16(0x7FFF, (-0x8000).toShort(), 0, 0, 0, 0, (-0x8000).toShort(), 0x7FFF))
}

public fun testMmPackusEpi16() {
    val a = mmSetrEpi16(0x100, (-1).toShort(), 0, 0, 0, 0, 0, 0)
    val b = mmSetrEpi16(0, 0, 0, 0, 0, 0, (-1).toShort(), 0x100)
    val r = mmPackusEpi16(a, b)
    assertEqM128i(r, mmSetrEpi8((0.inv()).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (0.inv()).toByte()))
}

public fun testMmExtractEpi16() {
    val a = mmSetrEpi16((-1).toShort(), 1, 2, 3, 4, 5, 6, 7)
    val r1 = mmExtractEpi16(a, 0)
    val r2 = mmExtractEpi16(a, 3)
    check(r1 == 0xFFFF)
    check(r2 == 3)
}

public fun testMmInsertEpi16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmInsertEpi16(a, 9, 0)
    val e = mmSetrEpi16(9, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMmMovemaskEpi8() {
    val a = mmSetrEpi8((0x80u.toUByte()).toByte(), 0x0, (0x80u.toUByte()).toByte(), 0x1, 0x5, (0xF0u.toUByte()).toByte(), 0, 0, 0, (0xB5u.toUByte()).toByte(), (0xF0u.toUByte()).toByte(), 0x5, 0x1, (0x80u.toUByte()).toByte(), 0x0, (0x80u.toUByte()).toByte())
    val r = mmMovemaskEpi8(a)
    check(r == 0xA625)
}

public fun testMmShuffleEpi32() {
    val a = mmSetrEpi32(5, 10, 15, 20)
    val r = mmShuffleEpi32(a, 0x17)
    val e = mmSetrEpi32(20, 10, 10, 5)
    assertEqM128i(r, e)
}

public fun testMmShufflehiEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 10, 15, 20)
    val r = mmShufflehiEpi16(a, 0x17)
    val e = mmSetrEpi16(1, 2, 3, 4, 20, 10, 10, 5)
    assertEqM128i(r, e)
}

public fun testMmShuffleloEpi16() {
    val a = mmSetrEpi16(5, 10, 15, 20, 1, 2, 3, 4)
    val r = mmShuffleloEpi16(a, 0x17)
    val e = mmSetrEpi16(20, 10, 10, 5, 1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmUnpackhiEpi8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mmUnpackhiEpi8(a, b)
    val e = mmSetrEpi8(8, 24, 9, 25, 10, 26, 11, 27, 12, 28, 13, 29, 14, 30, 15, 31)
    assertEqM128i(r, e)
}

public fun testMmUnpackhiEpi16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetrEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmUnpackhiEpi16(a, b)
    val e = mmSetrEpi16(4, 12, 5, 13, 6, 14, 7, 15)
    assertEqM128i(r, e)
}

public fun testMmUnpackhiEpi32() {
    val a = mmSetrEpi32(0, 1, 2, 3)
    val b = mmSetrEpi32(4, 5, 6, 7)
    val r = mmUnpackhiEpi32(a, b)
    val e = mmSetrEpi32(2, 6, 3, 7)
    assertEqM128i(r, e)
}

public fun testMmUnpackhiEpi64() {
    val a = mmSetrEpi64x(0, 1)
    val b = mmSetrEpi64x(2, 3)
    val r = mmUnpackhiEpi64(a, b)
    val e = mmSetrEpi64x(1, 3)
    assertEqM128i(r, e)
}

public fun testMmUnpackloEpi8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mmUnpackloEpi8(a, b)
    val e = mmSetrEpi8(0, 16, 1, 17, 2, 18, 3, 19, 4, 20, 5, 21, 6, 22, 7, 23)
    assertEqM128i(r, e)
}

public fun testMmUnpackloEpi16() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetrEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmUnpackloEpi16(a, b)
    val e = mmSetrEpi16(0, 8, 1, 9, 2, 10, 3, 11)
    assertEqM128i(r, e)
}

public fun testMmUnpackloEpi32() {
    val a = mmSetrEpi32(0, 1, 2, 3)
    val b = mmSetrEpi32(4, 5, 6, 7)
    val r = mmUnpackloEpi32(a, b)
    val e = mmSetrEpi32(0, 4, 1, 5)
    assertEqM128i(r, e)
}

public fun testMmUnpackloEpi64() {
    val a = mmSetrEpi64x(0, 1)
    val b = mmSetrEpi64x(2, 3)
    val r = mmUnpackloEpi64(a, b)
    val e = mmSetrEpi64x(0, 2)
    assertEqM128i(r, e)
}

public fun testMmAddSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmAddSd(a, b)
    assertEqM128d(r, mmSetrPd(6.0, 2.0))
}

public fun testMmAddPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmAddPd(a, b)
    assertEqM128d(r, mmSetrPd(6.0, 12.0))
}

public fun testMmDivSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmDivSd(a, b)
    assertEqM128d(r, mmSetrPd(0.2, 2.0))
}

public fun testMmDivPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmDivPd(a, b)
    assertEqM128d(r, mmSetrPd(0.2, 0.2))
}

public fun testMmMaxSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmMaxSd(a, b)
    assertEqM128d(r, mmSetrPd(5.0, 2.0))
}

public fun testMmMaxPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmMaxPd(a, b)
    assertEqM128d(r, mmSetrPd(5.0, 10.0))
    // Check SSE(2)-specific semantics for -0.0 handling.
    val a1 = mmSetrPd(-0.0, 0.0)
    val b1 = mmSetrPd(0.0, 0.0)
    // Cast to __m128i to compare exact bit patterns
    val r1 = mmCastpdSi128(mmMaxPd(a1, b1))
    val r2 = mmCastpdSi128(mmMaxPd(b1, a1))
    val a2 = mmCastpdSi128(a1)
    val b2 = mmCastpdSi128(b1)
    assertEqM128i(r1, b2)
    assertEqM128i(r2, a2)
    check(a2.asU8x16() != b2.asU8x16())
    // sanity check that -0.0 is actually present
}

public fun testMmMinSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmMinSd(a, b)
    assertEqM128d(r, mmSetrPd(1.0, 2.0))
}

public fun testMmMinPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmMinPd(a, b)
    assertEqM128d(r, mmSetrPd(1.0, 2.0))
    // Check SSE(2)-specific semantics for -0.0 handling.
    val a1 = mmSetrPd(-0.0, 0.0)
    val b1 = mmSetrPd(0.0, 0.0)
    // Cast to __m128i to compare exact bit patterns
    val r1 = mmCastpdSi128(mmMinPd(a1, b1))
    val r2 = mmCastpdSi128(mmMinPd(b1, a1))
    val a2 = mmCastpdSi128(a1)
    val b2 = mmCastpdSi128(b1)
    assertEqM128i(r1, b2)
    assertEqM128i(r2, a2)
    check(a2.asU8x16() != b2.asU8x16())
    // sanity check that -0.0 is actually present
}

public fun testMmMulSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmMulSd(a, b)
    assertEqM128d(r, mmSetrPd(5.0, 2.0))
}

public fun testMmMulPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmMulPd(a, b)
    assertEqM128d(r, mmSetrPd(5.0, 20.0))
}

public fun testMmSqrtSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmSqrtSd(a, b)
    assertEqM128d(r, mmSetrPd(kmSqrtf64(5.0), 2.0))
}

public fun testMmSqrtPd() {
    val r = mmSqrtPd(mmSetrPd(1.0, 2.0))
    assertEqM128d(r, mmSetrPd(kmSqrtf64(1.0), kmSqrtf64(2.0)))
}

public fun testMmSubSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmSubSd(a, b)
    assertEqM128d(r, mmSetrPd(-4.0, 2.0))
}

public fun testMmSubPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 10.0)
    val r = mmSubPd(a, b)
    assertEqM128d(r, mmSetrPd(-4.0, -8.0))
}

public fun testMmAndPd() {
    val a = F64x2.fromBits(Simd<ULong>(List(2) { 5uL })).asM128d()
    val b = F64x2.fromBits(Simd<ULong>(List(2) { 3uL })).asM128d()
    val r = mmAndPd(a, b)
    val e = F64x2.fromBits(Simd<ULong>(List(2) { 1uL })).asM128d()
    assertEqM128d(r, e)
}

public fun testMmAndnotPd() {
    val a = F64x2.fromBits(Simd<ULong>(List(2) { 5uL })).asM128d()
    val b = F64x2.fromBits(Simd<ULong>(List(2) { 3uL })).asM128d()
    val r = mmAndnotPd(a, b)
    val e = F64x2.fromBits(Simd<ULong>(List(2) { 2uL })).asM128d()
    assertEqM128d(r, e)
}

public fun testMmOrPd() {
    val a = F64x2.fromBits(Simd<ULong>(List(2) { 5uL })).asM128d()
    val b = F64x2.fromBits(Simd<ULong>(List(2) { 3uL })).asM128d()
    val r = mmOrPd(a, b)
    val e = F64x2.fromBits(Simd<ULong>(List(2) { 7uL })).asM128d()
    assertEqM128d(r, e)
}

public fun testMmXorPd() {
    val a = F64x2.fromBits(Simd<ULong>(List(2) { 5uL })).asM128d()
    val b = F64x2.fromBits(Simd<ULong>(List(2) { 3uL })).asM128d()
    val r = mmXorPd(a, b)
    val e = F64x2.fromBits(Simd<ULong>(List(2) { 6uL })).asM128d()
    assertEqM128d(r, e)
}

public fun testMmCmpeqSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpeqSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpltSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpltSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpleSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpleSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpgtSd() {
    val (a, b) = Pair(mmSetrPd(5.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpgtSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpgeSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpgeSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpordSd() {
    val (a, b) = Pair(mmSetrPd(NAN, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0, 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpordSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpunordSd() {
    val (a, b) = Pair(mmSetrPd(NAN, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpunordSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpneqSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpneqSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpnltSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0, 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpnltSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpnleSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpnleSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpngtSd() {
    val (a, b) = Pair(mmSetrPd(5.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpngtSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpngeSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 2.0.toRawBits().toULong().toLong())
    val r = mmCastpdSi128(mmCmpngeSd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpeqPd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 0)
    val r = mmCastpdSi128(mmCmpeqPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpltPd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 0.inv())
    val r = mmCastpdSi128(mmCmpltPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmplePd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 0.inv())
    val r = mmCastpdSi128(mmCmplePd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpgtPd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 0)
    val r = mmCastpdSi128(mmCmpgtPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpgePd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 0)
    val r = mmCastpdSi128(mmCmpgePd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpordPd() {
    val (a, b) = Pair(mmSetrPd(NAN, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0, 0.inv())
    val r = mmCastpdSi128(mmCmpordPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpunordPd() {
    val (a, b) = Pair(mmSetrPd(NAN, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 0)
    val r = mmCastpdSi128(mmCmpunordPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpneqPd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0.inv(), 0.inv())
    val r = mmCastpdSi128(mmCmpneqPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpnltPd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(5.0, 3.0))
    val e = mmSetrEpi64x(0, 0)
    val r = mmCastpdSi128(mmCmpnltPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpnlePd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 0)
    val r = mmCastpdSi128(mmCmpnlePd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpngtPd() {
    val (a, b) = Pair(mmSetrPd(5.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 0.inv())
    val r = mmCastpdSi128(mmCmpngtPd(a, b))
    assertEqM128i(r, e)
}

public fun testMmCmpngePd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    val e = mmSetrEpi64x(0, 0.inv())
    val r = mmCastpdSi128(mmCmpngePd(a, b))
    assertEqM128i(r, e)
}

public fun testMmComieqSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComieqSd(a, b) != 0)
    val (a1, b1) = Pair(mmSetrPd(NAN, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComieqSd(a1, b1) == 0)
}

public fun testMmComiltSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComiltSd(a, b) == 0)
}

public fun testMmComileSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComileSd(a, b) != 0)
}

public fun testMmComigtSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComigtSd(a, b) == 0)
}

public fun testMmComigeSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComigeSd(a, b) != 0)
}

public fun testMmComineqSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmComineqSd(a, b) == 0)
}

public fun testMmUcomieqSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmUcomieqSd(a, b) != 0)
    val (a1, b1) = Pair(mmSetrPd(NAN, 2.0), mmSetrPd(NAN, 3.0))
    check(mmUcomieqSd(a1, b1) == 0)
}

public fun testMmUcomiltSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmUcomiltSd(a, b) == 0)
}

public fun testMmUcomileSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmUcomileSd(a, b) != 0)
}

public fun testMmUcomigtSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmUcomigtSd(a, b) == 0)
}

public fun testMmUcomigeSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmUcomigeSd(a, b) != 0)
}

public fun testMmUcomineqSd() {
    val (a, b) = Pair(mmSetrPd(1.0, 2.0), mmSetrPd(1.0, 3.0))
    check(mmUcomineqSd(a, b) == 0)
}

public fun testMmMovemaskPd() {
    val r = mmMovemaskPd(mmSetrPd(-1.0, 5.0))
    check(r == 0x1)
    val r1 = mmMovemaskPd(mmSetrPd(-1.0, -5.0))
    check(r1 == 0x3)
}

public open class Memory internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
) {
    public var data: DoubleArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })

    public companion object {
        public operator fun invoke(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()): Memory = Memory(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()): Memory = Memory(fieldStorage, fieldLayout)
    }
}

public fun testMmLoadPd() {
    val mem = Memory(data = doubleArrayOf(1.0, 2.0, 3.0, 4.0))
    val vals = mem.data
    val d = vals.asPtr()
    val r = mmLoadPd(d)
    assertEqM128d(r, mmSetrPd(1.0, 2.0))
}

public fun testMmLoadSd() {
    val localStorageL4930C8 = AddressableLocal(1.0, UnresolvedTypeLayout<Any?>())
    val a by localStorageL4930C8
    val expected = mmSetrPd(a, 0.0)
    val r = mmLoadSd(a)
    assertEqM128d(r, expected)
}

public fun testMmLoadhPd() {
    val a = mmSetrPd(1.0, 2.0)
    val expected = mmSetrPd(mmCvtsdF64(a), 3.0)
    val r = mmLoadhPd(a, b)
    assertEqM128d(r, expected)
}

public fun testMmLoadlPd() {
    val a = mmSetrPd(1.0, 2.0)
    val expected = mmSetrPd(3.0, getM128d(a, 1))
    val r = mmLoadlPd(a, b)
    assertEqM128d(r, expected)
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamPd() {
    open class Memory(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 128L)), AlignedTypeLayout(fieldLayout, 128L))

        public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })
    }
    val a = mmSet1Pd(7.0)
    val mem = Memory(data = DoubleArray(2) { -1.0 })
    run {
        mmStreamPd(addrOfMut(mem.data[0]), a)
    }
    mmSfence()
    for (i in 0 until 2) {
        check(mem.data[i] == getM128d(a, i))
    }
}

public fun testMmStoreSd() {
    val localStorageL4977C8 = AddressableLocal(0.0, UnresolvedTypeLayout<Any?>())
    var dest by localStorageL4977C8
    val a = mmSetrPd(1.0, 2.0)
    run {
        mmStoreSd(RustMutableReference(localStorageL4977C8.asMutPtr(), localStorageL4977C8.layout), a)
    }
    check(dest == mmCvtsdF64(a))
}

public fun testMmStorePd() {
    val mem = Memory(data = DoubleArray(4) { 0.0 })
    val vals = mem.data
    val a = mmSetrPd(1.0, 2.0)
    val d = vals.asMutPtr()
    run {
        mmStorePd(d, blackBox(a))
    }
    check(vals[0] == 1.0.toDouble())
    check(vals[1] == 2.0.toDouble())
}

public fun testMmStoreuPd() {
    // guaranteed to be aligned to 16 bytes
    val mem = Memory(data = DoubleArray(4) { 0.0 })
    val vals = mem.data
    val a = mmSetrPd(1.0, 2.0)
    // so p is *not* aligned to 16 bytes
    run {
        val p = MutPtr((vals.asMutPtr()).rawAddress + ((1).toLong() * (PrimitiveLayouts.F64).strideBytes))
        mmStoreuPd(p, blackBox(a))
    }
    check(vals == doubleArrayOf(0.0, 1.0, 2.0, 0.0))
}

public fun testMmStoreuSi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmSetrEpi16(9, 10, 11, 12, 13, 14, 15, 16)
    run {
        mmStoreuSi16(addrOfMut(r).cast(), a)
    }
    val e = mmSetrEpi16(1, 10, 11, 12, 13, 14, 15, 16)
    assertEqM128i(r, e)
}

public fun testMmStoreuSi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val r = mmSetrEpi32(5, 6, 7, 8)
    run {
        mmStoreuSi32(addrOfMut(r).cast(), a)
    }
    val e = mmSetrEpi32(1, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMmStoreuSi64() {
    val a = mmSetrEpi64x(1, 2)
    val r = mmSetrEpi64x(3, 4)
    run {
        mmStoreuSi64(addrOfMut(r).cast(), a)
    }
    val e = mmSetrEpi64x(1, 4)
    assertEqM128i(r, e)
}

public fun testMmStore1Pd() {
    val mem = Memory(data = DoubleArray(4) { 0.0 })
    val vals = mem.data
    val a = mmSetrPd(1.0, 2.0)
    val d = vals.asMutPtr()
    run {
        mmStore1Pd(d, blackBox(a))
    }
    check(vals[0] == 1.0.toDouble())
    check(vals[1] == 1.0.toDouble())
}

public fun testMmStorePd1() {
    val mem = Memory(data = DoubleArray(4) { 0.0 })
    val vals = mem.data
    val a = mmSetrPd(1.0, 2.0)
    val d = vals.asMutPtr()
    run {
        mmStorePd1(d, blackBox(a))
    }
    check(vals[0] == 1.0.toDouble())
    check(vals[1] == 1.0.toDouble())
}

public fun testMmStorerPd() {
    val mem = Memory(data = DoubleArray(4) { 0.0 })
    val vals = mem.data
    val a = mmSetrPd(1.0, 2.0)
    val d = vals.asMutPtr()
    run {
        mmStorerPd(d, blackBox(a))
    }
    check(vals[0] == 2.0.toDouble())
    check(vals[1] == 1.0.toDouble())
}

public fun testMmStorehPd() {
    val localStorageL5092C8 = AddressableLocal(0.0, UnresolvedTypeLayout<Any?>())
    var dest by localStorageL5092C8
    val a = mmSetrPd(1.0, 2.0)
    run {
        mmStorehPd(RustMutableReference(localStorageL5092C8.asMutPtr(), localStorageL5092C8.layout), a)
    }
    check(dest == getM128d(a, 1))
}

public fun testMmStorelPd() {
    val localStorageL5102C8 = AddressableLocal(0.0, UnresolvedTypeLayout<Any?>())
    var dest by localStorageL5102C8
    val a = mmSetrPd(1.0, 2.0)
    run {
        mmStorelPd(RustMutableReference(localStorageL5102C8.asMutPtr(), localStorageL5102C8.layout), a)
    }
    check(dest == mmCvtsdF64(a))
}

public fun testMmLoadrPd() {
    val mem = Memory(data = doubleArrayOf(1.0, 2.0, 3.0, 4.0))
    val vals = mem.data
    val d = vals.asPtr()
    val r = mmLoadrPd(d)
    assertEqM128d(r, mmSetrPd(2.0, 1.0))
}

public fun testMmLoaduPd() {
    // guaranteed to be aligned to 16 bytes
    val mem = Memory(data = doubleArrayOf(1.0, 2.0, 3.0, 4.0))
    val vals = mem.data
    // so this will *not* be aligned to 16 bytes
    val d = ConstPtr((vals.asPtr()).rawAddress + ((1).toLong() * (PrimitiveLayouts.F64).strideBytes))
    val r = mmLoaduPd(d)
    val e = mmSetrPd(2.0, 3.0)
    assertEqM128d(r, e)
}

public fun testMmLoaduSi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmLoaduSi16(constPtrFromRustCast(addrOf(a)))
    assertEqM128i(r, mmSetrEpi16(1, 0, 0, 0, 0, 0, 0, 0))
}

public fun testMmLoaduSi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val r = mmLoaduSi32(constPtrFromRustCast(addrOf(a)))
    assertEqM128i(r, mmSetrEpi32(1, 0, 0, 0))
}

public fun testMmLoaduSi64() {
    val a = mmSetrEpi64x(5, 6)
    val r = mmLoaduSi64(constPtrFromRustCast(addrOf(a)))
    assertEqM128i(r, mmSetrEpi64x(5, 0))
}

public fun testMmCvtpdPs() {
    val r = mmCvtpdPs(mmSetrPd(-1.0, 5.0))
    assertEqM128(r, mmSetrPs(-1.0f, 5.0f, 0.0f, 0.0f))
    val r1 = mmCvtpdPs(mmSetrPd(-1.0, -5.0))
    assertEqM128(r1, mmSetrPs(-1.0f, -5.0f, 0.0f, 0.0f))
    val r2 = mmCvtpdPs(mmSetrPd(Double.MAX_VALUE, (-Double.MAX_VALUE)))
    assertEqM128(r2, mmSetrPs(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, 0.0f))
    val r3 = mmCvtpdPs(mmSetrPd(Float.MAX_VALUE.toDouble(), ((-Float.MAX_VALUE)).toDouble()))
    assertEqM128(r3, mmSetrPs(Float.MAX_VALUE, (-Float.MAX_VALUE), 0.0f, 0.0f))
}

public fun testMmCvtpsPd() {
    val r = mmCvtpsPd(mmSetrPs(-1.0f, 2.0f, -3.0f, 5.0f))
    assertEqM128d(r, mmSetrPd(-1.0, 2.0))
    val r1 = mmCvtpsPd(mmSetrPs(Float.MAX_VALUE, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, (-Float.MAX_VALUE)))
    assertEqM128d(r1, mmSetrPd(Float.MAX_VALUE.toDouble(), Double.POSITIVE_INFINITY))
}

public fun testMmCvtpdEpi32() {
    val r = mmCvtpdEpi32(mmSetrPd(-1.0, 5.0))
    assertEqM128i(r, mmSetrEpi32(-1, 5, 0, 0))
    val r1 = mmCvtpdEpi32(mmSetrPd(-1.0, -5.0))
    assertEqM128i(r1, mmSetrEpi32(-1, -5, 0, 0))
    val r2 = mmCvtpdEpi32(mmSetrPd(Double.MAX_VALUE, (-Double.MAX_VALUE)))
    assertEqM128i(r2, mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0))
    val r3 = mmCvtpdEpi32(mmSetrPd(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY))
    assertEqM128i(r3, mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0))
    val r4 = mmCvtpdEpi32(mmSetrPd(Double.NaN, Double.NaN))
    assertEqM128i(r4, mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0))
}

public fun testMmCvtsdSi32() {
    val r = mmCvtsdSi32(mmSetrPd(-2.0, 5.0))
    check(r == -2)
    val r1 = mmCvtsdSi32(mmSetrPd(Double.MAX_VALUE, (-Double.MAX_VALUE)))
    check(r1 == Int.MIN_VALUE)
    val r2 = mmCvtsdSi32(mmSetrPd(Double.NaN, Double.NaN))
    check(r2 == Int.MIN_VALUE)
}

public fun testMmCvtsdSs() {
    val a = mmSetrPs(-1.1f, -2.2f, 3.3f, 4.4f)
    val b = mmSetrPd(2.0, -5.0)
    val r = mmCvtsdSs(a, b)
    assertEqM128(r, mmSetrPs(2.0f, -2.2f, 3.3f, 4.4f))
    val a1 = mmSetrPs(-1.1f, Float.NEGATIVE_INFINITY, Float.MAX_VALUE, Float.NEGATIVE_INFINITY)
    val b1 = mmSetrPd(Double.POSITIVE_INFINITY, -5.0)
    val r1 = mmCvtsdSs(a1, b1)
    assertEqM128(r1, mmSetrPs(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.MAX_VALUE, Float.NEGATIVE_INFINITY))
}

public fun testMmCvtsdF64() {
    val r = mmCvtsdF64(mmSetrPd(-1.1, 2.2))
    check(r == (-1.1).toDouble())
}

public fun testMmCvtssSd() {
    val a = mmSetrPd(-1.1, 2.2)
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtssSd(a, b)
    assertEqM128d(r, mmSetrPd(1.0, 2.2))
    val a1 = mmSetrPd(-1.1, Double.POSITIVE_INFINITY)
    val b1 = mmSetrPs(Float.NEGATIVE_INFINITY, 2.0f, 3.0f, 4.0f)
    val r1 = mmCvtssSd(a1, b1)
    assertEqM128d(r1, mmSetrPd(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
}

public fun testMmCvttpdEpi32() {
    val a = mmSetrPd(-1.1, 2.2)
    val r = mmCvttpdEpi32(a)
    assertEqM128i(r, mmSetrEpi32(-1, 2, 0, 0))
    val a1 = mmSetrPd(Double.NEGATIVE_INFINITY, Double.NaN)
    val r1 = mmCvttpdEpi32(a1)
    assertEqM128i(r1, mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0))
}

public fun testMmCvttsdSi32() {
    val a = mmSetrPd(-1.1, 2.2)
    val r = mmCvttsdSi32(a)
    check(r == -1)
    val a1 = mmSetrPd(Double.NEGATIVE_INFINITY, Double.NaN)
    val r1 = mmCvttsdSi32(a1)
    check(r1 == Int.MIN_VALUE)
}

public fun testMmCvttpsEpi32() {
    val a = mmSetrPs(-1.1f, 2.2f, -3.3f, 6.6f)
    val r = mmCvttpsEpi32(a)
    assertEqM128i(r, mmSetrEpi32(-1, 2, -3, 6))
    val a1 = mmSetrPs(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, (-Float.MAX_VALUE), Float.MAX_VALUE)
    val r1 = mmCvttpsEpi32(a1)
    assertEqM128i(r1, mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE))
}

public fun testMmSetSd() {
    val r = mmSetSd(-1.0)
    assertEqM128d(r, mmSetrPd(-1.0, 0.0))
}

public fun testMmSet1Pd() {
    val r = mmSet1Pd(-1.0)
    assertEqM128d(r, mmSetrPd(-1.0, -1.0))
}

public fun testMmSetPd1() {
    val r = mmSetPd1(-2.0)
    assertEqM128d(r, mmSetrPd(-2.0, -2.0))
}

public fun testMmSetPd() {
    val r = mmSetPd(1.0, 5.0)
    assertEqM128d(r, mmSetrPd(5.0, 1.0))
}

public fun testMmSetrPd() {
    val r = mmSetrPd(1.0, -5.0)
    assertEqM128d(r, mmSetrPd(1.0, -5.0))
}

public fun testMmSetzeroPd() {
    val r = mmSetzeroPd()
    assertEqM128d(r, mmSetrPd(0.0, 0.0))
}

public fun testMmLoad1Pd() {
    val localStorageL5335C8 = AddressableLocal(-5.0, UnresolvedTypeLayout<Any?>())
    val d by localStorageL5335C8
    val r = mmLoad1Pd(d)
    assertEqM128d(r, mmSetrPd(d, d))
}

public fun testMmLoadPd1() {
    val localStorageL5342C8 = AddressableLocal(-5.0, UnresolvedTypeLayout<Any?>())
    val d by localStorageL5342C8
    val r = mmLoadPd1(d)
    assertEqM128d(r, mmSetrPd(d, d))
}

public fun testMmUnpackhiPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(3.0, 4.0)
    val r = mmUnpackhiPd(a, b)
    assertEqM128d(r, mmSetrPd(2.0, 4.0))
}

public fun testMmUnpackloPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(3.0, 4.0)
    val r = mmUnpackloPd(a, b)
    assertEqM128d(r, mmSetrPd(1.0, 3.0))
}

public fun testMmShufflePd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(3.0, 4.0)
    val expected = mmSetrPd(1.0, 3.0)
    val r = mmShufflePd(a, b, 0x0)
    assertEqM128d(r, expected)
}

public fun testMmMoveSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(3.0, 4.0)
    val expected = mmSetrPd(3.0, 2.0)
    val r = mmMoveSd(a, b)
    assertEqM128d(r, expected)
}

public fun testMmCastpdPs() {
    val a = mmSet1Pd(0.0)
    val expected = mmSet1Ps(0.0f)
    val r = mmCastpdPs(a)
    assertEqM128(r, expected)
}

public fun testMmCastpdSi128() {
    val a = mmSet1Pd(0.0)
    val expected = mmSet1Epi64x(0L)
    val r = mmCastpdSi128(a)
    assertEqM128i(r, expected)
}

public fun testMmCastpsPd() {
    val a = mmSet1Ps(0.0f)
    val expected = mmSet1Pd(0.0)
    val r = mmCastpsPd(a)
    assertEqM128d(r, expected)
}

public fun testMmCastpsSi128() {
    val a = mmSet1Ps(0.0f)
    val expected = mmSet1Epi32(0)
    val r = mmCastpsSi128(a)
    assertEqM128i(r, expected)
}

public fun testMmCastsi128Pd() {
    val a = mmSet1Epi64x(0L)
    val expected = mmSet1Pd(0.0)
    val r = mmCastsi128Pd(a)
    assertEqM128d(r, expected)
}

public fun testMmCastsi128Ps() {
    val a = mmSet1Epi32(0)
    val expected = mmSet1Ps(0.0f)
    val r = mmCastsi128Ps(a)
    assertEqM128(r, expected)
}

public class Sse2InlineTests {
    @kotlin.test.Test
    public fun testMmPause() {
        mmPause()
    }
}
