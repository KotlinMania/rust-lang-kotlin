// port-lint: source float/complex/mul.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.complex.mul

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex
import io.github.kotlinmania.rustlang.support.Float

/**
 * Returns the product of `a + ib` and `c + id`.
 *
 * The standard formula is `(ac - bd) + (ad + bc) i`, but this function has custom behavior when
 * both the real and imaginary components of that expression are NaN.
 */
public fun <F : Float> complexMul(a: F, b: F, c: F, d: F): Complex<F> {
    var a1 = a
    var b1 = b
    var c1 = c
    var d1 = d
    val ac = a1 * c1
    val bd = b1 * d1
    val ad = a1 * d1
    val bc = b1 * c1
    val z = Complex.new(ac - bd, ad + bc)
    // The fast path: exit when at least one component is not NaN.
    if (!(z.re.isNan() && z.im.isNan())) {
        return z
    }
    val zeroIfNan =
        { x: F -> if (x.isNan()) {
            F.ZERO.copysign(x)
        } else {
            x
        } }
    val signedUnitIfInf =
        { x: F ->
            val mag =
                if (x.isInfinite()) {
                    F.ONE
                } else {
                    F.ZERO
                }
            mag.copysign(x)
        }
    var recalc = false
    if (a1.isInfinite() || b1.isInfinite()) {
        a1 = signedUnitIfInf(a1)
        b1 = signedUnitIfInf(b1)
        c1 = zeroIfNan(c1)
        d1 = zeroIfNan(d1)
        recalc = true
    }
    if (c1.isInfinite() || d1.isInfinite()) {
        c1 = signedUnitIfInf(c1)
        d1 = signedUnitIfInf(d1)
        a1 = zeroIfNan(a1)
        b1 = zeroIfNan(b1)
        recalc = true
    }
    if (!recalc && (ac.isInfinite() || bd.isInfinite() || ad.isInfinite() || bc.isInfinite())) {
        a1 = zeroIfNan(a1)
        b1 = zeroIfNan(b1)
        c1 = zeroIfNan(c1)
        d1 = zeroIfNan(d1)
        recalc = true
    }
    if (!recalc) {
        return z
    }
    return Complex.new(F.INFINITY * (a1 * c1 - b1 * d1), F.INFINITY * (a1 * d1 + b1 * c1))
}

public fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> = complexMul(a, b, c, d)

public fun mulsc3(a: Float, b: Float, c: Float, d: Float): Complex<Float> = complexMul(a, b, c, d)

public fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> = complexMul(a, b, c, d)

public fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> = complexMul(a, b, c, d)
