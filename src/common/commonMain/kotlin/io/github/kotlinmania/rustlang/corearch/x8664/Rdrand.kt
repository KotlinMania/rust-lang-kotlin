// port-lint: source x86_64/rdrand.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// RDRAND and RDSEED instructions for returning random numbers from an Intel
// on-chip hardware random number generator which has been seeded by an
// on-chip entropy source.

public expect fun x86Rdrand64Step(): Pair<ULong, Int>

public expect fun x86Rdseed64Step(): Pair<ULong, Int>

/**
 * Read a hardware generated 64-bit random value and store the result in val.
 * Returns 1 if a random value was generated, and 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdrand64_step)
 */
public fun rdrand64Step(value: RustMutableReference<ULong>): Int {
    val value1 = value
    val (v, flag) = x86Rdrand64Step()
    (value1).write(v)
    return flag
}

/**
 * Read a 64-bit NIST SP800-90B and SP800-90C compliant random value and store
 * in val. Return 1 if a random value was generated, and 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdseed64_step)
 */
public fun rdseed64Step(value: RustMutableReference<ULong>): Int {
    val value1 = value
    val (v, flag) = x86Rdseed64Step()
    (value1).write(v)
    return flag
}
