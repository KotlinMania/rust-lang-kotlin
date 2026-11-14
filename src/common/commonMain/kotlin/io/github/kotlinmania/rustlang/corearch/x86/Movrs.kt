// port-lint: source x86/movrs.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// Read-shared move intrinsics

public expect fun prefetchrs(p: ConstPtr)

/**
 * Prefetches the cache line that contains address `p`, with an indication that the source memory
 * location is likely to become read-shared by multiple processors, i.e., read in the future by at
 * least one other processor before it is written, assuming it is ever written in the future.
 *
 * Note: this intrinsic is safe to use even though it takes a raw pointer argument. In general, this
 * cannot change the behavior of the program, including not trapping on invalid pointers.
 */
public fun mPrefetchrs(p: ConstPtr) {
    prefetchrs(p)
}
