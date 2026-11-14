// port-lint: source math/k_tanf.rs
package io.github.kotlinmania.rustlang.libm.math.ktanf

// origin: FreeBSD /usr/src/lib/msun/src/kTan.c
//
// ====================================================
// Copyright 2004 Sun Microsystems, Inc.  All Rights Reserved.
//
// Permission to use, copy, modify, and distribute this
// software is freely granted, provided that this notice
// is preserved.
// ====================================================
//
// |tan(x)/x - t(x)| < 2**-25.5 (~[-2e-08, 2e-08]).
public val T: DoubleArray = doubleArrayOf(0.333331395030791399758, 0.133392002712976742718, 0.0533812378445670393523, 0.0245283181166547278873, 0.00297435743359967304927, 0.00946564784943673166728)

public fun kTanf(x: Double, odd: Boolean): kotlin.Float {
    val z = x * x
    //
    // Split up the polynomial into small independent terms to give
    // opportunities for parallel evaluation.  The chosen splitting is
    // micro-optimized for Athlons (XP, X64).  It costs 2 multiplications
    // relative to Horner's method on sequential machines.
    //
    // We add the small terms from lowest degree up for efficiency on
    // non-sequential machines (the lowest degree terms tend to be ready
    // earlier).  Apart from this, we don't care about order of
    // operations, and don't need to to care since we have precision to
    // spare.  However, the chosen splitting is good for accuracy too,
    // and would give results as accurate as Horner's method if the
    // small terms were added from highest degree down.
    //
    var r = T[4] + z * T[5]
    val t = T[2] + z * T[3]
    val w = z * z
    val s = z * x
    val u = T[0] + z * T[1]
    r = (x + s * u) + (s * w) * (t + w * r)
    return (if (odd) {
        -1.0 / r
    } else {
        r
    }).toFloat()
}
