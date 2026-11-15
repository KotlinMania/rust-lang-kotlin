// port-lint: source math/asinhf.rs
package io.github.kotlinmania.rustlang.libm.math.asinhf

import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrtf
import io.github.kotlinmania.rustlang.math.log1pf.log1pf
import io.github.kotlinmania.rustlang.math.logf.logf

public val LN2: kotlin.Float = 0.693147180559945309417232121458176568f

// asinh(x) = sign(x)*log(|x|+sqrt(x*x+1)) ~= x - x^3/6 + o(x^5)

/**
 * Inverse hyperbolic sine (f32)
 *
 * Calculates the inverse hyperbolic sine of `x`.
 * Is defined as `sgn(x) * log(| x | + sqrt(x * x + 1))`.
 */
public fun asinhf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val u = x1.toRawBits().toUInt()
    val i = u and 0x7fffffffu
    val sign = (u shr 31) != 0u
    // |x|
    x1 = kotlin.Float.fromBits(i.toInt())
    if (i >= 0x3f800000u + (12u shl 23)) {
        // |x| >= 0x1p12 or inf or nan
        x1 = logf(x1) + LN2
    } else if (i >= 0x3f800000u + (1u shl 23)) {
        // |x| >= 2
        x1 = logf(2.0f * x1 + 1.0f / (sqrtf(x1 * x1 + 1.0f) + x1))
    } else if (i >= 0x3f800000u - (12u shl 23)) {
        // |x| >= 0x1p-12, up to 1.6ulp error in [0.125,0.5]
        x1 = log1pf(x1 + x1 * x1 / (sqrtf(x1 * x1 + 1.0f) + 1.0f))
    } else {
        // |x| < 0x1p-12, raise inexact if x!=0
        val x1p120 = kotlin.Float.fromBits(0x7b800000u.toInt())
        readVolatile(x1 + x1p120)
    }
    return if (sign) {
        -x1
    } else {
        x1
    }
}
