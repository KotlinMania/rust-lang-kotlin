// port-lint: source math/generic/copysign.rs
package io.github.kotlinmania.rustlang.libm.math.generic.copysign

import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

/**
 * Copy the sign of `y` to `x`.
 */
public fun <Int, SignedInt, F : Float<F, Any?, Any?>> copysign(x: F, y: F, fromBitsFFn: (Int) -> F): F {
    var ux = x.toBits()
    val uy = y.toBits()
    ux = ux and F.SIGN_MASK.inv()
    ux = ux or (uy and F.SIGN_MASK)
    return fromBitsFFn(ux)
}
