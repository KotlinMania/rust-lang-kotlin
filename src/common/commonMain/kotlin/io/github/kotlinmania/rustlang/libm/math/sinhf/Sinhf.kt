// port-lint: source math/sinhf.rs
package io.github.kotlinmania.rustlang.libm.math.sinhf

import io.github.kotlinmania.rustlang.math.expm1f.expm1f
import io.github.kotlinmania.rustlang.math.kexpo2f.kExpo2f

/**
 * The hyperbolic sine of `x` (f32).
 */
public fun sinhf(x: kotlin.Float): kotlin.Float {
    var h = 0.5f
    var ix = x.toRawBits().toUInt()
    if ((ix shr 31) != 0u) {
        h = -h
    }
    // |x|
    ix = ix and 0x7fffffffu
    val absx = kotlin.Float.fromBits(ix.toInt())
    val w = ix
    // |x| < log(FLT_MAX)
    if (w < 0x42b17217u) {
        val t = expm1f(absx)
        if (w < 0x3f800000u) {
            if (w < (0x3f800000u - (12u shl 23))) {
                return x
            }
            return h * (2.0f * t - t * t / (t + 1.0f))
        }
        return h * (t + t / (t + 1.0f))
    }
    // |x| > logf(FLT_MAX) or nan
    return 2.0f * h * kExpo2f(absx)
}
