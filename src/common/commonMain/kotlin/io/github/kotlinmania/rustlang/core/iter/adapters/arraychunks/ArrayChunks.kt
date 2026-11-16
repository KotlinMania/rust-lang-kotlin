// port-lint: source iter/adapters/array_chunks.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.arraychunks

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result

public typealias Source<I> = I.Source

/**
 * An iterator over `N` elements of the iterator at a time.
 *
 * The chunks do not overlap. If `N` does not divide the length of the
 * iterator, then the last up to `N - 1` elements will be omitted.
 *
 * This `class` is created by the [`arrayChunks`][Iterator.arrayChunks]
 * method on [Iterator]. See its documentation for more.
 */
public open class ArrayChunks<I> internal constructor(
    public var iter: I,
    remainderCtorArg: IntoIter<Item>?,
) {
    public var remainder: IntoIter<Item>? = remainderCtorArg
        internal set

    public open fun clone(): ArrayChunks<I> = ArrayChunks(iter, remainder)

    public companion object {
        public operator fun <I> invoke(
            iter: I,
            remainder: IntoIter<Item>?,
        ): ArrayChunks<I> = ArrayChunks<I>(
            iter = iter,
            remainderCtorArg = remainder,
        )
    }
}

public fun <Item, I> ArrayChunks.Companion.new(iter: I, n: Int): ArrayChunks<I> {
    check(n != 0) { "chunk size must be non-zero" }
    return ArrayChunks(iter = iter, remainder = null)
}

/**
 * Returns an iterator over the remaining elements of the original iterator
 * that are not going to be returned by this iterator. The returned
 * iterator will yield at most `N - 1` elements.
 *
 * # Example
 * ```kotlin
 * val x = listOf(1, 2, 3, 4, 5).intoIter().arrayChunks()
 * var rem = x.intoRemainder()
 * check(rem.next() == 5)
 * check(rem.next() == null)
 * ```
 */
public fun <Item, I> ArrayChunks<I>.intoRemainder(n: Int): IntoIter<Item> {
    if (this.remainder == null) {
        while (true) {
            val matchVal = this.next()
            if (matchVal == null) break
        }
    }
    return this.remainder.unwrapOrDefault()
}

public fun <Item, I> ArrayChunks<I>.next(n: Int): List<Item>? = this.tryForEach(ControlFlow.Break).breakValue()

public fun <Item, I> ArrayChunks<I>.sizeHint(n: Int): Pair<Int, Int?> {
    val (lower, upper) = this.iter.sizeHint()
    return Pair(lower / n, upper.map({ n -> n / n }))
}

public fun <Item, I> ArrayChunks<I>.count(n: Int): Int = this.iter.count() / n

public fun <Item, I, B, R> ArrayChunks<I>.tryFold(init: B, f: (B, List<Item>) -> R, n: Int): R where R : Try<R, B, Any?> {
    var acc = init
    while (true) {
        run {
            val matchVal = this.iter.nextChunk()
            when (matchVal) { is Result.Ok -> {
                val chunk = matchVal.value
                acc = when (val tryResult = f(acc, chunk)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            }; is Result.Err -> {
                val remainder = matchVal.error
                // Make sure to not overwrite `self.remainder` with an empty array
                // when `next` is called after `ArrayChunks` exhaustion.
                this.remainder.getOrInsert(remainder)
                break
            } }
        }
    }
}

public fun <Item, I, B> ArrayChunks<I>.fold(init: B, f: (B, List<Item>) -> B, n: Int): B = ArrayChunks.fold(this, init, f)

public fun <I> ArrayChunks<I>.nextBack(n: Int): Item? = this.tryRfold({ x -> ControlFlow.Break(x) }).breakValue()

public fun <I, B, R> ArrayChunks<I>.tryRfold(init: B, f: (B, Item) -> R, n: Int): R where R : Try<R, B, Any?> {
    // We are iterating from the back we need to first handle the remainder.
    this.nextBackRemainder()
    var acc = init
    // NB remainder is handled by `nextBackRemainder`, so
    // `nextChunkBack` can't return `Err` with non-empty remainder
    // (assuming correct `I as ExactSizeIterator` impl).
    while (true) {
        val matchVal = this.iter.nextChunkBack()
        if (matchVal !is Result.Ok) break
        val chunk = (matchVal as Result.Ok).value
        acc = when (val tryResult = f(acc, chunk)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
    }
    return acc
}

public fun <I, Aaa> ArrayChunks<I>.rfold(init: Aaa, fold: (Aaa, Item) -> Aaa, n: Int): Aaa = this.tryRfold(init, NeverShortCircuit.wrapMut2(fold)).first

/**
 * Updates `self.remainder` such that `self.iter.len` is divisible by `N`.
 */
public fun <I> ArrayChunks<I>.nextBackRemainder(n: Int) {
    // Make sure to not override `self.remainder` with an empty array
    // when `nextBack` is called after `ArrayChunks` exhaustion.
    if (this.remainder != null) {
        return
    }
    // We use the `ExactSizeIterator` implementation of the underlying
    // iterator to know how many remaining elements there are.
    val rem = this.iter.len() % n
    // Take the last `rem` elements out of `self.iter`.
    val remainder = this.iter.byRef().rev().take(rem).nextChunk().unwrapErrUnchecked()
    // We used `.rev()` above, so we need to re-reverse the reminder
    remainder.asMutSlice().reverse()
    this.remainder = remainder
}

public fun <I> ArrayChunks<I>.len(n: Int): Int = this.iter.len() / n

public fun <I> ArrayChunks<I>.isEmpty(n: Int): Boolean = this.iter.len() < n

public interface SpecFold<T> {
    public fun <B> fold(init: B, f: (B, Item) -> B): B
}

public fun <Item, I, B> ArrayChunks<I>.fold(init: B, f: (B, Item) -> B, n: Int): B = this.tryFold(init, NeverShortCircuit.wrapMut2(f)).first

public fun <Source, Item, I> ArrayChunks<I>.asInner(n: Int): Source where I : SourceIter<Any?> {
    // SAFETY: unchecked function forwarding to unchecked function with the same requirements
    return this.iter.asInner()
}

public val EXPAND_BY: NonZero<Int, NonZeroUsizeInner>? = I.EXPAND_BY

public val MERGE_BY: NonZero<Int, NonZeroUsizeInner>? = run {
    val matchVal = Pair(I.MERGE_BY, NonZero.new(N))
    when {
        matchVal.first != null && matchVal.second != null -> {
            val m = matchVal.first
            val n = matchVal.second
            m.checkedMul(n)
        }
        else -> null
    }
}
