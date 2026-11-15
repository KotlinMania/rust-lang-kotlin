// port-lint: source math/generic/rint.rs
package io.github.kotlinmania.rustlang.libm.math.generic.rint

import io.github.kotlinmania.rustlang.math.support.env.FpResult
import io.github.kotlinmania.rustlang.math.support.env.Status
import io.github.kotlinmania.rustlang.math.support.floattraits.Float

/**
 * IEEE 754-2019 `roundToIntegralExact`, which respects rounding mode and raises inexact if
 * applicable.
 */
public fun <Int, SignedInt, F : Float<F, Any?, Any?>> rintStatus(x: F): FpResult<F> {
    val toint = F.ONE / F.EPSILON
    val e = x.ex()
    val positive = x.isSignPositive()
    // On i386 `forceEval` must be used to force rounding via storage to memory. Otherwise,
    // the excess precission from x87 would cause an incorrect final result.
    val force =
        { x1 ->
            if (false && (F.BITS == 32 || F.BITS == 64)) {
                readVolatile(x1)
            } else {
                x1
            }
        }
    val res =
        if (e >= F.EXP_BIAS + F.SIG_BITS) {
            // No fractional part; exact result can be returned.
            x
        } else {
            // Apply a net-zero adjustment that nudges `y` in the direction of the rounding mode. For
            // Rust this is always nearest, but ideally it would take `round` into account.
            val y =
                if (positive) {
                    force(force(x) + toint) - toint
                } else {
                    force(force(x) - toint) + toint
                }
            if (y == F.ZERO) {
                // A zero result takes the sign of the input.
                if (positive) {
                    F.ZERO
                } else {
                    F.NEG_ZERO
                }
            } else {
                y
            }
        }
    val status =
        if (res == x) {
            Status.OK
        } else {
            Status.INEXACT
        }
    return FpResult.new(res, status)
}
