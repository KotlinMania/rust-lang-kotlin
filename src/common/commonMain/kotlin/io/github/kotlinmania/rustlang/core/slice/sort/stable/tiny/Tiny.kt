// port-lint: source slice/sort/stable/tiny.rs
package io.github.kotlinmania.rustlang.core.slice.sort.stable.tiny

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.ptr.swapNonoverlapping
import io.github.kotlinmania.rustlang.slice.sort.stable.merge.merge

// Binary-size optimized mergesort inspired by https://github.com/voultapher/tiny-sort-rs.

/**
 * Tiny recursive top-down merge sort optimized for binary size. It has no adaptiveness whatsoever,
 * no run detection, etc.
 */
public fun <T> mergesort(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, isLess: (T, T) -> Boolean) {
    val len = v.size
    if (len > 2) {
        val mid = len / 2
        // SAFETY: mid is in-bounds.
        run {
            // Sort the left half recursively.
            mergesort(v.getUncheckedMut(0 until mid), scratch, isLess)
            // Sort the right half recursively.
            mergesort(v.getUncheckedMut(mid until Int.MAX_VALUE), scratch, isLess)
        }
        merge.merge(v, scratch, mid, isLess)
    } else if (len == 2) {
        // SAFETY: We checked the len, the pointers we create are valid and don't overlap.
        run {
            val vBase = v.asMutPtr()
            val vA = vBase
            val vB = MutPtr((vBase).rawAddress + ((1).toLong() * (ObjectSlotLayout<T>()).strideBytes))
            if (isLess((ObjectSlotLayout<T>()).readNative((vB).rawAddress), (ObjectSlotLayout<T>()).readNative((vA).rawAddress))) {
                swapNonoverlapping(vA, vB, 1)
            }
        }
    }
}
