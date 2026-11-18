// port-lint: source math/generic/frexp.rs
package io.github.kotlinmania.rustlang.libm.math.generic.frexp

import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> frexp(x: F, normalizeFFn: (Int) -> Pair<Int, Int>, fromBitsFFn: (Int) -> F): Pair<F, kotlin.Int> {
    var ix = x.toBits()
    var ee = ((x.ex() as Any?) as kotlin.Int)
    if (ee == 0) {
        if (x == x.ZERO) {
            return Pair(x, 0)
        }
        // Subnormals, needs to be normalized first
        ix = ix and x.SIG_MASK
        run { val (destructVal0, destructVal1) = normalizeFFn(ix); ee = destructVal0; ix = destructVal1 }
        ix = ix or (x.toBits() and x.SIGN_MASK)
    } else if (ee == ((x.EXP_SAT as Any?) as kotlin.Int)) {
        // inf or  NaN
        return Pair(x, 0)
    }
    val e = ee - (((x.EXP_BIAS as Any?) as kotlin.Int) - 1)
    ix = ix and (x.SIGN_MASK or x.SIG_MASK)
    ix = ix or (F.Int.castFrom(x.EXP_BIAS - 1) shl x.SIG_BITS.toInt())
    return Pair(fromBitsFFn(ix), e)
}
