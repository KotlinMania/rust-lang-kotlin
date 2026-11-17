// port-lint: source math/asinh.rs
package io.github.kotlinmania.rustlang.libm.math.asinh

import io.github.kotlinmania.rustlang.libm.math.log.log
import io.github.kotlinmania.rustlang.libm.math.log1p.log1p
import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrt

public val LN2: Double = 0.693147180559945309417232121458176568

// 0x3fe62e42,  0xfefa39ef
// asinh(x) = sign(x)*log(|x|+sqrt(x*x+1)) ~= x - x^3/6 + o(x^5)

/**
 * Inverse hyperbolic sine (f64)
 *
 * Calculates the inverse hyperbolic sine of `x`.
 * Is defined as `sgn(x) * log(| x | + sqrt(x * x + 1))`.
 */
public fun asinh(x: Double): Double {
    var x1 = x
    var u = x1.toRawBits().toULong()
    val e = ((u shr 52).toInt()) and 0x7ff
    val sign = (u shr 63) != 0uL
    // |x|
    u = u and ((0uL.inv()) shr 1)
    x1 = Double.fromBits(u.toLong())
    if (e >= 0x3ff + 26) {
        // |x| >= 0x1p26 or inf or nan
        x1 = log(x1) + LN2
    } else if (e >= 0x3ff + 1) {
        // |x| >= 2
        x1 = log(2.0 * x1 + 1.0 / (sqrt(x1 * x1 + 1.0) + x1))
    } else if (e >= 0x3ff - 26) {
        // |x| >= 0x1p-26, up to 1.6ulp error in [0.125,0.5]
        x1 = log1p(x1 + x1 * x1 / (sqrt(x1 * x1 + 1.0) + 1.0))
    } else {
        // |x| < 0x1p-26, raise inexact if x != 0
        val x1p120 = Double.fromBits(0x4770000000000000uL.toLong())
        readVolatile(x1 + x1p120)
    }
    return if (sign) {
        -x1
    } else {
        x1
    }
}
