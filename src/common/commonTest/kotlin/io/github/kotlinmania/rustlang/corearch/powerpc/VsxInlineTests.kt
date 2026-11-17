// port-lint: tests powerpc/vsx.rs
package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.SimdM

// PowerPC Vector Scalar eXtensions (VSX) intrinsics.
//
// The references are: [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA
// NVlink)] and [POWER ISA v3.0B (for POWER9)].
//
// [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA NVlink)]: https://ibm.box.com/s/jd5w15gz301s5b5dt375mshpq9c3lh4u
// [POWER ISA v3.0B (for POWER9)]: https://ibm.box.com/s/1hzcwkwf8rbju5h9iyf44wm94amnlcrv

public fun testVecXxpermdiU64x2() {
    val a = VectorUnsignedLong.from(Simd<ULong>((listOf(0, 1)).toList()))
    val b = VectorUnsignedLong.from(Simd<ULong>((listOf(2, 3)).toList()))
    run {
        check(Simd<ULong>((listOf(0, 2)).toList()) == u64x2.from(vecXxpermdi(a, b, 0)))
        check(Simd<ULong>((listOf(1, 2)).toList()) == u64x2.from(vecXxpermdi(a, b, 1)))
        check(Simd<ULong>((listOf(0, 3)).toList()) == u64x2.from(vecXxpermdi(a, b, 2)))
        check(Simd<ULong>((listOf(1, 3)).toList()) == u64x2.from(vecXxpermdi(a, b, 3)))
    }
}

public fun testVecXxpermdiI64x2() {
    val a = VectorSignedLong.from(Simd<Long>((listOf(0, -1)).toList()))
    val b = VectorSignedLong.from(Simd<Long>((listOf(2, -3)).toList()))
    run {
        check(Simd<Long>((listOf(0, 2)).toList()) == i64x2.from(vecXxpermdi(a, b, 0)))
        check(Simd<Long>((listOf(-1, 2)).toList()) == i64x2.from(vecXxpermdi(a, b, 1)))
        check(Simd<Long>((listOf(0, -3)).toList()) == i64x2.from(vecXxpermdi(a, b, 2)))
        check(Simd<Long>((listOf(-1, -3)).toList()) == i64x2.from(vecXxpermdi(a, b, 3)))
    }
}

public fun testVecXxpermdiM64x2() {
    val a = VectorBoolLong.from(SimdM<Long>((booleanArrayOf(false, true)).toList()))
    val b = VectorBoolLong.from(SimdM<Long>((booleanArrayOf(false, true)).toList()))
    run {
        check(SimdM<Long>((booleanArrayOf(false, false)).toList()) == m64x2.from(vecXxpermdi(a, b, 0)))
        check(SimdM<Long>((booleanArrayOf(true, false)).toList()) == m64x2.from(vecXxpermdi(a, b, 1)))
        check(SimdM<Long>((booleanArrayOf(false, true)).toList()) == m64x2.from(vecXxpermdi(a, b, 2)))
        check(SimdM<Long>((booleanArrayOf(true, true)).toList()) == m64x2.from(vecXxpermdi(a, b, 3)))
    }
}

public fun testVecXxpermdiF64x2() {
    val a = VectorDouble.from(Simd<Double>((listOf(0.0, 1.0)).toList()))
    val b = VectorDouble.from(Simd<Double>((listOf(2.0, 3.0)).toList()))
    run {
        check(Simd<Double>((listOf(0.0, 2.0)).toList()) == f64x2.from(vecXxpermdi(a, b, 0)))
        check(Simd<Double>((listOf(1.0, 2.0)).toList()) == f64x2.from(vecXxpermdi(a, b, 1)))
        check(Simd<Double>((listOf(0.0, 3.0)).toList()) == f64x2.from(vecXxpermdi(a, b, 2)))
        check(Simd<Double>((listOf(1.0, 3.0)).toList()) == f64x2.from(vecXxpermdi(a, b, 3)))
    }
}
