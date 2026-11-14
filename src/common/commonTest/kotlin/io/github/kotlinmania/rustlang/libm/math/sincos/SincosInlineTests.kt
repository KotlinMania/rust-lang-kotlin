// port-lint: tests math/sincos.rs
package io.github.kotlinmania.rustlang.libm.math.sincos

public val TOLERANCE: Double = 1e-6

public class SincosInlineTests {
    @kotlin.test.Test
    public fun withPi() {
        val (s, c) = sincos(kotlin.math.PI)
        check((s - 0.0).abs() < TOLERANCE) { "|$s - ${0.0}| = ${(s - 0.0).abs()} >= $TOLERANCE" }
        check((c + 1.0).abs() < TOLERANCE) { "|$c + ${1.0}| = ${(s + 1.0).abs()} >= $TOLERANCE" }
    }

    @kotlin.test.Test
    public fun rotationalSymmetry() {
        val N: Int = 24
        for (n in 0 until N) {
            val theta = 2.0 * kotlin.math.PI * (n.toDouble()) / (N.toDouble())
            val (s, c) = sincos(theta)
            val (sPlus, cPlus) = sincos(theta + 2.0 * kotlin.math.PI)
            val (sMinus, cMinus) = sincos(theta - 2.0 * kotlin.math.PI)
            check((s - sPlus).abs() < TOLERANCE) { "|$s - $sPlus| = ${(s - sPlus).abs()} >= $TOLERANCE" }
            check((s - sMinus).abs() < TOLERANCE) { "|$s - $sMinus| = ${(s - sMinus).abs()} >= $TOLERANCE" }
            check((c - cPlus).abs() < TOLERANCE) { "|$c - $cPlus| = ${(c - cPlus).abs()} >= $TOLERANCE" }
            check((c - cMinus).abs() < TOLERANCE) { "|$c - $cMinus| = ${(c - cMinus).abs()} >= $TOLERANCE" }
        }
    }
}
