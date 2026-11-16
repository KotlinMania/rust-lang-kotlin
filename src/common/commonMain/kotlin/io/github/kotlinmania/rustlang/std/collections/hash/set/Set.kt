// port-lint: source collections/hash/set.rs
package io.github.kotlinmania.rustlang.std.collections.hash.set

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.iter.adapters.chain.Chain
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.collections.hash.map.mapTryReserveError
import io.github.kotlinmania.rustlang.std.hash.random.RandomState
import io.github.kotlinmania.rustlang.hashbrown.HashSet as Base

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
 * A [hash set] implemented as a `HashMap` where the value is `()`.
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
 * Violating this property is a logic error.
 *
 * It is also a logic error for a key to be modified in such a way that the key's
 * hash, as determined by the [Hash] trait, or its equality, as determined by
 * the [Eq] trait, changes while it is in the map. This is normally only
 * possible through [Cell], [RefCell], global state, I/O, or unchecked code.
 *
 * The behavior resulting from either logic error is not specified, but will
 * be encapsulated to the `HashSet` that observed the logic error and not
 * result in undefined behavior. This could include panics, incorrect results,
 * aborts, memory leaks, and non-termination.
 *
 * # Examples
 *
 * ```kotlin
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
 * [Eq] and [Hash]. We must also derive [PartialEq],
 * which is required if [Eq] is derived.
 *
 * ```kotlin
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
 * A `HashSet` with a known list of items can be initialized from an array:
 *
 * ```kotlin
 * val vikingNames = mutableSetOf("Einar", "Olaf", "Harald")
 * ```
 *
 * # Usage in `const` and `static`
 *
 * Like `HashMap`, `HashSet` is randomly seeded: each `HashSet` instance uses a different seed,
 * which means that `HashSet.new` cannot be used in const context. To construct a `HashSet` in the
 * initializer of a `const` or `static` item, you will have to use a different hasher that does not
 * involve a random seed, as demonstrated in the following example. **A `HashSet` constructed this
 * way is not resistant against HashDoS!**
 *
 * ```kotlin
 * import std.hash.BuildHasherDefault
 * import std.hash.DefaultHasher
 * import std.sync.Mutex
 *
 * val EMPTY_SET: MutableSet<String> = HashSet.withHasher(BuildHasherDefault.new())
 * var SET: Mutex<MutableSet<String>> = Mutex.new(HashSet.withHasher(BuildHasherDefault.new()))
 * ```
 */
