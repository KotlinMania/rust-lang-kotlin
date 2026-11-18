// port-lint: tests x86/avx512dq.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public val OPRND1_64: Double = Double.fromBits(0x3333333333333333uL.toLong())

public val OPRND2_64: Double = Double.fromBits(0x5555555555555555uL.toLong())

public val AND_64: Double = Double.fromBits(0x1111111111111111uL.toLong())

public val ANDN_64: Double = Double.fromBits(0x4444444444444444uL.toLong())

public val OR_64: Double = Double.fromBits(0x7777777777777777uL.toLong())

public val XOR_64: Double = Double.fromBits(0x6666666666666666uL.toLong())

public val OPRND1_32: Float = Float.fromBits(0x33333333u.toInt())

public val OPRND2_32: Float = Float.fromBits(0x55555555u.toInt())

public val AND_32: Float = Float.fromBits(0x11111111u.toInt())

public val ANDN_32: Float = Float.fromBits(0x44444444u.toInt())

public val OR_32: Float = Float.fromBits(0x77777777u.toInt())

public val XOR_32: Float = Float.fromBits(0x66666666u.toInt())

public fun testMmMaskAndPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val src = mmSetPd(1.0, 2.0)
    val r = mmMaskAndPd(src, 0x1u, a, b)
    val e = mmSetPd(1.0, AND_64)
    assertEqM128d(r, e)
}

public fun testMmMaskzAndPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val r = mmMaskzAndPd(0x1u, a, b)
    val e = mmSetPd(0.0, AND_64)
    assertEqM128d(r, e)
}

public fun testMm256MaskAndPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val src = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskAndPd(src, 0x5u, a, b)
    val e = mm256SetPd(1.0, AND_64, 3.0, AND_64)
    assertEqM256d(r, e)
}

public fun testMm256MaskzAndPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val r = mm256MaskzAndPd(0x5u, a, b)
    val e = mm256SetPd(0.0, AND_64, 0.0, AND_64)
    assertEqM256d(r, e)
}

public fun testMm512AndPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512AndPd(a, b)
    val e = mm512Set1Pd(AND_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskAndPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val src = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskAndPd(src, 0x55u, a, b)
    val e = mm512SetPd(1.0, AND_64, 3.0, AND_64, 5.0, AND_64, 7.0, AND_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskzAndPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512MaskzAndPd(0x55u, a, b)
    val e = mm512SetPd(0.0, AND_64, 0.0, AND_64, 0.0, AND_64, 0.0, AND_64)
    assertEqM512d(r, e)
}

public fun testMmMaskAndPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val src = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskAndPs(src, 0x5u, a, b)
    val e = mmSetPs(1.0f, AND_32, 3.0f, AND_32)
    assertEqM128(r, e)
}

public fun testMmMaskzAndPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val r = mmMaskzAndPs(0x5u, a, b)
    val e = mmSetPs(0.0f, AND_32, 0.0f, AND_32)
    assertEqM128(r, e)
}

public fun testMm256MaskAndPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val src = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskAndPs(src, 0x55u, a, b)
    val e = mm256SetPs(1.0f, AND_32, 3.0f, AND_32, 5.0f, AND_32, 7.0f, AND_32)
    assertEqM256(r, e)
}

public fun testMm256MaskzAndPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val r = mm256MaskzAndPs(0x55u, a, b)
    val e = mm256SetPs(0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32)
    assertEqM256(r, e)
}

public fun testMm512AndPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512AndPs(a, b)
    val e = mm512Set1Ps(AND_32)
    assertEqM512(r, e)
}

public fun testMm512MaskAndPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val src = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskAndPs(src, 0x5555u, a, b)
    val e = mm512SetPs(1.0f, AND_32, 3.0f, AND_32, 5.0f, AND_32, 7.0f, AND_32, 9.0f, AND_32, 11.0f, AND_32, 13.0f, AND_32, 15.0f, AND_32)
    assertEqM512(r, e)
}

public fun testMm512MaskzAndPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512MaskzAndPs(0x5555u, a, b)
    val e = mm512SetPs(0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32, 0.0f, AND_32)
    assertEqM512(r, e)
}

public fun testMmMaskAndnotPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val src = mmSetPd(1.0, 2.0)
    val r = mmMaskAndnotPd(src, 0x1u, a, b)
    val e = mmSetPd(1.0, ANDN_64)
    assertEqM128d(r, e)
}

public fun testMmMaskzAndnotPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val r = mmMaskzAndnotPd(0x1u, a, b)
    val e = mmSetPd(0.0, ANDN_64)
    assertEqM128d(r, e)
}

public fun testMm256MaskAndnotPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val src = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskAndnotPd(src, 0x5u, a, b)
    val e = mm256SetPd(1.0, ANDN_64, 3.0, ANDN_64)
    assertEqM256d(r, e)
}

public fun testMm256MaskzAndnotPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val r = mm256MaskzAndnotPd(0x5u, a, b)
    val e = mm256SetPd(0.0, ANDN_64, 0.0, ANDN_64)
    assertEqM256d(r, e)
}

public fun testMm512AndnotPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512AndnotPd(a, b)
    val e = mm512Set1Pd(ANDN_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskAndnotPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val src = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskAndnotPd(src, 0x55u, a, b)
    val e = mm512SetPd(1.0, ANDN_64, 3.0, ANDN_64, 5.0, ANDN_64, 7.0, ANDN_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskzAndnotPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512MaskzAndnotPd(0x55u, a, b)
    val e = mm512SetPd(0.0, ANDN_64, 0.0, ANDN_64, 0.0, ANDN_64, 0.0, ANDN_64)
    assertEqM512d(r, e)
}

public fun testMmMaskAndnotPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val src = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskAndnotPs(src, 0x5u, a, b)
    val e = mmSetPs(1.0f, ANDN_32, 3.0f, ANDN_32)
    assertEqM128(r, e)
}

public fun testMmMaskzAndnotPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val r = mmMaskzAndnotPs(0x5u, a, b)
    val e = mmSetPs(0.0f, ANDN_32, 0.0f, ANDN_32)
    assertEqM128(r, e)
}

public fun testMm256MaskAndnotPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val src = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskAndnotPs(src, 0x55u, a, b)
    val e = mm256SetPs(1.0f, ANDN_32, 3.0f, ANDN_32, 5.0f, ANDN_32, 7.0f, ANDN_32)
    assertEqM256(r, e)
}

public fun testMm256MaskzAndnotPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val r = mm256MaskzAndnotPs(0x55u, a, b)
    val e = mm256SetPs(0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32)
    assertEqM256(r, e)
}

public fun testMm512AndnotPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512AndnotPs(a, b)
    val e = mm512Set1Ps(ANDN_32)
    assertEqM512(r, e)
}

public fun testMm512MaskAndnotPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val src = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskAndnotPs(src, 0x5555u, a, b)
    val e = mm512SetPs(1.0f, ANDN_32, 3.0f, ANDN_32, 5.0f, ANDN_32, 7.0f, ANDN_32, 9.0f, ANDN_32, 11.0f, ANDN_32, 13.0f, ANDN_32, 15.0f, ANDN_32)
    assertEqM512(r, e)
}

public fun testMm512MaskzAndnotPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512MaskzAndnotPs(0x5555u, a, b)
    val e = mm512SetPs(0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32, 0.0f, ANDN_32)
    assertEqM512(r, e)
}

public fun testMmMaskOrPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val src = mmSetPd(1.0, 2.0)
    val r = mmMaskOrPd(src, 0x1u, a, b)
    val e = mmSetPd(1.0, OR_64)
    assertEqM128d(r, e)
}

public fun testMmMaskzOrPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val r = mmMaskzOrPd(0x1u, a, b)
    val e = mmSetPd(0.0, OR_64)
    assertEqM128d(r, e)
}

public fun testMm256MaskOrPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val src = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskOrPd(src, 0x5u, a, b)
    val e = mm256SetPd(1.0, OR_64, 3.0, OR_64)
    assertEqM256d(r, e)
}

public fun testMm256MaskzOrPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val r = mm256MaskzOrPd(0x5u, a, b)
    val e = mm256SetPd(0.0, OR_64, 0.0, OR_64)
    assertEqM256d(r, e)
}

public fun testMm512OrPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512OrPd(a, b)
    val e = mm512Set1Pd(OR_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskOrPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val src = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskOrPd(src, 0x55u, a, b)
    val e = mm512SetPd(1.0, OR_64, 3.0, OR_64, 5.0, OR_64, 7.0, OR_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskzOrPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512MaskzOrPd(0x55u, a, b)
    val e = mm512SetPd(0.0, OR_64, 0.0, OR_64, 0.0, OR_64, 0.0, OR_64)
    assertEqM512d(r, e)
}

public fun testMmMaskOrPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val src = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskOrPs(src, 0x5u, a, b)
    val e = mmSetPs(1.0f, OR_32, 3.0f, OR_32)
    assertEqM128(r, e)
}

