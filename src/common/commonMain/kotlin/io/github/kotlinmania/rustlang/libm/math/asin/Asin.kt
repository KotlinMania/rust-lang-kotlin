// port-lint: source math/asin.rs
package io.github.kotlinmania.rustlang.libm.math.asin

import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrt
import io.github.kotlinmania.rustlang.math.fabs.fabs
import io.github.kotlinmania.rustlang.math.getHighWord
import io.github.kotlinmania.rustlang.math.getLowWord
import io.github.kotlinmania.rustlang.math.withSetLowWord

public val PIO2_HI: Double = 1.57079632679489655800e00

// 0x3FF921FB, 0x54442D18
public val PIO2_LO: Double = 6.12323399573676603587e-17

// 0x3C91A626, 0x33145C07
// coefficients for R(x^2)
public val P_S0: Double = 1.66666666666666657415e-01

// 0x3FC55555, 0x55555555
public val P_S1: Double = -3.25565818622400915405e-01

// 0xBFD4D612, 0x03EB6F7D
public val P_S2: Double = 2.01212532134862925881e-01

// 0x3FC9C155, 0x0E884455
public val P_S3: Double = -4.00555345006794114027e-02

// 0xBFA48228, 0xB5688F3B
public val P_S4: Double = 7.91534994289814532176e-04

// 0x3F49EFE0, 0x7501B288
public val P_S5: Double = 3.47933107596021167570e-05

// 0x3F023DE1, 0x0DFDF709
public val Q_S1: Double = -2.40339491173441421878e00

// 0xC0033A27, 0x1C8A2D4B
public val Q_S2: Double = 2.02094576023350569471e00

// 0x40002AE5, 0x9C598AC8
public val Q_S3: Double = -6.88283971605453293030e-01

// 0xBFE6066C, 0x1B8D0159
public val Q_S4: Double = 7.70381505559019352791e-02

// 0x3FB3B8C5, 0xB12E9282
public fun compR(z: Double): Double {
    val p = z * (P_S0 + z * (P_S1 + z * (P_S2 + z * (P_S3 + z * (P_S4 + z * P_S5)))))
    val q = 1.0 + z * (Q_S1 + z * (Q_S2 + z * (Q_S3 + z * Q_S4)))
    return p / q
}

/**
 * Arcsine (f64)
 *
 * Computes the inverse sine (arc sine) of the argument `x`.
 * Arguments to asin must be in the range -1 to 1.
 * Returns values in radians, in the range of -pi/2 to pi/2.
 */
public fun asin(x: Double): Double {
    var x1 = x
    val z: Double
    val r: Double
    val s: Double
    val hx: UInt
    val ix: UInt
    hx = getHighWord(x1)
    ix = hx and 0x7fffffffu
    // |x| >= 1 or nan
    if (ix >= 0x3ff00000u) {
        val lx: UInt
        lx = getLowWord(x1)
        if (((ix - 0x3ff00000u) or lx) == 0u) {
            // asin(1) = +-pi/2 with inexact
            return x1 * PIO2_HI + Double.fromBits(0x3870000000000000uL.toLong())
        } else {
            return 0.0 / (x1 - x1)
        }
    }
    // |x| < 0.5
    if (ix < 0x3fe00000u) {
        // if 0x1p-1022 <= |x| < 0x1p-26, avoid raising underflow
        if ((0x00100000 until 0x3e500000).contains(ix)) {
            return x1
        } else {
            return x1 + x1 * compR(x1 * x1)
        }
    }
    // 1 > |x| >= 0.5
    z = (1.0 - fabs(x1)) * 0.5
    s = sqrt(z)
    r = compR(z)
    if (ix >= 0x3fef3333u) {
        // if |x| > 0.975
        x1 = PIO2_HI - (2.0 * (s + s * r) - PIO2_LO)
    } else {
        val f: Double
        val c: Double
        // f+c = sqrt(z)
        f = withSetLowWord(s, 0u)
        c = (z - f * f) / (s + f)
        x1 = 0.5 * PIO2_HI - (2.0 * s * r - (PIO2_LO - 2.0 * c) - (0.5 * PIO2_HI - 2.0 * f))
    }
    return if (hx shr 31 != 0u) {
        -x1
    } else {
        x1
    }
}
