// port-lint: tests x86/avx512fp16.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

public fun mmSet1Pch(re: F16, im: F16): M128h = mmSetrPh(re, im, re, im, re, im, re, im)

public fun mm256Set1Pch(re: F16, im: F16): M256h = mm256SetrPh(re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im)

public fun mm512Set1Pch(re: F16, im: F16): M512h = mm512SetrPh(re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im, re, im)

public fun testMmSetPh() {
    val r = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val e = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256SetPh() {
    val r = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val e = mm256SetrPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512SetPh() {
    val r = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val e = mm512SetrPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmSetSh() {
    val r = mmSetSh(F16.fromFloat(1.0f))
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmSet1Ph() {
    val r = mmSet1Ph(F16.fromFloat(1.0f))
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256Set1Ph() {
    val r = mm256Set1Ph(F16.fromFloat(1.0f))
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512Set1Ph() {
    val r = mm512Set1Ph(F16.fromFloat(1.0f))
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmSetrPh() {
    val r = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val e = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256SetrPh() {
    val r = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val e = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512SetrPh() {
    val r = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val e = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmSetzeroPh() {
    val r = mmSetzeroPh()
    val e = mmSet1Ph(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256SetzeroPh() {
    val r = mm256SetzeroPh()
    val e = mm256Set1Ph(F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512SetzeroPh() {
    val r = mm512SetzeroPh()
    val e = mm512Set1Ph(F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmCastsi128Ph() {
    val a = mmSet1Epi16(0x3c00)
    val r = mmCastsi128Ph(a)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256Castsi256Ph() {
    val a = mm256Set1Epi16(0x3c00)
    val r = mm256Castsi256Ph(a)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512Castsi512Ph() {
    val a = mm512Set1Epi16(0x3c00)
    val r = mm512Castsi512Ph(a)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmCastphSi128() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmCastphSi128(a)
    val e = mmSet1Epi16(0x3c00)
    assertEqM128i(r, e)
}

public fun testMm256CastphSi256() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256CastphSi256(a)
    val e = mm256Set1Epi16(0x3c00)
    assertEqM256i(r, e)
}

public fun testMm512CastphSi512() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512CastphSi512(a)
    val e = mm512Set1Epi16(0x3c00)
    assertEqM512i(r, e)
}

public fun testMmCastpsPh() {
    val a = mmCastsi128Ps(mmSet1Epi16(0x3c00))
    val r = mmCastpsPh(a)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256CastpsPh() {
    val a = mm256Castsi256Ps(mm256Set1Epi16(0x3c00))
    val r = mm256CastpsPh(a)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512CastpsPh() {
    val a = mm512Castsi512Ps(mm512Set1Epi16(0x3c00))
    val r = mm512CastpsPh(a)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmCastphPs() {
    val a = mmCastsi128Ph(mmSet1Epi32(0x3f800000))
    val r = mmCastphPs(a)
    val e = mmSet1Ps(1.0f)
    assertEqM128(r, e)
}

public fun testMm256CastphPs() {
    val a = mm256Castsi256Ph(mm256Set1Epi32(0x3f800000))
    val r = mm256CastphPs(a)
    val e = mm256Set1Ps(1.0f)
    assertEqM256(r, e)
}

public fun testMm512CastphPs() {
    val a = mm512Castsi512Ph(mm512Set1Epi32(0x3f800000))
    val r = mm512CastphPs(a)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMmCastpdPh() {
    val a = mmCastsi128Pd(mmSet1Epi16(0x3c00))
    val r = mmCastpdPh(a)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256CastpdPh() {
    val a = mm256Castsi256Pd(mm256Set1Epi16(0x3c00))
    val r = mm256CastpdPh(a)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512CastpdPh() {
    val a = mm512Castsi512Pd(mm512Set1Epi16(0x3c00))
    val r = mm512CastpdPh(a)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmCastphPd() {
    val a = mmCastsi128Ph(mmSet1Epi64x(0x3ff0000000000000L))
    val r = mmCastphPd(a)
    val e = mmSet1Pd(1.0)
    assertEqM128d(r, e)
}

public fun testMm256CastphPd() {
    val a = mm256Castsi256Ph(mm256Set1Epi64x(0x3ff0000000000000L))
    val r = mm256CastphPd(a)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r, e)
}

public fun testMm512CastphPd() {
    val a = mm512Castsi512Ph(mm512Set1Epi64(0x3ff0000000000000L))
    val r = mm512CastphPd(a)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
}

public fun testMm256Castph256Ph128() {
    val a = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256Castph256Ph128(a)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512Castph512Ph128() {
    val a = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512Castph512Ph128(a)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512Castph512Ph256() {
    val a = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512Castph512Ph256(a)
    val e = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256Castph128Ph256() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256Castph128Ph256(a)
    assertEqM128h(mm256Castph256Ph128(r), a)
}

public fun testMm512Castph128Ph512() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512Castph128Ph512(a)
    assertEqM128h(mm512Castph512Ph128(r), a)
}

public fun testMm512Castph256Ph512() {
    val a = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512Castph256Ph512(a)
    assertEqM256h(mm512Castph512Ph256(r), a)
}

public fun testMm256Zextph128Ph256() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256Zextph128Ph256(a)
    val e = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512Zextph128Ph512() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512Zextph128Ph512(a)
    val e = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512Zextph256Ph512() {
    val a = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512Zextph256Ph512(a)
    val e = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmCmpPhMask() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f))
    val r = mmCmpPhMask(a, b, CMP_EQ_OQ)
    check(r == 0xF0u.toUByte())
}

public fun testMmMaskCmpPhMask() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f))
    val r = mmMaskCmpPhMask(0x55u, a, b, CMP_EQ_OQ)
    check(r == 0x50u.toUByte())
}

public fun testMm256CmpPhMask() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f))
    val r = mm256CmpPhMask(a, b, CMP_EQ_OQ)
    check(r == 0xF0F0u.toUShort())
}

public fun testMm256MaskCmpPhMask() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f))
    val r = mm256MaskCmpPhMask(0x5555u, a, b, CMP_EQ_OQ)
    check(r == 0x5050u.toUShort())
}

public fun testMm512CmpPhMask() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), -F16.fromFloat(21.0f), -F16.fromFloat(22.0f), -F16.fromFloat(23.0f), -F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), -F16.fromFloat(29.0f), -F16.fromFloat(30.0f), -F16.fromFloat(31.0f), -F16.fromFloat(32.0f))
    val r = mm512CmpPhMask(a, b, CMP_EQ_OQ)
    check(r == 0xF0F0F0F0u)
}

public fun testMm512MaskCmpPhMask() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), -F16.fromFloat(21.0f), -F16.fromFloat(22.0f), -F16.fromFloat(23.0f), -F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), -F16.fromFloat(29.0f), -F16.fromFloat(30.0f), -F16.fromFloat(31.0f), -F16.fromFloat(32.0f))
    val r = mm512MaskCmpPhMask(0x55555555u, a, b, CMP_EQ_OQ)
    check(r == 0x50505050u)
}

public fun testMm512CmpRoundPhMask() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), -F16.fromFloat(21.0f), -F16.fromFloat(22.0f), -F16.fromFloat(23.0f), -F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), -F16.fromFloat(29.0f), -F16.fromFloat(30.0f), -F16.fromFloat(31.0f), -F16.fromFloat(32.0f))
    val r = mm512CmpRoundPhMask(a, b, CMP_EQ_OQ, MM_FROUND_NO_EXC)
    check(r == 0xF0F0F0F0u)
}

public fun testMm512MaskCmpRoundPhMask() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), -F16.fromFloat(21.0f), -F16.fromFloat(22.0f), -F16.fromFloat(23.0f), -F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), -F16.fromFloat(29.0f), -F16.fromFloat(30.0f), -F16.fromFloat(31.0f), -F16.fromFloat(32.0f))
    val r = mm512MaskCmpRoundPhMask(0x55555555u, a, b, CMP_EQ_OQ, MM_FROUND_NO_EXC)
    check(r == 0x50505050u)
}

public fun testMmCmpRoundShMask() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmCmpRoundShMask(a, b, CMP_EQ_OQ, MM_FROUND_NO_EXC)
    check(r == 1u.toUByte())
}

public fun testMmMaskCmpRoundShMask() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmMaskCmpRoundShMask(0u, a, b, CMP_EQ_OQ, MM_FROUND_NO_EXC)
    check(r == 0u.toUByte())
}

public fun testMmCmpShMask() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmCmpShMask(a, b, CMP_EQ_OQ)
    check(r == 1u.toUByte())
}

public fun testMmMaskCmpShMask() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmMaskCmpShMask(0u, a, b, CMP_EQ_OQ)
    check(r == 0u.toUByte())
}

public fun testMmComiRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmComiRoundSh(a, b, CMP_EQ_OQ, MM_FROUND_NO_EXC)
    check(r == 1)
}

public fun testMmComiSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmComiSh(a, b, CMP_EQ_OQ)
    check(r == 1)
}

public fun testMmComieqSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmComieqSh(a, b)
    check(r == 1)
}

public fun testMmComigeSh() {
    val a = mmSetSh(F16.fromFloat(2.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmComigeSh(a, b)
    check(r == 1)
}

public fun testMmComigtSh() {
    val a = mmSetSh(F16.fromFloat(2.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmComigtSh(a, b)
    check(r == 1)
}

public fun testMmComileSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmComileSh(a, b)
    check(r == 1)
}

public fun testMmComiltSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmComiltSh(a, b)
    check(r == 1)
}

public fun testMmComineqSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmComineqSh(a, b)
    check(r == 1)
}

public fun testMmUcomieqSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmUcomieqSh(a, b)
    check(r == 1)
}

public fun testMmUcomigeSh() {
    val a = mmSetSh(F16.fromFloat(2.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmUcomigeSh(a, b)
    check(r == 1)
}

public fun testMmUcomigtSh() {
    val a = mmSetSh(F16.fromFloat(2.0f))
    val b = mmSetSh(F16.fromFloat(1.0f))
    val r = mmUcomigtSh(a, b)
    check(r == 1)
}

public fun testMmUcomileSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmUcomileSh(a, b)
    check(r == 1)
}

public fun testMmUcomiltSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmUcomiltSh(a, b)
    check(r == 1)
}

public fun testMmUcomineqSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmUcomineqSh(a, b)
    check(r == 1)
}

public fun testMmLoadPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmLoadPh(addrOf(a).cast())
    assertEqM128h(a, b)
}

public fun testMm256LoadPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256LoadPh(addrOf(a).cast())
    assertEqM256h(a, b)
}

public fun testMm512LoadPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512LoadPh(addrOf(a).cast())
    assertEqM512h(a, b)
}

public fun testMmLoadSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmLoadSh(addrOf(a).cast())
    assertEqM128h(a, b)
}

public fun testMmMaskLoadSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val src = mmSetSh(F16.fromFloat(2.0f))
    val b = mmMaskLoadSh(src, 1u, addrOf(a).cast())
    assertEqM128h(a, b)
    val b1 = mmMaskLoadSh(src, 0u, addrOf(a).cast())
    assertEqM128h(src, b1)
}

public fun testMmMaskzLoadSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmMaskzLoadSh(1u, addrOf(a).cast())
    assertEqM128h(a, b)
    val b1 = mmMaskzLoadSh(0u, addrOf(a).cast())
    assertEqM128h(mmSetzeroPh(), b1)
}

public fun testMmLoaduPh() {
    val array = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mmLoaduPh(array.asPtr())
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256LoaduPh() {
    val array = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val r = mm256LoaduPh(array.asPtr())
    val e = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512LoaduPh() {
    val array = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0)
    val r = mm512LoaduPh(array.asPtr())
    val e = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMmMoveSh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetSh(F16.fromFloat(9.0f))
    val r = mmMoveSh(a, b)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMoveSh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetSh(F16.fromFloat(9.0f))
    val src = mmSetSh(F16.fromFloat(10.0f))
    val r = mmMaskMoveSh(src, 0u, a, b)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(10.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMoveSh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetSh(F16.fromFloat(9.0f))
    val r = mmMaskzMoveSh(0u, a, b)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMmStorePh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetzeroPh()
    run {
        mmStorePh(addrOfMut(b).cast(), a)
    }
    assertEqM128h(a, b)
}

public fun testMm256StorePh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetzeroPh()
    run {
        mm256StorePh(addrOfMut(b).cast(), a)
    }
    assertEqM256h(a, b)
}

public fun testMm512StorePh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetzeroPh()
    run {
        mm512StorePh(addrOfMut(b).cast(), a)
    }
    assertEqM512h(a, b)
}

public fun testMmStoreSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetzeroPh()
    run {
        mmStoreSh(addrOfMut(b).cast(), a)
    }
    assertEqM128h(a, b)
}

public fun testMmMaskStoreSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetzeroPh()
    run {
        mmMaskStoreSh(addrOfMut(b).cast(), 0u, a)
    }
    assertEqM128h(mmSetzeroPh(), b)
    run {
        mmMaskStoreSh(addrOfMut(b).cast(), 1u, a)
    }
    assertEqM128h(a, b)
}

public fun testMmStoreuPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val array = MutableList(8) { 0.0 }
    run {
        mmStoreuPh(array.asMutPtr(), a)
    }
    assertEqM128h(a, mmLoaduPh(array.asPtr()))
}

public fun testMm256StoreuPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val array = MutableList(16) { 0.0 }
    run {
        mm256StoreuPh(array.asMutPtr(), a)
    }
    assertEqM256h(a, mm256LoaduPh(array.asPtr()))
}

public fun testMm512StoreuPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val array = MutableList(32) { 0.0 }
    run {
        mm512StoreuPh(array.asMutPtr(), a)
    }
    assertEqM512h(a, mm512LoaduPh(array.asPtr()))
}

