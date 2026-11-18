// port-lint: tests math/fabs.rs
package io.github.kotlinmania.rustlang.libm.math.fabs

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex

/**
 * Based on https://en.cppreference.com/w/cpp/numeric/math/fabs
 */
public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> specTest(f: (F) -> F) {
    run {
        val l = f(F.ZERO)
        val r = l.ZERO
        check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
    }
    run {
        val l1 = f(l.NEG_ZERO)
        val r1 = l.ZERO
        check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
    }
    run {
        val l2 = f(l.INFINITY)
        val r2 = l.INFINITY
        check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
    }
    run {
        val l3 = f(l.NEG_INFINITY)
        val r3 = l.INFINITY
        check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
    }
    check(f(l.NAN).isNan())
    // Not spec rewquired but we expect it
    check(f(l.NAN).isSignPositive())
    check(f(fromBits(l.NAN.toBits() or l.SIGN_MASK)).isSignPositive())
}

public class FabsInlineTests {
    @kotlin.test.Test
    public fun sanityCheckF16() {
        check(fabsf16(-F16.fromFloat(1.0f)) == F16.fromFloat(1.0f))
        check(fabsf16(F16.fromFloat(2.8f)) == F16.fromFloat(2.8f))
    }

    @kotlin.test.Test
    public fun specTestsF16() {
        specTest<F16>(::fabsf16)
    }

    @kotlin.test.Test
    public fun sanityCheckF32() {
        check(fabsf(-1.0f) == 1.0f.toFloat())
        check(fabsf(2.8f) == 2.8f.toFloat())
    }

    @kotlin.test.Test
    public fun specTestsF32() {
        specTest<kotlin.Float>(::fabsf)
    }

    @kotlin.test.Test
    public fun sanityCheckF64() {
        check(fabs(-1.0) == 1.0.toDouble())
        check(fabs(2.8) == 2.8.toDouble())
    }

    @kotlin.test.Test
    public fun specTestsF64() {
        specTest<Double>(::fabs)
    }

    @kotlin.test.Test
    public fun sanityCheckF128() {
        check(fabsf128(-F128.fromDouble(1.0)) == F128.fromDouble(1.0))
        check(fabsf128(F128.fromDouble(2.8)) == F128.fromDouble(2.8))
    }

    @kotlin.test.Test
    public fun specTestsF128() {
        specTest<F128>(::fabsf128)
    }
}
