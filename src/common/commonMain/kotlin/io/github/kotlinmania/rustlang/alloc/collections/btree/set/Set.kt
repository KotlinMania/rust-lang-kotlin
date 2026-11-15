// port-lint: source collections/btree/set.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.set

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.BTreeMap
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.ExtractIfInner
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.Keys
import io.github.kotlinmania.rustlang.alloc.collections.btree.map.UnorderedKeyError
import io.github.kotlinmania.rustlang.alloc.collections.btree.mergeiter.MergeIterInner
import io.github.kotlinmania.rustlang.alloc.collections.btree.set.entry.Entry
import io.github.kotlinmania.rustlang.alloc.collections.btree.set.entry.OccupiedEntry
import io.github.kotlinmania.rustlang.alloc.collections.btree.set.entry.VacantEntry
import io.github.kotlinmania.rustlang.alloc.collections.btree.setval.SetValZST
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.iter.adapters.peekable.Peekable
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.ops.arith.Sub
import io.github.kotlinmania.rustlang.core.ops.bit.BitAnd
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.BitXor
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.and
import io.github.kotlinmania.rustlang.core.result.or

public class DebugStruct(
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
 * An ordered set based on a B-Tree.
 *
 * See [BTreeMap]s documentation for a detailed discussion of this collection's performance
 * benefits and drawbacks.
 *
 * It is a logic error for an item to be modified in such a way that the item's ordering relative
 * to any other item, as determined by the [Ord] trait, changes while it is in the set. This is
 * normally only possible through [Cell], [RefCell], global state, I/O, or unchecked code.
 * The behavior resulting from such a logic error is not specified, but will be encapsulated to the
 * `BTreeSet` that observed the logic error and not result in undefined behavior. This could
 * include panics, incorrect results, aborts, memory leaks, and non-termination.
 *
 * Iterators returned by [BTreeSet.iter] and [BTreeSet.intoIter] produce their items in order, and take worst-case
 * logarithmic and amortized constant time per item returned.
 *
 * # Examples
 *
 * ```kotlin
 * // Type inference lets us omit an explicit type signature (which
 * // would be `BTreeSet<String>` in this example).
 * var books = mutableSetOf()
 * // Add some books.
 * books.add("A Dance With Dragons")
 * books.add("To Kill a Mockingbird")
 * books.add("The Odyssey")
 * books.add("The Great Gatsby")
 * // Check for a specific one.
 * if (!books.contains("The Winds of Winter")) {
 *     println("We have ${books.size} books, but The Winds of Winter ain't one.")
 * }
 * // Remove a book.
 * books.remove("The Odyssey")
 * // Iterate over everything.
 * for (book in books) {
 *     println("$book")
 * }
 * ```
 *
 * A `BTreeSet` with a known list of items can be initialized from an array:
 *
 * ```kotlin
 * val set = mutableSetOf(1, 2, 3)
 * ```
 */
public open class BTreeSet<T, A : AllocatorClone>(
    public var map: MutableMap<T, SetValZST>,
) : Sub<BTreeSet<T, A>, BTreeSet<T, A>>,
    BitXor<BTreeSet<T, A>, BTreeSet<T, A>>,
    BitAnd<BTreeSet<T, A>, BTreeSet<T, A>>,
    BitOr<BTreeSet<T, A>, BTreeSet<T, A>>,
    Iterable<T> {
    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.map).hashCode()
        return state
    }

    public open fun eq(other: BTreeSet<T, A>): Boolean = this.map.eq(other.map)

    public open fun partialCmp(other: BTreeSet<T, A>): Ordering? = this.map.partialCmp(other.map)

    public open fun cmp(other: BTreeSet<T, A>): Ordering = this.map.cmp(other.map)

    public open fun clone(): BTreeSet<T, A> = BTreeSet(map = this.map.toMutableMap())

    public open fun cloneFrom(source: BTreeSet<T, A>) {
        this.map = source.map
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.map.allocator()

    /**
     * Constructs a double-ended iterator over a sub-range of elements in the set.
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
     * var set = mutableSetOf()
     * set.add(3)
     * set.add(5)
     * set.add(8)
     * for (elem in set.range(Pair(Included(4), Included(8)))) {
     *     println("$elem")
     * }
     * check(5 == set.range(4 until Int.MAX_VALUE).next())
     * ```
     */
    public open fun <K, R> range(range: R): Range<T> where R : RangeBounds<K> = Range(iter = this.map.range(range))

    /**
     * Visits the elements representing the difference,
     * i.e., the elements that are in `self` but not in `other`,
     * in ascending order.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableSetOf()
     * a.add(1)
     * a.add(2)
     * var b = mutableSetOf()
     * b.add(2)
     * b.add(3)
     * val diff: Vec = a.difference(b).cloned().collect()
     * check(diff == listOf(1))
     * ```
     */
    public open fun difference(other: BTreeSet<T, A>): Difference<T, A> =
        if (this.first() != null && this.last() != null && other.first() != null && other.last() != null) {
            Difference(
                inner =
                    run {
                        val matchVal = Pair(selfMin.cmp(otherMax), selfMax.cmp(otherMin))
                        when {
                            matchVal.first == Greater -> DifferenceInner.Iterate(this.iter())
                            matchVal.second == Less -> DifferenceInner.Iterate(this.iter())
                            matchVal.first == Equal -> {
                                val selfIter = this.iter()
                                selfIter.next()
                                DifferenceInner.Iterate(selfIter)
                            }
                            matchVal.second == Equal -> {
                                val selfIter1 = this.iter()
                                selfIter1.nextBack()
                                DifferenceInner.Iterate(selfIter1)
                            }
                            this.len() <= other.len() / ITER_PERFORMANCE_TIPPING_SIZE_DIFF -> {
                                DifferenceInner.Search(selfIter = this.iter(), otherSet = other)
                            }
                            else -> DifferenceInner.Stitch(selfIter = this.iter(), otherIter = other.iter().peekable())
                        }
                    },
            )
        } else {
            Difference(inner = DifferenceInner.Iterate(this.iter()))
        }

    /**
     * Visits the elements representing the symmetric difference,
     * i.e., the elements that are in `self` or in `other` but not in both,
     * in ascending order.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableSetOf()
     * a.add(1)
     * a.add(2)
     * var b = mutableSetOf()
     * b.add(2)
     * b.add(3)
     * val symDiff: Vec = a.symmetricDifference(b).cloned().collect()
     * check(symDiff == listOf(1, 3))
     * ```
     */
    public open fun symmetricDifference(other: BTreeSet<T, A>): SymmetricDifference<T> = SymmetricDifference(MergeIterInner.new(this.iter(), other.iter()))

    /**
     * Visits the elements representing the intersection,
     * i.e., the elements that are both in `self` and `other`,
     * in ascending order.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableSetOf()
     * a.add(1)
     * a.add(2)
     * var b = mutableSetOf()
     * b.add(2)
     * b.add(3)
     * val intersection: Vec = a.intersection(b).cloned().collect()
     * check(intersection == listOf(2))
     * ```
     */
    public open fun intersection(other: BTreeSet<T, A>): Intersection<T, A> =
        if (this.first() != null && this.last() != null && other.first() != null && other.last() != null) {
            Intersection(
                inner =
                    run {
                        val matchVal = Pair(selfMin.cmp(otherMax), selfMax.cmp(otherMin))
                        when {
                            matchVal.first == Greater -> IntersectionInner.Answer(null)
                            matchVal.second == Less -> IntersectionInner.Answer(null)
                            matchVal.first == Equal -> IntersectionInner.Answer(selfMin)
                            matchVal.second == Equal -> IntersectionInner.Answer(selfMax)
                            this.len() <= other.len() / ITER_PERFORMANCE_TIPPING_SIZE_DIFF -> {
                                IntersectionInner.Search(smallIter = this.iter(), largeSet = other)
                            }
                            other.len() <= this.len() / ITER_PERFORMANCE_TIPPING_SIZE_DIFF -> {
                                IntersectionInner.Search(smallIter = other.iter(), largeSet = this)
                            }
                            else -> IntersectionInner.Stitch(a = this.iter(), b = other.iter())
                        }
                    },
            )
        } else {
            Intersection(inner = IntersectionInner.Answer(null))
        }

    /**
     * Visits the elements representing the union,
     * i.e., all the elements in `self` or `other`, without duplicates,
     * in ascending order.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableSetOf()
     * a.add(1)
     * var b = mutableSetOf()
     * b.add(2)
     * val union: Vec = a.union(b).cloned().collect()
     * check(union == listOf(1, 2))
     * ```
     */
    public open fun union(other: BTreeSet<T, A>): Union<T> = Union(MergeIterInner.new(this.iter(), other.iter()))

    /**
     * Clears the set, removing all elements.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableSetOf()
     * v.add(1)
     * v.clear()
     * check(v.isEmpty())
     * ```
     */
    public open fun clear() {
        this.map.clear()
    }

    /**
     * Returns `true` if the set contains an element equal to the value.
     *
     * The value may be any borrowed form of the set's element type,
     * but the ordering on the borrowed form *must* match the
     * ordering on the element type.
     *
     * # Examples
     *
     * ```kotlin
     * val set = mutableSetOf(1, 2, 3)
     * check(set.contains(1) == true)
     * check(set.contains(4) == false)
     * ```
     */
    public open fun <Q> contains(value: Q): Boolean = this.map.containsKey(value)

    /**
     * Returns a reference to the element in the set, if any, that is equal to
     * the value.
     *
     * The value may be any borrowed form of the set's element type,
     * but the ordering on the borrowed form *must* match the
     * ordering on the element type.
     *
     * # Examples
     *
     * ```kotlin
     * val set = mutableSetOf(1, 2, 3)
     * check(set.get(2) == 2)
     * check(set.get(4) == null)
     * ```
     */
    public open fun <Q> get(value: Q): T? = this.map.getKeyValue(value)?.let({ (k, _) -> k })

    /**
     * Returns `true` if `self` has no elements in common with `other`.
     * This is equivalent to checking for an empty intersection.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * var b = mutableSetOf()
     * check(a.isDisjoint(b) == true)
     * b.add(4)
     * check(a.isDisjoint(b) == true)
     * b.add(1)
     * check(a.isDisjoint(b) == false)
     * ```
     */
    public open fun isDisjoint(other: BTreeSet<T, A>): Boolean = this.intersection(other).next() == null

    /**
     * Returns `true` if the set is a subset of another,
     * i.e., `other` contains at least all the elements in `self`.
     *
     * # Examples
     *
     * ```kotlin
     * val sup = mutableSetOf(1, 2, 3)
     * var set = mutableSetOf()
     * check(set.isSubset(sup) == true)
     * set.add(2)
     * check(set.isSubset(sup) == true)
     * set.add(4)
     * check(set.isSubset(sup) == false)
     * ```
     */
    public open fun isSubset(other: BTreeSet<T, A>): Boolean {
        // Same result as self.difference(other).next().isNone()
        // but the code below is faster (hugely in some cases).
        if (this.len() > other.len()) {
            return false
            // self has more elements than other
        }
        val (selfMin, selfMax) = Pair(this.first(), this.last()) ?: return true
        val (otherMin, otherMax) = Pair(other.first(), other.last()) ?: return false
        val selfIter = this.iter()
        when (selfMin.cmp(otherMin)) {
            Less -> {
                return false
            }
            // other does not contain selfMin
            Equal -> {
                selfIter.next()
                // selfMin is contained in other, so remove it from consideration
                // otherMin is now not in selfIter (used below)
            }
            Greater -> {}
        }
        when (selfMax.cmp(otherMax)) {
            Greater -> {
                return false
            }
            // other does not contain selfMax
            Equal -> {
                selfIter.nextBack()
                // selfMax is contained in other, so remove it from consideration
                // otherMax is now not in selfIter (used below)
            }
            Less -> {}
        }
        return if (selfIter.len() <= other.len() / ITER_PERFORMANCE_TIPPING_SIZE_DIFF) {
            selfIter.all({ e -> other.contains(e) })
        } else {
            val otherIter = other.iter()
            run {
                // remove otherMin and otherMax as they are not in selfIter (see above)
                otherIter.next()
                otherIter.nextBack()
            }
            // custom `selfIter.all(| e | other.contains(e))`
            selfIter.all(
                closure@{ self1 ->
                    while (true) {
                        val matchVal = otherIter.next()
                        if (matchVal == null) break
                        val other1 = matchVal
                        when (other1.cmp(self1)) {
                            // happens up to `ITER_PERFORMANCE_TIPPING_SIZE_DIFF * self.len() - 1` times
                            Less -> {
                                continue
                            }
                            // skip over elements that are smaller
                            // happens `self.len()` times
                            Equal -> {
                                return@closure true
                            }
                            // self1 is in other
                            // happens only once
                            Greater -> {
                                return@closure false
                            }
                        }
                    }
                    false
                },
            )
        }
    }

    /**
     * Returns `true` if the set is a superset of another,
     * i.e., `self` contains at least all the elements in `other`.
     *
     * # Examples
     *
     * ```kotlin
     * val sub = mutableSetOf(1, 2)
     * var set = mutableSetOf()
     * check(set.isSuperset(sub) == false)
     * set.add(0)
     * set.add(1)
     * check(set.isSuperset(sub) == false)
     * set.add(2)
     * check(set.isSuperset(sub) == true)
     * ```
     */
    public open fun isSuperset(other: BTreeSet<T, A>): Boolean = other.isSubset(this)

    /**
     * Returns a reference to the first element in the set, if any.
     * This element is always the minimum of all elements in the set.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var set = mutableSetOf()
     * check(set.first() == null)
     * set.add(1)
     * check(set.first() == 1)
     * set.add(2)
     * check(set.first() == 1)
     * ```
     */
    public open fun first(): T? = this.map.firstKeyValue().map({ (k, _) -> k })

    /**
     * Returns a reference to the last element in the set, if any.
     * This element is always the maximum of all elements in the set.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var set = mutableSetOf()
     * check(set.last() == null)
     * set.add(1)
     * check(set.last() == 1)
     * set.add(2)
     * check(set.last() == 2)
     * ```
     */
    public open fun last(): T? = this.map.lastKeyValue().map({ (k, _) -> k })

    /**
     * Removes the first element from the set and returns it, if any.
     * The first element is always the minimum element in the set.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.add(1)
     * while (true) {
     *     val matchVal = set.popFirst()
     *     if (matchVal == null) break
     *     val n = matchVal
     *     check(n == 1)
     * }
     * check(set.isEmpty())
     * ```
     */
    public open fun popFirst(): T? = this.map.popFirst().map({ kv -> kv.first })

    /**
     * Removes the last element from the set and returns it, if any.
     * The last element is always the maximum element in the set.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.add(1)
     * while (true) {
     *     val matchVal = set.popLast()
     *     if (matchVal == null) break
     *     val n = matchVal
     *     check(n == 1)
     * }
     * check(set.isEmpty())
     * ```
     */
    public open fun popLast(): T? = this.map.popLast().map({ kv -> kv.first })

    /**
     * Adds a value to the set.
     *
     * Returns whether the value was newly inserted. That is:
     *
     * - If the set did not previously contain an equal value, `true` is
     *   returned.
     * - If the set already contained an equal value, `false` is returned, and
     *   the entry is not updated.
     *
     * See the [module-level documentation] for more.
     *
     * [module-level documentation]: index.html#insert-and-complex-keys
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * check(set.add(2) == true)
     * check(set.add(2) == false)
     * check(set.size == 1)
     * ```
     */
    public open fun insert(value: T): Boolean = this.map.put(value, SetValZST) == null

    /**
     * Adds a value to the set, replacing the existing element, if any, that is
     * equal to the value. Returns the replaced element.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.add(mutableListOf())
     * check(set.get(listOf())!!.capacity() == 0)
     * set.replace(ArrayList(10))
     * check(set.get(listOf())!!.capacity() == 10)
     * ```
     */
    public open fun replace(value: T): T? = this.map.replace(value)

    /**
     * Inserts the given `value` into the set if it is not present, then
     * returns a reference to the value in the set.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf(1, 2, 3)
     *     check(set.size == 3)
     *     check(set.getOrInsert(2) == 2)
     *     check(set.getOrInsert(100) == 100)
     *     check(set.size == 4)
     *     // 100 was inserted
     * }
     * ```
     */
    public open fun getOrInsert(value: T): T =
        this.map
            .entry(value)
            .insertEntry(SetValZST)
            .intoKey()

    /**
     * Inserts a value computed from `f` into the set if the given `value` is
     * not present, then returns a reference to the value in the set.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var set: MutableSet<String> = listOf("cat", "dog", "horse").iter().map({ pet -> pet.toOwned() }).toMutableSet()
     *     check(set.size == 3)
     *     for (pet in listOf("cat", "dog", "fish")) {
     *         val value = set.getOrInsertWith(pet, { it.toOwned() })
     *         check(value == pet)
     *     }
     *     check(set.size == 4)
     *     // a new "fish" was inserted
     * }
     * ```
     */
    public open fun <Q> getOrInsertWith(value: Q, f: (Q) -> T): T = this.map.getOrInsertWith(value, f)

    /**
     * Gets the given value's corresponding entry in the set for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.btreeset.Entry.*
     *
     * @feature(btreeSetEntry)
     * public fun doctestEntry() {
     *     var singles = mutableSetOf()
     *     var dupes = mutableSetOf()
     *     for (ch in "a short treatise on fungi".chars()) {
     *         when (val matchVal = dupes.entry(ch)) {
     *             is Vacant -> {
     *                 val (dupeEntry) = matchVal
     *                 // We haven't already seen a duplicate, so
     *                 // check if we've at least seen it once.
     *                 when (val matchVal1 = singles.entry(ch)) {
     *                     is Vacant -> {
     *                         val (singleEntry) = matchVal1
     *                         // We found a new character for the first time.
     *                         singleEntry.insert()
     *                     }
     *                     is Occupied -> {
     *                         val (singleEntry1) = matchVal1
     *                         // We've already seen this once, "move" it to dupes.
     *                         singleEntry1.remove()
     *                         dupeEntry.insert()
     *                     }
     *                 }
     *             }
     *             else -> {}
     *         }
     *     }
     *     check(!singles.contains('t') && dupes.contains('t'))
     *     check(singles.contains('u') && !dupes.contains('u'))
     *     check(!singles.contains('v') && !dupes.contains('v'))
     * }
     * ```
     */
    public open fun entry(value: T): Entry<T, A> =
        when (val matchVal = this.map.entry(value)) {
            is io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.Entry.Occupied -> {
                val (entry) = matchVal
                Entry.Occupied(OccupiedEntry(inner = entry))
            }
            is io.github.kotlinmania.rustlang.alloc.collections.btree.map.entry.Entry.Vacant -> {
                val (entry1) = matchVal
                Entry.Vacant(VacantEntry(inner = entry1))
            }
        }

    /**
     * If the set contains an element equal to the value, removes it from the
     * set and drops it. Returns whether such an element was present.
     *
     * The value may be any borrowed form of the set's element type,
     * but the ordering on the borrowed form *must* match the
     * ordering on the element type.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.add(2)
     * check(set.remove(2) == true)
     * check(set.remove(2) == false)
     * ```
     */
    public open fun <Q> remove(value: Q): Boolean = this.map.remove(value) != null

    /**
     * Removes and returns the element in the set, if any, that is equal to
     * the value.
     *
     * The value may be any borrowed form of the set's element type,
     * but the ordering on the borrowed form *must* match the
     * ordering on the element type.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf(1, 2, 3)
     * check(set.take(2) == 2)
     * check(set.take(2) == null)
     * ```
     */
    public open fun <Q> take(value: Q): T? = this.map.removeEntry(value).map({ (k, _) -> k })

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns `false`.
     * The elements are visited in ascending order.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf(1, 2, 3, 4, 5, 6)
     * // Keep only the even numbers.
     * set.retain({ k -> k % 2 == 0 })
     * check(set.iter().eq(listOf(2, 4, 6).iter()))
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        this.extractIf(0 until 0, { v -> !f(v) }).forEach(drop)
    }

    /**
     * Moves all elements from `other` into `self`, leaving `other` empty.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableSetOf()
     * a.add(1)
     * a.add(2)
     * a.add(3)
     * var b = mutableSetOf()
     * b.add(3)
     * b.add(4)
     * b.add(5)
     * a.append(b)
     * check(a.size == 5)
     * check(b.size == 0)
     * check(a.contains(1))
     * check(a.contains(2))
     * check(a.contains(3))
     * check(a.contains(4))
     * check(a.contains(5))
     * ```
     */
    public open fun append(other: BTreeSet<T, A>) {
        this.map.append(other.map)
    }

    /**
     * Splits the collection into two at the value. Returns a new collection
     * with all elements greater than or equal to the value.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var a = mutableSetOf()
     * a.add(1)
     * a.add(2)
     * a.add(3)
     * a.add(17)
     * a.add(41)
     * val b = a.splitOff(3)
     * check(a.size == 2)
     * check(b.len() == 3)
     * check(a.contains(1))
     * check(a.contains(2))
     * check(b.contains(3))
     * check(b.contains(17))
     * check(b.contains(41))
     * ```
     */
    public open fun <Q> splitOff(value: Q): BTreeSet<T, A> = BTreeSet(map = this.map.splitOff(value))

    /**
     * Creates an iterator that visits elements in the specified range in ascending order and
     * uses a closure to determine if an element should be removed.
     *
     * If the closure returns `true`, the element is removed from the set and
     * yielded. If the closure returns `false`, or panics, the element remains
     * in the set and will not be yielded.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use `extractIf().forEach(drop)` if you do not need the returned iterator,
     * or [retain] with a negated predicate if you also do not need to restrict the range.
     *
     * [retain]: BTreeSet.retain
     * # Examples
     *
     * ```kotlin
     * // Splitting a set into even and odd values, reusing the original set:
     * var set: MutableSet<Int> = (0 until 8).toMutableSet()
     * val evens: BTreeSet = set.extractIf(0 until 0, { v -> v % 2 == 0 }).collect()
     * val odds = set
     * check(evens.intoIter().collect<Vec>() == mutableListOf(0, 2, 4, 6))
     * check(ArrayDeque((odds).toList()).collect<Vec>() == mutableListOf(1, 3, 5, 7))
     * // Splitting a set into low and high halves, reusing the original set:
     * var set1: MutableSet<Int> = (0 until 8).toMutableSet()
     * val low: BTreeSet = set1.extractIf(0 until 4, { v -> true }).collect()
     * val high = set1
     * check(low.intoIter().collect<Vec>() == listOf(0, 1, 2, 3))
     * check(ArrayDeque((high).toList()).collect<Vec>() == listOf(4, 5, 6, 7))
     * ```
     */
    public open fun <R> extractIf(range: R, pred: (T) -> Boolean): ExtractIf<T, R, (T) -> Boolean, A> where R : RangeBounds<T> {
        val (inner, alloc) = this.map.extractIfInner(range)
        return ExtractIf(pred = pred, inner = inner, alloc = alloc)
    }

    /**
     * Gets an iterator that visits the elements in the `BTreeSet` in ascending
     * order.
     *
     * # Examples
     *
     * ```kotlin
     * val set = mutableSetOf(3, 1, 2)
     * var setIter = set.iter()
     * check(setIter.next() == 1)
     * check(setIter.next() == 2)
     * check(setIter.next() == 3)
     * check(setIter.next() == null)
     * ```
     */
    public open fun iter(): Iter<T> = Iter(iter = this.map.keys())

    /**
     * Returns the number of elements in the set.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableSetOf()
     * check(v.size == 0)
     * v.add(1)
     * check(v.size == 1)
     * ```
     */
    public open fun len(): Int = this.map.size

    /**
     * Returns `true` if the set contains no elements.
     *
     * # Examples
     *
     * ```kotlin
     * var v = mutableSetOf()
     * check(v.isEmpty())
     * v.add(1)
     * check(!v.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Returns a [Cursor] pointing at the gap before the smallest element
     * greater than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap before the smallest element greater than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap before the smallest element greater than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap before the smallest element in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     val set = mutableSetOf(1, 2, 3, 4)
     *     val cursor = set.lowerBound(Bound.Included(2))
     *     check(cursor.peekPrev() == 1)
     *     check(cursor.peekNext() == 2)
     *     val cursor1 = set.lowerBound(Bound.Excluded(2))
     *     check(cursor1.peekPrev() == 2)
     *     check(cursor1.peekNext() == 3)
     *     val cursor2 = set.lowerBound(Bound.Unbounded)
     *     check(cursor2.peekPrev() == null)
     *     check(cursor2.peekNext() == 1)
     * }
     * ```
     */
    public open fun <Q> lowerBound(bound: Bound<Q>): Cursor<T> = Cursor(inner = this.map.lowerBound(bound))

    /**
     * Returns a [CursorMut] pointing at the gap before the smallest element
     * greater than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap before the smallest element greater than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap before the smallest element greater than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap before the smallest element in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     var set = mutableSetOf(1, 2, 3, 4)
     *     var cursor = set.lowerBoundMut(Bound.Included(2))
     *     check(cursor.peekPrev() == 1)
     *     check(cursor.peekNext() == 2)
     *     var cursor1 = set.lowerBoundMut(Bound.Excluded(2))
     *     check(cursor1.peekPrev() == 2)
     *     check(cursor1.peekNext() == 3)
     *     var cursor2 = set.lowerBoundMut(Bound.Unbounded)
     *     check(cursor2.peekPrev() == null)
     *     check(cursor2.peekNext() == 1)
     * }
     * ```
     */
    public open fun <Q> lowerBoundMut(bound: Bound<Q>): CursorMut<T, A> = CursorMut(inner = this.map.lowerBoundMut(bound))

    /**
     * Returns a [Cursor] pointing at the gap after the greatest element
     * smaller than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap after the greatest element smaller than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap after the greatest element smaller than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap after the greatest element in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     val set = mutableSetOf(1, 2, 3, 4)
     *     val cursor = set.upperBound(Bound.Included(3))
     *     check(cursor.peekPrev() == 3)
     *     check(cursor.peekNext() == 4)
     *     val cursor1 = set.upperBound(Bound.Excluded(3))
     *     check(cursor1.peekPrev() == 2)
     *     check(cursor1.peekNext() == 3)
     *     val cursor2 = set.upperBound(Bound.Unbounded)
     *     check(cursor2.peekPrev() == 4)
     *     check(cursor2.peekNext() == null)
     * }
     * ```
     */
    public open fun <Q> upperBound(bound: Bound<Q>): Cursor<T> = Cursor(inner = this.map.upperBound(bound))

    /**
     * Returns a [CursorMut] pointing at the gap after the greatest element
     * smaller than the given bound.
     *
     * Passing `Bound.Included(x)` will return a cursor pointing to the
     * gap after the greatest element smaller than or equal to `x`.
     *
     * Passing `Bound.Excluded(x)` will return a cursor pointing to the
     * gap after the greatest element smaller than `x`.
     *
     * Passing `Bound.Unbounded` will return a cursor pointing to the
     * gap after the greatest element in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound
     *
     * @feature(btreeCursors)
     * public fun doctestEntry() {
     *     var set = mutableSetOf(1, 2, 3, 4)
     *     var cursor = set.upperBoundMut(Bound.Included(3))
     *     check(cursor.peekPrev() == 3)
     *     check(cursor.peekNext() == 4)
     *     var cursor1 = set.upperBoundMut(Bound.Excluded(3))
     *     check(cursor1.peekPrev() == 2)
     *     check(cursor1.peekNext() == 3)
     *     var cursor2 = set.upperBoundMut(Bound.Unbounded)
     *     check(cursor2.peekPrev() == 4)
     *     check(cursor2.peekNext() == null)
     * }
     * ```
     */
    public open fun <Q> upperBoundMut(bound: Bound<Q>): CursorMut<T, A> = CursorMut(inner = this.map.upperBoundMut(bound))

    /**
     * Gets an iterator for moving out the `BTreeSet`s contents in ascending order.
     *
     * # Examples
     *
     * ```kotlin
     * val set = mutableSetOf(1, 2, 3, 4)
     * val v: Vec = ArrayDeque((set).toList()).collect()
     * check(v == listOf(1, 2, 3, 4))
     * ```
     */
    public open fun intoIter(): IntoIter<T, A> = IntoIter(iter = this.map.intoIter())

    public open fun <Iter : Iterable<T>> extend(iter: Iter) {
        iter.iterator().forEach({ elem ->
            this.insert(elem)
        })
    }

    public open fun extendOne(elem: T) {
        this.insert(elem)
    }

    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.extend(iter.iterator().cloned())
    }

    public open fun extendOneT(elem: T) {
        this.insert(elem)
    }

    /**
     * Returns the difference of `self` and `rhs` as a new `BTreeSet<T>`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(3, 4, 5)
     * val result = a - b
     * check(result == mutableSetOf(1, 2))
     * ```
     */
    public open fun sub(rhs: BTreeSet<T, A>): BTreeSet<T, A> = BTreeSet.fromSortedIter(this.difference(rhs).cloned(), ManuallyDrop.intoInner(this.map.alloc.clone()))

    /**
     * Returns the symmetric difference of `self` and `rhs` as a new `BTreeSet<T>`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(2, 3, 4)
     * val result = a xor b
     * check(result == mutableSetOf(1, 4))
     * ```
     */
    public open fun bitxor(rhs: BTreeSet<T, A>): BTreeSet<T, A> = BTreeSet.fromSortedIter(this.symmetricDifference(rhs).cloned(), ManuallyDrop.intoInner(this.map.alloc.clone()))

    /**
     * Returns the intersection of `self` and `rhs` as a new `BTreeSet<T>`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(2, 3, 4)
     * val result = a and b
     * check(result == mutableSetOf(2, 3))
     * ```
     */
    public open fun bitand(rhs: BTreeSet<T, A>): BTreeSet<T, A> = BTreeSet.fromSortedIter(this.intersection(rhs).cloned(), ManuallyDrop.intoInner(this.map.alloc.clone()))

    /**
     * Returns the union of `self` and `rhs` as a new `BTreeSet<T>`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(3, 4, 5)
     * val result = a or b
     * check(result == mutableSetOf(1, 2, 3, 4, 5))
     * ```
     */
    public open fun bitor(rhs: BTreeSet<T, A>): BTreeSet<T, A> = BTreeSet.fromSortedIter(this.union(rhs).cloned(), ManuallyDrop.intoInner(this.map.alloc.clone()))

    public open fun fmt(f: StringBuilder) {
        object {
            val entries = mutableListOf<Any?>()
            fun entry(v: Any?) = apply { entries.add(v) }
            fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }
            fun finish() {
                f.append(entries.joinToString(", ", "{", "}"))
            }
        }.entries(this.iter()).finish()
    }

    public companion object {
        /**
         * Makes a new `BTreeSet` with a reasonable choice of B.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.Global
         *
         * val set: MutableSet<Int> = BTreeSet.newIn(Global)
         * ```
         */
        public fun <T, A : AllocatorClone> newIn(alloc: A): BTreeSet<T, A> = BTreeSet(map = BTreeMap.newIn(alloc))

        public fun <T, A : AllocatorClone, I : Iterator<T>> fromSortedIter(iter: I, alloc: A): BTreeSet<T, A> {
            val iter1 = iter.map({ k -> Pair(k, SetValZST) })
            val map = BTreeMap.bulkBuildFromSortedIter(iter1, alloc)
            return BTreeSet(map = map)
        }
    }
}

