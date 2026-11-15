// port-lint: tests math/exp2m1f.rs
package io.github.kotlinmania.rustlang.libm.math.exp2m1f

import io.github.kotlinmania.rustlang.libm.math.support.Float
import io.github.kotlinmania.rustlang.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.math.support.hexfloat.Hex

public class Exp2m1fInlineTests {
    @kotlin.test.Test
    public fun knownResults() {
        for ((x, expected) in listOf(Pair(kotlin.Float.ZERO, kotlin.Float.ZERO), Pair(kotlin.Float.NEG_ZERO, kotlin.Float.NEG_ZERO), Pair(kotlin.Float.ONE, kotlin.Float.ONE), Pair(kotlin.Float.NEG_ONE, -0.5), Pair(128.0, kotlin.Float.POSITIVE_INFINITY), Pair(-25.0, kotlin.Float.NEG_ONE), Pair(kotlin.Float.POSITIVE_INFINITY, kotlin.Float.POSITIVE_INFINITY), Pair(kotlin.Float.NEGATIVE_INFINITY, kotlin.Float.NEG_ONE))) {
            val result = exp2m1f(x)
            run {
                val l = result
                val r = expected
                check(biteq(l, r)) { ("x = $x").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toUInt()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
        for (x1 in floatArrayOf(kotlin.Float.NaN, kotlin.Float.NEG_NAN, kotlin.Float.SNAN, kotlin.Float.NEG_SNAN)) {
            // NaN operations can change the sign or payload, including canonicalization on RISC-V.
            check(exp2m1f(x1).isNaN()) { "x = $x" }
        }
    }
}
