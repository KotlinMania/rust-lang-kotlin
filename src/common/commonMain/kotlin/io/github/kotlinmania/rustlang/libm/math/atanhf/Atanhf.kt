// port-lint: source math/atanhf.rs
package io.github.kotlinmania.rustlang.libm.math.atanhf

import io.github.kotlinmania.rustlang.math.log1pf.log1pf

// atanh(x) = log((1+x)/(1-x))/2 = log1p(2x/(1-x))/2 ~= x + x^3/3 + o(x^5)

/**
 * Inverse hyperbolic tangent (f32)
 *
 * Calculates the inverse hyperbolic tangent of `x`.
 * Is defined as `log((1 + x) / (1 - x)) / 2 = log1p(2x / (1 - x)) / 2`.
 */
public fun atanhf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    var u = x1.toRawBits().toUInt()
    val sign = (u shr 31) != 0u
    // |x|
    u = u and 0x7fffffffu
    x1 = kotlin.Float.fromBits(u.toInt())
    if (u < 0x3f800000u - (1u shl 23)) {
        if (u < 0x3f800000u - (32u shl 23)) {
            // handle underflow
            if (u < (1u shl 23)) {
                readVolatile(x1 * x1)
            }
        } else {
            // |x| < 0.5, up to 1.7ulp error
            x1 = 0.5f * log1pf(2.0f * x1 + 2.0f * x1 * x1 / (1.0f - x1))
        }
    } else {
        // avoid overflow
        x1 = 0.5f * log1pf(2.0f * (x1 / (1.0f - x1)))
    }
    return if (sign) {
        -x1
    } else {
        x1
    }
}
