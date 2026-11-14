// port-lint: source math/log1p.rs
package io.github.kotlinmania.rustlang.libm.math.log1p

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts

// origin: FreeBSD /usr/src/lib/msun/src/sLog1p.c
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
// double log1p(double x)
// Return the natural logarithm of 1+x.
//
// Method :
// 1. Argument Reduction: find k and f such that
// 1+x = 2^k * (1+f),
// where  sqrt(2)/2 < 1+f < sqrt(2) .
//
// Note. If k=0, then f=x is exact. However, if k!=0, then f
// may not be representable exactly. In that case, a correction
// term is need. Let u=1+x rounded. Let c = (1+x)-u, then
// log(1+x) - log(u) ~ c/u. Thus, we proceed to compute log(u),
// and add back the correction term c/u.
// (Note: when x > 2**53, one can simply return log(x))
//
// 2. Approximation of log(1+f): See log.c
//
// 3. Finally, log1p(x) = k*ln2 + log(1+f) + c/u. See log.c
//
// Special cases:
// log1p(x) is NaN with signal if x < -1 (including -INF) ;
// log1p(+INF) is +INF; log1p(-1) is -INF with signal;
// log1p(NaN) is that NaN with no signal.
//
// Accuracy:
// according to an error analysis, the error is always less than
// 1 ulp (unit in the last place).
//
// Constants:
// The hexadecimal values are the intended ones for the following
// constants. The decimal values may be used, provided that the
// compiler will convert from decimal to binary accurately enough
// to produce the hexadecimal values shown.
//
// Note: Assuming log() return accurate answer, the following
// algorithm can be used to compute log1p(x) to within a few ULP:
//
// u = 1+x;
// if(u==1.0) return x ; else
// return log(u)*(x/(u-1.0));
//
// See HP-15C Advanced Functions Handbook, p.193.
//
public val LN2_HI: Double = 6.93147180369123816490e-01

// 3fe62e42 fee00000
public val LN2_LO: Double = 1.90821492927058770002e-10

// 3dea39ef 35793c76
public val LG1: Double = 6.666666666666735130e-01

// 3FE55555 55555593
public val LG2: Double = 3.999999999940941908e-01

// 3FD99999 9997FA04
public val LG3: Double = 2.857142874366239149e-01

// 3FD24924 94229359
public val LG4: Double = 2.222219843214978396e-01

// 3FCC71C5 1D8E78AF
public val LG5: Double = 1.818357216161805012e-01

// 3FC74664 96CB03DE
public val LG6: Double = 1.531383769920937332e-01

// 3FC39A09 D078C69F
public val LG7: Double = 1.479819860511658591e-01

// 3FC2F112 DF3E5244

/**
 * The natural logarithm of 1+`x` (f64).
 */
public fun log1p(x: Double): Double {
    var ui: ULong = x.toRawBits().toULong()
    val hfsq: Double
    var f: Double = 0.0
    var c: Double = 0.0
    val s: Double
    val z: Double
    val r: Double
    val w: Double
    val t1: Double
    val t2: Double
    val dk: Double
    val hx: UInt
    var hu: UInt
    var k: Int
    hx = (ui shr 32).toUInt()
    k = 1
    if (hx < 0x3fda827au || (hx shr 31) > 0u) {
        // 1+x < sqrt(2)+
        if (hx >= 0xbff00000u) {
            // x <= -1.0
            if (x == -1.0) {
                return x / 0.0
                // log1p(-1) = -inf
            }
            return (x - x) / 0.0
            // log1p(x<-1) = NaN
        }
        if (hx shl 1 < 0x3ca00000u shl 1) {
            // |x| < 2**-53
            // underflow if subnormal
            if ((hx and 0x7ff00000u) == 0u) {
                readVolatile(x.toFloat(), typeLayoutT = PrimitiveLayouts.F32)
            }
            return x
        }
        if (hx <= 0xbfd2bec4u) {
            // sqrt(2)/2- <= 1+x < sqrt(2)+
            k = 0
            c = 0.0
            f = x
        }
    } else if (hx >= 0x7ff00000u) {
        return x
    }
    if (k > 0) {
        ui = (1.0 + x).toRawBits().toULong()
        hu = (ui shr 32).toUInt()
        hu += 0x3ff00000u - 0x3fe6a09eu
        k = (hu shr 20).toInt() - 0x3ff
        // correction term ~ log(1+x)-log(u), avoid underflow in c/u
        if (k < 54) {
            c = if (k >= 2) {
                1.0 - (Double.fromBits(ui.toLong()) - x)
            } else {
                x - (Double.fromBits(ui.toLong()) - 1.0)
            }
            c /= Double.fromBits(ui.toLong())
        } else {
            c = 0.0
        }
        // reduce u into [sqrt(2)/2, sqrt(2)]
        hu = (hu and 0x000fffffu) + 0x3fe6a09eu
        ui = ((hu.toULong()) shl 32) or (ui and 0xffffffffuL)
        f = Double.fromBits(ui.toLong()) - 1.0
    }
    hfsq = 0.5 * f * f
    s = f / (2.0 + f)
    z = s * s
    w = z * z
    t1 = w * (LG2 + w * (LG4 + w * LG6))
    t2 = z * (LG1 + w * (LG3 + w * (LG5 + w * LG7)))
    r = t2 + t1
    dk = k.toDouble()
    return s * (hfsq + r) + (dk * LN2_LO + c) - hfsq + f + dk * LN2_HI
}
