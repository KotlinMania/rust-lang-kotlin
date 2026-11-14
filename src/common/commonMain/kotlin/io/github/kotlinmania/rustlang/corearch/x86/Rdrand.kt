// port-lint: source x86/rdrand.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// RDRAND and RDSEED instructions for returning random numbers from an Intel
// on-chip hardware random number generator which has been seeded by an
// on-chip entropy source.

public expect fun x86Rdrand16Step(): Pair<UShort, Int>

public expect fun x86Rdrand32Step(): Pair<UInt, Int>

public expect fun x86Rdseed16Step(): Pair<UShort, Int>

public expect fun x86Rdseed32Step(): Pair<UInt, Int>

/**
 * Read a hardware generated 16-bit random value and store the result in val.
 * Returns 1 if a random value was generated, and 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdrand16_step)
 */
public fun rdrand16Step(value: RustMutableReference<UShort>): Int {
    val value1 = value
    val (v, flag) = x86Rdrand16Step()
    (value1).write(v)
    return flag
}

/**
 * Read a hardware generated 32-bit random value and store the result in val.
 * Returns 1 if a random value was generated, and 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdrand32_step)
 */
public fun rdrand32Step(value: RustMutableReference<UInt>): Int {
    val value1 = value
    val (v, flag) = x86Rdrand32Step()
    (value1).write(v)
    return flag
}

/**
 * Read a 16-bit NIST SP800-90B and SP800-90C compliant random value and store
 * in val. Return 1 if a random value was generated, and 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdseed16_step)
 */
public fun rdseed16Step(value: RustMutableReference<UShort>): Int {
    val value1 = value
    val (v, flag) = x86Rdseed16Step()
    (value1).write(v)
    return flag
}

/**
 * Read a 32-bit NIST SP800-90B and SP800-90C compliant random value and store
 * in val. Return 1 if a random value was generated, and 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_rdseed32_step)
 */
public fun rdseed32Step(value: RustMutableReference<UInt>): Int {
    val value1 = value
    val (v, flag) = x86Rdseed32Step()
    (value1).write(v)
    return flag
}
