// port-lint: source x86/eflags.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.arch.asm

// `i386` intrinsics

/**
 * Reads EFLAGS.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__readeflags)
 */
public fun readeflags(): UInt {
    val eflags: UInt
    asm("pushfd", "pop {}", out, reg, eflags, options(nomem, attSyntax))
    return eflags
}

/**
 * Write EFLAGS.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__writeeflags)
 */
public fun writeeflags(eflags: UInt) {
    asm("push {}", "popfd", reg, eflags, options(nomem, attSyntax))
}

/**
 * Write EFLAGS.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__writeeflags)
 */
public fun writeeflags(eflags: ULong) {
    asm("push {}", "popfq", reg, eflags, options(nomem, attSyntax))
}
