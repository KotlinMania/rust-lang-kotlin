// port-lint: source external_trait_impls/rayon/map.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map

import io.github.kotlinmania.rustlang.HashMap
import io.github.kotlinmania.rustlang.alloc.Allocator
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.externaltraitimpls.rayon.helpers.collect
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw.RawIntoParIter
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw.RawParDrain
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw.RawParIter
import io.github.kotlinmania.rustlang.rayon.iter.IntoParallelIterator
import io.github.kotlinmania.rustlang.rayon.iter.ParallelIterator
import io.github.kotlinmania.rustlang.rayon.iter.plumbing.UnindexedConsumer

// Rayon extensions for `HashMap`.

/**
 * Parallel iterator over shared references to entries in a map.
 *
 * This iterator is created by the [parIter] method on [HashMap]
 * (provided by the [IntoParallelRefIterator] trait).
 * See its documentation for more.
 *
 * [parIter]: rayon.iter.IntoParallelRefIterator.parIter
 * [IntoParallelRefIterator]: rayon.iter.IntoParallelRefIterator
 */
public open class ParIter<K, V> internal constructor(
    innerCtorArg: RawParIter<Pair<K, V>>,
    public var marker: Unit,
) : ParallelIterator {
    public var inner: RawParIter<Pair<K, V>> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<Pair<K, V>> =
        this.inner.map({ x -> run {
            val r = x.asRef()
            Pair(r.first, r.second)
        } }).driveUnindexed(consumer)

    public open fun clone(): ParIter<K, V> = ParIter(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder) {
        val iter =
            this.inner.iter().map({ x -> run {
                val r = x.asRef()
                Pair(r.first, r.second)
            } })
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(iter).finish()
    }

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawParIter<Pair<K, V>>,
            marker: Unit,
        ): ParIter<K, V> = ParIter<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )
    }
}

/**
 * Parallel iterator over shared references to keys in a map.
 *
 * This iterator is created by the [parKeys] method on [HashMap].
 * See its documentation for more.
 *
 * [parKeys]: HashMap.parKeys
 */
public open class ParKeys<K, V> internal constructor(
    innerCtorArg: RawParIter<Pair<K, V>>,
    public var marker: Unit,
) : ParallelIterator {
    public var inner: RawParIter<Pair<K, V>> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<K> = this.inner.map({ x -> x.asRef().first }).driveUnindexed(consumer)

    public open fun clone(): ParKeys<K, V> = ParKeys(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder) {
        val iter = this.inner.iter().map({ x -> x.asRef().first })
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(iter).finish()
    }

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawParIter<Pair<K, V>>,
            marker: Unit,
        ): ParKeys<K, V> = ParKeys<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )
    }
}

/**
 * Parallel iterator over shared references to values in a map.
 *
 * This iterator is created by the [parValues] method on [HashMap].
 * See its documentation for more.
 *
 * [parValues]: HashMap.parValues
 */
public open class ParValues<K, V> internal constructor(
    innerCtorArg: RawParIter<Pair<K, V>>,
    public var marker: Unit,
) : ParallelIterator {
    public var inner: RawParIter<Pair<K, V>> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<V> = this.inner.map({ x -> x.asRef().second }).driveUnindexed(consumer)

    public open fun clone(): ParValues<K, V> = ParValues(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder) {
        val iter = this.inner.iter().map({ x -> x.asRef().second })
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(iter).finish()
    }

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawParIter<Pair<K, V>>,
            marker: Unit,
        ): ParValues<K, V> = ParValues<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )
    }
}

/**
 * Parallel iterator over mutable references to entries in a map.
 *
 * This iterator is created by the [parIterMut] method on [HashMap]
 * (provided by the [IntoParallelRefMutIterator] trait).
 * See its documentation for more.
 *
 * [parIterMut]: rayon.iter.IntoParallelRefMutIterator.parIterMut
 * [IntoParallelRefMutIterator]: rayon.iter.IntoParallelRefMutIterator
 */
public open class ParIterMut<K, V> internal constructor(
    innerCtorArg: RawParIter<Pair<K, V>>,
    public var marker: Unit,
) : ParallelIterator {
    public var inner: RawParIter<Pair<K, V>> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<Pair<K, V>> =
        this.inner.map({ x -> run {
            val r = x.asMut()
            Pair(r.first, r.second)
        } }).driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder) {
        ParIter(inner = this.inner.clone(), marker = Unit).fmt(f)
    }

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawParIter<Pair<K, V>>,
            marker: Unit,
        ): ParIterMut<K, V> = ParIterMut<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )
    }
}

/**
 * Parallel iterator over mutable references to values in a map.
 *
 * This iterator is created by the [parValuesMut] method on [HashMap].
 * See its documentation for more.
 *
 * [parValuesMut]: HashMap.parValuesMut
 */
public open class ParValuesMut<K, V> internal constructor(
    innerCtorArg: RawParIter<Pair<K, V>>,
    public var marker: Unit,
) : ParallelIterator {
    public var inner: RawParIter<Pair<K, V>> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<V> = this.inner.map({ x -> x.asMut().second }).driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder) {
        ParValues(inner = this.inner.clone(), marker = Unit).fmt(f)
    }

    public companion object {
        public operator fun <K, V> invoke(
            inner: RawParIter<Pair<K, V>>,
            marker: Unit,
        ): ParValuesMut<K, V> = ParValuesMut<K, V>(
            innerCtorArg = inner,
            marker = marker,
        )
    }
}

