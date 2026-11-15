// port-lint: source float/conv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.fixtfti

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.I128

// Conversions from unsigned integers to floats.
// Conversions from signed integers to floats.
// Conversions from floats to unsigned integers.
// Conversions from floats to signed integers.
public fun fixtfti(f: F128): I128 = io.github.kotlinmania.rustlang.float.conv.fixtfti(f)
