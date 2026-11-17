// port-lint: source collections/hash/map.rs
package io.github.kotlinmania.rustlang.std.collections.hash.map

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.rustcentry.RustcEntry
import io.github.kotlinmania.rustlang.hashbrown.rustcentry.RustcOccupiedEntry
import io.github.kotlinmania.rustlang.hashbrown.rustcentry.RustcVacantEntry
import io.github.kotlinmania.rustlang.std.collections.hash.map.Entry.*
import io.github.kotlinmania.rustlang.std.hash.random.RandomState
import io.github.kotlinmania.rustlang.hashbrown.hashMap as base

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
 * A [hash map] implemented with quadratic probing and SIMD lookup.
 *
 * By default, `HashMap` uses a hashing algorithm selected to provide
 * resistance against HashDoS attacks. The algorithm is randomly seeded, and a
 * reasonable best-effort is made to generate this seed from a high quality,
 * secure source of randomness provided by the host without blocking the
 * program. Because of this, the randomness of the seed depends on the output
 * quality of the system's random number generator when the seed is created.
 * In particular, seeds generated when the system's entropy pool is abnormally
 * low such as during system boot may be of a lower quality.
 *
 * The default hashing algorithm is currently SipHash 1-3, though this is
 * subject to change at any point in the future. While its performance is very
 * competitive for medium sized keys, other hashing algorithms will outperform
 * it for small keys such as integers as well as large keys such as long
 * strings, though those algorithms will typically *not* protect against
 * attacks such as HashDoS.
 *
 * The hashing algorithm can be replaced on a per-`HashMap` basis using the
 * [default], [withHasher], and [withCapacityAndHasher] methods.
 * There are many alternative [hashing algorithms available on crates.io].
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
 * Violating this property is a logic error.
 *
 * It is also a logic error for a key to be modified in such a way that the key's
 * hash, as determined by the [Hash] trait, or its equality, as determined by
 * the [Eq] trait, changes while it is in the map. This is normally only
 * possible through [Cell], [RefCell], global state, I/O, or unchecked code.
 *
 * The behavior resulting from either logic error is not specified, but will
 * be encapsulated to the `HashMap` that observed the logic error and not
 * result in undefined behavior. This could include panics, incorrect results,
 * aborts, memory leaks, and non-termination.
 *
 * The hash table implementation is a Rust port of Google's [SwissTable].
 * The original C++ version of SwissTable can be found [here], and this
 * [CppCon talk] gives an overview of how the algorithm works.
 *
 * [hashing algorithms available on crates.io]: https://crates.io/keywords/hasher
 * [SwissTable]: https://abseil.io/blog/20180927-swisstables
 * [here]: https://github.com/abseil/abseil-cpp/blob/master/absl/container/internal/rawHashSet.h
 * [CppCon talk]: https://www.youtube.com/watch?v=ncHmEUmJZf4
 *
 * # Examples
 *
 * ```kotlin
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
 *     println("$book: \"$review\"")
 * }
 * ```
 *
 * A `HashMap` with a known list of items can be initialized from an array:
 *
 * ```kotlin
 * val solarDistance = mutableMapOf("Mercury" to 0.4, "Venus" to 0.7, "Earth" to 1.0, "Mars" to 1.5)
 * ```
 *
 * ## `Entry` API
 *
 * `HashMap` implements an [`Entry` API](#method.entry), which allows
 * for complex methods of getting, setting, updating and removing keys and
 * their values:
 *
 * ```kotlin
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
 * // modify an entry before an insert with in-place mutation
 * playerStats.entry("mana").andModify({ mana -> mana += 200 }).orInsert(100)
 * ```
 *
 * ## Usage with custom key types
 *
 * The easiest way to use `HashMap` with a custom key type is to derive [Eq] and [Hash].
 * We must also derive [PartialEq].
 *
 * [default]: Default.default
 * [withHasher]: Self.withHasher
 * [withCapacityAndHasher]: Self.withCapacityAndHasher
 *
 * ```kotlin
 * open class Viking(public var name: String, public var country: String) {
 *     / **
 *      * Creates a new Viking.
 *      * /
 *     public open fun new(name: String, country: String): Viking {
 *         return Viking(name = name.toString(), country = country.toString())
 *     }
 * }
 * // Use a HashMap to store the vikings' health points.
 * val vikings = mutableMapOf(Viking.new("Einar", "Norway") to 25, Viking.new("Olaf", "Denmark") to 24, Viking.new("Harald", "Iceland") to 12)
 * // Use derived implementation to print the status of the vikings.
 * for ((viking, health) in vikings) {
 *     println("$viking has $health hp")
 * }
 * ```
 *
 * # Usage in `const` and `static`
 *
 * As explained above, `HashMap` is randomly seeded: each `HashMap` instance uses a different seed,
 * which means that `HashMap.new` normally cannot be used in a `const` or `static` initializer.
 *
 * However, if you need to use a `HashMap` in a `const` or `static` initializer while retaining
 * random seed generation, you can wrap the `HashMap` in [LazyLock].
 *
 * Alternatively, you can construct a `HashMap` in a `const` or `static` initializer using a different
 * hasher that does not rely on a random seed. **Be aware that a `HashMap` created this way is not
 * resistant to HashDoS attacks!**
 *
 * ```kotlin
 * import std.hash.BuildHasherDefault
 * import std.hash.DefaultHasher
 * import std.sync.LazyLock
 * import std.sync.Mutex
 *
 * // HashMaps with a fixed, non-random hasher
 * val NONRANDOM_EMPTY_MAP: MutableMap<String, List<Int>> = HashMap.withHasher(BuildHasherDefault.new())
 * var NONRANDOM_MAP: Mutex<MutableMap<String, List<Int>>> = Mutex.new(HashMap.withHasher(BuildHasherDefault.new()))
 * // HashMaps using LazyLock to retain random seeding
 * val RANDOM_EMPTY_MAP: LazyLock<MutableMap<String, List<Int>>> = LazyLock.new(HashMap.new)
 * var RANDOM_MAP: LazyLock<Mutex<MutableMap<String, List<Int>>>> = LazyLock.new({ Mutex.new(mutableMapOf()) })
 * ```
 */
public open class HashMap<K, V, S, A : Allocator> internal constructor(
    baseCtorArg: io.github.kotlinmania.rustlang.hashbrown.HashMap<K, V, S, A>,
) : Iterable<Pair<K, V>> {
    public var base: io.github.kotlinmania.rustlang.hashbrown.HashMap<K, V, S, A> = baseCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = this.intoIter().iterator()

    /**
     * Returns the number of elements the map can hold without reallocating.
     *
     * This number is a lower bound; the `HashMap<K, V>` might be able to hold
     * more, but is guaranteed to be able to hold at least this many.
     *
     * # Examples
     *
     * ```kotlin
     * val map: MutableMap<Int, Int> = mutableMapOf()
     * check(map.capacity() >= 100)
     * ```
     */
    public open fun capacity(): Int = this.base.capacity()

    /**
     * An iterator visiting all keys in arbitrary order.
     * The iterator element type is `K`.
     *
     * # Examples
     *
     * ```kotlin
     * val map: MutableMap<String, Int> = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * var values: Vec = map.keys().copied().collect()
     * values.sort()
     * check(values == mutableListOf("a", "b", "c"))
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over keys takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun keys(): Keys<K, V> = Keys(inner = this.iter())

    /**
     * Creates a consuming iterator visiting all the keys in arbitrary order.
     * The map cannot be used after calling this.
     * The iterator element type is `K`.
     *
     * # Examples
     *
     * ```kotlin
     * val map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * var vec: List<String> = map.intoKeys().toMutableList()
     * // The `IntoKeys` iterator produces keys in arbitrary order, so the
     * // keys must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf("a", "b", "c"))
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over keys takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun intoKeys(): IntoKeys<K, V, A> = IntoKeys(inner = this.intoIter())

    /**
     * An iterator visiting all values in arbitrary order.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * val map: MutableMap<String, Int> = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * var values: Vec = map.values().copied().collect()
     * values.sort()
     * check(values == mutableListOf(1, 2, 3))
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over values takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun values(): Values<K, V> = Values(inner = this.iter())

    /**
     * An iterator visiting all values mutably in arbitrary order.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * for (value in map.valuesMut()) {
     *     value += 10
     * }
     * check(map.get("a") == 11)
     * check(map.get("b") == 12)
     * check(map.get("c") == 13)
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over values takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun valuesMut(): ValuesMut<K, V> = ValuesMut(inner = this.iterMut())

    /**
     * Creates a consuming iterator visiting all the values in arbitrary order.
     * The map cannot be used after calling this.
     * The iterator element type is `V`.
     *
     * # Examples
     *
     * ```kotlin
     * val map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * var vec: List<Int> = map.intoValues().toMutableList()
     * // The `IntoValues` iterator produces values in arbitrary order, so
     * // the values must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(1, 2, 3))
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over values takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun intoValues(): IntoValues<K, V, A> = IntoValues(inner = this.intoIter())

    /**
     * An iterator visiting all key-value pairs in arbitrary order.
     * The iterator element type is `(K, V)`.
     *
     * # Examples
     *
     * ```kotlin
     * val map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * var count = 0
     * for ((key, value) in map.iter()) {
     *     count += 1
     * }
     * check(count == 3)
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over map takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun iter(): Iter<K, V> = Iter(base = this.base.iter())

    /**
     * An iterator visiting all key-value pairs in arbitrary order,
     * with mutable references to the values.
     * The iterator element type is `(K, V)`.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * // Update all values
     * for ((_, value) in map.iterMut()) {
     *     value *= 2
     * }
     * check(map.get("a") == 2)
     * check(map.get("b") == 4)
     * check(map.get("c") == 6)
     * ```
     *
     * # Performance
     *
     * In the current implementation, iterating over map takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun iterMut(): IterMut<K, V> = IterMut(base = this.base.iterMut())

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
    public open fun len(): Int = this.base.len()

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
    public open fun isEmpty(): Boolean = this.base.isEmpty()

    /**
     * Clears the map, returning all key-value pairs as an iterator. Keeps the
     * allocated memory for reuse.
     *
     * If the returned iterator is dropped before being fully consumed, it
     * drops the remaining key-value pairs. The returned iterator keeps a
     * mutable borrow on the map to optimize its implementation.
     *
     * # Examples
     *
     * ```kotlin
     * var a = mutableMapOf()
     * a.put(1, "a")
     * a.put(2, "b")
     * for ((k, v) in a.drain().take(1)) {
     *     check(k == 1 || k == 2)
     *     check(v == "a" || v == "b")
     * }
     * check(a.isEmpty())
     * ```
     */
    public open fun drain(): Drain<K, V, A> = Drain(base = this.base.drain())

    /**
     * Creates an iterator which uses a closure to determine if an element (key-value pair) should be removed.
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
     * Use [retain] with a negated predicate if you do not need the returned iterator.
     *
     * [retain]: HashMap.retain
     *
     * # Examples
     *
     * Splitting a map into even and odd keys, reusing the original map:
     *
     * ```kotlin
     * var map: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x) }).toMap(LinkedHashMap())
     * val extracted: MutableMap<Int, Int> = map.extractIf({ k, v -> k % 2 == 0 }).toMap(LinkedHashMap())
     * var evens = extracted.keys().copied().collect<Vec>()
     * var odds = map.keys().copied().collect<Vec>()
     * evens.sort()
     * odds.sort()
     * check(evens == mutableListOf(0, 2, 4, 6))
     * check(odds == mutableListOf(1, 3, 5, 7))
     * ```
     */
    public open fun extractIf(pred: (K, V) -> Boolean): ExtractIf<K, V, (K, V) -> Boolean, A> = ExtractIf(base = this.base.extractIf(pred))

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all pairs `(k, v)` for which `f(k, v)` returns `false`.
     * The elements are visited in unsorted (and unspecified) order.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<Int, Int> = (0 until 8).map({ x -> Pair(x, x * 10) }).toMap(LinkedHashMap())
     * map.retain({ k -> k % 2 == 0 })
     * check(map.size == 4)
     * ```
     *
     * # Performance
     *
     * In the current implementation, this operation takes O(capacity) time
     * instead of O(len) because it internally visits empty buckets too.
     */
    public open fun retain(f: (K, V) -> Boolean) {
        this.base.retain(f)
    }

    /**
     * Clears the map, removing all key-value pairs. Keeps the allocated memory
     * for reuse.
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
        this.base.clear()
    }

    /**
     * Returns a reference to the map's [BuildHasher].
     *
     * # Examples
     *
     * ```kotlin
     * import std.hash.RandomState
     *
     * val hasher = RandomState.new()
     * val map: MutableMap<Int, Int> = HashMap.withHasher(hasher)
     * val hasher1: RandomState = map.hasher()
     * ```
     */
    public open fun hasher(): S = this.base.hasher()

    /**
     * Reserves capacity for at least `additional` more elements to be inserted
     * in the `HashMap`. The collection may reserve more space to speculatively
     * avoid frequent reallocations. After calling `reserve`,
     * capacity will be greater than or equal to `self.len() + additional`.
     * Does nothing if capacity is already sufficient.
     *
     * # Panics
     *
     * Panics if the new allocation size overflows [usize].
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.reserve(10)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.base.reserve(additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the `HashMap`. The collection may reserve more space to speculatively
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
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.tryReserve(10).expect("why is the test harness OOMing on a handful of bytes?")
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.base.tryReserve(additional).mapErr(::mapTryReserveError)

    /**
     * Shrinks the capacity of the map as much as possible. It will drop
     * down as much as possible while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<Int, Int> = mutableMapOf()
     * map.put(1, 2)
     * map.put(3, 4)
     * check(map.capacity() >= 100)
     * map.shrinkToFit()
     * check(map.capacity() >= 2)
     * ```
     */
    public open fun shrinkToFit() {
        this.base.shrinkToFit()
    }

    /**
     * Shrinks the capacity of the map with a lower limit. It will drop
     * down no lower than the supplied limit while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<Int, Int> = mutableMapOf()
     * map.put(1, 2)
     * map.put(3, 4)
     * check(map.capacity() >= 100)
     * map.shrinkTo(10)
     * check(map.capacity() >= 10)
     * map.shrinkTo(0)
     * check(map.capacity() >= 2)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.base.shrinkTo(minCapacity)
    }

    /**
     * Gets the given key's corresponding entry in the map for in-place manipulation.
     *
     * # Examples
     *
     * ```kotlin
     * var letters = mutableMapOf()
     * for (ch in "a short treatise on fungi".chars()) {
     *     letters.entry(ch).andModify({ counter -> counter += 1 }).orInsert(1)
     * }
     * check(letters['s'] == 2)
     * check(letters['t'] == 3)
     * check(letters['u'] == 1)
     * check(letters.get('y') == null)
     * ```
     */
    public open fun entry(key: K): Entry<K, V, A> = mapEntry(this.base.rustcEntry(key))

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
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.get(1) == "a")
     * check(map.get(2) == null)
     * ```
     */
    public open fun <Q> get(k: Q): V? = this.base.get(k)

    /**
     * Returns the key-value pair corresponding to the supplied key. This is
     * potentially useful:
     * - for key types where non-identical keys can be considered equal;
     * - for getting the `K` stored key value from a borrowed `Q` lookup key; or
     * - for getting a reference to a key with the same scope as the collection.
     *
     * The supplied key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * open class S(public var id: UInt, public var name: String) {
     *     public open fun clone(): S = S(id, name)
     *
     *     public open fun eq(other: S): Boolean {
     *         return this.id == other.id
     *     }
     *
     *     public override fun hashCode(): Int {
     *         var state = 0
     *         state = 31 * state + (this.id).hashCode()
     *         return state
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
    public open fun <Q> getKeyValue(k: Q): Pair<K, V>? = this.base.getKeyValue(k)

    /**
     * Attempts to get mutable references to `N` values in the map at once.
     *
     * Returns an array of length `N` with the results of each query. For soundness, at most one
     * mutable reference will be returned to any value. `None` will be used if the key is missing.
     *
     * This method performs a check to ensure there are no duplicate keys, which currently has a time-complexity of O(n^2),
     * so be careful when passing many keys.
     *
     * # Panics
     *
     * Panics if any keys are overlapping.
     *
     * # Examples
     *
     * ```kotlin
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
     * var libraries = mutableMapOf()
     * libraries.put("Athenæum".toString(), 1807)
     * // Duplicate keys panic
     * val got = libraries.getDisjointMut(listOf("Athenæum", "Athenæum"))
     * ```
     */
    public open fun <Q> getDisjointMut(ks: List<Q>, n: Int): List<V> = this.base.getDisjointMut(ks)

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
    public open fun <Q> getDisjointUncheckedMut(ks: List<Q>, n: Int): List<V> = this.base.getDisjointUncheckedMut(ks)

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
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.containsKey(1) == true)
     * check(map.containsKey(2) == false)
     * ```
     */
    public open fun <Q> containsKey(k: Q): Boolean = this.base.containsKey(k)

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
    public open fun <Q> getMut(k: Q): V? = this.base.getMut(k)

    /**
     * Inserts a key-value pair into the map.
     *
     * If the map did not have this key present, [None] is returned.
     *
     * If the map did have this key present, the value is updated, and the old
     * value is returned. The key is not updated, though; this matters for
     * types that can be `==` without being identical. See the [module-level
     * documentation] for more.
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
    public open fun insert(k: K, v: V): V? = this.base.insert(k, v)

    /**
     * Tries to insert a key-value pair into the map, and returns
     * a mutable reference to the value in the entry.
     *
     * If the map already had this key present, nothing is updated, and
     * an error containing the occupied entry, key, and the value is returned.
     *
     * # Examples
     *
     * Basic usage:
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
    public open fun tryInsert(key: K, value: V): Result<V, OccupiedError<K, V, A>> =
        run {
            val matchVal = this.base.rustcTryInsert(key, value)
            when {
                matchVal is Result.Ok -> {
                    val value1 = matchVal.value
                    Result.Ok<V, OccupiedError<K, V, A>>(value1)
                }
                else -> {
                    Result.Err<V, OccupiedError<K, V, A>>(OccupiedError(entry = OccupiedEntry(base = entry), key = key, value = value))
                }
            }
        }

    /**
     * Removes a key from the map, returning the value at the key if the key
     * was previously in the map.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
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
    public open fun <Q> remove(k: Q): V? = this.base.remove(k)

    /**
     * Removes a key from the map, returning the stored key and value if the
     * key was previously in the map.
     *
     * The key may be any borrowed form of the map's key type, but
     * [Hash] and [Eq] on the borrowed form *must* match those for
     * the key type.
     *
     * # Examples
     *
     * ```kotlin
     * var map = mutableMapOf()
     * map.put(1, "a")
     * check(map.removeEntry(1) == Pair(1, "a"))
     * check(map.remove(1) == null)
     * ```
     */
    public open fun <Q> removeEntry(k: Q): Pair<K, V>? = this.base.removeEntry(k)

    public open fun clone(): HashMap<K, V, S, A> = HashMap(base = this.base.clone())

    public open fun cloneFrom(source: HashMap<K, V, S, A>) {
        this.base = source.base
    }

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
     */
    public open fun index(key: Q): V = (this.get(key) ?: error("no entry found for key"))

    public open fun intoIter(): Iter<K, V> = this.iter()

    /**
     * Creates a consuming iterator, that is, one that moves each key-value
     * pair out of the map in arbitrary order. The map cannot be used after
     * calling this.
     *
     * # Examples
     *
     * ```kotlin
     * val map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
     * // Not possible with .iter()
     * val vec: List<Pair<String, Int>> = map.intoIter().toMutableList()
     * ```
     */
    public open fun intoIterIntoIterator(): IntoIter<K, V, A> = IntoIter(base = this.base.intoIter())

    /**
     * Inserts all new key-values from the iterator and replaces values with existing
     * keys with new values returned from the iterator.
     */
    public open fun <I : Iterable<Pair<K, V>>> extend(iter: I) {
        this.base.extend(iter)
    }

    public open fun extendOne(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.base.insert(k, v)
    }

    public open fun extendReserve(additional: Int) {
        this.base.extendReserve(additional)
    }

    public open fun <I : Iterable<Pair<K, V>>> extendKV(iter: I) {
        this.base.extend(iter)
    }

    public open fun extendOneKV(tupleArg0: Pair<K, V>) {
        val (k, v) = tupleArg0
        this.base.insert(k, v)
    }

    public open fun extendReserveKV(additional: Int) {
        Extend.extendReserve(this, additional)
    }

    public companion object {
        public operator fun <K, V, S, A : Allocator> invoke(
            base: io.github.kotlinmania.rustlang.hashbrown.HashMap<K, V, S, A>,
        ): HashMap<K, V, S, A> = HashMap<K, V, S, A>(
            baseCtorArg = base,
        )

        /**
         * Creates an empty `HashMap` which will use the given hash builder and
         * allocator.
         *
         * The created map has the default initial capacity.
         *
         * Warning: `hashBuilder` is normally randomly generated, and
         * is designed to allow HashMaps to be resistant to attacks that
         * cause many collisions and very poor performance. Setting it
         * manually using this function can expose a DoS attack vector.
         *
         * The `hashBuilder` passed should implement the [BuildHasher] trait for
         * the `HashMap` to be useful, see its documentation for details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.Global
         * import std.hash.RandomState
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val s = RandomState.new()
         *     val map: MutableMap<Int, Int> = HashMap.withHasherIn(s, Global)
         * }
         * ```
         */
        public fun <K, V, S, A : Allocator> withHasherIn(hashBuilder: S, alloc: A): HashMap<K, V, S, A> = HashMap(base = io.github.kotlinmania.rustlang.hashbrown.HashMap.withHasherIn(hashBuilder, alloc))

        /**
         * Creates an empty `HashMap` with at least the specified capacity, using
         * `hasher` to hash the keys and `alloc` to allocate memory.
         *
         * The hash map will be able to hold at least `capacity` elements without
         * reallocating. This method is allowed to allocate for more elements than
         * `capacity`. If `capacity` is zero, the hash map will not allocate.
         *
         * Warning: `hasher` is normally randomly generated, and
         * is designed to allow HashMaps to be resistant to attacks that
         * cause many collisions and very poor performance. Setting it
         * manually using this function can expose a DoS attack vector.
         *
         * The `hasher` passed should implement the [BuildHasher] trait for
         * the `HashMap` to be useful, see its documentation for details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.Global
         * import std.hash.RandomState
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val s = RandomState.new()
         *     val map: MutableMap<Int, Int> = HashMap.withCapacityAndHasherIn(10, s, Global)
         * }
         * ```
         */
        public fun <K, V, S, A : Allocator> withCapacityAndHasherIn(capacity: Int, hashBuilder: S, alloc: A): HashMap<K, V, S, A> = HashMap(base = io.github.kotlinmania.rustlang.hashbrown.HashMap.withCapacityAndHasherIn(capacity, hashBuilder, alloc))
    }
}