/**
 * Parallel iterator over entries of a consumed map.
 *
 * This iterator is created by the [intoParIter] method on [HashMap]
 * (provided by the [IntoParallelIterator] trait).
 * See its documentation for more.
 *
 * [intoParIter]: HashMap.intoParIter
 */
public open class IntoParIter<K, V, A : Allocator> internal constructor(
    innerCtorArg: RawIntoParIter<Pair<K, V>, A>,
) : ParallelIterator {
    public var inner: RawIntoParIter<Pair<K, V>, A> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<Pair<K, V>> = this.inner.driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder) {
        ParIter(inner = this.inner.parIter(), marker = Unit).fmt(f)
    }

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: RawIntoParIter<Pair<K, V>, A>,
        ): IntoParIter<K, V, A> = IntoParIter<K, V, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Parallel draining iterator over entries of a map.
 *
 * This iterator is created by the [parDrain] method on [HashMap].
 * See its documentation for more.
 *
 * [parDrain]: HashMap.parDrain
 */
public open class ParDrain<K, V, A : Allocator> internal constructor(
    innerCtorArg: RawParDrain<Pair<K, V>, A>,
) : ParallelIterator {
    public var inner: RawParDrain<Pair<K, V>, A> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<Pair<K, V>> = this.inner.driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder) {
        ParIter(inner = this.inner.parIter(), marker = Unit).fmt(f)
    }

    public companion object {
        public operator fun <K, V, A : Allocator> invoke(
            inner: RawParDrain<Pair<K, V>, A>,
        ): ParDrain<K, V, A> = ParDrain<K, V, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Visits (potentially in parallel) immutably borrowed keys in an arbitrary order.
 */
public fun <K, V, S, A : Allocator> MutableMap<K, V>.parKeys(): ParKeys<K, V> = ParKeys(inner = this.table.parIter(), marker = Unit)

/**
 * Visits (potentially in parallel) immutably borrowed values in an arbitrary order.
 */
public fun <K, V, S, A : Allocator> MutableMap<K, V>.parValues(): ParValues<K, V> = ParValues(inner = this.table.parIter(), marker = Unit)

/**
 * Visits (potentially in parallel) mutably borrowed values in an arbitrary order.
 */
public fun <K, V, S, A : Allocator> MutableMap<K, V>.parValuesMut(): ParValuesMut<K, V> = ParValuesMut(inner = this.table.parIter(), marker = Unit)

/**
 * Consumes (potentially in parallel) all values in an arbitrary order,
 * while preserving the map's allocated memory for reuse.
 */
public fun <K, V, S, A : Allocator> MutableMap<K, V>.parDrain(): ParDrain<K, V, A> = ParDrain(inner = this.table.parDrain())

/**
 * Returns `true` if the map is equal to another,
 * i.e. both maps contain the same keys mapped to the same values.
 *
 * This method runs in a potentially parallel fashion.
 */
public fun <K, V, S, A> MutableMap<K, V>.parEq(other: MutableMap<K, V>): Boolean where S : BuildHasher, A : Allocator = this.size == other.size && this.intoParIter().all({ (key, value) -> other.get(key).isSomeAnd({ v -> value == v }) })

public fun <K, V, S, A : Allocator> MutableMap<K, V>.intoParIter(): IntoParIter<K, V, A> = IntoParIter(inner = this.table.intoParIter())

/**
 * Collect (key, value) pairs from a parallel iterator into a
 * hashmap. If multiple pairs correspond to the same key, then the
 * ones produced earlier in the parallel iterator will be
 * overwritten, just as with a sequential iterator.
 */
public fun <K, V, S, P> fromParallelIteratorFromParIterHashMapKVSGlobal(parIter: P): MutableMap<K, V> where S : BuildHasher, P : IntoParallelIterator<Pair<K, V>> {
    val map = HashMap.default()
    map.parExtend(parIter)
    return map
}

/**
 * Extend a hash map with items from a parallel iterator.
 */
public fun <K, V, S, A, I> MutableMap<K, V>.parExtend(parIter: I) where S : BuildHasher, A : Allocator, I : IntoParallelIterator<Pair<K, V>> {
    extend(this, parIter)
}

/**
 * Extend a hash map with copied items from a parallel iterator.
 */
public fun <K, V, S, A, I> MutableMap<K, V>.parExtendKV(parIter: I) where S : BuildHasher, A : Allocator, I : IntoParallelIterator<Pair<K, V>> {
    extend(this, parIter)
}

// This is equal to the normal `HashMap` -- no custom advantage.
public fun <K, V, S, A, I> extend(map: MutableMap<K, V>, parIter: I) where S : BuildHasher, I : IntoParallelIterator, A : Allocator {
    val (list, len) = collect(parIter)
    // Keys may be already present or show multiple times in the iterator.
    // Reserve the entire length if the map is empty.
    // Otherwise reserve half the length (rounded up), so the map
    // will only resize twice in the worst case.
    val reserve =
        if (map.isEmpty()) {
            len
        } else {
            len.divCeil(2)
        }
    map.reserve(reserve)
    for (vec in list) {
        map.extend(vec)
    }
}
