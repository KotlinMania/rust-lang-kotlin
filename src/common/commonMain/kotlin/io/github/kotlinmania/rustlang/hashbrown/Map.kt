// port-lint: source map.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Allocator
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Global
import io.github.kotlinmania.rustlang.hashbrown.hasher.DefaultHashBuilder
import io.github.kotlinmania.rustlang.hashbrown.raw.Bucket
import io.github.kotlinmania.rustlang.hashbrown.raw.RawDrain
import io.github.kotlinmania.rustlang.hashbrown.raw.RawExtractIf
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIntoIter
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIter
import io.github.kotlinmania.rustlang.hashbrown.raw.RawTable

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
 * A hash map implemented with quadratic probing and SIMD lookup.
 *
 * The default hashing algorithm is currently [foldhash], though this is
 * subject to change at any point in the future. This hash function is very
 * fast for all types of keys, but this algorithm will typically *not* protect
 * against attacks such as HashDoS.
 *
 * The hashing algorithm can be replaced on a per-`HashMap` basis using the
 * [default], [withHasher], and [withCapacityAndHasher] methods. Many
 * alternative algorithms are available on crates.io, such as the [fnv] crate.
 *
 * It is required that the keys implement the [Eq] and [Hash] traits, although
 * this can frequently be achieved by using `@derive(PartialEq, Eq, Hash)`.
 * If you implement these yourself, it is important that the following
 * property holds:
 *
 * ```text
 * k1 == k2 -> hash(k1) == hash(k2)
 * ```
 *
 * In other words, if two keys are equal, their hashes must be equal.
 *
 * It is a logic error for a key to be modified in such a way that the key's
 * hash, as determined by the [Hash] trait, or its equality, as determined by
 * the [Eq] trait, changes while it is in the map. This is normally only
 * possible through [Cell], [RefCell], global state, I/O, or unchecked code.
 *
 * It is also a logic error for the [Hash] implementation of a key to panic.
 * This is generally only possible if the trait is implemented manually. If a
 * panic does occur then the contents of the `HashMap` may become corrupted and
 * some items may be dropped from the table.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * // Type inference lets us omit an explicit type signature (which
 * // would be `HashMap<String, String>` in this example).
 * var bookReviews = mutableMapOf()
 * // Review some books.
 * bookReviews.put("Adventures of Huckleberry Finn".toString(), "My favorite book.".toString())
 * bookReviews.put("Grimms' Fairy Tales".toString(), "Masterpiece.".toString())
 * bookReviews.put("Pride and Prejudice".toString(), "Very enjoyable.".toString())
 * bookReviews.put("The Adventures of Sherlock Holmes".toString(), "Eye lyked it alot.".toString())
 * // Check for a specific one.
 * // When collections store owned values (String), they can still be
 * // queried using references (&str).
 * if (!bookReviews.containsKey("Les Misérables")) {
 *     println("We've got ${bookReviews.size} reviews, but Les Misérables ain't one.")
 * }
 * // oops, this review has a lot of spelling mistakes, let's delete it.
 * bookReviews.remove("The Adventures of Sherlock Holmes")
 * // Look up the values associated with some keys.
 * val toFind = listOf("Pride and Prejudice", "Alice's Adventure in Wonderland")
 * for (book in toFind) {
 *     when (val matchVal = bookReviews.get(book)) {
 *         null -> println("$book is unreviewed.")
 *         else -> {
 *             val review = matchVal
 *             println("$book: $review")
 *         }
 *     }
 * }
 * // Look up the value for a key (will panic if the key is not found).
 * println("Review for Jane: " + (bookReviews["Pride and Prejudice"]).toString())
 * // Iterate over everything.
 * for ((book1, review1) in bookReviews) {
 *     println("$book1: \"$review1\"")
 * }
 * ```
 *
 * `HashMap` also implements an [`Entry.Api`](#method.entry), which allows
 * for more complex methods of getting, setting, updating and removing keys and
 * their values:
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * fun randomStatBuff(): UByte {
 *     // could actually return some random value here - let's just return
 *     // some fixed value for now
 *     return 42u
 * }
 * // type inference lets us omit an explicit type signature (which
 * // would be `HashMap<String, UByte>` in this example).
 * var playerStats = mutableMapOf()
 * // insert a key only if it doesn't already exist
 * playerStats.entry("health").orInsert(100)
 * // insert a key using a function that provides a new value only if it
 * // doesn't already exist
 * playerStats.entry("defence").orInsertWith(::randomStatBuff)
 * // update a key, guarding against the key possibly not being set
 * val stat = playerStats.entry("attack").orInsert(100)
 * stat += randomStatBuff()
 * ```
 *
 * The easiest way to use `HashMap` with a custom key type is to derive [Eq] and [Hash].
 * We must also derive [PartialEq].
 *
 * [default]: Default.default
 * [withHasher]: HashMap.withHasher
 * [withCapacityAndHasher]: HashMap.withCapacityAndHasher
 * [fnv]: https://crates.io/crates/fnv
 * [foldhash]: https://crates.io/crates/foldhash
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * open class Viking(public var name: String, public var country: String) {
 *     / **
 *      * Creates a new Viking.
 *      * /
 *     public open fun new(name: String, country: String): Viking {
 *         return Viking(name = name.toString(), country = country.toString())
 *     }
 * }
 * // Use a HashMap to store the vikings' health points.
 * var vikings = mutableMapOf()
 * vikings.put(Viking.new("Einar", "Norway"), 25)
 * vikings.put(Viking.new("Olaf", "Denmark"), 24)
 * vikings.put(Viking.new("Harald", "Iceland"), 12)
 * // Use derived implementation to print the status of the vikings.
 * for ((viking, health) in vikings) {
 *     println("$viking has $health hp")
 * }
 * ```
 *
 * A `HashMap` with fixed list of elements can be initialized from an array:
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val timberResources: MutableMap<String, Int> = listOf(Pair("Norway", 100), Pair("Denmark", 50), Pair("Iceland", 10)).intoIter().toMap(LinkedHashMap())
 * // use the values stored in map
 * ```
 */
