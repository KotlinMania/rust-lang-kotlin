package io.github.kotlinmania.rustlang.alloc.rawvec

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind.*
import io.github.kotlinmania.rustlang.alloc.handleAllocError
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.result.Result

// One central function responsible for reporting capacity overflows. This'll
// ensure that the code generation related to these panics is minimal as there's
// only one location which panics rather than a bunch throughout the module.
public fun capacityOverflow(): Nothing {
    error("capacity overflow")
}

/**
 * `Cap(cap)`, except if `T` is a ZST then `Cap.ZERO`.
 *
 * # Safety: cap must be <= `Long.MAX`.
 */
public fun <T> newCap(cap: Int): Cap {
    // SAFETY: Upheld by caller.
    return if (T.IS_ZST) {
        ZERO_CAP
    } else {
        Cap.newUnchecked(cap)
    }
}

// Tiny Vecs are dumb. Skip to:
// - 8 if the element size is 1, because any heap allocator is likely
// to round up a request of less than 8 bytes to at least 8 bytes.
// - 4 if elements are moderate-sized (<= 1 KiB).
// - 1 otherwise, to avoid wasting too much space for very short Vecs.
public fun minNonZeroCap(size: Int): Int =
    if (size == 1) {
        8
    } else if (size <= 1024) {
        4
    } else {
        1
    }

// Central function for reserve error handling.
public fun handleError(e: TryReserveError): Nothing =
    when (val matchVal = e.kind()) {
        CapacityOverflow -> capacityOverflow()
        is AllocError -> {
            val layout = matchVal.layout
            handleAllocError(layout)
        }
    }

public fun layoutArray(cap: Int, elemLayout: Layout): Result<Layout, TryReserveError> {
    // This is only used with `elemLayout`s which are those of real rust types,
    // which lets us use the much-simpler `repeatPacked`.
    check(elemLayout.size() == elemLayout.padToAlign().size())
    return elemLayout.repeatPacked(cap).mapErr({ CapacityOverflow.into() })
}
