// port-lint: source int/mul.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.mul.mulodi4

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public fun mulodi4(a: Long, b: Long, oflow: RustMutableReference<Int>): Long =
    io.github.kotlinmania.rustlang.compilerbuiltins.int.mul
        .mulodi4(a, b, oflow)
