// port-lint: source set.rs
package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.iter.adapters.chain.Chain
import io.github.kotlinmania.rustlang.core.ops.arith.Sub
import io.github.kotlinmania.rustlang.core.ops.arith.SubAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitAnd
import io.github.kotlinmania.rustlang.core.ops.bit.BitAndAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.BitOrAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitXor
import io.github.kotlinmania.rustlang.core.ops.bit.BitXorAssign
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.alloc.Global
import io.github.kotlinmania.rustlang.hashbrown.hasher.DefaultHashBuilder
import io.github.kotlinmania.rustlang.hashbrown.map.HashMap
import io.github.kotlinmania.rustlang.hashbrown.map.Keys
import io.github.kotlinmania.rustlang.hashbrown.raw.RawExtractIf

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

// Future Optimization (Note!)
// =============================
//
// Iteration over zero sized values is a noop. There is no need
// for `bucket.val` in the case of HashSet. I suppose we would need HKT
// to get rid of it properly.

/**
 * A hash set implemented as a `HashMap` where the value is `()`.
 *
 * As with the [HashMap] type, a `HashSet` requires that the elements
 * implement the [Eq] and [Hash] traits. This can frequently be achieved by
 * using `@derive(PartialEq, Eq, Hash)`. If you implement these yourself,
 * it is important that the following property holds:
 *
 * ```text
 * k1 == k2 -> hash(k1) == hash(k2)
 * ```
 *
 * In other words, if two keys are equal, their hashes must be equal.
 *
 * It is a logic error for an item to be modified in such a way that the
 * item's hash, as determined by the [Hash] trait, or its equality, as
 * determined by the [Eq] trait, changes while it is in the set. This is
 * normally only possible through [Cell], [RefCell], global state, I/O, or
 * unchecked code.
 *
 * It is also a logic error for the [Hash] implementation of a key to panic.
 * This is generally only possible if the trait is implemented manually. If a
 * panic does occur then the contents of the `HashSet` may become corrupted and
 * some items may be dropped from the table.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * // Type inference lets us omit an explicit type signature (which
 * // would be `HashSet<String>` in this example).
 * var books = mutableSetOf()
 * // Add some books.
 * books.add("A Dance With Dragons".toString())
 * books.add("To Kill a Mockingbird".toString())
 * books.add("The Odyssey".toString())
 * books.add("The Great Gatsby".toString())
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
 * The easiest way to use `HashSet` with a custom type is to derive
 * [Eq] and [Hash]. We must also derive [PartialEq]. This will in the
 * future be implied by [Eq].
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * open class Viking(public var name: String, public var power: Int)
 * var vikings = mutableSetOf()
 * vikings.add(Viking(name = "Einar".toString(), power = 9))
 * vikings.add(Viking(name = "Einar".toString(), power = 9))
 * vikings.add(Viking(name = "Olaf".toString(), power = 4))
 * vikings.add(Viking(name = "Harald".toString(), power = 8))
 * // Use derived implementation to print the vikings.
 * for (x in vikings) {
 *     println("$x")
 * }
 * ```
 *
 * A `HashSet` with fixed list of elements can be initialized from an array:
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * val vikingNames: MutableSet<String> = listOf("Einar", "Olaf", "Harald").intoIter().toMutableSet()
 * // use the values stored in the set
 * ```
 */
