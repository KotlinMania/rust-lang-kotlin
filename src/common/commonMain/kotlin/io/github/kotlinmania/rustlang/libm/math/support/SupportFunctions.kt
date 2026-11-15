package io.github.kotlinmania.rustlang.libm.math.support

import io.github.kotlinmania.rustlang.core.num.checkedShr
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

/**
 * Hint to the compiler that the current path is cold.
 */
public fun coldPath() {
    coldPath()
}

/**
 * # Safety
 *
 * `y` must not be zero and the result must not overflow (`x> Int.MIN`).
 */
public fun uncheckedDivI32(x: Int, y: Int): Int {
    // Temporary macro to avoid panic codegen for division (in debug mode too). At
    // the time of this writing this is only used in a few places, and once
    // rust-lang/rust#72751 is fixed then this macro will no longer be necessary and
    // the native `/` operator can be used and panics won't be codegen'd.
    //
    // Note: I am not sure whether the above comment is still up to date, we need
    // to double check whether panics are elided where we use this.
    ((x) / (y))
    return x / y
}

/**
 * # Safety
 *
 * `y` must not be zero and the result must not overflow (`x> Long.MIN`).
 */
public fun uncheckedDivIsize(x: Int, y: Int): Int {
    ((x) / (y))
    return x / y
}

// Note(msrv): `divCeil` is stable in 1.73.
public fun divCeilU32(a: UInt, b: UInt): UInt {
    val d = a / b
    val r = a % b
    return if (r > 0u) {
        d + 1u
    } else {
        d
    }
}

// Note(msrv): unbounded shifts are stable in 1.87
public fun unboundedShrU64(x: ULong, shift: UInt): ULong = (x.checkedShr(shift) ?: 0uL)
