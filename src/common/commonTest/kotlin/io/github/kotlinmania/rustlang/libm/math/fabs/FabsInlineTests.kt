// port-lint: tests math/fabs.rs
package io.github.kotlinmania.rustlang.libm.math.fabs

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex

/**
 * Based on https://en.cppreference.com/w/cpp/numeric/math/fabs
 */
public fun <Int, SignedInt, F : Float<F, Any?, Any?>> specTest(f: (F) -> F) {
    run {
        val l = f(F.ZERO)
        val r = F.ZERO
        check(l.biteq(r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
    }
    run {
        val l1 = f(F.NEG_ZERO)
        val r1 = F.ZERO
        check(l1.biteq(r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
    }
    run {
        val l2 = f(F.INFINITY)
        val r2 = F.INFINITY
        check(l2.biteq(r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
    }
    run {
        val l3 = f(F.NEG_INFINITY)
        val r3 = F.INFINITY
        check(l3.biteq(r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
    }
    check(f(F.NAN).isNan())
    // Not spec rewquired but we expect it
    check(f(F.NAN).isSignPositive())
    check(f(F.fromBits(F.NAN.toBits() or F.SIGN_MASK)).isSignPositive())
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
