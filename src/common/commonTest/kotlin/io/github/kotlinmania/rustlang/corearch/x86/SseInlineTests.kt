// port-lint: tests x86/sse.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Streaming SIMD Extensions (SSE)

public val NAN: Float = Float.NaN

public fun testMmAddPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmAddPs(a, b)
    assertEqM128(r, mmSetrPs(-101.0f, 25.0f, 0.0f, -15.0f))
}

public fun testMmAddSs() {
    val a = mmSetPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmAddSs(a, b)
    assertEqM128(r, mmSetPs(-1.0f, 5.0f, 0.0f, -15.0f))
}

public fun testMmSubPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmSubPs(a, b)
    assertEqM128(r, mmSetrPs(99.0f, -15.0f, 0.0f, -5.0f))
}

public fun testMmSubSs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmSubSs(a, b)
    assertEqM128(r, mmSetrPs(99.0f, 5.0f, 0.0f, -10.0f))
}

public fun testMmMulPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmMulPs(a, b)
    assertEqM128(r, mmSetrPs(100.0f, 100.0f, 0.0f, 50.0f))
}

public fun testMmMulSs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmMulSs(a, b)
    assertEqM128(r, mmSetrPs(100.0f, 5.0f, 0.0f, -10.0f))
}

public fun testMmDivPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 2.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.2f, -5.0f)
    val r = mmDivPs(a, b)
    assertEqM128(r, mmSetrPs(0.01f, 0.25f, 10.0f, 2.0f))
}

public fun testMmDivSs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmDivSs(a, b)
    assertEqM128(r, mmSetrPs(0.01f, 5.0f, 0.0f, -10.0f))
}

public fun testMmSqrtSs() {
    val a = mmSetrPs(4.0f, 13.0f, 16.0f, 100.0f)
    val r = mmSqrtSs(a)
    val e = mmSetrPs(2.0f, 13.0f, 16.0f, 100.0f)
    assertEqM128(r, e)
}

public fun testMmSqrtPs() {
    val a = mmSetrPs(4.0f, 13.0f, 16.0f, 100.0f)
    val r = mmSqrtPs(a)
    val e = mmSetrPs(2.0f, 3.6055512f, 4.0f, 10.0f)
    assertEqM128(r, e)
}

public fun testMmRcpSs() {
    val a = mmSetrPs(4.0f, 13.0f, 16.0f, 100.0f)
    val r = mmRcpSs(a)
    val e = mmSetrPs(0.24993896f, 13.0f, 16.0f, 100.0f)
    val relErr = 0.00048828125
    assertApproxEq(getM128(r, 0), getM128(e, 0), 2.0 * relErr)
    for (i in 1 until 4) {
        check(getM128(r, i) == getM128(e, i))
    }
}

public fun testMmRcpPs() {
    val a = mmSetrPs(4.0f, 13.0f, 16.0f, 100.0f)
    val r = mmRcpPs(a)
    val e = mmSetrPs(0.24993896f, 0.0769043f, 0.06248474f, 0.0099983215f)
    val relErr = 0.00048828125
    for (i in 0 until 4) {
        assertApproxEq(getM128(r, i), getM128(e, i), 2.0 * relErr)
    }
}

public fun testMmRsqrtSs() {
    val a = mmSetrPs(4.0f, 13.0f, 16.0f, 100.0f)
    val r = mmRsqrtSs(a)
    val e = mmSetrPs(0.49987793f, 13.0f, 16.0f, 100.0f)
    val relErr = 0.00048828125
    for (i in 0 until 4) {
        assertApproxEq(getM128(r, i), getM128(e, i), 2.0 * relErr)
    }
}

public fun testMmRsqrtPs() {
    val a = mmSetrPs(4.0f, 13.0f, 16.0f, 100.0f)
    val r = mmRsqrtPs(a)
    val e = mmSetrPs(0.49987793f, 0.2772827f, 0.24993896f, 0.099990845f)
    val relErr = 0.00048828125
    for (i in 0 until 4) {
        assertApproxEq(getM128(r, i), getM128(e, i), 2.0 * relErr)
    }
}

