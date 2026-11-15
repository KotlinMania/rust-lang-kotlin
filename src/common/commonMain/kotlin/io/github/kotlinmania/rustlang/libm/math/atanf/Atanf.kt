// port-lint: source math/atanf.rs
package io.github.kotlinmania.rustlang.libm.math.atanf

import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf

public val ATAN_HI: FloatArray = floatArrayOf(4.6364760399e-01f, 7.8539812565e-01f, 9.8279368877e-01f, 1.5707962513e00f)

public val ATAN_LO: FloatArray = floatArrayOf(5.0121582440e-09f, 3.7748947079e-08f, 3.4473217170e-08f, 7.5497894159e-08f)

public val A_T: FloatArray = floatArrayOf(3.3333328366e-01f, -1.9999158382e-01f, 1.4253635705e-01f, -1.0648017377e-01f, 6.1687607318e-02f)

/**
 * Arctangent (f32)
 *
 * Computes the inverse tangent (arc tangent) of the input value.
 * Returns a value in radians, in the range of -pi/2 to pi/2.
 */
public fun atanf(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val x1p120 = kotlin.Float.fromBits(0x03800000u.toInt())
    // 0x1p-120 === 2 ^ (-120)
    val z: kotlin.Float
    var ix = x1.toRawBits().toUInt()
    val sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    if (ix >= 0x4c800000u) {
        // if |x| >= 2**26
        if (x1.isNaN()) {
            return x1
        }
        z = ATAN_HI.getUnchecked(3) + x1p120
        return if (sign) {
            -z
        } else {
            z
        }
    }
    val id =
        if (ix < 0x3ee00000u) {
            // |x| < 0.4375
            if (ix < 0x39800000u) {
                // |x| < 2**-12
                if (ix < 0x00800000u) {
                    readVolatile(x1 * x1)
                }
                return x1
            }
            -1
        } else {
            x1 = fabsf(x1)
            if (ix < 0x3f980000u) {
                // |x| < 1.1875
                if (ix < 0x3f300000u) {
                    // 7/16 <= |x| < 11/16
                    x1 = (2.0f * x1 - 1.0f) / (2.0f + x1)
                    0
                } else {
                    // 11/16 <= |x| < 19/16
                    x1 = (x1 - 1.0f) / (x1 + 1.0f)
                    1
                }
            } else if (ix < 0x401c0000u) {
                // |x| < 2.4375
                x1 = (x1 - 1.5f) / (1.0f + 1.5f * x1)
                2
            } else {
                // 2.4375 <= |x| < 2**26
                x1 = -1.0f / x1
                3
            }
        }
    // end of argument reduction
    z = x1 * x1
    val w = z * z
    // break sum from i=0 to 10 aT[i]z**(i+1) into odd and even poly
    val s1 = z * (A_T.getUnchecked(0) + w * (A_T.getUnchecked(2) + w * A_T.getUnchecked(4)))
    val s2 = w * (A_T.getUnchecked(1) + w * A_T.getUnchecked(3))
    if (id < 0) {
        return x1 - x1 * (s1 + s2)
    }
    val id1 = id.toInt()
    val z1 = ATAN_HI.getUnchecked(id1) - ((x1 * (s1 + s2) - ATAN_LO.getUnchecked(id1)) - x1)
    return if (sign) {
        -z1
    } else {
        z1
    }
}
