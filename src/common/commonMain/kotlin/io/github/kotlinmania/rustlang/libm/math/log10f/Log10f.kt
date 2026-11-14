// port-lint: source math/log10f.rs
package io.github.kotlinmania.rustlang.libm.math.log10f

// origin: FreeBSD /usr/src/lib/msun/src/eLog10f.c
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
//
// See comments in log10.c.
//
public val IVLN10HI: kotlin.Float = 4.3432617188e-01f

// 0x3ede6000
public val IVLN10LO: kotlin.Float = -3.1689971365e-05f

// 0xb804ead9
public val LOG10_2HI: kotlin.Float = 3.0102920532e-01f

// 0x3e9a2080
public val LOG10_2LO: kotlin.Float = 7.9034151668e-07f

// 0x355427db
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
 * The base 10 logarithm of `x` (f32).
 */
public fun log10f(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val x1p25f = kotlin.Float.fromBits(0x4c000000u.toInt())
    // 0x1p25f === 2 ^ 25
    var ui: UInt = x1.toRawBits().toUInt()
    val hfsq: kotlin.Float
    val f: kotlin.Float
    val s: kotlin.Float
    val z: kotlin.Float
    val r: kotlin.Float
    val w: kotlin.Float
    val t1: kotlin.Float
    val t2: kotlin.Float
    val dk: kotlin.Float
    var hi: kotlin.Float
    val lo: kotlin.Float
    var ix: UInt
    var k: Int
    ix = ui
    k = 0
    if (ix < 0x00800000u || (ix shr 31) > 0u) {
        // x < 2**-126
        if (ix shl 1 == 0u) {
            return -1.0f / (x1 * x1)
            // log(+-0)=-inf
        }
        if ((ix shr 31) > 0u) {
            return (x1 - x1) / 0.0f
            // log(-#) = NaN
        }
        // subnormal number, scale up x
        k -= 25
        x1 *= x1p25f
        ui = x1.toRawBits().toUInt()
        ix = ui
    } else if (ix >= 0x7f800000u) {
        return x1
    } else if (ix == 0x3f800000u) {
        return 0.0f
    }
    // reduce x into [sqrt(2)/2, sqrt(2)]
    ix += 0x3f800000u - 0x3f3504f3u
    k += (ix shr 23).toInt() - 0x7f
    ix = (ix and 0x007fffffu) + 0x3f3504f3u
    ui = ix
    x1 = kotlin.Float.fromBits(ui.toInt())
    f = x1 - 1.0f
    s = f / (2.0f + f)
    z = s * s
    w = z * z
    t1 = w * (LG2 + w * LG4)
    t2 = z * (LG1 + w * LG3)
    r = t2 + t1
    hfsq = 0.5f * f * f
    hi = f - hfsq
    ui = hi.toRawBits().toUInt()
    ui = ui and 0xfffff000u
    hi = kotlin.Float.fromBits(ui.toInt())
    lo = f - hi - hfsq + s * (hfsq + r)
    dk = k.toFloat()
    return dk * LOG10_2LO + (lo + hi) * IVLN10LO + lo * IVLN10HI + hi * IVLN10HI + dk * LOG10_2HI
}
