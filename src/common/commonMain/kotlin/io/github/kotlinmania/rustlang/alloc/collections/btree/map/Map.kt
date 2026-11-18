// port-lint: source collections/btree/map.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.map

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.btree.borrow.DormantMutRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.dedupsortediter.DedupSortedIter
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.Entry
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.Entry.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.OccupiedEntry
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.OccupiedError
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.VacantEntry
import io.github.kotlinmania.rustlang.alloc.collections.btree.navigate.LazyLeafRange
import io.github.kotlinmania.rustlang.alloc.collections.btree.navigate.LeafRange
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Dying
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ForceResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Handle
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Immut
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Internal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.LeafOrInternal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.MIN_LEN_AFTER_SPLIT
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Mut
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.NodeRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ValMut
import io.github.kotlinmania.rustlang.alloc.collections.btree.search.SearchBound
import io.github.kotlinmania.rustlang.alloc.collections.btree.search.SearchResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.setval.SetValZST
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.mem.swap
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.iterMut
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

private class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Minimum number of elements in a node that is not a root.
 * We might temporarily have fewer elements during methods.
 */
public val MIN_LEN: Int = MIN_LEN_AFTER_SPLIT

// A tree in a `BTreeMap` is a tree in the `node` module with additional invariants:
// - Keys must appear in ascending order (according to the key's type).
// - Every non-leaf node contains at least 1 element (has at least 2 children).
// - Every non-root node contains at least MIN_LEN elements.
//
// An empty map is represented either by the absence of a root node or by a
// root node that is an empty leaf.

