// port-lint: source x86_64/xsave.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// `x8664`s `xsave` and `xsaveopt` target feature intrinsics

public expect fun xsave64(p: MutPtr, hi: UInt, lo: UInt)

public expect fun xrstor64(p: ConstPtr, hi: UInt, lo: UInt)

public expect fun xsaveopt64(p: MutPtr, hi: UInt, lo: UInt)

public expect fun xsavec64(p: MutPtr, hi: UInt, lo: UInt)

public expect fun xsaves64(p: MutPtr, hi: UInt, lo: UInt)

public expect fun xrstors64(p: ConstPtr, hi: UInt, lo: UInt)

/**
 * Performs a full or partial save of the enabled processor states to memory at
 * `memAddr`.
 *
 * State is saved based on bits `[62: 0]` in `saveMask` and XCR0.
 * `memAddr` must be aligned on a 64-byte boundary.
 *
 * The format of the XSAVE area is detailed in Section 13.4, “XSAVE Area,” of
 * Intel® 64 and IA-32 Architectures Software Developer’s Manual, Volume 1.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsave64)
 */
public fun xsave64(memAddr: MutPtr, saveMask: ULong) {
    xsave64(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
}

/**
 * Performs a full or partial restore of the enabled processor states using
 * the state information stored in memory at `memAddr`.
 *
 * State is restored based on bits `[62: 0]` in `rsMask`, `XCR0`, and
 * `memAddr.HEADER.XSTATE_BV`. `memAddr` must be aligned on a 64-byte
 * boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xrstor64)
 */
public fun xrstor64(memAddr: ConstPtr, rsMask: ULong) {
    xrstor64(memAddr, (rsMask shr 32).toUInt(), rsMask.toUInt())
}

/**
 * Performs a full or partial save of the enabled processor states to memory at
 * `memAddr`.
 *
 * State is saved based on bits `[62: 0]` in `saveMask` and `XCR0`.
 * `memAddr` must be aligned on a 64-byte boundary. The hardware may optimize
 * the manner in which data is saved. The performance of this instruction will
 * be equal to or better than using the `XSAVE64` instruction.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsaveopt64)
 */
public fun xsaveopt64(memAddr: MutPtr, saveMask: ULong) {
    xsaveopt64(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
}

/**
 * Performs a full or partial save of the enabled processor states to memory
 * at `memAddr`.
 *
 * `xsavec` differs from `xsave` in that it uses compaction and that it may
 * use init optimization. State is saved based on bits `[62: 0]` in `saveMask`
 * and `XCR0`. `memAddr` must be aligned on a 64-byte boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsavec64)
 */
public fun xsavec64(memAddr: MutPtr, saveMask: ULong) {
    xsavec64(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
}

/**
 * Performs a full or partial save of the enabled processor states to memory at
 * `memAddr`
 *
 * `xsaves` differs from xsave in that it can save state components
 * corresponding to bits set in `IA32_XSS` `MSR` and that it may use the
 * modified optimization. State is saved based on bits `[62: 0]` in `saveMask`
 * and `XCR0`. `memAddr` must be aligned on a 64-byte boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsaves64)
 */
public fun xsaves64(memAddr: MutPtr, saveMask: ULong) {
    xsaves64(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
}

/**
 * Performs a full or partial restore of the enabled processor states using the
 * state information stored in memory at `memAddr`.
 *
 * `xrstors` differs from `xrstor` in that it can restore state components
 * corresponding to bits set in the `IA32_XSS` `MSR`; `xrstors` cannot restore
 * from an `xsave` area in which the extended region is in the standard form.
 * State is restored based on bits `[62: 0]` in `rsMask`, `XCR0`, and
 * `memAddr.HEADER.XSTATE_BV`. `memAddr` must be aligned on a 64-byte
 * boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xrstors64)
 */
public fun xrstors64(memAddr: ConstPtr, rsMask: ULong) {
    xrstors64(memAddr, (rsMask shr 32).toUInt(), rsMask.toUInt())
}
