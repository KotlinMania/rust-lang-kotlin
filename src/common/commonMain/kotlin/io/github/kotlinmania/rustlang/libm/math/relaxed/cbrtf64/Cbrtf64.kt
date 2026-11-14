// port-lint: source math/relaxed/cbrtf64.rs
package io.github.kotlinmania.rustlang.libm.math.relaxed.cbrtf64

public val B1: UInt = 715094163u

// B1 = (1023-1023/3-0.03306235651)*2**20
public val B2: UInt = 696219795u

// B2 = (1023-1023/3-54/3-0.03306235651)*2**20
// |1/cbrt(x) - p(x)| < 2**-23.5 (~[-7.93e-8, 7.929e-8]).
public val P0: Double = 1.87595182427177009643

// 0x3ffe03e6, 0x0f61e692
public val P1: Double = -1.88497979543377169875

// 0xbffe28e0, 0x92f02420
public val P2: Double = 1.621429720105354466140

// 0x3ff9f160, 0x4a49d6c2
public val P3: Double = -0.758397934778766047437

// 0xbfe844cb, 0xbee751d9
public val P4: Double = 0.145996192886612446982

// 0x3fc2b000, 0xd4e4edd7

/**
 * Cube root (f64)
 *
 * Computes the cube root of the argument.
 */
public fun cbrtf64(x: Double): Double {
    val x1p54 = Double.fromBits(0x4350000000000000uL.toLong())
    // 0x1p54 === 2 ^ 54
    var ui: ULong = x.toRawBits().toULong()
    var r: Double
    val s: Double
    var t: Double
    val w: Double
    var hx: UInt = (ui shr 32).toUInt() and 0x7fffffffu
    if (hx >= 0x7ff00000u) {
        // cbrt(NaN,INF) is itself
        return x + x
    }
    //
    // Rough cbrt to 5 bits:
    // cbrt(2**e*(1+m) ~= 2**(e/3)*(1+(e%3+m)/3)
    // where e is integral and >= 0, m is real and in [0, 1), and "/" and
    // "%" are integer division and modulus with rounding towards minus
    // infinity.  The RHS is always >= the LHS and has a maximum relative
    // error of about 1 in 16.  Adding a bias of -0.03306235651 to the
    // (e%3+m)/3 term reduces the error to about 1 in 32. With the IEEE
    // floating point representation, for finite positive normal values,
    // ordinary integer divison of the value in bits magically gives
    // almost exactly the RHS of the above provided we first subtract the
    // exponent bias (1023 for doubles) and later add it back.  We do the
    // subtraction virtually to keep e >= 0 so that ordinary integer
    // division rounds towards minus infinity; this is also efficient.
    //
    if (hx < 0x00100000u) {
        // zero or subnormal?
        ui = (x * x1p54).toRawBits().toULong()
        hx = (ui shr 32).toUInt() and 0x7fffffffu
        if (hx == 0u) {
            return x
            // cbrt(0) is itself
        }
        hx = hx / 3u + B2
    } else {
        hx = hx / 3u + B1
    }
    ui = ui and (1uL shl 63)
    ui = ui or ((hx.toULong()) shl 32)
    t = Double.fromBits(ui.toLong())
    //
    // New cbrt to 23 bits:
    // cbrt(x) = t*cbrt(x/t**3) ~= t*P(t**3/x)
    // where P(r) is a polynomial of degree 4 that approximates 1/cbrt(r)
    // to within 2**-23.5 when |r - 1| < 1/10.  The rough approximation
    // has produced t such than |t/cbrt(x) - 1| ~< 1/32, and cubing this
    // gives us bounds for r = t**3/x.
    //
    // Try to optimize for parallel evaluation as in __tanf.c.
    //
    r = (t * t) * (t / x)
    t = t * ((P0 + r * (P1 + r * P2)) + ((r * r) * r) * (P3 + r * P4))
    //
    // Round t away from zero to 23 bits (sloppily except for ensuring that
    // the result is larger in magnitude than cbrt(x) but not much more than
    // 2 23-bit ulps larger).  With rounding towards zero, the error bound
    // would be ~5/6 instead of ~4/6.  With a maximum error of 2 23-bit ulps
    // in the rounded t, the infinite-precision error in the Newton
    // approximation barely affects third digit in the final error
    // 0.667; the error in the rounded t can be up to about 3 23-bit ulps
    // before the final error is larger than 0.667 ulps.
    //
    ui = t.toRawBits().toULong()
    ui = (ui + 0x80000000uL) and 0xffffffffc0000000uL
    t = Double.fromBits(ui.toLong())
    // one step Newton iteration to 53 bits with error < 0.667 ulps
    s = t * t
    // t*t is exact
    r = x / s
    // error <= 0.5 ulps; |r| < |t|
    w = t + t
    // t+t is exact
    r = (r - t) / (w + r)
    // r-t is exact; w+r ~= 3*t
    t = t + t * r
    // error <= 0.5 + 0.5/3 + epsilon
    return t
}
