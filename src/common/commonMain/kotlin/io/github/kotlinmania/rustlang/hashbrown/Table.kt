// port-lint: source table.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.len
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Allocator
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Global
import io.github.kotlinmania.rustlang.hashbrown.control.tag.Tag
import io.github.kotlinmania.rustlang.hashbrown.raw.Bucket
import io.github.kotlinmania.rustlang.hashbrown.raw.FullBucketsIndices
import io.github.kotlinmania.rustlang.hashbrown.raw.RawDrain
import io.github.kotlinmania.rustlang.hashbrown.raw.RawExtractIf
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIntoIter
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIter
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIterHash
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIterHashIndices
import io.github.kotlinmania.rustlang.hashbrown.raw.RawTable

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

private class DebugTuple(
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
 * Low-level hash table with explicit hashing.
 *
 * The primary use case for this type over [HashMap] or [HashSet] is to
 * support types that do not implement the [Hash] and [Eq] traits, but
 * instead require additional data not contained in the key itself to compute a
 * hash and compare two elements for equality.
 *
 * Examples of when this can be useful include:
 * - An `IndexMap` implementation where indices into a `List` are stored as
 *   elements in a `HashTable<Int>`. Hashing and comparing the elements
 *   requires indexing the associated `List` to get the actual value referred to
 *   by the index.
 * - Avoiding re-computing a hash when it is already known.
 * - Mutating the key of an element in a way that doesn't affect its hash.
 *
 * To achieve this, `HashTable` methods that search for an element in the table
 * require a hash value and equality function to be explicitly passed in as
 * arguments. The method will then iterate over the elements with the given
 * hash and call the equality function on each of them, until a match is found.
 *
 * In most cases, a `HashTable` will not be exposed directly in an API. It will
 * instead be wrapped in a helper type which handles the work of calculating
 * hash values and comparing elements.
 *
 * Due to its low-level nature, this type provides fewer guarantees than
 * [HashMap] and [HashSet]. Specifically, the API allows you to shoot
 * yourself in the foot by having multiple elements with identical keys in the
 * table. The table itself will still function correctly and lookups will
 * arbitrarily return one of the matching elements. However you should avoid
 * doing this because it changes the runtime of hash table operations from
 * `O(1)` to `O(k)` where `k` is the number of duplicate entries.
 */
public open class HashTable<T, A> internal constructor(
    rawCtorArg: RawTable<T, A>,
) : Iterable<T> where A : Allocator {
    public var raw: RawTable<T, A> = rawCtorArg
        internal set

    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.raw.allocator()

    /**
     * Returns a reference to an entry in the table with the given hash and
     * which satisfies the equality function passed.
     *
     * This method will call `eq` for all entries with the given hash, but may
     * also call it for entries with a different hash. `eq` should only return
     * true for the desired entry, at which point the search is stopped.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), 1, hasher1)
     * table.insertUnique(hasher1(2), 2, hasher1)
     * table.insertUnique(hasher1(3), 3, hasher1)
     * check(table.find(hasher1(2), { value -> value == 2 }) == 2)
     * check(table.find(hasher1(4), { value -> value == 4 }) == null)
     * ```
     */
    public open fun find(hash: ULong, eq: (T) -> Boolean): T? = this.raw.get(hash, eq)

    /**
     * Returns a mutable reference to an entry in the table with the given hash
     * and which satisfies the equality function passed.
     *
     * This method will call `eq` for all entries with the given hash, but may
     * also call it for entries with a different hash. `eq` should only return
     * true for the desired entry, at which point the search is stopped.
     *
     * When mutating an entry, you should ensure that it still retains the same
     * hash value as when it was inserted, otherwise lookups of that entry may
     * fail to find it.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, "a"), { value -> hasher1(value.first) })
     * when (val matchVal = table.findMut(hasher1(1), { value -> value.first == 1 })) {
     *     null -> {}
     *     else -> {
     *         val value = matchVal
     *         value.second = "b"
     *     }
     * }
     * check(table.find(hasher1(1), { value1 -> value1.first == 1 }) == Pair(1, "b"))
     * check(table.find(hasher1(2), { value1 -> value1.first == 2 }) == null)
     * ```
     */
    public open fun findMut(hash: ULong, eq: (T) -> Boolean): T? = this.raw.getMut(hash, eq)

    /**
     * Returns an `OccupiedEntry` for an entry in the table with the given hash
     * and which satisfies the equality function passed.
     *
     * This can be used to remove the entry from the table. Call
     * [HashTable.entry] instead if you wish to insert an entry if the
     * lookup fails.
     *
     * This method will call `eq` for all entries with the given hash, but may
     * also call it for entries with a different hash. `eq` should only return
     * true for the desired entry, at which point the search is stopped.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, "a"), { value -> hasher1(value.first) })
     * when (val matchVal = table.findEntry(hasher1(1), { value -> value.first == 1 })) {
     *     is Ok -> {
     *         val (entry) = matchVal
     *         entry.remove()
     *     }
     *     else -> {}
     * }
     * check(table.find(hasher1(1), { value -> value.first == 1 }) == null)
     * ```
     */
    public open fun findEntry(hash: ULong, eq: (T) -> Boolean): Result<OccupiedEntry<T, A>, AbsentEntry<T, A>> =
        when (val matchVal = this.raw.find(hash, eq)) {
            null -> Result.Err<OccupiedEntry<T, A>, AbsentEntry<T, A>>(AbsentEntry(table = this))
            else -> {
                val bucket = matchVal
                Result.Ok<OccupiedEntry<T, A>, AbsentEntry<T, A>>(OccupiedEntry(bucket = bucket, table = this))
            }
        }

    /**
     * Returns the bucket index in the table for an entry with the given hash
     * and which satisfies the equality function passed.
     *
     * This can be used to store a borrow-free "reference" to the entry, later using
     * [`getBucket`][Self.getBucket], [`getBucketMut`][Self.getBucketMut], or
     * [`getBucketEntry`][Self.getBucketEntry] to access it again without hash probing.
     *
     * The index is only meaningful as long as the table is not resized and no entries are added
     * or removed. After such changes, it may end up pointing to a different entry or none at all.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 1), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 2), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 3), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(table.getBucket(index) == Pair(2, 2))
     * // Mutation would invalidate any normal reference
     * for ((key, value) in table) {
     *     value *= 11
     * }
     * // The index still reaches the same key with the updated value
     * check(table.getBucket(index) == Pair(2, 22))
     * ```
     */
    public open fun findBucketIndex(hash: ULong, eq: (T) -> Boolean): Int? =
        when (val matchVal = this.raw.find(hash, eq)) {
            null -> null
            else -> {
                val bucket = matchVal
                this.raw.bucketIndex(bucket)
            }
        }

    /**
     * Returns an `Entry` for an entry in the table with the given hash
     * and which satisfies the equality function passed.
     *
     * This can be used to remove the entry from the table, or insert a new
     * entry with the given hash if one doesn't already exist.
     *
     * This method will call `eq` for all entries with the given hash, but may
     * also call it for entries with a different hash. `eq` should only return
     * true for the desired entry, at which point the search is stopped.
     *
     * This method may grow the table in preparation for an insertion. Call
     * [HashTable.findEntry] if this is undesirable.
     *
     * `hasher` is called if entries need to be moved or copied to a new table.
     * This must return the same hash value that each entry was inserted with.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, "a"), { value -> hasher1(value.first) })
     * when (val matchVal = table.entry(hasher1(1), { value -> value.first == 1 }, { value -> hasher1(value.first) })) {
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         entry.remove()
     *     }
     *     else -> {}
     * }
     * when (val matchVal1 = table.entry(hasher1(2), { value -> value.first == 2 }, { value -> hasher1(value.first) })) {
     *     is Entry.Vacant -> {
     *         val (entry1) = matchVal1
     *         entry1.insert(Pair(2, "b"))
     *     }
     *     else -> {}
     * }
     * check(table.find(hasher1(1), { value -> value.first == 1 }) == null)
     * check(table.find(hasher1(2), { value -> value.first == 2 }) == Pair(2, "b"))
     * ```
     */
    public open fun entry(hash: ULong, eq: (T) -> Boolean, hasher: (T) -> ULong): Entry<T, A> =
        run {
            val matchVal = this.raw.findOrFindInsertIndex(hash, eq, hasher)
            when (matchVal) { is Result.Ok<*, *> -> {
                val bucket = matchVal.okValue()
                Entry.Occupied(OccupiedEntry(bucket = bucket, table = this))
            }; is Result.Err<*, *> -> {
                val insertIndex = matchVal.errValue()
                Entry.Vacant(VacantEntry(tag = Tag.full(hash), index = insertIndex, table = this))
            } }
        }

    /**
     * Returns an `OccupiedEntry` for the given bucket index in the table,
     * or `AbsentEntry` if it is unoccupied or out of bounds.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(table.getBucketEntry(Int.MAX_VALUE).isErr())
     * val occupiedEntry = table.getBucketEntry(index).unwrap()
     * check(occupiedEntry.get() == Pair(2, 'b'))
     * check(occupiedEntry.remove().first == Pair(2, 'b'))
     * check(table.find(hasher1(2), { value -> value.first == 2 }) == null)
     * ```
     */
    public open fun getBucketEntry(index: Int): Result<OccupiedEntry<T, A>, AbsentEntry<T, A>> =
        when (val matchVal = this.raw.checkedBucket(index)) {
            null -> Result.Err<OccupiedEntry<T, A>, AbsentEntry<T, A>>(AbsentEntry(table = this))
            else -> {
                val bucket = matchVal
                Result.Ok<OccupiedEntry<T, A>, AbsentEntry<T, A>>(OccupiedEntry(bucket = bucket, table = this))
            }
        }

    /**
     * Returns an `OccupiedEntry` for the given bucket index in the table,
     * without checking whether the index is in-bounds or occupied.
     *
     * For a safe alternative, see [`getBucketEntry`](Self.getBucketEntry).
     *
     * # Safety
     *
     * It is *[undefined behavior]* to call this method with an index that is
     * out-of-bounds or unoccupied, even if the resulting entry is not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(eq(table.getBucketEntry(index).unwrap().intoMut(), table.getBucketEntryUnchecked(index).intoMut()))
     * ```
     */
    public open fun getBucketEntryUnchecked(index: Int): OccupiedEntry<T, A> = OccupiedEntry(bucket = this.raw.bucket(index), table = this)

    /**
     * Gets a reference to an entry in the table at the given bucket index,
     * or `None` if it is unoccupied or out of bounds.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(table.getBucket(index) == Pair(2, 'b'))
     * ```
     */
    public open fun getBucket(index: Int): T? = this.raw.getBucket(index)

    /**
     * Gets a reference to an entry in the table at the given bucket index,
     * without checking whether the index is in-bounds or occupied.
     *
     * For a safe alternative, see [`getBucket`](Self.getBucket).
     *
     * # Safety
     *
     * It is *[undefined behavior]* to call this method with an index that is
     * out-of-bounds or unoccupied, even if the resulting reference is not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(eq(table.getBucket(index).unwrap(), table.getBucketUnchecked(index)))
     * ```
     */
    public open fun getBucketUnchecked(index: Int): T = this.raw.bucket(index).asRef()

    /**
     * Gets a mutable reference to an entry in the table at the given bucket index,
     * or `None` if it is unoccupied or out of bounds.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(table.getBucket(index) == Pair(2, 'b'))
     * when (val matchVal = table.getBucketMut(index)) {
     *     null -> {}
     *     else -> {
     *         val value = matchVal.second
     *         value = 'B'
     *     }
     * }
     * check(table.getBucket(index) == Pair(2, 'B'))
     * ```
     */
    public open fun getBucketMut(index: Int): T? = this.raw.getBucketMut(index)

    /**
     * Gets a mutable reference to an entry in the table at the given bucket index,
     * without checking whether the index is in-bounds or occupied.
     *
     * For a safe alternative, see [`getBucketMut`](Self.getBucketMut).
     *
     * # Safety
     *
     * It is *[undefined behavior]* to call this method with an index that is
     * out-of-bounds or unoccupied, even if the resulting reference is not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * val index = table.findBucketIndex(hasher1(2), { value -> value.first == 2 }).unwrap()
     * check(eq(table.getBucketMut(index).unwrap(), table.getBucketUncheckedMut(index)))
     * ```
     */
    public open fun getBucketUncheckedMut(index: Int): T = this.raw.bucket(index).asMut()

    /**
     * Inserts an element into the `HashTable` with the given hash value, but
     * without checking whether an equivalent element already exists within the
     * table.
     *
     * `hasher` is called if entries need to be moved or copied to a new table.
     * This must return the same hash value that each entry was inserted with.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var v = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * v.insertUnique(hasher1(1), 1, hasher1)
     * ```
     */
    public open fun insertUnique(hash: ULong, value: T, hasher: (T) -> ULong): OccupiedEntry<T, A> {
        val bucket = this.raw.insert(hash, value, hasher)
        return OccupiedEntry(bucket = bucket, table = this)
    }

    /**
     * Clears the table, removing all values.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var v = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * v.insertUnique(hasher1(1), 1, hasher1)
     * v.clear()
     * check(v.isEmpty())
     * ```
     */
    public open fun clear() {
        this.raw.clear()
    }

    /**
     * Shrinks the capacity of the table as much as possible. It will drop
     * down as much as possible while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * `hasher` is called if entries need to be moved or copied to a new table.
     * This must return the same hash value that each entry was inserted with.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.withCapacity(100)
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), 1, hasher1)
     * table.insertUnique(hasher1(2), 2, hasher1)
     * check(table.capacity() >= 100)
     * table.shrinkToFit(hasher1)
     * check(table.capacity() >= 2)
     * ```
     */
    public open fun shrinkToFit(hasher: (T) -> ULong) {
        this.raw.shrinkTo(this.len(), hasher)
    }

    /**
     * Shrinks the capacity of the table with a lower limit. It will drop
     * down no lower than the supplied limit while maintaining the internal rules
     * and possibly leaving some space in accordance with the resize policy.
     *
     * `hasher` is called if entries need to be moved or copied to a new table.
     * This must return the same hash value that each entry was inserted with.
     *
     * Panics if the current capacity is smaller than the supplied
     * minimum capacity.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.withCapacity(100)
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), 1, hasher1)
     * table.insertUnique(hasher1(2), 2, hasher1)
     * check(table.capacity() >= 100)
     * table.shrinkTo(10, hasher1)
     * check(table.capacity() >= 10)
     * table.shrinkTo(0, hasher1)
     * check(table.capacity() >= 2)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int, hasher: (T) -> ULong) {
        this.raw.shrinkTo(minCapacity, hasher)
    }

    /**
     * Reserves capacity for at least `additional` more elements to be inserted
     * in the `HashTable`. The collection may reserve more space to avoid
     * frequent reallocations.
     *
     * `hasher` is called if entries need to be moved or copied to a new table.
     * This must return the same hash value that each entry was inserted with.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds [isize.Max] bytes and [abort] the program
     * in case of allocation error. Use [`tryReserve`](HashTable.tryReserve) instead
     * if you want to handle memory allocation failure.
     *
     * [abort]: stdalloc.alloc.handleAllocError
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table: HashTable<Int> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.reserve(10, hasher1)
     * check(table.capacity() >= 10)
     * ```
     */
    public open fun reserve(additional: Int, hasher: (T) -> ULong) {
        this.raw.reserve(additional, hasher)
    }

    /**
     * Tries to reserve capacity for at least `additional` more elements to be inserted
     * in the given `HashTable`. The collection may reserve more space to avoid
     * frequent reallocations.
     *
     * `hasher` is called if entries need to be moved or copied to a new table.
     * This must return the same hash value that each entry was inserted with.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table: HashTable<Int> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.tryReserve(10, hasher1).expect("why is the test harness OOMing on 10 bytes?")
     * ```
     */
    public open fun tryReserve(additional: Int, hasher: (T) -> ULong): Result<Unit, TryReserveError> = this.raw.tryReserve(additional, hasher)

    /**
     * Returns the raw number of buckets allocated in the table.
     *
     * This is an upper bound on any methods that take or return a bucket index,
     * as opposed to the usable [`capacity`](Self.capacity) for entries which is
     * reduced by an unspecified load factor.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 'a'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 'b'), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 'c'), { value -> hasher1(value.first) })
     * // Each entry is available at some index in the bucket range.
     * val count = (0 until table.numBuckets()).mapNotNull({ i -> table.getBucket(i) }).count()
     * check(count == 3)
     * check(table.getBucket(table.numBuckets()) == null)
     * ```
     */
    public open fun numBuckets(): Int = this.raw.numBuckets()

    /**
     * Returns the number of elements the table can hold without reallocating.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.HashTable
     *
     * val table: HashTable<Int> = HashTable.withCapacity(100)
     * check(table.capacity() >= 100)
     * ```
     */
    public open fun capacity(): Int = this.raw.capacity()

    /**
     * Returns the number of elements in the table.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * var v = HashTable.new()
     * check(v.len() == 0)
     * v.insertUnique(hasher1(1), 1, hasher1)
     * check(v.len() == 1)
     * ```
     */
    public open fun len(): Int = this.raw.len()

    /**
     * Returns `true` if the set contains no elements.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * var v = HashTable.new()
     * check(v.isEmpty())
     * v.insertUnique(hasher1(1), 1, hasher1)
     * check(!v.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.raw.isEmpty()

    /**
     * An iterator visiting all elements in arbitrary order.
     * The iterator element type is `T`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("a"), "a", hasher1)
     * table.insertUnique(hasher1("b"), "b", hasher1)
     * // Will print in an arbitrary order.
     * for (x in table.iter()) {
     *     println("$x")
     * }
     * ```
     */
    public open fun iter(): Iter<T> = Iter(inner = this.raw.iter(), marker = Unit)

    /**
     * An iterator visiting all elements in arbitrary order,
     * with mutable references to the elements.
     * The iterator element type is `T`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), 1, hasher1)
     * table.insertUnique(hasher1(2), 2, hasher1)
     * table.insertUnique(hasher1(3), 3, hasher1)
     * // Update all values
     * for (idx in table.indices) {
     *     table[idx] *= 2
     * }
     * check(table.len() == 3)
     * var vec: List<Int> = mutableListOf()
     * for (value in table) {
     *     println("val: $value")
     *     vec.add(value)
     * }
     * // The `Iter` iterator produces items in arbitrary order, so the
     * // items must be sorted to test them against a sorted array.
     * vec.sortUnstable()
     * check(vec == listOf(2, 4, 6))
     * check(table.len() == 3)
     * ```
     */
    public open fun iterMut(): IterMut<T> = IterMut(inner = this.raw.iter(), marker = Unit)

    /**
     * An iterator visiting all elements in arbitrary order,
     * with pointers to the elements.
     * The iterator element type is `NonNull<T>`.
     *
     * This iterator is intended for APIs where only part of the elements are
     * mutable, with the remainder being immutable. In these cases, wrapping
     * the ordinary mutable iterator is incorrect because all components of
     * the element type will be [invariant]. A correct implementation will use
     * an appropriate [PhantomData] marker to make the immutable parts
     * [covariant] and the mutable parts invariant.
     *
     * [invariant]: https://doc.rust-lang.org/stable/reference/subtyping.html#r-subtyping.variance.invariant
     * [covariant]: https://doc.rust-lang.org/stable/reference/subtyping.html#r-subtyping.variance.covariant
     *
     * See the documentation for [UnsafeIter] for more information on how
     * to correctly use this.
     */
    public open fun unsafeIter(): UnsafeIter<T> = UnsafeIter(inner = this.raw.iter(), marker = Unit)

    /**
     * An iterator producing the `Int` indices of all occupied buckets.
     *
     * The order in which the iterator yields indices is unspecified
     * and may change in the future.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("a"), "a", hasher1)
     * table.insertUnique(hasher1("b"), "b", hasher1)
     * // Will print in an arbitrary order.
     * for (index in table.iterBuckets()) {
     *     println("$index: ${table.getBucket(index).unwrap()}")
     * }
     * ```
     */
    public open fun iterBuckets(): IterBuckets<T> = IterBuckets(inner = this.raw.fullBucketsIndices(), marker = Unit)

    /**
     * An iterator visiting all elements which may match a hash.
     * The iterator element type is `T`.
     *
     * This iterator may return elements from the table that have a hash value
     * different than the one provided. You should always validate the returned
     * values before using them.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("a"), "a", hasher1)
     * table.insertUnique(hasher1("a"), "b", hasher1)
     * table.insertUnique(hasher1("b"), "c", hasher1)
     * // Will print "a" and "b" (and possibly "c") in an arbitrary order.
     * for (x in table.iterHash(hasher1("a"))) {
     *     println("$x")
     * }
     * ```
     */
    public open fun iterHash(hash: ULong): IterHash<T> = IterHash(inner = this.raw.iterHash(hash), marker = Unit)

    /**
     * A mutable iterator visiting all elements which may match a hash.
     * The iterator element type is `T`.
     *
     * This iterator may return elements from the table that have a hash value
     * different than the one provided. You should always validate the returned
     * values before using them.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), 2, hasher1)
     * table.insertUnique(hasher1(1), 3, hasher1)
     * table.insertUnique(hasher1(2), 5, hasher1)
     * // Update matching values
     * for (value in table.iterHashMut(hasher1(1))) {
     *     value *= 2
     * }
     * check(table.len() == 3)
     * var vec: List<Int> = mutableListOf()
     * for (value1 in table) {
     *     println("val: $value1")
     *     vec.add(value1)
     * }
     * // The values will contain 4 and 6 and may contain either 5 or 10.
     * check(vec.contains(4))
     * check(vec.contains(6))
     * check(table.len() == 3)
     * ```
     */
    public open fun iterHashMut(hash: ULong): IterHashMut<T> = IterHashMut(inner = this.raw.iterHash(hash), marker = Unit)

    /**
     * An iterator producing the `Int` indices of all buckets which may match a hash.
     *
     * This iterator may return indices from the table that have a hash value
     * different than the one provided. You should always validate the returned
     * values before using them.
     *
     * The order in which the iterator yields indices is unspecified
     * and may change in the future.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("a"), "a", hasher1)
     * table.insertUnique(hasher1("a"), "b", hasher1)
     * table.insertUnique(hasher1("b"), "c", hasher1)
     * // Will print the indices with "a" and "b" (and possibly "c") in an arbitrary order.
     * for (index in table.iterHashBuckets(hasher1("a"))) {
     *     println("$index: ${table.getBucket(index).unwrap()}")
     * }
     * ```
     */
    public open fun iterHashBuckets(hash: ULong): IterHashBuckets<T> = IterHashBuckets(inner = this.raw.iterHashBuckets(hash), marker = Unit)

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` such that `f(e)` returns `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for (x in 1..6) {
     *     table.insertUnique(hasher1(x), x, hasher1)
     * }
     * table.retain({ x1 -> x1 % 2 == 0 })
     * check(table.len() == 3)
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        // Here we only use `iter` as a temporary, preventing use-after-free
        for (item in this.raw.iter()) {
            if (!f(item.asMut())) {
                this.raw.erase(item)
            }
        }
    }

    /**
     * Clears the set, returning all elements in an iterator.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for (x in 1..3) {
     *     table.insertUnique(hasher1(x), x, hasher1)
     * }
     * check(!table.isEmpty())
     * // print 1, 2, 3 in an arbitrary order
     * for (i in table.drain()) {
     *     println("$i")
     * }
     * check(table.isEmpty())
     * ```
     */
    public open fun drain(): Drain<T, A> = Drain(inner = this.raw.drain())

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
     * [retain]: HashTable.retain
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for (x in 0 until 8) {
     *     table.insertUnique(hasher1(x), x, hasher1)
     * }
     * val drained: List<Int> = table.extractIf({ v -> v % 2 == 0 }).toMutableList()
     * var evens = ArrayDeque(drained).collect<Vec>()
     * var odds = table.intoIter().collect<Vec>()
     * evens.sort()
     * odds.sort()
     * check(evens == mutableListOf(0, 2, 4, 6))
     * check(odds == mutableListOf(1, 3, 5, 7))
     * ```
     */
    public open fun extractIf(f: (T) -> Boolean): ExtractIf<T, (T) -> Boolean, A> = ExtractIf(f = f, inner = RawExtractIf(iter = this.raw.iter(), table = this.raw))

    /**
     * Attempts to get mutable references to `N` values in the map at once.
     *
     * The `eq` argument should be a closure such that `eq(i, k)` returns true if `k` is equal to
     * the `i`th key to be looked up.
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
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var libraries: HashTable<Pair<String, UInt>> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for ((k, v) in listOf(Pair("Bodleian Library", 1602), Pair("Athenæum", 1807), Pair("Herzogin-Anna-Amalia-Bibliothek", 1691), Pair("Library of Congress", 1800))) {
     *     libraries.insertUnique(hasher1(k), Pair(k, v), { (k1, _) -> hasher1(k1) })
     * }
     * val keys = listOf("Athenæum", "Library of Congress")
     * val got = libraries.getDisjointMut(keys.map({ k1 -> hasher1(k1) }), { i, value -> keys[i] == value.first })
     * check(got == listOf(Pair("Athenæum", 1807), Pair("Library of Congress", 1800)))
     * // Missing keys result in None
     * val keys1 = listOf("Athenæum", "New York Public Library")
     * val got1 = libraries.getDisjointMut(keys1.map({ k1 -> hasher1(k1) }), { i, value -> keys1[i] == value.first })
     * check(got1 == listOf(Pair("Athenæum", 1807), null))
     * ```
     *
     * ```kotlin
     * var libraries: HashTable<Pair<String, UInt>> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for ((k, v) in listOf(Pair("Athenæum", 1807), Pair("Library of Congress", 1800))) {
     *     libraries.insertUnique(hasher1(k), Pair(k, v), { (k1, _) -> hasher1(k1) })
     * }
     * // Duplicate keys result in a panic
     * val keys = listOf("Athenæum", "Athenæum")
     * val got = libraries.getDisjointMut(keys.map({ k1 -> hasher1(k1) }), { i, value -> keys[i] == value.first })
     * ```
     */
    public open fun getDisjointMut(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int = 0): List<T> = this.raw.getDisjointMut(hashes, eq)

    /**
     * Attempts to get mutable references to `N` values in the map at once.
     */
    public open fun getManyMut(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int = 0): List<T> = this.raw.getDisjointMut(hashes, eq)

    /**
     * Attempts to get mutable references to `N` values in the map at once, without validating that
     * the values are unique.
     *
     * The `eq` argument should be a closure such that `eq(i, k)` returns true if `k` is equal to
     * the `i`th key to be looked up.
     *
     * Returns an array of length `N` with the results of each query. `None` will be returned if
     * any of the keys are missing.
     *
     * For a safe alternative see [`getDisjointMut`](`HashTable.getDisjointMut`).
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
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var libraries: HashTable<Pair<String, UInt>> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for ((k, v) in listOf(Pair("Bodleian Library", 1602), Pair("Athenæum", 1807), Pair("Herzogin-Anna-Amalia-Bibliothek", 1691), Pair("Library of Congress", 1800))) {
     *     libraries.insertUnique(hasher1(k), Pair(k, v), { (k1, _) -> hasher1(k1) })
     * }
     * val keys = listOf("Athenæum", "Library of Congress")
     * val got = libraries.getDisjointMut(keys.map({ k1 -> hasher1(k1) }), { i, value -> keys[i] == value.first })
     * check(got == listOf(Pair("Athenæum", 1807), Pair("Library of Congress", 1800)))
     * // Missing keys result in None
     * val keys1 = listOf("Athenæum", "New York Public Library")
     * val got1 = libraries.getDisjointMut(keys1.map({ k1 -> hasher1(k1) }), { i, value -> keys1[i] == value.first })
     * check(got1 == listOf(Pair("Athenæum", 1807), null))
     * ```
     */
    public open fun getDisjointUncheckedMut(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int = 0): List<T> = this.raw.getDisjointUncheckedMut(hashes, eq)

    /**
     * Attempts to get mutable references to `N` values in the map at once, without validating that
     * the values are unique.
     */
    public open fun getManyUncheckedMut(hashes: ULongArray, eq: (Int, T) -> Boolean, n: Int = 0): List<T> = this.raw.getDisjointUncheckedMut(hashes, eq)

    /**
     * Returns the total amount of memory allocated internally by the hash
     * table, in bytes.
     *
     * The returned number is informational only. It is intended to be
     * primarily used for memory profiling.
     */
    public open fun allocationSize(): Int = this.raw.allocationSize()

    public open fun intoIter(): IntoIter<T, A> = IntoIter(inner = this.raw.intoIter())

    public open fun clone(): HashTable<T, A> = HashTable(raw = this.raw.clone())

    public open fun cloneFrom(source: HashTable<T, A>) {
        this.raw = source.raw
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "{", "}")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun <T, A> invoke(
            raw: RawTable<T, A>,
        ): HashTable<T, A> = HashTable<T, A>(
            rawCtorArg = raw,
        )

        /**
         * Creates an empty `HashTable` using the given allocator.
         *
         * The hash table is initially created with a capacity of 0, so it will not allocate until it
         * is first inserted into.
         *
         * # Examples
         *
         * ```kotlin
         * import bumpalo.Bump
         * import hashbrown.DefaultHashBuilder
         * import hashbrown.HashTable
         * import std.hash.BuildHasher
         *
         * val bump = Bump.new()
         * var table = HashTable.newIn(bump)
         * val hasher = DefaultHashBuilder.default()
         * val hasher1 = { value: Any? -> hasher.hashOne(value) }
         * // The created HashTable holds none elements
         * check(table.len() == 0)
         * // The created HashTable also doesn't allocate memory
         * check(table.capacity() == 0)
         * // Now we insert element inside created HashTable
         * table.insertUnique(hasher1("One"), "One", hasher1)
         * // We can see that the HashTable holds 1 element
         * check(table.len() == 1)
         * // And it also allocates some capacity
         * check(table.capacity() > 1)
         * ```
         */
        public fun <T, A> newIn(alloc: A): HashTable<T, A> where A : Allocator = HashTable(raw = RawTable.newIn(alloc))

        /**
         * Creates an empty `HashTable` with the specified capacity using the given allocator.
         *
         * The hash table will be able to hold at least `capacity` elements without
         * reallocating. If `capacity` is 0, the hash table will not allocate.
         *
         * # Examples
         *
         * ```kotlin
         * import bumpalo.Bump
         * import hashbrown.DefaultHashBuilder
         * import hashbrown.HashTable
         * import std.hash.BuildHasher
         *
         * val bump = Bump.new()
         * var table = HashTable.withCapacityIn(5, bump)
         * val hasher = DefaultHashBuilder.default()
         * val hasher1 = { value: Any? -> hasher.hashOne(value) }
         * // The created HashTable holds none elements
         * check(table.len() == 0)
         * // But it can hold at least 5 elements without reallocating
         * val emptyMapCapacity = table.capacity()
         * check(emptyMapCapacity >= 5)
         * // Now we insert some 5 elements inside created HashTable
         * table.insertUnique(hasher1("One"), "One", hasher1)
         * table.insertUnique(hasher1("Two"), "Two", hasher1)
         * table.insertUnique(hasher1("Three"), "Three", hasher1)
         * table.insertUnique(hasher1("Four"), "Four", hasher1)
         * table.insertUnique(hasher1("Five"), "Five", hasher1)
         * // We can see that the HashTable holds 5 elements
         * check(table.len() == 5)
         * // But its capacity isn't changed
         * check(table.capacity() == emptyMapCapacity)
         * ```
         */
        public fun <T, A> withCapacityIn(capacity: Int, alloc: A): HashTable<T, A> where A : Allocator = HashTable(raw = RawTable.withCapacityIn(capacity, alloc))

        public fun <T, A> default(defaultAFn: () -> A): HashTable<T, A> where A : Allocator = HashTable(raw = defaultAFn())
    }
}

