// port-lint: source math/erff.rs
package io.github.kotlinmania.rustlang.libm.math.erff

import io.github.kotlinmania.rustlang.libm.math.expf.expf
import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf

public val ERX: kotlin.Float = 8.4506291151e-01f

// 0x3f58560b
//
// Coefficients for approximation to  erf on [0,0.84375]
//
public val EFX8: kotlin.Float = 1.0270333290e00f

// 0x3f8375d4
public val PP0: kotlin.Float = 1.2837916613e-01f

// 0x3e0375d4
public val PP1: kotlin.Float = -3.2504209876e-01f

// 0xbea66beb
public val PP2: kotlin.Float = -2.8481749818e-02f

// 0xbce9528f
public val PP3: kotlin.Float = -5.7702702470e-03f

// 0xbbbd1489
public val PP4: kotlin.Float = -2.3763017452e-05f

// 0xb7c756b1
public val QQ1: kotlin.Float = 3.9791721106e-01f

// 0x3ecbbbce
public val QQ2: kotlin.Float = 6.5022252500e-02f

// 0x3d852a63
public val QQ3: kotlin.Float = 5.0813062117e-03f

// 0x3ba68116
public val QQ4: kotlin.Float = 1.3249473704e-04f

// 0x390aee49
public val QQ5: kotlin.Float = -3.9602282413e-06f

// 0xb684e21a
//
// Coefficients for approximation to  erf  in [0.84375,1.25]
//
public val PA0: kotlin.Float = -2.3621185683e-03f

// 0xbb1acdc6
public val PA1: kotlin.Float = 4.1485610604e-01f

// 0x3ed46805
public val PA2: kotlin.Float = -3.7220788002e-01f

// 0xbebe9208
public val PA3: kotlin.Float = 3.1834661961e-01f

// 0x3ea2fe54
public val PA4: kotlin.Float = -1.1089469492e-01f

// 0xbde31cc2
public val PA5: kotlin.Float = 3.5478305072e-02f

// 0x3d1151b3
public val PA6: kotlin.Float = -2.1663755178e-03f

// 0xbb0df9c0
public val QA1: kotlin.Float = 1.0642088205e-01f

// 0x3dd9f331
public val QA2: kotlin.Float = 5.4039794207e-01f

// 0x3f0a5785
public val QA3: kotlin.Float = 7.1828655899e-02f

// 0x3d931ae7
public val QA4: kotlin.Float = 1.2617121637e-01f

// 0x3e013307
public val QA5: kotlin.Float = 1.3637083583e-02f

// 0x3c5f6e13
public val QA6: kotlin.Float = 1.1984500103e-02f

// 0x3c445aa3
//
// Coefficients for approximation to  erfc in [1.25,1/0.35]
//
public val RA0: kotlin.Float = -9.8649440333e-03f

// 0xbc21a093
public val RA1: kotlin.Float = -6.9385856390e-01f

// 0xbf31a0b7
public val RA2: kotlin.Float = -1.0558626175e01f

// 0xc128f022
public val RA3: kotlin.Float = -6.2375331879e01f

// 0xc2798057
public val RA4: kotlin.Float = -1.6239666748e02f

// 0xc322658c
public val RA5: kotlin.Float = -1.8460508728e02f

// 0xc3389ae7
public val RA6: kotlin.Float = -8.1287437439e01f

// 0xc2a2932b
public val RA7: kotlin.Float = -9.8143291473e00f

// 0xc11d077e
public val SA1: kotlin.Float = 1.9651271820e01f

// 0x419d35ce
public val SA2: kotlin.Float = 1.3765776062e02f

// 0x4309a863
public val SA3: kotlin.Float = 4.3456588745e02f

// 0x43d9486f
public val SA4: kotlin.Float = 6.4538726807e02f

// 0x442158c9
public val SA5: kotlin.Float = 4.2900814819e02f

// 0x43d6810b
public val SA6: kotlin.Float = 1.0863500214e02f

// 0x42d9451f
public val SA7: kotlin.Float = 6.5702495575e00f

// 0x40d23f7c
public val SA8: kotlin.Float = -6.0424413532e-02f

// 0xbd777f97
//
// Coefficients for approximation to  erfc in [1/.35,28]
//
public val RB0: kotlin.Float = -9.8649431020e-03f

// 0xbc21a092
public val RB1: kotlin.Float = -7.9928326607e-01f

// 0xbf4c9dd4
public val RB2: kotlin.Float = -1.7757955551e01f

// 0xc18e104b
public val RB3: kotlin.Float = -1.6063638306e02f

// 0xc320a2ea
public val RB4: kotlin.Float = -6.3756646729e02f

// 0xc41f6441
public val RB5: kotlin.Float = -1.0250950928e03f

// 0xc480230b
public val RB6: kotlin.Float = -4.8351919556e02f

// 0xc3f1c275
public val SB1: kotlin.Float = 3.0338060379e01f

// 0x41f2b459
public val SB2: kotlin.Float = 3.2579251099e02f

// 0x43a2e571
public val SB3: kotlin.Float = 1.5367296143e03f

// 0x44c01759
public val SB4: kotlin.Float = 3.1998581543e03f

// 0x4547fdbb
public val SB5: kotlin.Float = 2.5530502930e03f

// 0x451f90ce
public val SB6: kotlin.Float = 4.7452853394e02f

// 0x43ed43a7
public val SB7: kotlin.Float = -2.2440952301e01f