/**
 * Creates an empty `HashMap`.
 *
 * The hash map is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # Examples
 *
 * ```kotlin
 * var map: MutableMap<String, Int> = mutableMapOf()
 * ```
 */
public fun <K, V> HashMap.Companion.new(): HashMap<K, V, RandomState, Global> = Default.default()

/**
 * Creates an empty `HashMap` with at least the specified capacity.
 *
 * The hash map will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the hash map will not allocate.
 *
 * # Examples
 *
 * ```kotlin
 * var map: MutableMap<String, Int> = mutableMapOf()
 * ```
 */
public fun <K, V> HashMap.Companion.withCapacity(capacity: Int): HashMap<K, V, RandomState, Global> = HashMap.withCapacityAndHasher(capacity, Default.default())

/**
 * Creates an empty `HashMap` using the given allocator.
 *
 * The hash map is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.Global
 *
 * val map: MutableMap<Int, Int> = HashMap.newIn(Global)
 * ```
 */
public fun <K, V, A : Allocator> HashMap.Companion.newIn(alloc: A): HashMap<K, V, RandomState, A> = HashMap.withHasherIn(Default.default(), alloc)

/**
 * Creates an empty `HashMap` with at least the specified capacity using
 * the given allocator.
 *
 * The hash map will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the hash map will not allocate.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.Global
 *
 * val map: MutableMap<Int, Int> = HashMap.withCapacityIn(10, Global)
 * ```
 */