public fun testMmMaskzOrPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val r = mmMaskzOrPs(0x5u, a, b)
    val e = mmSetPs(0.0f, OR_32, 0.0f, OR_32)
    assertEqM128(r, e)
}

public fun testMm256MaskOrPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val src = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskOrPs(src, 0x55u, a, b)
    val e = mm256SetPs(1.0f, OR_32, 3.0f, OR_32, 5.0f, OR_32, 7.0f, OR_32)
    assertEqM256(r, e)
}

public fun testMm256MaskzOrPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val r = mm256MaskzOrPs(0x55u, a, b)
    val e = mm256SetPs(0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32)
    assertEqM256(r, e)
}

public fun testMm512OrPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512OrPs(a, b)
    val e = mm512Set1Ps(OR_32)
    assertEqM512(r, e)
}

public fun testMm512MaskOrPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val src = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskOrPs(src, 0x5555u, a, b)
    val e = mm512SetPs(1.0f, OR_32, 3.0f, OR_32, 5.0f, OR_32, 7.0f, OR_32, 9.0f, OR_32, 11.0f, OR_32, 13.0f, OR_32, 15.0f, OR_32)
    assertEqM512(r, e)
}

public fun testMm512MaskzOrPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512MaskzOrPs(0x5555u, a, b)
    val e = mm512SetPs(0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32, 0.0f, OR_32)
    assertEqM512(r, e)
}

public fun testMmMaskXorPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val src = mmSetPd(1.0, 2.0)
    val r = mmMaskXorPd(src, 0x1u, a, b)
    val e = mmSetPd(1.0, XOR_64)
    assertEqM128d(r, e)
}

public fun testMmMaskzXorPd() {
    val a = mmSet1Pd(OPRND1_64)
    val b = mmSet1Pd(OPRND2_64)
    val r = mmMaskzXorPd(0x1u, a, b)
    val e = mmSetPd(0.0, XOR_64)
    assertEqM128d(r, e)
}

public fun testMm256MaskXorPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val src = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskXorPd(src, 0x5u, a, b)
    val e = mm256SetPd(1.0, XOR_64, 3.0, XOR_64)
    assertEqM256d(r, e)
}

public fun testMm256MaskzXorPd() {
    val a = mm256Set1Pd(OPRND1_64)
    val b = mm256Set1Pd(OPRND2_64)
    val r = mm256MaskzXorPd(0x5u, a, b)
    val e = mm256SetPd(0.0, XOR_64, 0.0, XOR_64)
    assertEqM256d(r, e)
}

public fun testMm512XorPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512XorPd(a, b)
    val e = mm512Set1Pd(XOR_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskXorPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val src = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskXorPd(src, 0x55u, a, b)
    val e = mm512SetPd(1.0, XOR_64, 3.0, XOR_64, 5.0, XOR_64, 7.0, XOR_64)
    assertEqM512d(r, e)
}

public fun testMm512MaskzXorPd() {
    val a = mm512Set1Pd(OPRND1_64)
    val b = mm512Set1Pd(OPRND2_64)
    val r = mm512MaskzXorPd(0x55u, a, b)
    val e = mm512SetPd(0.0, XOR_64, 0.0, XOR_64, 0.0, XOR_64, 0.0, XOR_64)
    assertEqM512d(r, e)
}

public fun testMmMaskXorPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val src = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskXorPs(src, 0x5u, a, b)
    val e = mmSetPs(1.0f, XOR_32, 3.0f, XOR_32)
    assertEqM128(r, e)
}

public fun testMmMaskzXorPs() {
    val a = mmSet1Ps(OPRND1_32)
    val b = mmSet1Ps(OPRND2_32)
    val r = mmMaskzXorPs(0x5u, a, b)
    val e = mmSetPs(0.0f, XOR_32, 0.0f, XOR_32)
    assertEqM128(r, e)
}

public fun testMm256MaskXorPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val src = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskXorPs(src, 0x55u, a, b)
    val e = mm256SetPs(1.0f, XOR_32, 3.0f, XOR_32, 5.0f, XOR_32, 7.0f, XOR_32)
    assertEqM256(r, e)
}

public fun testMm256MaskzXorPs() {
    val a = mm256Set1Ps(OPRND1_32)
    val b = mm256Set1Ps(OPRND2_32)
    val r = mm256MaskzXorPs(0x55u, a, b)
    val e = mm256SetPs(0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32)
    assertEqM256(r, e)
}

public fun testMm512XorPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512XorPs(a, b)
    val e = mm512Set1Ps(XOR_32)
    assertEqM512(r, e)
}

public fun testMm512MaskXorPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val src = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskXorPs(src, 0x5555u, a, b)
    val e = mm512SetPs(1.0f, XOR_32, 3.0f, XOR_32, 5.0f, XOR_32, 7.0f, XOR_32, 9.0f, XOR_32, 11.0f, XOR_32, 13.0f, XOR_32, 15.0f, XOR_32)
    assertEqM512(r, e)
}

public fun testMm512MaskzXorPs() {
    val a = mm512Set1Ps(OPRND1_32)
    val b = mm512Set1Ps(OPRND2_32)
    val r = mm512MaskzXorPs(0x5555u, a, b)
    val e = mm512SetPs(0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32, 0.0f, XOR_32)
    assertEqM512(r, e)
}

