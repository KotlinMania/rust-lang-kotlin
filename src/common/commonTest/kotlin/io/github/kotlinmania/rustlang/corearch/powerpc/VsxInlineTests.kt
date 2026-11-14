// port-lint: tests powerpc/vsx.rs
package io.github.kotlinmania.rustlang.corearch.powerpc

// PowerPC Vector Scalar eXtensions (VSX) intrinsics.
//
// The references are: [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA
// NVlink)] and [POWER ISA v3.0B (for POWER9)].
//
// [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA NVlink)]: https://ibm.box.com/s/jd5w15gz301s5b5dt375mshpq9c3lh4u
// [POWER ISA v3.0B (for POWER9)]: https://ibm.box.com/s/1hzcwkwf8rbju5h9iyf44wm94amnlcrv

public fun testVecXxpermdiU64x2() {
    val a = VectorUnsignedLong.from(u64x2.fromArray(listOf(0, 1)))
    val b = VectorUnsignedLong.from(u64x2.fromArray(listOf(2, 3)))
    run {
        check(u64x2.fromArray(listOf(0, 2)) == u64x2.from(vecXxpermdi(a, b, 0)))
        check(u64x2.fromArray(listOf(1, 2)) == u64x2.from(vecXxpermdi(a, b, 1)))
        check(u64x2.fromArray(listOf(0, 3)) == u64x2.from(vecXxpermdi(a, b, 2)))
        check(u64x2.fromArray(listOf(1, 3)) == u64x2.from(vecXxpermdi(a, b, 3)))
    }
}

public fun testVecXxpermdiI64x2() {
    val a = VectorSignedLong.from(i64x2.fromArray(listOf(0, -1)))
    val b = VectorSignedLong.from(i64x2.fromArray(listOf(2, -3)))
    run {
        check(i64x2.fromArray(listOf(0, 2)) == i64x2.from(vecXxpermdi(a, b, 0)))
        check(i64x2.fromArray(listOf(-1, 2)) == i64x2.from(vecXxpermdi(a, b, 1)))
        check(i64x2.fromArray(listOf(0, -3)) == i64x2.from(vecXxpermdi(a, b, 2)))
        check(i64x2.fromArray(listOf(-1, -3)) == i64x2.from(vecXxpermdi(a, b, 3)))
    }
}

public fun testVecXxpermdiM64x2() {
    val a = VectorBoolLong.from(m64x2.fromArray(booleanArrayOf(false, true)))
    val b = VectorBoolLong.from(m64x2.fromArray(booleanArrayOf(false, true)))
    run {
        check(m64x2.fromArray(booleanArrayOf(false, false)) == m64x2.from(vecXxpermdi(a, b, 0)))
        check(m64x2.fromArray(booleanArrayOf(true, false)) == m64x2.from(vecXxpermdi(a, b, 1)))
        check(m64x2.fromArray(booleanArrayOf(false, true)) == m64x2.from(vecXxpermdi(a, b, 2)))
        check(m64x2.fromArray(booleanArrayOf(true, true)) == m64x2.from(vecXxpermdi(a, b, 3)))
    }
}

public fun testVecXxpermdiF64x2() {
    val a = VectorDouble.from(f64x2.fromArray(listOf(0.0, 1.0)))
    val b = VectorDouble.from(f64x2.fromArray(listOf(2.0, 3.0)))
    run {
        check(f64x2.fromArray(listOf(0.0, 2.0)) == f64x2.from(vecXxpermdi(a, b, 0)))
        check(f64x2.fromArray(listOf(1.0, 2.0)) == f64x2.from(vecXxpermdi(a, b, 1)))
        check(f64x2.fromArray(listOf(0.0, 3.0)) == f64x2.from(vecXxpermdi(a, b, 2)))
        check(f64x2.fromArray(listOf(1.0, 3.0)) == f64x2.from(vecXxpermdi(a, b, 3)))
    }
}