public open class HashSet<T, S, A : Allocator>(
    public var map: MutableMap<T, Unit>,
) : BitOr<HashSet<T, S, A>, HashSet<T, S, A>>, BitAnd<HashSet<T, S, A>, HashSet<T, S, A>>, BitXor<HashSet<T, S, A>, HashSet<T, S, A>>, Sub<HashSet<T, S, A>, HashSet<T, S, A>>, BitOrAssign<HashSet<T, S, A>>, BitAndAssign<HashSet<T, S, A>>, BitXorAssign<HashSet<T, S, A>>, SubAssign<HashSet<T, S, A>>, Iterable<T> {
    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    public open fun clone(): HashSet<T, S, A> = HashSet(map = this.map.toMutableMap())

    public open fun cloneFrom(source: HashSet<T, S, A>) {
        this.map = source.map
    }

    /**
     * Returns the number of elements the set can hold without reallocating.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val set: MutableSet<Int> = mutableSetOf()
     * check(set.capacity() >= 100)
     * ```
     */
    public open fun capacity(): Int = this.map.capacity()

    /**
     * An iterator visiting all elements in arbitrary order.
     * The iterator element type is `T`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * set.add("a")
     * set.add("b")
     * // Will print in an arbitrary order.
     * for (x in set.iter()) {
     *     println("$x")
     * }
     * ```
     */
    public open fun iter(): Iter<T> = Iter(iter = this.map.keys())

    /**
     * Returns the number of elements in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
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
     * import hashbrown.HashSet
     *
     * var v = mutableSetOf()
     * check(v.isEmpty())
     * v.add(1)
     * check(!v.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.map.isEmpty()

    /**
     * Clears the set, returning all elements in an iterator.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: HashSet = listOf(1, 2, 3).intoIter().collect()
     * check(!set.isEmpty())
     * // print 1, 2, 3 in an arbitrary order
     * for (i in set.drain()) {
     *     println("$i")
     * }
     * check(set.isEmpty())
     * ```
     */
    public open fun drain(): Drain<T, A> = Drain(iter = this.map.drain())

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` such that `f(e)` returns `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val xs = listOf(1, 2, 3, 4, 5, 6)
     * var set: MutableSet<Int> = xs.intoIter().toMutableSet()
     * set.retain({ k -> k % 2 == 0 })
     * check(set.size == 3)
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        this.map.retain({ k -> f(k) })
    }

    /**
     * Drains elements which are true under the given predicate,
     * and returns an iterator over the removed items.
     *
     * In other words, move all elements `e` such that `f(e)` returns `true` out
     * into another iterator.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use [retain] with a negated predicate if you do not need the returned iterator.
     *
     * [retain]: HashSet.retain
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<Int> = (0 until 8).toMutableSet()
     * val drained: MutableSet<Int> = set.extractIf({ v -> v % 2 == 0 }).toMutableSet()
     * var evens = ArrayDeque((drained).toList()).collect<Vec>()
     * var odds = ArrayDeque((set).toList()).collect<Vec>()
     * evens.sort()
     * odds.sort()
     * check(evens == mutableListOf(0, 2, 4, 6))
     * check(odds == mutableListOf(1, 3, 5, 7))
     * ```
     */
    public open fun extractIf(f: (T) -> Boolean): ExtractIf<T, (T) -> Boolean, A> = ExtractIf(f = f, inner = RawExtractIf(iter = this.map.table.iter(), table = this.map.table))

    /**
     * Clears the set, removing all values.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
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
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.map.allocator()

    /**
     * Returns a reference to the set's [BuildHasher].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashSet
     *
     * val hasher = DefaultHashBuilder.default()
     * val set: MutableSet<Int> = HashSet.withHasher(hasher)
     * val hasher1: DefaultHashBuilder = set.hasher()
     * ```
     */
    public open fun hasher(): S = this.map.hasher()

    /**
     * Reserves capacity for at least `additional` more elements to be inserted
     * in the `HashSet`. The collection may reserve more space to avoid
     * frequent reallocations.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds [isize.Max] bytes and [abort] the program
     * in case of allocation error. Use [`tryReserve`](HashSet.tryReserve) instead
     * if you want to handle memory allocation failure.
     *
     * [abort]: stdalloc.alloc.handleAllocError
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<Int> = mutableSetOf()
     * set.reserve(10)
     * check(set.capacity() >= 10)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.map.reserve(additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the given `HashSet<K, V>`. The collection may reserve more space to avoid
     * frequent reallocations.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<Int> = mutableSetOf()
     * set.tryReserve(10).expect("why is the test harness OOMing on 10 bytes?")
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.map.tryReserve(additional)

    /**
     * Shrinks the capacity of the set as much as possible. It will drop
     * down as much as possible while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * set.add(1)
     * set.add(2)
     * check(set.capacity() >= 100)
     * set.shrinkToFit()
     * check(set.capacity() >= 2)
     * ```
     */
    public open fun shrinkToFit() {
        this.map.shrinkToFit()
    }

    /**
     * Shrinks the capacity of the set with a lower limit. It will drop
     * down no lower than the supplied limit while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * Panics if the current capacity is smaller than the supplied
     * minimum capacity.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * set.add(1)
     * set.add(2)
     * check(set.capacity() >= 100)
     * set.shrinkTo(10)
     * check(set.capacity() >= 10)
     * set.shrinkTo(0)
     * check(set.capacity() >= 2)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.map.shrinkTo(minCapacity)
    }

    /**
     * Visits the values representing the difference,
     * i.e., the values that are in `self` but not in `other`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = listOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = listOf(4, 2, 3, 4).intoIter().collect()
     * // Can be seen as `a - b`.
     * for (x in a.difference(b)) {
     *     println("$x")
     *     // Print 1
     * }
     * val diff: HashSet = a.difference(b).collect()
     * check(diff == listOf(1).iter().collect())
     * // Note that difference is not symmetric,
     * // and `b - a` means something else:
     * val diff1: HashSet = b.difference(a).collect()
     * check(diff1 == listOf(4).iter().collect())
     * ```
     */
    public open fun difference(other: HashSet<T, S, A>): Difference<T, S, A> = Difference(iter = this.iter(), other = other)

    /**
     * Visits the values representing the symmetric difference,
     * i.e., the values that are in `self` or in `other` but not in both.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = listOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = listOf(4, 2, 3, 4).intoIter().collect()
     * // Print 1, 4 in arbitrary order.
     * for (x in a.symmetricDifference(b)) {
     *     println("$x")
     * }
     * val diff1: HashSet = a.symmetricDifference(b).collect()
     * val diff2: HashSet = b.symmetricDifference(a).collect()
     * check(diff1 == diff2)
     * check(diff1 == listOf(1, 4).iter().collect())
     * ```
     */
    public open fun symmetricDifference(other: HashSet<T, S, A>): SymmetricDifference<T, S, A> = SymmetricDifference(iter = this.difference(other).chain(other.difference(this)))

    /**
     * Visits the values representing the intersection,
     * i.e., the values that are both in `self` and `other`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = listOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = listOf(4, 2, 3, 4).intoIter().collect()
     * // Print 2, 3 in arbitrary order.
     * for (x in a.intersection(b)) {
     *     println("$x")
     * }
     * val intersection: HashSet = a.intersection(b).collect()
     * check(intersection == listOf(2, 3).iter().collect())
     * ```
     */
    public open fun intersection(other: HashSet<T, S, A>): Intersection<T, S, A> {
        val (smaller, larger) =
            if (this.len() <= other.len()) {
                Pair(this, other)
            } else {
                Pair(other, this)
            }
        return Intersection(iter = smaller.iter(), other = larger)
    }

    /**
     * Visits the values representing the union,
     * i.e., all the values in `self` or `other`, without duplicates.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = listOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = listOf(4, 2, 3, 4).intoIter().collect()
     * // Print 1, 2, 3, 4 in arbitrary order.
     * for (x in a.union(b)) {
     *     println("$x")
     * }
     * val union: HashSet = a.union(b).collect()
     * check(union == listOf(1, 2, 3, 4).iter().collect())
     * ```
     */
    public open fun union(other: HashSet<T, S, A>): Union<T, S, A> {
        // We'll iterate one set in full, and only the remaining difference from the other.
        // Use the smaller set for the difference in order to reduce hash lookups.
        val (smaller, larger) =
            if (this.len() <= other.len()) {
                Pair(this, other)
            } else {
                Pair(other, this)
            }
        return Union(iter = larger.iter().chain(smaller.difference(larger)))
    }

    /**
     * Returns `true` if the set contains a value.
     *
     * The value may be any borrowed form of the set's value type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the value type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val set: HashSet = listOf(1, 2, 3).intoIter().collect()
     * check(set.contains(1) == true)
     * check(set.contains(4) == false)
     * ```
     */
    public open fun <Q> contains(value: Q): Boolean where Q : Equivalent<T> = this.map.containsKey(value)

    /**
     * Returns a reference to the value in the set, if any, that is equal to the given value.
     *
     * The value may be any borrowed form of the set's value type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the value type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val set: HashSet = listOf(1, 2, 3).intoIter().collect()
     * check(set.get(2) == 2)
     * check(set.get(4) == null)
     * ```
     */
    public open fun <Q> get(value: Q): T? where Q : Equivalent<T> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.map.getKeyValue(value)) {
            null -> null
            else -> {
                val k = matchVal.first
                k
            }
        }
    }

    /**
     * Inserts the given `value` into the set if it is not present, then
     * returns a reference to the value in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: HashSet = listOf(1, 2, 3).intoIter().collect()
     * check(set.len() == 3)
     * check(set.getOrInsert(2) == 2)
     * check(set.getOrInsert(100) == 100)
     * check(set.len() == 4)
     * // 100 was inserted
     * ```
     */
    public open fun getOrInsert(value: T): T =
        when (val matchVal = this.map.entry(value)) {
            is Entry.Occupied -> {
                val (entry) = matchVal
                entry
            }
            is Entry.Vacant -> {
                val (entry1) = matchVal
                entry1.insertEntry()
            }
        }.intoEntry().first

    /**
     * Inserts a value computed from `f` into the set if the given `value` is
     * not present, then returns a reference to the value in the set.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<String> = listOf("cat", "dog", "horse").iter().map({ pet -> pet.toOwned() }).toMutableSet()
     * check(set.size == 3)
     * for (pet in listOf("cat", "dog", "fish")) {
     *     val value = set.getOrInsertWith(pet, { it.toOwned() })
     *     check(value == pet)
     * }
     * check(set.size == 4)
     * // a new "fish" was inserted
     * ```
     *
     * The following example will panic because the new value doesn't match.
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.getOrInsertWith("rust", { "" })
     * ```
     */
    public open fun <Q> getOrInsertWith(value: Q, f: (Q) -> T): T where Q : Equivalent<T> =
        when (val matchVal = this.map.entryRef(value)) {
            is EntryRef.Occupied -> {
                val (entry) = matchVal
                entry
            }
            is EntryRef.Vacant -> {
                val (entry1) = matchVal
                entry1.insertEntryWithKey(f(value))
            }
        }.intoEntry().first

    /**
     * Gets the given value's corresponding entry in the set for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     * import hashbrown.hashset.Entry.*
     *
     * var singles = mutableSetOf()
     * var dupes = mutableSetOf()
     * for (ch in "a short treatise on fungi".chars()) {
     *     when (val matchVal = dupes.entry(ch)) {
     *         is Vacant -> {
     *             val (dupeEntry) = matchVal
     *             // We haven't already seen a duplicate, so
     *             // check if we've at least seen it once.
     *             when (val matchVal1 = singles.entry(ch)) {
     *                 is Vacant -> {
     *                     val (singleEntry) = matchVal1
     *                     // We found a new character for the first time.
     *                     singleEntry.insert()
     *                 }
     *                 is Occupied -> {
     *                     val (singleEntry1) = matchVal1
     *                     // We've already seen this once, "move" it to dupes.
     *                     singleEntry1.remove()
     *                     dupeEntry.insert()
     *                 }
     *             }
     *         }
     *         else -> {}
     *     }
     * }
     * check(!singles.contains('t') && dupes.contains('t'))
     * check(singles.contains('u') && !dupes.contains('u'))
     * check(!singles.contains('v') && !dupes.contains('v'))
     * ```
     */
    public open fun entry(value: T): Entry<T, S, A> =
        when (val matchVal = this.map.entry(value)) {
            is Entry.Occupied -> {
                val (entry) = matchVal
                Entry.Occupied(OccupiedEntry(inner = entry))
            }
            is Entry.Vacant -> {
                val (entry1) = matchVal
                Entry.Vacant(VacantEntry(inner = entry1))
            }
        }

    /**
     * Returns `true` if `self` has no elements in common with `other`.
     * This is equivalent to checking for an empty intersection.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = listOf(1, 2, 3).intoIter().collect()
     * var b = mutableSetOf()
     * check(a.isDisjoint(b) == true)
     * b.add(4)
     * check(a.isDisjoint(b) == true)
     * b.add(1)
     * check(a.isDisjoint(b) == false)
     * ```
     */
    public open fun isDisjoint(other: HashSet<T, S, A>): Boolean = this.intersection(other).next() == null

    /**
     * Returns `true` if the set is a subset of another,
     * i.e., `other` contains at least all the values in `self`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val sup: HashSet = listOf(1, 2, 3).intoIter().collect()
     * var set = mutableSetOf()
     * check(set.isSubset(sup) == true)
     * set.add(2)
     * check(set.isSubset(sup) == true)
     * set.add(4)
     * check(set.isSubset(sup) == false)
     * ```
     */
    public open fun isSubset(other: HashSet<T, S, A>): Boolean = this.len() <= other.len() && this.iter().all({ v -> other.contains(v) })

    /**
     * Returns `true` if the set is a superset of another,
     * i.e., `self` contains at least all the values in `other`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val sub: HashSet = listOf(1, 2).intoIter().collect()
     * var set = mutableSetOf()
     * check(set.isSuperset(sub) == false)
     * set.add(0)
     * set.add(1)
     * check(set.isSuperset(sub) == false)
     * set.add(2)
     * check(set.isSuperset(sub) == true)
     * ```
     */
    public open fun isSuperset(other: HashSet<T, S, A>): Boolean = other.isSubset(this)

    /**
     * Adds a value to the set.
     *
     * If the set did not have this value present, `true` is returned.
     *
     * If the set did have this value present, `false` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * check(set.add(2) == true)
     * check(set.add(2) == false)
     * check(set.size == 1)
     * ```
     */
    public open fun insert(value: T): Boolean = this.map.insert(value) == null

    /**
     * Insert a value the set without checking if the value already exists in the set.
     *
     * This operation is faster than regular insert, because it does not perform
     * lookup before insertion.
     *
     * This operation is useful during initial population of the set.
     * For example, when constructing a set from another set, we know
     * that values are unique.
     *
     * # Safety
     *
     * This operation is safe if a value does not exist in the set.
     *
     * However, if a value exists in the set already, the behavior is unspecified:
     * this operation may panic, loop forever, or any following operation with the set
     * may panic, loop forever or return arbitrary result.
     *
     * That said, this operation (and following operations) are guaranteed to
     * not violate memory safety.
     *
     * However this operation is still unchecked because the resulting `HashSet`
     * may be passed to unchecked code which does expect the set to behave
     * correctly, and would cause unsoundness as a result.
     */
    public open fun insertUniqueUnchecked(value: T): T = this.map.insertUniqueUnchecked(value).first

    /**
     * Adds a value to the set, replacing the existing value, if any, that is equal to the given
     * one. Returns the replaced value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * set.add(mutableListOf())
     * check(set.get(listOf())!!.capacity() == 0)
     * set.replace(ArrayList(10))
     * check(set.get(listOf())!!.capacity() == 10)
     * ```
     */
    public open fun replace(value: T): T? {
        val value1 = UnsafeCell.new(value)
        // SAFETY: We know the key is no longer accessed after the initial check.
        return when (val matchVal = this.map.entryRef(value1.get())) {
            is EntryRef.Occupied -> {
                val (entry) = matchVal
                // SAFETY: We know the key will not be accessed any more, and
                // that the key is equivalent to the one in the entry.
                entry.replaceKeyUnchecked(value1.intoInner())
            }
            is EntryRef.Vacant -> {
                val (entry1) = matchVal
                // SAFETY: A value is equivalent to itself.
                run {
                    entry1.insertWithKeyUnchecked(value1.intoInner())
                }
                null
            }
        }
    }

    /**
     * Removes a value from the set. Returns whether the value was
     * present in the set.
     *
     * The value may be any borrowed form of the set's value type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the value type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * set.add(2)
     * check(set.remove(2) == true)
     * check(set.remove(2) == false)
     * ```
     */
    public open fun <Q> remove(value: Q): Boolean where Q : Equivalent<T> = this.map.remove(value) != null

    /**
     * Removes and returns the value in the set, if any, that is equal to the given one.
     *
     * The value may be any borrowed form of the set's value type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the value type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: HashSet = listOf(1, 2, 3).intoIter().collect()
     * check(set.take(2) == 2)
     * check(set.take(2) == null)
     * ```
     */
    public open fun <Q> take(value: Q): T? where Q : Equivalent<T> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.map.removeEntry(value)) {
            null -> null
            else -> {
                val k = matchVal.first
                k
            }
        }
    }

    /**
     * Returns the total amount of memory allocated internally by the hash
     * set, in bytes.
     *
     * The returned number is informational only. It is intended to be
     * primarily used for memory profiling.
     */
    public open fun allocationSize(): Int = this.map.allocationSize()

    public open fun eq(other: HashSet<T, S, A>): Boolean {
        if (this.len() != other.len()) {
            return false
        }
        return this.iter().all({ key -> other.contains(key) })
    }

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "{", "}")) } }.entries(this.iter()).finish()
    }

    public open fun <I : Iterable<T>> extend(iter: I) {
        this.map.extend(iter.iterator().map({ k -> Pair(k, Unit) }))
    }

    public open fun extendOne(k: T) {
        this.map.insert(k)
    }

    public open fun extendReserve(additional: Int) {
        Extend.extendReserve(this.map, additional)
    }

    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.extend(iter.iterator().copied())
    }

    public open fun extendOneT(k: T) {
        this.map.insert(k)
    }

    public open fun extendReserveT(additional: Int) {
        Extend.extendReserve(this.map, additional)
    }

    /**
     * Returns the union of `self` and `rhs` as a new `HashSet<T, S>`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(3, 4, 5).intoIter().collect()
     * val set = a or b
     * var i = 0
     * val expected = listOf(1, 2, 3, 4, 5)
     * for (x in set) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun bitor(rhs: HashSet<T, S, A>): HashSet<T, S, A> = this.union(rhs).cloned().toMutableSet()

    /**
     * Returns the intersection of `self` and `rhs` as a new `HashSet<T, S>`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(2, 3, 4).intoIter().collect()
     * val set = a and b
     * var i = 0
     * val expected = listOf(2, 3)
     * for (x in set) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun bitand(rhs: HashSet<T, S, A>): HashSet<T, S, A> = this.intersection(rhs).cloned().toMutableSet()

    /**
     * Returns the symmetric difference of `self` and `rhs` as a new `HashSet<T, S>`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(3, 4, 5).intoIter().collect()
     * val set = a xor b
     * var i = 0
     * val expected = listOf(1, 2, 4, 5)
     * for (x in set) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun bitxor(rhs: HashSet<T, S, A>): HashSet<T, S, A> = this.symmetricDifference(rhs).cloned().toMutableSet()

    /**
     * Returns the difference of `self` and `rhs` as a new `HashSet<T, S>`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * val a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(3, 4, 5).intoIter().collect()
     * val set = a - b
     * var i = 0
     * val expected = listOf(1, 2)
     * for (x in set) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun sub(rhs: HashSet<T, S, A>): HashSet<T, S, A> = this.difference(rhs).cloned().toMutableSet()

    /**
     * Modifies this set to contain the union of `self` and `rhs`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(3, 4, 5).intoIter().collect()
     * a = a or b
     * var i = 0
     * val expected = listOf(1, 2, 3, 4, 5)
     * for (x in a) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun bitorAssign(rhs: HashSet<T, S, A>) {
        for (item in rhs) {
            if (!this.contains(item)) {
                this.add(item.clone())
            }
        }
    }

    /**
     * Modifies this set to contain the intersection of `self` and `rhs`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(2, 3, 4).intoIter().collect()
     * a = a and b
     * var i = 0
     * val expected = listOf(2, 3)
     * for (x in a) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun bitandAssign(rhs: HashSet<T, S, A>) {
        this.retain({ item -> rhs.contains(item) })
    }

    /**
     * Modifies this set to contain the symmetric difference of `self` and `rhs`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(3, 4, 5).intoIter().collect()
     * a = a xor b
     * var i = 0
     * val expected = listOf(1, 2, 4, 5)
     * for (x in a) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun bitxorAssign(rhs: HashSet<T, S, A>) {
        for (item in rhs) {
            when (val matchVal = this.map.entryRef(item)) {
                is EntryRef.Occupied -> {
                    val (entry) = matchVal
                    entry.remove()
                }
                is EntryRef.Vacant -> {
                    val (entry1) = matchVal
                    entry1.insert()
                }
            }
        }
    }

    /**
     * Modifies this set to contain the difference of `self` and `rhs`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var a: HashSet = mutableListOf(1, 2, 3).intoIter().collect()
     * val b: HashSet = mutableListOf(3, 4, 5).intoIter().collect()
     * a -= b
     * var i = 0
     * val expected = listOf(1, 2)
     * for (x in a) {
     *     check(expected.contains(x))
     *     i += 1
     * }
     * check(i == expected.len())
     * ```
     */
    public open fun subAssign(rhs: HashSet<T, S, A>) {
        if (rhs.len() < this.len()) {
            for (item in rhs) {
                this.remove(item)
            }
        } else {
            this.retain({ item1 -> !rhs.contains(item1) })
        }
    }

    public open fun intoIter(): Iter<T> = this.iter()

    /**
     * Creates a consuming iterator, that is, one that moves each value out
     * of the set in arbitrary order. The set cannot be used after calling
     * this.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set = mutableSetOf()
     * set.add("a".toString())
     * set.add("b".toString())
     * // Not possible to collect to a Vec<String> with a regular `.iter()`.
     * val v: List<String> = ArrayDeque((set).toList()).toMutableList()
     * // Will print in an arbitrary order.
     * for (x in v) {
     *     println("$x")
     * }
     * ```
     */
    public open fun intoIterIntoIterator(): IntoIter<T, A> = IntoIter(iter = this.map.intoIter())

    public companion object {
        /**
         * Creates a new empty hash set which will use the given hasher to hash
         * keys.
         *
         * The hash set is initially created with a capacity of 0, so it will not
         * allocate until it is first inserted into.
         *
         * # HashDoS resistance
         *
         * The `hashBuilder` normally use a fixed key by default and that does
         * not allow the `HashSet` to be protected against attacks such as [HashDoS].
         * Users who require HashDoS resistance should explicitly use
         * [std.hash.RandomState]
         * as the hasher when creating a [HashSet].
         *
         * The `hashBuilder` passed should implement the [BuildHasher] trait for
         * the `HashSet` to be useful, see its documentation for details.
         *
         * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
         *
         * # Examples
         *
         * ```kotlin
         * import hashbrown.DefaultHashBuilder
         * import hashbrown.HashSet
         *
         * val s = DefaultHashBuilder.default()
         * var set = HashSet.withHasher(s)
         * set.insert(2)
         * ```
         */
        public fun <T, S, A : Allocator> withHasherIn(hasher: S, alloc: A): HashSet<T, S, A> where A : Allocator = HashSet(map = HashMap.withHasherIn(hasher, alloc))

        /**
         * Creates an empty `HashSet` with the specified capacity, using
         * `hasher` to hash the keys.
         *
         * The hash set will be able to hold at least `capacity` elements without
         * reallocating. If `capacity` is 0, the hash set will not allocate.
         *
         * # HashDoS resistance
         *
         * The `hashBuilder` normally use a fixed key by default and that does
         * not allow the `HashSet` to be protected against attacks such as [HashDoS].
         * Users who require HashDoS resistance should explicitly use
         * [std.hash.RandomState]
         * as the hasher when creating a [HashSet].
         *
         * The `hashBuilder` passed should implement the [BuildHasher] trait for
         * the `HashSet` to be useful, see its documentation for details.
         *
         * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
         *
         * # Examples
         *
         * ```kotlin
         * import hashbrown.DefaultHashBuilder
         * import hashbrown.HashSet
         *
         * val s = DefaultHashBuilder.default()
         * var set = HashSet.withCapacityAndHasher(10, s)
         * set.insert(1)
         * ```
         */
        public fun <T, S, A : Allocator> withCapacityAndHasherIn(capacity: Int, hasher: S, alloc: A): HashSet<T, S, A> where A : Allocator = HashSet(map = HashMap.withCapacityAndHasherIn(capacity, hasher, alloc))

        public fun <T, S, A : Allocator> from(map: MutableMap<T, Unit>): HashSet<T, S, A> where A : Allocator = HashSet(map = map)

        public fun <T, S, A : Allocator, I : Iterable<T>> fromIter(iter: I, defaultSFn: () -> S, defaultAFn: () -> A): HashSet<T, S, A> where S : BuildHasher, A : Allocator {
            val set = HashSet.withHasherIn(Default.default(), Default.default())
            set.extend(iter)
            return set
        }

        /**
         * Creates an empty `HashSet<T, S>` with the `Default` value for the hasher.
         */
        public fun <T, S, A : Allocator> default(): HashSet<T, S, A> where A : Allocator = HashSet(map = HashMap.default())
    }
}

