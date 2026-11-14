// port-lint: source math/tanhf.rs
package io.github.kotlinmania.rustlang.libm.math.tanhf

import io.github.kotlinmania.rustlang.libm.math.expm1f.expm1f

/**
 * The hyperbolic tangent of `x` (f32).
 *
 * `x` is specified in radians.
 */
public fun tanhf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    // x = |x|
    var ix = x1.toRawBits().toUInt()
    val sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    x1 = kotlin.Float.fromBits(ix.toInt())
    val w = ix
    val tt =
        if (w > 0x3f0c9f54u) {
            // |x| > log(3)/2 ~= 0.5493 or nan
            if (w > 0x41200000u) {
                // |x| > 10
                1.0f + 0.0f / x1
            } else {
                val t = expm1f(2.0f * x1)
                1.0 - 2.0 / (t + 2.0)
            }
        } else if (w > 0x3e82c578u) {
            // |x| > log(5/3)/2 ~= 0.2554
            val t1 = expm1f(2.0f * x1)
            t1 / (t1 + 2.0)
        } else if (w >= 0x00800000u) {
            // |x| >= 0x1p-126
            val t2 = expm1f(-2.0f * x1)
            -t2 / (t2 + 2.0)
        } else {
            readVolatile(x1 * x1)
            x1
        }
    return if (sign) {
        -tt
    } else {
        tt
    }
}
