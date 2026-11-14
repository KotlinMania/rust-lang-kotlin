// port-lint: source vec/spec_from_iter.rs
package io.github.kotlinmania.rustlang.alloc.vec.specfromiter

import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.alloc.vec.intoiter.IntoIter
import io.github.kotlinmania.rustlang.alloc.vec.specfromiternested.SpecFromIterNested
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * Specialization trait used for Vec.fromIter
 *
 * ## The delegation graph:
 *
 * ```text
 * +-------------+
 * |FromIterator |
 * +-+-----------+
 *   |
 *   v
 * +-+---------------------------------+  +---------------------+
 * |SpecFromIter                    +---->+SpecFromIterNested   |
 * |where I:                        |  |  |where I:             |
 * |  Iterator (default)------------+  |  |  Iterator (default) |
 * |  vec.IntoIter                 |  |  |  TrustedLen         |
 * |  InPlaceCollect--(fallback to)-+  |  +---------------------+
 * +-----------------------------------+
 * ```
 */
internal interface SpecFromIter<T, I, SelfType> {
    public fun fromIter(iter: I): SelfType
}

public fun <T, I> specFromIterFromIterVecT(iterator: I): List<T> where I : Iterator<T> = SpecFromIterNested.fromIter(iterator)

public fun <T> specFromIterFromIterVecT(iterator: IntoIter<T>): List<T> {
    // A common case is passing a vector into a function which immediately
    // re-collects into a vector. We can short circuit this if the IntoIter
    // has not been advanced at all.
    // When it has been advanced We can also reuse the memory and move the data to the front.
    // But we only do so when the resulting Vec wouldn't have more unused capacity
    // than creating it through the generic FromIterator implementation would. That limitation
    // is not strictly necessary as Vec's allocation behavior is intentionally unspecified.
    // But it is a conservative choice.
    val hasAdvanced = iterator.buf != iterator.ptr
    if (!hasAdvanced || iterator.len() >= iterator.cap / 2) {
        val it = iterator
        // ignore-tidy-undocumented-unchecked
        run {
            if (hasAdvanced) {
                ptr.copy(it.ptr.asPtr(), it.buf.asPtr(), it.len())
            }
            return Vec.fromParts(it.buf, it.len(), it.cap)
        }
    }
    val vec = mutableListOf<T>()
    // must delegate to specExtend() since extend() itself delegates
    // to specFrom for empty Vecs
    vec.specExtend(iterator)
    return vec
}
