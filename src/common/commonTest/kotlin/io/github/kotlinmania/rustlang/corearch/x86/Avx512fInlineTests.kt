// port-lint: tests x86/avx512f.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import kotlin.experimental.inv
import kotlin.experimental.or

public fun testMm512AbsEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val r = mm512AbsEpi32(a)
    val e = mm512SetrEpi32(0, 1, 1, Int.MAX_VALUE, (Int.MAX_VALUE + 1), 100, 100, 32, 0, 1, 1, Int.MAX_VALUE, (Int.MAX_VALUE + 1), 100, 100, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskAbsEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val r = mm512MaskAbsEpi32(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskAbsEpi32(a, 0xFFu, a)
    val e = mm512SetrEpi32(0, 1, 1, Int.MAX_VALUE, (Int.MAX_VALUE + 1), 100, 100, 32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAbsEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val r = mm512MaskzAbsEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAbsEpi32(0xFFu, a)
    val e = mm512SetrEpi32(0, 1, 1, Int.MAX_VALUE, (Int.MAX_VALUE + 1), 100, 100, 32, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAbsEpi32() {
    val a = mm256SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val r = mm256MaskAbsEpi32(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskAbsEpi32(a, 0xFu, a)
    val e = mm256SetrEpi32(0, 1, 1, Int.MAX_VALUE, (Int.MAX_VALUE + 1), 100, -100, -32)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAbsEpi32() {
    val a = mm256SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val r = mm256MaskzAbsEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAbsEpi32(0xFu, a)
    val e = mm256SetrEpi32(0, 1, 1, Int.MAX_VALUE, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskAbsEpi32() {
    val a = mmSetrEpi32(Int.MIN_VALUE, 100, -100, -32)
    val r = mmMaskAbsEpi32(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskAbsEpi32(a, 0xFu, a)
    val e = mmSetrEpi32((Int.MAX_VALUE + 1), 100, 100, 32)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAbsEpi32() {
    val a = mmSetrEpi32(Int.MIN_VALUE, 100, -100, -32)
    val r = mmMaskzAbsEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAbsEpi32(0xFu, a)
    val e = mmSetrEpi32((Int.MAX_VALUE + 1), 100, 100, 32)
    assertEqM128i(r1, e)
}

public fun testMm512AbsPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val r = mm512AbsPs(a)
    val e = mm512SetrPs(0.0f, 1.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE, 100.0f, 100.0f, 32.0f, 0.0f, 1.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE, 100.0f, 100.0f, 32.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskAbsPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val r = mm512MaskAbsPs(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskAbsPs(a, 0xFFu, a)
    val e = mm512SetrPs(0.0f, 1.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE, 100.0f, 100.0f, 32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskMovEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(2)
    val r = mm512MaskMovEpi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskMovEpi32(src, 0xFFFFu, a)
    assertEqM512i(r1, a)
}

public fun testMm512MaskzMovEpi32() {
    val a = mm512Set1Epi32(2)
    val r = mm512MaskzMovEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMovEpi32(0xFFFFu, a)
    assertEqM512i(r1, a)
}

public fun testMm256MaskMovEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(2)
    val r = mm256MaskMovEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskMovEpi32(src, 0xFFu, a)
    assertEqM256i(r1, a)
}

public fun testMm256MaskzMovEpi32() {
    val a = mm256Set1Epi32(2)
    val r = mm256MaskzMovEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMovEpi32(0xFFu, a)
    assertEqM256i(r1, a)
}

public fun testMmMaskMovEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(2)
    val r = mmMaskMovEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskMovEpi32(src, 0xFu, a)
    assertEqM128i(r1, a)
}

public fun testMmMaskzMovEpi32() {
    val a = mmSet1Epi32(2)
    val r = mmMaskzMovEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMovEpi32(0xFu, a)
    assertEqM128i(r1, a)
}

public fun testMm512MaskMovPs() {
    val src = mm512Set1Ps(1.0f)
    val a = mm512Set1Ps(2.0f)
    val r = mm512MaskMovPs(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskMovPs(src, 0xFFFFu, a)
    assertEqM512(r1, a)
}

public fun testMm512MaskzMovPs() {
    val a = mm512Set1Ps(2.0f)
    val r = mm512MaskzMovPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMovPs(0xFFFFu, a)
    assertEqM512(r1, a)
}

public fun testMm256MaskMovPs() {
    val src = mm256Set1Ps(1.0f)
    val a = mm256Set1Ps(2.0f)
    val r = mm256MaskMovPs(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskMovPs(src, 0xFFu, a)
    assertEqM256(r1, a)
}

public fun testMm256MaskzMovPs() {
    val a = mm256Set1Ps(2.0f)
    val r = mm256MaskzMovPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzMovPs(0xFFu, a)
    assertEqM256(r1, a)
}

public fun testMmMaskMovPs() {
    val src = mmSet1Ps(1.0f)
    val a = mmSet1Ps(2.0f)
    val r = mmMaskMovPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskMovPs(src, 0xFu, a)
    assertEqM128(r1, a)
}

public fun testMmMaskzMovPs() {
    val a = mmSet1Ps(2.0f)
    val r = mmMaskzMovPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzMovPs(0xFu, a)
    assertEqM128(r1, a)
}

public fun testMm512AddEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(1)
    val r = mm512AddEpi32(a, b)
    val e = mm512SetrEpi32(1, 2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1, 101, -99, -31, 1, 2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1, 101, -99, -31)
    assertEqM512i(r, e)
}

public fun testMm512MaskAddEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskAddEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddEpi32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(1, 2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1, 101, -99, -31, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskzAddEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddEpi32(0xFFu, a, b)
    val e = mm512SetrEpi32(1, 2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1, 101, -99, -31, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddEpi32() {
    val a = mm256SetEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskAddEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(1, 2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1, 101, -99, -31)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddEpi32() {
    val a = mm256SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskzAddEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddEpi32(0xFFu, a, b)
    val e = mm256SetrEpi32(1, 2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1, 101, -99, -31)
    assertEqM256i(r1, e)
}

public fun testMmMaskAddEpi32() {
    val a = mmSetEpi32(1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
    val b = mmSet1Epi32(1)
    val r = mmMaskAddEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddEpi32() {
    val a = mmSetrEpi32(1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
    val b = mmSet1Epi32(1)
    val r = mmMaskzAddEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddEpi32(0xFu, a, b)
    val e = mmSetrEpi32(2, 0, Int.MIN_VALUE, Int.MIN_VALUE + 1)
    assertEqM128i(r1, e)
}

public fun testMm512AddPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512AddPs(a, b)
    val e = mm512SetrPs(1.0f, 2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f, 101.0f, -99.0f, -31.0f, 1.0f, 2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f, 101.0f, -99.0f, -31.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskAddPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512MaskAddPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskAddPs(a, 0xFFu, a, b)
    val e = mm512SetrPs(1.0f, 2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f, 101.0f, -99.0f, -31.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzAddPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512MaskzAddPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzAddPs(0xFFu, a, b)
    val e = mm512SetrPs(1.0f, 2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f, 101.0f, -99.0f, -31.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskAddPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm256Set1Ps(1.0f)
    val r = mm256MaskAddPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskAddPs(a, 0xFFu, a, b)
    val e = mm256SetPs(1.0f, 2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f, 101.0f, -99.0f, -31.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzAddPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm256Set1Ps(1.0f)
    val r = mm256MaskzAddPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzAddPs(0xFFu, a, b)
    val e = mm256SetPs(1.0f, 2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f, 101.0f, -99.0f, -31.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskAddPs() {
    val a = mmSetPs(1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE))
    val b = mmSet1Ps(1.0f)
    val r = mmMaskAddPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskAddPs(a, 0xFu, a, b)
    val e = mmSetPs(2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzAddPs() {
    val a = mmSetPs(1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE))
    val b = mmSet1Ps(1.0f)
    val r = mmMaskzAddPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzAddPs(0xFu, a, b)
    val e = mmSetPs(2.0f, 0.0f, Float.MAX_VALUE, (-Float.MAX_VALUE) + 1.0f)
    assertEqM128(r1, e)
}

public fun testMm512SubEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(1)
    val r = mm512SubEpi32(a, b)
    val e = mm512SetrEpi32(-1, 0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE, 99, -101, -33, -1, 0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE, 99, -101, -33)
    assertEqM512i(r, e)
}

public fun testMm512MaskSubEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskSubEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubEpi32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(-1, 0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE, 99, -101, -33, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskzSubEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubEpi32(0xFFu, a, b)
    val e = mm512SetrEpi32(-1, 0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE, 99, -101, -33, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubEpi32() {
    val a = mm256SetEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskSubEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(-1, 0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE, 99, -101, -33)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubEpi32() {
    val a = mm256SetEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskzSubEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(-1, 0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE, 99, -101, -33)
    assertEqM256i(r1, e)
}

public fun testMmMaskSubEpi32() {
    val a = mmSetEpi32(1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
    val b = mmSet1Epi32(1)
    val r = mmMaskSubEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubEpi32() {
    val a = mmSetEpi32(1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
    val b = mmSet1Epi32(1)
    val r = mmMaskzSubEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubEpi32(0xFu, a, b)
    val e = mmSetEpi32(0, -2, Int.MAX_VALUE - 1, Int.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512SubPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512SubPs(a, b)
    val e = mm512SetrPs(-1.0f, 0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE), 99.0f, -101.0f, -33.0f, -1.0f, 0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE), 99.0f, -101.0f, -33.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskSubPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512MaskSubPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskSubPs(a, 0xFFu, a, b)
    val e = mm512SetrPs(-1.0f, 0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE), 99.0f, -101.0f, -33.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzSubPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512MaskzSubPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzSubPs(0xFFu, a, b)
    val e = mm512SetrPs(-1.0f, 0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE), 99.0f, -101.0f, -33.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskSubPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm256Set1Ps(1.0f)
    val r = mm256MaskSubPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskSubPs(a, 0xFFu, a, b)
    val e = mm256SetPs(-1.0f, 0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE), 99.0f, -101.0f, -33.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzSubPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm256Set1Ps(1.0f)
    val r = mm256MaskzSubPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzSubPs(0xFFu, a, b)
    val e = mm256SetPs(-1.0f, 0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE), 99.0f, -101.0f, -33.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskSubPs() {
    val a = mmSetPs(1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE))
    val b = mmSet1Ps(1.0f)
    val r = mmMaskSubPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskSubPs(a, 0xFu, a, b)
    val e = mmSetPs(0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE))
    assertEqM128(r1, e)
}

public fun testMmMaskzSubPs() {
    val a = mmSetPs(1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE))
    val b = mmSet1Ps(1.0f)
    val r = mmMaskzSubPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzSubPs(0xFu, a, b)
    val e = mmSetPs(0.0f, -2.0f, Float.MAX_VALUE - 1.0f, (-Float.MAX_VALUE))
    assertEqM128(r1, e)
}

public fun testMm512MulloEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(2)
    val r = mm512MulloEpi32(a, b)
    val e = mm512SetrEpi32(0, 2, -2, -2, 0, 200, -200, -64, 0, 2, -2, -2, 0, 200, -200, -64)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulloEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(2)
    val r = mm512MaskMulloEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulloEpi32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(0, 2, -2, -2, 0, 200, -200, -64, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulloEpi32() {
    val a = mm512SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32, 0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm512Set1Epi32(2)
    val r = mm512MaskzMulloEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulloEpi32(0xFFu, a, b)
    val e = mm512SetrEpi32(0, 2, -2, -2, 0, 200, -200, -64, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulloEpi32() {
    val a = mm256SetEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm256Set1Epi32(2)
    val r = mm256MaskMulloEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulloEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(0, 2, -2, -2, 0, 200, -200, -64)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulloEpi32() {
    val a = mm256SetEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val b = mm256Set1Epi32(2)
    val r = mm256MaskzMulloEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulloEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(0, 2, -2, -2, 0, 200, -200, -64)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulloEpi32() {
    val a = mmSetEpi32(1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
    val b = mmSet1Epi32(2)
    val r = mmMaskMulloEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulloEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(2, -2, -2, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulloEpi32() {
    val a = mmSetEpi32(1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
    val b = mmSet1Epi32(2)
    val r = mmMaskzMulloEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulloEpi32(0xFu, a, b)
    val e = mmSetEpi32(2, -2, -2, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MulPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(2.0f)
    val r = mm512MulPs(a, b)
    val e = mm512SetrPs(0.0f, 2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 200.0f, -200.0f, -64.0f, 0.0f, 2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 200.0f, -200.0f, -64.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMulPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(2.0f)
    val r = mm512MaskMulPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskMulPs(a, 0xFFu, a, b)
    val e = mm512SetrPs(0.0f, 2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 200.0f, -200.0f, -64.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMulPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm512Set1Ps(2.0f)
    val r = mm512MaskzMulPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMulPs(0xFFu, a, b)
    val e = mm512SetrPs(0.0f, 2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 200.0f, -200.0f, -64.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskMulPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm256Set1Ps(2.0f)
    val r = mm256MaskMulPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskMulPs(a, 0xFFu, a, b)
    val e = mm256SetPs(0.0f, 2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 200.0f, -200.0f, -64.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzMulPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, -32.0f)
    val b = mm256Set1Ps(2.0f)
    val r = mm256MaskzMulPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzMulPs(0xFFu, a, b)
    val e = mm256SetPs(0.0f, 2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 200.0f, -200.0f, -64.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskMulPs() {
    val a = mmSetPs(1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE))
    val b = mmSet1Ps(2.0f)
    val r = mmMaskMulPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskMulPs(a, 0xFu, a, b)
    val e = mmSetPs(2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
    assertEqM128(r1, e)
}

public fun testMmMaskzMulPs() {
    val a = mmSetPs(1.0f, -1.0f, Float.MAX_VALUE, (-Float.MAX_VALUE))
    val b = mmSet1Ps(2.0f)
    val r = mmMaskzMulPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzMulPs(0xFu, a, b)
    val e = mmSetPs(2.0f, -2.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
    assertEqM128(r1, e)
}

public fun testMm512DivPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, -2.0f, 100.0f, 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, 1000.0f, -131.0f, 100.0f, -100.0f, -32.0f)
    val b = mm512SetrPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512DivPs(a, b)
    val e = mm512SetrPs(0.0f, 0.5f, -0.5f, -1.0f, 50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f, 0.0f, 0.5f, -0.5f, 500.0f, Float.NEGATIVE_INFINITY, 50.0f, -50.0f, -16.0f)
    assertEqM512(r, e)
    // 0/0 = NAN
}

public fun testMm512MaskDivPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, -2.0f, 100.0f, 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, 1000.0f, -131.0f, 100.0f, -100.0f, -32.0f)
    val b = mm512SetrPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512MaskDivPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskDivPs(a, 0xFFu, a, b)
    val e = mm512SetrPs(0.0f, 0.5f, -0.5f, -1.0f, 50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f, 0.0f, 1.0f, -1.0f, 1000.0f, -131.0f, 100.0f, -100.0f, -32.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzDivPs() {
    val a = mm512SetrPs(0.0f, 1.0f, -1.0f, -2.0f, 100.0f, 100.0f, -100.0f, -32.0f, 0.0f, 1.0f, -1.0f, 1000.0f, -131.0f, 100.0f, -100.0f, -32.0f)
    val b = mm512SetrPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512MaskzDivPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzDivPs(0xFFu, a, b)
    val e = mm512SetrPs(0.0f, 0.5f, -0.5f, -1.0f, 50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskDivPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, -2.0f, 100.0f, 100.0f, -100.0f, -32.0f)
    val b = mm256SetPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f)
    val r = mm256MaskDivPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskDivPs(a, 0xFFu, a, b)
    val e = mm256SetPs(0.0f, 0.5f, -0.5f, -1.0f, 50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzDivPs() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, -2.0f, 100.0f, 100.0f, -100.0f, -32.0f)
    val b = mm256SetPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f)
    val r = mm256MaskzDivPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzDivPs(0xFFu, a, b)
    val e = mm256SetPs(0.0f, 0.5f, -0.5f, -1.0f, 50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskDivPs() {
    val a = mmSetPs(100.0f, 100.0f, -100.0f, -32.0f)
    val b = mmSetPs(2.0f, 0.0f, 2.0f, 2.0f)
    val r = mmMaskDivPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskDivPs(a, 0xFu, a, b)
    val e = mmSetPs(50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzDivPs() {
    val a = mmSetPs(100.0f, 100.0f, -100.0f, -32.0f)
    val b = mmSetPs(2.0f, 0.0f, 2.0f, 2.0f)
    val r = mmMaskzDivPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzDivPs(0xFu, a, b)
    val e = mmSetPs(50.0f, Float.POSITIVE_INFINITY, -50.0f, -16.0f)
    assertEqM128(r1, e)
}

public fun testMm512MaxEpi32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaxEpi32(a, b)
    val e = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpi32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMaxEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpi32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpi32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMaxEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpi32(0xFFu, a, b)
    val e = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaxEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMaxEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(7, 6, 5, 4, 4, 5, 6, 7)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMaxEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(7, 6, 5, 4, 4, 5, 6, 7)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaxEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskMaxEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(3, 2, 2, 3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskzMaxEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpi32(0xFu, a, b)
    val e = mmSetEpi32(3, 2, 2, 3)
    assertEqM128i(r1, e)
}

public fun testMm512MaxPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaxPs(a, b)
    val e = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMaxPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskMaxPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskMaxPs(a, 0xFFu, a, b)
    val e = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMaxPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskzMaxPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMaxPs(0xFFu, a, b)
    val e = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskMaxPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val b = mm256SetPs(7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm256MaskMaxPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskMaxPs(a, 0xFFu, a, b)
    val e = mm256SetPs(7.0f, 6.0f, 5.0f, 4.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzMaxPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val b = mm256SetPs(7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm256MaskzMaxPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzMaxPs(0xFFu, a, b)
    val e = mm256SetPs(7.0f, 6.0f, 5.0f, 4.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskMaxPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(3.0f, 2.0f, 1.0f, 0.0f)
    val r = mmMaskMaxPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskMaxPs(a, 0xFu, a, b)
    val e = mmSetPs(3.0f, 2.0f, 2.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzMaxPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(3.0f, 2.0f, 1.0f, 0.0f)
    val r = mmMaskzMaxPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskMaxPs(a, 0xFu, a, b)
    val e = mmSetPs(3.0f, 2.0f, 2.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMm512MaxEpu32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaxEpu32(a, b)
    val e = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpu32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMaxEpu32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpu32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpu32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMaxEpu32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpu32(0xFFu, a, b)
    val e = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaxEpu32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMaxEpu32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpu32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(7, 6, 5, 4, 4, 5, 6, 7)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpu32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMaxEpu32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpu32(0xFFu, a, b)
    val e = mm256SetEpi32(7, 6, 5, 4, 4, 5, 6, 7)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaxEpu32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskMaxEpu32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpu32(a, 0xFu, a, b)
    val e = mmSetEpi32(3, 2, 2, 3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpu32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskzMaxEpu32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpu32(0xFu, a, b)
    val e = mmSetEpi32(3, 2, 2, 3)
    assertEqM128i(r1, e)
}

public fun testMm512MinEpi32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MinEpi32(a, b)
    val e = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpi32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMinEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpi32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpi32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMinEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpi32(0xFFu, a, b)
    val e = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMinEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMinEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(0, 1, 2, 3, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMinEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(0, 1, 2, 3, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMinEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskMinEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(0, 1, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskzMinEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpi32(0xFu, a, b)
    val e = mmSetEpi32(0, 1, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MinPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MinPs(a, b)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMinPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskMinPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskMinPs(a, 0xFFu, a, b)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMinPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskzMinPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMinPs(0xFFu, a, b)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskMinPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val b = mm256SetPs(7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm256MaskMinPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskMinPs(a, 0xFFu, a, b)
    val e = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzMinPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val b = mm256SetPs(7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm256MaskzMinPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzMinPs(0xFFu, a, b)
    val e = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskMinPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(3.0f, 2.0f, 1.0f, 0.0f)
    val r = mmMaskMinPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskMinPs(a, 0xFu, a, b)
    val e = mmSetPs(0.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzMinPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(3.0f, 2.0f, 1.0f, 0.0f)
    val r = mmMaskzMinPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzMinPs(0xFu, a, b)
    val e = mmSetPs(0.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r1, e)
}

public fun testMm512MinEpu32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MinEpu32(a, b)
    val e = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpu32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMinEpu32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpu32(a, 0xFFu, a, b)
    val e = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpu32() {
    val a = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMinEpu32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpu32(0xFFu, a, b)
    val e = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMinEpu32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMinEpu32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpu32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(0, 1, 2, 3, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpu32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetEpi32(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMinEpu32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpu32(0xFFu, a, b)
    val e = mm256SetEpi32(0, 1, 2, 3, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMinEpu32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskMinEpu32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpu32(a, 0xFu, a, b)
    val e = mmSetEpi32(0, 1, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpu32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val b = mmSetEpi32(3, 2, 1, 0)
    val r = mmMaskzMinEpu32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpu32(0xFu, a, b)
    val e = mmSetEpi32(0, 1, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SqrtPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 4.0f, 9.0f, 16.0f, 25.0f, 36.0f, 49.0f, 64.0f, 81.0f, 100.0f, 121.0f, 144.0f, 169.0f, 196.0f, 225.0f)
    val r = mm512SqrtPs(a)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskSqrtPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 4.0f, 9.0f, 16.0f, 25.0f, 36.0f, 49.0f, 64.0f, 81.0f, 100.0f, 121.0f, 144.0f, 169.0f, 196.0f, 225.0f)
    val r = mm512MaskSqrtPs(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskSqrtPs(a, 0xFFu, a)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 64.0f, 81.0f, 100.0f, 121.0f, 144.0f, 169.0f, 196.0f, 225.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzSqrtPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 4.0f, 9.0f, 16.0f, 25.0f, 36.0f, 49.0f, 64.0f, 81.0f, 100.0f, 121.0f, 144.0f, 169.0f, 196.0f, 225.0f)
    val r = mm512MaskzSqrtPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzSqrtPs(0xFFu, a)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskSqrtPs() {
    val a = mm256SetPs(0.0f, 1.0f, 4.0f, 9.0f, 16.0f, 25.0f, 36.0f, 49.0f)
    val r = mm256MaskSqrtPs(a, 0u, a)
    assertEqM256(r, a)
    val r1 = mm256MaskSqrtPs(a, 0xFFu, a)
    val e = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzSqrtPs() {
    val a = mm256SetPs(0.0f, 1.0f, 4.0f, 9.0f, 16.0f, 25.0f, 36.0f, 49.0f)
    val r = mm256MaskzSqrtPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzSqrtPs(0xFFu, a)
    val e = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskSqrtPs() {
    val a = mmSetPs(0.0f, 1.0f, 4.0f, 9.0f)
    val r = mmMaskSqrtPs(a, 0u, a)
    assertEqM128(r, a)
    val r1 = mmMaskSqrtPs(a, 0xFu, a)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzSqrtPs() {
    val a = mmSetPs(0.0f, 1.0f, 4.0f, 9.0f)
    val r = mmMaskzSqrtPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzSqrtPs(0xFu, a)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMm512FmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512FmaddPs(a, b, c)
    val e = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFmaddPs(a, 0u, b, c)
    assertEqM512(r, a)
    val r1 = mm512MaskFmaddPs(a, 0xFFu, b, c)
    val e = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFmaddPs(0u, a, b, c)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmaddPs(0xFFu, a, b, c)
    val e = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(2.0f)
    val r = mm512Mask3FmaddPs(a, b, c, 0u)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmaddPs(a, b, c, 0xFFu)
    val e = mm512SetrPs(2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskFmaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskFmaddPs(a, 0u, b, c)
    assertEqM256(r, a)
    val r1 = mm256MaskFmaddPs(a, 0xFFu, b, c)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzFmaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskzFmaddPs(0u, a, b, c)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzFmaddPs(0xFFu, a, b, c)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask3FmaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256Mask3FmaddPs(a, b, c, 0u)
    assertEqM256(r, c)
    val r1 = mm256Mask3FmaddPs(a, b, c, 0xFFu)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskFmaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskFmaddPs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFmaddPs(a, 0xFu, b, c)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskzFmaddPs(0u, a, b, c)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzFmaddPs(0xFu, a, b, c)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMask3FmaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMask3FmaddPs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FmaddPs(a, b, c, 0xFu)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm512FmsubPs() {
    val a = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val r = mm512FmsubPs(a, b, c)
    val e = mm512SetrPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFmsubPs(a, 0u, b, c)
    assertEqM512(r, a)
    val r1 = mm512MaskFmsubPs(a, 0xFFu, b, c)
    val e = mm512SetrPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFmsubPs(0u, a, b, c)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmsubPs(0xFFu, a, b, c)
    val e = mm512SetrPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512Mask3FmsubPs(a, b, c, 0u)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmsubPs(a, b, c, 0xFFu)
    val e = mm512SetrPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskFmsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskFmsubPs(a, 0u, b, c)
    assertEqM256(r, a)
    val r1 = mm256MaskFmsubPs(a, 0xFFu, b, c)
    val e = mm256SetPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzFmsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskzFmsubPs(0u, a, b, c)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzFmsubPs(0xFFu, a, b, c)
    val e = mm256SetPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask3FmsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256Mask3FmsubPs(a, b, c, 0u)
    assertEqM256(r, c)
    val r1 = mm256Mask3FmsubPs(a, b, c, 0xFFu)
    val e = mm256SetPs(-1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskFmsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskFmsubPs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFmsubPs(a, 0xFu, b, c)
    val e = mmSetPs(-1.0f, 0.0f, 1.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskzFmsubPs(0u, a, b, c)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzFmsubPs(0xFu, a, b, c)
    val e = mmSetPs(-1.0f, 0.0f, 1.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMmMask3FmsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMask3FmsubPs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FmsubPs(a, b, c, 0xFu)
    val e = mmSetPs(-1.0f, 0.0f, 1.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMm512FmaddsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512FmaddsubPs(a, b, c)
    val e = mm512SetrPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 7.0f, 10.0f, 9.0f, 12.0f, 11.0f, 14.0f, 13.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFmaddsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFmaddsubPs(a, 0u, b, c)
    assertEqM512(r, a)
    val r1 = mm512MaskFmaddsubPs(a, 0xFFu, b, c)
    val e = mm512SetrPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmaddsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFmaddsubPs(0u, a, b, c)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmaddsubPs(0xFFu, a, b, c)
    val e = mm512SetrPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmaddsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512Mask3FmaddsubPs(a, b, c, 0u)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmaddsubPs(a, b, c, 0xFFu)
    val e = mm512SetrPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskFmaddsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskFmaddsubPs(a, 0u, b, c)
    assertEqM256(r, a)
    val r1 = mm256MaskFmaddsubPs(a, 0xFFu, b, c)
    val e = mm256SetPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzFmaddsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskzFmaddsubPs(0u, a, b, c)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzFmaddsubPs(0xFFu, a, b, c)
    val e = mm256SetPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask3FmaddsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256Mask3FmaddsubPs(a, b, c, 0u)
    assertEqM256(r, c)
    val r1 = mm256Mask3FmaddsubPs(a, b, c, 0xFFu)
    val e = mm256SetPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskFmaddsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskFmaddsubPs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFmaddsubPs(a, 0xFu, b, c)
    val e = mmSetPs(1.0f, 0.0f, 3.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmaddsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskzFmaddsubPs(0u, a, b, c)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzFmaddsubPs(0xFu, a, b, c)
    val e = mmSetPs(1.0f, 0.0f, 3.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMmMask3FmaddsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMask3FmaddsubPs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FmaddsubPs(a, b, c, 0xFu)
    val e = mmSetPs(1.0f, 0.0f, 3.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMm512FmsubaddPs() {
    val a = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val r = mm512FmsubaddPs(a, b, c)
    val e = mm512SetrPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f, 9.0f, 8.0f, 11.0f, 10.0f, 13.0f, 12.0f, 15.0f, 14.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFmsubaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFmsubaddPs(a, 0u, b, c)
    assertEqM512(r, a)
    val r1 = mm512MaskFmsubaddPs(a, 0xFFu, b, c)
    val e = mm512SetrPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmsubaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFmsubaddPs(0u, a, b, c)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmsubaddPs(0xFFu, a, b, c)
    val e = mm512SetrPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmsubaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512Mask3FmsubaddPs(a, b, c, 0u)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmsubaddPs(a, b, c, 0xFFu)
    val e = mm512SetrPs(1.0f, 0.0f, 3.0f, 2.0f, 5.0f, 4.0f, 7.0f, 6.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskFmsubaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskFmsubaddPs(a, 0u, b, c)
    assertEqM256(r, a)
    val r1 = mm256MaskFmsubaddPs(a, 0xFFu, b, c)
    val e = mm256SetPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzFmsubaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskzFmsubaddPs(0u, a, b, c)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzFmsubaddPs(0xFFu, a, b, c)
    val e = mm256SetPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask3FmsubaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256Mask3FmsubaddPs(a, b, c, 0u)
    assertEqM256(r, c)
    val r1 = mm256Mask3FmsubaddPs(a, b, c, 0xFFu)
    val e = mm256SetPs(-1.0f, 2.0f, 1.0f, 4.0f, 3.0f, 6.0f, 5.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskFmsubaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskFmsubaddPs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFmsubaddPs(a, 0xFu, b, c)
    val e = mmSetPs(-1.0f, 2.0f, 1.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmsubaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskzFmsubaddPs(0u, a, b, c)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzFmsubaddPs(0xFu, a, b, c)
    val e = mmSetPs(-1.0f, 2.0f, 1.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMask3FmsubaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMask3FmsubaddPs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FmsubaddPs(a, b, c, 0xFu)
    val e = mmSetPs(-1.0f, 2.0f, 1.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm512FnmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512FnmaddPs(a, b, c)
    val e = mm512SetrPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f, -9.0f, -10.0f, -11.0f, -12.0f, -13.0f, -14.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFnmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFnmaddPs(a, 0u, b, c)
    assertEqM512(r, a)
    val r1 = mm512MaskFnmaddPs(a, 0xFFu, b, c)
    val e = mm512SetrPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFnmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFnmaddPs(0u, a, b, c)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFnmaddPs(0xFFu, a, b, c)
    val e = mm512SetrPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FnmaddPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512Mask3FnmaddPs(a, b, c, 0u)
    assertEqM512(r, c)
    val r1 = mm512Mask3FnmaddPs(a, b, c, 0xFFu)
    val e = mm512SetrPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskFnmaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskFnmaddPs(a, 0u, b, c)
    assertEqM256(r, a)
    val r1 = mm256MaskFnmaddPs(a, 0xFFu, b, c)
    val e = mm256SetPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzFnmaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskzFnmaddPs(0u, a, b, c)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzFnmaddPs(0xFFu, a, b, c)
    val e = mm256SetPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask3FnmaddPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256Mask3FnmaddPs(a, b, c, 0u)
    assertEqM256(r, c)
    val r1 = mm256Mask3FnmaddPs(a, b, c, 0xFFu)
    val e = mm256SetPs(1.0f, 0.0f, -1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskFnmaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskFnmaddPs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFnmaddPs(a, 0xFu, b, c)
    val e = mmSetPs(1.0f, 0.0f, -1.0f, -2.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFnmaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskzFnmaddPs(0u, a, b, c)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzFnmaddPs(0xFu, a, b, c)
    val e = mmSetPs(1.0f, 0.0f, -1.0f, -2.0f)
    assertEqM128(r1, e)
}

public fun testMmMask3FnmaddPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMask3FnmaddPs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FnmaddPs(a, b, c, 0xFu)
    val e = mmSetPs(1.0f, 0.0f, -1.0f, -2.0f)
    assertEqM128(r1, e)
}

public fun testMm512FnmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512FnmsubPs(a, b, c)
    val e = mm512SetrPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f, -9.0f, -10.0f, -11.0f, -12.0f, -13.0f, -14.0f, -15.0f, -16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFnmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFnmsubPs(a, 0u, b, c)
    assertEqM512(r, a)
    val r1 = mm512MaskFnmsubPs(a, 0xFFu, b, c)
    val e = mm512SetrPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFnmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFnmsubPs(0u, a, b, c)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFnmsubPs(0xFFu, a, b, c)
    val e = mm512SetrPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FnmsubPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val c = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    val r = mm512Mask3FnmsubPs(a, b, c, 0u)
    assertEqM512(r, c)
    val r1 = mm512Mask3FnmsubPs(a, b, c, 0xFFu)
    val e = mm512SetrPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskFnmsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskFnmsubPs(a, 0u, b, c)
    assertEqM256(r, a)
    val r1 = mm256MaskFnmsubPs(a, 0xFFu, b, c)
    val e = mm256SetPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzFnmsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256MaskzFnmsubPs(0u, a, b, c)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzFnmsubPs(0xFFu, a, b, c)
    val e = mm256SetPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask3FnmsubPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val c = mm256Set1Ps(1.0f)
    val r = mm256Mask3FnmsubPs(a, b, c, 0u)
    assertEqM256(r, c)
    val r1 = mm256Mask3FnmsubPs(a, b, c, 0xFFu)
    val e = mm256SetPs(-1.0f, -2.0f, -3.0f, -4.0f, -5.0f, -6.0f, -7.0f, -8.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskFnmsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskFnmsubPs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFnmsubPs(a, 0xFu, b, c)
    val e = mmSetPs(-1.0f, -2.0f, -3.0f, -4.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFnmsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMaskzFnmsubPs(0u, a, b, c)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzFnmsubPs(0xFu, a, b, c)
    val e = mmSetPs(-1.0f, -2.0f, -3.0f, -4.0f)
    assertEqM128(r1, e)
}

public fun testMmMask3FnmsubPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val c = mmSet1Ps(1.0f)
    val r = mmMask3FnmsubPs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FnmsubPs(a, b, c, 0xFu)
    val e = mmSetPs(-1.0f, -2.0f, -3.0f, -4.0f)
    assertEqM128(r1, e)
}

public fun testMm512Rcp14Ps() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512Rcp14Ps(a)
    val e = mm512Set1Ps(0.33333206f)
    assertEqM512(r, e)
}

public fun testMm512MaskRcp14Ps() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskRcp14Ps(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskRcp14Ps(a, 0xFF00u, a)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzRcp14Ps() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskzRcp14Ps(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzRcp14Ps(0xFF00u, a)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f, 0.33333206f)
    assertEqM512(r1, e)
}

public fun testMm256Rcp14Ps() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256Rcp14Ps(a)
    val e = mm256Set1Ps(0.33333206f)
    assertEqM256(r, e)
}

public fun testMm256MaskRcp14Ps() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256MaskRcp14Ps(a, 0u, a)
    assertEqM256(r, a)
    val r1 = mm256MaskRcp14Ps(a, 0xFFu, a)
    val e = mm256Set1Ps(0.33333206f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzRcp14Ps() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256MaskzRcp14Ps(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzRcp14Ps(0xFFu, a)
    val e = mm256Set1Ps(0.33333206f)
    assertEqM256(r1, e)
}

public fun testMmRcp14Ps() {
    val a = mmSet1Ps(3.0f)
    val r = mmRcp14Ps(a)
    val e = mmSet1Ps(0.33333206f)
    assertEqM128(r, e)
}

public fun testMmMaskRcp14Ps() {
    val a = mmSet1Ps(3.0f)
    val r = mmMaskRcp14Ps(a, 0u, a)
    assertEqM128(r, a)
    val r1 = mmMaskRcp14Ps(a, 0xFu, a)
    val e = mmSet1Ps(0.33333206f)
    assertEqM128(r1, e)
}

public fun testMmMaskzRcp14Ps() {
    val a = mmSet1Ps(3.0f)
    val r = mmMaskzRcp14Ps(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzRcp14Ps(0xFu, a)
    val e = mmSet1Ps(0.33333206f)
    assertEqM128(r1, e)
}

public fun testMm512Rsqrt14Ps() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512Rsqrt14Ps(a)
    val e = mm512Set1Ps(0.5773392f)
    assertEqM512(r, e)
}

public fun testMm512MaskRsqrt14Ps() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskRsqrt14Ps(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskRsqrt14Ps(a, 0xFF00u, a)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzRsqrt14Ps() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskzRsqrt14Ps(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzRsqrt14Ps(0xFF00u, a)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f, 0.5773392f)
    assertEqM512(r1, e)
}

public fun testMm256Rsqrt14Ps() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256Rsqrt14Ps(a)
    val e = mm256Set1Ps(0.5773392f)
    assertEqM256(r, e)
}

public fun testMm256MaskRsqrt14Ps() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256MaskRsqrt14Ps(a, 0u, a)
    assertEqM256(r, a)
    val r1 = mm256MaskRsqrt14Ps(a, 0xFFu, a)
    val e = mm256Set1Ps(0.5773392f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzRsqrt14Ps() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256MaskzRsqrt14Ps(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzRsqrt14Ps(0xFFu, a)
    val e = mm256Set1Ps(0.5773392f)
    assertEqM256(r1, e)
}

public fun testMmRsqrt14Ps() {
    val a = mmSet1Ps(3.0f)
    val r = mmRsqrt14Ps(a)
    val e = mmSet1Ps(0.5773392f)
    assertEqM128(r, e)
}

public fun testMmMaskRsqrt14Ps() {
    val a = mmSet1Ps(3.0f)
    val r = mmMaskRsqrt14Ps(a, 0u, a)
    assertEqM128(r, a)
    val r1 = mmMaskRsqrt14Ps(a, 0xFu, a)
    val e = mmSet1Ps(0.5773392f)
    assertEqM128(r1, e)
}

public fun testMmMaskzRsqrt14Ps() {
    val a = mmSet1Ps(3.0f)
    val r = mmMaskzRsqrt14Ps(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzRsqrt14Ps(0xFu, a)
    val e = mmSet1Ps(0.5773392f)
    assertEqM128(r1, e)
}

public fun testMm512GetexpPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512GetexpPs(a)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskGetexpPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskGetexpPs(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskGetexpPs(a, 0xFF00u, a)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzGetexpPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskzGetexpPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzGetexpPs(0xFF00u, a)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm256GetexpPs() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256GetexpPs(a)
    val e = mm256Set1Ps(1.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskGetexpPs() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256MaskGetexpPs(a, 0u, a)
    assertEqM256(r, a)
    val r1 = mm256MaskGetexpPs(a, 0xFFu, a)
    val e = mm256Set1Ps(1.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzGetexpPs() {
    val a = mm256Set1Ps(3.0f)
    val r = mm256MaskzGetexpPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzGetexpPs(0xFFu, a)
    val e = mm256Set1Ps(1.0f)
    assertEqM256(r1, e)
}

public fun testMmGetexpPs() {
    val a = mmSet1Ps(3.0f)
    val r = mmGetexpPs(a)
    val e = mmSet1Ps(1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskGetexpPs() {
    val a = mmSet1Ps(3.0f)
    val r = mmMaskGetexpPs(a, 0u, a)
    assertEqM128(r, a)
    val r1 = mmMaskGetexpPs(a, 0xFu, a)
    val e = mmSet1Ps(1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzGetexpPs() {
    val a = mmSet1Ps(3.0f)
    val r = mmMaskzGetexpPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzGetexpPs(0xFu, a)
    val e = mmSet1Ps(1.0f)
    assertEqM128(r1, e)
}

public fun testMm512RoundscalePs() {
    val a = mm512Set1Ps(1.1f)
    val r = mm512RoundscalePs(a, 0x0)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskRoundscalePs() {
    val a = mm512Set1Ps(1.1f)
    val r = mm512MaskRoundscalePs(a, 0u, a, 0x0)
    val e = mm512Set1Ps(1.1f)
    assertEqM512(r, e)
    val r1 = mm512MaskRoundscalePs(a, 0xFFFFu, a, 0x0)
    val e1 = mm512Set1Ps(1.0f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskzRoundscalePs() {
    val a = mm512Set1Ps(1.1f)
    val r = mm512MaskzRoundscalePs(0u, a, 0x0)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzRoundscalePs(0xFFFFu, a, 0x0)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r1, e)
}

public fun testMm256RoundscalePs() {
    val a = mm256Set1Ps(1.1f)
    val r = mm256RoundscalePs(a, 0x0)
    val e = mm256Set1Ps(1.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskRoundscalePs() {
    val a = mm256Set1Ps(1.1f)
    val r = mm256MaskRoundscalePs(a, 0u, a, 0x0)
    val e = mm256Set1Ps(1.1f)
    assertEqM256(r, e)
    val r1 = mm256MaskRoundscalePs(a, 0xFFu, a, 0x0)
    val e1 = mm256Set1Ps(1.0f)
    assertEqM256(r1, e1)
}

public fun testMm256MaskzRoundscalePs() {
    val a = mm256Set1Ps(1.1f)
    val r = mm256MaskzRoundscalePs(0u, a, 0x0)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzRoundscalePs(0xFFu, a, 0x0)
    val e = mm256Set1Ps(1.0f)
    assertEqM256(r1, e)
}

public fun testMmRoundscalePs() {
    val a = mmSet1Ps(1.1f)
    val r = mmRoundscalePs(a, 0x0)
    val e = mmSet1Ps(1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskRoundscalePs() {
    val a = mmSet1Ps(1.1f)
    val r = mmMaskRoundscalePs(a, 0u, a, 0x0)
    val e = mmSet1Ps(1.1f)
    assertEqM128(r, e)
    val r1 = mmMaskRoundscalePs(a, 0xFu, a, 0x0)
    val e1 = mmSet1Ps(1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzRoundscalePs() {
    val a = mmSet1Ps(1.1f)
    val r = mmMaskzRoundscalePs(0u, a, 0x0)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzRoundscalePs(0xFu, a, 0x0)
    val e = mmSet1Ps(1.0f)
    assertEqM128(r1, e)
}

public fun testMm512ScalefPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512ScalefPs(a, b)
    val e = mm512Set1Ps(8.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskScalefPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512MaskScalefPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskScalefPs(a, 0xFF00u, a, b)
    val e = mm512SetPs(8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzScalefPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512MaskzScalefPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzScalefPs(0xFF00u, a, b)
    val e = mm512SetPs(8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256ScalefPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256Set1Ps(3.0f)
    val r = mm256ScalefPs(a, b)
    val e = mm256Set1Ps(8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskScalefPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256Set1Ps(3.0f)
    val r = mm256MaskScalefPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskScalefPs(a, 0xFFu, a, b)
    val e = mm256Set1Ps(8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzScalefPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256Set1Ps(3.0f)
    val r = mm256MaskzScalefPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzScalefPs(0xFFu, a, b)
    val e = mm256Set1Ps(8.0f)
    assertEqM256(r1, e)
}

public fun testMmScalefPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmScalefPs(a, b)
    val e = mmSet1Ps(8.0f)
    assertEqM128(r, e)
}

public fun testMmMaskScalefPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskScalefPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskScalefPs(a, 0xFu, a, b)
    val e = mmSet1Ps(8.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzScalefPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskzScalefPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzScalefPs(0xFu, a, b)
    val e = mmSet1Ps(8.0f)
    assertEqM128(r1, e)
}

public fun testMm512FixupimmPs() {
    val a = mm512Set1Ps(Float.NaN)
    val b = mm512Set1Ps(Float.MAX_VALUE)
    val c = mm512Set1Epi32(Int.MAX_VALUE)
    // let r = _mm512_fixupimm_ps(a, b, c, 5);
    val r = mm512FixupimmPs(a, b, c, 5)
    val e = mm512Set1Ps(0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFixupimmPs() {
    val a = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val b = mm512Set1Ps(Float.MAX_VALUE)
    val c = mm512Set1Epi32(Int.MAX_VALUE)
    val r = mm512MaskFixupimmPs(a, 0xFF00u, b, c, 5)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzFixupimmPs() {
    val a = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val b = mm512Set1Ps(Float.MAX_VALUE)
    val c = mm512Set1Epi32(Int.MAX_VALUE)
    val r = mm512MaskzFixupimmPs(0xFF00u, a, b, c, 5)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm256FixupimmPs() {
    val a = mm256Set1Ps(Float.NaN)
    val b = mm256Set1Ps(Float.MAX_VALUE)
    val c = mm256Set1Epi32(Int.MAX_VALUE)
    val r = mm256FixupimmPs(a, b, c, 5)
    val e = mm256Set1Ps(0.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskFixupimmPs() {
    val a = mm256Set1Ps(Float.NaN)
    val b = mm256Set1Ps(Float.MAX_VALUE)
    val c = mm256Set1Epi32(Int.MAX_VALUE)
    val r = mm256MaskFixupimmPs(a, 0xFFu, b, c, 5)
    val e = mm256Set1Ps(0.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzFixupimmPs() {
    val a = mm256Set1Ps(Float.NaN)
    val b = mm256Set1Ps(Float.MAX_VALUE)
    val c = mm256Set1Epi32(Int.MAX_VALUE)
    val r = mm256MaskzFixupimmPs(0xFFu, a, b, c, 5)
    val e = mm256Set1Ps(0.0f)
    assertEqM256(r, e)
}

public fun testMmFixupimmPs() {
    val a = mmSet1Ps(Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmFixupimmPs(a, b, c, 5)
    val e = mmSet1Ps(0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFixupimmPs() {
    val a = mmSet1Ps(Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmMaskFixupimmPs(a, 0xFu, b, c, 5)
    val e = mmSet1Ps(0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzFixupimmPs() {
    val a = mmSet1Ps(Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmMaskzFixupimmPs(0xFu, a, b, c, 5)
    val e = mmSet1Ps(0.0f)
    assertEqM128(r, e)
}

public fun testMm512TernarylogicEpi32() {
    val a = mm512Set4Epi32(0x4, 0x6, 0x1, 0x5)
    val b = mm512Set4Epi32(0x2, 0x3, 0x1, 0x6)
    val c = mm512Set4Epi32(0x1, 0x0, 0x1, 0x7)
    // Identity of A.
    val r = mm512TernarylogicEpi32(a, b, c, 0xF0)
    assertEqM512i(r, a)
    // Bitwise xor.
    val r1 = mm512TernarylogicEpi32(a, b, c, 0x96)
    val e = mm512Set4Epi32(0x7, 0x5, 0x1, 0x4)
    assertEqM512i(r1, e)
    assertEqM512i(r1, mm512XorSi512(mm512XorSi512(a, b), c))
    // Majority (2 or more bits set).
    val r2 = mm512TernarylogicEpi32(a, b, c, 0xE8)
    val e1 = mm512Set4Epi32(0x0, 0x2, 0x1, 0x7)
    assertEqM512i(r2, e1)
}

public fun testMm512MaskTernarylogicEpi32() {
    val src = mm512Set1Epi32(1 shl 2)
    val a = mm512Set1Epi32(1 shl 1)
    val b = mm512Set1Epi32(1 shl 0)
    val r = mm512MaskTernarylogicEpi32(src, 0u, a, b, 8)
    assertEqM512i(r, src)
    val r1 = mm512MaskTernarylogicEpi32(src, 0xFFFFu, a, b, 8)
    val e = mm512Set1Epi32(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzTernarylogicEpi32() {
    val a = mm512Set1Epi32(1 shl 2)
    val b = mm512Set1Epi32(1 shl 1)
    val c = mm512Set1Epi32(1 shl 0)
    val r = mm512MaskzTernarylogicEpi32(0u, a, b, c, 9)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzTernarylogicEpi32(0xFFFFu, a, b, c, 8)
    val e = mm512Set1Epi32(0)
    assertEqM512i(r1, e)
}

public fun testMm256TernarylogicEpi32() {
    val mm256Set4Epi32 = { a, b, c, d -> mm256SetrEpi32(a, b, c, d, a, b, c, d) }
    val a = mm256Set4Epi32(0x4, 0x6, 0x1, 0x5)
    val b = mm256Set4Epi32(0x2, 0x3, 0x1, 0x6)
    val c = mm256Set4Epi32(0x1, 0x0, 0x1, 0x7)
    // Identity of A.
    val r = mm256TernarylogicEpi32(a, b, c, 0xF0)
    assertEqM256i(r, a)
    // Bitwise xor.
    val r1 = mm256TernarylogicEpi32(a, b, c, 0x96)
    val e = mm256Set4Epi32(0x7, 0x5, 0x1, 0x4)
    assertEqM256i(r1, e)
    assertEqM256i(r1, mm256XorSi256(mm256XorSi256(a, b), c))
    // Majority (2 or more bits set).
    val r2 = mm256TernarylogicEpi32(a, b, c, 0xE8)
    val e1 = mm256Set4Epi32(0x0, 0x2, 0x1, 0x7)
    assertEqM256i(r2, e1)
}

public fun testMm256MaskTernarylogicEpi32() {
    val src = mm256Set1Epi32(1 shl 2)
    val a = mm256Set1Epi32(1 shl 1)
    val b = mm256Set1Epi32(1 shl 0)
    val r = mm256MaskTernarylogicEpi32(src, 0u, a, b, 8)
    assertEqM256i(r, src)
    val r1 = mm256MaskTernarylogicEpi32(src, 0xFFu, a, b, 8)
    val e = mm256Set1Epi32(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzTernarylogicEpi32() {
    val a = mm256Set1Epi32(1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val c = mm256Set1Epi32(1 shl 0)
    val r = mm256MaskzTernarylogicEpi32(0u, a, b, c, 9)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzTernarylogicEpi32(0xFFu, a, b, c, 8)
    val e = mm256Set1Epi32(0)
    assertEqM256i(r1, e)
}

public fun testMmTernarylogicEpi32() {
    val a = mmSetrEpi32(0x4, 0x6, 0x1, 0x5)
    val b = mmSetrEpi32(0x2, 0x3, 0x1, 0x6)
    val c = mmSetrEpi32(0x1, 0x0, 0x1, 0x7)
    // Identity of A.
    val r = mmTernarylogicEpi32(a, b, c, 0xF0)
    assertEqM128i(r, a)
    // Bitwise xor.
    val r1 = mmTernarylogicEpi32(a, b, c, 0x96)
    val e = mmSetrEpi32(0x7, 0x5, 0x1, 0x4)
    assertEqM128i(r1, e)
    assertEqM128i(r1, mmXorSi128(mmXorSi128(a, b), c))
    // Majority (2 or more bits set).
    val r2 = mmTernarylogicEpi32(a, b, c, 0xE8)
    val e1 = mmSetrEpi32(0x0, 0x2, 0x1, 0x7)
    assertEqM128i(r2, e1)
}

public fun testMmMaskTernarylogicEpi32() {
    val src = mmSet1Epi32(1 shl 2)
    val a = mmSet1Epi32(1 shl 1)
    val b = mmSet1Epi32(1 shl 0)
    val r = mmMaskTernarylogicEpi32(src, 0u, a, b, 8)
    assertEqM128i(r, src)
    val r1 = mmMaskTernarylogicEpi32(src, 0xFu, a, b, 8)
    val e = mmSet1Epi32(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzTernarylogicEpi32() {
    val a = mmSet1Epi32(1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val c = mmSet1Epi32(1 shl 0)
    val r = mmMaskzTernarylogicEpi32(0u, a, b, c, 9)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzTernarylogicEpi32(0xFu, a, b, c, 8)
    val e = mmSet1Epi32(0)
    assertEqM128i(r1, e)
}

public fun testMm512GetmantPs() {
    val a = mm512Set1Ps(10.0f)
    val r = mm512GetmantPs(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm512Set1Ps(1.25f)
    assertEqM512(r, e)
}

public fun testMm512MaskGetmantPs() {
    val a = mm512Set1Ps(10.0f)
    val r = mm512MaskGetmantPs(a, 0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    assertEqM512(r, a)
    val r1 = mm512MaskGetmantPs(a, 0xFF00u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mm512SetrPs(10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzGetmantPs() {
    val a = mm512Set1Ps(10.0f)
    val r = mm512MaskzGetmantPs(0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzGetmantPs(0xFF00u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f)
    assertEqM512(r1, e)
}

public fun testMm256GetmantPs() {
    val a = mm256Set1Ps(10.0f)
    val r = mm256GetmantPs(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mm256Set1Ps(1.25f)
    assertEqM256(r, e)
}

public fun testMm256MaskGetmantPs() {
    val a = mm256Set1Ps(10.0f)
    val r = mm256MaskGetmantPs(a, 0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    assertEqM256(r, a)
    val r1 = mm256MaskGetmantPs(a, 0xFFu, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mm256Set1Ps(1.25f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzGetmantPs() {
    val a = mm256Set1Ps(10.0f)
    val r = mm256MaskzGetmantPs(0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzGetmantPs(0xFFu, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mm256Set1Ps(1.25f)
    assertEqM256(r1, e)
}

public fun testMmGetmantPs() {
    val a = mmSet1Ps(10.0f)
    val r = mmGetmantPs(a, MM_MANT_NORM_P75_1P5, MM_MANT_SIGN_NAN)
    val e = mmSet1Ps(1.25f)
    assertEqM128(r, e)
}

public fun testMmMaskGetmantPs() {
    val a = mmSet1Ps(10.0f)
    val r = mmMaskGetmantPs(a, 0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    assertEqM128(r, a)
    val r1 = mmMaskGetmantPs(a, 0xFu, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSet1Ps(1.25f)
    assertEqM128(r1, e)
}

public fun testMmMaskzGetmantPs() {
    val a = mmSet1Ps(10.0f)
    val r = mmMaskzGetmantPs(0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzGetmantPs(0xFu, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSet1Ps(1.25f)
    assertEqM128(r1, e)
}

public fun testMm512AddRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000007f)
    val b = mm512Set1Ps(-1.0f)
    val r = mm512AddRoundPs(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-1.0f, 0.5f, 1.0f, 2.5f, 3.0f, 4.5f, 5.0f, 6.5f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512AddRoundPs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPs(-1.0f, 0.5f, 1.0f, 2.5f, 3.0f, 4.5f, 5.0f, 6.5f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskAddRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000007f)
    val b = mm512Set1Ps(-1.0f)
    val r = mm512MaskAddRoundPs(a, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskAddRoundPs(a, 0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.99999994f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzAddRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000007f)
    val b = mm512Set1Ps(-1.0f)
    val r = mm512MaskzAddRoundPs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzAddRoundPs(0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.99999994f)
    assertEqM512(r1, e)
}

public fun testMm512SubRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512SubRoundPs(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-1.0f, 0.5f, 1.0f, 2.5f, 3.0f, 4.5f, 5.0f, 6.5f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512SubRoundPs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPs(-1.0f, 0.5f, 1.0f, 2.5f, 3.0f, 4.5f, 5.0f, 6.5f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskSubRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512MaskSubRoundPs(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskSubRoundPs(a, 0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.99999994f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzSubRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val r = mm512MaskzSubRoundPs(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzSubRoundPs(0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 7.0f, 8.5f, 9.0f, 10.5f, 11.0f, 12.5f, 13.0f, -0.99999994f)
    assertEqM512(r1, e)
}

public fun testMm512MulRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000000000000000000007f)
    val b = mm512Set1Ps(0.1f)
    val r = mm512MulRoundPs(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 0.15f, 0.2f, 0.35f, 0.4f, 0.55f, 0.6f, 0.75f, 0.8f, 0.95f, 1.0f, 1.15f, 1.2f, 1.35f, 1.4f, 0.000000000000000000000007000001f)
    assertEqM512(r, e)
    val r1 = mm512MulRoundPs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPs(0.0f, 0.14999999f, 0.2f, 0.35f, 0.4f, 0.54999995f, 0.59999996f, 0.75f, 0.8f, 0.95f, 1.0f, 1.15f, 1.1999999f, 1.3499999f, 1.4f, 0.000000000000000000000007f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskMulRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000000000000000000007f)
    val b = mm512Set1Ps(0.1f)
    val r = mm512MaskMulRoundPs(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskMulRoundPs(a, 0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 0.8f, 0.95f, 1.0f, 1.15f, 1.2f, 1.35f, 1.4f, 0.000000000000000000000007000001f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMulRoundPs() {
    val a = mm512SetrPs(0.0f, 1.5f, 2.0f, 3.5f, 4.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 0.00000000000000000000007f)
    val b = mm512Set1Ps(0.1f)
    val r = mm512MaskzMulRoundPs(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMulRoundPs(0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.95f, 1.0f, 1.15f, 1.2f, 1.35f, 1.4f, 0.000000000000000000000007000001f)
    assertEqM512(r1, e)
}

public fun testMm512DivRoundPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512DivRoundPs(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(0.33333334f)
    assertEqM512(r, e)
    val r1 = mm512DivRoundPs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Ps(0.3333333f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskDivRoundPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512MaskDivRoundPs(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskDivRoundPs(a, 0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzDivRoundPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512MaskzDivRoundPs(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzDivRoundPs(0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f, 0.33333334f)
    assertEqM512(r1, e)
}

public fun testMm512SqrtRoundPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512SqrtRoundPs(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(1.7320508f)
    assertEqM512(r, e)
    val r1 = mm512SqrtRoundPs(a, MM_FROUND_TO_POS_INF or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Ps(1.7320509f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskSqrtRoundPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskSqrtRoundPs(a, 0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskSqrtRoundPs(a, 0xFF00u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzSqrtRoundPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskzSqrtRoundPs(0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzSqrtRoundPs(0xFF00u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f, 1.7320508f)
    assertEqM512(r1, e)
}

public fun testMm512FmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512FmaddRoundPs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(-0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512FmaddRoundPs(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Ps(-0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskFmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskFmaddRoundPs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskFmaddRoundPs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskzFmaddRoundPs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmaddRoundPs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512Mask3FmaddRoundPs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmaddRoundPs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    assertEqM512(r1, e)
}

public fun testMm512FmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512FmsubRoundPs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(-0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512FmsubRoundPs(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Ps(-0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskFmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFmsubRoundPs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskFmsubRoundPs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFmsubRoundPs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmsubRoundPs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512Mask3FmsubRoundPs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmsubRoundPs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, -0.99999994f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512FmaddsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512FmaddsubRoundPs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512FmaddsubRoundPs(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPs(1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskFmaddsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskFmaddsubRoundPs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskFmaddsubRoundPs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmaddsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskzFmaddsubRoundPs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmaddsubRoundPs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmaddsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512Mask3FmaddsubRoundPs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmaddsubRoundPs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    assertEqM512(r1, e)
}

public fun testMm512FmsubaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512FmsubaddRoundPs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f)
    assertEqM512(r, e)
    val r1 = mm512FmsubaddRoundPs(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPs(-0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f, -0.9999999f, 1.0f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskFmsubaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskFmsubaddRoundPs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskFmsubaddRoundPs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFmsubaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskzFmsubaddRoundPs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFmsubaddRoundPs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FmsubaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512Mask3FmsubaddRoundPs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, c)
    val r1 = mm512Mask3FmsubaddRoundPs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(-0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -0.99999994f, 1.0000001f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    assertEqM512(r1, e)
}

public fun testMm512FnmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512FnmaddRoundPs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512FnmaddRoundPs(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Ps(0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskFnmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskFnmaddRoundPs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskFnmaddRoundPs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFnmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512MaskzFnmaddRoundPs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFnmaddRoundPs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FnmaddRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(1.0f)
    val r = mm512Mask3FnmaddRoundPs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, c)
    val r1 = mm512Mask3FnmaddRoundPs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512FnmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512FnmsubRoundPs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(0.99999994f)
    assertEqM512(r, e)
    val r1 = mm512FnmsubRoundPs(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Ps(0.9999999f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskFnmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskFnmsubRoundPs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskFnmsubRoundPs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f, 0.00000007f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzFnmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512MaskzFnmsubRoundPs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzFnmsubRoundPs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask3FnmsubRoundPs() {
    val a = mm512Set1Ps(0.00000007f)
    val b = mm512Set1Ps(1.0f)
    val c = mm512Set1Ps(-1.0f)
    val r = mm512Mask3FnmsubRoundPs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, c)
    val r1 = mm512Mask3FnmsubRoundPs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, 0.99999994f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaxRoundPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaxRoundPs(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMaxRoundPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskMaxRoundPs(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, a)
    val r1 = mm512MaskMaxRoundPs(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMaxRoundPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskzMaxRoundPs(0u, a, b, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMaxRoundPs(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512MinRoundPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MinRoundPs(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMinRoundPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskMinRoundPs(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, a)
    val r1 = mm512MaskMinRoundPs(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMinRoundPs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm512MaskzMinRoundPs(0u, a, b, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMinRoundPs(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512GetexpRoundPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512GetexpRoundPs(a, MM_FROUND_CUR_DIRECTION)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskGetexpRoundPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskGetexpRoundPs(a, 0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, a)
    val r1 = mm512MaskGetexpRoundPs(a, 0xFF00u, a, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzGetexpRoundPs() {
    val a = mm512Set1Ps(3.0f)
    val r = mm512MaskzGetexpRoundPs(0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzGetexpRoundPs(0xFF00u, a, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512RoundscaleRoundPs() {
    val a = mm512Set1Ps(1.1f)
    val r = mm512RoundscaleRoundPs(a, 0, MM_FROUND_CUR_DIRECTION)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskRoundscaleRoundPs() {
    val a = mm512Set1Ps(1.1f)
    val r = mm512MaskRoundscaleRoundPs(a, 0u, a, 0, MM_FROUND_CUR_DIRECTION)
    val e = mm512Set1Ps(1.1f)
    assertEqM512(r, e)
    val r1 = mm512MaskRoundscaleRoundPs(a, 0xFFFFu, a, 0, MM_FROUND_CUR_DIRECTION)
    val e1 = mm512Set1Ps(1.0f)
    assertEqM512(r1, e1)
}

public fun testMm512MaskzRoundscaleRoundPs() {
    val a = mm512Set1Ps(1.1f)
    val r = mm512MaskzRoundscaleRoundPs(0u, a, 0, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzRoundscaleRoundPs(0xFFFFu, a, 0, MM_FROUND_CUR_DIRECTION)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r1, e)
}

public fun testMm512ScalefRoundPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512ScalefRoundPs(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(8.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskScalefRoundPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512MaskScalefRoundPs(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, a)
    val r1 = mm512MaskScalefRoundPs(a, 0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPs(8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzScalefRoundPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(3.0f)
    val r = mm512MaskzScalefRoundPs(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzScalefRoundPs(0xFF00u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPs(8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512FixupimmRoundPs() {
    val a = mm512Set1Ps(Float.NaN)
    val b = mm512Set1Ps(Float.MAX_VALUE)
    val c = mm512Set1Epi32(Int.MAX_VALUE)
    val r = mm512FixupimmRoundPs(a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mm512Set1Ps(0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskFixupimmRoundPs() {
    val a = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val b = mm512Set1Ps(Float.MAX_VALUE)
    val c = mm512Set1Epi32(Int.MAX_VALUE)
    val r = mm512MaskFixupimmRoundPs(a, 0xFF00u, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzFixupimmRoundPs() {
    val a = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val b = mm512Set1Ps(Float.MAX_VALUE)
    val c = mm512Set1Epi32(Int.MAX_VALUE)
    val r = mm512MaskzFixupimmRoundPs(0xFF00u, a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512GetmantRoundPs() {
    val a = mm512Set1Ps(10.0f)
    val r = mm512GetmantRoundPs(a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mm512Set1Ps(1.25f)
    assertEqM512(r, e)
}

public fun testMm512MaskGetmantRoundPs() {
    val a = mm512Set1Ps(10.0f)
    val r = mm512MaskGetmantRoundPs(a, 0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, a)
    val r1 = mm512MaskGetmantRoundPs(a, 0xFF00u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzGetmantRoundPs() {
    val a = mm512Set1Ps(10.0f)
    val r = mm512MaskzGetmantRoundPs(0u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzGetmantRoundPs(0xFF00u, a, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mm512SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f, 1.25f)
    assertEqM512(r1, e)
}

public fun testMm512CvtpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.4f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvtpsEpi32(a)
    val e = mm512SetrEpi32(0, -1, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.4f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvtpsEpi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtpsEpi32(src, 0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -4, 4, -6, 6, -8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.4f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvtpsEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtpsEpi32(0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -4, 4, -6, 6, -8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtpsEpi32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm256Set1Epi32(0)
    val r = mm256MaskCvtpsEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtpsEpi32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtpsEpi32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm256MaskzCvtpsEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtpsEpi32(0xFFu, a)
    val e = mm256SetEpi32(8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtpsEpi32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtpsEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtpsEpi32(src, 0xFu, a)
    val e = mmSetEpi32(12, 14, 14, 16)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtpsEpi32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val r = mmMaskzCvtpsEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtpsEpi32(0xFu, a)
    val e = mmSetEpi32(12, 14, 14, 16)
    assertEqM128i(r1, e)
}

public fun testMm512CvtpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.4f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvtpsEpu32(a)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.4f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvtpsEpu32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtpsEpu32(src, 0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.4f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvtpsEpu32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtpsEpu32(0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256CvtpsEpu32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm256CvtpsEpu32(a)
    val e = mm256SetEpi32(8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtpsEpu32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm256Set1Epi32(0)
    val r = mm256MaskCvtpsEpu32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtpsEpu32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtpsEpu32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm256MaskzCvtpsEpu32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtpsEpu32(0xFFu, a)
    val e = mm256SetEpi32(8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM256i(r1, e)
}

public fun testMmCvtpsEpu32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val r = mmCvtpsEpu32(a)
    val e = mmSetEpi32(12, 14, 14, 16)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtpsEpu32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtpsEpu32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtpsEpu32(src, 0xFu, a)
    val e = mmSetEpi32(12, 14, 14, 16)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtpsEpu32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val r = mmMaskzCvtpsEpu32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtpsEpu32(0xFu, a)
    val e = mmSetEpi32(12, 14, 14, 16)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi8Epi32(a)
    val e = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi32(-1)
    val r = mm512MaskCvtepi8Epi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepi8Epi32(src, 0xFFu, a)
    val e = mm512SetEpi32(-1, -1, -1, -1, -1, -1, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi8Epi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepi8Epi32(0xFFu, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi32(-1)
    val r = mm256MaskCvtepi8Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepi8Epi32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepi8Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepi8Epi32(0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi32(-1)
    val r = mmMaskCvtepi8Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi8Epi32(src, 0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepi8Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi8Epi32(0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu8Epi32(a)
    val e = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi32(-1)
    val r = mm512MaskCvtepu8Epi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepu8Epi32(src, 0xFFu, a)
    val e = mm512SetEpi32(-1, -1, -1, -1, -1, -1, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu8Epi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepu8Epi32(0xFFu, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi32(-1)
    val r = mm256MaskCvtepu8Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepu8Epi32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepu8Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepu8Epi32(0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi32(-1)
    val r = mmMaskCvtepu8Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepu8Epi32(src, 0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepu8Epi32() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepu8Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepu8Epi32(0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi16Epi32() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi16Epi32(a)
    val e = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepi16Epi32() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi32(-1)
    val r = mm512MaskCvtepi16Epi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepi16Epi32(src, 0xFFu, a)
    val e = mm512SetEpi32(-1, -1, -1, -1, -1, -1, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepi16Epi32() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi16Epi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepi16Epi32(0xFFu, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepi16Epi32() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val src = mm256Set1Epi32(-1)
    val r = mm256MaskCvtepi16Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepi16Epi32(src, 0xFFu, a)
    val e = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepi16Epi32() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzCvtepi16Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepi16Epi32(0xFFu, a)
    val e = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepi16Epi32() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val src = mmSet1Epi32(-1)
    val r = mmMaskCvtepi16Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi16Epi32(src, 0xFu, a)
    val e = mmSetEpi32(4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi16Epi32() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskzCvtepi16Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi16Epi32(0xFu, a)
    val e = mmSetEpi32(4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepu16Epi32() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu16Epi32(a)
    val e = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepu16Epi32() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi32(-1)
    val r = mm512MaskCvtepu16Epi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepu16Epi32(src, 0xFFu, a)
    val e = mm512SetEpi32(-1, -1, -1, -1, -1, -1, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepu16Epi32() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu16Epi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepu16Epi32(0xFFu, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepu16Epi32() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi32(-1)
    val r = mm256MaskCvtepu16Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepu16Epi32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepu16Epi32() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepu16Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepu16Epi32(0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepu16Epi32() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi32(-1)
    val r = mmMaskCvtepu16Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepu16Epi32(src, 0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepu16Epi32() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepu16Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepu16Epi32(0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi32Ps() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi32Ps(a)
    val e = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtepi32Ps() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Ps(-1.0f)
    val r = mm512MaskCvtepi32Ps(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtepi32Ps(src, 0xFFu, a)
    val e = mm512SetPs(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCvtepi32Ps() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi32Ps(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCvtepi32Ps(0xFFu, a)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskCvtepi32Ps() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mm256Set1Ps(-1.0f)
    val r = mm256MaskCvtepi32Ps(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskCvtepi32Ps(src, 0xFFu, a)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzCvtepi32Ps() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzCvtepi32Ps(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzCvtepi32Ps(0xFFu, a)
    val e = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskCvtepi32Ps() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val src = mmSet1Ps(-1.0f)
    val r = mmMaskCvtepi32Ps(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskCvtepi32Ps(src, 0xFu, a)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzCvtepi32Ps() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskzCvtepi32Ps(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzCvtepi32Ps(0xFu, a)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm512Cvtepu32Ps() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu32Ps(a)
    val e = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtepu32Ps() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Ps(-1.0f)
    val r = mm512MaskCvtepu32Ps(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtepu32Ps(src, 0xFFu, a)
    val e = mm512SetPs(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCvtepu32Ps() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu32Ps(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCvtepu32Ps(0xFFu, a)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm256Cvtepu32Ps() {
    val a = mm256SetEpi32(-1, Int.MIN_VALUE, 16777217, 16777216, 4, 3, 2, 1)
    val r = mm256Cvtepu32Ps(a)
    val e = mm256SetPs(4294967296.0f, 2147483648.0f, 16777216.0f, 16777216.0f, 4.0f, 3.0f, 2.0f, 1.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskCvtepu32Ps() {
    val a = mm256SetEpi32(-1, Int.MIN_VALUE, 6, 5, 4, 3, 2, 1)
    val src = mm256Set1Ps(-1.0f)
    val r = mm256MaskCvtepu32Ps(src, 0xAAu, a)
    val e = mm256SetPs(4294967296.0f, -1.0f, 6.0f, -1.0f, 4.0f, -1.0f, 2.0f, -1.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzCvtepu32Ps() {
    val a = mm256SetEpi32(-1, Int.MIN_VALUE, 6, 5, 4, 3, 2, 1)
    val r = mm256MaskzCvtepu32Ps(0x55u, a)
    val e = mm256SetPs(0.0f, 2147483648.0f, 0.0f, 5.0f, 0.0f, 3.0f, 0.0f, 1.0f)
    assertEqM256(r, e)
}

public fun testMmCvtepu32Ps() {
    val a = mmSetEpi32(-1, Int.MIN_VALUE, 1, 0)
    val r = mmCvtepu32Ps(a)
    val e = mmSetPs(4294967296.0f, 2147483648.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtepu32Ps() {
    val a = mmSetEpi32(-1, Int.MIN_VALUE, 2, 1)
    val src = mmSet1Ps(-1.0f)
    val r = mmMaskCvtepu32Ps(src, 0xF5u, a)
    val e = mmSetPs(-1.0f, 2147483648.0f, -1.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzCvtepu32Ps() {
    val a = mmSetEpi32(-1, Int.MIN_VALUE, 2, 1)
    val r = mmMaskzCvtepu32Ps(0xFAu, a)
    val e = mmSetPs(4294967296.0f, 0.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMm512Cvtepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi32Epi16(a)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi16((-1).toShort())
    val r = mm512MaskCvtepi32Epi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtepi32Epi16(src, 0xFFu, a)
    val e = mm256SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi32Epi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtepi32Epi16(0xFFu, a)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtepi32Epi16() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256Cvtepi32Epi16(a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi32Epi16() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val src = mmSet1Epi16((-1).toShort())
    val r = mm256MaskCvtepi32Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtepi32Epi16(src, 0xFFu, a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtepi32Epi16() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzCvtepi32Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtepi32Epi16(0xFFu, a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmCvtepi32Epi16() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val r = mmCvtepi32Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi32Epi16() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtepi32Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi32Epi16(src, 0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi32Epi16() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val r = mmMaskzCvtepi32Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi32Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi32Epi8(a)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi8((-1).toByte())
    val r = mm512MaskCvtepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtepi32Epi8(src, 0xFFu, a)
    val e = mmSetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtepi32Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm256Cvtepi32Epi8() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256Cvtepi32Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi32Epi8() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val src = mmSet1Epi8(0)
    val r = mm256MaskCvtepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtepi32Epi8(src, 0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtepi32Epi8() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzCvtepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtepi32Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmCvtepi32Epi8() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val r = mmCvtepi32Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi32Epi8() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val src = mmSet1Epi8(0)
    val r = mmMaskCvtepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi32Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi32Epi8() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val r = mmMaskzCvtepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi32Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtsepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MAX_VALUE)
    val r = mm512Cvtsepi32Epi16(a)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtsepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MAX_VALUE)
    val src = mm256Set1Epi16((-1).toShort())
    val r = mm512MaskCvtsepi32Epi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtsepi32Epi16(src, 0xFFu, a)
    val e = mm256SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 8, 9, 10, 11, 12, 13, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtsepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MAX_VALUE)
    val r = mm512MaskzCvtsepi32Epi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtsepi32Epi16(0xFFu, a)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtsepi32Epi16() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256Cvtsepi32Epi16(a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi32Epi16() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val src = mmSet1Epi16((-1).toShort())
    val r = mm256MaskCvtsepi32Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtsepi32Epi16(src, 0xFFu, a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtsepi32Epi16() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzCvtsepi32Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtsepi32Epi16(0xFFu, a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmCvtsepi32Epi16() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val r = mmCvtsepi32Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi32Epi16() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtsepi32Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtsepi32Epi16(src, 0xFFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtsepi32Epi16() {
    val a = mmSetEpi32(4, 5, 6, 7)
    val r = mmMaskzCvtsepi32Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtsepi32Epi16(0xFFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtsepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MAX_VALUE)
    val r = mm512Cvtsepi32Epi8(a)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MAX_VALUE)
    val src = mmSet1Epi8((-1).toByte())
    val r = mm512MaskCvtsepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtsepi32Epi8(src, 0xFFu, a)
    val e = mmSetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 8, 9, 10, 11, 12, 13, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtsepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MAX_VALUE)
    val r = mm512MaskzCvtsepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtsepi32Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256Cvtsepi32Epi8() {
    val a = mm256SetEpi32(9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256Cvtsepi32Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi32Epi8() {
    val a = mm256SetEpi32(9, 10, 11, 12, 13, 14, 15, 16)
    val src = mmSet1Epi8(0)
    val r = mm256MaskCvtsepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtsepi32Epi8(src, 0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtsepi32Epi8() {
    val a = mm256SetEpi32(9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256MaskzCvtsepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtsepi32Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM128i(r1, e)
}

public fun testMmCvtsepi32Epi8() {
    val a = mmSetEpi32(13, 14, 15, 16)
    val r = mmCvtsepi32Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 14, 15, 16)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi32Epi8() {
    val a = mmSetEpi32(13, 14, 15, 16)
    val src = mmSet1Epi8(0)
    val r = mmMaskCvtsepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtsepi32Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 14, 15, 16)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtsepi32Epi8() {
    val a = mmSetEpi32(13, 14, 15, 16)
    val r = mmMaskzCvtsepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtsepi32Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 14, 15, 16)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtusepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MIN_VALUE)
    val r = mm512Cvtusepi32Epi16(a)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, (-1).toShort(), (-1).toShort())
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtusepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MIN_VALUE)
    val src = mm256Set1Epi16((-1).toShort())
    val r = mm512MaskCvtusepi32Epi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtusepi32Epi16(src, 0xFFu, a)
    val e = mm256SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 8, 9, 10, 11, 12, 13, (-1).toShort(), (-1).toShort())
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtusepi32Epi16() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MIN_VALUE)
    val r = mm512MaskzCvtusepi32Epi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtusepi32Epi16(0xFFu, a)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, (-1).toShort(), (-1).toShort())
    assertEqM256i(r1, e)
}

public fun testMm256Cvtusepi32Epi16() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256Cvtusepi32Epi16(a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi32Epi16() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mmSet1Epi16(0)
    val r = mm256MaskCvtusepi32Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtusepi32Epi16(src, 0xFFu, a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtusepi32Epi16() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzCvtusepi32Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtusepi32Epi16(0xFFu, a)
    val e = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM128i(r1, e)
}

public fun testMmCvtusepi32Epi16() {
    val a = mmSetEpi32(5, 6, 7, 8)
    val r = mmCvtusepi32Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi32Epi16() {
    val a = mmSetEpi32(5, 6, 7, 8)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtusepi32Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtusepi32Epi16(src, 0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 5, 6, 7, 8)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtusepi32Epi16() {
    val a = mmSetEpi32(5, 6, 7, 8)
    val r = mmMaskzCvtusepi32Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtusepi32Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 5, 6, 7, 8)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtusepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MIN_VALUE)
    val r = mm512Cvtusepi32Epi8(a)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, (-1).toByte(), (-1).toByte())
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MIN_VALUE)
    val src = mmSet1Epi8((-1).toByte())
    val r = mm512MaskCvtusepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtusepi32Epi8(src, 0xFFu, a)
    val e = mmSetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 8, 9, 10, 11, 12, 13, (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtusepi32Epi8() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, Int.MIN_VALUE, Int.MIN_VALUE)
    val r = mm512MaskzCvtusepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtusepi32Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm256Cvtusepi32Epi8() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, Int.MAX_VALUE)
    val r = mm256Cvtusepi32Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi32Epi8() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, Int.MAX_VALUE)
    val src = mmSet1Epi8(0)
    val r = mm256MaskCvtusepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtusepi32Epi8(src, 0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtusepi32Epi8() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, Int.MAX_VALUE)
    val r = mm256MaskzCvtusepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtusepi32Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMmCvtusepi32Epi8() {
    val a = mmSetEpi32(5, 6, 7, Int.MAX_VALUE)
    val r = mmCvtusepi32Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 7, UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi32Epi8() {
    val a = mmSetEpi32(5, 6, 7, Int.MAX_VALUE)
    val src = mmSet1Epi8(0)
    val r = mmMaskCvtusepi32Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtusepi32Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 7, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtusepi32Epi8() {
    val a = mmSetEpi32(5, 6, 7, Int.MAX_VALUE)
    val r = mmMaskzCvtusepi32Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtusepi32Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 7, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMm512CvtRoundpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvtRoundpsEpi32(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM512i(r, e)
    val r1 = mm512CvtRoundpsEpi32(a, MM_FROUND_TO_NEG_INF or MM_FROUND_NO_EXC)
    val e1 = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e1)
}

public fun testMm512MaskCvtRoundpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvtRoundpsEpi32(src, 0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtRoundpsEpi32(src, 0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtRoundpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvtRoundpsEpi32(0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtRoundpsEpi32(0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512CvtRoundpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvtRoundpsEpu32(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 8, 10, 10, 12, 12, 14, 14, 16)
    assertEqM512i(r, e)
    val r1 = mm512CvtRoundpsEpu32(a, MM_FROUND_TO_NEG_INF or MM_FROUND_NO_EXC)
    val e1 = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e1)
}

public fun testMm512MaskCvtRoundpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvtRoundpsEpu32(src, 0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtRoundpsEpu32(src, 0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtRoundpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvtRoundpsEpu32(0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtRoundpsEpu32(0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512CvtRoundepi32Ps() {
    val a = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    val r = mm512CvtRoundepi32Ps(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, -2.0f, 2.0f, -4.0f, 4.0f, -6.0f, 6.0f, -8.0f, 8.0f, 10.0f, 10.0f, 12.0f, 12.0f, 14.0f, 14.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtRoundepi32Ps() {
    val a = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    val src = mm512Set1Ps(0.0f)
    val r = mm512MaskCvtRoundepi32Ps(src, 0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtRoundepi32Ps(src, 0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, -2.0f, 2.0f, -4.0f, 4.0f, -6.0f, 6.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCvtRoundepi32Ps() {
    val a = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    val r = mm512MaskzCvtRoundepi32Ps(0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCvtRoundepi32Ps(0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, -2.0f, 2.0f, -4.0f, 4.0f, -6.0f, 6.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512CvtRoundepu32Ps() {
    val a = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    val r = mm512CvtRoundepu32Ps(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 4294967300.0f, 2.0f, 4294967300.0f, 4.0f, 4294967300.0f, 6.0f, 4294967300.0f, 8.0f, 10.0f, 10.0f, 12.0f, 12.0f, 14.0f, 14.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtRoundepu32Ps() {
    val a = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    val src = mm512Set1Ps(0.0f)
    val r = mm512MaskCvtRoundepu32Ps(src, 0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtRoundepu32Ps(src, 0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 4294967300.0f, 2.0f, 4294967300.0f, 4.0f, 4294967300.0f, 6.0f, 4294967300.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCvtRoundepu32Ps() {
    val a = mm512SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8, 8, 10, 10, 12, 12, 14, 14, 16)
    val r = mm512MaskzCvtRoundepu32Ps(0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCvtRoundepu32Ps(0xFFu, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPs(0.0f, 4294967300.0f, 2.0f, 4294967300.0f, 4.0f, 4294967300.0f, 6.0f, 4294967300.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512CvtRoundpsPh() {
    val a = mm512Set1Ps(1.0f)
    val r = mm512CvtRoundpsPh(a, MM_FROUND_NO_EXC)
    val e = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtRoundpsPh() {
    val a = mm512Set1Ps(1.0f)
    val src = mm256Set1Epi16(0)
    val r = mm512MaskCvtRoundpsPh(src, 0u, a, MM_FROUND_NO_EXC)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtRoundpsPh(src, 0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtRoundpsPh() {
    val a = mm512Set1Ps(1.0f)
    val r = mm512MaskzCvtRoundpsPh(0u, a, MM_FROUND_NO_EXC)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtRoundpsPh(0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskCvtRoundpsPh() {
    val a = mm256Set1Ps(1.0f)
    val src = mmSet1Epi16(0)
    val r = mm256MaskCvtRoundpsPh(src, 0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtRoundpsPh(src, 0xFFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtRoundpsPh() {
    val a = mm256Set1Ps(1.0f)
    val r = mm256MaskzCvtRoundpsPh(0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtRoundpsPh(0xFFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    assertEqM128i(r1, e)
}

public fun testMmMaskCvtRoundpsPh() {
    val a = mmSet1Ps(1.0f)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtRoundpsPh(src, 0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtRoundpsPh(src, 0xFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtRoundpsPh() {
    val a = mmSet1Ps(1.0f)
    val r = mmMaskzCvtRoundpsPh(0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtRoundpsPh(0xFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 0)
    assertEqM128i(r1, e)
}

public fun testMm512CvtpsPh() {
    val a = mm512Set1Ps(1.0f)
    val r = mm512CvtpsPh(a, MM_FROUND_NO_EXC)
    val e = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtpsPh() {
    val a = mm512Set1Ps(1.0f)
    val src = mm256Set1Epi16(0)
    val r = mm512MaskCvtpsPh(src, 0u, a, MM_FROUND_NO_EXC)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtpsPh(src, 0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtpsPh() {
    val a = mm512Set1Ps(1.0f)
    val r = mm512MaskzCvtpsPh(0u, a, MM_FROUND_NO_EXC)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtpsPh(0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskCvtpsPh() {
    val a = mm256Set1Ps(1.0f)
    val src = mmSet1Epi16(0)
    val r = mm256MaskCvtpsPh(src, 0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtpsPh(src, 0xFFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtpsPh() {
    val a = mm256Set1Ps(1.0f)
    val r = mm256MaskzCvtpsPh(0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtpsPh(0xFFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    assertEqM128i(r1, e)
}

public fun testMmMaskCvtpsPh() {
    val a = mmSet1Ps(1.0f)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtpsPh(src, 0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtpsPh(src, 0xFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtpsPh() {
    val a = mmSet1Ps(1.0f)
    val r = mmMaskzCvtpsPh(0u, a, MM_FROUND_CUR_DIRECTION)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtpsPh(0xFu, a, MM_FROUND_CUR_DIRECTION)
    val e = mmSetrEpi64x(4323521613979991040, 0)
    assertEqM128i(r1, e)
}

public fun testMm512CvtRoundphPs() {
    val a = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    val r = mm512CvtRoundphPs(a, MM_FROUND_NO_EXC)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtRoundphPs() {
    val a = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    val src = mm512Set1Ps(0.0f)
    val r = mm512MaskCvtRoundphPs(src, 0u, a, MM_FROUND_NO_EXC)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtRoundphPs(src, 0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCvtRoundphPs() {
    val a = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    val r = mm512MaskzCvtRoundphPs(0u, a, MM_FROUND_NO_EXC)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCvtRoundphPs(0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512CvtphPs() {
    val a = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    val r = mm512CvtphPs(a)
    val e = mm512Set1Ps(1.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtphPs() {
    val a = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    val src = mm512Set1Ps(0.0f)
    val r = mm512MaskCvtphPs(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtphPs(src, 0xFFu, a)
    val e = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCvtphPs() {
    val a = mm256SetrEpi64x(4323521613979991040L, 4323521613979991040L, 4323521613979991040L, 4323521613979991040L)
    val r = mm512MaskzCvtphPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCvtphPs(0xFFu, a)
    val e = mm512SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskCvtphPs() {
    val a = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    val src = mm256Set1Ps(0.0f)
    val r = mm256MaskCvtphPs(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskCvtphPs(src, 0xFFu, a)
    val e = mm256SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzCvtphPs() {
    val a = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    val r = mm256MaskzCvtphPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzCvtphPs(0xFFu, a)
    val e = mm256SetrPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskCvtphPs() {
    val a = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    val src = mmSet1Ps(0.0f)
    val r = mmMaskCvtphPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskCvtphPs(src, 0xFu, a)
    val e = mmSetrPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzCvtphPs() {
    val a = mmSetrEpi64x(4323521613979991040, 4323521613979991040)
    val r = mmMaskzCvtphPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzCvtphPs(0xFu, a)
    val e = mmSetrPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMm512CvttRoundpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvttRoundpsEpi32(a, MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvttRoundpsEpi32(src, 0u, a, MM_FROUND_NO_EXC)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvttRoundpsEpi32(src, 0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvttRoundpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvttRoundpsEpi32(0u, a, MM_FROUND_NO_EXC)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvttRoundpsEpi32(0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512CvttRoundpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvttRoundpsEpu32(a, MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttRoundpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvttRoundpsEpu32(src, 0u, a, MM_FROUND_NO_EXC)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvttRoundpsEpu32(src, 0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvttRoundpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvttRoundpsEpu32(0u, a, MM_FROUND_NO_EXC)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvttRoundpsEpu32(0xFFu, a, MM_FROUND_NO_EXC)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512CvttpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvttpsEpi32(a)
    val e = mm512SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvttpsEpi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvttpsEpi32(src, 0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvttpsEpi32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvttpsEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvttpsEpi32(0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvttpsEpi32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm256Set1Epi32(0)
    val r = mm256MaskCvttpsEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvttpsEpi32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvttpsEpi32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm256MaskzCvttpsEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvttpsEpi32(0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvttpsEpi32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvttpsEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvttpsEpi32(src, 0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvttpsEpi32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val r = mmMaskzCvttpsEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvttpsEpi32(0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512CvttpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512CvttpsEpu32(a)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvttpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm512Set1Epi32(0)
    val r = mm512MaskCvttpsEpu32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvttpsEpu32(src, 0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvttpsEpu32() {
    val a = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm512MaskzCvttpsEpu32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvttpsEpu32(0xFFu, a)
    val e = mm512SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256CvttpsEpu32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm256CvttpsEpu32(a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvttpsEpu32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val src = mm256Set1Epi32(0)
    val r = mm256MaskCvttpsEpu32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvttpsEpu32(src, 0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvttpsEpu32() {
    val a = mm256SetPs(8.0f, 9.5f, 10.0f, 11.5f, 12.0f, 13.5f, 14.0f, 15.5f)
    val r = mm256MaskzCvttpsEpu32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvttpsEpu32(0xFFu, a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmCvttpsEpu32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val r = mmCvttpsEpu32(a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttpsEpu32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvttpsEpu32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvttpsEpu32(src, 0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvttpsEpu32() {
    val a = mmSetPs(12.0f, 13.5f, 14.0f, 15.5f)
    val r = mmMaskzCvttpsEpu32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvttpsEpu32(0xFu, a)
    val e = mmSetEpi32(12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512I32gatherPs() {
    val localStorageL51489C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 120, 128, 136, 144, 152, 160, 168, 176)
    val r = mm512I32gatherPs(index, localStorageL51489C8.elementConstPtr(0), 4)
    assertEqM512(r, mm512SetrPs(0.0f, 16.0f, 32.0f, 48.0f, 64.0f, 80.0f, 96.0f, 112.0f, 120.0f, 128.0f, 136.0f, 144.0f, 152.0f, 160.0f, 168.0f, 176.0f))
}

public fun testMm512MaskI32gatherPs() {
    val localStorageL51502C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    val src = mm512Set1Ps(2.0f)
    val mask = 0xAAAA
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 120, 128, 136, 144, 152, 160, 168, 176)
    // A multiplier of 4 is word-addressing
    val r = mm512MaskI32gatherPs(src, mask, index, localStorageL51502C8.elementConstPtr(0), 4)
    assertEqM512(r, mm512SetrPs(2.0f, 16.0f, 2.0f, 48.0f, 2.0f, 80.0f, 2.0f, 112.0f, 2.0f, 128.0f, 2.0f, 144.0f, 2.0f, 160.0f, 2.0f, 176.0f))
}

public fun testMm512I32gatherEpi32() {
    val localStorageL51517C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 120, 128, 136, 144, 152, 160, 168, 176)
    val r = mm512I32gatherEpi32(index, localStorageL51517C8.elementConstPtr(0), 4)
    assertEqM512i(r, mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 120, 128, 136, 144, 152, 160, 168, 176))
}

public fun testMm512MaskI32gatherEpi32() {
    val localStorageL51530C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    val src = mm512Set1Epi32(2)
    val mask = 0xAAAA
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240)
    // A multiplier of 4 is word-addressing
    val r = mm512MaskI32gatherEpi32(src, mask, index, localStorageL51530C8.elementConstPtr(0), 4)
    assertEqM512i(r, mm512SetrEpi32(2, 16, 2, 48, 2, 80, 2, 112, 2, 144, 2, 176, 2, 208, 2, 240))
}

public fun testMm512I32scatterPs() {
    val arr = MutableList(256) { 0.0f }
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240)
    val src = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    // A multiplier of 4 is word-addressing
    run {
        mm512I32scatterPs(arr.asMutPtr(), index, src, 4)
    }
    val expected = MutableList(256) { 0.0f }
    for (i in 0 until 16) {
        expected[i * 16] = (i + 1).toFloat()
    }
    check(arr == expected)
}

public fun testMm512MaskI32scatterPs() {
    val arr = MutableList(256) { 0.0f }
    val mask = 0xAAAA
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240)
    val src = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    // A multiplier of 4 is word-addressing
    run {
        mm512MaskI32scatterPs(arr.asMutPtr(), mask, index, src, 4)
    }
    val expected = MutableList(256) { 0.0f }
    for (i in 0 until 8) {
        expected[i * 32 + 16] = 2.0f * (i + 1).toFloat()
    }
    check(arr == expected)
}

public fun testMm512I32scatterEpi32() {
    val localStorageL51587C8 = AddressableArrayLocal.fromIntArray(IntArray(256) { 0 })
    var arr by localStorageL51587C8
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240)
    val src = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    // A multiplier of 4 is word-addressing
    run {
        mm512I32scatterEpi32(localStorageL51587C8.elementMutPtr(0), index, src, 4)
    }
    val expected = IntArray(256) { 0 }
    for (i in 0 until 16) {
        expected[i * 16] = (i + 1).toInt()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512MaskI32scatterEpi32() {
    val localStorageL51606C8 = AddressableArrayLocal.fromIntArray(IntArray(256) { 0 })
    var arr by localStorageL51606C8
    val mask = 0xAAAA
    val index = mm512SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240)
    val src = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    // A multiplier of 4 is word-addressing
    run {
        mm512MaskI32scatterEpi32(localStorageL51606C8.elementMutPtr(0), mask, index, src, 4)
    }
    val expected = IntArray(256) { 0 }
    for (i in 0 until 8) {
        expected[i * 32 + 16] = 2 * (i + 1).toInt()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512CmpltPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val m = mm512CmpltPsMask(a, b)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpltPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val mask = 0x6666
    val r = mm512MaskCmpltPsMask(mask, a, b)
    check(r == 0x404u.toUShort())
}

public fun testMm512CmpnltPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    check(mm512CmpnltPsMask(a, b) == mm512CmpltPsMask(a, b).inv())
}

public fun testMm512MaskCmpnltPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val mask = 0x7A7A
    check(mm512MaskCmpnltPsMask(mask, a, b) == 0x7A7Au.toUShort())
}

public fun testMm512CmpnlePsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val m = mm512CmpnlePsMask(b, a)
    check(m == 0xD0Du.toUShort())
}

public fun testMm512MaskCmpnlePsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val mask = 0x6666
    val r = mm512MaskCmpnlePsMask(mask, b, a)
    check(r == 0x404u.toUShort())
}

public fun testMm512CmplePsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    check(mm512CmplePsMask(a, b) == 0x2525u.toUShort())
}

public fun testMm512MaskCmplePsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val mask = 0x7A7A
    check(mm512MaskCmplePsMask(mask, a, b) == 0x2020u.toUShort())
}

public fun testMm512CmpeqPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val b = mm512SetPs(0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val m = mm512CmpeqPsMask(b, a)
    check(m == 0xCDCDu.toUShort())
}

public fun testMm512MaskCmpeqPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val b = mm512SetPs(0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val mask = 0x7A7A
    val r = mm512MaskCmpeqPsMask(mask, b, a)
    check(r == 0x4848u.toUShort())
}

public fun testMm512CmpneqPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val b = mm512SetPs(0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val m = mm512CmpneqPsMask(b, a)
    check(m == 0x3232u.toUShort())
}

public fun testMm512MaskCmpneqPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val b = mm512SetPs(0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f, 0.0f, 1.0f, 13.0f, 42.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), Float.NaN, -100.0f)
    val mask = 0x7A7A
    val r = mm512MaskCmpneqPsMask(mask, b, a)
    check(r == 0x3232u.toUShort())
}

public fun testMm512CmpPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val m = mm512CmpPsMask(a, b, CMP_LT_OQ)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val mask = 0x6666
    val r = mm512MaskCmpPsMask(mask, a, b, CMP_LT_OQ)
    check(r == 0x404u.toUShort())
}

public fun testMm256CmpPsMask() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm256Set1Ps(-1.0f)
    val m = mm256CmpPsMask(a, b, CMP_LT_OQ)
    check(m == 0x5u.toUByte())
}

public fun testMm256MaskCmpPsMask() {
    val a = mm256SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm256Set1Ps(-1.0f)
    val mask = 0x66
    val r = mm256MaskCmpPsMask(mask, a, b, CMP_LT_OQ)
    check(r == 0x4u.toUByte())
}

public fun testMmCmpPsMask() {
    val a = mmSetPs(0.0f, 1.0f, -1.0f, 13.0f)
    val b = mmSet1Ps(1.0f)
    val m = mmCmpPsMask(a, b, CMP_LT_OQ)
    check(m == 0xAu.toUByte())
}

public fun testMmMaskCmpPsMask() {
    val a = mmSetPs(0.0f, 1.0f, -1.0f, 13.0f)
    val b = mmSet1Ps(1.0f)
    val mask = 0xFF
    val r = mmMaskCmpPsMask(mask, a, b, CMP_LT_OQ)
    check(r == 0xAu.toUByte())
}

public fun testMm512CmpRoundPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val m = mm512CmpRoundPsMask(a, b, CMP_LT_OQ, MM_FROUND_CUR_DIRECTION)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpRoundPsMask() {
    val a = mm512SetPs(0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f, 0.0f, 1.0f, -1.0f, 13.0f, Float.MAX_VALUE, (-Float.MAX_VALUE), 100.0f, -100.0f)
    val b = mm512Set1Ps(-1.0f)
    val mask = 0x6666
    val r = mm512MaskCmpRoundPsMask(mask, a, b, CMP_LT_OQ, MM_FROUND_CUR_DIRECTION)
    check(r == 0x404u.toUShort())
}

public fun testMm512CmpordPsMask() {
    val a = mm512SetPs(Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, -1.0f, Float.NaN, 0.0f, Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, 1.0f, Float.NaN, 2.0f)
    val b = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 0.0f, Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 2.0f)
    val m = mm512CmpordPsMask(a, b)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpordPsMask() {
    val a = mm512SetPs(Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, -1.0f, Float.NaN, 0.0f, Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, 1.0f, Float.NaN, 2.0f)
    val b = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 0.0f, Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 2.0f)
    val mask = 0xC3C3
    val m = mm512MaskCmpordPsMask(mask, a, b)
    check(m == 0x101u.toUShort())
}

public fun testMm512CmpunordPsMask() {
    val a = mm512SetPs(Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, -1.0f, Float.NaN, 0.0f, Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, 1.0f, Float.NaN, 2.0f)
    val b = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 0.0f, Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 2.0f)
    val m = mm512CmpunordPsMask(a, b)
    check(m == 0xFAFAu.toUShort())
}

public fun testMm512MaskCmpunordPsMask() {
    val a = mm512SetPs(Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, -1.0f, Float.NaN, 0.0f, Float.NaN, Float.MAX_VALUE, Float.NaN, (-Float.MAX_VALUE), Float.NaN, 1.0f, Float.NaN, 2.0f)
    val b = mm512SetPs(Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 0.0f, Float.NaN, Float.NaN, Float.NaN, Float.NaN, (-Float.MAX_VALUE), Float.MAX_VALUE, -1.0f, 2.0f)
    val mask = 0xF0F
    val m = mm512MaskCmpunordPsMask(mask, a, b)
    check(m == 0xA0Au.toUShort())
}

public fun testMmCmpSsMask() {
    val a = mmSetrPs(2.0f, 1.0f, 1.0f, 1.0f)
    val b = mmSetrPs(1.0f, 2.0f, 2.0f, 2.0f)
    val m = mmCmpSsMask(a, b, CMP_GE_OS)
    check(m == 1u.toUByte())
}

public fun testMmMaskCmpSsMask() {
    val a = mmSetrPs(2.0f, 1.0f, 1.0f, 1.0f)
    val b = mmSetrPs(1.0f, 2.0f, 2.0f, 2.0f)
    val m = mmMaskCmpSsMask(0x2u, a, b, CMP_GE_OS)
    check(m == 0u.toUByte())
    val m1 = mmMaskCmpSsMask(0x1u, a, b, CMP_GE_OS)
    check(m1 == 1u.toUByte())
}

public fun testMmCmpRoundSsMask() {
    val a = mmSetrPs(2.0f, 1.0f, 1.0f, 1.0f)
    val b = mmSetrPs(1.0f, 2.0f, 2.0f, 2.0f)
    val m = mmCmpRoundSsMask(a, b, CMP_GE_OS, MM_FROUND_CUR_DIRECTION)
    check(m == 1u.toUByte())
}

public fun testMmMaskCmpRoundSsMask() {
    val a = mmSetrPs(2.0f, 1.0f, 1.0f, 1.0f)
    val b = mmSetrPs(1.0f, 2.0f, 2.0f, 2.0f)
    val m = mmMaskCmpRoundSsMask(0x2u, a, b, CMP_GE_OS, MM_FROUND_CUR_DIRECTION)
    check(m == 0u.toUByte())
    val m1 = mmMaskCmpRoundSsMask(0x1u, a, b, CMP_GE_OS, MM_FROUND_CUR_DIRECTION)
    check(m1 == 1u.toUByte())
}

public fun testMmCmpSdMask() {
    val a = mmSetrPd(2.0, 1.0)
    val b = mmSetrPd(1.0, 2.0)
    val m = mmCmpSdMask(a, b, CMP_GE_OS)
    check(m == 1u.toUByte())
}

public fun testMmMaskCmpSdMask() {
    val a = mmSetrPd(2.0, 1.0)
    val b = mmSetrPd(1.0, 2.0)
    val m = mmMaskCmpSdMask(0x2u, a, b, CMP_GE_OS)
    check(m == 0u.toUByte())
    val m1 = mmMaskCmpSdMask(0x1u, a, b, CMP_GE_OS)
    check(m1 == 1u.toUByte())
}

public fun testMmCmpRoundSdMask() {
    val a = mmSetrPd(2.0, 1.0)
    val b = mmSetrPd(1.0, 2.0)
    val m = mmCmpRoundSdMask(a, b, CMP_GE_OS, MM_FROUND_CUR_DIRECTION)
    check(m == 1u.toUByte())
}

public fun testMmMaskCmpRoundSdMask() {
    val a = mmSetrPd(2.0, 1.0)
    val b = mmSetrPd(1.0, 2.0)
    val m = mmMaskCmpRoundSdMask(0x2u, a, b, CMP_GE_OS, MM_FROUND_CUR_DIRECTION)
    check(m == 0u.toUByte())
    val m1 = mmMaskCmpRoundSdMask(0x1u, a, b, CMP_GE_OS, MM_FROUND_CUR_DIRECTION)
    check(m1 == 1u.toUByte())
}

public fun testMm512CmpltEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val m = mm512CmpltEpu32Mask(a, b)
    check(m == 0xCFCFu.toUShort())
}

public fun testMm512MaskCmpltEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    val r = mm512MaskCmpltEpu32Mask(mask, a, b)
    check(r == 0x4A4Au.toUShort())
}

public fun testMm256CmpltEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 101, 100, 99)
    val b = mm256Set1Epi32(1)
    val r = mm256CmpltEpu32Mask(a, b)
    check(r == 0x80u.toUByte())
}

public fun testMm256MaskCmpltEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 101, 100, 99)
    val b = mm256Set1Epi32(1)
    val mask = 0xFF
    val r = mm256MaskCmpltEpu32Mask(mask, a, b)
    check(r == 0x80u.toUByte())
}

public fun testMmCmpltEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val r = mmCmpltEpu32Mask(a, b)
    check(r == 0x8u.toUByte())
}

public fun testMmMaskCmpltEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val mask = 0xFF
    val r = mmMaskCmpltEpu32Mask(mask, a, b)
    check(r == 0x8u.toUByte())
}

public fun testMm512CmpgtEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val m = mm512CmpgtEpu32Mask(b, a)
    check(m == 0xCFCFu.toUShort())
}

public fun testMm512MaskCmpgtEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    val r = mm512MaskCmpgtEpu32Mask(mask, b, a)
    check(r == 0x4A4Au.toUShort())
}

public fun testMm256CmpgtEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 99, 100, 101)
    val b = mm256Set1Epi32(1)
    val r = mm256CmpgtEpu32Mask(a, b)
    check(r == 0x3Fu.toUByte())
}

public fun testMm256MaskCmpgtEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 99, 100, 101)
    val b = mm256Set1Epi32(1)
    val mask = 0xFF
    val r = mm256MaskCmpgtEpu32Mask(mask, a, b)
    check(r == 0x3Fu.toUByte())
}

public fun testMmCmpgtEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val r = mmCmpgtEpu32Mask(a, b)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpgtEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val mask = 0xFF
    val r = mmMaskCmpgtEpu32Mask(mask, a, b)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpleEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    check(mm512CmpleEpu32Mask(a, b) == mm512CmpgtEpu32Mask(a, b).inv())
}

public fun testMm512MaskCmpleEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    check(mm512MaskCmpleEpu32Mask(mask, a, b) == 0x7A7Au.toUShort())
}

public fun testMm256CmpleEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 200, 100, 101)
    val b = mm256Set1Epi32(1)
    val r = mm256CmpleEpu32Mask(a, b)
    check(r == 0xC0u.toUByte())
}

public fun testMm256MaskCmpleEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 200, 100, 101)
    val b = mm256Set1Epi32(1)
    val mask = 0xFF
    val r = mm256MaskCmpleEpu32Mask(mask, a, b)
    check(r == 0xC0u.toUByte())
}

public fun testMmCmpleEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val r = mmCmpleEpu32Mask(a, b)
    check(r == 0xCu.toUByte())
}

public fun testMmMaskCmpleEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val mask = 0xFF
    val r = mmMaskCmpleEpu32Mask(mask, a, b)
    check(r == 0xCu.toUByte())
}

public fun testMm512CmpgeEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    check(mm512CmpgeEpu32Mask(a, b) == mm512CmpltEpu32Mask(a, b).inv())
}

public fun testMm512MaskCmpgeEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    check(mm512MaskCmpgeEpu32Mask(mask, a, b) == 0x3030u.toUShort())
}

public fun testMm256CmpgeEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 300, 100, 200)
    val b = mm256Set1Epi32(1)
    val r = mm256CmpgeEpu32Mask(a, b)
    check(r == 0x7Fu.toUByte())
}

public fun testMm256MaskCmpgeEpu32Mask() {
    val a = mm256SetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, 300, 100, 200)
    val b = mm256Set1Epi32(1)
    val mask = 0xFF
    val r = mm256MaskCmpgeEpu32Mask(mask, a, b)
    check(r == 0x7Fu.toUByte())
}

public fun testMmCmpgeEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val r = mmCmpgeEpu32Mask(a, b)
    check(r == 0x7u.toUByte())
}

public fun testMmMaskCmpgeEpu32Mask() {
    val a = mmSetEpi32(0, 1, 2, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(1)
    val mask = 0xFF
    val r = mmMaskCmpgeEpu32Mask(mask, a, b)
    check(r == 0x7u.toUByte())
}

public fun testMm512CmpeqEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm512CmpeqEpu32Mask(b, a)
    check(m == 0xCFCFu.toUShort())
}

public fun testMm512MaskCmpeqEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0x7A7A
    val r = mm512MaskCmpeqEpu32Mask(mask, b, a)
    check(r == 0x4A4Au.toUShort())
}

public fun testMm256CmpeqEpu32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm256CmpeqEpu32Mask(b, a)
    check(m == 0xCFu.toUByte())
}

public fun testMm256MaskCmpeqEpu32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0x7A
    val r = mm256MaskCmpeqEpu32Mask(mask, b, a)
    check(r == 0x4Au.toUByte())
}

public fun testMmCmpeqEpu32Mask() {
    val a = mmSetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt())
    val b = mmSetEpi32(0, 1, 13, 42)
    val m = mmCmpeqEpu32Mask(b, a)
    check(m == 0xCu.toUByte())
}

public fun testMmMaskCmpeqEpu32Mask() {
    val a = mmSetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt())
    val b = mmSetEpi32(0, 1, 13, 42)
    val mask = 0xFF
    val r = mmMaskCmpeqEpu32Mask(mask, b, a)
    check(r == 0xCu.toUByte())
}

public fun testMm512CmpneqEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm512CmpneqEpu32Mask(b, a)
    check(m == mm512CmpeqEpu32Mask(b, a).inv())
}

public fun testMm512MaskCmpneqEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, -100, 100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0x7A7A
    val r = mm512MaskCmpneqEpu32Mask(mask, b, a)
    check(r == 0x3232u.toUShort())
}

public fun testMm256CmpneqEpu32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val r = mm256CmpneqEpu32Mask(b, a)
    check(r == 0x30u.toUByte())
}

public fun testMm256MaskCmpneqEpu32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val mask = 0xFF
    val r = mm256MaskCmpneqEpu32Mask(mask, b, a)
    check(r == 0x30u.toUByte())
}

public fun testMmCmpneqEpu32Mask() {
    val a = mmSetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt())
    val b = mmSetEpi32(0, 1, 13, 42)
    val r = mmCmpneqEpu32Mask(b, a)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpneqEpu32Mask() {
    val a = mmSetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt())
    val b = mmSetEpi32(0, 1, 13, 42)
    val mask = 0xFF
    val r = mmMaskCmpneqEpu32Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val m = mm512CmpEpu32Mask(a, b, MM_CMPINT_LT)
    check(m == 0xCFCFu.toUShort())
}

public fun testMm512MaskCmpEpu32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    val r = mm512MaskCmpEpu32Mask(mask, a, b, MM_CMPINT_LT)
    check(r == 0x4A4Au.toUShort())
}

public fun testMm256CmpEpu32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val m = mm256CmpEpu32Mask(a, b, MM_CMPINT_LT)
    check(m == 0xCFu.toUByte())
}

public fun testMm256MaskCmpEpu32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val mask = 0xFF
    val r = mm256MaskCmpEpu32Mask(mask, a, b, MM_CMPINT_LT)
    check(r == 0xCFu.toUByte())
}

public fun testMmCmpEpu32Mask() {
    val a = mmSetEpi32(0, 1, -1, Int.MAX_VALUE)
    val b = mmSet1Epi32(1)
    val m = mmCmpEpu32Mask(a, b, MM_CMPINT_LT)
    check(m == 0x8u.toUByte())
}

public fun testMmMaskCmpEpu32Mask() {
    val a = mmSetEpi32(0, 1, -1, Int.MAX_VALUE)
    val b = mmSet1Epi32(1)
    val mask = 0xFF
    val r = mmMaskCmpEpu32Mask(mask, a, b, MM_CMPINT_LT)
    check(r == 0x8u.toUByte())
}

public fun testMm512CmpltEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val m = mm512CmpltEpi32Mask(a, b)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpltEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x6666
    val r = mm512MaskCmpltEpi32Mask(mask, a, b)
    check(r == 0x404u.toUShort())
}

public fun testMm256CmpltEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 101, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val r = mm256CmpltEpi32Mask(a, b)
    check(r == 0x5u.toUByte())
}

public fun testMm256MaskCmpltEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 101, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val mask = 0xFF
    val r = mm256MaskCmpltEpi32Mask(mask, a, b)
    check(r == 0x5u.toUByte())
}

public fun testMmCmpltEpi32Mask() {
    val a = mmSetEpi32(Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mmSet1Epi32(-1)
    val r = mmCmpltEpi32Mask(a, b)
    check(r == 0x5u.toUByte())
}

public fun testMmMaskCmpltEpi32Mask() {
    val a = mmSetEpi32(Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mmSet1Epi32(-1)
    val mask = 0xFF
    val r = mmMaskCmpltEpi32Mask(mask, a, b)
    check(r == 0x5u.toUByte())
}

public fun testMm512CmpgtEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val m = mm512CmpgtEpi32Mask(b, a)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpgtEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x6666
    val r = mm512MaskCmpgtEpi32Mask(mask, b, a)
    check(r == 0x404u.toUShort())
}

public fun testMm256CmpgtEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val r = mm256CmpgtEpi32Mask(a, b)
    check(r == 0xDAu.toUByte())
}

public fun testMm256MaskCmpgtEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val mask = 0xFF
    val r = mm256MaskCmpgtEpi32Mask(mask, a, b)
    check(r == 0xDAu.toUByte())
}

public fun testMmCmpgtEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSet1Epi32(-1)
    val r = mmCmpgtEpi32Mask(a, b)
    check(r == 0xDu.toUByte())
}

public fun testMmMaskCmpgtEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSet1Epi32(-1)
    val mask = 0xFF
    val r = mmMaskCmpgtEpi32Mask(mask, a, b)
    check(r == 0xDu.toUByte())
}

public fun testMm512CmpleEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    check(mm512CmpleEpi32Mask(a, b) == mm512CmpgtEpi32Mask(a, b).inv())
}

public fun testMm512MaskCmpleEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    check(mm512MaskCmpleEpi32Mask(mask, a, b) == 0x3030u.toUShort())
}

public fun testMm256CmpleEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 200, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val r = mm256CmpleEpi32Mask(a, b)
    check(r == 0x25u.toUByte())
}

public fun testMm256MaskCmpleEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 200, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val mask = 0xFF
    val r = mm256MaskCmpleEpi32Mask(mask, a, b)
    check(r == 0x25u.toUByte())
}

public fun testMmCmpleEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 200)
    val b = mmSet1Epi32(-1)
    val r = mmCmpleEpi32Mask(a, b)
    check(r == 0x2u.toUByte())
}

public fun testMmMaskCmpleEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 200)
    val b = mmSet1Epi32(-1)
    val mask = 0xFF
    val r = mmMaskCmpleEpi32Mask(mask, a, b)
    check(r == 0x2u.toUByte())
}

public fun testMm512CmpgeEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    check(mm512CmpgeEpi32Mask(a, b) == mm512CmpltEpi32Mask(a, b).inv())
}

public fun testMm512MaskCmpgeEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x7A7A
    check(mm512MaskCmpgeEpi32Mask(mask, a, b) == 0x7A7Au.toUShort())
}

public fun testMm256CmpgeEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val r = mm256CmpgeEpi32Mask(a, b)
    check(r == 0xFAu.toUByte())
}

public fun testMm256MaskCmpgeEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt(), Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val mask = 0xFF
    val r = mm256MaskCmpgeEpi32Mask(mask, a, b)
    check(r == 0xFAu.toUByte())
}

public fun testMmCmpgeEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(-1)
    val r = mmCmpgeEpi32Mask(a, b)
    check(r == 0xFu.toUByte())
}

public fun testMmMaskCmpgeEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, UInt.MAX_VALUE.toInt())
    val b = mmSet1Epi32(-1)
    val mask = 0xFF
    val r = mmMaskCmpgeEpi32Mask(mask, a, b)
    check(r == 0xFu.toUByte())
}

public fun testMm512CmpeqEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm512CmpeqEpi32Mask(b, a)
    check(m == 0xCFCFu.toUShort())
}

public fun testMm512MaskCmpeqEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0x7A7A
    val r = mm512MaskCmpeqEpi32Mask(mask, b, a)
    check(r == 0x4A4Au.toUShort())
}

public fun testMm256CmpeqEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm256CmpeqEpi32Mask(b, a)
    check(m == 0xCFu.toUByte())
}

public fun testMm256MaskCmpeqEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0x7A
    val r = mm256MaskCmpeqEpi32Mask(mask, b, a)
    check(r == 0x4Au.toUByte())
}

public fun testMmCmpeqEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSetEpi32(0, 1, 13, 42)
    val m = mmCmpeqEpi32Mask(b, a)
    check(m == 0xCu.toUByte())
}

public fun testMmMaskCmpeqEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSetEpi32(0, 1, 13, 42)
    val mask = 0xFF
    val r = mmMaskCmpeqEpi32Mask(mask, b, a)
    check(r == 0xCu.toUByte())
}

public fun testMm512CmpneqEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm512CmpneqEpi32Mask(b, a)
    check(m == mm512CmpeqEpi32Mask(b, a).inv())
}

public fun testMm512MaskCmpneqEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, -100, 100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val b = mm512SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0x7A7A
    val r = mm512MaskCmpneqEpi32Mask(mask, b, a)
    check(r == 0x3232u.toUShort())
}

public fun testMm256CmpneqEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val m = mm256CmpneqEpi32Mask(b, a)
    check(m == mm256CmpeqEpi32Mask(b, a).inv())
}

public fun testMm256MaskCmpneqEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, -100, 100)
    val b = mm256SetEpi32(0, 1, 13, 42, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val mask = 0xFF
    val r = mm256MaskCmpneqEpi32Mask(mask, b, a)
    check(r == 0x33u.toUByte())
}

public fun testMmCmpneqEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSetEpi32(0, 1, 13, 42)
    val r = mmCmpneqEpi32Mask(b, a)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpneqEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSetEpi32(0, 1, 13, 42)
    val mask = 0xFF
    val r = mmMaskCmpneqEpi32Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val m = mm512CmpEpi32Mask(a, b, MM_CMPINT_LT)
    check(m == 0x505u.toUShort())
}

public fun testMm512MaskCmpEpi32Mask() {
    val a = mm512SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, 0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm512Set1Epi32(-1)
    val mask = 0x6666
    val r = mm512MaskCmpEpi32Mask(mask, a, b, MM_CMPINT_LT)
    check(r == 0x404u.toUShort())
}

public fun testMm256CmpEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val m = mm256CmpEpi32Mask(a, b, MM_CMPINT_LT)
    check(m == 0x5u.toUByte())
}

public fun testMm256MaskCmpEpi32Mask() {
    val a = mm256SetEpi32(0, 1, -1, 13, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100)
    val b = mm256Set1Epi32(-1)
    val mask = 0x66
    val r = mm256MaskCmpEpi32Mask(mask, a, b, MM_CMPINT_LT)
    check(r == 0x4u.toUByte())
}

public fun testMmCmpEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSet1Epi32(1)
    val m = mmCmpEpi32Mask(a, b, MM_CMPINT_LT)
    check(m == 0xAu.toUByte())
}

public fun testMmMaskCmpEpi32Mask() {
    val a = mmSetEpi32(0, 1, -1, 13)
    val b = mmSet1Epi32(1)
    val mask = 0xFF
    val r = mmMaskCmpEpi32Mask(mask, a, b, MM_CMPINT_LT)
    check(r == 0xAu.toUByte())
}

public fun testMm512SetEpi8() {
    val r = mm512Set1Epi8(2)
    assertEqM512i(r, mm512SetEpi8(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))
}

public fun testMm512SetEpi16() {
    val r = mm512Set1Epi16(2)
    assertEqM512i(r, mm512SetEpi16(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))
}

public fun testMm512SetEpi32() {
    val r = mm512SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, mm512SetEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0))
}

public fun testMm512SetrEpi32() {
    val r = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, mm512SetrEpi32(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0))
}

public fun testMm512Set1Epi8() {
    val r = mm512SetEpi8(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, mm512Set1Epi8(2))
}

public fun testMm512Set1Epi16() {
    val r = mm512SetEpi16(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, mm512Set1Epi16(2))
}

public fun testMm512Set1Epi32() {
    val r = mm512SetEpi32(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, mm512Set1Epi32(2))
}

public fun testMm512SetzeroSi512() {
    assertEqM512i(mm512Set1Epi32(0), mm512SetzeroSi512())
}

public fun testMm512SetzeroEpi32() {
    assertEqM512i(mm512Set1Epi32(0), mm512SetzeroEpi32())
}

public fun testMm512SetPs() {
    val r = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, mm512SetPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f))
}

public fun testMm512SetrPs() {
    val r = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    assertEqM512(r, mm512SetrPs(15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f, 0.0f))
}

public fun testMm512Set1Ps() {
    val expected = mm512SetPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM512(expected, mm512Set1Ps(2.0f))
}

public fun testMm512Set4Epi32() {
    val r = mm512SetEpi32(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    assertEqM512i(r, mm512Set4Epi32(4, 3, 2, 1))
}

public fun testMm512Set4Ps() {
    val r = mm512SetPs(4.0f, 3.0f, 2.0f, 1.0f, 4.0f, 3.0f, 2.0f, 1.0f, 4.0f, 3.0f, 2.0f, 1.0f, 4.0f, 3.0f, 2.0f, 1.0f)
    assertEqM512(r, mm512Set4Ps(4.0f, 3.0f, 2.0f, 1.0f))
}

public fun testMm512Setr4Epi32() {
    val r = mm512SetEpi32(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    assertEqM512i(r, mm512Setr4Epi32(1, 2, 3, 4))
}

public fun testMm512Setr4Ps() {
    val r = mm512SetPs(4.0f, 3.0f, 2.0f, 1.0f, 4.0f, 3.0f, 2.0f, 1.0f, 4.0f, 3.0f, 2.0f, 1.0f, 4.0f, 3.0f, 2.0f, 1.0f)
    assertEqM512(r, mm512Setr4Ps(1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMm512SetzeroPs() {
    assertEqM512(mm512SetzeroPs(), mm512Set1Ps(0.0f))
}

public fun testMm512Setzero() {
    assertEqM512(mm512Setzero(), mm512Set1Ps(0.0f))
}

public fun testMm512LoaduPd() {
    val a = listOf(4.0, 3.0, 2.0, 5.0, 8.0, 9.0, 64.0, 50.0)
    val p = a.asPtr()
    val r = mm512LoaduPd(blackBox(p))
    val e = mm512SetrPd(4.0, 3.0, 2.0, 5.0, 8.0, 9.0, 64.0, 50.0)
    assertEqM512d(r, e)
}

public fun testMm512StoreuPd() {
    val a = mm512Set1Pd(9.0)
    val localStorageL52910C8 = AddressableLocal(mm512UndefinedPd(), UnresolvedTypeLayout<M512d>())
    val r by localStorageL52910C8
    run {
        mm512StoreuPd((MutPtr(localStorageL52910C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512d(r, a)
}

public fun testMm512LoaduPs() {
    val a = listOf(4.0, 3.0, 2.0, 5.0, 8.0, 9.0, 64.0, 50.0, -4.0, -3.0, -2.0, -5.0, -8.0, -9.0, -64.0, -50.0)
    val p = a.asPtr()
    val r = mm512LoaduPs(blackBox(p))
    val e = mm512SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f, -4.0f, -3.0f, -2.0f, -5.0f, -8.0f, -9.0f, -64.0f, -50.0f)
    assertEqM512(r, e)
}

public fun testMm512StoreuPs() {
    val a = mm512Set1Ps(9.0f)
    val localStorageL52933C8 = AddressableLocal(mm512UndefinedPs(), UnresolvedTypeLayout<M512>())
    val r by localStorageL52933C8
    run {
        mm512StoreuPs((MutPtr(localStorageL52933C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512(r, a)
}

public fun testMm512MaskLoaduEpi32() {
    val src = mm512Set1Epi32(42)
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskLoaduEpi32(src, m, blackBox(p))
    val e = mm512SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzLoaduEpi32() {
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskzLoaduEpi32(m, blackBox(p))
    val e = mm512SetrEpi32(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm512Set1Epi32(42)
    val a = Align(data = floatArrayOf(1, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f))
    val p = a.data.asPtr()
    val m = 0xE8CA
    val r = mm512MaskLoadEpi32(src, m, blackBox(p))
    val e = mm512SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskzLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f))
    val p = a.data.asPtr()
    val m = 0xE8CA
    val r = mm512MaskzLoadEpi32(m, blackBox(p))
    val e = mm512SetrEpi32(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskStoreuEpi32() {
    val localStorageL52996C8 = AddressableArrayLocal.fromIntArray(IntArray(16) { 42 })
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val m = 0xE8CA
    run {
        mm512MaskStoreuEpi32(localStorageL52996C8.elementMutPtr(0), m, a)
    }
    val e = mm512SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    assertEqM512i(mm512LoaduEpi32(localStorageL52996C8.elementConstPtr(0)), e)
}

public fun testMm512MaskStoreEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(16) { 42.0f })
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val m = 0xE8CA
    run {
        mm512MaskStoreEpi32(r.data.asMutPtr(), m, a)
    }
    val e = mm512SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    assertEqM512i(mm512LoadEpi32(r.data.asPtr()), e)
}

public fun testMm512MaskLoaduEpi64() {
    val src = mm512Set1Epi64(42L)
    val a = longArrayOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm512MaskLoaduEpi64(src, m, blackBox(p))
    val e = mm512SetrEpi64(42L, 2L, 42L, 4L, 42L, 42L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzLoaduEpi64() {
    val a = longArrayOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm512MaskzLoaduEpi64(m, blackBox(p))
    val e = mm512SetrEpi64(0L, 2L, 0L, 4L, 0L, 0L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm512Set1Epi64(42L)
    val a = Align(data = floatArrayOf(1L, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm512MaskLoadEpi64(src, m, blackBox(p))
    val e = mm512SetrEpi64(42L, 2L, 42L, 4L, 42L, 42L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1L, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm512MaskzLoadEpi64(m, blackBox(p))
    val e = mm512SetrEpi64(0L, 2L, 0L, 4L, 0L, 0L, 7L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskStoreuEpi64() {
    val localStorageL53078C8 = AddressableArrayLocal.fromLongArray(LongArray(8) { 42L })
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val m = 0xCA
    run {
        mm512MaskStoreuEpi64(localStorageL53078C8.elementMutPtr(0), m, a)
    }
    val e = mm512SetrEpi64(42L, 2L, 42L, 4L, 42L, 42L, 7L, 8L)
    assertEqM512i(mm512LoaduEpi64(localStorageL53078C8.elementConstPtr(0)), e)
}

public fun testMm512MaskStoreEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(8) { 42.0f })
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val m = 0xCA
    val p = r.data.asMutPtr()
    run {
        mm512MaskStoreEpi64(p, m, a)
    }
    val e = mm512SetrEpi64(42L, 2L, 42L, 4L, 42L, 42L, 7L, 8L)
    assertEqM512i(mm512LoadEpi64(r.data.asPtr()), e)
}

public fun testMm512MaskLoaduPs() {
    val src = mm512Set1Ps(42.0f)
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskLoaduPs(src, m, blackBox(p))
    val e = mm512SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f, 42.0f, 42.0f, 42.0f, 12.0f, 42.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzLoaduPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskzLoaduPs(m, blackBox(p))
    val e = mm512SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 0.0f, 7.0f, 8.0f, 0.0f, 0.0f, 0.0f, 12.0f, 0.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskLoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm512Set1Ps(42.0f)
    val a = Align(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f))
    val p = a.data.asPtr()
    val m = 0xE8CA
    val r = mm512MaskLoadPs(src, m, blackBox(p))
    val e = mm512SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f, 42.0f, 42.0f, 42.0f, 12.0f, 42.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzLoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f))
    val p = a.data.asPtr()
    val m = 0xE8CA
    val r = mm512MaskzLoadPs(m, blackBox(p))
    val e = mm512SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 0.0f, 7.0f, 8.0f, 0.0f, 0.0f, 0.0f, 12.0f, 0.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskStoreuPs() {
    val r = MutableList(16) { 42.0f }
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val m = 0xE8CA
    run {
        mm512MaskStoreuPs(r.asMutPtr(), m, a)
    }
    val e = mm512SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f, 42.0f, 42.0f, 42.0f, 12.0f, 42.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(mm512LoaduPs(r.asPtr()), e)
}

public fun testMm512MaskStorePs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(16) { 42.0f })
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val m = 0xE8CA
    run {
        mm512MaskStorePs(r.data.asMutPtr(), m, a)
    }
    val e = mm512SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f, 42.0f, 42.0f, 42.0f, 12.0f, 42.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(mm512LoadPs(r.data.asPtr()), e)
}

public fun testMm512MaskLoaduPd() {
    val src = mm512Set1Pd(42.0)
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm512MaskLoaduPd(src, m, blackBox(p))
    val e = mm512SetrPd(42.0, 2.0, 42.0, 4.0, 42.0, 42.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzLoaduPd() {
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm512MaskzLoaduPd(m, blackBox(p))
    val e = mm512SetrPd(0.0, 2.0, 0.0, 4.0, 0.0, 0.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskLoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm512Set1Pd(42.0)
    val a = Align(data = floatArrayOf(1.0, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm512MaskLoadPd(src, m, blackBox(p))
    val e = mm512SetrPd(42.0, 2.0, 42.0, 4.0, 42.0, 42.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzLoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1.0, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm512MaskzLoadPd(m, blackBox(p))
    val e = mm512SetrPd(0.0, 2.0, 0.0, 4.0, 0.0, 0.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskStoreuPd() {
    val r = MutableList(8) { 42.0 }
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val m = 0xCA
    run {
        mm512MaskStoreuPd(r.asMutPtr(), m, a)
    }
    val e = mm512SetrPd(42.0, 2.0, 42.0, 4.0, 42.0, 42.0, 7.0, 8.0)
    assertEqM512d(mm512LoaduPd(r.asPtr()), e)
}

public fun testMm512MaskStorePd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(8) { 42.0f })
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val m = 0xCA
    run {
        mm512MaskStorePd(r.data.asMutPtr(), m, a)
    }
    val e = mm512SetrPd(42.0, 2.0, 42.0, 4.0, 42.0, 42.0, 7.0, 8.0)
    assertEqM512d(mm512LoadPd(r.data.asPtr()), e)
}

public fun testMm256MaskLoaduEpi32() {
    val src = mm256Set1Epi32(42)
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm256MaskLoaduEpi32(src, m, blackBox(p))
    val e = mm256SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskzLoaduEpi32() {
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm256MaskzLoaduEpi32(m, blackBox(p))
    val e = mm256SetrEpi32(0, 2, 0, 4, 0, 0, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm256Set1Epi32(42)
    val a = Align(data = floatArrayOf(1, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm256MaskLoadEpi32(src, m, blackBox(p))
    val e = mm256SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskzLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm256MaskzLoadEpi32(m, blackBox(p))
    val e = mm256SetrEpi32(0, 2, 0, 4, 0, 0, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskStoreuEpi32() {
    val localStorageL53357C8 = AddressableArrayLocal.fromIntArray(IntArray(8) { 42 })
    val a = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val m = 0xCA
    run {
        mm256MaskStoreuEpi32(localStorageL53357C8.elementMutPtr(0), m, a)
    }
    val e = mm256SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8)
    assertEqM256i(mm256LoaduEpi32(localStorageL53357C8.elementConstPtr(0)), e)
}

public fun testMm256MaskStoreEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(8) { 42.0f })
    val a = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val m = 0xCA
    run {
        mm256MaskStoreEpi32(r.data.asMutPtr(), m, a)
    }
    val e = mm256SetrEpi32(42, 2, 42, 4, 42, 42, 7, 8)
    assertEqM256i(mm256LoadEpi32(r.data.asPtr()), e)
}

public fun testMm256MaskLoaduEpi64() {
    val src = mm256Set1Epi64x(42L)
    val a = longArrayOf(1L, 2L, 3L, 4L)
    val p = a.asPtr()
    val m = 0xA
    val r = mm256MaskLoaduEpi64(src, m, blackBox(p))
    val e = mm256SetrEpi64x(42L, 2L, 42L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzLoaduEpi64() {
    val a = longArrayOf(1L, 2L, 3L, 4L)
    val p = a.asPtr()
    val m = 0xA
    val r = mm256MaskzLoaduEpi64(m, blackBox(p))
    val e = mm256SetrEpi64x(0L, 2L, 0L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm256Set1Epi64x(42L)
    val a = Align(data = floatArrayOf(1L, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mm256MaskLoadEpi64(src, m, blackBox(p))
    val e = mm256SetrEpi64x(42L, 2L, 42L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1L, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mm256MaskzLoadEpi64(m, blackBox(p))
    val e = mm256SetrEpi64x(0L, 2L, 0L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256MaskStoreuEpi64() {
    val localStorageL53439C8 = AddressableArrayLocal.fromLongArray(LongArray(4) { 42L })
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val m = 0xA
    run {
        mm256MaskStoreuEpi64(localStorageL53439C8.elementMutPtr(0), m, a)
    }
    val e = mm256SetrEpi64x(42L, 2L, 42L, 4L)
    assertEqM256i(mm256LoaduEpi64(localStorageL53439C8.elementConstPtr(0)), e)
}

public fun testMm256MaskStoreEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(4) { 42.0f })
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val m = 0xA
    run {
        mm256MaskStoreEpi64(r.data.asMutPtr(), m, a)
    }
    val e = mm256SetrEpi64x(42L, 2L, 42L, 4L)
    assertEqM256i(mm256LoadEpi64(r.data.asPtr()), e)
}

public fun testMm256MaskLoaduPs() {
    val src = mm256Set1Ps(42.0f)
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm256MaskLoaduPs(src, m, blackBox(p))
    val e = mm256SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzLoaduPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val p = a.asPtr()
    val m = 0xCA
    val r = mm256MaskzLoaduPs(m, blackBox(p))
    val e = mm256SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 0.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskLoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm256Set1Ps(42.0f)
    val a = Align(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm256MaskLoadPs(src, m, blackBox(p))
    val e = mm256SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzLoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val p = a.data.asPtr()
    val m = 0xCA
    val r = mm256MaskzLoadPs(m, blackBox(p))
    val e = mm256SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 0.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskStoreuPs() {
    val r = MutableList(8) { 42.0f }
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val m = 0xCA
    run {
        mm256MaskStoreuPs(r.asMutPtr(), m, a)
    }
    val e = mm256SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f)
    assertEqM256(mm256LoaduPs(r.asPtr()), e)
}

public fun testMm256MaskStorePs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(8) { 42.0f })
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val m = 0xCA
    run {
        mm256MaskStorePs(r.data.asMutPtr(), m, a)
    }
    val e = mm256SetrPs(42.0f, 2.0f, 42.0f, 4.0f, 42.0f, 42.0f, 7.0f, 8.0f)
    assertEqM256(mm256LoadPs(r.data.asPtr()), e)
}

public fun testMm256MaskLoaduPd() {
    val src = mm256Set1Pd(42.0)
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
    val p = a.asPtr()
    val m = 0xA
    val r = mm256MaskLoaduPd(src, m, blackBox(p))
    val e = mm256SetrPd(42.0, 2.0, 42.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzLoaduPd() {
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
    val p = a.asPtr()
    val m = 0xA
    val r = mm256MaskzLoaduPd(m, blackBox(p))
    val e = mm256SetrPd(0.0, 2.0, 0.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskLoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mm256Set1Pd(42.0)
    val a = Align(data = floatArrayOf(1.0, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mm256MaskLoadPd(src, m, blackBox(p))
    val e = mm256SetrPd(42.0, 2.0, 42.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzLoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1.0, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mm256MaskzLoadPd(m, blackBox(p))
    val e = mm256SetrPd(0.0, 2.0, 0.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskStoreuPd() {
    val r = MutableList(4) { 42.0 }
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val m = 0xA
    run {
        mm256MaskStoreuPd(r.asMutPtr(), m, a)
    }
    val e = mm256SetrPd(42.0, 2.0, 42.0, 4.0)
    assertEqM256d(mm256LoaduPd(r.asPtr()), e)
}

public fun testMm256MaskStorePd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(4) { 42.0f })
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val m = 0xA
    run {
        mm256MaskStorePd(r.data.asMutPtr(), m, a)
    }
    val e = mm256SetrPd(42.0, 2.0, 42.0, 4.0)
    assertEqM256d(mm256LoadPd(r.data.asPtr()), e)
}

public fun testMmMaskLoaduEpi32() {
    val src = mmSet1Epi32(42)
    val a = intArrayOf(1, 2, 3, 4)
    val p = a.asPtr()
    val m = 0xA
    val r = mmMaskLoaduEpi32(src, m, blackBox(p))
    val e = mmSetrEpi32(42, 2, 42, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskzLoaduEpi32() {
    val a = intArrayOf(1, 2, 3, 4)
    val p = a.asPtr()
    val m = 0xA
    val r = mmMaskzLoaduEpi32(m, blackBox(p))
    val e = mmSetrEpi32(0, 2, 0, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mmSet1Epi32(42)
    val a = Align(data = floatArrayOf(1, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mmMaskLoadEpi32(src, m, blackBox(p))
    val e = mmSetrEpi32(42, 2, 42, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskzLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mmMaskzLoadEpi32(m, blackBox(p))
    val e = mmSetrEpi32(0, 2, 0, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskStoreuEpi32() {
    val localStorageL53685C8 = AddressableArrayLocal.fromIntArray(IntArray(4) { 42 })
    val a = mmSetrEpi32(1, 2, 3, 4)
    val m = 0xA
    run {
        mmMaskStoreuEpi32(localStorageL53685C8.elementMutPtr(0), m, a)
    }
    val e = mmSetrEpi32(42, 2, 42, 4)
    assertEqM128i(mmLoaduEpi32(localStorageL53685C8.elementConstPtr(0)), e)
}

public fun testMmMaskStoreEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(4) { 42.0f })
    val a = mmSetrEpi32(1, 2, 3, 4)
    val m = 0xA
    run {
        mmMaskStoreEpi32(r.data.asMutPtr(), m, a)
    }
    val e = mmSetrEpi32(42, 2, 42, 4)
    assertEqM128i(mmLoadEpi32(r.data.asPtr()), e)
}

public fun testMmMaskLoaduEpi64() {
    val src = mmSet1Epi64x(42L)
    val a = longArrayOf(1L, 2L)
    val p = a.asPtr()
    val m = 0x2
    val r = mmMaskLoaduEpi64(src, m, blackBox(p))
    val e = mmSetrEpi64x(42, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskzLoaduEpi64() {
    val a = longArrayOf(1L, 2L)
    val p = a.asPtr()
    val m = 0x2
    val r = mmMaskzLoaduEpi64(m, blackBox(p))
    val e = mmSetrEpi64x(0, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mmSet1Epi64x(42L)
    val a = Align(data = floatArrayOf(1L, 2.0f))
    val p = a.data.asPtr()
    val m = 0x2
    val r = mmMaskLoadEpi64(src, m, blackBox(p))
    val e = mmSetrEpi64x(42, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskzLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1L, 2.0f))
    val p = a.data.asPtr()
    val m = 0x2
    val r = mmMaskzLoadEpi64(m, blackBox(p))
    val e = mmSetrEpi64x(0, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskStoreuEpi64() {
    val localStorageL53763C8 = AddressableArrayLocal.fromLongArray(LongArray(2) { 42L })
    val a = mmSetrEpi64x(1, 2)
    val m = 0x2
    run {
        mmMaskStoreuEpi64(localStorageL53763C8.elementMutPtr(0), m, a)
    }
    val e = mmSetrEpi64x(42, 2)
    assertEqM128i(mmLoaduEpi64(localStorageL53763C8.elementConstPtr(0)), e)
}

public fun testMmMaskStoreEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(2) { 42.0f })
    val a = mmSetrEpi64x(1, 2)
    val m = 0x2
    run {
        mmMaskStoreEpi64(r.data.asMutPtr(), m, a)
    }
    val e = mmSetrEpi64x(42, 2)
    assertEqM128i(mmLoadEpi64(r.data.asPtr()), e)
}

public fun testMmMaskLoaduPs() {
    val src = mmSet1Ps(42.0f)
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
    val p = a.asPtr()
    val m = 0xA
    val r = mmMaskLoaduPs(src, m, blackBox(p))
    val e = mmSetrPs(42.0f, 2.0f, 42.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzLoaduPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
    val p = a.asPtr()
    val m = 0xA
    val r = mmMaskzLoaduPs(m, blackBox(p))
    val e = mmSetrPs(0.0f, 2.0f, 0.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskLoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mmSet1Ps(42.0f)
    val a = Align(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mmMaskLoadPs(src, m, blackBox(p))
    val e = mmSetrPs(42.0f, 2.0f, 42.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzLoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
    val p = a.data.asPtr()
    val m = 0xA
    val r = mmMaskzLoadPs(m, blackBox(p))
    val e = mmSetrPs(0.0f, 2.0f, 0.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskStoreuPs() {
    val r = MutableList(4) { 42.0f }
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val m = 0xA
    run {
        mmMaskStoreuPs(r.asMutPtr(), m, a)
    }
    val e = mmSetrPs(42.0f, 2.0f, 42.0f, 4.0f)
    assertEqM128(mmLoaduPs(r.asPtr()), e)
}

public fun testMmMaskStorePs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(4) { 42.0f })
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val m = 0xA
    run {
        mmMaskStorePs(r.data.asMutPtr(), m, a)
    }
    val e = mmSetrPs(42.0f, 2.0f, 42.0f, 4.0f)
    assertEqM128(mmLoadPs(r.data.asPtr()), e)
}

public fun testMmMaskLoaduPd() {
    val src = mmSet1Pd(42.0)
    val a = doubleArrayOf(1.0, 2.0)
    val p = a.asPtr()
    val m = 0x2
    val r = mmMaskLoaduPd(src, m, blackBox(p))
    val e = mmSetrPd(42.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzLoaduPd() {
    val a = doubleArrayOf(1.0, 2.0)
    val p = a.asPtr()
    val m = 0x2
    val r = mmMaskzLoaduPd(m, blackBox(p))
    val e = mmSetrPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskLoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mmSet1Pd(42.0)
    val a = Align(data = floatArrayOf(1.0, 2.0f))
    val p = a.data.asPtr()
    val m = 0x2
    val r = mmMaskLoadPd(src, m, blackBox(p))
    val e = mmSetrPd(42.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzLoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(1.0, 2.0f))
    val p = a.data.asPtr()
    val m = 0x2
    val r = mmMaskzLoadPd(m, blackBox(p))
    val e = mmSetrPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskLoadSs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<Float> = UnresolvedTypeLayout(),
    ) {
        public var data: Float
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: Float, fieldLayout: TypeLayout<Float> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mmSetSs(2.0f)
    val mem = Align(data = 1.0)
    val r = mmMaskLoadSs(src, 0x1u, mem.data)
    assertEqM128(r, mmSetSs(1.0f))
    val r1 = mmMaskLoadSs(src, 0x0u, mem.data)
    assertEqM128(r1, mmSetSs(2.0f))
}

public fun testMmMaskzLoadSs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<Float> = UnresolvedTypeLayout(),
    ) {
        public var data: Float
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: Float, fieldLayout: TypeLayout<Float> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val mem = Align(data = 1.0)
    val r = mmMaskzLoadSs(0x1u, mem.data)
    assertEqM128(r, mmSetSs(1.0f))
    val r1 = mmMaskzLoadSs(0x0u, mem.data)
    assertEqM128(r1, mmSetSs(0.0f))
}

public fun testMmMaskLoadSd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<Double> = UnresolvedTypeLayout(),
    ) {
        public var data: Double
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: Double, fieldLayout: TypeLayout<Double> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val src = mmSetSd(2.0)
    val mem = Align(data = 1.0)
    val r = mmMaskLoadSd(src, 0x1u, mem.data)
    assertEqM128d(r, mmSetSd(1.0))
    val r1 = mmMaskLoadSd(src, 0x0u, mem.data)
    assertEqM128d(r1, mmSetSd(2.0))
}

public fun testMmMaskzLoadSd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<Double> = UnresolvedTypeLayout(),
    ) {
        public var data: Double
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: Double, fieldLayout: TypeLayout<Double> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val mem = Align(data = 1.0)
    val r = mmMaskzLoadSd(0x1u, mem.data)
    assertEqM128d(r, mmSetSd(1.0))
    val r1 = mmMaskzLoadSd(0x0u, mem.data)
    assertEqM128d(r1, mmSetSd(0.0))
}

public fun testMmMaskStoreuPd() {
    val r = MutableList(2) { 42.0 }
    val a = mmSetrPd(1.0, 2.0)
    val m = 0x2
    run {
        mmMaskStoreuPd(r.asMutPtr(), m, a)
    }
    val e = mmSetrPd(42.0, 2.0)
    assertEqM128d(mmLoaduPd(r.asPtr()), e)
}

public fun testMmMaskStorePd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val r = Align(data = FloatArray(2) { 42.0f })
    val a = mmSetrPd(1.0, 2.0)
    val m = 0x2
    run {
        mmMaskStorePd(r.data.asMutPtr(), m, a)
    }
    val e = mmSetrPd(42.0, 2.0)
    assertEqM128d(mmLoadPd(r.data.asPtr()), e)
}

public fun testMmMaskStoreSs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<Float> = UnresolvedTypeLayout(),
    ) {
        public var data: Float
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: Float, fieldLayout: TypeLayout<Float> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = mmSetSs(2.0f)
    val mem = Align(data = 1.0)
    run {
        mmMaskStoreSs(mem.data, 0x1u, a)
    }
    check(mem.data == 2.0)
    run {
        mmMaskStoreSs(mem.data, 0x0u, a)
    }
    check(mem.data == 2.0)
}

public fun testMmMaskStoreSd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<Double> = UnresolvedTypeLayout(),
    ) {
        public var data: Double
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: Double, fieldLayout: TypeLayout<Double> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = mmSetSd(2.0)
    val mem = Align(data = 1.0)
    run {
        mmMaskStoreSd(mem.data, 0x1u, a)
    }
    check(mem.data == 2.0)
    run {
        mmMaskStoreSd(mem.data, 0x0u, a)
    }
    check(mem.data == 2.0)
}

public fun testMm512SetrPd() {
    val r = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r, mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0))
}

public fun testMm512SetPd() {
    val r = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r, mm512SetPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0))
}

public fun testMm512RolEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val r = mm512RolEpi32(a, 1)
    val e = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, e)
}

public fun testMm512MaskRolEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val r = mm512MaskRolEpi32(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskRolEpi32(a, 0xFFFFu, a, 1)
    val e = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRolEpi32() {
    val a = mm512SetEpi32(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 shl 31)
    val r = mm512MaskzRolEpi32(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRolEpi32(0xFFu, a, 1)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1 shl 0)
    assertEqM512i(r1, e)
}

public fun testMm256RolEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val r = mm256RolEpi32(a, 1)
    val e = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r, e)
}

public fun testMm256MaskRolEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val r = mm256MaskRolEpi32(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskRolEpi32(a, 0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRolEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val r = mm256MaskzRolEpi32(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRolEpi32(0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMmRolEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val r = mmRolEpi32(a, 1)
    val e = mmSetEpi32(1 shl 0, 2, 2, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskRolEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val r = mmMaskRolEpi32(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskRolEpi32(a, 0xFu, a, 1)
    val e = mmSetEpi32(1 shl 0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRolEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val r = mmMaskzRolEpi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRolEpi32(0xFu, a, 1)
    val e = mmSetEpi32(1 shl 0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512RorEpi32() {
    val a = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val r = mm512RorEpi32(a, 1)
    val e = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskRorEpi32() {
    val a = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val r = mm512MaskRorEpi32(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskRorEpi32(a, 0xFFFFu, a, 1)
    val e = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRorEpi32() {
    val a = mm512SetEpi32(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 shl 0)
    val r = mm512MaskzRorEpi32(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRorEpi32(0xFFu, a, 1)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 shl 31)
    assertEqM512i(r1, e)
}

public fun testMm256RorEpi32() {
    val a = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    val r = mm256RorEpi32(a, 1)
    val e = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r, e)
}

public fun testMm256MaskRorEpi32() {
    val a = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    val r = mm256MaskRorEpi32(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskRorEpi32(a, 0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRorEpi32() {
    val a = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    val r = mm256MaskzRorEpi32(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRorEpi32(0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmRorEpi32() {
    val a = mmSetEpi32(1 shl 0, 2, 2, 2)
    val r = mmRorEpi32(a, 1)
    val e = mmSetEpi32(1 shl 31, 1, 1, 1)
    assertEqM128i(r, e)
}

public fun testMmMaskRorEpi32() {
    val a = mmSetEpi32(1 shl 0, 2, 2, 2)
    val r = mmMaskRorEpi32(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskRorEpi32(a, 0xFu, a, 1)
    val e = mmSetEpi32(1 shl 31, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRorEpi32() {
    val a = mmSetEpi32(1 shl 0, 2, 2, 2)
    val r = mmMaskzRorEpi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRorEpi32(0xFu, a, 1)
    val e = mmSetEpi32(1 shl 31, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512SlliEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val r = mm512SlliEpi32(a, 1)
    val e = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, e)
}

public fun testMm512MaskSlliEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val r = mm512MaskSlliEpi32(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskSlliEpi32(a, 0xFFFFu, a, 1)
    val e = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSlliEpi32() {
    val a = mm512SetEpi32(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 shl 31)
    val r = mm512MaskzSlliEpi32(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSlliEpi32(0xFFu, a, 1)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSlliEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val r = mm256MaskSlliEpi32(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSlliEpi32(a, 0xFFu, a, 1)
    val e = mm256SetEpi32(0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSlliEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val r = mm256MaskzSlliEpi32(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSlliEpi32(0xFFu, a, 1)
    val e = mm256SetEpi32(0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMmMaskSlliEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val r = mmMaskSlliEpi32(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSlliEpi32(a, 0xFu, a, 1)
    val e = mmSetEpi32(0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSlliEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val r = mmMaskzSlliEpi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSlliEpi32(0xFu, a, 1)
    val e = mmSetEpi32(0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512SrliEpi32() {
    val a = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val r = mm512SrliEpi32(a, 1)
    val e = mm512SetEpi32(0 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrliEpi32() {
    val a = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val r = mm512MaskSrliEpi32(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrliEpi32(a, 0xFFFFu, a, 1)
    val e = mm512SetEpi32(0 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrliEpi32() {
    val a = mm512SetEpi32(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0)
    val r = mm512MaskzSrliEpi32(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrliEpi32(0xFFu, a, 1)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0 shl 31)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrliEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val r = mm256MaskSrliEpi32(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrliEpi32(a, 0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrliEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val r = mm256MaskzSrliEpi32(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrliEpi32(0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrliEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val r = mmMaskSrliEpi32(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSrliEpi32(a, 0xFu, a, 1)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrliEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val r = mmMaskzSrliEpi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrliEpi32(0xFu, a, 1)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512RolvEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val b = mm512Set1Epi32(1)
    val r = mm512RolvEpi32(a, b)
    val e = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, e)
}

public fun testMm512MaskRolvEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskRolvEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskRolvEpi32(a, 0xFFFFu, a, b)
    val e = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRolvEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 shl 31)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskzRolvEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRolvEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1 shl 0)
    assertEqM512i(r1, e)
}

public fun testMm256RolvEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val b = mm256Set1Epi32(1)
    val r = mm256RolvEpi32(a, b)
    val e = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r, e)
}

public fun testMm256MaskRolvEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskRolvEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskRolvEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRolvEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskzRolvEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRolvEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMmRolvEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val b = mmSet1Epi32(1)
    val r = mmRolvEpi32(a, b)
    val e = mmSetEpi32(1 shl 0, 2, 2, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskRolvEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val b = mmSet1Epi32(1)
    val r = mmMaskRolvEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskRolvEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(1 shl 0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRolvEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val b = mmSet1Epi32(1)
    val r = mmMaskzRolvEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRolvEpi32(0xFu, a, b)
    val e = mmSetEpi32(1 shl 0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512RorvEpi32() {
    val a = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val b = mm512Set1Epi32(1)
    val r = mm512RorvEpi32(a, b)
    val e = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskRorvEpi32() {
    val a = mm512SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskRorvEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskRorvEpi32(a, 0xFFFFu, a, b)
    val e = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRorvEpi32() {
    val a = mm512SetEpi32(3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 shl 0)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskzRorvEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRorvEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 shl 31)
    assertEqM512i(r1, e)
}

public fun testMm256RorvEpi32() {
    val a = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    val b = mm256Set1Epi32(1)
    val r = mm256RorvEpi32(a, b)
    val e = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r, e)
}

public fun testMm256MaskRorvEpi32() {
    val a = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskRorvEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskRorvEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRorvEpi32() {
    val a = mm256SetEpi32(1 shl 0, 2, 2, 2, 2, 2, 2, 2)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskzRorvEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRorvEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmRorvEpi32() {
    val a = mmSetEpi32(1 shl 0, 2, 2, 2)
    val b = mmSet1Epi32(1)
    val r = mmRorvEpi32(a, b)
    val e = mmSetEpi32(1 shl 31, 1, 1, 1)
    assertEqM128i(r, e)
}

public fun testMmMaskRorvEpi32() {
    val a = mmSetEpi32(1 shl 0, 2, 2, 2)
    val b = mmSet1Epi32(1)
    val r = mmMaskRorvEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskRorvEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(1 shl 31, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRorvEpi32() {
    val a = mmSetEpi32(1 shl 0, 2, 2, 2)
    val b = mmSet1Epi32(1)
    val r = mmMaskzRorvEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRorvEpi32(0xFu, a, b)
    val e = mmSetEpi32(1 shl 31, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512SllvEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val count = mm512Set1Epi32(1)
    val r = mm512SllvEpi32(a, count)
    val e = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r, e)
}

public fun testMm512MaskSllvEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val count = mm512Set1Epi32(1)
    val r = mm512MaskSllvEpi32(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSllvEpi32(a, 0xFFFFu, a, count)
    val e = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSllvEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 shl 31)
    val count = mm512SetEpi32(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val r = mm512MaskzSllvEpi32(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSllvEpi32(0xFFu, a, count)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSllvEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val count = mm256Set1Epi32(1)
    val r = mm256MaskSllvEpi32(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSllvEpi32(a, 0xFFu, a, count)
    val e = mm256SetEpi32(0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSllvEpi32() {
    val a = mm256SetEpi32(1 shl 31, 1, 1, 1, 1, 1, 1, 1)
    val count = mm256Set1Epi32(1)
    val r = mm256MaskzSllvEpi32(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSllvEpi32(0xFFu, a, count)
    val e = mm256SetEpi32(0, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMmMaskSllvEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val count = mmSet1Epi32(1)
    val r = mmMaskSllvEpi32(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSllvEpi32(a, 0xFu, a, count)
    val e = mmSetEpi32(0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSllvEpi32() {
    val a = mmSetEpi32(1 shl 31, 1, 1, 1)
    val count = mmSet1Epi32(1)
    val r = mmMaskzSllvEpi32(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSllvEpi32(0xFu, a, count)
    val e = mmSetEpi32(0, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512SrlvEpi32() {
    val a = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val count = mm512Set1Epi32(1)
    val r = mm512SrlvEpi32(a, count)
    val e = mm512SetEpi32(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrlvEpi32() {
    val a = mm512SetEpi32(0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    val count = mm512Set1Epi32(1)
    val r = mm512MaskSrlvEpi32(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrlvEpi32(a, 0xFFFFu, a, count)
    val e = mm512SetEpi32(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrlvEpi32() {
    val a = mm512SetEpi32(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0)
    val count = mm512SetEpi32(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val r = mm512MaskzSrlvEpi32(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrlvEpi32(0xFFu, a, count)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrlvEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mm256Set1Epi32(1)
    val r = mm256MaskSrlvEpi32(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrlvEpi32(a, 0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrlvEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mm256Set1Epi32(1)
    val r = mm256MaskzSrlvEpi32(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrlvEpi32(0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrlvEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSet1Epi32(1)
    val r = mmMaskSrlvEpi32(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSrlvEpi32(a, 0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrlvEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSet1Epi32(1)
    val r = mmMaskzSrlvEpi32(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrlvEpi32(0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SllEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1 shl 0, 1 shl 1, 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 2)
    val r = mm512SllEpi32(a, count)
    val e = mm512SetEpi32(0, 1 shl 2, 1 shl 3, 1 shl 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSllEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1 shl 0, 1 shl 1, 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 2)
    val r = mm512MaskSllEpi32(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSllEpi32(a, 0xFFFFu, a, count)
    val e = mm512SetEpi32(0, 1 shl 2, 1 shl 3, 1 shl 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSllEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1 shl 0, 1 shl 1, 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 31)
    val count = mmSetEpi32(2, 0, 0, 2)
    val r = mm512MaskzSllEpi32(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSllEpi32(0xFFu, a, count)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSllEpi32() {
    val a = mm256SetEpi32(1 shl 13, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mm256MaskSllEpi32(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSllEpi32(a, 0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 14, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSllEpi32() {
    val a = mm256SetEpi32(1 shl 13, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mm256MaskzSllEpi32(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSllEpi32(0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 14, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSllEpi32() {
    val a = mmSetEpi32(1 shl 13, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mmMaskSllEpi32(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSllEpi32(a, 0xFu, a, count)
    val e = mmSetEpi32(1 shl 14, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSllEpi32() {
    val a = mmSetEpi32(1 shl 13, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mmMaskzSllEpi32(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSllEpi32(0xFu, a, count)
    val e = mmSetEpi32(1 shl 14, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SrlEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1 shl 0, 1 shl 1, 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 2)
    val r = mm512SrlEpi32(a, count)
    val e = mm512SetEpi32(1 shl 29, 0, 0, 1 shl 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrlEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1 shl 0, 1 shl 1, 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 2)
    val r = mm512MaskSrlEpi32(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrlEpi32(a, 0xFFFFu, a, count)
    val e = mm512SetEpi32(1 shl 29, 0, 0, 1 shl 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrlEpi32() {
    val a = mm512SetEpi32(1 shl 31, 1 shl 0, 1 shl 1, 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 31)
    val count = mmSetEpi32(2, 0, 0, 2)
    val r = mm512MaskzSrlEpi32(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrlEpi32(0xFFu, a, count)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 29)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrlEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mm256MaskSrlEpi32(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrlEpi32(a, 0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrlEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mm256MaskzSrlEpi32(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrlEpi32(0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrlEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mmMaskSrlEpi32(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSrlEpi32(a, 0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrlEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mmMaskzSrlEpi32(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrlEpi32(0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SraEpi32() {
    val a = mm512SetEpi32(8, -8, 16, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    val count = mmSetEpi32(1, 0, 0, 2)
    val r = mm512SraEpi32(a, count)
    val e = mm512SetEpi32(2, -2, 4, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSraEpi32() {
    val a = mm512SetEpi32(8, -8, 16, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16)
    val count = mmSetEpi32(0, 0, 0, 2)
    val r = mm512MaskSraEpi32(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSraEpi32(a, 0xFFFFu, a, count)
    val e = mm512SetEpi32(2, -2, 4, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSraEpi32() {
    val a = mm512SetEpi32(8, -8, 16, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -15, -14)
    val count = mmSetEpi32(2, 0, 0, 2)
    val r = mm512MaskzSraEpi32(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSraEpi32(0xFFu, a, count)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -4, -4)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSraEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mm256MaskSraEpi32(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSraEpi32(a, 0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSraEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mm256MaskzSraEpi32(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSraEpi32(0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSraEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mmMaskSraEpi32(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSraEpi32(a, 0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSraEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSetEpi32(0, 0, 0, 1)
    val r = mmMaskzSraEpi32(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSraEpi32(0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SravEpi32() {
    val a = mm512SetEpi32(8, -8, 16, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    val count = mm512SetEpi32(2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = mm512SravEpi32(a, count)
    val e = mm512SetEpi32(2, -2, 4, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskSravEpi32() {
    val a = mm512SetEpi32(8, -8, 16, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16)
    val count = mm512SetEpi32(2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    val r = mm512MaskSravEpi32(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSravEpi32(a, 0xFFFFu, a, count)
    val e = mm512SetEpi32(2, -2, 4, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSravEpi32() {
    val a = mm512SetEpi32(8, -8, 16, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -15, -14)
    val count = mm512SetEpi32(2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2)
    val r = mm512MaskzSravEpi32(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSravEpi32(0xFFu, a, count)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -4, -4)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSravEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mm256Set1Epi32(1)
    val r = mm256MaskSravEpi32(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSravEpi32(a, 0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSravEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val count = mm256Set1Epi32(1)
    val r = mm256MaskzSravEpi32(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSravEpi32(0xFFu, a, count)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSravEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSet1Epi32(1)
    val r = mmMaskSravEpi32(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSravEpi32(a, 0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSravEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val count = mmSet1Epi32(1)
    val r = mmMaskzSravEpi32(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSravEpi32(0xFu, a, count)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SraiEpi32() {
    val a = mm512SetEpi32(8, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, -15)
    val r = mm512SraiEpi32(a, 2)
    val e = mm512SetEpi32(2, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, -4)
    assertEqM512i(r, e)
}

public fun testMm512MaskSraiEpi32() {
    val a = mm512SetEpi32(8, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, -15)
    val r = mm512MaskSraiEpi32(a, 0u, a, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskSraiEpi32(a, 0xFFFFu, a, 2)
    val e = mm512SetEpi32(2, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -4)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSraiEpi32() {
    val a = mm512SetEpi32(8, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, -15)
    val r = mm512MaskzSraiEpi32(0u, a, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSraiEpi32(0xFFu, a, 2)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -4)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSraiEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val r = mm256MaskSraiEpi32(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSraiEpi32(a, 0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSraiEpi32() {
    val a = mm256SetEpi32(1 shl 5, 0, 0, 0, 0, 0, 0, 0)
    val r = mm256MaskzSraiEpi32(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSraiEpi32(0xFFu, a, 1)
    val e = mm256SetEpi32(1 shl 4, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSraiEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val r = mmMaskSraiEpi32(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSraiEpi32(a, 0xFu, a, 1)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSraiEpi32() {
    val a = mmSetEpi32(1 shl 5, 0, 0, 0)
    val r = mmMaskzSraiEpi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSraiEpi32(0xFu, a, 1)
    val e = mmSetEpi32(1 shl 4, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512PermutePs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512PermutePs(a, 0xFF)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 7.0f, 7.0f, 7.0f, 7.0f, 11.0f, 11.0f, 11.0f, 11.0f, 15.0f, 15.0f, 15.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskPermutePs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskPermutePs(a, 0u, a, 0xFF)
    assertEqM512(r, a)
    val r1 = mm512MaskPermutePs(a, 0xFFFFu, a, 0xFF)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 7.0f, 7.0f, 7.0f, 7.0f, 11.0f, 11.0f, 11.0f, 11.0f, 15.0f, 15.0f, 15.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzPermutePs() {
    val a = mm512SetrPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskzPermutePs(0u, a, 0xFF)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzPermutePs(0xFFFFu, a, 0xFF)
    val e = mm512SetrPs(3.0f, 3.0f, 3.0f, 3.0f, 7.0f, 7.0f, 7.0f, 7.0f, 11.0f, 11.0f, 11.0f, 11.0f, 15.0f, 15.0f, 15.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskPermutePs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskPermutePs(a, 0u, a, 0xFF)
    assertEqM256(r, a)
    val r1 = mm256MaskPermutePs(a, 0xFFu, a, 0xFF)
    val e = mm256SetPs(0.0f, 0.0f, 0.0f, 0.0f, 4.0f, 4.0f, 4.0f, 4.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzPermutePs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskzPermutePs(0u, a, 0xFF)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzPermutePs(0xFFu, a, 0xFF)
    val e = mm256SetPs(0.0f, 0.0f, 0.0f, 0.0f, 4.0f, 4.0f, 4.0f, 4.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskPermutePs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r = mmMaskPermutePs(a, 0u, a, 0xFF)
    assertEqM128(r, a)
    val r1 = mmMaskPermutePs(a, 0xFu, a, 0xFF)
    val e = mmSetPs(0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzPermutePs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r = mmMaskzPermutePs(0u, a, 0xFF)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzPermutePs(0xFu, a, 0xFF)
    val e = mmSetPs(0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM128(r1, e)
}

public fun testMm512PermutevarEpi32() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512PermutevarEpi32(idx, a)
    val e = mm512Set1Epi32(14)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutevarEpi32() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskPermutevarEpi32(a, 0u, idx, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutevarEpi32(a, 0xFFFFu, idx, a)
    val e = mm512Set1Epi32(14)
    assertEqM512i(r1, e)
}

public fun testMm512PermutevarPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512Set1Epi32(0x1)
    val r = mm512PermutevarPs(a, b)
    val e = mm512SetPs(2.0f, 2.0f, 2.0f, 2.0f, 6.0f, 6.0f, 6.0f, 6.0f, 10.0f, 10.0f, 10.0f, 10.0f, 14.0f, 14.0f, 14.0f, 14.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskPermutevarPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512Set1Epi32(0x1)
    val r = mm512MaskPermutevarPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskPermutevarPs(a, 0xFFFFu, a, b)
    val e = mm512SetPs(2.0f, 2.0f, 2.0f, 2.0f, 6.0f, 6.0f, 6.0f, 6.0f, 10.0f, 10.0f, 10.0f, 10.0f, 14.0f, 14.0f, 14.0f, 14.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzPermutevarPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val b = mm512Set1Epi32(0x1)
    val r = mm512MaskzPermutevarPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzPermutevarPs(0xFFu, a, b)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 10.0f, 10.0f, 10.0f, 10.0f, 14.0f, 14.0f, 14.0f, 14.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskPermutevarPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val b = mm256Set1Epi32(0x1)
    val r = mm256MaskPermutevarPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskPermutevarPs(a, 0xFFu, a, b)
    val e = mm256SetPs(2.0f, 2.0f, 2.0f, 2.0f, 6.0f, 6.0f, 6.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzPermutevarPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val b = mm256Set1Epi32(0x1)
    val r = mm256MaskzPermutevarPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzPermutevarPs(0xFFu, a, b)
    val e = mm256SetPs(2.0f, 2.0f, 2.0f, 2.0f, 6.0f, 6.0f, 6.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskPermutevarPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSet1Epi32(0x1)
    val r = mmMaskPermutevarPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskPermutevarPs(a, 0xFu, a, b)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzPermutevarPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSet1Epi32(0x1)
    val r = mmMaskzPermutevarPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzPermutevarPs(0xFu, a, b)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMm512PermutexvarEpi32() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512PermutexvarEpi32(idx, a)
    val e = mm512Set1Epi32(14)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutexvarEpi32() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskPermutexvarEpi32(a, 0u, idx, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutexvarEpi32(a, 0xFFFFu, idx, a)
    val e = mm512Set1Epi32(14)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutexvarEpi32() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzPermutexvarEpi32(0u, idx, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutexvarEpi32(0xFFu, idx, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 14, 14, 14, 14, 14, 14, 14, 14)
    assertEqM512i(r1, e)
}

public fun testMm256PermutexvarEpi32() {
    val idx = mm256Set1Epi32(1)
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256PermutexvarEpi32(idx, a)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutexvarEpi32() {
    val idx = mm256Set1Epi32(1)
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskPermutexvarEpi32(a, 0u, idx, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutexvarEpi32(a, 0xFFu, idx, a)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutexvarEpi32() {
    val idx = mm256Set1Epi32(1)
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzPermutexvarEpi32(0u, idx, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutexvarEpi32(0xFFu, idx, a)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r1, e)
}

public fun testMm512PermutexvarPs() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512PermutexvarPs(idx, a)
    val e = mm512Set1Ps(14.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskPermutexvarPs() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskPermutexvarPs(a, 0u, idx, a)
    assertEqM512(r, a)
    val r1 = mm512MaskPermutexvarPs(a, 0xFFFFu, idx, a)
    val e = mm512Set1Ps(14.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzPermutexvarPs() {
    val idx = mm512Set1Epi32(1)
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskzPermutexvarPs(0u, idx, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzPermutexvarPs(0xFFu, idx, a)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 14.0f, 14.0f, 14.0f, 14.0f, 14.0f, 14.0f, 14.0f, 14.0f)
    assertEqM512(r1, e)
}

public fun testMm256PermutexvarPs() {
    val idx = mm256Set1Epi32(1)
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256PermutexvarPs(idx, a)
    val e = mm256Set1Ps(6.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskPermutexvarPs() {
    val idx = mm256Set1Epi32(1)
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskPermutexvarPs(a, 0u, idx, a)
    assertEqM256(r, a)
    val r1 = mm256MaskPermutexvarPs(a, 0xFFu, idx, a)
    val e = mm256Set1Ps(6.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzPermutexvarPs() {
    val idx = mm256Set1Epi32(1)
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskzPermutexvarPs(0u, idx, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzPermutexvarPs(0xFFu, idx, a)
    val e = mm256Set1Ps(6.0f)
    assertEqM256(r1, e)
}

public fun testMm512Permutex2varEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Epi32(100)
    val r = mm512Permutex2varEpi32(a, idx, b)
    val e = mm512SetEpi32(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutex2varEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Epi32(100)
    val r = mm512MaskPermutex2varEpi32(a, 0u, idx, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutex2varEpi32(a, 0xFFFFu, idx, b)
    val e = mm512SetEpi32(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutex2varEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Epi32(100)
    val r = mm512MaskzPermutex2varEpi32(0u, a, idx, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutex2varEpi32(0xFFu, a, idx, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM512i(r1, e)
}

public fun testMm512Mask2Permutex2varEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm512SetEpi32(1000, 1 shl 4, 2000, 1 shl 4, 3000, 1 shl 4, 4000, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Epi32(100)
    val r = mm512Mask2Permutex2varEpi32(a, idx, 0u, b)
    assertEqM512i(r, idx)
    val r1 = mm512Mask2Permutex2varEpi32(a, idx, 0xFFu, b)
    val e = mm512SetEpi32(1000, 1 shl 4, 2000, 1 shl 4, 3000, 1 shl 4, 4000, 1 shl 4, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM512i(r1, e)
}

public fun testMm256Permutex2varEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Epi32(100)
    val r = mm256Permutex2varEpi32(a, idx, b)
    val e = mm256SetEpi32(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutex2varEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Epi32(100)
    val r = mm256MaskPermutex2varEpi32(a, 0u, idx, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutex2varEpi32(a, 0xFFu, idx, b)
    val e = mm256SetEpi32(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutex2varEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Epi32(100)
    val r = mm256MaskzPermutex2varEpi32(0u, a, idx, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutex2varEpi32(0xFFu, a, idx, b)
    val e = mm256SetEpi32(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM256i(r1, e)
}

public fun testMm256Mask2Permutex2varEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Epi32(100)
    val r = mm256Mask2Permutex2varEpi32(a, idx, 0u, b)
    assertEqM256i(r, idx)
    val r1 = mm256Mask2Permutex2varEpi32(a, idx, 0xFFu, b)
    val e = mm256SetEpi32(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM256i(r1, e)
}

public fun testMmPermutex2varEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Epi32(100)
    val r = mmPermutex2varEpi32(a, idx, b)
    val e = mmSetEpi32(2, 100, 1, 100)
    assertEqM128i(r, e)
}

public fun testMmMaskPermutex2varEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Epi32(100)
    val r = mmMaskPermutex2varEpi32(a, 0u, idx, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPermutex2varEpi32(a, 0xFu, idx, b)
    val e = mmSetEpi32(2, 100, 1, 100)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPermutex2varEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Epi32(100)
    val r = mmMaskzPermutex2varEpi32(0u, a, idx, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPermutex2varEpi32(0xFu, a, idx, b)
    val e = mmSetEpi32(2, 100, 1, 100)
    assertEqM128i(r1, e)
}

public fun testMmMask2Permutex2varEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Epi32(100)
    val r = mmMask2Permutex2varEpi32(a, idx, 0u, b)
    assertEqM128i(r, idx)
    val r1 = mmMask2Permutex2varEpi32(a, idx, 0xFu, b)
    val e = mmSetEpi32(2, 100, 1, 100)
    assertEqM128i(r1, e)
}

public fun testMm512Permutex2varPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Ps(100.0f)
    val r = mm512Permutex2varPs(a, idx, b)
    val e = mm512SetPs(14.0f, 100.0f, 13.0f, 100.0f, 12.0f, 100.0f, 11.0f, 100.0f, 10.0f, 100.0f, 9.0f, 100.0f, 8.0f, 100.0f, 7.0f, 100.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskPermutex2varPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Ps(100.0f)
    val r = mm512MaskPermutex2varPs(a, 0u, idx, b)
    assertEqM512(r, a)
    val r1 = mm512MaskPermutex2varPs(a, 0xFFFFu, idx, b)
    val e = mm512SetPs(14.0f, 100.0f, 13.0f, 100.0f, 12.0f, 100.0f, 11.0f, 100.0f, 10.0f, 100.0f, 9.0f, 100.0f, 8.0f, 100.0f, 7.0f, 100.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzPermutex2varPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Ps(100.0f)
    val r = mm512MaskzPermutex2varPs(0u, a, idx, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzPermutex2varPs(0xFFu, a, idx, b)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 10.0f, 100.0f, 9.0f, 100.0f, 8.0f, 100.0f, 7.0f, 100.0f)
    assertEqM512(r1, e)
}

public fun testMm512Mask2Permutex2varPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val idx = mm512SetEpi32(1, 1 shl 4, 2, 1 shl 4, 3, 1 shl 4, 4, 1 shl 4, 5, 1 shl 4, 6, 1 shl 4, 7, 1 shl 4, 8, 1 shl 4)
    val b = mm512Set1Ps(100.0f)
    val r = mm512Mask2Permutex2varPs(a, idx, 0u, b)
    assertEqM512(r, mm512Castsi512Ps(idx))
    val r1 = mm512Mask2Permutex2varPs(a, idx, 0xFFFFu, b)
    val e = mm512SetPs(14.0f, 100.0f, 13.0f, 100.0f, 12.0f, 100.0f, 11.0f, 100.0f, 10.0f, 100.0f, 9.0f, 100.0f, 8.0f, 100.0f, 7.0f, 100.0f)
    assertEqM512(r1, e)
}

public fun testMm256Permutex2varPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Ps(100.0f)
    val r = mm256Permutex2varPs(a, idx, b)
    val e = mm256SetPs(6.0f, 100.0f, 5.0f, 100.0f, 4.0f, 100.0f, 3.0f, 100.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskPermutex2varPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Ps(100.0f)
    val r = mm256MaskPermutex2varPs(a, 0u, idx, b)
    assertEqM256(r, a)
    val r1 = mm256MaskPermutex2varPs(a, 0xFFu, idx, b)
    val e = mm256SetPs(6.0f, 100.0f, 5.0f, 100.0f, 4.0f, 100.0f, 3.0f, 100.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzPermutex2varPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Ps(100.0f)
    val r = mm256MaskzPermutex2varPs(0u, a, idx, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzPermutex2varPs(0xFFu, a, idx, b)
    val e = mm256SetPs(6.0f, 100.0f, 5.0f, 100.0f, 4.0f, 100.0f, 3.0f, 100.0f)
    assertEqM256(r1, e)
}

public fun testMm256Mask2Permutex2varPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val idx = mm256SetEpi32(1, 1 shl 3, 2, 1 shl 3, 3, 1 shl 3, 4, 1 shl 3)
    val b = mm256Set1Ps(100.0f)
    val r = mm256Mask2Permutex2varPs(a, idx, 0u, b)
    assertEqM256(r, mm256Castsi256Ps(idx))
    val r1 = mm256Mask2Permutex2varPs(a, idx, 0xFFu, b)
    val e = mm256SetPs(6.0f, 100.0f, 5.0f, 100.0f, 4.0f, 100.0f, 3.0f, 100.0f)
    assertEqM256(r1, e)
}

public fun testMmPermutex2varPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Ps(100.0f)
    val r = mmPermutex2varPs(a, idx, b)
    val e = mmSetPs(2.0f, 100.0f, 1.0f, 100.0f)
    assertEqM128(r, e)
}

public fun testMmMaskPermutex2varPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Ps(100.0f)
    val r = mmMaskPermutex2varPs(a, 0u, idx, b)
    assertEqM128(r, a)
    val r1 = mmMaskPermutex2varPs(a, 0xFu, idx, b)
    val e = mmSetPs(2.0f, 100.0f, 1.0f, 100.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzPermutex2varPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Ps(100.0f)
    val r = mmMaskzPermutex2varPs(0u, a, idx, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzPermutex2varPs(0xFu, a, idx, b)
    val e = mmSetPs(2.0f, 100.0f, 1.0f, 100.0f)
    assertEqM128(r1, e)
}

public fun testMmMask2Permutex2varPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val idx = mmSetEpi32(1, 1 shl 2, 2, 1 shl 2)
    val b = mmSet1Ps(100.0f)
    val r = mmMask2Permutex2varPs(a, idx, 0u, b)
    assertEqM128(r, mmCastsi128Ps(idx))
    val r1 = mmMask2Permutex2varPs(a, idx, 0xFu, b)
    val e = mmSetPs(2.0f, 100.0f, 1.0f, 100.0f)
    assertEqM128(r1, e)
}

public fun testMm512ShuffleEpi32() {
    val a = mm512SetrEpi32(1, 4, 5, 8, 9, 12, 13, 16, 1, 4, 5, 8, 9, 12, 13, 16)
    val r = mm512ShuffleEpi32(a, MM_PERM_AADD)
    val e = mm512SetrEpi32(8, 8, 1, 1, 16, 16, 9, 9, 8, 8, 1, 1, 16, 16, 9, 9)
    assertEqM512i(r, e)
}

public fun testMm512MaskShuffleEpi32() {
    val a = mm512SetrEpi32(1, 4, 5, 8, 9, 12, 13, 16, 1, 4, 5, 8, 9, 12, 13, 16)
    val r = mm512MaskShuffleEpi32(a, 0u, a, MM_PERM_AADD)
    assertEqM512i(r, a)
    val r1 = mm512MaskShuffleEpi32(a, 0xFFFFu, a, MM_PERM_AADD)
    val e = mm512SetrEpi32(8, 8, 1, 1, 16, 16, 9, 9, 8, 8, 1, 1, 16, 16, 9, 9)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShuffleEpi32() {
    val a = mm512SetrEpi32(1, 4, 5, 8, 9, 12, 13, 16, 1, 4, 5, 8, 9, 12, 13, 16)
    val r = mm512MaskzShuffleEpi32(0u, a, MM_PERM_AADD)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShuffleEpi32(0xFFu, a, MM_PERM_AADD)
    val e = mm512SetrEpi32(8, 8, 1, 1, 16, 16, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskShuffleEpi32() {
    val a = mm256SetEpi32(1, 4, 5, 8, 9, 12, 13, 16)
    val r = mm256MaskShuffleEpi32(a, 0u, a, MM_PERM_AADD)
    assertEqM256i(r, a)
    val r1 = mm256MaskShuffleEpi32(a, 0xFFu, a, MM_PERM_AADD)
    val e = mm256SetEpi32(8, 8, 1, 1, 16, 16, 9, 9)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShuffleEpi32() {
    val a = mm256SetEpi32(1, 4, 5, 8, 9, 12, 13, 16)
    val r = mm256MaskzShuffleEpi32(0u, a, MM_PERM_AADD)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShuffleEpi32(0xFFu, a, MM_PERM_AADD)
    val e = mm256SetEpi32(8, 8, 1, 1, 16, 16, 9, 9)
    assertEqM256i(r1, e)
}

public fun testMmMaskShuffleEpi32() {
    val a = mmSetEpi32(1, 4, 5, 8)
    val r = mmMaskShuffleEpi32(a, 0u, a, MM_PERM_AADD)
    assertEqM128i(r, a)
    val r1 = mmMaskShuffleEpi32(a, 0xFu, a, MM_PERM_AADD)
    val e = mmSetEpi32(8, 8, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShuffleEpi32() {
    val a = mmSetEpi32(1, 4, 5, 8)
    val r = mmMaskzShuffleEpi32(0u, a, MM_PERM_AADD)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShuffleEpi32(0xFu, a, MM_PERM_AADD)
    val e = mmSetEpi32(8, 8, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512ShufflePs() {
    val a = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f, 1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm512SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f, 2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm512ShufflePs(a, b, 0xF)
    val e = mm512SetrPs(8.0f, 8.0f, 2.0f, 2.0f, 16.0f, 16.0f, 10.0f, 10.0f, 8.0f, 8.0f, 2.0f, 2.0f, 16.0f, 16.0f, 10.0f, 10.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskShufflePs() {
    val a = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f, 1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm512SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f, 2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm512MaskShufflePs(a, 0u, a, b, 0xF)
    assertEqM512(r, a)
    val r1 = mm512MaskShufflePs(a, 0xFFFFu, a, b, 0xF)
    val e = mm512SetrPs(8.0f, 8.0f, 2.0f, 2.0f, 16.0f, 16.0f, 10.0f, 10.0f, 8.0f, 8.0f, 2.0f, 2.0f, 16.0f, 16.0f, 10.0f, 10.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzShufflePs() {
    val a = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f, 1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm512SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f, 2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm512MaskzShufflePs(0u, a, b, 0xF)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzShufflePs(0xFFu, a, b, 0xF)
    val e = mm512SetrPs(8.0f, 8.0f, 2.0f, 2.0f, 16.0f, 16.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskShufflePs() {
    val a = mm256SetPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256MaskShufflePs(a, 0u, a, b, 0xFF)
    assertEqM256(r, a)
    val r1 = mm256MaskShufflePs(a, 0xFFu, a, b, 0xF)
    val e = mm256SetPs(7.0f, 7.0f, 1.0f, 1.0f, 15.0f, 15.0f, 9.0f, 9.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzShufflePs() {
    val a = mm256SetPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256MaskzShufflePs(0u, a, b, 0xFF)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzShufflePs(0xFFu, a, b, 0xF)
    val e = mm256SetPs(7.0f, 7.0f, 1.0f, 1.0f, 15.0f, 15.0f, 9.0f, 9.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskShufflePs() {
    val a = mmSetPs(1.0f, 4.0f, 5.0f, 8.0f)
    val b = mmSetPs(2.0f, 3.0f, 6.0f, 7.0f)
    val r = mmMaskShufflePs(a, 0u, a, b, 0xFF)
    assertEqM128(r, a)
    val r1 = mmMaskShufflePs(a, 0xFu, a, b, 0xF)
    val e = mmSetPs(7.0f, 7.0f, 1.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzShufflePs() {
    val a = mmSetPs(1.0f, 4.0f, 5.0f, 8.0f)
    val b = mmSetPs(2.0f, 3.0f, 6.0f, 7.0f)
    val r = mmMaskzShufflePs(0u, a, b, 0xFF)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzShufflePs(0xFu, a, b, 0xF)
    val e = mmSetPs(7.0f, 7.0f, 1.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMm512ShuffleI32x4() {
    val a = mm512SetrEpi32(1, 4, 5, 8, 9, 12, 13, 16, 1, 4, 5, 8, 9, 12, 13, 16)
    val b = mm512SetrEpi32(2, 3, 6, 7, 10, 11, 14, 15, 2, 3, 6, 7, 10, 11, 14, 15)
    val r = mm512ShuffleI32x4(a, b, 0x0)
    val e = mm512SetrEpi32(1, 4, 5, 8, 1, 4, 5, 8, 2, 3, 6, 7, 2, 3, 6, 7)
    assertEqM512i(r, e)
}

public fun testMm512MaskShuffleI32x4() {
    val a = mm512SetrEpi32(1, 4, 5, 8, 9, 12, 13, 16, 1, 4, 5, 8, 9, 12, 13, 16)
    val b = mm512SetrEpi32(2, 3, 6, 7, 10, 11, 14, 15, 2, 3, 6, 7, 10, 11, 14, 15)
    val r = mm512MaskShuffleI32x4(a, 0u, a, b, 0x0)
    assertEqM512i(r, a)
    val r1 = mm512MaskShuffleI32x4(a, 0xFFFFu, a, b, 0x0)
    val e = mm512SetrEpi32(1, 4, 5, 8, 1, 4, 5, 8, 2, 3, 6, 7, 2, 3, 6, 7)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShuffleI32x4() {
    val a = mm512SetrEpi32(1, 4, 5, 8, 9, 12, 13, 16, 1, 4, 5, 8, 9, 12, 13, 16)
    val b = mm512SetrEpi32(2, 3, 6, 7, 10, 11, 14, 15, 2, 3, 6, 7, 10, 11, 14, 15)
    val r = mm512MaskzShuffleI32x4(0u, a, b, 0x0)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShuffleI32x4(0xFFu, a, b, 0x0)
    val e = mm512SetrEpi32(1, 4, 5, 8, 1, 4, 5, 8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256ShuffleI32x4() {
    val a = mm256SetEpi32(1, 4, 5, 8, 9, 12, 13, 16)
    val b = mm256SetEpi32(2, 3, 6, 7, 10, 11, 14, 15)
    val r = mm256ShuffleI32x4(a, b, 0x0)
    val e = mm256SetEpi32(10, 11, 14, 15, 9, 12, 13, 16)
    assertEqM256i(r, e)
}

public fun testMm256MaskShuffleI32x4() {
    val a = mm256SetEpi32(1, 4, 5, 8, 9, 12, 13, 16)
    val b = mm256SetEpi32(2, 3, 6, 7, 10, 11, 14, 15)
    val r = mm256MaskShuffleI32x4(a, 0u, a, b, 0x0)
    assertEqM256i(r, a)
    val r1 = mm256MaskShuffleI32x4(a, 0xFFu, a, b, 0x0)
    val e = mm256SetEpi32(10, 11, 14, 15, 9, 12, 13, 16)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShuffleI32x4() {
    val a = mm256SetEpi32(1, 4, 5, 8, 9, 12, 13, 16)
    val b = mm256SetEpi32(2, 3, 6, 7, 10, 11, 14, 15)
    val r = mm256MaskzShuffleI32x4(0u, a, b, 0x0)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShuffleI32x4(0xFFu, a, b, 0x0)
    val e = mm256SetEpi32(10, 11, 14, 15, 9, 12, 13, 16)
    assertEqM256i(r1, e)
}

public fun testMm512ShuffleF32x4() {
    val a = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f, 1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm512SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f, 2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm512ShuffleF32x4(a, b, 0x0)
    val e = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 1.0f, 4.0f, 5.0f, 8.0f, 2.0f, 3.0f, 6.0f, 7.0f, 2.0f, 3.0f, 6.0f, 7.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskShuffleF32x4() {
    val a = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f, 1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm512SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f, 2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm512MaskShuffleF32x4(a, 0u, a, b, 0x0)
    assertEqM512(r, a)
    val r1 = mm512MaskShuffleF32x4(a, 0xFFFFu, a, b, 0x0)
    val e = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 1.0f, 4.0f, 5.0f, 8.0f, 2.0f, 3.0f, 6.0f, 7.0f, 2.0f, 3.0f, 6.0f, 7.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzShuffleF32x4() {
    val a = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f, 1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm512SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f, 2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm512MaskzShuffleF32x4(0u, a, b, 0x0)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzShuffleF32x4(0xFFu, a, b, 0x0)
    val e = mm512SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 1.0f, 4.0f, 5.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256ShuffleF32x4() {
    val a = mm256SetPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256ShuffleF32x4(a, b, 0x0)
    val e = mm256SetPs(10.0f, 11.0f, 14.0f, 15.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskShuffleF32x4() {
    val a = mm256SetPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256MaskShuffleF32x4(a, 0u, a, b, 0x0)
    assertEqM256(r, a)
    val r1 = mm256MaskShuffleF32x4(a, 0xFFu, a, b, 0x0)
    val e = mm256SetPs(10.0f, 11.0f, 14.0f, 15.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzShuffleF32x4() {
    val a = mm256SetPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256MaskzShuffleF32x4(0u, a, b, 0x0)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzShuffleF32x4(0xFFu, a, b, 0x0)
    val e = mm256SetPs(10.0f, 11.0f, 14.0f, 15.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    assertEqM256(r1, e)
}

public fun testMm512Extractf32x4Ps() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512Extractf32x4Ps(a, 1)
    val e = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMm512MaskExtractf32x4Ps() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val src = mmSet1Ps(100.0f)
    val r = mm512MaskExtractf32x4Ps(src, 0u, a, 1)
    assertEqM128(r, src)
    val r1 = mm512MaskExtractf32x4Ps(src, 0xFFu, a, 1)
    val e = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM128(r1, e)
}

public fun testMm512MaskzExtractf32x4Ps() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskzExtractf32x4Ps(0u, a, 1)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mm512MaskzExtractf32x4Ps(0x1u, a, 1)
    val e = mmSetrPs(5.0f, 0.0f, 0.0f, 0.0f)
    assertEqM128(r1, e)
}

public fun testMm256Extractf32x4Ps() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256Extractf32x4Ps(a, 1)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256MaskExtractf32x4Ps() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val src = mmSet1Ps(100.0f)
    val r = mm256MaskExtractf32x4Ps(src, 0u, a, 1)
    assertEqM128(r, src)
    val r1 = mm256MaskExtractf32x4Ps(src, 0xFu, a, 1)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm256MaskzExtractf32x4Ps() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskzExtractf32x4Ps(0u, a, 1)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mm256MaskzExtractf32x4Ps(0xFu, a, 1)
    val e = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm512Extracti32x4Epi32() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512Extracti32x4Epi32(a, 1)
    val e = mmSetrEpi32(5, 6, 7, 8)
    assertEqM128i(r, e)
}

public fun testMm512MaskExtracti32x4Epi32() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val src = mmSet1Epi32(100)
    val r = mm512MaskExtracti32x4Epi32(src, 0u, a, 1)
    assertEqM128i(r, src)
    val r1 = mm512MaskExtracti32x4Epi32(src, 0xFFu, a, 1)
    val e = mmSetrEpi32(5, 6, 7, 8)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzExtracti32x4Epi32() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzExtracti32x4Epi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzExtracti32x4Epi32(0x1u, a, 1)
    val e = mmSetrEpi32(5, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm256Extracti32x4Epi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256Extracti32x4Epi32(a, 1)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMm256MaskExtracti32x4Epi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val src = mmSet1Epi32(100)
    val r = mm256MaskExtracti32x4Epi32(src, 0u, a, 1)
    assertEqM128i(r, src)
    val r1 = mm256MaskExtracti32x4Epi32(src, 0xFu, a, 1)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzExtracti32x4Epi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzExtracti32x4Epi32(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzExtracti32x4Epi32(0xFu, a, 1)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r1, e)
}

public fun testMm512MoveldupPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MoveldupPs(a)
    val e = mm512SetrPs(1.0f, 1.0f, 3.0f, 3.0f, 5.0f, 5.0f, 7.0f, 7.0f, 9.0f, 9.0f, 11.0f, 11.0f, 13.0f, 13.0f, 15.0f, 15.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMoveldupPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskMoveldupPs(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskMoveldupPs(a, 0xFFFFu, a)
    val e = mm512SetrPs(1.0f, 1.0f, 3.0f, 3.0f, 5.0f, 5.0f, 7.0f, 7.0f, 9.0f, 9.0f, 11.0f, 11.0f, 13.0f, 13.0f, 15.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMoveldupPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskzMoveldupPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMoveldupPs(0xFFu, a)
    val e = mm512SetrPs(1.0f, 1.0f, 3.0f, 3.0f, 5.0f, 5.0f, 7.0f, 7.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskMoveldupPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskMoveldupPs(a, 0u, a)
    assertEqM256(r, a)
    val r1 = mm256MaskMoveldupPs(a, 0xFFu, a)
    val e = mm256SetPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzMoveldupPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskzMoveldupPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzMoveldupPs(0xFFu, a)
    val e = mm256SetPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskMoveldupPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskMoveldupPs(a, 0u, a)
    assertEqM128(r, a)
    val r1 = mmMaskMoveldupPs(a, 0xFu, a)
    val e = mmSetPs(2.0f, 2.0f, 4.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzMoveldupPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzMoveldupPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzMoveldupPs(0xFu, a)
    val e = mmSetPs(2.0f, 2.0f, 4.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm512MovehdupPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MovehdupPs(a)
    val e = mm512SetrPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f, 10.0f, 10.0f, 12.0f, 12.0f, 14.0f, 14.0f, 16.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskMovehdupPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskMovehdupPs(a, 0u, a)
    assertEqM512(r, a)
    val r1 = mm512MaskMovehdupPs(a, 0xFFFFu, a)
    val e = mm512SetrPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f, 10.0f, 10.0f, 12.0f, 12.0f, 14.0f, 14.0f, 16.0f, 16.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzMovehdupPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm512MaskzMovehdupPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzMovehdupPs(0xFFu, a)
    val e = mm512SetrPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskMovehdupPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskMovehdupPs(a, 0u, a)
    assertEqM256(r, a)
    val r1 = mm256MaskMovehdupPs(a, 0xFFu, a)
    val e = mm256SetPs(1.0f, 1.0f, 3.0f, 3.0f, 5.0f, 5.0f, 7.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzMovehdupPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MaskzMovehdupPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzMovehdupPs(0xFFu, a)
    val e = mm256SetPs(1.0f, 1.0f, 3.0f, 3.0f, 5.0f, 5.0f, 7.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskMovehdupPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskMovehdupPs(a, 0u, a)
    assertEqM128(r, a)
    val r1 = mmMaskMovehdupPs(a, 0xFu, a)
    val e = mmSetPs(1.0f, 1.0f, 3.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzMovehdupPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmMaskzMovehdupPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzMovehdupPs(0xFu, a)
    val e = mmSetPs(1.0f, 1.0f, 3.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMm512Inserti32x4() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetrEpi32(17, 18, 19, 20)
    val r = mm512Inserti32x4(a, b, 0)
    val e = mm512SetrEpi32(17, 18, 19, 20, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskInserti32x4() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetrEpi32(17, 18, 19, 20)
    val r = mm512MaskInserti32x4(a, 0u, a, b, 0)
    assertEqM512i(r, a)
    val r1 = mm512MaskInserti32x4(a, 0xFFFFu, a, b, 0)
    val e = mm512SetrEpi32(17, 18, 19, 20, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzInserti32x4() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetrEpi32(17, 18, 19, 20)
    val r = mm512MaskzInserti32x4(0u, a, b, 0)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzInserti32x4(0xFFu, a, b, 0)
    val e = mm512SetrEpi32(17, 18, 19, 20, 5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256Inserti32x4() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mm256Inserti32x4(a, b, 1)
    val e = mm256SetEpi32(17, 18, 19, 20, 5, 6, 7, 8)
    assertEqM256i(r, e)
}

public fun testMm256MaskInserti32x4() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mm256MaskInserti32x4(a, 0u, a, b, 0)
    assertEqM256i(r, a)
    val r1 = mm256MaskInserti32x4(a, 0xFFu, a, b, 1)
    val e = mm256SetEpi32(17, 18, 19, 20, 5, 6, 7, 8)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzInserti32x4() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mm256MaskzInserti32x4(0u, a, b, 0)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzInserti32x4(0xFFu, a, b, 1)
    val e = mm256SetEpi32(17, 18, 19, 20, 5, 6, 7, 8)
    assertEqM256i(r1, e)
}

public fun testMm512Insertf32x4() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mmSetrPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512Insertf32x4(a, b, 0)
    val e = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskInsertf32x4() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mmSetrPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskInsertf32x4(a, 0u, a, b, 0)
    assertEqM512(r, a)
    val r1 = mm512MaskInsertf32x4(a, 0xFFFFu, a, b, 0)
    val e = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzInsertf32x4() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mmSetrPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskzInsertf32x4(0u, a, b, 0)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzInsertf32x4(0xFFu, a, b, 0)
    val e = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 5.0f, 6.0f, 7.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256Insertf32x4() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256Insertf32x4(a, b, 1)
    val e = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskInsertf32x4() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256MaskInsertf32x4(a, 0u, a, b, 0)
    assertEqM256(r, a)
    val r1 = mm256MaskInsertf32x4(a, 0xFFu, a, b, 1)
    val e = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzInsertf32x4() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256MaskzInsertf32x4(0u, a, b, 0)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzInsertf32x4(0xFFu, a, b, 1)
    val e = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm512Castps128Ps512() {
    val a = mmSetrPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512Castps128Ps512(a)
    assertEqM128(mm512Castps512Ps128(r), a)
}

public fun testMm512Castps256Ps512() {
    val a = mm256SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm512Castps256Ps512(a)
    assertEqM256(mm512Castps512Ps256(r), a)
}

public fun testMm512Zextps128Ps512() {
    val a = mmSetrPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512Zextps128Ps512(a)
    val e = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512Zextps256Ps512() {
    val a = mm256SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm512Zextps256Ps512(a)
    val e = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512Castps512Ps128() {
    val a = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val r = mm512Castps512Ps128(a)
    val e = mmSetrPs(17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM128(r, e)
}

public fun testMm512Castps512Ps256() {
    val a = mm512SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val r = mm512Castps512Ps256(a)
    val e = mm256SetrPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    assertEqM256(r, e)
}

public fun testMm512CastpsPd() {
    val a = mm512Set1Ps(1.0f)
    val r = mm512CastpsPd(a)
    val e = mm512Set1Pd(0.007812501848093234)
    assertEqM512d(r, e)
}

public fun testMm512CastpsSi512() {
    val a = mm512Set1Ps(1.0f)
    val r = mm512CastpsSi512(a)
    val e = mm512Set1Epi32(1065353216)
    assertEqM512i(r, e)
}

public fun testMm512BroadcastdEpi32() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm512BroadcastdEpi32(a)
    val e = mm512Set1Epi32(20)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastdEpi32() {
    val src = mm512Set1Epi32(20)
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm512MaskBroadcastdEpi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskBroadcastdEpi32(src, 0xFFFFu, a)
    val e = mm512Set1Epi32(20)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzBroadcastdEpi32() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm512MaskzBroadcastdEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzBroadcastdEpi32(0xFFu, a)
    val e = mm512SetrEpi32(20, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskBroadcastdEpi32() {
    val src = mm256Set1Epi32(20)
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm256MaskBroadcastdEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskBroadcastdEpi32(src, 0xFFu, a)
    val e = mm256Set1Epi32(20)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzBroadcastdEpi32() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm256MaskzBroadcastdEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzBroadcastdEpi32(0xFFu, a)
    val e = mm256Set1Epi32(20)
    assertEqM256i(r1, e)
}

public fun testMmMaskBroadcastdEpi32() {
    val src = mmSet1Epi32(20)
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mmMaskBroadcastdEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskBroadcastdEpi32(src, 0xFu, a)
    val e = mmSet1Epi32(20)
    assertEqM128i(r1, e)
}

public fun testMmMaskzBroadcastdEpi32() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mmMaskzBroadcastdEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzBroadcastdEpi32(0xFu, a)
    val e = mmSet1Epi32(20)
    assertEqM128i(r1, e)
}

public fun testMm512BroadcastssPs() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512BroadcastssPs(a)
    val e = mm512Set1Ps(20.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskBroadcastssPs() {
    val src = mm512Set1Ps(20.0f)
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskBroadcastssPs(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskBroadcastssPs(src, 0xFFFFu, a)
    val e = mm512Set1Ps(20.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzBroadcastssPs() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskzBroadcastssPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzBroadcastssPs(0xFFu, a)
    val e = mm512SetrPs(20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskBroadcastssPs() {
    val src = mm256Set1Ps(20.0f)
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256MaskBroadcastssPs(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskBroadcastssPs(src, 0xFFu, a)
    val e = mm256Set1Ps(20.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzBroadcastssPs() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256MaskzBroadcastssPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzBroadcastssPs(0xFFu, a)
    val e = mm256Set1Ps(20.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskBroadcastssPs() {
    val src = mmSet1Ps(20.0f)
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mmMaskBroadcastssPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskBroadcastssPs(src, 0xFu, a)
    val e = mmSet1Ps(20.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzBroadcastssPs() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mmMaskzBroadcastssPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzBroadcastssPs(0xFu, a)
    val e = mmSet1Ps(20.0f)
    assertEqM128(r1, e)
}

public fun testMm512BroadcastI32x4() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm512BroadcastI32x4(a)
    val e = mm512SetEpi32(17, 18, 19, 20, 17, 18, 19, 20, 17, 18, 19, 20, 17, 18, 19, 20)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastI32x4() {
    val src = mm512Set1Epi32(20)
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm512MaskBroadcastI32x4(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskBroadcastI32x4(src, 0xFFFFu, a)
    val e = mm512SetEpi32(17, 18, 19, 20, 17, 18, 19, 20, 17, 18, 19, 20, 17, 18, 19, 20)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzBroadcastI32x4() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm512MaskzBroadcastI32x4(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzBroadcastI32x4(0xFFu, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 17, 18, 19, 20, 17, 18, 19, 20)
    assertEqM512i(r1, e)
}

public fun testMm256BroadcastI32x4() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm256BroadcastI32x4(a)
    val e = mm256SetEpi32(17, 18, 19, 20, 17, 18, 19, 20)
    assertEqM256i(r, e)
}

public fun testMm256MaskBroadcastI32x4() {
    val src = mm256Set1Epi32(20)
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm256MaskBroadcastI32x4(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskBroadcastI32x4(src, 0xFFu, a)
    val e = mm256SetEpi32(17, 18, 19, 20, 17, 18, 19, 20)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzBroadcastI32x4() {
    val a = mmSetEpi32(17, 18, 19, 20)
    val r = mm256MaskzBroadcastI32x4(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzBroadcastI32x4(0xFFu, a)
    val e = mm256SetEpi32(17, 18, 19, 20, 17, 18, 19, 20)
    assertEqM256i(r1, e)
}

public fun testMm512BroadcastF32x4() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512BroadcastF32x4(a)
    val e = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskBroadcastF32x4() {
    val src = mm512Set1Ps(20.0f)
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskBroadcastF32x4(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskBroadcastF32x4(src, 0xFFFFu, a)
    val e = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzBroadcastF32x4() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm512MaskzBroadcastF32x4(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzBroadcastF32x4(0xFFu, a)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM512(r1, e)
}

public fun testMm256BroadcastF32x4() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256BroadcastF32x4(a)
    val e = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskBroadcastF32x4() {
    val src = mm256Set1Ps(20.0f)
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256MaskBroadcastF32x4(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskBroadcastF32x4(src, 0xFFu, a)
    val e = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzBroadcastF32x4() {
    val a = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mm256MaskzBroadcastF32x4(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzBroadcastF32x4(0xFFu, a)
    val e = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 17.0f, 18.0f, 19.0f, 20.0f)
    assertEqM256(r1, e)
}

public fun testMm512MaskBlendEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(2)
    val r = mm512MaskBlendEpi32(0xFF00u, a, b)
    val e = mm512SetEpi32(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm256MaskBlendEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(2)
    val r = mm256MaskBlendEpi32(0xFFu, a, b)
    val e = mm256Set1Epi32(2)
    assertEqM256i(r, e)
}

public fun testMmMaskBlendEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(2)
    val r = mmMaskBlendEpi32(0xFu, a, b)
    val e = mmSet1Epi32(2)
    assertEqM128i(r, e)
}

public fun testMm512MaskBlendPs() {
    val a = mm512Set1Ps(1.0f)
    val b = mm512Set1Ps(2.0f)
    val r = mm512MaskBlendPs(0xFF00u, a, b)
    val e = mm512SetPs(2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM512(r, e)
}

public fun testMm256MaskBlendPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256Set1Ps(2.0f)
    val r = mm256MaskBlendPs(0xFFu, a, b)
    val e = mm256Set1Ps(2.0f)
    assertEqM256(r, e)
}

public fun testMmMaskBlendPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val r = mmMaskBlendPs(0xFu, a, b)
    val e = mmSet1Ps(2.0f)
    assertEqM128(r, e)
}

public fun testMm512UnpackhiEpi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512UnpackhiEpi32(a, b)
    val e = mm512SetEpi32(17, 1, 18, 2, 21, 5, 22, 6, 25, 9, 26, 10, 29, 13, 30, 14)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackhiEpi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskUnpackhiEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackhiEpi32(a, 0xFFFFu, a, b)
    val e = mm512SetEpi32(17, 1, 18, 2, 21, 5, 22, 6, 25, 9, 26, 10, 29, 13, 30, 14)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackhiEpi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzUnpackhiEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackhiEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 25, 9, 26, 10, 29, 13, 30, 14)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackhiEpi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm256MaskUnpackhiEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackhiEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(17, 1, 18, 2, 21, 5, 22, 6)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackhiEpi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm256MaskzUnpackhiEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackhiEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(17, 1, 18, 2, 21, 5, 22, 6)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackhiEpi32() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mmMaskUnpackhiEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackhiEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(17, 1, 18, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackhiEpi32() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mmMaskzUnpackhiEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackhiEpi32(0xFu, a, b)
    val e = mmSetEpi32(17, 1, 18, 2)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackhiPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512UnpackhiPs(a, b)
    val e = mm512SetPs(17.0f, 1.0f, 18.0f, 2.0f, 21.0f, 5.0f, 22.0f, 6.0f, 25.0f, 9.0f, 26.0f, 10.0f, 29.0f, 13.0f, 30.0f, 14.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskUnpackhiPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512MaskUnpackhiPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskUnpackhiPs(a, 0xFFFFu, a, b)
    val e = mm512SetPs(17.0f, 1.0f, 18.0f, 2.0f, 21.0f, 5.0f, 22.0f, 6.0f, 25.0f, 9.0f, 26.0f, 10.0f, 29.0f, 13.0f, 30.0f, 14.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzUnpackhiPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512MaskzUnpackhiPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzUnpackhiPs(0xFFu, a, b)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 25.0f, 9.0f, 26.0f, 10.0f, 29.0f, 13.0f, 30.0f, 14.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskUnpackhiPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm256MaskUnpackhiPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskUnpackhiPs(a, 0xFFu, a, b)
    val e = mm256SetPs(17.0f, 1.0f, 18.0f, 2.0f, 21.0f, 5.0f, 22.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzUnpackhiPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm256MaskzUnpackhiPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzUnpackhiPs(0xFFu, a, b)
    val e = mm256SetPs(17.0f, 1.0f, 18.0f, 2.0f, 21.0f, 5.0f, 22.0f, 6.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskUnpackhiPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mmMaskUnpackhiPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskUnpackhiPs(a, 0xFu, a, b)
    val e = mmSetPs(17.0f, 1.0f, 18.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzUnpackhiPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mmMaskzUnpackhiPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzUnpackhiPs(0xFu, a, b)
    val e = mmSetPs(17.0f, 1.0f, 18.0f, 2.0f)
    assertEqM128(r1, e)
}

public fun testMm512UnpackloEpi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512UnpackloEpi32(a, b)
    val e = mm512SetEpi32(19, 3, 20, 4, 23, 7, 24, 8, 27, 11, 28, 12, 31, 15, 32, 16)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackloEpi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskUnpackloEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackloEpi32(a, 0xFFFFu, a, b)
    val e = mm512SetEpi32(19, 3, 20, 4, 23, 7, 24, 8, 27, 11, 28, 12, 31, 15, 32, 16)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackloEpi32() {
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzUnpackloEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackloEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 27, 11, 28, 12, 31, 15, 32, 16)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackloEpi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm256MaskUnpackloEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackloEpi32(a, 0xFFu, a, b)
    val e = mm256SetEpi32(19, 3, 20, 4, 23, 7, 24, 8)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackloEpi32() {
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mm256SetEpi32(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm256MaskzUnpackloEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackloEpi32(0xFFu, a, b)
    val e = mm256SetEpi32(19, 3, 20, 4, 23, 7, 24, 8)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackloEpi32() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mmMaskUnpackloEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackloEpi32(a, 0xFu, a, b)
    val e = mmSetEpi32(19, 3, 20, 4)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackloEpi32() {
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mmSetEpi32(17, 18, 19, 20)
    val r = mmMaskzUnpackloEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackloEpi32(0xFu, a, b)
    val e = mmSetEpi32(19, 3, 20, 4)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackloPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512UnpackloPs(a, b)
    val e = mm512SetPs(19.0f, 3.0f, 20.0f, 4.0f, 23.0f, 7.0f, 24.0f, 8.0f, 27.0f, 11.0f, 28.0f, 12.0f, 31.0f, 15.0f, 32.0f, 16.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskUnpackloPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512MaskUnpackloPs(a, 0u, a, b)
    assertEqM512(r, a)
    val r1 = mm512MaskUnpackloPs(a, 0xFFFFu, a, b)
    val e = mm512SetPs(19.0f, 3.0f, 20.0f, 4.0f, 23.0f, 7.0f, 24.0f, 8.0f, 27.0f, 11.0f, 28.0f, 12.0f, 31.0f, 15.0f, 32.0f, 16.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzUnpackloPs() {
    val a = mm512SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val b = mm512SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f, 29.0f, 30.0f, 31.0f, 32.0f)
    val r = mm512MaskzUnpackloPs(0u, a, b)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzUnpackloPs(0xFFu, a, b)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 27.0f, 11.0f, 28.0f, 12.0f, 31.0f, 15.0f, 32.0f, 16.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskUnpackloPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm256MaskUnpackloPs(a, 0u, a, b)
    assertEqM256(r, a)
    val r1 = mm256MaskUnpackloPs(a, 0xFFu, a, b)
    val e = mm256SetPs(19.0f, 3.0f, 20.0f, 4.0f, 23.0f, 7.0f, 24.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzUnpackloPs() {
    val a = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetPs(17.0f, 18.0f, 19.0f, 20.0f, 21.0f, 22.0f, 23.0f, 24.0f)
    val r = mm256MaskzUnpackloPs(0u, a, b)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzUnpackloPs(0xFFu, a, b)
    val e = mm256SetPs(19.0f, 3.0f, 20.0f, 4.0f, 23.0f, 7.0f, 24.0f, 8.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskUnpackloPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mmMaskUnpackloPs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskUnpackloPs(a, 0xFu, a, b)
    val e = mmSetPs(19.0f, 3.0f, 20.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzUnpackloPs() {
    val a = mmSetPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetPs(17.0f, 18.0f, 19.0f, 20.0f)
    val r = mmMaskzUnpackloPs(0u, a, b)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzUnpackloPs(0xFu, a, b)
    val e = mmSetPs(19.0f, 3.0f, 20.0f, 4.0f)
    assertEqM128(r1, e)
}

public fun testMm512AlignrEpi32() {
    val a = mm512SetEpi32(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val b = mm512SetEpi32(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17)
    val r = mm512AlignrEpi32(a, b, 0)
    assertEqM512i(r, b)
    val r1 = mm512AlignrEpi32(a, b, 16)
    assertEqM512i(r1, b)
    val r2 = mm512AlignrEpi32(a, b, 1)
    val e = mm512SetEpi32(1, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18)
    assertEqM512i(r2, e)
}

public fun testMm512MaskAlignrEpi32() {
    val a = mm512SetEpi32(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val b = mm512SetEpi32(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17)
    val r = mm512MaskAlignrEpi32(a, 0u, a, b, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskAlignrEpi32(a, 0xFFFFu, a, b, 1)
    val e = mm512SetEpi32(1, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAlignrEpi32() {
    val a = mm512SetEpi32(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val b = mm512SetEpi32(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17)
    val r = mm512MaskzAlignrEpi32(0u, a, b, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAlignrEpi32(0xFFu, a, b, 1)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 25, 24, 23, 22, 21, 20, 19, 18)
    assertEqM512i(r1, e)
}

public fun testMm256AlignrEpi32() {
    val a = mm256SetEpi32(8, 7, 6, 5, 4, 3, 2, 1)
    val b = mm256SetEpi32(16, 15, 14, 13, 12, 11, 10, 9)
    val r = mm256AlignrEpi32(a, b, 0)
    assertEqM256i(r, b)
    val r1 = mm256AlignrEpi32(a, b, 1)
    val e = mm256SetEpi32(1, 16, 15, 14, 13, 12, 11, 10)
    assertEqM256i(r1, e)
}

public fun testMm256MaskAlignrEpi32() {
    val a = mm256SetEpi32(8, 7, 6, 5, 4, 3, 2, 1)
    val b = mm256SetEpi32(16, 15, 14, 13, 12, 11, 10, 9)
    val r = mm256MaskAlignrEpi32(a, 0u, a, b, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskAlignrEpi32(a, 0xFFu, a, b, 1)
    val e = mm256SetEpi32(1, 16, 15, 14, 13, 12, 11, 10)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAlignrEpi32() {
    val a = mm256SetEpi32(8, 7, 6, 5, 4, 3, 2, 1)
    val b = mm256SetEpi32(16, 15, 14, 13, 12, 11, 10, 9)
    val r = mm256MaskzAlignrEpi32(0u, a, b, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAlignrEpi32(0xFFu, a, b, 1)
    val e = mm256SetEpi32(1, 16, 15, 14, 13, 12, 11, 10)
    assertEqM256i(r1, e)
}

public fun testMmAlignrEpi32() {
    val a = mmSetEpi32(4, 3, 2, 1)
    val b = mmSetEpi32(8, 7, 6, 5)
    val r = mmAlignrEpi32(a, b, 0)
    assertEqM128i(r, b)
    val r1 = mmAlignrEpi32(a, b, 1)
    val e = mmSetEpi32(1, 8, 7, 6)
    assertEqM128i(r1, e)
}

public fun testMmMaskAlignrEpi32() {
    val a = mmSetEpi32(4, 3, 2, 1)
    val b = mmSetEpi32(8, 7, 6, 5)
    val r = mmMaskAlignrEpi32(a, 0u, a, b, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskAlignrEpi32(a, 0xFu, a, b, 1)
    val e = mmSetEpi32(1, 8, 7, 6)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAlignrEpi32() {
    val a = mmSetEpi32(4, 3, 2, 1)
    val b = mmSetEpi32(8, 7, 6, 5)
    val r = mmMaskzAlignrEpi32(0u, a, b, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAlignrEpi32(0xFu, a, b, 1)
    val e = mmSetEpi32(1, 8, 7, 6)
    assertEqM128i(r1, e)
}

public fun testMm512AndEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512AndEpi32(a, b)
    val e = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3)
    assertEqM512i(r, e)
}

public fun testMm512MaskAndEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512MaskAndEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAndEpi32(a, 0x7FFFu, a, b)
    val e = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAndEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512MaskzAndEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAndEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAndEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskAndEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAndEpi32(a, 0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAndEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskzAndEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAndEpi32(0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskAndEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskAndEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAndEpi32(a, 0xFu, a, b)
    val e = mmSet1Epi32(1 shl 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAndEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskzAndEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAndEpi32(0xFu, a, b)
    val e = mmSet1Epi32(1 shl 1)
    assertEqM128i(r1, e)
}

public fun testMm512AndSi512() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512AndSi512(a, b)
    val e = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3)
    assertEqM512i(r, e)
}

public fun testMm512OrEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512OrEpi32(a, b)
    val e = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3 or 1 shl 4)
    assertEqM512i(r, e)
}

public fun testMm512MaskOrEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512MaskOrEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskOrEpi32(a, 0xFFFFu, a, b)
    val e = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3 or 1 shl 4)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzOrEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512MaskzOrEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzOrEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3 or 1 shl 4)
    assertEqM512i(r1, e)
}

public fun testMm256OrEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256OrEpi32(a, b)
    val e = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    assertEqM256i(r, e)
}

public fun testMm256MaskOrEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskOrEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskOrEpi32(a, 0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzOrEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskzOrEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzOrEpi32(0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    assertEqM256i(r1, e)
}

public fun testMmOrEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmOrEpi32(a, b)
    val e = mmSet1Epi32(1 shl 1 or 1 shl 2)
    assertEqM128i(r, e)
}

public fun testMmMaskOrEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskOrEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskOrEpi32(a, 0xFu, a, b)
    val e = mmSet1Epi32(1 shl 1 or 1 shl 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzOrEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskzOrEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzOrEpi32(0xFu, a, b)
    val e = mmSet1Epi32(1 shl 1 or 1 shl 2)
    assertEqM128i(r1, e)
}

public fun testMm512OrSi512() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512OrSi512(a, b)
    val e = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3 or 1 shl 4)
    assertEqM512i(r, e)
}

public fun testMm512XorEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512XorEpi32(a, b)
    val e = mm512SetEpi32(1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 4)
    assertEqM512i(r, e)
}

public fun testMm512MaskXorEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512MaskXorEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskXorEpi32(a, 0x7FFFu, a, b)
    val e = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 4)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzXorEpi32() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512MaskzXorEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzXorEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 4)
    assertEqM512i(r1, e)
}

public fun testMm256XorEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256XorEpi32(a, b)
    val e = mm256Set1Epi32(1 shl 2)
    assertEqM256i(r, e)
}

public fun testMm256MaskXorEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskXorEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskXorEpi32(a, 0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzXorEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskzXorEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzXorEpi32(0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 2)
    assertEqM256i(r1, e)
}

public fun testMmXorEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmXorEpi32(a, b)
    val e = mmSet1Epi32(1 shl 2)
    assertEqM128i(r, e)
}

public fun testMmMaskXorEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskXorEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskXorEpi32(a, 0xFu, a, b)
    val e = mmSet1Epi32(1 shl 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzXorEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskzXorEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzXorEpi32(0xFu, a, b)
    val e = mmSet1Epi32(1 shl 2)
    assertEqM128i(r1, e)
}

public fun testMm512XorSi512() {
    val a = mm512SetEpi32(1 shl 1 or 1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 3)
    val b = mm512SetEpi32(1 shl 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4)
    val r = mm512XorSi512(a, b)
    val e = mm512SetEpi32(1 shl 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 shl 1 or 1 shl 4)
    assertEqM512i(r, e)
}

public fun testMm512AndnotEpi32() {
    val a = mm512Set1Epi32(0)
    val b = mm512Set1Epi32(1 shl 3 or 1 shl 4)
    val r = mm512AndnotEpi32(a, b)
    val e = mm512Set1Epi32(1 shl 3 or 1 shl 4)
    assertEqM512i(r, e)
}

public fun testMm512MaskAndnotEpi32() {
    val a = mm512Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm512Set1Epi32(1 shl 3 or 1 shl 4)
    val r = mm512MaskAndnotEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAndnotEpi32(a, 0xFFFFu, a, b)
    val e = mm512Set1Epi32(1 shl 3 or 1 shl 4)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAndnotEpi32() {
    val a = mm512Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm512Set1Epi32(1 shl 3 or 1 shl 4)
    val r = mm512MaskzAndnotEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAndnotEpi32(0xFFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4, 1 shl 3 or 1 shl 4)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAndnotEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 3 or 1 shl 4)
    val r = mm256MaskAndnotEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAndnotEpi32(a, 0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 3 or 1 shl 4)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAndnotEpi32() {
    val a = mm256Set1Epi32(1 shl 1 or 1 shl 2)
    val b = mm256Set1Epi32(1 shl 3 or 1 shl 4)
    val r = mm256MaskzAndnotEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAndnotEpi32(0xFFu, a, b)
    val e = mm256Set1Epi32(1 shl 3 or 1 shl 4)
    assertEqM256i(r1, e)
}

public fun testMmMaskAndnotEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 3 or 1 shl 4)
    val r = mmMaskAndnotEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAndnotEpi32(a, 0xFu, a, b)
    val e = mmSet1Epi32(1 shl 3 or 1 shl 4)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAndnotEpi32() {
    val a = mmSet1Epi32(1 shl 1 or 1 shl 2)
    val b = mmSet1Epi32(1 shl 3 or 1 shl 4)
    val r = mmMaskzAndnotEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAndnotEpi32(0xFu, a, b)
    val e = mmSet1Epi32(1 shl 3 or 1 shl 4)
    assertEqM128i(r1, e)
}

public fun testCvtmask16U32() {
    val a: Mmask16 = 0xCC33u
    val r = cvtmask16U32(a)
    val e: UInt = 0xCC33u
    check(r == e)
}

public fun testCvtu32Mask16() {
    val a: UInt = 0xCC33u
    val r = cvtu32Mask16(a)
    val e: Mmask16 = 0xCC33u
    check(r == e)
}

public fun testMm512Kand() {
    val a: UShort = 0xCC33u
    val b: UShort = 0xCC33u
    val r = mm512Kand(a, b)
    val e: UShort = 0xCC33u
    check(r == e)
}

public fun testKandMask16() {
    val a: UShort = 0xCC33u
    val b: UShort = 0xCC33u
    val r = kandMask16(a, b)
    val e: UShort = 0xCC33u
    check(r == e)
}

public fun testMm512Kor() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kor(a, b)
    val e: UShort = 0xEE3Bu
    check(r == e)
}

public fun testKorMask16() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = korMask16(a, b)
    val e: UShort = 0xEE3Bu
    check(r == e)
}

public fun testMm512Kxor() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kxor(a, b)
    val e: UShort = 0xE238u
    check(r == e)
}

public fun testKxorMask16() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = kxorMask16(a, b)
    val e: UShort = 0xE238u
    check(r == e)
}

public fun testMm512Knot() {
    val a: UShort = 0xCC33u
    val r = mm512Knot(a)
    val e: UShort = 0x33CCu
    check(r == e)
}

public fun testKnotMask16() {
    val a: UShort = 0xCC33u
    val r = knotMask16(a)
    val e: UShort = 0x33CCu
    check(r == e)
}

public fun testMm512Kandn() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kandn(a, b)
    val e: UShort = 0x2208u
    check(r == e)
}

public fun testKandnMask16() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = kandnMask16(a, b)
    val e: UShort = 0x2208u
    check(r == e)
}

public fun testMm512Kxnor() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kxnor(a, b)
    val e: UShort = 0x1DC7u
    check(r == e)
}

public fun testKxnorMask16() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = kxnorMask16(a, b)
    val e: UShort = 0x1DC7u
    check(r == e)
}

public fun testKortestMask16U8() {
    val a: Mmask16 = 0x6969u
    val b: Mmask16 = 0xB6B6u
    val localStorageL58043C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var allOnes by localStorageL58043C8
    val r = kortestMask16U8(a, b, RustMutableReference(localStorageL58043C8.asMutPtr(), localStorageL58043C8.layout))
    check(r == 0u.toUByte())
    check(allOnes == 1u.toUByte())
}

public fun testKortestcMask16U8() {
    val a: Mmask16 = 0x6969u
    val b: Mmask16 = 0xB6B6u
    val r = kortestcMask16U8(a, b)
    check(r == 1u.toUByte())
}

public fun testKortestzMask16U8() {
    val a: Mmask16 = 0x6969u
    val b: Mmask16 = 0xB6B6u
    val r = kortestzMask16U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKshiftliMask16() {
    val a: Mmask16 = 0x96C3u
    val r = kshiftliMask16(a, 3)
    val e: Mmask16 = 0xB618u
    check(r == e)
    val r1 = kshiftliMask16(a, 15)
    val e1: Mmask16 = 0x8000u
    check(r1 == e1)
    val r2 = kshiftliMask16(a, 16)
    val e2: Mmask16 = 0x0u
    check(r2 == e2)
    val r3 = kshiftliMask16(a, 17)
    val e3: Mmask16 = 0x0u
    check(r3 == e3)
}

public fun testKshiftriMask16() {
    val a: Mmask16 = 0xA93Cu
    val r = kshiftriMask16(a, 3)
    val e: Mmask16 = 0x1527u
    check(r == e)
    val r1 = kshiftriMask16(a, 15)
    val e1: Mmask16 = 0x1u
    check(r1 == e1)
    val r2 = kshiftriMask16(a, 16)
    val e2: Mmask16 = 0x0u
    check(r2 == e2)
    val r3 = kshiftriMask16(a, 17)
    val e3: Mmask16 = 0x0u
    check(r3 == e3)
}

public fun testLoadMask16() {
    val r = loadMask16(a)
    val e: Mmask16 = 0x96C3u
    check(r == e)
}

public fun testStoreMask16() {
    val a: Mmask16 = 0x693Cu
    val localStorageL58116C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var r by localStorageL58116C8
    run {
        storeMask16(RustMutableReference(localStorageL58116C8.asMutPtr(), localStorageL58116C8.layout), a)
    }
    val e: Mmask16 = 0x693Cu
    check(r == e)
}

public fun testMm512Kmov() {
    val a: UShort = 0xCC33u
    val r = mm512Kmov(a)
    val e: UShort = 0xCC33u
    check(r == e)
}

public fun testMm512Int2mask() {
    val a: Int = 0xCC33
    val r = mm512Int2mask(a)
    val e: UShort = 0xCC33u
    check(r == e)
}

public fun testMm512Mask2int() {
    val k1: Mmask16 = 0xCC33u
    val r = mm512Mask2int(k1)
    val e: Int = 0xCC33
    check(r == e)
}

public fun testMm512Kunpackb() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kunpackb(a, b)
    val e: UShort = 0x330Bu
    check(r == e)
}

public fun testMm512Kortestc() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kortestc(a, b)
    check(r == 0)
    val b1: UShort = 0xFFFFu
    val r1 = mm512Kortestc(a, b1)
    check(r1 == 1)
}

public fun testMm512Kortestz() {
    val a: UShort = 0xCC33u
    val b: UShort = 0x2E0Bu
    val r = mm512Kortestz(a, b)
    check(r == 0)
    val r1 = mm512Kortestz(0u, 0u)
    check(r1 == 1)
}

public fun testMm512TestEpi32Mask() {
    val a = mm512Set1Epi32(1 shl 0)
    val b = mm512Set1Epi32(1 shl 0 or 1 shl 1)
    val r = mm512TestEpi32Mask(a, b)
    val e: Mmask16 = 0xFFFFu
    check(r == e)
}

public fun testMm512MaskTestEpi32Mask() {
    val a = mm512Set1Epi32(1 shl 0)
    val b = mm512Set1Epi32(1 shl 0 or 1 shl 1)
    val r = mm512MaskTestEpi32Mask(0u, a, b)
    check(r == 0u.toUShort())
    val r1 = mm512MaskTestEpi32Mask(0xFFFFu, a, b)
    val e: Mmask16 = 0xFFFFu
    check(r1 == e)
}

public fun testMm256TestEpi32Mask() {
    val a = mm256Set1Epi32(1 shl 0)
    val b = mm256Set1Epi32(1 shl 0 or 1 shl 1)
    val r = mm256TestEpi32Mask(a, b)
    val e: Mmask8 = 0xFFu
    check(r == e)
}

public fun testMm256MaskTestEpi32Mask() {
    val a = mm256Set1Epi32(1 shl 0)
    val b = mm256Set1Epi32(1 shl 0 or 1 shl 1)
    val r = mm256MaskTestEpi32Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mm256MaskTestEpi32Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFFu
    check(r1 == e)
}

public fun testMmTestEpi32Mask() {
    val a = mmSet1Epi32(1 shl 0)
    val b = mmSet1Epi32(1 shl 0 or 1 shl 1)
    val r = mmTestEpi32Mask(a, b)
    val e: Mmask8 = 0xFu
    check(r == e)
}

public fun testMmMaskTestEpi32Mask() {
    val a = mmSet1Epi32(1 shl 0)
    val b = mmSet1Epi32(1 shl 0 or 1 shl 1)
    val r = mmMaskTestEpi32Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mmMaskTestEpi32Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFu
    check(r1 == e)
}

public fun testMm512TestnEpi32Mask() {
    val a = mm512Set1Epi32(1 shl 0)
    val b = mm512Set1Epi32(1 shl 0 or 1 shl 1)
    val r = mm512TestnEpi32Mask(a, b)
    val e: Mmask16 = 0x0u
    check(r == e)
}

public fun testMm512MaskTestnEpi32Mask() {
    val a = mm512Set1Epi32(1 shl 0)
    val b = mm512Set1Epi32(1 shl 1)
    val r = mm512MaskTestEpi32Mask(0u, a, b)
    check(r == 0u.toUShort())
    val r1 = mm512MaskTestnEpi32Mask(0xFFFFu, a, b)
    val e: Mmask16 = 0xFFFFu
    check(r1 == e)
}

public fun testMm256TestnEpi32Mask() {
    val a = mm256Set1Epi32(1 shl 0)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256TestnEpi32Mask(a, b)
    val e: Mmask8 = 0xFFu
    check(r == e)
}

public fun testMm256MaskTestnEpi32Mask() {
    val a = mm256Set1Epi32(1 shl 0)
    val b = mm256Set1Epi32(1 shl 1)
    val r = mm256MaskTestEpi32Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mm256MaskTestnEpi32Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFFu
    check(r1 == e)
}

public fun testMmTestnEpi32Mask() {
    val a = mmSet1Epi32(1 shl 0)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmTestnEpi32Mask(a, b)
    val e: Mmask8 = 0xFu
    check(r == e)
}

public fun testMmMaskTestnEpi32Mask() {
    val a = mmSet1Epi32(1 shl 0)
    val b = mmSet1Epi32(1 shl 1)
    val r = mmMaskTestEpi32Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mmMaskTestnEpi32Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFu
    check(r1 == e)
}

// Inline asm (for non-temporal store), which is not supported by Miri
public fun testMm512StreamPs() {
    open class Memory(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })
    }
    val a = mm512Set1Ps(7.0f)
    val mem = Memory(data = LongArray(16) { -1.0 })
    run {
        mm512StreamPs(mutPtrFromRustCast(mem.data[0]), a)
    }
    mmSfence()
    for (i in 0 until 16) {
        check(mem.data[i] == getM512(a, i))
    }
}

// Inline asm (for non-temporal store), which is not supported by Miri
public fun testMm512StreamPd() {
    open class Memory(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })
    }
    val a = mm512Set1Pd(7.0)
    val mem = Memory(data = LongArray(8) { -1.0 })
    run {
        mm512StreamPd(mutPtrFromRustCast(mem.data[0]), a)
    }
    mmSfence()
    for (i in 0 until 8) {
        check(mem.data[i] == getM512d(a, i))
    }
}

// Inline asm (for non-temporal store), which is not supported by Miri
public fun testMm512StreamSi512() {
    open class Memory(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout(),
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })
    }
    val a = mm512Set1Epi32(7)
    val mem = Memory(data = LongArray(8) { -1L })
    run {
        mm512StreamSi512(mem.data.asMutPtr(), a)
    }
    mmSfence()
    for (i in 0 until 8) {
        check(mem.data[i] == getM512i(a, i))
    }
}

public fun testMm512StreamLoadSi512() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512StreamLoadSi512(constPtrFromRustCast(addrOf(a)))
    assertEqM512i(a, r)
}

public fun testMm512ReduceAddEpi32() {
    val a = mm512Set1Epi32(1)
    val e: Int = mm512ReduceAddEpi32(a)
    check(16 == e)
}

public fun testMm512MaskReduceAddEpi32() {
    val a = mm512Set1Epi32(1)
    val e: Int = mm512MaskReduceAddEpi32(0xFF00u, a)
    check(8 == e)
}

public fun testMm512ReduceAddPs() {
    val a = mm512Set1Ps(1.0f)
    val e: Float = mm512ReduceAddPs(a)
    check(16.0f.toFloat() == e)
}

public fun testMm512MaskReduceAddPs() {
    val a = mm512Set1Ps(1.0f)
    val e: Float = mm512MaskReduceAddPs(0xFF00u, a)
    check(8.0f.toFloat() == e)
}

public fun testMm512ReduceMulEpi32() {
    val a = mm512Set1Epi32(2)
    val e: Int = mm512ReduceMulEpi32(a)
    check(65536 == e)
}

public fun testMm512MaskReduceMulEpi32() {
    val a = mm512Set1Epi32(2)
    val e: Int = mm512MaskReduceMulEpi32(0xFF00u, a)
    check(256 == e)
}

public fun testMm512ReduceMulPs() {
    val a = mm512Set1Ps(2.0f)
    val e: Float = mm512ReduceMulPs(a)
    check(65536.0f.toFloat() == e)
}

public fun testMm512MaskReduceMulPs() {
    val a = mm512Set1Ps(2.0f)
    val e: Float = mm512MaskReduceMulPs(0xFF00u, a)
    check(256.0f.toFloat() == e)
}

public fun testMm512ReduceMaxEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Int = mm512ReduceMaxEpi32(a)
    check(15 == e)
}

public fun testMm512MaskReduceMaxEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Int = mm512MaskReduceMaxEpi32(0xFF00u, a)
    check(7 == e)
}

public fun testMm512ReduceMaxEpu32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UInt = mm512ReduceMaxEpu32(a)
    check(15u == e)
}

public fun testMm512MaskReduceMaxEpu32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UInt = mm512MaskReduceMaxEpu32(0xFF00u, a)
    check(7u == e)
}

public fun testMm512ReduceMaxPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val e: Float = mm512ReduceMaxPs(a)
    check(15.0f.toFloat() == e)
}

public fun testMm512MaskReduceMaxPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val e: Float = mm512MaskReduceMaxPs(0xFF00u, a)
    check(7.0f.toFloat() == e)
}

public fun testMm512ReduceMinEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Int = mm512ReduceMinEpi32(a)
    check(0 == e)
}

public fun testMm512MaskReduceMinEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Int = mm512MaskReduceMinEpi32(0xFF00u, a)
    check(0 == e)
}

public fun testMm512ReduceMinEpu32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UInt = mm512ReduceMinEpu32(a)
    check(0u == e)
}

public fun testMm512MaskReduceMinEpu32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UInt = mm512MaskReduceMinEpu32(0xFF00u, a)
    check(0u == e)
}

public fun testMm512ReduceMinPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val e: Float = mm512ReduceMinPs(a)
    check(0.0f.toFloat() == e)
}

public fun testMm512MaskReduceMinPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val e: Float = mm512MaskReduceMinPs(0xFF00u, a)
    check(0.0f.toFloat() == e)
}

public fun testMm512ReduceAndEpi32() {
    val a = mm512SetEpi32(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e: Int = mm512ReduceAndEpi32(a)
    check(0 == e)
}

public fun testMm512MaskReduceAndEpi32() {
    val a = mm512SetEpi32(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e: Int = mm512MaskReduceAndEpi32(0xFF00u, a)
    check(1 == e)
}

public fun testMm512ReduceOrEpi32() {
    val a = mm512SetEpi32(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e: Int = mm512ReduceOrEpi32(a)
    check(3 == e)
}

public fun testMm512MaskReduceOrEpi32() {
    val a = mm512SetEpi32(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e: Int = mm512MaskReduceAndEpi32(0xFF00u, a)
    check(1 == e)
}

public fun testMm512MaskCompressEpi32() {
    val src = mm512Set1Epi32(200)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskCompressEpi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCompressEpi32(src, 0x5555u, a)
    val e = mm512SetEpi32(200, 200, 200, 200, 200, 200, 200, 200, 1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCompressEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCompressEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCompressEpi32(0x5555u, a)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCompressEpi32() {
    val src = mm256Set1Epi32(200)
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskCompressEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCompressEpi32(src, 0x55u, a)
    val e = mm256SetEpi32(200, 200, 200, 200, 1, 3, 5, 7)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCompressEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzCompressEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCompressEpi32(0x55u, a)
    val e = mm256SetEpi32(0, 0, 0, 0, 1, 3, 5, 7)
    assertEqM256i(r1, e)
}

public fun testMmMaskCompressEpi32() {
    val src = mmSet1Epi32(200)
    val a = mmSetEpi32(0, 1, 2, 3)
    val r = mmMaskCompressEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCompressEpi32(src, 0x5u, a)
    val e = mmSetEpi32(200, 200, 1, 3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCompressEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val r = mmMaskzCompressEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCompressEpi32(0x5u, a)
    val e = mmSetEpi32(0, 0, 1, 3)
    assertEqM128i(r1, e)
}

public fun testMm512MaskCompressPs() {
    val src = mm512Set1Ps(200.0f)
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskCompressPs(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskCompressPs(src, 0x5555u, a)
    val e = mm512SetPs(200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 1.0f, 3.0f, 5.0f, 7.0f, 9.0f, 11.0f, 13.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzCompressPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskzCompressPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzCompressPs(0x5555u, a)
    val e = mm512SetPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 3.0f, 5.0f, 7.0f, 9.0f, 11.0f, 13.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskCompressPs() {
    val src = mm256Set1Ps(200.0f)
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskCompressPs(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskCompressPs(src, 0x55u, a)
    val e = mm256SetPs(200.0f, 200.0f, 200.0f, 200.0f, 1.0f, 3.0f, 5.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzCompressPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskzCompressPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzCompressPs(0x55u, a)
    val e = mm256SetPs(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 3.0f, 5.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskCompressPs() {
    val src = mmSet1Ps(200.0f)
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r = mmMaskCompressPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskCompressPs(src, 0x5u, a)
    val e = mmSetPs(200.0f, 200.0f, 1.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzCompressPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r = mmMaskzCompressPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzCompressPs(0x5u, a)
    val e = mmSetPs(0.0f, 0.0f, 1.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMm512MaskCompressstoreuEpi32() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val localStorageL58677C8 = AddressableArrayLocal.fromIntArray(IntArray(16) { 0 })
    var r by localStorageL58677C8
    run {
        mm512MaskCompressstoreuEpi32(localStorageL58677C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(IntArray(16) { 0 }))
    run {
        mm512MaskCompressstoreuEpi32(localStorageL58677C8.elementMutPtr(0), 0xF0CAu, a)
    }
    check((r).contentEquals(listOf(2, 4, 7, 8, 13, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0)))
}

public fun testMm256MaskCompressstoreuEpi32() {
    val a = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val localStorageL58691C8 = AddressableArrayLocal.fromIntArray(IntArray(8) { 0 })
    var r by localStorageL58691C8
    run {
        mm256MaskCompressstoreuEpi32(localStorageL58691C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(IntArray(8) { 0 }))
    run {
        mm256MaskCompressstoreuEpi32(localStorageL58691C8.elementMutPtr(0), 0xCAu, a)
    }
    check((r).contentEquals(listOf(2, 4, 7, 8, 0, 0, 0, 0)))
}

public fun testMmMaskCompressstoreuEpi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val localStorageL58705C8 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    var r by localStorageL58705C8
    run {
        mmMaskCompressstoreuEpi32(localStorageL58705C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(IntArray(4) { 0 }))
    run {
        mmMaskCompressstoreuEpi32(localStorageL58705C8.elementMutPtr(0), 0xBu, a)
    }
    check((r).contentEquals(listOf(1, 2, 4, 0)))
}

public fun testMm512MaskCompressstoreuEpi64() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val localStorageL58719C8 = AddressableArrayLocal.fromLongArray(LongArray(8) { 0L })
    var r by localStorageL58719C8
    run {
        mm512MaskCompressstoreuEpi64(localStorageL58719C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(LongArray(8) { 0L }))
    run {
        mm512MaskCompressstoreuEpi64(localStorageL58719C8.elementMutPtr(0), 0xCAu, a)
    }
    check(r == listOf(2, 4, 7, 8, 0, 0, 0, 0))
}

public fun testMm256MaskCompressstoreuEpi64() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val localStorageL58733C8 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0L })
    var r by localStorageL58733C8
    run {
        mm256MaskCompressstoreuEpi64(localStorageL58733C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(LongArray(4) { 0L }))
    run {
        mm256MaskCompressstoreuEpi64(localStorageL58733C8.elementMutPtr(0), 0xBu, a)
    }
    check(r == listOf(1, 2, 4, 0))
}

public fun testMmMaskCompressstoreuEpi64() {
    val a = mmSetrEpi64x(1, 2)
    val localStorageL58747C8 = AddressableArrayLocal.fromLongArray(LongArray(2) { 0L })
    var r by localStorageL58747C8
    run {
        mmMaskCompressstoreuEpi64(localStorageL58747C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(LongArray(2) { 0L }))
    run {
        mmMaskCompressstoreuEpi64(localStorageL58747C8.elementMutPtr(0), 0x2u, a)
    }
    check(r == listOf(2, 0))
}

public fun testMm512MaskCompressstoreuPs() {
    val a = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = MutableList(16) { 0.0f }
    run {
        mm512MaskCompressstoreuPs(r.asMutPtr(), 0u, a)
    }
    check(r == List(16) { 0.0f })
    run {
        mm512MaskCompressstoreuPs(r.asMutPtr(), 0xF0CAu, a)
    }
    check(r == listOf(2.0f, 4.0f, 7.0f, 8.0f, 13.0f, 14.0f, 15.0f, 16.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f))
}

public fun testMm256MaskCompressstoreuPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = MutableList(8) { 0.0f }
    run {
        mm256MaskCompressstoreuPs(r.asMutPtr(), 0u, a)
    }
    check(r == List(8) { 0.0f })
    run {
        mm256MaskCompressstoreuPs(r.asMutPtr(), 0xCAu, a)
    }
    check(r == listOf(2.0f, 4.0f, 7.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f))
}

public fun testMmMaskCompressstoreuPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = MutableList(4) { 0.0 }
    run {
        mmMaskCompressstoreuPs(r.asMutPtr(), 0u, a)
    }
    check(r == List(4) { 0.0 })
    run {
        mmMaskCompressstoreuPs(r.asMutPtr(), 0xBu, a)
    }
    check(r == listOf(1.0f, 2.0f, 4.0f, 0.0f))
}

public fun testMm512MaskCompressstoreuPd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = MutableList(8) { 0.0 }
    run {
        mm512MaskCompressstoreuPd(r.asMutPtr(), 0u, a)
    }
    check(r == List(8) { 0.0 })
    run {
        mm512MaskCompressstoreuPd(r.asMutPtr(), 0xCAu, a)
    }
    check(r == listOf(2.0, 4.0, 7.0, 8.0, 0.0, 0.0, 0.0, 0.0))
}

public fun testMm256MaskCompressstoreuPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = MutableList(4) { 0.0 }
    run {
        mm256MaskCompressstoreuPd(r.asMutPtr(), 0u, a)
    }
    check(r == List(4) { 0.0 })
    run {
        mm256MaskCompressstoreuPd(r.asMutPtr(), 0xBu, a)
    }
    check(r == listOf(1.0, 2.0, 4.0, 0.0))
}

public fun testMmMaskCompressstoreuPd() {
    val a = mmSetrPd(1.0, 2.0)
    val r = MutableList(2) { 0.0 }
    run {
        mmMaskCompressstoreuPd(r.asMutPtr(), 0u, a)
    }
    check(r == List(2) { 0.0 })
    run {
        mmMaskCompressstoreuPd(r.asMutPtr(), 0x2u, a)
    }
    check(r == listOf(2.0, 0.0))
}

public fun testMm512MaskExpandEpi32() {
    val src = mm512Set1Epi32(200)
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskExpandEpi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskExpandEpi32(src, 0x5555u, a)
    val e = mm512SetEpi32(200, 8, 200, 9, 200, 10, 200, 11, 200, 12, 200, 13, 200, 14, 200, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzExpandEpi32() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzExpandEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzExpandEpi32(0x5555u, a)
    val e = mm512SetEpi32(0, 8, 0, 9, 0, 10, 0, 11, 0, 12, 0, 13, 0, 14, 0, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskExpandEpi32() {
    val src = mm256Set1Epi32(200)
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskExpandEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskExpandEpi32(src, 0x55u, a)
    val e = mm256SetEpi32(200, 4, 200, 5, 200, 6, 200, 7)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzExpandEpi32() {
    val a = mm256SetEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256MaskzExpandEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzExpandEpi32(0x55u, a)
    val e = mm256SetEpi32(0, 4, 0, 5, 0, 6, 0, 7)
    assertEqM256i(r1, e)
}

public fun testMmMaskExpandEpi32() {
    val src = mmSet1Epi32(200)
    val a = mmSetEpi32(0, 1, 2, 3)
    val r = mmMaskExpandEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskExpandEpi32(src, 0x5u, a)
    val e = mmSetEpi32(200, 2, 200, 3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzExpandEpi32() {
    val a = mmSetEpi32(0, 1, 2, 3)
    val r = mmMaskzExpandEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzExpandEpi32(0x5u, a)
    val e = mmSetEpi32(0, 2, 0, 3)
    assertEqM128i(r1, e)
}

public fun testMm512MaskExpandPs() {
    val src = mm512Set1Ps(200.0f)
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskExpandPs(src, 0u, a)
    assertEqM512(r, src)
    val r1 = mm512MaskExpandPs(src, 0x5555u, a)
    val e = mm512SetPs(200.0f, 8.0f, 200.0f, 9.0f, 200.0f, 10.0f, 200.0f, 11.0f, 200.0f, 12.0f, 200.0f, 13.0f, 200.0f, 14.0f, 200.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm512MaskzExpandPs() {
    val a = mm512SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f)
    val r = mm512MaskzExpandPs(0u, a)
    assertEqM512(r, mm512SetzeroPs())
    val r1 = mm512MaskzExpandPs(0x5555u, a)
    val e = mm512SetPs(0.0f, 8.0f, 0.0f, 9.0f, 0.0f, 10.0f, 0.0f, 11.0f, 0.0f, 12.0f, 0.0f, 13.0f, 0.0f, 14.0f, 0.0f, 15.0f)
    assertEqM512(r1, e)
}

public fun testMm256MaskExpandPs() {
    val src = mm256Set1Ps(200.0f)
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskExpandPs(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm256MaskExpandPs(src, 0x55u, a)
    val e = mm256SetPs(200.0f, 4.0f, 200.0f, 5.0f, 200.0f, 6.0f, 200.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskzExpandPs() {
    val a = mm256SetPs(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f)
    val r = mm256MaskzExpandPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm256MaskzExpandPs(0x55u, a)
    val e = mm256SetPs(0.0f, 4.0f, 0.0f, 5.0f, 0.0f, 6.0f, 0.0f, 7.0f)
    assertEqM256(r1, e)
}

public fun testMmMaskExpandPs() {
    val src = mmSet1Ps(200.0f)
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r = mmMaskExpandPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskExpandPs(src, 0x5u, a)
    val e = mmSetPs(200.0f, 2.0f, 200.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzExpandPs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r = mmMaskzExpandPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzExpandPs(0x5u, a)
    val e = mmSetPs(0.0f, 2.0f, 0.0f, 3.0f)
    assertEqM128(r1, e)
}

public fun testMm512LoaduEpi32() {
    val a = listOf(4, 3, 2, 5, 8, 9, 64, 50, -4, -3, -2, -5, -8, -9, -64, -50)
    val p = a.asPtr()
    val r = mm512LoaduEpi32(blackBox(p))
    val e = mm512SetrEpi32(4, 3, 2, 5, 8, 9, 64, 50, -4, -3, -2, -5, -8, -9, -64, -50)
    assertEqM512i(r, e)
}

public fun testMm256LoaduEpi32() {
    val a = listOf(4, 3, 2, 5, 8, 9, 64, 50)
    val p = a.asPtr()
    val r = mm256LoaduEpi32(blackBox(p))
    val e = mm256SetrEpi32(4, 3, 2, 5, 8, 9, 64, 50)
    assertEqM256i(r, e)
}

public fun testMmLoaduEpi32() {
    val a = listOf(4, 3, 2, 5)
    val p = a.asPtr()
    val r = mmLoaduEpi32(blackBox(p))
    val e = mmSetrEpi32(4, 3, 2, 5)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi32StoreuEpi16() {
    val a = mm512Set1Epi32(9)
    val localStorageL59020C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL59020C8
    run {
        mm512MaskCvtepi32StoreuEpi16((MutPtr(localStorageL59020C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mm256Set1Epi16(9)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtepi32StoreuEpi16() {
    val a = mm256Set1Epi32(9)
    val localStorageL59031C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59031C8
    run {
        mm256MaskCvtepi32StoreuEpi16((MutPtr(localStorageL59031C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSet1Epi16(9)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi32StoreuEpi16() {
    val a = mmSet1Epi32(9)
    val localStorageL59042C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59042C8
    run {
        mmMaskCvtepi32StoreuEpi16((MutPtr(localStorageL59042C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, 9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi32StoreuEpi16() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val localStorageL59053C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL59053C8
    run {
        mm512MaskCvtsepi32StoreuEpi16((MutPtr(localStorageL59053C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mm256Set1Epi16(Short.MAX_VALUE)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtsepi32StoreuEpi16() {
    val a = mm256Set1Epi32(Int.MAX_VALUE)
    val localStorageL59068C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59068C8
    run {
        mm256MaskCvtsepi32StoreuEpi16((MutPtr(localStorageL59068C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSet1Epi16(Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi32StoreuEpi16() {
    val a = mmSet1Epi32(Int.MAX_VALUE)
    val localStorageL59079C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59079C8
    run {
        mmMaskCvtsepi32StoreuEpi16((MutPtr(localStorageL59079C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi32StoreuEpi16() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val localStorageL59090C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL59090C8
    run {
        mm512MaskCvtusepi32StoreuEpi16((MutPtr(localStorageL59090C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mm256Set1Epi16(UShort.MAX_VALUE.toShort())
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtusepi32StoreuEpi16() {
    val a = mm256Set1Epi32(Int.MAX_VALUE)
    val localStorageL59105C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59105C8
    run {
        mm256MaskCvtusepi32StoreuEpi16((MutPtr(localStorageL59105C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSet1Epi16(UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi32StoreuEpi16() {
    val a = mmSet1Epi32(Int.MAX_VALUE)
    val localStorageL59116C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59116C8
    run {
        mmMaskCvtusepi32StoreuEpi16((MutPtr(localStorageL59116C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi32StoreuEpi8() {
    val a = mm512Set1Epi32(9)
    val localStorageL59136C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59136C8
    run {
        mm512MaskCvtepi32StoreuEpi8((MutPtr(localStorageL59136C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mmSet1Epi8(9)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi32StoreuEpi8() {
    val a = mm256Set1Epi32(9)
    val localStorageL59147C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59147C8
    run {
        mm256MaskCvtepi32StoreuEpi8((MutPtr(localStorageL59147C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi32StoreuEpi8() {
    val a = mmSet1Epi32(9)
    val localStorageL59158C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59158C8
    run {
        mmMaskCvtepi32StoreuEpi8((MutPtr(localStorageL59158C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi32StoreuEpi8() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val localStorageL59169C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59169C8
    run {
        mm512MaskCvtsepi32StoreuEpi8((MutPtr(localStorageL59169C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mmSet1Epi8(Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi32StoreuEpi8() {
    val a = mm256Set1Epi32(Int.MAX_VALUE)
    val localStorageL59180C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59180C8
    run {
        mm256MaskCvtsepi32StoreuEpi8((MutPtr(localStorageL59180C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi32StoreuEpi8() {
    val a = mmSet1Epi32(Int.MAX_VALUE)
    val localStorageL59197C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59197C8
    run {
        mmMaskCvtsepi32StoreuEpi8((MutPtr(localStorageL59197C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi32StoreuEpi8() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val localStorageL59214C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59214C8
    run {
        mm512MaskCvtusepi32StoreuEpi8((MutPtr(localStorageL59214C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mmSet1Epi8(UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi32StoreuEpi8() {
    val a = mm256Set1Epi32(Int.MAX_VALUE)
    val localStorageL59225C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59225C8
    run {
        mm256MaskCvtusepi32StoreuEpi8((MutPtr(localStorageL59225C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi32StoreuEpi8() {
    val a = mmSet1Epi32(Int.MAX_VALUE)
    val localStorageL59242C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59242C8
    run {
        mmMaskCvtusepi32StoreuEpi8((MutPtr(localStorageL59242C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMm512StoreuEpi32() {
    val a = mm512Set1Epi32(9)
    val localStorageL59259C8 = AddressableLocal(mm512UndefinedEpi32(), UnresolvedTypeLayout<M512i>())
    val r by localStorageL59259C8
    run {
        mm512StoreuEpi32((MutPtr(localStorageL59259C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512i(r, a)
}

public fun testMm256StoreuEpi32() {
    val a = mm256Set1Epi32(9)
    val localStorageL59269C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL59269C8
    run {
        mm256StoreuEpi32((MutPtr(localStorageL59269C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM256i(r, a)
}

public fun testMmStoreuEpi32() {
    val a = mmSet1Epi32(9)
    val localStorageL59279C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59279C8
    run {
        mmStoreuEpi32((MutPtr(localStorageL59279C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM128i(r, a)
}

public fun testMm512LoaduSi512() {
    val a = listOf(4, 3, 2, 5, 8, 9, 64, 50, -4, -3, -2, -5, -8, -9, -64, -50)
    val p = a.asPtr().cast()
    val r = mm512LoaduSi512(blackBox(p))
    val e = mm512SetrEpi32(4, 3, 2, 5, 8, 9, 64, 50, -4, -3, -2, -5, -8, -9, -64, -50)
    assertEqM512i(r, e)
}

public fun testMm512StoreuSi512() {
    val a = mm512Set1Epi32(9)
    val localStorageL59298C8 = AddressableLocal(mm512UndefinedEpi32(), UnresolvedTypeLayout<M512i>())
    val r by localStorageL59298C8
    run {
        mm512StoreuSi512(MutPtr(localStorageL59298C8.asMutPtr().rawAddress), a)
    }
    assertEqM512i(r, a)
}

public fun testMm512LoadSi512() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f, -4.0f, -3.0f, -2.0f, -5.0f, -8.0f, -9.0f, -64.0f, -50.0f))
    val p = (a.data).asPtr()
    val r = mm512LoadSi512(blackBox(p))
    val e = mm512SetrEpi32(4, 3, 2, 5, 8, 9, 64, 50, -4, -3, -2, -5, -8, -9, -64, -50)
    assertEqM512i(r, e)
}

public fun testMm512StoreSi512() {
    val a = mm512Set1Epi32(9)
    val localStorageL59323C8 = AddressableLocal(mm512UndefinedEpi32(), UnresolvedTypeLayout<M512i>())
    val r by localStorageL59323C8
    run {
        mm512StoreSi512(MutPtr(localStorageL59323C8.asMutPtr().rawAddress), a)
    }
    assertEqM512i(r, a)
}

public fun testMm512LoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f, -4.0f, -3.0f, -2.0f, -5.0f, -8.0f, -9.0f, -64.0f, -50.0f))
    val p = (a.data).asPtr()
    val r = mm512LoadEpi32(blackBox(p))
    val e = mm512SetrEpi32(4, 3, 2, 5, 8, 9, 64, 50, -4, -3, -2, -5, -8, -9, -64, -50)
    assertEqM512i(r, e)
}

public fun testMm256LoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f))
    val p = (a.data).asPtr()
    val r = mm256LoadEpi32(blackBox(p))
    val e = mm256SetrEpi32(4, 3, 2, 5, 8, 9, 64, 50)
    assertEqM256i(r, e)
}

public fun testMmLoadEpi32() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout(),
    ) {
        public var data: IntArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: IntArray, fieldLayout: TypeLayout<IntArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(4.0f, 3.0f, 2.0f, 5.0f))
    val p = (a.data).asPtr()
    val r = mmLoadEpi32(blackBox(p))
    val e = mmSetrEpi32(4, 3, 2, 5)
    assertEqM128i(r, e)
}

public fun testMm512StoreEpi32() {
    val a = mm512Set1Epi32(9)
    val localStorageL59376C8 = AddressableLocal(mm512UndefinedEpi32(), UnresolvedTypeLayout<M512i>())
    val r by localStorageL59376C8
    run {
        mm512StoreEpi32((MutPtr(localStorageL59376C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512i(r, a)
}

public fun testMm256StoreEpi32() {
    val a = mm256Set1Epi32(9)
    val localStorageL59386C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL59386C8
    run {
        mm256StoreEpi32((MutPtr(localStorageL59386C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM256i(r, a)
}

public fun testMmStoreEpi32() {
    val a = mmSet1Epi32(9)
    val localStorageL59396C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL59396C8
    run {
        mmStoreEpi32((MutPtr(localStorageL59396C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM128i(r, a)
}

public fun testMm512LoadPs() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = floatArrayOf(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f, -4.0f, -3.0f, -2.0f, -5.0f, -8.0f, -9.0f, -64.0f, -50.0f))
    val p = (a.data).asPtr()
    val r = mm512LoadPs(blackBox(p))
    val e = mm512SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f, -4.0f, -3.0f, -2.0f, -5.0f, -8.0f, -9.0f, -64.0f, -50.0f)
    assertEqM512(r, e)
}

public fun testMm512StorePs() {
    val a = mm512Set1Ps(9.0f)
    val localStorageL59425C8 = AddressableLocal(mm512UndefinedPs(), UnresolvedTypeLayout<M512>())
    val r by localStorageL59425C8
    run {
        mm512StorePs((MutPtr(localStorageL59425C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512(r, a)
}

public fun testMm512MaskSet1Epi32() {
    val src = mm512Set1Epi32(2)
    val a: Int = 11
    val r = mm512MaskSet1Epi32(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskSet1Epi32(src, 0xFFFFu, a)
    val e = mm512Set1Epi32(11)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSet1Epi32() {
    val a: Int = 11
    val r = mm512MaskzSet1Epi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSet1Epi32(0xFFFFu, a)
    val e = mm512Set1Epi32(11)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSet1Epi32() {
    val src = mm256Set1Epi32(2)
    val a: Int = 11
    val r = mm256MaskSet1Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskSet1Epi32(src, 0xFFu, a)
    val e = mm256Set1Epi32(11)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSet1Epi32() {
    val a: Int = 11
    val r = mm256MaskzSet1Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSet1Epi32(0xFFu, a)
    val e = mm256Set1Epi32(11)
    assertEqM256i(r1, e)
}

public fun testMmMaskSet1Epi32() {
    val src = mmSet1Epi32(2)
    val a: Int = 11
    val r = mmMaskSet1Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskSet1Epi32(src, 0xFu, a)
    val e = mmSet1Epi32(11)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSet1Epi32() {
    val a: Int = 11
    val r = mmMaskzSet1Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSet1Epi32(0xFu, a)
    val e = mmSet1Epi32(11)
    assertEqM128i(r1, e)
}

public fun testMmMaskMoveSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskMoveSs(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMoveSs(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 40.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMoveSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzMoveSs(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMoveSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 40.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskMoveSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskMoveSd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMoveSd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 4.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMoveSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzMoveSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMoveSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 4.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskAddSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskAddSs(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskAddSs(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 60.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzAddSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzAddSs(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzAddSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 60.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskAddSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskAddSd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskAddSd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 6.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzAddSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzAddSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzAddSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 6.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskSubSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskSubSs(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskSubSs(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, -20.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzSubSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzSubSs(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzSubSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, -20.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskSubSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskSubSd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskSubSd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, -2.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzSubSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzSubSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzSubSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, -2.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskMulSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskMulSs(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMulSs(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 800.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMulSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzMulSs(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMulSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 800.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskMulSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskMulSd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMulSd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMulSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzMulSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMulSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskDivSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskDivSs(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskDivSs(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzDivSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzDivSs(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzDivSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r1, e1)
}

public fun testMmMaskDivSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskDivSd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskDivSd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 0.5)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzDivSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzDivSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzDivSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 0.5)
    assertEqM128d(r1, e1)
}

public fun testMmMaskMaxSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskMaxSs(a, 0u, a, b)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMaxSs(a, 0xFFu, a, b)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 7.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMaxSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskzMaxSs(0u, a, b)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMaxSs(0xFFu, a, b)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 7.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskMaxSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskMaxSd(a, 0u, a, b)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMaxSd(a, 0xFFu, a, b)
    val e1 = mmSetPd(0.0, 3.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMaxSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskzMaxSd(0u, a, b)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMaxSd(0xFFu, a, b)
    val e1 = mmSetPd(0.0, 3.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskMinSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskMinSs(a, 0u, a, b)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMinSs(a, 0xFFu, a, b)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMinSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskzMinSs(0u, a, b)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMinSs(0xFFu, a, b)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskMinSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskMinSd(a, 0u, a, b)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMinSd(a, 0xFFu, a, b)
    val e1 = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMinSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskzMinSd(0u, a, b)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMinSd(0xFFu, a, b)
    val e1 = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskSqrtSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskSqrtSs(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskSqrtSs(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 2.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzSqrtSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskzSqrtSs(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzSqrtSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 2.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskSqrtSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskSqrtSd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskSqrtSd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzSqrtSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzSqrtSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzSqrtSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e1)
}

public fun testMmRsqrt14Ss() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmRsqrt14Ss(a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r, e)
}

public fun testMmMaskRsqrt14Ss() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskRsqrt14Ss(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskRsqrt14Ss(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzRsqrt14Ss() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskzRsqrt14Ss(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzRsqrt14Ss(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r1, e1)
}

public fun testMmRsqrt14Sd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmRsqrt14Sd(a, b)
    val e = mmSetPd(1.0, 0.5)
    assertEqM128d(r, e)
}

public fun testMmMaskRsqrt14Sd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskRsqrt14Sd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskRsqrt14Sd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 0.5)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzRsqrt14Sd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzRsqrt14Sd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzRsqrt14Sd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 0.5)
    assertEqM128d(r1, e1)
}

public fun testMmRcp14Ss() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmRcp14Ss(a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.25f)
    assertEqM128(r, e)
}

public fun testMmMaskRcp14Ss() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskRcp14Ss(src, 0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskRcp14Ss(src, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.25f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzRcp14Ss() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskzRcp14Ss(0u, a, b)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzRcp14Ss(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.25f)
    assertEqM128(r1, e1)
}

public fun testMmRcp14Sd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmRcp14Sd(a, b)
    val e = mmSetPd(1.0, 0.25)
    assertEqM128d(r, e)
}

public fun testMmMaskRcp14Sd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskRcp14Sd(src, 0u, a, b)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskRcp14Sd(src, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 0.25)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzRcp14Sd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzRcp14Sd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzRcp14Sd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 0.25)
    assertEqM128d(r1, e1)
}

public fun testMmGetexpSs() {
    val a = mmSet1Ps(2.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmGetexpSs(a, b)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskGetexpSs() {
    val a = mmSet1Ps(2.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskGetexpSs(a, 0u, a, b)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM128(r, e)
    val r1 = mmMaskGetexpSs(a, 0xFFu, a, b)
    val e1 = mmSetPs(2.0f, 2.0f, 2.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzGetexpSs() {
    val a = mmSet1Ps(2.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskzGetexpSs(0u, a, b)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzGetexpSs(0xFFu, a, b)
    val e1 = mmSetPs(2.0f, 2.0f, 2.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmGetexpSd() {
    val a = mmSet1Pd(2.0)
    val b = mmSet1Pd(3.0)
    val r = mmGetexpSd(a, b)
    val e = mmSetPd(2.0, 1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskGetexpSd() {
    val a = mmSet1Pd(2.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskGetexpSd(a, 0u, a, b)
    val e = mmSetPd(2.0, 2.0)
    assertEqM128d(r, e)
    val r1 = mmMaskGetexpSd(a, 0xFFu, a, b)
    val e1 = mmSetPd(2.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzGetexpSd() {
    val a = mmSet1Pd(2.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskzGetexpSd(0u, a, b)
    val e = mmSetPd(2.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzGetexpSd(0xFFu, a, b)
    val e1 = mmSetPd(2.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmGetmantSs() {
    val a = mmSet1Ps(20.0f)
    val b = mmSet1Ps(10.0f)
    val r = mmGetmantSs(a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSetPs(20.0f, 20.0f, 20.0f, 1.25f)
    assertEqM128(r, e)
}

public fun testMmMaskGetmantSs() {
    val a = mmSet1Ps(20.0f)
    val b = mmSet1Ps(10.0f)
    val r = mmMaskGetmantSs(a, 0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSetPs(20.0f, 20.0f, 20.0f, 20.0f)
    assertEqM128(r, e)
    val r1 = mmMaskGetmantSs(a, 0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e1 = mmSetPs(20.0f, 20.0f, 20.0f, 1.25f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzGetmantSs() {
    val a = mmSet1Ps(20.0f)
    val b = mmSet1Ps(10.0f)
    val r = mmMaskzGetmantSs(0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSetPs(20.0f, 20.0f, 20.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzGetmantSs(0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e1 = mmSetPs(20.0f, 20.0f, 20.0f, 1.25f)
    assertEqM128(r1, e1)
}

public fun testMmGetmantSd() {
    val a = mmSet1Pd(20.0)
    val b = mmSet1Pd(10.0)
    val r = mmGetmantSd(a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSetPd(20.0, 1.25)
    assertEqM128d(r, e)
}

public fun testMmMaskGetmantSd() {
    val a = mmSet1Pd(20.0)
    val b = mmSet1Pd(10.0)
    val r = mmMaskGetmantSd(a, 0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSetPd(20.0, 20.0)
    assertEqM128d(r, e)
    val r1 = mmMaskGetmantSd(a, 0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e1 = mmSetPd(20.0, 1.25)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzGetmantSd() {
    val a = mmSet1Pd(20.0)
    val b = mmSet1Pd(10.0)
    val r = mmMaskzGetmantSd(0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e = mmSetPd(20.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzGetmantSd(0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC)
    val e1 = mmSetPd(20.0, 1.25)
    assertEqM128d(r1, e1)
}

public fun testMmRoundscaleSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmRoundscaleSs(a, b, 0)
    val e = mmSetPs(2.2f, 2.2f, 2.2f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskRoundscaleSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmMaskRoundscaleSs(a, 0u, a, b, 0)
    val e = mmSetPs(2.2f, 2.2f, 2.2f, 2.2f)
    assertEqM128(r, e)
    val r1 = mmMaskRoundscaleSs(a, 0xFFu, a, b, 0)
    val e1 = mmSetPs(2.2f, 2.2f, 2.2f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzRoundscaleSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmMaskzRoundscaleSs(0u, a, b, 0)
    val e = mmSetPs(2.2f, 2.2f, 2.2f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzRoundscaleSs(0xFFu, a, b, 0)
    val e1 = mmSetPs(2.2f, 2.2f, 2.2f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmRoundscaleSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmRoundscaleSd(a, b, 0)
    val e = mmSetPd(2.2, 1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskRoundscaleSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmMaskRoundscaleSd(a, 0u, a, b, 0)
    val e = mmSetPd(2.2, 2.2)
    assertEqM128d(r, e)
    val r1 = mmMaskRoundscaleSd(a, 0xFFu, a, b, 0)
    val e1 = mmSetPd(2.2, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzRoundscaleSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmMaskzRoundscaleSd(0u, a, b, 0)
    val e = mmSetPd(2.2, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzRoundscaleSd(0xFFu, a, b, 0)
    val e1 = mmSetPd(2.2, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmScalefSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmScalefSs(a, b)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMmMaskScalefSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskScalefSs(a, 0u, a, b)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r, e)
    val r1 = mmMaskScalefSs(a, 0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 8.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzScalefSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskzScalefSs(0u, a, b)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzScalefSs(0xFFu, a, b)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 8.0f)
    assertEqM128(r1, e1)
}

public fun testMmScalefSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmScalefSd(a, b)
    val e = mmSetPd(1.0, 8.0)
    assertEqM128d(r, e)
}

public fun testMmMaskScalefSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskScalefSd(a, 0u, a, b)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r, e)
    val r1 = mmMaskScalefSd(a, 0xFFu, a, b)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzScalefSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskzScalefSd(0u, a, b)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzScalefSd(0xFFu, a, b)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskFmaddSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFmaddSs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFmaddSs(a, 0xFFu, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 5.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmaddSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFmaddSs(0u, a, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFmaddSs(0xFFu, a, b, c)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 5.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FmaddSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FmaddSs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FmaddSs(a, b, c, 0xFFu)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, 5.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskFmaddSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFmaddSd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFmaddSd(a, 0xFFu, b, c)
    val e = mmSetPd(1.0, 5.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmaddSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFmaddSd(0u, a, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFmaddSd(0xFFu, a, b, c)
    val e1 = mmSetPd(1.0, 5.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FmaddSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FmaddSd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FmaddSd(a, b, c, 0xFFu)
    val e = mmSetPd(3.0, 5.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskFmsubSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFmsubSs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFmsubSs(a, 0xFFu, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, -1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmsubSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFmsubSs(0u, a, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFmsubSs(0xFFu, a, b, c)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, -1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FmsubSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FmsubSs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FmsubSs(a, b, c, 0xFFu)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, -1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskFmsubSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFmsubSd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFmsubSd(a, 0xFFu, b, c)
    val e = mmSetPd(1.0, -1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmsubSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFmsubSd(0u, a, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFmsubSd(0xFFu, a, b, c)
    val e1 = mmSetPd(1.0, -1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FmsubSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FmsubSd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FmsubSd(a, b, c, 0xFFu)
    val e = mmSetPd(3.0, -1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskFnmaddSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFnmaddSs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFnmaddSs(a, 0xFFu, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFnmaddSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFnmaddSs(0u, a, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFnmaddSs(0xFFu, a, b, c)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FnmaddSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FnmaddSs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FnmaddSs(a, b, c, 0xFFu)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskFnmaddSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFnmaddSd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFnmaddSd(a, 0xFFu, b, c)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFnmaddSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFnmaddSd(0u, a, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFnmaddSd(0xFFu, a, b, c)
    val e1 = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FnmaddSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FnmaddSd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FnmaddSd(a, b, c, 0xFFu)
    val e = mmSetPd(3.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskFnmsubSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFnmsubSs(a, 0u, b, c)
    assertEqM128(r, a)
    val r1 = mmMaskFnmsubSs(a, 0xFFu, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, -5.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFnmsubSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFnmsubSs(0u, a, b, c)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFnmsubSs(0xFFu, a, b, c)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, -5.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FnmsubSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FnmsubSs(a, b, c, 0u)
    assertEqM128(r, c)
    val r1 = mmMask3FnmsubSs(a, b, c, 0xFFu)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, -5.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskFnmsubSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFnmsubSd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFnmsubSd(a, 0xFFu, b, c)
    val e = mmSetPd(1.0, -5.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFnmsubSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFnmsubSd(0u, a, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFnmsubSd(0xFFu, a, b, c)
    val e1 = mmSetPd(1.0, -5.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FnmsubSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FnmsubSd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FnmsubSd(a, b, c, 0xFFu)
    val e = mmSetPd(3.0, -5.0)
    assertEqM128d(r1, e)
}

public fun testMmAddRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmAddRoundSs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 60.0f)
    assertEqM128(r, e)
}

public fun testMmMaskAddRoundSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskAddRoundSs(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskAddRoundSs(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 60.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzAddRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzAddRoundSs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzAddRoundSs(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 60.0f)
    assertEqM128(r1, e1)
}

public fun testMmAddRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmAddRoundSd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 6.0)
    assertEqM128d(r, e)
}

public fun testMmMaskAddRoundSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskAddRoundSd(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskAddRoundSd(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 6.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzAddRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzAddRoundSd(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzAddRoundSd(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 6.0)
    assertEqM128d(r1, e1)
}

public fun testMmSubRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmSubRoundSs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, -20.0f)
    assertEqM128(r, e)
}

public fun testMmMaskSubRoundSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskSubRoundSs(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskSubRoundSs(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, -20.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzSubRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzSubRoundSs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzSubRoundSs(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, -20.0f)
    assertEqM128(r1, e1)
}

public fun testMmSubRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmSubRoundSd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, -2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskSubRoundSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskSubRoundSd(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskSubRoundSd(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, -2.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzSubRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzSubRoundSd(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzSubRoundSd(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, -2.0)
    assertEqM128d(r1, e1)
}

public fun testMmMulRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMulRoundSs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 800.0f)
    assertEqM128(r, e)
}

public fun testMmMaskMulRoundSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskMulRoundSs(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMulRoundSs(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 800.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMulRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzMulRoundSs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMulRoundSs(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 800.0f)
    assertEqM128(r1, e1)
}

public fun testMmMulRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMulRoundSd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 8.0)
    assertEqM128d(r, e)
}

public fun testMmMaskMulRoundSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskMulRoundSd(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMulRoundSd(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMulRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzMulRoundSd(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMulRoundSd(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmDivRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmDivRoundSs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r, e)
}

public fun testMmMaskDivRoundSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskDivRoundSs(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskDivRoundSs(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzDivRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 40.0f)
    val r = mmMaskzDivRoundSs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzDivRoundSs(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 0.5f)
    assertEqM128(r1, e1)
}

public fun testMmDivRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmDivRoundSd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.5)
    assertEqM128d(r, e)
}

public fun testMmMaskDivRoundSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskDivRoundSd(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskDivRoundSd(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 0.5)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzDivRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzDivRoundSd(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzDivRoundSd(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 0.5)
    assertEqM128d(r1, e1)
}

public fun testMmMaxRoundSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaxRoundSs(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 7.0f)
    assertEqM128(r, e)
}

public fun testMmMaskMaxRoundSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskMaxRoundSs(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMaxRoundSs(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 7.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMaxRoundSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskzMaxRoundSs(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMaxRoundSs(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 7.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaxRoundSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaxRoundSd(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(0.0, 3.0)
    assertEqM128d(r, e)
}

public fun testMmMaskMaxRoundSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskMaxRoundSd(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMaxRoundSd(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(0.0, 3.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMaxRoundSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskzMaxRoundSd(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMaxRoundSd(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(0.0, 3.0)
    assertEqM128d(r1, e1)
}

public fun testMmMinRoundSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMinRoundSs(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r, e)
}

public fun testMmMaskMinRoundSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskMinRoundSs(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r, e)
    val r1 = mmMaskMinRoundSs(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzMinRoundSs() {
    val a = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    val b = mmSetPs(4.0f, 5.0f, 6.0f, 7.0f)
    val r = mmMaskzMinRoundSs(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(0.0f, 1.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzMinRoundSs(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(0.0f, 1.0f, 2.0f, 3.0f)
    assertEqM128(r1, e1)
}

public fun testMmMinRoundSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMinRoundSd(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskMinRoundSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskMinRoundSd(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r, e)
    val r1 = mmMaskMinRoundSd(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzMinRoundSd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskzMinRoundSd(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzMinRoundSd(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmSqrtRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmSqrtRoundSs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 2.0f)
    assertEqM128(r, e)
}

public fun testMmMaskSqrtRoundSs() {
    val src = mmSetPs(10.0f, 11.0f, 100.0f, 110.0f)
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskSqrtRoundSs(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 110.0f)
    assertEqM128(r, e)
    val r1 = mmMaskSqrtRoundSs(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 2.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzSqrtRoundSs() {
    val a = mmSetPs(1.0f, 2.0f, 10.0f, 20.0f)
    val b = mmSetPs(3.0f, 4.0f, 30.0f, 4.0f)
    val r = mmMaskzSqrtRoundSs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 2.0f, 10.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzSqrtRoundSs(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 2.0f, 10.0f, 2.0f)
    assertEqM128(r1, e1)
}

public fun testMmSqrtRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmSqrtRoundSd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskSqrtRoundSd() {
    val src = mmSetPd(10.0, 11.0)
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskSqrtRoundSd(src, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 11.0)
    assertEqM128d(r, e)
    val r1 = mmMaskSqrtRoundSd(src, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzSqrtRoundSd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(3.0, 4.0)
    val r = mmMaskzSqrtRoundSd(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzSqrtRoundSd(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e1)
}

public fun testMmGetexpRoundSs() {
    val a = mmSet1Ps(2.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmGetexpRoundSs(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskGetexpRoundSs() {
    val a = mmSet1Ps(2.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskGetexpRoundSs(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 2.0f)
    assertEqM128(r, e)
    val r1 = mmMaskGetexpRoundSs(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(2.0f, 2.0f, 2.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzGetexpRoundSs() {
    val a = mmSet1Ps(2.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskzGetexpRoundSs(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(2.0f, 2.0f, 2.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzGetexpRoundSs(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(2.0f, 2.0f, 2.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmGetexpRoundSd() {
    val a = mmSet1Pd(2.0)
    val b = mmSet1Pd(3.0)
    val r = mmGetexpRoundSd(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(2.0, 1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskGetexpRoundSd() {
    val a = mmSet1Pd(2.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskGetexpRoundSd(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(2.0, 2.0)
    assertEqM128d(r, e)
    val r1 = mmMaskGetexpRoundSd(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(2.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzGetexpRoundSd() {
    val a = mmSet1Pd(2.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskzGetexpRoundSd(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(2.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzGetexpRoundSd(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(2.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmGetmantRoundSs() {
    val a = mmSet1Ps(20.0f)
    val b = mmSet1Ps(10.0f)
    val r = mmGetmantRoundSs(a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(20.0f, 20.0f, 20.0f, 1.25f)
    assertEqM128(r, e)
}

public fun testMmMaskGetmantRoundSs() {
    val a = mmSet1Ps(20.0f)
    val b = mmSet1Ps(10.0f)
    val r = mmMaskGetmantRoundSs(a, 0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(20.0f, 20.0f, 20.0f, 20.0f)
    assertEqM128(r, e)
    val r1 = mmMaskGetmantRoundSs(a, 0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(20.0f, 20.0f, 20.0f, 1.25f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzGetmantRoundSs() {
    val a = mmSet1Ps(20.0f)
    val b = mmSet1Ps(10.0f)
    val r = mmMaskzGetmantRoundSs(0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(20.0f, 20.0f, 20.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzGetmantRoundSs(0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(20.0f, 20.0f, 20.0f, 1.25f)
    assertEqM128(r1, e1)
}

public fun testMmGetmantRoundSd() {
    val a = mmSet1Pd(20.0)
    val b = mmSet1Pd(10.0)
    val r = mmGetmantRoundSd(a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(20.0, 1.25)
    assertEqM128d(r, e)
}

public fun testMmMaskGetmantRoundSd() {
    val a = mmSet1Pd(20.0)
    val b = mmSet1Pd(10.0)
    val r = mmMaskGetmantRoundSd(a, 0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(20.0, 20.0)
    assertEqM128d(r, e)
    val r1 = mmMaskGetmantRoundSd(a, 0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(20.0, 1.25)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzGetmantRoundSd() {
    val a = mmSet1Pd(20.0)
    val b = mmSet1Pd(10.0)
    val r = mmMaskzGetmantRoundSd(0u, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(20.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzGetmantRoundSd(0xFFu, a, b, MM_MANT_NORM_1_2, MM_MANT_SIGN_SRC, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(20.0, 1.25)
    assertEqM128d(r1, e1)
}

public fun testMmRoundscaleRoundSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmRoundscaleRoundSs(a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(2.2f, 2.2f, 2.2f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskRoundscaleRoundSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmMaskRoundscaleRoundSs(a, 0u, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(2.2f, 2.2f, 2.2f, 2.2f)
    assertEqM128(r, e)
    val r1 = mmMaskRoundscaleRoundSs(a, 0xFFu, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(2.2f, 2.2f, 2.2f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzRoundscaleRoundSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmMaskzRoundscaleRoundSs(0u, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(2.2f, 2.2f, 2.2f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzRoundscaleRoundSs(0xFFu, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(2.2f, 2.2f, 2.2f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmRoundscaleRoundSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmRoundscaleRoundSd(a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(2.2, 1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskRoundscaleRoundSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmMaskRoundscaleRoundSd(a, 0u, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(2.2, 2.2)
    assertEqM128d(r, e)
    val r1 = mmMaskRoundscaleRoundSd(a, 0xFFu, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(2.2, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzRoundscaleRoundSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmMaskzRoundscaleRoundSd(0u, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(2.2, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzRoundscaleRoundSd(0xFFu, a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(2.2, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmScalefRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmScalefRoundSs(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMmMaskScalefRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskScalefRoundSs(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r, e)
    val r1 = mmMaskScalefRoundSs(a, 0xFFu, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 8.0f)
    assertEqM128(r1, e1)
}

public fun testMmMaskzScalefRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(3.0f)
    val r = mmMaskzScalefRoundSs(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzScalefRoundSs(0xFFu, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 8.0f)
    assertEqM128(r1, e1)
}

public fun testMmScalefRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmScalefRoundSd(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 8.0)
    assertEqM128d(r, e)
}

public fun testMmMaskScalefRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskScalefRoundSd(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r, e)
    val r1 = mmMaskScalefRoundSd(a, 0xFFu, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzScalefRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskzScalefRoundSd(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzScalefRoundSd(0xFFu, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 8.0)
    assertEqM128d(r1, e1)
}

public fun testMmFmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmFmaddRoundSs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 5.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFmaddRoundSs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, a)
    val r1 = mmMaskFmaddRoundSs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 5.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFmaddRoundSs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFmaddRoundSs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 5.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FmaddRoundSs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, c)
    val r1 = mmMask3FmaddRoundSs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, 5.0f)
    assertEqM128(r1, e)
}

public fun testMmFmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmFmaddRoundSd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 5.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFmaddRoundSd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, a)
    val r1 = mmMaskFmaddRoundSd(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 5.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFmaddRoundSd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFmaddRoundSd(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 5.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FmaddRoundSd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, c)
    val r1 = mmMask3FmaddRoundSd(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(3.0, 5.0)
    assertEqM128d(r1, e)
}

public fun testMmFmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmFmsubRoundSs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, -1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFmsubRoundSs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, a)
    val r1 = mmMaskFmsubRoundSs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, -1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFmsubRoundSs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFmsubRoundSs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, -1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FmsubRoundSs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, c)
    val r1 = mmMask3FmsubRoundSs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, -1.0f)
    assertEqM128(r1, e)
}

public fun testMmFmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmFmsubRoundSd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, -1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFmsubRoundSd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, a)
    val r1 = mmMaskFmsubRoundSd(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, -1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFmsubRoundSd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFmsubRoundSd(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, -1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FmsubRoundSd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, c)
    val r1 = mmMask3FmsubRoundSd(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(3.0, -1.0)
    assertEqM128d(r1, e)
}

public fun testMmFnmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmFnmaddRoundSs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFnmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFnmaddRoundSs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, a)
    val r1 = mmMaskFnmaddRoundSs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFnmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFnmaddRoundSs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFnmaddRoundSs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FnmaddRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FnmaddRoundSs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, c)
    val r1 = mmMask3FnmaddRoundSs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, 1.0f)
    assertEqM128(r1, e)
}

public fun testMmFnmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmFnmaddRoundSd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFnmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFnmaddRoundSd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, a)
    val r1 = mmMaskFnmaddRoundSd(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFnmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFnmaddRoundSd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFnmaddRoundSd(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FnmaddRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FnmaddRoundSd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, c)
    val r1 = mmMask3FnmaddRoundSd(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(3.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmFnmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmFnmsubRoundSs(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, -5.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFnmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskFnmsubRoundSs(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, a)
    val r1 = mmMaskFnmsubRoundSs(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, -5.0f)
    assertEqM128(r1, e)
}

public fun testMmMaskzFnmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMaskzFnmsubRoundSs(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFnmsubRoundSs(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(1.0f, 1.0f, 1.0f, -5.0f)
    assertEqM128(r1, e1)
}

public fun testMmMask3FnmsubRoundSs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSet1Ps(2.0f)
    val c = mmSet1Ps(3.0f)
    val r = mmMask3FnmsubRoundSs(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128(r, c)
    val r1 = mmMask3FnmsubRoundSs(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPs(3.0f, 3.0f, 3.0f, -5.0f)
    assertEqM128(r1, e)
}

public fun testMmFnmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmFnmsubRoundSd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, -5.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFnmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskFnmsubRoundSd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, a)
    val r1 = mmMaskFnmsubRoundSd(a, 0xFFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, -5.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFnmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMaskzFnmsubRoundSd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFnmsubRoundSd(0xFFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e1 = mmSetPd(1.0, -5.0)
    assertEqM128d(r1, e1)
}

public fun testMmMask3FnmsubRoundSd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val c = mmSet1Pd(3.0)
    val r = mmMask3FnmsubRoundSd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM128d(r, c)
    val r1 = mmMask3FnmsubRoundSd(a, b, c, 0xFFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mmSetPd(3.0, -5.0)
    assertEqM128d(r1, e)
}

public fun testMmFixupimmSs() {
    val a = mmSetPs(0.0f, 0.0f, 0.0f, Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmFixupimmSs(a, b, c, 5)
    val e = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFixupimmSs() {
    val a = mmSetPs(0.0f, 0.0f, 0.0f, Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmMaskFixupimmSs(a, 0xFFu, b, c, 5)
    val e = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzFixupimmSs() {
    val a = mmSetPs(0.0f, 0.0f, 0.0f, Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmMaskzFixupimmSs(0x0u, a, b, c, 5)
    val e = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFixupimmSs(0xFFu, a, b, c, 5)
    val e1 = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -0.0f)
    assertEqM128(r1, e1)
}

public fun testMmFixupimmSd() {
    val a = mmSetPd(0.0, Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmFixupimmSd(a, b, c, 5)
    val e = mmSetPd(Double.MAX_VALUE, -0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFixupimmSd() {
    val a = mmSetPd(0.0, Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmMaskFixupimmSd(a, 0xFFu, b, c, 5)
    val e = mmSetPd(Double.MAX_VALUE, -0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzFixupimmSd() {
    val a = mmSetPd(0.0, Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmMaskzFixupimmSd(0x0u, a, b, c, 5)
    val e = mmSetPd(Double.MAX_VALUE, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFixupimmSd(0xFFu, a, b, c, 5)
    val e1 = mmSetPd(Double.MAX_VALUE, -0.0)
    assertEqM128d(r1, e1)
}

public fun testMmFixupimmRoundSs() {
    val a = mmSetPs(1.0f, 0.0f, 0.0f, Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmFixupimmRoundSs(a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskFixupimmRoundSs() {
    val a = mmSetPs(0.0f, 0.0f, 0.0f, Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmMaskFixupimmRoundSs(a, 0xFFu, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -0.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzFixupimmRoundSs() {
    val a = mmSetPs(0.0f, 0.0f, 0.0f, Float.NaN)
    val b = mmSet1Ps(Float.MAX_VALUE)
    val c = mmSet1Epi32(Int.MAX_VALUE)
    val r = mmMaskzFixupimmRoundSs(0x0u, a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzFixupimmRoundSs(0xFFu, a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPs(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -0.0f)
    assertEqM128(r1, e1)
}

public fun testMmFixupimmRoundSd() {
    val a = mmSetPd(0.0, Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmFixupimmRoundSd(a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(Double.MAX_VALUE, -0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFixupimmRoundSd() {
    val a = mmSetPd(0.0, Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmMaskFixupimmRoundSd(a, 0xFFu, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(Double.MAX_VALUE, -0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzFixupimmRoundSd() {
    val a = mmSetPd(0.0, Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmMaskzFixupimmRoundSd(0x0u, a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(Double.MAX_VALUE, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzFixupimmRoundSd(0xFFu, a, b, c, 5, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(Double.MAX_VALUE, -0.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskCvtssSd() {
    val a = mmSetPd(6.0, -7.5)
    val b = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmMaskCvtssSd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskCvtssSd(a, 0xFFu, a, b)
    val e = mmSetPd(6.0, -1.5)
    assertEqM128d(r1, e)
}

public fun testMmMaskzCvtssSd() {
    val a = mmSetPd(6.0, -7.5)
    val b = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmMaskzCvtssSd(0u, a, b)
    val e = mmSetPd(6.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzCvtssSd(0xFFu, a, b)
    val e1 = mmSetPd(6.0, -1.5)
    assertEqM128d(r1, e1)
}

public fun testMmMaskCvtsdSs() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b = mmSetPd(6.0, -7.5)
    val r = mmMaskCvtsdSs(a, 0u, a, b)
    assertEqM128(r, a)
    val r1 = mmMaskCvtsdSs(a, 0xFFu, a, b)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, -7.5f)
    assertEqM128(r1, e)
}

public fun testMmMaskzCvtsdSs() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b = mmSetPd(6.0, -7.5)
    val r = mmMaskzCvtsdSs(0u, a, b)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzCvtsdSs(0xFFu, a, b)
    val e1 = mmSetPs(0.0f, -0.5f, 1.0f, -7.5f)
    assertEqM128(r1, e1)
}

public fun testMmCvtRoundssSd() {
    val a = mmSetPd(6.0, -7.5)
    val b = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssSd(a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(6.0, -1.5)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtRoundssSd() {
    val a = mmSetPd(6.0, -7.5)
    val b = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmMaskCvtRoundssSd(a, 0u, a, b, MM_FROUND_CUR_DIRECTION)
    assertEqM128d(r, a)
    val r1 = mmMaskCvtRoundssSd(a, 0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(6.0, -1.5)
    assertEqM128d(r1, e)
}

public fun testMmMaskzCvtRoundssSd() {
    val a = mmSetPd(6.0, -7.5)
    val b = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmMaskzCvtRoundssSd(0u, a, b, MM_FROUND_CUR_DIRECTION)
    val e = mmSetPd(6.0, 0.0)
    assertEqM128d(r, e)
    val r1 = mmMaskzCvtRoundssSd(0xFFu, a, b, MM_FROUND_CUR_DIRECTION)
    val e1 = mmSetPd(6.0, -1.5)
    assertEqM128d(r1, e1)
}

public fun testMmCvtRoundsdSs() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b = mmSetPd(6.0, -7.5)
    val r = mmCvtRoundsdSs(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, -7.5f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtRoundsdSs() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b = mmSetPd(6.0, -7.5)
    val r = mmMaskCvtRoundsdSs(a, 0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    assertEqM128(r, a)
    val r1 = mmMaskCvtRoundsdSs(a, 0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, -7.5f)
    assertEqM128(r1, e)
}

public fun testMmMaskzCvtRoundsdSs() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b = mmSetPd(6.0, -7.5)
    val r = mmMaskzCvtRoundsdSs(0u, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 0.0f)
    assertEqM128(r, e)
    val r1 = mmMaskzCvtRoundsdSs(0xFFu, a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mmSetPs(0.0f, -0.5f, 1.0f, -7.5f)
    assertEqM128(r1, e1)
}

public fun testMmCvtRoundssSi32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssSi32(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvtRoundssI32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssI32(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvtRoundssU32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssU32(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvtssI32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtssI32(a)
    val e: Int = -2
    check(r == e)
}

public fun testMmCvtssU32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtssU32(a)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvtRoundsdSi32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtRoundsdSi32(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvtRoundsdI32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtRoundsdI32(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvtRoundsdU32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtRoundsdU32(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvtsdI32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtsdI32(a)
    val e: Int = -2
    check(r == e)
}

public fun testMmCvtsdU32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtsdU32(a)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvtRoundi32Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: Int = 9
    val r = mmCvtRoundi32Ss(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvtRoundsi32Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: Int = 9
    val r = mmCvtRoundsi32Ss(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvtRoundu32Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: UInt = 9u
    val r = mmCvtRoundu32Ss(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvti32Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: Int = 9
    val r = mmCvti32Ss(a, b)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvti32Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: Int = 9
    val r = mmCvti32Sd(a, b)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}

public fun testMmCvttRoundssSi32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttRoundssSi32(a, MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvttRoundssI32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttRoundssI32(a, MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvttRoundssU32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttRoundssU32(a, MM_FROUND_NO_EXC)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvttssI32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttssI32(a)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvttssU32() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttssU32(a)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvttRoundsdSi32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttRoundsdSi32(a, MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvttRoundsdI32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttRoundsdI32(a, MM_FROUND_NO_EXC)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvttRoundsdU32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttRoundsdU32(a, MM_FROUND_NO_EXC)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvttsdI32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttsdI32(a)
    val e: Int = -1
    check(r == e)
}

public fun testMmCvttsdU32() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttsdU32(a)
    val e: UInt = UInt.MAX_VALUE
    check(r == e)
}

public fun testMmCvtu32Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: UInt = 9u
    val r = mmCvtu32Ss(a, b)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvtu32Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: UInt = 9u
    val r = mmCvtu32Sd(a, b)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}

public fun testMmComiRoundSs() {
    val a = mmSet1Ps(2.2f)
    val b = mmSet1Ps(1.1f)
    val r = mmComiRoundSs(a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e: Int = 0
    check(r == e)
}

public fun testMmComiRoundSd() {
    val a = mmSet1Pd(2.2)
    val b = mmSet1Pd(1.1)
    val r = mmComiRoundSd(a, b, 0, MM_FROUND_CUR_DIRECTION)
    val e: Int = 0
    check(r == e)
}

public fun testMm512Cvtsi512Si32() {
    val a = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512Cvtsi512Si32(a)
    val e: Int = 1
    check(r == e)
}

public fun testMm512CvtssF32() {
    val a = mm512SetrPs(312.0134f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f, -4.0f, -3.0f, -2.0f, -5.0f, -8.0f, -9.0f, -64.0f, -50.0f)
    check(mm512CvtssF32(a) == 312.0134f.toFloat())
}

public fun testMm512CvtsdF64() {
    val r = mm512CvtsdF64(mm512SetrPd(-1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8))
    check(r == (-1.1).toDouble())
}

public fun testMm512ShufflePd() {
    val a = mm512SetrPd(1.0, 4.0, 5.0, 8.0, 1.0, 4.0, 5.0, 8.0)
    val b = mm512SetrPd(2.0, 3.0, 6.0, 7.0, 2.0, 3.0, 6.0, 7.0)
    val r = mm512ShufflePd(a, b, 0xFF)
    val e = mm512SetrPd(4.0, 3.0, 8.0, 7.0, 4.0, 3.0, 8.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskShufflePd() {
    val a = mm512SetrPd(1.0, 4.0, 5.0, 8.0, 1.0, 4.0, 5.0, 8.0)
    val b = mm512SetrPd(2.0, 3.0, 6.0, 7.0, 2.0, 3.0, 6.0, 7.0)
    val r = mm512MaskShufflePd(a, 0u, a, b, 0xFF)
    assertEqM512d(r, a)
    val r1 = mm512MaskShufflePd(a, 0xFFu, a, b, 0xFF)
    val e = mm512SetrPd(4.0, 3.0, 8.0, 7.0, 4.0, 3.0, 8.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzShufflePd() {
    val a = mm512SetrPd(1.0, 4.0, 5.0, 8.0, 1.0, 4.0, 5.0, 8.0)
    val b = mm512SetrPd(2.0, 3.0, 6.0, 7.0, 2.0, 3.0, 6.0, 7.0)
    val r = mm512MaskzShufflePd(0u, a, b, 0xFF)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzShufflePd(0xFu, a, b, 0xFF)
    val e = mm512SetrPd(4.0, 3.0, 8.0, 7.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskExpandloaduEpi32() {
    val src = mm512Set1Epi32(42)
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskExpandloaduEpi32(src, m, blackBox(p))
    val e = mm512SetEpi32(8, 7, 6, 42, 5, 42, 42, 42, 4, 3, 42, 42, 2, 42, 1, 42)
    assertEqM512i(r, e)
}

public fun testMm512MaskzExpandloaduEpi32() {
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskzExpandloaduEpi32(m, blackBox(p))
    val e = mm512SetEpi32(8, 7, 6, 0, 5, 0, 0, 0, 4, 3, 0, 0, 2, 0, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm256MaskExpandloaduEpi32() {
    val src = mm256Set1Epi32(42)
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskExpandloaduEpi32(src, m, blackBox(p))
    val e = mm256SetEpi32(4, 3, 2, 42, 1, 42, 42, 42)
    assertEqM256i(r, e)
}

public fun testMm256MaskzExpandloaduEpi32() {
    val a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskzExpandloaduEpi32(m, blackBox(p))
    val e = mm256SetEpi32(4, 3, 2, 0, 1, 0, 0, 0)
    assertEqM256i(r, e)
}

public fun testMmMaskExpandloaduEpi32() {
    val src = mmSet1Epi32(42)
    val a = intArrayOf(1, 2, 3, 4)
    val p = a.asPtr()
    val m = 0xF8
    val r = mmMaskExpandloaduEpi32(src, m, blackBox(p))
    val e = mmSetEpi32(1, 42, 42, 42)
    assertEqM128i(r, e)
}

public fun testMmMaskzExpandloaduEpi32() {
    val a = intArrayOf(1, 2, 3, 4)
    val p = a.asPtr()
    val m = 0xF8
    val r = mmMaskzExpandloaduEpi32(m, blackBox(p))
    val e = mmSetEpi32(1, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMm512MaskExpandloaduEpi64() {
    val src = mm512Set1Epi64(42L)
    val a = longArrayOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm512MaskExpandloaduEpi64(src, m, blackBox(p))
    val e = mm512SetEpi64(4L, 3L, 2L, 42L, 1L, 42L, 42L, 42L)
    assertEqM512i(r, e)
}

public fun testMm512MaskzExpandloaduEpi64() {
    val a = longArrayOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm512MaskzExpandloaduEpi64(m, blackBox(p))
    val e = mm512SetEpi64(4L, 3L, 2L, 0L, 1L, 0L, 0L, 0L)
    assertEqM512i(r, e)
}

public fun testMm256MaskExpandloaduEpi64() {
    val src = mm256Set1Epi64x(42L)
    val a = longArrayOf(1L, 2L, 3L, 4L)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskExpandloaduEpi64(src, m, blackBox(p))
    val e = mm256SetEpi64x(1L, 42L, 42L, 42L)
    assertEqM256i(r, e)
}

public fun testMm256MaskzExpandloaduEpi64() {
    val a = longArrayOf(1L, 2L, 3L, 4L)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskzExpandloaduEpi64(m, blackBox(p))
    val e = mm256SetEpi64x(1L, 0L, 0L, 0L)
    assertEqM256i(r, e)
}

public fun testMmMaskExpandloaduEpi64() {
    val src = mmSet1Epi64x(42L)
    val a = longArrayOf(1L, 2L)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskExpandloaduEpi64(src, m, blackBox(p))
    val e = mmSetEpi64x(42L, 42L)
    assertEqM128i(r, e)
}

public fun testMmMaskzExpandloaduEpi64() {
    val a = longArrayOf(1L, 2L)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskzExpandloaduEpi64(m, blackBox(p))
    val e = mmSetEpi64x(0L, 0L)
    assertEqM128i(r, e)
}

public fun testMm512MaskExpandloaduPs() {
    val src = mm512Set1Ps(42.0f)
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskExpandloaduPs(src, m, blackBox(p))
    val e = mm512SetPs(8.0f, 7.0f, 6.0f, 42.0f, 5.0f, 42.0f, 42.0f, 42.0f, 4.0f, 3.0f, 42.0f, 42.0f, 2.0f, 42.0f, 1.0f, 42.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzExpandloaduPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm512MaskzExpandloaduPs(m, blackBox(p))
    val e = mm512SetPs(8.0f, 7.0f, 6.0f, 0.0f, 5.0f, 0.0f, 0.0f, 0.0f, 4.0f, 3.0f, 0.0f, 0.0f, 2.0f, 0.0f, 1.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm256MaskExpandloaduPs() {
    val src = mm256Set1Ps(42.0f)
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskExpandloaduPs(src, m, blackBox(p))
    val e = mm256SetPs(4.0f, 3.0f, 2.0f, 42.0f, 1.0f, 42.0f, 42.0f, 42.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzExpandloaduPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskzExpandloaduPs(m, blackBox(p))
    val e = mm256SetPs(4.0f, 3.0f, 2.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r, e)
}

public fun testMmMaskExpandloaduPs() {
    val src = mmSet1Ps(42.0f)
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskExpandloaduPs(src, m, blackBox(p))
    val e = mmSetPs(1.0f, 42.0f, 42.0f, 42.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzExpandloaduPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskzExpandloaduPs(m, blackBox(p))
    val e = mmSetPs(1.0f, 0.0f, 0.0f, 0.0f)
    assertEqM128(r, e)
}

public fun testMm512MaskExpandloaduPd() {
    val src = mm512Set1Pd(42.0)
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm512MaskExpandloaduPd(src, m, blackBox(p))
    val e = mm512SetPd(4.0, 3.0, 2.0, 42.0, 1.0, 42.0, 42.0, 42.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzExpandloaduPd() {
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm512MaskzExpandloaduPd(m, blackBox(p))
    val e = mm512SetPd(4.0, 3.0, 2.0, 0.0, 1.0, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm256MaskExpandloaduPd() {
    val src = mm256Set1Pd(42.0)
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskExpandloaduPd(src, m, blackBox(p))
    val e = mm256SetPd(1.0, 42.0, 42.0, 42.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzExpandloaduPd() {
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
    val p = a.asPtr()
    val m = 0xE8
    val r = mm256MaskzExpandloaduPd(m, blackBox(p))
    val e = mm256SetPd(1.0, 0.0, 0.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMmMaskExpandloaduPd() {
    val src = mmSet1Pd(42.0)
    val a = doubleArrayOf(1.0, 2.0)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskExpandloaduPd(src, m, blackBox(p))
    val e = mmSetPd(42.0, 42.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzExpandloaduPd() {
    val a = doubleArrayOf(1.0, 2.0)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskzExpandloaduPd(m, blackBox(p))
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r, e)
}
