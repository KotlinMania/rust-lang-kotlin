// port-lint: source math/exp.rs
package io.github.kotlinmania.rustlang.libm.math.exp

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.libm.math.arch.x87Exp
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbn

public val HALF: DoubleArray = doubleArrayOf(0.5, -0.5)

public val LN2HI: Double = 6.93147180369123816490e-01

// 0x3fe62e42, 0xfee00000
public val LN2LO: Double = 1.90821492927058770002e-10

// 0x3dea39ef, 0x35793c76
public val INVLN2: Double = 1.44269504088896338700e00

// 0x3ff71547, 0x652b82fe
public val P1: Double = 1.66666666666666019037e-01

// 0x3FC55555, 0x5555553E
public val P2: Double = -2.77777777770155933842e-03

// 0xBF66C16C, 0x16BEBD93
public val P3: Double = 6.61375632143793436117e-05

// 0x3F11566A, 0xAF25DE2C
public val P4: Double = -1.65339022054652515390e-06

// 0xBEBBBD41, 0xC5D26BF1
public val P5: Double = 4.13813679705723846039e-08

// 0x3E663769, 0x72BEA4D0

/**
 * Exponential, base *e* (f64)
 *
 * Calculate the exponential of `x`, that is, *e* raised to the power `x`
 * (where *e* is the base of the natural system of logarithms, approximately 2.71828).
 */
public fun exp(x: Double): Double {
    var x1 = x
    if (true) {
        return x87Exp(x1)
    }
    val x1p1023 = Double.fromBits(0x7fe0000000000000uL.toLong())
    // 0x1p1023 === 2 ^ 1023
    val x1p149 = Double.fromBits(0x36a0000000000000uL.toLong())
    // 0x1p-149 === 2 ^ -149
    val hi: Double
    val lo: Double
    val c: Double
    val xx: Double
    val y: Double
    val k: Int
    val sign: Int
    var hx: UInt
    hx = (x1.toRawBits().toULong() shr 32).toUInt()
    sign = (hx shr 31).toInt()
    hx = hx and 0x7fffffffu
    // high word of |x|
    // special cases
    if (hx >= 0x4086232bu) {
        // if |x| >= 708.39...
        if (x1.isNaN()) {
            return x1
        }
        if (x1 > 709.782712893383973096) {
            // overflow if x!=inf
            x1 *= x1p1023
            return x1
        }
        if (x1 < -708.39641853226410622) {
            readVolatile((-x1p149 / x1).toFloat(), typeLayoutT = PrimitiveLayouts.F32)
            if (x1 < -745.13321910194110842) {
                return 0.0
            }
        }
    }
    // argument reduction
    if (hx > 0x3fd62e42u) {
        // if |x| > 0.5 ln2
        if (hx >= 0x3ff0a2b2u) {
            // if |x| >= 1.5 ln2
            k = (INVLN2 * x1 + HALF.getUnchecked(sign)).toInt()
        } else {
            k = 1 - sign - sign
        }
        hi = x1 - k.toDouble() * LN2HI
        // k*ln2hi is exact here
        lo = k.toDouble() * LN2LO
        x1 = hi - lo
    } else if (hx > 0x3e300000u) {
        // if |x| > 2**-28
        k = 0
        hi = x1
        lo = 0.0
    } else {
        readVolatile(x1p1023 + x1)
        return 1.0 + x1
    }
    // x is now in primary range
    xx = x1 * x1
    c = x1 - xx * (P1 + xx * (P2 + xx * (P3 + xx * (P4 + xx * P5))))
    y = 1.0 + (x1 * c / (2.0 - c) - lo + hi)
    return if (k == 0) {
        y
    } else {
        scalbn(y, k)
    }
}
