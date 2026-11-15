// port-lint: source vec/spec_from_iter_nested.rs
package io.github.kotlinmania.rustlang.alloc.vec.specfromiternested

import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.rawvec.RawVec

/**
 * Another specialization trait for Vec.fromIter
 * necessary to manually prioritize overlapping specializations
 * see [`SpecFromIter`](SpecFromIter) for details.
 */
public interface SpecFromIterNested<T, I, SelfType> {
    public fun fromIter(iter: I): SelfType
}

public fun <T, I> specFromIterNestedFromIterVecT(iterator: I): List<T> where I : Iterator<T> {
    // Unroll the first iteration, as the vector is going to be
    // expanded on this iteration in every case when the iterable is not
    // empty, but the loop in extendDesugared() is not going to see the
    // vector being full in the few subsequent loop iterations.
    // So we get better branch prediction.
    var vector1 =
        when (val matchVal = iterator.next()) {
            null -> { return mutableListOf() }
            else -> {
                val element = matchVal
                val (lower, _) = iterator.sizeHint()
                val initialCapacity = max(RawVec.MIN_NON_ZERO_CAP, lower.saturatingAdd(1))
                var vector = ArrayList<T>(initialCapacity)
                // SAFETY: We requested capacity at least 1
                run {
                    ptr.write(vector.asMutPtr(), element)
                    vector.setLen(1)
                }
                vector
            }
        }
    // must delegate to specExtend() since extend() itself delegates
    // to specFrom for empty Vecs
    List.specExtend(vector1, iterator)
    return vector1
}