/**
 * An iterator over the items of a `BTreeSet`.
 *
 * This `class` is created by the [iter] method on [BTreeSet].
 * See its documentation for more.
 *
 * [iter]: BTreeSet.iter
 */
public open class Iter<T> internal constructor(
    iterCtorArg: Keys<T, SetValZST>,
) : TrustedLen,
    Iterable<T> {
    public var iter: Keys<T, SetValZST> = iterCtorArg
        internal set

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@Iter.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "Iter").field(this.iter).finish()
    }

    public open fun clone(): Iter<T> = Iter(iter = this.iter.clone())

    public open fun next(): T? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun last(): T? = this.nextBack()

    public open fun min(): T? = this.next()

    public open fun max(): T? = this.nextBack()

    public open fun nextBack(): T? = this.iter.nextBack()

    public open fun len(): Int = this.iter.len()

    public companion object {
        public operator fun <T> invoke(
            iter: Keys<T, SetValZST>,
        ): Iter<T> =
            Iter<T>(
                iterCtorArg = iter,
            )

        /**
         * Creates an empty `btreeSet.Iter`.
         *
         * ```kotlin
         * import btreeset.Iter
         *
         * val iter: Iter<UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <T> default(): Iter<T> = Iter(iter = Default.default())
    }
}

/**
 * An owning iterator over the items of a `BTreeSet` in ascending order.
 *
 * This `class` is created by the [intoIter] method on [BTreeSet]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: BTreeSet#method.intoIter
 */
public open class IntoIter<T, A : AllocatorClone> internal constructor(
    iterCtorArg: io.github.kotlinmania.rustlang.alloc.collections.btree.map.IntoIter<T, SetValZST, A>,
) : TrustedLen,
    Iterable<T> {
    public var iter: io.github.kotlinmania.rustlang.alloc.collections.btree.map.IntoIter<T, SetValZST, A> = iterCtorArg
        internal set

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@IntoIter.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun next(): T? = this.iter.next()?.let({ (k, _) -> k })

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun nextBack(): T? = this.iter.nextBack()?.let({ (k, _) -> k })

    public open fun len(): Int = this.iter.len()

    public companion object {
        public operator fun <T, A : AllocatorClone> invoke(
            iter: io.github.kotlinmania.rustlang.alloc.collections.btree.map.IntoIter<T, SetValZST, A>,
        ): IntoIter<T, A> =
            IntoIter<T, A>(
                iterCtorArg = iter,
            )

        /**
         * Creates an empty `btreeSet.IntoIter`.
         *
         * ```kotlin
         * import btreeset.IntoIter
         *
         * val iter: IntoIter<UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <T, A : AllocatorClone> default(defaultAFn: () -> A): IntoIter<T, A> where A : AllocatorClone = IntoIter(iter = defaultAFn())
    }
}

/**
 * An iterator over a sub-range of items in a `BTreeSet`.
 *
 * This `class` is created by the [range] method on [BTreeSet].
 * See its documentation for more.
 *
 * [range]: BTreeSet.range
 */
public open class Range<T> internal constructor(
    iterCtorArg: io.github.kotlinmania.rustlang.alloc.collections.btree.map.Range<T, SetValZST>,
) : Iterable<T> {
    public var iter: io.github.kotlinmania.rustlang.alloc.collections.btree.map.Range<T, SetValZST> = iterCtorArg
        internal set

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@Range.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun clone(): Range<T> = Range(iter = this.iter.clone())

    public open fun next(): T? = this.iter.next()?.let({ (k, _) -> k })

    public open fun last(): T? = this.nextBack()

    public open fun min(): T? = this.next()

    public open fun max(): T? = this.nextBack()

    public open fun nextBack(): T? = this.iter.nextBack()?.let({ (k, _) -> k })

    public companion object {
        public operator fun <T> invoke(
            iter: io.github.kotlinmania.rustlang.alloc.collections.btree.map.Range<T, SetValZST>,
        ): Range<T> =
            Range<T>(
                iterCtorArg = iter,
            )

        /**
         * Creates an empty `btreeSet.Range`.
         *
         * ```kotlin
         * import btreeset.Range
         *
         * val iter: Range<UByte> = Default.default()
         * check(iter.count() == 0)
         * ```
         */
        public fun <T> default(): Range<T> = Range(iter = Default.default())
    }
}

/**
 * A lazy iterator producing elements in the difference of `BTreeSet`s.
 *
 * This `class` is created by the [difference] method on [BTreeSet].
 * See its documentation for more.
 *
 * [difference]: BTreeSet.difference
 */
public open class Difference<T, A : AllocatorClone> internal constructor(
    innerCtorArg: DifferenceInner<T, A>,
) : Iterable<T> {
    public var inner: DifferenceInner<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@Difference.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "Difference").field(this.inner).finish()
    }

    public open fun clone(): Difference<T, A> =
        Difference(
            inner =
                when (val matchVal = this.inner) {
                    is DifferenceInner.Stitch -> {
                        val selfIter = matchVal.selfIter
                        val otherIter = matchVal.otherIter
                        DifferenceInner.Stitch(selfIter = selfIter.clone(), otherIter = otherIter.clone())
                    }
                    is DifferenceInner.Search -> {
                        val selfIter1 = matchVal.selfIter
                        val otherSet = matchVal.otherSet
                        DifferenceInner.Search(selfIter = selfIter1.clone(), otherSet = otherSet)
                    }
                    is DifferenceInner.Iterate -> {
                        val (iter) = matchVal
                        DifferenceInner.Iterate(iter.clone())
                    }
                },
        )

    public open fun next(): T? {
        return when (val matchVal = this.inner) {
            is DifferenceInner.Stitch -> {
                val selfIter = matchVal.selfIter
                val otherIter = matchVal.otherIter
                var selfNext = (selfIter.next() ?: return null)
                while (true) {
                    when (otherIter.peek().mapOr(Less, { otherNext -> selfNext.cmp(otherNext) })) {
                        Less -> {
                            return selfNext
                        }
                        Equal -> {
                            selfNext = (selfIter.next() ?: return null)
                            otherIter.next()
                        }
                        Greater -> {
                            otherIter.next()
                        }
                    }
                }
            }
            is DifferenceInner.Search -> {
                val selfIter1 = matchVal.selfIter
                val otherSet = matchVal.otherSet
                while (true) {
                    val selfNext1 = (selfIter1.next() ?: return null)
                    if (!otherSet.contains(selfNext1)) {
                        return selfNext1
                    }
                }
            }
            is DifferenceInner.Iterate -> {
                val (iter) = matchVal
                iter.next()
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (selfLen, otherLen) =
            when (val matchVal = this.inner) {
                is DifferenceInner.Stitch -> {
                    val selfIter = matchVal.selfIter
                    val otherIter = matchVal.otherIter
                    Pair(selfIter.len(), otherIter.len())
                }
                is DifferenceInner.Search -> {
                    val selfIter1 = matchVal.selfIter
                    val otherSet = matchVal.otherSet
                    Pair(selfIter1.len(), otherSet.len())
                }
                is DifferenceInner.Iterate -> {
                    val (iter) = matchVal
                    Pair(iter.len(), 0)
                }
            }
        return Pair(selfLen.saturatingSub(otherLen), selfLen)
    }

    public open fun min(): T? = this.next()

    public companion object {
        public operator fun <T, A : AllocatorClone> invoke(
            inner: DifferenceInner<T, A>,
        ): Difference<T, A> =
            Difference<T, A>(
                innerCtorArg = inner,
            )
    }
}

public sealed class DifferenceInner<T, A : AllocatorClone> {
    public class Stitch<T, A : AllocatorClone> internal constructor(
        public var selfIter: Iter<T>,
        otherIterCtorArg: Peekable<Iter<T>>,
    ) : DifferenceInner<T, A>() {
        public var otherIter: Peekable<Iter<T>> = otherIterCtorArg
            internal set

        public operator fun component1(): Iter<T> = this.selfIter

        public operator fun component2(): Peekable<Iter<T>> = this.otherIter

        public fun copy(selfIter: Iter<T> = this.selfIter, otherIter: Peekable<Iter<T>> = this.otherIter): Stitch<T, A> = Stitch<T, A>(selfIter = selfIter, otherIterCtorArg = otherIter)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Stitch<*, *>) return false
            if (this.selfIter != other.selfIter) return false
            if (this.otherIter != other.otherIter) return false
            return true
        }

        override fun hashCode(): Int {
            var result = this.selfIter.hashCode()
            result = 31 * result + this.otherIter.hashCode()
            return result
        }

        public companion object {
            public operator fun <T, A : AllocatorClone> invoke(
                selfIter: Iter<T>,
                otherIter: Peekable<Iter<T>>,
            ): Stitch<T, A> =
                Stitch<T, A>(
                    selfIter = selfIter,
                    otherIterCtorArg = otherIter,
                )
        }
    }
    public class Search<T, A : AllocatorClone> internal constructor(
        public var selfIter: Iter<T>,
        otherSetCtorArg: BTreeSet<T, A>,
    ) : DifferenceInner<T, A>() {
        public var otherSet: BTreeSet<T, A> = otherSetCtorArg
            internal set

        public operator fun component1(): Iter<T> = this.selfIter

        public operator fun component2(): BTreeSet<T, A> = this.otherSet

        public fun copy(selfIter: Iter<T> = this.selfIter, otherSet: BTreeSet<T, A> = this.otherSet): Search<T, A> = Search<T, A>(selfIter = selfIter, otherSetCtorArg = otherSet)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Search<*, *>) return false
            if (this.selfIter != other.selfIter) return false
            if (this.otherSet != other.otherSet) return false
            return true
        }

        override fun hashCode(): Int {
            var result = this.selfIter.hashCode()
            result = 31 * result + this.otherSet.hashCode()
            return result
        }

        public companion object {
            public operator fun <T, A : AllocatorClone> invoke(
                selfIter: Iter<T>,
                otherSet: BTreeSet<T, A>,
            ): Search<T, A> =
                Search<T, A>(
                    selfIter = selfIter,
                    otherSetCtorArg = otherSet,
                )
        }
    }
    public data class Iterate<T, A : AllocatorClone>(
        public var value: Iter<T>,
    ) : DifferenceInner<T, A>()

    // Explicit Debug impl necessary because of issue #26925
    public open fun fmt(f: StringBuilder) {
        when (this) {
            is DifferenceInner.Stitch -> {
                val selfIter = this.selfIter
                val otherIter = this.otherIter
                DebugStruct(f, "Stitch").field("self_iter", selfIter).field("other_iter", otherIter).finish()
            }
            is DifferenceInner.Search -> {
                val selfIter1 = this.selfIter
                val otherSet = this.otherSet
                DebugStruct(f, "Search").field("self_iter", selfIter1).field("other_iter", otherSet).finish()
            }
            is DifferenceInner.Iterate -> {
                val (x) = this
                DebugTuple(f, "Iterate").field(x).finish()
            }
        }
    }
}

