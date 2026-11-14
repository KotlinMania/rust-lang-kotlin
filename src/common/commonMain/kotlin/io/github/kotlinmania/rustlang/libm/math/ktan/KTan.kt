// port-lint: source math/k_tan.rs
package io.github.kotlinmania.rustlang.libm.math.ktan

// origin: FreeBSD /usr/src/lib/msun/src/kTan.c * /
//
// ====================================================
// Copyright 2004 Sun Microsystems, Inc.  All Rights Reserved.
//
// Permission to use, copy, modify, and distribute this
// software is freely granted, provided that this notice
// is preserved.
// ====================================================
// kernel tan function on ~[-pi/4, pi/4] (except on -0), pi/4 ~ 0.7854
// Input x is assumed to be bounded by ~pi/4 in magnitude.
// Input y is the tail of x.
// Input odd indicates whether tan (if odd = 0) or -1/tan (if odd = 1) is returned.
//
// Algorithm
// 1. Since tan(-x) = -tan(x), we need only to consider positive x.
// 2. Callers must return tan(-0) = -0 without calling here since our
// odd polynomial is not evaluated in a way that preserves -0.
// Callers may do the optimization tan(x) ~ x for tiny x.
// 3. tan(x) is approximated by a odd polynomial of degree 27 on
// [0,0.67434]
// 3             27
// tan(x) ~ x + T1*x + ... + T13*x
// where
//
// |tan(x)         2     4            26   |     -59.2
// |----- - (1+T1*x +T2*x +.... +T13*x    )| <= 2
// |  x                                    |
//
// Note: tan(x+y) = tan(x) + tan'(x)*y
// ~ tan(x) + (1+x*x)*y
// Therefore, for better accuracy in computing tan(x+y), let
// 3      2      2       2       2
// r = x *(T2+x *(T3+x *(...+x *(T12+x *T13))))
// then
// 3    2
// tan(x+y) = x + (T1*x + (x *(r+y)+y))
//
// 4. For x in [0.67434,pi/4],  let y = pi/4 - x, then
// tan(x) = tan(pi/4-y) = (1-tan(y))/(1+tan(y))
// = 1 - 2*(tan(y) - (tan(y)^2)/(1+tan(y)))
public var T: DoubleArray = doubleArrayOf(3.33333333333334091986e-01, 1.33333333333201242699e-01, 5.39682539762260521377e-02, 2.18694882948595424599e-02, 8.86323982359930005737e-03, 3.59207910759131235356e-03, 1.45620945432529025516e-03, 5.88041240820264096874e-04, 2.46463134818469906812e-04, 7.81794442939557092300e-05, 7.14072491382608190305e-05, -1.85586374855275456654e-05, 2.59073051863633712884e-05)

public val PIO4: Double = 7.85398163397448278999e-01

// 3FE921FB, 54442D18
public val PIO4_LO: Double = 3.06161699786838301793e-17

// 3C81A626, 33145C07
internal fun kTan(x: Double, y: Double, odd: Int): Double {
    var x1 = x
    var y1 = y
    val hx = (x1.toRawBits().toULong() shr 32).toUInt()
    val big = (hx and 0x7fffffffu) >= 0x3FE59428u
    // |x| >= 0.6744
    if (big) {
        val sign = hx shr 31
        if (sign != 0u) {
            x1 = -x1
            y1 = -y1
        }
        x1 = (PIO4 - x1) + (PIO4_LO - y1)
        y1 = 0.0
    }
    val z = x1 * x1
    val w = z * z
    //
    // Break x^5*(T[1]+x^2*T[2]+...) into
    // x^5(T[1]+x^4*T[3]+...+x^20*T[11]) +
    // x^5(x^2*(T[2]+x^4*T[4]+...+x^22*[T12]))
    //
    val r = T[1] + w * (T[3] + w * (T[5] + w * (T[7] + w * (T[9] + w * T[11]))))
    val v = z * (T[2] + w * (T[4] + w * (T[6] + w * (T[8] + w * (T[10] + w * T[12])))))
    val s = z * x1
    val r1 = y1 + z * (s * (r + v) + y1) + s * T[0]
    val w1 = x1 + r1
    if (big) {
        val sign1 = hx shr 31
        val s1 = 1.0 - 2.0 * odd.toDouble()
        val v1 = s1 - 2.0 * (x1 + (r1 - w1 * w1 / (w1 + s1)))
        return if (sign1 != 0u) {
            -v1
        } else {
            v1
        }
    }
    if (odd == 0) {
        return w1
    }
    // -1.0/(x+r) has up to 2ulp error, so compute it accurately
    val w0 = zeroLowWord(w1)
    val v2 = r1 - (w0 - x1)
    // w0+v = r+x
    val a = -1.0 / w1
    val a0 = zeroLowWord(a)
    return a0 + a * (1.0 + a0 * w0 + a0 * v2)
}

public fun zeroLowWord(x: Double): Double = Double.fromBits((x.toRawBits().toULong() and 0xFFFFFFFF00000000uL).toLong())