/**
 * Creates an empty `HashSet`.
 *
 * The hash set is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashSet` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashSet], for example with
 * [`withHasher`](HashSet.withHasher) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * val set: MutableSet<Int> = mutableSetOf()
 * ```
 */
public fun <T> HashSet.Companion.new(): HashSet<T, DefaultHashBuilder> = HashSet(map = mutableMapOf())

/**
 * Creates an empty `HashSet` with the specified capacity.
 *
 * The hash set will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash set will not allocate.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashSet` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashSet], for example with
 * [`withCapacityAndHasher`](HashSet.withCapacityAndHasher) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * val set: MutableSet<Int> = mutableSetOf()
 * check(set.capacity() >= 10)
 * ```
 */
public fun <T> HashSet.Companion.withCapacity(capacity: Int): HashSet<T, DefaultHashBuilder> = HashSet(map = mutableMapOf())

/**
 * Creates an empty `HashSet`.
 *
 * The hash set is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashSet` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashSet], for example with
 * [`withHasherIn`](HashSet.withHasherIn) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * val set: MutableSet<Int> = mutableSetOf()
 * ```
 */
public fun <T, A : Allocator> HashSet.Companion.newIn(alloc: A): HashSet<T, DefaultHashBuilder, A> = HashSet(map = HashMap.newIn(alloc))

