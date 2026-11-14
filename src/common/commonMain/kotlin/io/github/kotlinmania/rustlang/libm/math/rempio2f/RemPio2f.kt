// port-lint: source math/rem_pio2f.rs
package io.github.kotlinmania.rustlang.libm.math.rempio2f

import io.github.kotlinmania.rustlang.libm.math.rempio2large.remPio2Large

public val TOINT: Double = 1.5 / 2.220446049250313E-16

/**
 * 53 bits of 2/pi
 */
public val INV_PIO2: Double = 6.36619772367581382433e-01

// 0x3FE45F30, 0x6DC9C883

/**
 * first 25 bits of pi/2
 */
public val PIO2_1: Double = 1.57079631090164184570e00

// 0x3FF921FB, 0x50000000

/**
 * pi/2 - pio21
 */
public val PIO2_1T: Double = 1.58932547735281966916e-08

// 0x3E5110b4, 0x611A6263

/**
 * Return the remainder of x rem pi/2 in *y
 *
 * use double precision for everything except passing x
 * use __rem_pio2_large() for large x
 */
internal fun remPio2f(x: kotlin.Float): Pair<Int, Double> {
    val x64 = x.toDouble()
    val tx: DoubleArray  = doubleArrayOf(0.0)
    val ty: DoubleArray  = doubleArrayOf(0.0)
    val ix = x.toRawBits().toUInt() and 0x7fffffffu
    // 25+53 bit pi is good enough for medium size
    if (ix < 0x4dc90fdbu) {
        // |x| ~< 2^28*(pi/2), medium size
        // Use a specialized rint() to get fn.  Assume round-to-nearest.
        val tmp = x64 * INV_PIO2 + TOINT
        // force rounding of tmp to it's storage format on x87 to avoid
        // excess precision issues.
        val tmp1 = readVolatile(tmp)
        val fN = tmp1 - TOINT
        return Pair(fN.toInt(), x64 - fN * PIO2_1 - fN * PIO2_1T)
    }
    if (ix >= 0x7f800000u) {
        // x is inf or NaN
        return Pair(0, x64 - x64)
    }
    // scale x into [2^23, 2^24-1]
    val sign = (x.toRawBits().toUInt() shr 31) != 0u
    val e0 = ((ix shr 23) - (0x7fu + 23u)).toInt()
    // e0 = ilogb(|x|)-23, positive
    tx[0] = kotlin.Float.fromBits((ix - (e0 shl 23).toUInt()).toInt()).toDouble()
    val n = remPio2Large(tx, ty, e0, 0)
    if (sign) {
        return Pair(-n, -ty[0])
    }
    return Pair(n, ty[0])
}
