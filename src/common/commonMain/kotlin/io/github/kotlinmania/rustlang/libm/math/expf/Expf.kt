// port-lint: source math/expf.rs
package io.github.kotlinmania.rustlang.libm.math.expf

import io.github.kotlinmania.rustlang.libm.math.arch.i586.expall.x87Expf
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbnf

public val HALF: FloatArray = floatArrayOf(0.5f, -0.5f)

public val LN2_HI: kotlin.Float = 6.9314575195e-01f

// 0x3f317200
public val LN2_LO: kotlin.Float = 1.4286067653e-06f

// 0x35bfbe8e
public val INV_LN2: kotlin.Float = 1.4426950216e00f

// 0x3fb8aa3b
//
// Domain [-0.34568, 0.34568], range ~[-4.278e-9, 4.447e-9]:
// |x*(exp(x)+1)/(exp(x)-1) - p(x)| < 2**-27.74
//
public val P1: kotlin.Float = 1.6666625440e-1f

// 0xaaaa8f.0p-26
public val P2: kotlin.Float = -2.7667332906e-3f

// -0xb55215.0p-32

/**
 * Exponential, base *e* (f32)
 *
 * Calculate the exponential of `x`, that is, *e* raised to the power `x`
 * (where *e* is the base of the natural system of logarithms, approximately 2.71828).
 */
public fun expf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    if (true) {
        return x87Expf(x1)
    }
    val x1p127 = kotlin.Float.fromBits(0x7f000000u.toInt())
    // 0x1p127f === 2 ^ 127
    val x1p126 = kotlin.Float.fromBits(0x800000u.toInt())
    // 0x1p-126f === 2 ^ -126  // original 0x1p-149f    ???????????
    var hx = x1.toRawBits().toUInt()
    val sign = (hx shr 31).toInt()
    // sign bit of x
    val signb: Boolean = sign != 0
    hx = hx and 0x7fffffffu
    // high word of |x|
    // special cases
    if (hx >= 0x42aeac50u) {
        // if |x| >= -87.33655f or NaN
        if (hx > 0x7f800000u) {
            // NaN
            return x1
        }
        if ((hx >= 0x42b17218u) && (!signb)) {
            // x >= 88.722839f
            // overflow
            x1 *= x1p127
            return x1
        }
        if (signb) {
            readVolatile(-x1p126 / x1)
            if (hx >= 0x42cff1b5u) {
                // x <= -103.972084f
                return 0.0f
            }
        }
    }
    // argument reduction
    val k: Int
    val hi: kotlin.Float
    val lo: kotlin.Float
    if (hx > 0x3eb17218u) {
        // if |x| > 0.5 ln2
        if (hx > 0x3f851592u) {
            // if |x| > 1.5 ln2
            k = (INV_LN2 * x1 + HALF.getUnchecked(sign)).toInt()
        } else {
            k = 1 - sign - sign
        }
        val kf = k.toFloat()
        hi = x1 - kf * LN2_HI
        // k*ln2hi is exact here
        lo = kf * LN2_LO
        x1 = hi - lo
    } else if (hx > 0x39000000u) {
        // |x| > 2**-14
        k = 0
        hi = x1
        lo = 0.0f
    } else {
        readVolatile(x1p127 + x1)
        return 1.0f + x1
    }
    // x is now in primary range
    val xx = x1 * x1
    val c = x1 - xx * (P1 + xx * P2)
    val y = 1.0f + (x1 * c / (2.0f - c) - lo + hi)
    return if (k == 0) {
        y
    } else {
        scalbnf(y, k)
    }
}
