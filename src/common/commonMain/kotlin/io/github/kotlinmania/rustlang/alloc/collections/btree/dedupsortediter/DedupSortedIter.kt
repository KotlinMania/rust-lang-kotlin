// port-lint: source collections/btree/dedup_sorted_iter.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.dedupsortediter

import io.github.kotlinmania.rustlang.core.iter.adapters.peekable.Peekable

/**
 * An iterator for deduping the key of a sorted iterator.
 * When encountering the duplicated key, only the last key-value pair is yielded.
 *
 * Used by [`BTreeMap.bulkBuildFromSortedIter`][1].
 */
internal open class DedupSortedIter<K, V, I>(
    public var iter: Peekable<I>,
) : Iterable<Pair<K, V>> where I : Iterator<Pair<K, V>> {
    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@DedupSortedIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<K, V>? {
        while (true) {
            val next1 =
                when (val matchVal = this.iter.next()) {
                    null -> { return null }
                    else -> {
                        val next = matchVal
                        next
                    }
                }
            val peeked1 =
                when (val matchVal1 = this.iter.peek()) {
                    null -> { return next1 }
                    else -> {
                        val peeked = matchVal1
                        peeked
                    }
                }
            if (next1.first != peeked1.first) {
                return next1
            }
        }
    }

    public companion object {
        internal fun <K, V, I> new(iter: I): DedupSortedIter<K, V, I> where I : Iterator<Pair<K, V>> = DedupSortedIter(iter = iter.peekable())
    }
}
