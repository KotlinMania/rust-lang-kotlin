// port-lint: source slice/sort/unstable/heapsort.rs
package io.github.kotlinmania.rustlang.core.slice.sort.unstable.heapsort

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// This module contains a branchless heapsort as fallback for unstable quicksort.

/**
 * Sorts `v` using heapsort, which guarantees *O*(*n* \* log(*n*)) worst-case.
 *
 * Never inline this, it sits the main hot-loop in `recurse` and is meant as unlikely algorithmic
 * fallback.
 */
internal fun <T> heapsort(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    val len = v.size
    for (i in (0 until len + len / 2).rev()) {
        val siftIdx =
            if (i >= len) {
                i - len
            } else {
                v.swap(0, i)
                0
            }
        // SAFETY: The above calculation ensures that `siftIdx` is either 0 or
        // `(len ..(len + (len / 2))) - len`, which simplifies to `0 ..(len / 2)`.
        // This guarantees the required `siftIdx <= len`.
        run {
            siftDown(v.subList(0, minOf(i, len)), siftIdx, isLess)
        }
    }
}

// This binary heap respects the invariant `parent >= child`.
//
// SAFETY: The caller has to guarantee that `node <= v.len()`.
public fun <T> siftDown(v: MutableList<T>, node: Int, isLess: (T, T) -> Boolean) {
    var node1 = node
    // SAFETY: See function safety.
    run {}
    val len = v.size
    val vBase = v.asMutPtr()
    while (true) {
        // Children of `node`.
        var child = 2 * node1 + 1
        if (child >= len) {
            break
        }
        // SAFETY: The invariants and checks guarantee that both node and child are in-bounds.
        run {
            // Choose the greater child.
            if (child + 1 < len) {
                // We need a branch to be sure not to out-of-bounds index,
                // but it's highly predictable.  The comparison, however,
                // is better done branchless, especially for primitives.
                child += isLess((ObjectSlotLayout<T>()).readNative((MutPtr((vBase).rawAddress + ((child).toLong() * (ObjectSlotLayout<T>()).strideBytes))).rawAddress), (ObjectSlotLayout<T>()).readNative((MutPtr((vBase).rawAddress + ((child + 1).toLong() * (ObjectSlotLayout<T>()).strideBytes))).rawAddress)).toInt()
            }
            // Stop if the invariant holds at `node`.
            if (!isLess((ObjectSlotLayout<T>()).readNative((MutPtr((vBase).rawAddress + ((node1).toLong() * (ObjectSlotLayout<T>()).strideBytes))).rawAddress), (ObjectSlotLayout<T>()).readNative((MutPtr((vBase).rawAddress + ((child).toLong() * (ObjectSlotLayout<T>()).strideBytes))).rawAddress))) {
                break
            }
            swapNonoverlapping(MutPtr((vBase).rawAddress + ((node1).toLong() * (ObjectSlotLayout<T>()).strideBytes)), MutPtr((vBase).rawAddress + ((child).toLong() * (ObjectSlotLayout<T>()).strideBytes)), 1)
        }
        node1 = child
    }
}