public fun <K, V, A : Allocator> HashMap.Companion.withCapacityIn(capacity: Int, alloc: A): HashMap<K, V, RandomState, A> = HashMap.withCapacityAndHasherIn(capacity, Default.default(), alloc)

/**
 * Creates an empty `HashMap` which will use the given hash builder to hash
 * keys.
 *
 * The created map has the default initial capacity.
 *
 * Warning: `hashBuilder` is normally randomly generated, and
 * is designed to allow HashMaps to be resistant to attacks that
 * cause many collisions and very poor performance. Setting it
 * manually using this function can expose a DoS attack vector.
 *
 * The `hashBuilder` passed should implement the [BuildHasher] trait for
 * the `HashMap` to be useful, see its documentation for details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.RandomState
 *
 * val s = RandomState.new()
 * var map = HashMap.withHasher(s)
 * map.insert(1, 2)
 * ```
 */
public fun <K, V, S> HashMap.Companion.withHasher(hashBuilder: S): HashMap<K, V, S, Global> = HashMap(base = io.github.kotlinmania.rustlang.hashbrown.HashMap.withHasher(hashBuilder))

/**
 * Creates an empty `HashMap` with at least the specified capacity, using
 * `hasher` to hash the keys.
 *
 * The hash map will be able to hold at least `capacity` elements without
 * reallocating. This method is allowed to allocate for more elements than
 * `capacity`. If `capacity` is zero, the hash map will not allocate.
 *
 * Warning: `hasher` is normally randomly generated, and
 * is designed to allow HashMaps to be resistant to attacks that
 * cause many collisions and very poor performance. Setting it
 * manually using this function can expose a DoS attack vector.
 *
 * The `hasher` passed should implement the [BuildHasher] trait for
 * the `HashMap` to be useful, see its documentation for details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.RandomState
 *
 * val s = RandomState.new()
 * var map = HashMap.withCapacityAndHasher(10, s)
 * map.insert(1, 2)
 * ```
 */