public open class HashSet<T, S, A : Allocator> internal constructor(
    baseCtorArg: HashSet<T, S, A>,
) : Iterable<T> {
    public var base: HashSet<T, S, A> = baseCtorArg
        internal set

    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    /**
     * Returns the number of elements the set can hold without reallocating.
     *
     * # Examples
     *
     * ```kotlin
     * val set: MutableSet<Int> = mutableSetOf()
     * check(set.capacity() >= 100)
     * ```
     */
    public open fun capacity(): Int = this.base.capacity()

    /**
     * An iterator visiting all elements in arbitrary order.
     * The iterator element type is `T`.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.add("a")
     * set.add("b")
     * // Will print in an arbitrary order.
     * for (x in set.iter()) {
     *     println("$x")
     * }
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over set takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun iter(): Iter<T> = Iter(base = this.base.iter())

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
    public open fun len(): Int = this.base.len()

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
    public open fun isEmpty(): Boolean = this.base.isEmpty()

    /**
     * Clears the set, returning all elements as an iterator. Keeps the
     * allocated memory for reuse.
     *
     * If the returned iterator is dropped before being fully consumed, it
     * drops the remaining elements. The returned iterator keeps a mutable
     * borrow on the set to optimize its implementation.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf(1, 2, 3)
     * check(!set.isEmpty())
     * // print 1, 2, 3 in an arbitrary order
     * for (i in set.drain()) {
     *     println("$i")
     * }
     * check(set.isEmpty())
     * ```
     */
    public open fun drain(): Drain<T, A> = Drain(base = this.base.drain())

    /**
     * Creates an iterator which uses a closure to determine if an element should be removed.
     *
     * If the closure returns `true`, the element is removed from the set and
     * yielded. If the closure returns `false`, or panics, the element remains
     * in the set and will not be yielded.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use [retain] with a negated predicate if you do not need the returned iterator.
     *
     * [retain]: HashSet.retain
     *
     * # Examples
     *
     * Splitting a set into even and odd values, reusing the original set:
     *
     * ```kotlin
     * var set: MutableSet<Int> = (0 until 8).toMutableSet()
     * val extracted: MutableSet<Int> = set.extractIf({ v -> v % 2 == 0 }).toMutableSet()
     * var evens = ArrayDeque((extracted).toList()).collect<Vec>()
     * var odds = ArrayDeque((set).toList()).collect<Vec>()
     * evens.sort()
     * odds.sort()
     * check(evens == mutableListOf(0, 2, 4, 6))
     * check(odds == mutableListOf(1, 3, 5, 7))
     * ```
     */
    public open fun extractIf(pred: (T) -> Boolean): ExtractIf<T, (T) -> Boolean, A> = ExtractIf(base = this.base.extractIf(pred))

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns `false`.
     * The elements are visited in unsorted (and unspecified) order.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf(1, 2, 3, 4, 5, 6)
     * set.retain({ k -> k % 2 == 0 })
     * check(set == mutableSetOf(2, 4, 6))
     * ```
     *
     * # Performance
     *
     * In the current implementation, this operation takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun retain(f: (T) -> Boolean) {
        this.base.retain(f)
    }

    /**
     * Clears the set, removing all values.
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
        this.base.clear()
    }

    /**
     * Returns a reference to the set's [BuildHasher].
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.RandomState
     *
     * val hasher = RandomState.new()
     * val set: MutableSet<Int> = HashSet.withHasher(hasher)
     * val hasher1: RandomState = set.hasher()
     * ```
     */
    public open fun hasher(): S = this.base.hasher()

    /**
     * Reserves capacity for at least `additional` more elements to be inserted
     * in the `HashSet`. The collection may reserve more space to speculatively
     * avoid frequent reallocations. After calling `reserve`,
     * capacity will be greater than or equal to `self.len() + additional`.
     * Does nothing if capacity is already sufficient.
     *
     * # Panics
     *
     * Panics if the new allocation size overflows `Int`.
     *
     * # Examples
     *
     * ```kotlin
     * var set: MutableSet<Int> = mutableSetOf()
     * set.reserve(10)
     * check(set.capacity() >= 10)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.base.reserve(additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the `HashSet`. The collection may reserve more space to speculatively
     * avoid frequent reallocations. After calling `tryReserve`,
     * capacity will be greater than or equal to `self.len() + additional` if
     * it returns `Ok(())`.
     * Does nothing if capacity is already sufficient.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * var set: MutableSet<Int> = mutableSetOf()
     * set.tryReserve(10).expect("why is the test harness OOMing on a handful of bytes?")
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.base.tryReserve(additional).mapErr(mapTryReserveError)

    /**
     * Shrinks the capacity of the set as much as possible. It will drop
     * down as much as possible while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * # Examples
     *
     * ```kotlin
     * var set = mutableSetOf()
     * set.add(1)
     * set.add(2)
     * check(set.capacity() >= 100)
     * set.shrinkToFit()
     * check(set.capacity() >= 2)
     * ```
     */
    public open fun shrinkToFit() {
        this.base.shrinkToFit()
    }

    /**
     * Shrinks the capacity of the set with a lower limit. It will drop
     * down no lower than the supplied limit while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     * # Examples
     *
     * ```kotlin
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
        this.base.shrinkTo(minCapacity)
    }

    /**
     * Visits the values representing the difference,
     * i.e., the values that are in `self` but not in `other`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(4, 2, 3, 4)
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
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(4, 2, 3, 4)
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
     * When an equal element is present in `self` and `other`
     * then the resulting `Intersection` may yield references to
     * one or the other. This can be relevant if `T` contains fields which
     * are not compared by its `Eq` implementation, and may hold different
     * value between the two equal copies of `T` in the two sets.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(4, 2, 3, 4)
     * // Print 2, 3 in arbitrary order.
     * for (x in a.intersection(b)) {
     *     println("$x")
     * }
     * val intersection: HashSet = a.intersection(b).collect()
     * check(intersection == listOf(2, 3).iter().collect())
     * ```
     */
    public open fun intersection(other: HashSet<T, S, A>): Intersection<T, S, A> =
        if (this.len() <= other.len()) {
            Intersection(iter = this.iter(), other = other)
        } else {
            Intersection(iter = other.iter(), other = this)
        }

    /**
     * Visits the values representing the union,
     * i.e., all the values in `self` or `other`, without duplicates.
     *
     * # Examples
     *
     * ```kotlin
     * val a = mutableSetOf(1, 2, 3)
     * val b = mutableSetOf(4, 2, 3, 4)
     * // Print 1, 2, 3, 4 in arbitrary order.
     * for (x in a.union(b)) {
     *     println("$x")
     * }
     * val union: HashSet = a.union(b).collect()
     * check(union == listOf(1, 2, 3, 4).iter().collect())
     * ```
     */
    public open fun union(other: HashSet<T, S, A>): Union<T, S, A> =
        if (this.len() >= other.len()) {
            Union(iter = this.iter().chain(other.difference(this)))
        } else {
            Union(iter = other.iter().chain(this.difference(other)))
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
     * val set = mutableSetOf(1, 2, 3)
     * check(set.contains(1) == true)
     * check(set.contains(4) == false)
     * ```
     */
    public open fun <Q> contains(value: Q): Boolean = this.base.contains(value)

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
     * val set = mutableSetOf(1, 2, 3)
     * check(set.get(2) == 2)
     * check(set.get(4) == null)
     * ```
     */
    public open fun <Q> get(value: Q): T? = this.base.get(value)

    /**
     * Inserts the given `value` into the set if it is not present, then
     * returns a reference to the value in the set.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(hashSetEntry)
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
    public open fun getOrInsert(value: T): T {
        // Although the raw entry gives us `T`, we only return `T` to be consistent with
        // `get`. Key mutation is "raw" because you're not supposed to affect `Eq` or `Hash`.
        return this.base.getOrInsert(value)
    }

    /**
     * Inserts a value computed from `f` into the set if the given `value` is
     * not present, then returns a reference to the value in the set.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(hashSetEntry)
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
    public open fun <Q> getOrInsertWith(value: Q, f: (Q) -> T): T {
        // Although the raw entry gives us `T`, we only return `T` to be consistent with
        // `get`. Key mutation is "raw" because you're not supposed to affect `Eq` or `Hash`.
        return this.base.getOrInsertWith(value, f)
    }

    /**
     * Gets the given value's corresponding entry in the set for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashset.Entry.*
     *
     * @feature(hashSetEntry)
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
    public open fun entry(value: T): Entry<T, S, A> = mapEntry(this.base.entry(value))

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
    public open fun isDisjoint(other: HashSet<T, S, A>): Boolean =
        if (this.len() <= other.len()) {
            this.iter().all({ v -> !other.contains(v) })
        } else {
            other.iter().all({ v -> !this.contains(v) })
        }

    /**
     * Returns `true` if the set is a subset of another,
     * i.e., `other` contains at least all the values in `self`.
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
    public open fun isSubset(other: HashSet<T, S, A>): Boolean =
        if (this.len() <= other.len()) {
            this.iter().all({ v -> other.contains(v) })
        } else {
            false
        }

    /**
     * Returns `true` if the set is a superset of another,
     * i.e., `self` contains at least all the values in `other`.
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
    public open fun isSuperset(other: HashSet<T, S, A>): Boolean = other.isSubset(this)

    /**
     * Adds a value to the set.
     *
     * Returns whether the value was newly inserted. That is:
     *
     * - If the set did not previously contain this value, `true` is returned.
     * - If the set already contained this value, `false` is returned,
     *   and the set is not modified: original value is not replaced,
     *   and the value passed as argument is dropped.
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
    public open fun insert(value: T): Boolean = this.base.add(value)

    /**
     * Adds a value to the set, replacing the existing value, if any, that is equal to the given
     * one. Returns the replaced value.
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
    public open fun replace(value: T): T? = this.base.replace(value)

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
     * var set = mutableSetOf()
     * set.add(2)
     * check(set.remove(2) == true)
     * check(set.remove(2) == false)
     * ```
     */
    public open fun <Q> remove(value: Q): Boolean = this.base.remove(value)

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
     * var set = mutableSetOf(1, 2, 3)
     * check(set.take(2) == 2)
     * check(set.take(2) == null)
     * ```
     */
    public open fun <Q> take(value: Q): T? = this.base.take(value)

    public open fun clone(): HashSet<T, S, A> = HashSet(base = this.base.toMutableSet())

    /**
     * Overwrites the contents of `self` with a clone of the contents of `source`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible.
     */
    public open fun cloneFrom(other: HashSet<T, S, A>) {
        this.base = other.base
    }

    public open fun eq(other: HashSet<T, S, A>): Boolean {
        if (this.len() != other.len()) {
            return false
        }
        return this.iter().all({ key -> other.contains(key) })
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "{", "}")) } }.entries(this.iter()).finish()

    public open fun <I : Iterable<T>> extend(iter: I) {
        this.base.extend(iter)
    }

    public open fun extendOne(item: T) {
        this.base.add(item)
    }

    public open fun extendReserve(additional: Int) {
        this.base.extendReserve(additional)
    }

    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.extend(iter.iterator().cloned())
    }

    public open fun extendOneT(item: T) {
        this.base.add(item)
    }

    public open fun extendReserveT(additional: Int) {
        Extend.extendReserve(this, additional)
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
    public open fun intoIterIntoIterator(): IntoIter<T, A> = IntoIter(base = this.base.intoIter())

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            base: HashSet<T, S, A>,
        ): HashSet<T, S, A> = HashSet<T, S, A>(
            baseCtorArg = base,
        )

        /**
         * Creates a new empty hash set which will use the given hasher to hash
         * keys and will allocate memory using the provided allocator.
         *
         * The hash set is also created with the default initial capacity.
         *
         * Warning: `hasher` is normally randomly generated, and
         * is designed to allow `HashSet`s to be resistant to attacks that
         * cause many collisions and very poor performance. Setting it
         * manually using this function can expose a DoS attack vector.
         *
         * The `hashBuilder` passed should implement the [BuildHasher] trait for
         * the `HashSet` to be useful, see its documentation for details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.Global
         * import std.hash.RandomState
         *
         * val s = RandomState.new()
         * val set: MutableSet<Int> = HashSet.withHasherIn(s, Global)
         * ```
         */
        public fun <T, S, A : Allocator> withHasherIn(hasher: S, alloc: A): HashSet<T, S, A> = HashSet(base = Base.HashSet.withHasherIn(hasher, alloc))

        /**
         * Creates an empty `HashSet` with at least the specified capacity, using
         * `hasher` to hash the keys and `alloc` to allocate memory.
         *
         * The hash set will be able to hold at least `capacity` elements without
         * reallocating. This method is allowed to allocate for more elements than
         * `capacity`. If `capacity` is zero, the hash set will not allocate.
         *
         * Warning: `hasher` is normally randomly generated, and
         * is designed to allow `HashSet`s to be resistant to attacks that
         * cause many collisions and very poor performance. Setting it
         * manually using this function can expose a DoS attack vector.
         *
         * The `hashBuilder` passed should implement the [BuildHasher] trait for
         * the `HashSet` to be useful, see its documentation for details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.Global
         * import std.hash.RandomState
         *
         * val s = RandomState.new()
         * val set: MutableSet<Int> = HashSet.withCapacityAndHasherIn(10, s, Global)
         * ```
         */
        public fun <T, S, A : Allocator> withCapacityAndHasherIn(capacity: Int, hasher: S, alloc: A): HashSet<T, S, A> = HashSet(base = Base.HashSet.withCapacityAndHasherIn(capacity, hasher, alloc))
    }
}

