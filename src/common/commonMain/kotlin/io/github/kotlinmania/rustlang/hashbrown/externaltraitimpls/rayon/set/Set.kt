// port-lint: source external_trait_impls/rayon/set.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.set

import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.num.divCeil
import io.github.kotlinmania.rustlang.hashbrown.HashSet
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Allocator
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.helpers.collect
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.ParKeys
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.IntoParallelIterator
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.ParallelIterator
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.plumbing.UnindexedConsumer

// Rayon extensions for `HashSet`.

/**
 * Parallel iterator over elements of a consumed set.
 *
 * This iterator is created by the [intoParIter] method on [HashSet]
 * (provided by the [IntoParallelIterator] trait).
 * See its documentation for more.
 *
 * [intoParIter]: rayon.iter.IntoParallelIterator.intoParIter
 * [IntoParallelIterator]: rayon.iter.IntoParallelIterator
 */
public open class IntoParIter<T, A : Allocator> internal constructor(
    innerCtorArg: io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.IntoParIter<T, Unit, A>,
) : ParallelIterator {
    public var inner: io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.IntoParIter<T, Unit, A> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.map({ (k, _) -> k }).driveUnindexed(consumer)

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.IntoParIter<T, Unit, A>,
        ): IntoParIter<T, A> = IntoParIter<T, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Parallel draining iterator over entries of a set.
 *
 * This iterator is created by the [parDrain] method on [HashSet].
 * See its documentation for more.
 *
 * [parDrain]: HashSet.parDrain
 */
public open class ParDrain<T, A : Allocator> internal constructor(
    innerCtorArg: io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.ParDrain<T, Unit, A>,
) : ParallelIterator {
    public var inner: io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.ParDrain<T, Unit, A> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.map({ (k, _) -> k }).driveUnindexed(consumer)

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            inner: io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.map.ParDrain<T, Unit, A>,
        ): ParDrain<T, A> = ParDrain<T, A>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Parallel iterator over shared references to elements in a set.
 *
 * This iterator is created by the [parIter] method on [HashSet]
 * (provided by the [IntoParallelRefIterator] trait).
 * See its documentation for more.
 *
 * [parIter]: rayon.iter.IntoParallelRefIterator.parIter
 * [IntoParallelRefIterator]: rayon.iter.IntoParallelRefIterator
 */
public open class ParIter<T> internal constructor(
    innerCtorArg: ParKeys<T, Unit>,
) : ParallelIterator {
    public var inner: ParKeys<T, Unit> = innerCtorArg
        internal set

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> = this.inner.driveUnindexed(consumer)

    public companion object {
        public operator fun <T> invoke(
            inner: ParKeys<T, Unit>,
        ): ParIter<T> = ParIter<T>(
            innerCtorArg = inner,
        )
    }
}

/**
 * Parallel iterator over shared references to elements in the difference of
 * sets.
 *
 * This iterator is created by the [parDifference] method on [HashSet].
 * See its documentation for more.
 *
 * [parDifference]: HashSet.parDifference
 */
public open class ParDifference<T, S, A : Allocator>(
    public var a: MutableSet<T>,
    public var b: MutableSet<T>,
)

public fun <Hasher, T, S, A, C> ParDifference<T, S, A>.driveUnindexed(consumer: C): Result where S : BuildHasher<Hasher>, A : Allocator, C : UnindexedConsumer<T> = this.a.intoParIter().filter({ x -> !this.b.contains(x) }).driveUnindexed(consumer)

/**
 * Parallel iterator over shared references to elements in the symmetric
 * difference of sets.
 *
 * This iterator is created by the [parSymmetricDifference] method on
 * [HashSet].
 * See its documentation for more.
 *
 * [parSymmetricDifference]: HashSet.parSymmetricDifference
 */
public open class ParSymmetricDifference<T, S, A : Allocator>(
    public var a: MutableSet<T>,
    public var b: MutableSet<T>,
)

public fun <Hasher, T, S, A, C> ParSymmetricDifference<T, S, A>.driveUnindexed(consumer: C): Result where S : BuildHasher<Hasher>, A : Allocator, C : UnindexedConsumer<T> = this.a.parDifference(this.b).chain(this.b.parDifference(this.a)).driveUnindexed(consumer)

/**
 * Parallel iterator over shared references to elements in the intersection of
 * sets.
 *
 * This iterator is created by the [parIntersection] method on [HashSet].
 * See its documentation for more.
 *
 * [parIntersection]: HashSet.parIntersection
 */
public open class ParIntersection<T, S, A : Allocator>(
    public var a: MutableSet<T>,
    public var b: MutableSet<T>,
)

public fun <Hasher, T, S, A, C> ParIntersection<T, S, A>.driveUnindexed(consumer: C): Result where S : BuildHasher<Hasher>, A : Allocator, C : UnindexedConsumer<T> = this.a.intoParIter().filter({ x -> this.b.contains(x) }).driveUnindexed(consumer)

/**
 * Parallel iterator over shared references to elements in the union of sets.
 *
 * This iterator is created by the [parUnion] method on [HashSet].
 * See its documentation for more.
 *
 * [parUnion]: HashSet.parUnion
 */
public open class ParUnion<T, S, A : Allocator>(
    public var a: MutableSet<T>,
    public var b: MutableSet<T>,
)

