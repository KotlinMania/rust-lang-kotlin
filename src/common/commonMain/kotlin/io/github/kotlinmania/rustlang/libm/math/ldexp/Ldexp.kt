// port-lint: source math/ldexp.rs
package io.github.kotlinmania.rustlang.libm.math.ldexp

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.math.scalbn.scalbn
import io.github.kotlinmania.rustlang.math.scalbn.scalbnf
import io.github.kotlinmania.rustlang.math.scalbn.scalbnf128
import io.github.kotlinmania.rustlang.math.scalbn.scalbnf16

public fun ldexpf16(x: F16, n: Int): F16 = scalbnf16(x, n)

public fun ldexpf(x: kotlin.Float, n: Int): kotlin.Float = scalbnf(x, n)

public fun ldexp(x: Double, n: Int): Double = scalbn(x, n)

public fun ldexpf128(x: F128, n: Int): F128 = scalbnf128(x, n)
