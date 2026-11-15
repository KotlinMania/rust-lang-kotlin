// port-lint: source float/conv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.floatuntikf

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.U128

// Conversions from unsigned integers to floats.
// Conversions from signed integers to floats.
// Conversions from floats to unsigned integers.
// Conversions from floats to signed integers.
public fun floatuntikf(i: U128): F128 =
    io.github.kotlinmania.rustlang.compilerbuiltins.float.conv
        .floatuntikf(i)