/**
 * Creates an empty `HashTable`.
 *
 * The hash table is initially created with a capacity of 0, so it will not allocate until it
 * is first inserted into.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashTable
 *
 * var table: HashTable<String> = HashTable.new()
 * check(table.len() == 0)
 * check(table.capacity() == 0)
 * ```
 */
public fun <T> HashTable.Companion.new(): HashTable<T, Global> = HashTable(raw = RawTable.new())

/**
 * Creates an empty `HashTable` with the specified capacity.
 *
 * The hash table will be able to hold at least `capacity` elements without
 * reallocating. If `capacity` is 0, the hash table will not allocate.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.HashTable
 *
 * var table: HashTable<String> = HashTable.withCapacity(10)
 * check(table.len() == 0)
 * check(table.capacity() >= 10)
 * ```
 */
public fun <T> HashTable.Companion.withCapacity(capacity: Int): HashTable<T, Global> = HashTable(raw = RawTable.withCapacity(capacity))

/**
 * A view into a single entry in a table, which may either be vacant or occupied.
 *
 * This `sealed class` is constructed from the [entry] method on [HashTable].
 *
 * [entry]: HashTable.entry
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashTable
 * import hashbrown.hashtable.Entry
 * import hashbrown.hashtable.OccupiedEntry
 * import std.hash.BuildHasher
 *
 * var table = HashTable.new()
 * val hasher = DefaultHashBuilder.default()
 * val hasher1 = { value: Any? -> hasher.hashOne(value) }
 * for (x in listOf("a", "b", "c")) {
 *     table.insertUnique(hasher1(x), x, hasher1)
 * }
 * check(table.len() == 3)
 * // Existing value (insert)
 * val entry: Entry = table.entry(hasher1("a"), { x1 -> x1 == "a" }, hasher1)
 * val rawO: OccupiedEntry = entry.insert("a")
 * check(table.len() == 3)
 * // Nonexistent value (insert)
 * table.entry(hasher1("d"), { x1 -> x1 == "d" }, hasher1).insert("d")
 * // Existing value (orInsert)
 * table.entry(hasher1("b"), { x1 -> x1 == "b" }, hasher1).orInsert("b")
 * // Nonexistent value (orInsert)
 * table.entry(hasher1("e"), { x1 -> x1 == "e" }, hasher1).orInsert("e")
 * println("Our HashTable: $table")
 * var vec: Vec = table.iter().copied().collect()
 * // The `Iter` iterator produces items in arbitrary order, so the
 * // items must be sorted to test them against a sorted array.
 * vec.sortUnstable()
 * check(vec == listOf("a", "b", "c", "d", "e"))
 * ```
 */