public fun <K, V, S> HashMap.Companion.withCapacityAndHasher(capacity: Int, hasher: S): HashMap<K, V, S, Global> = HashMap(base = io.github.kotlinmania.rustlang.hashbrown.HashMap.withCapacityAndHasher(capacity, hasher))

/**
 * Creates an empty `HashMap<K, V, S>`, with the `Default` value for the hasher.
 */
public fun <K, V, S> defaultHashMapKVS(defaultSFn: () -> S): HashMap<K, V, S, Global> = HashMap.withHasher(defaultSFn())

// Note: as what is currently the most convenient built-in way to construct
// a HashMap, a simple usage of this function must not *require* the user
// to provide a type annotation in order to infer the third type parameter
// (the hasher parameter, conventionally "S").
// To that end, this impl is defined using RandomState as the concrete
// type of S, rather than being generic over `S: BuildHasher + Default`.
// It is expected that users who want to specify a hasher will manually use
// `withCapacityAndHasher`.
// If type parameter defaults worked on impls, and if type parameter
// defaults could be mixed with const generics, then perhaps
// this could be generalized.
// See also the equivalent impl on HashSet.

/**
 * Converts a `[(K, V); N]` into a `HashMap<K, V>`.
 *
 * If any entries in the array have equal keys,
 * all but one of the corresponding values will be dropped.
 *
 * # Examples
 *
 * ```kotlin
 * val map1 = mutableMapOf(1 to 2, 3 to 4)
 * val map2: HashMap = listOf(Pair(1, 2), Pair(3, 4)).into()
 * check(map1 == map2)
 * ```
 */
