// port-lint: source math/tgammaf.rs
package io.github.kotlinmania.rustlang.libm.math.tgammaf

import io.github.kotlinmania.rustlang.libm.math.tgamma.tgamma

/**
 * The [Gamma function](https://en.wikipedia.org/wiki/GammaFunction) (f32).
 */
public fun tgammaf(x: kotlin.Float): kotlin.Float = tgamma(x.toDouble()).toFloat()
