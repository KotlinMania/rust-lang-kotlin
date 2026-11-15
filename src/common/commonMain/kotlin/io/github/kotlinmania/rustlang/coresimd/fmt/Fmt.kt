// port-lint: source fmt.rs
package io.github.kotlinmania.rustlang.coresimd.fmt

import io.github.kotlinmania.rustlang.coresimd.Simd

/**
 * A `Simd<T, N>` has a debug format like the one for `[T]`:
 * ```kotlin
 * val floats = Simd.splat(-1.0)
 * check("${List(4) { -1.0 }}" == "$floats")
 * ```
 */
public fun <Mask, T> Simd<T, N>.fmt(f: StringBuilder, n: Int) {
    List.fmt(this.asArray(), f)
}
