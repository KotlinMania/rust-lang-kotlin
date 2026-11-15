// port-lint: source collections/btree/navigate.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.navigate

import io.github.kotlinmania.rustlang.alloc.collections.btree.mem.replace
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.BorrowType
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ForceResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Handle
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.NodeRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Dying
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Immut
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.KV
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.LeafOrInternal
import io.github.kotlinmania.rustlang.alloc.collections.btree.search.SearchBound
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result

// `front` and `back` are always both `None` or both `Some`.
public open class LeafRange<BorrowType, K, V> internal constructor(
    frontCtorArg: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>?,
    backCtorArg: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>?,
) {
    public var front: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>? = frontCtorArg
        internal set

    public var back: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>? = backCtorArg
        internal set

    public open fun isEmpty(): Boolean = this.front == this.back

    /**
     * Temporarily takes out another, immutable equivalent of the same range.
     */
    public open fun reborrow(): LeafRange<Immut, K, V> = LeafRange(front = this.front?.let({ f -> f.reborrow() }), back = this.back?.let({ b -> b.reborrow() }))

    /**
     * If possible, extract some result from the following KV and move to the edge beyond it.
     */
    public open fun <R> performNextChecked(f: (Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>) -> R): R? =
        if (this.isEmpty()) {
            null
        } else {
            // SAFETY: The range is nonempty, so there is an unvisited KV before the back cursor.
            this.front!!.performNextUnchecked(f)
        }

    /**
     * If possible, extract some result from the preceding KV and move to the edge beyond it.
     */
    public open fun <R> performNextBackChecked(f: (Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>) -> R): R? =
        if (this.isEmpty()) {
            null
        } else {
            // SAFETY: The range is nonempty, so there is an unvisited KV before the front cursor.
            this.back!!.performNextBackUnchecked(f)
        }

    public companion object {
        public operator fun <BorrowType, K, V> invoke(
            front: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>?,
            back: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>?,
        ): LeafRange<BorrowType, K, V> =
            LeafRange<BorrowType, K, V>(
                frontCtorArg = front,
                backCtorArg = back,
            )

        public fun <BorrowType, K, V> none(): LeafRange<BorrowType, K, V> = LeafRange(front = null, back = null)
    }
}

public fun <K, V> LeafRange<Immut, K, V>.clone(): LeafRange<Immut, K, V> = LeafRange(front = this.front, back = this.back)

public fun <B, K, V> defaultLeafRangeBKV(): LeafRange<B, K, V> = LeafRange(front = null, back = null)

public fun <K, V> LeafRange<Immut, K, V>.nextChecked(): Pair<K, V>? = this.performNextChecked({ kv -> kv.intoKv() })

public fun <K, V> LeafRange<Immut, K, V>.nextBackChecked(): Pair<K, V>? = this.performNextBackChecked({ kv -> kv.intoKv() })

public sealed class LazyLeafHandle<BorrowType, K, V> {
    public class Root<BorrowType, K, V> internal constructor(
        valueCtorArg: NodeRef<BorrowType, K, V, LeafOrInternal>,
    ) : LazyLeafHandle<BorrowType, K, V>() {
        public var value: NodeRef<BorrowType, K, V, LeafOrInternal> = valueCtorArg
            internal set

        public operator fun component1(): NodeRef<BorrowType, K, V, LeafOrInternal> = this.value

        public fun copy(value: NodeRef<BorrowType, K, V, LeafOrInternal> = this.value): Root<BorrowType, K, V> = Root<BorrowType, K, V>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Root<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <BorrowType, K, V> invoke(
                value: NodeRef<BorrowType, K, V, LeafOrInternal>,
            ): Root<BorrowType, K, V> =
                Root<BorrowType, K, V>(
                    valueCtorArg = value,
                )
        }
    }

    // not yet descended
    public class Edge<BorrowType, K, V> internal constructor(
        valueCtorArg: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>,
    ) : LazyLeafHandle<BorrowType, K, V>() {
        public var value: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> = valueCtorArg
            internal set

        public operator fun component1(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> = this.value

        public fun copy(value: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> = this.value): Edge<BorrowType, K, V> = Edge<BorrowType, K, V>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Edge<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <BorrowType, K, V> invoke(
                value: Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>,
            ): Edge<BorrowType, K, V> =
                Edge<BorrowType, K, V>(
                    valueCtorArg = value,
                )
        }
    }

    public open fun reborrow(): LazyLeafHandle<Immut, K, V> =
        when (this) {
            is LazyLeafHandle.Root -> {
                val (root) = this
                LazyLeafHandle.Root(root.reborrow())
            }
            is LazyLeafHandle.Edge -> {
                val (edge) = this
                LazyLeafHandle.Edge(edge.reborrow())
            }
        }
}

