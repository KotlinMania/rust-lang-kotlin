package io.github.kotlinmania.rustlang.core.slice.sort.stable

import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit

/**
 * Abstracts owned memory buffer, so that sort code can live in core where no allocation is
 * possible. This trait can then be implemented in a place that has access to allocation.
 */
public interface BufGuard<T, SelfType> {
    /**
     * Creates new buffer that holds at least `capacity` memory.
     */
    public fun withCapacity(capacity: Int): SelfType

    /**
     * Returns mutable access to uninitialized memory owned by the buffer.
     */
    public fun asUninitSliceMut(): MutableList<MaybeUninit<T>>
}
