// port-lint: source iter/adapters/fuse.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.fuse

import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.tryGetUnchecked
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedFused
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Source<I> = I.Source

/**
 * An iterator that yields `None` forever after the underlying iterator
 * yields `None` once.
 *
 * This `class` is created by [Iterator.fuse]. See its documentation
 * for more.
 */
public open class Fuse<I>(
    // NOTE: for `I: FusedIterator`, we never bother setting `None`, but
    // we still have to be prepared for that state due to variance.
    // See rust-lang/rust#85863
    public var iter: I?,
) : FuseImpl<I, Item>, Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@Fuse.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Fuse<I> = Fuse(iter)

    public open fun intoInner(): I? = this.iter

    // Any specialized implementation here is made internal
    // to avoid exposing default fns outside this trait.
    public override fun next(): Item? = this.next()

    public override fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.advanceBy(n)

    public override fun nth(n: Int): Item? = this.nth(n)

    public open fun last(): Item? =
        when (val matchVal = this.iter) {
            null -> null
            else -> {
                val iter = matchVal
                iter.last()
            }
        }

    public open fun count(): Int =
        when (val matchVal = this.iter) {
            null -> 0
            else -> {
                val iter = matchVal
                iter.count()
            }
        }

    public open fun sizeHint(): Pair<Int, Int?> =
        when (val matchVal = this.iter) {
            null -> Pair(0, 0)
            else -> {
                val iter = matchVal
                iter.sizeHint()
            }
        }

    public override fun <Residual, Acc, R> tryFold(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.tryFold(acc, fold)

    public open fun <Acc> fold(acc: Acc, fold: (Acc, Item) -> Acc): Acc {
        var acc1 = acc
        when (val matchVal = this.iter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = iter.fold(acc1, fold)
            }
        }
        return acc1
    }

    public override fun find(predicate: (Item) -> Boolean): Item? = this.find(predicate)

    public open fun iteratorGetUnchecked(idx: Int): Item =
        when (val matchVal = this.iter) {
            // SAFETY: the caller asserts there is an item at `i`, so we're not exhausted.
            null -> error("Entered Rust intrinsics::unreachable")
            // SAFETY: the caller must uphold the contract for
            // `Iterator.__iterator_get_unchecked`.
            else -> {
                val iter = matchVal
                tryGetUnchecked(iter, idx)
            }
        }

    public override fun nextBack(): Item? = this.nextBack()

    public override fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.advanceBackBy(n)

    public override fun nthBack(n: Int): Item? = this.nthBack(n)

    public override fun <Residual, Acc, R> tryRfold(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.tryRfold(acc, fold)

    public open fun <Acc> rfold(acc: Acc, fold: (Acc, Item) -> Acc): Acc {
        var acc1 = acc
        when (val matchVal = this.iter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = iter.rfold(acc1, fold)
            }
        }
        return acc1
    }

    public override fun rfind(predicate: (Item) -> Boolean): Item? = this.rfind(predicate)

    public open fun len(): Int =
        when (val matchVal = this.iter) {
            null -> 0
            else -> {
                val iter = matchVal
                iter.len()
            }
        }

    public open fun isEmpty(): Boolean =
        when (val matchVal = this.iter) {
            null -> true
            else -> {
                val iter = matchVal
                iter.isEmpty()
            }
        }

    /**
     * General `Fuse` impl which sets `iter = None` when exhausted.
     */
    public open fun nextI(): Item? = andThenOrClear(this.iter, Iterator::next)

    public open fun advanceByI(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val iter = this.iter ?: return when (val matchVal = NonZero.new<NonZeroUsizeInner, Int>(n)) {
    null -> Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
    else -> {
        val n1 = matchVal
        Result.Err<Unit, NonZero<Int, NonZeroUsizeInner>>(n1)
    }
}
        val res = iter.advanceBy(n)
        if (res.isErr()) {
            this.iter = null
        }
        return res
    }

    public open fun nthI(n: Int): Item? = andThenOrClear(this.iter, { iter -> iter.nth(n) })

    public open fun <Residual, Acc, R> tryFoldI(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        when (val matchVal = this.iter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = when (val tryResult = iter.tryFold(acc1, fold)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                this.iter = null
            }
        }
        return acc1
    }

    public open fun findI(predicate: (Item) -> Boolean): Item? = andThenOrClear(this.iter, { iter -> iter.indexOf(predicate).takeIf { it >= 0 } })

    public open fun nextBackI(): Item? = andThenOrClear(this.iter, { iter -> iter.nextBack() })

    public open fun advanceBackByI(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val iter = this.iter ?: return when (val matchVal = NonZero.new<NonZeroUsizeInner, Int>(n)) {
    null -> Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
    else -> {
        val n1 = matchVal
        Result.Err<Unit, NonZero<Int, NonZeroUsizeInner>>(n1)
    }
}
        val res = iter.advanceBackBy(n)
        if (res.isErr()) {
            this.iter = null
        }
        return res
    }

    public open fun nthBackI(n: Int): Item? = andThenOrClear(this.iter, { iter -> iter.nthBack(n) })

    public open fun <Residual, Acc, R> tryRfoldI(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        when (val matchVal = this.iter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = when (val tryResult = iter.tryRfold(acc1, fold)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                this.iter = null
            }
        }
        return acc1
    }

    public open fun rfindI(predicate: (Item) -> Boolean): Item? = andThenOrClear(this.iter, { iter -> iter.rfind(predicate) })

    /**
     * Specialized `Fuse` impl which doesn't bother clearing `iter` when exhausted.
     * However, we must still be prepared for the possibility that it was already cleared!
     */
    public open fun nextII(): Item? {
        return (this.iter ?: return null).next()
    }

    public open fun advanceByII(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        when (val matchVal = this.iter) {
            null -> when (val matchVal1 = NonZero.new<NonZeroUsizeInner, Int>(n)) {
                null -> Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
                else -> {
                    val n1 = matchVal1
                    Result.Err<Unit, NonZero<Int, NonZeroUsizeInner>>(n1)
                }
            }
            else -> {
                val iter = matchVal
                iter.advanceBy(n)
            }
        }

    public open fun nthII(n: Int): Item? {
        return (this.iter ?: return null).nth(n)
    }

    public open fun <Residual, Acc, R> tryFoldII(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        when (val matchVal = this.iter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = when (val tryResult = iter.tryFold(acc1, fold)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
            }
        }
        return acc1
    }

    public open fun findII(predicate: (Item) -> Boolean): Item? {
        return (this.iter ?: return null).indexOf(predicate).takeIf { it >= 0 }
    }

    public open fun nextBackII(): Item? {
        return (this.iter ?: return null).nextBack()
    }

    public open fun advanceBackByII(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        when (val matchVal = this.iter) {
            null -> when (val matchVal1 = NonZero.new<NonZeroUsizeInner, Int>(n)) {
                null -> Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
                else -> {
                    val n1 = matchVal1
                    Result.Err<Unit, NonZero<Int, NonZeroUsizeInner>>(n1)
                }
            }
            else -> {
                val iter = matchVal
                iter.advanceBackBy(n)
            }
        }

    public open fun nthBackII(n: Int): Item? {
        return (this.iter ?: return null).nthBack(n)
    }

    public open fun <Residual, Acc, R> tryRfoldII(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        when (val matchVal = this.iter) {
            null -> {}
            else -> {
                val iter = matchVal
                acc1 = when (val tryResult = iter.tryRfold(acc1, fold)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
            }
        }
        return acc1
    }

    public open fun rfindII(predicate: (Item) -> Boolean): Item? {
        return (this.iter ?: return null).rfind(predicate)
    }

    public companion object {
        public fun <I> new(iter: I): Fuse<I> = Fuse(iter = iter)

        /**
         * Creates a `Fuse` iterator from the default value of `I`.
         *
         * ```kotlin
         * val iter: Fuse<ArrayDeque<UByte>> = Default.default()
         * check(iter.len() == 0)
         * ```
         *
         * This is equivalent to `I.default().fuse()`[^fuseNote]; e.g. if
         * `I.default()` is not an empty iterator, then this will not be
         * an empty iterator.
         *
         * ```kotlin
         * open class Fourever : Iterable<UInt> {
         *     override fun iterator(): Iterator<UInt> = object : AbstractIterator<UInt>() {
         *         override fun computeNext() {
         *             when (val v = this@Fourever.next()) {
         *                 null -> done()
         *                 else -> setNext(v)
         *             }
         *         }
         *     }
         *
         *     public open fun next(): UInt? {
         *         return 4u
         *     }
         * }
         * var iter: Fuse<Fourever> = Default.default()
         * check(iter.next() == 4)
         * ```
         *
         * [^fuseNote]: if `I` does not override `Iterator.fuse`s default implementation
         */
        public fun <I> default(defaultIFn: () -> I): Fuse<I> = Fuse(iter = defaultIFn())
    }
}

// SAFETY: `TrustedLen` requires that an accurate length is reported via `sizeHint()`. As `Fuse`
// is just forwarding this to the wrapped iterator `I` this property is preserved and it is safe to
// implement `TrustedLen` here.

// SAFETY: `TrustedRandomAccess` requires that `sizeHint()` must be exact and cheap to call, and
// `Iterator.__iterator_get_unchecked()` must be implemented accordingly.
//
// This is safe to implement as `Fuse` is just forwarding these to the wrapped iterator `I`, which
// preserves these properties.

public val MAY_HAVE_SIDE_EFFECT: Boolean = I.MAY_HAVE_SIDE_EFFECT

/**
 * Fuse specialization trait
 *
 * We only need to worry about `self` methods, which
 * may exhaust the iterator without consuming it.
 */
public interface FuseImpl<I, Item> {
    public fun next(): Item?

    public fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>>

    public fun nth(n: Int): Item?

    public fun <Residual, Acc, R> tryFold(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual>

    public fun find(predicate: (Item) -> Boolean): Item?

    public fun nextBack(): Item?

    public fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>>

    public fun nthBack(n: Int): Item?

    public fun <Residual, Acc, R> tryRfold(acc: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual>

    public fun rfind(predicate: (Item) -> Boolean): Item?
}

// This is used by Flatten's SourceIter impl
public fun <Source, I> Fuse<I>.asInner(): Source where I : SourceIter<Source>, I : TrustedFused {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements.
    // TrustedFused guarantees that we'll never encounter a case where `self.iter` would
    // be set to None.
    return this.iter.unwrapUnchecked().asInner()
}

public fun <T, U> andThenOrClear(opt: T?, f: (T) -> U?): U? {
    var opt1 = opt
    val x = f((opt1 ?: return null))
    if (x == null) {
        opt1 = null
    }
    return x
}
