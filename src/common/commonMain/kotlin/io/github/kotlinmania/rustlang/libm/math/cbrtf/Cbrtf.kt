// port-lint: source math/cbrtf.rs
package io.github.kotlinmania.rustlang.libm.math.cbrtf

// origin: FreeBSD /usr/src/lib/msun/src/sCbrtf.c
//
// Conversion to float by Ian Lance Taylor, Cygnus Support, ian@cygnus.com.
// Debugged and optimized by Bruce D. Evans.
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
// cbrtf(x)
// Return cube root of x
//
public val B1: UInt = 709958130u

// B1 = (127-127.0/3-0.03306235651)*2**23
public val B2: UInt = 642849266u

// B2 = (127-127.0/3-24/3-0.03306235651)*2**23

/**
 * Cube root (f32)
 *
 * Computes the cube root of the argument.
 */
public fun cbrtf(x: kotlin.Float): kotlin.Float {
    val x1p24 = kotlin.Float.fromBits(0x4b800000u.toInt())
    // 0x1p24f === 2 ^ 24
    var r: Double
    var t: Double
    var ui: UInt = x.toRawBits().toUInt()
    var hx: UInt = ui and 0x7fffffffu
    if (hx >= 0x7f800000u) {
        // cbrt(NaN,INF) is itself
        return x + x
    }
    // rough cbrt to 5 bits
    if (hx < 0x00800000u) {
        // zero or subnormal?
        if (hx == 0u) {
            return x
            // cbrt(+-0) is itself
        }
        ui = (x * x1p24).toRawBits().toUInt()
        hx = ui and 0x7fffffffu
        hx = hx / 3u + B2
    } else {
        hx = hx / 3u + B1
    }
    ui = ui and 0x80000000u
    ui = ui or hx
    //
    // First step Newton iteration (solving t*t-x/t == 0) to 16 bits.  In
    // double precision so that its terms can be arranged for efficiency
    // without causing overflow or underflow.
    //
    t = kotlin.Float.fromBits(ui.toInt()).toDouble()
    r = t * t * t
    t = t * (x.toDouble() + x.toDouble() + r) / (x.toDouble() + r + r)
    //
    // Second step Newton iteration to 47 bits.  In double precision for
    // efficiency and accuracy.
    //
    r = t * t * t
    t = t * (x.toDouble() + x.toDouble() + r) / (x.toDouble() + r + r)
    // rounding to 24 bits is perfect in round-to-nearest mode
    return t.toFloat()
}
