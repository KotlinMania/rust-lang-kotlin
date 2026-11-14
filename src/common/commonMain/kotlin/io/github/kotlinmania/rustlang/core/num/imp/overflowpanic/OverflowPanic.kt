// port-lint: source num/imp/overflow_panic.rs
package io.github.kotlinmania.rustlang.core.num.imp.overflowpanic

// Functions for panicking on overflow.
//
// In particular, these are used by the `strict_` methods on integers.

public fun add(): Nothing {
    error("attempt to add with overflow")
}

public fun sub(): Nothing {
    error("attempt to subtract with overflow")
}

public fun mul(): Nothing {
    error("attempt to multiply with overflow")
}

public fun rem(): Nothing {
    error("attempt to calculate the remainder with overflow")
}

public fun neg(): Nothing {
    error("attempt to negate with overflow")
}

public fun shr(): Nothing {
    error("attempt to shift right with overflow")
}

public fun shl(): Nothing {
    error("attempt to shift left with overflow")
}

public fun pow(): Nothing {
    error("attempt to exponentiate with overflow")
}

public fun castInteger(): Nothing {
    error("attempt to cast integer with overflow")
}
