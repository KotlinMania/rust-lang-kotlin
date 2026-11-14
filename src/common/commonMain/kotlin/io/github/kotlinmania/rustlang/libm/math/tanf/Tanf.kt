// port-lint: source math/tanf.rs
package io.github.kotlinmania.rustlang.libm.math.tanf

import io.github.kotlinmania.rustlang.libm.math.ktanf.kTanf
import io.github.kotlinmania.rustlang.libm.math.rempio2f.remPio2f

// Small multiples of pi/2 rounded to double precision.
public val T1_PIO2: Double = 1.0 * 1.5707963267948966

// 0x3FF921FB, 0x54442D18
public val T2_PIO2: Double = 2.0 * 1.5707963267948966

// 0x400921FB, 0x54442D18
public val T3_PIO2: Double = 3.0 * 1.5707963267948966

// 0x4012D97C, 0x7F3321D2
public val T4_PIO2: Double = 4.0 * 1.5707963267948966

// 0x401921FB, 0x54442D18

/**
 * The tangent of `x` (f32).
 *
 * `x` is specified in radians.
 */
public fun tanf(x: kotlin.Float): kotlin.Float {
    val x64 = x.toDouble()
    val x1p120 = kotlin.Float.fromBits(0x7b800000u.toInt())
    // 0x1p120f === 2 ^ 120
    var ix = x.toRawBits().toUInt()
    val sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    if (ix <= 0x3f490fdau) {
        // |x| ~<= pi/4
        if (ix < 0x39800000u) {
            readVolatile(if (ix < 0x00800000u) {
                x / x1p120
            } else {
                x + x1p120
            })
            return x
        }
        return kTanf(x64, false)
    }
    if (ix <= 0x407b53d1u) {
        // |x| ~<= 5*pi/4
        if (ix <= 0x4016cbe3u) {
            // |x| ~<= 3pi/4
            return kTanf(if (sign) {
                x64 + T1_PIO2
            } else {
                x64 - T1_PIO2
            }, true)
        } else {
            return kTanf(if (sign) {
                x64 + T2_PIO2
            } else {
                x64 - T2_PIO2
            }, false)
        }
    }
    if (ix <= 0x40e231d5u) {
        // |x| ~<= 9*pi/4
        if (ix <= 0x40afeddfu) {
            // |x| ~<= 7*pi/4
            return kTanf(if (sign) {
                x64 + T3_PIO2
            } else {
                x64 - T3_PIO2
            }, true)
        } else {
            return kTanf(if (sign) {
                x64 + T4_PIO2
            } else {
                x64 - T4_PIO2
            }, false)
        }
    }
    // tan(Inf or NaN) is NaN
    if (ix >= 0x7f800000u) {
        return x - x
    }
    // argument reduction
    val (n, y) = remPio2f(x)
    return kTanf(y, n and 1 != 0)
}