public open class HashMap<K, V, S, A : Allocator> internal constructor(
    public var hashBuilder: S,
    tableCtorArg: RawTable<Pair<K, V>, A>,
) : Iterable<Pair<K, V>> {
    public var table: RawTable<Pair<K, V>, A> = tableCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = this.intoIter().iterator()

    public open fun clone(): HashMap<K, V, S, A> = HashMap(hashBuilder = this.hashBuilder.clone(), table = this.table.clone())

    public open fun cloneFrom(source: HashMap<K, V, S, A>) {
        this.table = source.table
        // Update hashBuilder only if we successfully cloned all elements.
        this.hashBuilder = source.hashBuilder
    }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.table.allocator()

    /**
     * Returns a reference to the map's [BuildHasher].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashMap
     *
     * val hasher = DefaultHashBuilder.default()
     * val map: MutableMap<Int, Int> = HashMap.withHasher(hasher)
     * val hasher1: DefaultHashBuilder = map.hasher()
     * ```
     */
    public open fun hasher(): S = this.hashBuilder

    /**
     * Returns the number of elements the map can hold without reallocating.
     *
     * This number is a lower bound; the `HashMap<K, V>` might be able to hold
     * more, but is guaranteed to be able to hold at least this many.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * val map: MutableMap<Int, Int> = mutableMapOf()
     * check(map.size == 0)
     * check(map.capacity() >= 100)
     * ```
     */
    public open fun capacity(): Int = this.table.capacity()

    /**
     * An iterator visiting all keys in arbitrary order.
     * The iterator element type is `K`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * check(map.size == 3)
     * var vec: List<String> = mutableListOf()
     * for (key in map.keys()) {
     *     println("$key")
     *     vec.add(key)
     * }
     * // The `Keys` iterator produces keys in arbitrary order, so the
     * // keys must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf("a", "b", "c"))
     * check(map.size == 3)
     * ```
     */
    public open fun keys(): Keys<K, V> = Keys(inner = this.iter())

    /**
     * An iterator visiting all values in arbitrary order.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * check(map.size == 3)
     * var vec: List<Int> = mutableListOf()
     * for (value in map.values()) {
     *     println("$value")
     *     vec.add(value)
     * }
     * // The `Values` iterator produces values in arbitrary order, so the
     * // values must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(1, 2, 3))
     * check(map.size == 3)
     * ```
     */
    public open fun values(): Values<K, V> = Values(inner = this.iter())

    /**
     * An iterator visiting all values mutably in arbitrary order.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * for (value in map.valuesMut()) {
     *     value = value + 10
     * }
     * check(map.size == 3)
     * var vec: List<Int> = mutableListOf()
     * for (value1 in map.values()) {
     *     println("$value1")
     *     vec.add(value1)
     * }
     * // The `Values` iterator produces values in arbitrary order, so the
     * // values must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(11, 12, 13))
     * check(map.size == 3)
     * ```
     */
    public open fun valuesMut(): ValuesMut<K, V> = ValuesMut(inner = this.iterMut())

    /**
     * An iterator visiting all key-value pairs in arbitrary order.
     * The iterator element type is `(K, V)`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * check(map.size == 3)
     * var vec: List<Pair<String, Int>> = mutableListOf()
     * for ((key, value) in map.iter()) {
     *     println("key: $key val: $value")
     *     vec.add(Pair(key, value))
     * }
     * // The `Iter` iterator produces items in arbitrary order, so the
     * // items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3)))
     * check(map.size == 3)
     * ```
     */
    public open fun iter(): Iter<K, V> {
        // Here we tie the scope of self to the iter.
        return Iter(inner = this.table.iter(), marker = Unit)
    }

    /**
     * An iterator visiting all key-value pairs in arbitrary order,
     * with mutable references to the values.
     * The iterator element type is `(K, V)`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * // Update all values
     * for ((_, value) in map.iterMut()) {
     *     value *= 2
     * }
     * check(map.size == 3)
     * var vec: List<Pair<String, Int>> = mutableListOf()
     * for ((key, value1) in map) {
     *     println("key: $key val: $value1")
     *     vec.add(Pair(key, value1))
     * }
     * // The `Iter` iterator produces items in arbitrary order, so the
     * // items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(Pair("a", 2), Pair("b", 4), Pair("c", 6)))
     * check(map.size == 3)
     * ```
     */
    public open fun iterMut(): IterMut<K, V> {
        // Here we tie the scope of self to the iter.
        return IterMut(inner = this.table.iter(), marker = Unit)
    }

    /**
     * Returns the number of elements in the map.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var a = mutableMapOf()
     * check(a.size == 0)
     * a.put(1, "a")
     * check(a.size == 1)
     * ```
     */
    public open fun len(): Int = this.table.len()

    /**
     * Returns `true` if the map contains no elements.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var a = mutableMapOf()
     * check(a.isEmpty())
     * a.put(1, "a")
     * check(!a.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Clears the map, returning all key-value pairs as an iterator. Keeps the
     * allocated memory for reuse.
     *
     * If the returned iterator is dropped before being fully consumed, it
     * drops the remaining key-value pairs. The returned iterator keeps a
     * mutable borrow on the vector to optimize its implementation.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var a = mutableMapOf()
     * a.put(1, "a")
     * a.put(2, "b")
     * val capacityBeforeDrain = a.capacity()
     * for ((k, v) in a.drain().take(1)) {
     *     check(k == 1 || k == 2)
     *     check(v == "a" || v == "b")
     * }
     * // As we can see, the map is empty and contains no element.
     * check(a.isEmpty() && a.size == 0)
     * // But map capacity is equal to old one.
     * check(a.capacity() == capacityBeforeDrain)
     * var a1 = mutableMapOf()
     * a1.put(1, "a")
     * a1.put(2, "b")
     * run {
     *     // Iterator is dropped without being consumed.
     *     val d = a1.drain()
     * }
     * // But the map is empty even if we do not use Drain iterator.
     * check(a1.isEmpty())
     * ```
     */
    public open fun drain(): Drain<K, V, A> = Drain(inner = this.table.drain())

    /**
     * Retains only the elements specified by the predicate. Keeps the
     * allocated memory for reuse.
     *
     * In other words, remove all pairs `(k, v)` such that `f(k, v)` returns `false`.
     * The elements are visited in unsorted (and unspecified) order.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x * 10) }).toMap(LinkedHashMap())
     * check(map.size == 8)
     * map.retain({ k -> k % 2 == 0 })
     * // We can see, that the number of elements inside map is changed.
     * check(map.size == 4)
     * var vec: List<Pair<Int, Int>> = map.iter().map({ (k, v) -> Pair(k, v) }).toMutableList()
     * vec.sortUnstable()
     * check(vec == listOf(Pair(0, 0), Pair(2, 20), Pair(4, 40), Pair(6, 60)))
     * ```
     */
    public open fun retain(f: (K, V) -> Boolean) {
        // Here we only use `iter` as a temporary, preventing use-after-free
        for (item in this.table.iter()) {
            var (key, value) = item.asMut()
            if (!f(key, value)) {
                this.table.erase(item)
            }
        }
    }

    /**
     * Drains elements which are true under the given predicate,
     * and returns an iterator over the removed items.
     *
     * In other words, move all pairs `(k, v)` such that `f(k, v)` returns `true` out
     * into another iterator.
     *
     * Note that `extractIf` lets you mutate every value in the filter closure, regardless of
     * whether you choose to keep or remove it.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use [retain] with a negated predicate if you do not need the returned iterator.
     *
     * Keeps the allocated memory for reuse.
     *
     * [retain]: HashMap.retain
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x) }).toMap(LinkedHashMap())
     * val drained: MutableMap<Int, Int> = map.extractIf({ k, v -> k % 2 == 0 }).toMap(LinkedHashMap())
     * var evens = drained.keys().cloned().collect<Vec>()
     * var odds = map.keys().cloned().collect<Vec>()
     * evens.sort()
     * odds.sort()
     * check(evens == mutableListOf(0, 2, 4, 6))
     * check(odds == mutableListOf(1, 3, 5, 7))
     * var map1: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x) }).toMap(LinkedHashMap())
     * run {
     *     // Iterator is dropped without being consumed.
     *     val d = map1.extractIf({ k, v -> k % 2 != 0 })
     * }
     * // ExtractIf was not exhausted, therefore no elements were drained.
     * check(map1.size == 8)
     * ```
     */
    public open fun extractIf(f: (K, V) -> Boolean): ExtractIf<K, V, (K, V) -> Boolean, A> = ExtractIf(f = f, inner = RawExtractIf(iter = this.table.iter(), table = this.table))

    /**
     * Clears the map, removing all key-value pairs. Keeps the allocated memory
     * for reuse.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var a = mutableMapOf()
     * a.put(1, "a")
     * val capacityBeforeClear = a.capacity()
     * a.clear()
     * // Map is empty.
     * check(a.isEmpty())
     * // But map capacity is equal to old one.
     * check(a.capacity() == capacityBeforeClear)
     * ```
     */
    public open fun clear() {
        this.table.clear()
    }

    /**
     * Creates a consuming iterator visiting all the keys in arbitrary order.
     * The map cannot be used after calling this.
     * The iterator element type is `K`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * var vec: List<String> = map.intoKeys().toMutableList()
     * // The `IntoKeys` iterator produces keys in arbitrary order, so the
     * // keys must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf("a", "b", "c"))
     * ```
     */
    public open fun intoKeys(): IntoKeys<K, V, A> = IntoKeys(inner = this.intoIter())

    /**
     * Creates a consuming iterator visiting all the values in arbitrary order.
     * The map cannot be used after calling this.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put("a", 1)
     * map.put("b", 2)
     * map.put("c", 3)
     * var vec: List<Int> = map.intoValues().toMutableList()
     * // The `IntoValues` iterator produces values in arbitrary order, so
     * // the values must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(1, 2, 3))
     * ```
     */
    public open fun intoValues(): IntoValues<K, V, A> = IntoValues(inner = this.intoIter())

    /**
     * Reserves capacity for at least `additional` more elements to be inserted
     * in the `HashMap`. The collection may reserve more space to avoid
     * frequent reallocations.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds [isize.Max] bytes and [abort] the program
     * in case of allocation error. Use [`tryReserve`](HashMap.tryReserve) instead
     * if you want to handle memory allocation failure.
     *
     * [abort]: stdalloc.alloc.handleAllocError
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * // Map is empty and doesn't allocate memory
     * check(map.capacity() == 0)
     * map.reserve(10)
     * // And now map can hold at least 10 elements
     * check(map.capacity() >= 10)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.table.reserve(additional, makeHasher<V, S>(this.hashBuilder))
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the given `HashMap<K, V>`. The collection may reserve more space to avoid
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
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * // Map is empty and doesn't allocate memory
     * check(map.capacity() == 0)
     * map.tryReserve(10).expect("why is the test harness OOMing on 10 bytes?")
     * // And now map can hold at least 10 elements
     * check(map.capacity() >= 10)
     * ```
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned:
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.TryReserveError
     *
     * var map: MutableMap<Int, Int> = mutableMapOf()
     * when (val matchVal = map.tryReserve(Int.MAX_VALUE)) {
     *     is Err -> {
     *         val (error) = matchVal
     *         when (error) {
     *             TryReserveError.CapacityOverflow -> {}
     *             else -> error("TryReserveError.AllocError ?")
     *         }
     *     }
     *     else -> error("panic")
     * }
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.table.tryReserve(additional, makeHasher<V, S>(this.hashBuilder))

    /**
     * Shrinks the capacity of the map as much as possible. It will drop
     * down as much as possible while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<Int, Int> = mutableMapOf()
     * map.put(1, 2)
     * map.put(3, 4)
     * check(map.capacity() >= 100)
     * map.shrinkToFit()
     * check(map.capacity() >= 2)
     * ```
     */
    public open fun shrinkToFit() {
        this.table.shrinkTo(0, makeHasher<V, S>(this.hashBuilder))
    }

    /**
     * Shrinks the capacity of the map with a lower limit. It will drop
     * down no lower than the supplied limit while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * This function does nothing if the current capacity is smaller than the
     * supplied minimum capacity.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<Int, Int> = mutableMapOf()
     * map.put(1, 2)
     * map.put(3, 4)
     * check(map.capacity() >= 100)
     * map.shrinkTo(10)
     * check(map.capacity() >= 10)
     * map.shrinkTo(0)
     * check(map.capacity() >= 2)
     * map.shrinkTo(10)
     * check(map.capacity() >= 2)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.table.shrinkTo(minCapacity, makeHasher<V, S>(this.hashBuilder))
    }

    /**
     * Gets the given key's corresponding entry in the map for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var letters = mutableMapOf()
     * for (ch in "a short treatise on fungi".chars()) {
     *     val counter = letters.entry(ch).orInsert(0)
     *     counter += 1
     * }
     * check(letters['s'] == 2)
     * check(letters['t'] == 3)
     * check(letters['u'] == 1)
     * check(letters.get('y') == null)
     * ```
     */
    public open fun entry(key: K): Entry<K, V, S, A> {
        val hash = makeHash<K, S>(this.hashBuilder, key)
        return when (val matchVal = this.table.find(hash, equivalentKey(key))) {
            null -> {
                Entry.Vacant(VacantEntry(hash = hash, key = key, table = this))
            }
            else -> {
                val elem = matchVal
                Entry.Occupied(OccupiedEntry(hash = hash, elem = elem, table = this))
            }
        }
    }

    /**
     * Gets the given key's corresponding entry by reference in the map for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var words: MutableMap<String, Int> = mutableMapOf()
     * val source = listOf("poneyland", "horseyland", "poneyland", "poneyland")
     * for ((i, s) in source.iter().mapIndexed { index, item -> Pair(index, item) }) {
     *     val counter = words.entryRef(s).orInsert(0)
     *     counter += 1
     * }
     * check(words["poneyland"] == 3)
     * check(words["horseyland"] == 1)
     * ```
     */
    public open fun <Q> entryRef(key: Q): EntryRef<K, Q, V, S, A> where Q : Equivalent<K> {
        val hash = makeHash<Q, S>(this.hashBuilder, key)
        return when (val matchVal = this.table.find(hash, equivalentKey(key))) {
            null -> {
                EntryRef.Vacant(VacantEntryRef(hash = hash, key = key, table = this))
            }
            else -> {
                val elem = matchVal
                EntryRef.Occupied(OccupiedEntry(hash = hash, elem = elem, table = this))
            }
        }
    }

    /**
     * Returns a reference to the value corresponding to the key.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.get(1) == "a")
     * check(map.get(2) == null)
     * ```
     */
    public open fun <Q> get(k: Q): V? where Q : Equivalent<K> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return if (this.table.isEmpty()) {
            null
        } else {
            val hash = makeHash<Q, S>(this.hashBuilder, k)
            when (val matchVal = this.table.get(hash, equivalentKey(k))) {
                null -> null
                else -> {
                    val v = matchVal.second
                    v
                }
            }
        }
    }

    /**
     * Returns the key-value pair corresponding to the supplied key.
     *
     * The supplied key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.getKeyValue(1) == Pair(1, "a"))
     * check(map.getKeyValue(2) == null)
     * ```
     */
    public open fun <Q> getKeyValue(k: Q): Pair<K, V>? where Q : Equivalent<K> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return if (this.table.isEmpty()) {
            null
        } else {
            val hash = makeHash<Q, S>(this.hashBuilder, k)
            when (val matchVal = this.table.get(hash, equivalentKey(k))) {
                null -> null
                else -> {
                    val key = matchVal.first
                    val value = matchVal.second
                    Pair(key, value)
                }
            }
        }
    }

    /**
     * Returns the key-value pair corresponding to the supplied key, with a mutable reference to value.
     *
     * The supplied key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, "a")
     * val (k, v) = map.getKeyValueMut(1).unwrap()
     * check(k == 1)
     * check(v == "a")
     * v = "b"
     * check(map.getKeyValueMut(1) == Pair(1, "b"))
     * check(map.getKeyValueMut(2) == null)
     * ```
     */
    public open fun <Q> getKeyValueMut(k: Q): Pair<K, V>? where Q : Equivalent<K> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return if (this.table.isEmpty()) {
            null
        } else {
            val hash = makeHash<Q, S>(this.hashBuilder, k)
            when (val matchVal = this.table.getMut(hash, equivalentKey(k))) {
                null -> null
                else -> {
                    val key = matchVal.first
                    val value = matchVal.second
                    Pair(key, value)
                }
            }
        }
    }

    /**
     * Returns `true` if the map contains a value for the specified key.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.containsKey(1) == true)
     * check(map.containsKey(2) == false)
     * ```
     */
    public open fun <Q> containsKey(k: Q): Boolean where Q : Equivalent<K> =
        if (this.table.isEmpty()) {
            false
        } else {
            val hash = makeHash<Q, S>(this.hashBuilder, k)
            this.table.get(hash, equivalentKey(k)) != null
        }

    /**
     * Returns a mutable reference to the value corresponding to the key.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
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
     * check(map.get(2) == null)
     * ```
     */
    public open fun <Q> getMut(k: Q): V? where Q : Equivalent<K> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return if (this.table.isEmpty()) {
            null
        } else {
            val hash = makeHash<Q, S>(this.hashBuilder, k)
            when (val matchVal = this.table.getMut(hash, equivalentKey(k))) {
                null -> null
                else -> {
                    val v = matchVal.second
                    v
                }
            }
        }
    }

    /**
     * Attempts to get mutable references to `N` values in the map at once.
     *
     * Returns an array of length `N` with the results of each query. For soundness, at most one
     * mutable reference will be returned to any value. `None` will be used if the key is missing.
     *
     * # Panics
     *
     * Panics if any keys are overlapping.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var libraries = mutableMapOf()
     * libraries.put("Bodleian Library".toString(), 1602)
     * libraries.put("Athenæum".toString(), 1807)
     * libraries.put("Herzogin-Anna-Amalia-Bibliothek".toString(), 1691)
     * libraries.put("Library of Congress".toString(), 1800)
     * // Get Athenæum and Bodleian Library
     * val (a, b) = libraries.getDisjointMut(listOf("Athenæum", "Bodleian Library")) ?: error("panic")
     * // Assert values of Athenæum and Library of Congress
     * val got = libraries.getDisjointMut(listOf("Athenæum", "Library of Congress"))
     * check(got == listOf(RustMutableReference(allocateNativeCell(1807, PrimitiveLayouts.I32), PrimitiveLayouts.I32), RustMutableReference(allocateNativeCell(1800, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     * // Missing keys result in None
     * val got1 = libraries.getDisjointMut(listOf("Athenæum", "New York Public Library"))
     * check(got1 == listOf(RustMutableReference(allocateNativeCell(1807, PrimitiveLayouts.I32), PrimitiveLayouts.I32), null))
     * ```
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var libraries = mutableMapOf()
     * libraries.put("Athenæum".toString(), 1807)
     * // Duplicate keys panic
     * val got = libraries.getDisjointMut(listOf("Athenæum", "Athenæum"))
     * ```
     */
    public open fun <Q> getDisjointMut(ks: List<Q>, n: Int): List<V> where Q : Equivalent<K> = this.getDisjointMutInner(ks, n).map({ res -> res.map({ (_, v) -> v }) })

    /**
     * Attempts to get mutable references to `N` values in the map at once.
     */
    public open fun <Q> getManyMut(ks: List<Q>, n: Int): List<V> where Q : Equivalent<K> = this.getDisjointMut(ks, n)

    /**
     * Attempts to get mutable references to `N` values in the map at once, without validating that
     * the values are unique.
     *
     * Returns an array of length `N` with the results of each query. `None` will be used if
     * the key is missing.
     *
     * For a safe alternative see [`getDisjointMut`](`HashMap.getDisjointMut`).
     *
     * # Safety
     *
     * Calling this method with overlapping keys is *[undefined behavior]* even if the resulting
     * references are not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var libraries = mutableMapOf()
     * libraries.put("Bodleian Library".toString(), 1602)
     * libraries.put("Athenæum".toString(), 1807)
     * libraries.put("Herzogin-Anna-Amalia-Bibliothek".toString(), 1691)
     * libraries.put("Library of Congress".toString(), 1800)
     * // SAFETY: The keys do not overlap.
     * val (a, b) = (libraries.getDisjointUncheckedMut(listOf("Athenæum", "Bodleian Library"))) ?: error("panic")
     * // SAFETY: The keys do not overlap.
     * val got = libraries.getDisjointUncheckedMut(listOf("Athenæum", "Library of Congress"))
     * check(got == listOf(RustMutableReference(allocateNativeCell(1807, PrimitiveLayouts.I32), PrimitiveLayouts.I32), RustMutableReference(allocateNativeCell(1800, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     * // SAFETY: The keys do not overlap.
     * val got1 = libraries.getDisjointUncheckedMut(listOf("Athenæum", "New York Public Library"))
     * // Missing keys result in None
     * check(got1 == listOf(RustMutableReference(allocateNativeCell(1807, PrimitiveLayouts.I32), PrimitiveLayouts.I32), null))
     * ```
     */
    public open fun <Q> getDisjointUncheckedMut(ks: List<Q>, n: Int): List<V> where Q : Equivalent<K> = this.getDisjointUncheckedMutInner(ks, n).map({ res -> res.map({ (_, v) -> v }) })

    /**
     * Attempts to get mutable references to `N` values in the map at once, without validating that
     * the values are unique.
     */
    public open fun <Q> getManyUncheckedMut(ks: List<Q>, n: Int): List<V> where Q : Equivalent<K> = this.getDisjointUncheckedMut(ks, n)

    /**
     * Attempts to get mutable references to `N` values in the map at once, with immutable
     * references to the corresponding keys.
     *
     * Returns an array of length `N` with the results of each query. For soundness, at most one
     * mutable reference will be returned to any value. `None` will be used if the key is missing.
     *
     * # Panics
     *
     * Panics if any keys are overlapping.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var libraries = mutableMapOf()
     * libraries.put("Bodleian Library".toString(), 1602)
     * libraries.put("Athenæum".toString(), 1807)
     * libraries.put("Herzogin-Anna-Amalia-Bibliothek".toString(), 1691)
     * libraries.put("Library of Congress".toString(), 1800)
     * val got = libraries.getDisjointKeyValueMut(listOf("Bodleian Library", "Herzogin-Anna-Amalia-Bibliothek"))
     * check(got == listOf(Pair("Bodleian Library".toString(), RustMutableReference(allocateNativeCell(1602, PrimitiveLayouts.I32), PrimitiveLayouts.I32)), Pair("Herzogin-Anna-Amalia-Bibliothek".toString(), RustMutableReference(allocateNativeCell(1691, PrimitiveLayouts.I32), PrimitiveLayouts.I32))))
     * // Missing keys result in None
     * val got1 = libraries.getDisjointKeyValueMut(listOf("Bodleian Library", "Gewandhaus"))
     * check(got1 == listOf(Pair("Bodleian Library".toString(), RustMutableReference(allocateNativeCell(1602, PrimitiveLayouts.I32), PrimitiveLayouts.I32)), null))
     * ```
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var libraries = mutableMapOf()
     * libraries.put("Bodleian Library".toString(), 1602)
     * libraries.put("Herzogin-Anna-Amalia-Bibliothek".toString(), 1691)
     * // Duplicate keys result in panic
     * val got = libraries.getDisjointKeyValueMut(listOf("Bodleian Library", "Herzogin-Anna-Amalia-Bibliothek", "Herzogin-Anna-Amalia-Bibliothek"))
     * ```
     */
    public open fun <Q> getDisjointKeyValueMut(ks: List<Q>, n: Int): List<Pair<K, V>> where Q : Equivalent<K> = this.getDisjointMutInner(ks, n).map({ res -> res.map({ (k, v) -> Pair(k, v) }) })

    /**
     * Attempts to get mutable references to `N` values in the map at once, with immutable
     * references to the corresponding keys.
     */
    public open fun <Q> getManyKeyValueMut(ks: List<Q>, n: Int): List<Pair<K, V>> where Q : Equivalent<K> = this.getDisjointKeyValueMut(ks, n)

    /**
     * Attempts to get mutable references to `N` values in the map at once, with immutable
     * references to the corresponding keys, without validating that the values are unique.
     *
     * Returns an array of length `N` with the results of each query. `None` will be returned if
     * any of the keys are missing.
     *
     * For a safe alternative see [`getDisjointKeyValueMut`](`HashMap.getDisjointKeyValueMut`).
     *
     * # Safety
     *
     * Calling this method with overlapping keys is *[undefined behavior]* even if the resulting
     * references are not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var libraries = mutableMapOf()
     * libraries.put("Bodleian Library".toString(), 1602)
     * libraries.put("Athenæum".toString(), 1807)
     * libraries.put("Herzogin-Anna-Amalia-Bibliothek".toString(), 1691)
     * libraries.put("Library of Congress".toString(), 1800)
     * val got = libraries.getDisjointKeyValueMut(listOf("Bodleian Library", "Herzogin-Anna-Amalia-Bibliothek"))
     * check(got == listOf(Pair("Bodleian Library".toString(), RustMutableReference(allocateNativeCell(1602, PrimitiveLayouts.I32), PrimitiveLayouts.I32)), Pair("Herzogin-Anna-Amalia-Bibliothek".toString(), RustMutableReference(allocateNativeCell(1691, PrimitiveLayouts.I32), PrimitiveLayouts.I32))))
     * // Missing keys result in None
     * val got1 = libraries.getDisjointKeyValueMut(listOf("Bodleian Library", "Gewandhaus"))
     * check(got1 == listOf(Pair("Bodleian Library".toString(), RustMutableReference(allocateNativeCell(1602, PrimitiveLayouts.I32), PrimitiveLayouts.I32)), null))
     * ```
     */
    public open fun <Q> getDisjointKeyValueUncheckedMut(ks: List<Q>, n: Int): List<Pair<K, V>> where Q : Equivalent<K> = this.getDisjointUncheckedMutInner(ks, n).map({ res -> res.map({ (k, v) -> Pair(k, v) }) })

    /**
     * Attempts to get mutable references to `N` values in the map at once, with immutable
     * references to the corresponding keys, without validating that the values are unique.
     */
    public open fun <Q> getManyKeyValueUncheckedMut(ks: List<Q>, n: Int): List<Pair<K, V>> where Q : Equivalent<K> = this.getDisjointKeyValueUncheckedMut(ks, n)

    public open fun <Q> getDisjointMutInner(ks: List<Q>, n: Int): List<Pair<K, V>> where Q : Equivalent<K> {
        val hashes = this.buildHashesInner(ks, n)
        return this.table.getDisjointMut(hashes, { i, (k, _) -> ks[i].equivalent(k) })
    }

    public open fun <Q> getDisjointUncheckedMutInner(ks: List<Q>, n: Int): List<Pair<K, V>> where Q : Equivalent<K> =
        run {
            val hashes = this.buildHashesInner(ks, n)
            this.table.getDisjointUncheckedMut(hashes, { i, (k, _) -> ks[i].equivalent(k) })
        }

    public open fun <Q> buildHashesInner(ks: List<Q>, n: Int): ULongArray where Q : Equivalent<K> {
        val hashes = ULongArray(n) { 0uL }
        for (i in 0 until n) {
            hashes[i] = makeHash<Q, S>(this.hashBuilder, ks[i])
        }
        return hashes
    }

    /**
     * Inserts a key-value pair into the map.
     *
     * If the map did not have this key present, [None] is returned.
     *
     * If the map did have this key present, the value is updated, and the old
     * value is returned. The key is not updated, though; this matters for
     * types that can be `==` without being identical. See the [std.collections]
     * module-level documentation for more.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * check(map.put(37, "a") == null)
     * check(map.isEmpty() == false)
     * map.put(37, "b")
     * check(map.put(37, "c") == "b")
     * check(map[37] == "c")
     * ```
     */
    public open fun insert(k: K, v: V): V? {
        val hash = makeHash(this.hashBuilder, k)
        val equivalent = equivalentKey(k)
        val hasher = makeHasher(this.hashBuilder)
        return run {
            val matchVal = this.table.findOrFindInsertIndex(hash, equivalent, hasher)
            when (matchVal) { is Result.Ok -> {
                val bucket = matchVal.value
                replace(bucket.asMut().second, v)
            }; is Result.Err -> {
                val index = matchVal.error
                run {
                    this.table.insertAtIndex(hash, index, Pair(k, v))
                }
                null
            } }
        }
    }

    /**
     * Insert a key-value pair into the map without checking
     * if the key already exists in the map.
     *
     * This operation is faster than regular insert, because it does not perform
     * lookup before insertion.
     *
     * This operation is useful during initial population of the map.
     * For example, when constructing a map from another map, we know
     * that keys are unique.
     *
     * Returns a reference to the key and value just inserted.
     *
     * # Safety
     *
     * This operation is safe if a key does not exist in the map.
     *
     * However, if a key exists in the map already, the behavior is unspecified:
     * this operation may panic, loop forever, or any following operation with the map
     * may panic, loop forever or return arbitrary result.
     *
     * That said, this operation (and following operations) are guaranteed to
     * not violate memory safety.
     *
     * However this operation is still unchecked because the resulting `HashMap`
     * may be passed to unchecked code which does expect the map to behave
     * correctly, and would cause unsoundness as a result.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map1 = mutableMapOf()
     * check(map1.put(1, "a") == null)
     * check(map1.put(2, "b") == null)
     * check(map1.put(3, "c") == null)
     * check(map1.size == 3)
     * var map2 = mutableMapOf()
     * for ((key, value) in map1.intoIter()) {
     *     run {
     *         map2.insertUniqueUnchecked(key, value)
     *     }
     * }
     * val (key1, value1) = map2.insertUniqueUnchecked(4, "d")
     * check(key1 == 4)
     * check(value1 == "d")
     * value1 = "e"
     * check(map2[1] == "a")
     * check(map2[2] == "b")
     * check(map2[3] == "c")
     * check(map2[4] == "e")
     * check(map2.size == 4)
     * ```
     */
    public open fun insertUniqueUnchecked(k: K, v: V): Pair<K, V> {
        val hash = makeHash<K, S>(this.hashBuilder, k)
        val bucket = this.table.insert(hash, Pair(k, v), makeHasher<V, S>(this.hashBuilder))
        val (kRef, vRef) = bucket.asMut()
        return Pair(kRef, vRef)
    }

    /**
     * Tries to insert a key-value pair into the map, and returns
     * a mutable reference to the value in the entry.
     *
     * # Errors
     *
     * If the map already had this key present, nothing is updated, and
     * an error containing the occupied entry and the value is returned.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.OccupiedError
     *
     * var map = mutableMapOf()
     * check(map.tryInsert(37, "a").unwrap() == "a")
     * run {
     *     val matchVal = map.tryInsert(37, "b")
     *     when {
     *         matchVal is Err -> {
     *             val entry = matchVal.value.entry
     *             val value = matchVal.value.value
     *             check(entry.key() == 37)
     *             check(entry.get() == "a")
     *             check(value == "b")
     *         }
     *         else -> error("panic")
     *     }
     * }
     * ```
     */
    public open fun tryInsert(key: K, value: V): Result<V, OccupiedError<K, V, S, A>> =
        when (val matchVal = this.entry(key)) {
            is Entry.Occupied -> {
                val (entry) = matchVal
                Result.Err<V, OccupiedError<K, V, S, A>>(OccupiedError(entry = entry, value = value))
            }
            is Entry.Vacant -> {
                val (entry1) = matchVal
                Result.Ok<V, OccupiedError<K, V, S, A>>(entry1.insert(value))
            }
        }

    /**
     * Removes a key from the map, returning the value at the key if the key
     * was previously in the map. Keeps the allocated memory for reuse.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * // The map is empty
     * check(map.isEmpty() && map.capacity() == 0)
     * map.put(1, "a")
     * check(map.remove(1) == "a")
     * check(map.remove(1) == null)
     * // Now map holds none elements
     * check(map.isEmpty())
     * ```
     */
    public open fun <Q> remove(k: Q): V? where Q : Equivalent<K> {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.removeEntry(k)) {
            null -> null
            else -> {
                val v = matchVal.second
                v
            }
        }
    }

    /**
     * Removes a key from the map, returning the stored key and value if the
     * key was previously in the map. Keeps the allocated memory for reuse.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map = mutableMapOf()
     * // The map is empty
     * check(map.isEmpty() && map.capacity() == 0)
     * map.put(1, "a")
     * check(map.removeEntry(1) == Pair(1, "a"))
     * check(map.remove(1) == null)
     * // Now map hold none elements
     * check(map.isEmpty())
     * ```
     */
    public open fun <Q> removeEntry(k: Q): Pair<K, V>? where Q : Equivalent<K> {
        val hash = makeHash<Q, S>(this.hashBuilder, k)
        return this.table.removeEntry(hash, equivalentKey(k))
    }

    /**
     * Returns the total amount of memory allocated internally by the hash
     * set, in bytes.
     *
     * The returned number is informational only. It is intended to be
     * primarily used for memory profiling.
     */
    public open fun allocationSize(): Int = this.table.allocationSize()

    public open fun eq(other: HashMap<K, V, S, A>): Boolean {
        if (this.len() != other.len()) {
            return false
        }
        return this.iter().all({ (key, value) -> other.get(key).isSomeAnd({ v -> value == v }) })
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { f.append(entries.joinToString(", ", "{", "}")) } }.entries(this.iter()).finish()

    /**
     * Returns a reference to the value corresponding to the supplied key.
     *
     * # Panics
     *
     * Panics if the key is not present in the `HashMap`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * val map: HashMap = listOf(Pair("a", "One"), Pair("b", "Two")).into()
     * check(map["a"] == "One")
     * check(map["b"] == "Two")
     * ```
     */
    public open fun index(key: Q): V = (this.get(key) ?: error("no entry found for key"))

    /**
     * Creates an iterator over the entries of a `HashMap` in arbitrary order.
     * The iterator element type is `(K, V)`.
     *
     * Return the same `Iter` struct as by the [iter] method on [HashMap].
     *
     * [iter]: HashMap.iter
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * val mapOne: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
     * var mapTwo = mutableMapOf()
     * for ((key, value) in mapOne) {
     *     println("Key: $key, Value: $value")
     *     mapTwo.put(key, value)
     * }
     * check(mapOne == mapTwo)
     * ```
     */
    public open fun intoIter(): Iter<K, V> = this.iter()

    /**
     * Creates a consuming iterator, that is, one that moves each key-value
     * pair out of the map in arbitrary order. The map cannot be used after
     * calling this.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * val map: HashMap = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3)).into()
     * // Not possible with .iter()
     * var vec: List<Pair<String, Int>> = map.intoIter().toMutableList()
     * // The `IntoIter` iterator produces items in arbitrary order, so
     * // the items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3)))
     * ```
     */
    public open fun intoIterIntoIterator(): IntoIter<K, V, A> = IntoIter(inner = this.table.intoIter())

    /**
     * Inserts all new key-values from the iterator and replaces values with existing
     * keys with new values returned from the iterator.
     */
    /**
     * Inserts all new key-values from the iterator to existing `HashMap<K, V, S, A>`.
     * Replace values with existing keys with new values returned from the iterator.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, 100)
     * val someIter = listOf(Pair(1, 1), Pair(2, 2)).intoIter()
     * map.extend(someIter)
     * // Replace values with existing keys with new values returned from the iterator.
     * // So that the map.get(&1) doesn't return Some(&100).
     * check(map.get(1) == 1)
     * val someVec: Vec = mutableListOf(Pair(3, 3), Pair(4, 4))
     * map.extend(someVec)
     * val someArr = listOf(Pair(5, 5), Pair(6, 6))
     * map.extend(someArr)
     * val oldMapLen = map.size
     * // You can also extend from another HashMap
     * var newMap = mutableMapOf()
     * newMap.extend(map)
     * check(newMap.size == oldMapLen)
     * var vec: Vec = newMap.intoIter().collect()
     * // The `IntoIter` iterator produces items in arbitrary order, so the
     * // items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)))
     * ```
     */
    public open fun <T : Iterable<Pair<K, V>>> extend(iter: T) {
        // Keys may be already present or show multiple times in the iterator.
        // Reserve the entire hint lower bound if the map is empty.
        // Otherwise reserve half the hint (rounded up), so the map
        // will only resize twice in the worst case.
        val iter1 = iter.iterator()
        val reserve =
            if (this.isEmpty()) {
                iter1.sizeHint().first
            } else {
                iter1.sizeHint().first.divCeil(2)
            }
        this.reserve(reserve)
        iter1.forEach({ (k, v) ->
            this.put(k, v)
        })
    }

    public open fun extendOne(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.put(k, v)
    }

    public open fun extendReserve(additional: Int) {
        // Keys may be already present or show multiple times in the iterator.
        // Reserve the entire hint lower bound if the map is empty.
        // Otherwise reserve half the hint (rounded up), so the map
        // will only resize twice in the worst case.
        val reserve =
            if (this.isEmpty()) {
                additional
            } else {
                additional.divCeil(2)
            }
        this.reserve(reserve)
    }

    /**
     * Inserts all new key-values from the iterator and replaces values with existing
     * keys with new values returned from the iterator.
     */
    /**
     * Inserts all new key-values from the iterator to existing `HashMap<K, V, S, A>`.
     * Replace values with existing keys with new values returned from the iterator.
     * The keys and values must implement [Copy] trait.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, 100)
     * val arr = listOf(Pair(1, 1), Pair(2, 2))
     * val someIter = arr.iter().map({ (k, v) -> Pair(k, v) })
     * map.extend(someIter)
     * // Replace values with existing keys with new values returned from the iterator.
     * // So that the map.get(&1) doesn't return Some(&100).
     * check(map.get(1) == 1)
     * val someVec: Vec = mutableListOf(Pair(3, 3), Pair(4, 4))
     * map.extend(ArrayDeque((ArrayDeque(someVec)).map({ (k, v) -> Pair(k, v) })))
     * val someArr = listOf(Pair(5, 5), Pair(6, 6))
     * map.extend(someArr.iter().map({ (k, v) -> Pair(k, v) }))
     * // You can also extend from another HashMap
     * var newMap = mutableMapOf()
     * newMap.extend(map)
     * check(newMap == map)
     * var vec: Vec = newMap.intoIter().collect()
     * // The `IntoIter` iterator produces items in arbitrary order, so the
     * // items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)))
     * ```
     */
    public open fun <T : Iterable<Pair<K, V>>> extendKV(iter: T) {
        this.extend(iter.iterator().map({ (key, value) -> Pair(key, value) }))
    }

    public open fun extendOneKV(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.put(k, v)
    }

    public open fun extendReserveKV(additional: Int) {
        Extend.extendReserve(this, additional)
    }

    /**
     * Inserts all new key-values from the iterator and replaces values with existing
     * keys with new values returned from the iterator.
     */
    /**
     * Inserts all new key-values from the iterator to existing `HashMap<K, V, S, A>`.
     * Replace values with existing keys with new values returned from the iterator.
     * The keys and values must implement [Copy] trait.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.HashMap
     *
     * var map = mutableMapOf()
     * map.put(1, 100)
     * val arr = listOf(Pair(1, 1), Pair(2, 2))
     * val someIter = arr.iter()
     * map.extend(someIter)
     * // Replace values with existing keys with new values returned from the iterator.
     * // So that the map.get(&1) doesn't return Some(&100).
     * check(map.get(1) == 1)
     * val someVec: Vec = mutableListOf(Pair(3, 3), Pair(4, 4))
     * map.extend(someVec)
     * val someArr = listOf(Pair(5, 5), Pair(6, 6))
     * map.extend(someArr)
     * var vec: Vec = map.intoIter().collect()
     * // The `IntoIter` iterator produces items in arbitrary order, so the
     * // items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)))
     * ```
     */
    public open fun <T : Iterable<Pair<K, V>>> extendKVKV(iter: T) {
        this.extend(iter.iterator().map({ (key, value) -> Pair(key, value) }))
    }

    public open fun extendOneKVKV(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.put(k, v)
    }

    public open fun extendReserveKVKV(additional: Int) {
        Extend.extendReserve(this, additional)
    }

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            hashBuilder: S,
            table: RawTable<Pair<K, V>, A>,
        ): HashMap<K, V, S, A> = HashMap<K, V, S, A>(
            hashBuilder = hashBuilder,
            tableCtorArg = table,
        )

        /**
         * Creates an empty `HashMap` which will use the given hash builder to hash
         * keys. It will be allocated with the given allocator.
         *
         * The hash map is initially created with a capacity of 0, so it will not allocate until it
         * is first inserted into.
         *
         * # HashDoS resistance
         *
         * The `hashBuilder` normally use a fixed key by default and that does
         * not allow the `HashMap` to be protected against attacks such as [HashDoS].
         * Users who require HashDoS resistance should explicitly use
         * [std.hash.RandomState]
         * as the hasher when creating a [HashMap].
         *
         * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
         *
         * # Examples
         *
         * ```kotlin
         * import hashbrown.DefaultHashBuilder
         * import hashbrown.HashMap
         *
         * val s = DefaultHashBuilder.default()
         * var map = HashMap.withHasher(s)
         * map.insert(1, 2)
         * ```
         */
        public fun <K, V, S, A : Allocator> withHasherIn(hashBuilder: S, alloc: A): HashMap<K, V, S, A> = HashMap(hashBuilder = hashBuilder, table = RawTable.newIn(alloc))

        /**
         * Creates an empty `HashMap` with the specified capacity, using `hashBuilder`
         * to hash the keys. It will be allocated with the given allocator.
         *
         * The hash map will be able to hold at least `capacity` elements without
         * reallocating. If `capacity` is 0, the hash map will not allocate.
         *
         * # HashDoS resistance
         *
         * The `hashBuilder` normally use a fixed key by default and that does
         * not allow the `HashMap` to be protected against attacks such as [HashDoS].
         * Users who require HashDoS resistance should explicitly use
         * [std.hash.RandomState]
         * as the hasher when creating a [HashMap].
         *
         * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
         *
         * # Examples
         *
         * ```kotlin
         * import hashbrown.DefaultHashBuilder
         * import hashbrown.HashMap
         *
         * val s = DefaultHashBuilder.default()
         * var map = HashMap.withCapacityAndHasher(10, s)
         * map.insert(1, 2)
         * ```
         */
        public fun <K, V, S, A : Allocator> withCapacityAndHasherIn(capacity: Int, hashBuilder: S, alloc: A): HashMap<K, V, S, A> = HashMap(hashBuilder = hashBuilder, table = RawTable.withCapacityIn(capacity, alloc))

        /**
         * Creates an empty `HashMap<K, V, S, A>`, with the `Default` value for the hasher and allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import hashbrown.HashMap
         * import std.hash.RandomState
         *
         * // You can specify all types of HashMap, including hasher and allocator.
         * // Created map is empty and don't allocate memory
         * val map: MutableMap<UInt, String> = Default.default()
         * check(map.capacity() == 0)
         * val map1: MutableMap<UInt, String> = HashMap.default()
         * check(map1.capacity() == 0)
         * ```
         */
        public fun <K, V, S, A : Allocator> default(): HashMap<K, V, S, A> where A : Allocator = HashMap.withHasherIn(HashMap.default(), HashMap.default())

        public fun <K, V, S, A : Allocator, T : Iterable<Pair<K, V>>> fromIter(iter: T, defaultSFn: () -> S, defaultAFn: () -> A): HashMap<K, V, S, A> where S : BuildHasher, A : Allocator {
            val iter1 = iter.iterator()
            val map = HashMap.withCapacityAndHasherIn(iter1.sizeHint().first, defaultSFn(), defaultAFn())
            iter1.forEach({ (k, v) ->
                map.insert(k, v)
            })
            return map
        }
    }
}

