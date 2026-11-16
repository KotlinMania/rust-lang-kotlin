// port-lint: source math/generic/fminimum_num.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fminimumnum

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

// IEEE 754-2019 `minimum`.
//
// Per the spec, returns:
// - `x` if `x<y`
// - `y` if `y<x`
// - -0.0 if x and y are zero with opposite signs
// - Either `x` or `y` if `x == y` and the signs are the same
// - Non-NaN if one operand is NaN
// - qNaN if both operands are NaNx
//
// Note that the IEEE 754-2019 specifies that a sNaN in either argument should signal invalid,
// but we do not implement this.

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fminimumNum(x: F, y: F): F {
    val res =
        if ((kmCompareValues(x, y) > 0) || x.isNan()) {
            y
        } else if ((kmCompareValues(y, x) > 0) || y.isNan()) {
            x
        } else if (x.isSignPositive()) {
            y
        } else {
            x
        }
    return res.canonicalize()
}
