// port-lint: tests math/trunc.rs
package io.github.kotlinmania.rustlang.libm.math.trunc

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.generic.trunc.truncStatus
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf128
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf16
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf32
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> check(f: (F) -> F, cases: List<Triple<F, F, Status>>) {
    for ((x, expRes, expStat) in cases) {
        val structVal = truncStatus(x)
        val value = structVal.value
        val status = structVal.status
        run {
            val l = value
            val r = expRes
            check(biteq(l, r)) { ("generic::trunc_status($x) (${Hex(x)})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        check(status == expStat)
        val value1 = f(x)
        run {
            val l1 = value1
            val r1 = expRes
            check(biteq(l1, r1)) { ("trunc($x) (${Hex(x)})").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
    }
}

public class TruncInlineTests {
    @kotlin.test.Test
    public fun checkF16() {
        check<F16>(::truncf16, listOf(Triple(0.0, 0.0, Status.OK), Triple(-0.0, -0.0, Status.OK), Triple(1.0, 1.0, Status.OK), Triple(-1.0, -1.0, Status.OK), Triple(F16.INFINITY, F16.INFINITY, Status.OK), Triple(F16.NEG_INFINITY, F16.NEG_INFINITY, Status.OK), Triple(0.1, 0.0, Status.INEXACT), Triple(-0.1, -0.0, Status.INEXACT), Triple(0.5, 0.0, Status.INEXACT), Triple(-0.5, -0.0, Status.INEXACT), Triple(0.9, 0.0, Status.INEXACT), Triple(-0.9, -0.0, Status.INEXACT), Triple(1.1, 1.0, Status.INEXACT), Triple(-1.1, -1.0, Status.INEXACT), Triple(1.5, 1.0, Status.INEXACT), Triple(-1.5, -1.0, Status.INEXACT), Triple(1.9, 1.0, Status.INEXACT), Triple(-1.9, -1.0, Status.INEXACT)))
        check<F16>(::truncf16, listOf(Triple(run {
            val X: F16 = hf16("0x1p10")
            X
        }, run {
            val X: F16 = hf16("0x1p10")
            X
        }, Status.OK), Triple(run {
            val X: F16 = hf16("-0x1p10")
            X
        }, run {
            val X: F16 = hf16("-0x1p10")
            X
        }, Status.OK)))
    }

    @kotlin.test.Test
    public fun checkF32() {
        check<kotlin.Float>(::truncf, listOf(Triple(0.0, 0.0, Status.OK), Triple(-0.0, -0.0, Status.OK), Triple(1.0, 1.0, Status.OK), Triple(-1.0, -1.0, Status.OK), Triple(kotlin.Float.POSITIVE_INFINITY, kotlin.Float.POSITIVE_INFINITY, Status.OK), Triple(kotlin.Float.NEGATIVE_INFINITY, kotlin.Float.NEGATIVE_INFINITY, Status.OK), Triple(0.1, 0.0, Status.INEXACT), Triple(-0.1, -0.0, Status.INEXACT), Triple(0.5, 0.0, Status.INEXACT), Triple(-0.5, -0.0, Status.INEXACT), Triple(0.9, 0.0, Status.INEXACT), Triple(-0.9, -0.0, Status.INEXACT), Triple(1.1, 1.0, Status.INEXACT), Triple(-1.1, -1.0, Status.INEXACT), Triple(1.5, 1.0, Status.INEXACT), Triple(-1.5, -1.0, Status.INEXACT), Triple(1.9, 1.0, Status.INEXACT), Triple(-1.9, -1.0, Status.INEXACT)))
        check<kotlin.Float>(::truncf, listOf(Triple(run {
            val X: kotlin.Float = hf32("0x1p23")
            X
        }, run {
            val X: kotlin.Float = hf32("0x1p23")
            X
        }, Status.OK), Triple(run {
            val X: kotlin.Float = hf32("-0x1p23")
            X
        }, run {
            val X: kotlin.Float = hf32("-0x1p23")
            X
        }, Status.OK)))
    }

    @kotlin.test.Test
    public fun checkF64() {
        check<Double>(::trunc, listOf(Triple(0.0, 0.0, Status.OK), Triple(-0.0, -0.0, Status.OK), Triple(1.0, 1.0, Status.OK), Triple(-1.0, -1.0, Status.OK), Triple(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Status.OK), Triple(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Status.OK), Triple(0.1, 0.0, Status.INEXACT), Triple(-0.1, -0.0, Status.INEXACT), Triple(0.5, 0.0, Status.INEXACT), Triple(-0.5, -0.0, Status.INEXACT), Triple(0.9, 0.0, Status.INEXACT), Triple(-0.9, -0.0, Status.INEXACT), Triple(1.1, 1.0, Status.INEXACT), Triple(-1.1, -1.0, Status.INEXACT), Triple(1.5, 1.0, Status.INEXACT), Triple(-1.5, -1.0, Status.INEXACT), Triple(1.9, 1.0, Status.INEXACT), Triple(-1.9, -1.0, Status.INEXACT)))
        check<Double>(::trunc, listOf(Triple(run {
            val X: Double = hf64("0x1p52")
            X
        }, run {
            val X: Double = hf64("0x1p52")
            X
        }, Status.OK), Triple(run {
            val X: Double = hf64("-0x1p52")
            X
        }, run {
            val X: Double = hf64("-0x1p52")
            X
        }, Status.OK)))
    }

    @kotlin.test.Test
    public fun checkF128() {
        check<F128>(::truncf128, listOf(Triple(0.0, 0.0, Status.OK), Triple(-0.0, -0.0, Status.OK), Triple(1.0, 1.0, Status.OK), Triple(-1.0, -1.0, Status.OK), Triple(F128.INFINITY, F128.INFINITY, Status.OK), Triple(F128.NEG_INFINITY, F128.NEG_INFINITY, Status.OK), Triple(0.1, 0.0, Status.INEXACT), Triple(-0.1, -0.0, Status.INEXACT), Triple(0.5, 0.0, Status.INEXACT), Triple(-0.5, -0.0, Status.INEXACT), Triple(0.9, 0.0, Status.INEXACT), Triple(-0.9, -0.0, Status.INEXACT), Triple(1.1, 1.0, Status.INEXACT), Triple(-1.1, -1.0, Status.INEXACT), Triple(1.5, 1.0, Status.INEXACT), Triple(-1.5, -1.0, Status.INEXACT), Triple(1.9, 1.0, Status.INEXACT), Triple(-1.9, -1.0, Status.INEXACT)))
        check<F128>(::truncf128, listOf(Triple(run {
            val X: F128 = hf128("0x1p112")
            X
        }, run {
            val X: F128 = hf128("0x1p112")
            X
        }, Status.OK), Triple(run {
            val X: F128 = hf128("-0x1p112")
            X
        }, run {
            val X: F128 = hf128("-0x1p112")
            X
        }, Status.OK)))
    }
}