/**
 * A lazy iterator producing elements in the symmetric difference of `BTreeSet`s.
 *
 * This `class` is created by the [symmetricDifference] method on
 * [BTreeSet]. See its documentation for more.
 *
 * [symmetricDifference]: BTreeSet.symmetricDifference
 */
public open class SymmetricDifference<T> internal constructor(
    valueCtorArg: MergeIterInner<Iter<T>>,
) : Iterable<T> {
    public var value: MergeIterInner<Iter<T>> = valueCtorArg
        internal set

    public var field0: MergeIterInner<Iter<T>>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@SymmetricDifference.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "SymmetricDifference").field(this.value).finish()
    }

    public open fun clone(): SymmetricDifference<T> = SymmetricDifference(this.value.clone())

    public open fun next(): T? {
        while (true) {
            val (aNext, bNext) = this.value.nexts(SymmetricDifference.Item::cmp)
            if (aNext.and(bNext) == null) {
                return aNext.or(bNext)
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (aLen, bLen) = this.value.lens()
        // No checkedAdd, because even if a and b refer to the same set,
        // and T is a zero-sized type, the storage overhead of sets limits
        // the number of elements to less than half the range of Int.
        return Pair(0, aLen + bLen)
    }

    public open fun min(): T? = this.next()

    public companion object {
        public operator fun <T> invoke(
            value: MergeIterInner<Iter<T>>,
        ): SymmetricDifference<T> =
            SymmetricDifference<T>(
                valueCtorArg = value,
            )
    }
}

/**
 * A lazy iterator producing elements in the intersection of `BTreeSet`s.
 *
 * This `class` is created by the [intersection] method on [BTreeSet].
 * See its documentation for more.
 *
 * [intersection]: BTreeSet.intersection
 */
public open class Intersection<T, A : AllocatorClone> internal constructor(
    innerCtorArg: IntersectionInner<T, A>,
) : Iterable<T> {
    public var inner: IntersectionInner<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@Intersection.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "Intersection").field(this.inner).finish()
    }

    public open fun clone(): Intersection<T, A> =
        Intersection(
            inner =
                when (val matchVal = this.inner) {
                    is IntersectionInner.Stitch -> {
                        val a = matchVal.a
                        val b = matchVal.b
                        IntersectionInner.Stitch(a = a.clone(), b = b.clone())
                    }
                    is IntersectionInner.Search -> {
                        val smallIter = matchVal.smallIter
                        val largeSet = matchVal.largeSet
                        IntersectionInner.Search(smallIter = smallIter.clone(), largeSet = largeSet)
                    }
                    is IntersectionInner.Answer -> {
                        val (answer) = matchVal
                        IntersectionInner.Answer(answer!!)
                    }
                },
        )

    public open fun next(): T? {
        return when (val matchVal = this.inner) {
            is IntersectionInner.Stitch -> {
                val a = matchVal.a
                val b = matchVal.b
                var aNext = (a.next() ?: return null)
                var bNext = (b.next() ?: return null)
                while (true) {
                    when (aNext.cmp(bNext)) {
                        Less -> aNext = (a.next() ?: return null)
                        Greater -> bNext = (b.next() ?: return null)
                        Equal -> {
                            return aNext
                        }
                    }
                }
            }
            is IntersectionInner.Search -> {
                val smallIter = matchVal.smallIter
                val largeSet = matchVal.largeSet
                while (true) {
                    val smallNext = (smallIter.next() ?: return null)
                    if (largeSet.contains(smallNext)) {
                        return smallNext
                    }
                }
            }
            is IntersectionInner.Answer -> {
                val (answer) = matchVal
                answer.also { answer = null }
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        run {
            val matchVal = this.inner
            when {
                matchVal is IntersectionInner.Stitch -> {
                    val a = matchVal.a
                    val b = matchVal.b
                    Pair(0, minOf(a.len(), b.len()))
                }
                matchVal is IntersectionInner.Search -> {
                    val smallIter = matchVal.smallIter
                    Pair(0, smallIter.len())
                }
                matchVal is IntersectionInner.Answer && matchVal.value == null -> {
                    val (None) = matchVal
                    Pair(0, 0)
                }
                else -> Pair(1, 1)
            }
        }

    public open fun min(): T? = this.next()

    public companion object {
        public operator fun <T, A : AllocatorClone> invoke(
            inner: IntersectionInner<T, A>,
        ): Intersection<T, A> =
            Intersection<T, A>(
                innerCtorArg = inner,
            )
    }
}

public sealed class IntersectionInner<T, A : AllocatorClone> {
    public data class Stitch<T, A : AllocatorClone>(
        public var a: Iter<T>,
        public var b: Iter<T>,
    ) : IntersectionInner<T, A>()
    public class Search<T, A : AllocatorClone> internal constructor(
        public var smallIter: Iter<T>,
        largeSetCtorArg: BTreeSet<T, A>,
    ) : IntersectionInner<T, A>() {
        public var largeSet: BTreeSet<T, A> = largeSetCtorArg
            internal set

        public operator fun component1(): Iter<T> = this.smallIter

        public operator fun component2(): BTreeSet<T, A> = this.largeSet

        public fun copy(smallIter: Iter<T> = this.smallIter, largeSet: BTreeSet<T, A> = this.largeSet): Search<T, A> = Search<T, A>(smallIter = smallIter, largeSetCtorArg = largeSet)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Search<*, *>) return false
            if (this.smallIter != other.smallIter) return false
            if (this.largeSet != other.largeSet) return false
            return true
        }

        override fun hashCode(): Int {
            var result = this.smallIter.hashCode()
            result = 31 * result + this.largeSet.hashCode()
            return result
        }

        public companion object {
            public operator fun <T, A : AllocatorClone> invoke(
                smallIter: Iter<T>,
                largeSet: BTreeSet<T, A>,
            ): Search<T, A> =
                Search<T, A>(
                    smallIter = smallIter,
                    largeSetCtorArg = largeSet,
                )
        }
    }
    public data class Answer<T, A : AllocatorClone>(
        public var value: T?,
    ) : IntersectionInner<T, A>()

    // Explicit Debug impl necessary because of issue #26925
    public open fun fmt(f: StringBuilder) {
        when (this) {
            is IntersectionInner.Stitch -> {
                val a = this.a
                val b = this.b
                DebugStruct(f, "Stitch").field("a", a).field("b", b).finish()
            }
            is IntersectionInner.Search -> {
                val smallIter = this.smallIter
                val largeSet = this.largeSet
                DebugStruct(f, "Search").field("small_iter", smallIter).field("large_set", largeSet).finish()
            }
            is IntersectionInner.Answer -> {
                val (x) = this
                DebugTuple(f, "Answer").field(x).finish()
            }
        }
    }
}