public fun testMm256BroadcastF32x2() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256BroadcastF32x2(a)
    val e = mm256SetPs(3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskBroadcastF32x2() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetPs(5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f)
    val r = mm256MaskBroadcastF32x2(b, 0x69u, a)
    val e = mm256SetPs(5.0f, 4.0f, 3.0f, 8.0f, 3.0f, 10.0f, 11.0f, 4.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzBroadcastF32x2() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256MaskzBroadcastF32x2(0x69u, a)
    val e = mm256SetPs(0.0f, 4.0f, 3.0f, 0.0f, 3.0f, 0.0f, 0.0f, 4.0f)
    assertEqM256(r, e)
}

public fun testMm512BroadcastF32x2() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm512BroadcastF32x2(a)
    val e = mm512SetPs(3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f, 3.0f, 4.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskBroadcastF32x2() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm512SetPs(5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskBroadcastF32x2(b, 0x693Cu, a)
    val e = mm512SetPs(5.0f, 4.0f, 3.0f, 8.0f, 3.0f, 10.0f, 11.0f, 4.0f, 13.0f, 14.0f, 3.0f, 4.0f, 3.0f, 4.0f, 19.0f, 20.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzBroadcastF32x2() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm512MaskzBroadcastF32x2(0x693Cu, a)
    val e = mm512SetPs(0.0f, 4.0f, 3.0f, 0.0f, 3.0f, 0.0f, 0.0f, 4.0f, 0.0f, 0.0f, 3.0f, 4.0f, 3.0f, 4.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512BroadcastF32x8() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512BroadcastF32x8(a)
    val e = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskBroadcastF32x8() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm512MaskBroadcastF32x8(b, 0x693Cu, a)
    val e = mm512SetPs(9.0f, 2.0f, 3.0f, 12.0f, 5.0f, 14.0f, 15.0f, 8.0f, 17.0f, 18.0f, 3.0f, 4.0f, 5.0f, 6.0f, 23.0f, 24.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzBroadcastF32x8() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzBroadcastF32x8(0x693Cu, a)
    val e = mm512SetPs(0.0f, 2.0f, 3.0f, 0.0f, 5.0f, 0.0f, 0.0f, 8.0f, 0.0f, 0.0f, 3.0f, 4.0f, 5.0f, 6.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm256BroadcastF64x2() {
    val a = mmSetPd(1.0, 2.0)
    val r = mm256BroadcastF64x2(a)
    val e = mm256SetPd(1.0, 2.0, 1.0, 2.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskBroadcastF64x2() {
    val a = mmSetPd(1.0, 2.0)
    val b = mm256SetPd(3.0, 4.0, 5.0, 6.0)
    val r = mm256MaskBroadcastF64x2(b, 0x6u, a)
    val e = mm256SetPd(3.0, 2.0, 1.0, 6.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzBroadcastF64x2() {
    val a = mmSetPd(1.0, 2.0)
    val r = mm256MaskzBroadcastF64x2(0x6u, a)
    val e = mm256SetPd(0.0, 2.0, 1.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm512BroadcastF64x2() {
    val a = mmSetPd(1.0, 2.0)
    val r = mm512BroadcastF64x2(a)
    val e = mm512SetPd(1.0, 2.0, 1.0, 2.0, 1.0, 2.0, 1.0, 2.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskBroadcastF64x2() {
    val a = mmSetPd(1.0, 2.0)
    val b = mm512SetPd(3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val r = mm512MaskBroadcastF64x2(b, 0x69u, a)
    val e = mm512SetPd(3.0, 2.0, 1.0, 6.0, 1.0, 8.0, 9.0, 2.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzBroadcastF64x2() {
    val a = mmSetPd(1.0, 2.0)
    val r = mm512MaskzBroadcastF64x2(0x69u, a)
    val e = mm512SetPd(0.0, 2.0, 1.0, 0.0, 1.0, 0.0, 0.0, 2.0)
    assertEqM512d(r, e)
}

public fun testMmBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmBroadcastI32x2(a)
    val e = mmSetEpi32(3, 4, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mmSetEpi32(5, 6, 7, 8)
    val r = mmMaskBroadcastI32x2(b, 0x6u, a)
    val e = mmSetEpi32(5, 4, 3, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskzBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskzBroadcastI32x2(0x6u, a)
    val e = mmSetEpi32(0, 4, 3, 0)
    assertEqM128i(r, e)
}

public fun testMm256BroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mm256BroadcastI32x2(a)
    val e = mm256SetEpi32(3, 4, 3, 4, 3, 4, 3, 4)
    assertEqM256i(r, e)
}

public fun testMm256MaskBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mm256SetEpi32(5, 6, 7, 8, 9, 10, 11, 12)
    val r = mm256MaskBroadcastI32x2(b, 0x69u, a)
    val e = mm256SetEpi32(5, 4, 3, 8, 3, 10, 11, 4)
    assertEqM256i(r, e)
}

public fun testMm256MaskzBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mm256MaskzBroadcastI32x2(0x69u, a)
    val e = mm256SetEpi32(0, 4, 3, 0, 3, 0, 0, 4)
    assertEqM256i(r, e)
}

public fun testMm512BroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mm512BroadcastI32x2(a)
    val e = mm512SetEpi32(3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mm512SetEpi32(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
    val r = mm512MaskBroadcastI32x2(b, 0x693Cu, a)
    val e = mm512SetEpi32(5, 4, 3, 8, 3, 10, 11, 4, 13, 14, 3, 4, 3, 4, 19, 20)
    assertEqM512i(r, e)
}

public fun testMm512MaskzBroadcastI32x2() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mm512MaskzBroadcastI32x2(0x693Cu, a)
    val e = mm512SetEpi32(0, 4, 3, 0, 3, 0, 0, 4, 0, 0, 3, 4, 3, 4, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512BroadcastI32x8() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm512BroadcastI32x8(a)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastI32x8() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mm512SetEpi32(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512MaskBroadcastI32x8(b, 0x693Cu, a)
    val e = mm512SetEpi32(9, 2, 3, 12, 5, 14, 15, 8, 17, 18, 3, 4, 5, 6, 23, 24)
    assertEqM512i(r, e)
}

public fun testMm512MaskzBroadcastI32x8() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm512MaskzBroadcastI32x8(0x693Cu, a)
    val e = mm512SetEpi32(0, 2, 3, 0, 5, 0, 0, 8, 0, 0, 3, 4, 5, 6, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm256BroadcastI64x2() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mm256BroadcastI64x2(a)
    val e = mm256SetEpi64x(1L, 2L, 1L, 2L)
    assertEqM256i(r, e)
}

public fun testMm256MaskBroadcastI64x2() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mm256SetEpi64x(3L, 4L, 5L, 6L)
    val r = mm256MaskBroadcastI64x2(b, 0x6u, a)
    val e = mm256SetEpi64x(3L, 2L, 1L, 6L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzBroadcastI64x2() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mm256MaskzBroadcastI64x2(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 1L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512BroadcastI64x2() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mm512BroadcastI64x2(a)
    val e = mm512SetEpi64(1L, 2L, 1L, 2L, 1L, 2L, 1L, 2L)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastI64x2() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mm512SetEpi64(3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
    val r = mm512MaskBroadcastI64x2(b, 0x69u, a)
    val e = mm512SetEpi64(3L, 2L, 1L, 6L, 1L, 8L, 9L, 2L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzBroadcastI64x2() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mm512MaskzBroadcastI64x2(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 1L, 0L, 1L, 0L, 0L, 2L)
    assertEqM512i(r, e)
}

public fun testMm512Extractf32x8Ps() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512Extractf32x8Ps(a, 1)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskExtractf32x8Ps() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm512MaskExtractf32x8Ps(b, 0x69u, a, 1)
    val e = mm256SetPs(17.0f, 2.0f, 3.0f, 20.0f, 5.0f, 22.0f, 23.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskzExtractf32x8Ps() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskzExtractf32x8Ps(0x69u, a, 1)
    val e = mm256SetPs(0.0f, 2.0f, 3.0f, 0.0f, 5.0f, 0.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256Extractf64x2Pd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256Extractf64x2Pd(a, 1)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256MaskExtractf64x2Pd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mmSetPd(5.0, 6.0)
    val r = mm256MaskExtractf64x2Pd(b, 0x1u, a, 1)
    val e = mmSetPd(5.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256MaskzExtractf64x2Pd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzExtractf64x2Pd(0x1u, a, 1)
    val e = mmSetPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm512Extractf64x2Pd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512Extractf64x2Pd(a, 2)
    val e = mmSetPd(3.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMm512MaskExtractf64x2Pd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mmSetPd(9.0, 10.0)
    val r = mm512MaskExtractf64x2Pd(b, 0x1u, a, 2)
    val e = mmSetPd(9.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMm512MaskzExtractf64x2Pd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzExtractf64x2Pd(0x1u, a, 2)
    val e = mmSetPd(0.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMm512Extracti32x8Epi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512Extracti32x8Epi32(a, 1)
    val e = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm512MaskExtracti32x8Epi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512MaskExtracti32x8Epi32(b, 0x69u, a, 1)
    val e = mm256SetEpi32(17, 2, 3, 20, 5, 22, 23, 8)
    assertEqM256i(r, e)
}

public fun testMm512MaskzExtracti32x8Epi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzExtracti32x8Epi32(0x69u, a, 1)
    val e = mm256SetEpi32(0, 2, 3, 0, 5, 0, 0, 8)
    assertEqM256i(r, e)
}

public fun testMm256Extracti64x2Epi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Extracti64x2Epi64(a, 1)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256MaskExtracti64x2Epi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetEpi64x(5L, 6L)
    val r = mm256MaskExtracti64x2Epi64(b, 0x1u, a, 1)
    val e = mmSetEpi64x(5L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256MaskzExtracti64x2Epi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzExtracti64x2Epi64(0x1u, a, 1)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm512Extracti64x2Epi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Extracti64x2Epi64(a, 2)
    val e = mmSetEpi64x(3L, 4L)
    assertEqM128i(r, e)
}

public fun testMm512MaskExtracti64x2Epi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mmSetEpi64x(9L, 10L)
    val r = mm512MaskExtracti64x2Epi64(b, 0x1u, a, 2)
    val e = mmSetEpi64x(9L, 4L)
    assertEqM128i(r, e)
}

public fun testMm512MaskzExtracti64x2Epi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzExtracti64x2Epi64(0x1u, a, 2)
    val e = mmSetEpi64x(0L, 4L)
    assertEqM128i(r, e)
}

public fun testMm512Insertf32x8() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm512Insertf32x8(a, b, 1)
    val e = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskInsertf32x8() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val src = mm512SetPs(25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f, 33.0f, 34.0f, 35.0f, 36.0f, 37.0f, 38.0f, 39.0f, 40.0f)
    val r = mm512MaskInsertf32x8(src, 0x693Cu, a, b, 1)
    val e = mm512SetPs(25.0f, 18.0f, 19.0f, 28.0f, 21.0f, 30.0f, 31.0f, 24.0f, 33.0f, 34.0f, 11.0f, 12.0f, 13.0f, 14.0f, 39.0f, 40.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzInsertf32x8() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm512MaskzInsertf32x8(0x693Cu, a, b, 1)
    val e = mm512SetPs(0.0f, 18.0f, 19.0f, 0.0f, 21.0f, 0.0f, 0.0f, 24.0f, 0.0f, 0.0f, 11.0f, 12.0f, 13.0f, 14.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm256Insertf64x2() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mmSetPd(5.0, 6.0)
    val r = mm256Insertf64x2(a, b, 1)
    val e = mm256SetPd(5.0, 6.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskInsertf64x2() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mmSetPd(5.0, 6.0)
    val src = mm256SetPd(7.0, 8.0, 9.0, 10.0)
    val r = mm256MaskInsertf64x2(src, 0x6u, a, b, 1)
    val e = mm256SetPd(7.0, 6.0, 3.0, 10.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzInsertf64x2() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mmSetPd(5.0, 6.0)
    val r = mm256MaskzInsertf64x2(0x6u, a, b, 1)
    val e = mm256SetPd(0.0, 6.0, 3.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm512Insertf64x2() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mmSetPd(9.0, 10.0)
    val r = mm512Insertf64x2(a, b, 2)
    val e = mm512SetPd(1.0, 2.0, 9.0, 10.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskInsertf64x2() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mmSetPd(9.0, 10.0)
    val src = mm512SetPd(11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0)
    val r = mm512MaskInsertf64x2(src, 0x69u, a, b, 2)
    val e = mm512SetPd(11.0, 2.0, 9.0, 14.0, 5.0, 16.0, 17.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzInsertf64x2() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mmSetPd(9.0, 10.0)
    val r = mm512MaskzInsertf64x2(0x69u, a, b, 2)
    val e = mm512SetPd(0.0, 2.0, 9.0, 0.0, 5.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512Inserti32x8() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512Inserti32x8(a, b, 1)
    val e = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskInserti32x8() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val src = mm512SetEpi32(25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40)
    val r = mm512MaskInserti32x8(src, 0x693Cu, a, b, 1)
    val e = mm512SetEpi32(25, 18, 19, 28, 21, 30, 31, 24, 33, 34, 11, 12, 13, 14, 39, 40)
    assertEqM512i(r, e)
}

public fun testMm512MaskzInserti32x8() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512MaskzInserti32x8(0x693Cu, a, b, 1)
    val e = mm512SetEpi32(0, 18, 19, 0, 21, 0, 0, 24, 0, 0, 11, 12, 13, 14, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm256Inserti64x2() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetEpi64x(5L, 6L)
    val r = mm256Inserti64x2(a, b, 1)
    val e = mm256SetEpi64x(5L, 6L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskInserti64x2() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetEpi64x(5L, 6L)
    val src = mm256SetEpi64x(7L, 8L, 9L, 10L)
    val r = mm256MaskInserti64x2(src, 0x6u, a, b, 1)
    val e = mm256SetEpi64x(7L, 6L, 3L, 10L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzInserti64x2() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetEpi64x(5L, 6L)
    val r = mm256MaskzInserti64x2(0x6u, a, b, 1)
    val e = mm256SetEpi64x(0L, 6L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512Inserti64x2() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mmSetEpi64x(9L, 10L)
    val r = mm512Inserti64x2(a, b, 2)
    val e = mm512SetEpi64(1L, 2L, 9L, 10L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskInserti64x2() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mmSetEpi64x(9L, 10L)
    val src = mm512SetEpi64(11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L)
    val r = mm512MaskInserti64x2(src, 0x69u, a, b, 2)
    val e = mm512SetEpi64(11L, 2L, 9L, 14L, 5L, 16L, 17L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzInserti64x2() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mmSetEpi64x(9L, 10L)
    val r = mm512MaskzInserti64x2(0x69u, a, b, 2)
    val e = mm512SetEpi64(0L, 2L, 9L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundepi64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512CvtRoundepi64Pd(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtRoundepi64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm512MaskCvtRoundepi64Pd(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(9.0, 2.0, 3.0, 12.0, 5.0, 14.0, 15.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzCvtRoundepi64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtRoundepi64Pd(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(0.0, 2.0, 3.0, 0.0, 5.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMmCvtepi64Pd() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmCvtepi64Pd(a)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtepi64Pd() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskCvtepi64Pd(b, 0x1u, a)
    val e = mmSetPd(3.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzCvtepi64Pd() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmMaskzCvtepi64Pd(0x1u, a)
    val e = mmSetPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256Cvtepi64Pd() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepi64Pd(a)
    val e = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskCvtepi64Pd() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256MaskCvtepi64Pd(b, 0x6u, a)
    val e = mm256SetPd(5.0, 2.0, 3.0, 8.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzCvtepi64Pd() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepi64Pd(0x6u, a)
    val e = mm256SetPd(0.0, 2.0, 3.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm512Cvtepi64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Cvtepi64Pd(a)
    val e = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtepi64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm512MaskCvtepi64Pd(b, 0x69u, a)
    val e = mm512SetPd(9.0, 2.0, 3.0, 12.0, 5.0, 14.0, 15.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzCvtepi64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtepi64Pd(0x69u, a)
    val e = mm512SetPd(0.0, 2.0, 3.0, 0.0, 5.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512CvtRoundepi64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512CvtRoundepi64Ps(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskCvtRoundepi64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskCvtRoundepi64Ps(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPs(9.0f, 2.0f, 3.0f, 12.0f, 5.0f, 14.0f, 15.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskzCvtRoundepi64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtRoundepi64Ps(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPs(0.0f, 2.0f, 3.0f, 0.0f, 5.0f, 0.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmCvtepi64Ps() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmCvtepi64Ps(a)
    val e = mmSetPs(0.0f, 0.0f, 1.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtepi64Ps() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetPs(3.0f, 4.0f, 5.0f, 6.0f)
    val r = mmMaskCvtepi64Ps(b, 0x1u, a)
    val e = mmSetPs(0.0f, 0.0f, 5.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzCvtepi64Ps() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmMaskzCvtepi64Ps(0x1u, a)
    val e = mmSetPs(0.0f, 0.0f, 0.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMm256Cvtepi64Ps() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepi64Ps(a)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256MaskCvtepi64Ps() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskCvtepi64Ps(b, 0x6u, a)
    val e = mmSetPs(5.0f, 2.0f, 3.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMm256MaskzCvtepi64Ps() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepi64Ps(0x6u, a)
    val e = mmSetPs(0.0f, 2.0f, 3.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMm512Cvtepi64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Cvtepi64Ps(a)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskCvtepi64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskCvtepi64Ps(b, 0x69u, a)
    val e = mm256SetPs(9.0f, 2.0f, 3.0f, 12.0f, 5.0f, 14.0f, 15.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskzCvtepi64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtepi64Ps(0x69u, a)
    val e = mm256SetPs(0.0f, 2.0f, 3.0f, 0.0f, 5.0f, 0.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512CvtRoundepu64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512CvtRoundepu64Pd(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtRoundepu64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm512MaskCvtRoundepu64Pd(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(9.0, 2.0, 3.0, 12.0, 5.0, 14.0, 15.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzCvtRoundepu64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtRoundepu64Pd(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(0.0, 2.0, 3.0, 0.0, 5.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMmCvtepu64Pd() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmCvtepu64Pd(a)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtepu64Pd() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskCvtepu64Pd(b, 0x1u, a)
    val e = mmSetPd(3.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzCvtepu64Pd() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmMaskzCvtepu64Pd(0x1u, a)
    val e = mmSetPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256Cvtepu64Pd() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepu64Pd(a)
    val e = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskCvtepu64Pd() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256MaskCvtepu64Pd(b, 0x6u, a)
    val e = mm256SetPd(5.0, 2.0, 3.0, 8.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzCvtepu64Pd() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepu64Pd(0x6u, a)
    val e = mm256SetPd(0.0, 2.0, 3.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm512Cvtepu64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Cvtepu64Pd(a)
    val e = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtepu64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm512MaskCvtepu64Pd(b, 0x69u, a)
    val e = mm512SetPd(9.0, 2.0, 3.0, 12.0, 5.0, 14.0, 15.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzCvtepu64Pd() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtepu64Pd(0x69u, a)
    val e = mm512SetPd(0.0, 2.0, 3.0, 0.0, 5.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512CvtRoundepu64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512CvtRoundepu64Ps(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskCvtRoundepu64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskCvtRoundepu64Ps(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPs(9.0f, 2.0f, 3.0f, 12.0f, 5.0f, 14.0f, 15.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskzCvtRoundepu64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtRoundepu64Ps(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPs(0.0f, 2.0f, 3.0f, 0.0f, 5.0f, 0.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmCvtepu64Ps() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmCvtepu64Ps(a)
    val e = mmSetPs(0.0f, 0.0f, 1.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtepu64Ps() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetPs(3.0f, 4.0f, 5.0f, 6.0f)
    val r = mmMaskCvtepu64Ps(b, 0x1u, a)
    val e = mmSetPs(0.0f, 0.0f, 5.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzCvtepu64Ps() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmMaskzCvtepu64Ps(0x1u, a)
    val e = mmSetPs(0.0f, 0.0f, 0.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMm256Cvtepu64Ps() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepu64Ps(a)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256MaskCvtepu64Ps() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskCvtepu64Ps(b, 0x6u, a)
    val e = mmSetPs(5.0f, 2.0f, 3.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMm256MaskzCvtepu64Ps() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepu64Ps(0x6u, a)
    val e = mmSetPs(0.0f, 2.0f, 3.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMm512Cvtepu64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Cvtepu64Ps(a)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskCvtepu64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskCvtepu64Ps(b, 0x69u, a)
    val e = mm256SetPs(9.0f, 2.0f, 3.0f, 12.0f, 5.0f, 14.0f, 15.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512MaskzCvtepu64Ps() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtepu64Ps(0x69u, a)
    val e = mm256SetPs(0.0f, 2.0f, 3.0f, 0.0f, 5.0f, 0.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512CvtRoundpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvtRoundpdEpi64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtRoundpdEpi64(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvtRoundpdEpi64(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvtpdEpi64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmCvtpdEpi64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtpdEpi64() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetEpi64x(3L, 4L)
    val r = mmMaskCvtpdEpi64(b, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtpdEpi64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskzCvtpdEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvtpdEpi64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CvtpdEpi64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtpdEpi64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvtpdEpi64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtpdEpi64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzCvtpdEpi64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvtpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvtpdEpi64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtpdEpi64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvtpdEpi64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvtRoundpsEpi64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtRoundpsEpi64(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvtRoundpsEpi64(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvtpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtpsEpi64(a)
    val e = mmSetEpi64x(3L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetEpi64x(5L, 6L)
    val r = mmMaskCvtpsEpi64(b, 0x1u, a)
    val e = mmSetEpi64x(5L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvtpsEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 4L)
    assertEqM128i(r, e)
}

public fun testMm256CvtpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256CvtpsEpi64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvtpsEpi64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256MaskzCvtpsEpi64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvtpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvtpsEpi64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtpsEpi64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvtpsEpi64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvtRoundpdEpu64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtRoundpdEpu64(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvtRoundpdEpu64(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvtpdEpu64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmCvtpdEpu64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtpdEpu64() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetEpi64x(3L, 4L)
    val r = mmMaskCvtpdEpu64(b, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtpdEpu64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskzCvtpdEpu64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvtpdEpu64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CvtpdEpu64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtpdEpu64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvtpdEpu64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtpdEpu64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzCvtpdEpu64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvtpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvtpdEpu64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtpdEpu64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvtpdEpu64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvtRoundpsEpu64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtRoundpsEpu64(b, 0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvtRoundpsEpu64(0x69u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvtpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtpsEpu64(a)
    val e = mmSetEpi64x(3L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetEpi64x(5L, 6L)
    val r = mmMaskCvtpsEpu64(b, 0x1u, a)
    val e = mmSetEpi64x(5L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvtpsEpu64(0x1u, a)
    val e = mmSetEpi64x(0L, 4L)
    assertEqM128i(r, e)
}

public fun testMm256CvtpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256CvtpsEpu64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvtpsEpu64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256MaskzCvtpsEpu64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvtpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvtpsEpu64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvtpsEpu64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvtpsEpu64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvttRoundpdEpi64(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttRoundpdEpi64(b, 0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvttRoundpdEpi64(0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvttpdEpi64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmCvttpdEpi64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttpdEpi64() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetEpi64x(3L, 4L)
    val r = mmMaskCvttpdEpi64(b, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttpdEpi64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskzCvttpdEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvttpdEpi64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CvttpdEpi64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttpdEpi64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvttpdEpi64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttpdEpi64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzCvttpdEpi64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvttpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvttpdEpi64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttpdEpi64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttpdEpi64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvttpdEpi64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvttRoundpsEpi64(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttRoundpsEpi64(b, 0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvttRoundpsEpi64(0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvttpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvttpsEpi64(a)
    val e = mmSetEpi64x(3L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetEpi64x(5L, 6L)
    val r = mmMaskCvttpsEpi64(b, 0x1u, a)
    val e = mmSetEpi64x(5L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvttpsEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 4L)
    assertEqM128i(r, e)
}

public fun testMm256CvttpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256CvttpsEpi64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvttpsEpi64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttpsEpi64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256MaskzCvttpsEpi64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvttpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvttpsEpi64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttpsEpi64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttpsEpi64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvttpsEpi64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvttRoundpdEpu64(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttRoundpdEpu64(b, 0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvttRoundpdEpu64(0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvttpdEpu64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmCvttpdEpu64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttpdEpu64() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetEpi64x(3L, 4L)
    val r = mmMaskCvttpdEpu64(b, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttpdEpu64() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskzCvttpdEpu64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvttpdEpu64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CvttpdEpu64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttpdEpu64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvttpdEpu64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttpdEpu64() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzCvttpdEpu64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvttpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvttpdEpu64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttpdEpu64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttpdEpu64() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvttpdEpu64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvttRoundpsEpu64(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttRoundpsEpu64(b, 0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvttRoundpsEpu64(0x69u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvttpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvttpsEpu64(a)
    val e = mmSetEpi64x(3L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetEpi64x(5L, 6L)
    val r = mmMaskCvttpsEpu64(b, 0x1u, a)
    val e = mmSetEpi64x(5L, 4L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvttpsEpu64(0x1u, a)
    val e = mmSetEpi64x(0L, 4L)
    assertEqM128i(r, e)
}

public fun testMm256CvttpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256CvttpsEpu64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskCvttpsEpu64(b, 0x6u, a)
    val e = mm256SetEpi64x(5L, 2L, 3L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttpsEpu64() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256MaskzCvttpsEpu64(0x6u, a)
    val e = mm256SetEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512CvttpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512CvttpsEpu64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskCvttpsEpu64(b, 0x69u, a)
    val e = mm512SetEpi64(9L, 2L, 3L, 12L, 5L, 14L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttpsEpu64() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm512MaskzCvttpsEpu64(0x69u, a)
    val e = mm512SetEpi64(0L, 2L, 3L, 0L, 5L, 0L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmMulloEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(3L, 4L)
    val r = mmMulloEpi64(a, b)
    val e = mmSetEpi64x(3L, 8L)
    assertEqM128i(r, e)
}

public fun testMmMaskMulloEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(3L, 4L)
    val c = mmSetEpi64x(5L, 6L)
    val r = mmMaskMulloEpi64(c, 0x1u, a, b)
    val e = mmSetEpi64x(5L, 8L)
    assertEqM128i(r, e)
}

public fun testMmMaskzMulloEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(3L, 4L)
    val r = mmMaskzMulloEpi64(0x1u, a, b)
    val e = mmSetEpi64x(0L, 8L)
    assertEqM128i(r, e)
}

public fun testMm256MulloEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MulloEpi64(a, b)
    val e = mm256SetEpi64x(5L, 12L, 21L, 32L)
    assertEqM256i(r, e)
}

public fun testMm256MaskMulloEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val c = mm256SetEpi64x(9L, 10L, 11L, 12L)
    val r = mm256MaskMulloEpi64(c, 0x6u, a, b)
    val e = mm256SetEpi64x(9L, 12L, 21L, 12L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzMulloEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256MaskzMulloEpi64(0x6u, a, b)
    val e = mm256SetEpi64x(0L, 12L, 21L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512MulloEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MulloEpi64(a, b)
    val e = mm512SetEpi64(9L, 20L, 33L, 48L, 65L, 84L, 105L, 128L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulloEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val c = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512MaskMulloEpi64(c, 0x69u, a, b)
    val e = mm512SetEpi64(17L, 20L, 33L, 20L, 65L, 22L, 23L, 128L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzMulloEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val r = mm512MaskzMulloEpi64(0x69u, a, b)
    val e = mm512SetEpi64(0L, 20L, 33L, 0L, 65L, 0L, 0L, 128L)
    assertEqM512i(r, e)
}

public fun testCvtmask8U32() {
    val a: Mmask8 = 0x69u
    val r = cvtmask8U32(a)
    val e: UInt = 0x69u
    check(r == e)
}

public fun testCvtu32Mask8() {
    val a: UInt = 0x69u
    val r = cvtu32Mask8(a)
    val e: Mmask8 = 0x69u
    check(r == e)
}

public fun testKaddMask16() {
    val a: Mmask16 = 27549u
    val b: Mmask16 = 23434u
    val r = kaddMask16(a, b)
    val e: Mmask16 = 50983u
    check(r == e)
}

public fun testKaddMask8() {
    val a: Mmask8 = 98u
    val b: Mmask8 = 117u
    val r = kaddMask8(a, b)
    val e: Mmask8 = 215u
    check(r == e)
}

public fun testKandMask8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB3u
    val r = kandMask8(a, b)
    val e: Mmask8 = 0x21u
    check(r == e)
}

public fun testKandnMask8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB3u
    val r = kandnMask8(a, b)
    val e: Mmask8 = 0x92u
    check(r == e)
}

public fun testKnotMask8() {
    val a: Mmask8 = 0x69u
    val r = knotMask8(a)
    val e: Mmask8 = 0x96u
    check(r == e)
}

public fun testKorMask8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB3u
    val r = korMask8(a, b)
    val e: Mmask8 = 0xFBu
    check(r == e)
}

public fun testKxnorMask8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB3u
    val r = kxnorMask8(a, b)
    val e: Mmask8 = 0x25u
    check(r == e)
}

public fun testKxorMask8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB3u
    val r = kxorMask8(a, b)
    val e: Mmask8 = 0xDAu
    check(r == e)
}

public fun testKortestMask8U8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB6u
    val localStorageL10009C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var allOnes by localStorageL10009C8
    val r = kortestMask8U8(a, b, RustMutableReference(localStorageL10009C8.asMutPtr(), localStorageL10009C8.layout))
    check(r == 0u.toUByte())
    check(allOnes == 1u.toUByte())
}

public fun testKortestcMask8U8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB6u
    val r = kortestcMask8U8(a, b)
    check(r == 1u.toUByte())
}

public fun testKortestzMask8U8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0xB6u
    val r = kortestzMask8U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKshiftliMask8() {
    val a: Mmask8 = 0x69u
    val r = kshiftliMask8(a, 3)
    val e: Mmask8 = 0x48u
    check(r == e)
    val r1 = kshiftliMask8(a, 7)
    val e1: Mmask8 = 0x80u
    check(r1 == e1)
    val r2 = kshiftliMask8(a, 8)
    val e2: Mmask8 = 0x0u
    check(r2 == e2)
    val r3 = kshiftliMask8(a, 9)
    val e3: Mmask8 = 0x0u
    check(r3 == e3)
}

public fun testKshiftriMask8() {
    val a: Mmask8 = 0xA9u
    val r = kshiftriMask8(a, 3)
    val e: Mmask8 = 0x15u
    check(r == e)
    val r1 = kshiftriMask8(a, 7)
    val e1: Mmask8 = 0x1u
    check(r1 == e1)
    val r2 = kshiftriMask8(a, 8)
    val e2: Mmask8 = 0x0u
    check(r2 == e2)
    val r3 = kshiftriMask8(a, 9)
    val e3: Mmask8 = 0x0u
    check(r3 == e3)
}

public fun testKtestMask8U8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0x96u
    val localStorageL10075C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var andNot by localStorageL10075C8
    val r = ktestMask8U8(a, b, RustMutableReference(localStorageL10075C8.asMutPtr(), localStorageL10075C8.layout))
    check(r == 1u.toUByte())
    check(andNot == 0u.toUByte())
}

public fun testKtestcMask8U8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0x96u
    val r = ktestcMask8U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKtestzMask8U8() {
    val a: Mmask8 = 0x69u
    val b: Mmask8 = 0x96u
    val r = ktestzMask8U8(a, b)
    check(r == 1u.toUByte())
}

public fun testKtestMask16U8() {
    val a: Mmask16 = 0x693Cu
    val b: Mmask16 = 0x96C3u
    val localStorageL10101C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var andNot by localStorageL10101C8
    val r = ktestMask16U8(a, b, RustMutableReference(localStorageL10101C8.asMutPtr(), localStorageL10101C8.layout))
    check(r == 1u.toUByte())
    check(andNot == 0u.toUByte())
}

public fun testKtestcMask16U8() {
    val a: Mmask16 = 0x693Cu
    val b: Mmask16 = 0x96C3u
    val r = ktestcMask16U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKtestzMask16U8() {
    val a: Mmask16 = 0x693Cu
    val b: Mmask16 = 0x96C3u
    val r = ktestzMask16U8(a, b)
    check(r == 1u.toUByte())
}

public fun testLoadMask8() {
    val r = loadMask8(a)
    val e: Mmask8 = 0x69u
    check(r == e)
}

public fun testStoreMask8() {
    val a: Mmask8 = 0x69u
    val localStorageL10134C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var r by localStorageL10134C8
    run {
        storeMask8(RustMutableReference(localStorageL10134C8.asMutPtr(), localStorageL10134C8.layout), a)
    }
    val e: Mmask8 = 0x69u
    check(r == e)
}

public fun testMmMovepi32Mask() {
    val a = mmSetEpi32(0, -2, -3, 4)
    val r = mmMovepi32Mask(a)
    val e = 0x6
    check(r == e)
}

public fun testMm256Movepi32Mask() {
    val a = mm256SetEpi32(0, -2, -3, 4, -5, 6, 7, -8)
    val r = mm256Movepi32Mask(a)
    val e = 0x69
    check(r == e)
}

public fun testMm512Movepi32Mask() {
    val a = mm512SetEpi32(0, -2, -3, 4, -5, 6, 7, -8, 9, 10, -11, -12, -13, -14, 15, 16)
    val r = mm512Movepi32Mask(a)
    val e = 0x693C
    check(r == e)
}

public fun testMmMovepi64Mask() {
    val a = mmSetEpi64x(0L, -2L)
    val r = mmMovepi64Mask(a)
    val e = 0x1
    check(r == e)
}

public fun testMm256Movepi64Mask() {
    val a = mm256SetEpi64x(0L, -2L, -3L, 4L)
    val r = mm256Movepi64Mask(a)
    val e = 0x6
    check(r == e)
}

public fun testMm512Movepi64Mask() {
    val a = mm512SetEpi64(0L, -2L, -3L, 4L, -5L, 6L, 7L, -8L)
    val r = mm512Movepi64Mask(a)
    val e = 0x69
    check(r == e)
}

public fun testMmMovmEpi32() {
    val a = 0x6
    val r = mmMovmEpi32(a)
    val e = mmSetEpi32(0, -1, -1, 0)
    assertEqM128i(r, e)
}

public fun testMm256MovmEpi32() {
    val a = 0x69
    val r = mm256MovmEpi32(a)
    val e = mm256SetEpi32(0, -1, -1, 0, -1, 0, 0, -1)
    assertEqM256i(r, e)
}

public fun testMm512MovmEpi32() {
    val a = 0x693C
    val r = mm512MovmEpi32(a)
    val e = mm512SetEpi32(0, -1, -1, 0, -1, 0, 0, -1, 0, 0, -1, -1, -1, -1, 0, 0)
    assertEqM512i(r, e)
}

public fun testMmMovmEpi64() {
    val a = 0x1
    val r = mmMovmEpi64(a)
    val e = mmSetEpi64x(0L, -1L)
    assertEqM128i(r, e)
}

public fun testMm256MovmEpi64() {
    val a = 0x6
    val r = mm256MovmEpi64(a)
    val e = mm256SetEpi64x(0L, -1L, -1L, 0L)
    assertEqM256i(r, e)
}

public fun testMm512MovmEpi64() {
    val a = 0x69
    val r = mm512MovmEpi64(a)
    val e = mm512SetEpi64(0L, -1L, -1L, 0L, -1L, 0L, 0L, -1L)
    assertEqM512i(r, e)
}

public fun testMm512RangeRoundPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0, 7.0)
    val r = mm512RangeRoundPd(a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mm512SetPd(2.0, 2.0, 4.0, 4.0, 6.0, 6.0, 8.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskRangeRoundPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0, 7.0)
    val c = mm512SetPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm512MaskRangeRoundPd(c, 0x69u, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mm512SetPd(9.0, 2.0, 4.0, 12.0, 6.0, 14.0, 15.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzRangeRoundPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0, 7.0)
    val r = mm512MaskzRangeRoundPd(0x69u, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mm512SetPd(0.0, 2.0, 4.0, 0.0, 6.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMmRangePd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(2.0, 1.0)
    val r = mmRangePd(a, b, 0x5)
    val e = mmSetPd(2.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskRangePd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(2.0, 1.0)
    val c = mmSetPd(3.0, 4.0)
    val r = mmMaskRangePd(c, 0x1u, a, b, 0x5)
    val e = mmSetPd(3.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzRangePd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(2.0, 1.0)
    val r = mmMaskzRangePd(0x1u, a, b, 0x5)
    val e = mmSetPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256RangePd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(2.0, 1.0, 4.0, 3.0)
    val r = mm256RangePd(a, b, 0x5)
    val e = mm256SetPd(2.0, 2.0, 4.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskRangePd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(2.0, 1.0, 4.0, 3.0)
    val c = mm256SetPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256MaskRangePd(c, 0x6u, a, b, 0x5)
    val e = mm256SetPd(5.0, 2.0, 4.0, 8.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzRangePd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(2.0, 1.0, 4.0, 3.0)
    val r = mm256MaskzRangePd(0x6u, a, b, 0x5)
    val e = mm256SetPd(0.0, 2.0, 4.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm512RangePd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0, 7.0)
    val r = mm512RangePd(a, b, 0x5)
    val e = mm512SetPd(2.0, 2.0, 4.0, 4.0, 6.0, 6.0, 8.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskRangePd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0, 7.0)
    val c = mm512SetPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm512MaskRangePd(c, 0x69u, a, b, 0x5)
    val e = mm512SetPd(9.0, 2.0, 4.0, 12.0, 6.0, 14.0, 15.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzRangePd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0, 7.0)
    val r = mm512MaskzRangePd(0x69u, a, b, 0x5)
    val e = mm512SetPd(0.0, 2.0, 4.0, 0.0, 6.0, 0.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512RangeRoundPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f, 15.0f)
    val r = mm512RangeRoundPs(a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mm512SetPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f, 10.0f, 10.0f, 12.0f, 12.0f, 14.0f, 14.0f, 16.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskRangeRoundPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f, 15.0f)
    val c = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512MaskRangeRoundPs(c, 0x693Cu, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mm512SetPs(17.0f, 2.0f, 4.0f, 20.0f, 6.0f, 22.0f, 23.0f, 8.0f, 25.0f, 26.0f, 12.0f, 12.0f, 14.0f, 14.0f, 31.0f, 32.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzRangeRoundPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f, 15.0f)
    val r = mm512MaskzRangeRoundPs(0x693Cu, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mm512SetPs(0.0f, 2.0f, 4.0f, 0.0f, 6.0f, 0.0f, 0.0f, 8.0f, 0.0f, 0.0f, 12.0f, 12.0f, 14.0f, 14.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMmRangePs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(2.0f, 1.0f, 4.0f, 3.0f)
    val r = mmRangePs(a, b, 0x5)
    val e = mmSetPs(2.0f, 2.0f, 4.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskRangePs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(2.0f, 1.0f, 4.0f, 3.0f)
    val c = mmSetPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmMaskRangePs(c, 0x6u, a, b, 0x5)
    val e = mmSetPs(5.0f, 2.0f, 4.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzRangePs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(2.0f, 1.0f, 4.0f, 3.0f)
    val r = mmMaskzRangePs(0x6u, a, b, 0x5)
    val e = mmSetPs(0.0f, 2.0f, 4.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMm256RangePs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f)
    val r = mm256RangePs(a, b, 0x5)
    val e = mm256SetPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskRangePs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f)
    val c = mm256SetPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm256MaskRangePs(c, 0x69u, a, b, 0x5)
    val e = mm256SetPs(9.0f, 2.0f, 4.0f, 12.0f, 6.0f, 14.0f, 15.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzRangePs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f)
    val r = mm256MaskzRangePs(0x69u, a, b, 0x5)
    val e = mm256SetPs(0.0f, 2.0f, 4.0f, 0.0f, 6.0f, 0.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512RangePs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f, 15.0f)
    val r = mm512RangePs(a, b, 0x5)
    val e = mm512SetPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f, 10.0f, 10.0f, 12.0f, 12.0f, 14.0f, 14.0f, 16.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskRangePs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f, 15.0f)
    val c = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512MaskRangePs(c, 0x693Cu, a, b, 0x5)
    val e = mm512SetPs(17.0f, 2.0f, 4.0f, 20.0f, 6.0f, 22.0f, 23.0f, 8.0f, 25.0f, 26.0f, 12.0f, 12.0f, 14.0f, 14.0f, 31.0f, 32.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzRangePs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f, 15.0f)
    val r = mm512MaskzRangePs(0x693Cu, a, b, 0x5)
    val e = mm512SetPs(0.0f, 2.0f, 4.0f, 0.0f, 6.0f, 0.0f, 0.0f, 8.0f, 0.0f, 0.0f, 12.0f, 12.0f, 14.0f, 14.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMmRangeRoundSd() {
    val a = mmSetSd(1.0)
    val b = mmSetSd(2.0)
    val r = mmRangeRoundSd(a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mmSetSd(2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskRangeRoundSd() {
    val a = mmSetSd(1.0)
    val b = mmSetSd(2.0)
    val c = mmSetSd(3.0)
    val r = mmMaskRangeRoundSd(c, 0x0u, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mmSetSd(3.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzRangeRoundSd() {
    val a = mmSetSd(1.0)
    val b = mmSetSd(2.0)
    val r = mmMaskzRangeRoundSd(0x0u, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mmSetSd(0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskRangeSd() {
    val a = mmSetSd(1.0)
    val b = mmSetSd(2.0)
    val c = mmSetSd(3.0)
    val r = mmMaskRangeSd(c, 0x0u, a, b, 0x5)
    val e = mmSetSd(3.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzRangeSd() {
    val a = mmSetSd(1.0)
    val b = mmSetSd(2.0)
    val r = mmMaskzRangeSd(0x0u, a, b, 0x5)
    val e = mmSetSd(0.0)
    assertEqM128d(r, e)
}

public fun testMmRangeRoundSs() {
    val a = mmSetSs(1.0f)
    val b = mmSetSs(2.0f)
    val r = mmRangeRoundSs(a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mmSetSs(2.0f)
    assertEqM128(r, e)
}

public fun testMmMaskRangeRoundSs() {
    val a = mmSetSs(1.0f)
    val b = mmSetSs(2.0f)
    val c = mmSetSs(3.0f)
    val r = mmMaskRangeRoundSs(c, 0x0u, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mmSetSs(3.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzRangeRoundSs() {
    val a = mmSetSs(1.0f)
    val b = mmSetSs(2.0f)
    val r = mmMaskzRangeRoundSs(0x0u, a, b, 0x5, MM_FROUND_NO_EXC)
    val e = mmSetSs(0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskRangeSs() {
    val a = mmSetSs(1.0f)
    val b = mmSetSs(2.0f)
    val c = mmSetSs(3.0f)
    val r = mmMaskRangeSs(c, 0x0u, a, b, 0x5)
    val e = mmSetSs(3.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzRangeSs() {
    val a = mmSetSs(1.0f)
    val b = mmSetSs(2.0f)
    val r = mmMaskzRangeSs(0x0u, a, b, 0x5)
    val e = mmSetSs(0.0f)
    assertEqM128(r, e)
}

public fun testMm512ReduceRoundPd() {
    val a = mm512SetPd(0.25, 0.50, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0)
    val r = mm512ReduceRoundPd(a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPd(0.25, 0.0, 0.25, 0.0, 0.25, 0.0, 0.25, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskReduceRoundPd() {
    val a = mm512SetPd(0.25, 0.50, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0)
    val src = mm512SetPd(3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val r = mm512MaskReduceRoundPd(src, 0x69u, a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPd(3.0, 0.0, 0.25, 6.0, 0.25, 8.0, 9.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzReduceRoundPd() {
    val a = mm512SetPd(0.25, 0.50, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0)
    val r = mm512MaskzReduceRoundPd(0x69u, a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPd(0.0, 0.0, 0.25, 0.0, 0.25, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMmReducePd() {
    val a = mmSetPd(0.25, 0.50)
    val r = mmReducePd(a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPd(0.25, 0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskReducePd() {
    val a = mmSetPd(0.25, 0.50)
    val src = mmSetPd(3.0, 4.0)
    val r = mmMaskReducePd(src, 0x1u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPd(3.0, 0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzReducePd() {
    val a = mmSetPd(0.25, 0.50)
    val r = mmMaskzReducePd(0x1u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r, e)
}

public fun testMm256ReducePd() {
    val a = mm256SetPd(0.25, 0.50, 0.75, 1.0)
    val r = mm256ReducePd(a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPd(0.25, 0.0, 0.25, 0.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskReducePd() {
    val a = mm256SetPd(0.25, 0.50, 0.75, 1.0)
    val src = mm256SetPd(3.0, 4.0, 5.0, 6.0)
    val r = mm256MaskReducePd(src, 0x6u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPd(3.0, 0.0, 0.25, 6.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzReducePd() {
    val a = mm256SetPd(0.25, 0.50, 0.75, 1.0)
    val r = mm256MaskzReducePd(0x6u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPd(0.0, 0.0, 0.25, 0.0)
    assertEqM256d(r, e)
}

public fun testMm512ReducePd() {
    val a = mm512SetPd(0.25, 0.50, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0)
    val r = mm512ReducePd(a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPd(0.25, 0.0, 0.25, 0.0, 0.25, 0.0, 0.25, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskReducePd() {
    val a = mm512SetPd(0.25, 0.50, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0)
    val src = mm512SetPd(3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    val r = mm512MaskReducePd(src, 0x69u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPd(3.0, 0.0, 0.25, 6.0, 0.25, 8.0, 9.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzReducePd() {
    val a = mm512SetPd(0.25, 0.50, 0.75, 1.0, 1.25, 1.50, 1.75, 2.0)
    val r = mm512MaskzReducePd(0x69u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPd(0.0, 0.0, 0.25, 0.0, 0.25, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512ReduceRoundPs() {
    val a = mm512SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 2.25f, 2.50f, 2.75f, 3.0f, 3.25f, 3.50f, 3.75f, 4.0f)
    val r = mm512ReduceRoundPs(a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPs(0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskReduceRoundPs() {
    val a = mm512SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 2.25f, 2.50f, 2.75f, 3.0f, 3.25f, 3.50f, 3.75f, 4.0f)
    val src = mm512SetPs(5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskReduceRoundPs(src, 0x693Cu, a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPs(5.0f, 0.0f, 0.25f, 8.0f, 0.25f, 10.0f, 11.0f, 0.0f, 13.0f, 14.0f, 0.25f, 0.0f, 0.25f, 0.0f, 19.0f, 20.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzReduceRoundPs() {
    val a = mm512SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 2.25f, 2.50f, 2.75f, 3.0f, 3.25f, 3.50f, 3.75f, 4.0f)
    val r = mm512MaskzReduceRoundPs(0x693Cu, a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPs(0.0f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMmReducePs() {
    val a = mmSetPs(0.25f, 0.50f, 0.75f, 1.0f)
    val r = mmReducePs(a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPs(0.25f, 0.0f, 0.25f, 0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskReducePs() {
    val a = mmSetPs(0.25f, 0.50f, 0.75f, 1.0f)
    val src = mmSetPs(2.0f, 3.0f, 4.0f, 5.0f)
    val r = mmMaskReducePs(src, 0x6u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPs(2.0f, 0.0f, 0.25f, 5.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzReducePs() {
    val a = mmSetPs(0.25f, 0.50f, 0.75f, 1.0f)
    val r = mmMaskzReducePs(0x6u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPs(0.0f, 0.0f, 0.25f, 0.0f)
    assertEqM128(r, e)
}

public fun testMm256ReducePs() {
    val a = mm256SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f)
    val r = mm256ReducePs(a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPs(0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskReducePs() {
    val a = mm256SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f)
    val src = mm256SetPs(3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f)
    val r = mm256MaskReducePs(src, 0x69u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPs(3.0f, 0.0f, 0.25f, 6.0f, 0.25f, 8.0f, 9.0f, 0.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzReducePs() {
    val a = mm256SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f)
    val r = mm256MaskzReducePs(0x69u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPs(0.0f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r, e)
}

public fun testMm512ReducePs() {
    val a = mm512SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 2.25f, 2.50f, 2.75f, 3.0f, 3.25f, 3.50f, 3.75f, 4.0f)
    val r = mm512ReducePs(a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPs(0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskReducePs() {
    val a = mm512SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 2.25f, 2.50f, 2.75f, 3.0f, 3.25f, 3.50f, 3.75f, 4.0f)
    val src = mm512SetPs(5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskReducePs(src, 0x693Cu, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPs(5.0f, 0.0f, 0.25f, 8.0f, 0.25f, 10.0f, 11.0f, 0.0f, 13.0f, 14.0f, 0.25f, 0.0f, 0.25f, 0.0f, 19.0f, 20.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzReducePs() {
    val a = mm512SetPs(0.25f, 0.50f, 0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f, 2.25f, 2.50f, 2.75f, 3.0f, 3.25f, 3.50f, 3.75f, 4.0f)
    val r = mm512MaskzReducePs(0x693Cu, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPs(0.0f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 0.0f, 0.25f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMmReduceRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetSd(0.25)
    val r = mmReduceRoundSd(a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.25)
    assertEqM128d(r, e)
}

public fun testMmMaskReduceRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetSd(0.25)
    val c = mmSetPd(3.0, 4.0)
    val r = mmMaskReduceRoundSd(c, 0x0u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzReduceRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetSd(0.25)
    val r = mmMaskzReduceRoundSd(0x0u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
}

public fun testMmReduceSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetSd(0.25)
    val r = mmReduceSd(a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPd(1.0, 0.25)
    assertEqM128d(r, e)
}

public fun testMmMaskReduceSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetSd(0.25)
    val c = mmSetPd(3.0, 4.0)
    val r = mmMaskReduceSd(c, 0x0u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPd(1.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzReduceSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetSd(0.25)
    val r = mmMaskzReduceSd(0x0u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
}

public fun testMmReduceRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetSs(0.25f)
    val r = mmReduceRoundSs(a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 0.25f)
    assertEqM128(r, e)
}

public fun testMmMaskReduceRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetSs(0.25f)
    val c = mmSetPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmMaskReduceRoundSs(c, 0x0u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzReduceRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetSs(0.25f)
    val r = mmMaskzReduceRoundSs(0x0u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMmReduceSs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetSs(0.25f)
    val r = mmReduceSs(a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 0.25f)
    assertEqM128(r, e)
}

public fun testMmMaskReduceSs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetSs(0.25f)
    val c = mmSetPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmMaskReduceSs(c, 0x0u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzReduceSs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetSs(0.25f)
    val r = mmMaskzReduceSs(0x0u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMmFpclassPdMask() {
    val a = mmSetPd(1.0, Double.POSITIVE_INFINITY)
    val r = mmFpclassPdMask(a, 0x18)
    val e = 0x1
    check(r == e)
}

public fun testMmMaskFpclassPdMask() {
    val a = mmSetPd(1.0, Double.POSITIVE_INFINITY)
    val r = mmMaskFpclassPdMask(0x2u, a, 0x18)
    val e = 0x0
    check(r == e)
}

public fun testMm256FpclassPdMask() {
    val a = mm256SetPd(1.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0)
    val r = mm256FpclassPdMask(a, 0x18)
    val e = 0x6
    check(r == e)
}

public fun testMm256MaskFpclassPdMask() {
    val a = mm256SetPd(1.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0)
    val r = mm256MaskFpclassPdMask(0xAu, a, 0x18)
    val e = 0x2
    check(r == e)
}

public fun testMm512FpclassPdMask() {
    val a = mm512SetPd(1.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0, -2.0, Double.NaN, 1.0e-308)
    val r = mm512FpclassPdMask(a, 0x18)
    val e = 0x60
    check(r == e)
}

public fun testMm512MaskFpclassPdMask() {
    val a = mm512SetPd(1.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0, -2.0, Double.NaN, 1.0e-308)
    val r = mm512MaskFpclassPdMask(0xAAu, a, 0x18)
    val e = 0x20
    check(r == e)
}

public fun testMmFpclassPsMask() {
    val a = mmSetPs(1.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f)
    val r = mmFpclassPsMask(a, 0x18)
    val e = 0x6
    check(r == e)
}

public fun testMmMaskFpclassPsMask() {
    val a = mmSetPs(1.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f)
    val r = mmMaskFpclassPsMask(0xAu, a, 0x18)
    val e = 0x2
    check(r == e)
}

public fun testMm256FpclassPsMask() {
    val a = mm256SetPs(1.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f, -2.0f, Float.NaN, 1.0e-38f)
    val r = mm256FpclassPsMask(a, 0x18)
    val e = 0x60
    check(r == e)
}

public fun testMm256MaskFpclassPsMask() {
    val a = mm256SetPs(1.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f, -2.0f, Float.NaN, 1.0e-38f)
    val r = mm256MaskFpclassPsMask(0xAAu, a, 0x18)
    val e = 0x20
    check(r == e)
}

public fun testMm512FpclassPsMask() {
    val a = mm512SetPs(1.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f, -2.0f, Float.NaN, 1.0e-38f, -1.0f, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, -0.0f, 0.0f, 2.0f, Float.NaN, -1.0e-38f)
    val r = mm512FpclassPsMask(a, 0x18)
    val e = 0x6060
    check(r == e)
}

public fun testMm512MaskFpclassPsMask() {
    val a = mm512SetPs(1.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f, -2.0f, Float.NaN, 1.0e-38f, -1.0f, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, -0.0f, 0.0f, 2.0f, Float.NaN, -1.0e-38f)
    val r = mm512MaskFpclassPsMask(0xAAAAu, a, 0x18)
    val e = 0x2020
    check(r == e)
}

public fun testMmFpclassSdMask() {
    val a = mmSetPd(1.0, Double.POSITIVE_INFINITY)
    val r = mmFpclassSdMask(a, 0x18)
    val e = 0x1
    check(r == e)
}

public fun testMmMaskFpclassSdMask() {
    val a = mmSetSd(Double.POSITIVE_INFINITY)
    val r = mmMaskFpclassSdMask(0x0u, a, 0x18)
    val e = 0x0
    check(r == e)
}

public fun testMmFpclassSsMask() {
    val a = mmSetSs(Float.POSITIVE_INFINITY)
    val r = mmFpclassSsMask(a, 0x18)
    val e = 0x1
    check(r == e)
}

public fun testMmMaskFpclassSsMask() {
    val a = mmSetSs(Float.POSITIVE_INFINITY)
    val r = mmMaskFpclassSsMask(0x0u, a, 0x18)
    val e = 0x0
    check(r == e)
}
