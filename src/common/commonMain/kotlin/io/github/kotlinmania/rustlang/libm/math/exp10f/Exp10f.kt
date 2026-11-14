// port-lint: source math/exp10f.rs
package io.github.kotlinmania.rustlang.libm.math.exp10f

import io.github.kotlinmania.rustlang.libm.math.arch.x87Exp10f
import io.github.kotlinmania.rustlang.libm.math.exp2.exp2
import io.github.kotlinmania.rustlang.libm.math.exp2f.exp2f
import io.github.kotlinmania.rustlang.libm.math.modff.modff

public val LN10_F32: kotlin.Float = 3.32192809488736234787031942948939f

public val LN10_F64: Double = 3.32192809488736234787031942948939

public val P10: List<kotlin.Float> = listOf(1e-7f, 1e-6f, 1e-5f, 1e-4f, 1e-3f, 1e-2f, 1e-1f, 1e0f, 1e1f, 1e2f, 1e3f, 1e4f, 1e5f, 1e6f, 1e7f)

/**
 * Calculates 10 raised to the power of `x` (f32).
 */
public fun exp10f(x: kotlin.Float): kotlin.Float {
    if (true) {
        return x87Exp10f(x)
    }
    var (y, n) = modff(x)
    val u = n.toBits()
    // fabsf(n) < 8 without raising invalid on nan
    if (((u shr 23) and 0xffuL) < 0x7fuL + 3uL) {
        if (y == 0.0) {
            return P10.getUnchecked(((n) + 7))
        }
        y = exp2f(LN10_F32 * y)
        return y * P10.getUnchecked(((n) + 7))
    }
    return exp2(LN10_F64 * (x.toDouble())).toFloat()
}
