// port-lint: tests math/round.rs
package io.github.kotlinmania.rustlang.libm.math.round

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf128
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf16
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf32
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> check(f: (F) -> F, cases: List<Pair<F, F>>) {
    for ((x, expRes) in cases) {
        val value = io.github.kotlinmania.rustlang.libm.math.generic.round.round(x)
        run {
            val l = value
            val r = expRes
            check(biteq(l, r)) { ("generic::round_status($x) ${Hex(x)}").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        val value1 = f(x)
        run {
            val l1 = value1
            val r1 = expRes
            check(biteq(l1, r1)) { ("round($x) ${Hex(x)}").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
    }
}

public class RoundInlineTests {
    @kotlin.test.Test
    public fun checkF16() {
        check<F16>(::roundf16, listOf(Pair(0.0, 0.0), Pair(-0.0, -0.0), Pair(1.0, 1.0), Pair(-1.0, -1.0), Pair(F16.INFINITY, F16.INFINITY), Pair(F16.NEG_INFINITY, F16.NEG_INFINITY), Pair(0.1, 0.0), Pair(-0.1, -0.0), Pair(0.5, 1.0), Pair(-0.5, -1.0), Pair(0.9, 1.0), Pair(-0.9, -1.0), Pair(1.1, 1.0), Pair(-1.1, -1.0), Pair(1.5, 2.0), Pair(-1.5, -2.0), Pair(1.9, 2.0), Pair(-1.9, -2.0)))
        check<F16>(::roundf16, listOf(Pair(run {
            val X: F16 = hf16("0x1p10")
            X
        }, run {
            val X: F16 = hf16("0x1p10")
            X
        }), Pair(run {
            val X: F16 = hf16("-0x1p10")
            X
        }, run {
            val X: F16 = hf16("-0x1p10")
            X
        })))
    }

    @kotlin.test.Test
    public fun checkF32() {
        check<kotlin.Float>(::roundf, listOf(Pair(0.0, 0.0), Pair(-0.0, -0.0), Pair(1.0, 1.0), Pair(-1.0, -1.0), Pair(kotlin.Float.POSITIVE_INFINITY, kotlin.Float.POSITIVE_INFINITY), Pair(kotlin.Float.NEGATIVE_INFINITY, kotlin.Float.NEGATIVE_INFINITY), Pair(0.1, 0.0), Pair(-0.1, -0.0), Pair(0.5, 1.0), Pair(-0.5, -1.0), Pair(0.9, 1.0), Pair(-0.9, -1.0), Pair(1.1, 1.0), Pair(-1.1, -1.0), Pair(1.5, 2.0), Pair(-1.5, -2.0), Pair(1.9, 2.0), Pair(-1.9, -2.0)))
        check<kotlin.Float>(::roundf, listOf(Pair(run {
            val X: kotlin.Float = hf32("0x1p23")
            X
        }, run {
            val X: kotlin.Float = hf32("0x1p23")
            X
        }), Pair(run {
            val X: kotlin.Float = hf32("-0x1p23")
            X
        }, run {
            val X: kotlin.Float = hf32("-0x1p23")
            X
        })))
    }

    @kotlin.test.Test
    public fun checkF64() {
        check<Double>(::round, listOf(Pair(0.0, 0.0), Pair(-0.0, -0.0), Pair(1.0, 1.0), Pair(-1.0, -1.0), Pair(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), Pair(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), Pair(0.1, 0.0), Pair(-0.1, -0.0), Pair(0.5, 1.0), Pair(-0.5, -1.0), Pair(0.9, 1.0), Pair(-0.9, -1.0), Pair(1.1, 1.0), Pair(-1.1, -1.0), Pair(1.5, 2.0), Pair(-1.5, -2.0), Pair(1.9, 2.0), Pair(-1.9, -2.0)))
        check<Double>(::round, listOf(Pair(run {
            val X: Double = hf64("0x1p52")
            X
        }, run {
            val X: Double = hf64("0x1p52")
            X
        }), Pair(run {
            val X: Double = hf64("-0x1p52")
            X
        }, run {
            val X: Double = hf64("-0x1p52")
            X
        })))
    }

    @kotlin.test.Test
    public fun checkF128() {
        check<F128>(::roundf128, listOf(Pair(0.0, 0.0), Pair(-0.0, -0.0), Pair(1.0, 1.0), Pair(-1.0, -1.0), Pair(F128.INFINITY, F128.INFINITY), Pair(F128.NEG_INFINITY, F128.NEG_INFINITY), Pair(0.1, 0.0), Pair(-0.1, -0.0), Pair(0.5, 1.0), Pair(-0.5, -1.0), Pair(0.9, 1.0), Pair(-0.9, -1.0), Pair(1.1, 1.0), Pair(-1.1, -1.0), Pair(1.5, 2.0), Pair(-1.5, -2.0), Pair(1.9, 2.0), Pair(-1.9, -2.0)))
        check<F128>(::roundf128, listOf(Pair(run {
            val X: F128 = hf128("0x1p112")
            X
        }, run {
            val X: F128 = hf128("0x1p112")
            X
        }), Pair(run {
            val X: F128 = hf128("-0x1p112")
            X
        }, run {
            val X: F128 = hf128("-0x1p112")
            X
        })))
    }
}
