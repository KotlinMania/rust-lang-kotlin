// port-lint: source float/conv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.floattikf

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.I128

// Conversions from unsigned integers to floats.
// Conversions from signed integers to floats.
// Conversions from floats to unsigned integers.
// Conversions from floats to signed integers.
public fun floattikf(i: I128): F128 = io.github.kotlinmania.rustlang.float.conv.floattikf(i)
