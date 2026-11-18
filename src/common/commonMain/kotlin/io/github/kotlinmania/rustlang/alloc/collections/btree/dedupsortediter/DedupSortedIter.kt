// port-lint: source collections/btree/dedup_sorted_iter.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.dedupsortediter

import io.github.kotlinmania.rustlang.core.iter.adapters.peekable.Peekable
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * An iterator for deduping the key of a sorted iterator.
 * When encountering the duplicated key, only the last key-value pair is yielded.
 *
 * Used by [`BTreeMap.bulkBuildFromSortedIter`][1].
 */
public open class DedupSortedIter<K, V, I, Item> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Peekable<I>> = UnresolvedTypeLayout(),
) : Iterable<Pair<K, V>> where I : Iterator<Pair<K, V>> {
    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@DedupSortedIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public var iter: Peekable<I>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(iter: Peekable<I>, fieldLayout: TypeLayout<Peekable<I>> = UnresolvedTypeLayout()) : this(allocateNativeCell(iter, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<DedupSortedIter<K, V, I, Item>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> DedupSortedIter<K, V, I, Item>(ptr, layout) }, { it.iter })

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
        public operator fun <K, V, I, Item> invoke(iter: Peekable<I>, fieldLayout: TypeLayout<Peekable<I>> = UnresolvedTypeLayout()): DedupSortedIter<K, V, I, Item> = DedupSortedIter<K, V, I, Item>(iter, fieldLayout)

        public fun <K, V, I, Item> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Peekable<I>> = UnresolvedTypeLayout()): DedupSortedIter<K, V, I, Item> = DedupSortedIter<K, V, I, Item>(fieldStorage, fieldLayout)

        public fun <K, V, I> new(iter: I, fieldLayout: TypeLayout<Peekable<I>> = UnresolvedTypeLayout()): DedupSortedIter<K, V, I, Item> where I : Iterator<Pair<K, V>> = DedupSortedIter(iter = iter.peekable(), fieldLayout = fieldLayout)
    }
}
