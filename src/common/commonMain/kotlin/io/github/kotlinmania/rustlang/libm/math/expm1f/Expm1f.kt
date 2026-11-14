// port-lint: source math/expm1f.rs
package io.github.kotlinmania.rustlang.libm.math.expm1f

// origin: FreeBSD /usr/src/lib/msun/src/sExpm1f.c
//
// Conversion to float by Ian Lance Taylor, Cygnus Support, ian@cygnus.com.
//
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
public val LN2_HI: kotlin.Float = 6.9313812256e-01f

// 0x3f317180
public val LN2_LO: kotlin.Float = 9.0580006145e-06f

// 0x3717f7d1
public val INV_LN2: kotlin.Float = 1.4426950216e00f

// 0x3fb8aa3b
//
// Domain [-0.34568, 0.34568], range ~[-6.694e-10, 6.696e-10]:
// |6 / x * (1 + 2 * (1 / (exp(x) - 1) - 1 / x)) - q(x)| < 2**-30.04
// Scaled coefficients: QnHere = 2**n * QnForQ (see sExpm1.c):
//
public val Q1: kotlin.Float = -3.3333212137e-2f

// -0x888868.0p-28
public val Q2: kotlin.Float = 1.5807170421e-3f

// 0xcf3010.0p-33

/**
 * Exponential, base *e*, of x-1 (f32)
 *
 * Calculates the exponential of `x` and subtract 1, that is, *e* raised
 * to the power `x` minus 1 (where *e* is the base of the natural
 * system of logarithms, approximately 2.71828).
 * The result is accurate even for small values of `x`,
 * where using `exp(x) - 1` would lose many significant digits.
 */
public fun expm1f(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val x1p127 = kotlin.Float.fromBits(0x7f000000u.toInt())
    // 0x1p127f === 2 ^ 127
    var hx = x1.toRawBits().toUInt()
    val sign = (hx shr 31) != 0u
    hx = hx and 0x7fffffffu
    // filter out huge and non-finite argument
    if (hx >= 0x4195b844u) {
        // if |x|>=27*ln2
        if (hx > 0x7f800000u) {
            // NaN
            return x1
        }
        if (sign) {
            return -1.0f
        }
        if (hx > 0x42b17217u) {
            // x > log(FLT_MAX)
            x1 *= x1p127
            return x1
        }
    }
    val k: Int
    val hi: kotlin.Float
    val lo: kotlin.Float
    var c = 0.0f
    // argument reduction
    if (hx > 0x3eb17218u) {
        // if  |x| > 0.5 ln2
        if (hx < 0x3F851592u) {
            // and |x| < 1.5 ln2
            if (!sign) {
                hi = x1 - LN2_HI
                lo = LN2_LO
                k = 1
            } else {
                hi = x1 + LN2_HI
                lo = -LN2_LO
                k = -1
            }
        } else {
            k = (INV_LN2 * x1 +
                (if (sign) {
                    -0.5f
                } else {
                    0.5f
                })).toInt()
            val t = k.toFloat()
            hi = x1 - t * LN2_HI
            // t*ln2Hi is exact here
            lo = t * LN2_LO
        }
        x1 = hi - lo
        c = (hi - x1) - lo
    } else if (hx < 0x33000000u) {
        // when |x|<2**-25, return x
        if (hx < 0x00800000u) {
            readVolatile(x1 * x1)
        }
        return x1
    } else {
        k = 0
    }
    // x is now in primary range
    val hfx = 0.5f * x1
    val hxs = x1 * hfx
    val r1 = 1.0f + hxs * (Q1 + hxs * Q2)
    val t1 = 3.0f - r1 * hfx
    var e = hxs * ((r1 - t1) / (6.0f - x1 * t1))
    if (k == 0) {
        // c is 0
        return x1 - (x1 * e - hxs)
    }
    e = x1 * (e - c) - c
    e -= hxs
    // exp(x) ~ 2^k (xReduced - e + 1)
    if (k == -1) {
        return 0.5f * (x1 - e) - 0.5f
    }
    if (k == 1) {
        if (x1 < -0.25f) {
            return -2.0f * (e - (x1 + 0.5f))
        }
        return 1.0f + 2.0f * (x1 - e)
    }
    val twopk = kotlin.Float.fromBits((((0x7f + k) shl 23).toUInt()).toInt())
    // 2^k
    if (!(0..56).contains(k)) {
        // suffice to return exp(x)-1
        var y = x1 - e + 1.0f
        if (k == 128) {
            y = y * 2.0f * x1p127
        } else {
            y = y * twopk
        }
        return y - 1.0f
    }
    val uf = kotlin.Float.fromBits((((0x7f - k) shl 23).toUInt()).toInt())
    // 2^-k
    return if (k < 23) {
        (x1 - e + (1.0f - uf)) * twopk
    } else {
        (x1 - (e + uf) + 1.0f) * twopk
    }
}