public sealed class Entry<T, A> {
    /**
     * An occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import hashbrown.hashtable.OccupiedEntry
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * for (x in listOf("a", "b")) {
     *     table.insertUnique(hasher1(x), x, hasher1)
     * }
     * when (val matchVal = table.entry(hasher1("a"), { x1 -> x1 == "a" }, hasher1)) {
     *     is Entry.Vacant -> error("unreachable")
     *     is Entry.Occupied -> {}
     * }
     * ```
     */
    public class Occupied<T, A> internal constructor(valueCtorArg: OccupiedEntry<T, A>) : Entry<T, A>() {
        public var value: OccupiedEntry<T, A> = valueCtorArg
            internal set

        public operator fun component1(): OccupiedEntry<T, A> = this.value

        public fun copy(value: OccupiedEntry<T, A> = this.value): Occupied<T, A> = Occupied<T, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Occupied<*, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T, A> invoke(
                value: OccupiedEntry<T, A>,
            ): Occupied<T, A> = Occupied<T, A>(
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
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import hashbrown.hashtable.OccupiedEntry
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * when (val matchVal = table.entry(hasher1("a"), { x -> x == "a" }, hasher1)) {
     *     is Entry.Vacant -> {}
     *     is Entry.Occupied -> error("unreachable")
     * }
     * ```
     */
    public class Vacant<T, A> internal constructor(valueCtorArg: VacantEntry<T, A>) : Entry<T, A>() {
        public var value: VacantEntry<T, A> = valueCtorArg
            internal set

        public operator fun component1(): VacantEntry<T, A> = this.value

        public fun copy(value: VacantEntry<T, A> = this.value): Vacant<T, A> = Vacant<T, A>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Vacant<*, *>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T, A> invoke(
                value: VacantEntry<T, A>,
            ): Vacant<T, A> = Vacant<T, A>(
                valueCtorArg = value,
            )
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is Entry.Vacant<*, *> -> {
                val (v) = this as Entry.Vacant<T, A>
                run { DebugTuple(f, "Entry").field(v).finish(); Result.Ok<Unit, Error>(Unit) }
            }
            is Entry.Occupied<*, *> -> {
                val (o) = this as Entry.Occupied<T, A>
                run { DebugTuple(f, "Entry").field(o).finish(); Result.Ok<Unit, Error>(Unit) }
            }
        }

    /**
     * Sets the value of the entry, replacing any existing value if there is
     * one, and returns an [OccupiedEntry].
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table: HashTable<String> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * val entry = table.entry(hasher1("horseyland"), { x -> x == "horseyland" }, hasher1).insert("horseyland")
     * check(entry.get() == "horseyland")
     * ```
     */
    public open fun insert(value: T): OccupiedEntry<T, A> =
        when (this) {
            is Entry.Occupied<*, *> -> {
                val (entry) = this as Entry.Occupied<T, A>
                entry.getMut() = value
                entry
            }
            is Entry.Vacant<*, *> -> {
                val (entry1) = this as Entry.Vacant<T, A>
                entry1.insert(value)
            }
        }

    /**
     * Ensures a value is in the entry by inserting if it was vacant.
     *
     * Returns an [OccupiedEntry] pointing to the now-occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table: HashTable<String> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * // nonexistent key
     * table.entry(hasher1("poneyland"), { x -> x == "poneyland" }, hasher1).orInsert("poneyland")
     * check(table.find(hasher1("poneyland"), { x -> x == "poneyland" }) != null)
     * // existing key
     * table.entry(hasher1("poneyland"), { x -> x == "poneyland" }, hasher1).orInsert("poneyland")
     * check(table.find(hasher1("poneyland"), { x -> x == "poneyland" }) != null)
     * check(table.len() == 1)
     * ```
     */
    public open fun orInsert(default: T): OccupiedEntry<T, A> =
        when (this) {
            is Entry.Occupied<*, *> -> {
                val (entry) = this as Entry.Occupied<T, A>
                entry
            }
            is Entry.Vacant<*, *> -> {
                val (entry1) = this as Entry.Vacant<T, A>
                entry1.insert(default)
            }
        }

    /**
     * Ensures a value is in the entry by inserting the result of the default function if empty..
     *
     * Returns an [OccupiedEntry] pointing to the now-occupied entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table: HashTable<String> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.entry(hasher1("poneyland"), { x -> x == "poneyland" }, { value -> hasher1(value) }).orInsertWith({ "poneyland".toString() })
     * check(table.find(hasher1("poneyland"), { x -> x == "poneyland" }) != null)
     * ```
     */
    public open fun orInsertWith(default: () -> T): OccupiedEntry<T, A> =
        when (this) {
            is Entry.Occupied<*, *> -> {
                val (entry) = this as Entry.Occupied<T, A>
                entry
            }
            is Entry.Vacant<*, *> -> {
                val (entry1) = this as Entry.Vacant<T, A>
                entry1.insert(default())
            }
        }

    /**
     * Provides in-place mutable access to an occupied entry before any
     * potential inserts into the table.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table: HashTable<Pair<String, UInt>> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.entry(hasher1("poneyland"), { (x, _) -> x == "poneyland" }, { (k, _) -> hasher1(k) }).andModify({ (_, v) -> v += 1 }).orInsert(Pair("poneyland", 42))
     * check(table.find(hasher1("poneyland"), { (k, _) -> k == "poneyland" }) == Pair("poneyland", 42))
     * table.entry(hasher1("poneyland"), { (x, _) -> x == "poneyland" }, { (k, _) -> hasher1(k) }).andModify({ (_, v) -> v += 1 }).orInsert(Pair("poneyland", 42))
     * check(table.find(hasher1("poneyland"), { (k, _) -> k == "poneyland" }) == Pair("poneyland", 43))
     * ```
     */
    public open fun andModify(f: (T) -> Unit): Entry<T, A> =
        when (this) {
            is Entry.Occupied<*, *> -> {
                val (entry) = this as Entry.Occupied<T, A>
                f(entry.getMut())
                Entry.Occupied(entry)
            }
            is Entry.Vacant<*, *> -> {
                val (entry1) = this as Entry.Vacant<T, A>
                Entry.Vacant(entry1)
            }
        }

    /**
     * Converts the `Entry` into a mutable reference to the underlying table.
     */
    public open fun intoTable(): HashTable<T, A> =
        when (this) {
            is Entry.Occupied<*, *> -> {
                val (entry) = this as Entry.Occupied<T, A>
                entry.table
            }
            is Entry.Vacant<*, *> -> {
                val (entry1) = this as Entry.Vacant<T, A>
                entry1.table
            }
        }
}

/**
 * A view into an occupied entry in a `HashTable`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashTable
 * import hashbrown.hashtable.Entry
 * import hashbrown.hashtable.OccupiedEntry
 * import std.hash.BuildHasher
 *
 * var table = HashTable.new()
 * val hasher = DefaultHashBuilder.default()
 * val hasher1 = { value: Any? -> hasher.hashOne(value) }
 * for (x in listOf("a", "b", "c")) {
 *     table.insertUnique(hasher1(x), x, hasher1)
 * }
 * check(table.len() == 3)
 * val entryO: OccupiedEntry = table.findEntry(hasher1("a"), { x1 -> x1 == "a" }).unwrap()
 * check(table.len() == 3)
 * // Existing key
 * when (val matchVal = table.entry(hasher1("a"), { x1 -> x1 == "a" }, hasher1)) {
 *     is Entry.Vacant -> error("unreachable")
 *     is Entry.Occupied -> {
 *         val (view) = matchVal
 *         check(view.get() == "a")
 *     }
 * }
 * check(table.len() == 3)
 * // Existing key (take)
 * when (val matchVal1 = table.entry(hasher1("c"), { x1 -> x1 == "c" }, hasher1)) {
 *     is Entry.Vacant -> error("unreachable")
 *     is Entry.Occupied -> {
 *         val (view1) = matchVal1
 *         check(view1.remove().first == "c")
 *     }
 * }
 * check(table.find(hasher1("c"), { x1 -> x1 == "c" }) == null)
 * check(table.len() == 2)
 * ```
 */
public open class OccupiedEntry<T, A> internal constructor(
    public var bucket: Bucket<T>,
    tableCtorArg: HashTable<T, A>,
) where A : Allocator {
    public var table: HashTable<T, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "OccupiedEntry").field("value", this.get()).finish(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Takes the value out of the entry, and returns it along with a
     * `VacantEntry` that can be used to insert another value with the same
     * hash as the one that was just removed.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table: HashTable<String> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * // The table is empty
     * check(table.isEmpty() && table.capacity() == 0)
     * table.insertUnique(hasher1("poneyland"), "poneyland", hasher1)
     * val capacityBeforeRemove = table.capacity()
     * when (val matchVal = table.entry(hasher1("poneyland"), { x -> x == "poneyland" }, hasher1)) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         check(o.remove().first == "poneyland")
     *     }
     *     else -> {}
     * }
     * check(table.find(hasher1("poneyland"), { x -> x == "poneyland" }) == null)
     * // Now table hold none elements but capacity is equal to the old one
     * check(table.len() == 0 && table.capacity() == capacityBeforeRemove)
     * ```
     */
    public open fun remove(): Pair<T, VacantEntry<T, A>> {
        val (value, index, tag) = this.table.raw.removeTagged(this.bucket)
        return Pair(value, VacantEntry(tag = tag, index = index, table = this.table))
    }

    /**
     * Gets a reference to the value in the entry.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table: HashTable<String> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("poneyland"), "poneyland", hasher1)
     * when (val matchVal = table.entry(hasher1("poneyland"), { x -> x == "poneyland" }, hasher1)) {
     *     is Entry.Vacant -> error("panic")
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         check(entry.get() == "poneyland")
     *     }
     * }
     * ```
     */
    public open fun get(): T = this.bucket.asRef()

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
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table: HashTable<Pair<String, UInt>> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("poneyland"), Pair("poneyland", 12), { (k, _) -> hasher1(k) })
     * check(table.find(hasher1("poneyland"), { (x, _) -> x == "poneyland" }) == Pair("poneyland", 12))
     * when (val matchVal = table.entry(hasher1("poneyland"), { (x, _) -> x == "poneyland" }, { (k, _) -> hasher1(k) })) {
     *     is Entry.Occupied -> {
     *         val (o) = matchVal
     *         o.getMut().second += 10
     *         check(o.get().second == 22)
     *         // We can use the same Entry multiple times.
     *         o.getMut().second += 2
     *     }
     *     else -> {}
     * }
     * check(table.find(hasher1("poneyland"), { (x, _) -> x == "poneyland" }) == Pair("poneyland", 24))
     * ```
     */
    public open fun getMut(): T = this.bucket.asMut()

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the value in the entry
     * with a scope bound to the table itself.
     *
     * If you need multiple references to the `OccupiedEntry`, see [getMut].
     *
     * [getMut]: #method.getMut
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table: HashTable<Pair<String, UInt>> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1("poneyland"), Pair("poneyland", 12), { (k, _) -> hasher1(k) })
     * check(table.find(hasher1("poneyland"), { (x, _) -> x == "poneyland" }) == Pair("poneyland", 12))
     * val value: Pair<String, UInt>
     * when (val matchVal = table.entry(hasher1("poneyland"), { (x, _) -> x == "poneyland" }, { (k, _) -> hasher1(k) })) {
     *     is Entry.Occupied -> {
     *         val (entry) = matchVal
     *         value = entry.intoMut()
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * value.second += 10u
     * check(table.find(hasher1("poneyland"), { (x, _) -> x == "poneyland" }) == Pair("poneyland", 22))
     * ```
     */
    public open fun intoMut(): T = this.bucket.asMut()

    /**
     * Converts the `OccupiedEntry` into a mutable reference to the underlying
     * table.
     */
    public open fun intoTable(): HashTable<T, A> = this.table

    /**
     * Returns the bucket index in the table for this entry.
     *
     * This can be used to store a borrow-free "reference" to the entry, later using
     * [HashTable.getBucket], [HashTable.getBucketMut], or
     * [HashTable.getBucketEntry] to access it again without hash probing.
     *
     * The index is only meaningful as long as the table is not resized and no entries are added
     * or removed. After such changes, it may end up pointing to a different entry or none at all.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * table.insertUnique(hasher1(1), Pair(1, 1), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(2), Pair(2, 2), { value -> hasher1(value.first) })
     * table.insertUnique(hasher1(3), Pair(3, 3), { value -> hasher1(value.first) })
     * val index = table.entry(hasher1(2), { value -> value.first == 2 }, { value -> hasher1(value.first) }).orInsert(Pair(2, -2)).bucketIndex()
     * check(table.getBucket(index) == Pair(2, 2))
     * // Full mutation would invalidate any normal reference
     * for ((key, value) in table) {
     *     value *= 11
     * }
     * // The index still reaches the same key with the updated value
     * check(table.getBucket(index) == Pair(2, 22))
     * ```
     */
    public open fun bucketIndex(): Int = this.table.raw.bucketIndex(this.bucket)

    /**
     * Provides owned access to the value of the entry and allows to replace or
     * remove it based on the value of the returned option.
     *
     * The hash of the new item should be the same as the old item.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { (key, _): Any? -> hasher.hashOne(key) }
     * table.insertUnique(hasher1(Pair("poneyland", 42)), Pair("poneyland", 42), hasher1)
     * val entry = when (val matchVal = table.entry(hasher1(Pair("poneyland", 42)), { entry -> entry.first == "poneyland" }, hasher1)) {
     *     is Entry.Occupied -> {
     *         val (e) = matchVal
     *         e.replaceEntryWith({ (k, v) ->
     *             check(k == "poneyland")
     *             check(v == 42)
     *             Pair("poneyland", v + 1)
     *         })
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * when (entry) {
     *     is Entry.Occupied -> {
     *         val (e1) = entry
     *         check(e1.get() == Pair("poneyland", 43))
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * val entry1 = when (val matchVal1 = table.entry(hasher1(Pair("poneyland", 43)), { entry1 -> entry1.first == "poneyland" }, hasher1)) {
     *     is Entry.Occupied -> {
     *         val (e2) = matchVal1
     *         e2.replaceEntryWith({ (k, v) -> null })
     *     }
     *     is Entry.Vacant -> error("panic")
     * }
     * when (entry1) {
     *     is Entry.Vacant -> {
     *         val (e3) = entry1
     *     }
     *     is Entry.Occupied -> error("panic")
     * }
     * check(table.isEmpty())
     * ```
     */
    public open fun replaceEntryWith(f: (T) -> T?): Entry<T, A> =
        when (val matchVal = this.table.raw.replaceBucketWith(this.bucket.clone(), f)) {
            null -> Entry.Occupied(this)
            else -> {
                val tag = matchVal
                Entry.Vacant(VacantEntry(tag = tag, index = this.bucketIndex(), table = this.table))
            }
        }

    public companion object {
        public operator fun <T, A> invoke(
            bucket: Bucket<T>,
            table: HashTable<T, A>,
        ): OccupiedEntry<T, A> = OccupiedEntry<T, A>(
            bucket = bucket,
            tableCtorArg = table,
        )
    }
}

