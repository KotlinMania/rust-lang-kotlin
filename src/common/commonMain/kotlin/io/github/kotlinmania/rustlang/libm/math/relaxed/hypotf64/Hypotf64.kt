// port-lint: source math/relaxed/hypotf64.rs
package io.github.kotlinmania.rustlang.libm.math.relaxed.hypotf64

import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrt

public val SPLIT: Double = 134217728.0 + 1.0

// 0x1p27 + 1 === (2 ^ 27) + 1
public fun sq(x: Double): Pair<Double, Double> {
    val xh: Double
    val xl: Double
    val xc: Double
    xc = x * SPLIT
    xh = x - xc + xc
    xl = x - xh
    val hi = x * x
    val lo = xh * xh - hi + 2.0 * xh * xl + xl * xl
    return Pair(hi, lo)
}

public fun hypotf64(x: Double, y: Double): Double {
    var x1 = x
    var y1 = y
    val x1p700 = Double.fromBits(0x6bb0000000000000uL.toLong())
    // 0x1p700 === 2 ^ 700
    val x1p700 = Double.fromBits(0x1430000000000000uL.toLong())
    // 0x1p-700 === 2 ^ -700
    var uxi = x1.toRawBits().toULong()
    var uyi = y1.toRawBits().toULong()
    val uti: Any?
    val ex: Long
    val ey: Long
    var z: Double
    // arrange |x| >= |y|
    uxi = uxi and ((-1L).toULong() shr 1)
    uyi = uyi and ((-1L).toULong() shr 1)
    if (uxi < uyi) {
        uti = uxi
        uxi = uyi
        uyi = uti
    }
    // special cases
    ex = (uxi shr 52).toLong()
    ey = (uyi shr 52).toLong()
    x1 = Double.fromBits(uxi.toLong())
    y1 = Double.fromBits(uyi.toLong())
    // note: hypot(inf,nan) == inf
    if (ey == 0x7ffL) {
        return y1
    }
    if (ex == 0x7ffL || uyi == 0uL) {
        return x1
    }
    // note: hypot(x,y) ~= x + y*y/x/2 with inexact for small y/x
    // 64 difference is enough for ld80 doubleT
    if (ex - ey > 64L) {
        return x1 + y1
    }
    // precise sqrt argument in nearest rounding mode without overflow
    // xh*xh must not overflow and xl*xl must not underflow in sq
    z = 1.0
    if (ex > 0x3ffL + 510L) {
        z = x1p700
        x1 *= x1p700
        y1 *= x1p700
    } else if (ey < 0x3ffL - 450L) {
        z = x1p700
        x1 *= x1p700
        y1 *= x1p700
    }
    val (hx, lx) = sq(x1)
    val (hy, ly) = sq(y1)
    return z * sqrt(ly + lx + hy + hx)
}