// 0xc1b38712
public fun erfc1(x: kotlin.Float): kotlin.Float {
    val s: kotlin.Float
    val p: kotlin.Float
    val q: kotlin.Float
    s = fabsf(x) - 1.0f
    p = PA0 + s * (PA1 + s * (PA2 + s * (PA3 + s * (PA4 + s * (PA5 + s * PA6)))))
    q = 1.0f + s * (QA1 + s * (QA2 + s * (QA3 + s * (QA4 + s * (QA5 + s * QA6)))))
    return 1.0f - ERX - p / q
}

public fun erfc2(ix: UInt, x: kotlin.Float): kotlin.Float {
    var ix1 = ix
    var x1 = x
    val s: kotlin.Float
    val r: kotlin.Float
    val bigS: kotlin.Float
    val z: kotlin.Float
    if (ix1 < 0x3fa00000u) {
        // |x| < 1.25
        return erfc1(x1)
    }
    x1 = fabsf(x1)
    s = 1.0f / (x1 * x1)
    if (ix1 < 0x4036db6du) {
        // |x| < 1/0.35
        r = RA0 + s * (RA1 + s * (RA2 + s * (RA3 + s * (RA4 + s * (RA5 + s * (RA6 + s * RA7))))))
        bigS = 1.0f + s * (SA1 + s * (SA2 + s * (SA3 + s * (SA4 + s * (SA5 + s * (SA6 + s * (SA7 + s * SA8)))))))
    } else {
        // |x| >= 1/0.35
        r = RB0 + s * (RB1 + s * (RB2 + s * (RB3 + s * (RB4 + s * (RB5 + s * RB6)))))
        bigS = 1.0f + s * (SB1 + s * (SB2 + s * (SB3 + s * (SB4 + s * (SB5 + s * (SB6 + s * SB7))))))
    }
    ix1 = x1.toRawBits().toUInt()
    z = kotlin.Float.fromBits((ix1 and 0xffffe000u).toInt())
    return expf(-z * z - 0.5625f) * expf((z - x1) * (z + x1) + r / bigS) / x1
}

/**
 * Error function (f32)
 *
 * Calculates an approximation to the “error function”, which estimates
 * the probability that an observation will fall within x standard
 * deviations of the mean (assuming a normal distribution).
 */
public fun erff(x: kotlin.Float): kotlin.Float {
    val r: kotlin.Float
    val s: kotlin.Float
    val z: kotlin.Float
    val y: kotlin.Float
    var ix: UInt
    val sign: Int
    ix = x.toRawBits().toUInt()
    sign = (ix shr 31).toInt()
    ix = ix and 0x7fffffffu
    if (ix >= 0x7f800000u) {
        // erf(nan)=nan, erf(+-inf)=+-1
        return 1.0f - 2.0f * (sign.toFloat()) + 1.0f / x
    }
    if (ix < 0x3f580000u) {
        // |x| < 0.84375
        if (ix < 0x31800000u) {
            // |x| < 2**-28
            // avoid underflow
            return 0.125f * (8.0f * x + EFX8 * x)
        }
        z = x * x
        r = PP0 + z * (PP1 + z * (PP2 + z * (PP3 + z * PP4)))
        s = 1.0f + z * (QQ1 + z * (QQ2 + z * (QQ3 + z * (QQ4 + z * QQ5))))
        y = r / s
        return x + x * y
    }
    if (ix < 0x40c00000u) {
        // |x| < 6
        y = 1.0f - erfc2(ix, x)
    } else {
        val x1p120 = kotlin.Float.fromBits(0x03800000u.toInt())
        y = 1.0f - x1p120
    }
    return if (sign != 0) {
        -y
    } else {
        y
    }
}

/**
 * Complementary error function (f32)
 *
 * Calculates the complementary probability.
 * Is `1 - erf(x)`. Is computed directly, so that you can use it to avoid
 * the loss of precision that would result from subtracting
 * large probabilities (on large `x`) from 1.
 */
public fun erfcf(x: kotlin.Float): kotlin.Float {
    val r: kotlin.Float
    val s: kotlin.Float
    val z: kotlin.Float
    val y: kotlin.Float
    var ix: UInt
    val sign: Int
    ix = x.toRawBits().toUInt()
    sign = (ix shr 31).toInt()
    ix = ix and 0x7fffffffu
    if (ix >= 0x7f800000u) {
        // erfc(nan)=nan, erfc(+-inf)=0,2
        return 2.0f * (sign.toFloat()) + 1.0f / x
    }
    if (ix < 0x3f580000u) {
        // |x| < 0.84375
        if (ix < 0x23800000u) {
            // |x| < 2**-56
            return 1.0f - x
        }
        z = x * x
        r = PP0 + z * (PP1 + z * (PP2 + z * (PP3 + z * PP4)))
        s = 1.0f + z * (QQ1 + z * (QQ2 + z * (QQ3 + z * (QQ4 + z * QQ5))))
        y = r / s
        if (sign != 0 || ix < 0x3e800000u) {
            // x < 1/4
            return 1.0f - (x + x * y)
        }
        return 0.5f - (x - 0.5f + x * y)
    }
    if (ix < 0x41e00000u) {
        // |x| < 28
        if (sign != 0) {
            return 2.0f - erfc2(ix, x)
        } else {
            return erfc2(ix, x)
        }
    }
    val x1p120 = kotlin.Float.fromBits(0x03800000u.toInt())
    return if (sign != 0) {
        2.0f - x1p120
    } else {
        x1p120 * x1p120
    }
}
