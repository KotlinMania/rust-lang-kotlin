// port-lint: source math/generic/frexp.rs
package io.github.kotlinmania.rustlang.libm.math.generic.frexp

import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> frexp(x: F, normalizeFFn: (Int) -> Pair<Int, Int>, fromBitsFFn: (Int) -> F): Pair<F, Int> {
    var ix = x.toBits()
    val ee = x.ex().toInt()
    if (ee == 0) {
        if (x == F.ZERO) {
            return Pair(x, 0)
        }
        // Subnormals, needs to be normalized first
        ix = ix and F.SIG_MASK
        Pair(ee, ix) = normalizeFFn(ix)
        ix = ix or (x.toBits() and F.SIGN_MASK)
    } else if (ee == F.EXP_SAT.toInt()) {
        // inf or  NaN
        return Pair(x, 0)
    }
    val e = ee - (F.EXP_BIAS.toInt() - 1)
    ix = ix and (F.SIGN_MASK or F.SIG_MASK)
    ix = ix or (F.Int.castFrom(F.EXP_BIAS - 1) shl F.SIG_BITS.toInt())
    return Pair(fromBitsFFn(ix), e)
}
