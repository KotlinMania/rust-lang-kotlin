// port-lint: source math/lgammaf.rs
package io.github.kotlinmania.rustlang.libm.math.lgammaf

import io.github.kotlinmania.rustlang.math.lgammafr.lgammafR

/**
 * The natural logarithm of the
 * [Gamma function](https://en.wikipedia.org/wiki/GammaFunction) (f32).
 */
public fun lgammaf(x: kotlin.Float): kotlin.Float = lgammafR(x).first
