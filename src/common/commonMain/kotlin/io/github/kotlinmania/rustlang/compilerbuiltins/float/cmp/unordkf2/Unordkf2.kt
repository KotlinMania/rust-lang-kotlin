// port-lint: source float/cmp.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.unordkf2

import io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp.CmpResult
import io.github.kotlinmania.rustlang.core.F128

public fun unordkf2(a: F128, b: F128): CmpResult =
    io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp
        .unordkf2(a, b)