/**
 * Ensures that a single closure type across uses of this which, in turn prevents multiple
 * instances of any functions like `RawTable.reserve` from being generated
 */
public fun <Q, V, S> makeHasher(hashBuilder: S): (Pair<Q, V>) -> ULong where S : BuildHasher = { value -> makeHash<Q, S>(hashBuilder, value.first) }

/**
 * Ensures that a single closure type across uses of this which, in turn prevents multiple
 * instances of any functions like `RawTable.reserve` from being generated
 */
public fun <Q, K, V> equivalentKey(k: Q): (Pair<K, V>) -> Boolean where Q : Equivalent<K> = { x -> k.equivalent(x.first) }

/**
 * Ensures that a single closure type across uses of this which, in turn prevents multiple
 * instances of any functions like `RawTable.reserve` from being generated
 */
public fun <Q, K> equivalent(k: Q): (K) -> Boolean where Q : Equivalent<K> = { x -> k.equivalent(x) }

public fun <Q, S> makeHash(hashBuilder: S, value: Q): ULong where S : BuildHasher = hashBuilder.hashOne(value)

/**
 * Creates an empty `HashMap`.
 *
 * The hash map is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashMap` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashMap], for example with
 * [`withHasher`](HashMap.withHasher) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var map: MutableMap<String, Int> = mutableMapOf()
 * check(map.size == 0)
 * check(map.capacity() == 0)
 * ```
 */
