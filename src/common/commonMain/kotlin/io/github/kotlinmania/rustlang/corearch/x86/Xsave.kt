// port-lint: source x86/xsave.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// `i586`s `xsave` and `xsaveopt` target feature intrinsics

public expect fun externXsave(p: MutPtr, hi: UInt, lo: UInt)

public expect fun externXrstor(p: ConstPtr, hi: UInt, lo: UInt)

public expect fun externXsetbv(v: UInt, hi: UInt, lo: UInt)

public expect fun externXgetbv(v: UInt): Long

public expect fun externXsaveopt(p: MutPtr, hi: UInt, lo: UInt)

public expect fun externXsavec(p: MutPtr, hi: UInt, lo: UInt)

public expect fun externXsaves(p: MutPtr, hi: UInt, lo: UInt)

public expect fun externXrstors(p: ConstPtr, hi: UInt, lo: UInt)

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
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsave)
 */
public fun xsave(memAddr: MutPtr, saveMask: ULong) {
    externXsave(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
}

/**
 * Performs a full or partial restore of the enabled processor states using
 * the state information stored in memory at `memAddr`.
 *
 * State is restored based on bits `[62: 0]` in `rsMask`, `XCR0`, and
 * `memAddr.HEADER.XSTATE_BV`. `memAddr` must be aligned on a 64-byte
 * boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xrstor)
 */
public fun xrstor(memAddr: ConstPtr, rsMask: ULong) {
    externXrstor(memAddr, (rsMask shr 32).toUInt(), rsMask.toUInt())
}

/**
 * `XFEATURE_ENABLED_MASK` for `XCR`
 *
 * This intrinsic maps to `XSETBV` instruction.
 */
public val XCR_XFEATURE_ENABLED_MASK: UInt = 0u

/**
 * Copies 64-bits from `val` to the extended control register (`XCR`) specified
 * by `a`.
 *
 * Currently only `XFEATURE_ENABLED_MASK` `XCR` is supported.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsetbv)
 */
public fun xsetbv(a: UInt, value: ULong) {
    externXsetbv(a, (value shr 32).toUInt(), value.toUInt())
}

/**
 * Reads the contents of the extended control register `XCR`
 * specified in `xcrNo`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xgetbv)
 */
public fun xgetbv(xcrNo: UInt): ULong = externXgetbv(xcrNo).toULong()

/**
 * Performs a full or partial save of the enabled processor states to memory at
 * `memAddr`.
 *
 * State is saved based on bits `[62: 0]` in `saveMask` and `XCR0`.
 * `memAddr` must be aligned on a 64-byte boundary. The hardware may optimize
 * the manner in which data is saved. The performance of this instruction will
 * be equal to or better than using the `XSAVE` instruction.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsaveopt)
 */
public fun xsaveopt(memAddr: MutPtr, saveMask: ULong) {
    externXsaveopt(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
}

/**
 * Performs a full or partial save of the enabled processor states to memory
 * at `memAddr`.
 *
 * `xsavec` differs from `xsave` in that it uses compaction and that it may
 * use init optimization. State is saved based on bits `[62: 0]` in `saveMask`
 * and `XCR0`. `memAddr` must be aligned on a 64-byte boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsavec)
 */
public fun xsavec(memAddr: MutPtr, saveMask: ULong) {
    externXsavec(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
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
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xsaves)
 */
public fun xsaves(memAddr: MutPtr, saveMask: ULong) {
    externXsaves(memAddr, (saveMask shr 32).toUInt(), saveMask.toUInt())
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
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_xrstors)
 */
public fun xrstors(memAddr: ConstPtr, rsMask: ULong) {
    externXrstors(memAddr, (rsMask shr 32).toUInt(), rsMask.toUInt())
}
