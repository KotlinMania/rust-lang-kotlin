// port-lint: tests math/copysign.rs
package io.github.kotlinmania.rustlang.libm.math.copysign

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> specTest(f: (F, F) -> F) {
    run {
        val l = f(F.ZERO, F.ZERO)
        val r = l.ZERO
        check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
    }
    run {
        val l1 = f(l.NEG_ZERO, l.ZERO)
        val r1 = l.ZERO
        check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
    }
    run {
        val l2 = f(l.ZERO, l.NEG_ZERO)
        val r2 = l.NEG_ZERO
        check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
    }
    run {
        val l3 = f(l.NEG_ZERO, l.NEG_ZERO)
        val r3 = l.NEG_ZERO
        check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
    }
    run {
        val l4 = f(l.ONE, l.ONE)
        val r4 = l.ONE
        check(biteq(l4, r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toBits()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toBits()).toString() + " " + (Hex(r4)).toString() + ")" }
    }
    run {
        val l5 = f(l.NEG_ONE, l.ONE)
        val r5 = l.ONE
        check(biteq(l5, r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toBits()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toBits()).toString() + " " + (Hex(r5)).toString() + ")" }
    }
    run {
        val l6 = f(l.ONE, l.NEG_ONE)
        val r6 = l.NEG_ONE
        check(biteq(l6, r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toBits()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toBits()).toString() + " " + (Hex(r6)).toString() + ")" }
    }
    run {
        val l7 = f(l.NEG_ONE, l.NEG_ONE)
        val r7 = l.NEG_ONE
        check(biteq(l7, r7)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toBits()).toString() + " " + (Hex(r7)).toString() + ")" }
    }
    run {
        val l8 = f(l.INFINITY, l.INFINITY)
        val r8 = l.INFINITY
        check(biteq(l8, r8)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
    }
    run {
        val l9 = f(l.NEG_INFINITY, l.INFINITY)
        val r9 = l.INFINITY
        check(biteq(l9, r9)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l9.toBits()).toString() + " " + (Hex(l9)).toString() + ")\nr: " + (r).toString() + " (" + (r9.toBits()).toString() + " " + (Hex(r9)).toString() + ")" }
    }
    run {
        val l10 = f(l.INFINITY, l.NEG_INFINITY)
        val r10 = l.NEG_INFINITY
        check(biteq(l10, r10)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l10.toBits()).toString() + " " + (Hex(l10)).toString() + ")\nr: " + (r).toString() + " (" + (r10.toBits()).toString() + " " + (Hex(r10)).toString() + ")" }
    }
    run {
        val l11 = f(l.NEG_INFINITY, l.NEG_INFINITY)
        val r11 = l.NEG_INFINITY
        check(biteq(l11, r11)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l11.toBits()).toString() + " " + (Hex(l11)).toString() + ")\nr: " + (r).toString() + " (" + (r11.toBits()).toString() + " " + (Hex(r11)).toString() + ")" }
    }
    run {
        val l12 = f(l.NAN, l.NAN)
        val r12 = l.NAN
        check(biteq(l12, r12)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l12.toBits()).toString() + " " + (Hex(l12)).toString() + ")\nr: " + (r).toString() + " (" + (r12.toBits()).toString() + " " + (Hex(r12)).toString() + ")" }
    }
    run {
        val l13 = f(l.NAN, l.ONE)
        val r13 = l.NAN
        check(biteq(l13, r13)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l13.toBits()).toString() + " " + (Hex(l13)).toString() + ")\nr: " + (r).toString() + " (" + (r13.toBits()).toString() + " " + (Hex(r13)).toString() + ")" }
    }
    run {
        val l14 = f(l.NAN, l.NEG_ONE)
        val r14 = l.NEG_NAN
        check(biteq(l14, r14)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l14.toBits()).toString() + " " + (Hex(l14)).toString() + ")\nr: " + (r).toString() + " (" + (r14.toBits()).toString() + " " + (Hex(r14)).toString() + ")" }
    }
    run {
        val l15 = f(l.NAN, l.NEG_NAN)
        val r15 = l.NEG_NAN
        check(biteq(l15, r15)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l15.toBits()).toString() + " " + (Hex(l15)).toString() + ")\nr: " + (r).toString() + " (" + (r15.toBits()).toString() + " " + (Hex(r15)).toString() + ")" }
    }
    run {
        val l16 = f(l.NEG_NAN, l.NAN)
        val r16 = l.NAN
        check(biteq(l16, r16)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l16.toBits()).toString() + " " + (Hex(l16)).toString() + ")\nr: " + (r).toString() + " (" + (r16.toBits()).toString() + " " + (Hex(r16)).toString() + ")" }
    }
    run {
        val l17 = f(l.NEG_NAN, l.ONE)
        val r17 = l.NAN
        check(biteq(l17, r17)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l17.toBits()).toString() + " " + (Hex(l17)).toString() + ")\nr: " + (r).toString() + " (" + (r17.toBits()).toString() + " " + (Hex(r17)).toString() + ")" }
    }
    run {
        val l18 = f(l.NEG_NAN, l.NEG_ONE)
        val r18 = l.NEG_NAN
        check(biteq(l18, r18)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l18.toBits()).toString() + " " + (Hex(l18)).toString() + ")\nr: " + (r).toString() + " (" + (r18.toBits()).toString() + " " + (Hex(r18)).toString() + ")" }
    }
    run {
        val l19 = f(l.NEG_NAN, l.NEG_NAN)
        val r19 = l.NEG_NAN
        check(biteq(l19, r19)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l19.toBits()).toString() + " " + (Hex(l19)).toString() + ")\nr: " + (r).toString() + " (" + (r19.toBits()).toString() + " " + (Hex(r19)).toString() + ")" }
    }
    run {
        val l20 = f(l.ONE, l.NAN)
        val r20 = l.ONE
        check(biteq(l20, r20)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l20.toBits()).toString() + " " + (Hex(l20)).toString() + ")\nr: " + (r).toString() + " (" + (r20.toBits()).toString() + " " + (Hex(r20)).toString() + ")" }
    }
    run {
        val l21 = f(l.ONE, l.NEG_NAN)
        val r21 = l.NEG_ONE
        check(biteq(l21, r21)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l21.toBits()).toString() + " " + (Hex(l21)).toString() + ")\nr: " + (r).toString() + " (" + (r21.toBits()).toString() + " " + (Hex(r21)).toString() + ")" }
    }
    run {
        val l22 = f(l.NEG_ONE, l.NAN)
        val r22 = l.ONE
        check(biteq(l22, r22)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l22.toBits()).toString() + " " + (Hex(l22)).toString() + ")\nr: " + (r).toString() + " (" + (r22.toBits()).toString() + " " + (Hex(r22)).toString() + ")" }
    }
    run {
        val l23 = f(l.NEG_ONE, l.NEG_NAN)
        val r23 = l.NEG_ONE
        check(biteq(l23, r23)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l23.toBits()).toString() + " " + (Hex(l23)).toString() + ")\nr: " + (r).toString() + " (" + (r23.toBits()).toString() + " " + (Hex(r23)).toString() + ")" }
    }
}

public class CopysignInlineTests {
    @kotlin.test.Test
    public fun specTestsF16() {
        specTest<F16>(::copysignf16)
    }

    @kotlin.test.Test
    public fun specTestsF32() {
        specTest<kotlin.Float>(::copysignf)
    }

    @kotlin.test.Test
    public fun specTestsF64() {
        specTest<Double>(::copysign)
    }

    @kotlin.test.Test
    public fun specTestsF128() {
        specTest<F128>(::copysignf128)
    }
}
