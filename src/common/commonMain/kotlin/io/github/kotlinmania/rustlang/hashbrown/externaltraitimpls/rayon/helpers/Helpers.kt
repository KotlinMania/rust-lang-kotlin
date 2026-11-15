// port-lint: source external_trait_impls/rayon/helpers.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.helpers

import io.github.kotlinmania.rustlang.alloc.collections.linkedlist.LinkedList
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.rayon.iter.IntoParallelIterator

// yes, we need linked list here for efficient appending!

/**
 * Helper for collecting parallel iterators to an intermediary
 */
public fun <I : IntoParallelIterator> collect(iter: I): Pair<LinkedList<List<Item>>, Int> {
    val list = iter.intoParIter().collectVecList()
    val len = list.iter().map(Vec::len).sum()
    return Pair(list, len)
}
