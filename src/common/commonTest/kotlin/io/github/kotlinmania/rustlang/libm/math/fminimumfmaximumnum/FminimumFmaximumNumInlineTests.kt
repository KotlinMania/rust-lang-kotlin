// port-lint: tests math/fminimum_fmaximum_num.rs
package io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fminimumNumSpecTest(f: (F, F) -> F) {
    val cases = listOf(Triple(F.ZERO, F.ZERO, F.ZERO), Triple(F.ZERO, F.NEG_ZERO, F.NEG_ZERO), Triple(F.ZERO, F.ONE, F.ZERO), Triple(F.ZERO, F.NEG_ONE, F.NEG_ONE), Triple(F.ZERO, F.INFINITY, F.ZERO), Triple(F.ZERO, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.ZERO, F.NAN, F.ZERO), Triple(F.ZERO, F.NEG_NAN, F.ZERO), Triple(F.ZERO, F.SNAN, F.ZERO), Triple(F.ZERO, F.NEG_SNAN, F.ZERO), Triple(F.NEG_ZERO, F.ZERO, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_ZERO, F.ONE, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_ZERO, F.INFINITY, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_ZERO, F.NAN, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_NAN, F.NEG_ZERO), Triple(F.NEG_ZERO, F.SNAN, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_SNAN, F.NEG_ZERO), Triple(F.ONE, F.ZERO, F.ZERO), Triple(F.ONE, F.NEG_ZERO, F.NEG_ZERO), Triple(F.ONE, F.ONE, F.ONE), Triple(F.ONE, F.NEG_ONE, F.NEG_ONE), Triple(F.ONE, F.INFINITY, F.ONE), Triple(F.ONE, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.ONE, F.NAN, F.ONE), Triple(F.ONE, F.NEG_NAN, F.ONE), Triple(F.ONE, F.SNAN, F.ONE), Triple(F.ONE, F.NEG_SNAN, F.ONE), Triple(F.NEG_ONE, F.ZERO, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_ZERO, F.NEG_ONE), Triple(F.NEG_ONE, F.ONE, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_ONE, F.INFINITY, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_ONE, F.NAN, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_NAN, F.NEG_ONE), Triple(F.NEG_ONE, F.SNAN, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_SNAN, F.NEG_ONE), Triple(F.INFINITY, F.ZERO, F.ZERO), Triple(F.INFINITY, F.NEG_ZERO, F.NEG_ZERO), Triple(F.INFINITY, F.ONE, F.ONE), Triple(F.INFINITY, F.NEG_ONE, F.NEG_ONE), Triple(F.INFINITY, F.INFINITY, F.INFINITY), Triple(F.INFINITY, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.INFINITY, F.NAN, F.INFINITY), Triple(F.INFINITY, F.NEG_NAN, F.INFINITY), Triple(F.INFINITY, F.SNAN, F.INFINITY), Triple(F.INFINITY, F.NEG_SNAN, F.INFINITY), Triple(F.NEG_INFINITY, F.ZERO, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_ZERO, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.ONE, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_ONE, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.INFINITY, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NAN, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_NAN, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.SNAN, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_SNAN, F.NEG_INFINITY), Triple(F.NAN, F.ZERO, F.ZERO), Triple(F.NAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NAN, F.ONE, F.ONE), Triple(F.NAN, F.NEG_ONE, F.NEG_ONE), Triple(F.NAN, F.INFINITY, F.INFINITY), Triple(F.NAN, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NAN, F.NAN, F.NAN), Triple(F.NEG_NAN, F.ZERO, F.ZERO), Triple(F.NEG_NAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_NAN, F.ONE, F.ONE), Triple(F.NEG_NAN, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_NAN, F.INFINITY, F.INFINITY), Triple(F.NEG_NAN, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.SNAN, F.ZERO, F.ZERO), Triple(F.SNAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.SNAN, F.ONE, F.ONE), Triple(F.SNAN, F.NEG_ONE, F.NEG_ONE), Triple(F.SNAN, F.INFINITY, F.INFINITY), Triple(F.SNAN, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_SNAN, F.ZERO, F.ZERO), Triple(F.NEG_SNAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_SNAN, F.ONE, F.ONE), Triple(F.NEG_SNAN, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_SNAN, F.INFINITY, F.INFINITY), Triple(F.NEG_SNAN, F.NEG_INFINITY, F.NEG_INFINITY))
    for ((x, y, expected) in cases) {
        val actual = f(x, y)
        run {
            val l = actual
            val r = expected
            check(biteq(l, r)) { ("fminimum_num(${Hex(x)}, ${Hex(y)}) (${Hex(x.toBits())}, ${Hex(y.toBits())})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
    }
    // Selection between NaNs does not matter, it just must be quiet
    check(f(F.NAN, F.NEG_NAN).isQnan())
    check(f(F.NEG_NAN, F.NAN).isQnan())
    check(f(F.NEG_NAN, F.NEG_NAN).isQnan())
    // These operations should technically return a qnan, but LLVM optimizes out our
    // `* 1.0` canonicalization.
    check(f(F.NAN, F.NEG_SNAN).isNan())
    check(f(F.NAN, F.SNAN).isNan())
    check(f(F.NEG_NAN, F.NEG_SNAN).isNan())
    check(f(F.NEG_NAN, F.SNAN).isNan())
    check(f(F.NEG_SNAN, F.NAN).isNan())
    check(f(F.NEG_SNAN, F.NEG_NAN).isNan())
    check(f(F.NEG_SNAN, F.NEG_SNAN).isNan())
    check(f(F.NEG_SNAN, F.SNAN).isNan())
    check(f(F.SNAN, F.NAN).isNan())
    check(f(F.SNAN, F.NEG_NAN).isNan())
    check(f(F.SNAN, F.NEG_SNAN).isNan())
    check(f(F.SNAN, F.SNAN).isNan())
}

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fmaximumNumSpecTest(f: (F, F) -> F) {
    val cases = listOf(Triple(F.ZERO, F.ZERO, F.ZERO), Triple(F.ZERO, F.NEG_ZERO, F.ZERO), Triple(F.ZERO, F.ONE, F.ONE), Triple(F.ZERO, F.NEG_ONE, F.ZERO), Triple(F.ZERO, F.INFINITY, F.INFINITY), Triple(F.ZERO, F.NEG_INFINITY, F.ZERO), Triple(F.ZERO, F.NAN, F.ZERO), Triple(F.ZERO, F.NEG_NAN, F.ZERO), Triple(F.ZERO, F.SNAN, F.ZERO), Triple(F.ZERO, F.NEG_SNAN, F.ZERO), Triple(F.NEG_ZERO, F.ZERO, F.ZERO), Triple(F.NEG_ZERO, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_ZERO, F.ONE, F.ONE), Triple(F.NEG_ZERO, F.NEG_ONE, F.NEG_ZERO), Triple(F.NEG_ZERO, F.INFINITY, F.INFINITY), Triple(F.NEG_ZERO, F.NEG_INFINITY, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NAN, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_NAN, F.NEG_ZERO), Triple(F.NEG_ZERO, F.SNAN, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_SNAN, F.NEG_ZERO), Triple(F.ONE, F.ZERO, F.ONE), Triple(F.ONE, F.NEG_ZERO, F.ONE), Triple(F.ONE, F.ONE, F.ONE), Triple(F.ONE, F.NEG_ONE, F.ONE), Triple(F.ONE, F.INFINITY, F.INFINITY), Triple(F.ONE, F.NEG_INFINITY, F.ONE), Triple(F.ONE, F.NAN, F.ONE), Triple(F.ONE, F.NEG_NAN, F.ONE), Triple(F.ONE, F.SNAN, F.ONE), Triple(F.ONE, F.NEG_SNAN, F.ONE), Triple(F.NEG_ONE, F.ZERO, F.ZERO), Triple(F.NEG_ONE, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_ONE, F.ONE, F.ONE), Triple(F.NEG_ONE, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_ONE, F.INFINITY, F.INFINITY), Triple(F.NEG_ONE, F.NEG_INFINITY, F.NEG_ONE), Triple(F.NEG_ONE, F.NAN, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_NAN, F.NEG_ONE), Triple(F.NEG_ONE, F.SNAN, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_SNAN, F.NEG_ONE), Triple(F.INFINITY, F.ZERO, F.INFINITY), Triple(F.INFINITY, F.NEG_ZERO, F.INFINITY), Triple(F.INFINITY, F.ONE, F.INFINITY), Triple(F.INFINITY, F.NEG_ONE, F.INFINITY), Triple(F.INFINITY, F.INFINITY, F.INFINITY), Triple(F.INFINITY, F.NEG_INFINITY, F.INFINITY), Triple(F.INFINITY, F.NAN, F.INFINITY), Triple(F.INFINITY, F.NEG_NAN, F.INFINITY), Triple(F.INFINITY, F.SNAN, F.INFINITY), Triple(F.INFINITY, F.NEG_SNAN, F.INFINITY), Triple(F.NEG_INFINITY, F.ZERO, F.ZERO), Triple(F.NEG_INFINITY, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_INFINITY, F.ONE, F.ONE), Triple(F.NEG_INFINITY, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_INFINITY, F.INFINITY, F.INFINITY), Triple(F.NEG_INFINITY, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NAN, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_NAN, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.SNAN, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_SNAN, F.NEG_INFINITY), Triple(F.NAN, F.ZERO, F.ZERO), Triple(F.NAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NAN, F.ONE, F.ONE), Triple(F.NAN, F.NEG_ONE, F.NEG_ONE), Triple(F.NAN, F.INFINITY, F.INFINITY), Triple(F.NAN, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NAN, F.NAN, F.NAN), Triple(F.NEG_NAN, F.ZERO, F.ZERO), Triple(F.NEG_NAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_NAN, F.ONE, F.ONE), Triple(F.NEG_NAN, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_NAN, F.INFINITY, F.INFINITY), Triple(F.NEG_NAN, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.SNAN, F.ZERO, F.ZERO), Triple(F.SNAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.SNAN, F.ONE, F.ONE), Triple(F.SNAN, F.NEG_ONE, F.NEG_ONE), Triple(F.SNAN, F.INFINITY, F.INFINITY), Triple(F.SNAN, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_SNAN, F.ZERO, F.ZERO), Triple(F.NEG_SNAN, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_SNAN, F.ONE, F.ONE), Triple(F.NEG_SNAN, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_SNAN, F.INFINITY, F.INFINITY), Triple(F.NEG_SNAN, F.NEG_INFINITY, F.NEG_INFINITY))
    for ((x, y, expected) in cases) {
        val actual = f(x, y)
        run {
            val l = actual
            val r = expected
            check(biteq(l, r)) { ("fmaximum_num(${Hex(x)}, ${Hex(y)}) (${Hex(x.toBits())}, ${Hex(y.toBits())})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
    }
    // Selection between NaNs does not matter, it just must be quiet
    check(f(F.NAN, F.NEG_NAN).isQnan())
    check(f(F.NEG_NAN, F.NAN).isQnan())
    check(f(F.NEG_NAN, F.NEG_NAN).isQnan())
    // These operations should technically return a qnan, but LLVM optimizes out our
    // `* 1.0` canonicalization.
    check(f(F.NAN, F.NEG_SNAN).isNan())
    check(f(F.NAN, F.SNAN).isNan())
    check(f(F.NEG_NAN, F.NEG_SNAN).isNan())
    check(f(F.NEG_NAN, F.SNAN).isNan())
    check(f(F.NEG_SNAN, F.NAN).isNan())
    check(f(F.NEG_SNAN, F.NEG_NAN).isNan())
    check(f(F.NEG_SNAN, F.NEG_SNAN).isNan())
    check(f(F.NEG_SNAN, F.SNAN).isNan())
    check(f(F.SNAN, F.NAN).isNan())
    check(f(F.SNAN, F.NEG_NAN).isNan())
    check(f(F.SNAN, F.NEG_SNAN).isNan())
    check(f(F.SNAN, F.SNAN).isNan())
}

public class FminimumFmaximumNumInlineTests {
    @kotlin.test.Test
    public fun fminimumNumSpecTestsF16() {
        fminimumNumSpecTest<F16>(::fminimumNumf16)
    }

    @kotlin.test.Test
    public fun fminimumNumSpecTestsF32() {
        fminimumNumSpecTest<kotlin.Float>(::fminimumNumf)
    }

    @kotlin.test.Test
    public fun fminimumNumSpecTestsF64() {
        fminimumNumSpecTest<Double>(::fminimumNum)
    }

    @kotlin.test.Test
    public fun fminimumNumSpecTestsF128() {
        fminimumNumSpecTest<F128>(::fminimumNumf128)
    }

    @kotlin.test.Test
    public fun fmaximumNumSpecTestsF16() {
        fmaximumNumSpecTest<F16>(::fmaximumNumf16)
    }

    @kotlin.test.Test
    public fun fmaximumNumSpecTestsF32() {
        fmaximumNumSpecTest<kotlin.Float>(::fmaximumNumf)
    }

    @kotlin.test.Test
    public fun fmaximumNumSpecTestsF64() {
        fmaximumNumSpecTest<Double>(::fmaximumNum)
    }

    @kotlin.test.Test
    public fun fmaximumNumSpecTestsF128() {
        fmaximumNumSpecTest<F128>(::fmaximumNumf128)
    }
}
