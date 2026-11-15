// port-lint: source int/udiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.udivmodsi4

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

/**
 * Returns `n / d` and sets `* rem = n % d`
 */
public fun udivmodsi4(n: UInt, d: UInt, rem: RustMutableReference<UInt>?): UInt =
    io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv
        .udivmodsi4(n, d, rem)
