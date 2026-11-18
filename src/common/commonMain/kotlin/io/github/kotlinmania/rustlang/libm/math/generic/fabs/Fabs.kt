// port-lint: source math/generic/fabs.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fabs

import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

/**
 * Absolute value.
 */
public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fabs(x: F, fromBitsFFn: (Int) -> F): F {
    val absMask = x.SIGN_MASK.inv()
    return fromBitsFFn(x.toBits() and absMask)
}