/**
 * Creates an empty `HashSet` with the specified capacity.
 *
 * The hash set will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash set will not allocate.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashSet` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashSet], for example with
 * [`withCapacityAndHasherIn`](HashSet.withCapacityAndHasherIn) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * val set: MutableSet<Int> = mutableSetOf()
 * check(set.capacity() >= 10)
 * ```
 */
public fun <T, A : Allocator> HashSet.Companion.withCapacityIn(capacity: Int, alloc: A): HashSet<T, DefaultHashBuilder, A> = HashSet(map = HashMap.withCapacityIn(capacity, alloc))

/**
 * Creates a new empty hash set which will use the given hasher to hash
 * keys.
 *
 * The hash set is initially created with a capacity of 0, so it will not
 * allocate until it is first inserted into.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashSet` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashSet].
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashSet` to be useful, see its documentation for details.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashSet
 *
 * val s = DefaultHashBuilder.default()
 * var set = HashSet.withHasher(s)
 * set.insert(2)
 * ```
 */
public fun <T, S> HashSet.Companion.withHasher(hasher: S): HashSet<T, S, Global> = HashSet(map = HashMap.withHasher(hasher))

/**
 * Creates an empty `HashSet` with the specified capacity, using
 * `hasher` to hash the keys.
 *
 * The hash set will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash set will not allocate.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashSet` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashSet].
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashSet` to be useful, see its documentation for details.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashSet
 *
 * val s = DefaultHashBuilder.default()
 * var set = HashSet.withCapacityAndHasher(10, s)
 * set.insert(1)
 * ```
 */