public fun testMmMinSs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmMinSs(a, b)
    assertEqM128(r, mmSetrPs(-100.0f, 5.0f, 0.0f, -10.0f))
}

public fun testMmMinPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmMinPs(a, b)
    assertEqM128(r, mmSetrPs(-100.0f, 5.0f, 0.0f, -10.0f))
    // `_mm_min_ps` can **not** be implemented using the `simdMinimumNumberNsz` rust
    // intrinsic. That intrinsic is lowered by the llvm codegen backend to `llvm.minimumnum.v *`
    // llvm intrinsic with the `nsz` attribute. The `nsz` attribute means -0.0 is handled
    // non-deterministically. The `minps` x86 instruction however has a deterministic semantics
    // for signed zeros.
    val a1 = mmSetrPs(-0.0f, 0.0f, 0.0f, 0.0f)
    val b1 = mmSetrPs(0.0f, 0.0f, 0.0f, 0.0f)
    val r1 = mmMinPs(a1, b1).asF32x4().toBits()
    val r2 = mmMinPs(b1, a1).asF32x4().toBits()
    val a2 = a1.asF32x4().toBits()
    val b2 = b1.asF32x4().toBits()
    check(r1 == b2)
    check(r2 == a2)
    check(a2 != b2)
    // sanity check that -0.0 is actually present
}

public fun testMmMaxSs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmMaxSs(a, b)
    assertEqM128(r, mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f))
}

public fun testMmMaxPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmMaxPs(a, b)
    assertEqM128(r, mmSetrPs(-1.0f, 20.0f, 0.0f, -5.0f))
    // Check SSE-specific semantics for -0.0 handling.
    val a1 = mmSetrPs(-0.0f, 0.0f, 0.0f, 0.0f)
    val b1 = mmSetrPs(0.0f, 0.0f, 0.0f, 0.0f)
    val r1 = mmMaxPs(a1, b1).asF32x4().toBits()
    val r2 = mmMaxPs(b1, a1).asF32x4().toBits()
    val a2 = a1.asF32x4().toBits()
    val b2 = b1.asF32x4().toBits()
    check(r1 == b2)
    check(r2 == a2)
    check(a2 != b2)
    // sanity check that -0.0 is actually present
}

public fun testMmAndPs() {
    val a = f32x4.fromBits(u32x4.splat(0x3)).asM128()
    val b = f32x4.fromBits(u32x4.splat(0x5)).asM128()
    val r = mmAndPs(blackBox(a), blackBox(b))
    val e = f32x4.fromBits(u32x4.splat(0x1)).asM128()
    assertEqM128(r, e)
}

public fun testMmAndnotPs() {
    val a = f32x4.fromBits(u32x4.splat(0x3)).asM128()
    val b = f32x4.fromBits(u32x4.splat(0x5)).asM128()
    val r = mmAndnotPs(blackBox(a), blackBox(b))
    val e = f32x4.fromBits(u32x4.splat(0x4)).asM128()
    assertEqM128(r, e)
}

public fun testMmOrPs() {
    val a = f32x4.fromBits(u32x4.splat(0x3)).asM128()
    val b = f32x4.fromBits(u32x4.splat(0x5)).asM128()
    val r = mmOrPs(blackBox(a), blackBox(b))
    val e = f32x4.fromBits(u32x4.splat(0x7)).asM128()
    assertEqM128(r, e)
}

public fun testMmXorPs() {
    val a = f32x4.fromBits(u32x4.splat(0x3)).asM128()
    val b = f32x4.fromBits(u32x4.splat(0x5)).asM128()
    val r = mmXorPs(blackBox(a), blackBox(b))
    val e = f32x4.fromBits(u32x4.splat(0x6)).asM128()
    assertEqM128(r, e)
}

public fun testMmCmpeqSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(-1.0f, 5.0f, 6.0f, 7.0f)
    val r = mmCmpeqSs(a, b).asF32x4().toBits()
    val e = f32x4.new(Float.fromBits(0u.toInt()), 2.0, 3.0, 4.0).toBits()
    check(r == e)
    val b2 = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val r2 = mmCmpeqSs(a, b2).asF32x4().toBits()
    val e2 = f32x4.new(Float.fromBits(0xffffffffu.toInt()), 2.0, 3.0, 4.0).toBits()
    check(r2 == e2)
}

