// port-lint: source math/generic/fmin.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fmin

import io.github.kotlinmania.rustlang.core.cmp.compareNaturalOrder
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

// IEEE 754-2008 `minNum`. This has been superseded by IEEE 754-2019 `minimumNumber`.
//
// Per the spec, returns the canonicalized result of:
// - `x` if `x<y`
// - `y` if `y<x`
// - The other number if one is NaN
// - Otherwise, either `x` or `y`, canonicalized
// - -0.0 and +0.0 may be disregarded (unlike newer operations)
//
// We do not treat sNaN and qNaN differently; even though IEEE technically requires this, (a call
// with sNaN should return qNaN rather than the other result), it breaks associativity so isn't
// desired behavior. C23 does not differentiate between sNaN and qNaN, so we do not either. More
// on the problems with `minNum` [here][minnum-removal].
//
// IEEE also specifies that a sNaN in either argument should signal invalid, but we do not
// implement this.
//
// [minnum-removal]: https://grouper.ieee.org/groups/msc/ANSI_IEEE-Std-754-2019/background/minNumMaxNumRemovalDemotionV3.pdf

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fmin(x: F, y: F): F {
    val res =
        if (y.isNan() || (compareNaturalOrder(x, y) < 0)) {
            x
        } else {
            y
        }
    return res.canonicalize()
}
