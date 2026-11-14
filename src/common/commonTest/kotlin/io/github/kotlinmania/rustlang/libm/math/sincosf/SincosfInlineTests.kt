// port-lint: tests math/sincosf.rs
package io.github.kotlinmania.rustlang.libm.math.sincosf

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
            check((s - sPlus).abs() < TOLERANCE) { "|$s - $sPlus| = ${(s - sPlus).abs()} >= $TOLERANCE" }
            check((s - sMinus).abs() < TOLERANCE) { "|$s - $sMinus| = ${(s - sMinus).abs()} >= $TOLERANCE" }
            check((c - cPlus).abs() < TOLERANCE) { "|$c - $cPlus| = ${(c - cPlus).abs()} >= $TOLERANCE" }
            check((c - cMinus).abs() < TOLERANCE) { "|$c - $cMinus| = ${(c - cMinus).abs()} >= $TOLERANCE" }
        }
    }
}
