// port-lint: source math/coshf.rs
package io.github.kotlinmania.rustlang.libm.math.coshf

import io.github.kotlinmania.rustlang.math.expf.expf
import io.github.kotlinmania.rustlang.math.expm1f.expm1f
import io.github.kotlinmania.rustlang.math.kexpo2f.kExpo2f

/**
 * Hyperbolic cosine (f64)
 *
 * Computes the hyperbolic cosine of the argument x.
 * Is defined as `(exp(x) + exp(- x)) / 2`
 * Angles are specified in radians.
 */
public fun coshf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val x1p120 = kotlin.Float.fromBits(0x7b800000u.toInt())
    // 0x1p120f === 2 ^ 120
    // |x|
    var ix = x1.toRawBits().toUInt()
    ix = ix and 0x7fffffffu
    x1 = kotlin.Float.fromBits(ix.toInt())
    val w = ix
    // |x| < log(2)
    if (w < 0x3f317217u) {
        if (w < (0x3f800000u - (12u shl 23))) {
            readVolatile(x1 + x1p120)
            return 1.0f
        }
        val t = expm1f(x1)
        return 1.0f + t * t / (2.0f * (1.0f + t))
    }
    // |x| < log(FLT_MAX)
    if (w < 0x42b17217u) {
        val t1 = expf(x1)
        return 0.5f * (t1 + 1.0f / t1)
    }
    // |x| > log(FLT_MAX) or nan
    return kExpo2f(x1)
}
