// port-lint: source math/scalbn.rs
package io.github.kotlinmania.rustlang.libm.math.scalbn

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

public fun scalbnf16(x: F16, n: Int): F16 = io.github.kotlinmania.rustlang.libm.math.generic.scalbn(x.toDouble(), n)

public fun scalbnf(x: kotlin.Float, n: Int): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.scalbn(x.toDouble(), n)

public fun scalbn(x: Double, n: Int): Double = io.github.kotlinmania.rustlang.libm.math.generic.scalbn(x, n)

public fun scalbnf128(x: F128, n: Int): F128 = io.github.kotlinmania.rustlang.libm.math.generic.scalbn(x.toDouble(), n)
