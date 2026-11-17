// port-lint: source collections/btree/search.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.search

import io.github.kotlinmania.rustlang.alloc.collections.btree.node.BorrowType
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ForceResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Handle
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Internal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.NodeRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Leaf
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.LeafOrInternal
import io.github.kotlinmania.rustlang.alloc.collections.btree.search.SearchBound.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.search.SearchResult.*
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.result.Result

public sealed class SearchBound<T> {
    /**
     * An inclusive bound to look for, just like `Bound.Included(T)`.
     */
    public data class Included<T>(public var value: T) : SearchBound<T>()
    /**
     * An exclusive bound to look for, just like `Bound.Excluded(T)`.
     */
    public data class Excluded<T>(public var value: T) : SearchBound<T>()
    /**
     * An unconditional inclusive bound, just like `Bound.Unbounded`.
     */
    public class AllIncluded<T>() : SearchBound<T>()
    /**
     * An unconditional exclusive bound.
     */
    public class AllExcluded<T>() : SearchBound<T>()

    public companion object {
        public fun <T> fromRange(rangeBound: Bound<T>): SearchBound<T> =
            when (rangeBound) {
                is Bound.Included<*> -> {
                    val (t) = rangeBound
                    Included(t)
                }
                is Bound.Excluded<*> -> {
                    val (t1) = rangeBound
                    Excluded(t1)
                }
                Bound.Unbounded -> AllIncluded
            }
    }
}

public sealed class SearchResult<BorrowType, K, V, FoundType, GoDownType> {
    public class Found<BorrowType, K, V, FoundType, GoDownType> internal constructor(valueCtorArg: Handle<NodeRef<BorrowType, K, V, FoundType>, KV>) : SearchResult<BorrowType, K, V, FoundType, GoDownType>() {
        public var value: Handle<NodeRef<BorrowType, K, V, FoundType>, KV> = valueCtorArg
            internal set

        public operator fun component1(): Handle<NodeRef<BorrowType, K, V, FoundType>, KV> = this.value

        public fun copy(value: Handle<NodeRef<BorrowType, K, V, FoundType>, KV> = this.value): Found<BorrowType, K, V, FoundType, GoDownType> = Found<BorrowType, K, V, FoundType, GoDownType>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Found<*, *, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <BorrowType, K, V, FoundType, GoDownType> invoke(
                value: Handle<NodeRef<BorrowType, K, V, FoundType>, KV>,
            ): Found<BorrowType, K, V, FoundType, GoDownType> = Found<BorrowType, K, V, FoundType, GoDownType>(
                valueCtorArg = value,
            )
        }
    }
    public class GoDown<BorrowType, K, V, FoundType, GoDownType> internal constructor(valueCtorArg: Handle<NodeRef<BorrowType, K, V, GoDownType>, Edge>) : SearchResult<BorrowType, K, V, FoundType, GoDownType>() {
        public var value: Handle<NodeRef<BorrowType, K, V, GoDownType>, Edge> = valueCtorArg
            internal set

        public operator fun component1(): Handle<NodeRef<BorrowType, K, V, GoDownType>, Edge> = this.value

        public fun copy(value: Handle<NodeRef<BorrowType, K, V, GoDownType>, Edge> = this.value): GoDown<BorrowType, K, V, FoundType, GoDownType> = GoDown<BorrowType, K, V, FoundType, GoDownType>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is GoDown<*, *, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <BorrowType, K, V, FoundType, GoDownType> invoke(
                value: Handle<NodeRef<BorrowType, K, V, GoDownType>, Edge>,
            ): GoDown<BorrowType, K, V, FoundType, GoDownType> = GoDown<BorrowType, K, V, FoundType, GoDownType>(
                valueCtorArg = value,
            )
        }
    }
}

public sealed class IndexResult {
    public data class KV(public var value: Int) : IndexResult()
    public data class Edge(public var value: Int) : IndexResult()
}

