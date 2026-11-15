// port-lint: source math/atanh.rs
package io.github.kotlinmania.rustlang.libm.math.atanh

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.libm.math.log1p.log1p

// atanh(x) = log((1+x)/(1-x))/2 = log1p(2x/(1-x))/2 ~= x + x^3/3 + o(x^5)

/**
 * Inverse hyperbolic tangent (f64)
 *
 * Calculates the inverse hyperbolic tangent of `x`.
 * Is defined as `log((1 + x) / (1 - x)) / 2 = log1p(2x / (1 - x)) / 2`.
 */
public fun atanh(x: Double): Double {
    val u = x.toRawBits().toULong()
    val e = ((u shr 52).toInt()) and 0x7ff
    val sign = (u shr 63) != 0uL
    // |x|
    var y = Double.fromBits((u and 0x7fffffffffffffffuL).toLong())
    if (e < 0x3ff - 1) {
        if (e < 0x3ff - 32) {
            // handle underflow
            if (e == 0) {
                readVolatile(y.toFloat(), typeLayoutT = PrimitiveLayouts.F32)
            }
        } else {
            // |x| < 0.5, up to 1.7ulp error
            y = 0.5 * log1p(2.0 * y + 2.0 * y * y / (1.0 - y))
        }
    } else {
        // avoid overflow
        y = 0.5 * log1p(2.0 * (y / (1.0 - y)))
    }
    return if (sign) {
        -y
    } else {
        y
    }
}
