// port-lint: source float/complex/div.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.complex.div

import io.github.kotlinmania.rustlang.compilerbuiltins.math.fmax
import io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.generic.ilogb
import io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.generic.scalbn
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.num.complex.Complex
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

/**
 * Returns the quotient of `(a + ib)` and `(c + id)`.
 *
 * This implementation uses the standard formula, but has special behavior when the output
 * of that formula has both a real and imaginary component that are NaN.
 */
public fun <F : Float> complexDiv(a: F, b: F, c: F, d: F): Complex<F> {
    var a1 = a
    var b1 = b
    var c1 = c
    var d1 = d
    val max = fmax(c1.abs(), d1.abs())
    var ilogbw = 0
    if (max.isFinite() && max != a.ZERO) {
        ilogbw = ilogb(max)
        c1 = scalbn(c1, -ilogbw)
        d1 = scalbn(d1, -ilogbw)
    }
    val denom = c1 * c1 + d1 * d1
    val z = Complex.new(scalbn((a1 * c1 + b1 * d1) / denom, -ilogbw), scalbn((b1 * c1 - a1 * d1) / denom, -ilogbw))
    // The fast path: exit when at least one component is not NaN.
    if (!(z.re.isNan() && z.im.isNan())) {
        return z
    }
    val signedUnitIfInf =
        { x: F ->
            val mag =
                if (x.isInfinite()) {
                    a.ONE
                } else {
                    a.ZERO
                }
            mag.copysign(x)
        }
    if (denom == a.ZERO && (!a1.isNan() || !b1.isNan())) {
        z.re = a.INFINITY.copysign(c1) * a1
        z.im = a.INFINITY.copysign(c1) * b1
    } else if ((a1.isInfinite() || b1.isInfinite()) && c1.isFinite() && d1.isFinite()) {
        a1 = signedUnitIfInf(a1)
        b1 = signedUnitIfInf(b1)
        z.re = a.INFINITY * (a1 * c1 + b1 * d1)
        z.im = a.INFINITY * (b1 * c1 - a1 * d1)
    } else if (max.isInfinite() && a1.isFinite() && b1.isFinite()) {
        c1 = signedUnitIfInf(c1)
        d1 = signedUnitIfInf(d1)
        z.re = a.ZERO * (a1 * c1 + b1 * d1)
        z.im = a.ZERO * (b1 * c1 - a1 * d1)
    }
    return z
}

public fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> = complexDiv(a, b, c, d)

public fun divsc3(a: Float, b: Float, c: Float, d: Float): Complex<Float> = complexDiv(a, b, c, d)

public fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> = complexDiv(a, b, c, d)

public fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> = complexDiv(a, b, c, d)