public fun <T, S> HashSet.Companion.withCapacityAndHasher(capacity: Int, hasher: S): HashSet<T, S, Global> = HashSet(map = HashMap.withCapacityAndHasher(capacity, hasher))

// The default hasher is used to match the std implementation signature

/**
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashSet
 *
 * val set1 = mutableSetOf(1, 2, 3, 4)
 * val set2: HashSet = listOf(1, 2, 3, 4).into()
 * check(set1 == set2)
 * ```
 */
public fun <T, A> fromHashSetTDefaultHashBuilderA(arr: List<T>, n: Int): HashSet<T, DefaultHashBuilder, A> where A : Allocator = ArrayDeque((arr).toList()).toMutableSet()

/**
 * An iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [iter] method on [HashSet].
 * See its documentation for more.
 *
 * [iter]: HashSet.iter
 */
public open class Iter<K> internal constructor(
    iterCtorArg: Keys<K, Unit>,
) : Iterable<K> {
    public var iter: Keys<K, Unit> = iterCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Iter<K> = Iter(iter = this.iter.clone())

    public open fun next(): K? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.iter.fold(init, f)

    public open fun len(): Int = this.iter.len()

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()
    }

    public companion object {
        public operator fun <K> invoke(
            iter: Keys<K, Unit>,
        ): Iter<K> = Iter<K>(
            iterCtorArg = iter,
        )

        public fun <K> default(): Iter<K> = Iter(iter = Default.default())
    }
}

