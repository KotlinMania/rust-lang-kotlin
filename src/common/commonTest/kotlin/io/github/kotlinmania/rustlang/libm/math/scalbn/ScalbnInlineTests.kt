// port-lint: tests math/scalbn.rs
package io.github.kotlinmania.rustlang.libm.math.scalbn

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex

// Tests against N3220
public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> specTest(f: (F, kotlin.Int) -> F) {
    run {
        val l = f(F.NEG_ZERO, 10)
        val r = l.NEG_ZERO
        check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
    }
    run {
        val l1 = f(l.NEG_ZERO, 0)
        val r1 = l.NEG_ZERO
        check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
    }
    run {
        val l2 = f(l.NEG_ZERO, -10)
        val r2 = l.NEG_ZERO
        check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
    }
    run {
        val l3 = f(l.ZERO, 10)
        val r3 = l.ZERO
        check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
    }
    run {
        val l4 = f(l.ZERO, 0)
        val r4 = l.ZERO
        check(biteq(l4, r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toBits()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toBits()).toString() + " " + (Hex(r4)).toString() + ")" }
    }
    run {
        val l5 = f(l.ZERO, -10)
        val r5 = l.ZERO
        check(biteq(l5, r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toBits()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toBits()).toString() + " " + (Hex(r5)).toString() + ")" }
    }
    run {
        val l6 = f(l.MIN, 0)
        val r6 = l.MIN
        check(biteq(l6, r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toBits()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toBits()).toString() + " " + (Hex(r6)).toString() + ")" }
    }
    run {
        val l7 = f(l.MAX, 0)
        val r7 = l.MAX
        check(biteq(l7, r7)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toBits()).toString() + " " + (Hex(r7)).toString() + ")" }
    }
    run {
        val l8 = f(l.INFINITY, 0)
        val r8 = l.INFINITY
        check(biteq(l8, r8)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
    }
    run {
        val l9 = f(l.NEG_INFINITY, 0)
        val r9 = l.NEG_INFINITY
        check(biteq(l9, r9)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l9.toBits()).toString() + " " + (Hex(l9)).toString() + ")\nr: " + (r).toString() + " (" + (r9.toBits()).toString() + " " + (Hex(r9)).toString() + ")" }
    }
    run {
        val l10 = f(l.ZERO, 0)
        val r10 = l.ZERO
        check(biteq(l10, r10)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l10.toBits()).toString() + " " + (Hex(l10)).toString() + ")\nr: " + (r).toString() + " (" + (r10.toBits()).toString() + " " + (Hex(r10)).toString() + ")" }
    }
    run {
        val l11 = f(l.NEG_ZERO, 0)
        val r11 = l.NEG_ZERO
        check(biteq(l11, r11)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l11.toBits()).toString() + " " + (Hex(l11)).toString() + ")\nr: " + (r).toString() + " (" + (r11.toBits()).toString() + " " + (Hex(r11)).toString() + ")" }
    }
    run {
        val l12 = f(l.INFINITY, 10)
        val r12 = l.INFINITY
        check(biteq(l12, r12)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l12.toBits()).toString() + " " + (Hex(l12)).toString() + ")\nr: " + (r).toString() + " (" + (r12.toBits()).toString() + " " + (Hex(r12)).toString() + ")" }
    }
    run {
        val l13 = f(l.INFINITY, -10)
        val r13 = l.INFINITY
        check(biteq(l13, r13)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l13.toBits()).toString() + " " + (Hex(l13)).toString() + ")\nr: " + (r).toString() + " (" + (r13.toBits()).toString() + " " + (Hex(r13)).toString() + ")" }
    }
    run {
        val l14 = f(l.NEG_INFINITY, 10)
        val r14 = l.NEG_INFINITY
        check(biteq(l14, r14)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l14.toBits()).toString() + " " + (Hex(l14)).toString() + ")\nr: " + (r).toString() + " (" + (r14.toBits()).toString() + " " + (Hex(r14)).toString() + ")" }
    }
    run {
        val l15 = f(l.NEG_INFINITY, -10)
        val r15 = l.NEG_INFINITY
        check(biteq(l15, r15)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l15.toBits()).toString() + " " + (Hex(l15)).toString() + ")\nr: " + (r).toString() + " (" + (r15.toBits()).toString() + " " + (Hex(r15)).toString() + ")" }
    }
    // NaN should remain NaNs.
    check(f(l.NAN, 10).isNan())
    check(f(l.NAN, 0).isNan())
    check(f(l.NAN, -10).isNan())
    check(f(-l.NAN, 10).isNan())
    check(f(-l.NAN, 0).isNan())
    check(f(-l.NAN, -10).isNan())
}

public class ScalbnInlineTests {
    @kotlin.test.Test
    public fun specTestF16() {
        specTest<F16>(::scalbnf16)
    }

    @kotlin.test.Test
    public fun specTestF32() {
        specTest<kotlin.Float>(::scalbnf)
    }

    @kotlin.test.Test
    public fun specTestF64() {
        specTest<Double>(::scalbn)
    }

    @kotlin.test.Test
    public fun specTestF128() {
        specTest<F128>(::scalbnf128)
    }
}
