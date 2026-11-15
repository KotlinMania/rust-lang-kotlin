// port-lint: source math/sincos.rs
package io.github.kotlinmania.rustlang.libm.math.sincos

import io.github.kotlinmania.rustlang.libm.math.getHighWord
import io.github.kotlinmania.rustlang.libm.math.kcos.kCos
import io.github.kotlinmania.rustlang.libm.math.ksin.kSin
import io.github.kotlinmania.rustlang.libm.math.rempio2.remPio2

/**
 * Both the sine and cosine of `x` (f64).
 *
 * `x` is specified in radians and the return value is (sin(x), cos(x)).
 */
public fun sincos(x: Double): Pair<Double, Double> {
    val s: Double
    val c: Double
    var ix: UInt
    ix = getHighWord(x)
    ix = ix and 0x7fffffffu
    // |x| ~< pi/4
    if (ix <= 0x3fe921fbu) {
        // if |x| < 2**-27 * sqrt(2)
        if (ix < 0x3e46a09eu) {
            // raise inexact if x!=0 and underflow if subnormal
            val x1p120 = Double.fromBits(0x4770000000000000uL.toLong())
            // 0x1p120 == 2^120
            if (ix < 0x00100000u) {
                readVolatile(x / x1p120)
            } else {
                readVolatile(x + x1p120)
            }
            return Pair(x, 1.0)
        }
        return Pair(kSin(x, 0.0, 0), kCos(x, 0.0))
    }
    // sincos(Inf or NaN) is NaN
    if (ix >= 0x7ff00000u) {
        val rv = x - x
        return Pair(rv, rv)
    }
    // argument reduction needed
    val (n, y0, y1) = remPio2(x)
    s = kSin(y0, y1, 1)
    c = kCos(y0, y1)
    return when (n and 3) {
        0 -> Pair(s, c)
        1 -> Pair(c, -s)
        2 -> Pair(-s, -c)
        3 -> Pair(-c, s)
        else -> error("unreachable")
    }
}
