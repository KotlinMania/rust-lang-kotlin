// port-lint: source collections/btree/merge_iter.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.mergeiter

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * Core of an iterator that merges the output of two strictly ascending iterators,
 * for instance a union or a symmetric difference.
 */
public open class MergeIterInner<I> internal constructor(
    public var a: I,
    public var b: I,
    peekedCtorArg: Peeked<I>?,
) {
    public var peeked: Peeked<I>? = peekedCtorArg
        internal set

    public open fun clone(): MergeIterInner<I> = MergeIterInner(a = this.a.clone(), b = this.b.clone(), peeked = this.peeked)

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "MergeIterInner").field(this.a).field(this.b).field(this.peeked).finish()
    }

    /**
     * Returns the next pair of items stemming from the pair of sources
     * being merged. If both returned options contain a value, that value
     * is equal and occurs in both sources. If one of the returned options
     * contains a value, that value doesn't occur in the other source (or
     * the sources are not strictly ascending). If neither returned option
     * contains a value, iteration has finished and subsequent calls will
     * return the same empty pair.
     */
    public open fun nexts(cmp: (I.Item, I.Item) -> Ordering): Pair<Item?, Item?> {
        var aNext: Any?
        var bNext: Any?
        when (val matchVal = this.peeked.also { this.peeked = null }) {
            is Peeked.A -> {
                val (next) = matchVal as Peeked.A<I>
                aNext = next
                bNext = this.b.next()
            }
            is Peeked.B -> {
                val (next1) = matchVal as Peeked.B<I>
                bNext = next1
                aNext = this.a.next()
            }
            null -> {
                aNext = this.a.next()
                bNext = this.b.next()
            }
        }
        if (Pair(aNext, bNext) != null) {
            val a1 = Pair(aNext, bNext).first
            val b1 = Pair(aNext, bNext).second
            when (cmp(a1, b1)) {
                Ordering.Less -> this.peeked = (bNext.also { bNext = null })?.let({ Peeked.B(it) })
                Ordering.Greater -> this.peeked = (aNext.also { aNext = null })?.let({ Peeked.A(it) })
                Ordering.Equal -> Unit
            }
        }
        return Pair(aNext, bNext)
    }

    /**
     * Returns a pair of upper bounds for the `sizeHint` of the final iterator.
     */
    public open fun lens(): Pair<Int, Int> =
        when (val matchVal = this.peeked) {
            is Peeked.A -> Pair(1 + this.a.len(), this.b.len())
            is Peeked.B -> Pair(this.a.len(), 1 + this.b.len())
            else -> Pair(this.a.len(), this.b.len())
        }

    public companion object {
        public operator fun <I> invoke(
            a: I,
            b: I,
            peeked: Peeked<I>?,
        ): MergeIterInner<I> = MergeIterInner<I>(
            a = a,
            b = b,
            peekedCtorArg = peeked,
        )

        /**
         * Creates a new core for an iterator merging a pair of sources.
         */
        public fun <Item, I> new(a: I, b: I): MergeIterInner<I> = MergeIterInner(a = a, b = b, peeked = null)
    }
}

/**
 * Benchmarks faster than wrapping both iterators in a Peekable,
 * probably because we can afford to impose a FusedIterator bound.
 */
public sealed class Peeked<I> {
    public data class A<I>(public var value: Item) : Peeked<I>()
    public data class B<I>(public var value: Item) : Peeked<I>()
}
