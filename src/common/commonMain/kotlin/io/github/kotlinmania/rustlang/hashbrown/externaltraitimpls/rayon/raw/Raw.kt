// port-lint: source external_trait_impls/rayon/raw.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.rayon.raw

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.hashbrown.Guard
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Allocator
import io.github.kotlinmania.rustlang.hashbrown.bridgeUnindexed
import io.github.kotlinmania.rustlang.hashbrown.raw.Bucket
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIter
import io.github.kotlinmania.rustlang.hashbrown.raw.RawIterRange
import io.github.kotlinmania.rustlang.hashbrown.raw.RawTable
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.ParallelIterator
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.plumbing.Folder
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.plumbing.UnindexedConsumer
import io.github.kotlinmania.rustlang.hashbrown.rayon.iter.plumbing.UnindexedProducer

/**
 * Parallel iterator which returns a raw pointer to every full bucket in the table.
 */
public open class RawParIter<T>(
    public var iter: RawIterRange<T>,
) : ParallelIterator {
    public open fun iter(): RawIterRange<T> = this.iter.clone()

    public open fun clone(): RawParIter<T> = RawParIter(iter = this.iter.clone())

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<Bucket<T>> {
        val producer = ParIterProducer(iter = this.iter)
        return bridgeUnindexed(producer, consumer)
    }

    public companion object {
        public fun <T> from(it: RawIter<T>): RawParIter<T> = RawParIter(iter = it.iter)
    }
}

/**
 * Producer which returns a `Bucket<T>` for every element.
 */
public open class ParIterProducer<T>(
    public var iter: RawIterRange<T>,
) : UnindexedProducer {
    public open fun split(): Pair<ParIterProducer<T>, ParIterProducer<T>?> {
        val (left, right) = this.iter.split()
        val left1 = ParIterProducer(iter = left)
        val right1 = right.map({ right1 -> ParIterProducer(iter = right1) })
        return Pair(left1, right1)
    }

    public open fun <F> foldWith(folder: F): F where F : Folder<Bucket<T>> = folder.consumeIter(this.iter)
}

/**
 * Parallel iterator which consumes a table and returns elements.
 */
public open class RawIntoParIter<T, A : Allocator> internal constructor(
    tableCtorArg: RawTable<T, A>,
) : ParallelIterator {
    public var table: RawTable<T, A> = tableCtorArg
        internal set

    public open fun parIter(): RawParIter<T> = this.table.parIter()

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> {
        val iter = this.table.iter().iter
        val guard =
            Guard(this.table.intoAllocation(), { alloc ->
                when (val matchVal = alloc) {
                    null -> {}
                    else -> {
                        val ptr = matchVal.first
                        val layout = matchVal.second
                        val alloc1 = matchVal.third
                        run {
                            alloc1.deallocate(ptr, layout)
                        }
                    }
                }
            })
        val producer = ParDrainProducer(iter = iter)
        return bridgeUnindexed(producer, consumer)
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            table: RawTable<T, A>,
        ): RawIntoParIter<T, A> = RawIntoParIter<T, A>(
            tableCtorArg = table,
        )
    }
}

/**
 * Parallel iterator which consumes elements without freeing the table storage.
 */
public open class RawParDrain<T, A : Allocator> internal constructor(
    tableCtorArg: NonNull,
    public var marker: Unit,
) : ParallelIterator {
    // We don't use a &a mut RawTable<T> because we want RawParDrain to be
    // covariant over T.
    public var table: NonNull = tableCtorArg
        internal set

    public open fun parIter(): RawParIter<T> = this.table.asRef().parIter()

    public open fun <C> driveUnindexed(consumer: C): Result where C : UnindexedConsumer<T> {
        val guard =
            Guard(this.table, { table -> run {
                table.asMut().clearNoDrop()
            } })
        val iter = this.table.asRef().iter().iter
        forget(this)
        val producer = ParDrainProducer(iter = iter)
        return bridgeUnindexed(producer, consumer)
    }

    public open fun drop() {
        // If driveUnindexed is not called then simply clear the table.
        run {
            this.table.asMut().clear()
        }
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            table: NonNull,
            marker: Unit,
        ): RawParDrain<T, A> = RawParDrain<T, A>(
            tableCtorArg = table,
            marker = marker,
        )
    }
}

/**
 * Producer which will consume all elements in the range, even if it is dropped
 * halfway through.
 */
public open class ParDrainProducer<T>(
    public var iter: RawIterRange<T>,
) : UnindexedProducer {
    public open fun split(): Pair<ParDrainProducer<T>, ParDrainProducer<T>?> {
        val (left, right) = this.iter.clone().split()
        forget(this)
        val left1 = ParDrainProducer(iter = left)
        val right1 = right.map({ right1 -> ParDrainProducer(iter = right1) })
        return Pair(left1, right1)
    }

    public open fun <F> foldWith(folder: F): F where F : Folder<T> {
        var folder1 = folder
        // Make sure to modify the iterator in-place so that any remaining
        // elements are processed in our Drop impl.
        for (item in this.iter) {
            folder1 = folder1.consume(item.read())
            if (folder1.full()) {
                return folder1
            }
        }
        // If we processed all elements then we don't need to run the drop.
        forget(this)
        return folder1
    }

    public open fun drop() {
        // Drop all remaining elements
        if (needsDrop<T>()) {
            for (item in this.iter) {
                run {
                    item.drop()
                }
            }
        }
    }
}

/**
 * Returns a parallel iterator over the elements in a `RawTable`.
 */
public fun <T, A : Allocator> RawTable<T, A>.parIter(): RawParIter<T> = RawParIter(iter = this.iter().iter)

/**
 * Returns a parallel iterator over the elements in a `RawTable`.
 */
public fun <T, A : Allocator> RawTable<T, A>.intoParIter(): RawIntoParIter<T, A> = RawIntoParIter(table = this)

/**
 * Returns a parallel iterator which consumes all elements of a `RawTable`
 * without freeing its memory allocation.
 */
public fun <T, A : Allocator> RawTable<T, A>.parDrain(): RawParDrain<T, A> = RawParDrain(table = NonNull.from(this), marker = Unit)
