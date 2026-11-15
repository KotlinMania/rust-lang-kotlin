// port-lint: source math/generic/fdim.rs
package io.github.kotlinmania.rustlang.libm.math.generic.fdim

import io.github.kotlinmania.rustlang.core.cmp.compareNaturalOrder
import io.github.kotlinmania.rustlang.math.support.floattraits.Float

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fdim(x: F, y: F): F =
    if ((compareNaturalOrder(x, y) <= 0)) {
        F.ZERO
    } else {
        x - y
    }
