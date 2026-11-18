package io.github.kotlinmania.rustlang.alloc.collections.vecdeque

import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

/**
 * Turn a [VecDeque.T] into a [Vec.T].
 *
 * This never needs to re-allocate, but does need to do *O*(*n*) data movement if
 * the circular buffer doesn't happen to be at the beginning of the allocation.
 *
 * # Examples
 *
 * ```kotlin
 * import core.ptr
 *
 * // This one is *O*(1).
 * val deque: VecDeque = (1 until 5).collect()
 * val ptr = deque.asSlices().first.asPtr()
 * val vec = deque.toMutableList()
 * check(vec == mutableListOf(1, 2, 3, 4))
 * check(vec.asPtr() == ptr)
 * // This one needs data rearranging.
 * var deque1: VecDeque = (1 until 5).collect()
 * deque1.pushFront(9)
 * deque1.pushFront(8)
 * val ptr1 = deque1.asSlices().second.asPtr()
 * val vec1 = deque1.toMutableList()
 * check(vec1 == mutableListOf(8, 9, 1, 2, 3, 4))
 * check(vec1.asPtr() == ptr1)
 * ```
 */
public fun <T, A : Allocator> fromVecTA(other: VecDeque<T, A>): List<T> {
    other.makeContiguous()
    // ignore-tidy-undocumented-unchecked
    return run {
        val other1 = other
        val buf = other1.buf.ptr()
        val len = other1.len()
        val cap = other1.capacity()
        val alloc = read(other1.allocator())
        if (!other1.head.isZero()) {
            copy(buf.add(other1.head.asIndex()), buf, len)
        }
        Vec.fromRawPartsIn(buf, len, cap, alloc)
    }
}