/**
 * A view into a vacant entry in a `HashTable`.
 * It is part of the [Entry] enum.
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashTable
 * import hashbrown.hashtable.Entry
 * import hashbrown.hashtable.VacantEntry
 * import std.hash.BuildHasher
 *
 * var table: HashTable<String> = HashTable.new()
 * val hasher = DefaultHashBuilder.default()
 * val hasher1 = { value: Any? -> hasher.hashOne(value) }
 * val entryV: VacantEntry = when (val matchVal = table.entry(hasher1("a"), { x -> x == "a" }, hasher1)) {
 *     is Entry.Vacant -> {
 *         val (view) = matchVal
 *         view
 *     }
 *     is Entry.Occupied -> error("unreachable")
 * }
 * entryV.insert("a")
 * check(table.find(hasher1("a"), { x -> x == "a" }) != null && table.len() == 1)
 * // Nonexistent key (insert)
 * when (val matchVal1 = table.entry(hasher1("b"), { x -> x == "b" }, hasher1)) {
 *     is Entry.Vacant -> {
 *         val (view1) = matchVal1
 *         view1.insert("b")
 *     }
 *     is Entry.Occupied -> error("unreachable")
 * }
 * check(table.find(hasher1("b"), { x -> x == "b" }) != null && table.len() == 2)
 * ```
 */