public fun <K, V> HashMap.Companion.new(): HashMap<K, V, DefaultHashBuilder, Global> = HashMap.default()

/**
 * Creates an empty `HashMap` with the specified capacity.
 *
 * The hash map will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash map will not allocate.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashMap` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashMap], for example with
 * [`withCapacityAndHasher`](HashMap.withCapacityAndHasher) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var map: MutableMap<String, Int> = mutableMapOf()
 * check(map.size == 0)
 * check(map.capacity() >= 10)
 * ```
 */
public fun <K, V> HashMap.Companion.withCapacity(capacity: Int): HashMap<K, V, DefaultHashBuilder, Global> = HashMap.withCapacityAndHasher(capacity, DefaultHashBuilder.default())

/**
 * Creates an empty `HashMap` using the given allocator.
 *
 * The hash map is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashMap` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashMap], for example with
 * [`withHasherIn`](HashMap.withHasherIn) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import bumpalo.Bump
 * import hashbrown.HashMap
 *
 * val bump = Bump.new()
 * var map = HashMap.newIn(bump)
 * // The created HashMap holds none elements
 * check(map.len() == 0)
 * // The created HashMap also doesn't allocate memory
 * check(map.capacity() == 0)
 * // Now we insert element inside created HashMap
 * map.insert("One", 1)
 * // We can see that the HashMap holds 1 element
 * check(map.len() == 1)
 * // And it also allocates some capacity
 * check(map.capacity() > 1)
 * ```
 */
public fun <K, V, A : Allocator> HashMap.Companion.newIn(alloc: A): HashMap<K, V, DefaultHashBuilder, A> = HashMap.withHasherIn(DefaultHashBuilder.default(), alloc)

/**
 * Creates an empty `HashMap` with the specified capacity using the given allocator.
 *
 * The hash map will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash map will not allocate.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashMap` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashMap], for example with
 * [`withCapacityAndHasherIn`](HashMap.withCapacityAndHasherIn) method.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import bumpalo.Bump
 * import hashbrown.HashMap
 *
 * val bump = Bump.new()
 * var map = HashMap.withCapacityIn(5, bump)
 * // The created HashMap holds none elements
 * check(map.len() == 0)
 * // But it can hold at least 5 elements without reallocating
 * val emptyMapCapacity = map.capacity()
 * check(emptyMapCapacity >= 5)
 * // Now we insert some 5 elements inside created HashMap
 * map.insert("One", 1)
 * map.insert("Two", 2)
 * map.insert("Three", 3)
 * map.insert("Four", 4)
 * map.insert("Five", 5)
 * // We can see that the HashMap holds 5 elements
 * check(map.len() == 5)
 * // But its capacity isn't changed
 * check(map.capacity() == emptyMapCapacity)
 * ```
 */
public fun <K, V, A : Allocator> HashMap.Companion.withCapacityIn(capacity: Int, alloc: A): HashMap<K, V, DefaultHashBuilder, A> = HashMap.withCapacityAndHasherIn(capacity, DefaultHashBuilder.default(), alloc)

