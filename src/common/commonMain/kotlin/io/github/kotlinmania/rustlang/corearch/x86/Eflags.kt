// port-lint: source x86/eflags.rs
package io.github.kotlinmania.rustlang.corearch.x86

// `i386` intrinsics

/**
 * Reads EFLAGS.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__readeflags)
 */
public fun readeflags(): UInt {
    val eflags: UInt
    Unit
    return eflags
}

/**
 * Write EFLAGS.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__writeeflags)
 */
public fun writeeflags(eflags: UInt) {
    Unit
}

/**
 * Write EFLAGS.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__writeeflags)
 */
public fun writeeflags(eflags: ULong) {
    Unit
}
