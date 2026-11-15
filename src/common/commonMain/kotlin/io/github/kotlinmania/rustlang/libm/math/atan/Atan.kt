// port-lint: source math/atan.rs
package io.github.kotlinmania.rustlang.libm.math.atan

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.libm.math.fabs.fabs

public val ATANHI: DoubleArray = doubleArrayOf(4.63647609000806093515e-01, 7.85398163397448278999e-01, 9.82793723247329054082e-01, 1.57079632679489655800e00)

public val ATANLO: DoubleArray = doubleArrayOf(2.26987774529616870924e-17, 3.06161699786838301793e-17, 1.39033110312309984516e-17, 6.12323399573676603587e-17)

public val AT: DoubleArray = doubleArrayOf(3.33333333333329318027e-01, -1.99999999998764832476e-01, 1.42857142725034663711e-01, -1.11111104054623557880e-01, 9.09088713343650656196e-02, -7.69187620504482999495e-02, 6.66107313738753120669e-02, -5.83357013379057348645e-02, 4.97687799461593236017e-02, -3.65315727442169155270e-02, 1.62858201153657823623e-02)

/**
 * Arctangent (f64)
 *
 * Computes the inverse tangent (arc tangent) of the input value.
 * Returns a value in radians, in the range of -pi/2 to pi/2.
 */
public fun atan(x: Double): Double {
    val x1 = x
    var x2 = x1
    var ix = (x2.toBits() shr 32).toUInt()
    val sign = ix shr 31
    ix = ix and 0x7fffffffu
    if (ix >= 0x44100000u) {
        if (x2.isNan()) {
            return x2
        }
        val z = ATANHI[3] + Double.fromBits(0x03800000uL.toLong())
        // 0x1p-120f
        return if (sign != 0u) {
            -z
        } else {
            z
        }
    }
    val id =
        if (ix < 0x3fdc0000u) {
            // |x| < 0.4375
            if (ix < 0x3e400000u) {
                // |x| < 2^-27
                if (ix < 0x00100000u) {
                    readVolatile(x2.toFloat(), typeLayoutT = PrimitiveLayouts.F32)
                }
                return x2
            }
            -1
        } else {
            x2 = fabs(x2)
            if (ix < 0x3ff30000u) {
                // |x| < 1.1875
                if (ix < 0x3fe60000u) {
                    // 7/16 <= |x| < 11/16
                    x2 = (2.0 * x2 - 1.0) / (2.0 + x2)
                    0
                } else {
                    // 11/16 <= |x| < 19/16
                    x2 = (x2 - 1.0) / (x2 + 1.0)
                    1
                }
            } else if (ix < 0x40038000u) {
                // |x| < 2.4375
                x2 = (x2 - 1.5) / (1.0 + 1.5 * x2)
                2
            } else {
                // 2.4375 <= |x| < 2^66
                x2 = -1.0 / x2
                3
            }
        }
    val z1 = x2 * x2
    val w = z1 * z1
    // break sum from i=0 to 10 AT[i]z**(i+1) into odd and even poly
    val s1 = z1 * (AT[0] + w * (AT[2] + w * (AT[4] + w * (AT[6] + w * (AT[8] + w * AT[10])))))
    val s2 = w * (AT[1] + w * (AT[3] + w * (AT[5] + w * (AT[7] + w * AT[9]))))
    if (id < 0) {
        return x2 - x2 * (s1 + s2)
    }
    val z2 = ATANHI.getUnchecked(id.toInt()) - (x2 * (s1 + s2) - ATANLO.getUnchecked(id.toInt()) - x2)
    return if (sign != 0u) {
        -z2
    } else {
        z2
    }
}
