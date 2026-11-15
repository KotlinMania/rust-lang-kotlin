// port-lint: source math/lgamma.rs
package io.github.kotlinmania.rustlang.libm.math.lgamma

import io.github.kotlinmania.rustlang.math.lgammar.lgammaR

/**
 * The natural logarithm of the
 * [Gamma function](https://en.wikipedia.org/wiki/GammaFunction) (f64).
 */
public fun lgamma(x: Double): Double = lgammaR(x).first
