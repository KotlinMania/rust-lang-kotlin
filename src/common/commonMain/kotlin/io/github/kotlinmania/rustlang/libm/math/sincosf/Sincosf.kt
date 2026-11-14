// port-lint: source math/sincosf.rs
package io.github.kotlinmania.rustlang.libm.math.sincosf

import io.github.kotlinmania.rustlang.libm.math.kcosf.kCosf
import io.github.kotlinmania.rustlang.libm.math.ksinf.kSinf
import io.github.kotlinmania.rustlang.libm.math.rempio2f.remPio2f

// Small multiples of pi/2 rounded to double precision.
public val PI_2: Double = 0.5 * 3.1415926535897931160e00

public val S1PIO2: Double = 1.0 * PI_2

// 0x3FF921FB, 0x54442D18
public val S2PIO2: Double = 2.0 * PI_2

// 0x400921FB, 0x54442D18
public val S3PIO2: Double = 3.0 * PI_2

// 0x4012D97C, 0x7F3321D2
public val S4PIO2: Double = 4.0 * PI_2

// 0x401921FB, 0x54442D18

/**
 * Both the sine and cosine of `x` (f32).
 *
 * `x` is specified in radians and the return value is (sin(x), cos(x)).
 */
public fun sincosf(x: kotlin.Float): Pair<kotlin.Float, kotlin.Float> {
    val s: kotlin.Float
    val c: kotlin.Float
    var ix: UInt
    val sign: Boolean
    ix = x.toRawBits().toUInt()
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    // |x| ~<= pi/4
    if (ix <= 0x3f490fdau) {
        // |x| < 2**-12
        if (ix < 0x39800000u) {
            // raise inexact if x!=0 and underflow if subnormal
            val x1p120 = kotlin.Float.fromBits(0x7b800000u.toInt())
            // 0x1p120 == 2^120
            if (ix < 0x00100000u) {
                readVolatile(x / x1p120)
            } else {
                readVolatile(x + x1p120)
            }
            return Pair(x, 1.0)
        }
        return Pair(kSinf(x.toDouble()), kCosf(x.toDouble()))
    }
    // |x| ~<= 5*pi/4
    if (ix <= 0x407b53d1u) {
        if (ix <= 0x4016cbe3u) {
            // |x| ~<= 3pi/4
            if (sign) {
                s = -kCosf(x.toDouble() + S1PIO2)
                c = kSinf(x.toDouble() + S1PIO2)
            } else {
                s = kCosf(S1PIO2 - x.toDouble())
                c = kSinf(S1PIO2 - x.toDouble())
            }
        } else if (sign) {
            s = -kSinf(x.toDouble() + S2PIO2)
            c = -kCosf(x.toDouble() + S2PIO2)
        } else {
            s = -kSinf(x.toDouble() - S2PIO2)
            c = -kCosf(x.toDouble() - S2PIO2)
        }
        return Pair(s, c)
    }
    // |x| ~<= 9*pi/4
    if (ix <= 0x40e231d5u) {
        if (ix <= 0x40afeddfu) {
            // |x| ~<= 7*pi/4
            if (sign) {
                s = kCosf(x.toDouble() + S3PIO2)
                c = -kSinf(x.toDouble() + S3PIO2)
            } else {
                s = -kCosf(x.toDouble() - S3PIO2)
                c = kSinf(x.toDouble() - S3PIO2)
            }
        } else if (sign) {
            s = kSinf(x.toDouble() + S4PIO2)
            c = kCosf(x.toDouble() + S4PIO2)
        } else {
            s = kSinf(x.toDouble() - S4PIO2)
            c = kCosf(x.toDouble() - S4PIO2)
        }
        return Pair(s, c)
    }
    // sin(Inf or NaN) is NaN
    if (ix >= 0x7f800000u) {
        val rv = x - x
        return Pair(rv, rv)
    }
    // general argument reduction needed
    val (n, y) = remPio2f(x)
    s = kSinf(y)
    c = kCosf(y)
    return when (n and 3) {
        0 -> Pair(s, c)
        1 -> Pair(c, -s)
        2 -> Pair(-s, -c)
        3 -> Pair(-c, s)
        else -> error("unreachable")
    }
}
