// port-lint: source math/generic/fminimum.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fminimum

import io.github.kotlinmania.rustlang.core.cmp.compareNaturalOrder
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

// IEEE 754-2019 `minimum`.
//
// Per the spec, returns the canonicalized result of:
// - `x` if `x<y`
// - `y` if `y<x`
// - -0.0 if x and y are zero with opposite signs
// - qNaN if either operation is NaN
//
// Note that the IEEE 754-2019 specifies that a sNaN in either argument should signal invalid,
// but we do not implement this.

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fminimum(x: F, y: F): F {
    val res =
        if (x.isNan()) {
            x
        } else if (y.isNan()) {
            y
        } else if ((compareNaturalOrder(x, y) < 0) || (x.biteq(F.NEG_ZERO) && y.isSignPositive())) {
            x
        } else {
            y
        }
    return res.canonicalize()
}