/**
 * A lazy iterator producing elements in the union of `BTreeSet`s.
 *
 * This `class` is created by the [union] method on [BTreeSet].
 * See its documentation for more.
 *
 * [union]: BTreeSet.union
 */
public open class Union<T> internal constructor(
    valueCtorArg: MergeIterInner<Iter<T>>,
) : Iterable<T> {
    public var value: MergeIterInner<Iter<T>> = valueCtorArg
        internal set

    public var field0: MergeIterInner<Iter<T>>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@Union.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "Union").field(this.value).finish()
    }

    public open fun clone(): Union<T> = Union(this.value.clone())

    public open fun next(): T? {
        val (aNext, bNext) = this.value.nexts(Union.Item::cmp)
        return aNext.or(bNext)
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (aLen, bLen) = this.value.lens()
        // No checkedAdd - see SymmetricDifference.sizeHint.
        return Pair(max(aLen, bLen), aLen + bLen)
    }

    public open fun min(): T? = this.next()

    public companion object {
        public operator fun <T> invoke(
            value: MergeIterInner<Iter<T>>,
        ): Union<T> =
            Union<T>(
                valueCtorArg = value,
            )
    }
}

// This constant is used by functions that compare two sets.
// It estimates the relative size at which searching performs better
// than iterating, based on the benchmarks in
// https://github.com/ssomers/rustBenchBtreesetIntersection.
// It's used to divide rather than multiply sizes, to rule out overflow,
// and it's a power of two to make that division cheap.
public val ITER_PERFORMANCE_TIPPING_SIZE_DIFF: Int = 16

