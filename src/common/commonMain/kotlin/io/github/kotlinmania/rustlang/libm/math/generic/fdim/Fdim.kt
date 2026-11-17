// port-lint: source math/generic/fdim.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fdim

import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float

public fun <Int, SignedInt, F : Float<F, Int, SignedInt>> fdim(x: F, y: F): F =
    if ((kmCompareValues(x, y) <= 0)) {
        F.ZERO
    } else {
        x - y
    }
