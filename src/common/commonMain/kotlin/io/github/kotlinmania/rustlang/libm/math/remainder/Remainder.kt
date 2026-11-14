// port-lint: source math/remainder.rs
package io.github.kotlinmania.rustlang.libm.math.remainder

import io.github.kotlinmania.rustlang.libm.math.remquo.remquo

public fun remainder(x: Double, y: Double): Double {
    val (result, _) = remquo(x, y)
    return result
}
