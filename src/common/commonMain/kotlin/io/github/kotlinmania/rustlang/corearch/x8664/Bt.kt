// port-lint: source x86_64/bt.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * Returns the bit in position `b` of the memory addressed by `p`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittest64)
 */
public fun bittest64(p: ConstPtr, b: Long): UByte {
    val r: UByte
    Unit
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then sets the bit to `1`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandset64)
 */
public fun bittestandset64(p: MutPtr, b: Long): UByte {
    val r: UByte
    Unit
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then resets that bit to `0`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandreset64)
 */
public fun bittestandreset64(p: MutPtr, b: Long): UByte {
    val r: UByte
    Unit
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then inverts that bit.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandcomplement64)
 */
public fun bittestandcomplement64(p: MutPtr, b: Long): UByte {
    val r: UByte
    Unit
    return r
}