/**
 * Creates an empty `HashMap` which will use the given hash builder to hash
 * keys.
 *
 * The hash map is initially created with a capacity of 0, so it will not
 * allocate until it is first inserted into.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashMap` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashMap].
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashMap` to be useful, see its documentation for details.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashMap
 *
 * val s = DefaultHashBuilder.default()
 * var map = HashMap.withHasher(s)
 * check(map.len() == 0)
 * check(map.capacity() == 0)
 * map.insert(1, 2)
 * ```
 */
public fun <K, V, S> HashMap.Companion.withHasher(hashBuilder: S): HashMap<K, V, S, Global> = HashMap(hashBuilder = hashBuilder, table = RawTable.new())

/**
 * Creates an empty `HashMap` with the specified capacity, using `hashBuilder`
 * to hash the keys.
 *
 * The hash map will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash map will not allocate.
 *
 * # HashDoS resistance
 *
 * The `hashBuilder` normally use a fixed key by default and that does
 * not allow the `HashMap` to be protected against attacks such as [HashDoS].
 * Users who require HashDoS resistance should explicitly use
 * [std.hash.RandomState]
 * as the hasher when creating a [HashMap].
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashMap` to be useful, see its documentation for details.
 *
 * [HashDoS]: https://en.wikipedia.org/wiki/CollisionAttack
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashMap
 *
 * val s = DefaultHashBuilder.default()
 * var map = HashMap.withCapacityAndHasher(10, s)
 * check(map.len() == 0)
 * check(map.capacity() >= 10)
 * map.insert(1, 2)
 * ```
 */
public fun <K, V, S> HashMap.Companion.withCapacityAndHasher(capacity: Int, hashBuilder: S): HashMap<K, V, S, Global> = HashMap(hashBuilder = hashBuilder, table = RawTable.withCapacity(capacity))

// The default hasher is used to match the std implementation signature

/**
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map1 = mutableMapOf(1 to 2, 3 to 4)
 * val map2: HashMap = listOf(Pair(1, 2), Pair(3, 4)).into()
 * check(map1 == map2)
 * ```
 */
public fun <K, V, A> fromHashMapKVDefaultHashBuilderA(arr: List<Pair<K, V>>, n: Int): HashMap<K, V, DefaultHashBuilder, A> where A : Allocator = ArrayDeque((arr).toList()).toMap(LinkedHashMap())

/**
 * An iterator over the entries of a `HashMap` in arbitrary order.
 * The iterator element type is `(K, V)`.
 *
 * This `class` is created by the [iter] method on [HashMap]. See its
 * documentation for more.
 *
 * [iter]: HashMap.iter
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var iter = map.iter()
 * var vec = mutableListOf(iter.next(), iter.next(), iter.next())
 * // The `Iter` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")))
 * // It is fused iterator
 * check(iter.next() == null)
 * check(iter.next() == null)
 * ```
 */
public open class Iter<K, V> internal constructor(
    innerCtorArg: RawIter<Pair<K, V>>,
    public var marker: Unit,
) : Iterable<Pair<K, V>> {
    public var inner: RawIter<Pair<K, V>> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Iter<K, V> = Iter(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

    public open fun next(): Pair<K, V>? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val x = matchVal
                run {
                    val r = x.asRef()
                    Pair(r.first, r.second)
                }
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B =
        this.inner.fold(init, { acc, x -> run {
            val (k, v) = x.asRef()
            f(acc, Pair(k, v))
        } })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawIter<Pair<K, V>>,
            marker: Unit,
        ): Iter<K, V> = Iter<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )

        public fun <K, V> default(): Iter<K, V> = Iter(inner = RawIter.default(), marker = Unit)
    }
}

/**
 * A mutable iterator over the entries of a `HashMap` in arbitrary order.
 * The iterator element type is `(K, V)`.
 *
 * This `class` is created by the [iterMut] method on [HashMap]. See its
 * documentation for more.
 *
 * [iterMut]: HashMap.iterMut
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var map: HashMap = listOf(Pair(1, "One".toOwned()), Pair(2, "Two".into())).into()
 * var iter = map.iterMut()
 * iter.next()?.let({ (_, v) -> v.pushStr(" Mississippi") })
 * iter.next()?.let({ (_, v) -> v.pushStr(" Mississippi") })
 * // It is fused iterator
 * check(iter.next() == null)
 * check(iter.next() == null)
 * check(map.get(1)!! == "One Mississippi".toOwned())
 * check(map.get(2)!! == "Two Mississippi".toOwned())
 * ```
 */
public open class IterMut<K, V> internal constructor(
    innerCtorArg: RawIter<Pair<K, V>>,
    // To ensure invariance with respect to V
    public var marker: Unit,
) : Iterable<Pair<K, V>> {
    public var inner: RawIter<Pair<K, V>> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // We override the default Send impl which has K: Sync instead of K: Send. Both
    // are correct, but this one is more general since it allows keys which
    // implement Send but not Sync.

    /**
     * Returns a iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(inner = this.inner.clone(), marker = Unit)

    public open fun next(): Pair<K, V>? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val x = matchVal
                run {
                    val r = x.asMut()
                    Pair(r.first, r.second)
                }
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B =
        this.inner.fold(init, { acc, x -> run {
            val (k, v) = x.asMut()
            f(acc, Pair(k, v))
        } })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawIter<Pair<K, V>>,
            marker: Unit,
        ): IterMut<K, V> = IterMut<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )

        public fun <K, V> default(): IterMut<K, V> = IterMut(inner = RawIter.default(), marker = Unit)
    }
}

/**
 * An owning iterator over the entries of a `HashMap` in arbitrary order.
 * The iterator element type is `(K, V)`.
 *
 * This `class` is created by the [intoIter] method on [HashMap]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 * The map cannot be used after calling that method.
 *
 * [intoIter]: HashMap.intoIter
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var iter = map.intoIter()
 * var vec = mutableListOf(iter.next(), iter.next(), iter.next())
 * // The `IntoIter` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")))
 * // It is fused iterator
 * check(iter.next() == null)
 * check(iter.next() == null)
 * ```
 */
public open class IntoIter<K, V, A : Allocator> internal constructor(
    innerCtorArg: RawIntoIter<Pair<K, V>, A>,
) : Iterable<Pair<K, V>> {
    public var inner: RawIntoIter<Pair<K, V>, A> = innerCtorArg
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
     * Returns a iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(inner = this.inner.iter(), marker = Unit)

    public open fun next(): Pair<K, V>? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B = this.inner.fold(init, f)

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: RawIntoIter<Pair<K, V>, A>,
        ): IntoIter<K, V, A> = IntoIter<K, V, A>(
            innerCtorArg = inner,
        )

        public fun <K, V, A : Allocator> default(): IntoIter<K, V, A> = IntoIter(inner = RawIntoIter.default())
    }
}

/**
 * An owning iterator over the keys of a `HashMap` in arbitrary order.
 * The iterator element type is `K`.
 *
 * This `class` is created by the [intoKeys] method on [HashMap].
 * See its documentation for more.
 * The map cannot be used after calling that method.
 *
 * [intoKeys]: HashMap.intoKeys
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var keys = map.intoKeys()
 * var vec = mutableListOf(keys.next(), keys.next(), keys.next())
 * // The `IntoKeys` iterator produces keys in arbitrary order, so the
 * // keys must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(1, 2, 3))
 * // It is fused iterator
 * check(keys.next() == null)
 * check(keys.next() == null)
 * ```
 */
public open class IntoKeys<K, V, A : Allocator> internal constructor(
    innerCtorArg: IntoIter<K, V, A>,
) : Iterable<K> {
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

    public open fun next(): K? = this.inner.next()?.let({ (k, _) -> k })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.inner.fold(init, { acc, (k, _) -> f(acc, k) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (k, _) -> k })).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: IntoIter<K, V, A>,
        ): IntoKeys<K, V, A> = IntoKeys<K, V, A>(
            innerCtorArg = inner,
        )

        public fun <K, V, A : Allocator> default(): IntoKeys<K, V, A> = IntoKeys(inner = IntoIter.default())
    }
}

/**
 * An owning iterator over the values of a `HashMap` in arbitrary order.
 * The iterator element type is `V`.
 *
 * This `class` is created by the [intoValues] method on [HashMap].
 * See its documentation for more. The map cannot be used after calling that method.
 *
 * [intoValues]: HashMap.intoValues
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var values = map.intoValues()
 * var vec = mutableListOf(values.next(), values.next(), values.next())
 * // The `IntoValues` iterator produces values in arbitrary order, so
 * // the values must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf("a", "b", "c"))
 * // It is fused iterator
 * check(values.next() == null)
 * check(values.next() == null)
 * ```
 */
public open class IntoValues<K, V, A : Allocator> internal constructor(
    innerCtorArg: IntoIter<K, V, A>,
) : Iterable<V> {
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

    public open fun next(): V? = this.inner.next()?.let({ (_, v) -> v })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, V) -> B): B = this.inner.fold(init, { acc, (_, v) -> f(acc, v) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (_, v) -> v })).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: IntoIter<K, V, A>,
        ): IntoValues<K, V, A> = IntoValues<K, V, A>(
            innerCtorArg = inner,
        )

        public fun <K, V, A : Allocator> default(): IntoValues<K, V, A> = IntoValues(inner = IntoIter.default())
    }
}

/**
 * An iterator over the keys of a `HashMap` in arbitrary order.
 * The iterator element type is `K`.
 *
 * This `class` is created by the [keys] method on [HashMap]. See its
 * documentation for more.
 *
 * [keys]: HashMap.keys
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var keys = map.keys()
 * var vec = mutableListOf(keys.next(), keys.next(), keys.next())
 * // The `Keys` iterator produces keys in arbitrary order, so the
 * // keys must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(1, 2, 3))
 * // It is fused iterator
 * check(keys.next() == null)
 * check(keys.next() == null)
 * ```
 */
