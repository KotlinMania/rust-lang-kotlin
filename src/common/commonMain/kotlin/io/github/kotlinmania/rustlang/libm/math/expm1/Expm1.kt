// port-lint: source math/expm1.rs
package io.github.kotlinmania.rustlang.libm.math.expm1

// origin: FreeBSD /usr/src/lib/msun/src/sExpm1.c
//
// ====================================================
// Copyright (C) 1993 by Sun Microsystems, Inc. All rights reserved.
//
// Developed at SunPro, a Sun Microsystems, Inc. business.
// Permission to use, copy, modify, and distribute this
// software is freely granted, provided that this notice
// is preserved.
// ====================================================
//
public val O_THRESHOLD: Double = 7.09782712893383973096e02

// 0x40862E42, 0xFEFA39EF
public val LN2_HI: Double = 6.93147180369123816490e-01

// 0x3fe62e42, 0xfee00000
public val LN2_LO: Double = 1.90821492927058770002e-10

// 0x3dea39ef, 0x35793c76
public val INVLN2: Double = 1.44269504088896338700e00

// 0x3ff71547, 0x652b82fe
// Scaled Q's: QnHere = 2**n * QnAbove, for R(2*z) where z = hxs = x*x/2:
public val Q1: Double = -3.33333333333331316428e-02

// BFA11111 111110F4
public val Q2: Double = 1.58730158725481460165e-03

// 3F5A01A0 19FE5585
public val Q3: Double = -7.93650757867487942473e-05

// BF14CE19 9EAADBB7
public val Q4: Double = 4.00821782732936239552e-06

// 3ED0CFCA 86E65239
public val Q5: Double = -2.01099218183624371326e-07

// BE8AFDB7 6E09C32D

/**
 * Exponential, base *e*, of x-1 (f64)
 *
 * Calculates the exponential of `x` and subtract 1, that is, *e* raised
 * to the power `x` minus 1 (where *e* is the base of the natural
 * system of logarithms, approximately 2.71828).
 * The result is accurate even for small values of `x`,
 * where using `exp(x) - 1` would lose many significant digits.
 */
public fun expm1(x: Double): Double {
    var x1 = x
    val hi: Double
    val lo: Double
    val k: Int
    val c: Double
    var t: Double
    var y: Double
    var ui = x1.toRawBits().toULong()
    val hx = ((ui shr 32) and 0x7fffffffuL).toUInt()
    val sign = (ui shr 63).toInt()
    // filter out huge and non-finite argument
    if (hx >= 0x4043687Au) {
        // if |x|>=56*ln2
        if (x1.isNaN()) {
            return x1
        }
        if (sign != 0) {
            return -1.0
        }
        if (x1 > O_THRESHOLD) {
            x1 *= Double.fromBits(0x7fe0000000000000uL.toLong())
            return x1
        }
    }
    // argument reduction
    if (hx > 0x3fd62e42u) {
        // if  |x| > 0.5 ln2
        if (hx < 0x3FF0A2B2u) {
            // and |x| < 1.5 ln2
            if (sign == 0) {
                hi = x1 - LN2_HI
                lo = LN2_LO
                k = 1
            } else {
                hi = x1 + LN2_HI
                lo = -LN2_LO
                k = -1
            }
        } else {
            k = (INVLN2 * x1 +
                if (sign != 0) {
                    -0.5
                } else {
                    0.5
                }).toInt()
            t = k.toDouble()
            hi = x1 - t * LN2_HI
            // t*ln2Hi is exact here
            lo = t * LN2_LO
        }
        x1 = hi - lo
        c = (hi - x1) - lo
    } else if (hx < 0x3c900000u) {
        // |x| < 2**-54, return x
        if (hx < 0x00100000u) {
            readVolatile(x1)
        }
        return x1
    } else {
        c = 0.0
        k = 0
    }
    // x is now in primary range
    val hfx = 0.5 * x1
    val hxs = x1 * hfx
    val r1 = 1.0 + hxs * (Q1 + hxs * (Q2 + hxs * (Q3 + hxs * (Q4 + hxs * Q5))))
    t = 3.0 - r1 * hfx
    var e = hxs * ((r1 - t) / (6.0 - x1 * t))
    if (k == 0) {
        // c is 0
        return x1 - (x1 * e - hxs)
    }
    e = x1 * (e - c) - c
    e -= hxs
    // exp(x) ~ 2^k (xReduced - e + 1)
    if (k == -1) {
        return 0.5 * (x1 - e) - 0.5
    }
    if (k == 1) {
        if (x1 < -0.25) {
            return -2.0 * (e - (x1 + 0.5))
        }
        return 1.0 + 2.0 * (x1 - e)
    }
    ui = ((0x3ff + k).toULong()) shl 52
    // 2^k
    val twopk = Double.fromBits(ui.toLong())
    if (!(0..56).contains(k)) {
        // suffice to return exp(x)-1
        y = x1 - e + 1.0
        if (k == 1024) {
            y = y * 2.0 * Double.fromBits(0x7fe0000000000000uL.toLong())
        } else {
            y = y * twopk
        }
        return y - 1.0
    }
    ui = ((0x3ff - k).toULong()) shl 52
    // 2^-k
    val uf = Double.fromBits(ui.toLong())
    if (k < 20) {
        y = (x1 - e + (1.0 - uf)) * twopk
    } else {
        y = (x1 - (e + uf) + 1.0) * twopk
    }
    return y
}