public open class VacantEntry<T, A> internal constructor(
    public var tag: Tag,
    public var index: Int,
    tableCtorArg: HashTable<T, A>,
) where A : Allocator {
    public var table: HashTable<T, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("VacantEntry"); Result.Ok<Unit, Error>(Unit) }

    /**
     * Inserts a new element into the table with the hash that was used to
     * obtain the `VacantEntry`.
     *
     * An `OccupiedEntry` is returned for the newly inserted element.
     *
     * # Examples
     *
     * ```kotlin
     * import hashbrown.DefaultHashBuilder
     * import hashbrown.HashTable
     * import hashbrown.hashtable.Entry
     * import std.hash.BuildHasher
     *
     * var table: HashTable<String> = HashTable.new()
     * val hasher = DefaultHashBuilder.default()
     * val hasher1 = { value: Any? -> hasher.hashOne(value) }
     * when (val matchVal = table.entry(hasher1("poneyland"), { x -> x == "poneyland" }, hasher1)) {
     *     is Entry.Vacant -> {
     *         val (o) = matchVal
     *         o.insert("poneyland")
     *     }
     *     else -> {}
     * }
     * check(table.find(hasher1("poneyland"), { x -> x == "poneyland" }) == "poneyland")
     * ```
     */
    public open fun insert(value: T): OccupiedEntry<T, A> {
        val bucket = this.table.raw.insertTaggedAtIndex(this.tag, this.index, value)
        return OccupiedEntry(bucket = bucket, table = this.table)
    }

    /**
     * Converts the `VacantEntry` into a mutable reference to the underlying
     * table.
     */
    public open fun intoTable(): HashTable<T, A> = this.table

    public companion object {
        public operator fun <T, A> invoke(
            tag: Tag,
            index: Int,
            table: HashTable<T, A>,
        ): VacantEntry<T, A> = VacantEntry<T, A>(
            tag = tag,
            index = index,
            tableCtorArg = table,
        )
    }
}

