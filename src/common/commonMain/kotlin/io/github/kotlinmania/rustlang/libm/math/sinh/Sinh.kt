// port-lint: source math/sinh.rs
package io.github.kotlinmania.rustlang.libm.math.sinh

import io.github.kotlinmania.rustlang.libm.math.expm1.expm1
import io.github.kotlinmania.rustlang.libm.math.expo2.expo2

// sinh(x) = (exp(x) - 1/exp(x))/2
// = (exp(x)-1 + (exp(x)-1)/exp(x))/2
// = x + x^3/6 + o(x^5)
//

/**
 * The hyperbolic sine of `x` (f64).
 */
public fun sinh(x: Double): Double {
    // union (double f; uint64T i;) u = (.f = x);
    // uint32T w;
    // double t, h, absx;
    var uf: Double = x
    var ui: ULong = uf.toRawBits().toULong()
    val w: UInt
    val t: Double
    var h: Double
    val absx: Double
    h = 0.5
    if (ui shr 63 != 0uL) {
        h = -h
    }
    // |x|
    ui = ui and (1uL.inv() / 2uL)
    uf = Double.fromBits(ui.toLong())
    absx = uf
    w = (ui shr 32).toUInt()
    // |x| < log(DBL_MAX)
    if (w < 0x40862e42u) {
        t = expm1(absx)
        if (w < 0x3ff00000u) {
            if (w < 0x3ff00000u - (26u shl 20)) {
                // note: inexact and underflow are raised by expm1
                // note: this branch avoids spurious underflow
                return x
            }
            return h * (2.0 * t - t * t / (t + 1.0))
        }
        // note: |x|>log(0x1p26)+eps could be just h*exp(x)
        return h * (t + t / (t + 1.0))
    }
    // |x| > log(DBL_MAX) or nan
    // note: the result is stored to handle overflow
    t = 2.0 * h * expo2(absx)
    return t
}