/**
 * Looks up a given key in a (sub)tree headed by the node, recursively.
 * Returns a `Found` with the handle of the matching KV, if any. Otherwise,
 * returns a `GoDown` with the handle of the leaf edge where the key belongs.
 *
 * The result is meaningful only if the tree is ordered by key, like the tree
 * in a `BTreeMap` is.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q> NodeRef<BorrowType, K, V, LeafOrInternal>.searchTree(key: Q): SearchResult<BorrowType, K, V, LeafOrInternal, Leaf> where K : Borrow<Q> {
    var selfMut = this
    while (true) {
        selfMut = when (val matchVal = selfMut.searchNode(key)) {
            is Found<*, *, *, *, *> -> {
                val (handle) = matchVal
                return Found(handle)
            }
            is GoDown<*, *, *, *, *> -> {
                val (handle1) = matchVal
                when (val matchVal1 = handle1.force()) {
                    is io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf -> {
                        val (leaf) = matchVal1
                        return GoDown(leaf)
                    }
                    is Internal -> {
                        val (internal) = matchVal1
                        internal.descend()
                    }
                }
            }
        }
    }
}

/**
 * Descends to the nearest node where the edge matching the lower bound
 * of the range is different from the edge matching the upper bound, i.e.,
 * the nearest node that has at least one key contained in the range.
 *
 * If found, returns an `Ok` with that node, the strictly ascending pair of
 * edge indices in the node delimiting the range, and the corresponding
 * pair of bounds for continuing the search in the child nodes, in case
 * the node is internal.
 *
 * If not found, returns an `Err` with the leaf edge matching the entire
 * range.
 *
 * As a diagnostic service, panics if the range specifies impossible bounds.
 *
 * The result is meaningful only if the tree is ordered by key.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q, R> NodeRef<BorrowType, K, V, LeafOrInternal>.searchTreeForBifurcation(range: R, isSetValVFn: () -> V): Result<List<Any?>, Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>> where K : Borrow<Q>, R : RangeBounds<Q> {
    var selfMut = this
    // Determine if map or set is being searched
    val isSet = isSetValVFn()
    // Inlining these variables should be avoided. We assume the bounds reported by `range`
    // remain the same, but an adversarial implementation could change between calls (#81138).
    val (start, end) = Pair(range.startBound(), range.endBound())
    run {
        val matchVal = Pair(start, end)
        when {
            matchVal.first is Bound.Excluded<*> && matchVal.second is Bound.Excluded<*> && (matchVal.first as Bound.Excluded<*>).value == (matchVal.second as Bound.Excluded<*>).value -> {
                val (s) = matchVal.first as Bound.Excluded<*>
                val (e) = matchVal.second as Bound.Excluded<*>
                if (isSet) {
                    error("range start and end are equal and excluded in BTreeSet")
                } else {
                    error("range start and end are equal and excluded in BTreeMap")
                }
            }
            ((matchVal.first is Bound.Included<*> || matchVal.first is Bound.Excluded<*>) && (matchVal.second is Bound.Included<*> || matchVal.second is Bound.Excluded<*>)) && (matchVal.first as Bound.Included<*>).value > (matchVal.second as Bound.Included<*>).value -> {
                val (s1) = matchVal.first as Bound.Included<*>
                val (e1) = matchVal.second as Bound.Included<*>
                if (isSet) {
                    error("range start is greater than range end in BTreeSet")
                } else {
                    error("range start is greater than range end in BTreeMap")
                }
            }
            else -> {}
        }
    }
    var lowerBound = SearchBound.fromRange(start)
    var upperBound = SearchBound.fromRange(end)
    while (true) {
        val (lowerEdgeIdx, lowerChildBound) = selfMut.findLowerBoundIndex(lowerBound)
        val (upperEdgeIdx, upperChildBound) = selfMut.findUpperBoundIndex(upperBound, lowerEdgeIdx)
        if (lowerEdgeIdx < upperEdgeIdx) {
            return Result.Ok<List<Any?>, Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>>(listOf(selfMut, lowerEdgeIdx, upperEdgeIdx, lowerChildBound, upperChildBound))
        }
        check(lowerEdgeIdx == upperEdgeIdx)
        // ignore-tidy-undocumented-unchecked
        val commonEdge = Handle.newEdge(selfMut, lowerEdgeIdx)
        when (val matchVal1 = commonEdge.force()) {
            is io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf -> {
                val (commonEdge1) = matchVal1
                return Result.Err<List<Any?>, Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>>(commonEdge1)
            }
            is Internal -> {
                val (commonEdge2) = matchVal1
                selfMut = commonEdge2.descend()
                lowerBound = lowerChildBound
                upperBound = upperChildBound
            }
        }
    }
}

/**
 * Finds an edge in the node delimiting the lower bound of a range.
 * Also returns the lower bound to be used for continuing the search in
 * the matching child node, if `self` is an internal node.
 *
 * The result is meaningful only if the tree is ordered by key.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q> NodeRef<BorrowType, K, V, LeafOrInternal>.findLowerBoundEdge(bound: SearchBound<Q>): Pair<Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, Edge>, SearchBound<Q>> where K : Borrow<Q> {
    val (edgeIdx, bound1) = this.findLowerBoundIndex(bound)
    // ignore-tidy-undocumented-unchecked
    val edge = Handle.newEdge(this, edgeIdx)
    return Pair(edge, bound1)
}

/**
 * Clone of `findLowerBoundEdge` for the upper bound.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q> NodeRef<BorrowType, K, V, LeafOrInternal>.findUpperBoundEdge(bound: SearchBound<Q>): Pair<Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, Edge>, SearchBound<Q>> where K : Borrow<Q> {
    // ignore-tidy-undocumented-unchecked
    val (edgeIdx, bound1) = this.findUpperBoundIndex(bound, 0)
    // ignore-tidy-undocumented-unchecked
    val edge = Handle.newEdge(this, edgeIdx)
    return Pair(edge, bound1)
}

/**
 * Looks up a given key in the node, without recursion.
 * Returns a `Found` with the handle of the matching KV, if any. Otherwise,
 * returns a `GoDown` with the handle of the edge where the key might be found
 * (if the node is internal) or where the key can be inserted.
 *
 * The result is meaningful only if the tree is ordered by key, like the tree
 * in a `BTreeMap` is.
 */
