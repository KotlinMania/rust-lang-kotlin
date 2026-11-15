// port-lint: source math/atan2f.rs
package io.github.kotlinmania.rustlang.libm.math.atan2f

import io.github.kotlinmania.rustlang.libm.math.atanf.atanf
import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf

public val PI: kotlin.Float = 3.1415927410e00f

// 0x40490fdb
public val PI_LO: kotlin.Float = -8.7422776573e-08f

// 0xb3bbbd2e

/**
 * Arctangent of y/x (f32)
 *
 * Computes the inverse tangent (arc tangent) of `y / x`.
 * Produces the correct result even for angles near pi/2 or -pi/2 (that is, when `x` is near 0).
 * Returns a value in radians, in the range of -pi to pi.
 */
public fun atan2f(y: kotlin.Float, x: kotlin.Float): kotlin.Float {
    if (x.isNaN() || y.isNaN()) {
        return x + y
    }
    var ix = x.toRawBits().toUInt()
    var iy = y.toRawBits().toUInt()
    if (ix == 0x3f800000u) {
        // x=1.0
        return atanf(y)
    }
    val m = ((iy shr 31) and 1u) or ((ix shr 30) and 2u)
    // 2*sign(x)+sign(y)
    ix = ix and 0x7fffffffu
    iy = iy and 0x7fffffffu
    // when y = 0
    if (iy == 0u) {
        return when (m) {
            0, 1 -> y
            // atan(+-0,+anything)=+-0
            2 -> PI
            // atan(+0,-anything) = pi
            else -> -PI
        }
    }
    // when x = 0
    if (ix == 0u) {
        return if (m and 1u != 0u) {
            -PI / 2.0f
        } else {
            PI / 2.0f
        }
    }
    // when x is INF
    if (ix == 0x7f800000u) {
        return if (iy == 0x7f800000u) {
            when (m) {
                0 -> PI / 4.0f
                // atan(+INF,+INF)
                1 -> -PI / 4.0f
                // atan(-INF,+INF)
                2 -> 3.0f * PI / 4.0f
                // atan(+INF,-INF)
                else -> -3.0f * PI / 4.0f
            }
        } else {
            when (m) {
                0 -> 0.0f
                // atan(+...,+INF)
                1 -> -0.0f
                // atan(-...,+INF)
                2 -> PI
                // atan(+...,-INF)
                else -> -PI
            }
        }
    }
    // |y/x| > 0x1p26
    if ((ix + (26u shl 23) < iy) || (iy == 0x7f800000u)) {
        return if (m and 1u != 0u) {
            -PI / 2.0f
        } else {
            PI / 2.0f
        }
    }
    // z = atan(|y/x|) with correct underflow
    val z =
        if ((m and 2u != 0u) && (iy + (26u shl 23) < ix)) {
            // |y/x| < 0x1p-26, x < 0
            0.0
        } else {
            atanf(fabsf(y / x))
        }
    return when (m) {
        0 -> z
        // atan(+,+)
        1 -> -z
        // atan(-,+)
        2 -> PI - (z - PI_LO)
        // atan(+,-)
        else -> (z - PI_LO) - PI
    }
}
