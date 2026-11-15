// port-lint: source math/remainderf.rs
package io.github.kotlinmania.rustlang.libm.math.remainderf

import io.github.kotlinmania.rustlang.math.remquof.remquof

public fun remainderf(x: kotlin.Float, y: kotlin.Float): kotlin.Float {
    val (result, _) = remquof(x, y)
    return result
}
