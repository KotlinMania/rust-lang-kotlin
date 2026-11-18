// port-lint: tests math/sincos.rs
package io.github.kotlinmania.rustlang.libm.math.sincos

import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64

public val TOLERANCE: Double = 1e-6

public class SincosInlineTests {
    @kotlin.test.Test
    public fun withPi() {
        val (s, c) = sincos(kotlin.math.PI)
        check(kmFabsf64((s - 0.0)) < TOLERANCE) { "|$s - ${0.0}| = ${kmFabsf64((s - 0.0))} >= $TOLERANCE" }
        check(kmFabsf64((c + 1.0)) < TOLERANCE) { "|$c + ${1.0}| = ${kmFabsf64((s + 1.0))} >= $TOLERANCE" }
    }

    @kotlin.test.Test
    public fun rotationalSymmetry() {
        val N: Int = 24
        for (n in 0 until N) {
            val theta = 2.0 * kotlin.math.PI * (n.toDouble()) / (N.toDouble())
            val (s, c) = sincos(theta)
            val (sPlus, cPlus) = sincos(theta + 2.0 * kotlin.math.PI)
            val (sMinus, cMinus) = sincos(theta - 2.0 * kotlin.math.PI)
            check(kmFabsf64((s - sPlus)) < TOLERANCE) { "|$s - $sPlus| = ${kmFabsf64((s - sPlus))} >= $TOLERANCE" }
            check(kmFabsf64((s - sMinus)) < TOLERANCE) { "|$s - $sMinus| = ${kmFabsf64((s - sMinus))} >= $TOLERANCE" }
            check(kmFabsf64((c - cPlus)) < TOLERANCE) { "|$c - $cPlus| = ${kmFabsf64((c - cPlus))} >= $TOLERANCE" }
            check(kmFabsf64((c - cMinus)) < TOLERANCE) { "|$c - $cMinus| = ${kmFabsf64((c - cMinus))} >= $TOLERANCE" }
        }
    }
}