public fun <BorrowType, K, V, Type, Q> NodeRef<BorrowType, K, V, Type>.searchNode(key: Q): SearchResult<BorrowType, K, V, Type, Type> where K : Borrow<Q> {
    // ignore-tidy-undocumented-unchecked
    return when (val matchVal = this.findKeyIndex(key, 0)) {
        // ignore-tidy-undocumented-unchecked
        is IndexResult.KV -> {
            val (idx) = matchVal
            Found(Handle.newKv(this, idx))
        }
        // ignore-tidy-undocumented-unchecked
        is IndexResult.Edge -> {
            val (idx1) = matchVal
            GoDown(Handle.newEdge(this, idx1))
        }
    }
}

/**
 * Returns either the KV index in the node at which the key (or an equivalent)
 * exists, or the edge index where the key belongs, starting from a particular index.
 *
 * The result is meaningful only if the tree is ordered by key, like the tree
 * in a `BTreeMap` is.
 *
 * # Safety
 * `startIndex` must be a valid edge index for the node.
 */
public fun <BorrowType, K, V, Type, Q> NodeRef<BorrowType, K, V, Type>.findKeyIndex(key: Q, startIndex: Int): IndexResult where K : Borrow<Q> {
    val node = this.reborrow()
    val keys = node.keys()
    check(startIndex <= keys.len())
    // ignore-tidy-undocumented-unchecked
    for ((offset, k) in keys.getUnchecked(startIndex until Int.MAX_VALUE).iter().mapIndexed { index, item -> Pair(index, item) }) {
        when (kmCompareValues(key, k.borrow()).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Ordering.Greater -> {}
            Ordering.Equal -> { return IndexResult.KV(startIndex + offset) }
            Ordering.Less -> { return IndexResult.Edge(startIndex + offset) }
        }
    }
    return IndexResult.Edge(keys.len())
}

/**
 * Finds an edge index in the node delimiting the lower bound of a range.
 * Also returns the lower bound to be used for continuing the search in
 * the matching child node, if `self` is an internal node.
 *
 * The result is meaningful only if the tree is ordered by key.
 */
public fun <BorrowType, K, V, Type, Q> NodeRef<BorrowType, K, V, Type>.findLowerBoundIndex(bound: SearchBound<Q>): Pair<Int, SearchBound<Q>> where K : Borrow<Q> =
    when (bound) {
        // ignore-tidy-undocumented-unchecked
        is Included<*> -> {
            val (key) = bound
            when (val matchVal = this.findKeyIndex(key, 0)) {
                is IndexResult.KV -> {
                    val (idx) = matchVal
                    Pair(idx, AllExcluded)
                }
                is IndexResult.Edge -> {
                    val (idx1) = matchVal
                    Pair(idx1, bound)
                }
            }
        }
        // ignore-tidy-undocumented-unchecked
        is Excluded<*> -> {
            val (key1) = bound
            when (val matchVal1 = this.findKeyIndex(key1, 0)) {
                is IndexResult.KV -> {
                    val (idx2) = matchVal1
                    Pair(idx2 + 1, AllIncluded)
                }
                is IndexResult.Edge -> {
                    val (idx3) = matchVal1
                    Pair(idx3, bound)
                }
            }
        }
        AllIncluded -> Pair(0, AllIncluded)
        AllExcluded -> Pair(this.len(), AllExcluded)
    }

/**
 * Mirror image of `findLowerBoundIndex` for the upper bound,
 * with an additional parameter to skip part of the key array.
 *
 * # Safety
 * `startIndex` must be a valid edge index for the node.
 */
public fun <BorrowType, K, V, Type, Q> NodeRef<BorrowType, K, V, Type>.findUpperBoundIndex(bound: SearchBound<Q>, startIndex: Int): Pair<Int, SearchBound<Q>> where K : Borrow<Q> =
    when (bound) {
        // ignore-tidy-undocumented-unchecked
        is Included<*> -> {
            val (key) = bound
            when (val matchVal = this.findKeyIndex(key, startIndex)) {
                is IndexResult.KV -> {
                    val (idx) = matchVal
                    Pair(idx + 1, AllExcluded)
                }
                is IndexResult.Edge -> {
                    val (idx1) = matchVal
                    Pair(idx1, bound)
                }
            }
        }
        // ignore-tidy-undocumented-unchecked
        is Excluded<*> -> {
            val (key1) = bound
            when (val matchVal1 = this.findKeyIndex(key1, startIndex)) {
                is IndexResult.KV -> {
                    val (idx2) = matchVal1
                    Pair(idx2, AllIncluded)
                }
                is IndexResult.Edge -> {
                    val (idx3) = matchVal1
                    Pair(idx3, bound)
                }
            }
        }
        AllIncluded -> Pair(this.len(), AllIncluded)
        AllExcluded -> Pair(startIndex, AllExcluded)
    }
