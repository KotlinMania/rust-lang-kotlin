// port-lint: tests math/fminimum_fmaximum.rs
package io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.math.support.hexfloat.Hex

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fminimumSpecTest(f: (F, F) -> F) {
    val cases = listOf(Triple(F.ZERO, F.ZERO, F.ZERO), Triple(F.ZERO, F.NEG_ZERO, F.NEG_ZERO), Triple(F.ZERO, F.ONE, F.ZERO), Triple(F.ZERO, F.NEG_ONE, F.NEG_ONE), Triple(F.ZERO, F.INFINITY, F.ZERO), Triple(F.ZERO, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.ZERO, F.NAN, F.NAN), Triple(F.NEG_ZERO, F.ZERO, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_ZERO, F.ONE, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_ZERO, F.INFINITY, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_ZERO, F.NAN, F.NAN), Triple(F.ONE, F.ZERO, F.ZERO), Triple(F.ONE, F.NEG_ZERO, F.NEG_ZERO), Triple(F.ONE, F.ONE, F.ONE), Triple(F.ONE, F.NEG_ONE, F.NEG_ONE), Triple(F.ONE, F.INFINITY, F.ONE), Triple(F.ONE, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.ONE, F.NAN, F.NAN), Triple(F.NEG_ONE, F.ZERO, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_ZERO, F.NEG_ONE), Triple(F.NEG_ONE, F.ONE, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_ONE, F.INFINITY, F.NEG_ONE), Triple(F.NEG_ONE, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_ONE, F.NAN, F.NAN), Triple(F.INFINITY, F.ZERO, F.ZERO), Triple(F.INFINITY, F.NEG_ZERO, F.NEG_ZERO), Triple(F.INFINITY, F.ONE, F.ONE), Triple(F.INFINITY, F.NEG_ONE, F.NEG_ONE), Triple(F.INFINITY, F.INFINITY, F.INFINITY), Triple(F.INFINITY, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.INFINITY, F.NAN, F.NAN), Triple(F.NEG_INFINITY, F.ZERO, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_ZERO, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.ONE, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_ONE, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.INFINITY, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NAN, F.NAN), Triple(F.NAN, F.ZERO, F.NAN), Triple(F.NAN, F.NEG_ZERO, F.NAN), Triple(F.NAN, F.ONE, F.NAN), Triple(F.NAN, F.NEG_ONE, F.NAN), Triple(F.NAN, F.INFINITY, F.NAN), Triple(F.NAN, F.NEG_INFINITY, F.NAN), Triple(F.NAN, F.NAN, F.NAN), Triple(F.NAN, F.SNAN, F.NAN))
    for ((x, y, res) in cases) {
        val value = f(x, y)
        run {
            val l = value
            val r = res
            check(biteq(l, r)) { ("fminimum(${Hex(x)}, ${Hex(y)}) (${Hex(x.toBits())}, ${Hex(y.toBits())})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
    }
    // On platforms where operations only return a single canonical NaN (e.g. RISC-V), the
    // result may not exactly match one of the inputs which is fine.
    check(f(F.NAN, F.NEG_NAN).isQnan())
    check(f(F.NEG_NAN, F.NAN).isQnan())
    check(f(F.ZERO, F.NEG_NAN).isQnan())
    check(f(F.NEG_ZERO, F.NEG_NAN).isQnan())
    check(f(F.ONE, F.NEG_NAN).isQnan())
    check(f(F.NEG_ONE, F.NEG_NAN).isQnan())
    check(f(F.INFINITY, F.NEG_NAN).isQnan())
    check(f(F.NEG_INFINITY, F.NEG_NAN).isQnan())
    check(f(F.NEG_NAN, F.ZERO).isQnan())
    check(f(F.NEG_NAN, F.NEG_ZERO).isQnan())
    check(f(F.NEG_NAN, F.ONE).isQnan())
    check(f(F.NEG_NAN, F.NEG_ONE).isQnan())
    check(f(F.NEG_NAN, F.INFINITY).isQnan())
    check(f(F.NEG_NAN, F.NEG_INFINITY).isQnan())
    check(f(F.NEG_NAN, F.NEG_NAN).isQnan())
    // These operations should technically return a qnan, but LLVM optimizes out our
    // `* 1.0` canonicalization.
    check(f(F.INFINITY, F.SNAN).isNan())
    check(f(F.NEG_INFINITY, F.SNAN).isNan())
    check(f(F.NEG_ONE, F.SNAN).isNan())
    check(f(F.NEG_SNAN, F.INFINITY).isNan())
    check(f(F.NEG_SNAN, F.NEG_INFINITY).isNan())
    check(f(F.NEG_SNAN, F.NEG_NAN).isNan())
    check(f(F.NEG_SNAN, F.NEG_ONE).isNan())
    check(f(F.NEG_SNAN, F.NEG_ZERO).isNan())
    check(f(F.NEG_SNAN, F.ONE).isNan())
    check(f(F.NEG_SNAN, F.ZERO).isNan())
    check(f(F.NEG_ZERO, F.SNAN).isNan())
    check(f(F.ONE, F.SNAN).isNan())
    check(f(F.SNAN, F.INFINITY).isNan())
    check(f(F.SNAN, F.NEG_INFINITY).isNan())
    check(f(F.SNAN, F.NEG_ONE).isNan())
    check(f(F.SNAN, F.NEG_SNAN).isNan())
    check(f(F.SNAN, F.NEG_ZERO).isNan())
    check(f(F.SNAN, F.ONE).isNan())
    check(f(F.SNAN, F.SNAN).isNan())
    check(f(F.SNAN, F.ZERO).isNan())
    check(f(F.ZERO, F.SNAN).isNan())
}

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fmaximumSpecTest(f: (F, F) -> F) {
    val cases = listOf(Triple(F.ZERO, F.ZERO, F.ZERO), Triple(F.ZERO, F.NEG_ZERO, F.ZERO), Triple(F.ZERO, F.ONE, F.ONE), Triple(F.ZERO, F.NEG_ONE, F.ZERO), Triple(F.ZERO, F.INFINITY, F.INFINITY), Triple(F.ZERO, F.NEG_INFINITY, F.ZERO), Triple(F.ZERO, F.NAN, F.NAN), Triple(F.NEG_ZERO, F.ZERO, F.ZERO), Triple(F.NEG_ZERO, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_ZERO, F.ONE, F.ONE), Triple(F.NEG_ZERO, F.NEG_ONE, F.NEG_ZERO), Triple(F.NEG_ZERO, F.INFINITY, F.INFINITY), Triple(F.NEG_ZERO, F.NEG_INFINITY, F.NEG_ZERO), Triple(F.NEG_ZERO, F.NAN, F.NAN), Triple(F.ONE, F.ZERO, F.ONE), Triple(F.ONE, F.NEG_ZERO, F.ONE), Triple(F.ONE, F.ONE, F.ONE), Triple(F.ONE, F.NEG_ONE, F.ONE), Triple(F.ONE, F.INFINITY, F.INFINITY), Triple(F.ONE, F.NEG_INFINITY, F.ONE), Triple(F.ONE, F.NAN, F.NAN), Triple(F.NEG_ONE, F.ZERO, F.ZERO), Triple(F.NEG_ONE, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_ONE, F.ONE, F.ONE), Triple(F.NEG_ONE, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_ONE, F.INFINITY, F.INFINITY), Triple(F.NEG_ONE, F.NEG_INFINITY, F.NEG_ONE), Triple(F.NEG_ONE, F.NAN, F.NAN), Triple(F.INFINITY, F.ZERO, F.INFINITY), Triple(F.INFINITY, F.NEG_ZERO, F.INFINITY), Triple(F.INFINITY, F.ONE, F.INFINITY), Triple(F.INFINITY, F.NEG_ONE, F.INFINITY), Triple(F.INFINITY, F.INFINITY, F.INFINITY), Triple(F.INFINITY, F.NEG_INFINITY, F.INFINITY), Triple(F.INFINITY, F.NAN, F.NAN), Triple(F.NEG_INFINITY, F.ZERO, F.ZERO), Triple(F.NEG_INFINITY, F.NEG_ZERO, F.NEG_ZERO), Triple(F.NEG_INFINITY, F.ONE, F.ONE), Triple(F.NEG_INFINITY, F.NEG_ONE, F.NEG_ONE), Triple(F.NEG_INFINITY, F.INFINITY, F.INFINITY), Triple(F.NEG_INFINITY, F.NEG_INFINITY, F.NEG_INFINITY), Triple(F.NEG_INFINITY, F.NAN, F.NAN), Triple(F.NAN, F.ZERO, F.NAN), Triple(F.NAN, F.NEG_ZERO, F.NAN), Triple(F.NAN, F.ONE, F.NAN), Triple(F.NAN, F.NEG_ONE, F.NAN), Triple(F.NAN, F.INFINITY, F.NAN), Triple(F.NAN, F.NEG_INFINITY, F.NAN), Triple(F.NAN, F.NAN, F.NAN), Triple(F.NAN, F.SNAN, F.NAN))
    for ((x, y, res) in cases) {
        val value = f(x, y)
        run {
            val l = value
            val r = res
            check(biteq(l, r)) { ("fmaximum(${Hex(x)}, ${Hex(y)}) (${Hex(x.toBits())}, ${Hex(y.toBits())})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
    }
    // On platforms where operations only return a single canonical NaN (e.g. RISC-V), the
    // result may not exactly match one of the inputs which is fine.
    check(f(F.NAN, F.NEG_NAN).isQnan())
    check(f(F.NEG_NAN, F.NAN).isQnan())
    check(f(F.ZERO, F.NEG_NAN).isQnan())
    check(f(F.NEG_ZERO, F.NEG_NAN).isQnan())
    check(f(F.ONE, F.NEG_NAN).isQnan())
    check(f(F.NEG_ONE, F.NEG_NAN).isQnan())
    check(f(F.INFINITY, F.NEG_NAN).isQnan())
    check(f(F.NEG_INFINITY, F.NEG_NAN).isQnan())
    check(f(F.NEG_NAN, F.ZERO).isQnan())
    check(f(F.NEG_NAN, F.NEG_ZERO).isQnan())
    check(f(F.NEG_NAN, F.ONE).isQnan())
    check(f(F.NEG_NAN, F.NEG_ONE).isQnan())
    check(f(F.NEG_NAN, F.INFINITY).isQnan())
    check(f(F.NEG_NAN, F.NEG_INFINITY).isQnan())
    check(f(F.NEG_NAN, F.NEG_NAN).isQnan())
    // These operations should technically return a qnan, but LLVM optimizes out our
    // `* 1.0` canonicalization.
    check(f(F.INFINITY, F.SNAN).isNan())
    check(f(F.NEG_INFINITY, F.SNAN).isNan())
    check(f(F.NEG_ONE, F.SNAN).isNan())
    check(f(F.NEG_SNAN, F.INFINITY).isNan())
    check(f(F.NEG_SNAN, F.NEG_INFINITY).isNan())
    check(f(F.NEG_SNAN, F.NEG_NAN).isNan())
    check(f(F.NEG_SNAN, F.NEG_ONE).isNan())
    check(f(F.NEG_SNAN, F.NEG_ZERO).isNan())
    check(f(F.NEG_SNAN, F.ONE).isNan())
    check(f(F.NEG_SNAN, F.ZERO).isNan())
    check(f(F.NEG_ZERO, F.SNAN).isNan())
    check(f(F.ONE, F.SNAN).isNan())
    check(f(F.SNAN, F.INFINITY).isNan())
    check(f(F.SNAN, F.NEG_INFINITY).isNan())
    check(f(F.SNAN, F.NEG_ONE).isNan())
    check(f(F.SNAN, F.NEG_SNAN).isNan())
    check(f(F.SNAN, F.NEG_ZERO).isNan())
    check(f(F.SNAN, F.ONE).isNan())
    check(f(F.SNAN, F.SNAN).isNan())
    check(f(F.SNAN, F.ZERO).isNan())
    check(f(F.ZERO, F.SNAN).isNan())
}

public class FminimumFmaximumInlineTests {
    @kotlin.test.Test
    public fun fminimumSpecTestsF16() {
        fminimumSpecTest<F16>(::fminimumf16)
    }

    @kotlin.test.Test
    public fun fminimumSpecTestsF32() {
        fminimumSpecTest<kotlin.Float>(::fminimumf)
    }

    @kotlin.test.Test
    public fun fminimumSpecTestsF64() {
        fminimumSpecTest<Double>(::fminimum)
    }

    @kotlin.test.Test
    public fun fminimumSpecTestsF128() {
        fminimumSpecTest<F128>(::fminimumf128)
    }

    @kotlin.test.Test
    public fun fmaximumSpecTestsF16() {
        fmaximumSpecTest<F16>(::fmaximumf16)
    }

    @kotlin.test.Test
    public fun fmaximumSpecTestsF32() {
        fmaximumSpecTest<kotlin.Float>(::fmaximumf)
    }

    @kotlin.test.Test
    public fun fmaximumSpecTestsF64() {
        fmaximumSpecTest<Double>(::fmaximum)
    }

    @kotlin.test.Test
    public fun fmaximumSpecTestsF128() {
        fmaximumSpecTest<F128>(::fmaximumf128)
    }
}
