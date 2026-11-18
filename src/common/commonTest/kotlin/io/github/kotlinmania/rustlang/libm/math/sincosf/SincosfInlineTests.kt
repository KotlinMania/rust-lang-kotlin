// port-lint: tests math/sincosf.rs
package io.github.kotlinmania.rustlang.libm.math.sincosf

import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32

public class SincosfInlineTests {
    @kotlin.test.Test
    public fun rotationalSymmetry() {
        val N: Int = 24
        for (n in 0 until N) {
            val TOLERANCE: kotlin.Float = 1e-6f
            val theta = 2.0 * 3.1415927f * (n.toFloat()) / (N.toFloat())
            val (s, c) = sincosf(theta.toFloat())
            val (sPlus, cPlus) = sincosf(theta + 2.0 * 3.1415927f)
            val (sMinus, cMinus) = sincosf(theta - 2.0 * 3.1415927f)
            check(kmFabsf32((s - sPlus)) < TOLERANCE) { "|$s - $sPlus| = ${kmFabsf32((s - sPlus))} >= $TOLERANCE" }
            check(kmFabsf32((s - sMinus)) < TOLERANCE) { "|$s - $sMinus| = ${kmFabsf32((s - sMinus))} >= $TOLERANCE" }
            check(kmFabsf32((c - cPlus)) < TOLERANCE) { "|$c - $cPlus| = ${kmFabsf32((c - cPlus))} >= $TOLERANCE" }
            check(kmFabsf32((c - cMinus)) < TOLERANCE) { "|$c - $cMinus| = ${kmFabsf32((c - cMinus))} >= $TOLERANCE" }
        }
    }
}