public fun <K, V> fromHashMapKVRandomState(arr: List<Pair<K, V>>, n: Int): HashMap<K, V, RandomState, Global> = HashMap.fromIter(arr)

/**
 * An iterator over the entries of a `HashMap`.
 *
 * This `class` is created by the [iter] method on [HashMap]. See its
 * documentation for more.
 *
 * [iter]: HashMap.iter
 *
 * # Example
 *
 * ```kotlin
 * val map = mutableMapOf("a" to 1)
 * val iter = map.iter()
 * ```
 */
public open class Iter<K, V>(
    public var base: io.github.kotlinmania.rustlang.hashbrown.Iter<K, V>,
) : Iterable<Pair<K, V>> {
    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Iter<K, V> = Iter(base = this.base.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()

    public open fun next(): Pair<K, V>? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun count(): Int = this.base.len()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public companion object {
        public fun <K, V> default(): Iter<K, V> = Iter(base = Default.default())
    }
}

/**
 * A mutable iterator over the entries of a `HashMap`.
 *
 * This `class` is created by the [iterMut] method on [HashMap]. See its
 * documentation for more.
 *
 * [iterMut]: HashMap.iterMut
 *
 * # Example
 *
 * ```kotlin
 * var map = mutableMapOf("a" to 1)
 * val iter = map.iterMut()
 * ```
 */
public open class IterMut<K, V>(
    public var base: io.github.kotlinmania.rustlang.hashbrown.IterMut<K, V>,
) : Iterable<Pair<K, V>> {
    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns an iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(base = this.base.rustcIter())

    public open fun next(): Pair<K, V>? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun count(): Int = this.base.len()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

    public companion object {
        public fun <K, V> default(): IterMut<K, V> = IterMut(base = Default.default())
    }
}

/**
 * An owning iterator over the entries of a `HashMap`.
 *
 * This `class` is created by the [intoIter] method on [HashMap]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: IntoIterator.intoIter
 *
 * # Example
 *
 * ```kotlin
 * val map = mutableMapOf("a" to 1)
 * val iter = map.intoIter()
 * ```
 */
public open class IntoIter<K, V, A : Allocator> internal constructor(
    baseCtorArg: io.github.kotlinmania.rustlang.hashbrown.IntoIter<K, V, A>,
) : Iterable<Pair<K, V>> {
    public var base: io.github.kotlinmania.rustlang.hashbrown.IntoIter<K, V, A> = baseCtorArg
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
    public open fun iter(): Iter<K, V> = Iter(base = this.base.rustcIter())

    public open fun next(): Pair<K, V>? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun count(): Int = this.base.len()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            base: io.github.kotlinmania.rustlang.hashbrown.IntoIter<K, V, A>,
        ): IntoIter<K, V, A> = IntoIter<K, V, A>(
            baseCtorArg = base,
        )
    }
}

public fun <K, V> defaultIntoIterKV(): IntoIter<K, V, Global> = IntoIter(base = Default.default())

/**
 * An iterator over the keys of a `HashMap`.
 *
 * This `class` is created by the [keys] method on [HashMap]. See its
 * documentation for more.
 *
 * [keys]: HashMap.keys
 *
 * # Example
 *
 * ```kotlin
 * val map = mutableMapOf("a" to 1)
 * val iterKeys = map.keys()
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

    public open fun next(): K? = this.inner.next()?.let({ (k, _) -> k })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun count(): Int = this.inner.len()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.inner.fold(init, { acc, (k, _) -> f(acc, k) })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public fun <K, V> default(): Keys<K, V> = Keys(inner = Default.default())
    }
}

/**
 * An iterator over the values of a `HashMap`.
 *
 * This `class` is created by the [values] method on [HashMap]. See its
 * documentation for more.
 *
 * [values]: HashMap.values
 *
 * # Example
 *
 * ```kotlin
 * val map = mutableMapOf("a" to 1)
 * val iterValues = map.values()
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

    public open fun next(): V? = this.inner.next()?.let({ (_, v) -> v })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun count(): Int = this.inner.len()

    public open fun <B> fold(init: B, f: (B, V) -> B): B = this.inner.fold(init, { acc, (_, v) -> f(acc, v) })

    public open fun len(): Int = this.inner.len()

    public companion object {
        public fun <K, V> default(): Values<K, V> = Values(inner = Default.default())
    }
}

/**
 * A draining iterator over the entries of a `HashMap`.
 *
 * This `class` is created by the [drain] method on [HashMap]. See its
 * documentation for more.
 *
 * [drain]: HashMap.drain
 *
 * # Example
 *
 * ```kotlin
 * var map = mutableMapOf("a" to 1)
 * val iter = map.drain()
 * ```
 */