public open class Keys<K, V>(
    public var inner: Iter<K, V>,
) : Iterable<K> {
    override fun iterator(): Iterator<K> = object : AbstractIterator<K>() {
        override fun computeNext() {
            when (val v = this@Keys.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Keys<K, V> = Keys(inner = this.inner.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

    public open fun next(): K? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val k = matchVal.first
                k
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.inner.fold(init, { acc, (k, _) -> f(acc, k) })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public fun <K, V> default(): Keys<K, V> = Keys(inner = Iter.default())
    }
}

/**
 * An iterator over the values of a `HashMap` in arbitrary order.
 * The iterator element type is `V`.
 *
 * This `class` is created by the [values] method on [HashMap]. See its
 * documentation for more.
 *
 * [values]: HashMap.values
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * val map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var values = map.values()
 * var vec = mutableListOf(values.next(), values.next(), values.next())
 * // The `Values` iterator produces values in arbitrary order, so the
 * // values must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf("a", "b", "c"))
 * // It is fused iterator
 * check(values.next() == null)
 * check(values.next() == null)
 * ```
 */
public open class Values<K, V>(
    public var inner: Iter<K, V>,
) : Iterable<V> {
    override fun iterator(): Iterator<V> = object : AbstractIterator<V>() {
        override fun computeNext() {
            when (val v = this@Values.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Values<K, V> = Values(inner = this.inner.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

    public open fun next(): V? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val v = matchVal.second
                v
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, V) -> B): B = this.inner.fold(init, { acc, (_, v) -> f(acc, v) })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public fun <K, V> default(): Values<K, V> = Values(inner = Iter.default())
    }
}

/**
 * A draining iterator over the entries of a `HashMap` in arbitrary
 * order. The iterator element type is `(K, V)`.
 *
 * This `class` is created by the [drain] method on [HashMap]. See its
 * documentation for more.
 *
 * [drain]: HashMap.drain
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var map: HashMap = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var drainIter = map.drain()
 * var vec = mutableListOf(drainIter.next(), drainIter.next(), drainIter.next())
 * // The `Drain` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")))
 * // It is fused iterator
 * check(drainIter.next() == null)
 * check(drainIter.next() == null)
 * ```
 */
public open class Drain<K, V, A : Allocator> internal constructor(
    innerCtorArg: RawDrain<Pair<K, V>, A>,
) : Iterable<Pair<K, V>> {
    public var inner: RawDrain<Pair<K, V>, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(inner = this.inner.iter(), marker = Unit)

    public open fun next(): Pair<K, V>? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B = this.inner.fold(init, f)

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: RawDrain<Pair<K, V>, A>,
        ): Drain<K, V, A> = Drain<K, V, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * A draining iterator over entries of a `HashMap` which don't satisfy the predicate
 * `f(k, v)` in arbitrary order. The iterator element type is `(K, V)`.
 *
 * This `class` is created by the [extractIf] method on [HashMap]. See its
 * documentation for more.
 *
 * [extractIf]: HashMap.extractIf
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var map: MutableMap<Int, String> = listOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c")).into()
 * var extractIf = map.extractIf({ k, v -> k % 2 != 0 })
 * var vec = mutableListOf(extractIf.next(), extractIf.next())
 * // The `ExtractIf` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(Pair(1, "a"), Pair(3, "c")))
 * // It is fused iterator
 * check(extractIf.next() == null)
 * check(extractIf.next() == null)
 * (extractIf).let { }
 * check(map.size == 1)
 * ```
 */
public open class ExtractIf<K, V, F, A : Allocator> internal constructor(
    public var f: F,
    innerCtorArg: RawExtractIf<Pair<K, V>, A>,
) : Iterable<Pair<K, V>> {
    public var inner: RawExtractIf<Pair<K, V>, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@ExtractIf.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<K, V>? = this.inner.next({ (k, v) -> (this.f)(k, v) })

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, this.inner.iter.sizeHint().second)

    public companion object {
        public operator fun <K, V, F, A : Allocator> invoke(
            f: F,
            inner: RawExtractIf<Pair<K, V>, A>,
        ): ExtractIf<K, V, F, A> = ExtractIf<K, V, F, A>(
            f = f,
            innerCtorArg = inner,
        )
    }
}

/**
 * A mutable iterator over the values of a `HashMap` in arbitrary order.
 * The iterator element type is `V`.
 *
 * This `class` is created by the [valuesMut] method on [HashMap]. See its
 * documentation for more.
 *
 * [valuesMut]: HashMap.valuesMut
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashMap
 *
 * var map: HashMap = listOf(Pair(1, "One".toOwned()), Pair(2, "Two".into())).into()
 * var values = map.valuesMut()
 * values.next()?.let({ v -> v.pushStr(" Mississippi") })
 * values.next()?.let({ v -> v.pushStr(" Mississippi") })
 * // It is fused iterator
 * check(values.next() == null)
 * check(values.next() == null)
 * check(map.get(1)!! == "One Mississippi".toOwned())
 * check(map.get(2)!! == "Two Mississippi".toOwned())
 * ```
 */
public open class ValuesMut<K, V>(
    public var inner: IterMut<K, V>,
) : Iterable<V> {
    override fun iterator(): Iterator<V> = object : AbstractIterator<V>() {
        override fun computeNext() {
            when (val v = this@ValuesMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): V? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val v = matchVal.second
                v
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, V) -> B): B = this.inner.fold(init, { acc, (_, v) -> f(acc, v) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (_, value) -> value })).finish()

    public companion object {
        public fun <K, V> default(): ValuesMut<K, V> = ValuesMut(inner = IterMut.default())
    }
}

/**
 * A view into a single entry in a map, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [entry] method on [HashMap].
 *
 * [entry]: HashMap.entry
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashmap.Entry
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.OccupiedEntry
 *
 * var map = mutableMapOf()
 * map.extend(listOf(Pair("a", 10), Pair("b", 20), Pair("c", 30)))
 * check(map.size == 3)
 * // Existing key (insert)
 * val entry: Entry = map.entry("a")
 * val rawO: OccupiedEntry = entry.insert(1)
 * check(map.size == 3)
 * // Nonexistent key (insert)
 * map.entry("d").insert(4)
 * // Existing key (orInsert)
 * val v = map.entry("b").orInsert(2)
 * check(replace(v, 2) == 20)
 * // Nonexistent key (orInsert)
 * map.entry("e").orInsert(5)
 * // Existing key (orInsertWith)
 * val v1 = map.entry("c").orInsertWith({ 3 })
 * check(replace(v1, 3) == 30)
 * // Nonexistent key (orInsertWith)
 * map.entry("f").orInsertWith({ 6 })
 * println("Our HashMap: $map")
 * var vec: Vec = map.iter().map({ (k, v2) -> Pair(k, v2) }).collect()
 * // The `Iter` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4), Pair("e", 5), Pair("f", 6)))
 * ```
 */
public sealed class Entry<K, V, S, A> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     *
     * var map: HashMap = listOf(Pair("a", 100), Pair("b", 200)).into()
     * when (val matchVal = map.entry("a")) {
     *     is Entry.Vacant -> error("unreachable")
     *     is Entry.Occupied -> {}
     * }
     * ```
     */
    public class Occupied<K, V, S, A> internal constructor(valueCtorArg: OccupiedEntry<K, V, S, A>) : Entry<K, V, S, A>() {
        public var value: OccupiedEntry<K, V, S, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<K, V, S, A> = this.value

        public fun copy(value: OccupiedEntry<K, V, S, A> = this.value): Occupied<K, V, S, A> = Occupied<K, V, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, S, A> invoke(
                value: OccupiedEntry<K, V, S, A>,
            ): Occupied<K, V, S, A> = Occupied<K, V, S, A>(
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
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * when (val matchVal = map.entry("a")) {
     *     is Entry.Occupied -> error("unreachable")
     *     is Entry.Vacant -> {}
     * }
     * ```
     */
    public class Vacant<K, V, S, A> internal constructor(valueCtorArg: VacantEntry<K, V, S, A>) : Entry<K, V, S, A>() {
        public var value: VacantEntry<K, V, S, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntry<K, V, S, A> = this.value

        public fun copy(value: VacantEntry<K, V, S, A> = this.value): Vacant<K, V, S, A> = Vacant<K, V, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, S, A> invoke(
                value: VacantEntry<K, V, S, A>,
            ): Vacant<K, V, S, A> = Vacant<K, V, S, A>(
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
     * Sets the value of the entry, and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val entry = map.entry("horseyland").insert(37)
     * check(entry.key() == "horseyland")
     * ```
     */
    public open fun insert(value: V): OccupiedEntry<K, V, S, A> =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.insert(value)
                entry
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.insertEntry(value)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default if empty, and returns
     * a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // nonexistent key
     * map.entry("poneyland").orInsert(3)
     * check(map["poneyland"] == 3)
     * // existing key
     * map.entry("poneyland").orInsert(10) *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsert(default: V): V =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.insert(default)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default if empty,
     * and returns an [OccupiedEntry].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // nonexistent key
     * val entry = map.entry("poneyland").orInsertEntry(3)
     * check(entry.key() == "poneyland")
     * check(entry.get() == 3)
     * // existing key
     * var entry1 = map.entry("poneyland").orInsertEntry(10)
     * check(entry1.key() == "poneyland")
     * check(entry1.get() == 3)
     * ```
     */
    public open fun orInsertEntry(default: V): OccupiedEntry<K, V, S, A> =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.insertEntry(default)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the result of the default function if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // nonexistent key
     * map.entry("poneyland").orInsertWith({ 3 })
     * check(map["poneyland"] == 3)
     * // existing key
     * map.entry("poneyland").orInsertWith({ 10 }) *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsertWith(default: () -> V): V =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.insert(default())
            }
        }

    /**
     * Ensures a value is in the entry by inserting, if empty, the result of the default function.
     * This method allows for generating key-derived values for insertion by providing the default
     * function a reference to the key that was moved during the `.entry(key)` method call.
     *
     * The reference to the moved key is provided so that cloning or copying the key is
     * unnecessary, unlike with `.orInsertWith(|| ...)`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * // nonexistent key
     * map.entry("poneyland").orInsertWithKey({ key -> key.chars().count() })
     * check(map["poneyland"] == 9)
     * // existing key
     * map.entry("poneyland").orInsertWithKey({ key -> key.chars().count() * 10 }) *= 2
     * check(map["poneyland"] == 18)
     * ```
     */
    public open fun orInsertWithKey(default: (K) -> V): V =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Entry.Vacant -> {
                val (entry1) = this
                val value = default(entry1.key())
                entry1.insert(value)
            }
        }

    /**
     * Returns a reference to this entry's key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(3)
     * // existing key
     * check(map.entry("poneyland").key() == "poneyland")
     * // nonexistent key
     * check(map.entry("horseland").key() == "horseland")
     * ```
     */
    public open fun key(): K =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.key()
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.key()
            }
        }

    /**
     * Provides in-place mutable access to an occupied entry before any
     * potential inserts into the map.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 42)
     * map.entry("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 43)
     * ```
     */
    public open fun andModify(f: (V) -> Unit): Entry<K, V, S, A> =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                f(entry.getMut())
                Entry.Occupied(entry)
            }
            is Entry.Vacant -> {
                val (entry1) = this
                Entry.Vacant(entry1)
            }
        }

    /**
     * Provides shared access to the key and owned access to the value of
     * an occupied entry and allows to replace or remove it based on the
     * value of the returned option.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val entry = map.entry("poneyland").andReplaceEntryWith({ k, v -> error("panic") })
     * when (entry) {
     *     is Entry.Vacant -> {
     *         val (e) = entry
     *         check(e.key() == "poneyland")
     *     }
     *     is Entry.Occupied -> error("panic")
     * }
     * map.put("poneyland", 42)
     * val entry1 = map.entry("poneyland").andReplaceEntryWith({ k, v ->
     *     check(k == "poneyland")
     *     check(v == 42)
     *     v + 1
     * })
     * when (entry1) {
     *     is Entry.Occupied -> {
     *         val (e1) = entry1
     *         check(e1.key() == "poneyland")
     *         check(e1.get() == 43)
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * check(map["poneyland"] == 43)
     * val entry2 = map.entry("poneyland").andReplaceEntryWith({ k, v -> null })
     * when (entry2) {
     *     is Entry.Vacant -> {
     *         val (e2) = entry2
     *         check(e2.key() == "poneyland")
     *     }
     *     is Entry.Occupied -> error("panic")
     * }
     * check(!map.containsKey("poneyland"))
     * ```
     */
    public open fun andReplaceEntryWith(f: (K, V) -> V?): Entry<K, V, S, A> =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.replaceEntryWith(f)
            }
            is Entry.Vacant -> this
        }

    /**
     * Converts the `Entry` into a mutable reference to the underlying map.
     */
    public open fun intoMap(): HashMap<K, V, S, A> =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.table
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.table
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default value if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt?> = mutableMapOf()
     * // nonexistent key
     * map.entry("poneyland").orDefault()
     * check(map["poneyland"] == null)
     * map.put("horseland", 3)
     * // existing key
     * check(map.entry("horseland").orDefault() == 3)
     * ```
     */
    public open fun orDefault(defaultVFn: () -> V): V =
        when (this) {
            is Entry.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Entry.Vacant -> {
                val (entry1) = this
                entry1.insert(defaultVFn())
            }
        }
}

/**
 * A view into an occupied entry in a [HashMap].
 * It is part of the [Entry] and [EntryRef] enums.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashmap.Entry
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.OccupiedEntry
 *
 * var map = mutableMapOf()
 * map.extend(listOf(Pair("a", 10), Pair("b", 20), Pair("c", 30)))
 * val entryO: OccupiedEntry = map.entry("a").insert(100)
 * check(map.size == 3)
 * // Existing key (insert and update)
 * when (val matchVal = map.entry("a")) {
 *     is Entry.Vacant -> error("unreachable")
 *     is Entry.Occupied -> {
 *         val (view) = matchVal
 *         check(view.get() == 100)
 *         val v = view.getMut()
 *         v *= 10
 *         check(view.insert(1111) == 1000)
 *     }
 * }
 * check(map["a"] == 1111)
 * check(map.size == 3)
 * // Existing key (take)
 * when (val matchVal1 = map.entry("c")) {
 *     is Entry.Vacant -> error("unreachable")
 *     is Entry.Occupied -> {
 *         val (view1) = matchVal1
 *         check(view1.removeEntry() == Pair("c", 30))
 *     }
 * }
 * check(map.get("c") == null)
 * check(map.size == 2)
 * ```
 */
