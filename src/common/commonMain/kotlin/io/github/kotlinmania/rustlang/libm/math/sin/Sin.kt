// port-lint: source math/sin.rs
package io.github.kotlinmania.rustlang.libm.math.sin

import io.github.kotlinmania.rustlang.math.kcos.kCos
import io.github.kotlinmania.rustlang.math.ksin.kSin
import io.github.kotlinmania.rustlang.math.rempio2.remPio2

// sin(x)
// Return sine function of x.
//
// kernel function:
// kSin            ... sine function on [-pi/4,pi/4]
// kCos            ... cose function on [-pi/4,pi/4]
// remPio2         ... argument reduction routine
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
 * The sine of `x` (f64).
 *
 * `x` is specified in radians.
 */
public fun sin(x: Double): Double {
    val x1p120 = Double.fromBits(0x4770000000000000uL.toLong())
    // 0x1p120f === 2 ^ 120
    // High word of x.
    val ix = (x.toRawBits().toULong() shr 32).toUInt() and 0x7fffffffu
    // |x| ~< pi/4
    if (ix <= 0x3fe921fbu) {
        if (ix < 0x3e500000u) {
            // |x| < 2**-26
            // raise inexact if x != 0 and underflow if subnormal
            if (ix < 0x00100000u) {
                readVolatile(x / x1p120)
            } else {
                readVolatile(x + x1p120)
            }
            return x
        }
        return kSin(x, 0.0, 0)
    }
    // sin(Inf or NaN) is NaN
    if (ix >= 0x7ff00000u) {
        return x - x
    }
    // argument reduction needed
    val (n, y0, y1) = remPio2(x)
    return when (n and 3) {
        0 -> kSin(y0, y1, 1)
        1 -> kCos(y0, y1)
        2 -> -kSin(y0, y1, 1)
        else -> -kCos(y0, y1)
    }
}
