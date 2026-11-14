// port-lint: source x86/clflushopt.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// `CLFLUSHOPT` cache-line flush.

public expect fun clflushopt(p: ConstPtr)

/**
 * Invalidates from every level of the cache hierarchy the cache line that
 * contains `p`.
 *
 * Unlike [mmClflush], `CLFLUSHOPT` is only ordered with respect to older
 * writes to the flushed cache line and with respect to fence/locked
 * operations; it is *not* serialized against other `CLFLUSHOPT`/`CLFLUSH`
 * instructions or unrelated stores. This makes flushing a range of lines
 * substantially faster, but a fence (e.g. [mmSfence] or [mmMfence]) is
 * required afterward to order the flushes against subsequent operations.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_clflushopt)
 *
 * # Safety
 *
 * Unlike the prefetch intrinsics, `CLFLUSHOPT` is subject to all the
 * permission checking and faults associated with a byte load, so `p` must
 * point to a byte that is valid for reads.
 */
public fun mmClflushopt(p: ConstPtr) {
    clflushopt(p)
}
