// port-lint: source math/exp10.rs
package io.github.kotlinmania.rustlang.libm.math.exp10

import io.github.kotlinmania.rustlang.math.arch.i586.expall.x87Exp10
import io.github.kotlinmania.rustlang.math.exp2.exp2
import io.github.kotlinmania.rustlang.math.modf.modf
import io.github.kotlinmania.rustlang.math.pow.pow

public val LN10: Double = 3.32192809488736234787031942948939

public val P10: List<Double> = listOf(1e-15, 1e-14, 1e-13, 1e-12, 1e-11, 1e-10, 1e-9, 1e-8, 1e-7, 1e-6, 1e-5, 1e-4, 1e-3, 1e-2, 1e-1, 1e0, 1e1, 1e2, 1e3, 1e4, 1e5, 1e6, 1e7, 1e8, 1e9, 1e10, 1e11, 1e12, 1e13, 1e14, 1e15)

/**
 * Calculates 10 raised to the power of `x` (f64).
 */
public fun exp10(x: Double): Double {
    if (true) {
        return x87Exp10(x)
    }
    var (y, n) = modf(x)
    val u: ULong = n.toBits()
    // fabs(n) < 16 without raising invalid on nan
    if (((u shr 52) and 0x7ffuL) < 0x3ffuL + 4uL) {
        if (y == 0.0) {
            return P10.getUnchecked(((n) + 15))
        }
        y = exp2(LN10 * y)
        return y * P10.getUnchecked(((n) + 15))
    }
    return pow(10.0, x)
}