/**
 * Creates an empty `HashSet`.
 *
 * The hash set is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # Examples
 *
 * ```kotlin
 * val set: MutableSet<Int> = mutableSetOf()
 * ```
 */
public fun <T> HashSet.Companion.new(): HashSet<T, RandomState, Global> = Default.default()

/**
 * Creates an empty `HashSet` with at least the specified capacity.
 *
 * The hash set will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the hash set will not allocate.
 *
 * # Examples
 *
 * ```kotlin
 * val set: MutableSet<Int> = mutableSetOf()
 * check(set.capacity() >= 10)
 * ```
 */
public fun <T> HashSet.Companion.withCapacity(capacity: Int): HashSet<T, RandomState, Global> = HashSet.withCapacityAndHasher(capacity, Default.default())

/**
 * Creates an empty `HashSet` in the provided allocator.
 *
 * The hash set is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 * # Examples
 *
 * ```kotlin
 * import std.alloc.Global
 *
 * val set: MutableSet<Int> = HashSet.newIn(Global)
 * ```
 */
public fun <T, A : Allocator> HashSet.Companion.newIn(alloc: A): HashSet<T, RandomState, A> = HashSet.withHasherIn(Default.default(), alloc)

/**
 * Creates an empty `HashSet` with at least the specified capacity.
 *
 * The hash set will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the hash set will not allocate.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.Global
 *
 * val set: MutableSet<Int> = HashSet.withCapacityIn(10, Global)
 * ```
 */
