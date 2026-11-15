// port-lint: source math/cosh.rs
package io.github.kotlinmania.rustlang.libm.math.cosh

import io.github.kotlinmania.rustlang.math.exp.exp
import io.github.kotlinmania.rustlang.math.expm1.expm1
import io.github.kotlinmania.rustlang.math.kexpo2.kExpo2

/**
 * Hyperbolic cosine (f64)
 *
 * Computes the hyperbolic cosine of the argument x.
 * Is defined as `(exp(x) + exp(- x)) / 2`
 * Angles are specified in radians.
 */
public fun cosh(x: Double): Double {
    var x1 = x
    // |x|
    var ix = x1.toRawBits().toULong()
    ix = ix and 0x7fffffffffffffffuL
    x1 = Double.fromBits(ix.toLong())
    val w = ix shr 32
    // |x| < log(2)
    if (w < 0x3fe62e42uL) {
        if (w < 0x3ff00000uL - (26uL shl 20)) {
            val x1p120 = Double.fromBits(0x4770000000000000uL.toLong())
            readVolatile(x1 + x1p120)
            return 1.0
        }
        val t = expm1(x1)
        // exponential minus 1
        return 1.0 + t * t / (2.0 * (1.0 + t))
    }
    // |x| < log(DBL_MAX)
    if (w < 0x40862e42uL) {
        val t1 = exp(x1)
        // note: if x>log(0x1p26) then the 1/t is not needed
        return 0.5 * (t1 + 1.0 / t1)
    }
    // |x| > log(DBL_MAX) or nan
    return kExpo2(x1)
}