/**
 * Makes a new, empty `BTreeSet`.
 *
 * Does not allocate anything on its own.
 *
 * # Examples
 *
 * ```kotlin
 * var set: MutableSet<Int> = mutableSetOf()
 * ```
 */
public fun <T> BTreeSet.Companion.new(): BTreeSet<T> = BTreeSet(map = mutableMapOf())

public fun <T, I : Iterable<T>> fromIteratorFromIterBTreeSetT(iter: I): BTreeSet<T> {
    val inputs: Vec = iter.iterator().collect()
    if (inputs.isEmpty()) {
        return BTreeSet.new()
    }
    // use stable sort to preserve the insertion order.
    inputs.sort()
    return BTreeSet.fromSortedIter(inputs.intoIter(), Global)
}

/**
 * Converts a `[T; N]` into a `BTreeSet<T>`.
 *
 * If the array contains any equal values,
 * all but one will be dropped.
 *
 * # Examples
 *
 * ```kotlin
 * val set1 = mutableSetOf(1, 2, 3, 4)
 * val set2: BTreeSet = listOf(1, 2, 3, 4).into()
 * check(set1 == set2)
 * ```
 */
public fun <T> fromBTreeSetT(arr: List<T>, n: Int): BTreeSet<T> {
    if (n == 0) {
        return BTreeSet.new()
    }
    // use stable sort to preserve the insertion order.
    arr.sort()
    return BTreeSet.fromSortedIter(IntoIterator.IntoIter(arr), Global)
}

