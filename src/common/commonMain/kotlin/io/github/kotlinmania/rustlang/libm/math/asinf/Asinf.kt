// port-lint: source math/asinf.rs
package io.github.kotlinmania.rustlang.libm.math.asinf

import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrt
import io.github.kotlinmania.rustlang.libm.math.support.Float

public val PIO2: Double = 1.570796326794896558e00

// coefficients for R(x^2)
public val P_S0: kotlin.Float = 1.6666586697e-01f

public val P_S1: kotlin.Float = -4.2743422091e-02f

public val P_S2: kotlin.Float = -8.6563630030e-03f

public val Q_S1: kotlin.Float = -7.0662963390e-01f

public fun r(z: kotlin.Float): kotlin.Float {
    val p = z * (P_S0 + z * (P_S1 + z * P_S2))
    val q = 1.0f + z * Q_S1
    return p / q
}

/**
 * Arcsine (f32)
 *
 * Computes the inverse sine (arc sine) of the argument `x`.
 * Arguments to asin must be in the range -1 to 1.
 * Returns values in radians, in the range of -pi/2 to pi/2.
 */
public fun asinf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val x1p120 = Double.fromBits(0x3870000000000000uL.toLong())
    // 0x1p-120 === 2 ^ (-120)
    val hx = x1.toRawBits().toUInt()
    val ix = hx and 0x7fffffffu
    if (ix >= 0x3f800000u) {
        // |x| >= 1
        if (ix == 0x3f800000u) {
            // |x| == 1
            return ((x1.toDouble()) * PIO2 + x1p120).toFloat()
            // asin(+-1) = +-pi/2 with inexact
        }
        return 0.0f / (x1 - x1)
        // asin(|x|>1) is NaN
    }
    if (ix < 0x3f000000u) {
        // |x| < 0.5
        // if 0x1p-126 <= |x| < 0x1p-12, avoid raising underflow
        if ((0x00800000 until 0x39800000).contains(ix)) {
            return x1
        }
        return x1 + x1 * r(x1 * x1)
    }
    // 1 > |x| >= 0.5
    val z = (1.0 - x1.abs()) * 0.5
    val s = sqrt(z.toDouble())
    x1 = (PIO2 - 2.0 * (s + s * (r(z).toDouble()))).toFloat()
    return if ((hx shr 31) != 0u) {
        -x1
    } else {
        x1
    }
}
