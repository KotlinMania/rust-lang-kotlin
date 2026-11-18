// port-lint: source fmt.rs
package io.github.kotlinmania.rustlang.coresimd.fmt

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.coresimd.Simd

/**
 * A `Simd<T, N>` has a debug format like the one for `[T]`:
 * ```kotlin
 * val floats = Simd.splat(-1.0)
 * check("${List(4) { -1.0 }}" == "$floats")
 * ```
 */
public fun <Mask, T> Simd<T>.fmt(f: StringBuilder, n: Int = 0): Result<Unit, Error> = List.fmt(this.asArray(), f)
