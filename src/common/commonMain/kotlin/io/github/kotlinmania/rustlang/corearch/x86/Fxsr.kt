// port-lint: source x86/fxsr.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// FXSR floating-point context fast save and restore.

public expect fun fxsave(p: MutPtr)

public expect fun fxrstor(p: ConstPtr)

/**
 * Saves the `x87` FPU, `MMX` technology, `XMM`, and `MXCSR` registers to the
 * 512-byte-long 16-byte-aligned memory region `memAddr`.
 *
 * A misaligned destination operand raises a general-protection (#GP) or an
 * alignment check exception (#AC).
 *
 * See [`Fxsave`][fxsave] and [`Fxrstor`][fxrstor].
 *
 * [fxsave]: http://www.felixcloutier.com/x86/FXSAVE.html
 * [fxrstor]: http://www.felixcloutier.com/x86/FXRSTOR.html
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_fxsave)
 */
public fun fxsave(memAddr: MutPtr) {
    fxsave(memAddr)
}

/**
 * Restores the `XMM`, `MMX`, `MXCSR`, and `x87` FPU registers from the
 * 512-byte-long 16-byte-aligned memory region `memAddr`.
 *
 * The contents of this memory region should have been written to by a
 * previous
 * `_fxsave` or `_fxsave64` intrinsic.
 *
 * A misaligned destination operand raises a general-protection (#GP) or an
 * alignment check exception (#AC).
 *
 * See [`Fxsave`][fxsave] and [`Fxrstor`][fxrstor].
 *
 * [fxsave]: http://www.felixcloutier.com/x86/FXSAVE.html
 * [fxrstor]: http://www.felixcloutier.com/x86/FXRSTOR.html
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_fxrstor)
 */
public fun fxrstor(memAddr: ConstPtr) {
    fxrstor(memAddr)
}