/**
 * An owning iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [intoIter] method on [HashSet]
 * (provided by the `IntoIterator` trait). See its documentation for more.
 *
 * [intoIter]: HashSet.intoIter
 */
public open class IntoIter<K, A : Allocator> internal constructor(
    iterCtorArg: IntoIter<K, Unit, A>,
) : Iterable<K> {
    public var iter: IntoIter<K, Unit, A> = iterCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): K? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.iter.next()) {
            null -> null
            else -> {
                val k = matchVal.first
                k
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.iter.fold(init, { acc, (k, _) -> f(acc, k) })

    public open fun len(): Int = this.iter.len()

    public open fun fmt(f: StringBuilder) {
        val entriesIter = this.iter.iter().map({ (k, _) -> k })
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(entriesIter).finish()
    }

    public companion object {
        public operator fun <K, A : Allocator> invoke(
            iter: IntoIter<K, Unit, A>,
        ): IntoIter<K, A> = IntoIter<K, A>(
            iterCtorArg = iter,
        )

        public fun <K, A : Allocator> default(): IntoIter<K, A> = IntoIter(iter = Default.default())
    }
}

/**
 * A draining iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [drain] method on [HashSet].
 * See its documentation for more.
 *
 * [drain]: HashSet.drain
 */
public open class Drain<K, A : Allocator> internal constructor(
    iterCtorArg: Drain<K, Unit, A>,
) : Iterable<K> {
    public var iter: Drain<K, Unit, A> = iterCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): K? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.iter.next()) {
            null -> null
            else -> {
                val k = matchVal.first
                k
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.iter.fold(init, { acc, (k, _) -> f(acc, k) })

    public open fun len(): Int = this.iter.len()

    public open fun fmt(f: StringBuilder) {
        val entriesIter = this.iter.iter().map({ (k, _) -> k })
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(entriesIter).finish()
    }

    public companion object {
        public operator fun <K, A : Allocator> invoke(
            iter: Drain<K, Unit, A>,
        ): Drain<K, A> = Drain<K, A>(
            iterCtorArg = iter,
        )
    }
}

/**
 * A draining iterator over entries of a `HashSet` which don't satisfy the predicate `f`.
 *
 * This `class` is created by the [extractIf] method on [HashSet]. See its
 * documentation for more.
 *
 * [extractIf]: HashSet.extractIf
 */
public open class ExtractIf<K, F, A : Allocator> internal constructor(
    public var f: F,
    innerCtorArg: RawExtractIf<Pair<K, Unit>, A>,
) : Iterable<K> {
    public var inner: RawExtractIf<Pair<K, Unit>, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@ExtractIf.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): K? = this.inner.next({ (k, _) -> (this.f)(k) })?.let({ (k, _) -> k })

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, this.inner.iter.sizeHint().second)

    public companion object {
        public operator fun <K, F, A : Allocator> invoke(
            f: F,
            inner: RawExtractIf<Pair<K, Unit>, A>,
        ): ExtractIf<K, F, A> = ExtractIf<K, F, A>(
            f = f,
            innerCtorArg = inner,
        )
    }
}

/**
 * A lazy iterator producing elements in the intersection of `HashSet`s.
 *
 * This `class` is created by the [intersection] method on [HashSet].
 * See its documentation for more.
 *
 * [intersection]: HashSet.intersection
 */
public open class Intersection<T, S, A : Allocator> internal constructor(
    // iterator of the first set
    public var iter: Iter<T>,
    otherCtorArg: HashSet<T, S, A>,
) : Iterable<T> {
    // the second set
    public var other: HashSet<T, S, A> = otherCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Intersection.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Intersection<T, S, A> = Intersection(this.iter, this.other).copy(iter = this.iter.clone())

    public open fun next(): T? {
        while (true) {
            val elt = (this.iter.next() ?: return null)
            if (this.other.contains(elt)) {
                return elt
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (_, upper) = this.iter.sizeHint()
        return Pair(0, upper)
    }

    public open fun <B> fold(init: B, f: (B, T) -> B): B =
        this.iter.fold(init, { acc, elt ->
            if (this.other.contains(elt)) {
                f(acc, elt)
            } else {
                acc
            }
        })

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()
    }

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            iter: Iter<T>,
            other: HashSet<T, S, A>,
        ): Intersection<T, S, A> = Intersection<T, S, A>(
            iter = iter,
            otherCtorArg = other,
        )
    }
}

/**
 * A lazy iterator producing elements in the difference of `HashSet`s.
 *
 * This `class` is created by the [difference] method on [HashSet].
 * See its documentation for more.
 *
 * [difference]: HashSet.difference
 */