/**
 * This `class` is created by the [extractIf] method on [BTreeSet].
 *
 * [extractIf]: BTreeSet.extractIf
 */
public open class ExtractIf<T, R, F, A : AllocatorClone> internal constructor(
    public var pred: F,
    innerCtorArg: ExtractIfInner<T, SetValZST, R>,
    /**
     * The BTreeMap will outlive this IntoIter so we don't care about drop order for `alloc`.
     */
    public var alloc: A,
) : Iterable<T> {
    public var inner: ExtractIfInner<T, SetValZST, R> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@ExtractIf.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "ExtractIf").field("peek", this.inner.peek().map({ (k, _) -> k })).finishNonExhaustive()
    }

    public open fun next(): T? {
        val pred = this.pred
        val mappedPred = { k: T, v: SetValZST -> pred(k) }
        return this.inner.next(mappedPred, this.alloc.clone())?.let({ (k, _) -> k })
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public companion object {
        public operator fun <T, R, F, A : AllocatorClone> invoke(
            pred: F,
            inner: ExtractIfInner<T, SetValZST, R>,
            alloc: A,
        ): ExtractIf<T, R, F, A> =
            ExtractIf<T, R, F, A>(
                pred = pred,
                innerCtorArg = inner,
                alloc = alloc,
            )
    }
}

