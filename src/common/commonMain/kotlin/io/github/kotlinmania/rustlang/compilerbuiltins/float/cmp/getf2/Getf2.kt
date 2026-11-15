// port-lint: source float/cmp.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.getf2

import io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.CmpResult
import io.github.kotlinmania.rustlang.core.F128

public fun getf2(a: F128, b: F128): CmpResult =
    io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp
        .getf2(a, b)