public open class Difference<T, S, A : Allocator> internal constructor(
    // iterator of the first set
    public var iter: Iter<T>,
    otherCtorArg: HashSet<T, S, A>,
) : Iterable<T> {
    // the second set
    public var other: HashSet<T, S, A> = otherCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Difference.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Difference<T, S, A> = Difference(this.iter, this.other).copy(iter = this.iter.clone())

    public open fun next(): T? {
        while (true) {
            val elt = (this.iter.next() ?: return null)
            if (!this.other.contains(elt)) {
                return elt
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (lower, upper) = this.iter.sizeHint()
        return Pair(lower.saturatingSub(this.other.len()), upper)
    }

    public open fun <B> fold(init: B, f: (B, T) -> B): B =
        this.iter.fold(init, { acc, elt ->
            if (this.other.contains(elt)) {
                acc
            } else {
                f(acc, elt)
            }
        })

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()
    }

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            iter: Iter<T>,
            other: HashSet<T, S, A>,
        ): Difference<T, S, A> = Difference<T, S, A>(
            iter = iter,
            otherCtorArg = other,
        )
    }
}

/**
 * A lazy iterator producing elements in the symmetric difference of `HashSet`s.
 *
 * This `class` is created by the [symmetricDifference] method on
 * [HashSet]. See its documentation for more.
 *
 * [symmetricDifference]: HashSet.symmetricDifference
 */
public open class SymmetricDifference<T, S, A : Allocator> internal constructor(
    iterCtorArg: Chain<Difference<T, S, A>, Difference<T, S, A>>,
) : Iterable<T> {
    public var iter: Chain<Difference<T, S, A>, Difference<T, S, A>> = iterCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@SymmetricDifference.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): SymmetricDifference<T, S, A> = SymmetricDifference(iter = this.iter.clone())

    public open fun next(): T? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.iter.fold(init, f)

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()
    }

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            iter: Chain<Difference<T, S, A>, Difference<T, S, A>>,
        ): SymmetricDifference<T, S, A> = SymmetricDifference<T, S, A>(
            iterCtorArg = iter,
        )
    }
}

/**
 * A lazy iterator producing elements in the union of `HashSet`s.
 *
 * This `class` is created by the [union] method on [HashSet].
 * See its documentation for more.
 *
 * [union]: HashSet.union
 */
public open class Union<T, S, A : Allocator> internal constructor(
    iterCtorArg: Chain<Iter<T>, Difference<T, S, A>>,
) : Iterable<T> {
    public var iter: Chain<Iter<T>, Difference<T, S, A>> = iterCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Union.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Union<T, S, A> = Union(iter = this.iter.clone())

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()
    }

    public open fun next(): T? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.iter.fold(init, f)

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            iter: Chain<Iter<T>, Difference<T, S, A>>,
        ): Union<T, S, A> = Union<T, S, A>(
            iterCtorArg = iter,
        )
    }
}

/**
 * A view into a single entry in a set, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [entry] method on [HashSet].
 *
 * [entry]: HashSet.entry
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashset.Entry
 * import hashbrown.hashset.HashSet
 * import hashbrown.hashset.OccupiedEntry
 *
 * var set = mutableSetOf()
 * set.extend(listOf("a", "b", "c"))
 * check(set.size == 3)
 * // Existing value (insert)
 * val entry: Entry = set.entry("a")
 * val rawO: OccupiedEntry = entry.insert()
 * check(set.size == 3)
 * // Nonexistent value (insert)
 * set.entry("d").insert()
 * // Existing value (orInsert)
 * set.entry("b").orInsert()
 * // Nonexistent value (orInsert)
 * set.entry("e").orInsert()
 * println("Our HashSet: $set")
 * var vec: Vec = set.iter().copied().collect()
 * // The `Iter` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf("a", "b", "c", "d", "e"))
 * ```
 */
public sealed class Entry<T, S, A> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashset.Entry
     * import hashbrown.hashset.HashSet
     *
     * var set: HashSet = listOf("a", "b").into()
     * when (val matchVal = set.entry("a")) {
     *     is Entry.Vacant -> error("unreachable")
     *     is Entry.Occupied -> {}
     * }
     * ```
     */
    public class Occupied<T, S, A> internal constructor(valueCtorArg: OccupiedEntry<T, S, A>) : Entry<T, S, A>() {
        public var value: OccupiedEntry<T, S, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<T, S, A> = this.value

        public fun copy(value: OccupiedEntry<T, S, A> = this.value): Occupied<T, S, A> = Occupied<T, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T, S, A> invoke(
                value: OccupiedEntry<T, S, A>,
            ): Occupied<T, S, A> = Occupied<T, S, A>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * A vacant entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashset.Entry
     * import hashbrown.hashset.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * when (val matchVal = set.entry("a")) {
     *     is Entry.Occupied -> error("unreachable")
     *     is Entry.Vacant -> {}
     * }
     * ```
     */
    public class Vacant<T, S, A> internal constructor(valueCtorArg: VacantEntry<T, S, A>) : Entry<T, S, A>() {
        public var value: VacantEntry<T, S, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntry<T, S, A> = this.value

        public fun copy(value: VacantEntry<T, S, A> = this.value): Vacant<T, S, A> = Vacant<T, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T, S, A> invoke(
                value: VacantEntry<T, S, A>,
            ): Vacant<T, S, A> = Vacant<T, S, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder) {
        when (this) {
            is Entry.Vacant -> {
                val (v) = this
                DebugTuple(f, "Entry").field(v).finish()
            }
            is Entry.Occupied -> {
                val (o) = this
                DebugTuple(f, "Entry").field(o).finish()
            }
        }
    }

    /**
     * Sets the value of the entry, and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * val entry = set.entry("horseyland").insert()
     * check(entry.get() == "horseyland")
     * ```
     */
    public open fun insert(): OccupiedEntry<T, S, A> =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.insert()
            }
        }

    /**
     * Ensures a value is in the entry by inserting if it was vacant.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * // nonexistent key
     * set.entry("poneyland").orInsert()
     * check(set.contains("poneyland"))
     * // existing key
     * set.entry("poneyland").orInsert()
     * check(set.contains("poneyland"))
     * check(set.size == 1)
     * ```
     */
    public open fun orInsert() {
        if (this is Entry.Vacant) {
            val (entry) = this
            entry.insert()
        }
    }

    /**
     * Returns a reference to this entry's value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * set.entry("poneyland").orInsert()
     * // existing key
     * check(set.entry("poneyland").get() == "poneyland")
     * // nonexistent key
     * check(set.entry("horseland").get() == "horseland")
     * ```
     */
    public open fun get(): T =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.get()
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.get()
            }
        }
}