/**
 * An ordered map based on a [B-Tree].
 *
 * Given a key type with a [total order], an ordered map stores its entries in key order.
 * That means that keys must be of a type that implements the [Ord] trait,
 * such that two keys can always be compared to determine their [Ordering].
 * Examples of keys with a total order are strings with lexicographical order,
 * and numbers with their natural order.
 *
 * Iterators obtained from functions such as [BTreeMap.iter], [BTreeMap.intoIter], [BTreeMap.values], or
 * [BTreeMap.keys] produce their items in key order, and take worst-case logarithmic and
 * amortized constant time per item returned.
 *
 * It is a logic error for a key to be modified in such a way that the key's ordering relative to
 * any other key, as determined by the [Ord] trait, changes while it is in the map. This is
 * normally only possible through [Cell], [RefCell], global state, I/O, or unchecked code.
 * The behavior resulting from such a logic error is not specified, but will be encapsulated to the
 * `BTreeMap` that observed the logic error and not result in undefined behavior. This could
 * include panics, incorrect results, aborts, memory leaks, and non-termination.
 *
 * # Examples
 *
 * ```kotlin
 * // type inference lets us omit an explicit type signature (which
 * // would be `BTreeMap<String, String>` in this example).
 * var movieReviews = mutableMapOf()
 * // review some movies.
 * movieReviews.put("Office Space", "Deals with real issues in the workplace.")
 * movieReviews.put("Pulp Fiction", "Masterpiece.")
 * movieReviews.put("The Godfather", "Very enjoyable.")
 * movieReviews.put("The Blues Brothers", "Eye lyked it a lot.")
 * // check for a specific one.
 * if (!movieReviews.containsKey("Les Misérables")) {
 *     println("We've got ${movieReviews.size} reviews, but Les Misérables ain't one.")
 * }
 * // oops, this review has a lot of spelling mistakes, let's delete it.
 * movieReviews.remove("The Blues Brothers")
 * // look up the values associated with some keys.
 * val toFind = listOf("Up!", "Office Space")
 * for (movie in toFind) {
 *     when (val matchVal = movieReviews.get(movie)) {
 *         null -> println("$movie is unreviewed.")
 *         else -> {
 *             val review = matchVal
 *             println("$movie: $review")
 *         }
 *     }
 * }
 * // Look up the value for a key (will panic if the key is not found).
 * println("Movie review: " + (movieReviews["Office Space"]).toString())
 * // iterate over everything.
 * for ((movie1, review1) in movieReviews) {
 *     println("$movie: \"$review\"")
 * }
 * ```
 *
 * A `BTreeMap` with a known list of items can be initialized from an array:
 *
 * ```kotlin
 * val solarDistance = mutableMapOf("Mercury" to 0.4, "Venus" to 0.7, "Earth" to 1.0, "Mars" to 1.5)
 * ```
 *
 * ## `Entry` API
 *
 * `BTreeMap` implements an [Entry.Api], which allows for complex
 * methods of getting, setting, updating and removing keys and their values:
 *
 * [Entry.Api]: BTreeMap.entry
 *
 * ```kotlin
 * fun randomStatBuff(): UByte {
 *     // could actually return some random value here - let's just return
 *     // some fixed value for now
 *     return 42u.toUByte()
 * }
 * // type inference lets us omit an explicit type signature (which
 * // would be `BTreeMap<String, UByte>` in this example).
 * var playerStats = mutableMapOf()
 * // insert a key only if it doesn't already exist
 * playerStats.entry("health").orInsert(100)
 * // insert a key using a function that provides a new value only if it
 * // doesn't already exist
 * playerStats.entry("defence").orInsertWith(::randomStatBuff)
 * // update a key, guarding against the key possibly not being set
 * val stat = playerStats.entry("attack").orInsert(100)
 * stat += randomStatBuff()
 * // modify an entry before an insert with in-place mutation
 * playerStats.entry("mana").andModify({ mana -> mana += 200 }).orInsert(100)
 * ```
 *
 * # Background
 *
 * A B-tree is (like) a [binary search tree], but adapted to the natural granularity that modern
 * machines like to consume data at. This means that each node contains an entire array of elements,
 * instead of just a single element.
 *
 * B-Trees represent a fundamental compromise between cache-efficiency and actually minimizing
 * the amount of work performed in a search. In theory, a binary search tree (BST) is the optimal
 * choice for a sorted map, as a perfectly balanced BST performs the theoretical minimum number of
 * comparisons necessary to find an element (log<sub>2</sub>n). However, in practice the way this
 * is done is *very* inefficient for modern computer architectures. In particular, every element
 * is stored in its own individually heap-allocated node. This means that every single insertion
 * triggers a heap-allocation, and every comparison is a potential cache-miss due to the indirection.
 * Since both heap-allocations and cache-misses are notably expensive in practice, we are forced to,
 * at the very least, reconsider the BST strategy.
 *
 * A B-Tree instead makes each node contain B-1 to 2B-1 elements in a contiguous array. By doing
 * this, we reduce the number of allocations by a factor of B, and improve cache efficiency in
 * searches. However, this does mean that searches will have to do *more* comparisons on average.
 * The precise number of comparisons depends on the node search strategy used. For optimal cache
 * efficiency, one could search the nodes linearly. For optimal comparisons, one could search
 * the node using binary search. As a compromise, one could also perform a linear search
 * that initially only checks every i<sup>th</sup> element for some choice of i.
 *
 * Currently, our implementation simply performs naive linear search. This provides excellent
 * performance on *small* nodes of elements which are cheap to compare. However in the future we
 * would like to further explore choosing the optimal search strategy based on the choice of B,
 * and possibly other factors. Using linear search, searching for a random element is expected
 * to take B * log(n) comparisons, which is generally worse than a BST. In practice,
 * however, performance is excellent.
 *
 * [B-Tree]: https://en.wikipedia.org/wiki/B-tree
 * [binary search tree]: https://en.wikipedia.org/wiki/BinarySearchTree
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public open class BTreeMap<K, V, A : AllocatorClone>(
    public var root: Root<K, V>?,
    public var length: Int,
    // Although some of the accessory types store a copy of the allocator, the nodes do not.
    // Because allocations will remain live as long as any copy (like this one) of the allocator
    // is live, it's unnecessary to store the allocator in each node.
    /**
     * `ManuallyDrop` to control drop order (needs to be dropped after all the nodes).
     */
    public var alloc: A,
    // For dropck; the `Holder` avoids making the `Unpin` impl more strict than before
    public var marker: Unit,
) : Iterable<Pair<K, V>> {
    override fun iterator(): Iterator<Pair<K, V>> = this.intoIter().iterator()

    public open fun drop() {
        when (val matchVal = this.root.also { this.root = null }) {
            null -> {
                // SAFETY: The allocator has not been moved out and won't be used after this point.
                ManuallyDrop.drop(this.alloc)
            }
            else -> {
                val root = matchVal
                // SAFETY: The tree won't be used after this point.
                // The allocator has not been moved out and will be moved into `dropTree`.
                root.intoDying().dropTree(ManuallyDrop.take(this.alloc))
            }
        }
    }

    // Note: This implementation is "wrong", but changing it would be a breaking change.
    // (The bounds of the automatic `UnwindSafe` implementation have been like this since Rust 1.50.)
    // Maybe we can fix it nonetheless with a crater run, or if the `UnwindSafe`
    // traits are deprecated, or disarmed (no longer causing hard errors) in the future.

    public open fun clone(): BTreeMap<K, V, A> {
        fun <K, V, A : AllocatorClone> cloneSubtree(node: NodeRef<Immut, K, V, LeafOrInternal>, alloc: A): BTreeMap<K, V, A> =
            when (val matchVal = node.force()) {
                is Leaf -> {
                    val (leaf) = matchVal
                    var outTree = BTreeMap(root = Root.new(alloc.clone()), length = 0, alloc = alloc, marker = Unit)
                    run {
                        val root = outTree.root!!
                        // unwrap succeeds because we just wrapped
                        var outNode =
                            when (val matchVal1 = root.borrowMut().force()) {
                                is Leaf -> {
                                    val (leaf1) = matchVal1
                                    leaf1
                                }
                                is Internal -> error("unreachable")
                            }
                        var inEdge = leaf.firstEdge()
                        while (true) {
                            val matchVal2 = inEdge.rightKv()
                            if (matchVal2 !is Result.Ok<*, *>) break
                            val kv = matchVal2.okValue()
                            val (k, v) = kv.intoKv()
                            inEdge = kv.rightEdge()
                            outNode.push(k.clone(), v.clone())
                            outTree.length += 1
                        }
                    }
                    outTree
                }
                is Internal -> {
                    val (internal) = matchVal
                    var outTree1 = cloneSubtree(internal.firstEdge().descend(), alloc.clone())
                    run {
                        val outRoot = outTree1.root!!
                        val outNode1 = outRoot.pushInternalLevel(alloc.clone())
                        var inEdge1 = internal.firstEdge()
                        while (true) {
                            val matchVal3 = inEdge1.rightKv()
                            if (matchVal3 !is Result.Ok<*, *>) break
                            val kv1 = matchVal3.okValue()
                            val (k1, v1) = kv1.intoKv()
                            inEdge1 = kv1.rightEdge()
                            val k2 = k1.clone()
                            val v2 = v1.clone()
                            val subtree = cloneSubtree(inEdge1.descend(), alloc.clone())
                            // We can't destructure subtree directly
                            // because BTreeMap implements Drop
                            val (subroot, sublength) = run {
                                val subtree1 = subtree
                                // ignore-tidy-undocumented-unchecked
                                val root1 = read(subtree1.root)
                                val length = subtree1.length
                                Pair(root1, length)
                            }
                            outNode1.push(k2, v2, subroot.unwrapOrElse({ Root.new(alloc.clone()) }))
                            outTree1.length += 1 + sublength
                        }
                    }
                    outTree1
                }
            }
        return if (this.isEmpty()) {
            BTreeMap.newIn(this.alloc.clone())
        } else {
            cloneSubtree(this.root!!.reborrow(), this.alloc.clone())
            // unwrap succeeds because not empty
        }
    }

    /**
     * Clears the map, removing all elements.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "a")
     * a.clear()
     * check(a.isEmpty())
     * ```
     */
    public open fun clear() {
        // avoid moving the allocator
        (BTreeMap(root = this.root.also { this.root = null }, length = replace(this.length, 0), alloc = this.alloc.clone(), marker = Unit)).let { }
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.alloc

    /**
     * Returns a reference to the value corresponding to the key.
     *
     * The key may be any borrowed form of the map's key type, but the ordering
     * on the borrowed form *must* match the ordering on the key type.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.get(1) == "a")
     * check(map.get(2) == null)
     * ```
     */
    public open fun <Q> get(key: Q): V? {
        val rootNode = (this.root ?: return null).reborrow()
        return when (val matchVal = rootNode.searchTree(key)) {
            is Found<*, *, *, *, *> -> {
                val (handle) = matchVal
                handle.intoKv().second
            }
            is GoDown<*, *, *, *, *> -> null
        }
    }

    /**
     * Returns the key-value pair corresponding to the supplied key. This is
     * potentially useful:
     * - for key types where non-identical keys can be considered equal;
     * - for getting the `K` stored key value from a borrowed `Q` lookup key; or
     * - for getting a reference to a key with the same scope as the collection.
     *
     * The supplied key may be any borrowed form of the map's key type, but the ordering
     * on the borrowed form *must* match the ordering on the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * open class S(public var id: UInt, public var name: String) {
     *     public open fun clone(): S = S(id, name)
     *
     *     public open fun eq(other: S): Boolean {
     *         return this.id == other.id
     *     }
     *
     *     public open fun partialCmp(other: S): Ordering? {
     *         return this.id.partialCmp(other.id)
     *     }
     *
     *     public open fun cmp(other: S): Ordering {
     *         return this.id.cmp(other.id)
     *     }
     * }
     * val jA = S(id = 1u, name = "Jessica")
     * val jB = S(id = 1u, name = "Jess")
     * val p = S(id = 2u, name = "Paul")
     * check(jA == jB)
     * var map = mutableMapOf()
     * map.put(jA, "Paris")
     * check(map.getKeyValue(jA) == Pair(jA, "Paris"))
     * check(map.getKeyValue(jB) == Pair(jA, "Paris"))
     * // the notable case
     * check(map.getKeyValue(p) == null)
     * ```
     */
    public open fun <Q> getKeyValue(k: Q): Pair<K, V>? {
        val rootNode = (this.root ?: return null).reborrow()
        return when (val matchVal = rootNode.searchTree(k)) {
            is Found<*, *, *, *, *> -> {
                val (handle) = matchVal
                handle.intoKv()
            }
            is GoDown<*, *, *, *, *> -> null
        }
    }

    /**
     * Returns the first key-value pair in the map.
     * The key in this pair is the minimum key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * check(map.firstKeyValue() == null)
     * map.put(1, "b")
     * map.put(2, "a")
     * check(map.firstKeyValue() == Pair(1, "b"))
     * ```
     */
    public open fun firstKeyValue(): Pair<K, V>? {
        val rootNode = (this.root ?: return null).reborrow()
        return rootNode.firstLeafEdge().rightKv().ok()?.let(Handle::intoKv)
    }

    /**
     * Returns the first entry in the map for in-place manipulation.
     * The key of this entry is the minimum key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * map.put(2, "b")
     * when (val matchVal = map.firstEntry()) {
     *     null -> {}
     *     else -> {
     *         val entry = matchVal
     *         if (entry.key() > 0) {
     *             entry.insert("first")
     *         }
     *     }
     * }
     * check(map.get(1)!! == "first")
     * check(map.get(2)!! == "b")
     * ```
     */
    public open fun firstEntry(): OccupiedEntry<K, V, A>? {
        val (map, dormantMap) = DormantMutRef.new(this)
        val rootNode = (map.root.asMut() ?: return null).borrowMut()
        val kv = (rootNode.firstLeafEdge().rightKv().ok() ?: return null)
        return OccupiedEntry(handle = kv.forgetNodeType(), dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit)
    }

    /**
     * Removes and returns the first element in the map.
     * The key of this element is the minimum key that was in the map.
     *
     * # Examples
     *
     * Draining elements in ascending order, while keeping a usable map each iteration.
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * map.put(2, "b")
     * while (true) {
     *     val matchVal = map.popFirst()
     *     if (matchVal == null) break
     *     val key = matchVal.first
     *     val value = matchVal.second
     *     check(map.iter().all({ (k, v) -> k > key }))
     * }
     * check(map.isEmpty())
     * ```
     */
    public open fun popFirst(): Pair<K, V>? = this.firstEntry().map({ entry -> entry.removeEntry() })

    /**
     * Returns the last key-value pair in the map.
     * The key in this pair is the maximum key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "b")
     * map.put(2, "a")
     * check(map.lastKeyValue() == Pair(2, "a"))
     * ```
     */
    public open fun lastKeyValue(): Pair<K, V>? {
        val rootNode = (this.root ?: return null).reborrow()
        return rootNode.lastLeafEdge().leftKv().ok()?.let(Handle::intoKv)
    }

    /**
     * Returns the last entry in the map for in-place manipulation.
     * The key of this entry is the maximum key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * map.put(2, "b")
     * when (val matchVal = map.lastEntry()) {
     *     null -> {}
     *     else -> {
     *         val entry = matchVal
     *         if (entry.key() > 0) {
     *             entry.insert("last")
     *         }
     *     }
     * }
     * check(map.get(1)!! == "a")
     * check(map.get(2)!! == "last")
     * ```
     */
    public open fun lastEntry(): OccupiedEntry<K, V, A>? {
        val (map, dormantMap) = DormantMutRef.new(this)
        val rootNode = (map.root.asMut() ?: return null).borrowMut()
        val kv = (rootNode.lastLeafEdge().leftKv().ok() ?: return null)
        return OccupiedEntry(handle = kv.forgetNodeType(), dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit)
    }

    /**
     * Removes and returns the last element in the map.
     * The key of this element is the maximum key that was in the map.
     *
     * # Examples
     *
     * Draining elements in descending order, while keeping a usable map each iteration.
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * map.put(2, "b")
     * while (true) {
     *     val matchVal = map.popLast()
     *     if (matchVal == null) break
     *     val key = matchVal.first
     *     val value = matchVal.second
     *     check(map.iter().all({ (k, v) -> k < key }))
     * }
     * check(map.isEmpty())
     * ```
     */
    public open fun popLast(): Pair<K, V>? = this.lastEntry().map({ entry -> entry.removeEntry() })

    /**
     * Returns `true` if the map contains a value for the specified key.
     *
     * The key may be any borrowed form of the map's key type, but the ordering
     * on the borrowed form *must* match the ordering on the key type.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.containsKey(1) == true)
     * check(map.containsKey(2) == false)
     * ```
     */
    public open fun <Q> containsKey(key: Q): Boolean = this.get(key) != null

    // See `get` for implementation notes, this is basically a copy-paste with mut's added

    /**
     * Returns a mutable reference to the value corresponding to the key.
     *
     * The key may be any borrowed form of the map's key type, but the ordering
     * on the borrowed form *must* match the ordering on the key type.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * when (val matchVal = map.get(1)) {
     *     null -> {}
     *     else -> {
     *         val x = matchVal
     *         x = "b"
     *     }
     * }
     * check(map[1] == "b")
     * ```
     */
    public open fun <Q> getMut(key: Q): V? {
        val rootNode = (this.root ?: return null).borrowMut()
        return when (val matchVal = rootNode.searchTree(key)) {
            is Found<*, *, *, *, *> -> {
                val (handle) = matchVal
                handle.intoValMut()
            }
            is GoDown<*, *, *, *, *> -> null
        }
    }

    /**
     * Inserts a key-value pair into the map.
     *
     * If the map did not have this key present, `None` is returned.
     *
     * If the map did have this key present, the value is updated, and the old
     * value is returned. The key is not updated, though; this matters for
     * types that can be `==` without being identical. See the [module-level
     * documentation] for more.
     *
     * [module-level documentation]: index.html#insert-and-complex-keys
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * check(map.put(37, "a") == null)
     * check(map.isEmpty() == false)
     * map.put(37, "b")
     * check(map.put(37, "c") == "b")
     * check(map[37] == "c")
     * ```
     */
    public open fun insert(key: K, value: V): V? =
        when (val matchVal = this.entry(key)) {
            is Occupied -> {
                val (entry) = matchVal
                entry.insert(value)
            }
            is Vacant -> {
                val (entry1) = matchVal
                entry1.insert(value)
                null
            }
        }

    /**
     * Tries to insert a key-value pair into the map, and returns
     * a mutable reference to the value in the entry.
     *
     * If the map already had this key present, nothing is updated, and
     * an error containing the occupied entry, key, and the value is returned.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(mapTryInsert)
     * public fun doctestEntry() {
     *     var map = mutableMapOf()
     *     check(map.tryInsert(37, "a").unwrap() == "a")
     *     val err = map.tryInsert(37, "b").unwrapErr()
     *     check(err.entry.key() == 37)
     *     check(err.entry.get() == "a")
     *     check(err.key == 37)
     *     check(err.value == "b")
     * }
     * ```
     */
    public open fun tryInsert(key: K, value: V): Result<V, OccupiedError<K, V, A>> {
        val (map, dormantMap) = DormantMutRef.new(this)
        val handle2 =
            when (val matchVal = map.root) {
                null -> null
                else -> {
                    val root = matchVal
                    when (val matchVal1 = root.borrowMut().searchTree(key)) {
                        is Found<*, *, *, *, *> -> {
                            val (handle) = matchVal1
                            val entry = OccupiedEntry(handle = handle, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit)
                            return Result.Err<V, OccupiedError<K, V, A>>(OccupiedError(entry = entry, key = key, value = value))
                        }
                        is GoDown<*, *, *, *, *> -> {
                            val (handle1) = matchVal1
                            handle1
                        }
                    }
                }
            }
        val entry1 = VacantEntry(key = key, handle = handle2, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit)
        return Result.Ok<V, OccupiedError<K, V, A>>(entry1.insert(value))
    }

    /**
     * Removes a key from the map, returning the value at the key if the key
     * was previously in the map.
     *
     * The key may be any borrowed form of the map's key type, but the ordering
     * on the borrowed form *must* match the ordering on the key type.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.remove(1) == "a")
     * check(map.remove(1) == null)
     * ```
     */
    public open fun <Q> remove(key: Q): V? = this.removeEntry(key).map({ (_, v) -> v })

    /**
     * Removes a key from the map, returning the stored key and value if the key
     * was previously in the map.
     *
     * The key may be any borrowed form of the map's key type, but the ordering
     * on the borrowed form *must* match the ordering on the key type.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.removeEntry(1) == Pair(1, "a"))
     * check(map.removeEntry(1) == null)
     * ```
     */
    public open fun <Q> removeEntry(key: Q): Pair<K, V>? {
        val (map, dormantMap) = DormantMutRef.new(this)
        val rootNode = (map.root.asMut() ?: return null).borrowMut()
        return when (val matchVal = rootNode.searchTree(key)) {
            is Found<*, *, *, *, *> -> {
                val (handle) = matchVal
                OccupiedEntry(handle = handle, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit).removeEntry()
            }
            is GoDown<*, *, *, *, *> -> null
        }
    }

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all pairs `(k, v)` for which `f(k, v)` returns `false`.
     * The elements are visited in ascending key order.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x * 10) }).toMap(LinkedHashMap())
     * // Keep only the elements with even-numbered keys.
     * map.retain({ k -> k % 2 == 0 })
     * check(map.intoIter().eq(mutableListOf(Pair(0, 0), Pair(2, 20), Pair(4, 40), Pair(6, 60))))
     * ```
     */
    public open fun retain(f: (K, V) -> Boolean) {
        this.extractIf(0 until 0, { k, v -> !f(k, v) }).forEach({ drop(it) })
    }

    /**
     * Moves all elements from `other` into `self`, leaving `other` empty.
     *
     * If a key from `other` is already present in `self`, the respective
     * value from `self` will be overwritten with the respective value from `other`.
     * Similar to [insert], though, the key is not overwritten,
     * which matters for types that can be `==` without being identical.
     *
     * [insert]: BTreeMap.insert
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "a")
     * a.put(2, "b")
     * a.put(3, "c")
     * // Note: Key (3) also present in b.
     * var b = mutableMapOf()
     * b.put(3, "d")
     * // Note: Key (3) also present in a.
     * b.put(4, "e")
     * b.put(5, "f")
     * a.append(b)
     * check(a.size == 5)
     * check(b.size == 0)
     * check(a[1] == "a")
     * check(a[2] == "b")
     * check(a[3] == "d")
     * // Note: "c" has been overwritten.
     * check(a[4] == "e")
     * check(a[5] == "f")
     * ```
     */
    public open fun append(other: BTreeMap<K, V, A>) {
        val other1 = replace(other, BTreeMap.newIn(this.alloc.clone()))
        this.merge(other1, { key, selfVal, otherVal -> otherVal })
    }

    /**
     * Moves all elements from `other` into `self`, leaving `other` empty.
     *
     * If a key from `other` is already present in `self`, then the `conflict`
     * closure is used to return a value to `self`. The `conflict`
     * closure takes in a borrow of `self`s key, `self`s value, and `other`s value
     * in that order.
     *
     * An example of why one might use this method over [append]
     * is to combine `self`s value with `other`s value when their keys conflict.
     *
     * Similar to [insert], though, the key is not overwritten,
     * which matters for types that can be `==` without being identical.
     *
     * [insert]: BTreeMap.insert
     * [append]: BTreeMap.append
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeMerge)
     * public fun doctestEntry() {
     *     var a = mutableMapOf()
     *     a.put(1, "a")
     *     a.put(2, "b")
     *     a.put(3, "c")
     *     // Note: Key (3) also present in b.
     *     var b = mutableMapOf()
     *     b.put(3, "d")
     *     // Note: Key (3) also present in a.
     *     b.put(4, "e")
     *     b.put(5, "f")
     *     // concatenate a's value and b's value
     *     a.merge(b, { aVal, bVal ->
     *         "$aVal$bVal"
     *     })
     *     check(a.size == 5)
     *     // all of b's keys in a
     *     check(a[1] == "a")
     *     check(a[2] == "b")
     *     check(a[3] == "cd")
     *     // Note: "c" has been combined with "d".
     *     check(a[4] == "e")
     *     check(a[5] == "f")
     * }
     * ```
     */
    public open fun merge(other: BTreeMap<K, V, A>, conflict: (K, V, V) -> V, cmpKFn: (Any?, Any?) -> Any?) {
        // Do we have to append anything at all?
        if (other.isEmpty()) {
            return
        }
        // We can just swap `self` and `other` if `self` is empty.
        if (this.isEmpty()) {
            swap(this, other)
            return
        }
        val otherIter = other.intoIter()
        val (firstOtherKey, firstOtherVal) = otherIter.next()!!
        // find the first gap that has the smallest key greater than or equal to
        // the first key from other
        val selfCursor = this.lowerBoundMut(Bound.Included(firstOtherKey))
        when (val matchVal = selfCursor.peekNext()) {
            null -> {
                // SAFETY: reaching here means our cursor is at the end
                // self BTreeMap so we just insert otherKey here
                run {
                    selfCursor.insertBeforeUnchecked(firstOtherKey, firstOtherVal)
                }
            }
            else -> {
                val selfKey = matchVal.first
                when (cmpKFn(selfKey, firstOtherKey)) {
                    Ordering.Equal -> {
                        // if `f` unwinds, the next entry is already removed leaving
                        // the tree in valid state.
                        // Note: Once `MaybeDangling` is implemented, we can optimize
                        // this through using a drop handler and transmutating CursorMutKey<K, V>
                        // to CursorMutKey<ManuallyDrop<K>, ManuallyDrop<V>> (see PR #152418)
                        when (val matchVal1 = selfCursor.removeNext()) {
                            null -> {}
                            else -> {
                                val k = matchVal1.first
                                val v = matchVal1.second
                                val v1 = conflict(k, v, firstOtherVal)
                                // SAFETY: we remove the K, V out of the next entry,
                                // apply `f` to get a new (K, V), and insert it back
                                // into the next entry that the cursor is pointing at
                                selfCursor.insertAfterUnchecked(k, v1)
                            }
                        }
                    }
                    Ordering.Greater -> run {
                        selfCursor.insertBeforeUnchecked(firstOtherKey, firstOtherVal)
                    }
                    Ordering.Less -> {
                        error("Cursor's peek_next should return None.")
                    }
                }
            }
        }
        for ((otherKey, otherVal) in otherIter) {
            while (true) {
                when (val matchVal2 = selfCursor.peekNext()) {
                    null -> {
                        // Note: If we get here, that means all of other's keys are greater than
                        // self's keys. For performance, this should really do a bulk insertion of items
                        // from otherIter into the end of self `BTreeMap`. Maybe this should be
                        // a method for Cursor*?
                        // SAFETY: reaching here means our cursor is at the end
                        // self BTreeMap so we just insert otherKey here
                        run {
                            selfCursor.insertBeforeUnchecked(otherKey, otherVal)
                        }
                        break
                    }
                    else -> {
                        val selfKey1 = matchVal2.first
                        when (cmpKFn(selfKey1, otherKey)) {
                            Ordering.Equal -> {
                                // if `f` unwinds, the next entry is already removed leaving
                                // the tree in valid state.
                                // Note: Once `MaybeDangling` is implemented, we can optimize
                                // this through using a drop handler and transmutating CursorMutKey<K, V>
                                // to CursorMutKey<ManuallyDrop<K>, ManuallyDrop<V>> (see PR #152418)
                                when (val matchVal3 = selfCursor.removeNext()) {
                                    null -> {}
                                    else -> {
                                        val k1 = matchVal3.first
                                        val v2 = matchVal3.second
                                        val v3 = conflict(k1, v2, otherVal)
                                        // SAFETY: we remove the K, V out of the next entry,
                                        // apply `f` to get a new (K, V), and insert it back
                                        // into the next entry that the cursor is pointing at
                                        selfCursor.insertAfterUnchecked(k1, v3)
                                    }
                                }
                                break
                            }
                            Ordering.Greater -> {
                                // SAFETY: we know our selfKey's ordering is greater than otherKey,
                                // so inserting before will guarantee sorted order
                                run {
                                    selfCursor.insertBeforeUnchecked(otherKey, otherVal)
                                }
                                break
                            }
                            Ordering.Less -> {
                                // Note: instead of doing a linear search here,
                                // this can be optimized to search the tree by starting
                                // from selfCursor and going towards the root and then
                                // back down to the proper node -- that should probably
                                // be a new method on Cursor*.
                                selfCursor.next()
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Constructs a double-ended iterator over a sub-range of elements in the map.
     * The simplest way is to use the range syntax `min .. max`, thus `range(min .. max)` will
     * yield elements from min (inclusive) to max (exclusive).
     * The range may also be entered as `(Bound<T>, Bound<T>)`, so for example
     * `range((Excluded(4), Included(10)))` will yield a left-exclusive, right-inclusive
     * range from 4 to 10.
     *
     * # Panics
     *
     * Panics if range `start> end`.
     * Panics if range `start == end` and both bounds are `Excluded`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.bound.Included
     *
     * var map = mutableMapOf()
     * map.put(3, "a")
     * map.put(5, "b")
     * map.put(8, "c")
     * for ((key, value) in map.range(Pair(Included(4), Included(8)))) {
     *     println("$key: $value")
     * }
     * check(Pair(5, "b") == map.range(4 until Int.MAX_VALUE).next())
     * ```
     */
    public open fun <T, R> range(range: R): Range<K, V> where R : RangeBounds<T> =
        when (val matchVal = this.root) {
            null -> {
                Range(inner = LeafRange.none())
            }
            else -> {
                val root = matchVal
                Range(inner = root.reborrow().rangeSearch(range))
            }
        }

    /**
     * Constructs a mutable double-ended iterator over a sub-range of elements in the map.
     * The simplest way is to use the range syntax `min .. max`, thus `range(min .. max)` will
     * yield elements from min (inclusive) to max (exclusive).
     * The range may also be entered as `(Bound<T>, Bound<T>)`, so for example
     * `range((Excluded(4), Included(10)))` will yield a left-exclusive, right-inclusive
     * range from 4 to 10.
     *
     * # Panics
     *
     * Panics if range `start> end`.
     * Panics if range `start == end` and both bounds are `Excluded`.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = listOf(Pair("Alice", 0), Pair("Bob", 0), Pair("Carol", 0), Pair("Cheryl", 0)).into()
     * for ((_, balance) in map.rangeMut("B" until "Cheryl")) {
     *     balance += 100
     * }
     * for ((name, balance1) in map) {
     *     println("$name => $balance")
     * }
     * ```
     */
    public open fun <T, R> rangeMut(range: R): RangeMut<K, V> where R : RangeBounds<T> =
        when (val matchVal = this.root) {
            null -> {
                RangeMut(inner = LeafRange.none(), marker = Unit)
            }
            else -> {
                val root = matchVal
                RangeMut(inner = root.borrowValmut().rangeSearch(range), marker = Unit)
            }
        }

    /**
     * Gets the given key's corresponding entry in the map for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * var count: MutableMap<String, Int> = mutableMapOf()
     * // count the number of occurrences of letters in the vec
     * for (x in listOf("a", "b", "a", "c", "a", "b")) {
     *     count.entry(x).andModify({ curr -> curr += 1 }).orInsert(1)
     * }
     * check(count["a"] == 3)
     * check(count["b"] == 2)
     * check(count["c"] == 1)
     * ```
     */
    public open fun entry(key: K): Entry<K, V, A> {
        val (map, dormantMap) = DormantMutRef.new(this)
        return when (val matchVal = map.root) {
            null -> Vacant(VacantEntry(key = key, handle = null, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit))
            else -> {
                val root = matchVal
                when (val matchVal1 = root.borrowMut().searchTree(key)) {
                    is Found<*, *, *, *, *> -> {
                        val (handle) = matchVal1
                        Occupied(OccupiedEntry(handle = handle, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit))
                    }
                    is GoDown<*, *, *, *, *> -> {
                        val (handle1) = matchVal1
                        Vacant(VacantEntry(key = key, handle = handle1, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit))
                    }
                }
            }
        }
    }

    /**
     * Splits the collection into two at the given key. Returns everything after the given key,
     * including the key. If the key is not present, the split will occur at the nearest
     * greater key, or return an empty map if no such key exists.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "a")
     * a.put(2, "b")
     * a.put(3, "c")
     * a.put(17, "d")
     * a.put(41, "e")
     * val b = a.splitOff(3)
     * check(a.size == 2)
     * check(b.len() == 3)
     * check(a[1] == "a")
     * check(a[2] == "b")
     * check(b[3] == "c")
     * check(b[17] == "d")
     * check(b[41] == "e")
     * ```
     */
    public open fun <Q> splitOff(key: Q): BTreeMap<K, V, A> {
        if (this.isEmpty()) {
            return BTreeMap.newIn(this.alloc.clone())
        }
        val totalNum = this.len()
        val leftRoot = this.root!!
        // unwrap succeeds because not empty
        val rightRoot = leftRoot.splitOff(key, this.alloc.clone())
        val (newLeftLen, rightLen) = Root.calcSplitLength(totalNum, leftRoot, rightRoot)
        this.length = newLeftLen
        return BTreeMap(root = rightRoot, length = rightLen, alloc = this.alloc.clone(), marker = Unit)
    }

    /**
     * Creates an iterator that visits elements (key-value pairs) in the specified range in
     * ascending key order and uses a closure to determine if an element
     * should be removed.
     *
     * If the closure returns `true`, the element is removed from the map and
     * yielded. If the closure returns `false`, or panics, the element remains
     * in the map and will not be yielded.
     *
     * The iterator also lets you mutate the value of each element in the
     * closure, regardless of whether you choose to keep or remove it.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use `extractIf().forEach(drop)` if you do not need the returned iterator,
     * or [retain] with a negated predicate if you also do not need to restrict the range.
     *
     * [retain]: BTreeMap.retain
     *
     * # Examples
     *
     * ```kotlin
     * // Splitting a map into even and odd keys, reusing the original map:
     * var map: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x) }).toMap(LinkedHashMap())
     * val evens: BTreeMap = map.extractIf(0 until 0, { k, v -> k % 2 == 0 }).collect()
     * val odds = map
     * check(evens.keys().copied().collect<Vec>() == listOf(0, 2, 4, 6))
     * check(odds.keys().copied().collect<Vec>() == listOf(1, 3, 5, 7))
     * // Splitting a map into low and high halves, reusing the original map:
     * var map1: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x) }).toMap(LinkedHashMap())
     * val low: BTreeMap = map1.extractIf(0 until 4, { k, v -> true }).collect()
     * val high = map1
     * check(low.keys().copied().collect<Vec>() == listOf(0, 1, 2, 3))
     * check(high.keys().copied().collect<Vec>() == listOf(4, 5, 6, 7))
     * ```
     */
    public open fun <R> extractIf(range: R, pred: (K, V) -> Boolean): ExtractIf<K, V, R, (K, V) -> Boolean, A> where R : RangeBounds<K> {
        val (inner, alloc) = this.extractIfInner(range)
        return ExtractIf(pred = pred, inner = inner, alloc = alloc)
    }

    public open fun <R> extractIfInner(range: R): Pair<ExtractIfInner<K, V, R>, A> where R : RangeBounds<K> =
        when (val matchVal = this.root) {
            null -> {
                Pair(ExtractIfInner(length = this.length, dormantRoot = null, curLeafEdge = null, range = range), this.alloc.clone())
            }
            else -> {
                val root = matchVal
                val (root1, dormantRoot) = DormantMutRef.new(root)
                val first = root1.borrowMut().lowerBound(SearchBound.fromRange(range.startBound()))
                Pair(ExtractIfInner(length = this.length, dormantRoot = dormantRoot, curLeafEdge = first, range = range), this.alloc.clone())
            }
        }

    /**
     * Creates a consuming iterator visiting all the keys, in sorted order.
     * The map cannot be used after calling this.
     * The iterator element type is `K`.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(2, "b")
     * a.put(1, "a")
     * val keys: List<Int> = a.intoKeys().toMutableList()
     * check(keys == listOf(1, 2))
     * ```
     */
    public open fun intoKeys(): IntoKeys<K, V, A> = IntoKeys(inner = this.intoIter())

    /**
     * Creates a consuming iterator visiting all the values, in order by key.
     * The map cannot be used after calling this.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "hello")
     * a.put(2, "goodbye")
     * val values: List<String> = a.intoValues().toMutableList()
     * check(values == listOf("hello", "goodbye"))
     * ```
     */
    public open fun intoValues(): IntoValues<K, V, A> = IntoValues(inner = this.intoIter())

    public open fun intoIter(): Iter<K, V> = this.iter()

    /**
     * Gets an owning iterator over the entries of the map, sorted by key.
     */
    public open fun intoIterIntoIterator(): IntoIter<K, V, A> {
        val me = this
        return when (val matchVal = me.root.also { me.root = null }) {
            null -> {
                IntoIter(range = LazyLeafRange.none(), length = 0, alloc = ManuallyDrop.take(me.alloc))
            }
            else -> {
                val root = matchVal
                val fullRange = root.intoDying().fullRange()
                IntoIter(range = fullRange, length = me.length, alloc = ManuallyDrop.take(me.alloc))
            }
        }
    }

    public open fun <I : Iterable<Pair<K, V>>> extend(iter: I) {
        iter.iterator().forEach({ (k, v) ->
            this.insert(k, v)
        })
    }

    public open fun extendOne(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.insert(k, v)
    }

    public open fun <I : Iterable<Pair<K, V>>> extendKV(iter: I) {
        this.extend(iter.iterator().map({ (key, value) -> Pair(key, value) }))
    }

    public open fun extendOneKV(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.insert(k, v)
    }

    public override fun hashCode(): Int {
        var state = 0
        state.writeLengthPrefix(this.len())
        for (elt in this) {
            state = 31 * state + (elt).hashCode()
        }
        return state
    }

    public open fun eq(other: BTreeMap<K, V, A>): Boolean = this.len() == other.len() && (this.iter().zip(other)).all({ (a, b) -> a == b })

    public open fun partialCmp(other: BTreeMap<K, V, A>): Ordering? = this.iter().partialCmp(other.iter())

    public open fun cmp(other: BTreeMap<K, V, A>): Ordering = this.iter().cmp(other.iter())

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { f.append(entries.joinToString(", ", "{", "}")) } }.entries(this.iter()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    /**
     * Gets an iterator over the entries of the map, sorted by key.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(3, "c")
     * map.put(2, "b")
     * map.put(1, "a")
     * for ((key, value) in map.iter()) {
     *     println("$key: $value")
     * }
     * val (firstKey, firstValue) = map.iter().next()!!
     * check(Pair(firstKey, firstValue) == Pair(1, "a"))
     * ```
     */
    public open fun iter(): Iter<K, V> =
        when (val matchVal = this.root) {
            null -> {
                Iter(range = LazyLeafRange.none(), length = 0)
            }
            else -> {
                val root = matchVal
                val fullRange = root.reborrow().fullRange()
                Iter(range = fullRange, length = this.length)
            }
        }

    /**
     * Gets a mutable iterator over the entries of the map, sorted by key.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * // add 10 to the value if the key isn't "a"
     * for ((key, value) in map.iterMut()) {
     *     if (key != "a") {
     *         value += 10
     *     }
     * }
     * ```
     */
    public open fun iterMut(): IterMut<K, V> =
        when (val matchVal = this.root) {
            null -> {
                IterMut(range = LazyLeafRange.none(), length = 0, marker = Unit)
            }
            else -> {
                val root = matchVal
                val fullRange = root.borrowValmut().fullRange()
                IterMut(range = fullRange, length = this.length, marker = Unit)
            }
        }

    /**
     * Gets an iterator over the keys of the map, in sorted order.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(2, "b")
     * a.put(1, "a")
     * val keys: Vec = a.keys().cloned().collect()
     * check(keys == listOf(1, 2))
     * ```
     */
    public open fun keys(): Keys<K, V> = Keys(inner = this.iter())

    /**
     * Gets an iterator over the values of the map, in order by key.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "hello")
     * a.put(2, "goodbye")
     * val values: List<String> = a.values().cloned().toMutableList()
     * check(values == listOf("hello", "goodbye"))
     * ```
     */
    public open fun values(): Values<K, V> = Values(inner = this.iter())

    /**
     * Gets a mutable iterator over the values of the map, in order by key.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "hello")
     * a.put(2, "goodbye")
     * for (value in a.valuesMut()) {
     *     value.pushStr("!")
     * }
     * val values: List<String> = a.values().cloned().toMutableList()
     * check(values == listOf("hello!", "goodbye!"))
     * ```
     */
    public open fun valuesMut(): ValuesMut<K, V> = ValuesMut(inner = this.iterMut())

    /**
     * Returns the number of elements in the map.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * check(a.size == 0)
     * a.put(1, "a")
     * check(a.size == 1)
     * ```
     */
    public open fun len(): Int = this.length

    /**
     * Returns `true` if the map contains no elements.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * check(a.isEmpty())
     * a.put(1, "a")
     * check(!a.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Returns a [Cursor] pointing at the gap before the smallest key
     * greater than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap before the smallest key greater than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap before the smallest key greater than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap before the smallest key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     val map = mutableMapOf(1 to "a", 2 to "b", 3 to "c", 4 to "d")
     *     val cursor = map.lowerBound(Bound.Included(2))
     *     check(cursor.peekPrev() == Pair(1, "a"))
     *     check(cursor.peekNext() == Pair(2, "b"))
     *     val cursor1 = map.lowerBound(Bound.Excluded(2))
     *     check(cursor1.peekPrev() == Pair(2, "b"))
     *     check(cursor1.peekNext() == Pair(3, "c"))
     *     val cursor2 = map.lowerBound(Bound.Unbounded)
     *     check(cursor2.peekPrev() == null)
     *     check(cursor2.peekNext() == Pair(1, "a"))
     * }
     * ```
     */
    public open fun <Q> lowerBound(bound: Bound<Q>): Cursor<K, V> {
        val rootNode =
            when (val matchVal = this.root) {
                null -> { return Cursor(current = null, root = null) }
                else -> {
                    val root = matchVal
                    root.reborrow()
                }
            }
        val edge = rootNode.lowerBound(SearchBound.fromRange(bound))
        return Cursor(current = edge, root = this.root)
    }

    /**
     * Returns a [CursorMut] pointing at the gap before the smallest key
     * greater than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap before the smallest key greater than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap before the smallest key greater than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap before the smallest key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     var map = mutableMapOf(1 to "a", 2 to "b", 3 to "c", 4 to "d")
     *     var cursor = map.lowerBoundMut(Bound.Included(2))
     *     check(cursor.peekPrev() == Pair(1, "a"))
     *     check(cursor.peekNext() == Pair(2, "b"))
     *     var cursor1 = map.lowerBoundMut(Bound.Excluded(2))
     *     check(cursor1.peekPrev() == Pair(2, "b"))
     *     check(cursor1.peekNext() == Pair(3, "c"))
     *     var cursor2 = map.lowerBoundMut(Bound.Unbounded)
     *     check(cursor2.peekPrev() == null)
     *     check(cursor2.peekNext() == Pair(1, "a"))
     * }
     * ```
     */
    public open fun <Q> lowerBoundMut(bound: Bound<Q>): CursorMut<K, V, A> {
        val (root, dormantRoot) = DormantMutRef.new(this.root)
        val rootNode =
            when (val matchVal = root.asMut()) {
                null -> {
                    return CursorMut(inner = CursorMutKey(current = null, root = dormantRoot, length = this.length, alloc = this.alloc))
                }
                else -> {
                    val root1 = matchVal
                    root1.borrowMut()
                }
            }
        val edge = rootNode.lowerBound(SearchBound.fromRange(bound))
        return CursorMut(inner = CursorMutKey(current = edge, root = dormantRoot, length = this.length, alloc = this.alloc))
    }

    /**
     * Returns a [Cursor] pointing at the gap after the greatest key
     * smaller than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap after the greatest key smaller than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap after the greatest key smaller than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap after the greatest key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     val map = mutableMapOf(1 to "a", 2 to "b", 3 to "c", 4 to "d")
     *     val cursor = map.upperBound(Bound.Included(3))
     *     check(cursor.peekPrev() == Pair(3, "c"))
     *     check(cursor.peekNext() == Pair(4, "d"))
     *     val cursor1 = map.upperBound(Bound.Excluded(3))
     *     check(cursor1.peekPrev() == Pair(2, "b"))
     *     check(cursor1.peekNext() == Pair(3, "c"))
     *     val cursor2 = map.upperBound(Bound.Unbounded)
     *     check(cursor2.peekPrev() == Pair(4, "d"))
     *     check(cursor2.peekNext() == null)
     * }
     * ```
     */
    public open fun <Q> upperBound(bound: Bound<Q>): Cursor<K, V> {
        val rootNode =
            when (val matchVal = this.root) {
                null -> { return Cursor(current = null, root = null) }
                else -> {
                    val root = matchVal
                    root.reborrow()
                }
            }
        val edge = rootNode.upperBound(SearchBound.fromRange(bound))
        return Cursor(current = edge, root = this.root)
    }

    /**
     * Returns a [CursorMut] pointing at the gap after the greatest key
     * smaller than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap after the greatest key smaller than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap after the greatest key smaller than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap after the greatest key in the map.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     var map = mutableMapOf(1 to "a", 2 to "b", 3 to "c", 4 to "d")
     *     var cursor = map.upperBoundMut(Bound.Included(3))
     *     check(cursor.peekPrev() == Pair(3, "c"))
     *     check(cursor.peekNext() == Pair(4, "d"))
     *     var cursor1 = map.upperBoundMut(Bound.Excluded(3))
     *     check(cursor1.peekPrev() == Pair(2, "b"))
     *     check(cursor1.peekNext() == Pair(3, "c"))
     *     var cursor2 = map.upperBoundMut(Bound.Unbounded)
     *     check(cursor2.peekPrev() == Pair(4, "d"))
     *     check(cursor2.peekNext() == null)
     * }
     * ```
     */
    public open fun <Q> upperBoundMut(bound: Bound<Q>): CursorMut<K, V, A> {
        val (root, dormantRoot) = DormantMutRef.new(this.root)
        val rootNode =
            when (val matchVal = root.asMut()) {
                null -> {
                    return CursorMut(inner = CursorMutKey(current = null, root = dormantRoot, length = this.length, alloc = this.alloc))
                }
                else -> {
                    val root1 = matchVal
                    root1.borrowMut()
                }
            }
        val edge = rootNode.upperBound(SearchBound.fromRange(bound))
        return CursorMut(inner = CursorMutKey(current = edge, root = dormantRoot, length = this.length, alloc = this.alloc))
    }

    public companion object {
        /**
         * Makes a new empty BTreeMap with a reasonable choice for B.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.Global
         *
         * val map: MutableMap<Int, Int> = BTreeMap.newIn(Global)
         * ```
         */
        public fun <K, V, A : AllocatorClone> newIn(alloc: A): BTreeMap<K, V, A> = BTreeMap(root = null, length = 0, alloc = alloc, marker = Unit)

        /**
         * Makes a `BTreeMap` from a sorted iterator.
         */
        public fun <K, V, A : AllocatorClone, I> bulkBuildFromSortedIter(iter: I, alloc: A): BTreeMap<K, V, A> where I : Iterable<Pair<K, V>> {
            var root = Root.new(alloc.clone())
            val localStorageL1729C8 = AddressableLocal(0, PrimitiveLayouts.I32)
            val length by localStorageL1729C8
            root.bulkPush(DedupSortedIter.new(iter.iterator()), RustMutableReference(localStorageL1729C8.asMutPtr(), localStorageL1729C8.layout), alloc.clone())
            return BTreeMap(root = root, length = length, alloc = alloc, marker = Unit)
        }
    }
}

// Internal functionality for `BTreeSet`.
public fun <K, A : AllocatorClone> BTreeMap<K, SetValZST, A>.replace(key: K): K? {
    val (map, dormantMap) = DormantMutRef.new(this)
    val rootNode = map.root.getOrInsertWith({ Root.new(map.alloc.clone()) }).borrowMut()
    return when (val matchVal = rootNode.searchTree<K>(key)) {
        is Found<*, *, *, *, *> -> {
            val (kv) = matchVal
            io.github.kotlinmania.rustlang.core.mem.replace(kv.keyMut(), key)
        }
        is GoDown<*, *, *, *, *> -> {
            val (handle) = matchVal
            VacantEntry(key = key, handle = handle, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit).insert(SetValZST)
            null
        }
    }
}

public fun <K, A : AllocatorClone, Q> BTreeMap<K, SetValZST, A>.getOrInsertWith(q: Q, f: (Q) -> K): K where K : Borrow<Q> {
    val (map, dormantMap) = DormantMutRef.new(this)
    val rootNode = map.root.getOrInsertWith({ Root.new(map.alloc.clone()) }).borrowMut()
    return when (val matchVal = rootNode.searchTree(q)) {
        is Found<*, *, *, *, *> -> {
            val (handle) = matchVal
            handle.intoKvMut().first
        }
        is GoDown<*, *, *, *, *> -> {
            val (handle1) = matchVal
            val key = f(q)
            check(key.borrow() == q) { "new value is not equal" }
            VacantEntry(key = key, handle = handle1, dormantMap = dormantMap, alloc = map.alloc.clone(), marker = Unit).insertEntry(SetValZST).intoKey()
        }
    }
}

/**
 * An iterator over the entries of a `BTreeMap`.
 *
 * This `class` is created by the [iter] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [iter]: BTreeMap.iter
 */
public open class Iter<K, V> internal constructor(
    rangeCtorArg: LazyLeafRange<Immut, K, V>,
    public var length: Int,
) : TrustedLen, Iterable<Pair<K, V>> {
    public var range: LazyLeafRange<Immut, K, V> = rangeCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): Pair<K, V>? =
        if (this.length == 0) {
            null
        } else {
            this.length -= 1
            // SAFETY: Ensured by check.
            this.range.nextUnchecked()
        }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.length, this.length)

    public open fun last(): Pair<K, V>? = this.nextBack()

    public open fun min(): Pair<K, V>? = this.next()

    public open fun max(): Pair<K, V>? = this.nextBack()

    public open fun nextBack(): Pair<K, V>? =
        if (this.length == 0) {
            null
        } else {
            this.length -= 1
            // SAFETY: Ensured by check.
            this.range.nextBackUnchecked()
        }

    public open fun len(): Int = this.length

    public open fun clone(): Iter<K, V> = Iter(range = this.range.clone(), length = this.length)

    public companion object {
        public operator fun <K, V> invoke(
            range: LazyLeafRange<Immut, K, V>,
            length: Int,
        ): Iter<K, V> = Iter<K, V>(
            rangeCtorArg = range,
            length = length,
        )

        /**
         * Creates an empty `btreeMap.Iter`.
         *
         * ```kotlin
         * import btreemap.Iter
         *
         * val iter: Iter<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V> default(): Iter<K, V> = Iter(range = LazyLeafRange.default(), length = 0)
    }
}

/**
 * A mutable iterator over the entries of a `BTreeMap`.
 *
 * This `class` is created by the [iterMut] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [iterMut]: BTreeMap.iterMut
 */
public open class IterMut<K, V> internal constructor(
    rangeCtorArg: LazyLeafRange<ValMut, K, V>,
    public var length: Int,
    // Be invariant in `K` and `V`
    public var marker: Unit,
) : TrustedLen, Iterable<Pair<K, V>> {
    public var range: LazyLeafRange<ValMut, K, V> = rangeCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val range = Iter(range = this.range.reborrow(), length = this.length)
        return run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(range).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public open fun next(): Pair<K, V>? =
        if (this.length == 0) {
            null
        } else {
            this.length -= 1
            // SAFETY: Ensured by check.
            this.range.nextUnchecked()
        }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.length, this.length)

    public open fun last(): Pair<K, V>? = this.nextBack()

    public open fun min(): Pair<K, V>? = this.next()

    public open fun max(): Pair<K, V>? = this.nextBack()

    public open fun nextBack(): Pair<K, V>? =
        if (this.length == 0) {
            null
        } else {
            this.length -= 1
            // SAFETY: Ensured by check.
            this.range.nextBackUnchecked()
        }

    public open fun len(): Int = this.length

    /**
     * Returns an iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(range = this.range.reborrow(), length = this.length)

    public companion object {
        public operator fun <K, V> invoke(
            range: LazyLeafRange<ValMut, K, V>,
            length: Int,
            marker: Unit,
        ): IterMut<K, V> = IterMut<K, V>(
            rangeCtorArg = range,
            length = length,
            marker = marker,
        )

        /**
         * Creates an empty `btreeMap.IterMut`.
         *
         * ```kotlin
         * import btreemap.IterMut
         *
         * val iter: IterMut<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V> default(): IterMut<K, V> = IterMut(range = LazyLeafRange.default(), length = 0, marker = PhantomData())
    }
}

/**
 * An owning iterator over the entries of a `BTreeMap`, sorted by key.
 *
 * This `class` is created by the [intoIter] method on [BTreeMap]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: IntoIterator.intoIter
 */
public open class IntoIter<K, V, A : AllocatorClone> internal constructor(
    rangeCtorArg: LazyLeafRange<Dying, K, V>,
    public var length: Int,
    /**
     * The BTreeMap will outlive this IntoIter so we don't care about drop order for `alloc`.
     */
    public var alloc: A,
) : TrustedLen, Iterable<Pair<K, V>> {
    public var range: LazyLeafRange<Dying, K, V> = rangeCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns an iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(range = this.range.reborrow(), length = this.length)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun drop() {
        open class DropGuard<K, V, A : AllocatorClone>(
            public var value: IntoIter<K, V, A>,
        ) {
            public var field0: IntoIter<K, V, A>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                // Continue the same loop we perform below. This only runs when unwinding, so we
                // don't have to care about panics this time (they'll abort).
                while (true) {
                    val matchVal = this.value.dyingNext()
                    if (matchVal == null) break
                    val kv = matchVal
                    // SAFETY: we consume the dying handle immediately.
                    kv.dropKeyVal()
                }
            }
        }
        while (true) {
            val matchVal = this.dyingNext()
            if (matchVal == null) break
            val kv = matchVal
            val guard = DropGuard(this)
            // SAFETY: we don't touch the tree before consuming the dying handle.
            kv.dropKeyVal()
            forget(guard)
        }
    }

    /**
     * Core of a `next` method returning a dying KV handle,
     * invalidated by further calls to this function and some others.
     */
    public open fun dyingNext(): Handle<NodeRef<Dying, K, V, LeafOrInternal>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.KV>? =
        if (this.length == 0) {
            this.range.deallocatingEnd(this.alloc.clone())
            null
        } else {
            this.length -= 1
            // ignore-tidy-undocumented-unchecked
            this.range.deallocatingNextUnchecked(this.alloc.clone())
        }

    /**
     * Core of a `nextBack` method returning a dying KV handle,
     * invalidated by further calls to this function and some others.
     */
    public open fun dyingNextBack(): Handle<NodeRef<Dying, K, V, LeafOrInternal>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.KV>? =
        if (this.length == 0) {
            this.range.deallocatingEnd(this.alloc.clone())
            null
        } else {
            this.length -= 1
            // ignore-tidy-undocumented-unchecked
            this.range.deallocatingNextBackUnchecked(this.alloc.clone())
        }

    public open fun next(): Pair<K, V>? {
        // SAFETY: we consume the dying handle immediately.
        return this.dyingNext().map({ kv -> kv.intoKeyVal() })
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.length, this.length)

    public open fun nextBack(): Pair<K, V>? {
        // SAFETY: we consume the dying handle immediately.
        return this.dyingNextBack().map({ kv -> kv.intoKeyVal() })
    }

    public open fun len(): Int = this.length

    public companion object {
        public operator fun <K, V, A : AllocatorClone> invoke(
            range: LazyLeafRange<Dying, K, V>,
            length: Int,
            alloc: A,
        ): IntoIter<K, V, A> = IntoIter<K, V, A>(
            rangeCtorArg = range,
            length = length,
            alloc = alloc,
        )

        /**
         * Creates an empty `btreeMap.IntoIter`.
         *
         * ```kotlin
         * import btreemap.IntoIter
         *
         * val iter: IntoIter<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V, A : AllocatorClone> default(defaultAFn: () -> A): IntoIter<K, V, A> where A : AllocatorClone = IntoIter(range = defaultAFn(), length = 0, alloc = defaultAFn())
    }
}

/**
 * An iterator over the keys of a `BTreeMap`.
 *
 * This `class` is created by the [keys] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [keys]: BTreeMap.keys
 */
public open class Keys<K, V>(
    public var inner: Iter<K, V>,
) : TrustedLen, Iterable<K> {
    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@Keys.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): K? = this.inner.next()?.let({ (k, _) -> k })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): K? = this.nextBack()

    public open fun min(): K? = this.next()

    public open fun max(): K? = this.nextBack()

    public open fun nextBack(): K? = this.inner.nextBack()?.let({ (k, _) -> k })

    public open fun len(): Int = this.inner.len()

    public open fun clone(): Keys<K, V> = Keys(inner = this.inner.clone())

    public companion object {
        /**
         * Creates an empty `btreeMap.Keys`.
         *
         * ```kotlin
         * import btreemap.Keys
         *
         * val iter: Keys<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V> default(): Keys<K, V> = Keys(inner = Iter.default())
    }
}

/**
 * An iterator over the values of a `BTreeMap`.
 *
 * This `class` is created by the [values] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [values]: BTreeMap.values
 */
public open class Values<K, V>(
    public var inner: Iter<K, V>,
) : TrustedLen, Iterable<V> {
    override fun iterator(): Iterator<V> = object : AbstractIterator<V>() {
        override fun computeNext() {
            when (val v = this@Values.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): V? = this.inner.next()?.let({ (_, v) -> v })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): V? = this.nextBack()

    public open fun nextBack(): V? = this.inner.nextBack()?.let({ (_, v) -> v })

    public open fun len(): Int = this.inner.len()

    public open fun clone(): Values<K, V> = Values(inner = this.inner.clone())

    public companion object {
        /**
         * Creates an empty `btreeMap.Values`.
         *
         * ```kotlin
         * import btreemap.Values
         *
         * val iter: Values<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V> default(): Values<K, V> = Values(inner = Iter.default())
    }
}

/**
 * A mutable iterator over the values of a `BTreeMap`.
 *
 * This `class` is created by the [valuesMut] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [valuesMut]: BTreeMap.valuesMut
 */
public open class ValuesMut<K, V>(
    public var inner: IterMut<K, V>,
) : TrustedLen, Iterable<V> {
    override fun iterator(): Iterator<V> = object : AbstractIterator<V>() {
        override fun computeNext() {
            when (val v = this@ValuesMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (_, value) -> value })).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): V? = this.inner.next()?.let({ (_, v) -> v })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): V? = this.nextBack()

    public open fun nextBack(): V? = this.inner.nextBack()?.let({ (_, v) -> v })

    public open fun len(): Int = this.inner.len()

    public companion object {
        /**
         * Creates an empty `btreeMap.ValuesMut`.
         *
         * ```kotlin
         * import btreemap.ValuesMut
         *
         * val iter: ValuesMut<UByte, UByte> = Default.default()
         * check(iter.count() == 0)
         * ```
         */
        public fun <K, V> default(): ValuesMut<K, V> = ValuesMut(inner = IterMut.default())
    }
}

/**
 * An owning iterator over the keys of a `BTreeMap`.
 *
 * This `class` is created by the [intoKeys] method on [BTreeMap].
 * See its documentation for more.
 *
 * [intoKeys]: BTreeMap.intoKeys
 */
public open class IntoKeys<K, V, A : AllocatorClone> internal constructor(
    innerCtorArg: IntoIter<K, V, A>,
) : TrustedLen, Iterable<K> {
    public var inner: IntoIter<K, V, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@IntoKeys.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (key, _) -> key })).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): K? = this.inner.next()?.let({ (k, _) -> k })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): K? = this.nextBack()

    public open fun min(): K? = this.next()

    public open fun max(): K? = this.nextBack()

    public open fun nextBack(): K? = this.inner.nextBack()?.let({ (k, _) -> k })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public operator fun <K, V, A : AllocatorClone> invoke(
            inner: IntoIter<K, V, A>,
        ): IntoKeys<K, V, A> = IntoKeys<K, V, A>(
            innerCtorArg = inner,
        )

        /**
         * Creates an empty `btreeMap.IntoKeys`.
         *
         * ```kotlin
         * import btreemap.IntoKeys
         *
         * val iter: IntoKeys<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V, A : AllocatorClone> default(defaultAFn: () -> A): IntoKeys<K, V, A> where A : AllocatorClone = IntoKeys(inner = defaultAFn())
    }
}

/**
 * An owning iterator over the values of a `BTreeMap`.
 *
 * This `class` is created by the [intoValues] method on [BTreeMap].
 * See its documentation for more.
 *
 * [intoValues]: BTreeMap.intoValues
 */
public open class IntoValues<K, V, A : AllocatorClone> internal constructor(
    innerCtorArg: IntoIter<K, V, A>,
) : TrustedLen, Iterable<V> {
    public var inner: IntoIter<K, V, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<V> = object : AbstractIterator<V>() {
        override fun computeNext() {
            when (val v = this@IntoValues.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (_, value) -> value })).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): V? = this.inner.next()?.let({ (_, v) -> v })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): V? = this.nextBack()

    public open fun nextBack(): V? = this.inner.nextBack()?.let({ (_, v) -> v })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public operator fun <K, V, A : AllocatorClone> invoke(
            inner: IntoIter<K, V, A>,
        ): IntoValues<K, V, A> = IntoValues<K, V, A>(
            innerCtorArg = inner,
        )

        /**
         * Creates an empty `btreeMap.IntoValues`.
         *
         * ```kotlin
         * import btreemap.IntoValues
         *
         * val iter: IntoValues<UByte, UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <K, V, A : AllocatorClone> default(defaultAFn: () -> A): IntoValues<K, V, A> where A : AllocatorClone = IntoValues(inner = defaultAFn())
    }
}

/**
 * An iterator over a sub-range of entries in a `BTreeMap`.
 *
 * This `class` is created by the [range] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [range]: BTreeMap.range
 */
public open class Range<K, V> internal constructor(
    innerCtorArg: LeafRange<Immut, K, V>,
) : Iterable<Pair<K, V>> {
    public var inner: LeafRange<Immut, K, V> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@Range.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun next(): Pair<K, V>? = this.inner.nextChecked()

    public open fun last(): Pair<K, V>? = this.nextBack()

    public open fun min(): Pair<K, V>? = this.next()

    public open fun max(): Pair<K, V>? = this.nextBack()

    public open fun nextBack(): Pair<K, V>? = this.inner.nextBackChecked()

    public open fun clone(): Range<K, V> = Range(inner = this.inner.clone())

    public companion object {
        public operator fun <K, V> invoke(
            inner: LeafRange<Immut, K, V>,
        ): Range<K, V> = Range<K, V>(
            innerCtorArg = inner,
        )

        /**
         * Creates an empty `btreeMap.Range`.
         *
         * ```kotlin
         * import btreemap.Range
         *
         * val iter: Range<UByte, UByte> = Default.default()
         * check(iter.count() == 0)
         * ```
         */
        public fun <K, V> default(): Range<K, V> = Range(inner = LeafRange.default())
    }
}

/**
 * A mutable iterator over a sub-range of entries in a `BTreeMap`.
 *
 * This `class` is created by the [rangeMut] method on [BTreeMap]. See its
 * documentation for more.
 *
 * [rangeMut]: BTreeMap.rangeMut
 */
public open class RangeMut<K, V> internal constructor(
    innerCtorArg: LeafRange<ValMut, K, V>,
    // Be invariant in `K` and `V`
    public var marker: Unit,
) : Iterable<Pair<K, V>> {
    public var inner: LeafRange<ValMut, K, V> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@RangeMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val range = Range(inner = this.inner.reborrow())
        return run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(range).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public open fun next(): Pair<K, V>? = this.inner.nextChecked()

    public open fun last(): Pair<K, V>? = this.nextBack()

    public open fun min(): Pair<K, V>? = this.next()

    public open fun max(): Pair<K, V>? = this.nextBack()

    public open fun nextBack(): Pair<K, V>? = this.inner.nextBackChecked()

    public companion object {
        public operator fun <K, V> invoke(
            inner: LeafRange<ValMut, K, V>,
            marker: Unit,
        ): RangeMut<K, V> = RangeMut<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )

        /**
         * Creates an empty `btreeMap.RangeMut`.
         *
         * ```kotlin
         * import btreemap.RangeMut
         *
         * val iter: RangeMut<UByte, UByte> = Default.default()
         * check(iter.count() == 0)
         * ```
         */
        public fun <K, V> default(): RangeMut<K, V> = RangeMut(inner = LeafRange.default(), marker = Unit)
    }
}

/**
 * Makes a new, empty `BTreeMap`.
 *
 * Does not allocate anything on its own.
 *
 * # Examples
 *
 * ```kotlin
 * var map = mutableMapOf()
 * // entries can now be inserted into the empty map
 * map.put(1, "a")
 * ```
 */
public fun <K, V> BTreeMap.Companion.new(): BTreeMap<K, V, Global> = BTreeMap(root = null, length = 0, alloc = io.github.kotlinmania.rustlang.alloc.alloc.Global(), marker = Unit)

/**
 * This `class` is created by the [extractIf] method on [BTreeMap].
 *
 * [extractIf]: BTreeMap.extractIf
 */
public open class ExtractIf<K, V, R, F, A : AllocatorClone>(
    public var pred: F,
    public var inner: ExtractIfInner<K, V, R>,
    /**
     * The BTreeMap will outlive this IntoIter so we don't care about drop order for `alloc`.
     */
    public var alloc: A,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "ExtractIf").field("peek", this.inner.peek()).finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
}

/**
 * Most of the implementation of ExtractIf are generic over the type
 * of the predicate, thus also serving for BTreeSet.ExtractIf.
 */
public open class ExtractIfInner<K, V, R> internal constructor(
    lengthCtorArg: RustMutableReference<Int>,
    dormantRootCtorArg: DormantMutRef<Root<K, V>>?,
    curLeafEdgeCtorArg: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
    /**
     * Range over which iteration was requested.  We don't need the left side, but we
     * can't extract the right side without requiring K: Clone.
     */
    public var range: R,
) {
    /**
     * Reference to the length field in the borrowed map, updated live.
     */
    public var length: RustMutableReference<Int> = lengthCtorArg
        internal set

    /**
     * Buried reference to the root field in the borrowed map.
     * Wrapped in `Option` to allow drop handler to `take` it.
     */
    public var dormantRoot: DormantMutRef<Root<K, V>>? = dormantRootCtorArg
        internal set

    /**
     * Contains a leaf edge preceding the next element to be returned, or the last leaf edge.
     * Empty if the map has no root, if iteration went beyond the last leaf edge,
     * or if a panic occurred in the predicate.
     */
    public var curLeafEdge: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>? = curLeafEdgeCtorArg
        internal set

    /**
     * Allow Debug implementations to predict the next element.
     */
    public open fun peek(): Pair<K, V>? {
        val edge = (this.curLeafEdge ?: return null)
        return edge.reborrow().nextKv().ok()?.let(Handle::intoKv)
    }

    /**
     * Implementation of a typical `ExtractIf.next` method, given the predicate.
     */
    public open fun <A : AllocatorClone> next(pred: (K, V) -> Boolean, alloc: A): Pair<K, V>? {
        while (true) {
            val matchVal = (this.curLeafEdge.also { this.curLeafEdge = null } ?: return null).nextKv()
            if (matchVal !is Result.Ok<*, *>) break
            val kv = matchVal.okValue()
            val (k, v) = kv.kvMut()
            // On creation, we navigated directly to the left bound, so we need only check the
            // right bound here to decide whether to stop.
            run {
                val matchVal1 = this.range.endBound()
                when {
                    matchVal1 is Bound.Included<*> && k.le(matchVal1.value) -> {
                        val (end) = matchVal1
                        Unit
                    }
                    matchVal1 is Bound.Excluded<*> && k.lt(matchVal1.value) -> {
                        val (end1) = matchVal1
                        Unit
                    }
                    matchVal1 is Bound.Unbounded<*> -> Unit
                    else -> { return null }
                }
            }
            if (pred(k, v)) {
                (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue -= (1); targetPlace.write(currentValue) }
                val (kv1, pos) =
                    kv.removeKvTracking({
                        // SAFETY: we will touch the root in a way that will not
                        // invalidate the position returned.
                        val root = (this.dormantRoot.also { this.dormantRoot = null })!!.awaken()
                        root.popInternalLevel(alloc.clone())
                        this.dormantRoot = DormantMutRef.new(root).second
                    }, alloc.clone())
                this.curLeafEdge = pos
                return kv1
            }
            this.curLeafEdge = kv.nextLeafEdge()
        }
        return null
    }

    /**
     * Implementation of a typical `ExtractIf.sizeHint` method.
     */
    public open fun sizeHint(): Pair<Int, Int?> {
        // In most of the btree iterators, `self.length` is the number of elements
        // yet to be visited. Here, it includes elements that were visited and that
        // the predicate decided not to drain. Making this upper bound more tight
        // during iteration would require an extra field.
        return Pair(0, (this.length).read())
    }

    public companion object {
        public operator fun <K, V, R> invoke(
            length: RustMutableReference<Int>,
            dormantRoot: DormantMutRef<Root<K, V>>?,
            curLeafEdge: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
            range: R,
        ): ExtractIfInner<K, V, R> = ExtractIfInner<K, V, R>(
            lengthCtorArg = length,
            dormantRootCtorArg = dormantRoot,
            curLeafEdgeCtorArg = curLeafEdge,
            range = range,
        )
    }
}

public fun <K, V, R, A : AllocatorClone> ExtractIf<K, V, R, F, A>.next(): Pair<K, V>? where R : RangeBounds<K> = this.inner.next(this.pred, this.alloc.clone())

public fun <K, V, R, A : AllocatorClone> ExtractIf<K, V, R, F, A>.sizeHint(): Pair<Int, Int?> where R : RangeBounds<K> = this.inner.sizeHint()

/**
 * Constructs a `BTreeMap<K, V>` from an iterator of key-value pairs.
 *
 * If the iterator produces any pairs with equal keys,
 * all but one of the corresponding values will be dropped.
 */
public fun <K, V, I : Iterable<Pair<K, V>>> fromIteratorFromIterBTreeMapKV(iter: I): BTreeMap<K, V, Global> {
    val inputs: Vec  = iter.iterator().collect()
    if (inputs.isEmpty()) {
        return BTreeMap.new()
    }
    // use stable sort to preserve the insertion order.
    inputs.sortBy({ a, b -> a.first.cmp(b.first) })
    return BTreeMap.bulkBuildFromSortedIter(inputs, io.github.kotlinmania.rustlang.alloc.alloc.Global())
}

/**
 * Creates an empty `BTreeMap`.
 */
public fun <K, V> defaultBTreeMapKV(): BTreeMap<K, V, Global> = BTreeMap.new()

/**
 * Returns a reference to the value corresponding to the supplied key.
 *
 * # Panics
 *
 * Panics if the key is not present in the `BTreeMap`.
 */
public fun <K, Q, V, A : AllocatorClone> BTreeMap<K, V, A>.index(key: Q): V where K : Borrow<Q> = (this.get(key) ?: error("no entry found for key"))

/**
 * Converts a `[(K, V); N]` into a `BTreeMap<K, V>`.
 *
 * If any entries in the array have equal keys,
 * all but one of the corresponding values will be dropped.
 *
 * ```kotlin
 * val map1 = mutableMapOf(1 to 2, 3 to 4)
 * val map2: BTreeMap = listOf(Pair(1, 2), Pair(3, 4)).into()
 * check(map1 == map2)
 * ```
 */
public fun <K, V> fromBTreeMapKV(arr: List<Pair<K, V>>, n: Int = 0): BTreeMap<K, V, Global> {
    if (n == 0) {
        return BTreeMap.new()
    }
    // use stable sort to preserve the insertion order.
    arr.sortBy({ a, b -> a.first.cmp(b.first) })
    return BTreeMap.bulkBuildFromSortedIter(arr, io.github.kotlinmania.rustlang.alloc.alloc.Global())
}

/**
 * A cursor over a `BTreeMap`.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth.
 *
 * Cursors always point to a gap between two elements in the map, and can
 * operate on the two immediately adjacent elements.
 *
 * A `Cursor` is created with the [BTreeMap.lowerBound] and [BTreeMap.upperBound] methods.
 */
public open class Cursor<K, V> internal constructor(
    currentCtorArg: Handle<NodeRef<Immut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
    public var root: io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root<K, V>?,
) {
    // If current is None then it means the tree has not been allocated yet.
    public var current: Handle<NodeRef<Immut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>? = currentCtorArg
        internal set

    public open fun clone(): Cursor<K, V> {
        val structVal = Cursor(this.current, this.root)
        val current = structVal.current
        val root = structVal.root
        return Cursor(current = current, root = root)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("Cursor"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    /**
     * Advances the cursor to the next gap, returning the key and value of the
     * element that it moved over.
     *
     * If the cursor is already at the end of the map then `None` is returned
     * and the cursor is not moved.
     */
    public open fun next(): Pair<K, V>? {
        val current = (this.current.also { this.current = null } ?: return null)
        return run {
            val matchVal = current.nextKv()
            when (matchVal) { is Result.Ok<*, *> -> {
                val kv = matchVal.okValue()
                val result = kv.intoKv()
                this.current = kv.nextLeafEdge()
                result
            }; is Result.Err<*, *> -> {
                val root = matchVal.errValue()
                this.current = root.lastLeafEdge()
                null
            } }
        }
    }

    /**
     * Advances the cursor to the previous gap, returning the key and value of
     * the element that it moved over.
     *
     * If the cursor is already at the start of the map then `None` is returned
     * and the cursor is not moved.
     */
    public open fun prev(): Pair<K, V>? {
        val current = (this.current.also { this.current = null } ?: return null)
        return run {
            val matchVal = current.nextBackKv()
            when (matchVal) { is Result.Ok<*, *> -> {
                val kv = matchVal.okValue()
                val result = kv.intoKv()
                this.current = kv.nextBackLeafEdge()
                result
            }; is Result.Err<*, *> -> {
                val root = matchVal.errValue()
                this.current = root.firstLeafEdge()
                null
            } }
        }
    }

    /**
     * Returns a reference to the key and value of the next element without
     * moving the cursor.
     *
     * If the cursor is at the end of the map then `None` is returned.
     */
    public open fun peekNext(): Pair<K, V>? = this.clone().next()

    /**
     * Returns a reference to the key and value of the previous element
     * without moving the cursor.
     *
     * If the cursor is at the start of the map then `None` is returned.
     */
    public open fun peekPrev(): Pair<K, V>? = this.clone().prev()

    public companion object {
        public operator fun <K, V> invoke(
            current: Handle<NodeRef<Immut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
            root: io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root<K, V>?,
        ): Cursor<K, V> = Cursor<K, V>(
            currentCtorArg = current,
            root = root,
        )
    }
}

/**
 * A cursor over a `BTreeMap` with editing operations.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth, and can
 * safely mutate the map during iteration. This is because the scope of its yielded
 * references is tied to its own scope, instead of just the underlying map. This means
 * cursors cannot yield multiple elements at once.
 *
 * Cursors always point to a gap between two elements in the map, and can
 * operate on the two immediately adjacent elements.
 *
 * A `CursorMut` is created with the [BTreeMap.lowerBoundMut] and [BTreeMap.upperBoundMut]
 * methods.
 */
public open class CursorMut<K, V, A>(
    public var inner: CursorMutKey<K, V, A>,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("CursorMut"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    /**
     * Advances the cursor to the next gap, returning the key and value of the
     * element that it moved over.
     *
     * If the cursor is already at the end of the map then `None` is returned
     * and the cursor is not moved.
     */
    public open fun next(): Pair<K, V>? {
        val (k, v) = (this.inner.next() ?: return null)
        return Pair(k, v)
    }

    /**
     * Advances the cursor to the previous gap, returning the key and value of
     * the element that it moved over.
     *
     * If the cursor is already at the start of the map then `None` is returned
     * and the cursor is not moved.
     */
    public open fun prev(): Pair<K, V>? {
        val (k, v) = (this.inner.prev() ?: return null)
        return Pair(k, v)
    }

    /**
     * Returns a reference to the key and value of the next element without
     * moving the cursor.
     *
     * If the cursor is at the end of the map then `None` is returned.
     */
    public open fun peekNext(): Pair<K, V>? {
        val (k, v) = (this.inner.peekNext() ?: return null)
        return Pair(k, v)
    }

    /**
     * Returns a reference to the key and value of the previous element
     * without moving the cursor.
     *
     * If the cursor is at the start of the map then `None` is returned.
     */
    public open fun peekPrev(): Pair<K, V>? {
        val (k, v) = (this.inner.peekPrev() ?: return null)
        return Pair(k, v)
    }

    /**
     * Returns a read-only cursor pointing to the same location as the
     * `CursorMut`.
     *
     * The scope of the returned `Cursor` is bound to that of the
     * `CursorMut`, which means it cannot outlive the `CursorMut` and that the
     * `CursorMut` is frozen for the scope of the `Cursor`.
     */
    public open fun asCursor(): Cursor<K, V> = this.inner.asCursor()

    /**
     * Converts the cursor into a [CursorMutKey], which allows mutating
     * the key of elements in the tree.
     *
     * # Safety
     *
     * Since this cursor allows mutating keys, you must ensure that the `BTreeMap`
     * invariants are maintained. Specifically:
     *
     * * The key of the newly inserted element must be unique in the tree.
     * * All keys in the tree must remain in sorted order.
     */
    public open fun withMutableKey(): CursorMutKey<K, V, A> = this.inner

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap after the
     * newly inserted element.
     *
     * # Safety
     *
     * You must ensure that the `BTreeMap` invariants are maintained.
     * Specifically:
     *
     * * The key of the newly inserted element must be unique in the tree.
     * * All keys in the tree must remain in sorted order.
     */
    public open fun insertAfterUnchecked(key: K, value: V) {
        // SAFETY: Upheld by caller.
        this.inner.insertAfterUnchecked(key, value)
    }

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap after the
     * newly inserted element.
     *
     * # Safety
     *
     * You must ensure that the `BTreeMap` invariants are maintained.
     * Specifically:
     *
     * * The key of the newly inserted element must be unique in the tree.
     * * All keys in the tree must remain in sorted order.
     */
    public open fun insertBeforeUnchecked(key: K, value: V) {
        // SAFETY: Upheld by caller.
        this.inner.insertBeforeUnchecked(key, value)
    }

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap before the
     * newly inserted element.
     *
     * If the inserted key is not greater than the key before the cursor
     * (if any), or if it not less than the key after the cursor (if any),
     * then an [UnorderedKeyError] is returned since this would
     * invalidate the [Ord] invariant between the keys of the map.
     */
    public open fun insertAfter(key: K, value: V): Result<Unit, UnorderedKeyError> = this.inner.insertAfter(key, value)

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap after the
     * newly inserted element.
     *
     * If the inserted key is not greater than the key before the cursor
     * (if any), or if it not less than the key after the cursor (if any),
     * then an [UnorderedKeyError] is returned since this would
     * invalidate the [Ord] invariant between the keys of the map.
     */
    public open fun insertBefore(key: K, value: V): Result<Unit, UnorderedKeyError> = this.inner.insertBefore(key, value)

    /**
     * Removes the next element from the `BTreeMap`.
     *
     * The element that was removed is returned. The cursor position is
     * unchanged (before the removed element).
     */
    public open fun removeNext(): Pair<K, V>? = this.inner.removeNext()

    /**
     * Removes the preceding element from the `BTreeMap`.
     *
     * The element that was removed is returned. The cursor position is
     * unchanged (after the removed element).
     */
    public open fun removePrev(): Pair<K, V>? = this.inner.removePrev()
}

/**
 * A cursor over a `BTreeMap` with editing operations, and which allows
 * mutating the key of elements.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth, and can
 * safely mutate the map during iteration. This is because the scope of its yielded
 * references is tied to its own scope, instead of just the underlying map. This means
 * cursors cannot yield multiple elements at once.
 *
 * Cursors always point to a gap between two elements in the map, and can
 * operate on the two immediately adjacent elements.
 *
 * A `CursorMutKey` is created from a [CursorMut] with the
 * [CursorMut.withMutableKey] method.
 *
 * # Safety
 *
 * Since this cursor allows mutating keys, you must ensure that the `BTreeMap`
 * invariants are maintained. Specifically:
 *
 * * The key of the newly inserted element must be unique in the tree.
 * * All keys in the tree must remain in sorted order.
 */
public open class CursorMutKey<K, V, A> internal constructor(
    currentCtorArg: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
    rootCtorArg: DormantMutRef<io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root<K, V>?>,
    lengthCtorArg: RustMutableReference<Int>,
    public var alloc: A,
) {
    // If current is None then it means the tree has not been allocated yet.
    public var current: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>? = currentCtorArg
        internal set

    public var root: DormantMutRef<io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root<K, V>?> = rootCtorArg
        internal set

    public var length: RustMutableReference<Int> = lengthCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("CursorMutKey"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    /**
     * Advances the cursor to the next gap, returning the key and value of the
     * element that it moved over.
     *
     * If the cursor is already at the end of the map then `None` is returned
     * and the cursor is not moved.
     */
    public open fun next(): Pair<K, V>? {
        val current = (this.current.also { this.current = null } ?: return null)
        return run {
            val matchVal = current.nextKv()
            when (matchVal) { is Result.Ok<*, *> -> {
                val kv = matchVal.okValue()
                // SAFETY: The key/value pointers remain valid even after the
                // cursor is moved forward. The scopes then prevent any
                // further access to the cursor.
                val (k, v) = kv.reborrowMut().intoKvMut()
                val (k1, v1) = Pair(mutPtrFromRustCast(k), mutPtrFromRustCast(v))
                this.current = kv.nextLeafEdge()
                // ignore-tidy-undocumented-unchecked
                Pair(RustMutableReference(MutPtr((k1).rawAddress), UnresolvedTypeLayout<Any?>()), RustMutableReference(MutPtr((v1).rawAddress), UnresolvedTypeLayout<Any?>()))
            }; is Result.Err<*, *> -> {
                val root = matchVal.errValue()
                this.current = root.lastLeafEdge()
                null
            } }
        }
    }

    /**
     * Advances the cursor to the previous gap, returning the key and value of
     * the element that it moved over.
     *
     * If the cursor is already at the start of the map then `None` is returned
     * and the cursor is not moved.
     */
    public open fun prev(): Pair<K, V>? {
        val current = (this.current.also { this.current = null } ?: return null)
        return run {
            val matchVal = current.nextBackKv()
            when (matchVal) { is Result.Ok<*, *> -> {
                val kv = matchVal.okValue()
                // SAFETY: The key/value pointers remain valid even after the
                // cursor is moved forward. The scopes then prevent any
                // further access to the cursor.
                val (k, v) = kv.reborrowMut().intoKvMut()
                val (k1, v1) = Pair(mutPtrFromRustCast(k), mutPtrFromRustCast(v))
                this.current = kv.nextBackLeafEdge()
                // ignore-tidy-undocumented-unchecked
                Pair(RustMutableReference(MutPtr((k1).rawAddress), UnresolvedTypeLayout<Any?>()), RustMutableReference(MutPtr((v1).rawAddress), UnresolvedTypeLayout<Any?>()))
            }; is Result.Err<*, *> -> {
                val root = matchVal.errValue()
                this.current = root.firstLeafEdge()
                null
            } }
        }
    }

    /**
     * Returns a reference to the key and value of the next element without
     * moving the cursor.
     *
     * If the cursor is at the end of the map then `None` is returned.
     */
    public open fun peekNext(): Pair<K, V>? {
        val current = (this.current ?: return null)
        // SAFETY: We're not using this to mutate the tree.
        val kv = (current.reborrowMut().nextKv().ok() ?: return null).intoKvMut()
        return kv
    }

    /**
     * Returns a reference to the key and value of the previous element
     * without moving the cursor.
     *
     * If the cursor is at the start of the map then `None` is returned.
     */
    public open fun peekPrev(): Pair<K, V>? {
        val current = (this.current ?: return null)
        // SAFETY: We're not using this to mutate the tree.
        val kv = (current.reborrowMut().nextBackKv().ok() ?: return null).intoKvMut()
        return kv
    }

    /**
     * Returns a read-only cursor pointing to the same location as the
     * `CursorMutKey`.
     *
     * The scope of the returned `Cursor` is bound to that of the
     * `CursorMutKey`, which means it cannot outlive the `CursorMutKey` and that the
     * `CursorMutKey` is frozen for the scope of the `Cursor`.
     */
    public open fun asCursor(): Cursor<K, V> = Cursor(root = this.root.reborrowShared().asRef(), current = this.current?.let({ current -> current.reborrow() }))

    // Now the tree editing operations

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap before the
     * newly inserted element.
     *
     * # Safety
     *
     * You must ensure that the `BTreeMap` invariants are maintained.
     * Specifically:
     *
     * * The key of the newly inserted element must be unique in the tree.
     * * All keys in the tree must remain in sorted order.
     */
    public open fun insertAfterUnchecked(key: K, value: V) {
        val edge =
            when (val matchVal = this.current.also { this.current = null }) {
                null -> {
                    // Tree is empty, allocate a new root.
                    // SAFETY: We have no other reference to the tree.
                    val root = this.root.reborrow()
                    check(root == null)
                    val node = NodeRef.newLeaf(this.alloc.clone())
                    // SAFETY: We don't touch the root while the handle is alive.
                    val handle = node.borrowMut().pushWithHandle(key, value)
                    root = node.forgetType()
                    (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
                    this.current = handle.leftEdge()
                    return
                }
                else -> {
                    val current = matchVal
                    current
                }
            }
        val handle1 =
            edge.insertRecursing(key, value, this.alloc.clone(), { ins ->
                (ins.left).let { }
                // SAFETY: The handle to the newly inserted value is always on a
                // leaf node, so adding a new root node doesn't invalidate it.
                val root1 = this.root.reborrow().asMut().unwrap()
                root1.pushInternalLevel(this.alloc.clone()).push(ins.kv.first, ins.kv.second, ins.right)
            })
        this.current = handle1.leftEdge()
        (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
    }

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap after the
     * newly inserted element.
     *
     * # Safety
     *
     * You must ensure that the `BTreeMap` invariants are maintained.
     * Specifically:
     *
     * * The key of the newly inserted element must be unique in the tree.
     * * All keys in the tree must remain in sorted order.
     */
    public open fun insertBeforeUnchecked(key: K, value: V) {
        val edge =
            when (val matchVal = this.current.also { this.current = null }) {
                null -> {
                    // SAFETY: We have no other reference to the tree.
                    when (val matchVal1 = this.root.reborrow()) {
                        null -> {
                            val root = matchVal1
                            // Tree is empty, allocate a new root.
                            var node = NodeRef.newLeaf(this.alloc.clone())
                            // SAFETY: We don't touch the root while the handle is alive.
                            val handle = node.borrowMut().pushWithHandle(key, value)
                            root = node.forgetType()
                            (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
                            this.current = handle.rightEdge()
                            return
                        }
                        else -> {
                            val root1 = matchVal1
                            root1.borrowMut().lastLeafEdge()
                        }
                    }
                }
                else -> {
                    val current = matchVal
                    current
                }
            }
        val handle1 =
            edge.insertRecursing(key, value, this.alloc.clone(), { ins ->
                (ins.left).let { }
                // SAFETY: The handle to the newly inserted value is always on a
                // leaf node, so adding a new root node doesn't invalidate it.
                val root2 = this.root.reborrow().asMut().unwrap()
                root2.pushInternalLevel(this.alloc.clone()).push(ins.kv.first, ins.kv.second, ins.right)
            })
        this.current = handle1.rightEdge()
        (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
    }

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap before the
     * newly inserted element.
     *
     * If the inserted key is not greater than the key before the cursor
     * (if any), or if it not less than the key after the cursor (if any),
     * then an [UnorderedKeyError] is returned since this would
     * invalidate the [Ord] invariant between the keys of the map.
     */
    public open fun insertAfter(key: K, value: V): Result<Unit, UnorderedKeyError> {
        when (val matchVal = this.peekPrev()) {
            null -> {}
            else -> {
                val prev = matchVal.first
                if ((kmCompareValues(key, prev) <= 0)) {
                    return Result.Err<Unit, UnorderedKeyError>(UnorderedKeyError())
                }
            }
        }
        when (val matchVal1 = this.peekNext()) {
            null -> {}
            else -> {
                val next = matchVal1.first
                if ((kmCompareValues(key, next) >= 0)) {
                    return Result.Err<Unit, UnorderedKeyError>(UnorderedKeyError())
                }
            }
        }
        // SAFETY: Ensured by checks above.
        run {
            this.insertAfterUnchecked(key, value)
        }
        return Result.Ok<Unit, UnorderedKeyError>(Unit)
    }

    /**
     * Inserts a new key-value pair into the map in the gap that the
     * cursor is currently pointing to.
     *
     * After the insertion the cursor will be pointing at the gap after the
     * newly inserted element.
     *
     * If the inserted key is not greater than the key before the cursor
     * (if any), or if it not less than the key after the cursor (if any),
     * then an [UnorderedKeyError] is returned since this would
     * invalidate the [Ord] invariant between the keys of the map.
     */
    public open fun insertBefore(key: K, value: V): Result<Unit, UnorderedKeyError> {
        when (val matchVal = this.peekPrev()) {
            null -> {}
            else -> {
                val prev = matchVal.first
                if ((kmCompareValues(key, prev) <= 0)) {
                    return Result.Err<Unit, UnorderedKeyError>(UnorderedKeyError())
                }
            }
        }
        when (val matchVal1 = this.peekNext()) {
            null -> {}
            else -> {
                val next = matchVal1.first
                if ((kmCompareValues(key, next) >= 0)) {
                    return Result.Err<Unit, UnorderedKeyError>(UnorderedKeyError())
                }
            }
        }
        // SAFETY: Ensured by checks above.
        run {
            this.insertBeforeUnchecked(key, value)
        }
        return Result.Ok<Unit, UnorderedKeyError>(Unit)
    }

    /**
     * Removes the next element from the `BTreeMap`.
     *
     * The element that was removed is returned. The cursor position is
     * unchanged (before the removed element).
     */
    public open fun removeNext(): Pair<K, V>? {
        val current = (this.current.also { this.current = null } ?: return null)
        if (current.reborrow().nextKv().isErr()) {
            this.current = current
            return null
        }
        var emptiedInternalRoot = false
        val (kv, pos) = (current.nextKv().ok() ?: return null).removeKvTracking({ emptiedInternalRoot = true }, this.alloc.clone())
        this.current = pos
        (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue -= (1); targetPlace.write(currentValue) }
        if (emptiedInternalRoot) {
            // SAFETY: This is safe since current does not point within the now
            // empty root node.
            val root = this.root.reborrow().asMut().unwrap()
            root.popInternalLevel(this.alloc.clone())
        }
        return kv
    }

    /**
     * Removes the preceding element from the `BTreeMap`.
     *
     * The element that was removed is returned. The cursor position is
     * unchanged (after the removed element).
     */
    public open fun removePrev(): Pair<K, V>? {
        val current = (this.current.also { this.current = null } ?: return null)
        if (current.reborrow().nextBackKv().isErr()) {
            this.current = current
            return null
        }
        var emptiedInternalRoot = false
        val (kv, pos) = (current.nextBackKv().ok() ?: return null).removeKvTracking({ emptiedInternalRoot = true }, this.alloc.clone())
        this.current = pos
        (this.length).let { targetPlace -> var currentValue = targetPlace.read(); currentValue -= (1); targetPlace.write(currentValue) }
        if (emptiedInternalRoot) {
            // SAFETY: This is safe since current does not point within the now
            // empty root node.
            val root = this.root.reborrow().asMut().unwrap()
            root.popInternalLevel(this.alloc.clone())
        }
        return kv
    }

    public companion object {
        public operator fun <K, V, A> invoke(
            current: Handle<NodeRef<Mut, K, V, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf>, io.github.kotlinmania.rustlang.alloc.collections.btree.node.Edge>?,
            root: DormantMutRef<io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root<K, V>?>,
            length: RustMutableReference<Int>,
            alloc: A,
        ): CursorMutKey<K, V, A> = CursorMutKey<K, V, A>(
            currentCtorArg = current,
            rootCtorArg = root,
            lengthCtorArg = length,
            alloc = alloc,
        )
    }
}

/**
 * Error type returned by [CursorMut.insertBefore] and
 * [CursorMut.insertAfter] if the key being inserted is not properly
 * ordered with regards to adjacent keys.
 */
public open class UnorderedKeyError {
    public open fun clone(): UnorderedKeyError = UnorderedKeyError()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("key is not properly ordered relative to neighbors"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<UnorderedKeyError> =
            object : TypeLayout<UnorderedKeyError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): UnorderedKeyError =
                    UnorderedKeyError()
                override fun writeNative(addr: Long, value: UnorderedKeyError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnorderedKeyError =
                    UnorderedKeyError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnorderedKeyError) {
                }
            }
    }
}
