// port-lint: source math/log1pf.rs
package io.github.kotlinmania.rustlang.libm.math.log1pf

// origin: FreeBSD /usr/src/lib/msun/src/sLog1pf.c
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
 * The natural logarithm of 1+`x` (f32).
 */
public fun log1pf(x: kotlin.Float): kotlin.Float {
    var ui: UInt = x.toRawBits().toUInt()
    val hfsq: kotlin.Float
    var f: kotlin.Float = 0.0f
    var c: kotlin.Float = 0.0f
    val s: kotlin.Float
    val z: kotlin.Float
    val r: kotlin.Float
    val w: kotlin.Float
    val t1: kotlin.Float
    val t2: kotlin.Float
    val dk: kotlin.Float
    val ix: UInt
    var iu: UInt
    var k: Int
    ix = ui
    k = 1
    if (ix < 0x3ed413d0u || (ix shr 31) > 0u) {
        // 1+x < sqrt(2)+
        if (ix >= 0xbf800000u) {
            // x <= -1.0
            if (x == -1.0f) {
                return x / 0.0f
                // log1p(-1)=+inf
            }
            return (x - x) / 0.0f
            // log1p(x<-1)=NaN
        }
        if (ix shl 1 < 0x33800000u shl 1) {
            // |x| < 2**-24
            // underflow if subnormal
            if ((ix and 0x7f800000u) == 0u) {
                readVolatile(x * x)
            }
            return x
        }
        if (ix <= 0xbe95f619u) {
            // sqrt(2)/2- <= 1+x < sqrt(2)+
            k = 0
            c = 0.0f
            f = x
        }
    } else if (ix >= 0x7f800000u) {
        return x
    }
    if (k > 0) {
        ui = (1.0f + x).toRawBits().toUInt()
        iu = ui
        iu += 0x3f800000u - 0x3f3504f3u
        k = (iu shr 23).toInt() - 0x7f
        // correction term ~ log(1+x)-log(u), avoid underflow in c/u
        if (k < 25) {
            c = if (k >= 2) {
                1.0f - (kotlin.Float.fromBits(ui.toInt()) - x)
            } else {
                x - (kotlin.Float.fromBits(ui.toInt()) - 1.0f)
            }
            c /= kotlin.Float.fromBits(ui.toInt())
        } else {
            c = 0.0f
        }
        // reduce u into [sqrt(2)/2, sqrt(2)]
        iu = (iu and 0x007fffffu) + 0x3f3504f3u
        ui = iu
        f = kotlin.Float.fromBits(ui.toInt()) - 1.0f
    }
    s = f / (2.0f + f)
    z = s * s
    w = z * z
    t1 = w * (LG2 + w * LG4)
    t2 = z * (LG1 + w * LG3)
    r = t2 + t1
    hfsq = 0.5f * f * f
    dk = k.toFloat()
    return s * (hfsq + r) + (dk * LN2_LO + c) - hfsq + f + dk * LN2_HI
}
