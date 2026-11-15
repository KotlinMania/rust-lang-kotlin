// port-lint: source int/mul.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.mul.rustu128mulo

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public fun rustU128Mulo(a: U128, b: U128, oflow: RustMutableReference<Int>): U128 =
    io.github.kotlinmania.rustlang.compilerbuiltins.int.mul
        .rustU128Mulo(a, b, oflow)
