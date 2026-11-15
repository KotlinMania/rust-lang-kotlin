// port-lint: tests math/exp2m1.rs
package io.github.kotlinmania.rustlang.libm.math.exp2m1

import io.github.kotlinmania.rustlang.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.math.support.hexfloat.Hex
import io.github.kotlinmania.rustlang.math.support.hexfloat.hf64

public class Exp2m1InlineTests {
    @kotlin.test.Test
    public fun knownResults() {
        for ((x, expected) in listOf(Pair(Double.ZERO, Double.ZERO), Pair(Double.NEG_ZERO, Double.NEG_ZERO), Pair(Double.ONE, Double.ONE), Pair(Double.NEG_ONE, -0.5), Pair(Double.fromBits(1uL.toLong()), Double.fromBits(1uL.toLong())), Pair(-Double.fromBits(1uL.toLong()), -Double.fromBits(1uL.toLong())), Pair(Double.fromBits(2uL.toLong()), Double.fromBits(1uL.toLong())), Pair(-Double.fromBits(2uL.toLong()), -Double.fromBits(1uL.toLong())), Pair(54.0, run {
            val X: Double = hf64("0x1p+54")
            X
        }), Pair(-54.0, Double.NEG_ONE), Pair(1023.0, run {
            val X: Double = hf64("0x1p+1023")
            X
        }), Pair(1024.0, Double.POSITIVE_INFINITY), Pair(Double.MAX_VALUE, Double.POSITIVE_INFINITY), Pair((-Double.MAX_VALUE), Double.NEG_ONE), Pair(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), Pair(Double.NEGATIVE_INFINITY, Double.NEG_ONE))) {
            if (!false) {
                val result = exp2m1(x)
                run {
                    val l = result
                    val r = expected
                    check(biteq(l, r)) { ("x = $x").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toULong()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
                }
            }
        }
        for (x1 in doubleArrayOf(Double.NaN, Double.NEG_NAN, Double.SNAN, Double.NEG_SNAN)) {
            // NaN operations can change the sign or payload, including canonicalization on RISC-V.
            check(exp2m1(x1).isNaN()) { "x = $x" }
        }
    }

    @kotlin.test.Test
    public fun exactIntegerResults() {
        for (i in 1..53) {
            val x = Double.castFrom(i)
            val positive = ((1uL shl i.toInt()) - 1uL).cast()
            val negative = Double.fromBits(((1023uL - i) shl 52).toLong()) - 1.0
            run {
                val l = exp2m1(x)
                val r = positive
                check(biteq(l, r)) { ("x = $x").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toULong()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
            }
            run {
                val l1 = exp2m1(-x)
                val r1 = negative
                check(biteq(l1, r1)) { ("x = ${-x}").toString() + "\nl: " + (l).toString() + " (" + (l1.toRawBits().toULong()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toRawBits().toULong()).toString() + " " + (Hex(r1)).toString() + ")" }
            }
        }
    }
}
