// port-lint: source math/tan.rs
package io.github.kotlinmania.rustlang.libm.math.tan

import io.github.kotlinmania.rustlang.libm.math.ktan.kTan
import io.github.kotlinmania.rustlang.libm.math.rempio2.remPio2

// tan(x)
// Return tangent function of x.
//
// kernel function:
// kTan           ... tangent function on [-pi/4,pi/4]
// remPio2        ... argument reduction routine
//
// Method.
// Let S,C and T denote the sin, cos and tan respectively on
// [-PI/4, +PI/4]. Reduce the argument x to y1+y2 = x-k*pi/2
// in [-pi/4 , +pi/4], and let n = k mod 4.
// We have
//
// n        sin(x)      cos(x)        tan(x)
// ----------------------------------------------------------
// 0          S           C             T
// 1          C          -S            -1/T
// 2         -S          -C             T
// 3         -C           S            -1/T
// ----------------------------------------------------------
//
// Special cases:
// Let trig be any of sin, cos, or tan.
// trig(+-INF)  is NaN, with signals;
// trig(NaN)    is that NaN;
//
// Accuracy:
// TRIG(x) returns trig(x) nearly rounded

/**
 * The tangent of `x` (f64).
 *
 * `x` is specified in radians.
 */
public fun tan(x: Double): Double {
    val x1p120 = kotlin.Float.fromBits(0x7b800000u.toInt())
    // 0x1p120f === 2 ^ 120
    val ix = (x.toRawBits().toULong() shr 32).toUInt() and 0x7fffffffu
    // |x| ~< pi/4
    if (ix <= 0x3fe921fbu) {
        if (ix < 0x3e400000u) {
            readVolatile(if (ix < 0x00100000u) {
                x / x1p120.toDouble()
            } else {
                x + x1p120.toDouble()
            })
            return x
        }
        return kTan(x, 0.0, 0)
    }
    // tan(Inf or NaN) is NaN
    if (ix >= 0x7ff00000u) {
        return x - x
    }
    // argument reduction
    val (n, y0, y1) = remPio2(x)
    return kTan(y0, y1, n and 1)
}