public fun testMmCmpltSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u
    // a.extract(0) < b.extract(0)
    val c1 = 0u
    // a.extract(0) < c.extract(0)
    val d1 = 0u.inv()
    // a.extract(0) < d.extract(0)
    val rb = mmCmpltSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpltSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpltSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpleSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u
    // a.extract(0) <= b.extract(0)
    val c1 = 0u.inv()
    // a.extract(0) <= c.extract(0)
    val d1 = 0u.inv()
    // a.extract(0) <= d.extract(0)
    val rb = mmCmpleSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpleSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpleSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpgtSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u.inv()
    // a.extract(0) > b.extract(0)
    val c1 = 0u
    // a.extract(0) > c.extract(0)
    val d1 = 0u
    // a.extract(0) > d.extract(0)
    val rb = mmCmpgtSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpgtSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpgtSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpgeSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u.inv()
    // a.extract(0) >= b.extract(0)
    val c1 = 0u.inv()
    // a.extract(0) >= c.extract(0)
    val d1 = 0u
    // a.extract(0) >= d.extract(0)
    val rb = mmCmpgeSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpgeSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpgeSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpneqSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u.inv()
    // a.extract(0) != b.extract(0)
    val c1 = 0u
    // a.extract(0) != c.extract(0)
    val d1 = 0u.inv()
    // a.extract(0) != d.extract(0)
    val rb = mmCmpneqSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpneqSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpneqSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpnltSs() {
    // Note: this test is exactly the same as for `_mm_cmpge_ss`, but there
    // must be a difference. It may have to do with behavior in the
    // presence of NaNs (signaling or quiet). If so, we should add tests
    // for those.
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u.inv()
    // a.extract(0) >= b.extract(0)
    val c1 = 0u.inv()
    // a.extract(0) >= c.extract(0)
    val d1 = 0u
    // a.extract(0) >= d.extract(0)
    val rb = mmCmpnltSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpnltSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpnltSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpnleSs() {
    // Note: this test is exactly the same as for `_mm_cmpgt_ss`, but there
    // must be a difference. It may have to do with behavior in the
    // presence
    // of NaNs (signaling or quiet). If so, we should add tests for those.
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u.inv()
    // a.extract(0) > b.extract(0)
    val c1 = 0u
    // a.extract(0) > c.extract(0)
    val d1 = 0u
    // a.extract(0) > d.extract(0)
    val rb = mmCmpnleSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpnleSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpnleSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpngtSs() {
    // Note: this test is exactly the same as for `_mm_cmple_ss`, but there
    // must be a difference. It may have to do with behavior in the
    // presence of NaNs (signaling or quiet). If so, we should add tests
    // for those.
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u
    // a.extract(0) <= b.extract(0)
    val c1 = 0u.inv()
    // a.extract(0) <= c.extract(0)
    val d1 = 0u.inv()
    // a.extract(0) <= d.extract(0)
    val rb = mmCmpngtSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpngtSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpngtSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpngeSs() {
    // Note: this test is exactly the same as for `_mm_cmplt_ss`, but there
    // must be a difference. It may have to do with behavior in the
    // presence of NaNs (signaling or quiet). If so, we should add tests
    // for those.
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(1.0f, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u
    // a.extract(0) < b.extract(0)
    val c1 = 0u
    // a.extract(0) < c.extract(0)
    val d1 = 0u.inv()
    // a.extract(0) < d.extract(0)
    val rb = mmCmpngeSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpngeSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpngeSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpordSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(NAN, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u.inv()
    // a.extract(0) ord b.extract(0)
    val c1 = 0u
    // a.extract(0) ord c.extract(0)
    val d1 = 0u.inv()
    // a.extract(0) ord d.extract(0)
    val rb = mmCmpordSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpordSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpordSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpunordSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(0.0f, 5.0f, 6.0f, 7.0f)
    val c = mmSetrPs(NAN, 5.0f, 6.0f, 7.0f)
    val d = mmSetrPs(2.0f, 5.0f, 6.0f, 7.0f)
    val b1 = 0u
    // a.extract(0) unord b.extract(0)
    val c1 = 0u.inv()
    // a.extract(0) unord c.extract(0)
    val d1 = 0u
    // a.extract(0) unord d.extract(0)
    val rb = mmCmpunordSs(a, b).asF32x4().toBits()
    val eb = f32x4.new(Float.fromBits(b1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rb == eb)
    val rc = mmCmpunordSs(a, c).asF32x4().toBits()
    val ec = f32x4.new(Float.fromBits(c1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rc == ec)
    val rd = mmCmpunordSs(a, d).asF32x4().toBits()
    val ed = f32x4.new(Float.fromBits(d1.toInt()), 2.0, 3.0, 4.0).toBits()
    check(rd == ed)
}

public fun testMmCmpeqPs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, NAN)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(fls, fls, tru, fls)
    val r = mmCmpeqPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpltPs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, NAN)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(tru, fls, fls, fls)
    val r = mmCmpltPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmplePs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, 4.0f)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, NAN)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(tru, fls, tru, fls)
    val r = mmCmplePs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpgtPs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, 42.0f)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(fls, tru, fls, fls)
    val r = mmCmpgtPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpgePs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, 42.0f)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(fls, tru, tru, fls)
    val r = mmCmpgePs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpneqPs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, NAN)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(tru, tru, fls, tru)
    val r = mmCmpneqPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpnltPs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, 5.0f)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(fls, tru, tru, tru)
    val r = mmCmpnltPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpnlePs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, 5.0f)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(fls, tru, fls, tru)
    val r = mmCmpnlePs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpngtPs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, 5.0f)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(tru, fls, tru, tru)
    val r = mmCmpngtPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpngePs() {
    val a = mmSetrPs(10.0f, 50.0f, 1.0f, NAN)
    val b = mmSetrPs(15.0f, 20.0f, 1.0f, 5.0f)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(tru, fls, fls, tru)
    val r = mmCmpngePs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpordPs() {
    val a = mmSetrPs(10.0f, 50.0f, NAN, NAN)
    val b = mmSetrPs(15.0f, NAN, 1.0f, NAN)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(tru, fls, fls, fls)
    val r = mmCmpordPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmCmpunordPs() {
    val a = mmSetrPs(10.0f, 50.0f, NAN, NAN)
    val b = mmSetrPs(15.0f, NAN, 1.0f, NAN)
    val tru = 0u.inv()
    val fls = 0u
    val e = u32x4.new(fls, tru, tru, tru)
    val r = mmCmpunordPs(a, b).asF32x4().toBits()
    check(r == e)
}

public fun testMmComieqSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(1, 0, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmComieqSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmComiltSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(0, 1, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmComiltSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmComileSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(1, 1, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmComileSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmComigtSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(0, 0, 1, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmComigtSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmComigeSs() {
    val aa = floatArrayOf(3.0f, 23.0f, 12.0f, NAN)
    val bb = floatArrayOf(3.0f, 1.5f, 47.5f, NAN)
    val ee = intArrayOf(1, 1, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmComigeSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmComineqSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(0, 1, 1, 1)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmComineqSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmUcomieqSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(1, 0, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmUcomieqSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmUcomiltSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(0, 1, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmUcomiltSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmUcomileSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(1, 1, 0, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmUcomileSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmUcomigtSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(0, 0, 1, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmUcomigtSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmUcomigeSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(1, 0, 1, 0)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmUcomigeSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmUcomineqSs() {
    val aa = floatArrayOf(3.0f, 12.0f, 23.0f, NAN)
    val bb = floatArrayOf(3.0f, 47.5f, 1.5f, NAN)
    val ee = intArrayOf(0, 1, 1, 1)
    for (i in 0 until 4) {
        val a = mmSetrPs(aa[i], 1.0f, 2.0f, 3.0f)
        val b = mmSetrPs(bb[i], 0.0f, 2.0f, 4.0f)
        val r = mmUcomineqSs(a, b)
        check(ee[i] == r)
    }
}

public fun testMmCvtssSi32() {
    val inputs = floatArrayOf(42.0f, -3.1f, 4.0e10f, 4.0e-20f, NAN, 2147483500.1f)
    val result = intArrayOf(42, -3, Int.MIN_VALUE, 0, Int.MIN_VALUE, 2147483520)
    for (i in 0 until inputs.size) {
        val x = mmSetrPs(inputs[i], 1.0f, 3.0f, 4.0f)
        val e = result[i]
        val r = mmCvtssSi32(x)
        check(e == r)
    }
}

public fun testMmCvtSs2si() {
    val inputs = floatArrayOf(42.0f, -3.1f, 4.0e10f, 4.0e-20f, NAN, 2147483500.1f)
    val result = intArrayOf(42, -3, Int.MIN_VALUE, 0, Int.MIN_VALUE, 2147483520)
    for (i in 0 until inputs.size) {
        val x = mmSetrPs(inputs[i], 1.0f, 3.0f, 4.0f)
        val e = result[i]
        val r = mmCvtSs2si(x)
        check(e == r)
    }
}

public fun testMmCvttssSi32() {
    val inputs = listOf(Pair(42.0f, 42), Pair(-31.4, -31), Pair(-33.5, -33), Pair(-34.5, -34), Pair(10.999, 10), Pair(-5.99, -5), Pair(4.0e10, Int.MIN_VALUE), Pair(4.0e-10, 0), Pair(NAN, Int.MIN_VALUE), Pair(2147483500.1, 2147483520))
    for ((i, xi, e) in inputs.iter().mapIndexed { index, item -> Pair(index, item) }) {
        val x = mmSetrPs(xi, 1.0f, 3.0f, 4.0f)
        val r = mmCvttssSi32(x)
        check(e == r)
    }
}

public fun testMmCvttSs2si() {
    val inputs = listOf(Pair(42.0f, 42), Pair(-31.4, -31), Pair(-33.5, -33), Pair(-34.5, -34), Pair(10.999, 10), Pair(-5.99, -5), Pair(4.0e10, Int.MIN_VALUE), Pair(4.0e-10, 0), Pair(NAN, Int.MIN_VALUE), Pair(2147483500.1, 2147483520))
    for ((i, xi, e) in inputs.iter().mapIndexed { index, item -> Pair(index, item) }) {
        val x = mmSetrPs(xi, 1.0f, 3.0f, 4.0f)
        val r = mmCvttSs2si(x)
        check(e == r)
    }
}

public fun testMmCvtsi32Ss() {
    val a = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmCvtsi32Ss(a, 4555)
    assertEqM128(mmSetrPs(4555.0f, 6.0f, 7.0f, 8.0f), r)
    val r1 = mmCvtsi32Ss(a, 322223333)
    assertEqM128(mmSetrPs(322223333.0f, 6.0f, 7.0f, 8.0f), r1)
    val r2 = mmCvtsi32Ss(a, -432)
    assertEqM128(mmSetrPs(-432.0f, 6.0f, 7.0f, 8.0f), r2)
    val r3 = mmCvtsi32Ss(a, -322223333)
    assertEqM128(mmSetrPs(-322223333.0f, 6.0f, 7.0f, 8.0f), r3)
}

public fun testMmCvtSi2ss() {
    val a = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmCvtSi2ss(a, 4555)
    assertEqM128(mmSetrPs(4555.0f, 6.0f, 7.0f, 8.0f), r)
    val r1 = mmCvtSi2ss(a, 322223333)
    assertEqM128(mmSetrPs(322223333.0f, 6.0f, 7.0f, 8.0f), r1)
    val r2 = mmCvtSi2ss(a, -432)
    assertEqM128(mmSetrPs(-432.0f, 6.0f, 7.0f, 8.0f), r2)
    val r3 = mmCvtSi2ss(a, -322223333)
    assertEqM128(mmSetrPs(-322223333.0f, 6.0f, 7.0f, 8.0f), r3)
}

public fun testMmCvtssF32() {
    val a = mmSetrPs(312.0134f, 5.0f, 6.0f, 7.0f)
    check(mmCvtssF32(a) == 312.0134f.toFloat())
}

public fun testMmSetSs() {
    val r = mmSetSs(blackBox(4.25))
    assertEqM128(r, mmSetrPs(4.25f, 0.0f, 0.0f, 0.0f))
}

public fun testMmSet1Ps() {
    val r = mmSet1Ps(blackBox(4.25))
    check(getM128(r, 0) == 4.25)
    check(getM128(r, 1) == 4.25)
    check(getM128(r, 2) == 4.25)
    check(getM128(r, 3) == 4.25)
}

public fun testMmSetPs1() {
    val r = mmSetPs1(blackBox(4.25))
    check(getM128(r, 0) == 4.25)
    check(getM128(r, 1) == 4.25)
    check(getM128(r, 2) == 4.25)
    check(getM128(r, 3) == 4.25)
}

public fun testMmSetPs() {
    val r = mmSetPs(blackBox(1.0), blackBox(2.0), blackBox(3.0), blackBox(4.0))
    check(getM128(r, 0) == 4.0)
    check(getM128(r, 1) == 3.0)
    check(getM128(r, 2) == 2.0)
    check(getM128(r, 3) == 1.0)
}

public fun testMmSetrPs() {
    val r = mmSetrPs(blackBox(1.0), blackBox(2.0), blackBox(3.0), blackBox(4.0))
    assertEqM128(r, mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMmSetzeroPs() {
    val r = blackBox(mmSetzeroPs())
    assertEqM128(r, mmSet1Ps(0.0f))
}

public fun testMmShuffle() {
    check(mmShuffle(0u, 1u, 1u, 3u) == 0x17)
    check(mmShuffle(3u, 1u, 1u, 0u) == 0xD4)
    check(mmShuffle(1u, 2u, 2u, 1u) == 0x69)
}

public fun testMmShufflePs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmShufflePs(a, b, 0x17)
    assertEqM128(r, mmSetrPs(4.0f, 2.0f, 6.0f, 5.0f))
}

public fun testMmUnpackhiPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmUnpackhiPs(a, b)
    assertEqM128(r, mmSetrPs(3.0f, 7.0f, 4.0f, 8.0f))
}

public fun testMmUnpackloPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmUnpackloPs(a, b)
    assertEqM128(r, mmSetrPs(1.0f, 5.0f, 2.0f, 6.0f))
}

public fun testMmMovehlPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmMovehlPs(a, b)
    assertEqM128(r, mmSetrPs(7.0f, 8.0f, 3.0f, 4.0f))
}

public fun testMmMovelhPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmMovelhPs(a, b)
    assertEqM128(r, mmSetrPs(1.0f, 2.0f, 5.0f, 6.0f))
}

public fun testMmLoadSs() {
    val a = 42.0f
    val r = mmLoadSs(addrOf(a))
    assertEqM128(r, mmSetrPs(42.0f, 0.0f, 0.0f, 0.0f))
}

public fun testMmLoad1Ps() {
    val a = 42.0f
    val r = mmLoad1Ps(addrOf(a))
    assertEqM128(r, mmSetrPs(42.0f, 42.0f, 42.0f, 42.0f))
}

public fun testMmLoadPs1() {
    val a = 42.0f
    val r = mmLoadPs1(addrOf(a))
    assertEqM128(r, mmSetrPs(42.0f, 42.0f, 42.0f, 42.0f))
}

public fun testMmLoadPs() {
    val vals = Memory(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f), fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    // guaranteed to be aligned to 16 bytes
    val p = vals.data.asPtr()
    val r = mmLoadPs(p)
    val e = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmLoaduPs() {
    val vals = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val p = ConstPtr((vals.asPtr()).rawAddress + ((3).toLong() * (PrimitiveLayouts.F32).strideBytes))
    val r = mmLoaduPs(blackBox(p))
    assertEqM128(r, mmSetrPs(4.0f, 5.0f, 6.0f, 7.0f))
}

public fun testMmLoadrPs() {
    val vals = Memory(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f), fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    // guaranteed to be aligned to 16 bytes
    val p = vals.data.asPtr()
    val r = mmLoadrPs(p)
    val e = mmSetrPs(4.0f, 3.0f, 2.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmStoreSs() {
    val vals = FloatArray(8) { 0.0f }
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    run {
        mmStoreSs(MutPtr((vals.asMutPtr()).rawAddress + ((1).toLong() * (PrimitiveLayouts.F32).strideBytes)), a)
    }
    check(vals[0] == 0.0f.toFloat())
    check(vals[1] == 1.0f.toFloat())
    check(vals[2] == 0.0f.toFloat())
}

public fun testMmStore1Ps() {
    val vals = Memory(data = FloatArray(4) { 0.0f }, fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val p = vals.data.asMutPtr()
    mmStore1Ps(p, blackBox(a))
    check(vals.data == floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f))
}

public fun testMmStorePs1() {
    val vals = Memory(data = FloatArray(4) { 0.0f }, fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val p = vals.data.asMutPtr()
    mmStorePs1(p, blackBox(a))
    check(vals.data == floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f))
}

public fun testMmStorePs() {
    val vals = Memory(data = FloatArray(4) { 0.0f }, fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    // guaranteed to be aligned to 16 bytes
    val p = vals.data.asMutPtr()
    run {
        mmStorePs(p, blackBox(a))
    }
    check(vals.data == floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMmStorerPs() {
    val vals = Memory(data = FloatArray(4) { 0.0f }, fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    // guaranteed to be aligned to 16 bytes
    val p = vals.data.asMutPtr()
    run {
        mmStorerPs(p, blackBox(a))
    }
    check(vals.data == floatArrayOf(4.0f, 3.0f, 2.0f, 1.0f))
}

public fun testMmStoreuPs() {
    open class Memory8(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

        public fun pointerLayout(): TypeLayout<Memory8> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory8(ptr, layout) }, { it.data })
    }
    // guaranteed to be aligned to 16 bytes
    val vals = Memory8(data = FloatArray(8) { 0.0f }, fieldLayout = PrimitiveArrayLayouts.floatArray((8).toInt()))
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    // guaranteed to be *not* aligned to 16 bytes
    val p = MutPtr((vals.data.asMutPtr()).rawAddress + ((1).toLong() * (PrimitiveLayouts.F32).strideBytes))
    run {
        mmStoreuPs(p, blackBox(a))
    }
    check(vals.data == floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 0.0f, 0.0f))
}

public fun testMmMoveSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmMoveSs(a, b)
    val e = mmSetrPs(5.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(e, r)
}

public fun testMmMovemaskPs() {
    val r = mmMovemaskPs(mmSetrPs(-1.0f, 5.0f, -5.0f, 0.0f))
    check(r == 0x5)
    val r1 = mmMovemaskPs(mmSetrPs(-1.0f, -5.0f, -5.0f, 0.0f))
    check(r1 == 0x7)
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
public fun testMmSfence() {
    mmSfence()
}

public fun testMmTranspose4Ps() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val c = mmSetrPs(9.0f, 10.0f, 11.0f, 12.0f)
    val d = mmSetrPs(13.0f, 14.0f, 15.0f, 16.0f)
    mmTranspose4Ps(a, b, c, d)
    assertEqM128(a, mmSetrPs(1.0f, 5.0f, 9.0f, 13.0f))
    assertEqM128(b, mmSetrPs(2.0f, 6.0f, 10.0f, 14.0f))
    assertEqM128(c, mmSetrPs(3.0f, 7.0f, 11.0f, 15.0f))
    assertEqM128(d, mmSetrPs(4.0f, 8.0f, 12.0f, 16.0f))
}

public open class Memory internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
) {
    public var data: FloatArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })

    public companion object {
        public operator fun invoke(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()): Memory = Memory(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()): Memory = Memory(fieldStorage, fieldLayout)
    }
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamPs() {
    val a = mmSet1Ps(7.0f)
    val mem = Memory(data = FloatArray(4) { -1.0f }, fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    run {
        mmStreamPs(addrOfMut(mem.data[0]), a)
    }
    mmSfence()
    for (i in 0 until 4) {
        check(mem.data[i] == getM128(a, i))
    }
}
