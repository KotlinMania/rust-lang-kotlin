// port-lint: source int/udiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.udivmodti4

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

/**
 * Returns `n / d` and sets `* rem = n % d`
 */
public fun udivmodti4(n: U128, d: U128, rem: RustMutableReference<U128>?): U128 = io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.udivmodti4(n, d, rem)