/**
 * Type representing the absence of an entry, as returned by [HashTable.findEntry]
 * and [HashTable.getBucketEntry].
 *
 * This type only exists due to [limitations] in Rust's NLL borrow checker. In
 * the future, those methods will return an `Option<OccupiedEntry>` and this
 * type will be removed.
 *
 * [limitations]: https://smallcultfollowing.com/babysteps/blog/2018/06/15/mir-based-borrow-check-nll-status-update/#polonius
 *
 * # Examples
 *
 * ```kotlin
 * import hashbrown.DefaultHashBuilder
 * import hashbrown.HashTable
 * import hashbrown.hashtable.AbsentEntry
 * import hashbrown.hashtable.Entry
 * import std.hash.BuildHasher
 *
 * var table: HashTable<String> = HashTable.new()
 * val hasher = DefaultHashBuilder.default()
 * val hasher1 = { value: Any? -> hasher.hashOne(value) }
 * val entryV: AbsentEntry = table.findEntry(hasher1("a"), { x -> x == "a" }).unwrapErr()
 * entryV.intoTable().insertUnique(hasher1("a"), "a", hasher1)
 * check(table.find(hasher1("a"), { x -> x == "a" }) != null && table.len() == 1)
 * // Nonexistent key (insert)
 * when (val matchVal = table.entry(hasher1("b"), { x -> x == "b" }, hasher1)) {
 *     is Entry.Vacant -> {
 *         val (view) = matchVal
 *         view.insert("b")
 *     }
 *     is Entry.Occupied -> error("unreachable")
 * }
 * check(table.find(hasher1("b"), { x -> x == "b" }) != null && table.len() == 2)
 * ```
 */
