// port-lint: source math/cos.rs
package io.github.kotlinmania.rustlang.libm.math.cos

import io.github.kotlinmania.rustlang.libm.math.kcos.kCos
import io.github.kotlinmania.rustlang.libm.math.ksin.kSin
import io.github.kotlinmania.rustlang.libm.math.rempio2.remPio2

// cos(x)
// Return cosine function of x.
//
// kernel function:
// kSin           ... sine function on [-pi/4,pi/4]
// kCos           ... cosine function on [-pi/4,pi/4]
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
//

/**
 * The cosine of `x` (f64).
 *
 * `x` is specified in radians.
 */
public fun cos(x: Double): Double {
    val ix = (x.toRawBits().toULong() shr 32).toUInt() and 0x7fffffffu
    // |x| ~< pi/4
    if (ix <= 0x3fe921fbu) {
        if (ix < 0x3e46a09eu) {
            // if x < 2**-27 * sqrt(2)
            // raise inexact if x != 0
            if (x.toInt() == 0) {
                return 1.0
            }
        }
        return kCos(x, 0.0)
    }
    // cos(Inf or NaN) is NaN
    if (ix >= 0x7ff00000u) {
        return x - x
    }
    // argument reduction needed
    val (n, y0, y1) = remPio2(x)
    return when (n and 3) {
        0 -> kCos(y0, y1)
        1 -> -kSin(y0, y1, 1)
        2 -> -kCos(y0, y1)
        else -> kSin(y0, y1, 1)
    }
}