public fun <T, A : Allocator> HashSet.Companion.withCapacityIn(capacity: Int, alloc: A): HashSet<T, RandomState, A> = HashSet.withCapacityAndHasherIn(capacity, Default.default(), alloc)

/**
 * Creates a new empty hash set which will use the given hasher to hash
 * keys.
 *
 * The hash set is also created with the default initial capacity.
 *
 * Warning: `hasher` is normally randomly generated, and
 * is designed to allow `HashSet`s to be resistant to attacks that
 * cause many collisions and very poor performance. Setting it
 * manually using this function can expose a DoS attack vector.
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashSet` to be useful, see its documentation for details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.RandomState
 *
 * val s = RandomState.new()
 * var set = HashSet.withHasher(s)
 * set.insert(2)
 * ```
 */
public fun <T, S> HashSet.Companion.withHasher(hasher: S): HashSet<T, S, Global> = HashSet(base = Base.HashSet.withHasher(hasher))

/**
 * Creates an empty `HashSet` with at least the specified capacity, using
 * `hasher` to hash the keys.
 *
 * The hash set will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the hash set will not allocate.
 *
 * Warning: `hasher` is normally randomly generated, and
 * is designed to allow `HashSet`s to be resistant to attacks that
 * cause many collisions and very poor performance. Setting it
 * manually using this function can expose a DoS attack vector.
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashSet` to be useful, see its documentation for details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.RandomState
 *
 * val s = RandomState.new()
 * var set = HashSet.withCapacityAndHasher(10, s)
 * set.insert(1)
 * ```
 */
