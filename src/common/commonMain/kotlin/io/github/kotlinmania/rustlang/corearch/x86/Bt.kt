// port-lint: source x86/bt.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.arch.asm

/**
 * Returns the bit in position `b` of the memory addressed by `p`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittest)
 */
public fun bittest(p: ConstPtr, b: Int): UByte {
    val r: UByte
    asm(bt("btl"), "setc {r}", p, reg, p, b, reg, b, r, out, regByte, r, options(readonly, nostack, pure, attSyntax))
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then sets the bit to `1`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandset)
 */
public fun bittestandset(p: MutPtr, b: Int): UByte {
    val r: UByte
    asm(bt("btsl"), "setc {r}", p, reg, p, b, reg, b, r, out, regByte, r, options(nostack, attSyntax))
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then resets that bit to `0`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandreset)
 */
public fun bittestandreset(p: MutPtr, b: Int): UByte {
    val r: UByte
    asm(bt("btrl"), "setc {r}", p, reg, p, b, reg, b, r, out, regByte, r, options(nostack, attSyntax))
    return r
}

/**
 * Returns the bit in position `b` of the memory addressed by `p`, then inverts that bit.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_bittestandcomplement)
 */
public fun bittestandcomplement(p: MutPtr, b: Int): UByte {
    val r: UByte
    asm(bt("btcl"), "setc {r}", p, reg, p, b, reg, b, r, out, regByte, r, options(nostack, attSyntax))
    return r
}
