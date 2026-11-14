// port-lint: tests math/fma.rs
package io.github.kotlinmania.rustlang.libm.math.fma

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.fmaf16.fmaf16
import io.github.kotlinmania.rustlang.libm.math.generic.fma.fmaRound
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> check(f: (F, F, F) -> F, cases: List<List<Any?>>) {
    for ((x, y, z, expRes) in cases) {
        val value = f(x, y, z)
        run {
            val l = value
            val r = expRes
            check(l.biteq(r)) { ("fma($x, $y, $z) (${Hex(x)} ${Hex(y)} ${Hex(z)})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
    }
}

public class FmaInlineTests {
    @kotlin.test.Test
    public fun checkF16() {
        check<F16>(fmaf16, listOf(listOf(0.0, 0.0, 0.0, 0.0), listOf(1.0, 1.0, 0.0, 1.0), listOf(1.0, 0.0, 1.0, 1.0), listOf(1.0, 1.0, 1.0, 2.0), listOf(1.0, 1.0, -1.0, 0.0), listOf(1.0, -1.0, 1.0, 0.0), listOf(1.0, -1.0, -1.0, -2.0), listOf(-1.0, 1.0, 1.0, 0.0), listOf(-1.0, 1.0, -1.0, -2.0), listOf(-1.0, -1.0, 1.0, 2.0), listOf(-1.0, -1.0, -1.0, 0.0), listOf(F16.MAX, 1.0, 0.0, F16.MAX), listOf(F16.MAX, F16.MAX, 1.0, F16.INFINITY), listOf(F16.MAX, 1.0, -F16.MAX, 0.0), listOf(-F16.MAX, 1.0, F16.MAX, 0.0), listOf(F16.MIN_POSITIVE_NORMAL, 1.0, -F16.MIN_POSITIVE_NORMAL, 0.0), listOf(-F16.MIN_POSITIVE_NORMAL, 1.0, F16.MIN_POSITIVE_NORMAL, 0.0), listOf(F16.MIN_POSITIVE_SUBNORMAL, 1.0, -F16.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(-F16.MIN_POSITIVE_SUBNORMAL, 1.0, F16.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(F16.MAX, 1.0, -F16.MAX, 0.0), listOf(F16.MIN_POSITIVE_SUBNORMAL, F16.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0), listOf(F16.MIN_POSITIVE_SUBNORMAL, -F16.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-F16.MIN_POSITIVE_SUBNORMAL, F16.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-F16.MIN_POSITIVE_SUBNORMAL, -F16.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0)))
    }

    @kotlin.test.Test
    public fun checkF32() {
        check<kotlin.Float>(::fmaf, listOf(listOf(0.0, 0.0, 0.0, 0.0), listOf(1.0, 1.0, 0.0, 1.0), listOf(1.0, 0.0, 1.0, 1.0), listOf(1.0, 1.0, 1.0, 2.0), listOf(1.0, 1.0, -1.0, 0.0), listOf(1.0, -1.0, 1.0, 0.0), listOf(1.0, -1.0, -1.0, -2.0), listOf(-1.0, 1.0, 1.0, 0.0), listOf(-1.0, 1.0, -1.0, -2.0), listOf(-1.0, -1.0, 1.0, 2.0), listOf(-1.0, -1.0, -1.0, 0.0), listOf(kotlin.Float.MAX_VALUE, 1.0, 0.0, kotlin.Float.MAX_VALUE), listOf(kotlin.Float.MAX_VALUE, kotlin.Float.MAX_VALUE, 1.0, kotlin.Float.POSITIVE_INFINITY), listOf(kotlin.Float.MAX_VALUE, 1.0, -kotlin.Float.MAX_VALUE, 0.0), listOf(-kotlin.Float.MAX_VALUE, 1.0, kotlin.Float.MAX_VALUE, 0.0), listOf(kotlin.Float.MIN_POSITIVE_NORMAL, 1.0, -kotlin.Float.MIN_POSITIVE_NORMAL, 0.0), listOf(-kotlin.Float.MIN_POSITIVE_NORMAL, 1.0, kotlin.Float.MIN_POSITIVE_NORMAL, 0.0), listOf(kotlin.Float.MIN_POSITIVE_SUBNORMAL, 1.0, -kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(-kotlin.Float.MIN_POSITIVE_SUBNORMAL, 1.0, kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(kotlin.Float.MAX_VALUE, 1.0, -kotlin.Float.MAX_VALUE, 0.0), listOf(kotlin.Float.MIN_POSITIVE_SUBNORMAL, kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0), listOf(kotlin.Float.MIN_POSITIVE_SUBNORMAL, -kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-kotlin.Float.MIN_POSITIVE_SUBNORMAL, kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-kotlin.Float.MIN_POSITIVE_SUBNORMAL, -kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0)))
        // Also do a small check that the non-widening version works for f32 (this should ideally
        // get tested some more).
        check<kotlin.Float>({ x, y, z -> fmaRound(x, y, z, Round.Nearest).value }, listOf(listOf(0.0, 0.0, 0.0, 0.0), listOf(1.0, 1.0, 0.0, 1.0), listOf(1.0, 0.0, 1.0, 1.0), listOf(1.0, 1.0, 1.0, 2.0), listOf(1.0, 1.0, -1.0, 0.0), listOf(1.0, -1.0, 1.0, 0.0), listOf(1.0, -1.0, -1.0, -2.0), listOf(-1.0, 1.0, 1.0, 0.0), listOf(-1.0, 1.0, -1.0, -2.0), listOf(-1.0, -1.0, 1.0, 2.0), listOf(-1.0, -1.0, -1.0, 0.0), listOf(kotlin.Float.MAX_VALUE, 1.0, 0.0, kotlin.Float.MAX_VALUE), listOf(kotlin.Float.MAX_VALUE, kotlin.Float.MAX_VALUE, 1.0, kotlin.Float.POSITIVE_INFINITY), listOf(kotlin.Float.MAX_VALUE, 1.0, -kotlin.Float.MAX_VALUE, 0.0), listOf(-kotlin.Float.MAX_VALUE, 1.0, kotlin.Float.MAX_VALUE, 0.0), listOf(kotlin.Float.MIN_POSITIVE_NORMAL, 1.0, -kotlin.Float.MIN_POSITIVE_NORMAL, 0.0), listOf(-kotlin.Float.MIN_POSITIVE_NORMAL, 1.0, kotlin.Float.MIN_POSITIVE_NORMAL, 0.0), listOf(kotlin.Float.MIN_POSITIVE_SUBNORMAL, 1.0, -kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(-kotlin.Float.MIN_POSITIVE_SUBNORMAL, 1.0, kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(kotlin.Float.MAX_VALUE, 1.0, -kotlin.Float.MAX_VALUE, 0.0), listOf(kotlin.Float.MIN_POSITIVE_SUBNORMAL, kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0), listOf(kotlin.Float.MIN_POSITIVE_SUBNORMAL, -kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-kotlin.Float.MIN_POSITIVE_SUBNORMAL, kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-kotlin.Float.MIN_POSITIVE_SUBNORMAL, -kotlin.Float.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0)))
    }

    @kotlin.test.Test
    public fun checkF64() {
        check<Double>(::fma, listOf(listOf(0.0, 0.0, 0.0, 0.0), listOf(1.0, 1.0, 0.0, 1.0), listOf(1.0, 0.0, 1.0, 1.0), listOf(1.0, 1.0, 1.0, 2.0), listOf(1.0, 1.0, -1.0, 0.0), listOf(1.0, -1.0, 1.0, 0.0), listOf(1.0, -1.0, -1.0, -2.0), listOf(-1.0, 1.0, 1.0, 0.0), listOf(-1.0, 1.0, -1.0, -2.0), listOf(-1.0, -1.0, 1.0, 2.0), listOf(-1.0, -1.0, -1.0, 0.0), listOf(Double.MAX_VALUE, 1.0, 0.0, Double.MAX_VALUE), listOf(Double.MAX_VALUE, Double.MAX_VALUE, 1.0, Double.POSITIVE_INFINITY), listOf(Double.MAX_VALUE, 1.0, -Double.MAX_VALUE, 0.0), listOf(-Double.MAX_VALUE, 1.0, Double.MAX_VALUE, 0.0), listOf(Double.MIN_POSITIVE_NORMAL, 1.0, -Double.MIN_POSITIVE_NORMAL, 0.0), listOf(-Double.MIN_POSITIVE_NORMAL, 1.0, Double.MIN_POSITIVE_NORMAL, 0.0), listOf(Double.MIN_POSITIVE_SUBNORMAL, 1.0, -Double.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(-Double.MIN_POSITIVE_SUBNORMAL, 1.0, Double.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(Double.MAX_VALUE, 1.0, -Double.MAX_VALUE, 0.0), listOf(Double.MIN_POSITIVE_SUBNORMAL, Double.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0), listOf(Double.MIN_POSITIVE_SUBNORMAL, -Double.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-Double.MIN_POSITIVE_SUBNORMAL, Double.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-Double.MIN_POSITIVE_SUBNORMAL, -Double.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0)))
        val expectUnderflow =
            listOf(listOf(run {
                val X: Double = hf64("0x1.0p-1070")
                X
            }, run {
                val X: Double = hf64("0x1.0p-1070")
                X
            }, run {
                val X: Double = hf64("0x1.ffffffffffffp-1023")
                X
            }, run {
                val X: Double = hf64("0x0.ffffffffffff8p-1022")
                X
            }), listOf(run {
                val X: Double = hf64("0x1.0p-1070")
                X
            }, run {
                val X: Double = hf64("0x1.0p-1070")
                X
            }, run {
                val X: Double = hf64("-0x1.0p-1022")
                X
            }, run {
                val X: Double = hf64("-0x1.0p-1022")
                X
            }))
        for ((x, y, z, res) in expectUnderflow) {
            val structVal = fmaRound(x, y, z, Round.Nearest)
            val value = structVal.value
            val status = structVal.status
            run {
                val l = value
                val r = res
                check(l.biteq(r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
            }
            check(status == Status.UNDERFLOW)
        }
    }

    @kotlin.test.Test
    public fun checkF128() {
        check<F128>(::fmaf128, listOf(listOf(0.0, 0.0, 0.0, 0.0), listOf(1.0, 1.0, 0.0, 1.0), listOf(1.0, 0.0, 1.0, 1.0), listOf(1.0, 1.0, 1.0, 2.0), listOf(1.0, 1.0, -1.0, 0.0), listOf(1.0, -1.0, 1.0, 0.0), listOf(1.0, -1.0, -1.0, -2.0), listOf(-1.0, 1.0, 1.0, 0.0), listOf(-1.0, 1.0, -1.0, -2.0), listOf(-1.0, -1.0, 1.0, 2.0), listOf(-1.0, -1.0, -1.0, 0.0), listOf(F128.MAX, 1.0, 0.0, F128.MAX), listOf(F128.MAX, F128.MAX, 1.0, F128.INFINITY), listOf(F128.MAX, 1.0, -F128.MAX, 0.0), listOf(-F128.MAX, 1.0, F128.MAX, 0.0), listOf(F128.MIN_POSITIVE_NORMAL, 1.0, -F128.MIN_POSITIVE_NORMAL, 0.0), listOf(-F128.MIN_POSITIVE_NORMAL, 1.0, F128.MIN_POSITIVE_NORMAL, 0.0), listOf(F128.MIN_POSITIVE_SUBNORMAL, 1.0, -F128.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(-F128.MIN_POSITIVE_SUBNORMAL, 1.0, F128.MIN_POSITIVE_SUBNORMAL, 0.0), listOf(F128.MAX, 1.0, -F128.MAX, 0.0), listOf(F128.MIN_POSITIVE_SUBNORMAL, F128.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0), listOf(F128.MIN_POSITIVE_SUBNORMAL, -F128.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-F128.MIN_POSITIVE_SUBNORMAL, F128.MIN_POSITIVE_SUBNORMAL, 0.0, -0.0), listOf(-F128.MIN_POSITIVE_SUBNORMAL, -F128.MIN_POSITIVE_SUBNORMAL, 0.0, 0.0)))
    }

    @kotlin.test.Test
    public fun issue263() {
        val a = kotlin.Float.fromBits(1266679807u.toInt())
        val b = kotlin.Float.fromBits(1300234242u.toInt())
        val c = kotlin.Float.fromBits(1115553792u.toInt())
        val expected = kotlin.Float.fromBits(1501560833u.toInt())
        check(fmaf(a, b, c) == expected)
    }

    @kotlin.test.Test
    public fun fmaSegfault() {
        // These two inputs cause fma to segfault on release due to overflow:
        check(fma(-0.0000000000000002220446049250313, -0.0000000000000002220446049250313, -0.0000000000000002220446049250313) == (-0.00000000000000022204460492503126).toDouble())
        val result = fma(-0.992, -0.992, -0.992)
        // force rounding to storage format on x87 to prevent superious errors.
        val result1 = readVolatile(result)
        check(result1 == -0.007936000000000007)
    }

    @kotlin.test.Test
    public fun fmaSbb() {
        check(fma(-(1.0 - 2.220446049250313E-16), (-Double.MAX_VALUE), (-Double.MAX_VALUE)) == (-3991680619069439e277).toDouble())
    }

    @kotlin.test.Test
    public fun fmaUnderflow() {
        check(fma(1.1102230246251565e-16, -9.812526705433188e-305, 1.0894e-320) == 0.0.toDouble())
    }
}