public fun <T, S> HashSet.Companion.withCapacityAndHasher(capacity: Int, hasher: S): HashSet<T, S, Global> = HashSet(base = Base.HashSet.withCapacityAndHasher(capacity, hasher))

public fun <K, V, A : Allocator> mapEntry(raw: Entry<K, V, A>): Entry<K, V, A> =
    when (raw) {
        is Entry.Occupied -> {
            val (base) = raw
            Entry.Occupied(OccupiedEntry(base = base))
        }
        is Entry.Vacant -> {
            val (base1) = raw
            Entry.Vacant(VacantEntry(base = base1))
        }
    }

public fun <T, S, I : Iterable<T>> fromIteratorFromIterHashSetTS(iter: I, defaultSFn: () -> S): HashSet<T, S, Global> where S : BuildHasher {
    val set = HashSet.withHasher(defaultSFn())
    set.extend(iter, defaultSFn)
    return set
}

// Note: as what is currently the most convenient built-in way to construct
// a HashSet, a simple usage of this function must not *require* the user
// to provide a type annotation in order to infer the third type parameter
// (the hasher parameter, conventionally "S").
// To that end, this impl is defined using RandomState as the concrete
// type of S, rather than being generic over `S: BuildHasher + Default`.
// It is expected that users who want to specify a hasher will manually use
// `withCapacityAndHasher`.
// If type parameter defaults worked on impls, and if type parameter
// defaults could be mixed with const generics, then perhaps
// this could be generalized.
// See also the equivalent impl on HashMap.

/**
 * Converts a `[T; N]` into a `HashSet<T>`.
 *
 * If the array contains any equal values,
 * all but one will be dropped.
 *
 * # Examples
 *
 * ```kotlin
 * val set1 = mutableSetOf(1, 2, 3, 4)
 * val set2: HashSet = listOf(1, 2, 3, 4).into()
 * check(set1 == set2)
 * ```
 */
public fun <T> fromHashSetTRandomState(arr: List<T>, n: Int): HashSet<T, RandomState, Global> = HashSet.fromIter(arr)

/**
 * Creates an empty `HashSet<T, S>` with the `Default` value for the hasher.
 */
public fun <T, S> defaultHashSetTS(defaultSFn: () -> S): HashSet<T, S, Global> = HashSet(base = Base.HashSet.withHasher(defaultSFn()))

/**
 * Returns the union of `self` and `rhs` as a new `HashSet<T, S>`.
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(3, 4, 5)
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
public fun <T, S> HashSet<T, S, Global>.bitor(rhs: HashSet<T, S, Global>): HashSet<T, S, Global> where S : BuildHasher = this.union(rhs).cloned().toMutableSet()

/**
 * Returns the intersection of `self` and `rhs` as a new `HashSet<T, S>`.
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(2, 3, 4)
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
public fun <T, S> HashSet<T, S, Global>.bitand(rhs: HashSet<T, S, Global>): HashSet<T, S, Global> where S : BuildHasher = this.intersection(rhs).cloned().toMutableSet()

/**
 * Returns the symmetric difference of `self` and `rhs` as a new `HashSet<T, S>`.
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(3, 4, 5)
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
public fun <T, S> HashSet<T, S, Global>.bitxor(rhs: HashSet<T, S, Global>): HashSet<T, S, Global> where S : BuildHasher = this.symmetricDifference(rhs).cloned().toMutableSet()

/**
 * Returns the difference of `self` and `rhs` as a new `HashSet<T, S>`.
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(3, 4, 5)
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
public fun <T, S> HashSet<T, S, Global>.sub(rhs: HashSet<T, S, Global>): HashSet<T, S, Global> where S : BuildHasher = this.difference(rhs).cloned().toMutableSet()

/**
 * An iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [iter] method on [HashSet].
 * See its documentation for more.
 *
 * [iter]: HashSet.iter
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * var iter = a.iter()
 * ```
 */
