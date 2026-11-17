// port-lint: source iter/adapters/array_chunks.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.arraychunks

import io.github.kotlinmania.rustlang.core.array.fromFn
import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.iter.adapters.SourceIter
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
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
public open class ArrayChunks<I, Item> internal constructor(
    public var iter: I,
    remainderCtorArg: IntoIter<Item>?,
) : SpecFold, Iterable<List<Item>> {
    public var remainder: IntoIter<Item>? = remainderCtorArg
        internal set

    override fun iterator(): Iterator<List<Item>> = object : AbstractIterator<List<Item>>() {
        override fun computeNext() {
            when (val v = this@ArrayChunks.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): ArrayChunks<I, Item> = ArrayChunks(iter, remainder)

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
    public open fun intoRemainder(): IntoIter<Item> {
        if (this.remainder == null) {
            while (true) {
                val matchVal = this.next()
                if (matchVal == null) break
            }
        }
        return this.remainder.unwrapOrDefault()
    }

    public open fun next(): List<Item>? = this.tryForEach(ControlFlow.Break).breakValue()

    public open fun sizeHint(): Pair<Int, Int?> {
        val (lower, upper) = this.iter.sizeHint()
        return Pair(lower / N, upper.map({ n -> n / N }))
    }

    public open fun count(): Int = this.iter.count() / N

    public open fun <Residual, B, R> tryFold(init: B, f: (B, List<Item>) -> R): R where R : Try<R, B, Residual> {
        var acc = init
        while (true) {
            run {
                val matchVal = this.iter.nextChunk()
                when (matchVal) { is Result.Ok<*, *> -> {
                    val chunk = matchVal.okValue()
                    acc = when (val tryResult = f(acc, chunk)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                }; is Result.Err<*, *> -> {
                    val remainder = matchVal.errValue()
                    // Make sure to not overwrite `self.remainder` with an empty array
                    // when `next` is called after `ArrayChunks` exhaustion.
                    this.remainder.getOrInsert(remainder)
                    break
                } }
            }
        }
    }

    public override fun <B> fold(init: B, f: (B, List<Item>) -> B): B = ArrayChunks.fold(this, init, f)

    public open fun nextBack(): List<Item>? = this.tryRfold(Unit, { x -> ControlFlow.Break(x) }).breakValue()

    public open fun <Residual, B, R> tryRfold(init: B, f: (B, List<Item>) -> R): R where R : Try<R, B, Residual> {
        // We are iterating from the back we need to first handle the remainder.
        this.nextBackRemainder()
        var acc = init
        // NB remainder is handled by `nextBackRemainder`, so
        // `nextChunkBack` can't return `Err` with non-empty remainder
        // (assuming correct `I as ExactSizeIterator` impl).
        while (true) {
            val matchVal = this.iter.nextChunkBack()
            if (matchVal !is Result.Ok<*, *>) break
            val chunk = (matchVal as Result.Ok<*, *>).okValue()
            acc = when (val tryResult = f(acc, chunk)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        }
        return acc
    }

    public open fun <Aaa> rfold(init: Aaa, fold: (Aaa, List<Item>) -> Aaa): Aaa = this.tryRfold(init, NeverShortCircuit.wrapMut2(fold)).first

    /**
     * Updates `self.remainder` such that `self.iter.len` is divisible by `N`.
     */
    public open fun nextBackRemainder() {
        // Make sure to not override `self.remainder` with an empty array
        // when `nextBack` is called after `ArrayChunks` exhaustion.
        if (this.remainder != null) {
            return
        }
        // We use the `ExactSizeIterator` implementation of the underlying
        // iterator to know how many remaining elements there are.
        val rem = this.iter.len() % N
        // Take the last `rem` elements out of `self.iter`.
        val remainder = this.iter.byRef().rev().take(rem).nextChunk().unwrapErrUnchecked()
        // We used `.rev()` above, so we need to re-reverse the reminder
        remainder.asMutSlice().reverse()
        this.remainder = remainder
    }

    public open fun len(): Int = this.iter.len() / N

    public open fun isEmpty(): Boolean = this.iter.len() < N

    public open fun <B> foldSpecFold(init: B, f: (B, List<Item>) -> B): B = this.tryFold(init, NeverShortCircuit.wrapMut2(f)).first

    public companion object {
        public operator fun <I, Item> invoke(
            iter: I,
            remainder: IntoIter<Item>?,
        ): ArrayChunks<I, Item> = ArrayChunks<I, Item>(
            iter = iter,
            remainderCtorArg = remainder,
        )

        public fun <I> new(iter: I, n: Int): ArrayChunks<I, Item> {
            check(n != 0) { "chunk size must be non-zero" }
            return ArrayChunks(iter = iter, remainder = null)
        }
    }
}

public interface SpecFold {
    public fun <B> fold(init: B, f: (B, Item) -> B): B
}

public fun <I, B> ArrayChunks<I, Item>.fold(init: B, f: (B, List<Item>) -> B, n: Int): B where I : TrustedRandomAccessNoCoerce {
    var accum = init
    val innerLen = this.iter.size()
    var i = 0
    // Use a while loop because (0..len).stepBy(N) doesn't optimize well.
    while (innerLen - i >= n) {
        val chunk =
            fromFn({ local ->
                // SAFETY: The method consumes the iterator and the loop condition ensures that
                // all accesses are in bounds and only happen once.
                run {
                    val idx = i + local
                    this.iter.iteratorGetUnchecked(idx)
                }
            }, n)
        accum = f(accum, chunk)
        i += n
    }
    // unlike tryFold this method does not need to take care of the remainder
    // since `self` will be dropped
    return accum
}

public fun <Source, I> ArrayChunks<I, Item>.asInner(n: Int): Source where I : SourceIter<Source> {
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
