// port-lint: source math/generic/fma_wide.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fmawide

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.libm.math.support.env.FpResult
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.IntTy
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.NarrowFloat
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.WideFloat

/**
 * Fma implementation when a hardware-backed larger float type is available. For `Float` and `Double`,
 * `Double` has enough precision to represent the `Float` in its entirety, except for double rounding.
 */
public fun <Int, SignedInt, F, B> fmaWideRound(x: F, y: F, z: F, round: Round, fromBitsBFn: (Int) -> B): FpResult<F> where F : Float<F, Int, SignedInt>, F : NarrowFloat<F, B, Int, SignedInt>, B : Float<B, Int, SignedInt>, B : WideFloat<B, F, Int, SignedInt> {
    val one = IntTy.ONE
    val xy: B = x.widen() * y.widen()
    var result: B = xy + z.widen()
    var ui: Int = result.toBits().toInt()
    val re = result.ex()
    val zb: B = z.widen()
    val precDiff = B.SIG_BITS - F.SIG_BITS
    val excessPrec = ui and ((one shl precDiff.toInt()) - one)
    val halfway = one shl (precDiff - 1).toInt()
    // Common case: the larger precision is fine if...
    // This is not a halfway case
    if (excessPrec != halfway || re == B.EXP_SAT || (result - xy == zb && result - zb == xy) || round != Round.Nearest) {
        val minInexactExp = (((B.EXP_BIAS as Any?) as kotlin.Int) + F.EXP_MIN_SUBNORM).toUInt()
        val maxInexactExp = (((B.EXP_BIAS as Any?) as kotlin.Int) + F.EXP_MIN).toUInt()
        var status = Status.OK
        if ((minInexactExp until maxInexactExp).contains(re) && status.inexact()) {
            // This branch is never hit; requires previous operations to set a status
            status.setInexact(false)
            result = xy + z.widen()
            if (status.inexact()) {
                status.setUnderflow(true)
            } else {
                status.setInexact(true)
            }
        }
        return FpResult(value = result.narrow(), status = status)
    }
    val neg = ui shr (B.BITS - 1).toInt() != IntTy.ZERO
    val err =
        if (neg == ((kmCompareValues(zb, xy) > 0))) {
            xy - result + zb
        } else {
            zb - result + xy
        }
    if (neg == (err < B.ZERO)) {
        ui += one
    } else {
        ui -= one
    }
    return FpResult.ok(fromBitsBFn(ui).narrow())
}
