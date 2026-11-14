// port-lint: source math/k_sinf.rs
package io.github.kotlinmania.rustlang.libm.math.ksinf

// origin: FreeBSD /usr/src/lib/msun/src/kSinf.c
//
// Conversion to float by Ian Lance Taylor, Cygnus Support, ian@cygnus.com.
// Optimized by Bruce D. Evans.
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
// |sin(x)/x - s(x)| < 2**-37.5 (~[-4.89e-12, 4.824e-12]).
public val S1: Double = -0.166666666416265235595

// -0x15555554cbac77.0p-55
public val S2: Double = 0.0083333293858894631756

// 0x111110896efbb2.0p-59
public val S3: Double = -0.000198393348360966317347

// -0x1a00f9e2cae774.0p-65
public val S4: Double = 0.0000027183114939898219064

// 0x16cd878c3b46a7.0p-71
internal fun kSinf(x: Double): kotlin.Float {
    val z = x * x
    val w = z * z
    val r = S3 + z * S4
    val s = z * x
    return ((x + s * (S1 + z * S2)) + s * w * r).toFloat()
}
