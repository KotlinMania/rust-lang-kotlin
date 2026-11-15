// port-lint: source int/addsub.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.addsub.rustu128addo

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public fun rustU128Addo(a: U128, b: U128, oflow: RustMutableReference<Int>): U128 = io.github.kotlinmania.rustlang.int.addsub.rustU128Addo(a, b, oflow)
