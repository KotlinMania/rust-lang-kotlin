// port-lint: source math/tanh.rs
package io.github.kotlinmania.rustlang.libm.math.tanh

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.math.expm1.expm1

// tanh(x) = (exp(x) - exp(-x))/(exp(x) + exp(-x))
// = (exp(2*x) - 1)/(exp(2*x) - 1 + 2)
// = (1 - exp(-2*x))/(exp(-2*x) - 1 + 2)
//

/**
 * The hyperbolic tangent of `x` (f64).
 *
 * `x` is specified in radians.
 */
public fun tanh(x: Double): Double {
    var x1 = x
    var uf: Double = x1
    var ui: ULong = uf.toRawBits().toULong()
    val w: UInt
    val sign: Boolean
    var t: Double
    // x = |x|
    sign = ui shr 63 != 0uL
    ui = ui and (1uL.inv() / 2uL)
    uf = Double.fromBits(ui.toLong())
    x1 = uf
    w = (ui shr 32).toUInt()
    if (w > 0x3fe193eau) {
        // |x| > log(3)/2 ~= 0.5493 or nan
        if (w > 0x40340000u) {
            // |x| > 20 or nan
            // note: this branch avoids raising overflow
            t = 1.0 - 0.0 / x1
        } else {
            t = expm1(2.0 * x1)
            t = 1.0 - 2.0 / (t + 2.0)
        }
    } else if (w > 0x3fd058aeu) {
        // |x| > log(5/3)/2 ~= 0.2554
        t = expm1(2.0 * x1)
        t = t / (t + 2.0)
    } else if (w >= 0x00100000u) {
        // |x| >= 0x1p-1022, up to 2ulp error in [0.1,0.2554]
        t = expm1(-2.0 * x1)
        t = -t / (t + 2.0)
    } else {
        readVolatile(x1.toFloat(), typeLayoutT = PrimitiveLayouts.F32)
        t = x1
    }
    return if (sign) {
        -t
    } else {
        t
    }
}