/**
 * Creates an empty `BTreeSet`.
 */
public fun <T> defaultBTreeSetT(): BTreeSet<T> = BTreeSet.new()

/**
 * A cursor over a `BTreeSet`.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth.
 *
 * Cursors always point to a gap between two elements in the set, and can
 * operate on the two immediately adjacent elements.
 *
 * A `Cursor` is created with the [BTreeSet.lowerBound] and [BTreeSet.upperBound] methods.
 */
public open class Cursor<K> internal constructor(
    innerCtorArg: io.github.kotlinmania.rustlang.alloc.collections.btree.map.Cursor<K, SetValZST>,
) {
    public var inner: io.github.kotlinmania.rustlang.alloc.collections.btree.map.Cursor<K, SetValZST> = innerCtorArg
        internal set

    public open fun clone(): Cursor<K> = Cursor(inner)

    public open fun fmt(f: StringBuilder) {
        f.append("Cursor")
    }

    /**
     * Advances the cursor to the next gap, returning the element that it
     * moved over.
     *
     * If the cursor is already at the end of the set then `None` is returned
     * and the cursor is not moved.
     */
    public open fun next(): K? = this.inner.next()?.let({ (k, _) -> k })

    /**
     * Advances the cursor to the previous gap, returning the element that it
     * moved over.
     *
     * If the cursor is already at the start of the set then `None` is returned
     * and the cursor is not moved.
     */
    public open fun prev(): K? = this.inner.prev().map({ (k, _) -> k })

    /**
     * Returns a reference to next element without moving the cursor.
     *
     * If the cursor is at the end of the set then `None` is returned
     */
    public open fun peekNext(): K? = this.inner.peekNext().map({ (k, _) -> k })

    /**
     * Returns a reference to the previous element without moving the cursor.
     *
     * If the cursor is at the start of the set then `None` is returned.
     */
    public open fun peekPrev(): K? = this.inner.peekPrev().map({ (k, _) -> k })

    public companion object {
        public operator fun <K> invoke(
            inner: io.github.kotlinmania.rustlang.alloc.collections.btree.map.Cursor<K, SetValZST>,
        ): Cursor<K> =
            Cursor<K>(
                innerCtorArg = inner,
            )
    }
}

/**
 * A cursor over a `BTreeSet` with editing operations.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth, and can
 * safely mutate the set during iteration. This is because the scope of its yielded
 * references is tied to its own scope, instead of just the underlying map. This means
 * cursors cannot yield multiple elements at once.
 *
 * Cursors always point to a gap between two elements in the set, and can
 * operate on the two immediately adjacent elements.
 *
 * A `CursorMut` is created with the [BTreeSet.lowerBoundMut] and [BTreeSet.upperBoundMut]
 * methods.
 */
public open class CursorMut<K, A> internal constructor(
    innerCtorArg: io.github.kotlinmania.rustlang.alloc.collections.btree.map.CursorMut<K, SetValZST, A>,
) {
    public var inner: io.github.kotlinmania.rustlang.alloc.collections.btree.map.CursorMut<K, SetValZST, A> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder) {
        f.append("CursorMut")
    }

    public companion object {
        public operator fun <K, A> invoke(
            inner: io.github.kotlinmania.rustlang.alloc.collections.btree.map.CursorMut<K, SetValZST, A>,
        ): CursorMut<K, A> =
            CursorMut<K, A>(
                innerCtorArg = inner,
            )
    }
}

/**
 * A cursor over a `BTreeSet` with editing operations, and which allows
 * mutating elements.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth, and can
 * safely mutate the set during iteration. This is because the scope of its yielded
 * references is tied to its own scope, instead of just the underlying set. This means
 * cursors cannot yield multiple elements at once.
 *
 * Cursors always point to a gap between two elements in the set, and can
 * operate on the two immediately adjacent elements.
 *
 * A `CursorMutKey` is created from a [CursorMut] with the
 * [CursorMut.withMutableKey] method.
 *
 * # Safety
 *
 * Since this cursor allows mutating elements, you must ensure that the
 * `BTreeSet` invariants are maintained. Specifically:
 *
 * * The newly inserted element must be unique in the tree.
 * * All elements in the tree must remain in sorted order.
 */
public open class CursorMutKey<K, A> internal constructor(
    innerCtorArg: io.github.kotlinmania.rustlang.alloc.collections.btree.map.CursorMutKey<K, SetValZST, A>,
) {
    public var inner: io.github.kotlinmania.rustlang.alloc.collections.btree.map.CursorMutKey<K, SetValZST, A> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder) {
        f.append("CursorMutKey")
    }

    public companion object {
        public operator fun <K, A> invoke(
            inner: io.github.kotlinmania.rustlang.alloc.collections.btree.map.CursorMutKey<K, SetValZST, A>,
        ): CursorMutKey<K, A> =
            CursorMutKey<K, A>(
                innerCtorArg = inner,
            )
    }
}

/**
 * Advances the cursor to the next gap, returning the element that it
 * moved over.
 *
 * If the cursor is already at the end of the set then `None` is returned
 * and the cursor is not moved.
 */
public fun <T, A> CursorMut<T, A>.next(): T? = this.inner.next()?.let({ (k, _) -> k })

/**
 * Advances the cursor to the previous gap, returning the element that it
 * moved over.
 *
 * If the cursor is already at the start of the set then `None` is returned
 * and the cursor is not moved.
 */
