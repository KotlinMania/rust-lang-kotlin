// port-lint: source float/cmp.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.netf2

import io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.CmpResult
import io.github.kotlinmania.rustlang.core.F128

public fun netf2(a: F128, b: F128): CmpResult =
    io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp
        .netf2(a, b)
