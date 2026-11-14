// port-lint: source num/imp/overflow_panic.rs
package io.github.kotlinmania.rustlang.core.num.imp.overflowpanic

// Functions for panicking on overflow.
//
// In particular, these are used by the `strict_` methods on integers.

internal fun add(): Nothing {
    error("attempt to add with overflow")
}

internal fun sub(): Nothing {
    error("attempt to subtract with overflow")
}

internal fun mul(): Nothing {
    error("attempt to multiply with overflow")
}

internal fun rem(): Nothing {
    error("attempt to calculate the remainder with overflow")
}

internal fun neg(): Nothing {
    error("attempt to negate with overflow")
}

internal fun shr(): Nothing {
    error("attempt to shift right with overflow")
}

internal fun shl(): Nothing {
    error("attempt to shift left with overflow")
}

internal fun pow(): Nothing {
    error("attempt to exponentiate with overflow")
}

internal fun castInteger(): Nothing {
    error("attempt to cast integer with overflow")
}
