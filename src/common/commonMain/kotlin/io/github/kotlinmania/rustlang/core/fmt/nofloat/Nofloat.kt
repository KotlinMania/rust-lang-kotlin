// port-lint: source fmt/nofloat.rs
package io.github.kotlinmania.rustlang.core.fmt.nofloat

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.fmt.Formatter
import io.github.kotlinmania.rustlang.fmt.Result

public fun F16.fmt(fmt: Formatter): Result {
    error("floating point fmt support is turned off")
}

public fun kotlin.Float.fmt(fmt: Formatter): Result {
    error("floating point fmt support is turned off")
}

public fun Double.fmt(fmt: Formatter): Result {
    error("floating point fmt support is turned off")
}

public fun F128.fmt(fmt: Formatter): Result {
    error("floating point fmt support is turned off")
}