public open class OccupiedEntry<K, V, S, A : Allocator> internal constructor(
    public var hash: ULong,
    elemCtorArg: Bucket<Pair<K, V>>,
    tableCtorArg: HashMap<K, V, S, A>,
) {
    public var elem: Bucket<Pair<K, V>> = elemCtorArg
        internal set

    public var table: HashMap<K, V, S, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "OccupiedEntry").field("key", this.key()).field("value", this.get()).finish()

    /**
     * Gets a reference to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> error("panic")
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         check(entry.key() == "poneyland")
     *     }
     * }
     * ```
     */
    public open fun key(): K = this.elem.asRef().first

    /**
     * Replaces the key in the entry with a new one.
     *
     * # Panics
     *
     * This method panics if `key` is not equivalent to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val oldKey = "poneyland"
     * val newKey = Holder.leak(oldKey.toOwned().intoBoxedStr())
     * map.entry(oldKey).orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> error("panic")
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         val replaced = entry.replaceKey(newKey)
     *         check(eq(replaced, oldKey))
     *         check(eq(entry.key(), newKey))
     *     }
     * }
     * ```
     */
    public open fun replaceKey(key: K): K {
        check(this.key().equivalent(key)) { "replaced key is not equivalent to the one in the entry" }
        // SAFETY: We verified that the keys were equivalent.
        return this.replaceKeyUnchecked(key)
    }

    /**
     * Replaces the key in the entry with a new one, without checking the
     * equivalence of the key.
     *
     * # Safety
     *
     * This operation is safe if you replace the key with an equivalent one.
     *
     * Additionally, this operation (and following operations) are guaranteed
     * to not violate memory safety.
     *
     * However this operation is still unchecked because the resulting `HashMap`
     * may be passed to unchecked code which does expect the map to behave
     * correctly. If the map has keys at unexpected positions inside it,
     * future operations may panic, loop forever, or return unexpected results,
     * potentially violating memory safety.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val oldKey = "poneyland"
     * val newKey = Holder.leak(oldKey.toOwned().intoBoxedStr())
     * map.entry(oldKey).orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> error("panic")
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         val replaced = entry.replaceKeyUnchecked(newKey)
     *         check(eq(replaced, oldKey))
     *         check(eq(entry.key(), newKey))
     *     }
     * }
     * ```
     */
    public open fun replaceKeyUnchecked(key: K): K = replace(this.elem.asMut().first, key)

    /**
     * Take the ownership of the key and value from the map.
     * Keeps the allocated memory for reuse.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // The map is empty
     * check(map.isEmpty() && map.capacity() == 0)
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         // We delete the entry from the map.
     *         check(o.removeEntry() == Pair("poneyland", 12))
     *     }
     *     else -> {}
     * }
     * check(map.containsKey("poneyland") == false)
     * // Now map hold none elements
     * check(map.isEmpty())
     * ```
     */
    public open fun removeEntry(): Pair<K, V> = this.table.table.remove(this.elem).first

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> error("panic")
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         check(entry.get() == 12)
     *     }
     * }
     * ```
     */
    public open fun get(): V = this.elem.asRef().second

    /**
     * Gets a mutable reference to the value in the entry.
     *
     * If you need a reference to the `OccupiedEntry` which may outlive the
     * destruction of the `Entry` value, see [intoMut].
     *
     * [intoMut]: #method.intoMut
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         o.getMut() += 10
     *         check(o.get() == 22)
     *         // We can use the same Entry multiple times.
     *         o.getMut() += 2
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 24)
     * ```
     */
    public open fun getMut(): V = this.elem.asMut().second

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the value in the entry
     * with a scope bound to the map itself.
     *
     * If you need multiple references to the `OccupiedEntry`, see [getMut].
     *
     * [getMut]: #method.getMut
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * val value: RustMutableReference<UInt>
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         value = entry.intoMut()
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * (value).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (10); targetPlace.write(currentValue) }
     * check(map["poneyland"] == 22)
     * ```
     */
    public open fun intoMut(): V = this.elem.asMut().second

    /**
     * Converts the `OccupiedEntry` into a reference to the key and a
     * mutable reference to the value in the entry with a scope bound to the
     * map itself.
     *
     * If you need multiple references to the `OccupiedEntry`, see [key] and
     * [getMut].
     *
     * [key]: Self.key
     * [getMut]: Self.getMut
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * val keyVal: Pair<String, RustMutableReference<UInt>>
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         keyVal = entry.intoEntry()
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * keyVal.second += 10
     * check(keyVal == Pair("poneyland", RustMutableReference(allocateNativeCell(22, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     * check(map["poneyland"] == 22)
     * ```
     */
    public open fun intoEntry(): Pair<K, V> {
        val (key, value) = this.elem.asMut()
        return Pair(key, value)
    }

    /**
     * Sets the value of the entry, and returns the entry's old value.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.insert(15) == 12)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 15)
     * ```
     */
    public open fun insert(value: V): V = replace(this.getMut(), value)

    /**
     * Takes the value out of the entry, and returns it.
     * Keeps the allocated memory for reuse.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // The map is empty
     * check(map.isEmpty() && map.capacity() == 0)
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove() == 12)
     *     }
     *     else -> {}
     * }
     * check(map.containsKey("poneyland") == false)
     * // Now map hold none elements
     * check(map.isEmpty())
     * ```
     */
    public open fun remove(): V = this.removeEntry().second

    /**
     * Provides shared access to the key and owned access to the value of
     * the entry and allows to replace or remove it based on the
     * value of the returned option.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.put("poneyland", 42)
     * val entry = when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (e) = matchVal
     *         e.replaceEntryWith({ k, v ->
     *             check(k == "poneyland")
     *             check(v == 42)
     *             v + 1
     *         })
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * when (entry) {
     *     is Entry.Occupied -> {
     *         val (e1) = entry
     *         check(e1.key() == "poneyland")
     *         check(e1.get() == 43)
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * check(map["poneyland"] == 43)
     * val entry1 = when (val matchVal1 = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (e2) = matchVal1
     *         e2.replaceEntryWith({ k, v -> null })
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * when (entry1) {
     *     is Entry.Vacant -> {
     *         val (e3) = entry1
     *         check(e3.key() == "poneyland")
     *     }
     *     is Entry.Occupied -> error("panic")
     * }
     * check(!map.containsKey("poneyland"))
     * ```
     */
    public open fun replaceEntryWith(f: (K, V) -> V?): Entry<K, V, S, A> =
        run {
            var spareKey = null
            this.table.table.replaceBucketWith(this.elem.clone(), { (key, value) ->
                when (val matchVal = f(key, value)) {
                    null -> {
                        spareKey = key
                        null
                    }
                    else -> {
                        val newValue = matchVal
                        Pair(key, newValue)
                    }
                }
            })
            if (spareKey != null) {
                val key = spareKey
                Entry.Vacant(VacantEntry(hash = this.hash, key = key, table = this.table))
            } else {
                Entry.Occupied(this)
            }
        }

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the underlying map.
     */
    public open fun intoMap(): HashMap<K, V, S, A> = this.table

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            hash: ULong,
            elem: Bucket<Pair<K, V>>,
            table: HashMap<K, V, S, A>,
        ): OccupiedEntry<K, V, S, A> = OccupiedEntry<K, V, S, A>(
            hash = hash,
            elemCtorArg = elem,
            tableCtorArg = table,
        )
    }
}

/**
 * A view into a vacant entry in a `HashMap`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashmap.Entry
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.VacantEntry
 *
 * var map = mutableMapOf()
 * val entryV: VacantEntry = when (val matchVal = map.entry("a")) {
 *     is Entry.Vacant -> {
 *         val (view) = matchVal
 *         view
 *     }
 *     is Entry.Occupied -> error("unreachable")
 * }
 * entryV.insert(10)
 * check(map["a"] == 10 && map.size == 1)
 * // Nonexistent key (insert and update)
 * when (val matchVal1 = map.entry("b")) {
 *     is Entry.Occupied -> error("unreachable")
 *     is Entry.Vacant -> {
 *         val (view1) = matchVal1
 *         val value = view1.insert(2)
 *         check(value == 2)
 *         value = 20
 *     }
 * }
 * check(map["b"] == 20 && map.size == 2)
 * ```
 */
public open class VacantEntry<K, V, S, A : Allocator> internal constructor(
    public var hash: ULong,
    public var key: K,
    tableCtorArg: HashMap<K, V, S, A>,
) {
    public var table: HashMap<K, V, S, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "VacantEntry").field(this.key()).finish()

    /**
     * Gets a reference to the key that would be used when inserting a value
     * through the `VacantEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.key

    /**
     * Take ownership of the key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.Entry
     * import hashbrown.hashmap.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> error("panic")
     *     is Entry.Vacant -> {
     *         val (v) = matchVal
     *         check(v.intoKey() == "poneyland")
     *     }
     * }
     * ```
     */
    public open fun intoKey(): K = this.key

    /**
     * Sets the value of the entry with the [VacantEntry]s key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (o) = matchVal
     *         o.insert(37)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 37)
     * ```
     */
    public open fun insert(value: V): V {
        val table = this.table.table
        val entry = table.insertEntry(this.hash, Pair(this.key, value), makeHasher<V, S>(this.table.hashBuilder))
        return entry.second
    }

    /**
     * Sets the value of the entry with the [VacantEntry]s key,
     * and returns an [OccupiedEntry].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (v) = matchVal
     *         val o = v.insertEntry(37)
     *         check(o.get() == 37)
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun insertEntry(value: V): OccupiedEntry<K, V, S, A> {
        val elem = this.table.table.insert(this.hash, Pair(this.key, value), makeHasher<V, S>(this.table.hashBuilder))
        return OccupiedEntry(hash = this.hash, elem = elem, table = this.table)
    }

    /**
     * Converts the `VacantEntry` into a mutable reference to the underlying map.
     */
    public open fun intoMap(): HashMap<K, V, S, A> = this.table

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            hash: ULong,
            key: K,
            table: HashMap<K, V, S, A>,
        ): VacantEntry<K, V, S, A> = VacantEntry<K, V, S, A>(
            hash = hash,
            key = key,
            tableCtorArg = table,
        )
    }
}

/**
 * A view into a single entry in a map, which may either be vacant or occupied,
 * with any borrowed form of the map's key type.
 *
 * This `sealed class` is constructed from the [entryRef] method on [HashMap].
 *
 * [Hash] and [Eq] on the borrowed form of the map's key type *must* match those
 * for the key type. It also require that key may be constructed from the borrowed
 * form through the [ToOwned] trait.
 *
 * [entryRef]: HashMap.entryRef
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashmap.EntryRef
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.OccupiedEntry
 *
 * var map = mutableMapOf()
 * map.extend(listOf(Pair("a".toOwned(), 10), Pair("b".into(), 20), Pair("c".into(), 30)))
 * check(map.size == 3)
 * // Existing key (insert)
 * val key = "a"
 * val entry: EntryRef = map.entryRef(key)
 * val rawO: OccupiedEntry = entry.insert(1)
 * check(map.size == 3)
 * // Nonexistent key (insert)
 * map.entryRef("d").insert(4)
 * // Existing key (orInsert)
 * val v = map.entryRef("b").orInsert(2)
 * check(replace(v, 2) == 20)
 * // Nonexistent key (orInsert)
 * map.entryRef("e").orInsert(5)
 * // Existing key (orInsertWith)
 * val v1 = map.entryRef("c").orInsertWith({ 3 })
 * check(replace(v1, 3) == 30)
 * // Nonexistent key (orInsertWith)
 * map.entryRef("f").orInsertWith({ 6 })
 * println("Our HashMap: $map")
 * for ((key1, value) in (listOf("a", "b", "c", "d", "e", "f").intoIter().zip(1..6))) {
 *     check(map[key1] == value)
 * }
 * check(map.size == 6)
 * ```
 */
public sealed class EntryRef<K, Q, V, S, A> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.hashmap.EntryRef
     * import hashbrown.hashmap.HashMap
     *
     * var map: HashMap = listOf(Pair("a".toOwned(), 100), Pair("b".into(), 200)).into()
     * when (val matchVal = map.entryRef("a")) {
     *     is EntryRef.Vacant -> error("unreachable")
     *     is EntryRef.Occupied -> {}
     * }
     * ```
     */
    public class Occupied<K, Q, V, S, A> internal constructor(valueCtorArg: OccupiedEntry<K, V, S, A>) : EntryRef<K, Q, V, S, A>() {
        public var value: OccupiedEntry<K, V, S, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<K, V, S, A> = this.value

        public fun copy(value: OccupiedEntry<K, V, S, A> = this.value): Occupied<K, Q, V, S, A> = Occupied<K, Q, V, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, Q, V, S, A> invoke(
                value: OccupiedEntry<K, V, S, A>,
            ): Occupied<K, Q, V, S, A> = Occupied<K, Q, V, S, A>(
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
     * import hashbrown.hashmap.EntryRef
     * import hashbrown.hashmap.HashMap
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * when (val matchVal = map.entryRef("a")) {
     *     is EntryRef.Occupied -> error("unreachable")
     *     is EntryRef.Vacant -> {}
     * }
     * ```
     */
    public class Vacant<K, Q, V, S, A> internal constructor(valueCtorArg: VacantEntryRef<K, Q, V, S, A>) : EntryRef<K, Q, V, S, A>() {
        public var value: VacantEntryRef<K, Q, V, S, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntryRef<K, Q, V, S, A> = this.value

        public fun copy(value: VacantEntryRef<K, Q, V, S, A> = this.value): Vacant<K, Q, V, S, A> = Vacant<K, Q, V, S, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, Q, V, S, A> invoke(
                value: VacantEntryRef<K, Q, V, S, A>,
            ): Vacant<K, Q, V, S, A> = Vacant<K, Q, V, S, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is EntryRef.Vacant -> {
                val (v) = this
                DebugTuple(f, "EntryRef").field(v).finish()
            }
            is EntryRef.Occupied -> {
                val (o) = this
                DebugTuple(f, "EntryRef").field(o).finish()
            }
        }

    /**
     * Sets the value of the entry, and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val entry = map.entryRef("horseyland").insert(37)
     * check(entry.key() == "horseyland")
     * ```
     */
    public open fun insert(value: V): OccupiedEntry<K, V, S, A> =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.insert(value)
                entry
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.insertEntry(value)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default if empty, and returns
     * a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // nonexistent key
     * map.entryRef("poneyland").orInsert(3)
     * check(map["poneyland"] == 3)
     * // existing key
     * map.entryRef("poneyland").orInsert(10) *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsert(default: V): V =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.insert(default)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the result of the default function if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * // nonexistent key
     * map.entryRef("poneyland").orInsertWith({ 3 })
     * check(map["poneyland"] == 3)
     * // existing key
     * map.entryRef("poneyland").orInsertWith({ 10 }) *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsertWith(default: () -> V): V =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.insert(default())
            }
        }

    /**
     * Ensures a value is in the entry by inserting, if empty, the result of the default function.
     * This method allows for generating key-derived values for insertion by providing the default
     * function an access to the borrower form of the key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, Int> = mutableMapOf()
     * // nonexistent key
     * map.entryRef("poneyland").orInsertWithKey({ key -> key.chars().count() })
     * check(map["poneyland"] == 9)
     * // existing key
     * map.entryRef("poneyland").orInsertWithKey({ key -> key.chars().count() * 10 }) *= 2
     * check(map["poneyland"] == 18)
     * ```
     */
    public open fun orInsertWithKey(default: (Q) -> V): V =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                val value = default(entry1.key)
                entry1.insert(value)
            }
        }

    /**
     * Returns a reference to this entry's key.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entryRef("poneyland").orInsert(3)
     * // existing key
     * check(map.entryRef("poneyland").key() == "poneyland")
     * // nonexistent key
     * check(map.entryRef("horseland").key() == "horseland")
     * ```
     */
    public open fun key(): Q =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.key().borrow()
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.key()
            }
        }

    /**
     * Provides in-place mutable access to an occupied entry before any
     * potential inserts into the map.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entryRef("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 42)
     * map.entryRef("poneyland").andModify({ e ->
     *     e += 1
     * }).orInsert(42)
     * check(map["poneyland"] == 43)
     * ```
     */
    public open fun andModify(f: (V) -> Unit): EntryRef<K, Q, V, S, A> =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                f(entry.getMut())
                EntryRef.Occupied(entry)
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                EntryRef.Vacant(entry1)
            }
        }

    /**
     * Converts the `EntryRef` into a mutable reference to the underlying map.
     */
    public open fun intoMap(): HashMap<K, V, S, A> =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.table
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.table
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default value if empty,
     * and returns a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt?> = mutableMapOf()
     * // nonexistent key
     * map.entryRef("poneyland").orDefault()
     * check(map["poneyland"] == null)
     * map.put("horseland".toString(), 3)
     * // existing key
     * check(map.entryRef("horseland").orDefault() == 3)
     * ```
     */
    public open fun orDefault(defaultVFn: () -> V): V =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.insert(defaultVFn())
            }
        }

    /**
     * Ensures a value is in the entry by inserting the default value if empty,
     * and returns an [OccupiedEntry].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt?> = mutableMapOf()
     * // nonexistent key
     * val entry = map.entryRef("poneyland").orDefaultEntry()
     * check(entry.key() == "poneyland")
     * check(entry.get() == null)
     * // existing key
     * map.put("horseland".toString(), 3)
     * val entry1 = map.entryRef("horseland").orDefaultEntry()
     * check(entry1.key() == "horseland")
     * check(entry1.get() == 3)
     * ```
     */
    public open fun orDefaultEntry(defaultVFn: () -> V): OccupiedEntry<K, V, S, A> =
        when (this) {
            is EntryRef.Occupied -> {
                val (entry) = this
                entry
            }
            is EntryRef.Vacant -> {
                val (entry1) = this
                entry1.insertEntry(defaultVFn())
            }
        }
}