/**
 * A view into an occupied entry in a `HashSet`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashset.Entry
 * import hashbrown.hashset.HashSet
 * import hashbrown.hashset.OccupiedEntry
 *
 * var set = mutableSetOf()
 * set.extend(listOf("a", "b", "c"))
 * val entryO: OccupiedEntry = set.entry("a").insert()
 * check(set.size == 3)
 * // Existing key
 * when (val matchVal = set.entry("a")) {
 *     is Entry.Vacant -> error("unreachable")
 *     is Entry.Occupied -> {
 *         val (view) = matchVal
 *         check(view.get() == "a")
 *     }
 * }
 * check(set.size == 3)
 * // Existing key (take)
 * when (val matchVal1 = set.entry("c")) {
 *     is Entry.Vacant -> error("unreachable")
 *     is Entry.Occupied -> {
 *         val (view1) = matchVal1
 *         check(view1.remove() == "c")
 *     }
 * }
 * check(set.get("c") == null)
 * check(set.size == 2)
 * ```
 */
public open class OccupiedEntry<T, S, A : Allocator> internal constructor(
    innerCtorArg: OccupiedEntry<T, Unit, S, A>,
) {
    public var inner: OccupiedEntry<T, Unit, S, A> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "OccupiedEntry").field("value", this.get()).finish()
    }

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashset.Entry
     * import hashbrown.hashset.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * set.entry("poneyland").orInsert()
     * when (val matchVal = set.entry("poneyland")) {
     *     is Entry.Vacant -> error("panic")
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         check(entry.get() == "poneyland")
     *     }
     * }
     * ```
     */
    public open fun get(): T = this.inner.key()

    /**
     * Takes the value out of the entry, and returns it.
     * Keeps the allocated memory for reuse.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     * import hashbrown.hashset.Entry
     *
     * var set: MutableSet<String> = mutableSetOf()
     * // The set is empty
     * check(set.isEmpty() && set.capacity() == 0)
     * set.entry("poneyland").orInsert()
     * val capacityBeforeRemove = set.capacity()
     * when (val matchVal = set.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove() == "poneyland")
     *     }
     *     else -> {}
     * }
     * check(set.contains("poneyland") == false)
     * // Now set hold none elements but capacity is equal to the old one
     * check(set.size == 0 && set.capacity() == capacityBeforeRemove)
     * ```
     */
    public open fun remove(): T = this.inner.removeEntry().first

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            inner: OccupiedEntry<T, Unit, S, A>,
        ): OccupiedEntry<T, S, A> = OccupiedEntry<T, S, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * A view into a vacant entry in a `HashSet`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashset.Entry
 * import hashbrown.hashset.HashSet
 * import hashbrown.hashset.VacantEntry
 *
 * var set = mutableSetOf()
 * val entryV: VacantEntry = when (val matchVal = set.entry("a")) {
 *     is Entry.Vacant -> {
 *         val (view) = matchVal
 *         view
 *     }
 *     is Entry.Occupied -> error("unreachable")
 * }
 * entryV.insert()
 * check(set.contains("a") && set.size == 1)
 * // Nonexistent key (insert)
 * when (val matchVal1 = set.entry("b")) {
 *     is Entry.Vacant -> {
 *         val (view1) = matchVal1
 *         view1.insert()
 *     }
 *     is Entry.Occupied -> error("unreachable")
 * }
 * check(set.contains("b") && set.size == 2)
 * ```
 */
public open class VacantEntry<T, S, A : Allocator> internal constructor(
    innerCtorArg: VacantEntry<T, Unit, S, A>,
) {
    public var inner: VacantEntry<T, Unit, S, A> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "VacantEntry").field(this.get()).finish()
    }

    /**
     * Gets a reference to the value that would be used when inserting
     * through the `VacantEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * check(set.entry("poneyland").get() == "poneyland")
     * ```
     */
    public open fun get(): T = this.inner.key()

    /**
     * Take ownership of the value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashset.Entry
     * import hashbrown.hashset.HashSet
     *
     * var set: MutableSet<String> = mutableSetOf()
     * when (val matchVal = set.entry("poneyland")) {
     *     is Entry.Occupied -> error("panic")
     *     is Entry.Vacant -> {
     *         val (v) = matchVal
     *         check(v.intoValue() == "poneyland")
     *     }
     * }
     * ```
     */
    public open fun intoValue(): T = this.inner.intoKey()

    /**
     * Sets the value of the entry with the `VacantEntry`s value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashSet
     * import hashbrown.hashset.Entry
     *
     * var set: MutableSet<String> = mutableSetOf()
     * when (val matchVal = set.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (o) = matchVal
     *         o.insert()
     *     }
     *     else -> {}
     * }
     * check(set.contains("poneyland"))
     * ```
     */
    public open fun insert(): OccupiedEntry<T, S, A> = OccupiedEntry(inner = this.inner.insertEntry())

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            inner: VacantEntry<T, Unit, S, A>,
        ): VacantEntry<T, S, A> = VacantEntry<T, S, A>(
            innerCtorArg = inner,
        )
    }
}

public fun assertCovariance() {
    fun set(v: HashSet<String>): HashSet<String> = v
    fun iter(v: Iter<String>): Iter<String> = v
    fun <A : Allocator> intoIter(v: IntoIter<String, A>): IntoIter<String, A> = v
    fun <A : Allocator> difference(v: Difference<String, DefaultHashBuilder, A>): Difference<String, DefaultHashBuilder, A> = v
    fun <A : Allocator> symmetricDifference(v: SymmetricDifference<String, DefaultHashBuilder, A>): SymmetricDifference<String, DefaultHashBuilder, A> = v
    fun <A : Allocator> intersection(v: Intersection<String, DefaultHashBuilder, A>): Intersection<String, DefaultHashBuilder, A> = v
    fun <A : Allocator> union(v: Union<String, DefaultHashBuilder, A>): Union<String, DefaultHashBuilder, A> = v
    fun <A : Allocator> drain(d: Drain<String, A>): Drain<String, A> = d
}