public open class Drain<K, V, A : Allocator> internal constructor(
    baseCtorArg: io.github.kotlinmania.rustlang.hashbrown.Drain<K, V, A>,
) : Iterable<Pair<K, V>> {
    public var base: io.github.kotlinmania.rustlang.hashbrown.Drain<K, V, A> = baseCtorArg
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
     * Returns an iterator of references over the remaining items.
     */
    public open fun iter(): Iter<K, V> = Iter(base = this.base.rustcIter())

    public open fun next(): Pair<K, V>? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun <B> fold(init: B, f: (B, Pair<K, V>) -> B): B = this.base.fold(init, f)

    public open fun len(): Int = this.base.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            base: io.github.kotlinmania.rustlang.hashbrown.Drain<K, V, A>,
        ): Drain<K, V, A> = Drain<K, V, A>(
            baseCtorArg = base,
        )
    }
}

/**
 * A draining, filtering iterator over the entries of a `HashMap`.
 *
 * This `class` is created by the [extractIf] method on [HashMap].
 *
 * [extractIf]: HashMap.extractIf
 *
 * # Example
 *
 * ```kotlin
 * var map = mutableMapOf("a" to 1)
 * val iter = map.extractIf({ k, v -> v % 2 == 0 })
 * ```
 */
public open class ExtractIf<K, V, F, A : Allocator> internal constructor(
    baseCtorArg: io.github.kotlinmania.rustlang.hashbrown.ExtractIf<K, V, F, A>,
) : Iterable<Pair<K, V>> {
    public var base: io.github.kotlinmania.rustlang.hashbrown.ExtractIf<K, V, F, A> = baseCtorArg
        internal set

    override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
        override fun computeNext() {
            when (val v = this@ExtractIf.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<K, V>? = this.base.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.base.sizeHint()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "ExtractIf").finishNonExhaustive()

    public companion object {
        public operator fun <K, V, F, A : Allocator> invoke(
            base: io.github.kotlinmania.rustlang.hashbrown.ExtractIf<K, V, F, A>,
        ): ExtractIf<K, V, F, A> = ExtractIf<K, V, F, A>(
            baseCtorArg = base,
        )
    }
}

/**
 * A mutable iterator over the values of a `HashMap`.
 *
 * This `class` is created by the [valuesMut] method on [HashMap]. See its
 * documentation for more.
 *
 * [valuesMut]: HashMap.valuesMut
 *
 * # Example
 *
 * ```kotlin
 * var map = mutableMapOf("a" to 1)
 * val iterValues = map.valuesMut()
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

    public open fun next(): V? = this.inner.next()?.let({ (_, v) -> v })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun count(): Int = this.inner.len()

    public open fun <B> fold(init: B, f: (B, V) -> B): B = this.inner.fold(init, { acc, (_, v) -> f(acc, v) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (_, value) -> value })).finish()

    public companion object {
        public fun <K, V> default(): ValuesMut<K, V> = ValuesMut(inner = Default.default())
    }
}

/**
 * An owning iterator over the keys of a `HashMap`.
 *
 * This `class` is created by the [intoKeys] method on [HashMap].
 * See its documentation for more.
 *
 * [intoKeys]: HashMap.intoKeys
 *
 * # Example
 *
 * ```kotlin
 * val map = mutableMapOf("a" to 1)
 * val iterKeys = map.intoKeys()
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

    public open fun count(): Int = this.inner.len()

    public open fun <B> fold(init: B, f: (B, K) -> B): B = this.inner.fold(init, { acc, (k, _) -> f(acc, k) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (k, _) -> k })).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: IntoIter<K, V, A>,
        ): IntoKeys<K, V, A> = IntoKeys<K, V, A>(
            innerCtorArg = inner,
        )
    }
}

public fun <K, V> defaultIntoKeysKV(): IntoKeys<K, V, Global> = IntoKeys(inner = Default.default())

/**
 * An owning iterator over the values of a `HashMap`.
 *
 * This `class` is created by the [intoValues] method on [HashMap].
 * See its documentation for more.
 *
 * [intoValues]: HashMap.intoValues
 *
 * # Example
 *
 * ```kotlin
 * val map = mutableMapOf("a" to 1)
 * val iterKeys = map.intoValues()
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

    public open fun count(): Int = this.inner.len()

    public open fun <B> fold(init: B, f: (B, V) -> B): B = this.inner.fold(init, { acc, (_, v) -> f(acc, v) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.inner.iter().map({ (_, v) -> v })).finish()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: IntoIter<K, V, A>,
        ): IntoValues<K, V, A> = IntoValues<K, V, A>(
            innerCtorArg = inner,
        )
    }
}

public fun <K, V> defaultIntoValuesKV(): IntoValues<K, V, Global> = IntoValues(inner = Default.default())

/**
 * A view into a single entry in a map, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [entry] method on [HashMap].
 *
 * [entry]: HashMap.entry
 */
public sealed class Entry<K, V, A : Allocator> {
    /**
     * An occupied entry.
     */
    public class Occupied<K, V, A : Allocator> internal constructor(valueCtorArg: OccupiedEntry<K, V, A>) : Entry<K, V, A>() {
        public var value: OccupiedEntry<K, V, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<K, V, A> = this.value

        public fun copy(value: OccupiedEntry<K, V, A> = this.value): Occupied<K, V, A> = Occupied<K, V, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, A : Allocator> invoke(
                value: OccupiedEntry<K, V, A>,
            ): Occupied<K, V, A> = Occupied<K, V, A>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * A vacant entry.
     */
    public class Vacant<K, V, A : Allocator> internal constructor(valueCtorArg: VacantEntry<K, V, A>) : Entry<K, V, A>() {
        public var value: VacantEntry<K, V, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntry<K, V, A> = this.value

        public fun copy(value: VacantEntry<K, V, A> = this.value): Vacant<K, V, A> = Vacant<K, V, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <K, V, A : Allocator> invoke(
                value: VacantEntry<K, V, A>,
            ): Vacant<K, V, A> = Vacant<K, V, A>(
                valueCtorArg = value,
            )
        }
    }

    /**
     * Ensures a value is in the entry by inserting the default if empty, and returns
     * a mutable reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(3)
     * check(map["poneyland"] == 3)
     * map.entry("poneyland").orInsert(10) *= 2
     * check(map["poneyland"] == 6)
     * ```
     */
    public open fun orInsert(default: V): V =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.intoMut()
            }
            is Vacant -> {
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
     * var map = mutableMapOf()
     * val value = "hoho"
     * map.entry("poneyland").orInsertWith({ value })
     * check(map["poneyland"] == "hoho")
     * ```
     */
    public open fun orInsertWith(default: () -> V): V = this.orTryInsertWith({ Result.Ok(default()) }).unwrap()

    /**
     * Ensures a value is in the entry by inserting the result of a fallible default function
     * if empty, and returns a mutable reference to the value in the entry.
     *
     * This method works identically to [orInsertWith] except that the default function
     * should return a `Result` and, in the case of an error, the error is propagated.
     *
     * [orInsertWith]: Self.orInsertWith
     *
     * # Examples
     *
     * ```kotlin
     * @feature(tryEntry)
     * public fun doctestEntry(): Result<Unit> {
     *     var map: MutableMap<String, Int> = mutableMapOf()
     *     val value = "42"
     *     map.entry("poneyland").orTryInsertWith({ value.parse() }).getOrElse { return Result.failure(it) }
     *     check(map["poneyland"] == 42)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun <E> orTryInsertWith(default: () -> Result<V, E>): Result<V, E> {
        return when (this) {
            is Occupied -> {
                val (entry) = this
                Result.Ok<V, E>(entry.intoMut())
            }
            is Vacant -> {
                val (entry1) = this
                Result.Ok<V, E>(entry1.insert(when (val tryResult = default()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<V, E>(tryResult.error) }))
            }
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
     * var map: MutableMap<String, Int> = mutableMapOf()
     * map.entry("poneyland").orInsertWithKey({ key -> key.chars().count() })
     * check(map["poneyland"] == 9)
     * ```
     */
    public open fun orInsertWithKey(default: (K) -> V): V = this.orTryInsertWithKey({ k -> Result.Ok(default(k)) }).intoOk()

    /**
     * Ensures a value is in the entry by inserting, if empty, the result of the default function.
     * This method allows for generating key-derived values for insertion by providing the default
     * function a reference to the key that was moved during the `entry(key)` method call.
     *
     * This method works identically to [orInsertWithKey] except that the default function
     * should return a `Result` and, in the case of an error, the error is propagated.
     *
     * [orInsertWithKey]: Self.orInsertWithKey
     *
     * # Examples
     *
     * ```kotlin
     * @feature(tryEntry)
     * public fun doctestEntry(): Result<Unit> {
     *     var map: MutableMap<String, Int> = mutableMapOf()
     *     map.entry("42").orTryInsertWithKey({ key -> key.parse() }).getOrElse { return Result.failure(it) }
     *     check(map["42"] == 42)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun <E> orTryInsertWithKey(default: (K) -> Result<V, E>): Result<V, E> {
        return when (this) {
            is Occupied -> {
                val (entry) = this
                Result.Ok<V, E>(entry.intoMut())
            }
            is Vacant -> {
                val (entry1) = this
                val value = when (val tryResult = default(entry1.key())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<V, E>(tryResult.error) }
                Result.Ok<V, E>(entry1.insert(value))
            }
        }
    }

    /**
     * Returns a reference to this entry's key.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.key()
            }
            is Vacant -> {
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
    public open fun andModify(f: (V) -> Unit): Entry<K, V, A> =
        when (this) {
            is Occupied -> {
                val (entry) = this
                f(entry.getMut())
                Occupied(entry)
            }
            is Vacant -> {
                val (entry1) = this
                Vacant(entry1)
            }
        }

    /**
     * Sets the value of the entry, and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, String> = mutableMapOf()
     * val entry = map.entry("poneyland").insertEntry("hoho".toString())
     * check(entry.key() == "poneyland")
     * check(entry.get() == "hoho")
     * ```
     */
    public open fun insertEntry(value: V): OccupiedEntry<K, V, A> =
        when (this) {
            is Occupied -> {
                val (entry) = this
                entry.insert(value)
                entry
            }
            is Vacant -> {
                val (entry1) = this
                entry1.insertEntry(value)
            }
        }
}

public fun <K, V> Entry<K, V, Global>.fmt(f: StringBuilder): Result<Unit, Error> =
    when (this) {
        is Vacant -> {
            val (v) = this
            DebugTuple(f, "Entry").field(v).finish()
        }
        is Occupied -> {
            val (o) = this
            DebugTuple(f, "Entry").field(o).finish()
        }
    }

/**
 * A view into an occupied entry in a `HashMap`.
 * It is part of the [Entry] enum.
 */
public open class OccupiedEntry<K, V, A : Allocator> internal constructor(
    baseCtorArg: RustcOccupiedEntry<K, V, A>,
) {
    public var base: RustcOccupiedEntry<K, V, A> = baseCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "OccupiedEntry").field("key", this.key()).field("value", this.get()).finishNonExhaustive()

    /**
     * Gets a reference to the key in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.base.key()

    /**
     * Take the ownership of the key and value from the map.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         // We delete the entry from the map.
     *         o.removeEntry()
     *     }
     *     else -> {}
     * }
     * check(map.containsKey("poneyland") == false)
     * ```
     */
    public open fun removeEntry(): Pair<K, V> = this.base.removeEntry()

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.get() == 12)
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun get(): V = this.base.get()

    /**
     * Gets a mutable reference to the value in the entry.
     *
     * If you need a reference to the `OccupiedEntry` which may outlive the
     * destruction of the `Entry` value, see [intoMut].
     *
     * [intoMut]: Self.intoMut
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
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
    public open fun getMut(): V = this.base.getMut()

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the value in the entry
     * with a scope bound to the map itself.
     *
     * If you need multiple references to the `OccupiedEntry`, see [getMut].
     *
     * [getMut]: Self.getMut
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * check(map["poneyland"] == 12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         o.intoMut() += 10
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 22)
     * ```
     */
    public open fun intoMut(): V = this.base.intoMut()

    /**
     * Sets the value of the entry, and returns the entry's old value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
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
    public open fun insert(value: V): V = this.base.insert(value)

    /**
     * Takes the value out of the entry, and returns it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * map.entry("poneyland").orInsert(12)
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove() == 12)
     *     }
     *     else -> {}
     * }
     * check(map.containsKey("poneyland") == false)
     * ```
     */
    public open fun remove(): V = this.base.remove()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            base: RustcOccupiedEntry<K, V, A>,
        ): OccupiedEntry<K, V, A> = OccupiedEntry<K, V, A>(
            baseCtorArg = base,
        )
    }
}

/**
 * A view into a vacant entry in a `HashMap`.
 * It is part of the [Entry] enum.
 */
public open class VacantEntry<K, V, A : Allocator> internal constructor(
    baseCtorArg: RustcVacantEntry<K, V, A>,
) {
    public var base: RustcVacantEntry<K, V, A> = baseCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugTuple(f, "VacantEntry").field(this.key()).finish()

    /**
     * Gets a reference to the key that would be used when inserting a value
     * through the `VacantEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * check(map.entry("poneyland").key() == "poneyland")
     * ```
     */
    public open fun key(): K = this.base.key()

    /**
     * Take ownership of the key.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (v) = matchVal
     *         v.intoKey()
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun intoKey(): K = this.base.intoKey()

    /**
     * Sets the value of the entry with the `VacantEntry`s key,
     * and returns a mutable reference to it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
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
    public open fun insert(value: V): V = this.base.insert(value)

    /**
     * Sets the value of the entry with the `VacantEntry`s key,
     * and returns an `OccupiedEntry`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.hashmap.Entry
     *
     * var map: MutableMap<String, UInt> = mutableMapOf()
     * when (val matchVal = map.entry("poneyland")) {
     *     is Entry.Vacant -> {
     *         val (o) = matchVal
     *         val entry = o.insertEntry(37)
     *         check(entry.get() == 37)
     *     }
     *     else -> {}
     * }
     * check(map["poneyland"] == 37)
     * ```
     */
    public open fun insertEntry(value: V): OccupiedEntry<K, V, A> {
        val base = this.base.insertEntry(value)
        return OccupiedEntry(base = base)
    }

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            base: RustcVacantEntry<K, V, A>,
        ): VacantEntry<K, V, A> = VacantEntry<K, V, A>(
            baseCtorArg = base,
        )
    }
}

