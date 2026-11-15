// port-lint: source math/generic/round.rs
package io.github.kotlinmania.rustlang.libm.math.generic.round

import io.github.kotlinmania.rustlang.math.generic.copysign.copysign
import io.github.kotlinmania.rustlang.math.generic.trunc.truncStatus
import io.github.kotlinmania.rustlang.math.support.floattraits.Float

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> round(x: F, fromPartsFFn: (Boolean, UInt, Int) -> F): F {
    val f0p5 = fromPartsFFn(false, F.EXP_BIAS - 1, F.Int.ZERO)
    // 0.5
    val f0p25 = fromPartsFFn(false, F.EXP_BIAS - 2, F.Int.ZERO)
    // 0.25
    return truncStatus(x + copysign(f0p5 - f0p25 * F.EPSILON, x)).value
}
