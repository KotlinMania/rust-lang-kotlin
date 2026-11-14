// port-lint: source collections/vec_deque/spec_from_iter.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque.specfromiter

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.intoiter.IntoIter
import io.github.kotlinmania.rustlang.alloc.vec.Vec

/**
 * Specialization trait used for `VecDeque.fromIter`
 */
public interface SpecFromIter<T, I, SelfType> {
    public fun specFromIter(iter: I): SelfType
}

public fun <T, I> specFromIterVecDequeT(iterator: I): VecDeque<T> where I : Iterator<T> {
    // Since converting is O(1) now, just re-use the `List` logic for
    // anything where we can't do something extra-special for `VecDeque`,
    // especially as that could save us some monomorphization work
    // if one uses the same iterators (like slice ones) with both.
    return Vec.fromIter(iterator).into()
}

public fun <T> specFromIterVecDequeT(iterator: IntoIter<T>): VecDeque<T> = iterator.intoVecdeque()

public fun <T> specFromIterVecDequeT(iterator: io.github.kotlinmania.rustlang.alloc.collections.vecdeque.intoiter.IntoIter<T>): VecDeque<T> = iterator.intoVecdeque()
