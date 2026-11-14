// port-lint: source int/udiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.udivmoddi4

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

/**
 * Returns `n / d` and sets `* rem = n % d`
 */
public fun udivmoddi4(n: ULong, d: ULong, rem: RustMutableReference<ULong>?): ULong = io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.udivmoddi4(n, d, rem)
