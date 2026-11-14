// port-lint: source math/logf.rs
package io.github.kotlinmania.rustlang.libm.math.logf

// origin: FreeBSD /usr/src/lib/msun/src/eLogf.c
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
// |(log(1+s)-log(1-s))/s - Lg(s)| < 2**-34.24 (~[-4.95e-11, 4.97e-11]).
public val LG1: kotlin.Float = 0.66666662693f

// 0xaaaaaa.0p-24
public val LG2: kotlin.Float = 0.40000972152f

// 0xccce13.0p-25
public val LG3: kotlin.Float = 0.28498786688f

// 0x91e9ee.0p-25
public val LG4: kotlin.Float = 0.24279078841f

// 0xf89e26.0p-26

/**
 * The natural logarithm of `x` (f32).
 */
public fun logf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val x1p25 = kotlin.Float.fromBits(0x4c000000u.toInt())
    // 0x1p25f === 2 ^ 25
    var ix = x1.toRawBits().toUInt()
    var k = 0
    if ((ix < 0x00800000u) || ((ix shr 31) != 0u)) {
        // x < 2**-126
        if (ix shl 1 == 0u) {
            return -1.0f / (x1 * x1)
            // log(+-0)=-inf
        }
        if ((ix shr 31) != 0u) {
            return (x1 - x1) / 0.0f
            // log(-#) = NaN
        }
        // subnormal number, scale up x
        k -= 25
        x1 *= x1p25
        ix = x1.toRawBits().toUInt()
    } else if (ix >= 0x7f800000u) {
        return x1
    } else if (ix == 0x3f800000u) {
        return 0.0f
    }
    // reduce x into [sqrt(2)/2, sqrt(2)]
    ix += 0x3f800000u - 0x3f3504f3u
    k += ((ix shr 23).toInt()) - 0x7f
    ix = (ix and 0x007fffffu) + 0x3f3504f3u
    x1 = kotlin.Float.fromBits(ix.toInt())
    val f = x1 - 1.0f
    val s = f / (2.0f + f)
    val z = s * s
    val w = z * z
    val t1 = w * (LG2 + w * LG4)
    val t2 = z * (LG1 + w * LG3)
    val r = t2 + t1
    val hfsq = 0.5f * f * f
    val dk = k.toFloat()
    return s * (hfsq + r) + dk * LN2_LO - hfsq + f + dk * LN2_HI
}
