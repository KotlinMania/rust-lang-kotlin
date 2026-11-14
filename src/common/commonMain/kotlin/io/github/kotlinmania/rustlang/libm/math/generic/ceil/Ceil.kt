// port-lint: source math/generic/ceil.rs
package io.github.kotlinmania.rustlang.libm.math.generic.ceil

import io.github.kotlinmania.rustlang.libm.math.support.env.FpResult
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.IntTy
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

// Generic `ceil` algorithm.
//
// Note that this uses the algorithm from musl's `ceilf` rather than `ceil` or `ceill` because
// performance seems to be better (based on icount) and it does not seem to experience rounding
// errors on i386.

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> ceilStatus(x: F, fromBitsFFn: (Int) -> F): FpResult<F> {
    val zero = IntTy.ZERO
    var ix = x.toBits()
    val e = x.expUnbiased()
    // If the represented value has no fractional part, no truncation is needed.
    if (e >= F.SIG_BITS.toInt()) {
        return FpResult.ok(x)
    }
    val status: Any?
    val res =
        if (e >= 0) {
            // |x| >= 1.0
            val m = F.SIG_MASK shr e.unsigned().toInt()
            if ((ix and m) == zero) {
                // Portion to be masked is already zero; no adjustment needed.
                return FpResult.ok(x)
            }
            // Otherwise, raise an inexact exception.
            status = Status.INEXACT
            if (x.isSignPositive()) {
                ix += m
            }
            ix = ix and m.inv()
            fromBitsFFn(ix)
        } else {
            // |x| < 1.0, raise an inexact exception since truncation will happen (unless x == 0).
            if (ix and F.SIGN_MASK.inv() == F.Int.ZERO) {
                status = Status.OK
            } else {
                status = Status.INEXACT
            }
            if (x.isSignNegative()) {
                // -1.0 < x <= -0.0; rounding up goes toward -0.0.
                F.NEG_ZERO
            } else if (ix shl 1 != zero) {
                // 0.0 < x < 1.0; rounding up goes toward +1.0.
                F.ONE
            } else {
                // +0.0 remains unchanged
                x
            }
        }
    return FpResult.new(res, status)
}