/**
 * The error returned by [`tryInsert`](HashMap.tryInsert) when the key already exists.
 *
 * Contains the occupied entry, key, and the value that was not inserted.
 */
public open class OccupiedError<K, V, A : Allocator> internal constructor(
    entryCtorArg: OccupiedEntry<K, V, A>,
    /**
     * The key which was not inserted, because the entry was already occupied.
     */
    public var key: K,
    /**
     * The value which was not inserted, because the entry was already occupied.
     */
    public var value: V,
) {
    /**
     * The entry in the map that was already occupied.
     */
    public var entry: OccupiedEntry<K, V, A> = entryCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "OccupiedError").field("key", this.entry.key()).field("uninserted_key", this.key).field("old_value", this.entry.get()).field("new_value", this.value).finishNonExhaustive()

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            entry: OccupiedEntry<K, V, A>,
            key: K,
            value: V,
        ): OccupiedError<K, V, A> = OccupiedError<K, V, A>(
            entryCtorArg = entry,
            key = key,
            value = value,
        )
    }
}

/**
 * Ensures a value is in the entry by inserting the default value if empty,
 * and returns a mutable reference to the value in the entry.
 *
 * # Examples
 *
 * ```kotlin
 * var map: MutableMap<String, UInt?> = mutableMapOf()
 * map.entry("poneyland").orDefault()
 * check(map["poneyland"] == null)
 * ```
 */
