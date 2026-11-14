// port-lint: source math/log.rs
package io.github.kotlinmania.rustlang.libm.math.log

// origin: FreeBSD /usr/src/lib/msun/src/eLog.c
//
// ====================================================
// Copyright (C) 1993 by Sun Microsystems, Inc. All rights reserved.
//
// Developed at SunSoft, a Sun Microsystems, Inc. business.
// Permission to use, copy, modify, and distribute this
// software is freely granted, provided that this notice
// is preserved.
// ====================================================
//
// log(x)
// Return the logarithm of x
//
// Method :
// 1. Argument Reduction: find k and f such that
// x = 2^k * (1+f),
// where  sqrt(2)/2 < 1+f < sqrt(2) .
//
// 2. Approximation of log(1+f).
// Let s = f/(2+f) ; based on log(1+f) = log(1+s) - log(1-s)
// = 2s + 2/3 s**3 + 2/5 s**5 + .....,
// = 2s + s*R
// We use a special Remez algorithm on [0,0.1716] to generate
// a polynomial of degree 14 to approximate R The maximum error
// of this polynomial approximation is bounded by 2**-58.45. In
// other words,
// 2      4      6      8      10      12      14
// R(z) ~ Lg1*s +Lg2*s +Lg3*s +Lg4*s +Lg5*s  +Lg6*s  +Lg7*s
// (the values of Lg1 to Lg7 are listed in the program)
// and
// |      2          14          |     -58.45
// | Lg1*s +...+Lg7*s    -  R(z) | <= 2
// |                             |
// Note that 2s = f - s*f = f - hfsq + s*hfsq, where hfsq = f*f/2.
// In order to guarantee error in log below 1ulp, we compute log
// by
// log(1+f) = f - s*(f - R)        (if f is not too large)
// log(1+f) = f - (hfsq - s*(hfsq+R)).     (better accuracy)
//
// 3. Finally,  log(x) = k*ln2 + log(1+f).
// = k*ln2Hi+(f-(hfsq-(s*(hfsq+R)+k*ln2Lo)))
// Here ln2 is split into two floating point number:
// ln2Hi + ln2Lo,
// where n*ln2Hi is always exact for |n| < 2000.
//
// Special cases:
// log(x) is NaN with signal if x < 0 (including -INF) ;
// log(+INF) is +INF; log(0) is -INF with signal;
// log(NaN) is that NaN with no signal.
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
 * The natural logarithm of `x` (f64).
 */
public fun log(x: Double): Double {
    var x1 = x
    val x1p54 = Double.fromBits(0x4350000000000000uL.toLong())
    // 0x1p54 === 2 ^ 54
    var ui = x1.toRawBits().toULong()
    var hx: UInt = (ui shr 32).toUInt()
    var k: Int = 0
    if ((hx < 0x00100000u) || ((hx shr 31) != 0u)) {
        // x < 2**-126
        if (ui shl 1 == 0uL) {
            return -1.0 / (x1 * x1)
            // log(+-0)=-inf
        }
        if (hx shr 31 != 0u) {
            return (x1 - x1) / 0.0
            // log(-#) = NaN
        }
        // subnormal number, scale x up
        k -= 54
        x1 *= x1p54
        ui = x1.toRawBits().toULong()
        hx = (ui shr 32).toUInt()
    } else if (hx >= 0x7ff00000u) {
        return x1
    } else if (hx == 0x3ff00000u && ui shl 32 == 0uL) {
        return 0.0
    }
    // reduce x into [sqrt(2)/2, sqrt(2)]
    hx += 0x3ff00000u - 0x3fe6a09eu
    k += ((hx shr 20).toInt()) - 0x3ff
    hx = (hx and 0x000fffffu) + 0x3fe6a09eu
    ui = ((hx.toULong()) shl 32) or (ui and 0xffffffffuL)
    x1 = Double.fromBits(ui.toLong())
    val f: Double = x1 - 1.0
    val hfsq: Double = 0.5 * f * f
    val s: Double = f / (2.0 + f)
    val z: Double = s * s
    val w: Double = z * z
    val t1: Double = w * (LG2 + w * (LG4 + w * LG6))
    val t2: Double = z * (LG1 + w * (LG3 + w * (LG5 + w * LG7)))
    val r: Double = t2 + t1
    val dk: Double = k.toDouble()
    return s * (hfsq + r) + dk * LN2_LO - hfsq + f + dk * LN2_HI
}