/**
 * A view into a vacant entry in a `HashMap`.
 * It is part of the [EntryRef] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashmap.EntryRef
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.VacantEntryRef
 *
 * var map = mutableMapOf()
 * val entryV: VacantEntryRef = when (val matchVal = map.entryRef("a")) {
 *     is EntryRef.Vacant -> {
 *         val (view) = matchVal
 *         view
 *     }
 *     is EntryRef.Occupied -> error("unreachable")
 * }
 * entryV.insert(10)
 * check(map["a"] == 10 && map.size == 1)
 * // Nonexistent key (insert and update)
 * when (val matchVal1 = map.entryRef("b")) {
 *     is EntryRef.Occupied -> error("unreachable")
 *     is EntryRef.Vacant -> {
 *         val (view1) = matchVal1
 *         val value = view1.insert(2)
 *         check(value == 2)
 *         value = 20
 *     }
 * }
 * check(map["b"] == 20 && map.size == 2)
 * ```
 */
public open class VacantEntryRef<K, Q, V, S, A : Allocator> internal constructor(
    public var hash: ULong,
    public var key: Q,
    tableCtorArg: HashMap<K, V, S, A>,
) {
    public var table: HashMap<K, V, S, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "VacantEntryRef").field(this.key()).finish()

    /**
     * Gets a reference to the key that would be used when inserting a value
     * through the `VacantEntryRef`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val key: String = "poneyland"
     * check(map.entryRef(key).key() == "poneyland")
     * ```
     */
    public open fun key(): Q = this.key

    /**
     * Sets the value of the entry with the `VacantEntryRef`s key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.EntryRef
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * val key: String = "poneyland"
     * when (val matchVal = map.entryRef(key)) {
     *     is EntryRef.Vacant -> {
     *         val (o) = matchVal
     *         o.insert(37)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 37)
     * ```
     */
    public open fun insert(value: V): V {
        val table = this.table.table
        val entry = table.insertEntry(this.hash, Pair(this.key.toOwned(), value), makeHasher<V, S>(this.table.hashBuilder))
        return entry.second
    }

    /**
     * Sets the key and value of the entry and returns a mutable reference to
     * the inserted value.
     *
     * Unlike [VacantEntryRef.insert], this method allows the key to be
     * explicitly specified, which is useful for key types that don't implement
     * `ToOwned`.
     *
     * # Panics
     *
     * This method panics if `key` is not equivalent to the key used to create
     * the `VacantEntryRef`.
     *
     * # Example
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.EntryRef
     *
     * var map = mutableMapOf()
     * val k = Pair("c".toString(), "C".toString())
     * val v = when (val matchVal = map.entryRef(k)) {
     *     // Insert cannot be used here because tuples do not implement ToOwned.
     *     // However this works because we can manually clone instead.
     *     is EntryRef.Vacant -> {
     *         val (r) = matchVal
     *         r.insertWithKey(k.clone(), 'c')
     *     }
     *     // In this branch we avoid the clone.
     *     is EntryRef.Occupied -> {
     *         val (r1) = matchVal
     *         r1.intoMut()
     *     }
     * }
     * check(v == 'c')
     * ```
     */
    public open fun insertWithKey(key: K, value: V): V = this.insertEntryWithKey(key, value).intoMut()

    /**
     * Sets the key and value of the entry and returns a mutable reference to
     * the inserted value, without checking the equivalence of the key.
     *
     * See [`insertWithKey`](Self.insertWithKey) for more information.
     *
     * # Safety
     *
     * This operation is safe if the keys are equivalent.
     *
     * Additionally, this operation (and following operations) are guaranteed
     * to not violate memory safety.
     *
     * However this operation is still unchecked because the resulting `HashMap`
     * may be passed to unchecked code which does expect the map to behave
     * correctly. If the map has keys at unexpected positions inside it,
     * future operations may panic, loop forever, or return unexpected results,
     * potentially violating memory safety.
     *
     * # Example
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.EntryRef
     *
     * var map = mutableMapOf()
     * val k = Pair("c".toString(), "C".toString())
     * val v = when (val matchVal = map.entryRef(k)) {
     *     // SAFETY: We trust the `Clone` implementation to return an equivalent value
     *     is EntryRef.Vacant -> {
     *         val (r) = matchVal
     *         r.insertWithKeyUnchecked(k.clone(), 'c')
     *     }
     *     // In this branch we avoid the clone.
     *     is EntryRef.Occupied -> {
     *         val (r1) = matchVal
     *         r1.intoMut()
     *     }
     * }
     * check(v == 'c')
     * ```
     */
    public open fun insertWithKeyUnchecked(key: K, value: V): V {
        // SAFETY: Guaranteed by caller.
        return this.insertEntryWithKeyUnchecked(key, value).intoMut()
    }

    /**
     * Sets the value of the entry with the [VacantEntryRef]s key,
     * and returns an [OccupiedEntry].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.EntryRef
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entryRef("poneyland")) {
     *     is EntryRef.Vacant -> {
     *         val (v) = matchVal
     *         val o = v.insertEntry(37)
     *         check(o.get() == 37)
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun insertEntry(value: V): OccupiedEntry<K, V, S, A> {
        val elem = this.table.table.insert(this.hash, Pair(this.key.toOwned(), value), makeHasher<V, S>(this.table.hashBuilder))
        return OccupiedEntry(hash = this.hash, elem = elem, table = this.table)
    }

    /**
     * Sets the key and value of the entry and returns an [OccupiedEntry].
     *
     * Unlike [VacantEntryRef.insertEntry], this method allows the key to
     * be explicitly specified, which is useful for key types that don't
     * implement `ToOwned`.
     *
     * # Panics
     *
     * This method panics if `key` is not equivalent to the key used to create
     * the `VacantEntryRef`.
     *
     * # Example
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.EntryRef
     *
     * var map = mutableMapOf()
     * val k = Pair("c".toString(), "C".toString())
     * val r2 = when (val matchVal = map.entryRef(k)) {
     *     // Insert cannot be used here because tuples do not implement ToOwned.
     *     // However this works because we can manually clone instead.
     *     is EntryRef.Vacant -> {
     *         val (r) = matchVal
     *         r.insertEntryWithKey(k.clone(), 'c')
     *     }
     *     // In this branch we avoid the clone.
     *     is EntryRef.Occupied -> {
     *         val (r1) = matchVal
     *         r1
     *     }
     * }
     * check(r2.get() == 'c')
     * ```
     */
    public open fun insertEntryWithKey(key: K, value: V): OccupiedEntry<K, V, S, A> {
        check((this.key).equivalent(key)) { "key used for Entry creation is not equivalent to the one used for insertion" }
        // SAFETY: We checked equivalence first.
        return this.insertEntryWithKeyUnchecked(key, value)
    }

    /**
     * Sets the key and value of the entry and returns an [OccupiedEntry],
     * without checking the equivalence of the key.
     *
     * See [`insertEntryWithKey`](Self.insertEntryWithKey) for more information.
     *
     * # Safety
     *
     * This operation is safe if the keys are equivalent.
     *
     * Additionally, this operation (and following operations) are guaranteed
     * to not violate memory safety.
     *
     * However this operation is still unchecked because the resulting `HashMap`
     * may be passed to unchecked code which does expect the map to behave
     * correctly. If the map has keys at unexpected positions inside it,
     * future operations may panic, loop forever, or return unexpected results,
     * potentially violating memory safety.
     *
     * # Example
     *
     * ```kotlin
     * import hashbrown.HashMap
     * import hashbrown.hashmap.EntryRef
     *
     * var map = mutableMapOf()
     * val k = Pair("c".toString(), "C".toString())
     * val r2 = when (val matchVal = map.entryRef(k)) {
     *     // SAFETY: We trust the `Clone` implementation to return an equivalent key
     *     is EntryRef.Vacant -> {
     *         val (r) = matchVal
     *         r.insertEntryWithKeyUnchecked(k.clone(), 'c')
     *     }
     *     // In this branch we avoid the clone.
     *     is EntryRef.Occupied -> {
     *         val (r1) = matchVal
     *         r1
     *     }
     * }
     * check(r2.get() == 'c')
     * ```
     */
    public open fun insertEntryWithKeyUnchecked(key: K, value: V): OccupiedEntry<K, V, S, A> {
        val elem = this.table.table.insert(this.hash, Pair(key, value), makeHasher<V, S>(this.table.hashBuilder))
        return OccupiedEntry(hash = this.hash, elem = elem, table = this.table)
    }

    /**
     * Converts the `VacantEntryRef` into a mutable reference to the underlying map.
     */
    public open fun intoMap(): HashMap<K, V, S, A> = this.table

    public companion object {
        public operator fun <K, Q, V, S, A : Allocator> invoke(
            hash: ULong,
            key: Q,
            table: HashMap<K, V, S, A>,
        ): VacantEntryRef<K, Q, V, S, A> = VacantEntryRef<K, Q, V, S, A>(
            hash = hash,
            key = key,
            tableCtorArg = table,
        )
    }
}

/**
 * The error returned by [`tryInsert`](HashMap.tryInsert) when the key already exists.
 *
 * Contains the occupied entry, and the value that was not inserted.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.hashmap.HashMap
 * import hashbrown.hashmap.OccupiedError
 *
 * var map: HashMap = listOf(Pair("a", 10), Pair("b", 20)).into()
 * // tryInsert method returns mutable reference to the value if keys are vacant,
 * // but if the map did have key present, nothing is updated, and the provided
 * // value is returned inside `Err(_)` variant
 * run {
 *     val matchVal = map.tryInsert("a", 100)
 *     when {
 *         matchVal is Err -> {
 *             val entry = matchVal.value.entry
 *             val value = matchVal.value.value
 *             check(entry.key() == "a")
 *             check(value == 100)
 *             check(entry.insert(100) == 10)
 *         }
 *         else -> error("unreachable")
 *     }
 * }
 * check(map["a"] == 100)
 * ```
 */
public open class OccupiedError<K, V, S, A : Allocator> internal constructor(
    entryCtorArg: OccupiedEntry<K, V, S, A>,
    /**
     * The value which was not inserted, because the entry was already occupied.
     */
    public var value: V,
) {
    /**
     * The entry in the map that was already occupied.
     */
    public var entry: OccupiedEntry<K, V, S, A> = entryCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "OccupiedError").field("key", this.entry.key()).field("old_value", this.entry.get()).field("new_value", this.value).finish()

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = run { f.append("failed to insert ${this.value}, key ${this.entry.key()} already exists with value ${this.entry.get()}"); Result.Ok(Unit) }

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            entry: OccupiedEntry<K, V, S, A>,
            value: V,
        ): OccupiedError<K, V, S, A> = OccupiedError<K, V, S, A>(
            entryCtorArg = entry,
            value = value,
        )
    }
}

public fun assertCovariance() {
    fun mapKey(v: HashMap<String, UByte, DefaultHashBuilder, Global>): HashMap<String, UByte, DefaultHashBuilder, Global> = v
    fun mapVal(v: HashMap<UByte, String, DefaultHashBuilder, Global>): HashMap<UByte, String, DefaultHashBuilder, Global> = v
    fun iterKey(v: Iter<String, UByte>): Iter<String, UByte> = v
    fun iterVal(v: Iter<UByte, String>): Iter<UByte, String> = v
    fun <A : Allocator> intoIterKey(v: IntoIter<String, UByte, A>): IntoIter<String, UByte, A> = v
    fun <A : Allocator> intoIterVal(v: IntoIter<UByte, String, A>): IntoIter<UByte, String, A> = v
    fun keysKey(v: Keys<String, UByte>): Keys<String, UByte> = v
    fun keysVal(v: Keys<UByte, String>): Keys<UByte, String> = v
    fun valuesKey(v: Values<String, UByte>): Values<String, UByte> = v
    fun valuesVal(v: Values<UByte, String>): Values<UByte, String> = v
    fun drain(d: Drain<String, String, Global>): Drain<String, String, Global> = d
}