public open class Iter<K>(
    public var base: Iter<K>,
) : Iterable<K> {
    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Iter<K> = Iter(base = this.base.clone())

    public open fun next(): K? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun count(): Int = this.base.len()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

    public companion object {
        public fun <K> default(): Iter<K> = Iter(base = Default.default())
    }
}

/**
 * An owning iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [intoIter] method on [HashSet]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: IntoIterator.intoIter
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * var iter = ArrayDeque((a).toList())
 * ```
 */
public open class IntoIter<K, A : Allocator> internal constructor(
    baseCtorArg: IntoIter<K, A>,
) : Iterable<K> {
    public var base: IntoIter<K, A> = baseCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): K? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun count(): Int = this.base.len()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.base).toString()); Result.Ok(Unit) }

    public companion object {
        public operator fun <K, A : Allocator> invoke(
            base: IntoIter<K, A>,
        ): IntoIter<K, A> = IntoIter<K, A>(
            baseCtorArg = base,
        )
    }
}

public fun <K> defaultIntoIterK(): IntoIter<K, Global> = IntoIter(base = Default.default())

/**
 * A draining iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [drain] method on [HashSet].
 * See its documentation for more.
 *
 * [drain]: HashSet.drain
 *
 * # Examples
 *
 * ```kotlin
 * var a = mutableSetOf(1, 2, 3)
 * var drain = a.drain()
 * ```
 */
public open class Drain<K, A : Allocator> internal constructor(
    baseCtorArg: Drain<K, A>,
) : Iterable<K> {
    public var base: Drain<K, A> = baseCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): K? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.base).toString()); Result.Ok(Unit) }

    public companion object {
        public operator fun <K, A : Allocator> invoke(
            base: Drain<K, A>,
        ): Drain<K, A> = Drain<K, A>(
            baseCtorArg = base,
        )
    }
}

/**
 * A draining, filtering iterator over the items of a `HashSet`.
 *
 * This `class` is created by the [extractIf] method on [HashSet].
 *
 * [extractIf]: HashSet.extractIf
 *
 * # Examples
 *
 * ```kotlin
 * var a = mutableSetOf(1, 2, 3)
 * var extractIfed = a.extractIf({ v -> v % 2 == 0 })
 * ```
 */
