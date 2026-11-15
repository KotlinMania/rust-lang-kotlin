// port-lint: source math/acoshf.rs
package io.github.kotlinmania.rustlang.libm.math.acoshf

import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrtf
import io.github.kotlinmania.rustlang.math.log1pf.log1pf
import io.github.kotlinmania.rustlang.math.logf.logf
import io.github.kotlinmania.rustlang.math.support.floattraits.Float

public val LN2: kotlin.Float = 0.693147180559945309417232121458176568f

/**
 * Inverse hyperbolic cosine (f32)
 *
 * Calculates the inverse hyperbolic cosine of `x`.
 * Is defined as `log(x + sqrt(x * x - 1))`.
 * `x` must be a number greater than or equal to 1.
 */
public fun acoshf(x: kotlin.Float): kotlin.Float {
    val ux = x.toRawBits().toUInt()
    // x < 1 domain error is handled in the called functions
    return if ((ux.toULong() and (kotlin.Float.SIGN_MASK.inv()).toULong()) < 2.0f.toBits().toUInt()) {
        // |x| < 2, invalid if x < 1
        // up to 2ulp error in [1,1.125]
        val x1 = x - 1.0f
        log1pf(x1 + sqrtf(x1 * x1 + 2.0f * x1))
    } else if (ux < ((1 shl 12).toFloat()).toRawBits().toUInt()) {
        // 2 <= x < 0x1p12
        logf(2.0f * x - 1.0f / (x + sqrtf(x * x - 1.0f)))
    } else {
        // x >= 0x1p12 or x <= -2 or nan
        logf(x) + LN2
    }
}
