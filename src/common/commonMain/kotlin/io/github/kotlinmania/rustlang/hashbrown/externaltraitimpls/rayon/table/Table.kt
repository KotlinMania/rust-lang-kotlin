// port-lint: source external_trait_impls/rayon/table.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.table

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.HashTable
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw.RawIntoParIter
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw.RawParDrain
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw.RawParIter
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.ParallelIterator
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.plumbing.UnindexedConsumer

// Rayon extensions for `HashTable`.

/**
 * Parallel iterator over shared references to entries in a map.
 *
 * This iterator is created by the [parIter] method on [HashTable]
 * (provided by the [IntoParallelRefIterator] trait).
 * See its documentation for more.
 *
 * [parIter]: rayon.iter.IntoParallelRefIterator.parIter
 * [IntoParallelRefIterator]: rayon.iter.IntoParallelRefIterator
 */
public open class ParIter<T>(
    public var inner: RawParIter<T>,
    public var marker: Unit,
) : ParallelIterator {
    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.map({ x -> x.asRef() }).driveUnindexed(consumer)

    public open fun clone(): ParIter<T> = ParIter(inner = this.inner.clone(), marker = Unit)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        val iter = this.inner.iter().map({ x -> x.asRef() })
        return object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(iter).finish()
    }
}

/**
 * Parallel iterator over mutable references to entries in a map.
 *
 * This iterator is created by the [parIterMut] method on [HashTable]
 * (provided by the [IntoParallelRefMutIterator] trait).
 * See its documentation for more.
 *
 * [parIterMut]: rayon.iter.IntoParallelRefMutIterator.parIterMut
 * [IntoParallelRefMutIterator]: rayon.iter.IntoParallelRefMutIterator
 */
public open class ParIterMut<T>(
    public var inner: RawParIter<T>,
    public var marker: Unit,
) : ParallelIterator {
    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.map({ x -> x.asMut() }).driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = ParIter(inner = this.inner.clone(), marker = Unit).fmt(f)
}

/**
 * Parallel iterator over entries of a consumed map.
 *
 * This iterator is created by the [intoParIter] method on [HashTable]
 * (provided by the [IntoParallelIterator] trait).
 * See its documentation for more.
 *
 * [intoParIter]: rayon.iter.IntoParallelIterator.intoParIter
 * [IntoParallelIterator]: rayon.iter.IntoParallelIterator
 */
public open class IntoParIter<T, A : Allocator> internal constructor(
    innerCtorArg: RawIntoParIter<T, A>,
) : ParallelIterator {
    public var inner: RawIntoParIter<T, A> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = ParIter(inner = this.inner.parIter(), marker = Unit).fmt(f)

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: RawIntoParIter<T, A>,
        ): IntoParIter<T, A> = IntoParIter<T, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Parallel draining iterator over entries of a map.
 *
 * This iterator is created by the [parDrain] method on [HashTable].
 * See its documentation for more.
 *
 * [parDrain]: HashTable.parDrain
 */
public open class ParDrain<T, A : Allocator> internal constructor(
    innerCtorArg: RawParDrain<T, A>,
) : ParallelIterator {
    public var inner: RawParDrain<T, A> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.driveUnindexed(consumer)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = ParIter(inner = this.inner.parIter(), marker = Unit).fmt(f)

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: RawParDrain<T, A>,
        ): ParDrain<T, A> = ParDrain<T, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Consumes (potentially in parallel) all values in an arbitrary order,
 * while preserving the map's allocated memory for reuse.
 */
public fun <T, A : Allocator> HashTable<T, A>.parDrain(): ParDrain<T, A> = ParDrain(inner = this.raw.parDrain())

public fun <T, A : Allocator> HashTable<T, A>.intoParIter(): IntoParIter<T, A> = IntoParIter(inner = this.raw.intoParIter())