public open class ExtractIf<K, F, A : Allocator> internal constructor(
    baseCtorArg: ExtractIf<K, F, A>,
) : Iterable<K> {
    public var base: ExtractIf<K, F, A> = baseCtorArg
        internal set

    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@ExtractIf.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): K? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "ExtractIf").finishNonExhaustive()

    public companion object {
        public operator fun <K, F, A : Allocator> invoke(
            base: ExtractIf<K, F, A>,
        ): ExtractIf<K, F, A> = ExtractIf<K, F, A>(
            baseCtorArg = base,
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
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(4, 2, 3, 4)
 * var intersection = a.intersection(b)
 * ```
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
        this.iter.fold(init, { acc, elt -> if (this.other.contains(elt)) {
            f(acc, elt)
        } else {
            acc
        } })

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

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
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(4, 2, 3, 4)
 * var difference = a.difference(b)
 * ```
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
        val (_, upper) = this.iter.sizeHint()
        return Pair(0, upper)
    }

    public open fun <B> fold(init: B, f: (B, T) -> B): B =
        this.iter.fold(init, { acc, elt -> if (this.other.contains(elt)) {
            acc
        } else {
            f(acc, elt)
        } })

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

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
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(4, 2, 3, 4)
 * var intersection = a.symmetricDifference(b)
 * ```
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

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

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
 *
 * # Examples
 *
 * ```kotlin
 * val a = mutableSetOf(1, 2, 3)
 * val b = mutableSetOf(4, 2, 3, 4)
 * var unionIter = a.union(b)
 * ```
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

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

    public open fun next(): T? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun count(): Int = this.iter.count()

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
 * [HashSet]: struct.HashSet.html
 * [entry]: struct.HashSet.html#method.entry
 *
 * # Examples
 *
 * ```kotlin
 * @feature(hashSetEntry)
 * public fun doctestEntry() {
 *     var set = mutableSetOf()
 *     set.extend(listOf("a", "b", "c"))
 *     check(set.size == 3)
 *     // Existing value (insert)
 *     val entry = set.entry("a")
 *     val rawO = entry.insert()
 *     check(set.size == 3)
 *     // Nonexistent value (insert)
 *     set.entry("d").insert()
 *     // Existing value (orInsert)
 *     set.entry("b").orInsert()
 *     // Nonexistent value (orInsert)
 *     set.entry("e").orInsert()
 *     println("Our HashSet: $set")
 *     var vec: Vec = set.iter().copied().collect()
 *     // The `Iter` iterator produces items in arbitrary order, so the
 *     // items must be sorted to test them against a sorted array.
 *     vec.sortUnstable()
 *     check(vec == listOf("a", "b", "c", "d", "e"))
 * }
 * ```
 */
public sealed class Entry<T, S, A : Allocator> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashset.Entry
     *
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf("a", "b")
     *     when (val matchVal = set.entry("a")) {
     *         is Entry.Vacant -> error("unreachable")
     *         is Entry.Occupied -> {}
     *     }
     * }
     * ```
     */
    public class Occupied<T, S, A : Allocator> internal constructor(valueCtorArg: OccupiedEntry<T, S, A>) : Entry<T, S, A>() {
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
            public operator fun <T, S, A : Allocator> invoke(
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
     * import std.collections.hashset.Entry
     *
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     when (val matchVal = set.entry("a")) {
     *         is Entry.Occupied -> error("unreachable")
     *         is Entry.Vacant -> {}
     *     }
     * }
     * ```
     */
    public class Vacant<T, S, A : Allocator> internal constructor(valueCtorArg: VacantEntry<T, S, A>) : Entry<T, S, A>() {
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
            public operator fun <T, S, A : Allocator> invoke(
                value: VacantEntry<T, S, A>,
            ): Vacant<T, S, A> = Vacant<T, S, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
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

    /**
     * Sets the value of the entry, and returns an OccupiedEntry.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     val entry = set.entry("horseyland").insert()
     *     check(entry.get() == "horseyland")
     * }
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
                entry1.insertEntry()
            }
        }

    /**
     * Ensures a value is in the entry by inserting if it was vacant.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     // nonexistent key
     *     set.entry("poneyland").orInsert()
     *     check(set.contains("poneyland"))
     *     // existing key
     *     set.entry("poneyland").orInsert()
     *     check(set.contains("poneyland"))
     *     check(set.size == 1)
     * }
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
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     set.entry("poneyland").orInsert()
     *     // existing key
     *     check(set.entry("poneyland").get() == "poneyland")
     *     // nonexistent key
     *     check(set.entry("horseland").get() == "horseland")
     * }
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
 * [Entry]: enum.Entry.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.collections.hashset.Entry
 *
 * @feature(hashSetEntry)
 * public fun doctestEntry() {
 *     var set = mutableSetOf()
 *     set.extend(listOf("a", "b", "c"))
 *     val entryO = set.entry("a").insert()
 *     check(set.size == 3)
 *     // Existing key
 *     when (val matchVal = set.entry("a")) {
 *         is Entry.Vacant -> error("unreachable")
 *         is Entry.Occupied -> {
 *             val (view) = matchVal
 *             check(view.get() == "a")
 *         }
 *     }
 *     check(set.size == 3)
 *     // Existing key (take)
 *     when (val matchVal1 = set.entry("c")) {
 *         is Entry.Vacant -> error("unreachable")
 *         is Entry.Occupied -> {
 *             val (view1) = matchVal1
 *             check(view1.remove() == "c")
 *         }
 *     }
 *     check(set.get("c") == null)
 *     check(set.size == 2)
 * }
 * ```
 */
public open class OccupiedEntry<T, S, A : Allocator> internal constructor(
    baseCtorArg: OccupiedEntry<T, S, A>,
) {
    public var base: OccupiedEntry<T, S, A> = baseCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "OccupiedEntry").field("value", this.get()).finish()

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashset.Entry
     *
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     set.entry("poneyland").orInsert()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Vacant -> error("panic")
     *         is Entry.Occupied -> {
     *             val (entry) = matchVal
     *             check(entry.get() == "poneyland")
     *         }
     *     }
     * }
     * ```
     */
    public open fun get(): T = this.base.get()

    /**
     * Takes the value out of the entry, and returns it.
     * Keeps the allocated memory for reuse.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashset.Entry
     *
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     // The set is empty
     *     check(set.isEmpty() && set.capacity() == 0)
     *     set.entry("poneyland").orInsert()
     *     val capacityBeforeRemove = set.capacity()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Occupied -> {
     *             val (o) = matchVal
     *             check(o.remove() == "poneyland")
     *         }
     *         else -> {}
     *     }
     *     check(set.contains("poneyland") == false)
     *     // Now set hold none elements but capacity is equal to the old one
     *     check(set.size == 0 && set.capacity() == capacityBeforeRemove)
     * }
     * ```
     */
    public open fun remove(): T = this.base.remove()

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            base: OccupiedEntry<T, S, A>,
        ): OccupiedEntry<T, S, A> = OccupiedEntry<T, S, A>(
            baseCtorArg = base,
        )
    }
}

