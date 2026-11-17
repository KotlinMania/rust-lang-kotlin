// port-lint: source math/generic/fmaximum_num.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fmaximumnum

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

// IEEE 754-2019 `maximumNumber`.
//
// Per the spec, returns:
// - `x` if `x> y`
// - `y` if `y> x`
// - +0.0 if x and y are zero with opposite signs
// - Either `x` or `y` if `x == y` and the signs are the same
// - Non-NaN if one operand is NaN
// - qNaN if both operands are NaNx
//
// Note that the IEEE 754-2019 specifies that a sNaN in either argument should signal invalid,
// but we do not implement this.

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fmaximumNum(x: F, y: F): F {
    val res =
        if ((kmCompareValues(x, y) > 0) || y.isNan()) {
            x
        } else if ((kmCompareValues(y, x) > 0) || x.isNan()) {
            y
        } else if (x.isSignPositive()) {
            x
        } else {
            y
        }
    return res.canonicalize()
}
