// port-lint: source math/acosf.rs
package io.github.kotlinmania.rustlang.libm.math.acosf

import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrtf

public val PIO2_HI: kotlin.Float = 1.5707962513e00f

// 0x3fc90fda
public val PIO2_LO: kotlin.Float = 7.5497894159e-08f

// 0x33a22168
public val P_S0: kotlin.Float = 1.6666586697e-01f

public val P_S1: kotlin.Float = -4.2743422091e-02f

public val P_S2: kotlin.Float = -8.6563630030e-03f

public val Q_S1: kotlin.Float = -7.0662963390e-01f

public fun r(z: kotlin.Float): kotlin.Float {
    val p = z * (P_S0 + z * (P_S1 + z * P_S2))
    val q = 1.0f + z * Q_S1
    return p / q
}

/**
 * Arccosine (f32)
 *
 * Computes the inverse cosine (arc cosine) of the input value.
 * Arguments must be in the range -1 to 1.
 * Returns values in radians, in the range of 0 to pi.
 */
public fun acosf(x: kotlin.Float): kotlin.Float {
    val x1p120 = kotlin.Float.fromBits(0x03800000u.toInt())
    // 0x1p-120 === 2 ^ (-120)
    val z: kotlin.Float
    val w: kotlin.Float
    val s: kotlin.Float
    var hx = x.toRawBits().toUInt()
    val ix = hx and 0x7fffffffu
    // |x| >= 1 or nan
    if (ix >= 0x3f800000u) {
        if (ix == 0x3f800000u) {
            if ((hx shr 31) != 0u) {
                return 2.0f * PIO2_HI + x1p120
            }
            return 0.0f
        }
        return 0.0f / (x - x)
    }
    // |x| < 0.5
    if (ix < 0x3f000000u) {
        if (ix <= 0x32800000u) {
            // |x| < 2**-26
            return PIO2_HI + x1p120
        }
        return PIO2_HI - (x - (PIO2_LO - x * r(x * x)))
    }
    // x < -0.5
    if ((hx shr 31) != 0u) {
        z = (1.0f + x) * 0.5f
        s = sqrtf(z)
        w = r(z) * s - PIO2_LO
        return 2.0f * (PIO2_HI - (s + w))
    }
    // x > 0.5
    z = (1.0f - x) * 0.5f
    s = sqrtf(z)
    hx = s.toRawBits().toUInt()
    val df = kotlin.Float.fromBits((hx and 0xfffff000u).toInt())
    val c = (z - df * df) / (s + df)
    w = r(z) * s + c
    return 2.0f * (df + w)
}
