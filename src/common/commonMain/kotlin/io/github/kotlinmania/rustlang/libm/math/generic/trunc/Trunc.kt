// port-lint: source math/generic/trunc.rs
package io.github.kotlinmania.rustlang.libm.math.generic.trunc

import io.github.kotlinmania.rustlang.libm.math.support.env.FpResult
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.IntTy
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> truncStatus(x: F, fromBitsFFn: (Int) -> F): FpResult<F> {
    val xi: Int = x.toBits().toInt()
    val e: Int = x.expUnbiased()
    // The represented value has no fractional part, so no truncation is needed
    if (e >= F.SIG_BITS.toInt()) {
        return FpResult.ok(x)
    }
    val clearMask =
        if (e < 0) {
            // If the exponent is negative, the result will be zero so we clear everything
            // except the sign.
            F.SIGN_MASK.inv()
        } else {
            // Otherwise, we keep `e` fractional bits and clear the rest.
            F.SIG_MASK shr e.unsigned().toInt()
        }
    val cleared = xi and clearMask
    val status =
        if (cleared == IntTy.ZERO) {
            // If the to-be-zeroed portion is already zero, we have an exact result.
            Status.OK
        } else {
            // Otherwise the result is inexact and we will truncate, so indicate `FE_INEXACT`.
            Status.INEXACT
        }
    // Now zero the bits we need to truncate and return.
    return FpResult.new(fromBitsFFn(xi xor cleared), status)
}