public fun <K, V> Entry<K, V, Global>.orDefault(defaultVFn: () -> V): V =
    when (this) {
        is Occupied -> {
            val (entry) = this
            entry.intoMut()
        }
        is Vacant -> {
            val (entry1) = this
            entry1.insert(defaultVFn())
        }
    }

/**
 * Constructs a `HashMap<K, V>` from an iterator of key-value pairs.
 *
 * If the iterator produces any pairs with equal keys,
 * all but one of the corresponding values will be dropped.
 */
public fun <K, V, S, I : Iterable<Pair<K, V>>> fromIteratorFromIterHashMapKVS(iter: I, defaultSFn: () -> S): HashMap<K, V, S, Global> where S : BuildHasher {
    val map = HashMap.withHasher(defaultSFn())
    map.extend(iter, defaultSFn)
    return map
}

public fun <K, V, A : Allocator> mapEntry(raw: RustcEntry<K, V, A>): Entry<K, V, A> =
    when (raw) {
        is RustcEntry.Occupied -> {
            val (base) = raw
            Entry.Occupied(OccupiedEntry(base = base))
        }
        is RustcEntry.Vacant -> {
            val (base1) = raw
            Entry.Vacant(VacantEntry(base = base1))
        }
    }

public fun mapTryReserveError(err: TryReserveError): TryReserveError =
    when (err) {
        TryReserveError.CapacityOverflow -> {
            TryReserveErrorKind.CapacityOverflow.into()
        }
        is TryReserveError.AllocError -> {
            val layout = err.layout
            TryReserveErrorKind.AllocError(layout = layout, nonExhaustive = Unit).into()
        }
    }

public fun assertCovariance() {
    fun mapKey(v: HashMap<String, UByte, RandomState, Global>): HashMap<String, UByte, RandomState, Global> = v
    fun mapVal(v: HashMap<UByte, String, RandomState, Global>): HashMap<UByte, String, RandomState, Global> = v
    fun iterKey(v: Iter<String, UByte>): Iter<String, UByte> = v
    fun iterVal(v: Iter<UByte, String>): Iter<UByte, String> = v
    fun intoIterKey(v: IntoIter<String, UByte, Global>): IntoIter<String, UByte, Global> = v
    fun intoIterVal(v: IntoIter<UByte, String, Global>): IntoIter<UByte, String, Global> = v
    fun keysKey(v: Keys<String, UByte>): Keys<String, UByte> = v
    fun keysVal(v: Keys<UByte, String>): Keys<UByte, String> = v
    fun valuesKey(v: Values<String, UByte>): Values<String, UByte> = v
    fun valuesVal(v: Values<UByte, String>): Values<UByte, String> = v
    fun drain(d: Drain<String, String, Global>): Drain<String, String, Global> = d
}
