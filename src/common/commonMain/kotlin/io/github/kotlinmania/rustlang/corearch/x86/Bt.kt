// port-lint: source x86/bt.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * Returns the bit in position `b` of the memory addressed by `p`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittest)
 */
public fun bittest(p: ConstPtr, b: Int): UByte {
    val r: UByte
    Unit
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then sets the bit to `1`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandset)
 */
public fun bittestandset(p: MutPtr, b: Int): UByte {
    val r: UByte
    Unit
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then resets that bit to `0`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandreset)
 */
public fun bittestandreset(p: MutPtr, b: Int): UByte {
    val r: UByte
    Unit
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then inverts that bit.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandcomplement)
 */
public fun bittestandcomplement(p: MutPtr, b: Int): UByte {
    val r: UByte
    Unit
    return r
}