public open class AbsentEntry<T, A> internal constructor(
    tableCtorArg: HashTable<T, A>,
) where A : Allocator {
    public var table: HashTable<T, A> = tableCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("AbsentEntry"); Result.Ok<Unit, Error>(Unit) }

    /**
     * Converts the `AbsentEntry` into a mutable reference to the underlying
     * table.
     */
    public open fun intoTable(): HashTable<T, A> = this.table

    public companion object {
        public operator fun <T, A> invoke(
            table: HashTable<T, A>,
        ): AbsentEntry<T, A> = AbsentEntry<T, A>(
            tableCtorArg = table,
        )
    }
}

/**
 * An iterator over the entries of a `HashTable` in arbitrary order.
 * The iterator element type is `T`.
 *
 * This `class` is created by the [iter] method on [HashTable]. See its
 * documentation for more.
 *
 * [iter]: HashTable.iter
 */
public open class Iter<T>(
    public var inner: RawIter<T>,
    public var marker: Unit,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val bucket = matchVal
                bucket.asRef()
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.inner.fold(init, { acc, bucket -> f(acc, bucket.asRef()) })

    public open fun len(): Int = this.inner.len()

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Iter<T> = Iter(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): Iter<T> = Iter(inner = RawIter.default(), marker = Unit)
    }
}

/**
 * A mutable iterator over the entries of a `HashTable` in arbitrary order.
 * The iterator element type is `T`.
 *
 * This `class` is created by the [iterMut] method on [HashTable]. See its
 * documentation for more.
 *
 * [iterMut]: HashTable.iterMut
 */
public open class IterMut<T>(
    public var inner: RawIter<T>,
    public var marker: Unit,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a iterator of references over the remaining items.
     */
    public open fun iter(): Iter<T> = Iter(inner = this.inner.clone(), marker = Unit)

    public open fun next(): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val bucket = matchVal
                bucket.asMut()
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.inner.fold(init, { acc, bucket -> f(acc, bucket.asMut()) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): IterMut<T> = IterMut(inner = RawIter.default(), marker = Unit)
    }
}

