// port-lint: source math/generic/floor.rs
package io.github.kotlinmania.rustlang.libm.math.generic.floor

import io.github.kotlinmania.rustlang.math.support.env.FpResult
import io.github.kotlinmania.rustlang.math.support.env.Status
import io.github.kotlinmania.rustlang.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.math.support.floattraits.IntTy
import io.github.kotlinmania.rustlang.math.support.inttraits.Int

// Generic `floor` algorithm.
//
// Note that this uses the algorithm from musl's `floorf` rather than `floor` or `floorl` because
// performance seems to be better (based on icount) and it does not seem to experience rounding
// errors on i386.

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> floorStatus(x: F, fromBitsFFn: (Int) -> F): FpResult<F> {
    val zero = IntTy.ZERO
    var ix = x.toBits()
    val e = x.expUnbiased()
    // If the represented value has no fractional part, no truncation is needed.
    if (e >= F.SIG_BITS.toInt()) {
        return FpResult.ok(x)
    }
    val res =
        if (e >= 0) {
            // |x| >= 1.0
            val m = F.SIG_MASK shr e.unsigned().toInt()
            if (ix and m == zero) {
                // Portion to be masked is already zero; no adjustment needed.
                return FpResult.ok(x)
            }
            if (x.isSignNegative()) {
                ix += m
            }
            ix = ix and m.inv()
            fromBitsFFn(ix)
        } else {
            // |x| < 1.0, zero or inexact with truncation
            if ((ix and F.SIGN_MASK.inv()) == F.Int.ZERO) {
                return FpResult.ok(x)
            }
            if (x.isSignPositive()) {
                // 0.0 <= x < 1.0; rounding down goes toward +0.0.
                F.ZERO
            } else {
                // -1.0 < x < 0.0; rounding down goes toward -1.0.
                F.NEG_ONE
            }
        }
    return FpResult.new(res, Status.INEXACT)
}
