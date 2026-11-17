// port-lint: source math/generic/fmaximum.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fmaximum

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

// IEEE 754-2019 `maximum`.
//
// Per the spec, returns the canonicalized result of:
// - `x` if `x> y`
// - `y` if `y> x`
// - +0.0 if x and y are zero with opposite signs
// - qNaN if either operation is NaN
//
// Note that the IEEE 754-2019 specifies that a sNaN in either argument should signal invalid,
// but we do not implement this.

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fmaximum(x: F, y: F): F {
    val res =
        if (x.isNan()) {
            x
        } else if (y.isNan()) {
            y
        } else if ((kmCompareValues(x, y) > 0) || (y.biteq(F.NEG_ZERO) && x.isSignPositive())) {
            x
        } else {
            y
        }
    return res.canonicalize()
}
