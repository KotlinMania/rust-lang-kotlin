// port-lint: source math/acosh.rs
package io.github.kotlinmania.rustlang.libm.math.acosh

import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrt
import io.github.kotlinmania.rustlang.libm.math.log.log
import io.github.kotlinmania.rustlang.libm.math.log1p.log1p

public val LN2: Double = 0.693147180559945309417232121458176568

// 0x3fe62e42,  0xfefa39ef

/**
 * Inverse hyperbolic cosine (f64)
 *
 * Calculates the inverse hyperbolic cosine of `x`.
 * Is defined as `log(x + sqrt(x * x - 1))`.
 * `x` must be a number greater than or equal to 1.
 */
public fun acosh(x: Double): Double {
    val ux = x.toRawBits().toULong()
    // x < 1 domain error is handled in the called functions
    return if ((ux and (Double.SIGN_MASK.inv()).toULong()) < 2.0.toBits()) {
        // |x| < 2, invalid if x < 1
        // up to 2ulp error in [1,1.125]
        val x1 = x - 1.0
        log1p(x1 + sqrt(x1 * x1 + 2.0 * x1))
    } else if (ux < ((1 shl 26).toDouble()).toRawBits().toULong()) {
        // 2 <= x < 0x1p26
        log(2.0 * x - 1.0 / (x + sqrt(x * x - 1.0)))
    } else {
        // x >= 0x1p26 or x <= -2 or nan
        log(x) + LN2
    }
}