public fun <K, V> LazyLeafHandle<Immut, K, V>.clone(): LazyLeafHandle<Immut, K, V> =
    when (this) {
        is LazyLeafHandle.Root -> {
            val (root) = this
            LazyLeafHandle.Root(root)
        }
        is LazyLeafHandle.Edge -> {
            val (edge) = this
            LazyLeafHandle.Edge(edge)
        }
    }

// `front` and `back` are always both `None` or both `Some`.
public open class LazyLeafRange<BorrowType, K, V>(
    public var front: LazyLeafHandle<BorrowType, K, V>?,
    public var back: LazyLeafHandle<BorrowType, K, V>?,
) {
    /**
     * Temporarily takes out another, immutable equivalent of the same range.
     */
    public open fun reborrow(): LazyLeafRange<Immut, K, V> = LazyLeafRange(front = this.front?.let({ f -> f.reborrow() }), back = this.back?.let({ b -> b.reborrow() }))

    public open fun initFront(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>? {
        when (val matchVal = this.front) {
            is Some -> {
                val (root) = matchVal as LazyLeafHandle.Root<BorrowType, K, V>
                // ignore-tidy-undocumented-unchecked
                this.front = LazyLeafHandle.Edge(read(root).firstLeafEdge())
            }
            else -> {}
        }
        return when (val matchVal1 = this.front) {
            null -> null
            is LazyLeafHandle.Edge -> {
                val (edge) = matchVal1 as LazyLeafHandle.Edge<BorrowType, K, V>
                edge
            }
            // SAFETY: the code above would have replaced it.
            is LazyLeafHandle.Root -> unreachableUnchecked()
        }
    }

    public open fun initBack(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>? {
        when (val matchVal = this.back) {
            is Some -> {
                val (root) = matchVal as LazyLeafHandle.Root<BorrowType, K, V>
                // ignore-tidy-undocumented-unchecked
                this.back = LazyLeafHandle.Edge(read(root).lastLeafEdge())
            }
            else -> {}
        }
        return when (val matchVal1 = this.back) {
            null -> null
            is LazyLeafHandle.Edge -> {
                val (edge) = matchVal1 as LazyLeafHandle.Edge<BorrowType, K, V>
                edge
            }
            // SAFETY: the code above would have replaced it.
            is LazyLeafHandle.Root -> unreachableUnchecked()
        }
    }

    public companion object {
        public fun <BorrowType, K, V> none(): LazyLeafRange<BorrowType, K, V> = LazyLeafRange(front = null, back = null)
    }
}

public fun <B, K, V> defaultLazyLeafRangeBKV(): LazyLeafRange<B, K, V> = LazyLeafRange(front = null, back = null)

public fun <K, V> LazyLeafRange<Immut, K, V>.clone(): LazyLeafRange<Immut, K, V> = LazyLeafRange(front = this.front, back = this.back)

public fun <K, V> LazyLeafRange<Immut, K, V>.nextUnchecked(): Pair<K, V> {
    // SAFETY: Upheld by caller.
    return this.initFront()!!.nextUnchecked()
}

public fun <K, V> LazyLeafRange<Immut, K, V>.nextBackUnchecked(): Pair<K, V> {
    // SAFETY: Upheld by caller.
    return this.initBack()!!.nextBackUnchecked()
}

public fun <K, V> LazyLeafRange<Dying, K, V>.takeFront(): Handle<NodeRef<Dying, K, V, Leaf>, Edge>? {
    return when (val matchVal = (this.front.also { this.front = null } ?: return null)) {
        is LazyLeafHandle.Root -> {
            val (root) = matchVal
            root.firstLeafEdge()
        }
        is LazyLeafHandle.Edge -> {
            val (edge) = matchVal
            edge
        }
    }
}

public fun <K, V, A : AllocatorClone> LazyLeafRange<Dying, K, V>.deallocatingNextUnchecked(alloc: A): Handle<NodeRef<Dying, K, V, LeafOrInternal>, KV> {
    check(this.front != null)
    val front = this.initFront()!!
    // ignore-tidy-undocumented-unchecked
    return front.deallocatingNextUnchecked(alloc)
}

public fun <K, V, A : AllocatorClone> LazyLeafRange<Dying, K, V>.deallocatingNextBackUnchecked(alloc: A): Handle<NodeRef<Dying, K, V, LeafOrInternal>, KV> {
    check(this.back != null)
    val back = this.initBack()!!
    // ignore-tidy-undocumented-unchecked
    return back.deallocatingNextBackUnchecked(alloc)
}

public fun <K, V, A : AllocatorClone> LazyLeafRange<Dying, K, V>.deallocatingEnd(alloc: A) {
    when (val matchVal = this.takeFront()) {
        null -> {}
        else -> {
            val front = matchVal
            front.deallocatingEnd(alloc)
        }
    }
}

/**
 * Finds the distinct leaf edges delimiting a specified range in a tree.
 *
 * If such distinct edges exist, returns them in ascending order, meaning
 * that a non-zero number of calls to `nextUnchecked` on the `front` of
 * the result and/or calls to `nextBackUnchecked` on the `back` of the
 * result will eventually reach the same edge.
 *
 * If there are no such edges, i.e., if the tree contains no key within
 * the range, returns an empty `front` and `back`.
 *
 * # Safety
 * Unless `BorrowType` is `Immut`, do not use the handles to visit the same
 * KV twice.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q, R> NodeRef<BorrowType, K, V, LeafOrInternal>.findLeafEdgesSpanningRange(range: R): LeafRange<BorrowType, K, V> where K : Borrow<Q>, R : RangeBounds<Q> {
    return run {
        val matchVal = this.searchTreeForBifurcation(range)
        when (matchVal) {
            is Result.Ok<*, *> -> {
                val okVal = matchVal.value
                val node = okVal[0]
                val lowerEdgeIdx = okVal[1]
                val upperEdgeIdx = okVal[2]
                val lowerChildBound = okVal[3]
                val upperChildBound = okVal[4]
                // ignore-tidy-undocumented-unchecked
                var lowerEdge = Handle.newEdge(read(node), lowerEdgeIdx)
                // ignore-tidy-undocumented-unchecked
                var upperEdge = Handle.newEdge(node, upperEdgeIdx)
                while (true) {
                    run {
                        val matchVal1 = Pair(lowerEdge.force(), upperEdge.force())
                        when {
                            matchVal1.first is Leaf && matchVal1.second is Leaf -> {
                                val (f) = matchVal1.first as Leaf<BorrowType, K, V>
                                val (b) = matchVal1.second as Leaf<BorrowType, K, V>
                                return LeafRange(front = f, back = b)
                            }
                            matchVal1.first is Internal && matchVal1.second is Internal -> {
                                val (f1) = matchVal1.first as Internal<BorrowType, K, V>
                                val (b1) = matchVal1.second as Internal<BorrowType, K, V>
                                Pair(lowerEdge, lowerChildBound) = f1.descend().findLowerBoundEdge(lowerChildBound)
                                Pair(upperEdge, upperChildBound) = b1.descend().findUpperBoundEdge(upperChildBound)
                            }
                            else -> error("BTreeMap has different depths")
                        }
                    }
                }
            }
            is Result.Err<*, *> -> {
                LeafRange.none()
            }
        }
    }
}

public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> fullRange(root1: NodeRef<BorrowType, K, V, LeafOrInternal>, root2: NodeRef<BorrowType, K, V, LeafOrInternal>): LazyLeafRange<BorrowType, K, V> = LazyLeafRange(front = LazyLeafHandle.Root(root1), back = LazyLeafHandle.Root(root2))

/**
 * Finds the pair of leaf edges delimiting a specific range in a tree.
 *
 * The result is meaningful only if the tree is ordered by key, like the tree
 * in a `BTreeMap` is.
 */
public fun <K, V, Q, R> NodeRef<Immut, K, V, LeafOrInternal>.rangeSearch(range: R): LeafRange<Immut, K, V> where K : Borrow<Q>, R : RangeBounds<Q> {
    // SAFETY: our borrow type is immutable.
    return this.findLeafEdgesSpanningRange(range)
}

/**
 * Finds the pair of leaf edges delimiting an entire tree.
 */
public fun <K, V> NodeRef<Immut, K, V, LeafOrInternal>.fullRange(): LazyLeafRange<Immut, K, V> = fullRange(this, this)

/**
 * Given a leaf edge handle, returns [Result.Ok] with a handle to the neighboring KV
 * on the right side, which is either in the same leaf node or in an ancestor node.
 * If the leaf edge is the last one in the tree, returns [Result.Err] with the root node.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>.nextKv(): Result<Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>, NodeRef<BorrowType, K, V, LeafOrInternal>> {
    var edge = this.forgetNodeType()
    while (true) {
        edge =
            run {
                val matchVal = edge.rightKv()
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val kv = matchVal.value
                        return Result.Ok(kv)
                    }
                    is Result.Err<*, *> -> {
                        val lastEdge = matchVal.error
                        run {
                            val matchVal1 = lastEdge.intoNode().ascend()
                            when (matchVal1) {
                                is Result.Ok<*, *> -> {
                                    val parentEdge = matchVal1.value
                                    parentEdge.forgetNodeType()
                                }
                                is Result.Err<*, *> -> {
                                    val root = matchVal1.error
                                    return Result.Err(root)
                                }
                            }
                        }
                    }
                }
            }
    }
}

/**
 * Given a leaf edge handle, returns [Result.Ok] with a handle to the neighboring KV
 * on the left side, which is either in the same leaf node or in an ancestor node.
 * If the leaf edge is the first one in the tree, returns [Result.Err] with the root node.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>.nextBackKv(): Result<Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>, NodeRef<BorrowType, K, V, LeafOrInternal>> {
    var edge = this.forgetNodeType()
    while (true) {
        edge =
            run {
                val matchVal = edge.leftKv()
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val kv = matchVal.value
                        return Result.Ok(kv)
                    }
                    is Result.Err<*, *> -> {
                        val lastEdge = matchVal.error
                        run {
                            val matchVal1 = lastEdge.intoNode().ascend()
                            when (matchVal1) {
                                is Result.Ok<*, *> -> {
                                    val parentEdge = matchVal1.value
                                    parentEdge.forgetNodeType()
                                }
                                is Result.Err<*, *> -> {
                                    val root = matchVal1.error
                                    return Result.Err(root)
                                }
                            }
                        }
                    }
                }
            }
    }
}

/**
 * Given a leaf edge handle into a dying tree, returns the next leaf edge
 * on the right side, and the key-value pair in between, if they exist.
 *
 * If the given edge is the last one in a leaf, this method deallocates
 * the leaf, as well as any ancestor nodes whose last edge was reached.
 * This implies that if no more key-value pair follows, the entire tree
 * will have been deallocated and there is nothing left to return.
 *
 * # Safety
 * - The given edge must not have been previously returned by counterpart
 *   `deallocatingNextBack`.
 * - The returned KV handle is only valid to access the key and value,
 *   and only valid until the next call to a `deallocating_` method.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Dying, K, V, Leaf>, Edge>.deallocatingNext(alloc: A): Pair<Handle<NodeRef<Dying, K, V, Leaf>, Edge>, Handle<NodeRef<Dying, K, V, LeafOrInternal>, KV>>? {
    var edge = this.forgetNodeType()
    while (true) {
        edge =
            run {
                val matchVal = edge.rightKv()
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val kv = matchVal.value
                        return Pair(read(kv).nextLeafEdge(), kv)
                    }
                    is Result.Err<*, *> -> {
                        val lastEdge = matchVal.error
                        // ignore-tidy-undocumented-unchecked
                        when (val matchVal1 = lastEdge.intoNode().deallocateAndAscend(alloc.clone())) {
                            null -> {
                                return null
                            }
                            else -> {
                                val parentEdge = matchVal1
                                parentEdge.forgetNodeType()
                            }
                        }
                    }
                }
            }
    }
}

/**
 * Given a leaf edge handle into a dying tree, returns the next leaf edge
 * on the left side, and the key-value pair in between, if they exist.
 *
 * If the given edge is the first one in a leaf, this method deallocates
 * the leaf, as well as any ancestor nodes whose first edge was reached.
 * This implies that if no more key-value pair follows, the entire tree
 * will have been deallocated and there is nothing left to return.
 *
 * # Safety
 * - The given edge must not have been previously returned by counterpart
 *   `deallocatingNext`.
 * - The returned KV handle is only valid to access the key and value,
 *   and only valid until the next call to a `deallocating_` method.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Dying, K, V, Leaf>, Edge>.deallocatingNextBack(alloc: A): Pair<Handle<NodeRef<Dying, K, V, Leaf>, Edge>, Handle<NodeRef<Dying, K, V, LeafOrInternal>, KV>>? {
    var edge = this.forgetNodeType()
    while (true) {
        edge =
            run {
                val matchVal = edge.leftKv()
                when (matchVal) {
                    is Result.Ok<*, *> -> {
                        val kv = matchVal.value
                        return Pair(read(kv).nextBackLeafEdge(), kv)
                    }
                    is Result.Err<*, *> -> {
                        val lastEdge = matchVal.error
                        // ignore-tidy-undocumented-unchecked
                        when (val matchVal1 = lastEdge.intoNode().deallocateAndAscend(alloc.clone())) {
                            null -> {
                                return null
                            }
                            else -> {
                                val parentEdge = matchVal1
                                parentEdge.forgetNodeType()
                            }
                        }
                    }
                }
            }
    }
}

/**
 * Deallocates a pile of nodes from the leaf up to the root.
 * This is the only way to deallocate the remainder of a tree after
 * `deallocatingNext` and `deallocatingNextBack` have been nibbling at
 * both sides of the tree, and have hit the same edge. As it is intended
 * only to be called when all keys and values have been returned,
 * no cleanup is done on any of the keys or values.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Dying, K, V, Leaf>, Edge>.deallocatingEnd(alloc: A) {
    var edge = this.forgetNodeType()
    while (true) {
        val matchVal = edge.intoNode().deallocateAndAscend(alloc.clone())
        if (matchVal == null) break
        val parentEdge = matchVal
        edge = parentEdge.forgetNodeType()
    }
}

/**
 * Moves the leaf edge handle to the next leaf edge and returns references to the
 * key and value in between.
 *
 * # Safety
 * There must be another KV in the direction travelled.
 */
public fun <K, V> Handle<NodeRef<Immut, K, V, Leaf>, Edge>.nextUnchecked(): Pair<K, V> {
    // SAFETY: guaranteed by caller
    return this.performNextUnchecked({ node -> node.intoKv() })
}

/**
 * Moves the leaf edge handle to the previous leaf edge and returns references to the
 * key and value in between.
 *
 * # Safety
 * There must be another KV in the direction travelled.
 */
public fun <K, V> Handle<NodeRef<Immut, K, V, Leaf>, Edge>.nextBackUnchecked(): Pair<K, V> {
    // SAFETY: guaranteed by caller
    return this.performNextBackUnchecked({ node -> node.intoKv() })
}

/**
 * # Safety
 * - There must be another KV in the direction travelled.
 * - The KV produced by this call must not be aliased by any other
 *   live mutable reference
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, R> Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>.performNextUnchecked(f: (Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>) -> R): R {
    val idx = this.idx()
    val len = this.reborrow().intoNode().len()
    // The fast path when the next KV is still in the same node
    return if (idx < len) {
        // SAFETY: `self` will be immediately replaced below
        val node = read(this).intoNode()
        // SAFETY: `idx + 1 <= node.len()`. The copy of `node` is only used
        // to form the next edge; `node` is consumed below to access the current KV.
        this = Handle.newEdge(read(node), idx + 1)
        // SAFETY: `idx<node.len()`.
        f(Handle.newKv(node, idx).forgetNodeType())
    } else {
        val kv =
            replace(this, { leafEdge ->
                val kv = leafEdge.nextKv().ok()!!
                // SAFETY: The copy of `kv` is only used to form the next edge
                Pair(read(kv).nextLeafEdge(), kv)
            })
        f(kv)
    }
}

/**
 * # Safety
 * - There must be another KV in the direction travelled.
 * - The KV produced by this call must not be aliased by any other
 *   live mutable reference
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, R> Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>.performNextBackUnchecked(f: (Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>) -> R): R {
    val idx = this.idx()
    // The fast path when the next KV is still in the same node
    return if (idx > 0) {
        // SAFETY: `self` will be replaced below
        val node = read(this).intoNode()
        // SAFETY: `0 <= idx - 1<node.len()`. The copy of `node` is only used
        // to form the next edge; `node` is consumed below to access the current KV.
        this = Handle.newEdge(read(node), idx - 1)
        // SAFETY: `0 <= idx - 1<node.len()`
        f(Handle.newKv(node, idx - 1).forgetNodeType())
    } else {
        val kv =
            replace(this, { leafEdge ->
                val kv = leafEdge.nextBackKv().ok()!!
                // SAFETY: The copy of `kv` is only used to form the next edge
                Pair(read(kv).nextBackLeafEdge(), kv)
            })
        f(kv)
    }
}

/**
 * Moves the leaf edge handle to the next leaf edge and returns the key and value
 * in between, deallocating any node left behind while leaving the corresponding
 * edge in its parent node dangling.
 *
 * # Safety
 * - There must be another KV in the direction travelled.
 * - That KV was not previously returned by counterpart
 *   `deallocatingNextBackUnchecked` on any copy of the handles
 *   being used to traverse the tree.
 *
 * The only safe way to proceed with the updated handle is to compare it, drop it,
 * or call this method or counterpart `deallocatingNextBackUnchecked` again.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Dying, K, V, Leaf>, Edge>.deallocatingNextUnchecked(alloc: A): Handle<NodeRef<Dying, K, V, LeafOrInternal>, KV> {
    // ignore-tidy-undocumented-unchecked
    return replace(this, { leafEdge -> leafEdge.deallocatingNext(alloc).unwrap() })
}

/**
 * Moves the leaf edge handle to the previous leaf edge and returns the key and value
 * in between, deallocating any node left behind while leaving the corresponding
 * edge in its parent node dangling.
 *
 * # Safety
 * - There must be another KV in the direction travelled.
 * - That leaf edge was not previously returned by counterpart
 *   `deallocatingNextUnchecked` on any copy of the handles
 *   being used to traverse the tree.
 *
 * The only safe way to proceed with the updated handle is to compare it, drop it,
 * or call this method or counterpart `deallocatingNextUnchecked` again.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Dying, K, V, Leaf>, Edge>.deallocatingNextBackUnchecked(alloc: A): Handle<NodeRef<Dying, K, V, LeafOrInternal>, KV> {
    // ignore-tidy-undocumented-unchecked
    return replace(this, { leafEdge -> leafEdge.deallocatingNextBack(alloc).unwrap() })
}

/**
 * Returns the leftmost leaf edge in or underneath a node - in other words, the edge
 * you need first when navigating forward (or last when navigating backward).
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> NodeRef<BorrowType, K, V, LeafOrInternal>.firstLeafEdge(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> {
    var node = this
    while (true) {
        when (val matchVal = node.force()) {
            is Leaf -> {
                val (leaf) = matchVal
                return leaf.firstEdge()
            }
            is Internal -> {
                val (internal) = matchVal
                node = internal.firstEdge().descend()
            }
        }
    }
}

/**
 * Returns the rightmost leaf edge in or underneath a node - in other words, the edge
 * you need last when navigating forward (or first when navigating backward).
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> NodeRef<BorrowType, K, V, LeafOrInternal>.lastLeafEdge(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> {
    var node = this
    while (true) {
        when (val matchVal = node.force()) {
            is Leaf -> {
                val (leaf) = matchVal
                return leaf.lastEdge()
            }
            is Internal -> {
                val (internal) = matchVal
                node = internal.lastEdge().descend()
            }
        }
    }
}

public sealed class Position<BorrowType, K, V> {
    public class Leaf<BorrowType, K, V> internal constructor(
        valueCtorArg: NodeRef<BorrowType, K, V, Leaf>,
    ) : Position<BorrowType, K, V>() {
        public var value: NodeRef<BorrowType, K, V, Leaf> = valueCtorArg
            internal set

        public operator fun component1(): NodeRef<BorrowType, K, V, Leaf> = this.value

        public fun copy(value: NodeRef<BorrowType, K, V, Leaf> = this.value): Leaf<BorrowType, K, V> = Leaf<BorrowType, K, V>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Leaf<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <BorrowType, K, V> invoke(
                value: NodeRef<BorrowType, K, V, Leaf>,
            ): Leaf<BorrowType, K, V> =
                Leaf<BorrowType, K, V>(
                    valueCtorArg = value,
                )
        }
    }
    public class Internal<BorrowType, K, V> internal constructor(
        valueCtorArg: NodeRef<BorrowType, K, V, Internal>,
    ) : Position<BorrowType, K, V>() {
        public var value: NodeRef<BorrowType, K, V, Internal> = valueCtorArg
            internal set

        public operator fun component1(): NodeRef<BorrowType, K, V, Internal> = this.value

        public fun copy(value: NodeRef<BorrowType, K, V, Internal> = this.value): Internal<BorrowType, K, V> = Internal<BorrowType, K, V>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Internal<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <BorrowType, K, V> invoke(
                value: NodeRef<BorrowType, K, V, Internal>,
            ): Internal<BorrowType, K, V> =
                Internal<BorrowType, K, V>(
                    valueCtorArg = value,
                )
        }
    }
    public class InternalKV<BorrowType, K, V> : Position<BorrowType, K, V>()
}

/**
 * Visits leaf nodes and internal KVs in order of ascending keys, and also
 * visits internal nodes as a whole in a depth first order, meaning that
 * internal nodes precede their individual KVs and their child nodes.
 */
public fun <K, V> NodeRef<Immut, K, V, LeafOrInternal>.visitNodesInOrder(visit: (Position<Immut, K, V>) -> Unit) {
    when (val matchVal = this.force()) {
        is Leaf -> {
            val (leaf) = matchVal
            visit(Position.Leaf(leaf))
        }
        is Internal -> {
            val (internal) = matchVal
            visit(Position.Internal(internal))
            var edge = internal.firstEdge()
            while (true) {
                edge =
                    when (val matchVal1 = edge.descend().force()) {
                        is Leaf -> {
                            val (leaf1) = matchVal1
                            visit(Position.Leaf(leaf1))
                            run {
                                val matchVal2 = edge.nextKv()
                                when (matchVal2) {
                                    is Result.Ok<*, *> -> {
                                        val kv = matchVal2.value
                                        visit(Position.InternalKV)
                                        kv.rightEdge()
                                    }
                                    is Result.Err<*, *> -> {
                                        return
                                    }
                                }
                            }
                        }
                        is Internal -> {
                            val (internal1) = matchVal1
                            visit(Position.Internal(internal1))
                            internal1.firstEdge()
                        }
                    }
            }
        }
    }
}

/**
 * Calculates the number of elements in a (sub)tree.
 */
public fun <K, V> NodeRef<Immut, K, V, LeafOrInternal>.calcLength(): Int {
    var result = 0
    this.visitNodesInOrder({ pos ->
        when (pos) {
            is Position.Leaf -> {
                val (node) = pos
                result += node.len()
            }
            is Position.Internal -> {
                val (node1) = pos
                result += node1.len()
            }
            Position.InternalKV -> Unit
        }
    })
    return result
}

/**
 * Returns the leaf edge closest to a KV for forward navigation.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>.nextLeafEdge(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> =
    when (val matchVal = this.force()) {
        is Leaf -> {
            val (leafKv) = matchVal
            leafKv.rightEdge()
        }
        is Internal -> {
            val (internalKv) = matchVal
            val nextInternalEdge = internalKv.rightEdge()
            nextInternalEdge.descend().firstLeafEdge()
        }
    }

/**
 * Returns the leaf edge closest to a KV for backward navigation.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V> Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, KV>.nextBackLeafEdge(): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> =
    when (val matchVal = this.force()) {
        is Leaf -> {
            val (leafKv) = matchVal
            leafKv.leftEdge()
        }
        is Internal -> {
            val (internalKv) = matchVal
            val nextInternalEdge = internalKv.leftEdge()
            nextInternalEdge.descend().lastLeafEdge()
        }
    }

/**
 * Returns the leaf edge corresponding to the first point at which the
 * given bound is true.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q> NodeRef<BorrowType, K, V, LeafOrInternal>.lowerBound(bound: SearchBound<Q>): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> where K : Borrow<Q> {
    var bound1 = bound
    var node = this
    while (true) {
        val (edge, newBound) = node.findLowerBoundEdge(bound1)
        when (val matchVal = edge.force()) {
            is Leaf -> {
                val (edge1) = matchVal
                return edge1
            }
            is Internal -> {
                val (edge2) = matchVal
                node = edge2.descend()
                bound1 = newBound
            }
        }
    }
}

/**
 * Returns the leaf edge corresponding to the last point at which the
 * given bound is true.
 */
public fun <BorrowType : io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.BorrowType, K, V, Q> NodeRef<BorrowType, K, V, LeafOrInternal>.upperBound(bound: SearchBound<Q>): Handle<NodeRef<BorrowType, K, V, Leaf>, Edge> where K : Borrow<Q> {
    var bound1 = bound
    var node = this
    while (true) {
        val (edge, newBound) = node.findUpperBoundEdge(bound1)
        when (val matchVal = edge.force()) {
            is Leaf -> {
                val (edge1) = matchVal
                return edge1
            }
            is Internal -> {
                val (edge2) = matchVal
                node = edge2.descend()
                bound1 = newBound
            }
        }
    }
}

/**
 * # Safety
 * - `self` must be the root.
 * - The tree shouldn't be accessed anymore.
 */
public fun <K, V, A : AllocatorClone> NodeRef<Dying, K, V, LeafOrInternal>.dropTree(alloc: A) {
    /**
     * Deallocates `node` and returns the next node in postorder traversal
     *
     * # Safety
     *
     * The node must be initialized, all kvs in node must have already
     * been dropped or moved out and its children must have already been deallocated.
     * Also the node can't be touched anymore.
     */
    fun <K, V, A : AllocatorClone> deallocateAndNextPostorderNode(node: NodeRef<Dying, K, V, LeafOrInternal>, alloc1: A): NodeRef<Dying, K, V, LeafOrInternal>? {
        // SAFETY: guaranteed by the caller
        val parent = (node.deallocateAndAscend(alloc1) ?: return null)
        return run {
            val matchVal = parent.rightKv()
            when (matchVal) {
                is Result.Ok<*, *> -> {
                    val kv = matchVal.value
                    kv
                        .rightEdge()
                        .descend()
                        .firstLeafEdge()
                        .intoNode()
                        .forgetType()
                }
                is Result.Err<*, *> -> {
                    val lastEdge = matchVal.error
                    lastEdge.intoNode().forgetType()
                }
            }
        }
    }
    open class DropGuard<K, V, A : AllocatorClone>(
        public var node: NodeRef<Dying, K, V, LeafOrInternal>?,
        public var alloc: A,
    ) {
        public open fun drop() {
            when (val matchVal = this.node.also { this.node = null }) {
                null -> {}
                else -> {
                    val cur = matchVal
                    var next = deallocateAndNextPostorderNode(cur, this.alloc.clone())
                    while (next != null) {
                        val node = next
                        // SAFETY: Every node only calls the function once
                        node.dropKvs()
                        next = deallocateAndNextPostorderNode(node, this.alloc.clone())
                    }
                }
            }
        }
    }
    val node = this.firstLeafEdge().intoNode().forgetType()
    val guard = DropGuard(node = node, alloc = alloc)
    while (true) {
        // SAFETY: all kvs of each node are dropped once
        guard.node!!.dropKvs()
        // SAFETY: Nodes are deallocated in postorder, and each node is deallocated exactly once.
        when (val matchVal = deallocateAndNextPostorderNode((guard.node.also { guard.node = null })!!, guard.alloc.clone())) {
            null -> {
                return
            }
            else -> {
                val next = matchVal
                guard.node = next
            }
        }
    }
}
