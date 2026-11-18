// port-lint: source x86_64/cmpxchg16b.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.isMultipleOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.atomicCompareExchange

// even without panics, this helps for Miri backtraces

/**
 * Compares and exchange 16 bytes (128 bits) of data atomically.
 *
 * This intrinsic corresponds to the `cmpxchg16b` instruction on `x8664`
 * processors. It performs an atomic compare-and-swap, updating the `ptr`
 * memory location to `val` if the current value in memory equals `old`.
 *
 * # Return value
 *
 * This function returns the previous value at the memory location. If it is
 * equal to `old` then the memory was updated to `new`.
 *
 * # Memory Orderings
 *
 * This atomic operation has the same semantics of memory orderings as
 * `AtomicUsize.compareExchange` does, only operating on 16 bytes of memory
 * instead of just a pointer.
 *
 * The failure ordering must be [Ordering.SeqCst], [Ordering.Acquire] or
 * [Ordering.Relaxed].
 *
 * For more information on memory orderings here see the `compareExchange`
 * documentation for other `Atomic *` types in the standard library.
 *
 * # Unsafety
 *
 * This method is unchecked because it takes a raw pointer and will attempt to
 * read and possibly write the memory at the pointer. The pointer must also be
 * aligned on a 16-byte boundary.
 *
 * This method also requires the `cmpxchg16b` CPU feature to be available at
 * runtime to work correctly. If the CPU running the binary does not actually
 * support `cmpxchg16b` and the program enters an execution path that
 * eventually would reach this function the behavior is undefined.
 */
public fun cmpxchg16b(dst: MutPtr, old: U128, new: U128, success: Ordering, failure: Ordering): U128 {
    check((dst).rawAddress.toInt().isMultipleOf(16))
    val res = atomicCompareExchange(dst, old, new, success, failure)
    return res.unwrapOrElse({ x -> x })
}