public fun testMmAddPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mmAddPh(a, b)
    val e = mmSet1Ph(F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskAddPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskAddPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(12.0f), F16.fromFloat(9.0f), F16.fromFloat(14.0f), F16.fromFloat(9.0f), F16.fromFloat(16.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzAddPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mmMaskzAddPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(9.0f), F16.fromFloat(0.0f), F16.fromFloat(9.0f), F16.fromFloat(0.0f), F16.fromFloat(9.0f), F16.fromFloat(0.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMm256AddPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm256AddPh(a, b)
    val e = mm256Set1Ph(F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskAddPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm256SetPh(F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm256MaskAddPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(20.0f), F16.fromFloat(17.0f), F16.fromFloat(22.0f), F16.fromFloat(17.0f), F16.fromFloat(24.0f), F16.fromFloat(17.0f), F16.fromFloat(26.0f), F16.fromFloat(17.0f), F16.fromFloat(28.0f), F16.fromFloat(17.0f), F16.fromFloat(30.0f), F16.fromFloat(17.0f), F16.fromFloat(32.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzAddPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm256MaskzAddPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm512AddPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512AddPh(a, b)
    val e = mm512Set1Ph(F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskAddPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f), F16.fromFloat(65.0f))
    val r = mm512MaskAddPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(33.0f), F16.fromFloat(36.0f), F16.fromFloat(33.0f), F16.fromFloat(38.0f), F16.fromFloat(33.0f), F16.fromFloat(40.0f), F16.fromFloat(33.0f), F16.fromFloat(42.0f), F16.fromFloat(33.0f), F16.fromFloat(44.0f), F16.fromFloat(33.0f), F16.fromFloat(46.0f), F16.fromFloat(33.0f), F16.fromFloat(48.0f), F16.fromFloat(33.0f), F16.fromFloat(50.0f), F16.fromFloat(33.0f), F16.fromFloat(52.0f), F16.fromFloat(33.0f), F16.fromFloat(54.0f), F16.fromFloat(33.0f), F16.fromFloat(56.0f), F16.fromFloat(33.0f), F16.fromFloat(58.0f), F16.fromFloat(33.0f), F16.fromFloat(60.0f), F16.fromFloat(33.0f), F16.fromFloat(62.0f), F16.fromFloat(33.0f), F16.fromFloat(64.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzAddPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MaskzAddPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512AddRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512AddRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskAddRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f), F16.fromFloat(65.0f))
    val r = mm512MaskAddRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(33.0f), F16.fromFloat(36.0f), F16.fromFloat(33.0f), F16.fromFloat(38.0f), F16.fromFloat(33.0f), F16.fromFloat(40.0f), F16.fromFloat(33.0f), F16.fromFloat(42.0f), F16.fromFloat(33.0f), F16.fromFloat(44.0f), F16.fromFloat(33.0f), F16.fromFloat(46.0f), F16.fromFloat(33.0f), F16.fromFloat(48.0f), F16.fromFloat(33.0f), F16.fromFloat(50.0f), F16.fromFloat(33.0f), F16.fromFloat(52.0f), F16.fromFloat(33.0f), F16.fromFloat(54.0f), F16.fromFloat(33.0f), F16.fromFloat(56.0f), F16.fromFloat(33.0f), F16.fromFloat(58.0f), F16.fromFloat(33.0f), F16.fromFloat(60.0f), F16.fromFloat(33.0f), F16.fromFloat(62.0f), F16.fromFloat(33.0f), F16.fromFloat(64.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzAddRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MaskzAddRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f), F16.fromFloat(0.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMmAddRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmAddRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(3.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskAddRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskAddRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskAddRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(F16.fromFloat(3.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzAddRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzAddRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzAddRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(F16.fromFloat(3.0f))
    assertEqM128h(r1, e1)
}

public fun testMmAddSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmAddSh(a, b)
    val e = mmSetSh(F16.fromFloat(3.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskAddSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskAddSh(src, 0u, a, b)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskAddSh(src, 1u, a, b)
    val e1 = mmSetSh(F16.fromFloat(3.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzAddSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzAddSh(0u, a, b)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzAddSh(1u, a, b)
    val e1 = mmSetSh(F16.fromFloat(3.0f))
    assertEqM128h(r1, e1)
}

public fun testMmSubPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mmSubPh(a, b)
    val e = mmSetPh(-F16.fromFloat(7.0f), -F16.fromFloat(5.0f), -F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskSubPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskSubPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(10.0f), -F16.fromFloat(5.0f), F16.fromFloat(12.0f), -F16.fromFloat(1.0f), F16.fromFloat(14.0f), F16.fromFloat(3.0f), F16.fromFloat(16.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzSubPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mmMaskzSubPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMm256SubPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm256SubPh(a, b)
    val e = mm256SetPh(-F16.fromFloat(15.0f), -F16.fromFloat(13.0f), -F16.fromFloat(11.0f), -F16.fromFloat(9.0f), -F16.fromFloat(7.0f), -F16.fromFloat(5.0f), -F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskSubPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm256SetPh(F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm256MaskSubPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(18.0f), -F16.fromFloat(13.0f), F16.fromFloat(20.0f), -F16.fromFloat(9.0f), F16.fromFloat(22.0f), -F16.fromFloat(5.0f), F16.fromFloat(24.0f), -F16.fromFloat(1.0f), F16.fromFloat(26.0f), F16.fromFloat(3.0f), F16.fromFloat(28.0f), F16.fromFloat(7.0f), F16.fromFloat(30.0f), F16.fromFloat(11.0f), F16.fromFloat(32.0f), F16.fromFloat(15.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzSubPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm256MaskzSubPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), -F16.fromFloat(13.0f), F16.fromFloat(0.0f), -F16.fromFloat(9.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(7.0f), F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(0.0f), F16.fromFloat(15.0f))
    assertEqM256h(r, e)
}

public fun testMm512SubPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512SubPh(a, b)
    val e = mm512SetPh(-F16.fromFloat(31.0f), -F16.fromFloat(29.0f), -F16.fromFloat(27.0f), -F16.fromFloat(25.0f), -F16.fromFloat(23.0f), -F16.fromFloat(21.0f), -F16.fromFloat(19.0f), -F16.fromFloat(17.0f), -F16.fromFloat(15.0f), -F16.fromFloat(13.0f), -F16.fromFloat(11.0f), -F16.fromFloat(9.0f), -F16.fromFloat(7.0f), -F16.fromFloat(5.0f), -F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f), F16.fromFloat(17.0f), F16.fromFloat(19.0f), F16.fromFloat(21.0f), F16.fromFloat(23.0f), F16.fromFloat(25.0f), F16.fromFloat(27.0f), F16.fromFloat(29.0f), F16.fromFloat(31.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskSubPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f), F16.fromFloat(65.0f))
    val r = mm512MaskSubPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(34.0f), -F16.fromFloat(29.0f), F16.fromFloat(36.0f), -F16.fromFloat(25.0f), F16.fromFloat(38.0f), -F16.fromFloat(21.0f), F16.fromFloat(40.0f), -F16.fromFloat(17.0f), F16.fromFloat(42.0f), -F16.fromFloat(13.0f), F16.fromFloat(44.0f), -F16.fromFloat(9.0f), F16.fromFloat(46.0f), -F16.fromFloat(5.0f), F16.fromFloat(48.0f), -F16.fromFloat(1.0f), F16.fromFloat(50.0f), F16.fromFloat(3.0f), F16.fromFloat(52.0f), F16.fromFloat(7.0f), F16.fromFloat(54.0f), F16.fromFloat(11.0f), F16.fromFloat(56.0f), F16.fromFloat(15.0f), F16.fromFloat(58.0f), F16.fromFloat(19.0f), F16.fromFloat(60.0f), F16.fromFloat(23.0f), F16.fromFloat(62.0f), F16.fromFloat(27.0f), F16.fromFloat(64.0f), F16.fromFloat(31.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzSubPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MaskzSubPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), -F16.fromFloat(29.0f), F16.fromFloat(0.0f), -F16.fromFloat(25.0f), F16.fromFloat(0.0f), -F16.fromFloat(21.0f), F16.fromFloat(0.0f), -F16.fromFloat(17.0f), F16.fromFloat(0.0f), -F16.fromFloat(13.0f), F16.fromFloat(0.0f), -F16.fromFloat(9.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(7.0f), F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(0.0f), F16.fromFloat(15.0f), F16.fromFloat(0.0f), F16.fromFloat(19.0f), F16.fromFloat(0.0f), F16.fromFloat(23.0f), F16.fromFloat(0.0f), F16.fromFloat(27.0f), F16.fromFloat(0.0f), F16.fromFloat(31.0f))
    assertEqM512h(r, e)
}

public fun testMm512SubRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512SubRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(-F16.fromFloat(31.0f), -F16.fromFloat(29.0f), -F16.fromFloat(27.0f), -F16.fromFloat(25.0f), -F16.fromFloat(23.0f), -F16.fromFloat(21.0f), -F16.fromFloat(19.0f), -F16.fromFloat(17.0f), -F16.fromFloat(15.0f), -F16.fromFloat(13.0f), -F16.fromFloat(11.0f), -F16.fromFloat(9.0f), -F16.fromFloat(7.0f), -F16.fromFloat(5.0f), -F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f), F16.fromFloat(17.0f), F16.fromFloat(19.0f), F16.fromFloat(21.0f), F16.fromFloat(23.0f), F16.fromFloat(25.0f), F16.fromFloat(27.0f), F16.fromFloat(29.0f), F16.fromFloat(31.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskSubRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f), F16.fromFloat(65.0f))
    val r = mm512MaskSubRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(34.0f), -F16.fromFloat(29.0f), F16.fromFloat(36.0f), -F16.fromFloat(25.0f), F16.fromFloat(38.0f), -F16.fromFloat(21.0f), F16.fromFloat(40.0f), -F16.fromFloat(17.0f), F16.fromFloat(42.0f), -F16.fromFloat(13.0f), F16.fromFloat(44.0f), -F16.fromFloat(9.0f), F16.fromFloat(46.0f), -F16.fromFloat(5.0f), F16.fromFloat(48.0f), -F16.fromFloat(1.0f), F16.fromFloat(50.0f), F16.fromFloat(3.0f), F16.fromFloat(52.0f), F16.fromFloat(7.0f), F16.fromFloat(54.0f), F16.fromFloat(11.0f), F16.fromFloat(56.0f), F16.fromFloat(15.0f), F16.fromFloat(58.0f), F16.fromFloat(19.0f), F16.fromFloat(60.0f), F16.fromFloat(23.0f), F16.fromFloat(62.0f), F16.fromFloat(27.0f), F16.fromFloat(64.0f), F16.fromFloat(31.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzSubRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MaskzSubRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), -F16.fromFloat(29.0f), F16.fromFloat(0.0f), -F16.fromFloat(25.0f), F16.fromFloat(0.0f), -F16.fromFloat(21.0f), F16.fromFloat(0.0f), -F16.fromFloat(17.0f), F16.fromFloat(0.0f), -F16.fromFloat(13.0f), F16.fromFloat(0.0f), -F16.fromFloat(9.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(7.0f), F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(0.0f), F16.fromFloat(15.0f), F16.fromFloat(0.0f), F16.fromFloat(19.0f), F16.fromFloat(0.0f), F16.fromFloat(23.0f), F16.fromFloat(0.0f), F16.fromFloat(27.0f), F16.fromFloat(0.0f), F16.fromFloat(31.0f))
    assertEqM512h(r, e)
}

public fun testMmSubRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmSubRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(-F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskSubRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskSubRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskSubRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(-F16.fromFloat(1.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzSubRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzSubRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzSubRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(-F16.fromFloat(1.0f))
    assertEqM128h(r1, e1)
}

public fun testMmSubSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmSubSh(a, b)
    val e = mmSetSh(-F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskSubSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskSubSh(src, 0u, a, b)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskSubSh(src, 1u, a, b)
    val e1 = mmSetSh(-F16.fromFloat(1.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzSubSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzSubSh(0u, a, b)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzSubSh(1u, a, b)
    val e1 = mmSetSh(-F16.fromFloat(1.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMulPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mmMulPh(a, b)
    val e = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(14.0f), F16.fromFloat(18.0f), F16.fromFloat(20.0f), F16.fromFloat(20.0f), F16.fromFloat(18.0f), F16.fromFloat(14.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMulPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskMulPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(14.0f), F16.fromFloat(12.0f), F16.fromFloat(20.0f), F16.fromFloat(14.0f), F16.fromFloat(18.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMulPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mmMaskzMulPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(0.0f), F16.fromFloat(18.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MulPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm256MulPh(a, b)
    val e = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(30.0f), F16.fromFloat(42.0f), F16.fromFloat(52.0f), F16.fromFloat(60.0f), F16.fromFloat(66.0f), F16.fromFloat(70.0f), F16.fromFloat(72.0f), F16.fromFloat(72.0f), F16.fromFloat(70.0f), F16.fromFloat(66.0f), F16.fromFloat(60.0f), F16.fromFloat(52.0f), F16.fromFloat(42.0f), F16.fromFloat(30.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskMulPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm256SetPh(F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm256MaskMulPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(18.0f), F16.fromFloat(30.0f), F16.fromFloat(20.0f), F16.fromFloat(52.0f), F16.fromFloat(22.0f), F16.fromFloat(66.0f), F16.fromFloat(24.0f), F16.fromFloat(72.0f), F16.fromFloat(26.0f), F16.fromFloat(70.0f), F16.fromFloat(28.0f), F16.fromFloat(60.0f), F16.fromFloat(30.0f), F16.fromFloat(42.0f), F16.fromFloat(32.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzMulPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm256MaskzMulPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(30.0f), F16.fromFloat(0.0f), F16.fromFloat(52.0f), F16.fromFloat(0.0f), F16.fromFloat(66.0f), F16.fromFloat(0.0f), F16.fromFloat(72.0f), F16.fromFloat(0.0f), F16.fromFloat(70.0f), F16.fromFloat(0.0f), F16.fromFloat(60.0f), F16.fromFloat(0.0f), F16.fromFloat(42.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MulPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MulPh(a, b)
    val e = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(62.0f), F16.fromFloat(90.0f), F16.fromFloat(116.0f), F16.fromFloat(140.0f), F16.fromFloat(162.0f), F16.fromFloat(182.0f), F16.fromFloat(200.0f), F16.fromFloat(216.0f), F16.fromFloat(230.0f), F16.fromFloat(242.0f), F16.fromFloat(252.0f), F16.fromFloat(260.0f), F16.fromFloat(266.0f), F16.fromFloat(270.0f), F16.fromFloat(272.0f), F16.fromFloat(272.0f), F16.fromFloat(270.0f), F16.fromFloat(266.0f), F16.fromFloat(260.0f), F16.fromFloat(252.0f), F16.fromFloat(242.0f), F16.fromFloat(230.0f), F16.fromFloat(216.0f), F16.fromFloat(200.0f), F16.fromFloat(182.0f), F16.fromFloat(162.0f), F16.fromFloat(140.0f), F16.fromFloat(116.0f), F16.fromFloat(90.0f), F16.fromFloat(62.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskMulPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f), F16.fromFloat(65.0f))
    val r = mm512MaskMulPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(62.0f), F16.fromFloat(36.0f), F16.fromFloat(116.0f), F16.fromFloat(38.0f), F16.fromFloat(162.0f), F16.fromFloat(40.0f), F16.fromFloat(200.0f), F16.fromFloat(42.0f), F16.fromFloat(230.0f), F16.fromFloat(44.0f), F16.fromFloat(252.0f), F16.fromFloat(46.0f), F16.fromFloat(266.0f), F16.fromFloat(48.0f), F16.fromFloat(272.0f), F16.fromFloat(50.0f), F16.fromFloat(270.0f), F16.fromFloat(52.0f), F16.fromFloat(260.0f), F16.fromFloat(54.0f), F16.fromFloat(242.0f), F16.fromFloat(56.0f), F16.fromFloat(216.0f), F16.fromFloat(58.0f), F16.fromFloat(182.0f), F16.fromFloat(60.0f), F16.fromFloat(140.0f), F16.fromFloat(62.0f), F16.fromFloat(90.0f), F16.fromFloat(64.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMulPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MaskzMulPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(62.0f), F16.fromFloat(0.0f), F16.fromFloat(116.0f), F16.fromFloat(0.0f), F16.fromFloat(162.0f), F16.fromFloat(0.0f), F16.fromFloat(200.0f), F16.fromFloat(0.0f), F16.fromFloat(230.0f), F16.fromFloat(0.0f), F16.fromFloat(252.0f), F16.fromFloat(0.0f), F16.fromFloat(266.0f), F16.fromFloat(0.0f), F16.fromFloat(272.0f), F16.fromFloat(0.0f), F16.fromFloat(270.0f), F16.fromFloat(0.0f), F16.fromFloat(260.0f), F16.fromFloat(0.0f), F16.fromFloat(242.0f), F16.fromFloat(0.0f), F16.fromFloat(216.0f), F16.fromFloat(0.0f), F16.fromFloat(182.0f), F16.fromFloat(0.0f), F16.fromFloat(140.0f), F16.fromFloat(0.0f), F16.fromFloat(90.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MulRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MulRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(62.0f), F16.fromFloat(90.0f), F16.fromFloat(116.0f), F16.fromFloat(140.0f), F16.fromFloat(162.0f), F16.fromFloat(182.0f), F16.fromFloat(200.0f), F16.fromFloat(216.0f), F16.fromFloat(230.0f), F16.fromFloat(242.0f), F16.fromFloat(252.0f), F16.fromFloat(260.0f), F16.fromFloat(266.0f), F16.fromFloat(270.0f), F16.fromFloat(272.0f), F16.fromFloat(272.0f), F16.fromFloat(270.0f), F16.fromFloat(266.0f), F16.fromFloat(260.0f), F16.fromFloat(252.0f), F16.fromFloat(242.0f), F16.fromFloat(230.0f), F16.fromFloat(216.0f), F16.fromFloat(200.0f), F16.fromFloat(182.0f), F16.fromFloat(162.0f), F16.fromFloat(140.0f), F16.fromFloat(116.0f), F16.fromFloat(90.0f), F16.fromFloat(62.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskMulRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val src = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f), F16.fromFloat(65.0f))
    val r = mm512MaskMulRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(34.0f), F16.fromFloat(62.0f), F16.fromFloat(36.0f), F16.fromFloat(116.0f), F16.fromFloat(38.0f), F16.fromFloat(162.0f), F16.fromFloat(40.0f), F16.fromFloat(200.0f), F16.fromFloat(42.0f), F16.fromFloat(230.0f), F16.fromFloat(44.0f), F16.fromFloat(252.0f), F16.fromFloat(46.0f), F16.fromFloat(266.0f), F16.fromFloat(48.0f), F16.fromFloat(272.0f), F16.fromFloat(50.0f), F16.fromFloat(270.0f), F16.fromFloat(52.0f), F16.fromFloat(260.0f), F16.fromFloat(54.0f), F16.fromFloat(242.0f), F16.fromFloat(56.0f), F16.fromFloat(216.0f), F16.fromFloat(58.0f), F16.fromFloat(182.0f), F16.fromFloat(60.0f), F16.fromFloat(140.0f), F16.fromFloat(62.0f), F16.fromFloat(90.0f), F16.fromFloat(64.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMulRoundPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(F16.fromFloat(32.0f), F16.fromFloat(31.0f), F16.fromFloat(30.0f), F16.fromFloat(29.0f), F16.fromFloat(28.0f), F16.fromFloat(27.0f), F16.fromFloat(26.0f), F16.fromFloat(25.0f), F16.fromFloat(24.0f), F16.fromFloat(23.0f), F16.fromFloat(22.0f), F16.fromFloat(21.0f), F16.fromFloat(20.0f), F16.fromFloat(19.0f), F16.fromFloat(18.0f), F16.fromFloat(17.0f), F16.fromFloat(16.0f), F16.fromFloat(15.0f), F16.fromFloat(14.0f), F16.fromFloat(13.0f), F16.fromFloat(12.0f), F16.fromFloat(11.0f), F16.fromFloat(10.0f), F16.fromFloat(9.0f), F16.fromFloat(8.0f), F16.fromFloat(7.0f), F16.fromFloat(6.0f), F16.fromFloat(5.0f), F16.fromFloat(4.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    val r = mm512MaskzMulRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(62.0f), F16.fromFloat(0.0f), F16.fromFloat(116.0f), F16.fromFloat(0.0f), F16.fromFloat(162.0f), F16.fromFloat(0.0f), F16.fromFloat(200.0f), F16.fromFloat(0.0f), F16.fromFloat(230.0f), F16.fromFloat(0.0f), F16.fromFloat(252.0f), F16.fromFloat(0.0f), F16.fromFloat(266.0f), F16.fromFloat(0.0f), F16.fromFloat(272.0f), F16.fromFloat(0.0f), F16.fromFloat(270.0f), F16.fromFloat(0.0f), F16.fromFloat(260.0f), F16.fromFloat(0.0f), F16.fromFloat(242.0f), F16.fromFloat(0.0f), F16.fromFloat(216.0f), F16.fromFloat(0.0f), F16.fromFloat(182.0f), F16.fromFloat(0.0f), F16.fromFloat(140.0f), F16.fromFloat(0.0f), F16.fromFloat(90.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMmMulRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMulRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMulRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskMulRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskMulRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(F16.fromFloat(2.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzMulRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzMulRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzMulRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(F16.fromFloat(2.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMulSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMulSh(a, b)
    val e = mmSetSh(F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMulSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskMulSh(src, 0u, a, b)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskMulSh(src, 1u, a, b)
    val e1 = mmSetSh(F16.fromFloat(2.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzMulSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzMulSh(0u, a, b)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzMulSh(1u, a, b)
    val e1 = mmSetSh(F16.fromFloat(2.0f))
    assertEqM128h(r1, e1)
}

public fun testMmDivPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmDivPh(a, b)
    val e = mmSet1Ph(F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskDivPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val src = mmSetPh(F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f))
    val r = mmMaskDivPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(4.0f), F16.fromFloat(0.5f), F16.fromFloat(6.0f), F16.fromFloat(0.5f), F16.fromFloat(8.0f), F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskzDivPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmMaskzDivPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMm256DivPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256DivPh(a, b)
    val e = mm256Set1Ph(F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm256MaskDivPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val src = mm256SetPh(F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mm256MaskDivPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(4.0f), F16.fromFloat(0.5f), F16.fromFloat(6.0f), F16.fromFloat(0.5f), F16.fromFloat(8.0f), F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(0.5f), F16.fromFloat(12.0f), F16.fromFloat(0.5f), F16.fromFloat(14.0f), F16.fromFloat(0.5f), F16.fromFloat(16.0f), F16.fromFloat(0.5f), F16.fromFloat(18.0f), F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzDivPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256MaskzDivPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm512DivPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512DivPh(a, b)
    val e = mm512Set1Ph(F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskDivPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val src = mm512SetPh(F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f))
    val r = mm512MaskDivPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(4.0f), F16.fromFloat(0.5f), F16.fromFloat(6.0f), F16.fromFloat(0.5f), F16.fromFloat(8.0f), F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(0.5f), F16.fromFloat(12.0f), F16.fromFloat(0.5f), F16.fromFloat(14.0f), F16.fromFloat(0.5f), F16.fromFloat(16.0f), F16.fromFloat(0.5f), F16.fromFloat(18.0f), F16.fromFloat(0.5f), F16.fromFloat(20.0f), F16.fromFloat(0.5f), F16.fromFloat(22.0f), F16.fromFloat(0.5f), F16.fromFloat(24.0f), F16.fromFloat(0.5f), F16.fromFloat(26.0f), F16.fromFloat(0.5f), F16.fromFloat(28.0f), F16.fromFloat(0.5f), F16.fromFloat(30.0f), F16.fromFloat(0.5f), F16.fromFloat(32.0f), F16.fromFloat(0.5f), F16.fromFloat(34.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzDivPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskzDivPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512DivRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512DivRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskDivRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val src = mm512SetPh(F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f))
    val r = mm512MaskDivRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(4.0f), F16.fromFloat(0.5f), F16.fromFloat(6.0f), F16.fromFloat(0.5f), F16.fromFloat(8.0f), F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(0.5f), F16.fromFloat(12.0f), F16.fromFloat(0.5f), F16.fromFloat(14.0f), F16.fromFloat(0.5f), F16.fromFloat(16.0f), F16.fromFloat(0.5f), F16.fromFloat(18.0f), F16.fromFloat(0.5f), F16.fromFloat(20.0f), F16.fromFloat(0.5f), F16.fromFloat(22.0f), F16.fromFloat(0.5f), F16.fromFloat(24.0f), F16.fromFloat(0.5f), F16.fromFloat(26.0f), F16.fromFloat(0.5f), F16.fromFloat(28.0f), F16.fromFloat(0.5f), F16.fromFloat(30.0f), F16.fromFloat(0.5f), F16.fromFloat(32.0f), F16.fromFloat(0.5f), F16.fromFloat(34.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzDivRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskzDivRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMmDivRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmDivRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskDivRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskDivRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskDivRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(F16.fromFloat(0.5f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzDivRoundSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzDivRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzDivRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetSh(F16.fromFloat(0.5f))
    assertEqM128h(r1, e1)
}

public fun testMmDivSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmDivSh(a, b)
    val e = mmSetSh(F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskDivSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val src = mmSetSh(F16.fromFloat(4.0f))
    val r = mmMaskDivSh(src, 0u, a, b)
    val e = mmSetSh(F16.fromFloat(4.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskDivSh(src, 1u, a, b)
    val e1 = mmSetSh(F16.fromFloat(0.5f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzDivSh() {
    val a = mmSetSh(F16.fromFloat(1.0f))
    val b = mmSetSh(F16.fromFloat(2.0f))
    val r = mmMaskzDivSh(0u, a, b)
    val e = mmSetSh(F16.fromFloat(0.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzDivSh(1u, a, b)
    val e1 = mmSetSh(F16.fromFloat(0.5f))
    assertEqM128h(r1, e1)
}

public fun testMmMulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 1.0)
    val r = mmMulPch(a, b)
    val e = mmSet1Pch(-1.0, 0.0)
    assertEqM128h(r, e)
}

public fun testMmMaskMulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 1.0)
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    val r = mmMaskMulPch(src, 0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 1.0)
    val r = mmMaskzMulPch(0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256MulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 1.0)
    val r = mm256MulPch(a, b)
    val e = mm256Set1Pch(-1.0, 0.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskMulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 1.0)
    val src = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskMulPch(src, 0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzMulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 1.0)
    val r = mm256MaskzMulPch(0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512MulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512MulPch(a, b)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskMulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskMulPch(src, 0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512MaskzMulPch(0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512MulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512MulRoundPch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskMulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskMulRoundPch(src, 0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512MaskzMulRoundPch(0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmMulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmMulRoundSch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskMulRoundSch(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmMaskzMulRoundSch(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmMulSch(a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskMulSch(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmMaskzMulSch(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmFmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 1.0)
    val r = mmFmulPch(a, b)
    val e = mmSet1Pch(-1.0, 0.0)
    assertEqM128h(r, e)
}

public fun testMmMaskFmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 1.0)
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    val r = mmMaskFmulPch(src, 0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 1.0)
    val r = mmMaskzFmulPch(0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256FmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 1.0)
    val r = mm256FmulPch(a, b)
    val e = mm256Set1Pch(-1.0, 0.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskFmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 1.0)
    val src = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskFmulPch(src, 0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 1.0)
    val r = mm256MaskzFmulPch(0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512FmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512FmulPch(a, b)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskFmulPch(src, 0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512MaskzFmulPch(0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512FmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512FmulRoundPch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskFmulRoundPch(src, 0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 1.0)
    val r = mm512MaskzFmulRoundPch(0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmFmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmFmulRoundSch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskFmulRoundSch(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmMaskzFmulRoundSch(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmFmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmFmulSch(a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskFmulSch(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val r = mmMaskzFmulSch(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmCmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, -1.0)
    val r = mmCmulPch(a, b)
    val e = mmSet1Pch(-1.0, 0.0)
    assertEqM128h(r, e)
}

public fun testMmMaskCmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, -1.0)
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    val r = mmMaskCmulPch(src, 0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, -1.0)
    val r = mmMaskzCmulPch(0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256CmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, -1.0)
    val r = mm256CmulPch(a, b)
    val e = mm256Set1Pch(-1.0, 0.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskCmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, -1.0)
    val src = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCmulPch(src, 0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzCmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, -1.0)
    val r = mm256MaskzCmulPch(0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512CmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512CmulPch(a, b)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskCmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskCmulPch(src, 0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzCmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512MaskzCmulPch(0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512CmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512CmulRoundPch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskCmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskCmulRoundPch(src, 0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzCmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512MaskzCmulRoundPch(0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmCmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmCmulSch(a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskCmulSch(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmMaskzCmulSch(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmCmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmCmulRoundSch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskCmulRoundSch(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmMaskzCmulRoundSch(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmFcmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, -1.0)
    val r = mmFcmulPch(a, b)
    val e = mmSet1Pch(-1.0, 0.0)
    assertEqM128h(r, e)
}

public fun testMmMaskFcmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, -1.0)
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    val r = mmMaskFcmulPch(src, 0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFcmulPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, -1.0)
    val r = mmMaskzFcmulPch(0x5u, a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256FcmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, -1.0)
    val r = mm256FcmulPch(a, b)
    val e = mm256Set1Pch(-1.0, 0.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskFcmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, -1.0)
    val src = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskFcmulPch(src, 0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFcmulPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, -1.0)
    val r = mm256MaskzFcmulPch(0x55u, a, b)
    val e = mm256SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512FcmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512FcmulPch(a, b)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFcmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskFcmulPch(src, 0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFcmulPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512MaskzFcmulPch(0x5555u, a, b)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512FcmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512FcmulRoundPch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pch(-1.0, 0.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFcmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskFcmulRoundPch(src, 0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFcmulRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, -1.0)
    val r = mm512MaskzFcmulRoundPch(0x5555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmFcmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmFcmulSch(a, b)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFcmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskFcmulSch(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFcmulSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmMaskzFcmulSch(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmFcmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmFcmulRoundSch(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFcmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val src = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f))
    val r = mmMaskFcmulRoundSch(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFcmulRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), -F16.fromFloat(9.0f), F16.fromFloat(10.0f), -F16.fromFloat(11.0f), F16.fromFloat(12.0f), -F16.fromFloat(13.0f))
    val r = mmMaskzFcmulRoundSch(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmAbsPh() {
    val a = mmSetPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), -F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(6.0f))
    val r = mmAbsPh(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f))
    assertEqM128h(r, e)
}

public fun testMm256AbsPh() {
    val a = mm256SetPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), -F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(6.0f), F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(10.0f), F16.fromFloat(11.0f), -F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(14.0f))
    val r = mm256AbsPh(a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f))
    assertEqM256h(r, e)
}

public fun testMm512AbsPh() {
    val a = mm512SetPh(-F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), -F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(6.0f), F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(10.0f), F16.fromFloat(11.0f), -F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(14.0f), F16.fromFloat(15.0f), -F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(18.0f), F16.fromFloat(19.0f), -F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(22.0f), F16.fromFloat(23.0f), -F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(26.0f), F16.fromFloat(27.0f), -F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(30.0f))
    val r = mm512AbsPh(a)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f))
    assertEqM512h(r, e)
}

public fun testMmConjPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val r = mmConjPch(a)
    val e = mmSet1Pch(0.0, -1.0)
    assertEqM128h(r, e)
}

public fun testMmMaskConjPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    val r = mmMaskConjPch(src, 0x5u, a)
    val e = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzConjPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val r = mmMaskzConjPch(0x5u, a)
    val e = mmSetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256ConjPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val r = mm256ConjPch(a)
    val e = mm256Set1Pch(0.0, -1.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskConjPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val src = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskConjPch(src, 0x55u, a)
    val e = mm256SetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzConjPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val r = mm256MaskzConjPch(0x55u, a)
    val e = mm256SetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512ConjPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val r = mm512ConjPch(a)
    val e = mm512Set1Pch(0.0, -1.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskConjPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val src = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    val r = mm512MaskConjPch(src, 0x5555u, a)
    val e = mm512SetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzConjPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val r = mm512MaskzConjPch(0x5555u, a)
    val e = mm512SetrPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmFmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmFmaddPch(a, b, c)
    val e = mmSet1Pch(-2.0, 3.0)
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmMaskFmaddPch(a, 0x5u, b, c)
    val e = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmMask3FmaddPch(a, b, c, 0x5u)
    val e = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmMaskzFmaddPch(0x5u, a, b, c)
    val e = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256FmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256FmaddPch(a, b, c)
    val e = mm256Set1Pch(-2.0, 3.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskFmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256MaskFmaddPch(a, 0x55u, b, c)
    val e = mm256SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256Mask3FmaddPch(a, b, c, 0x55u)
    val e = mm256SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256MaskzFmaddPch(0x55u, a, b, c)
    val e = mm256SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512FmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512FmaddPch(a, b, c)
    val e = mm512Set1Pch(-2.0, 3.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskFmaddPch(a, 0x5555u, b, c)
    val e = mm512SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512Mask3FmaddPch(a, b, c, 0x5555u)
    val e = mm512SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskzFmaddPch(0x5555u, a, b, c)
    val e = mm512SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512FmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512FmaddRoundPch(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pch(-2.0, 3.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskFmaddRoundPch(a, 0x5555u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512Mask3FmaddRoundPch(a, b, c, 0x5555u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskzFmaddRoundPch(0x5555u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmFmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmFmaddSch(a, b, c)
    val e = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskFmaddSch(a, 0u, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFmaddSch(a, 1u, b, c)
    val e1 = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMask3FmaddSch(a, b, c, 0u)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FmaddSch(a, b, c, 1u)
    val e1 = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskzFmaddSch(0u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFmaddSch(1u, a, b, c)
    val e1 = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmFmaddRoundSch(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskFmaddRoundSch(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFmaddRoundSch(a, 1u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMask3FmaddRoundSch(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FmaddRoundSch(a, b, c, 1u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskzFmaddRoundSch(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFmaddRoundSch(1u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFcmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmFcmaddPch(a, b, c)
    val e = mmSet1Pch(2.0, 3.0)
    assertEqM128h(r, e)
}

public fun testMmMaskFcmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmMaskFcmaddPch(a, 0x5u, b, c)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FcmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmMask3FcmaddPch(a, b, c, 0x5u)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFcmaddPch() {
    val a = mmSet1Pch(0.0, 1.0)
    val b = mmSet1Pch(0.0, 2.0)
    val c = mmSet1Pch(0.0, 3.0)
    val r = mmMaskzFcmaddPch(0x5u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM128h(r, e)
}

public fun testMm256FcmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256FcmaddPch(a, b, c)
    val e = mm256Set1Pch(2.0, 3.0)
    assertEqM256h(r, e)
}

public fun testMm256MaskFcmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256MaskFcmaddPch(a, 0x55u, b, c)
    val e = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FcmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256Mask3FcmaddPch(a, b, c, 0x55u)
    val e = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFcmaddPch() {
    val a = mm256Set1Pch(0.0, 1.0)
    val b = mm256Set1Pch(0.0, 2.0)
    val c = mm256Set1Pch(0.0, 3.0)
    val r = mm256MaskzFcmaddPch(0x55u, a, b, c)
    val e = mm256SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM256h(r, e)
}

public fun testMm512FcmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512FcmaddPch(a, b, c)
    val e = mm512Set1Pch(2.0, 3.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFcmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskFcmaddPch(a, 0x5555u, b, c)
    val e = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FcmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512Mask3FcmaddPch(a, b, c, 0x5555u)
    val e = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFcmaddPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskzFcmaddPch(0x5555u, a, b, c)
    val e = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMm512FcmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512FcmaddRoundPch(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pch(2.0, 3.0)
    assertEqM512h(r, e)
}

public fun testMm512MaskFcmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskFcmaddRoundPch(a, 0x5555u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FcmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512Mask3FcmaddRoundPch(a, b, c, 0x5555u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(3.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFcmaddRoundPch() {
    val a = mm512Set1Pch(0.0, 1.0)
    val b = mm512Set1Pch(0.0, 2.0)
    val c = mm512Set1Pch(0.0, 3.0)
    val r = mm512MaskzFcmaddRoundPch(0x5555u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    assertEqM512h(r, e)
}

public fun testMmFcmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmFcmaddSch(a, b, c)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFcmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskFcmaddSch(a, 0u, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFcmaddSch(a, 1u, b, c)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FcmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMask3FcmaddSch(a, b, c, 0u)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FcmaddSch(a, b, c, 1u)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFcmaddSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskzFcmaddSch(0u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFcmaddSch(1u, a, b, c)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFcmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmFcmaddRoundSch(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFcmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskFcmaddRoundSch(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFcmaddRoundSch(a, 1u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FcmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMask3FcmaddRoundSch(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FcmaddRoundSch(a, b, c, 1u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFcmaddRoundSch() {
    val a = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    val b = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f))
    val c = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(3.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f))
    val r = mmMaskzFcmaddRoundSch(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFcmaddRoundSch(1u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmFmaddPh(a, b, c)
    val e = mmSet1Ph(F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskFmaddPh(a, 0x55u, b, c)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMask3FmaddPh(a, b, c, 0x55u)
    val e = mmSetPh(F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzFmaddPh(0x55u, a, b, c)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMm256FmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256FmaddPh(a, b, c)
    val e = mm256Set1Ph(F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskFmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskFmaddPh(a, 0x5555u, b, c)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256Mask3FmaddPh(a, b, c, 0x5555u)
    val e = mm256SetPh(F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzFmaddPh(0x5555u, a, b, c)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm512FmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmaddPh(a, b, c)
    val e = mm512Set1Ph(F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmaddPh(a, 0x55555555u, b, c)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmaddPh(a, b, c, 0x55555555u)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmaddPh(0x55555555u, a, b, c)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512FmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmaddRoundPh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmaddRoundPh(a, 0x55555555u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmaddRoundPh(a, b, c, 0x55555555u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmaddRoundPh(0x55555555u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMmFmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFmaddSh(a, b, c)
    val e = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFmaddSh(a, 0u, b, c)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFmaddSh(a, 1u, b, c)
    val e1 = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FmaddSh(a, b, c, 0u)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FmaddSh(a, b, c, 1u)
    val e1 = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFmaddSh(0u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFmaddSh(1u, a, b, c)
    val e1 = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFmaddRoundSh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFmaddRoundSh(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFmaddRoundSh(a, 1u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FmaddRoundSh(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FmaddRoundSh(a, b, c, 1u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFmaddRoundSh(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFmaddRoundSh(1u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmFmsubPh(a, b, c)
    val e = mmSet1Ph(-F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskFmsubPh(a, 0x55u, b, c)
    val e = mmSetPh(F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMask3FmsubPh(a, b, c, 0x55u)
    val e = mmSetPh(F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzFmsubPh(0x55u, a, b, c)
    val e = mmSetPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256FmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256FmsubPh(a, b, c)
    val e = mm256Set1Ph(-F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskFmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskFmsubPh(a, 0x5555u, b, c)
    val e = mm256SetPh(F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256Mask3FmsubPh(a, b, c, 0x5555u)
    val e = mm256SetPh(F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzFmsubPh(0x5555u, a, b, c)
    val e = mm256SetPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512FmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmsubPh(a, b, c)
    val e = mm512Set1Ph(-F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmsubPh(a, 0x55555555u, b, c)
    val e = mm512SetPh(F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmsubPh(a, b, c, 0x55555555u)
    val e = mm512SetPh(F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmsubPh(0x55555555u, a, b, c)
    val e = mm512SetPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512FmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmsubRoundPh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(-F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmsubRoundPh(a, 0x55555555u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmsubRoundPh(a, b, c, 0x55555555u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmsubRoundPh(0x55555555u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmFmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFmsubSh(a, b, c)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFmsubSh(a, 0u, b, c)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFmsubSh(a, 1u, b, c)
    val e1 = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FmsubSh(a, b, c, 0u)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FmsubSh(a, b, c, 1u)
    val e1 = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFmsubSh(0u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFmsubSh(1u, a, b, c)
    val e1 = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFmsubRoundSh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFmsubRoundSh(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFmsubRoundSh(a, 1u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FmsubRoundSh(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FmsubRoundSh(a, b, c, 1u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFmsubRoundSh(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFmsubRoundSh(1u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFnmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmFnmaddPh(a, b, c)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFnmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskFnmaddPh(a, 0x55u, b, c)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FnmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMask3FnmaddPh(a, b, c, 0x55u)
    val e = mmSetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFnmaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzFnmaddPh(0x55u, a, b, c)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256FnmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256FnmaddPh(a, b, c)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskFnmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskFnmaddPh(a, 0x5555u, b, c)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FnmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256Mask3FnmaddPh(a, b, c, 0x5555u)
    val e = mm256SetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFnmaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzFnmaddPh(0x5555u, a, b, c)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512FnmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FnmaddPh(a, b, c)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFnmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFnmaddPh(a, 0x55555555u, b, c)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FnmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FnmaddPh(a, b, c, 0x55555555u)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFnmaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFnmaddPh(0x55555555u, a, b, c)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512FnmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FnmaddRoundPh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFnmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFnmaddRoundPh(a, 0x55555555u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FnmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FnmaddRoundPh(a, b, c, 0x55555555u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFnmaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFnmaddRoundPh(0x55555555u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmFnmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFnmaddSh(a, b, c)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFnmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFnmaddSh(a, 0u, b, c)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFnmaddSh(a, 1u, b, c)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FnmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FnmaddSh(a, b, c, 0u)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FnmaddSh(a, b, c, 1u)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFnmaddSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFnmaddSh(0u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFnmaddSh(1u, a, b, c)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFnmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFnmaddRoundSh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFnmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFnmaddRoundSh(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFnmaddRoundSh(a, 1u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FnmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FnmaddRoundSh(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FnmaddRoundSh(a, b, c, 1u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFnmaddRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFnmaddRoundSh(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFnmaddRoundSh(1u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFnmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmFnmsubPh(a, b, c)
    val e = mmSet1Ph(-F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFnmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskFnmsubPh(a, 0x55u, b, c)
    val e = mmSetPh(F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FnmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMask3FnmsubPh(a, b, c, 0x55u)
    val e = mmSetPh(F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFnmsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzFnmsubPh(0x55u, a, b, c)
    val e = mmSetPh(F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMm256FnmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256FnmsubPh(a, b, c)
    val e = mm256Set1Ph(-F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskFnmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskFnmsubPh(a, 0x5555u, b, c)
    val e = mm256SetPh(F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FnmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256Mask3FnmsubPh(a, b, c, 0x5555u)
    val e = mm256SetPh(F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFnmsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzFnmsubPh(0x5555u, a, b, c)
    val e = mm256SetPh(F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm512FnmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FnmsubPh(a, b, c)
    val e = mm512Set1Ph(-F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFnmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFnmsubPh(a, 0x55555555u, b, c)
    val e = mm512SetPh(F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FnmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FnmsubPh(a, b, c, 0x55555555u)
    val e = mm512SetPh(F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFnmsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFnmsubPh(0x55555555u, a, b, c)
    val e = mm512SetPh(F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512FnmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FnmsubRoundPh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(-F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFnmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFnmsubRoundPh(a, 0x55555555u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f), F16.fromFloat(1.0f), -F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FnmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FnmsubRoundPh(a, b, c, 0x55555555u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f), F16.fromFloat(3.0f), -F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFnmsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFnmsubRoundPh(0x55555555u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f), F16.fromFloat(0.0f), -F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMmFnmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFnmsubSh(a, b, c)
    val e = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFnmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFnmsubSh(a, 0u, b, c)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFnmsubSh(a, 1u, b, c)
    val e1 = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FnmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FnmsubSh(a, b, c, 0u)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FnmsubSh(a, b, c, 1u)
    val e1 = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFnmsubSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFnmsubSh(0u, a, b, c)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFnmsubSh(1u, a, b, c)
    val e1 = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFnmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmFnmsubRoundSh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFnmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskFnmsubRoundSh(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskFnmsubRoundSh(a, 1u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMask3FnmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMask3FnmsubRoundSh(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r, e)
    val r1 = mmMask3FnmsubRoundSh(a, b, c, 1u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzFnmsubRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val c = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskzFnmsubRoundSh(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzFnmsubRoundSh(1u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(-F16.fromFloat(5.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmFmaddsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmFmaddsubPh(a, b, c)
    val e = mmSetPh(F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmaddsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskFmaddsubPh(a, 0x33u, b, c)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FmaddsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMask3FmaddsubPh(a, b, c, 0x33u)
    val e = mmSetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmaddsubPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzFmaddsubPh(0x33u, a, b, c)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256FmaddsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256FmaddsubPh(a, b, c)
    val e = mm256SetPh(F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskFmaddsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskFmaddsubPh(a, 0x3333u, b, c)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FmaddsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256Mask3FmaddsubPh(a, b, c, 0x3333u)
    val e = mm256SetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFmaddsubPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzFmaddsubPh(0x3333u, a, b, c)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512FmaddsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmaddsubPh(a, b, c)
    val e = mm512SetPh(F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmaddsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmaddsubPh(a, 0x33333333u, b, c)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmaddsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmaddsubPh(a, b, c, 0x33333333u)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmaddsubPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmaddsubPh(0x33333333u, a, b, c)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512FmaddsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmaddsubRoundPh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmaddsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmaddsubRoundPh(a, 0x33333333u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmaddsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmaddsubRoundPh(a, b, c, 0x33333333u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmaddsubRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmaddsubRoundPh(0x33333333u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmFmsubaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmFmsubaddPh(a, b, c)
    val e = mmSetPh(-F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskFmsubaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskFmsubaddPh(a, 0x33u, b, c)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMask3FmsubaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMask3FmsubaddPh(a, b, c, 0x33u)
    val e = mmSetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzFmsubaddPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(2.0f))
    val c = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzFmsubaddPh(0x33u, a, b, c)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM128h(r, e)
}

public fun testMm256FmsubaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256FmsubaddPh(a, b, c)
    val e = mm256SetPh(-F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskFmsubaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskFmsubaddPh(a, 0x3333u, b, c)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256Mask3FmsubaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256Mask3FmsubaddPh(a, b, c, 0x3333u)
    val e = mm256SetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzFmsubaddPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(2.0f))
    val c = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzFmsubaddPh(0x3333u, a, b, c)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM256h(r, e)
}

public fun testMm512FmsubaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmsubaddPh(a, b, c)
    val e = mm512SetPh(-F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmsubaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmsubaddPh(a, 0x33333333u, b, c)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmsubaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmsubaddPh(a, b, c, 0x33333333u)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmsubaddPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmsubaddPh(0x33333333u, a, b, c)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512FmsubaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512FmsubaddRoundPh(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(-F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskFmsubaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskFmsubaddRoundPh(a, 0x33333333u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(1.0f), F16.fromFloat(1.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512Mask3FmsubaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512Mask3FmsubaddRoundPh(a, b, c, 0x33333333u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(3.0f), F16.fromFloat(3.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzFmsubaddRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(2.0f))
    val c = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzFmsubaddRoundPh(0x33333333u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), -F16.fromFloat(1.0f), F16.fromFloat(5.0f))
    assertEqM512h(r, e)
}

public fun testMmRcpPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmRcpPh(a)
    val e = mmSet1Ph(F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskRcpPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val src = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMaskRcpPh(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskzRcpPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmMaskzRcpPh(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMm256RcpPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256RcpPh(a)
    val e = mm256Set1Ph(F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm256MaskRcpPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val src = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MaskRcpPh(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzRcpPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256MaskzRcpPh(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm512RcpPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512RcpPh(a)
    val e = mm512Set1Ph(F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskRcpPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val src = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskRcpPh(src, 0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzRcpPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskzRcpPh(0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMmRcpSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmRcpSh(a, b)
    val e = mmSetrPh(F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskRcpSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskRcpSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskRcpSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzRcpSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzRcpSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzRcpSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmRsqrtPh() {
    val a = mmSet1Ph(F16.fromFloat(4.0f))
    val r = mmRsqrtPh(a)
    val e = mmSet1Ph(F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskRsqrtPh() {
    val a = mmSet1Ph(F16.fromFloat(4.0f))
    val src = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMaskRsqrtPh(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMmMaskzRsqrtPh() {
    val a = mmSet1Ph(F16.fromFloat(4.0f))
    val r = mmMaskzRsqrtPh(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM128h(r, e)
}

public fun testMm256RsqrtPh() {
    val a = mm256Set1Ph(F16.fromFloat(4.0f))
    val r = mm256RsqrtPh(a)
    val e = mm256Set1Ph(F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm256MaskRsqrtPh() {
    val a = mm256Set1Ph(F16.fromFloat(4.0f))
    val src = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MaskRsqrtPh(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzRsqrtPh() {
    val a = mm256Set1Ph(F16.fromFloat(4.0f))
    val r = mm256MaskzRsqrtPh(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM256h(r, e)
}

public fun testMm512RsqrtPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512RsqrtPh(a)
    val e = mm512Set1Ph(F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskRsqrtPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val src = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskRsqrtPh(src, 0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f), F16.fromFloat(1.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzRsqrtPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512MaskzRsqrtPh(0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f), F16.fromFloat(0.0f), F16.fromFloat(0.5f))
    assertEqM512h(r, e)
}

public fun testMmRsqrtSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val r = mmRsqrtSh(a, b)
    val e = mmSetrPh(F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskRsqrtSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskRsqrtSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskRsqrtSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzRsqrtSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val r = mmMaskzRsqrtSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzRsqrtSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(0.5f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmSqrtPh() {
    val a = mmSet1Ph(F16.fromFloat(4.0f))
    val r = mmSqrtPh(a)
    val e = mmSet1Ph(F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskSqrtPh() {
    val a = mmSet1Ph(F16.fromFloat(4.0f))
    val src = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMaskSqrtPh(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzSqrtPh() {
    val a = mmSet1Ph(F16.fromFloat(4.0f))
    val r = mmMaskzSqrtPh(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMm256SqrtPh() {
    val a = mm256Set1Ph(F16.fromFloat(4.0f))
    val r = mm256SqrtPh(a)
    val e = mm256Set1Ph(F16.fromFloat(2.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskSqrtPh() {
    val a = mm256Set1Ph(F16.fromFloat(4.0f))
    val src = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MaskSqrtPh(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzSqrtPh() {
    val a = mm256Set1Ph(F16.fromFloat(4.0f))
    val r = mm256MaskzSqrtPh(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM256h(r, e)
}

public fun testMm512SqrtPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512SqrtPh(a)
    val e = mm512Set1Ph(F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskSqrtPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val src = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskSqrtPh(src, 0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzSqrtPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512MaskzSqrtPh(0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512SqrtRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512SqrtRoundPh(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskSqrtRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val src = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskSqrtRoundPh(src, 0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzSqrtRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512MaskzSqrtRoundPh(0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMmSqrtSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val r = mmSqrtSh(a, b)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskSqrtSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskSqrtSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskSqrtSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzSqrtSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val r = mmMaskzSqrtSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzSqrtSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmSqrtRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val r = mmSqrtRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskSqrtRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskSqrtRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskSqrtRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzSqrtRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f))
    val r = mmMaskzSqrtRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzSqrtRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaxPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val b = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMaxPh(a, b)
    val e = mmSet1Ph(F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMaxPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val b = mmSet1Ph(F16.fromFloat(1.0f))
    val src = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskMaxPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMaxPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val b = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMaskzMaxPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaxPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val b = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MaxPh(a, b)
    val e = mm256Set1Ph(F16.fromFloat(2.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskMaxPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val b = mm256Set1Ph(F16.fromFloat(1.0f))
    val src = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskMaxPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzMaxPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val b = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MaskzMaxPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaxPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaxPh(a, b)
    val e = mm512Set1Ph(F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskMaxPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val src = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskMaxPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMaxPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskzMaxPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaxRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaxRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskMaxRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val src = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskMaxRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMaxRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskzMaxRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM512h(r, e)
}

public fun testMmMaxSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaxSh(a, b)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMaxSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskMaxSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskMaxSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzMaxSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzMaxSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzMaxSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaxRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaxRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMaxRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskMaxRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskMaxRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzMaxRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzMaxRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzMaxRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMinPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val b = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMinPh(a, b)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMinPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val b = mmSet1Ph(F16.fromFloat(1.0f))
    val src = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskMinPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzMinPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val b = mmSet1Ph(F16.fromFloat(1.0f))
    val r = mmMaskzMinPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256MinPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val b = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MinPh(a, b)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskMinPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val b = mm256Set1Ph(F16.fromFloat(1.0f))
    val src = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskMinPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzMinPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val b = mm256Set1Ph(F16.fromFloat(1.0f))
    val r = mm256MaskzMinPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512MinPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MinPh(a, b)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskMinPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val src = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskMinPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMinPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskzMinPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MinRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MinRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskMinRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val src = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskMinRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzMinRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val b = mm512Set1Ph(F16.fromFloat(1.0f))
    val r = mm512MaskzMinRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmMinSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMinSh(a, b)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMinSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskMinSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskMinSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzMinSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzMinSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzMinSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMinRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMinRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskMinRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskMinRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskMinRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzMinRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzMinRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzMinRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmGetexpPh() {
    val a = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmGetexpPh(a)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskGetexpPh() {
    val a = mmSet1Ph(F16.fromFloat(3.0f))
    val src = mmSet1Ph(F16.fromFloat(4.0f))
    val r = mmMaskGetexpPh(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzGetexpPh() {
    val a = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzGetexpPh(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256GetexpPh() {
    val a = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256GetexpPh(a)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskGetexpPh() {
    val a = mm256Set1Ph(F16.fromFloat(3.0f))
    val src = mm256Set1Ph(F16.fromFloat(4.0f))
    val r = mm256MaskGetexpPh(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzGetexpPh() {
    val a = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzGetexpPh(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512GetexpPh() {
    val a = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512GetexpPh(a)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskGetexpPh() {
    val a = mm512Set1Ph(F16.fromFloat(3.0f))
    val src = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512MaskGetexpPh(src, 0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzGetexpPh() {
    val a = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzGetexpPh(0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512GetexpRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512GetexpRoundPh(a, MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskGetexpRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(3.0f))
    val src = mm512Set1Ph(F16.fromFloat(4.0f))
    val r = mm512MaskGetexpRoundPh(src, 0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f), F16.fromFloat(4.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzGetexpRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzGetexpRoundPh(0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmGetexpSh() {
    val a = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmGetexpSh(a, b)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskGetexpSh() {
    val a = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskGetexpSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskGetexpSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzGetexpSh() {
    val a = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzGetexpSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzGetexpSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmGetexpRoundSh() {
    val a = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmGetexpRoundSh(a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskGetexpRoundSh() {
    val a = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskGetexpRoundSh(src, 0u, a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskGetexpRoundSh(src, 1u, a, b, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzGetexpRoundSh() {
    val a = mmSetrPh(F16.fromFloat(4.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzGetexpRoundSh(0u, a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzGetexpRoundSh(1u, a, b, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmGetmantPh() {
    val a = mmSet1Ph(F16.fromFloat(10.0f))
    val r = mmGetmantPh(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSet1Ph(F16.fromFloat(1.25f))
    assertEqM128h(r, e)
}

public fun testMmMaskGetmantPh() {
    val a = mmSet1Ph(F16.fromFloat(10.0f))
    val src = mmSet1Ph(F16.fromFloat(20.0f))
    val r = mmMaskGetmantPh(src, 0x55u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSetPh(F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f))
    assertEqM128h(r, e)
}

public fun testMmMaskzGetmantPh() {
    val a = mmSet1Ph(F16.fromFloat(10.0f))
    val r = mmMaskzGetmantPh(0x55u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f))
    assertEqM128h(r, e)
}

public fun testMm256GetmantPh() {
    val a = mm256Set1Ph(F16.fromFloat(10.0f))
    val r = mm256GetmantPh(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm256Set1Ph(F16.fromFloat(1.25f))
    assertEqM256h(r, e)
}

public fun testMm256MaskGetmantPh() {
    val a = mm256Set1Ph(F16.fromFloat(10.0f))
    val src = mm256Set1Ph(F16.fromFloat(20.0f))
    val r = mm256MaskGetmantPh(src, 0x5555u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm256SetPh(F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzGetmantPh() {
    val a = mm256Set1Ph(F16.fromFloat(10.0f))
    val r = mm256MaskzGetmantPh(0x5555u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f))
    assertEqM256h(r, e)
}

public fun testMm512GetmantPh() {
    val a = mm512Set1Ph(F16.fromFloat(10.0f))
    val r = mm512GetmantPh(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm512Set1Ph(F16.fromFloat(1.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskGetmantPh() {
    val a = mm512Set1Ph(F16.fromFloat(10.0f))
    val src = mm512Set1Ph(F16.fromFloat(20.0f))
    val r = mm512MaskGetmantPh(src, 0x55555555u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm512SetPh(F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzGetmantPh() {
    val a = mm512Set1Ph(F16.fromFloat(10.0f))
    val r = mm512MaskzGetmantPh(0x55555555u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f))
    assertEqM512h(r, e)
}

public fun testMm512GetmantRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(10.0f))
    val r = mm512GetmantRoundPh(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(1.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskGetmantRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(10.0f))
    val src = mm512Set1Ph(F16.fromFloat(20.0f))
    val r = mm512MaskGetmantRoundPh(src, 0x55555555u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(1.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzGetmantRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(10.0f))
    val r = mm512MaskzGetmantRoundPh(0x55555555u, a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f), F16.fromFloat(0.0f), F16.fromFloat(1.25f))
    assertEqM512h(r, e)
}

public fun testMmGetmantSh() {
    val a = mmSetrPh(F16.fromFloat(15.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmGetmantSh(a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskGetmantSh() {
    val a = mmSetrPh(F16.fromFloat(15.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskGetmantSh(src, 0u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskGetmantSh(src, 1u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e1 = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzGetmantSh() {
    val a = mmSetrPh(F16.fromFloat(15.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzGetmantSh(0u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzGetmantSh(1u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e1 = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmGetmantRoundSh() {
    val a = mmSetrPh(F16.fromFloat(15.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmGetmantRoundSh(a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskGetmantRoundSh() {
    val a = mmSetrPh(F16.fromFloat(15.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskGetmantRoundSh(src, 0u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskGetmantRoundSh(src, 1u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzGetmantRoundSh() {
    val a = mmSetrPh(F16.fromFloat(15.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzGetmantRoundSh(0u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzGetmantRoundSh(1u, a, b, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmRoundscalePh() {
    val a = mmSet1Ph(F16.fromFloat(1.1f))
    val r = mmRoundscalePh(a, 0)
    val e = mmSet1Ph(F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskRoundscalePh() {
    val a = mmSet1Ph(F16.fromFloat(1.1f))
    val src = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmMaskRoundscalePh(src, 0x55u, a, 0)
    val e = mmSetPh(F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzRoundscalePh() {
    val a = mmSet1Ph(F16.fromFloat(1.1f))
    val r = mmMaskzRoundscalePh(0x55u, a, 0)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM128h(r, e)
}

public fun testMm256RoundscalePh() {
    val a = mm256Set1Ph(F16.fromFloat(1.1f))
    val r = mm256RoundscalePh(a, 0)
    val e = mm256Set1Ph(F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskRoundscalePh() {
    val a = mm256Set1Ph(F16.fromFloat(1.1f))
    val src = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256MaskRoundscalePh(src, 0x5555u, a, 0)
    val e = mm256SetPh(F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzRoundscalePh() {
    val a = mm256Set1Ph(F16.fromFloat(1.1f))
    val r = mm256MaskzRoundscalePh(0x5555u, a, 0)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM256h(r, e)
}

public fun testMm512RoundscalePh() {
    val a = mm512Set1Ph(F16.fromFloat(1.1f))
    val r = mm512RoundscalePh(a, 0)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskRoundscalePh() {
    val a = mm512Set1Ph(F16.fromFloat(1.1f))
    val src = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskRoundscalePh(src, 0x55555555u, a, 0)
    val e = mm512SetPh(F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzRoundscalePh() {
    val a = mm512Set1Ph(F16.fromFloat(1.1f))
    val r = mm512MaskzRoundscalePh(0x55555555u, a, 0)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512RoundscaleRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.1f))
    val r = mm512RoundscaleRoundPh(a, 0, MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskRoundscaleRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.1f))
    val src = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskRoundscaleRoundPh(src, 0x55555555u, a, 0, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzRoundscaleRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.1f))
    val r = mm512MaskzRoundscaleRoundPh(0x55555555u, a, 0, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f))
    assertEqM512h(r, e)
}

public fun testMmRoundscaleSh() {
    val a = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.1f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmRoundscaleSh(a, b, 0)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskRoundscaleSh() {
    val a = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.1f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskRoundscaleSh(src, 0u, a, b, 0)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskRoundscaleSh(src, 1u, a, b, 0)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzRoundscaleSh() {
    val a = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.1f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzRoundscaleSh(0u, a, b, 0)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzRoundscaleSh(1u, a, b, 0)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmRoundscaleRoundSh() {
    val a = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.1f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmRoundscaleRoundSh(a, b, 0, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskRoundscaleRoundSh() {
    val a = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.1f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskRoundscaleRoundSh(src, 0u, a, b, 0, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskRoundscaleRoundSh(src, 1u, a, b, 0, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzRoundscaleRoundSh() {
    val a = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.1f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzRoundscaleRoundSh(0u, a, b, 0, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzRoundscaleRoundSh(1u, a, b, 0, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmScalefPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmScalefPh(a, b)
    val e = mmSet1Ph(F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskScalefPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(3.0f))
    val src = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmMaskScalefPh(src, 0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzScalefPh() {
    val a = mmSet1Ph(F16.fromFloat(1.0f))
    val b = mmSet1Ph(F16.fromFloat(3.0f))
    val r = mmMaskzScalefPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256ScalefPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256ScalefPh(a, b)
    val e = mm256Set1Ph(F16.fromFloat(8.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskScalefPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(3.0f))
    val src = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256MaskScalefPh(src, 0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzScalefPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.0f))
    val b = mm256Set1Ph(F16.fromFloat(3.0f))
    val r = mm256MaskzScalefPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM256h(r, e)
}

public fun testMm512ScalefPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512ScalefPh(a, b)
    val e = mm512Set1Ph(F16.fromFloat(8.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskScalefPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(3.0f))
    val src = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskScalefPh(src, 0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzScalefPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzScalefPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM512h(r, e)
}

public fun testMm512ScalefRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512ScalefRoundPh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(8.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskScalefRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(3.0f))
    val src = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskScalefRoundPh(src, 0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f), F16.fromFloat(2.0f), F16.fromFloat(8.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzScalefRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.0f))
    val b = mm512Set1Ph(F16.fromFloat(3.0f))
    val r = mm512MaskzScalefRoundPh(0x55555555u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM512h(r, e)
}

public fun testMmScalefSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmScalefSh(a, b)
    val e = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskScalefSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskScalefSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskScalefSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzScalefSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzScalefSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzScalefSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmScalefRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmScalefRoundSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskScalefRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskScalefRoundSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskScalefRoundSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzScalefRoundSh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzScalefRoundSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzScalefRoundSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmReducePh() {
    val a = mmSet1Ph(F16.fromFloat(1.25f))
    val r = mmReducePh(a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSet1Ph(F16.fromFloat(0.25f))
    assertEqM128h(r, e)
}

public fun testMmMaskReducePh() {
    val a = mmSet1Ph(F16.fromFloat(1.25f))
    val src = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmMaskReducePh(src, 0x55u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPh(F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f))
    assertEqM128h(r, e)
}

public fun testMmMaskzReducePh() {
    val a = mmSet1Ph(F16.fromFloat(1.25f))
    val r = mmMaskzReducePh(0x55u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f))
    assertEqM128h(r, e)
}

public fun testMm256ReducePh() {
    val a = mm256Set1Ph(F16.fromFloat(1.25f))
    val r = mm256ReducePh(a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256Set1Ph(F16.fromFloat(0.25f))
    assertEqM256h(r, e)
}

public fun testMm256MaskReducePh() {
    val a = mm256Set1Ph(F16.fromFloat(1.25f))
    val src = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256MaskReducePh(src, 0x5555u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPh(F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzReducePh() {
    val a = mm256Set1Ph(F16.fromFloat(1.25f))
    val r = mm256MaskzReducePh(0x5555u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f))
    assertEqM256h(r, e)
}

public fun testMm512ReducePh() {
    val a = mm512Set1Ph(F16.fromFloat(1.25f))
    val r = mm512ReducePh(a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512Set1Ph(F16.fromFloat(0.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskReducePh() {
    val a = mm512Set1Ph(F16.fromFloat(1.25f))
    val src = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskReducePh(src, 0x55555555u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPh(F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzReducePh() {
    val a = mm512Set1Ph(F16.fromFloat(1.25f))
    val r = mm512MaskzReducePh(0x55555555u, a, 16 or MM_FROUND_TO_ZERO)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f))
    assertEqM512h(r, e)
}

public fun testMm512ReduceRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.25f))
    val r = mm512ReduceRoundPh(a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512Set1Ph(F16.fromFloat(0.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskReduceRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.25f))
    val src = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512MaskReduceRoundPh(src, 0x55555555u, a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f), F16.fromFloat(2.0f), F16.fromFloat(0.25f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzReduceRoundPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.25f))
    val r = mm512MaskzReduceRoundPh(0x55555555u, a, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f), F16.fromFloat(0.0f), F16.fromFloat(0.25f))
    assertEqM512h(r, e)
}

public fun testMmReduceSh() {
    val a = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmReduceSh(a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetrPh(F16.fromFloat(0.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskReduceSh() {
    val a = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskReduceSh(src, 0u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskReduceSh(src, 1u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e1 = mmSetrPh(F16.fromFloat(0.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzReduceSh() {
    val a = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzReduceSh(0u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzReduceSh(1u, a, b, 16 or MM_FROUND_TO_ZERO)
    val e1 = mmSetrPh(F16.fromFloat(0.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmReduceRoundSh() {
    val a = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmReduceRoundSh(a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskReduceRoundSh() {
    val a = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val src = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f))
    val r = mmMaskReduceRoundSh(src, 0u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(2.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskReduceRoundSh(src, 1u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(0.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzReduceRoundSh() {
    val a = mmSetrPh(F16.fromFloat(3.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mmSetrPh(F16.fromFloat(1.25f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f))
    val r = mmMaskzReduceRoundSh(0u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzReduceRoundSh(1u, a, b, 16 or MM_FROUND_TO_ZERO, MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(0.25f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM128h(r1, e1)
}

public fun testMmReduceAddPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmReduceAddPh(a)
    check(r == F16.fromFloat(16.0f))
}

public fun testMm256ReduceAddPh() {
    val a = mm256Set1Ph(F16.fromFloat(2.0f))
    val r = mm256ReduceAddPh(a)
    check(r == F16.fromFloat(32.0f))
}

public fun testMm512ReduceAddPh() {
    val a = mm512Set1Ph(F16.fromFloat(2.0f))
    val r = mm512ReduceAddPh(a)
    check(r == F16.fromFloat(64.0f))
}

public fun testMmReduceMulPh() {
    val a = mmSet1Ph(F16.fromFloat(2.0f))
    val r = mmReduceMulPh(a)
    check(r == F16.fromFloat(256.0f))
}

public fun testMm256ReduceMulPh() {
    val a = mm256Set1Ph(F16.fromFloat(1.2f))
    val r = mm256ReduceMulPh(a)
    check(r == F16.fromFloat(18.5f))
}

public fun testMm512ReduceMulPh() {
    val a = mm512Set1Ph(F16.fromFloat(1.2f))
    val r = mm512ReduceMulPh(a)
    check(r == F16.fromFloat(342.3f))
}

public fun testMmReduceMaxPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmReduceMaxPh(a)
    check(r == F16.fromFloat(8.0f))
}

public fun testMm256ReduceMaxPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256ReduceMaxPh(a)
    check(r == F16.fromFloat(16.0f))
}

public fun testMm512ReduceMaxPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512ReduceMaxPh(a)
    check(r == F16.fromFloat(32.0f))
}

public fun testMmReduceMinPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmReduceMinPh(a)
    check(r == F16.fromFloat(1.0f))
}

public fun testMm256ReduceMinPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256ReduceMinPh(a)
    check(r == F16.fromFloat(1.0f))
}

public fun testMm512ReduceMinPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512ReduceMinPh(a)
    check(r == F16.fromFloat(1.0f))
}

public fun testMmFpclassPhMask() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f))
    val r = mmFpclassPhMask(a, 0x18)
    // infinities
    check(r == 0x60u.toUByte())
}

public fun testMmMaskFpclassPhMask() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f))
    val r = mmMaskFpclassPhMask(0x55u, a, 0x18)
    check(r == 0x40u.toUByte())
}

public fun testMm256FpclassPhMask() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f))
    val r = mm256FpclassPhMask(a, 0x18)
    // infinities
    check(r == 0x6060u.toUShort())
}

public fun testMm256MaskFpclassPhMask() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f))
    val r = mm256MaskFpclassPhMask(0x5555u, a, 0x18)
    check(r == 0x4040u.toUShort())
}

public fun testMm512FpclassPhMask() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f))
    val r = mm512FpclassPhMask(a, 0x18)
    // infinities
    check(r == 0x60606060u)
}

public fun testMm512MaskFpclassPhMask() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f), F16.fromFloat(1.0f), F16.INFINITY, F16.NEG_INFINITY, F16.fromFloat(0.0f), -F16.fromFloat(0.0f), -F16.fromFloat(2.0f), F16.NAN, F16.fromFloat(5.9e-8f))
    val r = mm512MaskFpclassPhMask(0x55555555u, a, 0x18)
    check(r == 0x40404040u)
}

public fun testMmFpclassShMask() {
    val a = mmSetSh(F16.INFINITY)
    val r = mmFpclassShMask(a, 0x18)
    check(r == 1u.toUByte())
}

public fun testMmMaskFpclassShMask() {
    val a = mmSetSh(F16.INFINITY)
    val r = mmMaskFpclassShMask(0u, a, 0x18)
    check(r == 0u.toUByte())
    val r1 = mmMaskFpclassShMask(1u, a, 0x18)
    check(r1 == 1u.toUByte())
}

public fun testMmMaskBlendPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetPh(-F16.fromFloat(1.0f), -F16.fromFloat(2.0f), -F16.fromFloat(3.0f), -F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f))
    val r = mmMaskBlendPh(0x55u, a, b)
    val e = mmSetPh(F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), -F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(6.0f), F16.fromFloat(7.0f), -F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskBlendPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetPh(-F16.fromFloat(1.0f), -F16.fromFloat(2.0f), -F16.fromFloat(3.0f), -F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), -F16.fromFloat(9.0f), -F16.fromFloat(10.0f), -F16.fromFloat(11.0f), -F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f))
    val r = mm256MaskBlendPh(0x5555u, a, b)
    val e = mm256SetPh(F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), -F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(6.0f), F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(10.0f), F16.fromFloat(11.0f), -F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(14.0f), F16.fromFloat(15.0f), -F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskBlendPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetPh(-F16.fromFloat(1.0f), -F16.fromFloat(2.0f), -F16.fromFloat(3.0f), -F16.fromFloat(4.0f), -F16.fromFloat(5.0f), -F16.fromFloat(6.0f), -F16.fromFloat(7.0f), -F16.fromFloat(8.0f), -F16.fromFloat(9.0f), -F16.fromFloat(10.0f), -F16.fromFloat(11.0f), -F16.fromFloat(12.0f), -F16.fromFloat(13.0f), -F16.fromFloat(14.0f), -F16.fromFloat(15.0f), -F16.fromFloat(16.0f), -F16.fromFloat(17.0f), -F16.fromFloat(18.0f), -F16.fromFloat(19.0f), -F16.fromFloat(20.0f), -F16.fromFloat(21.0f), -F16.fromFloat(22.0f), -F16.fromFloat(23.0f), -F16.fromFloat(24.0f), -F16.fromFloat(25.0f), -F16.fromFloat(26.0f), -F16.fromFloat(27.0f), -F16.fromFloat(28.0f), -F16.fromFloat(29.0f), -F16.fromFloat(30.0f), -F16.fromFloat(31.0f), -F16.fromFloat(32.0f))
    val r = mm512MaskBlendPh(0x55555555u, a, b)
    val e = mm512SetPh(F16.fromFloat(1.0f), -F16.fromFloat(2.0f), F16.fromFloat(3.0f), -F16.fromFloat(4.0f), F16.fromFloat(5.0f), -F16.fromFloat(6.0f), F16.fromFloat(7.0f), -F16.fromFloat(8.0f), F16.fromFloat(9.0f), -F16.fromFloat(10.0f), F16.fromFloat(11.0f), -F16.fromFloat(12.0f), F16.fromFloat(13.0f), -F16.fromFloat(14.0f), F16.fromFloat(15.0f), -F16.fromFloat(16.0f), F16.fromFloat(17.0f), -F16.fromFloat(18.0f), F16.fromFloat(19.0f), -F16.fromFloat(20.0f), F16.fromFloat(21.0f), -F16.fromFloat(22.0f), F16.fromFloat(23.0f), -F16.fromFloat(24.0f), F16.fromFloat(25.0f), -F16.fromFloat(26.0f), F16.fromFloat(27.0f), -F16.fromFloat(28.0f), F16.fromFloat(29.0f), -F16.fromFloat(30.0f), F16.fromFloat(31.0f), -F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMmPermutex2varPh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val b = mmSetrPh(F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val idx = mmSetrEpi16(0, 2, 4, 6, 8, 10, 12, 14)
    val r = mmPermutex2varPh(a, idx, b)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f))
    assertEqM128h(r, e)
}

public fun testMm256Permutex2varPh() {
    val a = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val b = mm256SetrPh(F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val idx = mm256SetrEpi16(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)
    val r = mm256Permutex2varPh(a, idx, b)
    val e = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f), F16.fromFloat(17.0f), F16.fromFloat(19.0f), F16.fromFloat(21.0f), F16.fromFloat(23.0f), F16.fromFloat(25.0f), F16.fromFloat(27.0f), F16.fromFloat(29.0f), F16.fromFloat(31.0f))
    assertEqM256h(r, e)
}

public fun testMm512Permutex2varPh() {
    val a = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val b = mm512SetrPh(F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f), F16.fromFloat(42.0f), F16.fromFloat(43.0f), F16.fromFloat(44.0f), F16.fromFloat(45.0f), F16.fromFloat(46.0f), F16.fromFloat(47.0f), F16.fromFloat(48.0f), F16.fromFloat(49.0f), F16.fromFloat(50.0f), F16.fromFloat(51.0f), F16.fromFloat(52.0f), F16.fromFloat(53.0f), F16.fromFloat(54.0f), F16.fromFloat(55.0f), F16.fromFloat(56.0f), F16.fromFloat(57.0f), F16.fromFloat(58.0f), F16.fromFloat(59.0f), F16.fromFloat(60.0f), F16.fromFloat(61.0f), F16.fromFloat(62.0f), F16.fromFloat(63.0f), F16.fromFloat(64.0f))
    val idx = mm512SetEpi16(62, 60, 58, 56, 54, 52, 50, 48, 46, 44, 42, 40, 38, 36, 34, 32, 30, 28, 26, 24, 22, 20, 18, 16, 14, 12, 10, 8, 6, 4, 2, 0)
    val r = mm512Permutex2varPh(a, idx, b)
    val e = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f), F16.fromFloat(17.0f), F16.fromFloat(19.0f), F16.fromFloat(21.0f), F16.fromFloat(23.0f), F16.fromFloat(25.0f), F16.fromFloat(27.0f), F16.fromFloat(29.0f), F16.fromFloat(31.0f), F16.fromFloat(33.0f), F16.fromFloat(35.0f), F16.fromFloat(37.0f), F16.fromFloat(39.0f), F16.fromFloat(41.0f), F16.fromFloat(43.0f), F16.fromFloat(45.0f), F16.fromFloat(47.0f), F16.fromFloat(49.0f), F16.fromFloat(51.0f), F16.fromFloat(53.0f), F16.fromFloat(55.0f), F16.fromFloat(57.0f), F16.fromFloat(59.0f), F16.fromFloat(61.0f), F16.fromFloat(63.0f))
    assertEqM512h(r, e)
}

public fun testMmPermutexvarPh() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val idx = mmSetEpi16(0, 2, 4, 6, 1, 3, 5, 7)
    val r = mmPermutexvarPh(idx, a)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(2.0f), F16.fromFloat(4.0f), F16.fromFloat(6.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256PermutexvarPh() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val idx = mm256SetEpi16(0, 2, 4, 6, 8, 10, 12, 14, 1, 3, 5, 7, 9, 11, 13, 15)
    val r = mm256PermutexvarPh(idx, a)
    val e = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f), F16.fromFloat(2.0f), F16.fromFloat(4.0f), F16.fromFloat(6.0f), F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(12.0f), F16.fromFloat(14.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512PermutexvarPh() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val idx = mm512SetEpi16(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
    val r = mm512PermutexvarPh(idx, a)
    val e = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(3.0f), F16.fromFloat(5.0f), F16.fromFloat(7.0f), F16.fromFloat(9.0f), F16.fromFloat(11.0f), F16.fromFloat(13.0f), F16.fromFloat(15.0f), F16.fromFloat(17.0f), F16.fromFloat(19.0f), F16.fromFloat(21.0f), F16.fromFloat(23.0f), F16.fromFloat(25.0f), F16.fromFloat(27.0f), F16.fromFloat(29.0f), F16.fromFloat(31.0f), F16.fromFloat(2.0f), F16.fromFloat(4.0f), F16.fromFloat(6.0f), F16.fromFloat(8.0f), F16.fromFloat(10.0f), F16.fromFloat(12.0f), F16.fromFloat(14.0f), F16.fromFloat(16.0f), F16.fromFloat(18.0f), F16.fromFloat(20.0f), F16.fromFloat(22.0f), F16.fromFloat(24.0f), F16.fromFloat(26.0f), F16.fromFloat(28.0f), F16.fromFloat(30.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMmCvtepi16Ph() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmCvtepi16Ph(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtepi16Ph() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtepi16Ph(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtepi16Ph() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmMaskzCvtepi16Ph(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256Cvtepi16Ph() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256Cvtepi16Ph(a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskCvtepi16Ph() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm256MaskCvtepi16Ph(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzCvtepi16Ph() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256MaskzCvtepi16Ph(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512Cvtepi16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512Cvtepi16Ph(a)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskCvtepi16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val src = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f))
    val r = mm512MaskCvtepi16Ph(src, 0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f), F16.fromFloat(26.0f), F16.fromFloat(18.0f), F16.fromFloat(28.0f), F16.fromFloat(20.0f), F16.fromFloat(30.0f), F16.fromFloat(22.0f), F16.fromFloat(32.0f), F16.fromFloat(24.0f), F16.fromFloat(34.0f), F16.fromFloat(26.0f), F16.fromFloat(36.0f), F16.fromFloat(28.0f), F16.fromFloat(38.0f), F16.fromFloat(30.0f), F16.fromFloat(40.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzCvtepi16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzCvtepi16Ph(0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(0.0f), F16.fromFloat(18.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(0.0f), F16.fromFloat(22.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(0.0f), F16.fromFloat(26.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(0.0f), F16.fromFloat(30.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512CvtRoundepi16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512CvtRoundepi16Ph(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskCvtRoundepi16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val src = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f))
    val r = mm512MaskCvtRoundepi16Ph(src, 0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f), F16.fromFloat(26.0f), F16.fromFloat(18.0f), F16.fromFloat(28.0f), F16.fromFloat(20.0f), F16.fromFloat(30.0f), F16.fromFloat(22.0f), F16.fromFloat(32.0f), F16.fromFloat(24.0f), F16.fromFloat(34.0f), F16.fromFloat(26.0f), F16.fromFloat(36.0f), F16.fromFloat(28.0f), F16.fromFloat(38.0f), F16.fromFloat(30.0f), F16.fromFloat(40.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzCvtRoundepi16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzCvtRoundepi16Ph(0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(0.0f), F16.fromFloat(18.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(0.0f), F16.fromFloat(22.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(0.0f), F16.fromFloat(26.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(0.0f), F16.fromFloat(30.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMmCvtepu16Ph() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmCvtepu16Ph(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtepu16Ph() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtepu16Ph(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtepu16Ph() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmMaskzCvtepu16Ph(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256Cvtepu16Ph() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256Cvtepu16Ph(a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskCvtepu16Ph() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm256MaskCvtepu16Ph(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm256MaskzCvtepu16Ph() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256MaskzCvtepu16Ph(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512Cvtepu16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512Cvtepu16Ph(a)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskCvtepu16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val src = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f))
    val r = mm512MaskCvtepu16Ph(src, 0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f), F16.fromFloat(26.0f), F16.fromFloat(18.0f), F16.fromFloat(28.0f), F16.fromFloat(20.0f), F16.fromFloat(30.0f), F16.fromFloat(22.0f), F16.fromFloat(32.0f), F16.fromFloat(24.0f), F16.fromFloat(34.0f), F16.fromFloat(26.0f), F16.fromFloat(36.0f), F16.fromFloat(28.0f), F16.fromFloat(38.0f), F16.fromFloat(30.0f), F16.fromFloat(40.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzCvtepu16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzCvtepu16Ph(0x55555555u, a)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(0.0f), F16.fromFloat(18.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(0.0f), F16.fromFloat(22.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(0.0f), F16.fromFloat(26.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(0.0f), F16.fromFloat(30.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512CvtRoundepu16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512CvtRoundepu16Ph(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskCvtRoundepu16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val src = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f), F16.fromFloat(33.0f), F16.fromFloat(34.0f), F16.fromFloat(35.0f), F16.fromFloat(36.0f), F16.fromFloat(37.0f), F16.fromFloat(38.0f), F16.fromFloat(39.0f), F16.fromFloat(40.0f), F16.fromFloat(41.0f))
    val r = mm512MaskCvtRoundepu16Ph(src, 0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f), F16.fromFloat(26.0f), F16.fromFloat(18.0f), F16.fromFloat(28.0f), F16.fromFloat(20.0f), F16.fromFloat(30.0f), F16.fromFloat(22.0f), F16.fromFloat(32.0f), F16.fromFloat(24.0f), F16.fromFloat(34.0f), F16.fromFloat(26.0f), F16.fromFloat(36.0f), F16.fromFloat(28.0f), F16.fromFloat(38.0f), F16.fromFloat(30.0f), F16.fromFloat(40.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMm512MaskzCvtRoundepu16Ph() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzCvtRoundepu16Ph(0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(0.0f), F16.fromFloat(18.0f), F16.fromFloat(0.0f), F16.fromFloat(20.0f), F16.fromFloat(0.0f), F16.fromFloat(22.0f), F16.fromFloat(0.0f), F16.fromFloat(24.0f), F16.fromFloat(0.0f), F16.fromFloat(26.0f), F16.fromFloat(0.0f), F16.fromFloat(28.0f), F16.fromFloat(0.0f), F16.fromFloat(30.0f), F16.fromFloat(0.0f), F16.fromFloat(32.0f))
    assertEqM512h(r, e)
}

public fun testMmCvtepi32Ph() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmCvtepi32Ph(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtepi32Ph() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtepi32Ph(src, 0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(2.0f), F16.fromFloat(16.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtepi32Ph() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskzCvtepi32Ph(0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256Cvtepi32Ph() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256Cvtepi32Ph(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskCvtepi32Ph() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCvtepi32Ph(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskzCvtepi32Ph() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzCvtepi32Ph(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512Cvtepi32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512Cvtepi32Ph(a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskCvtepi32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm512MaskCvtepi32Ph(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskzCvtepi32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzCvtepi32Ph(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512CvtRoundepi32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512CvtRoundepi32Ph(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskCvtRoundepi32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm512MaskCvtRoundepi32Ph(src, 0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskzCvtRoundepi32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzCvtRoundepi32Ph(0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMmCvti32Sh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvti32Sh(a, 10)
    val e = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtRoundi32Sh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtRoundi32Sh(a, 10, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtepu32Ph() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmCvtepu32Ph(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtepu32Ph() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtepu32Ph(src, 0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(2.0f), F16.fromFloat(16.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtepu32Ph() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskzCvtepu32Ph(0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256Cvtepu32Ph() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256Cvtepu32Ph(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskCvtepu32Ph() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCvtepu32Ph(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskzCvtepu32Ph() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzCvtepu32Ph(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512Cvtepu32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512Cvtepu32Ph(a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskCvtepu32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm512MaskCvtepu32Ph(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskzCvtepu32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzCvtepu32Ph(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512CvtRoundepu32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512CvtRoundepu32Ph(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskCvtRoundepu32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm512MaskCvtRoundepu32Ph(src, 0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskzCvtRoundepu32Ph() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzCvtRoundepu32Ph(0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMmCvtu32Sh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtu32Sh(a, 10u)
    val e = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtRoundu32Sh() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtRoundu32Sh(a, 10u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtepi64Ph() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmCvtepi64Ph(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtepi64Ph() {
    val a = mmSetEpi64x(1L, 2L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtepi64Ph(src, 0x1u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtepi64Ph() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmMaskzCvtepi64Ph(0x1u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMm256Cvtepi64Ph() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepi64Ph(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskCvtepi64Ph() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCvtepi64Ph(src, 0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(2.0f), F16.fromFloat(16.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskzCvtepi64Ph() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepi64Ph(0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm512Cvtepi64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Cvtepi64Ph(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskCvtepi64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm512MaskCvtepi64Ph(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskzCvtepi64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtepi64Ph(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512CvtRoundepi64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512CvtRoundepi64Ph(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskCvtRoundepi64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm512MaskCvtRoundepi64Ph(src, 0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskzCvtRoundepi64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtRoundepi64Ph(0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtepu64Ph() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmCvtepu64Ph(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtepu64Ph() {
    val a = mmSetEpi64x(1L, 2L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtepu64Ph(src, 0x1u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtepu64Ph() {
    val a = mmSetEpi64x(1L, 2L)
    val r = mmMaskzCvtepu64Ph(0x1u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMm256Cvtepu64Ph() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepu64Ph(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskCvtepu64Ph() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCvtepu64Ph(src, 0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(2.0f), F16.fromFloat(16.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskzCvtepu64Ph() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepu64Ph(0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm512Cvtepu64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Cvtepu64Ph(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskCvtepu64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm512MaskCvtepu64Ph(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskzCvtepu64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtepu64Ph(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512CvtRoundepu64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512CvtRoundepu64Ph(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskCvtRoundepu64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm512MaskCvtRoundepu64Ph(src, 0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskzCvtRoundepu64Ph() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzCvtRoundepu64Ph(0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtxpsPh() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtxpsPh(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtxpsPh() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtxpsPh(src, 0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(2.0f), F16.fromFloat(16.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtxpsPh() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvtxpsPh(0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256CvtxpsPh() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256CvtxpsPh(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskCvtxpsPh() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCvtxpsPh(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskzCvtxpsPh() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskzCvtxpsPh(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512CvtxpsPh() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512CvtxpsPh(a)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskCvtxpsPh() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm512MaskCvtxpsPh(src, 0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskzCvtxpsPh() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskzCvtxpsPh(0x5555u, a)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512CvtxRoundpsPh() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512CvtxRoundpsPh(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskCvtxRoundpsPh() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val src = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f))
    val r = mm512MaskCvtxRoundpsPh(src, 0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f), F16.fromFloat(18.0f), F16.fromFloat(10.0f), F16.fromFloat(20.0f), F16.fromFloat(12.0f), F16.fromFloat(22.0f), F16.fromFloat(14.0f), F16.fromFloat(24.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMm512MaskzCvtxRoundpsPh() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskzCvtxRoundpsPh(0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm256SetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f), F16.fromFloat(0.0f), F16.fromFloat(10.0f), F16.fromFloat(0.0f), F16.fromFloat(12.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f))
    assertEqM256h(r, e)
}

public fun testMmCvtssSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtssSh(a, b)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtssSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val src = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f))
    val r = mmMaskCvtssSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskCvtssSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzCvtssSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvtssSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzCvtssSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmCvtRoundssSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmCvtRoundssSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtRoundssSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val src = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f))
    val r = mmMaskCvtRoundssSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskCvtRoundssSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzCvtRoundssSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzCvtRoundssSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzCvtRoundssSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmCvtpdPh() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmCvtpdPh(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtpdPh() {
    val a = mmSetPd(1.0, 2.0)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mmMaskCvtpdPh(src, 0x1u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(16.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskzCvtpdPh() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskzCvtpdPh(0x1u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f))
    assertEqM128h(r, e)
}

public fun testMm256CvtpdPh() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CvtpdPh(a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskCvtpdPh() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm256MaskCvtpdPh(src, 0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(14.0f), F16.fromFloat(2.0f), F16.fromFloat(16.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm256MaskzCvtpdPh() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzCvtpdPh(0x5u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f))
    assertEqM128h(r, e)
}

public fun testMm512CvtpdPh() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvtpdPh(a)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskCvtpdPh() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm512MaskCvtpdPh(src, 0x55u, a)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskzCvtpdPh() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvtpdPh(0x55u, a)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512CvtRoundpdPh() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512CvtRoundpdPh(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskCvtRoundpdPh() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val src = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val r = mm512MaskCvtRoundpdPh(src, 0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(10.0f), F16.fromFloat(2.0f), F16.fromFloat(12.0f), F16.fromFloat(4.0f), F16.fromFloat(14.0f), F16.fromFloat(6.0f), F16.fromFloat(16.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMm512MaskzCvtRoundpdPh() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzCvtRoundpdPh(0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(2.0f), F16.fromFloat(0.0f), F16.fromFloat(4.0f), F16.fromFloat(0.0f), F16.fromFloat(6.0f), F16.fromFloat(0.0f), F16.fromFloat(8.0f))
    assertEqM128h(r, e)
}

public fun testMmCvtsdSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPd(1.0, 2.0)
    val r = mmCvtsdSh(a, b)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtsdSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPd(1.0, 2.0)
    val src = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f))
    val r = mmMaskCvtsdSh(src, 0u, a, b)
    val e = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskCvtsdSh(src, 1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzCvtsdSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPd(1.0, 2.0)
    val r = mmMaskzCvtsdSh(0u, a, b)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzCvtsdSh(1u, a, b)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmCvtRoundsdSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPd(1.0, 2.0)
    val r = mmCvtRoundsdSh(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
}

public fun testMmMaskCvtRoundsdSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPd(1.0, 2.0)
    val src = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f))
    val r = mmMaskCvtRoundsdSh(src, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(20.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskCvtRoundsdSh(src, 1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmMaskzCvtRoundsdSh() {
    val a = mmSetrPh(F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    val b = mmSetrPd(1.0, 2.0)
    val r = mmMaskzCvtRoundsdSh(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetrPh(F16.fromFloat(0.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r, e)
    val r1 = mmMaskzCvtRoundsdSh(1u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f))
    assertEqM128h(r1, e1)
}

public fun testMmCvtphEpi16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttphEpi16(a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtphEpi16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mmSetEpi16(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mmMaskCvttphEpi16(src, 0x55u, a)
    val e = mmSetEpi16(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtphEpi16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmMaskzCvttphEpi16(0x55u, a)
    val e = mmSetEpi16(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM128i(r, e)
}

public fun testMm256CvtphEpi16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256CvttphEpi16(a)
    val e = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtphEpi16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm256SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm256MaskCvttphEpi16(src, 0x5555u, a)
    val e = mm256SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtphEpi16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256MaskzCvttphEpi16(0x5555u, a)
    val e = mm256SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM256i(r, e)
}

public fun testMm512CvtphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttphEpi16(a)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttphEpi16(src, 0x55555555u, a)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttphEpi16(0x55555555u, a)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttRoundphEpi16(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttRoundphEpi16(src, 0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttRoundphEpi16(0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMmCvtphEpu16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttphEpu16(a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtphEpu16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mmSetEpi16(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mmMaskCvttphEpu16(src, 0x55u, a)
    val e = mmSetEpi16(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtphEpu16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmMaskzCvttphEpu16(0x55u, a)
    val e = mmSetEpi16(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM128i(r, e)
}

public fun testMm256CvtphEpu16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256CvttphEpu16(a)
    val e = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtphEpu16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm256SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm256MaskCvttphEpu16(src, 0x5555u, a)
    val e = mm256SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtphEpu16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256MaskzCvttphEpu16(0x5555u, a)
    val e = mm256SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM256i(r, e)
}

public fun testMm512CvtphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttphEpu16(a)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttphEpu16(src, 0x55555555u, a)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttphEpu16(0x55555555u, a)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvtRoundphEpu16(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvtRoundphEpu16(src, 0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvtRoundphEpu16(0x55555555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMmCvttphEpi16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttphEpi16(a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttphEpi16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mmSetEpi16(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mmMaskCvttphEpi16(src, 0x55u, a)
    val e = mmSetEpi16(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttphEpi16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmMaskzCvttphEpi16(0x55u, a)
    val e = mmSetEpi16(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM128i(r, e)
}

public fun testMm256CvttphEpi16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256CvttphEpi16(a)
    val e = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttphEpi16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm256SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm256MaskCvttphEpi16(src, 0x5555u, a)
    val e = mm256SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttphEpi16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256MaskzCvttphEpi16(0x5555u, a)
    val e = mm256SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM256i(r, e)
}

public fun testMm512CvttphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttphEpi16(a)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttphEpi16(src, 0x55555555u, a)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttphEpi16(0x55555555u, a)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttRoundphEpi16(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttRoundphEpi16(src, 0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundphEpi16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttRoundphEpi16(0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMmCvttphEpu16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttphEpu16(a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttphEpu16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mmSetEpi16(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mmMaskCvttphEpu16(src, 0x55u, a)
    val e = mmSetEpi16(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttphEpu16() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmMaskzCvttphEpu16(0x55u, a)
    val e = mmSetEpi16(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM128i(r, e)
}

public fun testMm256CvttphEpu16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256CvttphEpu16(a)
    val e = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttphEpu16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm256SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm256MaskCvttphEpu16(src, 0x5555u, a)
    val e = mm256SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttphEpu16() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256MaskzCvttphEpu16(0x5555u, a)
    val e = mm256SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM256i(r, e)
}

public fun testMm512CvttphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttphEpu16(a)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttphEpu16(src, 0x55555555u, a)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttphEpu16(0x55555555u, a)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvttRoundphEpu16(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val src = mm512SetEpi16(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41)
    val r = mm512MaskCvttRoundphEpu16(src, 0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16, 26, 18, 28, 20, 30, 22, 32, 24, 34, 26, 36, 28, 38, 30, 40, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundphEpu16() {
    val a = mm512SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512MaskzCvttRoundphEpu16(0x55555555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi16(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16, 0, 18, 0, 20, 0, 22, 0, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    assertEqM512i(r, e)
}

public fun testMmCvtphEpi32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmCvtphEpi32(a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtphEpi32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val src = mmSetEpi32(10, 11, 12, 13)
    val r = mmMaskCvtphEpi32(src, 0x5u, a)
    val e = mmSetEpi32(10, 2, 12, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtphEpi32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmMaskzCvtphEpi32(0x5u, a)
    val e = mmSetEpi32(0, 2, 0, 4)
    assertEqM128i(r, e)
}

public fun testMm256CvtphEpi32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256CvtphEpi32(a)
    val e = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtphEpi32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mm256SetEpi32(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mm256MaskCvtphEpi32(src, 0x55u, a)
    val e = mm256SetEpi32(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtphEpi32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256MaskzCvtphEpi32(0x55u, a)
    val e = mm256SetEpi32(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM256i(r, e)
}

public fun testMm512CvtphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvtphEpi32(a)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvtphEpi32(src, 0x5555u, a)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvtphEpi32(0x5555u, a)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvtRoundphEpi32(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvtRoundphEpi32(src, 0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvtRoundphEpi32(0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMmCvtshI32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtshI32(a)
    check(r == 1)
}

public fun testMmCvtRoundshI32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtRoundshI32(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    check(r == 1)
}

public fun testMmCvtphEpu32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmCvtphEpu32(a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtphEpu32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val src = mmSetEpi32(10, 11, 12, 13)
    val r = mmMaskCvtphEpu32(src, 0x5u, a)
    val e = mmSetEpi32(10, 2, 12, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtphEpu32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmMaskzCvtphEpu32(0x5u, a)
    val e = mmSetEpi32(0, 2, 0, 4)
    assertEqM128i(r, e)
}

public fun testMm256CvtphEpu32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256CvtphEpu32(a)
    val e = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtphEpu32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mm256SetEpi32(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mm256MaskCvtphEpu32(src, 0x55u, a)
    val e = mm256SetEpi32(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtphEpu32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256MaskzCvtphEpu32(0x55u, a)
    val e = mm256SetEpi32(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM256i(r, e)
}

public fun testMm512CvtphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvtphEpu32(a)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvtphEpu32(src, 0x5555u, a)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvtphEpu32(0x5555u, a)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvtRoundphEpu32(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvtRoundphEpu32(src, 0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvtRoundphEpu32(0x5555u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMmCvtshU32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtshU32(a)
    check(r == 1u)
}

public fun testMmCvtRoundshU32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtRoundshU32(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    check(r == 1u)
}

public fun testMmCvttphEpi32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmCvttphEpi32(a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttphEpi32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val src = mmSetEpi32(10, 11, 12, 13)
    val r = mmMaskCvttphEpi32(src, 0x5u, a)
    val e = mmSetEpi32(10, 2, 12, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttphEpi32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmMaskzCvttphEpi32(0x5u, a)
    val e = mmSetEpi32(0, 2, 0, 4)
    assertEqM128i(r, e)
}

public fun testMm256CvttphEpi32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256CvttphEpi32(a)
    val e = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttphEpi32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mm256SetEpi32(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mm256MaskCvttphEpi32(src, 0x55u, a)
    val e = mm256SetEpi32(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttphEpi32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256MaskzCvttphEpi32(0x55u, a)
    val e = mm256SetEpi32(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM256i(r, e)
}

public fun testMm512CvttphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvttphEpi32(a)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvttphEpi32(src, 0x5555u, a)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvttphEpi32(0x5555u, a)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvttRoundphEpi32(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvttRoundphEpi32(src, 0x5555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundphEpi32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvttRoundphEpi32(0x5555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMmCvttshI32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttshI32(a)
    check(r == 1)
}

public fun testMmCvttRoundshI32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttRoundshI32(a, MM_FROUND_NO_EXC)
    check(r == 1)
}

public fun testMmCvttphEpu32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmCvttphEpu32(a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttphEpu32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val src = mmSetEpi32(10, 11, 12, 13)
    val r = mmMaskCvttphEpu32(src, 0x5u, a)
    val e = mmSetEpi32(10, 2, 12, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttphEpu32() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmMaskzCvttphEpu32(0x5u, a)
    val e = mmSetEpi32(0, 2, 0, 4)
    assertEqM128i(r, e)
}

public fun testMm256CvttphEpu32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256CvttphEpu32(a)
    val e = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttphEpu32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val src = mm256SetEpi32(10, 11, 12, 13, 14, 15, 16, 17)
    val r = mm256MaskCvttphEpu32(src, 0x55u, a)
    val e = mm256SetEpi32(10, 2, 12, 4, 14, 6, 16, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttphEpu32() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256MaskzCvttphEpu32(0x55u, a)
    val e = mm256SetEpi32(0, 2, 0, 4, 0, 6, 0, 8)
    assertEqM256i(r, e)
}

public fun testMm512CvttphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvttphEpu32(a)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvttphEpu32(src, 0x5555u, a)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvttphEpu32(0x5555u, a)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvttRoundphEpu32(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val src = mm512SetEpi32(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val r = mm512MaskCvttRoundphEpu32(src, 0x5555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(10, 2, 12, 4, 14, 6, 16, 8, 18, 10, 20, 12, 22, 14, 24, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundphEpu32() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvttRoundphEpu32(0x5555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi32(0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 0, 12, 0, 14, 0, 16)
    assertEqM512i(r, e)
}

public fun testMmCvttshU32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttshU32(a)
    check(r == 1u)
}

public fun testMmCvttRoundshU32() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvttRoundshU32(a, MM_FROUND_NO_EXC)
    check(r == 1u)
}

public fun testMmCvtphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmCvtphEpi64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtphEpi64() {
    val src = mmSetEpi64x(3L, 4L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskCvtphEpi64(src, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskzCvtphEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvtphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256CvtphEpi64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtphEpi64() {
    val src = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskCvtphEpi64(src, 0x5u, a)
    val e = mm256SetEpi64x(5L, 2L, 7L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskzCvtphEpi64(0x5u, a)
    val e = mm256SetEpi64x(0L, 2L, 0L, 4L)
    assertEqM256i(r, e)
}

public fun testMm512CvtphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvtphEpi64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtphEpi64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvtphEpi64(src, 0x55u, a)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvtphEpi64(0x55u, a)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvtRoundphEpi64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundphEpi64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvtRoundphEpi64(src, 0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvtRoundphEpi64(0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvtphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmCvtphEpu64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtphEpu64() {
    val src = mmSetEpi64x(3L, 4L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskCvtphEpu64(src, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvtphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskzCvtphEpu64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvtphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256CvtphEpu64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtphEpu64() {
    val src = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskCvtphEpu64(src, 0x5u, a)
    val e = mm256SetEpi64x(5L, 2L, 7L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvtphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskzCvtphEpu64(0x5u, a)
    val e = mm256SetEpi64x(0L, 2L, 0L, 4L)
    assertEqM256i(r, e)
}

public fun testMm512CvtphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvtphEpu64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtphEpu64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvtphEpu64(src, 0x55u, a)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvtphEpu64(0x55u, a)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvtRoundphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvtRoundphEpu64(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtRoundphEpu64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvtRoundphEpu64(src, 0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvtRoundphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvtRoundphEpu64(0x55u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvttphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmCvttphEpi64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttphEpi64() {
    val src = mmSetEpi64x(3L, 4L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskCvttphEpi64(src, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskzCvttphEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvttphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256CvttphEpi64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttphEpi64() {
    val src = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskCvttphEpi64(src, 0x5u, a)
    val e = mm256SetEpi64x(5L, 2L, 7L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttphEpi64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskzCvttphEpi64(0x5u, a)
    val e = mm256SetEpi64x(0L, 2L, 0L, 4L)
    assertEqM256i(r, e)
}

public fun testMm512CvttphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvttphEpi64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttphEpi64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvttphEpi64(src, 0x55u, a)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvttphEpi64(0x55u, a)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvttRoundphEpi64(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundphEpi64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvttRoundphEpi64(src, 0x55u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundphEpi64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvttRoundphEpi64(0x55u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvttphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmCvttphEpu64(a)
    val e = mmSetEpi64x(1L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttphEpu64() {
    val src = mmSetEpi64x(3L, 4L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskCvttphEpu64(src, 0x1u, a)
    val e = mmSetEpi64x(3L, 2L)
    assertEqM128i(r, e)
}

public fun testMmMaskzCvttphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskzCvttphEpu64(0x1u, a)
    val e = mmSetEpi64x(0L, 2L)
    assertEqM128i(r, e)
}

public fun testMm256CvttphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256CvttphEpu64(a)
    val e = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttphEpu64() {
    val src = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskCvttphEpu64(src, 0x5u, a)
    val e = mm256SetEpi64x(5L, 2L, 7L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCvttphEpu64() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskzCvttphEpu64(0x5u, a)
    val e = mm256SetEpi64x(0L, 2L, 0L, 4L)
    assertEqM256i(r, e)
}

public fun testMm512CvttphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvttphEpu64(a)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttphEpu64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvttphEpu64(src, 0x55u, a)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvttphEpu64(0x55u, a)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512CvttRoundphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvttRoundphEpu64(a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundphEpu64() {
    val src = mm512SetEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvttRoundphEpu64(src, 0x55u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(9L, 2L, 11L, 4L, 13L, 6L, 15L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCvttRoundphEpu64() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvttRoundphEpu64(0x55u, a, MM_FROUND_NO_EXC)
    val e = mm512SetEpi64(0L, 2L, 0L, 4L, 0L, 6L, 0L, 8L)
    assertEqM512i(r, e)
}

public fun testMmCvtxphPs() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmCvtxphPs(a)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtxphPs() {
    val src = mmSetPs(10.0f, 11.0f, 12.0f, 13.0f)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmMaskCvtxphPs(src, 0x5u, a)
    val e = mmSetPs(10.0f, 2.0f, 12.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzCvtxphPs() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mmMaskzCvtxphPs(0x5u, a)
    val e = mmSetPs(0.0f, 2.0f, 0.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256CvtxphPs() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256CvtxphPs(a)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskCvtxphPs() {
    val src = mm256SetPs(10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256MaskCvtxphPs(src, 0x55u, a)
    val e = mm256SetPs(10.0f, 2.0f, 12.0f, 4.0f, 14.0f, 6.0f, 16.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzCvtxphPs() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm256MaskzCvtxphPs(0x55u, a)
    val e = mm256SetPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm512CvtxphPs() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvtxphPs(a)
    val e = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtxphPs() {
    val src = mm512SetPs(10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f)
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskCvtxphPs(src, 0x5555u, a)
    val e = mm512SetPs(10.0f, 2.0f, 12.0f, 4.0f, 14.0f, 6.0f, 16.0f, 8.0f, 18.0f, 10.0f, 20.0f, 12.0f, 22.0f, 14.0f, 24.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzCvtxphPs() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvtxphPs(0x5555u, a)
    val e = mm512SetPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f, 0.0f, 10.0f, 0.0f, 12.0f, 0.0f, 14.0f, 0.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512CvtxRoundphPs() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512CvtxRoundphPs(a, MM_FROUND_NO_EXC)
    val e = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtxRoundphPs() {
    val src = mm512SetPs(10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f)
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskCvtxRoundphPs(src, 0x5555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetPs(10.0f, 2.0f, 12.0f, 4.0f, 14.0f, 6.0f, 16.0f, 8.0f, 18.0f, 10.0f, 20.0f, 12.0f, 22.0f, 14.0f, 24.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzCvtxRoundphPs() {
    val a = mm256SetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm512MaskzCvtxRoundphPs(0x5555u, a, MM_FROUND_NO_EXC)
    val e = mm512SetPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f, 0.0f, 10.0f, 0.0f, 12.0f, 0.0f, 14.0f, 0.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMmCvtshSs() {
    val a = mmSetrPs(2.0f, 20.0f, 21.0f, 22.0f)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmCvtshSs(a, b)
    val e = mmSetrPs(1.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtshSs() {
    val src = mmSetrPs(3.0f, 11.0f, 12.0f, 13.0f)
    val a = mmSetrPs(2.0f, 20.0f, 21.0f, 22.0f)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskCvtshSs(src, 0u, a, b)
    val e = mmSetrPs(3.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r, e)
    val r1 = mmMaskCvtshSs(src, 1u, a, b)
    val e1 = mmSetrPs(1.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzCvtshSs() {
    val a = mmSetrPs(2.0f, 20.0f, 21.0f, 22.0f)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskzCvtshSs(0u, a, b)
    val e = mmSetrPs(0.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzCvtshSs(1u, a, b)
    val e1 = mmSetrPs(1.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r1, e1)
}

public fun testMmCvtRoundshSs() {
    val a = mmSetrPs(2.0f, 20.0f, 21.0f, 22.0f)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmCvtRoundshSs(a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPs(1.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtRoundshSs() {
    val src = mmSetrPs(3.0f, 11.0f, 12.0f, 13.0f)
    val a = mmSetrPs(2.0f, 20.0f, 21.0f, 22.0f)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskCvtRoundshSs(src, 0u, a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPs(3.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r, e)
    val r1 = mmMaskCvtRoundshSs(src, 1u, a, b, MM_FROUND_NO_EXC)
    val e1 = mmSetrPs(1.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzCvtRoundshSs() {
    val a = mmSetrPs(2.0f, 20.0f, 21.0f, 22.0f)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskzCvtRoundshSs(0u, a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPs(0.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzCvtRoundshSs(1u, a, b, MM_FROUND_NO_EXC)
    val e1 = mmSetrPs(1.0f, 20.0f, 21.0f, 22.0f)
    assertEqM128(r1, e1)
}

public fun testMmCvtphPd() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmCvtphPd(a)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtphPd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskCvtphPd(src, 0x1u, a)
    val e = mmSetPd(10.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzCvtphPd() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f))
    val r = mmMaskzCvtphPd(0x1u, a)
    val e = mmSetPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256CvtphPd() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256CvtphPd(a)
    val e = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskCvtphPd() {
    val src = mm256SetPd(10.0, 11.0, 12.0, 13.0)
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskCvtphPd(src, 0x5u, a)
    val e = mm256SetPd(10.0, 2.0, 12.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzCvtphPd() {
    val a = mmSetPh(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f))
    val r = mm256MaskzCvtphPd(0x5u, a)
    val e = mm256SetPd(0.0, 2.0, 0.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm512CvtphPd() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvtphPd(a)
    val e = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtphPd() {
    val src = mm512SetPd(10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvtphPd(src, 0x55u, a)
    val e = mm512SetPd(10.0, 2.0, 12.0, 4.0, 14.0, 6.0, 16.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzCvtphPd() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvtphPd(0x55u, a)
    val e = mm512SetPd(0.0, 2.0, 0.0, 4.0, 0.0, 6.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512CvtRoundphPd() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512CvtRoundphPd(a, MM_FROUND_NO_EXC)
    val e = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtRoundphPd() {
    val src = mm512SetPd(10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0)
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskCvtRoundphPd(src, 0x55u, a, MM_FROUND_NO_EXC)
    val e = mm512SetPd(10.0, 2.0, 12.0, 4.0, 14.0, 6.0, 16.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzCvtRoundphPd() {
    val a = mmSetPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mm512MaskzCvtRoundphPd(0x55u, a, MM_FROUND_NO_EXC)
    val e = mm512SetPd(0.0, 2.0, 0.0, 4.0, 0.0, 6.0, 0.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMmCvtshSd() {
    val a = mmSetrPd(2.0, 20.0)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmCvtshSd(a, b)
    val e = mmSetrPd(1.0, 20.0)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtshSd() {
    val src = mmSetrPd(3.0, 11.0)
    val a = mmSetrPd(2.0, 20.0)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskCvtshSd(src, 0u, a, b)
    val e = mmSetrPd(3.0, 20.0)
    assertEqM128d(r, e)
    val r1 = mmMaskCvtshSd(src, 1u, a, b)
    val e1 = mmSetrPd(1.0, 20.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzCvtshSd() {
    val a = mmSetrPd(2.0, 20.0)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskzCvtshSd(0u, a, b)
    val e = mmSetrPd(0.0, 20.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzCvtshSd(1u, a, b)
    val e1 = mmSetrPd(1.0, 20.0)
    assertEqM128d(r1, e1)
}

public fun testMmCvtRoundshSd() {
    val a = mmSetrPd(2.0, 20.0)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmCvtRoundshSd(a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPd(1.0, 20.0)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtRoundshSd() {
    val src = mmSetrPd(3.0, 11.0)
    val a = mmSetrPd(2.0, 20.0)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskCvtRoundshSd(src, 0u, a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPd(3.0, 20.0)
    assertEqM128d(r, e)
    val r1 = mmMaskCvtRoundshSd(src, 1u, a, b, MM_FROUND_NO_EXC)
    val e1 = mmSetrPd(1.0, 20.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzCvtRoundshSd() {
    val a = mmSetrPd(2.0, 20.0)
    val b = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mmMaskzCvtRoundshSd(0u, a, b, MM_FROUND_NO_EXC)
    val e = mmSetrPd(0.0, 20.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzCvtRoundshSd(1u, a, b, MM_FROUND_NO_EXC)
    val e1 = mmSetrPd(1.0, 20.0)
    assertEqM128d(r1, e1)
}

public fun testMmCvtshH() {
    val a = mmSetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(42.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f))
    val r = mmCvtshH(a)
    check(r == F16.fromFloat(1.0f))
}

public fun testMm256CvtshH() {
    val a = mm256SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(42.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f))
    val r = mm256CvtshH(a)
    check(r == F16.fromFloat(1.0f))
}

public fun testMm512CvtshH() {
    val a = mm512SetrPh(F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(42.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f), F16.fromFloat(8.0f), F16.fromFloat(9.0f), F16.fromFloat(10.0f), F16.fromFloat(11.0f), F16.fromFloat(12.0f), F16.fromFloat(13.0f), F16.fromFloat(14.0f), F16.fromFloat(15.0f), F16.fromFloat(16.0f), F16.fromFloat(17.0f), F16.fromFloat(18.0f), F16.fromFloat(19.0f), F16.fromFloat(20.0f), F16.fromFloat(21.0f), F16.fromFloat(22.0f), F16.fromFloat(23.0f), F16.fromFloat(24.0f), F16.fromFloat(25.0f), F16.fromFloat(26.0f), F16.fromFloat(27.0f), F16.fromFloat(28.0f), F16.fromFloat(29.0f), F16.fromFloat(30.0f), F16.fromFloat(31.0f), F16.fromFloat(32.0f))
    val r = mm512CvtshH(a)
    check(r == F16.fromFloat(1.0f))
}

public fun testMmCvtsi128Si16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmCvtsi128Si16(a)
    check(r == 1.toShort())
}

public fun testMmCvtsi16Si128() {
    val a = 1
    val r = mmCvtsi16Si128(a)
    val e = mmSetrEpi16(1, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}