public fun <T, A> CursorMut<T, A>.prev(): T? = this.inner.prev().map({ (k, _) -> k })

/**
 * Returns a reference to the next element without moving the cursor.
 *
 * If the cursor is at the end of the set then `None` is returned.
 */
public fun <T, A> CursorMut<T, A>.peekNext(): T? = this.inner.peekNext().map({ (k, _) -> k })

/**
 * Returns a reference to the previous element without moving the cursor.
 *
 * If the cursor is at the start of the set then `None` is returned.
 */
public fun <T, A> CursorMut<T, A>.peekPrev(): T? = this.inner.peekPrev().map({ (k, _) -> k })

/**
 * Returns a read-only cursor pointing to the same location as the
 * `CursorMut`.
 *
 * The scope of the returned `Cursor` is bound to that of the
 * `CursorMut`, which means it cannot outlive the `CursorMut` and that the
 * `CursorMut` is frozen for the scope of the `Cursor`.
 */
public fun <T, A> CursorMut<T, A>.asCursor(): Cursor<T> = Cursor(inner = this.inner.asCursor())

/**
 * Converts the cursor into a [CursorMutKey], which allows mutating
 * elements in the tree.
 *
 * # Safety
 *
 * Since this cursor allows mutating elements, you must ensure that the
 * `BTreeSet` invariants are maintained. Specifically:
 *
 * * The newly inserted element must be unique in the tree.
 * * All elements in the tree must remain in sorted order.
 */
public fun <T, A> CursorMut<T, A>.withMutableKey(): CursorMutKey<T, A> {
    // ignore-tidy-undocumented-unchecked
    return CursorMutKey(inner = this.inner.withMutableKey())
}

/**
 * Advances the cursor to the next gap, returning the  element that it
 * moved over.
 *
 * If the cursor is already at the end of the set then `None` is returned
 * and the cursor is not moved.
 */
public fun <T, A> CursorMutKey<T, A>.next(): T? = this.inner.next()?.let({ (k, _) -> k })

/**
 * Advances the cursor to the previous gap, returning the element that it
 * moved over.
 *
 * If the cursor is already at the start of the set then `None` is returned
 * and the cursor is not moved.
 */
public fun <T, A> CursorMutKey<T, A>.prev(): T? = this.inner.prev().map({ (k, _) -> k })

/**
 * Returns a reference to the next element without moving the cursor.
 *
 * If the cursor is at the end of the set then `None` is returned
 */
public fun <T, A> CursorMutKey<T, A>.peekNext(): T? = this.inner.peekNext().map({ (k, _) -> k })

/**
 * Returns a reference to the previous element without moving the cursor.
 *
 * If the cursor is at the start of the set then `None` is returned.
 */
public fun <T, A> CursorMutKey<T, A>.peekPrev(): T? = this.inner.peekPrev().map({ (k, _) -> k })

/**
 * Returns a read-only cursor pointing to the same location as the
 * `CursorMutKey`.
 *
 * The scope of the returned `Cursor` is bound to that of the
 * `CursorMutKey`, which means it cannot outlive the `CursorMutKey` and that the
 * `CursorMutKey` is frozen for the scope of the `Cursor`.
 */
public fun <T, A> CursorMutKey<T, A>.asCursor(): Cursor<T> = Cursor(inner = this.inner.asCursor())

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap before the
 * newly inserted element.
 *
 * # Safety
 *
 * You must ensure that the `BTreeSet` invariants are maintained.
 * Specifically:
 *
 * * The newly inserted element must be unique in the tree.
 * * All elements in the tree must remain in sorted order.
 */
public fun <T, A : AllocatorClone> CursorMut<T, A>.insertAfterUnchecked(value: T) {
    // SAFETY: Upheld by caller.
    this.inner.insertAfterUnchecked(value, SetValZST)
}

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap after the
 * newly inserted element.
 *
 * # Safety
 *
 * You must ensure that the `BTreeSet` invariants are maintained.
 * Specifically:
 *
 * * The newly inserted element must be unique in the tree.
 * * All elements in the tree must remain in sorted order.
 */
public fun <T, A : AllocatorClone> CursorMut<T, A>.insertBeforeUnchecked(value: T) {
    // SAFETY: Upheld by caller.
    this.inner.insertBeforeUnchecked(value, SetValZST)
}

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap before the
 * newly inserted element.
 *
 * If the inserted element is not greater than the element before the
 * cursor (if any), or if it not less than the element after the cursor (if
 * any), then an [UnorderedKeyError] is returned since this would
 * invalidate the [Ord] invariant between the elements of the set.
 */
public fun <T, A : AllocatorClone> CursorMut<T, A>.insertAfter(value: T): Result<Unit, UnorderedKeyError> = this.inner.insertAfter(value, SetValZST)

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap after the
 * newly inserted element.
 *
 * If the inserted element is not greater than the element before the
 * cursor (if any), or if it not less than the element after the cursor (if
 * any), then an [UnorderedKeyError] is returned since this would
 * invalidate the [Ord] invariant between the elements of the set.
 */
public fun <T, A : AllocatorClone> CursorMut<T, A>.insertBefore(value: T): Result<Unit, UnorderedKeyError> = this.inner.insertBefore(value, SetValZST)

/**
 * Removes the next element from the `BTreeSet`.
 *
 * The element that was removed is returned. The cursor position is
 * unchanged (before the removed element).
 */
public fun <T, A : AllocatorClone> CursorMut<T, A>.removeNext(): T? = this.inner.removeNext().map({ (k, _) -> k })

/**
 * Removes the preceding element from the `BTreeSet`.
 *
 * The element that was removed is returned. The cursor position is
 * unchanged (after the removed element).
 */
public fun <T, A : AllocatorClone> CursorMut<T, A>.removePrev(): T? = this.inner.removePrev().map({ (k, _) -> k })

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap before the
 * newly inserted element.
 *
 * # Safety
 *
 * You must ensure that the `BTreeSet` invariants are maintained.
 * Specifically:
 *
 * * The key of the newly inserted element must be unique in the tree.
 * * All elements in the tree must remain in sorted order.
 */
public fun <T, A : AllocatorClone> CursorMutKey<T, A>.insertAfterUnchecked(value: T) {
    // SAFETY: Upheld by caller.
    this.inner.insertAfterUnchecked(value, SetValZST)
}

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap after the
 * newly inserted element.
 *
 * # Safety
 *
 * You must ensure that the `BTreeSet` invariants are maintained.
 * Specifically:
 *
 * * The newly inserted element must be unique in the tree.
 * * All elements in the tree must remain in sorted order.
 */
public fun <T, A : AllocatorClone> CursorMutKey<T, A>.insertBeforeUnchecked(value: T) {
    // SAFETY: Upheld by caller.
    this.inner.insertBeforeUnchecked(value, SetValZST)
}

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap before the
 * newly inserted element.
 *
 * If the inserted element is not greater than the element before the
 * cursor (if any), or if it not less than the element after the cursor (if
 * any), then an [UnorderedKeyError] is returned since this would
 * invalidate the [Ord] invariant between the elements of the set.
 */
public fun <T, A : AllocatorClone> CursorMutKey<T, A>.insertAfter(value: T): Result<Unit, UnorderedKeyError> = this.inner.insertAfter(value, SetValZST)

/**
 * Inserts a new element into the set in the gap that the
 * cursor is currently pointing to.
 *
 * After the insertion the cursor will be pointing at the gap after the
 * newly inserted element.
 *
 * If the inserted element is not greater than the element before the
 * cursor (if any), or if it not less than the element after the cursor (if
 * any), then an [UnorderedKeyError] is returned since this would
 * invalidate the [Ord] invariant between the elements of the set.
 */
public fun <T, A : AllocatorClone> CursorMutKey<T, A>.insertBefore(value: T): Result<Unit, UnorderedKeyError> = this.inner.insertBefore(value, SetValZST)

/**
 * Removes the next element from the `BTreeSet`.
 *
 * The element that was removed is returned. The cursor position is
 * unchanged (before the removed element).
 */
public fun <T, A : AllocatorClone> CursorMutKey<T, A>.removeNext(): T? = this.inner.removeNext().map({ (k, _) -> k })

/**
 * Removes the preceding element from the `BTreeSet`.
 *
 * The element that was removed is returned. The cursor position is
 * unchanged (after the removed element).
 */
public fun <T, A : AllocatorClone> CursorMutKey<T, A>.removePrev(): T? = this.inner.removePrev().map({ (k, _) -> k })
