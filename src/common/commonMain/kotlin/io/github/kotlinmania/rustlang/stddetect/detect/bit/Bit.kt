// port-lint: source detect/bit.rs
package io.github.kotlinmania.rustlang.stddetect.detect.bit

// Bit manipulation utilities.

/**
 * Tests the `bit` of `x`.
 */
public fun test(x: Int, bit: UInt): Boolean {
    check(bit < 32u) { "bit index out-of-bounds" }
    return x and (1 shl bit.toInt()) != 0
}
