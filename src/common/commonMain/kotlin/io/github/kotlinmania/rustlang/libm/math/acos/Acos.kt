// port-lint: source math/acos.rs
package io.github.kotlinmania.rustlang.libm.math.acos

import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrt

public val PIO2_HI: Double = 1.57079632679489655800e00

// 0x3FF921FB, 0x54442D18
public val PIO2_LO: Double = 6.12323399573676603587e-17

// 0x3C91A626, 0x33145C07
public val PS0: Double = 1.66666666666666657415e-01

// 0x3FC55555, 0x55555555
public val PS1: Double = -3.25565818622400915405e-01

// 0xBFD4D612, 0x03EB6F7D
public val PS2: Double = 2.01212532134862925881e-01

// 0x3FC9C155, 0x0E884455
public val PS3: Double = -4.00555345006794114027e-02

// 0xBFA48228, 0xB5688F3B
public val PS4: Double = 7.91534994289814532176e-04

// 0x3F49EFE0, 0x7501B288
public val PS5: Double = 3.47933107596021167570e-05

// 0x3F023DE1, 0x0DFDF709
public val QS1: Double = -2.40339491173441421878e00

// 0xC0033A27, 0x1C8A2D4B
public val QS2: Double = 2.02094576023350569471e00

// 0x40002AE5, 0x9C598AC8
public val QS3: Double = -6.88283971605453293030e-01

// 0xBFE6066C, 0x1B8D0159
public val QS4: Double = 7.70381505559019352791e-02

// 0x3FB3B8C5, 0xB12E9282
public fun r(z: Double): Double {
    val p: Double = z * (PS0 + z * (PS1 + z * (PS2 + z * (PS3 + z * (PS4 + z * PS5)))))
    val q: Double = 1.0 + z * (QS1 + z * (QS2 + z * (QS3 + z * QS4)))
    return p / q
}

/**
 * Arccosine (f64)
 *
 * Computes the inverse cosine (arc cosine) of the input value.
 * Arguments must be in the range -1 to 1.
 * Returns values in radians, in the range of 0 to pi.
 */
public fun acos(x: Double): Double {
    val x1p120f = Double.fromBits(0x3870000000000000uL.toLong())
    // 0x1p-120 === 2 ^ -120
    val z: Double
    val w: Double
    val s: Double
    val c: Double
    val df: Double
    val hx: UInt
    val ix: UInt
    hx = (x.toRawBits().toULong() shr 32).toUInt()
    ix = hx and 0x7fffffffu
    // |x| >= 1 or nan
    if (ix >= 0x3ff00000u) {
        val lx: UInt = x.toRawBits().toULong().toUInt()
        if (((ix - 0x3ff00000u) or lx) == 0u) {
            // acos(1)=0, acos(-1)=pi
            if ((hx shr 31) != 0u) {
                return 2.0 * PIO2_HI + x1p120f
            }
            return 0.0
        }
        return 0.0 / (x - x)
    }
    // |x| < 0.5
    if (ix < 0x3fe00000u) {
        if (ix <= 0x3c600000u) {
            // |x| < 2**-57
            return PIO2_HI + x1p120f
        }
        return PIO2_HI - (x - (PIO2_LO - x * r(x * x)))
    }
    // x < -0.5
    if ((hx shr 31) != 0u) {
        z = (1.0 + x) * 0.5
        s = sqrt(z)
        w = r(z) * s - PIO2_LO
        return 2.0 * (PIO2_HI - (s + w))
    }
    // x > 0.5
    z = (1.0 - x) * 0.5
    s = sqrt(z)
    // Set the low 4 bytes to zero
    df = Double.fromBits((s.toRawBits().toULong() and 0xffffffff00000000uL).toLong())
    c = (z - df * df) / (s + df)
    w = r(z) * s + c
    return 2.0 * (df + w)
}