/**
 * A view into a vacant entry in a `HashSet`.
 * It is part of the [Entry] enum.
 *
 * [Entry]: enum.Entry.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.collections.hashset.Entry
 *
 * @feature(hashSetEntry)
 * public fun doctestEntry() {
 *     var set = mutableSetOf()
 *     val entryV = when (val matchVal = set.entry("a")) {
 *         is Entry.Vacant -> {
 *             val (view) = matchVal
 *             view
 *         }
 *         is Entry.Occupied -> error("unreachable")
 *     }
 *     entryV.insert()
 *     check(set.contains("a") && set.size == 1)
 *     // Nonexistent key (insert)
 *     when (val matchVal1 = set.entry("b")) {
 *         is Entry.Vacant -> {
 *             val (view1) = matchVal1
 *             view1.insert()
 *         }
 *         is Entry.Occupied -> error("unreachable")
 *     }
 *     check(set.contains("b") && set.size == 2)
 * }
 * ```
 */
public open class VacantEntry<T, S, A : Allocator> internal constructor(
    baseCtorArg: VacantEntry<T, S, A>,
) {
    public var base: VacantEntry<T, S, A> = baseCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "VacantEntry").field(this.get()).finish()

    /**
     * Gets a reference to the value that would be used when inserting
     * through the `VacantEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     check(set.entry("poneyland").get() == "poneyland")
     * }
     * ```
     */
    public open fun get(): T = this.base.get()

    /**
     * Take ownership of the value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashset.Entry
     *
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Occupied -> error("panic")
     *         is Entry.Vacant -> {
     *             val (v) = matchVal
     *             check(v.intoValue() == "poneyland")
     *         }
     *     }
     * }
     * ```
     */
    public open fun intoValue(): T = this.base.intoValue()

    /**
     * Sets the value of the entry with the VacantEntry's value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashset.Entry
     *
     * @feature(hashSetEntry)
     * public fun doctestEntry() {
     *     var set = mutableSetOf()
     *     when (val matchVal = set.entry("poneyland")) {
     *         is Entry.Vacant -> {
     *             val (o) = matchVal
     *             o.insert()
     *         }
     *         else -> {}
     *     }
     *     check(set.contains("poneyland"))
     * }
     * ```
     */
    public open fun insert() {
        this.base.insert()
    }

    public open fun insertEntry(): OccupiedEntry<T, S, A> = OccupiedEntry(base = this.base.insert())

    public companion object {
        public operator fun <T, S, A : Allocator> invoke(
            base: VacantEntry<T, S, A>,
        ): VacantEntry<T, S, A> = VacantEntry<T, S, A>(
            baseCtorArg = base,
        )
    }
}

public fun assertCovariance() {
    fun set(v: HashSet<String, RandomState, Global>): HashSet<String, RandomState, Global> = v
    fun iter(v: Iter<String>): Iter<String> = v
    fun intoIter(v: IntoIter<String, Global>): IntoIter<String, Global> = v
    fun difference(v: Difference<String, RandomState, Global>): Difference<String, RandomState, Global> = v
    fun symmetricDifference(v: SymmetricDifference<String, RandomState, Global>): SymmetricDifference<String, RandomState, Global> = v
    fun intersection(v: Intersection<String, RandomState, Global>): Intersection<String, RandomState, Global> = v
    fun union(v: Union<String, RandomState, Global>): Union<String, RandomState, Global> = v
    fun drain(d: Drain<String, Global>): Drain<String, Global> = d
}
