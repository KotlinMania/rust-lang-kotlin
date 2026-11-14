// port-lint: tests math/exp2m1f16.rs
package io.github.kotlinmania.rustlang.libm.math.exp2m1f16

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex

public class Exp2m1f16InlineTests {
    @kotlin.test.Test
    public fun knownResults() {
        for ((x, expected) in listOf(Pair(F16.ZERO, F16.ZERO), Pair(F16.NEG_ZERO, F16.NEG_ZERO), Pair(F16.ONE, F16.ONE), Pair(F16.NEG_ONE, -0.5), Pair(16.0, F16.INFINITY), Pair(-25.0, F16.NEG_ONE), Pair(F16.INFINITY, F16.INFINITY), Pair(F16.NEG_INFINITY, F16.NEG_ONE))) {
            val result = exp2m1f16(x)
            run {
                val l = result
                val r = expected
                check(l.biteq(r)) { ("x = $x").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
        for (x1 in listOf(F16.NAN, F16.NEG_NAN, F16.SNAN, F16.NEG_SNAN)) {
            // NaN operations can change the sign or payload, including canonicalization on RISC-V.
            check(exp2m1f16(x1).isNaN()) { "x = $x" }
        }
    }
}
