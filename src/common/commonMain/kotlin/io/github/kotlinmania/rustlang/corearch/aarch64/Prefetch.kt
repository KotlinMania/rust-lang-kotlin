// port-lint: source aarch64/prefetch.rs
package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public expect fun externPrefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int)

/**
 * See [`prefetch`](fn._prefetch.html).
 */
public val PREFETCH_READ: Int = 0

/**
 * See [`prefetch`](fn._prefetch.html).
 */
public val PREFETCH_WRITE: Int = 1

/**
 * See [`prefetch`](fn._prefetch.html).
 */
public val PREFETCH_LOCALITY0: Int = 0

/**
 * See [`prefetch`](fn._prefetch.html).
 */
public val PREFETCH_LOCALITY1: Int = 1

/**
 * See [`prefetch`](fn._prefetch.html).
 */
public val PREFETCH_LOCALITY2: Int = 2

/**
 * See [`prefetch`](fn._prefetch.html).
 */
public val PREFETCH_LOCALITY3: Int = 3

// Note: Replace this with the standard ACLE __pld/__pldx/__pli/__plix intrinsics

/**
 * Fetch the cache line that contains address `p` using the given `RW` and `LOCALITY`.
 *
 * The `RW` must be one of:
 *
 * * [`prefetchRead`](constant._PREFETCH_READ.html): the prefetch is preparing
 *   for a read.
 *
 * * [`prefetchWrite`](constant._PREFETCH_WRITE.html): the prefetch is preparing
 *   for a write.
 *
 * The `LOCALITY` must be one of:
 *
 * * [`prefetchLocality0`](constant._PREFETCH_LOCALITY0.html): Streaming or
 *   non-temporal prefetch, for data that is used only once.
 *
 * * [`prefetchLocality1`](constant._PREFETCH_LOCALITY1.html): Fetch into level 3 cache.
 *
 * * [`prefetchLocality2`](constant._PREFETCH_LOCALITY2.html): Fetch into level 2 cache.
 *
 * * [`prefetchLocality3`](constant._PREFETCH_LOCALITY3.html): Fetch into level 1 cache.
 *
 * The prefetch memory instructions signal to the memory system that memory accesses
 * from a specified address are likely to occur in the near future. The memory system
 * can respond by taking actions that are expected to speed up the memory access when
 * they do occur, such as preloading the specified address into one or more caches.
 * Because these signals are only hints, it is valid for a particular CPU to treat
 * any or all prefetch instructions as a NOP.
 *
 * [Arm's documentation](https://developer.arm.com/documentation/den0024/a/the-a64-instruction-set/memory-access-instructions/prefetching-memory?lang=en)
 */
public fun prefetch(p: ConstPtr, rw: Int, locality: Int) {
    run {
        check(0 <= rw && rw < (1 shl 1)) { "RW doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= locality && locality < (1 shl 2)) { "LOCALITY doesn't fit in 2 bits (unsigned)" }
    }
    externPrefetch(p, rw, locality, 1)
}
