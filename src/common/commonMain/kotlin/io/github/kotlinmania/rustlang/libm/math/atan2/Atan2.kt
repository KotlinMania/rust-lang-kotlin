// port-lint: source math/atan2.rs
package io.github.kotlinmania.rustlang.libm.math.atan2

import io.github.kotlinmania.rustlang.math.atan.atan
import io.github.kotlinmania.rustlang.math.fabs.fabs

public val PI: Double = 3.1415926535897931160e00

// 0x400921FB, 0x54442D18
public val PI_LO: Double = 1.2246467991473531772e-16

// 0x3CA1A626, 0x33145C07

/**
 * Arctangent of y/x (f64)
 *
 * Computes the inverse tangent (arc tangent) of `y / x`.
 * Produces the correct result even for angles near pi/2 or -pi/2 (that is, when `x` is near 0).
 * Returns a value in radians, in the range of -pi to pi.
 */
public fun atan2(y: Double, x: Double): Double {
    if (x.isNaN() || y.isNaN()) {
        return x + y
    }
    var ix = (x.toRawBits().toULong() shr 32).toUInt()
    val lx = x.toRawBits().toULong().toUInt()
    var iy = (y.toRawBits().toULong() shr 32).toUInt()
    val ly = y.toRawBits().toULong().toUInt()
    if ((((ix - 0x3ff00000u)) or lx) == 0u) {
        // x = 1.0
        return atan(y)
    }
    val m = ((iy shr 31) and 1u) or ((ix shr 30) and 2u)
    // 2*sign(x)+sign(y)
    ix = ix and 0x7fffffffu
    iy = iy and 0x7fffffffu
    // when y = 0
    if ((iy or ly) == 0u) {
        return when (m) {
            0, 1 -> y
            // atan(+-0,+anything)=+-0
            2 -> PI
            // atan(+0,-anything) = PI
            else -> -PI
        }
    }
    // when x = 0
    if ((ix or lx) == 0u) {
        return if (m and 1u != 0u) {
            -PI / 2.0
        } else {
            PI / 2.0
        }
    }
    // when x is INF
    if (ix == 0x7ff00000u) {
        if (iy == 0x7ff00000u) {
            return when (m) {
                0 -> PI / 4.0
                // atan(+INF,+INF)
                1 -> -PI / 4.0
                // atan(-INF,+INF)
                2 -> 3.0 * PI / 4.0
                // atan(+INF,-INF)
                else -> -3.0 * PI / 4.0
            }
        } else {
            return when (m) {
                0 -> 0.0
                // atan(+...,+INF)
                1 -> -0.0
                // atan(-...,+INF)
                2 -> PI
                // atan(+...,-INF)
                else -> -PI
            }
        }
    }
    // |y/x| > 0x1p64
    if ((ix + ((64u shl 20).toUInt())) < iy || iy == 0x7ff00000u) {
        return if (m and 1u != 0u) {
            -PI / 2.0
        } else {
            PI / 2.0
        }
    }
    // z = atan(|y/x|) without spurious underflow
    val z =
        if ((m and 2u != 0u) && (iy + ((64u shl 20).toUInt())) < ix) {
            // |y/x| < 0x1p-64, x<0
            0.0
        } else {
            atan(fabs(y / x))
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
