// port-lint: source math/log2.rs
package io.github.kotlinmania.rustlang.libm.math.log2

// origin: FreeBSD /usr/src/lib/msun/src/eLog2.c
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
//
// Return the base 2 logarithm of x.  See log.c for most comments.
//
// Reduce x to 2^k (1+f) and calculate r = log(1+f) - f + f*f/2
// as in log.c, then combine and scale in extra precision:
// log2(x) = (f - f*f/2 + r)/log(2) + k
//
public val IVLN2HI: Double = 1.44269504072144627571e00

// 0x3ff71547, 0x65200000
public val IVLN2LO: Double = 1.67517131648865118353e-10

// 0x3de705fc, 0x2eefa200
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
 * The base 2 logarithm of `x` (f64).
 */
public fun log2(x: Double): Double {
    var x1 = x
    val x1p54 = Double.fromBits(0x4350000000000000uL.toLong())
    // 0x1p54 === 2 ^ 54
    var ui: ULong = x1.toRawBits().toULong()
    val hfsq: Double
    val f: Double
    val s: Double
    val z: Double
    val r: Double
    var w: Double
    val t1: Double
    val t2: Double
    val y: Double
    var hi: Double
    val lo: Double
    var valHi: Double
    var valLo: Double
    var hx: UInt
    var k: Int
    hx = (ui shr 32).toUInt()
    k = 0
    if (hx < 0x00100000u || (hx shr 31) > 0u) {
        if (ui shl 1 == 0uL) {
            return -1.0 / (x1 * x1)
            // log(+-0)=-inf
        }
        if ((hx shr 31) > 0u) {
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
    k += (hx shr 20).toInt() - 0x3ff
    hx = (hx and 0x000fffffu) + 0x3fe6a09eu
    ui = ((hx.toULong()) shl 32) or (ui and 0xffffffffuL)
    x1 = Double.fromBits(ui.toLong())
    f = x1 - 1.0
    hfsq = 0.5 * f * f
    s = f / (2.0 + f)
    z = s * s
    w = z * z
    t1 = w * (LG2 + w * (LG4 + w * LG6))
    t2 = z * (LG1 + w * (LG3 + w * (LG5 + w * LG7)))
    r = t2 + t1
    // hi+lo = f - hfsq + s*(hfsq+R) ~ log(1+f)
    hi = f - hfsq
    ui = hi.toRawBits().toULong()
    ui = ui and (((-1L).toULong()) shl 32)
    hi = Double.fromBits(ui.toLong())
    lo = f - hi - hfsq + s * (hfsq + r)
    valHi = hi * IVLN2HI
    valLo = (lo + hi) * IVLN2LO + lo * IVLN2HI
    // spadd(valHi, valLo, y), except for not using doubleT:
    y = k.into()
    w = y + valHi
    valLo += (y - w) + valHi
    valHi = w
    return valLo + valHi
}