public fun <Hasher, T, S, A, C> ParUnion<T, S, A>.driveUnindexed(consumer: C): Result where S : BuildHasher<Hasher>, A : Allocator, C : UnindexedConsumer<T> {
    // We'll iterate one set in full, and only the remaining difference from the other.
    // Use the smaller set for the difference in order to reduce hash lookups.
    val (smaller, larger) =
        if (this.a.size <= this.b.size) {
            Pair(this.a, this.b)
        } else {
            Pair(this.b, this.a)
        }
    return larger.intoParIter().chain(smaller.parDifference(larger)).driveUnindexed(consumer)
}

/**
 * Visits (potentially in parallel) the values representing the union,
 * i.e. all the values in `self` or `other`, without duplicates.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parUnion(other: MutableSet<T>): ParUnion<T, S, A> where S : BuildHasher<Hasher>, A : Allocator = ParUnion(a = this, b = other)

/**
 * Visits (potentially in parallel) the values representing the difference,
 * i.e. the values that are in `self` but not in `other`.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parDifference(other: MutableSet<T>): ParDifference<T, S, A> where S : BuildHasher<Hasher>, A : Allocator = ParDifference(a = this, b = other)

/**
 * Visits (potentially in parallel) the values representing the symmetric
 * difference, i.e. the values that are in `self` or in `other` but not in both.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parSymmetricDifference(other: MutableSet<T>): ParSymmetricDifference<T, S, A> where S : BuildHasher<Hasher>, A : Allocator = ParSymmetricDifference(a = this, b = other)

/**
 * Visits (potentially in parallel) the values representing the
 * intersection, i.e. the values that are both in `self` and `other`.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parIntersection(other: MutableSet<T>): ParIntersection<T, S, A> where S : BuildHasher<Hasher>, A : Allocator = ParIntersection(a = this, b = other)

/**
 * Returns `true` if `self` has no elements in common with `other`.
 * This is equivalent to checking for an empty intersection.
 *
 * This method runs in a potentially parallel fashion.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parIsDisjoint(other: MutableSet<T>): Boolean where S : BuildHasher<Hasher>, A : Allocator = this.intoParIter().all({ x -> !other.contains(x) })

/**
 * Returns `true` if the set is a subset of another,
 * i.e. `other` contains at least all the values in `self`.
 *
 * This method runs in a potentially parallel fashion.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parIsSubset(other: MutableSet<T>): Boolean where S : BuildHasher<Hasher>, A : Allocator =
    if (this.size <= other.size) {
        this.intoParIter().all({ x -> other.contains(x) })
    } else {
        false
    }

/**
 * Returns `true` if the set is a superset of another,
 * i.e. `self` contains at least all the values in `other`.
 *
 * This method runs in a potentially parallel fashion.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parIsSuperset(other: MutableSet<T>): Boolean where S : BuildHasher<Hasher>, A : Allocator = other.parIsSubset(this)

/**
 * Returns `true` if the set is equal to another,
 * i.e. both sets contain the same values.
 *
 * This method runs in a potentially parallel fashion.
 */
public fun <Hasher, T, S, A> MutableSet<T>.parEq(other: MutableSet<T>): Boolean where S : BuildHasher<Hasher>, A : Allocator = this.size == other.size && this.parIsSubset(other)

/**
 * Consumes (potentially in parallel) all values in an arbitrary order,
 * while preserving the set's allocated memory for reuse.
 */
public fun <T, S, A> MutableSet<T>.parDrain(): ParDrain<T, A> where A : Allocator = ParDrain(inner = this.map.parDrain())

public fun <T, S, A : Allocator> MutableSet<T>.intoParIter(): IntoParIter<T, A> = IntoParIter(inner = this.map.intoParIter())

/**
 * Collect values from a parallel iterator into a hashset.
 */
public fun <Hasher, T, S, P> fromParallelIteratorFromParIterHashSetTSGlobal(parIter: P): MutableSet<T> where S : BuildHasher<Hasher>, P : IntoParallelIterator<T> {
    val set = HashSet.default()
    set.parExtend(parIter)
    return set
}

/**
 * Extend a hash set with items from a parallel iterator.
 */
public fun <Hasher, T, S, I> MutableSet<T>.parExtend(parIter: I) where S : BuildHasher<Hasher>, I : IntoParallelIterator<T> {
    extend(this, parIter)
}

/**
 * Extend a hash set with copied items from a parallel iterator.
 */
public fun <Hasher, T, S, I> MutableSet<T>.parExtendT(parIter: I) where S : BuildHasher<Hasher>, I : IntoParallelIterator<T> {
    extend(this, parIter)
}

// This is equal to the normal `HashSet` -- no custom advantage.
public fun <Hasher, T, S, I, A> extend(set: MutableSet<T>, parIter: I) where S : BuildHasher<Hasher>, A : Allocator, I : IntoParallelIterator {
    val (list, len) = collect(parIter)
    // Values may be already present or show multiple times in the iterator.
    // Reserve the entire length if the set is empty.
    // Otherwise reserve half the length (rounded up), so the set
    // will only resize twice in the worst case.
    val reserve =
        if (set.isEmpty()) {
            len
        } else {
            len.divCeil(2)
        }
    set.reserve(reserve)
    for (vec in list) {
        set.extend(vec)
    }
}