/**
 * An unchecked iterator over the entries of a `HashTable` in arbitrary order.
 * The iterator element type is `NonNull<T>`.
 *
 * This `class` is created by the [unsafeIter] method on [HashTable].
 *
 * This is used for implementations of iterators with "mixed" mutability on
 * the iterated elements. For example, a mutable iterator for a map may return
 * an immutable key alongside a mutable value, even though these are both
 * stored inside the table.
 *
 * If you have no idea what any of this means, you probably should be using
 * [IterMut] instead, as it does not have any safety requirements.
 *
 * # Safety
 *
 * In order to correctly use this iterator, it should be wrapped in a safe
 * iterator struct with the appropriate [PhantomData] marker to indicate the
 * correct [variance].
 *
 * For example, below is a simplified [hashMap.IterMut] implementation
 * that correctly returns a [covariant] key, and an [invariant] value:
 *
 * [variance]: https://doc.rust-lang.org/stable/reference/subtyping.html#r-subtyping.variance
 * [covariant]: https://doc.rust-lang.org/stable/reference/subtyping.html#r-subtyping.variance.covariant
 * [invariant]: https://doc.rust-lang.org/stable/reference/subtyping.html#r-subtyping.variance.invariant
 * [unsafeIter]: HashTable.unsafeIter
 *
 * ```kotlin
 * import hashbrown.hashTable
 *
 * open class IterMut<K, V>(
 *     public var inner: hashtable.UnsafeIter<Pair<K, V>>,
 *     // Covariant over keys, invariant over values
 *     public var marker: Unit,
 * ) : Iterable<Pair<K, V>> {
 *     override fun iterator(): Iterator<Pair<K, V>> = object : AbstractIterator<Pair<K, V>>() {
 *         override fun computeNext() {
 *             when (val v = this@IterMut.next()) {
 *                 null -> done()
 *                 else -> setNext(v)
 *             }
 *         }
 *     }
 *
 *     public open fun next(): Pair<K, V>? {
 *         // SAFETY: The scope of the dereferenced pointer is derived from
 *         // the scope of its iterator, ensuring that it's always valid.
 *         // Additionally, we match the mutability in `self.marker` to ensure
 *         // the correct variance.
 *         var (key, value) = (this.inner.next() ?: return null).asMut()
 *         return Pair(key, value)
 *     }
 * }
 * ```
 */
public open class UnsafeIter<T>(
    public var inner: RawIter<T>,
    public var marker: Unit,
) : Iterable<NonNull> {
    override fun iterator(): Iterator<NonNull> = object : AbstractIterator<NonNull>() {
        override fun computeNext() {
            when (val v = this@UnsafeIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    /**
     * Returns a iterator of references over the remaining items.
     */
    public open fun iter(): Iter<T> = Iter(inner = this.inner.clone(), marker = Unit)

    public open fun next(): NonNull? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val bucket = matchVal
                NonNull.newUnchecked(bucket.asPtr())
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, NonNull) -> B): B = this.inner.fold(init, { acc, bucket -> f(acc, NonNull.newUnchecked(bucket.asPtr())) })

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): UnsafeIter<T> = UnsafeIter(inner = RawIter.default(), marker = Unit)
    }
}

/**
 * An iterator producing the `Int` indices of all occupied buckets,
 * within the range `0 .. table.numBuckets()`.
 *
 * The order in which the iterator yields indices is unspecified
 * and may change in the future.
 *
 * This `class` is created by the [HashTable.iterBuckets] method. See its
 * documentation for more.
 */
public open class IterBuckets<T>(
    public var inner: FullBucketsIndices,
    public var marker: Unit,
) : Iterable<Int> {
    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@IterBuckets.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IterBuckets<T> = IterBuckets(inner = this.inner.clone(), marker = Unit)

    public open fun next(): Int? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): IterBuckets<T> = IterBuckets(inner = FullBucketsIndices.default(), marker = Unit)
    }
}

/**
 * An iterator over the entries of a `HashTable` that could match a given hash.
 * The iterator element type is `T`.
 *
 * This `class` is created by the [iterHash] method on [HashTable]. See its
 * documentation for more.
 *
 * [iterHash]: HashTable.iterHash
 */
public open class IterHash<T>(
    public var inner: RawIterHash<T>,
    public var marker: Unit,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IterHash.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val bucket = matchVal
                bucket.asRef()
            }
        }
    }

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.inner.fold(init, { acc, bucket -> f(acc, bucket.asRef()) })

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): IterHash<T> = IterHash(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): IterHash<T> = IterHash(inner = RawIterHash.default(), marker = Unit)
    }
}

/**
 * A mutable iterator over the entries of a `HashTable` that could match a given hash.
 * The iterator element type is `T`.
 *
 * This `class` is created by the [iterHashMut] method on [HashTable]. See its
 * documentation for more.
 *
 * [iterHashMut]: HashTable.iterHashMut
 */
public open class IterHashMut<T>(
    public var inner: RawIterHash<T>,
    public var marker: Unit,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IterHashMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? {
        // Avoid `Option.map` because it bloats LLVM IR.
        return when (val matchVal = this.inner.next()) {
            null -> null
            else -> {
                val bucket = matchVal
                bucket.asMut()
            }
        }
    }

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.inner.fold(init, { acc, bucket -> f(acc, bucket.asMut()) })

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(IterHash(inner = this.inner.clone(), marker = Unit)).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): IterHashMut<T> = IterHashMut(inner = RawIterHash.default(), marker = Unit)
    }
}

/**
 * An iterator producing the `Int` indices of all buckets which may match a hash.
 *
 * This `class` is created by the [HashTable.iterHashBuckets] method. See its
 * documentation for more.
 */
public open class IterHashBuckets<T>(
    public var inner: RawIterHashIndices,
    public var marker: Unit,
) : Iterable<Int> {
    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@IterHashBuckets.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IterHashBuckets<T> = IterHashBuckets(inner = this.inner.clone(), marker = Unit)

    public open fun next(): Int? = this.inner.next()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <T> default(): IterHashBuckets<T> = IterHashBuckets(inner = RawIterHashIndices.default(), marker = Unit)
    }
}

/**
 * An owning iterator over the entries of a `HashTable` in arbitrary order.
 * The iterator element type is `T`.
 *
 * This `class` is created by the [intoIter] method on [HashTable]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 * The table cannot be used after calling that method.
 *
 * [intoIter]: HashTable.intoIter
 */
public open class IntoIter<T, A> internal constructor(
    innerCtorArg: RawIntoIter<T, A>,
) : Iterable<T> where A : Allocator {
    public var inner: RawIntoIter<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
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
    public open fun iter(): Iter<T> = Iter(inner = this.inner.iter(), marker = Unit)

    public open fun next(): T? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.inner.fold(init, f)

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun <T, A> invoke(
            inner: RawIntoIter<T, A>,
        ): IntoIter<T, A> = IntoIter<T, A>(
            innerCtorArg = inner,
        )

        public fun <T, A> default(): IntoIter<T, A> where A : Allocator = IntoIter(inner = RawIntoIter.default())
    }
}

/**
 * A draining iterator over the items of a `HashTable`.
 *
 * This `class` is created by the [drain] method on [HashTable].
 * See its documentation for more.
 *
 * [drain]: HashTable.drain
 */
public open class Drain<T, A : Allocator> internal constructor(
    innerCtorArg: RawDrain<T, A>,
) : Iterable<T> {
    public var inner: RawDrain<T, A> = innerCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
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
    public open fun iter(): Iter<T> = Iter(inner = this.inner.iter(), marker = Unit)

    public open fun next(): T? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <B> fold(init: B, f: (B, T) -> B): B = this.inner.fold(init, f)

    public open fun len(): Int = this.inner.len()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: RawDrain<T, A>,
        ): Drain<T, A> = Drain<T, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * A draining iterator over entries of a `HashTable` which don't satisfy the predicate `f`.
 *
 * This `class` is created by [HashTable.extractIf]. See its
 * documentation for more.
 */
public open class ExtractIf<T, F, A : Allocator> internal constructor(
    public var f: F,
    innerCtorArg: RawExtractIf<T, A>,
) {
    public var inner: RawExtractIf<T, A> = innerCtorArg
        internal set

    public companion object {
        public operator fun <T, F, A : Allocator> invoke(
            f: F,
            inner: RawExtractIf<T, A>,
        ): ExtractIf<T, F, A> = ExtractIf<T, F, A>(
            f = f,
            innerCtorArg = inner,
        )
    }
}

public fun <T, A : Allocator> ExtractIf<T, F, A>.next(): T? = this.inner.next({ value -> (this.f)(value) })

public fun <T, A : Allocator> ExtractIf<T, F, A>.sizeHint(): Pair<Int, Int?> = Pair(0, this.inner.iter.sizeHint().second)
